package org.uvg.project.Users;

import java.util.Scanner;

import org.uvg.project.Exceptions.EmployeeException;
import org.uvg.project.GestionProductos.Producto;
import org.uvg.project.GestionProductos.Transaction;
import org.uvg.project.Storage.Storage;

public class Employee {

    private int id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Storage storage;

    public Employee(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Transaction sellProduct(Scanner scanner, Clientes client) throws EmployeeException {

        if (this.role.equals("seller") && this.storage != null) {
            System.out.println("Ingresa el id del producto: ");
            int productId = scanner.nextInt();
            for (Location loc: this.storage.getLocations()){
                for (Producto p: loc.getProducts()){
                    if (p.getId() == productId && p.getCantidad() > 0){
                        p.setCantidad(p.getCantidad() -1);
                        Transaction transaction = new Transaction(p, 1);

                        client.buyProduct(transaction);

                        System.out.println("Gracias por tu compra!");
                        return transaction;
                    }
                    else if (p.getId() == productId && p.getCantidad() == 0){
                        throw new EmployeeException("No hay existencias para " + p.getNombre());
                    }
                }
            }
            throw new EmployeeException("Producto con id " + productId + " no encontrado.");

        } else if (this.role.equals("seller") && this.storage == null) {
            throw new EmployeeException("El empleado " + this.name + " no tiene asignado un almacen");
        } else {
            throw new EmployeeException("El empleado " + this.name + " no tiene permisos para vender productos");
        }
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
    }


}
