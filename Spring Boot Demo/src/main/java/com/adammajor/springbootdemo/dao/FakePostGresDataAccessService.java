package com.adammajor.springbootdemo.dao;

import com.adammajor.springbootdemo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

//just have to change Autowired Qualifier in PersonService
@Repository("postgres")
public class FakePostGresDataAccessService implements PersonDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FakePostGresDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        return 0;
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sqlQuery = "SELECT * FROM person";
        return jdbcTemplate.query(sqlQuery, (resultSet, i) -> {
            final UUID id = UUID.fromString(resultSet.getString("id"));
            final String firstName =resultSet.getString("firstName");
            final String lastName =resultSet.getString("lastName");
            return new Person(
                    id,
                    firstName,
                    lastName
            );
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return Optional.empty();
    }

    @Override
    public int deletePerson(UUID id) {
        return 0;
    }

    @Override
    public int updatePerson(UUID id, Person toUpdate) {
        return 0;
    }
}
