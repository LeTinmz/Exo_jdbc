package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.OperationStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Operation {
    int id;
    double amount;
    OperationStatus status;
    int accountId;

}
