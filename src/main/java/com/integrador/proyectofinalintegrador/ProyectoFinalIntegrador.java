/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.integrador.proyectofinalintegrador;

import Controlador.cLogin;
import Vista.vistaLogin;
import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación.
 * Esta clase contiene el método main, que es el punto de entrada de la aplicación.
 * Se encarga de mostrar la ventana de inicio de sesión al usuario y crear el controlador correspondiente.
 * 
 * @author EMMANUEL
 */
public class ProyectoFinalIntegrador {

    /**
     * Método principal que ejecuta la aplicación.
     * Inicia la interfaz de usuario de la ventana de inicio de sesión 
     * y establece su controlador.
     * 
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Ejecutar la aplicación
        SwingUtilities.invokeLater(() -> {
            vistaLogin loginFrame = new vistaLogin();
            loginFrame.setVisible(true);
            new cLogin(loginFrame);
        });
    }
}