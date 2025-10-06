package com.example.daoimp;

import com.example.dao.JudgeDAO;
import com.example.database.DBConnection;
import com.example.model.Judge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JudgeDaoImpl implements JudgeDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Judge> findJudgeByEmail(String email) {
        String sql = "SELECT * FROM judges WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToJudge(rs));
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
    public boolean saveJudge(Judge j, Connection conn) throws SQLException {
        String sql = "INSERT INTO judges (user_email, hackathon_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getEmail());
            ps.setInt(2, j.getHackathonId());
            return ps.executeUpdate() > 0;
        }
    }


    /**
     * {@inheritDoc}
     */
    public boolean saveJudge(Judge j) {
        try (Connection conn = DBConnection.getConnection()) {
            return saveJudge(j, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Judge> findJudgeByHackathonId(int hackathonId) {
        List<Judge> list = new ArrayList<>();
        String sql = "SELECT * FROM judges WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hackathonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToJudge(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Judge mapRowToJudge(ResultSet rs) throws SQLException {
        return new Judge(
                rs.getString("user_email"),
                rs.getInt("hackathon_id"));
    }
}