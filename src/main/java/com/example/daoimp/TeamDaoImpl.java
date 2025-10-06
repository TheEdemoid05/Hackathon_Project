package com.example.daoimp;

import com.example.dao.TeamDAO;
import com.example.database.DBConnection;
import com.example.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TeamDaoImpl implements TeamDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Team> findTeamById(int id) {
        String sql = "SELECT * FROM teams WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Team team = mapResultSetToTeam(rs);
                return Optional.of(team);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca del team per ID: " + id);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Team> findTeamByHackathonId(int hackathonId) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Team team = mapResultSetToTeam(rs);
                teams.add(team);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dei team per hackathon ID: " + hackathonId);
            e.printStackTrace();
        }
        return teams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveTeam(Team team, Connection conn) throws SQLException {
        String sql = "INSERT INTO teams (team_name, hackathon_id, max_members, leader_email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, team.getName());
            ps.setInt(2, team.getHackathonId());
            ps.setInt(3, team.getMax_participants());
            ps.setString(4, team.getLeader_email());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    team.setId(keys.getInt(1));
                    return true;
                } else {
                    return false;
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean teamNameExistsInHackathon(String teamName, int hackathonId) {
        String sql = "SELECT COUNT(*) FROM teams WHERE team_name = ? AND hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, teamName);
            ps.setInt(2, hackathonId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il controllo esistenza nome team: " + teamName);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getTeamMemberCount(int teamId) {
        String sql = "SELECT COUNT(*) FROM participants WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il conteggio membri del team: " + teamId);
            e.printStackTrace();
        }
        return 0;
    }


    private Team mapResultSetToTeam(ResultSet rs) throws SQLException {
        int teamId = rs.getInt("id");
        String name = rs.getString("team_name");
        int hackathonId = rs.getInt("hackathon_id");
        int maxMembers = rs.getInt("max_members");
        String leaderEmail = rs.getString("leader_email");

        return new Team(teamId, name, maxMembers, leaderEmail, hackathonId);
    }
}