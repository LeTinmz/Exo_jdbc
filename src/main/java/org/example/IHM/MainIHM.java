package org.example.IHM;

import org.example.Models.BankAccount;
import org.example.Models.Customer;
import org.example.Services.MainService;

import java.util.Scanner;

public class MainIHM {
    private MainService mainService = new MainService();
    private Scanner scanner = new Scanner(System.in);
    public void start(){

        while(true) {
            System.out.println("Main menu");
            System.out.println("Choose your option");
            System.out.println("1 : Create customer/accout");
            System.out.println("2 : deposit on existing account");
            System.out.println("3 : withdraw on existing account");
            System.out.println("4 : display given account");
            System.out.println("0 : exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createCustomer();
                    break;
                case "2":
                    deposit();
                    break;
                case "3":
                    withdraw();
                    break;
                case "4":
                    display();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Sois pas teuteu");
            }
        }
    }



    public void createCustomer(){
        System.out.println("enter customer first name");
        String firstName = scanner.nextLine();
        System.out.println("enter customer last name");
        String lastName = scanner.nextLine();

        System.out.println("enter customer phone");
        String phone = scanner.nextLine();

        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build();

        mainService.createCustomer(customer);
    }

    public void deposit(){
        System.out.println("enter account id");
        int accountId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("select amount to deposit");
        int amount = scanner.nextInt();
        scanner.nextLine();

        mainService.deposit(accountId, amount);
    }
    public void withdraw(){
        System.out.println("enter account id");
        int accountId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("select amount to withdraw");
        int amount = scanner.nextInt();
        scanner.nextLine();

        mainService.withdraw(accountId, amount);
    }
    public void display(){
        System.out.println("enter account id");
        int accountId = Integer.parseInt(scanner.nextLine());

        BankAccount account = mainService.getAccountDetails(accountId);

        if (account == null) {
            System.out.println("yapa");
            return;
        }

        Customer customer = account.getCustomer();

        System.out.println("Customer details:");
        System.out.println("Name: " + customer.getFirstName() +  " " + customer.getLastName());
        System.out.println("Phone number: " + customer.getPhone());
        System.out.println("Account details:");
        System.out.println("Account id: " + accountId);
        System.out.println("Moula " + account.getTotalAmount());




    }
}
