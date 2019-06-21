package com.example.spa_appv11_34.Interaction_Classes;

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
