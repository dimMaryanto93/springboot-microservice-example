package com.maryanto.dimas.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {

    private Integer id;
    private User user;
    private Date createdDate;
}
