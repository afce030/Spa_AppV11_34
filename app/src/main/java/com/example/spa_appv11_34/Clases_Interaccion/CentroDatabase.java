package com.example.spa_appv11_34.Clases_Interaccion;

public class CentroDatabase {

    private String nombreCentro;
    private String email;
    private String historia;
    private String URL_Foto;
    private String llaveCentro;
    private String llaveUsuario;

    private int calificacion;

    public CentroDatabase() {
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getURL_Foto() {
        return URL_Foto;
    }

    public void setURL_Foto(String URL_Foto) {
        this.URL_Foto = URL_Foto;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
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

}
