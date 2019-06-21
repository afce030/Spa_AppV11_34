package com.example.spa_appv11_34.Clases_Interaccion;

import com.google.firebase.database.ServerValue;

public class MensajeDatabase {

    private String key_emisor;
    private String textoMensaje;
    private String URL_foto_mensaje;
    private boolean tieneFoto;

    private Object createdTimestamp;

    public MensajeDatabase() {
        createdTimestamp = ServerValue.TIMESTAMP;
    }

    public String getKey_emisor() {
        return key_emisor;
    }

    public void setKey_emisor(String key_emisor) {
        this.key_emisor = key_emisor;
    }

    public String getTextoMensaje() {
        return textoMensaje;
    }

    public void setTextoMensaje(String textoMensaje) {
        this.textoMensaje = textoMensaje;
    }

    public String getURL_foto_mensaje() {
        return URL_foto_mensaje;
    }

    public void setURL_foto_mensaje(String URL_foto_mensaje) {
        this.URL_foto_mensaje = URL_foto_mensaje;
    }

    public boolean isTieneFoto() {
        return tieneFoto;
    }

    public void setTieneFoto(boolean tieneFoto) {
        this.tieneFoto = tieneFoto;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }

}
