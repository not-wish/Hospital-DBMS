package com.hdbms.services;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class PatientDashboard {
    private final String url;
    private final String user = "root";
    private final String password;

    public PatientDashboard() {
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.password = dotenv.get("DB_PASSWORD");

    }

}