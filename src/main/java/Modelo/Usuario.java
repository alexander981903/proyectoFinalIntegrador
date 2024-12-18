/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author EMMANUEL
 */
public class Usuario {
    private String login;
    private String clave;
    private String rol;
    private Object obj;
    private Configuracion config;

    public Usuario() {
    }

    public Usuario(String login, String clave, String rol, Object obj, Configuracion config) {
        this.login = login;
        this.clave = clave;
        this.rol = rol;
        this.obj = obj;
        this.config = config;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Configuracion getConfig() {
        return config;
    }

    public void setConfig(Configuracion config) {
        this.config = config;
    }
    
    
}