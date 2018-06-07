package com.maryanto.dimas.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    @NotNull
    private String email;
    @NotNull
    @Size(min = 5, message = "field password must be more then 5 characters")
    private String password;
    @NotNull
    private boolean enabled;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdatedDate;
    private String lastUpdatedBy;
}
