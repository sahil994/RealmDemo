package com.hocrox.realmdemo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    private String name;
    private int age;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @PrimaryKey
    private int key;

    public RealmList<ContactInfo> getContactInfoRealmList() {
        return contactInfoRealmList;
    }

    public void setContactInfoRealmList(RealmList<ContactInfo> contactInfoRealmList) {
        this.contactInfoRealmList = contactInfoRealmList;
    }

    private RealmList<ContactInfo> contactInfoRealmList ;

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    private ContactInfo contactInfo;


    @Ignore
    private int sessionId;

    public String getName() { return name; }
    public void   setName(String name) { this.name = name; }
    public int    getAge() { return age; }
    public void   setAge(int age) { this.age = age; }
    public int    getSessionId() { return sessionId; }
    public void   setSessionId(int sessionId) { this.sessionId = sessionId; }



}