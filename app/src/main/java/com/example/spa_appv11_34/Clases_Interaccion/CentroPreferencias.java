package com.example.spa_appv11_34.Clases_Interaccion;

public class CentroPreferencias {

    //Preferencias adicionales

    //Configuration preferences
    private String theme;
    private String idioma;
    private Boolean notificaciones;
    //Payment
    private Boolean recibirCreditCard;
    private Boolean recibirPayPal;

    public CentroPreferencias() {
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Boolean getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Boolean getRecibirCreditCard() {
        return recibirCreditCard;
    }

    public void setRecibirCreditCard(Boolean recibirCreditCard) {
        this.recibirCreditCard = recibirCreditCard;
    }

    public Boolean getRecibirPayPal() {
        return recibirPayPal;
    }

    public void setRecibirPayPal(Boolean recibirPayPal) {
        this.recibirPayPal = recibirPayPal;
    }
}
