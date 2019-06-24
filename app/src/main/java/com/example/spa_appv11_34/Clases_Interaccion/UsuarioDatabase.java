package com.example.spa_appv11_34.Clases_Interaccion;

import com.google.firebase.database.ServerValue;

public class UsuarioDatabase {

    private String nombre;
    private String apellidos;
    private String nombreUsuario;
    private String URL_Foto;
    private String historia;
    private String email;
    private String fechaNacimiento;
    private String Genero;
    private String celular;
    private String llaveUsuario;
    private String llaveCentro;

    //Fecha de creación del usuario
    private Object createdTimestamp;

    public UsuarioDatabase() {
        createdTimestamp = ServerValue.TIMESTAMP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getURL_Foto() {
        return URL_Foto;
    }

    public void setURL_Foto(String URL_Foto) {
        this.URL_Foto = URL_Foto;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getLlaveUsuario() {
        return llaveUsuario;
    }

    public void setLlaveUsuario(String llaveUsuario) {
        this.llaveUsuario = llaveUsuario;
    }

    public String getLlaveCentro() {
        return llaveCentro;
    }

    public void setLlaveCentro(String llaveCentro) {
        this.llaveCentro = llaveCentro;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }
}
