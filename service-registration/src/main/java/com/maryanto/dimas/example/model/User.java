package com.maryanto.dimas.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String email;
    private String password;
    private Timestamp createdDate;
    private String createdBy;
    private boolean enabled;
}
