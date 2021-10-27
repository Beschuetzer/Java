package com.adammajor.springbootdemo.dao;

import com.adammajor.springbootdemo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//telling Spring Boot to instantiate this as a bean
//@Component <--- works to but Respository is more specific
@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{
    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        System.out.println("person = " + person);
        DB.add(new Person(id, person.getFirstName(), person.getLastName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePerson(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if (personMaybe.isEmpty()) {
            return 0;
        }

        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePerson(UUID id, Person toUpdate) {
        //Optional<Person> returns null if no matching Person
        //.orElse(0) says to return 0 in that case;
//        return selectPersonById(id)
//                .map(person -> {
//                    int indexToUpdate = DB.indexOf(person);
//                    System.out.println("indexToUpdate = " + indexToUpdate);
//                    if (indexToUpdate >= 0) {
//                        DB.set(indexToUpdate, new Person(id, toUpdate.getName()));
//                        return 1;
//                    }
//                    return 0;
//                })
//                .orElse(0);
        int indexToUpdate = -1;
        for (int i = 0; i < DB.size(); i++) {
            Person person = DB.get(i);
            if (id.equals(person.getId())) {
               indexToUpdate = i;
                break;
            }
        }

        if (indexToUpdate != -1) {
            Person newPerson = getNewPerson(DB.get(indexToUpdate), toUpdate);
            if (newPerson == null) return 0;

            DB.set(indexToUpdate, newPerson);
            return 1;
        }
        return 0;
    }

    private Person getNewPerson(Person currentPerson, Person toUpdate) {
        System.out.println("currentPerson = " + currentPerson);
        System.out.println("toUpdate = " + toUpdate);
        Person.Builder personBuilder = new Person.Builder();
        return personBuilder
                .setId(currentPerson.getId())
                .setFirstName(toUpdate.getFirstName() != null ? toUpdate.getFirstName() : currentPerson.getFirstName())
                .setLastName(toUpdate.getLastName() != null ? toUpdate.getLastName() : currentPerson.getLastName())
                .build()
        ;
    }
}
