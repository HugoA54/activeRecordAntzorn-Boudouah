package activeRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {

    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }


    public static ArrayList<Personne> findAll(){
        ArrayList<Personne> personnes = new ArrayList<Personne>();
        try{
        System.out.println("***** AFFICHE TOUTES PERSONNES ***** ");
            String SQLPrep = "SELECT * FROM Personne;";
            PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int id = rs.getInt("id");
                personnes.add(new Personne(nom, prenom));
            }
            }catch (SQLException e){
            e.printStackTrace();
        }
        return personnes;
    }


    public static Personne findById(int id){
        try {
            System.out.println("***** Affichage par ID ******" + id);
            String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
            PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
            prep1.setInt(1, id);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                 id = rs.getInt("id");
                 return new Personne(nom, prenom);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
                return null;
    }


    public String getPrenom() {
        return prenom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }


    public String toString(){
        return "(" + nom + ", " + prenom + ")";
    }
}
