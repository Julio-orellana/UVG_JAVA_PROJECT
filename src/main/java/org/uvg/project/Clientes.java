package org.uvg.project;
import java.util.ArrayList;

public class Clientes
{
    private String nombre;
    private String IDcliente;
    private ArrayList<Compras> historial;

    public Clientes(String nombre, String IDcliente)
    {
        this.nombre = nombre;
        this.IDcliente = IDcliente;
        this.historial = new ArrayList<>();
    }
    
    public void cambiarNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }
    public String getIDcliente()
    {
        return IDcliente;
    }

    public void eliminarCompra(int index)
    {
        if (index >= 0 && index < historial.size())
        {
            historial.remove(index);
        }
    }

    public void mostrarHistorial()
    {
        for (Compra compra : historial)
        {
            System.out.println(compra);
        }
    }
}
