/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Vista.vistaRegEmpleado;
import dao.UsuarioDao;

/**
 *
 * @author EMMANUEL
 */
public class cUsuario {
    private vistaRegEmpleado vistaU;
    private UsuarioDao daoU;
    
    /**
   * Constructor que inicializa el controlador con la vista y el DAO.
   * @param vistaU Objeto vistaRegREmpleado para almacernar ususario.
   */
  public cUsuario(vistaRegEmpleado vistaU) {
    this.vistaU = vistaU;
    this.daoU = new UsuarioDao();
    this.vistaU.setControladorUser(this);
  }
 
  
    
}
