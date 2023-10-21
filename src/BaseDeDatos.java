import java.sql.Connection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;


public class BaseDeDatos {
	private List<Producto> productosEnProduccion;
    private static final String URL = "jdbc:mariadb://localhost:3306/bdfabricasillas";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "6321";
    
    public BaseDeDatos() {
        productosEnProduccion = new ArrayList<Producto>();
    }

    public void AgregarMaterial(Material material) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

            // Verificar si el material ya existe en la base de datos
            String selectSql = "SELECT cantidad FROM material WHERE codigo = ?";
            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, material.getCodigo());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // El material ya existe, actualiza la cantidad
                int cantidadExistente = resultSet.getInt("cantidad");
                int nuevaCantidad = cantidadExistente + material.getCantidad();
                String updateSql = "UPDATE material SET cantidad = ? WHERE codigo = ?";
                preparedStatement = connection.prepareStatement(updateSql);
                preparedStatement.setInt(1, nuevaCantidad);
                preparedStatement.setInt(2, material.getCodigo());
                preparedStatement.executeUpdate();
            } else {
                // El material no existe, agrega un nuevo registro
                String insertSql = "INSERT INTO material (codigo, nombre, cantidad, precio) VALUES (?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.setInt(1, material.getCodigo());
                preparedStatement.setString(2, material.getNombre());
                preparedStatement.setInt(3, material.getCantidad());
                preparedStatement.setFloat(4, material.getPrecio());
                preparedStatement.executeUpdate();
            }

            System.out.println("¡Datos registrados en la base de datos correctamente!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
   //Metodo para modificar los atributos de un material, segun el codigo ingresado por el usuario
	   
   
   
   public void ModificarMaterial() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);
	        
	        System.out.println("Ingrese el código del material que desea actualizar:");
	        int codigo = entrada.nextInt();
	        
	        // Verificar si el material existe en la base de datos
	        String selectSql = "SELECT * FROM material WHERE codigo = ?";
	        preparedStatement = connection.prepareStatement(selectSql);
	        preparedStatement.setInt(1, codigo);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // El material existe, obtener los valores actuales
	            String nombreActual = resultSet.getString("nombre");
	            int cantidadActual = resultSet.getInt("cantidad");
	            float precioActual = resultSet.getFloat("precio");

	            System.out.println("Material encontrado:");
	            System.out.println("Código: " + codigo);
	            System.out.println("Nombre actual: " + nombreActual);
	            System.out.println("Cantidad actual: " + cantidadActual);
	            System.out.println("Precio actual: " + precioActual);

	            // Solicitar al usuario los nuevos valores
	            System.out.println("Ingrese el nuevo nombre del material:");
	            entrada.nextLine();
	            String nuevoNombre = entrada.next();
	            System.out.println("Ingrese la nueva cantidad del material:");
	            entrada.nextLine();
	            int nuevaCantidad = entrada.nextInt();
	            System.out.println("Ingrese el nuevo precio precio del material:");
	            entrada.nextLine();
	            float nuevoPrecio = entrada.nextFloat();

	            // Actualizar el material con los nuevos valores si se ingresaron
	            if (!nuevoNombre.isEmpty()) {
	                nombreActual = nuevoNombre;
	            }
	            if (nuevaCantidad != 0) {
	                cantidadActual = nuevaCantidad;
	            }
	            if (nuevoPrecio != 0.0f) {
	                precioActual = nuevoPrecio;
	            }

	            // Realizar la actualización en la base de datos
	            String updateSql = "UPDATE material SET nombre = ?, cantidad = ?, precio = ? WHERE codigo = ?";
	            preparedStatement = connection.prepareStatement(updateSql);
	            preparedStatement.setString(1, nombreActual);
	            preparedStatement.setInt(2, cantidadActual);
	            preparedStatement.setFloat(3, precioActual);
	            preparedStatement.setInt(4, codigo);
	            preparedStatement.executeUpdate();

	            System.out.println("Material actualizado exitosamente.");
	        } else {
	            System.out.println("Material con código " + codigo + " no encontrado en la base de datos.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   
   public void EliminarMaterial() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);
	        
	        System.out.println("Ingrese el código del material que desea eliminar:");
	        int codigo = entrada.nextInt();
	        
	        // Verificar si el material existe en la base de datos
	        String selectSql = "SELECT * FROM material WHERE codigo = ?";
	        preparedStatement = connection.prepareStatement(selectSql);
	        preparedStatement.setInt(1, codigo);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // El material existe, eliminarlo
	            String deleteSql = "DELETE FROM material WHERE codigo = ?";
	            preparedStatement = connection.prepareStatement(deleteSql);
	            preparedStatement.setInt(1, codigo);
	            preparedStatement.executeUpdate();

	            System.out.println("Material con código " + codigo + " ha sido eliminado exitosamente.");
	        } else {
	            System.out.println("Material con código " + codigo + " no encontrado en la base de datos.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   public void VerMateriales() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null; 
	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Consulta SQL para obtener todos los materiales
	        String selectSql = "SELECT * FROM material";
	        preparedStatement = connection.prepareStatement(selectSql);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        System.out.println("Código\tNombre\t\t\tCantidad\tPrecio");

	        while (resultSet.next()) {
	            int codigo = resultSet.getInt("codigo");
	            String nombre = resultSet.getString("nombre");
	            int cantidad = resultSet.getInt("cantidad");
	            float precio = resultSet.getFloat("precio");

	            // Imprimir los datos en formato de tabla
	            System.out.printf("%d\t%-20s\t%d\t\t%.2f%n", codigo, nombre, cantidad, precio);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

   public void IniciarProduccion() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    int numeroLote = 0;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        System.out.println("Ingrese el número de lote de producción:");
	        numeroLote = entrada.nextInt();
	        entrada.nextLine();

	        boolean agregarProductos = true;
	        while (agregarProductos) {
	            int codigoProducto;
	            String descripcionProducto;
	            float precioProducto;
	            int cantidadProducto;
	            List<Material> materiales = new ArrayList<Material>();

	            System.out.println("Ingrese el código del producto:");
	            codigoProducto = entrada.nextInt();
	            entrada.nextLine();

	            System.out.println("Ingrese la descripción del producto:");
	            descripcionProducto = entrada.nextLine();

	            System.out.println("Ingrese el precio del producto:");
	            precioProducto = entrada.nextFloat();
	            entrada.nextLine();

	            System.out.println("Ingrese la cantidad a producir:");
	            cantidadProducto = entrada.nextInt();
	            entrada.nextLine();

	            boolean agregarMateriales = true;
	            while (agregarMateriales) {
	                System.out.println("Ingrese el código del material necesario para la produccón:");
	                int codigoMaterial = entrada.nextInt();
	                entrada.nextLine();
	                System.out.println("Ingrese la cantidad requerida de este material:");
	                int cantidadRequerida = entrada.nextInt();
	                entrada.nextLine();

	                // Verifica si el material existe en la base de datos
	                String selectSql = "SELECT cantidad FROM material WHERE codigo = ?";
	                preparedStatement = connection.prepareStatement(selectSql);
	                preparedStatement.setInt(1, codigoMaterial);
	                ResultSet resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    int cantidadEnInventario = resultSet.getInt("cantidad");

	                    if (cantidadEnInventario >= cantidadRequerida) {
	                        // Resta la cantidad requerida de material en el inventario
	                        String updateSql = "UPDATE material SET cantidad = cantidad - ? WHERE codigo = ?";
	                        preparedStatement = connection.prepareStatement(updateSql);
	                        preparedStatement.setInt(1, cantidadRequerida);
	                        preparedStatement.setInt(2, codigoMaterial);
	                        preparedStatement.executeUpdate();
	                        
	                        materiales.add(new Material(codigoMaterial, "", cantidadRequerida, 0)); 
	                    } else {
	                        System.out.println("No hay suficiente cantidad de material en el inventario.");
	                    }
	                } else {
	                    System.out.println("Material con código " + codigoMaterial + " no encontrado en la base de datos.");
	                }

	                System.out.print("¿Desea agregar otro material necesario para la producción? (S/s = sí, N/n = no): ");
	                char respuesta = entrada.next().charAt(0);
	                entrada.nextLine();
	                if (respuesta == 'N' || respuesta == 'n') {
	                    agregarMateriales = false;
	                }
	            }

	            System.out.print("¿Desea agregar otro producto al lote de producción? (S/s = sí, N/n = no): ");
	            char respuesta = entrada.next().charAt(0);
	            entrada.nextLine();
	            if (respuesta == 'N' || respuesta == 'n') {
	                agregarProductos = false;
	            }

	            // Después de agregar los productos, los almacenamos en la lista productosEnProduccion
	            productosEnProduccion.add(new Producto(codigoProducto, numeroLote, descripcionProducto, cantidadProducto, precioProducto, materiales));
	        }


	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}



   
   public void VerProductosEnProceso() {
	    if (productosEnProduccion.isEmpty()) {
	        System.out.println("No hay productos en proceso.");
	        return;
	    }

	    int loteActual = -1; // Inicializa un valor que no se repetirá con ningún lote real

	    for (Producto producto : productosEnProduccion) {
	        if (producto.getLote() != loteActual) {
	            // Muestra un nuevo lote
	            System.out.println("Número de lote: " + String.format("%03d", producto.getLote()));
	       
	            
	            loteActual = producto.getLote();
	        }

	        // Muestra los detalles del producto
	        System.out.printf("Código: %d\tDescripción: %s\tCantidad: %d\tPrecio: $%.2f%n",
	                          producto.getCodigo(), producto.getDescripcion(), producto.getCantidad(), producto.getPrecio());
	        System.out.println("---------------------------------------------------------------------");
	    }
	}
   
   public void FinalizarProduccion() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        System.out.println("Ingrese el número de lote que desea finalizar:");
	        int numeroLoteFinalizar = entrada.nextInt();
	        entrada.nextLine();

	        for (Producto producto : productosEnProduccion) {
	            if (producto.getLote() == numeroLoteFinalizar) {
	                // Insertamos el producto en la base de datos
	                String insertSql = "INSERT INTO producto (lote, codigo, descripcion, cantidad, precio) VALUES (?, ?, ?, ?, ?)";
	                preparedStatement = connection.prepareStatement(insertSql);
	                preparedStatement.setInt(1, producto.getLote());
	                preparedStatement.setInt(2, producto.getCodigo());
	                preparedStatement.setString(3, producto.getDescripcion());
	                preparedStatement.setInt(4, producto.getCantidad());
	                preparedStatement.setFloat(5, producto.getPrecio());
	                preparedStatement.executeUpdate();
	            }
	        }

	        System.out.println("Producción finalizada y productos guardados en la base de datos correctamente.");

	        // Eliminamos los productos del lote finalizado de la lista de productos en producción
	        productosEnProduccion.removeIf(producto -> producto.getLote() == numeroLoteFinalizar);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   
   public void ActualizarProducto() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        System.out.println("Ingrese el código del producto que desea actualizar:");
	        int codigoProducto = entrada.nextInt();
	        entrada.nextLine();

	        // Verificamos si el producto existe en la base de datos
	        String selectSql = "SELECT lote, descripcion, cantidad, precio FROM producto WHERE codigo = ?";
	        preparedStatement = connection.prepareStatement(selectSql);
	        preparedStatement.setInt(1, codigoProducto);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            //Si el producto existe, obtener los valores actuales
	            int loteActual = resultSet.getInt("lote");
	            String descripcionActual = resultSet.getString("descripcion");
	            int cantidadActual = resultSet.getInt("cantidad");
	            float precioActual = resultSet.getFloat("precio");

	            System.out.println("Producto encontrado:");
	            System.out.println("Código: " + codigoProducto);
	            System.out.println("Lote: " + loteActual);
	            System.out.println("Descripción actual: " + descripcionActual);
	            System.out.println("Cantidad actual: " + cantidadActual);
	            System.out.println("Precio actual: " + precioActual);

	            // Solicita al usuario los nuevos valores
	            System.out.println("Ingrese la nueva descripción (deje en blanco para mantener la actual):");
	            String nuevaDescripcion = entrada.nextLine();
	            System.out.println("Ingrese la nueva cantidad:");
	            int nuevaCantidad = entrada.nextInt();
	            entrada.nextLine();
	            System.out.println("Ingrese el nuevo precio:");
	            float nuevoPrecio = entrada.nextFloat();

	            
	            if (!nuevaDescripcion.isEmpty()) {
	                descripcionActual = nuevaDescripcion;
	            }
	            if (nuevaCantidad != 0) {
	                cantidadActual = nuevaCantidad;
	            }
	            if (nuevoPrecio != 0.0f) {
	                precioActual = nuevoPrecio;
	            }

	            // Realiza la actualización en la base de datos
	            String updateSql = "UPDATE producto SET descripcion = ?, cantidad = ?, precio = ? WHERE codigo = ?";
	            preparedStatement = connection.prepareStatement(updateSql);
	            preparedStatement.setString(1, descripcionActual);
	            preparedStatement.setInt(2, cantidadActual);
	            preparedStatement.setFloat(3, precioActual);
	            preparedStatement.setInt(4, codigoProducto);
	            preparedStatement.executeUpdate();

	            System.out.println("Producto actualizado exitosamente.");
	        } else {
	            System.out.println("Producto con código " + codigoProducto + " no encontrado en la base de datos.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   
   public void EliminarProducto() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        System.out.println("Ingrese el código del producto que desea eliminar:");
	        int codigoProducto = entrada.nextInt();
	        entrada.nextLine();

	        // Verifica si el producto existe en la base de datos
	        String selectSql = "SELECT lote, descripcion, cantidad, precio FROM producto WHERE codigo = ?";
	        preparedStatement = connection.prepareStatement(selectSql);
	        preparedStatement.setInt(1, codigoProducto);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // Si el producto existe, obtener los valores actuales
	            int loteActual = resultSet.getInt("lote");
	            String descripcionActual = resultSet.getString("descripcion");
	            int cantidadActual = resultSet.getInt("cantidad");
	            float precioActual = resultSet.getFloat("precio");

	            System.out.println("Producto encontrado:");
	            System.out.println("Código: " + codigoProducto);
	            System.out.println("Lote: " + loteActual);
	            System.out.println("Descripción: " + descripcionActual);
	            System.out.println("Cantidad: " + cantidadActual);
	            System.out.println("Precio: " + precioActual);

	            // Confirma con el usuario si desea eliminar el producto
	            System.out.print("¿Desea eliminar este producto? (S/s = sí, N/n = no): ");
	            char respuesta = entrada.next().charAt(0);
	            entrada.nextLine();
	            
	            if (respuesta == 'S' || respuesta == 's') {
	                // Elimina el producto de la base de datos
	                String deleteSql = "DELETE FROM producto WHERE codigo = ?";
	                preparedStatement = connection.prepareStatement(deleteSql);
	                preparedStatement.setInt(1, codigoProducto);
	                preparedStatement.executeUpdate();
	                System.out.println("Producto eliminado exitosamente.");
	            } else {
	                System.out.println("Operación cancelada, el producto no se eliminó.");
	            }
	        } else {
	            System.out.println("Producto con código " + codigoProducto + " no encontrado en la base de datos.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

   
   public void VerProductos() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Consulta SQL para obtener todos los productos
	        String selectSql = "SELECT lote, codigo, descripcion, cantidad, precio FROM producto";
	        preparedStatement = connection.prepareStatement(selectSql);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        System.out.println("Lista de Productos:");
	        System.out.println("Número de lote\tCódigo\tDescripción\tCantidad\tPrecio");

	        while (resultSet.next()) {
	            int lote = resultSet.getInt("lote");
	            int codigo = resultSet.getInt("codigo");
	            String descripcion = resultSet.getString("descripcion");
	            int cantidad = resultSet.getInt("cantidad");
	            float precio = resultSet.getFloat("precio");

	            // Imprimir los datos en formato de lista
	            System.out.printf("%04d\t\t%d\t%s\t%d\t$%.2f%n", lote, codigo, descripcion, cantidad, precio);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

   public void RegistrarCliente(Cliente cliente) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Consulta SQL para insertar un nuevo cliente en la tabla
	        String insertSql = "INSERT INTO cliente (nit, nombre, telefono) VALUES (?, ?, ?)";
	        preparedStatement = connection.prepareStatement(insertSql);
	        preparedStatement.setString(1, cliente.getNit());
	        preparedStatement.setString(2, cliente.getNombre());
	        preparedStatement.setString(3, cliente.getTelefono());

	        // Ejecutar la consulta para insertar el cliente
	        int filasAfectadas = preparedStatement.executeUpdate();

	        if (filasAfectadas > 0) {
	            System.out.println("Cliente registrado exitosamente en la base de datos.");
	        } else {
	            System.out.println("Error al registrar el cliente en la base de datos.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   public void ActualizarCliente() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        System.out.println("Ingrese el NIT del cliente que desea actualizar:");
	        String nitBuscado = entrada.nextLine();

	        // Verificar si el cliente existe en la base de datos
	        String selectSql = "SELECT * FROM cliente WHERE nit = ?";
	        preparedStatement = connection.prepareStatement(selectSql);
	        preparedStatement.setString(1, nitBuscado);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // El cliente existe, mostrar la información actual
	            String nombreActual = resultSet.getString("nombre");
	            String telefonoActual = resultSet.getString("telefono");

	            System.out.println("Cliente encontrado:");
	            System.out.println("NIT: " + nitBuscado);
	            System.out.println("Nombre actual: " + nombreActual);
	            System.out.println("Teléfono actual: " + telefonoActual);

	            // Solicitar al usuario los nuevos valores
	            System.out.println("Ingrese el nuevo nombre (deje en blanco para mantener el actual):");
	            String nuevoNombre = entrada.nextLine();
	            System.out.println("Ingrese el nuevo teléfono (deje en blanco para mantener el actual):");
	            String nuevoTelefono = entrada.nextLine();

	            // Actualizar el cliente con los nuevos valores si se ingresaron
	            if (!nuevoNombre.isEmpty() || !nuevoTelefono.isEmpty()) {
	                String updateSql = "UPDATE cliente SET nombre = ?, telefono = ? WHERE nit = ?";
	                preparedStatement = connection.prepareStatement(updateSql);
	                preparedStatement.setString(1, nuevoNombre.isEmpty() ? nombreActual : nuevoNombre);
	                preparedStatement.setString(2, nuevoTelefono.isEmpty() ? telefonoActual : nuevoTelefono);
	                preparedStatement.setString(3, nitBuscado);
	                preparedStatement.executeUpdate();

	                System.out.println("Cliente actualizado exitosamente.");
	            } else {
	                System.out.println("No se realizaron modificaciones en los datos del cliente.");
	            }
	        } else {
	            System.out.println("Cliente con NIT " + nitBuscado + " no encontrado en la base de datos.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   
   public void EliminarCliente() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        System.out.println("Ingrese el NIT del cliente que desea eliminar:");
	        String nitBuscado = entrada.nextLine();

	        // Verificar si el cliente existe en la base de datos
	        String selectSql = "SELECT * FROM cliente WHERE nit = ?";
	        preparedStatement = connection.prepareStatement(selectSql);
	        preparedStatement.setString(1, nitBuscado);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // El cliente existe, mostrar la información
	            String nombreCliente = resultSet.getString("nombre");
	            String telefonoCliente = resultSet.getString("telefono");

	            System.out.println("Cliente encontrado:");
	            System.out.println("NIT: " + nitBuscado);
	            System.out.println("Nombre: " + nombreCliente);
	            System.out.println("Teléfono: " + telefonoCliente);

	            // Solicitar confirmación para eliminar el cliente
	            System.out.print("¿Desea eliminar este cliente? (S/s = sí, N/n = no): ");
	            char respuesta = entrada.next().charAt(0);

	            if (respuesta == 'S' || respuesta == 's') {
	                // Eliminar el cliente
	                String deleteSql = "DELETE FROM cliente WHERE nit = ?";
	                preparedStatement = connection.prepareStatement(deleteSql);
	                preparedStatement.setString(1, nitBuscado);
	                preparedStatement.executeUpdate();

	                System.out.println("Cliente con NIT " + nitBuscado + " ha sido eliminado exitosamente.");
	            } else {
	                System.out.println("El cliente no ha sido eliminado.");
	            }
	        } else {
	            System.out.println("Cliente con NIT " + nitBuscado + " no encontrado en la base de datos.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   public void VerClientes() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Consulta SQL para obtener todos los clientes
	        String selectSql = "SELECT * FROM cliente";
	        preparedStatement = connection.prepareStatement(selectSql);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        System.out.println("NIT\tNombre\t\tTeléfono");

	        while (resultSet.next()) {
	            String nit = resultSet.getString("nit");
	            String nombre = resultSet.getString("nombre");
	            String telefono = resultSet.getString("telefono");

	            // Imprimir los datos en formato de lista
	            System.out.printf("%s\t%-20s\t%s%n", nit, nombre, telefono);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   
   public void RegistrarOrden() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        // Solicitar el número de orden
	        System.out.println("Ingrese el número de orden:");
	        int numeroOrden = entrada.nextInt();
	        entrada.nextLine();

	        // Solicitar la fecha de la orden
	        System.out.println("Ingrese la fecha de la orden (en formato YYYY-MM-DD):");
	        String fechaOrden = entrada.nextLine();

	        // Solicitar el NIT del cliente
	        System.out.println("Ingrese el NIT del cliente:");
	        String nitCliente = entrada.nextLine();

	        // Verificar si el cliente existe en la base de datos
	        String selectClienteSql = "SELECT nombre FROM cliente WHERE nit = ?";
	        preparedStatement = connection.prepareStatement(selectClienteSql);
	        preparedStatement.setString(1, nitCliente);
	        ResultSet clienteResult = preparedStatement.executeQuery();

	        if (clienteResult.next()) {
	            String nombreCliente = clienteResult.getString("nombre");
	            System.out.println("Cliente encontrado: " + nombreCliente);

	            List<Producto> productosOrden = new ArrayList<>();
	            boolean agregarProductos = true;

	            // Verificar si el número de orden ya existe en la tabla "orden"
	            String selectOrdenSql = "SELECT numero FROM orden WHERE numero = ?";
	            preparedStatement = connection.prepareStatement(selectOrdenSql);
	            preparedStatement.setInt(1, numeroOrden);
	            ResultSet ordenResult = preparedStatement.executeQuery();

	            if (!ordenResult.next()) {
	                // Si el número de orden no existe, créalo en la tabla "orden"
	                String insertOrdenSql = "INSERT INTO orden (numero, fecha, nit_cliente, total) VALUES (?, ?, ?, ?)";
	                preparedStatement = connection.prepareStatement(insertOrdenSql);
	                preparedStatement.setInt(1, numeroOrden);
	                preparedStatement.setString(2, fechaOrden);
	                preparedStatement.setString(3, nitCliente);
	                preparedStatement.setFloat(4, 0.0f);  // Inicializar el total en 0
	                preparedStatement.executeUpdate();
	            }

	            while (agregarProductos) {
	                // Mostrar los productos disponibles en la tabla producto
	                System.out.println("Productos disponibles en la tabla producto:");
	                // Consulta SQL para obtener todos los productos
	                String selectProductosSql = "SELECT codigo, descripcion, cantidad, precio FROM producto";
	                preparedStatement = connection.prepareStatement(selectProductosSql);
	                ResultSet productosResult = preparedStatement.executeQuery();
	                System.out.println("Código\tDescripción\tCantidad\tPrecio");

	                while (productosResult.next()) {
	                    int codigoProducto = productosResult.getInt("codigo");
	                    String descripcion = productosResult.getString("descripcion");
	                    int cantidad = productosResult.getInt("cantidad");
	                    float precio = productosResult.getFloat("precio");

	                    System.out.printf("%d\t%s\t%d\t$%.2f%n", codigoProducto, descripcion, cantidad, precio);
	                }

	                // Solicitar el código del producto
	                System.out.println("Ingrese el código del producto que desea agregar a la orden:");
	                int codigoProducto = entrada.nextInt();
	                entrada.nextLine();

	                // Solicitar la cantidad
	                System.out.println("Ingrese la cantidad del producto:");
	                int cantidadProducto = entrada.nextInt();
	                entrada.nextLine();

	                // Calcular el total para este producto
	                float totalProducto = 0.0f;

	                String selectProductoSql = "SELECT precio, cantidad FROM producto WHERE codigo = ?";
	                preparedStatement = connection.prepareStatement(selectProductoSql);
	                preparedStatement.setInt(1, codigoProducto);
	                ResultSet productoResult = preparedStatement.executeQuery();

	                if (productoResult.next()) {
	                    float precioProducto = productoResult.getFloat("precio");
	                    int cantidadEnInventario = productoResult.getInt("cantidad");

	                    if (cantidadEnInventario >= cantidadProducto) {
	                        totalProducto = precioProducto * cantidadProducto;  // Calcular el precio total del producto
	                        // Actualizar la cantidad en la tabla producto restando la cantidad seleccionada
	                        String updateCantidadProductoSql = "UPDATE producto SET cantidad = cantidad - ? WHERE codigo = ?";
	                        preparedStatement = connection.prepareStatement(updateCantidadProductoSql);
	                        preparedStatement.setInt(1, cantidadProducto);
	                        preparedStatement.setInt(2, codigoProducto);
	                        preparedStatement.executeUpdate();
	                    } else {
	                        System.out.println("No hay suficiente cantidad de producto en el inventario.");
	                        continue;  // Continuar al siguiente producto
	                    }
	                } else {
	                    System.out.println("Producto con código " + codigoProducto + " no encontrado en la base de datos.");
	                    continue;  // Continuar al siguiente producto
	                }

	                // Agregar el producto a la lista de productos de la orden
	                productosOrden.add(new Producto(codigoProducto, 0, "", cantidadProducto, totalProducto, null));

	                // Insertar el detalle de la orden en la tabla "detalle_orden"
	                String insertDetalleOrdenSql = "INSERT INTO detalle_orden (numero_orden, codigo_producto, cantidad) VALUES (?, ?, ?)";
	                preparedStatement = connection.prepareStatement(insertDetalleOrdenSql);
	                preparedStatement.setInt(1, numeroOrden);
	                preparedStatement.setInt(2, codigoProducto);
	                preparedStatement.setInt(3, cantidadProducto);
	                preparedStatement.executeUpdate();

	                System.out.print("¿Desea agregar otro producto a la orden? (S/s = sí, N/n = no): ");
	                char respuesta = entrada.next().charAt(0);
	                entrada.nextLine();
	                if (respuesta == 'N' || respuesta == 'n') {
	                    agregarProductos = false;
	                }
	            }

	            // Calcular el total de la orden sumando los precios unitarios de los productos multiplicados por la cantidad
	            float totalOrden = 0.0f;
	            for (Producto producto : productosOrden) {
	                totalOrden += producto.getPrecio();
	            }

	            // Actualizar el total de la orden en la tabla "orden"
	            String updateOrdenSql = "UPDATE orden SET total = ? WHERE numero = ?";
	            preparedStatement = connection.prepareStatement(updateOrdenSql);
	            preparedStatement.setFloat(1, totalOrden);
	            preparedStatement.setInt(2, numeroOrden);
	            preparedStatement.executeUpdate();

	            System.out.println("Orden registrada exitosamente.");

	        } else {
	            System.out.println("Cliente con NIT " + nitCliente + " no encontrado en la base de datos.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

   
   
   public void VerOrdenes() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Consulta SQL para obtener todas las órdenes con sus detalles
	        String selectOrdenesSql = "SELECT o.numero, o.fecha, o.nit_cliente, c.nombre, c.telefono " +
	                "FROM orden o " +
	                "JOIN cliente c ON o.nit_cliente = c.nit";
	        preparedStatement = connection.prepareStatement(selectOrdenesSql);
	        ResultSet ordenesResult = preparedStatement.executeQuery();

	        while (ordenesResult.next()) {
	            // Obtener los datos de la orden y el cliente
	            int numeroOrden = ordenesResult.getInt("numero");
	            String fechaOrden = ordenesResult.getString("fecha");
	            String nitCliente = ordenesResult.getString("nit_cliente");
	            String nombreCliente = ordenesResult.getString("nombre");
	            String telefonoCliente = ordenesResult.getString("telefono");

	            // Imprimir la información de la orden y el cliente
	            System.out.println("Número de orden: " + numeroOrden);
	            System.out.println("Fecha: " + fechaOrden);
	            System.out.println("NIT: " + nitCliente);
	            System.out.println("Nombre del Cliente: " + nombreCliente);
	            System.out.println("Teléfono del Cliente: " + telefonoCliente);
	            System.out.println("-------------------------");

	            // Consulta SQL para obtener los productos asociados a la orden
	            String selectProductosSql = "SELECT p.codigo, p.descripcion, do.cantidad, p.precio " +
	                    "FROM detalle_orden do " +
	                    "JOIN producto p ON do.codigo_producto = p.codigo " +
	                    "WHERE do.numero_orden = ?";
	            preparedStatement = connection.prepareStatement(selectProductosSql);
	            preparedStatement.setInt(1, numeroOrden);
	            ResultSet productosResult = preparedStatement.executeQuery();

	            while (productosResult.next()) {
	                int codigoProducto = productosResult.getInt("codigo");
	                String descripcionProducto = productosResult.getString("descripcion");
	                int cantidadProducto = productosResult.getInt("cantidad");
	                float precioProducto = productosResult.getFloat("precio");

	                // Imprimir los detalles del producto
	                System.out.printf("Código %d  Descripción: %s  Cantidad: %d  Precio: $%.2f%n",
	                        codigoProducto, descripcionProducto, cantidadProducto, precioProducto);
	            }

	            // Consultar y mostrar el precio total de la orden
	            String selectTotalOrdenSql = "SELECT total FROM orden WHERE numero = ?";
	            preparedStatement = connection.prepareStatement(selectTotalOrdenSql);
	            preparedStatement.setInt(1, numeroOrden);
	            ResultSet totalOrdenResult = preparedStatement.executeQuery();

	            if (totalOrdenResult.next()) {
	                float totalOrden = totalOrdenResult.getFloat("total");
	                System.out.println("Precio total de la orden: $" + totalOrden);
	            }

	            System.out.println(); // Separador entre órdenes
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   
   public void EliminarOrden() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        // Solicitar el número de orden a eliminar
	        System.out.println("Ingrese el número de orden que desea eliminar:");
	        int numeroOrden = entrada.nextInt();
	        entrada.nextLine();

	        // Verificar si el número de orden existe en la tabla "orden"
	        String selectOrdenSql = "SELECT numero FROM orden WHERE numero = ?";
	        preparedStatement = connection.prepareStatement(selectOrdenSql);
	        preparedStatement.setInt(1, numeroOrden);
	        ResultSet ordenResult = preparedStatement.executeQuery();

	        if (ordenResult.next()) {
	            // Mostrar la orden y solicitar confirmación
	            System.out.println("Orden encontrada. Detalles de la orden:");

	            // Obtener los detalles de la orden
	            String selectDetalleOrdenSql = "SELECT codigo_producto, cantidad FROM detalle_orden WHERE numero_orden = ?";
	            preparedStatement = connection.prepareStatement(selectDetalleOrdenSql);
	            preparedStatement.setInt(1, numeroOrden);
	            ResultSet detalleOrdenResult = preparedStatement.executeQuery();

	            while (detalleOrdenResult.next()) {
	                int codigoProducto = detalleOrdenResult.getInt("codigo_producto");
	                int cantidad = detalleOrdenResult.getInt("cantidad");
	                System.out.println("Código Producto: " + codigoProducto + " Cantidad: " + cantidad);
	            }

	            System.out.print("¿Desea eliminar esta orden? (S/s = sí, N/n = no): ");
	            char respuesta = entrada.next().charAt(0);
	            entrada.nextLine();

	            if (respuesta == 'S' || respuesta == 's') {
	                // Eliminar los detalles de la orden
	                String deleteDetalleOrdenSql = "DELETE FROM detalle_orden WHERE numero_orden = ?";
	                preparedStatement = connection.prepareStatement(deleteDetalleOrdenSql);
	                preparedStatement.setInt(1, numeroOrden);
	                preparedStatement.executeUpdate();

	                // Eliminar la orden
	                String deleteOrdenSql = "DELETE FROM orden WHERE numero = ?";
	                preparedStatement = connection.prepareStatement(deleteOrdenSql);
	                preparedStatement.setInt(1, numeroOrden);
	                preparedStatement.executeUpdate();

	                System.out.println("Orden eliminada exitosamente.");
	            } else {
	                System.out.println("Operación de eliminación cancelada.");
	            }
	        } else {
	            System.out.println("La orden con el número " + numeroOrden + " no se encontró en la base de datos.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   
   
   public Factura generarFactura(int numeroOrden) {
	// Generar el número de serie
	   int serie = obtenerSiguienteNumeroSerie();


       // Generar el número de factura
       int numeroFactura = obtenerSiguienteNumeroFactura();

       // Obtener la fecha actual
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date date = new Date();
       String fecha = dateFormat.format(date);

       // Obtener los datos de la orden y su cliente
       Orden orden = obtenerOrden(numeroOrden);
       Cliente cliente = obtenerCliente(orden.getNit());
       List<Producto> productos = obtenerProductosDeOrden(numeroOrden);

       // Crear la factura
       Factura factura = new Factura(serie, numeroFactura, fecha);

       // Generar el contenido de la factura
       StringBuilder contenidoFactura = new StringBuilder();
       contenidoFactura.append("Número de Factura: ").append(factura.getSerie()).append("-").append(factura.getNumero()).append("\n");
       contenidoFactura.append("Fecha de Factura: ").append(factura.getFecha()).append("\n\n");
       contenidoFactura.append("Cliente:\n");
       contenidoFactura.append("NIT: ").append(cliente.getNit()).append("\n");
       contenidoFactura.append("Nombre: ").append(cliente.getNombre()).append("\n");
       contenidoFactura.append("Teléfono: ").append(cliente.getTelefono()).append("\n\n");
       contenidoFactura.append("Detalle de la Orden:\n");
       for (Producto producto : productos) {
           contenidoFactura.append("Código: ").append(producto.getCodigo()).append("\n");
           contenidoFactura.append("Descripción: ").append(producto.getDescripcion()).append("\n");
           contenidoFactura.append("Cantidad: ").append(producto.getCantidad()).append("\n");
           contenidoFactura.append("Precio Unitario: $").append(producto.getPrecio()).append("\n");
           contenidoFactura.append("Subtotal: $").append(producto.getCantidad() * producto.getPrecio()).append("\n\n");
       }
       contenidoFactura.append("Total: $").append(calcularTotalFactura(productos)).append("\n");

       // Guardar la factura en la base de datos o en un archivo, como lo prefieras
       guardarFacturaEnBaseDeDatos(factura, contenidoFactura.toString());

       // Retornar la factura generada
       return factura;
   }
   
   private int obtenerSiguienteNumeroSerie() {
	    Random random = new Random();
	    return random.nextInt(100000); // Modifica el rango según tus necesidades
	}

	private int obtenerSiguienteNumeroFactura() {
	    Random random = new Random();
	    return random.nextInt(10000); // Modifica el rango según tus necesidades
	}

   private Orden obtenerOrden(int numeroOrden) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Orden orden = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Consulta SQL para obtener los datos de la orden por número de orden
	        String sql = "SELECT * FROM orden WHERE numero = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, numeroOrden);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // Obtener los datos de la orden
	            int numero = resultSet.getInt("numero");
	            String fecha = resultSet.getString("fecha");
	            String nitCliente = resultSet.getString("nit_cliente");
	            float total = resultSet.getFloat("total");

	            // Crear un objeto Orden con los datos
	            orden = new Orden(numero, fecha, nitCliente, total);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        //Maneja excepciones o errores de conexión con la base de datos
	    } finally {
	        //Cerrar recursos en el orden inverso de apertura
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return orden;
	}

   
   private Cliente obtenerCliente(String nitCliente) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Cliente cliente = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Consulta SQL para obtener los datos del cliente por NIT
	        String sql = "SELECT * FROM cliente WHERE nit = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, nitCliente);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // Obtener los datos del cliente
	            String nit = resultSet.getString("nit");
	            String nombre = resultSet.getString("nombre");
	            String telefono = resultSet.getString("telefono");

	            // Crear un objeto Cliente con los datos
	            cliente = new Cliente(nit, nombre, telefono);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Maneja excepciones o errores de conexión con la base de datos
	    } finally {
	        // Cerrar recursos en el orden inverso de apertura
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return cliente;
	}
   private List<Producto> obtenerProductosDeOrden(int numeroOrden) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Producto> productos = new ArrayList<>();

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Consulta SQL para obtener los productos asociados a una orden
	        String sql = "SELECT p.codigo, p.descripcion, p.cantidad, p.precio " +
	                     "FROM producto p " +
	                     "INNER JOIN detalle_orden d ON p.codigo = d.codigo_producto " +
	                     "WHERE d.numero_orden = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, numeroOrden);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            // Obtener los datos del producto
	            int codigo = resultSet.getInt("codigo");
	            String descripcion = resultSet.getString("descripcion");
	            int cantidad = resultSet.getInt("cantidad");
	            float precio = resultSet.getFloat("precio");

	            // Crear un objeto Producto con los datos y agregarlo a la lista
	            productos.add(new Producto(codigo, 0, descripcion, cantidad, precio, null));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Maneja excepciones o errores de conexión con la base de datos
	    } finally {
	        // Cerrar recursos en el orden inverso de apertura
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return productos;
	}

   
   private float calcularTotalFactura(List<Producto> productos) {
	    float total = 0;
	    for (Producto producto : productos) {
	        total += producto.getCantidad() * producto.getPrecio();
	    }
	    return total;
	}

   private void guardarFacturaEnBaseDeDatos(Factura factura, String contenido) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

	        // Insertar la factura en la base de datos
	        String insertFacturaSql = "INSERT INTO factura (serie, numero, fecha, contenido) VALUES (?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(insertFacturaSql);
	        preparedStatement.setInt(1, factura.getSerie());
	        preparedStatement.setInt(2, factura.getNumero());
	        preparedStatement.setString(3, factura.getFecha());
	        preparedStatement.setString(4, contenido); // El contenido de la factura

	        preparedStatement.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
   

   public void EntregarProducto() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        Scanner entrada = new Scanner(System.in);

	        // Solicitar el número de orden
	        System.out.println("Ingrese el número de orden a entregar:");
	        int numeroOrden = entrada.nextInt();
	        entrada.nextLine();

	        // Verificar si la orden existe en la base de datos
	        String selectOrdenSql = "SELECT numero, fecha, nit_cliente FROM orden WHERE numero = ?";
	        preparedStatement = connection.prepareStatement(selectOrdenSql);
	        preparedStatement.setInt(1, numeroOrden);
	        ResultSet ordenResult = preparedStatement.executeQuery();

	        if (ordenResult.next()) {
	            int numero = ordenResult.getInt("numero");
	            String fecha = ordenResult.getString("fecha");
	            String nitCliente = ordenResult.getString("nit_cliente");

	            // Obtener el nombre del cliente
	            String selectClienteSql = "SELECT nombre FROM cliente WHERE nit = ?";
	            preparedStatement = connection.prepareStatement(selectClienteSql);
	            preparedStatement.setString(1, nitCliente);
	            ResultSet clienteResult = preparedStatement.executeQuery();

	            String nombreCliente = "";
	            if (clienteResult.next()) {
	                nombreCliente = clienteResult.getString("nombre");
	            }

	            // Obtener y mostrar los productos asociados a esta orden
	            String selectProductosOrdenSql = "SELECT p.codigo, p.descripcion, do.cantidad, p.precio FROM producto p " +
	                                             "INNER JOIN detalle_orden do ON p.codigo = do.codigo_producto " +
	                                             "WHERE do.numero_orden = ?";
	            preparedStatement = connection.prepareStatement(selectProductosOrdenSql);
	            preparedStatement.setInt(1, numero);
	            ResultSet productosOrdenResult = preparedStatement.executeQuery();

	            System.out.println("Detalle de la orden:");
	            System.out.println("Número de orden: " + numero);
	            System.out.println("Fecha de la orden: " + fecha);
	            System.out.println("NIT del cliente: " + nitCliente);
	            System.out.println("Nombre del cliente: " + nombreCliente);
	            System.out.println("Productos de la orden:");
	            System.out.println("Código\tDescripción\tCantidad\tPrecio");

	            while (productosOrdenResult.next()) {
	                int codigoProducto = productosOrdenResult.getInt("codigo");
	                String descripcion = productosOrdenResult.getString("descripcion");
	                int cantidad = productosOrdenResult.getInt("cantidad");
	                float precio = productosOrdenResult.getFloat("precio");

	                System.out.printf("%d\t%s\t%d\t$%.2f%n", codigoProducto, descripcion, cantidad, precio);
	            }

	            // Eliminar las filas en detalle_orden relacionadas con esta orden
	            String deleteDetalleOrdenSql = "DELETE FROM detalle_orden WHERE numero_orden = ?";
	            preparedStatement = connection.prepareStatement(deleteDetalleOrdenSql);
	            preparedStatement.setInt(1, numeroOrden);
	            preparedStatement.executeUpdate();

	            // Eliminar la fila en la tabla orden
	            String deleteOrdenSql = "DELETE FROM orden WHERE numero = ?";
	            preparedStatement = connection.prepareStatement(deleteOrdenSql);
	            preparedStatement.setInt(1, numeroOrden);
	            preparedStatement.executeUpdate();

	            System.out.println("La orden " + numero + " fue FACTURADA Y ENTREGADA correctamente al cliente " + nitCliente + " " + nombreCliente + " y ha sido eliminada de la base de datos.");
	        } else {
	            System.out.println("La orden con número " + numeroOrden + " no fue encontrada en la base de datos.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}







   
   
   
   

}
