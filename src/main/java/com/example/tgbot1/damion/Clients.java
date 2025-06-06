package com.example.tgbot1.damion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String number;
    private String chatId;
    private boolean til;
    //true ozbek tili
    //false rus tili

    public Clients() {
    }

    public Clients(String name, String number, String chatId) {
        this.name = name;
        this.number = number;
        this.chatId = chatId;
    }


    public boolean isTil() {
        return til;
    }

    public void setTil(boolean til) {
        this.til = til;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
