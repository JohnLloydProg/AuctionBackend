package com.payaman.auctms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Category_data")
public class CategoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
}
