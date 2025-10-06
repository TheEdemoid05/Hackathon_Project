package com.example.daoimp;

import com.example.dao.ParticipantDAO;
import com.example.database.DBConnection;
import com.example.model.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class ParticipantDaoImpl implements ParticipantDAO {


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Participant> findParteicipantByEmail(String email) {
        String sql = "SELECT * FROM participants WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToParticipant(rs));
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
    public List<Participant> findParticipantByHackathonId(int hackathonId) {
        List<Participant> list = new ArrayList<>();
        String sql = "SELECT * FROM participants WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToParticipant(rs));
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
    public List<Participant> findParticipantByTeamId(int teamId) {
        List<Participant> list = new ArrayList<>();
        String sql = "SELECT * FROM participants WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToParticipant(rs));
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
    public boolean saveParticipant(Participant p) {
        String sql = "INSERT INTO participants (user_email, hackathon_id, team_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getEmail());
            ps.setInt(2, p.getHackathonID());
            if (Objects.nonNull(p.getTeamID())) {
                ps.setInt(3, p.getTeamID());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

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
    public boolean deleteParticipantByEmail(String email, Connection conn) throws SQLException {
        String sql = "DELETE FROM participants WHERE user_email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assignParticipantToTeam(String email, int teamId, Connection conn) throws SQLException {
        String sql = "UPDATE participants SET team_id = ? WHERE user_email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assignParticipantToTeam(String email, int teamId) {
        try (Connection conn = DBConnection.getConnection()) {
            return assignParticipantToTeam(email, teamId, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Participant mapRowToParticipant(ResultSet rs) throws SQLException {
        return new Participant(
                rs.getString("user_email"),
                rs.getInt("hackathon_id"),
                rs.getObject("team_id") != null ? rs.getInt("team_id") : null);
    }


}
