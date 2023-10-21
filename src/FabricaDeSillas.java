import java.util.*;

public class FabricaDeSillas {

	public static void main(String[] args) {
		BaseDeDatos base = new BaseDeDatos();
		
        Scanner entrada = new Scanner(System.in);
        int opcion = 0;
        int salir = 0;
        while (salir != 1){
            Menu();
            try{
                opcion = entrada.nextInt();               
            }catch(Exception e){
                System.out.println("Opcion no valida");
            }
            switch(opcion){
            	case 1:
            		boolean enSubMenu = true;
                    while (enSubMenu) {
                        SubMenu();
                        try {
                            int subOpcion = entrada.nextInt();
                            entrada.nextLine();
                            switch (subOpcion) {
                                case 1:
				            		do {
				            			System.out.println("\n\tREGISTRAR COMPRAS\n");
				                        System.out.println("Ingrese el codigo del material");
				                        int cod = entrada.nextInt();
				                        entrada.nextLine();
				                        System.out.println("Ingrese el nombre del material");
				                        String nom = entrada.nextLine();
				                        System.out.println("Ingrese la cantidad del material");
				                        int cant = entrada.nextInt();
				                        System.out.println("Ingrese el precio del material");
				                        float pre = entrada.nextFloat();
				
				                        Material material = new Material(cod, nom, cant, pre);
				                        base.AgregarMaterial(material);
				
				                        System.out.print("¿Desea registrar otro material? (S/s = sí, N/n = no): ");
				                        char respuesta = entrada.next().charAt(0);
				                        if (respuesta == 'N' || respuesta == 'n') {
				                            break; // Sal del bucle si la respuesta es 'N' o 'n'
				                        }
				                    } while (true);
				            		break;
                                case 2:
                                	System.out.println("\n\tACTUALIZAR MATERIAL\n");
                                	base.ModificarMaterial();
                                	break;
                                	
                                case 3:
                                	System.out.println("\n\tELIMINAR MATERIAL\n");
                                	base.EliminarMaterial();
                                	break;
                                case 4:
                                	System.out.println("\n\tINVENTARIO DE MATERIALES\n");
                                	base.VerMateriales();
                                	break;
                                case 5:
                                    enSubMenu = false; // Salir del submenú y regresar al menú principal
                                    break;
                            }} catch (Exception e) {
                                System.out.println("Opción no válida en el submenú...");
                                entrada.nextLine();
                            }
                    }
                        
                    break;
                case 2:
                	//iniciar la produccion de las sillas
            		boolean enSubMenuProduccion = true;
                    while (enSubMenuProduccion) {
                        SubMenuProduccion();
                        try {
                            int subOpcion = entrada.nextInt();
                            entrada.nextLine();
                            switch (subOpcion) {
                                case 1:
                                	System.out.println("\n\tINICIAR PRODUCCION\n");
                                	base.IniciarProduccion();
				            		break;
                                case 2:
                                	System.out.println("\n\tPRODUCTOS EN PROCESO\n");
                                	base.VerProductosEnProceso();
                                	
                                	break;
                                	
                                case 3:
                                	System.out.println("\n\tTERMINAR PRODUCCION\n");
                                	base.FinalizarProduccion();
                                	
                                	break;
                                case 4:
                                	System.out.println("\n\tACTUALIZAR PRODUCTO\n");
                                	base.ActualizarProducto();
                                	
                                	break;
                                case 5:
                                	System.out.println("\n\tELIMINAR PRODUCTO\n");
                                	base.EliminarProducto();
                                	
                                	break;
                                case 6:
                                	System.out.println("\n\tINVENTARIO DE PRODUCTOS TERMINADOS\n");
                                	base.VerProductos();
                                	
                                	break;
                                case 7:
                                    enSubMenuProduccion = false; // Salir del submenú y regresar al menú principal
                                    break;
                            }} catch (Exception e) {
                                System.out.println("Opción no válida...");
                                entrada.nextLine();
                            }
                    }
                    
                    break;
                case 3:
            		boolean enSubMenuClientes = true;
                    while (enSubMenuClientes) {
                        SubMenuClientes();
                        try {
                            int subOpcion = entrada.nextInt();
                            entrada.nextLine();
                            switch (subOpcion) {
                                case 1:
                                	System.out.println("\n\tREGISTRAR CLIENTE\n");
				            		do {
				                        System.out.println("Ingrese el nit del cliente");
				                        String nit = entrada.nextLine();
				                        //entrada.nextLine();
				                        System.out.println("Ingrese el nombre del cliente");
				                        String nom = entrada.nextLine();
				                        System.out.println("Ingrese el numero de telefono del cliente");
				                        String tel = entrada.nextLine();

				
				                        Cliente cliente = new Cliente(nit, nom, tel);
				                        base.RegistrarCliente(cliente);
				
				                        System.out.print("¿Desea registrar otro cliente? (S/s = sí, N/n = no): ");
				                        char respuesta = entrada.next().charAt(0);
				                        entrada.nextLine();
				                        if (respuesta == 'N' || respuesta == 'n') {
				                            break; // Sal del bucle si la respuesta es 'N' o 'n'
				                        }
				                    } while (true);
				            		break;
                                case 2:
                                	System.out.println("\n\tACTUALIZAR CLIENTE\n");
                                	base.ActualizarCliente();
                                	break;
                                	
                                case 3:
                                	System.out.println("\n\tELIMINAR CLIENTE\n");
                                	base.EliminarCliente();
                                	break;
                                case 4:
                                	System.out.println("\n\tCLIENTES REGISTRADOS\n");
                                	base.VerClientes();
                                	break;
                                case 5: 
                                    enSubMenuClientes = false; // Salir del submenú y regresar al menú principal
                                    break;
                            }} catch (Exception e) {
                                System.out.println("Opción no válida...");
                                entrada.nextLine();
                            }
                    }

                    break;
                case 4:
                	boolean enSubMenuVentas = true;
                    while (enSubMenuVentas) {
                        SubMenuVentas();
                        try {
                            int subOpcion = entrada.nextInt();
                            entrada.nextLine();
                            switch (subOpcion) {
                                case 1:
                                	base.RegistrarOrden();	
                                	
				            		break;
                                case 2:
                                	base.EliminarOrden();
                                	
                                	break;
                                	
                                case 3:
                                	base.VerOrdenes();
                                	
                                	break;
                                case 4: 
                                    enSubMenuVentas = false; // Salir del submenú y regresar al menú principal
                                    break;
                            }} catch (Exception e) {
                                System.out.println("Opción no válida...");
                                entrada.nextLine();
                            }
                    }


                    
                    break;
                case 5:
                	
                	boolean enSubMenuFacturacion = true;
                    while (enSubMenuFacturacion) {
                        SubMenuFacturacion();
                        try {
                            int subOpcion = entrada.nextInt();
                            entrada.nextLine();
                            switch (subOpcion) {
                                case 1:
                                	
                                    // Crear una instancia de la clase BaseDeDatos (reemplaza con el nombre correcto de tu clase)
                                    BaseDeDatos baseDeDatos = new BaseDeDatos();  
                                    
                                    //Scanner entrada = new Scanner(System.in);
                                    System.out.print("Ingrese el número de orden a facturar: ");
                                    int numeroOrden = entrada.nextInt();
                                    
                                    
                                    // Llamar al método para generar la factura (reemplaza con el nombre correcto del método)
                                    try {
                                        Factura factura = baseDeDatos.generarFactura(numeroOrden);
                                        System.out.println("Factura generada:");
                                        System.out.println("Número de Factura: " + factura.getSerie() + "-" + factura.getNumero());
                                        System.out.println("Fecha de Factura: " + factura.getFecha());
                                        // ... Agrega más detalles de la factura según tus necesidades
                                    } catch (Exception e) {
                                        // Captura cualquier excepción y maneja el error aquí
                                        System.err.println("Error al generar la factura: " + e.getMessage());
                                        // Aquí puedes registrar el error en un archivo de registro o realizar otras acciones necesarias
                                    }
                                	
				            		break;
                                case 2:
                                	base.EntregarProducto();
                                	
                                	break;
                                case 3: 
                                    enSubMenuFacturacion = false; // Salir del submenú y regresar al menú principal
                                    break;
                            }} catch (Exception e) {
                                System.out.println("Opción no válida...");
                                entrada.nextLine();
                            }
                    }
                    break;
                case 6:
                    salir = 1;
                    break;
            }
        }
    }
        
