package org.example.Models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;


}
