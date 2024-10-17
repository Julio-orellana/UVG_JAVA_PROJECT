package org.uvg.project.GestionProductos;

/**
 * @author Ricardo Rodríguez
 * @version 1
 * Clase para simular al programador
 */

import org.uvg.project.Users.Employee;

import java.time.LocalDate;
import java.util.ArrayList;

public class Gestion {
    private String id;
    private String tipo;
    private LocalDate fecha;
    private ArrayList<Integer> identificadores;
    private ArrayList<Integer> cantidades;

    /**
     * @param cantidad_descontada Cantidad a descontar.
     * @param producto            Producto al cual se le descontarán existencias
     */

    public Gestion(String id, String tipo, LocalDate fecha, ArrayList<Producto> productos, ArrayList<Integer> cantidades, Employee empleado) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cantidades = cantidades;
        ArrayList<Integer> id_producto = new ArrayList<>();

        for (Producto producto : productos) {
            id_producto.add(producto.getId());
        }

        if (tipo.equals("Compras") || tipo.equals("Entradas")) {
            Entradas(cantidades, productos);
        }
        if (tipo.equals("Ventas") || tipo.equals("Salidas")) {
            Salidas(cantidades, productos);
        }

        this.identificadores = id_producto;
    }

    /**
     * @param cantidad_descontada Cantidad a descontar.
     * @param producto            Producto al cual se le descontarán existencias
     */

    public final void Salidas(ArrayList<Integer> cantidades, ArrayList<Producto> productos) {
        for (int i = 0; i < productos.size(); i++) {
            productos.get(i).rebajarSalida(cantidades.get(i));
        }
    }

    /**
     * @param cantidad_aumentar Cantidad a aumentar.
     * @param producto          Producto al cual se le aumentará existencias.
     */

    public final void Entradas(ArrayList<Integer> cantidades, ArrayList<Producto> productos) {
        for (int i = 0; i < productos.size(); i++) {
            productos.get(i).aumentarExistencias(cantidades.get(i));
        }
    }
}