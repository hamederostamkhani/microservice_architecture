package com.javagym.microservice_one.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("sellar")
public class SellerEntity {
    @Id
    private Long id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column
    private String email;
    @Column
    private String image;
    @Column("valid_from")
    private LocalDateTime validFrom;
    @Column("valid_to")
    private LocalDateTime validTo;
}
