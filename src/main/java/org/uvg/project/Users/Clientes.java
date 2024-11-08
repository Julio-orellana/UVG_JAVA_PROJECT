package org.uvg.project.Users;

import java.util.ArrayList;

import org.uvg.project.Exceptions.TransactionException;
import org.uvg.project.GestionProductos.Transaction;

public class Clientes
{
    private String nombre;
    private int id;
    protected ArrayList<Transaction> historial;

    public Clientes(String nombre, int id)
    {
        this.nombre = nombre;
        this.id = id;
        this.historial = new ArrayList<>();
    }

    public Transaction buyProduct(Transaction transaction)
    {
        historial.add(transaction);
        return transaction;
    }

    public void cambiarNombre(String nombre)
    {
        this.nombre = nombre;
    }
    public String getNombre()
    {
        return nombre;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return id;
    }

    public void setHistorial(ArrayList<Transaction> historial)
    {
        this.historial = historial;
    }
    public ArrayList<Transaction> getHistorial()
    {
        return historial;
    }

    public Transaction buyProduct(Transaction transaction)
    {
        historial.add(transaction);
        return transaction;
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

    @Override
    public String toString()
    {
        return "Cliente: " + nombre + " (ID: " + id + ")";
    }
}
