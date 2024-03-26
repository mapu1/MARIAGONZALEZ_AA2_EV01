/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/*Contenido del código:
 * Se realiza la conexión a una base de datos MySQL
 * Se inserta un registro en la tabla.
 * Se actualiza un registro que ya existe.
 * Se elimina un registro que ya existe.
 * Se consultan todos los registros de nuevo.
 */
package gestioninventarios;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestionInventarios {

    public static void main(String[] args) {
        String usuario = "root";
        String password = "paula";
        String url = "jdbc:mysql://localhost:3306/inventarios";
        Connection conexion;
        Statement statement;
        ResultSet rs;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionInventarios.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        try {
            conexion = DriverManager.getConnection(url, usuario, password);
            statement = conexion.createStatement();
            
            // Insertar un registro
            
            statement.executeUpdate("INSERT INTO CLINICA (CODIGO, NOMBRE) VALUES ('1', 'REINA SOFIA')");
            System.out.println("Registro insertado correctamente.");
            
            // Consultar registros
            
            rs = statement.executeQuery("SELECT * FROM CLINICA");
            System.out.println("Registros en la tabla CLINICA:");
            while (rs.next()) {
                System.out.println(rs.getInt("codigo") + ": " + rs.getString("nombre"));
            }
            
            // Actualizar un registro que ya existe
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingresa el código de la clínica que desea actualizar: ");
            int codigoClinica = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Ingresa el nuevo nombre de la clínica: ");
            String nuevoNombre = scanner.nextLine();
            String updateQuery = "UPDATE CLINICA SET NOMBRE = ? WHERE CODIGO = ?";
            PreparedStatement updateStatement = conexion.prepareStatement(updateQuery);
            updateStatement.setString(1, nuevoNombre);
            updateStatement.setInt(2, codigoClinica);
            int filasActualizadas = updateStatement.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Registro actualizado correctamente.");
            } else {
                System.out.println("No se encontró ninguna clínica con el código proporcionado.");
            }
            
            // Eliminar un registro que ya existe
            
            System.out.print("Ingrese el código de la clínica que desea eliminar: ");
            int codigoEliminar = 0;
            if (scanner.hasNextInt()) {
                codigoEliminar = scanner.nextInt();
                String deleteQuery = "DELETE FROM CLINICA WHERE CODIGO = ?";
                PreparedStatement deleteStatement = conexion.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, codigoEliminar);
                int filasEliminadas = deleteStatement.executeUpdate();
                if (filasEliminadas > 0) {
                    System.out.println("Registro eliminado correctamente.");
                } else {
                    System.out.println("No se encontró ninguna clínica con el código proporcionado.");
                }
            } else {
                System.out.println("El código de la clínica debe ser un número entero.");
            }
            
            // Consultar todos los registros de nuevo
            rs = statement.executeQuery("SELECT * FROM CLINICA");
            System.out.println("Registros en la tabla CLINICA:");
            while (rs.next()) {
                System.out.println(rs.getInt("codigo") + ": " + rs.getString("nombre"));
            }
            
            rs.close();
            statement.close();
            conexion.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GestionInventarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
