/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al programador
 */

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

    public Producto(int id, String nombre, String tipo, float cantidad, String dimension, Ubicacion ubicacion, Categoria categoria)
    {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.dimension = dimension;
        this.id_ubicacion = ubicacion.GetId();
        this.id_categoria = categoria.GetId();
        this.descontinuado = false;
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



    public void RebajarSalida(float cantidad_descontada)
    {
        if (this.dimension.equals("Unidad"))
        {
            cantidad_descontada = Math.round(cantidad_descontada);
        }

        if (cantidad_descontada < 0 || CorroborarSalida(cantidad_descontada) == false)
        {
            System.out.println("Mucho");
            cantidad_descontada = 0f;
        }
        
        this.cantidad -= cantidad_descontada;
        System.out.println("Cant" + this.cantidad);
    }



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

    public void InsertarFila()
    {
        DatabaseConnector db = new DatabaseConnector();

        String query = "INSERT INTO PRODUCTOS (ID_PRODUCTO, NOMBRE, TIPO, CANTIDAD, DIMENSIONES, DESCONTINUADO, ID_UBICACION, ID_CATEGORIA) " +
        "VALUES (" + this.id + ", '" + this.nombre + "', '" + this.tipo + "', " + this.cantidad + ", '" + this.dimension + "', " + this.descontinuado + ", " + this.id_ubicacion + ", " + this.id_categoria + ") " +
        "ON DUPLICATE KEY UPDATE NOMBRE = '"  + this.nombre + "', TIPO = '" + this.tipo + "', CANTIDAD = " + this.cantidad + ", DIMENSIONES = '" + this.dimension + "', DESCONTINUADO = " + this.descontinuado + 
        ", ID_UBICACION = " + this.id_ubicacion + ", ID_CATEGORIA = " + this.id_categoria;
        db.executeUpdate(query);
    }
}