package com.example.spa_appv11_34.Clases_Interaccion;

public class UsuarioPreferences {

    //Configuration preferences
    private String theme;
    private String idioma;
    private Boolean notificaciones;
    //Payment
    private Boolean creditCard;
    private Boolean payPal;

    public UsuarioPreferences() {

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

    public Boolean getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Boolean creditCard) {
        this.creditCard = creditCard;
    }

    public Boolean getPayPal() {
        return payPal;
    }

    public void setPayPal(Boolean payPal) {
        this.payPal = payPal;
    }
}
