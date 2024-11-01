/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al ave
 * fecha_creación = 25/10/2024
 * fecha_modificación = 28/10/2024
 */

import java.util.List;
import java.util.Map;


class Cliente_Proveedor
{
    private int id;
    private String nombre;
    private String tipo;

    public Cliente_Proveedor(String nombre, String tipo)
    {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Cliente_Proveedor(int id)
    {
        this.id = id;
        if(this.ExisteId())
        {
            DatabaseConnector db = new DatabaseConnector();
            String sql = "SELECT * FROM CLIENTES_PROVEEDORES WHERE ID_C_P = " + this.id;
            List<Map<String, Object>> clientes_proveedores = db.executeQuery(sql);
            Map<String, Object> fila = clientes_proveedores.get(0);
            this.nombre = (String) fila.get("NOMBRE");
            this.tipo = (String) fila.get("TIPO");
        }
    }

    public void SetId(int id)
    {
        this.id = id;
    }

    public void SetNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void SetTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public int GetId()
    {
        return this.id;
    }

    public String GetNombre()
    {
        return this.nombre;
    }

    public String GetTipo()
    {
        return this.tipo;
    }

    public boolean ExisteId()
    {
        DatabaseConnector db = new DatabaseConnector();
        String sql = "SELECT NOMBRE FROM CLIENTES_PROVEEDORES WHERE ID_C_P = " + this.id;
        List<Map<String, Object>> clientes_proveedores = db.executeQuery(sql);
        return !clientes_proveedores.isEmpty();
    }


    /**
     * Permite insertar una fila en la tabla CATEGORIAS.
    */

    public void InsertarFila()
    {
        DatabaseConnector db = new DatabaseConnector();
        String query = "INSERT INTO CLIENTES_PROVEEDORES (NOMBRE, TIPO) VALUES ('" + this.nombre + "', '" + this.tipo + "')";
        db.executeUpdate(query);
    }

    /**
     * Permite insertar una fila en la tabla CATEGORIAS.
    */

    public void ModificarFila()
    {
        DatabaseConnector db = new DatabaseConnector();
        String query = "UPDATE CLIENTES_PROVEEDORES SET NOMBRE = '" + this.nombre + "', TIPO = '" + this.tipo + "' WHERE ID_C_P = " + this.id;
        db.executeUpdate(query);
    }
}