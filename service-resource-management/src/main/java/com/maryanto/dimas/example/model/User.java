package com.maryanto.dimas.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    @Column(name = "email", unique = true, nullable = false, length = 100)
    @NotNull
    private String email;
    @Column(name = "password", nullable = false, length = 150)
    @NotNull
    @Size(min = 5, message = "field password must be more then 5 characters")
    private String password;
    @Column(name = "is_enabled", nullable = false)
    private boolean enabled;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;
    @Column(name = "last_updated_date")
    private Timestamp lastUpdatedDate;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

}
