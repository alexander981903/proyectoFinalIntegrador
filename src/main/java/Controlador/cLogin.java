/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import Vista.vistaHome;
import Vista.vistaLogin;
import dao.UsuarioDao;
import javax.swing.JOptionPane;

/**
 *
 * @author EMMANUEL
 */
public class cLogin {
    
    private vistaLogin vistaL;
    private UsuarioDao daoU;
    
    public cLogin(vistaLogin vistaL){
        this.vistaL = vistaL;
        this.daoU = new UsuarioDao();
        this.vistaL.setControladorL(this);
    }
    
    public void validarCredenciales(String login ,String clave){
        Usuario user = daoU.validarCredenciales(login, clave);
        if (user != null) {
            // Si el usuario es válido, mostramos la vista home
            vistaL.setVisible(false); // Ocultamos la vista de login
            // Puedes mostrar un mensaje o realizar otras acciones
            JOptionPane.showMessageDialog(this.vistaL, "Iniciando sesión...");
            vistaHome vistaHome = new vistaHome(); // Creamos la vista principal
            vistaHome.setVisible(true);  // Mostramos la vista principal
            new cHome(vistaHome);
            // Mostrar información del usuario o manejar su rol
            //vistaHome.setUsuario(user);
        } else {
            // Si las credenciales no son válidas, mostramos un mensaje de error
            vistaL.mostrarMensaje("Credenciales incorrectas");
        }
    }
}
