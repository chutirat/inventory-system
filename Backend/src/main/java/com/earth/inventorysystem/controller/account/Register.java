package com.earth.inventorysystem.controller.account;


import com.earth.inventorysystem.util.MySqlConnection;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Register {
    @PostMapping(path = "/account/register")
    public Map<String, Object> register(@RequestParam String username, @RequestParam String email, @RequestParam String password) {

        Map<String, Object> res = new HashMap<>();
        try {
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into users (username, email, password) value (?,?,?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            res.put("success", false);
        }
        return res;
    }
}

