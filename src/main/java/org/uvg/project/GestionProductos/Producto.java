package org.uvg.project.GestionProductos;

import org.uvg.project.Exceptions.DBException;
import org.uvg.project.Exceptions.ProductException;
import org.uvg.project.db.CRUD;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al programador
 */

public class Producto
{
    protected int id;
    protected String nombre;
    protected float cantidad;
    protected String dimension;
    protected int storage_id;
    protected int id_ubicacion;
    protected String id_categoria;
    protected static CRUD CRUD;

    /**
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     * @param cantidad Cantidad de existencias del producto.
     * @param dimension Unidades en las que se mide el producto.
    */

    public Producto(int id, String nombre, String tipo, float cantidad, String dimension, Location location, Categoria categoria) throws ProductException
    {
        try {
            if (id <= 0 || nombre != null || tipo != null || dimension != null || loation != null || categoria != null) {
                this.id = id;
                this.nombre = nombre;
                this.cantidad = cantidad;
                this.dimension = dimension;
                this.id_ubicacion = location.getId();
                this.id_categoria = categoria.getID();
                this.CRUD = new CRUD();
            }
        } catch (DBException | NullPointerException e) {
            throw new ProductException("No se pueden ingresar valores nulos");
        }
    }

    // Nuevo constructor para la clase Producto
    public Producto(int id, String nombre, float cantidad, String dimension, int storage_id, int id_ubicacion) throws ProductException
    {
        try {
            if (id <= 0 || nombre != null || dimension != null || id_ubicacion <= 0 || storage_id <= 0)
            {
                this.id = id;
                this.nombre = nombre;
                this.cantidad = cantidad;
                this.dimension = dimension;
                this.id_ubicacion = id_ubicacion;
                this.storage_id = storage_id;
                this.CRUD = new CRUD();
            }
        } catch (DBException | NullPointerException e) {
            throw new ProductException("No se pueden ingresar valores nulos");
        }
    }

    /**
     * @param nombre Nombre del producto.
    */

    public void SetNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @param cantidad Cantidad de existencias del producto.
    */

    public void SetCantidad(float cantidad)
    {
        this.cantidad = cantidad;
    }

    /**
     * @param dimension Unidades en las que se mide el producto.
    */

    public void SetDimension(String dimension)
    {
        this.dimension = dimension;
    }

    /**
     * @param dimension Unidades en las que se mide el producto.
    */

    public void SetUbicacion(int id_ubicacion)
    {
        this.id_ubicacion = id_ubicacion;
    }

    /**
     * @param dimension Unidades en las que se mide el producto.
    */

    public void SetCategoria(String id_categoria)
    {
        this.id_categoria = id_categoria;
    }

    /**
     * @return ID del producto.
    */

    public int getId()
    {
        return this.id;
    }

    /**
     * @return Nombre del producto.
    */

    public String getNombre()
    {
        return this.nombre;
    }

    /**
     * @return Cantidad del producto.
    */

    public float getCantidad()
    {
        return this.cantidad;
    }

    /**
     * @return Dimensión con la que se cuantifica el producto.
    */

    public String getDimension()
    {
        return this.dimension;
    }

    /**
     * @return Devuelve True si el producto se puede rebajar.
    */

    public boolean CorroborarSalida(float cantidad_descontada)
    {
        return cantidad_descontada > 0 & this.cantidad <= cantidad_descontada;
    }



    public void RebajarSalida(float cantidad_descontada) throws ProductException {
        if (this.dimension.equals("Unidad"))
        {
            cantidad_descontada = Math.round(cantidad_descontada);
        }
        else
        {
            throw new ProductException("No se pueden rebajar productos que no sean de tipo unidad");
        }

        if (cantidad_descontada < 0 || CorroborarSalida(cantidad_descontada) == false)
        {
            cantidad_descontada = 0f;
        }

        this.SetCantidad((float) this.getCantidad() - cantidad_descontada);
    }



    public void AumentarExistencias(float cantidad_aumentar) throws ProductException {
        if (this.dimension.equals("Unidad"))
        {
            cantidad_aumentar = Math.round(cantidad_aumentar);
        }
        else
        {
            throw new ProductException("No se pueden aumentar productos que no sean de tipo unidad");
        }

        if (cantidad_aumentar < 0)
        {
            cantidad_aumentar = 0f;
        }

        this.SetCantidad((float) this.getCantidad() + cantidad_aumentar);
    }


    // CRUD

    // CREATE
    public static void saveProduct(Producto producto) throws ProductException {
        try {
            String insertQuery = "INSERT INTO products ('name', 'quantity', 'dimension', 'storage_id', 'location_id') VALUES ('" + this.nombre + "', " + this.cantidad + ", '" + this.dimension + "', " + this.storage_id + ", " + this.id_ubicacion + ")";
            CRUD.setQuery(insertQuery);
            CRUD.saveObject(producto);
        } catch (DBException e) {
            throw new ProductException("PROBLEMA EN SAVE PRODUCT: " + e.getMessage());
        }
    }

    // READ
    public static ArrayList<Producto> getProducts() throws ProductException {
        ArrayList<Producto> products = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM products";
            CRUD.setQuery(selectQuery);
            List<Object> lista = CRUD.getObjects();
            for (int i = 0; i < lista.size(); i += 6) {
                products.add(new Producto((int) lista.get(i), (String) lista.get(i + 1), (float) lista.get(i + 2), (String) lista.get(i + 3), (int) lista.get(i + 4), (int) lista.get(i + 5)));
            }
            return products;
        } catch (DBException e) {
            throw new ProductException("PROBLEMA EN GET PRODUCTS: " + e.getMessage());
        }
    }

    // UPDATE
    public static void updateProduct (Producto producto) throws ProductException {
        try {
            String updateQuery = "UPDATE products SET name = '"+ producto.getNombre() +"', quantity = "+ producto.getCantidad() +", dimension = '"+ producto.getDimension() +"', storage_id = "+ producto.getStorageId() +", location_id = "+ producto.getIdUbicacion() +" WHERE id = "+ producto.getId();
            CRUD.updateObject(updateQuery);
        } catch (DBException e) {
            throw new ProductException("PROBLEMA EN UPDATE PRODUCT: " + e.getMessage());
        }
    }

    // DELETE
    public static void deleteProduct (Producto producto) throws ProductException {
        try {
            String deleteQuery = "DELETE FROM products WHERE id = "+ producto.getId();
            CRUD.setQuery(deleteQuery);
            CRUD.deleteObject(producto);
        } catch (DBException e) {
            throw new ProductException("PROBLEMA EN DELETE PRODUCT: " + e.getMessage());
        }
    }

    // DELETE BY ID
    public static void deleteProduct (int id) throws ProductException {
        try {
            String deleteQuery = "DELETE FROM products WHERE id = "+ id;
            CRUD.setQuery(deleteQuery);
            CRUD.deleteObjectById();
        } catch (DBException e) {
            throw new ProductException("PROBLEMA EN DELETE PRODUCT: " + e.getMessage());
        }
    }


}