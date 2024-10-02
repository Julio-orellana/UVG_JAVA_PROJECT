package org.uvg.project;

import org.uvg.project.db.DBManager;
import org.uvg.project.Exceptions.DBException;
import org.uvg.project.Security.Encryption;

import javax.swing.JOptionPane;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {

        DBManager db = new DBManager();

        Encryption enc = new Encryption();

        showData("SELECT * FROM customers", db);
//        db.executeUpdate("");

    }

    public static void showData(String query, DBManager db) {

        try {

            List<Object> result = db.getAllData(query);
            System.out.println("Query ejecutado con éxito!\n");
            for (int i = 0; i < result.size(); i++) {

                if (result.get(i) != null && i < result.size() )
                    JOptionPane.showMessageDialog(null, "Dato en el indice " + result.indexOf(result.get(i)) + " : " + result.get(i));
                else if (result.get(i) != null && i == result.size() -1)
                    continue;
                else
                    JOptionPane.showMessageDialog(null, "DATO EN EL INDICE " + result.indexOf(result.get(i)) + " ES " + "NULL");

            }
        } catch (DBException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());

        }
    }

}