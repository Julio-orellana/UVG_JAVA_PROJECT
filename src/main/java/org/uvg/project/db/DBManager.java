package org.uvg.project.db;

import io.github.cdimascio.dotenv.Dotenv;
import org.uvg.project.Exceptions.DBException;
import org.uvg.project.utils.ILoadEnv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que se encarga de la conexión a la base de datos
 * @version 2.0
 * @since 2024-09-12
 * @author julio orellana
 */

public class DBManager implements ILoadEnv {

    public static Connection getConnection() throws DBException  {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new DBException("Error al conectar a la base de datos" + e.getMessage());
        }
        return connection;
    }

    public static Statement getStatement() throws DBException {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
        } catch (SQLException e) {
            throw new DBException("Error al crear el statement" + e.getMessage());
        }
        return statement;
    }

    public static void executeUpdate(String query) throws DBException {
        try {
            getStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new DBException("Error al ejecutar la consulta" + e.getMessage());
        }
    }


    public static void closeConnection(Connection connection) throws DBException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException("Error al cerrar la conexión" + e.getMessage());
        }
    }
}