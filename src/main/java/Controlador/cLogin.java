/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import Vista.vistaHome;
import Vista.vistaLogin;
import dao.UsuarioDao;

/**
 * Controlador para gestionar el proceso de inicio de sesión de un usuario.
 * Esta clase valida las credenciales del usuario y, en caso de éxito, redirige a la vista principal.
 * 
 * @author EMMANUEL
 */
public class cLogin {
    
    private vistaLogin vistaL;
    private UsuarioDao daoU;
    
    /**
     * Constructor del controlador de login.
     * Inicializa la vista de login y el acceso al DAO de usuario.
     * 
     * @param vistaL La vista de login a controlar.
     */
    public cLogin(vistaLogin vistaL){
        this.vistaL = vistaL;
        this.daoU = new UsuarioDao();
        this.vistaL.setControladorL(this);
    }
    
    /**
     * Valida las credenciales ingresadas por el usuario.
     * Si las credenciales son correctas, muestra la vista principal (vistaHome),
     * y en caso contrario, muestra un mensaje de error.
     * 
     * @param login El nombre de usuario ingresado.
     * @param clave La contraseña ingresada.
     */
    public void validarCredenciales(String login, String clave) {
        Usuario user = daoU.validarCredenciales(login, clave);

        if (user != null) {
                    vistaL.setVisible(false);
                    vistaHome vistaHome = new vistaHome(user);
                    vistaHome.setVisible(true);
                    new cHome(vistaHome);
                    new cReporte(vistaHome);
        } else {
            vistaL.mostrarMensaje("Credenciales incorrectas");
        }
    }
}

