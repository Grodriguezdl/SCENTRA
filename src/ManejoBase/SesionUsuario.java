package ManejoBase;

import java.sql.*;
import javax.swing.JOptionPane;

public class SesionUsuario {
    private static SesionUsuario instance;
    private String rol;
    private int idEmpresa;
    private int idUsuario;
    private String nombreusuario;
    private String contrasenausuario;

    private SesionUsuario() {
        rol = null;
        idEmpresa = -1;
        idUsuario = -1;
        nombreusuario = null;
        contrasenausuario = null;
    }
    public static SesionUsuario getInstance(){
        if(instance == null){
            instance = new SesionUsuario();
        }
        return instance;
    }
    public void setUsuario(String rol, int idEmpresa, int idUsuario, String nombreUsuario, String contrasenausuarioo){
        this.rol = rol;
        this.idEmpresa = idEmpresa;
        this.idUsuario = idUsuario;
        this.nombreusuario = nombreUsuario;
        this.contrasenausuario = contrasenausuarioo;
    }
    public void cerrarsesion(){
        this.rol = null;
        this.idEmpresa = -1;
        this.idUsuario = -1;
        this.nombreusuario = null;
        this.contrasenausuario = null;
    }

    public String getRol() {
        return rol;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public String getContrasenausuario() {
        return contrasenausuario;
    }
    
    public boolean sesionact(){
        return rol != null && idEmpresa !=-1 && idUsuario != -1;
    }

    public static void setInstance(SesionUsuario instance) {
        SesionUsuario.instance = instance;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public void setContrasenausuario(String contrasenausuario) {
        this.contrasenausuario = contrasenausuario;
    }
    
}
