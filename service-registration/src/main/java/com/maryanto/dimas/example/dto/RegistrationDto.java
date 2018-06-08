package com.maryanto.dimas.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private Integer id;
    private UserDto userId;
    @NotNull
    private String projectName;
}
