package org.uvg.project;

import org.uvg.project.Auth.Auth;
import org.uvg.project.Exceptions.AuthException;
import org.uvg.project.Exceptions.EmployeeException;
import org.uvg.project.Exceptions.LocationException;
import org.uvg.project.Exceptions.ProductException;
import org.uvg.project.Exceptions.StorageException;
import org.uvg.project.GestionProductos.Producto;
import org.uvg.project.GestionProductos.Transaction;
import org.uvg.project.Storage.Location;
import org.uvg.project.Storage.Storage;
import org.uvg.project.Users.Clientes;
import org.uvg.project.Users.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    
    static ArrayList<Clientes> clients = new ArrayList<Clientes>();
    static ArrayList<Employee> employees = new ArrayList<Employee>();
    static Storage storage = null;

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Storage storage = null;

        int option;
        boolean isAuth = false;

        do {
            // Mostrar menú dependiendo si está autenticado o no
            System.out.println("Escoge una opción");
            printMenu(isAuth);
            option = getInt(scanner);

            // Inicio de sesión
            if (option == 1 && !isAuth) {
                System.out.println("Ingrese su email");
                String userEmail = scanner.next();
                System.out.println("Ingrese su contraseña");
                String password = scanner.next();

                try {
                    isAuth = Auth.signIn("customers", userEmail, password);
                    option = isAuth ? -1 : 2; // Redirige después de autenticarse
                } catch (AuthException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Registro de usuario
            else if (option == 2 && !isAuth) {
                System.out.println("Ingrese tu email");
                String userEmail = scanner.next();
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
                String password = scanner.next();

                try {
                    isAuth = Auth.signUp("customers", userEmail, name, userGender, userBirthDate, password);
                } catch (AuthException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Salida si no está autenticado
            else if (option == 0 && !isAuth) {
                System.out.println("Saliendo...");
                Thread.sleep(1000);
                break;
            }
            // Opciones una vez autenticado
            else if (isAuth) {
                int locationId;
                switch (option) {
                    case 1:
                        System.out.println("Ingrese el id del almacenamiento que desea cargar");
                        int id = getInt(scanner);
                        try {
                            storage = Storage.loadStorage(id);
                        } catch (StorageException e) {
                            System.out.println("No se pudo cargar el almacenamiento, " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.println("Ingresa un id para el almacenamiento");
                        int storageId = getInt(scanner);
                        System.out.println("Ingrese el nombre del almacenamiento");
                        String name = scanner.next();
                        storage = new Storage(storageId, name);
                        try {
                            Storage.saveStorage(storage);
                            System.out.println("Almacenamiento creado con éxito");
                            System.out.println(storage);
                        } catch (StorageException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 3:
                        if (storage == null) {
                            System.out.println("Primero debe cargar o crear un almacenamiento");
                            break;
                        }
                        System.out.println("Ingresa un id de la ubiacion");
                        locationId = getInt(scanner);
                        System.out.println("Ingrese el nombre de la ubicación");
                        String locationName = scanner.next();
                        Location location = new Location(locationId, storage.getId(), locationName);
                        storage.addLocation(location);
                        try {
                            Location.saveLocation(location);
                            System.out.println("Ubicación agregada con éxito");
                            System.out.println(location);
                        } catch (LocationException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 4:
                        if (storage == null || storage.getLocations().isEmpty()) {
                            System.out.println("Primero debe cargar o crear un almacenamiento y agregar una ubicación");
                            break;
                        }
                        System.out.println("Ingrese el nombre del producto");
                        String productName = scanner.next();
                        System.out.println("Ingrese la cantidad del producto");
                        int quantity = getInt(scanner);
                        System.out.println("Ingrese las dimensiones del producto");
                        String dimension = scanner.next();
                        System.out.println("Ingrese el id de la ubicación donde desea agregar el producto");
                        locationId = getInt(scanner);

                        Producto product = new Producto(productName, quantity, dimension, storage.getId(), locationId);

                        try {
                            Producto.saveProduct(product);
                        } catch (ProductException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            storage.getLocation(locationId).addProduct(product);
                        }
                        break;

                    case 5:
                        if (storage == null || storage.getLocations().isEmpty()) {
                            System.out.println("Primero debe cargar o crear un almacenamiento y agregar una ubicación con productos");
                            break;
                        }
                        System.out.println("Ingrese el id de la ubicación");
                        locationId = getInt(scanner);
                        Location loc = storage.getLocation(locationId);
                        if (loc == null) {
                            System.out.println("Ubicación no encontrada");
                        } else {
                            System.out.println(loc.getProducts());
                        }
                        break;

                    case 6:
                        if (storage == null || storage.getLocations().isEmpty()) {
                            System.out.println("Primero debe cargar o crear un almacenamiento y agregar una ubicación");
                        } else {
                            System.out.println(storage.getLocations());
                        }
                        break;

                    case 7:
                        if (storage == null) {
                            System.out.println("Primero debe cargar o crear un almacenamiento");
                        } else {
                            try {
                                Storage.saveStorage(storage);
                                System.out.println("Almacenamiento guardado con éxito");
                            } catch (StorageException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        Thread.sleep(1000);
                        isAuth = false;
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("Opción no válida, por favor inicie sesión o regístrese");
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

    public static void printMenu(boolean isAuth) {
        System.out.println("--------------------");
        if (isAuth) {
            System.out.println("0. Salir");
            System.out.println("1. Cargar un almacen");
            System.out.println("2. Crear un nuevo almacen");
            System.out.println("3. Agregar una nueva ubicación en el almacen");
            System.out.println("4. Agregar un producto a una ubicación");
            System.out.println("5. Mostrar productos de una ubicación");
            System.out.println("6. Mostrar ubicaciones de un almacen");
            System.out.println("7. Guardar almacen");
        } else {
            System.out.println("0. Salir");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
        }
        System.out.println("--------------------");
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

    public static float getFloat(Scanner scanner) {
        float number = 0;
        do {
            try {
                number = scanner.nextFloat();
                break;
            } catch (Exception e) {
                System.out.println("Por favor ingrese un número");
                scanner.nextLine();
            }
        } while (number == 0);
        return number;
    }
}