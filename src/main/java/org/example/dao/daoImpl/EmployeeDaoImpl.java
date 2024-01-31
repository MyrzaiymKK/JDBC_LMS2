package org.example.dao.daoImpl;

import org.example.dao.EmployeeDao;
import org.example.model.Employee;
import org.example.model.Job;
import org.example.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection = Util.getConnection();
    @Override
    public void createEmployee() {
     String query =  """
             create table if not exists employees(
             id serial primary key,
             firstName varchar,
             lastName varchar,
             age int,
             email varchar,
             jobId int references job(id))""";
     try {
         Statement statement = connection.createStatement();
         statement.executeUpdate(query);
         System.out.println("Success");
     }catch (SQLException e){
         System.out.println(e.getMessage());
     }
    }

    @Override
    public void addEmployee(Employee employee) {
    String query = """
            insert into employees(firstName,lastName,age,email,jobId)
            values (?,?,?,?,?)
            """;
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,employee.getFirstName());
        preparedStatement.setString(2,employee.getLastName());
        preparedStatement.setInt(3,employee.getAge());
        preparedStatement.setString(4,employee.getEmail());
        preparedStatement.setLong(5,employee.getJobId());
        preparedStatement.executeUpdate();
        preparedStatement.cancel();
    }catch (SQLException e){
        throw new RuntimeException(e);
    }
    }

    @Override
    public void dropTable() {
    String query = "drop table employees;";
    try {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
        System.out.println("Successfully dropped table");
    }catch (SQLException e){
        System.out.println(e.getMessage());
    }
    }

    @Override
    public void cleanTable() {
    String query = "delete from employees";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println("Successfully cleaned");
    }catch (SQLException e){
        System.out.println(e.getMessage());
    }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {

            String query = "update employe " +
                    "set first_name=?," +
                    "last_name =?," +
                    "email =? ," +
                    "country = ?," +
                    "date_od_birth = ? " +
                    "job id =? ";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query) ;
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setInt(5, (employee.getAge()));
                preparedStatement.setString(3, employee.getEmail());

                preparedStatement.setLong(6, employee.getId());
                preparedStatement.executeUpdate();



            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();

        String query = "select * from;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();
            while (resultSet.next()){
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastName"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("Jobid"));
                employeeList.add(employee);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee = new Employee();
        String query = "select * from employees where email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                throw new RuntimeException("Employee with this email not found!");
            }
            employee.setFirstName(resultSet.getString(""));
            employee.setLastName(resultSet.getString("lastName"));
            employee.setAge(resultSet.getInt("age"));
            employee.setEmail(resultSet.getString("email"));
            employee.setJobId(resultSet.getInt("Jobid"));

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Employee employee = new Employee();
        String query = "select * from employees where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                throw new RuntimeException("Employee with this email not found!");
            }
            employee.setFirstName(resultSet.getString(""));
            employee.setLastName(resultSet.getString("lastName"));
            employee.setAge(resultSet.getInt("age"));
            employee.setEmail(resultSet.getString("email"));
            employee.setJobId(resultSet.getInt("Jobid"));

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return (Map<Employee, Job>) employee;

    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {

        List<Employee> employeesList = new ArrayList<>();

        String query = "SELECT * FROM Employee e JOIN Job j ON e.jobId = j.id WHERE j.position = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastName"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("jobId"));

                employeesList.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeesList;
    }
}



