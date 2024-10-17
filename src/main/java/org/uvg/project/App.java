package org.uvg.project;


import org.uvg.project.GestionProductos.Producto;
import org.uvg.project.Storage.Storage;

public class App {

    public static void main(String[] args) throws Exception {

        Storage storage = Storage.loadStorage(1);

        for (int i = 0; i < storage.getLocations().size(); i++) {
            storage.getLocations().get(i).addProduct(new Producto(i, "Producto" + i, 10, "cm", 1, 1));
        }

        System.out.println("Productos en la ubicación 1:");
        for (Producto product : storage.getLocation(1).getProducts()) {
            System.out.println(product);
        }
    }

}