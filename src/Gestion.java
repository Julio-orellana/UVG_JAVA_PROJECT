/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al programador
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Gestion
{   
    private int id;
    private String tipo;
    private LocalDate fecha;
    private ArrayList<Integer> identificadores;
    private ArrayList<Float> cantidades;
    private int id_empleado;

    /**
     * @param cantidad_descontada Cantidad a descontar.
     * @param producto Producto al cual se le descontarán existencias
    */

    public Gestion(int id, String tipo, ArrayList<Producto> productos, ArrayList<Float> cantidades, Empleado empleado)
    {
        this.id = id;
        this.tipo = tipo;
        this.fecha = LocalDate.now();
        this.cantidades = cantidades;
        this.id_empleado = empleado.GetId();
        ArrayList<Integer> id_producto = new ArrayList<>();
        
        for (Producto producto : productos)
        {
            id_producto.add(producto.GetId());
        }

        if (tipo.equals("Compras") || tipo.equals("Entradas"))
        {
            Entradas(cantidades, productos);
        }
        if (tipo.equals("Ventas") || tipo.equals("Salidas"))
        {
            Salidas(cantidades, productos);
        }

        this.identificadores = id_producto;
    }

    /**
     * @param cantidad_descontada Cantidad a descontar.
     * @param producto Producto al cual se le descontarán existencias
    */

    public final void Salidas(ArrayList<Float> cantidades, ArrayList<Producto> productos)
    {
        for (int i = 0; i < productos.size(); i++)
        {
            productos.get(i).RebajarSalida(cantidades.get(i));
        }
    }

    /**
     * @param cantidad_aumentar Cantidad a aumentar.
     * @param producto Producto al cual se le aumentará existencias.
    */

    public final void Entradas(ArrayList<Float> cantidades, ArrayList<Producto> productos)
    {
        for (int i = 0; i < productos.size(); i++)
        {
            productos.get(i).AumentarExistencias(cantidades.get(i));
        }
    }

    public void InsertarFilaTransaccion()
    {
        DatabaseConnector db = new DatabaseConnector();

        for (int i = 0; i < this.identificadores.size(); i++)
        {   
            String query = "INSERT INTO TRANSACCIONES (ID_GESTION, ID_PRODUCTO, CANTIDAD) VALUES (" + this.id + ", " + this.identificadores.get(i) + ", " + this.cantidades.get(i) + ")";
            System.out.println(query);
            db.executeUpdate(query);
        }
    }

    public void InsertarFilaGestion()
    {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DatabaseConnector db = new DatabaseConnector();
        String fecha_formato = this.fecha.format(formato);

        String query = "INSERT INTO GESTIONES (ID_GESTION, FECHA, TIPO, ID_EMPLEADO) VALUES (" + this.id + ", '" + fecha_formato + "', '" + this.tipo + "', " + this.id_empleado +")";
        db.executeUpdate(query);
    }
}