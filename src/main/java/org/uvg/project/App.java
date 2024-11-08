package org.uvg.project;

import org.uvg.project.Auth.Auth;
import org.uvg.project.Exceptions.*;
import org.uvg.project.GestionProductos.Producto;
import org.uvg.project.GestionProductos.Transaction;
import org.uvg.project.Storage.Location;
import org.uvg.project.Storage.Storage;
import org.uvg.project.Users.Clientes;
import org.uvg.project.Users.Employee;
import org.uvg.project.db.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    static ArrayList<Clientes> clients = new ArrayList<Clientes>();
    static ArrayList<Employee> employees = new ArrayList<Employee>();
    static Storage storage = null;

    public static void main(String[] args) throws Exception {

        try {
            storage = Storage.loadStorage(1);
        } catch (StorageException e) {
            System.out.println("No se pudo cargar el almacenamiento, " + e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        HashMap<Boolean, Integer> isAuth;

        isAuth = authMenu(scanner);

        for (HashMap.Entry<Boolean, Integer> entry : isAuth.entrySet()) {
            if (entry.getKey()) {
                if (entry.getValue() == 1) {
                    if (clients.size() > 0) {
                        customerMenu(scanner);
                    }
                } else if (entry.getValue() == 2) {
                    if (employees.size() > 0) {
                        employeeMenu(scanner);
                    }
                }
            }
        }
    }

    public static void printEmployeeMenu(){
        System.out.println("0. Salir");
        System.out.println("1. Ver productos");
        System.out.println("2. Ver ubicaciones");
        System.out.println("3. Vender producto");
        System.out.println("4. Ver historial de ventas");
        System.out.println("5. Agregar Ubicación");
        System.out.println("6. Modificar una ubicacion");
        System.out.println("7. Eliminar una ubicacion");
        System.out.println("8. Agregar Producto");
        System.out.println("9. Modificar un producto");
        System.out.println("10. Eliminar un producto");
    }

    public static void printCustomerMenu(){
        System.out.println("0. Salir");
        System.out.println("1. Ver productos disponibles");
        System.out.println("2. Comprar producto");
        System.out.println("3. Ver historial de compras");
    }

    public static HashMap<Boolean, Integer> authMenu(Scanner scanner) {
        boolean isAuth = false;
        int option = 0;
        int userType;

        System.out.println("0. Salir." +"\n1. Iniciar sesión" + "\n2. Registrarse");
        option = option == 0 ? getInt(scanner) : 0;
        System.out.println("Escoja una opcion:\n1. Cliente" +  "\n2. Empleado");
        userType = getInt(scanner);
        String userEmail;
        String password;
        // Clientes
        if (userType == 1) {
            switch (option) {
                case 1:
                    System.out.println("Ingrese su email");
                    userEmail = scanner.next();
                    System.out.println("Ingrese su contraseña");
                    password = scanner.next();

                    try {
                        isAuth = Auth.signIn("customers", userEmail, password);
                        if (isAuth){
                            Statement stmt = DBManager.getStatement();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM customers where email = '" + userEmail + "'");
                            while (rs.next()) {
                                int id = rs.getInt("id");
                                String name = rs.getString("name");
                                System.out.println("Bienvenido " + name + " tu id es: " + id);
                                clients.add(new Clientes(name, id));
                            }
                        }
                    } catch (AuthException e) {
                        System.out.println(e.getMessage());
                    } catch (SQLException | DBException e) {
                        System.out.println("Error de base de datos: " + e.getMessage());
                    } finally {
                        break;
                    }

                case 2:
                    System.out.println("Ingrese tu email");
                    userEmail = scanner.next();
                    System.out.println("Ingresa tu nombre");
                    String name = scanner.next();
                    System.out.println("Ingresa tu género (M/F)");
                    char userGender = scanner.next().charAt(0);
                    System.out.println("Ingresa tu fecha de nacimiento (YYYY-MM-DD)");
                    String userBirth = scanner.next().replace("/", "-").replace(" ", "");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate parseUserBirth = LocalDate.parse(userBirth, formatter);
                    String userBirthDate = parseUserBirth.format(formatter);

                    System.out.println("Ingrese su contraseña");
                    password = scanner.next();

                    try {
                        isAuth = Auth.signUp("customers", userEmail, name, userGender, userBirthDate, password);
                        if (isAuth){
                            Statement stmt = DBManager.getStatement();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM customers where email = '" + userEmail + "'");
                            while (rs.next()) {
                                int id = rs.getInt("id");
                                name = rs.getString("name");
                                System.out.println("Bienvenido " + name + " tu id es: " + id);
                                clients.add(new Clientes(name, id));
                            }
                        }
                    } catch (AuthException e) {
                        System.out.println(e.getMessage());
                    } catch (SQLException | DBException e) {
                        System.out.println("Error de base de datos: " + e.getMessage());
                    } finally {
                        break;
                    }

                case 0:
                    System.out.println("Saliendo...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        // Empleados
        } else if (userType == 2) {
            switch (option) {
                case 1:
                    System.out.println("Ingrese su email");
                    userEmail = scanner.next();
                    System.out.println("Ingrese su contraseña");
                    password = scanner.next();

                    try {
                        isAuth = Auth.signIn("employee", userEmail, password);
                        if (isAuth) {
                            Statement stmt = DBManager.getStatement();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM employee where email = '" + userEmail + "'");
                            while (rs.next()){

                                int id = rs.getInt("id");
                                String name = rs.getString("name");
                                String email = rs.getString("email");
                                char gender = rs.getString("gender").charAt(0);
                                String role = rs.getString("role");

                                employees.add(new Employee(id, name, email, gender, role));
                                System.out.println("Bienvenido " + name + " tu id es: " + id);
                                employees.get(-1).setStorage(storage);
                            }
                        }
                    } catch (AuthException e) {
                        System.out.println(e.getMessage());
                    } catch (SQLException | DBException e) {
                        System.out.println("Error de base de datos: " + e.getMessage());
                    } finally {
                        break;
                    }

                case 2:
                    System.out.println("Ingrese el email del empleado");
                    userEmail = scanner.next().replace(" ", "");
                    System.out.println("Ingresa el nombre del empleado");
                    String name = scanner.next().replace(" ", "");
                    System.out.println("Ingresa el rol del empleado");
                    String rol = scanner.next();
                    System.out.println("Ingresa el género del empleado (M/F)");
                    char userGender = scanner.next().charAt(0);
                    System.out.println("Ingrese una contraseña para el empleado");
                    password = scanner.next();

                    try {
                        isAuth = Auth.signUp("employee", userEmail, name, rol, userGender, password);
                        if (isAuth) {
                            Statement stmt = DBManager.getStatement();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM employee where email = '" + userEmail + "'");
                            while (rs.next()){

                                int id = rs.getInt("id");
                                name = rs.getString("name");
                                String email = rs.getString("email");
                                char gender = rs.getString("gender").charAt(0);
                                String role = rs.getString("role");

                                employees.add(new Employee(id, name, email, gender, role));
                                System.out.println("Bienvenido " + name + " tu id es: " + id);
                                employees.get(-1).setStorage(storage);
                            }
                        }
                    } catch (AuthException e) {
                        System.out.println(e.getMessage());
                    } catch (SQLException | DBException e) {
                        System.out.println("Error de base de datos: " + e.getMessage());
                    } finally {
                        break;
                    }

                case 0:
                    System.out.println("Saliendo...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Opción no válida");
            }
        } else {
            System.out.println("Opción no válida");
        }
        boolean finalIsAuth = isAuth;
        return new HashMap<Boolean, Integer>() {{
            put(finalIsAuth, userType);
        }};
    }

    public static void customerMenu(Scanner scanner) {
        int option = 0;
        do {
            printCustomerMenu();
            option = getInt(scanner);
            int clientId;
            switch (option) {
                case 1:
                    for (Location ubicacion: storage.getLocations()){
                        for (Producto producto: ubicacion.getProducts()){
                            System.out.println(producto);
                        }
                    }
                    break;
                case 2:
                    System.out.println("Ingresa tu id de cliente: ");
                    clientId = getInt(scanner);
                    for (Clientes cliente: clients) {
                        if (cliente.getId() == clientId) {
                            try {
                                if (employees.size() > 0) {
                                    sellProduct(scanner, clientId);
                                } else {
                                    System.out.println("No hay empleados disponibles para realizar la venta");
                                }
                            } catch (ProductException ex){
                                System.out.println(ex);
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println("Historial de compras:");
                    System.out.println("Ingresa tu id de cliente: ");
                    clientId = getInt(scanner);
                    for (Clientes cliente: clients) {
                        if (cliente.getId() == clientId) {
                            try {
                                cliente.mostrarHistorial();
                            } catch (TransactionException ex){
                                System.out.println(ex);
                            }
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saliendo...");

                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (option != 0);
    }

    public static void employeeMenu(Scanner scanner) {
        int option = 0;
        do {
            printEmployeeMenu();
            option = getInt(scanner);
            int locationId;
            switch (option) {
                case 1:
                    for (Location loc: storage.getLocations()){
                        for (Producto p: loc.getProducts()){
                            System.out.println(p);
                        }
                    }
                    break;
                case 2:
                    for (Location loc: storage.getLocations()){
                        System.out.println(loc);
                    }
                    break;
                case 3:
                    System.out.println("Ingresa tu id de empleado: ");
                    int employeeId = scanner.nextInt();
                    for (Employee e: employees) {
                        if (e.getId() == employeeId) {
                            System.out.println("Ingresa el id del cliente: ");
                            int clientId = scanner.nextInt();
                            for (Clientes c: clients) {
                                if (clientId == c.getId()){
                                    try {
                                        Transaction transaction = e.sellProduct(scanner, c);
                                        storage.addTransacatcion(transaction);
                                    } catch (EmployeeException ex){
                                        System.out.println(ex);
                                    }
                                }
                            }
                            System.out.println("No hay ningun cliente con id " + clientId);
                            break;
                        }
                    }
                    System.out.println("No se encontro el empleado con id "+ employeeId);
                    break;
                case 4:
                    System.out.println("Historial de ventas: \n");
                    for (Transaction t: storage.getTransactions()){
                        System.out.println(t);
                    }
                    break;
                case 5:
                    System.out.println("Agregar Ubicación");
                    String locationName = scanner.next();
                    try {
                        Location location = new Location(storage.getId(), locationName);
                        storage.addLocation(location);
                    } catch (LocationException e) {
                        System.out.println(e.getMessage());
                    } catch (StorageException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Modificar una ubicacion");
                    System.out.println("Ingrese el id de la ubicación que desea modificar");
                    locationId = getInt(scanner);
                    for (Location loc: storage.getLocations()){
                        if (loc.getId() == locationId){
                            System.out.println("Ingrese el nuevo nombre de la ubicación");
                            String newName = scanner.next();
                            loc.setName(newName);
                            try{
                                Location.updateLocation(loc);
                                System.out.println("Ubicación actualizada con éxito");
                            } catch (LocationException e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                    }
                    System.out.println("No se encontro la ubicación con id " + locationId);
                    break;
                case 7:
                    System.out.println("Eliminar una ubicación");
                    System.out.println("Ingrese el id de la ubicación que desea eliminar");
                    locationId = getInt(scanner);
                    for (Location loc: storage.getLocations()){
                        if (loc.getId() == locationId){
                            try{
                                Location.deleteLocation(loc);
                                storage.removeLocation(loc.getId());
                                System.out.println("Ubicación eliminada con éxito");
                            } catch (LocationException e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                    }
                    System.out.println("No se encontro la ubicación con id " + locationId);
                    break;
                case 8:
                    System.out.println("Agregar Producto");
                    System.out.println("Ingrese el nombre del producto");
                    String productName = scanner.next();
                    System.out.println("Ingrese la cantidad del producto");
                    int quantity = getInt(scanner);
                    System.out.println("Ingrese las dimensiones del producto");
                    String dimension = scanner.next();
                    System.out.println("Ingrese el id de la ubicación donde desea agregar el producto");
                    locationId = getInt(scanner);
                    try {
                        Producto product = new Producto(productName, quantity, dimension, storage.getId(), locationId);
                        storage.getLocation(locationId).addProduct(product);
                        Producto.saveProduct(product);
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 9:
                    System.out.println("Modificar un producto");
                    System.out.println("Ingrese el id del producto que desea modificar");
                    int productId = getInt(scanner);
                    for (Location loc: storage.getLocations()){
                        for (Producto p: loc.getProducts()){
                            if (p.getId() == productId){
                                System.out.println("Ingrese el nuevo nombre del producto");
                                String newName = scanner.next();
                                System.out.println("Ingrese la nueva cantidad del producto");
                                int newQuantity = getInt(scanner);
                                System.out.println("Ingrese las nuevas dimensiones del producto");
                                String newDimension = scanner.next();
                                p.setNombre(newName);
                                p.setCantidad(newQuantity);
                                p.setDimension(newDimension);
                                try{
                                    Producto.updateProduct(p);
                                    System.out.println("Producto actualizado con éxito");
                                } catch (ProductException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }
                        }
                    }
                    System.out.println("No se encontro el producto con id " + productId);
                    break;
                case 10:
                    System.out.println("Eliminar un producto");
                    System.out.println("Ingrese el id del producto que desea eliminar");
                    productId = getInt(scanner);
                    for (Location loc: storage.getLocations()){
                        for (Producto p: loc.getProducts()){
                            if (p.getId() == productId){
                                try{
                                    Producto.deleteProduct(p);
                                    loc.removeProduct(p);
                                    System.out.println("Producto eliminado con éxito");
                                } catch (ProductException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }
                        }
                    }
                    System.out.println("No se encontro el producto con id " + productId);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (option != 0);
    }

    public static Transaction sellProduct(Scanner scanner, int clientId) throws ProductException {
        System.out.println("Ingresa tu id de empleado: ");
        int employeeId = getInt(scanner);
        for (Employee empleado : employees) {
            if (empleado.getId() == employeeId) {
                for (Clientes cliente : clients) {
                    if (clientId == cliente.getId()) {
                        try {
                            Transaction transaction = empleado.sellProduct(scanner, cliente);
                            storage.addTransacatcion(transaction);
                            cliente.buyProduct(transaction);
                            return transaction;
                        } catch (EmployeeException e) {
                            System.out.println(e);
                        }
                    }
                }
                System.out.println("No hay ningun cliente con id " + clientId);
            }
        }
        throw new ProductException("No se encontro el empleado con id " + employeeId);
    }

    public static int getInt(Scanner scanner) {
        int number = 0;
        do {
            try {
                number = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Por favor ingrese un número");
                scanner.nextLine();
            }
        } while (number == 0);
        return number;
    }

}