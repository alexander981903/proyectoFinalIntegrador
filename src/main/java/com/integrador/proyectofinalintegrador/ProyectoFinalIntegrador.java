/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.integrador.proyectofinalintegrador;

import Controlador.cLogin;
import Vista.vistaLogin;
import javax.swing.SwingUtilities;

/**
 *
 * @author EMMANUEL
 */
public class ProyectoFinalIntegrador {

    public static void main(String[] args) {
        // Ejecutar la aplicación
        SwingUtilities.invokeLater(() -> {
            vistaLogin loginFrame = new vistaLogin();
            loginFrame.setVisible(true);
            new cLogin(loginFrame);
        });
    }
}
