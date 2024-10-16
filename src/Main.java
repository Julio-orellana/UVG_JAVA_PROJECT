import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Main
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        boolean flag_empleado = true;
        DatabaseConnector db = new DatabaseConnector();
        Empleado empleado = null;

        while(flag_empleado)
        {
            //Parte de validación del empleado, autenticar un una contraseña y pasar al siguiente en caso de que sea correcta

            empleado = new Empleado(1, "Ricardo");
            flag_empleado = false;
            flag = true;
        }


        while (flag) 
        {
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("1) Crear/modificar una ubicación");
            System.err.println("2) Crear/modificar una categoría");
            System.out.println("3) Crear/modificar/eliminar un producto");
            System.out.println("4) Crear/modificar/eliminar un empleado");
            System.out.println("5) Gestionar una transacción");
            System.out.println("6) *Ver información de un producto/ubicación/categoría/empleado");
            System.out.println("7) *Filtrar gestiones por fecha y tipo");
            System.out.println("8) Salir");
            String menu = sc.nextLine();

            if(menu.equals("1"))
            {
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1) Agregar una ubicación");
                System.out.println("2) Nodificar una ubicación");
                String opcion = sc.nextLine();
                
                if (opcion.equals("1"))
                {
                    System.out.println("\nIngrese el nombre de la ubicación");
                    String nombre = sc.nextLine();

                    String query_ubicacion = "INSERT INTO UBICACIONES (NOMBRE) VALUES ('" + nombre + "')";
                    db.executeUpdate(query_ubicacion);
                }

                else if (opcion.equals("2"))
                {
                    System.out.println("\nIngrese el id de la ubicación");
                    String id = sc.nextLine();

                    System.out.println("\nIngrese el nuevo nombre de la ubicación");
                    String nombre = sc.nextLine();

                    String query_ubicacion = "UPDATE UBICACIONES SET NOMBRE = '" + nombre + "' WHERE ID_UBICACION = " + id;
                    db.executeUpdate(query_ubicacion);
                }

                else
                {
                    System.out.println("\nHa ingresado una opción incorrecta");
                }
            }

            else if(menu.equals("2"))
            {
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1) Agregar una categoría");
                System.out.println("2) Modificar una categoría");
                String opcion = sc.nextLine();

                if (opcion.equals("1"))
                {
                    System.out.println("\nIngrese el nombre de la categoría");
                    String nombre = sc.nextLine();

                    String query_categoria = "INSERT INTO CATEGORIAS (NOMBRE) VALUES ('" + nombre + "')";
                    db.executeUpdate(query_categoria);
                }

                else if (opcion.equals("2"))
                {
                    System.out.println("\nIngrese el id de la categoría");
                    String id = sc.nextLine();

                    System.out.println("\nIngrese el nuevo nombre de la categoría");
                    String nombre = sc.nextLine();

                    String query_categoria = "UPDATE CATEGORIAS SET NOMBRE = '" + nombre + "' WHERE ID_CATEGORIA = " + id;
                    db.executeUpdate(query_categoria);
                }

                else
                {
                    System.out.println("\nHa ingresado una opción incorrecta");
                }
            }

            else if(menu.equals("3"))
            {
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1) Crear un producto");
                System.out.println("2) *Modificar un producto");
                System.out.println("3) *Eliminar un producto");
                String opcion = sc.nextLine();

                if(opcion.equals("1"))
                {
                    try 
                    {
                        System.out.println("\nIngrese el nombre del producto");
                        String nombre = sc.nextLine();

                        System.out.println("\nIngrese el tipo del producto");
                        System.out.println("1) Materia prima");
                        System.out.println("2) Producto terminado (Aquellos productos que fabrica la empresa que están listos para las ventas)");
                        System.out.println("3) Intermedio");
                        System.out.println("4) Producto reventa (Aquellos productos que se compran para vender y no los fabrica la empresa)");
                        String tipo_opcion = sc.nextLine();

                        String tipo = "";

                        switch (tipo_opcion)
                        {
                            case "1" -> tipo = "MP";
                            case "2" -> tipo = "PT";
                            case "3" -> tipo = "IN";
                            case "4" -> tipo = "PR";
                        }

                        System.out.println("\nIngrese las dimensiones de medición del producto");
                        String dimension = sc.nextLine();

                        System.out.println("\nIngrese el id de la ubicación");
                        String id_ubicacion = sc.nextLine();
                        
                        String query_ubicacion = "SELECT NOMBRE FROM UBICACIONES WHERE ID_UBICACION = " + id_ubicacion;
                        List<Map<String, Object>> ubicaciones = db.executeQuery(query_ubicacion);
                        Map<String, Object> fila = ubicaciones.get(0);
                        String nombre_ubicacion = (String) fila.get("NOMBRE");
                        Ubicacion ubicacion = new Ubicacion(Integer.parseInt(id_ubicacion), nombre_ubicacion);

                        System.out.println("\nIngrese el id de la categoría");
                        String id_categoria = sc.nextLine();
                        
                        String query_categoria = "SELECT NOMBRE FROM CATEGORIAS WHERE ID_CATEGORIA = " + id_categoria;
                        List<Map<String, Object>> categorias = db.executeQuery(query_categoria);
                        fila = categorias.get(0);
                        String nombre_categoria = (String) fila.get("NOMBRE");
                        Categoria categoria = new Categoria(Integer.parseInt(id_categoria), nombre_categoria);

                        if(nombre_ubicacion == null || nombre_categoria == null)
                        {
                            System.out.println("\nNo existe la categoría o la ubicación ingresadas.");
                        }

                        else
                        {
                            String query_id_max = "SELECT MAX(ID_PRODUCTO) AS MAXIMO FROM PRODUCTOS";
                            List<Map<String, Object>> maximos = db.executeQuery(query_id_max);
                            fila = maximos.get(0);
                            Integer id_maximo_prueba = (Integer) fila.get("MAXIMO");
                            
                            int id_maximo; 
                            if (id_maximo_prueba == null)
                            {
                                id_maximo = 0;
                            }
                            else
                            {
                                id_maximo = id_maximo_prueba;
                            }

                            if(!tipo.equals(""))
                            {
                                Producto producto = new Producto(id_maximo + 1, nombre, tipo, (float) 0, dimension, ubicacion, categoria);
                                producto.InsertarFila();
                            }

                            else
                            {
                                System.out.println("\nNo ingresó un tipo correcto.");
                            }
                        }
                    }
                    
                    catch (Exception e)
                    {
                        System.out.println("\nIngresó datos erróneos");
                    }
                }
            }

            else if(menu.equals("4"))
            {
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1) Agregar un empleado");
                System.out.println("2) Modificar un empleado");
                String opcion = sc.nextLine();

                if (opcion.equals("1"))
                {
                    System.out.println("\nIngrese el nombre del empleado");
                    String nombre = sc.nextLine();

                    String query_categoria = "INSERT INTO EMPLEADOS (NOMBRE) VALUES ('" + nombre + "')";
                    db.executeUpdate(query_categoria);
                }

                else if (opcion.equals("2"))
                {
                    System.out.println("\nIngrese el id del empleado");
                    String id = sc.nextLine();

                    System.out.println("\nIngrese el nuevo nombre del empleado");
                    String nombre = sc.nextLine();

                    String query_categoria = "UPDATE EMPLEADOS SET NOMBRE = '" + nombre + "' WHERE ID_EMPLEADO = " + id;
                    db.executeUpdate(query_categoria);
                }

                else
                {
                    System.out.println("\nHa ingresado una opción incorrecta");
                }
            }

            else if(menu.equals("5"))
            {
                System.out.println("\n¿Qué tipo de transacción desea hacer?");
                System.out.println("1) Compra");
                System.out.println("2) Venta");
                System.out.println("3) Aumento de existencias");
                System.out.println("4) Disminución de existencias");
                String opcion = sc.nextLine();

                if (opcion.equals("1"))
                {
                    boolean flag2 = true;
                    ArrayList<Producto> productos = new ArrayList<>();
                    ArrayList<Float> cantidades = new ArrayList<>();

                    while(flag2)
                    {
                        System.out.println("¿Desea ingresar un producto al listado?");
                        System.out.println("1) Si");
                        System.out.println("2) No");
                        String opcion2 = sc.nextLine();

                        if(opcion2.equals("1")) 
                        {  
                            System.out.println("\nIngrese el identificador del producto.");
                            String identificador = sc.nextLine();

                            String query_productos = "SELECT * FROM PRODUCTOS WHERE ID_PRODUCTO = " + identificador + " AND TIPO IN ('MP', 'PR') AND DESCONTINUADO = false";
                            
                            boolean id_correcto = true;
                            try
                            {
                                List<Map<String, Object>> prod = db.executeQuery(query_productos);
                                Map<String, Object> fila = prod.get(0);
                            
                                String nombre_producto = (String) fila.get("NOMBRE");
                                System.out.println("El nombre del producto que eligió es: " + nombre_producto);
                                System.out.println("¿Es el producto correcto?");
                                System.out.println("1) Si");
                                System.out.println("Cualquier otra tecla) No");
                                String correcto = sc.nextLine();

                                if(correcto.equals("1"))
                                {
                                    int id_producto = (int) fila.get("ID_PRODUCTO");
                                    String tipo = (String) fila.get("TIPO");
                                    float cantidad = (float) fila.get("CANTIDAD");
                                    String dimension = (String) fila.get("DIMENSIONES");
                                    int id_ubicacion = (int) fila.get("ID_UBICACION");
                                    int id_categoria = (int) fila.get("ID_CATEGORIA");

                                    Ubicacion ubicacion = new Ubicacion(id_ubicacion, " ");
                                    Categoria categoria = new Categoria(id_categoria, " ");

                                    Producto producto = new Producto(id_producto, nombre_producto, tipo, cantidad, dimension, ubicacion, categoria);
                                    
                                    System.out.println("¿Qué cantidad de este producto comprará?" + " Sus dimensiones son: " + dimension);
                                    String cantidad_comprada_str = sc.nextLine();

                                    try
                                    {
                                        float cantidad_comprada = Float.parseFloat(cantidad_comprada_str);
                                        if(cantidad_comprada > 0)
                                        {
                                            productos.add(producto);
                                            cantidades.add(cantidad_comprada);
                                        }
                                        else
                                        {
                                            System.out.println("Cantidades incorrectas.");
                                        }
                                    }
                                    
                                    catch (Exception e)
                                    {
                                        System.out.println("\nIngresó datos incorrectos");
                                    }
                                }
                            }

                            catch (Exception e)
                            {
                                System.out.println("\nEl identificador que ingresó no existe o no corresponde a una Materia Prima o Producto de Reventa o se encuentra descontinuado");
                            }
                        }

                        else if(opcion2.equals("2"))
                        {
                            flag2 = false;
                        }

                        else
                        {
                            System.out.println("Ingresó una opción incorrecta.");
                        }
                    }

                    if(!productos.isEmpty())
                    {
                        String query_id_max = "SELECT MAX(ID_GESTION) AS MAXIMO FROM GESTIONES";
                        List<Map<String, Object>> maximos = db.executeQuery(query_id_max);
                        Map<String, Object> filas = maximos.get(0);
                        Integer id_maximo_prueba = (Integer) filas.get("MAXIMO");

                        int id_maximo; 
                        if (id_maximo_prueba == null)
                        {
                            id_maximo = 0;
                        }
                        else
                        {
                            id_maximo = id_maximo_prueba;
                        }

                        Gestion gestion = new Gestion(id_maximo + 1, "Compras", productos, cantidades, empleado);
                        gestion.InsertarFilaGestion();
                        gestion.InsertarFilaTransaccion();

                        for (Producto producto_act : productos)
                        {
                            producto_act.InsertarFila();
                        }

                    }
                    
                    else
                    {
                        System.out.println("\nNo se ingresó ningún producto.");
                    }
                }

                else if (opcion.equals("2"))
                {
                    
                }

                else if (opcion.equals("3"))
                {
                    
                }

                else if (opcion.equals("4"))
                {
                    
                }

                else
                {

                }

            }

            else if(menu.equals("8"))
            {
                flag = false;
            }
        }
    }
}