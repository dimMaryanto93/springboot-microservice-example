package com.maryanto.dimas.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NonNull
    private boolean enabled;
    private Timestamp createdDate;
    private String createdBy;
}
