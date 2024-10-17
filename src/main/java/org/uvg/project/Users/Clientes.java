package org.uvg.project.Users;

import org.uvg.project.Exceptions.TransactionException;
import org.uvg.project.GestionProductos.Transaction;
import java.util.ArrayList;

public class Clientes
{
    private String nombre;
    private int id;
    private ArrayList<Transaction> historial;

    public Clientes(String nombre, int id)
    {
        this.nombre = nombre;
        this.id = id;
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
    public int getId()
    {
        return id;
    }

    public void eliminarCompra(int index)
    {
        if (index >= 0 && index < historial.size())
        {
            historial.remove(index);
        }
    }

    public void mostrarHistorial() throws TransactionException
    {
        if (historial.isEmpty())
            throw new TransactionException("No hay compras en el historial");

        for (Transaction transaction : historial)
        {
            System.out.println(transaction);
        }
    }
}
