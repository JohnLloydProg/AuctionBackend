package com.payaman.auctms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="User_data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

}
