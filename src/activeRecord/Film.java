package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Film {
    private String titre;
    private int id;
    private int id_real;


    /**
     *
     * @param titre
     * @param personne
     */
    public Film(String titre, Personne personne ) {
        this.titre = titre;
        this.id = -1;
        this.id_real = personne.getId();
    }

    /**
     *
     * @param titre
     * @param id
     * @param id_real
     */
    private Film(String titre, int id, int id_real) {
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    /**
     *
     * @param id
     * @return
     */
    public static Film findById(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM film WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String titre = rs.getString("titre");
                int id_real = rs.getInt("id_real");
                return new Film(titre, id, id_real);
            }
        }
        catch(SQLException e){
                e.printStackTrace();
            }
        return null;
    }


    public Personne getRealisateur() {
        return Personne.findById(this.id_real);
    }

    public static void createTable() {
        try {
            String sql = "CREATE TABLE Film (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "titre VARCHAR(40) NOT NULL, " +
                    "id_real INT NOT NULL, " +
                    "FOREIGN KEY (id_real) REFERENCES Personne(id)" +
                    ")";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTable() {
        try {
            String sql = "DROP TABLE Film";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void save() throws RealisateurAbsentException {
        if (this.id_real == -1)
            throw new RealisateurAbsentException("Le réalisateur n'existe pas dans la base.");
        if (this.id == -1) {
            saveNew();
        } else {
            update();
        }
    }


    private void saveNew() throws RealisateurAbsentException {

        try {
            String sql = "INSERT INTO Film (titre, id_real) VALUES (?, ?)";
            PreparedStatement ps = DBConnection.getInstance().getConnection()
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, this.titre);
            ps.setInt(2, this.id_real);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) this.id = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }









}
