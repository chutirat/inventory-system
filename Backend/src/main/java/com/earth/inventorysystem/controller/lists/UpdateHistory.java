package com.earth.inventorysystem.controller.lists;

import com.earth.inventorysystem.util.MySqlConnection;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UpdateHistory {
    @GetMapping(path = "/lists/history")
    public Map<String, Object> list(@CookieValue int user_id, @CookieValue String permission) {
        Map<String, Object> res = new HashMap<>();

        try {
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT updates.*, users.username, stocks.stock_name FROM updates INNER JOIN users ON updates.user_id = users.user_id INNER JOIN stocks ON updates.stock_id = stocks.stock_id " +
                            (permission.equals("admin") ? "" : " WHERE updates.user_id = " + user_id) + " ORDER BY date");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Object> arr = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> list = new HashMap<>();
                list.put("username", resultSet.getString("username"));
                list.put("stock", resultSet.getString("stock_name"));
                list.put("amount", resultSet.getInt("amount"));

                Timestamp timestamp = resultSet.getTimestamp("date");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                list.put("date", simpleDateFormat.format(new Date(timestamp.getTime())));

                arr.add(list);
            }
            res.put("success", true);
            res.put("lists", arr);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        res.put("success", false);
        return res;
    }
}
