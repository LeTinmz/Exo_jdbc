package org.example.DAO;

import org.example.Models.BankAccount;
import org.example.Models.Customer;
import org.example.Models.Operation;
import org.example.utils.DataBaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDAO {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String request;

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final OperationDAO operationDAO = new OperationDAO();

    public BankAccount save (BankAccount bankaccount) {
        try {
            connection = DataBaseManager.getConnection();
            request = "INSERT INTO bankaccount (customerId,totalAmount) values (?,?)";
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,bankaccount.getCustomerId());
            statement.setDouble(2,bankaccount.getTotalAmount());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){
                bankaccount.setId(resultSet.getInt(1));
            }
            return bankaccount;

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

    public boolean updateTotalAmount(int accountId, double amount) {
        try {
            connection = DataBaseManager.getConnection();
            request = "UPDATE bankaccount SET totalAmount = totalAmount + ? WHERE id = ?";
            statement = connection.prepareStatement(request);
            statement.setDouble(1, amount);       // +delta (dépôt), -delta (retrait)
            statement.setInt(2, accountId);
            int rows = statement.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            System.out.println("Error during updating account balance: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public BankAccount get (int id){
        try {
            connection = DataBaseManager.getConnection();
            request = "SELECT * FROM bankaccount where id = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int customerId = resultSet.getInt("customerId");
                Customer customer = customerDAO.get(customerId);
                List<Operation> operations = operationDAO.getByAccountId(id);

                return BankAccount.builder()
                        .id(resultSet.getInt("id"))
                        .customerId(customerId)
                        .customer(customer)
                        .operations(operations)
                        .totalAmount(resultSet.getDouble("totalAmount"))
                        .build();
            }

            return null;

        }catch (SQLException e){
            System.out.println("Error during updating bank account : "+e.getMessage());
            return null;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }



}