package org.uvg.project.Auth;


import org.uvg.project.Exceptions.DBException;
import org.uvg.project.Exceptions.SecurityException;
import org.uvg.project.Security.Encryption;
import org.uvg.project.Exceptions.AuthException;
import org.uvg.project.db.DBManager;


import java.util.Date;



public class Auth {

    private final DBManager db;
    private final Encryption enc = new Encryption();

    public Auth() throws AuthException {

        try {

            this.db = new DBManager();

        } catch (DBException e) {

            throw new AuthException("Error al conectar con la base de datos");

        }
    }

    public boolean signIn(String table, String email, String password) throws AuthException {

        table.toLowerCase();
        table = table.equals("customers") || table.equals("employee") ? table : null;
        String correctPassword = null;

        if (table != null){

            try {

                correctPassword = enc.decrypt(db.getData("SELECT password FROM " + table + " WHERE email = '" + email + "'"));

            } catch (SecurityException e){

                throw new AuthException("Contraseña incorrecta");

            } catch (DBException e){

                throw new AuthException("No se encontró el usuario" + email);

            }

        }
        // Autorizar el acceso
        return correctPassword != null && correctPassword.equals(password);
    }

    public boolean signUp(String table, String email, String name, char sex, Date birth, String password) throws AuthException {

        table = table.toLowerCase();
        table = table.equals("customers") || table.equals("employee") ? table : null;
        email = email.toLowerCase();
        name = name.toLowerCase();
        sex = Character.toUpperCase(sex);
        birth = birth.before(new Date()) ? birth : null;

        if (birth == null) throw new AuthException("La fecha de nacimiento no puede ser mayor a la fecha actual");

        if (table != null) {

            try {

                db.executeUpdate("INSERT INTO " + table + " (email, name, sex, birth, password) VALUES ('" + email + "', '" + name + "', '" + sex + "', '"+ birth +"', '" + enc.encrypt(password) + "')");

            } catch (DBException | SecurityException e) {

                throw new AuthException("Ocurrio un error al registrarse, por favor intentelo de nuevo");

            }

        } else {

            throw new AuthException("Error al registrar el usuario");

        } return true;
    }

    public void changePassword(String table, String email, String password) throws Exception {

        table = table.toLowerCase();
        table = table.equals("customers") || table.equals("employee") ? table : null;
        email = email.toLowerCase();

        String correctPassword = table != null ? enc.decrypt(db.getData("SELECT password FROM " + table + " WHERE email = '" + email + "'")) : null;

        if (correctPassword != null && correctPassword.equals(password) ) {
            db.executeUpdate("UPDATE " + table + " SET password = '" + enc.encrypt(password) + "' WHERE email = '" + email + "'");
        } else {
            throw new Exception("Contraseña incorrecta");
        }
    }

}