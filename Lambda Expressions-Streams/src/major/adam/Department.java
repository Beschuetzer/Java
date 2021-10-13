package major.adam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Department {
    private String name;
    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
        employees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public boolean addEmployee(Employee employee) {
        if (employees.contains(employee)) return false;

        employees.add(employee);
        return true;
    }

    public boolean addEmployees(Employee[] employees) {
        if (employees == null) return false;

        for (Employee employee : employees) {
            addEmployee(employee);
        }
        return true;
    }
}
