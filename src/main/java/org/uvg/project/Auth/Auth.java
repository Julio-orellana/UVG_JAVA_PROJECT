package org.uvg.project.Auth;


import org.uvg.project.Exceptions.DBException;
import org.uvg.project.Exceptions.SecurityException;
import org.uvg.project.Security.Encryption;
import org.uvg.project.Exceptions.AuthException;
import org.uvg.project.db.DBManager;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Auth {

    public Auth(){}

    public static boolean signIn(String table, String email, String password) throws AuthException {

        table.toLowerCase();
        table = table.equals("customers") || table.equals("employee") ? table : null;
        String correctPassword = null;

        if (table != null){

            try {
                Statement stmt = DBManager.getStatement();
                ResultSet rs = stmt.executeQuery("SELECT password FROM " + table + " WHERE email = '" + email + "'");
                correctPassword = Encryption.decrypt(rs.getString("password"));

            } catch (SecurityException e){

                throw new AuthException("Contraseña incorrecta");

            } catch (SQLException | DBException e){

                throw new AuthException("No se encontró el usuario" + email);

            }

        }
        // Autorizar el acceso
        return correctPassword != null && correctPassword.equals(password);
    }

    public static boolean signUp(String table, String email, String name, char sex, Date birth, String password) throws AuthException {

        table = table.toLowerCase();
        table = table.equals("customers") || table.equals("employee") ? table : null;
        email = email.toLowerCase();
        name = name.toLowerCase();
        sex = Character.toUpperCase(sex);
        birth = birth.before(new Date()) ? birth : null;

        if (birth == null) throw new AuthException("La fecha de nacimiento no puede ser mayor a la fecha actual");

        if (table != null) {

            try {
                Statement stmt = DBManager.getStatement();

                stmt.executeUpdate("INSERT INTO " + table + " (email, name, sex, birth, password) VALUES ('" + email + "', '" + name + "', '" + sex + "', '"+ birth +"', '" + Encryption.encrypt(password) + "')");

            } catch (DBException | SQLException| SecurityException e) {

                throw new AuthException("Ocurrio un error al registrarse, por favor intentelo de nuevo");

            }

        } else {

            throw new AuthException("Error al registrar el usuario");

        } return true;
    }

    public static void changePassword(String table, String email, String password) throws AuthException {

        table = table.toLowerCase();
        table = table.equals("customers") || table.equals("employee") ? table : null;
        email = email.toLowerCase();

        try {

            Statement stmt = DBManager.getStatement();
            ResultSet rs = stmt.executeQuery("SELECT password FROM " + table + " WHERE email = '" + email + "'");
            String correctPassword = table != null ? Encryption.decrypt(rs.getString("password")) : null;

            if (correctPassword != null && correctPassword.equals(password) ) {
                stmt.executeUpdate("UPDATE " + table + " SET password = '" + Encryption.encrypt(password) + "' WHERE email = '" + email + "'");
            } else {
                throw new AuthException("Contraseña incorrecta");
            }

        } catch (SQLException | DBException | SecurityException e) {
            throw new AuthException("No se encontró el usuario" + email);
        }


    }

}