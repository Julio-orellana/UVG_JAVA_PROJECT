package org.uvg.project.GestionProductos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


/**
 * Clase Transaction
 *
 * Esta clase se encarga de manejar las transacciones de los productos para llevar un control en el inventario.
 * @version 1.0
 * @since 12-10-2024
 * @author Ginebra Estrada
 */
public class Transaction {

    private String id;
    private String date;
    private Producto product;
    private int quantity;

    /**
     * Constructor de la clase Transaction
     * @param product
     * @param quantity
     */
    public Transaction(Producto product, int quantity) {
        // Crear un id único para la transacción
        this.id = UUID.randomUUID().toString();
        // Obtener la fecha actual de la transacción
        LocalDateTime date = LocalDateTime.now();
        // Formatear la fecha a un formato específico
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.date = formatter.format(date);
        this.product = product;
        this.quantity = quantity;
    }


    /**
     * Método getId
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Método getDate
     * @return date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Método getProduct
     * @return product
     */
    public Producto getProduct() {
        return this.product;
    }

    /**
     * Método getQuantity
     * @return quantity
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Método getTotal
     * @return total
     */

    /**
     * Método setId
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Método setDate
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Método setProduct
     * @param product
     */
    public void setProduct(Producto product) {
        this.product = product;
    }

    /**
     * Método setQuantity
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}