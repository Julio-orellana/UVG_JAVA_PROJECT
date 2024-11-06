package org.uvg.project.Users;
import org.uvg.project.Exceptions.TransactionException;
import org.uvg.project.GestionProductos.Transaction;

public class ClienteMayorista extends Clientes
{
    private double descuento = 0.15;
    private int cantMin = 10;


public ClienteMayorista(String nombre, int id, double descuento, int cantMin)
{
    super(nombre, id);
    this.descuento = descuento;
    this.cantMin = cantMin;
}

public void setDescuento(double  descuento)
    {
        this.descuento = descuento;
    }
    public double getDescuento()
    {
        return descuento;
    }

@Override
public void agregarCompra(Transaction compra) throws TransactionException
{
    if (compra.getQuantity() < cantMin)
    {
        throw new TransactionException("La compra debe de ser de 10 productos");
    }
    historial.add(compra);
}}