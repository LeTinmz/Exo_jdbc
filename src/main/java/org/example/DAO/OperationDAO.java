package org.example.DAO;

import org.example.Models.Customer;
import org.example.Models.Operation;
import org.example.utils.DataBaseManager;
import org.example.utils.OperationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String request;

    public Operation save(Operation operation) {
        try {
            connection = DataBaseManager.getConnection();
            request = "INSERT INTO operation (amount,status,accountId) values (?,?,?)";
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, operation.getAmount());
            statement.setString(2, operation.getStatus().name());
            statement.setInt(3, operation.getAccountId());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                operation.setId(resultSet.getInt(1));
            }
            return operation;

        } catch (SQLException e) {
            System.out.println("Error during saving person : " + e.getMessage());
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Operation> getByAccountId(int accountId) {
        List<Operation> operations = new ArrayList<>();
        try {
            connection = DataBaseManager.getConnection();
            request = "SELECT * FROM operation WHERE accountId = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, accountId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                operations.add(Operation.builder()
                        .id(resultSet.getInt("id"))
                        .amount(resultSet.getDouble("amount"))
                        .status(OperationStatus.valueOf(resultSet.getString("status")))
                        .accountId(resultSet.getInt("accountId"))
                        .build());
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching operations by account ID: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return operations;
    }
}


