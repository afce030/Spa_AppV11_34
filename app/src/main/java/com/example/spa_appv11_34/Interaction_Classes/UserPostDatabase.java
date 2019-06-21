package com.example.spa_appv11_34.Interaction_Classes;

import com.google.firebase.database.ServerValue;

public class UserPostDatabase {

    private String usuario;
    private String texto;
    private String URL_Foto1;
    private String URL_Foto2;
    private String URL_Foto3;
    private String URL_Foto4;
    private String Uid_post;
    private Object createdTimestamp;

    public UserPostDatabase() {
        createdTimestamp = ServerValue.TIMESTAMP;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getURL_Foto1() {
        return URL_Foto1;
    }

    public void setURL_Foto1(String URL_Foto1) {
        this.URL_Foto1 = URL_Foto1;
    }

    public String getURL_Foto2() {
        return URL_Foto2;
    }

    public void setURL_Foto2(String URL_Foto2) {
        this.URL_Foto2 = URL_Foto2;
    }

    public String getURL_Foto3() {
        return URL_Foto3;
    }

    public void setURL_Foto3(String URL_Foto3) {
        this.URL_Foto3 = URL_Foto3;
    }

    public String getURL_Foto4() {
        return URL_Foto4;
    }

    public void setURL_Foto4(String URL_Foto4) {
        this.URL_Foto4 = URL_Foto4;
    }

    public String getUid_post() {
        return Uid_post;
    }

    public void setUid_post(String uid_post) {
        Uid_post = uid_post;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }

}
