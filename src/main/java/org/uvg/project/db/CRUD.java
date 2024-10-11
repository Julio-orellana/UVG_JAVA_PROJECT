package org.uvg.project.db;

import org.uvg.project.Exceptions.DBException;
import org.uvg.project.GestionProductos.Producto;

import java.sql.SQLException;
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
            executeUpdate(query);
        } catch (DBException e) {
            throw new DBException("PROBLEMA EN SAVE PRODUCT: " + e.getMessage());
        }
    }

    @Override
    public List<Object> getObjects() throws DBException {
        getResultSet(query);
        List<Object> list = new ArrayList<>();
        try {
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
