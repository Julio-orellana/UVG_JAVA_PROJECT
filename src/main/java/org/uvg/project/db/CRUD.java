package org.uvg.project.db;

import org.uvg.project.Exceptions.DBException;
import org.uvg.project.GestionProductos.Producto;
import org.uvg.project.Storage.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUD extends DBManager implements ICRUD {

    private String query;

    public CRUD() throws DBException {
        super();
    }

    @Override
    public void saveObject(Object obj) throws DBException {
        try {
            Statement stmt = getStatement();
            executeUpdate(query);
        } catch (DBException  e) {
            throw new DBException("PROBLEMA EN SAVE PRODUCT: " + e.getMessage());
        }
    }

    @Override
    public List<Object> getObjects() throws DBException {
        Statement stmt = getStatement();
        List<Object> list = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    list.add(rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            throw new DBException("PROBLEMA EN GET OBJECT: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateObject(Object obj) throws DBException {
        if (obj instanceof Producto){
            try {
                executeUpdate(query);
            } catch (DBException e) {
                throw new DBException("ERROR AL ACTUALIZAR EL PRDUCTO: " + e.getMessage());
            }
        } else if (obj instanceof Location){
            try {
                executeUpdate(query);
            } catch (DBException e) {
                throw new DBException("ERROR AL ACTUALIZAR LA UBICACION: " + e.getMessage());
            }

        }
    }

    @Override
    public void deleteObject(Object obj) throws DBException {
        if (obj instanceof Producto){
            try {
                executeUpdate(query);
            } catch (DBException e) {
                throw new DBException("ERROR AL ElIMINAR EL PRDUCTO: " + e.getMessage());
            }
        }
        else if (obj instanceof Location){
            try {
                executeUpdate(query);
            } catch (DBException e) {
                throw new DBException("ERROR AL ELIMINAR LA UBICACION: " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteObjectById() throws DBException {
        try {
            executeUpdate(query);
        } catch (DBException e) {
            throw new DBException("ERROR AL ELIMINAR EL PRODUCTO POR ID: " + e.getMessage());
        }
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
