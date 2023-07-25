/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.myapp;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateur;
import com.mycompany.services.ServiceUtilisateur;
import com.mycompany.utils.Session;
import java.io.IOException;


/**
 * The Login form
 *
 * @author Shai Almog
 */
public class VerifierPseudo extends Form {
    public VerifierPseudo(Resources theme) throws IOException {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_TOTAL_BELOW));
        setUIID("LoginStyle1");
        Container welcome = FlowLayout.encloseCenter(
                       new Label("                                                 "),
                 new Label("                                                 "),
                new Label("Vérification ", "WelcomeBlue")
              
        );
        
            getTitleArea().setUIID("Container");
        
        Image profilePic = Image.createImage("/grand_logo_finale.png");
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        
        TextField login = new TextField("", "Pseudo", 20, TextField.USERNAME) ;
      
        login.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        
        Button BoutonVerifierPseudo = new Button("Valider");
        BoutonVerifierPseudo.setUIID("LoginButton");
        
        
                //********* Bouton "Retour"  *********************
      getToolbar().addCommandToLeftBar("", Image.createImage("/icone_retour.png") , (ActionListener) (ActionEvent evt) -> {
            try {
               new LoginForm(theme).show();
            } catch (IOException ex) {
               
            }
        });
    //********* FIN Bouton "Retour"  *********************
        
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
          
        
        
        Container by = BoxLayout.encloseY(
                welcome,
                profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BoutonVerifierPseudo
                
        );
        add(BorderLayout.CENTER, by);
        
     
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
        
               // ***********************************  Gestion de l'événement suite au clique sur le bouton de connexion *************************
        BoutonVerifierPseudo.addActionListener(e -> {
            Utilisateur   u= ServiceUtilisateur.getInstance().AfficherUtilisateurParPseudo("adminmagic1");
            System.out.println(u.toString());
        });
        
         // ***********************************  FIN Gestion de l'événement suite au clique sur le bouton de connexion *************************
        
           
         
         
         
       
         
    }

 



}
