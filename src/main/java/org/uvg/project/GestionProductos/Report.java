package org.uvg.project.GestionProductos;
import java.util.ArrayList;

import org.uvg.project.Users.Employee;

public class Report
{
    private int id;
    private Employee employee;
    private ArrayList<Transaction> transactions;

    public Report(int id, Employee employee)
    {
        this.id = id;
        this.employee = employee;
        this.transactions = new ArrayList<>();
    }

    public int getId()
    {
        return this.id;
    }

    public Employee getEmployee()
    {
        return this.employee;
    }

    public ArrayList<Transaction> getTransactions()
    {
        return this.transactions;
    }

    public void addTransaction(Transaction transaction)
    {
        this.transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction)
    {
        this.transactions.remove(transaction);
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public String toString()
    {
        return "Reporte de " + employee.getName();
    }
}

