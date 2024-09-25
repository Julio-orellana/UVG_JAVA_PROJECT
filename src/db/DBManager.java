package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    // CREDENCIALES
    private final String dbUrl = "";
    private final String dbUser = "";
    private final String dbPassword = "";

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;


    public DBManager() throws DBException {
        getConnection();
        getStatement();
    }

    public void getConnection() throws DBException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn =
                    DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
            throw new DBException("PROBLEMA EN GET CONNECTION: " + e.getMessage());
        }
    }

    public void getStatement() throws DBException {
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN GET STATEMENT: " + e.getMessage());
        }
    }

    public void getResultSet(String query) throws DBException {
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN GET RESULTSET: " + e.getMessage());
        }
    }

    public List<Object> executeQuery(String query) throws DBException {

        getResultSet(query);

        List<Object> result = new ArrayList<>();
        try {
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount() -1; i++) {
                    result.add(rs.getObject(i));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN EXECUTE QUERY: " + e.getMessage());
        }
    }


}
