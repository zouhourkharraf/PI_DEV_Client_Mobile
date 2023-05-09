/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author Sondes
 */
public class Evenement {
    String nom_ev;
    String lieu_ev,desc_ev;
    int id;

    public Evenement(int id,String nom_ev, String lieu_ev, String desc_ev) {
        this.id = id;
        this.nom_ev = nom_ev;
        this.lieu_ev = lieu_ev;
        this.desc_ev = desc_ev;
        
    }

    public Evenement(String nom_ev, String lieu_ev, String desc_ev) {
        this.nom_ev = nom_ev;
        this.lieu_ev = lieu_ev;
        this.desc_ev = desc_ev;
   
    }

    public Evenement() {
    }
    

    public String getNom_ev() {
        return nom_ev;
    }

    public void setNom_ev(String nom_ev) {
        this.nom_ev = nom_ev;
    }

    public String getLieu_ev() {
        return lieu_ev;
    }

    public void setLieu_ev(String lieu_ev) {
        this.lieu_ev = lieu_ev;
    }

    public String getDesc_ev() {
        return desc_ev;
    }

    public void setDesc_ev(String desc_ev) {
        this.desc_ev = desc_ev;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}
