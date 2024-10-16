package org.uvg.project.Storage;

import org.uvg.project.Exceptions.StorageException;
import org.uvg.project.GestionProductos.Producto;
import org.uvg.project.db.DBManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {

    private int id;
    private String name;
    private HashMap<Integer, Location> locations;

    public Storage(int id, String name) {
        this.id = id;
        this.name = name;
        this.locations = new HashMap<>();
    }

    public void addLocation(Location location) throws StorageException {
        if (this.locations.containsKey(location.getId())) {
            throw new StorageException("La ubicación ya existe");
        }
        this.locations.put(location.getId(), location);
    }

    public void removeLocation(int locationId) {
        this.locations.remove(locationId);
    }

    public Location getLocation(int locationId) {
        return this.locations.get(locationId);
    }

    public ArrayList<Location> getLocations() {
        return new ArrayList<>(this.locations.values());

    }

    public static Storage loadStorage(int id) throws StorageException {
        try {
            Statement stmt = DBManager.getStatement();
            String query = "SELECT * FROM locations WHERE storage_id = "+ id ;
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<Location> locations = new ArrayList<>();
            ArrayList<Producto> products = new ArrayList<>();
            Storage storage = null;

            // Cargar las ubicaciones del almacen
            while (rs.next()) {
                int loc_id = rs.getInt("id");
                int storageId = rs.getInt("storage_id");
                String name = rs.getString("name");
                locations.add(new Location(loc_id, storageId, name));
            }

            // Cargar los productos de cada ubicación
            for (Location location : locations) {
                query = "SELECT * FROM products WHERE location_id = " + location.getId();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int prod_id = rs.getInt("id");
                    String prod_name = rs.getString("name");
                    float quantity = rs.getFloat("quantity");
                    String dimension = rs.getString("dimension");
                    int storage_id = rs.getInt("storage_id");
                    int location_id = rs.getInt("location_id");
                    products.add(new Producto(prod_id, prod_name, quantity, dimension, storage_id, location_id));
                }
                location.setProducts(products);
            }

            // Cargar el almacen
            query = "SELECT * FROM storage WHERE id = " + id;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int storage_id = rs.getInt("id");
                String storage_name = rs.getString("name");
                storage = new Storage(storage_id, storage_name);
                for (Location location : locations) {
                    storage.addLocation(location);
                }
            }

            return storage;

        } catch (Exception e) {
            throw new StorageException("Error al cargar el almacen: " + e.getMessage());
        }
    }

    public void saveStorage() throws StorageException {
        try {
            Connection conn = DBManager.getConnection();
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO storage (id, name) VALUES (" + this.id + ", '" + this.name + "')";
            stmt.executeUpdate(query);
            for (Location location : this.locations.values()) {
                Location.saveLocation(location);
            }
        } catch (Exception e) {
            throw new StorageException("Error al guardar el almacen: " + e.getMessage());
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocations(HashMap<Integer, Location> locations) {
        this.locations = locations;
    }

}