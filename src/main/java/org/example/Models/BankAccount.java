package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BankAccount {
    int id;
    int customerId;
    Customer customer;
    List<Operation> operations;
    Double totalAmount;

}
