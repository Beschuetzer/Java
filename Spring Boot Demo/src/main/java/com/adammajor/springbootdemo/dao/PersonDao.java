package com.adammajor.springbootdemo.dao;

import com.adammajor.springbootdemo.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//The Data Access Object (DAO) pattern is a structural pattern
//that allows us to isolate the application/business layer
//from the persistence layer (usually a relational database, but it could be any other persistence mechanism) using an abstract API.
//https://www.baeldung.com/java-dao-pattern
public interface PersonDao {
    int insertPerson(UUID id, Person person);

    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonById(UUID id);

    int deletePerson(UUID id);
    int updatePerson(UUID id, Person toUpdate);
}
