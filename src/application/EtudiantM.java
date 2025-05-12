package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.projet.connexion.Connexion;

public class EtudiantM {
    private Connection connection = Connexion.getCn();
    
    // Méthode pour ajouter 
    public boolean create(Etudiant o) {
        try {
            String req = "INSERT INTO etudiant (nom, prenom, sexe, filiere) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, o.getSexe());
            ps.setString(4, o.getFiliere());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'étudiant");
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour supprimer
    public boolean delete(Etudiant o) {
        try {
            String req = "DELETE FROM etudiant WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, o.getId());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'étudiant");
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour modifier
    public boolean update(Etudiant o) {
        try {
            String req = "UPDATE etudiant SET nom = ?, prenom = ?, sexe = ?, filiere = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, o.getSexe());
            ps.setString(4, o.getFiliere());
            ps.setInt(5, o.getId());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'étudiant");
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour trouver 
    public Etudiant findById(int id) {
        try {
            String req = "SELECT * FROM etudiant WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("sexe"),
                    rs.getString("filiere")
                );
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de l'étudiant");
            e.printStackTrace();
            return null;
        }
    }
    
    // Méthode pour consulter
    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        try {
            String req = "SELECT * FROM etudiant";
            PreparedStatement ps = connection.prepareStatement(req);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                etudiants.add(new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("sexe"),
                    rs.getString("filiere")
                ));
            }
            return etudiants;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des étudiants");
            e.printStackTrace();
            return etudiants;
        }
    }
}