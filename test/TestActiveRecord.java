import activeRecord.DBConnection;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestActiveRecord {

    @Test
    public void test_DBConnection_getInstance_OK() {
        DBConnection db = DBConnection.getInstance();
        assertSame("Le Singleton pas mis en place", db, DBConnection.getInstance());
    }

    @Test
    public void test_setNomDB_OK(){
        DBConnection db = DBConnection.getInstance();
        String nom = db.getDBName();
        String nouvNom = "test";

        db.setNomDB(nouvNom);

        assertEquals("Pas le bon nom", nouvNom, db.getNomDB());
    }
}
