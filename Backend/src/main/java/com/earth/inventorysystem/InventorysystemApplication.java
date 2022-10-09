package com.earth.inventorysystem;

import com.earth.inventorysystem.util.MySqlConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventorysystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventorysystemApplication.class, args);

		try {
			MySqlConnection.reConnect();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
