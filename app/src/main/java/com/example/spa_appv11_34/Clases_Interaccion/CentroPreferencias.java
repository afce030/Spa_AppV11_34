package com.example.spa_appv11_34.Clases_Interaccion;

public class CentroPreferencias {

    //PReferencias adicionales

    //Configuration preferences
    private String theme;
    private String language;
    private Boolean notifications;
    //Payment
    private Boolean creditCard;
    private Boolean payPal;

    public CentroPreferencias() {
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
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
