package org.example;

import org.example.dao.daoImpl.JobDaoImpl;
import org.example.model.Employee;
import org.example.model.Job;
import org.example.service.EmployeeService;
import org.example.service.serviceImpl.EmployeeServiceImpl;
import org.example.service.serviceImpl.JobServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        JobServiceImpl service = new JobServiceImpl();
//        service.createJobTable();
        //service.addJob(new Job(1L,"Mentor","Java","Backend Developer",1));
       // System.out.println(service.getJobByEmployeeId(4L));
       //  System.out.println(service.getJobById(1L));


        EmployeeService employeeService = new EmployeeServiceImpl();
        //employeeService.createEmployee();
        //employeeService.addEmployee(new Employee(1L,"Mika","Keldibekova",20,"mika@gmail"));
        //System.out.println(service.getJobById(1L));

    }
}
