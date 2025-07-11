package org.example.Services;

import org.example.DAO.BankAccountDAO;
import org.example.DAO.CustomerDAO;
import org.example.DAO.OperationDAO;
import org.example.Models.BankAccount;
import org.example.Models.Customer;
import org.example.Models.Operation;
import org.example.utils.OperationStatus;

import java.util.List;

public class MainService {

    private CustomerDAO customerDAO = new CustomerDAO();
    private BankAccountDAO bankAccountDAO = new BankAccountDAO();
    private OperationDAO operationDAO = new OperationDAO();



    public Customer createCustomer(Customer customer){
        customerDAO.save(customer);

        BankAccount bankAccount = BankAccount.builder().customerId(customer.getId()).totalAmount(0.0).build();
        bankAccountDAO.save(bankAccount);
        return customer;
    };

    public Operation deposit(int accountId, double amount){

        BankAccount account = bankAccountDAO.get(accountId);
        Operation operation = Operation.builder()
                .amount(amount)
                .accountId(accountId)
                .status(OperationStatus.DEPOSIT)
                .build();


        operationDAO.save(operation);
        bankAccountDAO.updateTotalAmount(accountId, amount);

        return operation;
    }

    public Operation withdraw(int accountId, double amount){
        BankAccount account = bankAccountDAO.get(accountId);
        Operation operation = Operation.builder()
                .amount(amount)
                .accountId(accountId)
                .status(OperationStatus.WITHDRAWAL)
                .build();
        operationDAO.save(operation);
        bankAccountDAO.updateTotalAmount(accountId,-amount);

        return operation;
    }
    public BankAccount getAccountDetails(int accountId) {
        BankAccount account = bankAccountDAO.get(accountId);

        if (account == null) {
            System.out.println("Account not found with ID: " + accountId);
        }

        return account;
    }

}
