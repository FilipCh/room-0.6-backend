package org.example.db;

import org.apache.commons.lang3.time.DateUtils;
import org.example.cli.Employee;
import org.example.cli.Login;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class AuthDao {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public boolean validlogin(Login login) {
        try (Connection c = databaseConnector.getConnection()) {

            String selectStatement = "SELECT `Password` FROM `User` where `Username` = ?";


            PreparedStatement st = c.prepareStatement(selectStatement);

            st.setString(1, login.getUsername());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                return rs.getString("password").equals(login.getPassword());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;

    }

    public String generateToken(String username) throws SQLException {

        String token = UUID.randomUUID().toString();
        Date expiry = DateUtils.addHours(new Date(),8);

        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO Token (Username, Token, Expiry) VALUES (?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement);
        st.setString(1,username);
        st.setString(2,token);
        st.setTimestamp(3,new java.sql.Timestamp(expiry.getTime()));

        st.executeUpdate();

        return token;
    }
}