package com.example.daoimp;

import com.example.dao.RequestDAO;
import com.example.database.DBConnection;
import com.example.model.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RequestDaoImpl implements RequestDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveRequest(Request request) {
        String sql = "INSERT INTO requests (message, status, team_id, sender_participant_email, receiver_participant_email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, request.getMessage());
            ps.setString(2, request.getStatus());
            ps.setInt(3, request.getTeamId());
            ps.setString(4, request.getSender_participant_email());
            ps.setString(5, request.getReceiver_participant_email());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Request> findRequestsReceivedBy(String receiverEmail) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM requests WHERE receiver_participant_email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, receiverEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRowToRequest(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateRequest(Request request) {
        String sql = "UPDATE requests SET message = ?, status = ?, team_id = ?, sender_participant_email = ?, receiver_participant_email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, request.getMessage());
            ps.setString(2, request.getStatus());
            ps.setInt(3, request.getTeamId());
            ps.setString(4, request.getSender_participant_email());
            ps.setString(5, request.getReceiver_participant_email());
            ps.setInt(6, request.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Request> findRequestsSentBy(String senderEmail) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM requests WHERE sender_participant_email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, senderEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRowToRequest(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    private Request mapRowToRequest(ResultSet rs) throws SQLException {
        return new Request(
                rs.getInt("id"),
                rs.getString("message"),
                rs.getString("status"),
                rs.getInt("team_id"),
                rs.getString("sender_participant_email"),
                rs.getString("receiver_participant_email"));
    }


}
