/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al ave
 * fecha_creación = 25/10/2024
 * fecha_modificación = 28/10/2024
 */

import java.util.List;
import java.util.Map;

class Producto
{
    private int id;
    private String nombre;
    private String tipo;
    private float cantidad;
    private String dimension;
    private int id_ubicacion;
    private int id_categoria;
    private boolean descontinuado;

    /**
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     * @param cantidad Cantidad de existencias del producto.
     * @param dimension Unidades en las que se mide el producto.
    */

    public Producto(int id)
    {
        this.id = id;
        if(this.ExisteId())
        {
            DatabaseConnector db = new DatabaseConnector();
            String sql = "SELECT * FROM PRODUCTOS WHERE ID_PRODUCTO = " + this.id;
            List<Map<String, Object>> productos = db.executeQuery(sql);
            Map<String, Object> fila = productos.get(0);
            this.nombre = (String) fila.get("NOMBRE");
            this.tipo = (String) fila.get("TIPO");
            this.cantidad = (float) fila.get("CANTIDAD");
            this.dimension = (String) fila.get("DIMENSIONES");
            this.id_ubicacion = (int) fila.get("ID_UBICACION");
            this.id_categoria = (int) fila.get("ID_CATEGORIA");
            this.descontinuado = false;
        }
    }

    /**
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     * @param cantidad Cantidad de existencias del producto.
     * @param dimension Unidades en las que se mide el producto.
    */

    public Producto(String nombre, String tipo, float cantidad, String dimension, int id_ubicacion, int id_categoria)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.dimension = dimension;
        this.id_ubicacion = id_ubicacion;
        this.id_categoria = id_categoria;
        this.descontinuado = false;
    }

    /**
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     * @param cantidad Cantidad de existencias del producto.
     * @param dimension Unidades en las que se mide el producto.
    */

    public Producto(int id, String nombre, String tipo, float cantidad, String dimension)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.dimension = dimension;
    }

    /**
     * @param id Identificador del producto.
    */

    public void SetId(int id)
    {
        this.id = id;
    }


    /**
     * @param nombre Nombre del producto.
    */

    public void SetNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @param tipo Tipo de producto.
    */

    public void SetTipo(String tipo)
    {
        this.tipo = tipo;
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

    public void SetCategoria(int id_categoria)
    {
        this.id_categoria = id_categoria;
    }

    /**
     * @param descontinuado Boolean de si el producto está descontinuado.
    */

    public void SetDescontinuado(boolean descontinuado)
    {
        this.descontinuado = descontinuado;
    }

    /**
     * @return ID del producto.
    */

    public int GetId()
    {
        return this.id;
    }

    /**
     * @return Nombre del producto.
    */

    public String GetNombre()
    {
        return this.nombre;
    }

    /**
     * @return Tipo de producto.
    */

    public String GetTipo()
    {
        return this.tipo;
    }


    /**
     * @return Cantidad del producto.
    */

    public float GetCantidad()
    {
        return this.cantidad;
    }

    /**
     * @return Dimensión con la que se cuantifica el producto.
    */

    public String GetDimension()
    {
        return this.dimension;
    }

    /**
     * @return Boolean de si el producto está descontinuado.
    */

    public boolean GetDescontinuado()
    {
        return this.descontinuado;
    }

    /**
     * @return Devuelve True si el producto se puede rebajar.
    */

    public boolean CorroborarSalida(float cantidad_descontada)
    {
        return (cantidad_descontada > 0 && this.cantidad >= cantidad_descontada);
    }

    /**
     * Realiza la rebaja de inventario.
    */

    public void RebajarSalida(float cantidad_descontada)
    {
        if (this.dimension.equals("Unidad"))
        {
            cantidad_descontada = Math.round(cantidad_descontada);
        }
        if (CorroborarSalida(cantidad_descontada) == false)
        {
            cantidad_descontada = 0f;
        }
        this.cantidad -= cantidad_descontada;
    }

    /**
     * Realiza el aumento de inventario.
    */

    public void AumentarExistencias(float cantidad_aumentar)
    {
        if (this.dimension.equals("Unidad"))
        {
            cantidad_aumentar = Math.round(cantidad_aumentar);
        }

        if (cantidad_aumentar < 0)
        {
            cantidad_aumentar = 0f;
        }
        
        this.cantidad = (float) this.cantidad + cantidad_aumentar;
    }

    /**
     * @return Devuelve si el id existe.
    */

    public boolean ExisteId()
    {
        DatabaseConnector db = new DatabaseConnector();
        String sql = "SELECT NOMBRE FROM PRODUCTOS WHERE DESCONTINUADO = false AND ID_PRODUCTO = " + this.id;
        List<Map<String, Object>> productos = db.executeQuery(sql);
        return !productos.isEmpty();
    }

    /**
     * Permite insertar una fila en la tabla categoría.
    */

    public void InsertarFila()
    {
        DatabaseConnector db = new DatabaseConnector();

        String query = "INSERT INTO PRODUCTOS (NOMBRE, TIPO, CANTIDAD, DIMENSIONES, DESCONTINUADO, ID_UBICACION, ID_CATEGORIA) " +
        "VALUES ( '" + this.nombre + "', '" + this.tipo + "', " + this.cantidad + ", '" + this.dimension + "', " + this.descontinuado + ", " + this.id_ubicacion + ", " + this.id_categoria + ")";
        db.executeUpdate(query);
    }

    public void ModificarFila()
    {
        DatabaseConnector db = new DatabaseConnector();

        String query = "UPDATE PRODUCTOS SET NOMBRE = '" + this.nombre + "', TIPO = '" + this.tipo + "', CANTIDAD = " + this.cantidad +
        ", DIMENSIONES = '" + this.dimension + "', DESCONTINUADO = " + this.descontinuado + ", ID_UBICACION = " + this.id_ubicacion +
        ", ID_CATEGORIA = " + this.id_categoria+ " WHERE ID_PRODUCTO = " + this.id;
        db.executeUpdate(query);
    }
}