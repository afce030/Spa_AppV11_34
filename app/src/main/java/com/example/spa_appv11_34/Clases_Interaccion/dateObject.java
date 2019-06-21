package com.example.spa_appv11_34.Clases_Interaccion;

import com.google.firebase.database.ServerValue;

public class dateObject {

    private Object createdTimestamp;

    public dateObject() {
        createdTimestamp = ServerValue.TIMESTAMP;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }
}
