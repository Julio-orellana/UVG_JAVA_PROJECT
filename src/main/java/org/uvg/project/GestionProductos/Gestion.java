/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al programador
 */

import java.time.LocalDate;
import java.util.ArrayList;

class Gestion
{
    private String id;
    private String tipo;
    private LocalDate fecha;
    private ArrayList<String> identificadores;
    private ArrayList<Float> cantidades;
    private int id_empleado;

    /**
     * @param cantidad_descontada Cantidad a descontar.
     * @param producto Producto al cual se le descontarán existencias
     */

    public Gestion(String id, String tipo, LocalDate fecha, ArrayList<Producto> productos, ArrayList<Float> cantidades, Empleado empleado)
    {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cantidades = cantidades;
        this.id_empleado = empleado.GetId();
        ArrayList<String> id_producto = new ArrayList<>();

        for (Producto producto : productos)
        {
            id_producto.add(producto.getid());
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
            productos.get(i).rebajarsalida(cantidades.get(i));
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
            productos.get(i).aumentarexistencias(cantidades.get(i));
        }
    }
}