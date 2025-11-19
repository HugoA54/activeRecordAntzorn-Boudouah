import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;

    private Connection connection;

    private String userName = "root";
    private String password = "";      // MAMP : root
    private String serverName = "localhost";
    private String portNumber = "";    // MAMP : 8889
    private String dbName = "testpersonne";

    private DBConnection() {
        try {
            String urlDB = "jdbc:mysql://" + serverName;
            if (!portNumber.isEmpty()) {
                urlDB += ":" + portNumber;
            }
            urlDB += "/" + dbName + "?useSSL=false&serverTimezone=UTC";

            connection = DriverManager.getConnection(urlDB, userName, password);

        } catch (SQLException e) {
            throw new RuntimeException("Connexion à la base impossible : " + e.getMessage(), e);
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {


            String urlDB = "jdbc:mysql://" + serverName;
            if (!portNumber.isEmpty()) {
                urlDB += ":" + portNumber;
            }
            urlDB += "/" + dbName + "?useSSL=false&serverTimezone=UTC";

            connection = DriverManager.getConnection(urlDB, userName, password);
            } catch (SQLException e) {
                throw new RuntimeException("Connexion à la base impossible : " + e.getMessage(), e);
            }
        }
        return connection;
    }

    public void setNomDB(String dbName) {
        this.dbName = dbName;
        this.connection = null;
    }
}
