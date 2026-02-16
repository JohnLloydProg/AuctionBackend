package com.payaman.auctms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;

    private String username;
    private String email;
    private String passwordHash;

    private String role;
    private String status;

    private Date createdAt;
}