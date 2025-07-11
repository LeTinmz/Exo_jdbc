package org.example.DAO;

import org.example.Models.BankAccount;
import org.example.Models.Customer;
import org.example.utils.DataBaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String request;


    public Customer save (Customer customer) {
        try {
            connection = DataBaseManager.getConnection();
            request = "INSERT INTO customer (firstname,lastname,phone) values (?,?,?)";
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setString(3,customer.getPhone());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){
                customer.setId(resultSet.getInt(1));
            }
            return customer;

        }catch (SQLException e){
            System.out.println("Error during saving person : "+e.getMessage());
            return null;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean update (Customer customer){
        try {
            connection = DataBaseManager.getConnection();
            request = "UPDATE customer set firstname = ?,lastname = ?,phone= ? where id = ?";
            statement = connection.prepareStatement(request);
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setString(3,customer.getPhone());
            statement.setInt(4,customer.getId());
            int rows = statement.executeUpdate();

            return rows == 1;

        }catch (SQLException e){
            System.out.println("Error during updating person : "+e.getMessage());
            return false;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public Boolean delete (int id){
        try {
            connection = DataBaseManager.getConnection();
            request = "DELETE FROM customer where id = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1,id);
            int rows = statement.executeUpdate();

            return rows == 1;

        }catch (SQLException e){
            System.out.println("Error during updating person : "+e.getMessage());
            return false;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public Customer get (int id){
        try {
            connection = DataBaseManager.getConnection();
            request = "SELECT * FROM customer where id = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                return Customer.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("firstname"))
                        .lastName(resultSet.getString("lastname"))
                        .phone(resultSet.getString("phone"))
                        .build();
            }

            return null;

        }catch (SQLException e){
            System.out.println("Error during updating person : "+e.getMessage());
            return null;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Customer> get(){
        try {
            connection = DataBaseManager.getConnection();
            List<Customer> customers = new ArrayList<>();
            request = "SELECT * FROM customer";
            statement = connection.prepareStatement(request);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                customers.add(Customer.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("firstname"))
                        .lastName(resultSet.getString("lastname"))
                        .phone(resultSet.getString("phone"))
                        .build());
            }

            return customers;

        }catch (SQLException e){
            System.out.println("Error during updating person : "+e.getMessage());
            return new ArrayList<>();
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

}