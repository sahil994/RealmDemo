package com.hocrox.realmdemo;

import io.realm.RealmObject;

/**
 * Created by sahilgoyal on 24/7/17.
 */

public class ContactInfo extends RealmObject{

    public String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String MobileNumber;

}
