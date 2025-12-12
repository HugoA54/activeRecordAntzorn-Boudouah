package activeRecord;
import java.sql.*;
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

    public static ArrayList<Personne> findByName(String nom){
        ArrayList<Personne> personnes = new ArrayList<Personne>();
        try {
            System.out.println("***** Affichage par Nom ******" + nom);
            String SQLPrep = "SELECT * FROM Personne WHERE nom=?;";
            PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
            prep1.setString(1, nom);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            if (rs.next()) {
                String prenom = rs.getString("prenom");
                nom = rs.getString("nom");
                int id = rs.getInt("id");
                personnes.add(new Personne(nom, prenom));
            }
            return personnes;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
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


    public static void createTable(){
        try {
            String createString = "CREATE TABLE Personne ( "
                    + "ID INTEGER  AUTO_INCREMENT, " + "NOM varchar(40) NOT NULL, "
                    + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
            Statement stmt = DBConnection.getInstance().getConnection().createStatement();
            stmt.executeUpdate(createString);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteTable(){
    try {
        String drop = "DROP TABLE Personne";
        Statement stmt = DBConnection.getInstance().getConnection().createStatement();
        stmt.executeUpdate(drop);
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    }

    public void delete(){
    try {
        PreparedStatement prep = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Personne WHERE id=?");
        prep.setInt(1, this.getId());
        prep.execute();
    }catch (SQLException e){
        e.printStackTrace();
    }
        this.id = -1;
    }

    public void save(){
        if(this.getId() == -1){
            saveNew();
        }
        else{
            update();
        }
    }


    private void saveNew(){
        try {
            Connection connect = DBConnection.getInstance().getConnection();
            String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
            PreparedStatement prep;
            prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, this.getNom());
            prep.setString(2, this.getPrenom());
            prep.executeUpdate();
            
            int autoInc = -1;
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                autoInc = rs.getInt(1);
            }
            this.setId(autoInc);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void update() {
        String sql = "UPDATE Personne SET nom = ?, prenom = ? WHERE id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, this.nom);
            stmt.setString(2, this.prenom);
            stmt.setInt(3, this.id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void setId(int id){
        this.id = id;
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

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }


    public String toString(){
        return "(" + nom + ", " + prenom + ")";
    }
}
