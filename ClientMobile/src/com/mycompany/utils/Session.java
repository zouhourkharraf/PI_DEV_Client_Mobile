/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utils;

import com.mycompany.entities.Utilisateur;

/**
 *
 * @author MMD
 */
public class Session {
    
    public static Utilisateur utilisateur_connecte=null;
    public static Boolean EspaceConnecte=null; //cette variable permet d'indiquer quel espace ouvert maintenant l'espace administrateur ou l'espace utilisateur(càd soit enseignant soit élève)
                                              // ---> ture pour l'espace administrateur et false pour l'espace utilisateur et null si l'utilisateur est déconnectée
    
    
}
