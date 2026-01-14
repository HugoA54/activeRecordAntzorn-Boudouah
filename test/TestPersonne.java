package activeRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestPersonne {

    private List<Personne> listetest;
    private List<Personne> listeres;
    private Personne p1;

    private Personne p2;
    private Personne p3;
    private Personne p4;



    @Before
    public void setUp(){
        listetest = new ArrayList<Personne>();
        listeres = new ArrayList<Personne>();
        p1 = new Personne("Spielberg", "Steven");
        p2 = new Personne("Scott", "Ridley");
        p3 = new Personne("Kubrick", "Stanley");
        p4 = new Personne("Fincher", "David");
        Personne.createTable();
        p1.save();
        p2.save();
        p3.save();
        p4.save();
    }
    
    @After
    public void supprimerTable(){
        Personne.deleteTable();
    }

    @Test
    public void test_findAll_OK(){
        listetest.add(p1); 
        listetest.add(p2); 
        listetest.add(p3); 
        listetest.add(p4);
        listeres = Personne.findAll();
        System.out.println(listeres.size() + "///AAAAAAAAAQSLKD?QLKSlghqslqisuergflkqsdjflqskdhgAAAAAAAAAAAAAA");


        assertEquals(listeres, listetest);;
    }

    @Test
    public void test_findByName_OK(){
        listetest.add(p1);
        listeres = Personne.findByName("Spielberg");

        assertEquals(listeres, listetest);
    }

    @Test
    public void test_findById_OK(){
        listetest.add(p1);
        listeres.add(Personne.findById(1));
        assertEquals(listeres, listetest);
    }

}