package org.example.dao.daoImpl;

import org.example.dao.JobDao;
import org.example.model.Employee;
import org.example.model.Job;
import org.example.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    private  final Connection connection = Util.getConnection();
    @Override
    public void createJobTable() {
         String query = """
                 create table if not exists job(
                 id serial primary key,
                 position varchar,
                 profession varchar,
                 description varchar,
                 experience int,
                 employyId int references employees(id))
                 """;
         try {
             Statement statement = connection.createStatement();
             statement.executeUpdate(query);
             System.out.println("Success");
         } catch (SQLException e){
             System.out.println(e.getMessage());
         }

    }

    @Override
    public void addJob(Job job) {
    String query = """
            insert into job (position,profession,description,experience)
            values (?,?,?,?);
            """;
    int execute = 0;
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,job.getPosition());
        preparedStatement.setString(2,job.getProfession());
        preparedStatement.setString(3,job.getDescription());
        preparedStatement.setInt(4,job.getExperience());
         execute  =  preparedStatement.executeUpdate();

        preparedStatement.close();
    }catch (SQLException e){
        throw new RuntimeException(e);
    }
    }

    @Override
    public Job getJobById(Long jobId) {
        Job job = new Job();
        String query = "select * from job where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1,jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                throw new RuntimeException("Job with this id not found!");
            }
            job.setPosition(resultSet.getString("position"));
            job.setProfession(resultSet.getString("profession"));
            job.setDescription(resultSet.getString("description"));
            job.setExperience(resultSet.getInt("experience"));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return job;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        if (ascOrDesc.equalsIgnoreCase("asc")) {
            try {
                String queryAsc = "select * from jobs order by experience";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryAsc)) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Job job = new Job();
                        job.setId(resultSet.getLong("job_id"));
                        job.setPosition(resultSet.getString("position_job"));
                        job.setProfession(resultSet.getString("profession_job"));
                        job.setDescription(resultSet.getString("description_job"));
                        job.setExperience(resultSet.getInt("experience"));
                        job.setEmployeeId(resultSet.getInt("employee_id"));
                        jobs.add(job);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            try {
                String queryDesc = "select * from jobs order by experience desc";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryDesc)) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Job job = new Job();
                        job.setId(resultSet.getLong("job_id"));
                        job.setPosition(resultSet.getString("position_job"));
                        job.setProfession(resultSet.getString("profession_job"));
                        job.setDescription(resultSet.getString("description_job"));
                        job.setExperience(resultSet.getInt("experience"));
                        job.setEmployeeId(resultSet.getInt("employee_id"));
                        jobs.add(job);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else System.out.println("incorrect choice");
        return jobs;
    }



    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        try {
            String query = "select * from job where employyId = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

                preparedStatement.setLong(1,employeeId );
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    Job job = new Job();
                    job.setId(resultSet.getLong("job_id"));
                    job.setPosition(resultSet.getString("position_job"));
                    job.setProfession(resultSet.getString("profession_job"));
                    job.setDescription(resultSet.getString("description_job"));
                    job.setExperience(resultSet.getInt("experience"));
                    job.setEmployeeId(resultSet.getInt("employee_id"));
                    return job;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public void deleteDescriptionColumn() {
        try {
            String query = "alter table jobs drop column description_job";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                int i = preparedStatement.executeUpdate();
                if (i > 0) System.out.println("success dropped column");
                else System.out.println("error");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
