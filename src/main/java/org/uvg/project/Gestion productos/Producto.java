package org.uvg.project.Gestion

/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al programador
 */

class Producto
{
    protected String id;
    protected String nombre;
    protected float cantidad;
    protected String dimension;
    protected int id_ubicacion;
    protected String id_categoria;

    /**
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     * @param cantidad Cantidad de existencias del producto.
     * @param dimension Unidades en las que se mide el producto.
    */

    public Producto(String id, String nombre, String tipo, float cantidad, String dimension, Ubicacion ubicacion, Categoria categoria)
    {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.dimension = dimension;
        this.id_ubicacion = ubicacion.GetId();
        this.id_categoria = categoria.GetID();
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

    public String GetId()
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
     * @return Devuelve True si el producto se puede rebajar.
    */

    public boolean CorroborarSalida(float cantidad_descontada)
    {
        return cantidad_descontada > 0 & this.cantidad <= cantidad_descontada;
    }



    public void RebajarSalida(float cantidad_descontada)
    {
        if (this.dimension.equals("Unidad"))
        {
            cantidad_descontada = Math.round(cantidad_descontada);
        }

        if (cantidad_descontada < 0 || CorroborarSalida(cantidad_descontada) == false)
        {
            cantidad_descontada = 0f;
        }
        
        this.SetCantidad((float) this.GetCantidad() - cantidad_descontada);
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
        
        this.SetCantidad((float) this.GetCantidad() + cantidad_aumentar);
    }


}