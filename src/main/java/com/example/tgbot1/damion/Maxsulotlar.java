package com.example.tgbot1.damion;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Maxsulotlar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nomi;
    private String usr;
    private int narxi;
    private String image;

    public Maxsulotlar() {

    }

    public Maxsulotlar(String nomi, String usr, int narxi) {
        this.nomi = nomi;
        this.usr = usr;
        this.narxi = narxi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomi() {
        return nomi;
    }

    public void setNomi(String nomi) {
        this.nomi = nomi;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String info) {
        this.usr = info;
    }

    public int getNarxi() {
        return narxi;
    }

    public void setNarxi(int narxi) {
        this.narxi = narxi;
    }
}
