/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al programador
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Gestion
{   
    private int id;
    private String tipo;
    private LocalDateTime fecha;
    private ArrayList<Integer> identificadores;
    private ArrayList<Float> cantidades;
    private int id_empleado;
    private int id_c_p;
    private String descripcion;

    /**
     * @param cantidad_descontada Cantidad a descontar.
     * @param producto Producto al cual se le descontarán existencias
    */

    public Gestion(int id, String tipo, ArrayList<Producto> productos, ArrayList<Float> cantidades, Empleado empleado, int id_c_p, String descripcion)
    {
        this.id = id;
        this.tipo = tipo;
        this.fecha = LocalDateTime.now();
        this.cantidades = cantidades;
        this.id_empleado = empleado.GetId();
        this.id_c_p = id_c_p;
        this.descripcion = descripcion;
        ArrayList<Integer> id_producto = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++)
        {
            id_producto.add(productos.get(i).GetId());
            Producto producto = new Producto(productos.get(i).GetId());
            if (tipo.equals("Compras") || tipo.equals("Entradas"))
            {
                producto.AumentarExistencias(cantidades.get(i));
            }
            if (tipo.equals("Ventas") || tipo.equals("Salidas"))
            {
                if(producto.CorroborarSalida(cantidades.get(i)))
                {
                    producto.RebajarSalida(cantidades.get(i));
                }
                else
                {
                    this.cantidades.set(i, (float) 0);
                }
            }
            producto.ModificarFila();
        }
        this.identificadores = id_producto;
    }

    public void InsertarFilaTransaccion()
    {
        DatabaseConnector db = new DatabaseConnector();

        for (int i = 0; i < this.identificadores.size(); i++)
        {   
            String query = "INSERT INTO TRANSACCIONES (ID_GESTION, ID_PRODUCTO, CANTIDAD) VALUES (" + this.id + ", " + this.identificadores.get(i) + ", " + this.cantidades.get(i) + ")";
            db.executeUpdate(query);
        }
    }

    public void InsertarFilaGestion()
    {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DatabaseConnector db = new DatabaseConnector();
        String fecha_formato = this.fecha.format(formato);

        String query = "INSERT INTO GESTIONES (FECHA, TIPO, ID_EMPLEADO, ID_C_P, DESCRIPCION) VALUES ('" + fecha_formato + "', '" + this.tipo +
         "', " + this.id_empleado + ", " + this.id_c_p + ", '" + this.descripcion + "')";
        db.executeUpdate(query);
    }

    public String ToString(ArrayList<Producto> productos)
    {
        String texto = "";
        for (int i = 0; i < this.identificadores.size(); i++)
        {
            texto = texto + "Id: " + productos.get(i).GetId() +" | Producto: " + productos.get(i).GetNombre() + " | Cantidad: " + this.cantidades.get(i) + " " + productos.get(i).GetDimension()+"\n";
        }
        return texto;
    }
}