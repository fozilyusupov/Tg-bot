package com.example.tgbot1.damion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Zakazla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  long modelid;
    private String name;
    private  int soni;
    private  String chatid;
    private  String image;
    private  int messegid;
    private  int Narxi;
    private  int Hammasi;
    private String modelname;
    private boolean status;

    public Zakazla(long modelid, String name,  String chatid, String image,int messegid,int Narxi,boolean status,String modelname) {
        this.modelid = modelid;
        this.name = name;
        this.chatid = chatid;
        this.image = image;
        this.messegid=messegid;
        this.Narxi=Narxi;
        Hammasi=Narxi;
        this.status=status;
        this.modelname=modelname;
    }

    public Zakazla() {

    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public int getHammasi() {
        return Hammasi;
    }

    public void setHammasi(int hammasi) {
        Hammasi = hammasi;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getNarxi() {
        return Narxi;
    }

    public void setNarxi(int narxi) {
        Narxi = narxi;
    }

    public int getMessegid() {
        return messegid;
    }

    public void setMessegid(int messegid) {
        this.messegid = messegid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getModelid() {
        return modelid;
    }

    public void setModelid(long modelid) {
        this.modelid = modelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoni() {
        return soni;
    }

    public void setSoni(int soni) {
        Hammasi=Hammasi*soni;
        this.soni = soni;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
