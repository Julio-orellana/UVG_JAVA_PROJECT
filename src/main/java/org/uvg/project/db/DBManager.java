package org.uvg.project.db;


import org.uvg.project.Exceptions.DBException;
import org.uvg.project.utils.ILoadEnv;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBManager implements ILoadEnv {

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
            throw new DBException("PROBLEMA EN GET CONNECTION." +
                    "\nSQLException" + ex.getMessage() +
                    "\nSQLState: " + ex.getSQLState() +
                    "\nVendorError: " + ex.getErrorCode());
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

    public String getData(String query) throws DBException {
        getResultSet(query);
        String result = "";
        try {
            while (rs.next()) {
                result = rs.getString(1);
            }
            return result;
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN GET DATA: " + e.getMessage());
        }
    }

    public Date getDate(String query) throws DBException {
        getResultSet(query);
        Date result = null;
        try {
            while (rs.next()) {
                result = rs.getDate(1);
            }
            return result;
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN GET DATE: " + e.getMessage());
        }
    }

    public List<Object> getAllData(String query) throws DBException {

        getResultSet(query);

        List<Object> result = new ArrayList<>();
        try {
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount() ; i++) {
                    result.add(rs.getObject(i));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN EXECUTE QUERY: " + e.getMessage());
        }
    }

    public void executeUpdate(String query) throws DBException {
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN EXECUTE UPDATE: " + e.getMessage());
        }
    }

    public void closeConnection() throws DBException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN CLOSE: " + e.getMessage());
        }
    }

}
