package org.uvg.project;
import java.util.ArrayList;

import org.uvg.project.Storage.Storage;

public class Clientes
{
    private String nombre;
    private String IDcliente;
    private ArrayList<Storage> historial;

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
    public void setIDcliente(String IDcliente)
    {
        this.IDcliente = IDcliente;
    }
    public String getIDcliente()
    {
        return IDcliente;
    }

    public void setHistorial(ArrayList<Storage> historial)
    {
        this.historial = historial;
    }
    public ArrayList<Storage> getHistorial()
    {
        return historial;
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
        for (Storage compra : historial)
        {
            System.out.println(compra);
        }
    }
}
