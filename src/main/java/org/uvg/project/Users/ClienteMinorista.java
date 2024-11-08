package org.uvg.project.Users;
import org.uvg.project.Exceptions.TransactionException;
import org.uvg.project.GestionProductos.Transaction;

public class ClienteMinorista extends Clientes
{
    private int limite = 5;


    public ClienteMinorista(String nombre, int id, int limite)
    {
        super(nombre, id);
        this.limite = limite;
    }

    public void setLimite(int  limite)
    {
        this.limite = limite;
    }
    public int getLimite()
    {
        return limite;
    }

    public void agregarCompra(Transaction compra) throws TransactionException
    {
        if (compra.getQuantity() < limite)
    {
        throw new TransactionException("La compra debe de ser de 10 productos");
    }
    historial.add(compra);
    }
}
