package com.adammajor.springbootdemo.api;

import com.adammajor.springbootdemo.model.Person;
import com.adammajor.springbootdemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//making this class the a REST controller (GET, POST, PUT, UPDATE, DELETE, etc)
@RequestMapping("api/v1/person")
@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //telling Springboot this is a post request
    @PostMapping
    public void addPerson(@RequestBody Person person) {
        //@RequestBody tells Spring Boot to convert Json body to Person object
        System.out.println("person.toString() = " + person.toString());
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getPeople() {
        return personService.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        Optional<Person> optionalPerson = personService.getPersonById(id);
        System.out.println("optionalPerson = " + optionalPerson);
        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Person with id of '" + id + "'");
    }

    @DeleteMapping(path = "{id}")
    public void deletePersonById(@PathVariable("id") UUID id) {
        System.out.println("id = " + id);
         personService.deletePerson(id);
    }

    @PutMapping(path = "{id}")
    public void updatePersonById(@PathVariable("id") UUID id, @Validated @NonNull @RequestBody Person person) {
        System.out.println("id = " + id);
        System.out.println("person = " + person);
        personService.updatePerson(id, person);
    }

}
