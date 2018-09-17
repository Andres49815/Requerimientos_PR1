package com.example.josepablo.myapplication.model;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountAdministrator {
    private static AccountAdministrator instance = null;
    private static UserAccount user;
    public static String userType = "";

    public static void LogIn(String username, String password) {
        try {
            PreparedStatement statement = connectionDB().prepareStatement(
                    "SELECT * FROM dbo.account WHERE ID = ? AND PassW = ?"
            );
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet queryResult = statement.executeQuery();
            queryResult.next();

            String name = queryResult.getString("userName");
            String lastName = queryResult.getString("userLastName");
            String type = queryResult.getString("typeAccount");

            user = new UserAccount(username, name, lastName, 0);
            userType = type;
        }
        catch (Exception e) {
            userType = "";
        }
    }

    public static AccountAdministrator getInstance() {
        return instance;
    }

    public static UserAccount actualUser() {
        return user;
    }

    // DB Connection
    public static Connection connectionDB(){
        Connection connection = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://musicbeansjar.database.windows.net:1433/musicBeansJAR;user=administrador@musicbeansjar;password=Inicio123;");
        }
        catch(Exception e){
            connection = null;
        }
        return connection;
    }

    // Band Methods
    public static void AddEvent(Event event) {
        try {
            PreparedStatement statement = connectionDB().prepareStatement(
                    "INSERT INTO dbo.BandEvent VALUES (?, 0, ?, ?, ?)"
            );
            statement.setString(1, event.band);
            statement.setString(2, event.place);
            statement.setString(3, event.date.toString());
            statement.setString(4, event.details);

            statement.executeQuery();
        }
        catch (SQLException e) { }
    }

    // Statement
    public static void SetStatement(PreparedStatement statement, String ... strings) {
        try {
            for (int i = 0; i < strings.length; i++)
                statement.setString(i + 1, strings[i]);
        }
        catch (SQLException e) {}
    }

}