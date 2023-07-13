/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import java.util.Objects;

/**
 *
 * @author MMD
 */
public class Utilisateur {
    
    
        int id,age_util;
    String nom_util,prenom_util,pseudo_util,mot_de_passe_util,email_util,genre_util,role_util,demande_suppression;
    
    public Utilisateur(){}

     //constructeur complet
    public Utilisateur(int id, int age_util, String nom_util, String prenom_util, String pseudo_util, String mot_de_passe_util, String email_util, String genre_util, String role_util, String demande_suppression) {
        this.id = id;
        this.age_util = age_util;
        this.nom_util = nom_util;
        this.prenom_util = prenom_util;
        this.pseudo_util = pseudo_util;
        this.mot_de_passe_util = mot_de_passe_util;
        this.email_util = email_util;
        this.genre_util = genre_util;
        this.role_util = role_util;
        this.demande_suppression=demande_suppression;
    }
    
    //constructeur (avec id) sans "demande_suppression"
    public Utilisateur(int id, int age_util, String nom_util, String prenom_util, String pseudo_util, String mot_de_passe_util, String email_util, String genre_util, String role_util) {
        this.id = id;
        this.age_util = age_util;
        this.nom_util = nom_util;
        this.prenom_util = prenom_util;
        this.pseudo_util = pseudo_util;
        this.mot_de_passe_util = mot_de_passe_util;
        this.email_util = email_util;
        this.genre_util = genre_util;
        this.role_util = role_util;
    }
    
    //Constructeur sans id et sans demande de suppression
      public Utilisateur(int age_util, String nom_util, String prenom_util, String pseudo_util, String mot_de_passe_util, String email_util, String genre_util, String role_util) {
        this.age_util = age_util;
        this.nom_util = nom_util;
        this.prenom_util = prenom_util;
        this.pseudo_util = pseudo_util;
        this.mot_de_passe_util = mot_de_passe_util;
        this.email_util = email_util;
        this.genre_util = genre_util;
        this.role_util = role_util;
    }

    //Constructeur sans id et avec demande de suppression
      public Utilisateur(int age_util, String nom_util, String prenom_util, String pseudo_util, String mot_de_passe_util, String email_util, String genre_util, String role_util,String demande_suppression) 
      {
        this.age_util = age_util;
        this.nom_util = nom_util;
        this.prenom_util = prenom_util;
        this.pseudo_util = pseudo_util;
        this.mot_de_passe_util = mot_de_passe_util;
        this.email_util = email_util;
        this.genre_util = genre_util;
        this.role_util = role_util;
        this.demande_suppression=demande_suppression;
    }      

      // Getters
    public int getId() {
        return id;
    }

    public int getAge_util() {
        return age_util;
    }

    public String getNom_util() {
        return nom_util;
    }

    public String getPrenom_util() {
        return prenom_util;
    }

    public String getPseudo_util() {
        return pseudo_util;
    }

    public String getMot_de_passe_util() {
        return mot_de_passe_util;
    }

    public String getEmail_util() {
        return email_util;
    }

    public String getGenre_util() {
        return genre_util;
    }

    public String getRole_util() {
        return role_util;
    }



    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAge_util(int age_util) {
        this.age_util = age_util;
    }

    public void setNom_util(String nom_util) {
        this.nom_util = nom_util;
    }

    public void setPrenom_util(String prenom_util) {
        this.prenom_util = prenom_util;
    }

    public void setPseudo_util(String pseudo_util) {
        this.pseudo_util = pseudo_util;
    }

    public void setMot_de_passe_util(String mot_de_passe_util) {
        this.mot_de_passe_util = mot_de_passe_util;
    }

    public void setEmail_util(String email_util) {
        this.email_util = email_util;
    }

    public void setGenre_util(String genre_util) {
        this.genre_util = genre_util;
    }

    public void setRole_util(String role_util) {
        this.role_util = role_util;
    }

    public String getDemande_suppression() {
        return demande_suppression;
    }

    public void setDemande_suppression(String demande_suppression) {
        this.demande_suppression = demande_suppression;
    }
    
    
    
    
  /*  
    
     //cette méthode permet de générer un pseudo à partir du nom,prénom et de l'ID de l'utilisateur passé en paramètres
    public String GenererPseudo()
    {
      String nom=this.getNom_util().replace(" ",""); //le nom sans espaces
      String prenom=this.getPrenom_util().replace(" ",""); //le prénom sans espaces
       String id_string=String.valueOf(this.getId()); //convertir l'id en String
  
       return nom+prenom+id_string; 
    }
    */
    
   //toString
        @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", age_util=" + age_util + ", nom_util=" + nom_util + ", prenom_util=" + prenom_util + ", pseudo_util=" + pseudo_util + ", mot_de_passe_util=" + mot_de_passe_util + ", email_util=" + email_util + ", genre_util=" + genre_util + ", role_util=" + role_util + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + this.age_util;
        hash = 97 * hash + Objects.hashCode(this.nom_util);
        hash = 97 * hash + Objects.hashCode(this.prenom_util);
        hash = 97 * hash + Objects.hashCode(this.pseudo_util);
        hash = 97 * hash + Objects.hashCode(this.mot_de_passe_util);
        hash = 97 * hash + Objects.hashCode(this.email_util);
        hash = 97 * hash + Objects.hashCode(this.genre_util);
        hash = 97 * hash + Objects.hashCode(this.role_util);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilisateur other = (Utilisateur) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.age_util != other.age_util) {
            return false;
        }
        if (!Objects.equals(this.nom_util, other.nom_util)) {
            return false;
        }
        if (!Objects.equals(this.prenom_util, other.prenom_util)) {
            return false;
        }
        if (!Objects.equals(this.pseudo_util, other.pseudo_util)) {
            return false;
        }
        if (!Objects.equals(this.mot_de_passe_util, other.mot_de_passe_util)) {
            return false;
        }
        if (!Objects.equals(this.email_util, other.email_util)) {
            return false;
        }
        if (!Objects.equals(this.genre_util, other.genre_util)) {
            return false;
        }
        if (!Objects.equals(this.role_util, other.role_util)) {
            return false;
        }
        return true;
    }
    
    
}
