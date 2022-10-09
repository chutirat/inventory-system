package com.earth.inventorysystem.controller.stock;

import com.earth.inventorysystem.util.MySqlConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ListInStock {
    @GetMapping(path = "/stock/inlist")
    public Map<String, Object> list() {
        Map<String, Object> res = new HashMap<>();

        try {
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stocks WHERE amount > 0");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Object> arr = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> stock = new HashMap<>();
                stock.put("id", resultSet.getString("stock_id"));
                stock.put("name", resultSet.getString("stock_name"));
                stock.put("amount", resultSet.getString("amount"));
                stock.put("price", resultSet.getString("price"));
                stock.put("picture", resultSet.getString("picture_url"));
                stock.put("type", resultSet.getString("type"));
                arr.add(stock);
            }
            res.put("success", true);
            res.put("stocks", arr);
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        res.put("success", false);
        return res;
    }

}