    public static void Menu() {
        System.out.println("\n\tMENU PRINCIPAL\n");
        System.out.println("1. Materiales");
        System.out.println("2. Producción");
        System.out.println("3. Clientes");
        System.out.println("4. Ventas");
        System.out.println("5. Facturación");
        System.out.println("6. Salir");
        System.out.println("\nIngresa Una Opción...");

    }
    
    
    public static void SubMenu() {//Menu de materiales en case 1
    	System.out.println("\n\tMATERIALES\n");
    	System.out.println("1. Registrar Materiales");
        System.out.println("2. Actualizar Material");
        System.out.println("3. Eliminar Material");
        System.out.println("4. Ver Inventario De Materiales");
        System.out.println("5. Regresar Al Menu Principal");
        System.out.println("\nIngresa Una Opción...");
    }
    public static void SubMenuClientes() {//Menu de materiales en case 1
    	System.out.println("\n\tCLIENTES\n");
    	System.out.println("1. Registrar Clientes");
        System.out.println("2. Actualizar Cliente");
        System.out.println("3. Eliminar Clientes");
        System.out.println("4. Ver Clientes");
        System.out.println("5. Regresar Al Menu Principal");
        System.out.println("\nIngresa Una Opción...");
    }
    
    public static void SubMenuFacturacion() {//Menu de materiales en case 1
    	System.out.println("\n\tFACTURACIÓN Y ENTREGA\n");
    	System.out.println("1. Facturar");
        System.out.println("2. Entregar");
        System.out.println("3. Regresar Al Menu Principal");
    }
    
    public static void SubMenuVentas() {//Menu de materiales en case 1
    	System.out.println("\n\tVENTAS\n");
    	System.out.println("1. Registrar Orden");
        //System.out.println("2. Modificiar Cliente");
        System.out.println("2. Eliminar Orden");
        System.out.println("3. Ver Ordenes");
        System.out.println("4. Regresar Al Menu Principal");
        System.out.println("\nIngresa Una Opción...");
    }
    
    public static void SubMenuProduccion() {//Menu de materiales en case 1
    	System.out.println("\n\tPRODUCCIÓN\n");
    	System.out.println("1. Iniciar Produccion");
        System.out.println("2. Ver Productos en proceso");
        System.out.println("3. Terminar Produccion");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar Producto");
        System.out.println("6. Ver inventario de productos");
        System.out.println("7. Regresar Al Menu Principal");
        System.out.println("\nIngresa Una Opción...");
    }
}
