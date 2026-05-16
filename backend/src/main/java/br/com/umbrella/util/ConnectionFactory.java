package br.com.umbrella.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() {
        try {
            // O nome do banco que está no teu pgAdmin é umbrella_missions
            String url = "jdbc:postgresql://localhost:5432/umbrella_missions";
            String user = "postgres";
            
            // ATENÇÃO: Coloca a tua senha do PostgreSQL entre as aspas abaixo
            String password = "123456"; 

            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao ligar à Umbrella Corp: ", e);
        }
    }
}
