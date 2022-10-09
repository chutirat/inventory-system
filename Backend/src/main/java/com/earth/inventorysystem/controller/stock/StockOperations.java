package com.earth.inventorysystem.controller.stock;

import com.earth.inventorysystem.util.MySqlConnection;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stock")

public class StockOperations {

    @PostMapping(path = "/updatestock")
    public Map<String, Object> updatestock(@CookieValue int user_id,
                                           @CookieValue String permission,
                                           @RequestParam int stock_id,
                                           @RequestParam int amount) {
        Map<String, Object> res = new HashMap<>();

        if (!permission.equals("admin")) {
            if (amount > 0) {
                res.put("success", false);
                res.put("text", "Only admin can increase stock.");
                return res;
            }
        }

        try {
            Connection connection = MySqlConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT amount FROM stocks WHERE stock_id = ?");
            preparedStatement.setInt(1, stock_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            int amountBefore = 0;
            if (resultSet.next()) {
                amountBefore = resultSet.getInt("amount");

                if (amountBefore + amount < 0) {
                    res.put("text", "Amount cannot be lower than zero.");
                    res.put("success", false);
                    return res;
                }
            } else {
                res.put("text", "No stock found.");
                res.put("success", false);
                return res;
            }

            preparedStatement = connection.prepareStatement(
                    "UPDATE stocks SET amount = amount + ? WHERE stock_id = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, stock_id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO updates (user_id, stock_id, amount) VALUES (?, ?, ?);");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, stock_id);
            preparedStatement.setInt(3, amount);
            preparedStatement.executeUpdate();

            res.put("success", true);
            res.put("amount", amount + amountBefore);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        res.put("text", "Error.");
        res.put("success", false);
        return res;
    }
}