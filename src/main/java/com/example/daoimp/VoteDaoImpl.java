package com.example.daoimp;

import com.example.dao.VoteDAO;
import com.example.database.DBConnection;
import com.example.model.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VoteDaoImpl implements VoteDAO {


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasJudgeVotedForTeam(int hackathonId, String judgeEmail, int teamId) {
        String sql = "SELECT COUNT(*) FROM votes WHERE hackathon_id = ? AND judge_user_email = ? AND team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ps.setString(2, judgeEmail);
            ps.setInt(3, teamId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Vote> findVoteByTeamId(int teamId) {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM votes WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                votes.add(mapRowToVote(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void saveVote(Vote vote, int documentID) {
        String sql = "INSERT INTO votes (hackathon_id, judge_user_email, team_id, score, comment, document_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, vote.getHackathonId());
            ps.setString(2, vote.getJudgeEmail());
            ps.setInt(3, vote.getTeamId());
            ps.setInt(4, vote.getScore());
            ps.setString(5, vote.getComment());
            ps.setInt(6, documentID);

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                vote.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Vote mapRowToVote(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int hackathonId = rs.getInt("hackathon_id");
        String judgeEmail = rs.getString("judge_user_email");
        int teamId = rs.getInt("team_id");
        int score = rs.getInt("score");
        String comment = rs.getString("comment");
        return new Vote(id, hackathonId, judgeEmail, teamId, score, comment);
    }
}