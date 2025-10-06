package com.example.daoimp;

import com.example.dao.UserDAO;
import com.example.database.DBConnection;
import com.example.model.Role;
import com.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class UserDaoImpl implements UserDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findUserByEmail(String email) {

        String sql = """
                SELECT
                    u.*,
                    CASE
                        WHEN EXISTS (SELECT 1 FROM organizers o WHERE o.user_email = u.email) THEN 'organizer'
                        WHEN EXISTS (SELECT 1 FROM judges j WHERE j.user_email = u.email) THEN 'judge'
                        WHEN EXISTS (SELECT 1 FROM participants p WHERE p.user_email = u.email) THEN 'participant'
                        ELSE 'user'
                    END AS role
                FROM users u
                WHERE u.email = ?;
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = mapRowToUser(rs);
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                System.out.println(user.getRole());
                return Optional.of(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean saveUser(User user) {
        String sql = "INSERT INTO users (email, username, password, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFirst_name());
            ps.setString(5, user.getLast_name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private User mapRowToUser(ResultSet rs) throws SQLException {
        String email = rs.getString("email");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");

        Role role = Role.USER;
        try {
            String roleStr = rs.getString("role");
            if (roleStr != null) {
                role = Role.valueOf(roleStr.toUpperCase());
            }
        } catch (IllegalArgumentException e) {

        }

        return new User(email, username, password, firstName, lastName, role);
    }
}
