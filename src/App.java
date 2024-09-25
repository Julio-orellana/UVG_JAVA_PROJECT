import db.DBException;
import db.DBManager;

import java.util.List;

public class App {
    // Main class added to the project
    public static void main(String[] args) throws Exception {

        String query = "SELECT * FROM customers WHERE email = 'email@test.com'";

        DBManager db = new DBManager();

        System.out.println("Probando la conexión a la base de datos...");

        try {
            List<Object> result = db.executeQuery(query);
            System.out.println("Query ejecutado con éxito!\n");
            for (Object obj : result) {
                System.out.println("Dato: " +obj);
            }
        } catch (DBException e) {
            System.out.println(e.getMessage());
        }



    }
}
