package org.uvg.project.Storage;

import org.uvg.project.Exceptions.DBException;
import org.uvg.project.Exceptions.LocationException;
import org.uvg.project.GestionProductos.Producto;
import org.uvg.project.db.CRUD;

import java.util.ArrayList;
import java.util.List;

public class Location {

    private int id;
    private String name;
    private int storageId;
    private ArrayList<Producto> products;
    private static CRUD CRUD;

    public Location(int id, int storageId, String name) throws LocationException {
        this.id = id;
        this.name = name;
        this.storageId = storageId;
        this.products = new ArrayList<>();
        try {
           this.CRUD = new CRUD();
        } catch (DBException e) {
            throw new LocationException("Error al conectar con la base de datos");
        }
    }


    public void addProduct(Producto product) {
        this.products.add(product);
    }

    public void removeProduct(Producto product) {
        this.products.remove(product);
    }

    public ArrayList<Producto> getProducts() {
        return this.products;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getStorageId() {
        return this.storageId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public void setProducts(ArrayList<Producto> products) {
        this.products = products;
    }

    // CRUD

    // Create
    public static void saveLocation(Location location) throws LocationException {
        try {
            String query = "INSERT INTO locations (storage_id, name) VALUES (" + location.getStorageId() + ", '" + location.getName() + "')";
            CRUD.setQuery(query);
            CRUD.saveObject(location);

        } catch (DBException e) {
            throw new LocationException("PROBLEMA EN SAVE LOCATION: " + e.getMessage());
        }
    }

    // READ
    public static ArrayList<Location> getLocations() throws LocationException {
        ArrayList<Location> locations = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM locations";
            CRUD.setQuery(selectQuery);
            List<Object> lista = CRUD.getObjects();
            for (int i = 0; i < lista.size(); i += 6) {
                locations.add(new Location((int) lista.get(i), (int) lista.get(i + 1), (String) lista.get(i + 2)));
            }
            return locations;
        } catch (DBException e) {
            throw new LocationException("PROBLEMA EN GET LOCATIONS: " + e.getMessage());
        }
    }


    // UPDATE
    public static void updateLocation (Location location) throws LocationException {
        try {
            String updateQuery = "UPDATE locations SET storage_id = "+ location.getStorageId() +" name = '"+ location.getName() +"' , WHERE id = "+ location.getId();
            CRUD.updateObject(updateQuery);
        } catch (DBException e) {
            throw new LocationException("PROBLEMA EN UPDATE LOCATION: " + e.getMessage());
        }
    }

    // DELETE
    public static void deleteLocation (Location location) throws LocationException {
        try {
            String deleteQuery = "DELETE FROM locations WHERE id = "+ location.getId();
            CRUD.setQuery(deleteQuery);
            CRUD.deleteObject(location);
        } catch (DBException e) {
            throw new LocationException("PROBLEMA EN DELETE PRODUCT: " + e.getMessage());
        }
    }

    // DELETE BY ID
    public static void deleteLocation (int id) throws LocationException {
        try {
            String deleteQuery = "DELETE FROM products WHERE id = "+ id;
            CRUD.setQuery(deleteQuery);
            CRUD.deleteObjectById();
        } catch (DBException e) {
            throw new LocationException("PROBLEMA EN DELETE PRODUCT: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nUbicación: " + name);
        sb.append("\nId de ubicación: " + id);
        sb.append("\nId de almacen: " + storageId);
        sb.append("\nProductos: ");
        for (Producto product : products) {
            sb.append("     \nProducto: " + product.getNombre());
            sb.append("     \nId de producto: " + product.getId());
            sb.append("     \nCantidad: " + product.getCantidad()).append("\n");
        }
        return sb.toString();
    }

}
