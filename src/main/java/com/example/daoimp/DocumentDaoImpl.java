package com.example.daoimp;

import com.example.dao.DocumentDAO;
import com.example.database.DBConnection;
import com.example.model.Document;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DocumentDaoImpl implements DocumentDAO {

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Document> findDocumentByTeamId(int teamId) {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM documents WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                documents.add(mapRowToDocument(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Document> findDocumentByHackathonId(int hackathonId, String judgeEmail) {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT d.* FROM documents d " +
                "JOIN teams t ON d.team_id = t.id " +
                "WHERE t.hackathon_id = ? " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM votes v " +
                "    WHERE v.document_id = d.id " +
                "    AND v.hackathon_id = ? " +
                "    AND v.judge_user_email = ? " +
                ")";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ps.setInt(2, hackathonId);
            ps.setString(3, judgeEmail);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                documents.add(mapRowToDocument(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void saveDocument(Document document) {
        String sql = "INSERT INTO documents (filename, upload_date, team_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, document.getFileName());
            ps.setTimestamp(2, Timestamp.valueOf(document.getUploadDate()));
            ps.setInt(3, document.getTeamId());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                document.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Document mapRowToDocument(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String documentPath = rs.getString("filename");
        LocalDateTime uploadDate = rs.getTimestamp("upload_date").toLocalDateTime();
        int teamId = rs.getInt("team_id");

        return new Document(id, documentPath, uploadDate, teamId);
    }

}