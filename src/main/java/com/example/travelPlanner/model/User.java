package com.example.travelPlanner.model;

import lombok.Data;

import java.util.List;

@Data
public class User {

    String userName;

    String password;

    List<String> roles;

}
