/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al ave
 * fecha_creación = 25/10/2024
 * fecha_modificación = 28/10/2024
 */

import java.util.List;
import java.util.Map;

class Categoria
{
    private int id;
    private String nombre;

    /**
     * @param id Identificador de la categoría.
     * @param nombre Nombre de la categoría.
    */

    public Categoria(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * @param id Identificador de la categoría.
    */

    public Categoria(int id)
    {
        this.id = id;
    }

    /**
     * @param nombre Nombre de la categoría.
    */

    public Categoria(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @param id Identificador de la categoría.
    */

    public void SetId(int id)
    {
        this.id = id;
    }

    /**
     * @param nombre Nombre de la categoría.
    */

    public void SetNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    /**
     * @return Identificador de la categoría.
    */

    public int GetId()
    {
        return this.id;
    }

    /**
     * @return Nombre de la categoría.
    */

    public String GetNombre()
    {
        return this.nombre;
    }

    /**
     * @return  Devuelve si el id existe.
    */

    public boolean ExisteId()
    {
        DatabaseConnector db = new DatabaseConnector();
        String sql = "SELECT NOMBRE FROM CATEGORIAS WHERE ID_CATEGORIA = " + this.id;
        List<Map<String, Object>> categorias = db.executeQuery(sql);
        return !categorias.isEmpty();
    }

    /**
     * Permite insertar una fila en la tabla CATEGORIAS.
    */

    public void InsertarFila()
    {
        DatabaseConnector db = new DatabaseConnector();
        String query = "INSERT INTO CATEGORIAS (NOMBRE) VALUES ('" + this.nombre + "')";
        db.executeUpdate(query);
    }

    /**
     * Permite insertar una fila en la tabla CATEGORIAS.
    */

    public void ModificarFila()
    {
        DatabaseConnector db = new DatabaseConnector();
        String query = "UPDATE CATEGORIAS SET NOMBRE = '" + this.nombre + "' WHERE ID_CATEGORIA = " + this.id;
        db.executeUpdate(query);
    }

    @Override
    public String toString()
    {
        DatabaseConnector db = new DatabaseConnector();
        String sql = "SELECT NOMBRE FROM CATEGORIAS WHERE ID_CATEGORIA = " + this.id;
        List<Map<String, Object>> categorias = db.executeQuery(sql);
        Map<String, Object> fila = categorias.get(0);
        String nombre_categoria = (String) fila.get("NOMBRE");
        return nombre_categoria;
    }
}