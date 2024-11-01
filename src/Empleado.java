/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al ave
 * fecha_creación = 25/10/2024
 * fecha_modificación = 28/10/2024
 */

import java.util.List;
import java.util.Map;

class Empleado
{
    private int id;
    private String nombre;
    private String tipo;
    private String contrasenia;
    private boolean despedido;

    public Empleado(String nombre, String tipo, String contrasenia)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.contrasenia = contrasenia;
        this.despedido = false;
    }

    public Empleado(int id)
    {
        this.id = id;
        if(this.ExisteId())
        {
            DatabaseConnector db = new DatabaseConnector();
            String sql = "SELECT * FROM EMPLEADOS WHERE ID_EMPLEADO = " + this.id;
            List<Map<String, Object>> empleados = db.executeQuery(sql);
            Map<String, Object> fila = empleados.get(0);
            this.nombre = (String) fila.get("NOMBRE");
            this.tipo = (String) fila.get("TIPO");
            this.contrasenia = (String) fila.get("CONTRASENIA");
            this.despedido = false;
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

    public void SetContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }

    public void SetDespedido(boolean despedido)
    {
        this.despedido = despedido;
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

    public boolean GetDespedido()
    {
        return this.despedido;
    }

    public boolean CorroborarContrasenia()
    {
        DatabaseConnector db = new DatabaseConnector();
        String sql = "SELECT * FROM EMPLEADOS WHERE DESPEDIDO = false AND ID_EMPLEADO = " + this.id;
        List<Map<String, Object>> empleados = db.executeQuery(sql);
        Map<String, Object> fila = empleados.get(0);
        String contrasenia2 = (String) fila.get("CONTRASENIA");
        return this.contrasenia.equals(contrasenia2);
    }

    /**
     * @return Devuelve si el id existe.
    */

    public boolean ExisteId()
    {
        DatabaseConnector db = new DatabaseConnector();
        String sql = "SELECT NOMBRE FROM EMPLEADOS WHERE DESPEDIDO = false AND ID_EMPLEADO = " + this.id;
        List<Map<String, Object>> empleados = db.executeQuery(sql);
        return !empleados.isEmpty();
    }

    /**
     * Permite insertar una fila en la tabla CATEGORIAS.
    */

    public void InsertarFila()
    {
        DatabaseConnector db = new DatabaseConnector();
        String query = "INSERT INTO EMPLEADOS (NOMBRE, TIPO, CONTRASENIA, DESPEDIDO) VALUES ('" + 
        this.nombre + "', '" + this.tipo + "', '" + this.contrasenia + "', " + this.despedido + ")";
        db.executeUpdate(query);
    }

    /**
     * Permite insertar una fila en la tabla CATEGORIAS.
    */

    public void ModificarFila()
    {
        DatabaseConnector db = new DatabaseConnector();
        String query = "UPDATE EMPLEADOS SET NOMBRE = '" + this.nombre + "', TIPO = '" + this.tipo + "', CONTRASENIA = '" + this.contrasenia + "', DESPEDIDO = " + this.despedido + " WHERE ID_EMPLEADO = " + this.id;
        db.executeUpdate(query);
    }
}