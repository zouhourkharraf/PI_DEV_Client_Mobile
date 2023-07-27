

package com.mycompany.myapp;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
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

import com.mycompany.services.ServiceUtilisateur;
import com.mycompany.utils.Session;
import java.io.IOException;



/**
 * The Login form
 *
 * @author Shai Almog
 */
public class VerifierCodeSecret extends Form {
    public VerifierCodeSecret(Resources theme,int code_envoye) throws IOException {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_TOTAL_BELOW));
        setUIID("LoginStyle1");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Vérification du code envoyé", "WelcomeBlue"),
                new SpanLabel("Un code est envoyé à l'adresse suivante : "+Session.utilisateur_mp_oublie.getEmail_util(), "LoginButton2")
              
        );
        
            getTitleArea().setUIID("Container");
        
        Image profilePic = Image.createImage("/grand_logo_finale.png");
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        
        TextField code_secret_mp_oublie = new TextField("", "Code", 20, TextField.USERNAME) ;
      
        code_secret_mp_oublie.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        
        Button BoutonVerifierCodeSecret = new Button("Confirmer");
        BoutonVerifierCodeSecret.setUIID("LoginButton3");
        Button BoutonAnnuler = new Button("Annuler");
        BoutonAnnuler.setUIID("LoginButton3");
        
                //********* Bouton "Retour"  *********************
      getToolbar().addCommandToLeftBar("", Image.createImage("/icone_retour.png") , (ActionListener) (ActionEvent evt) -> {
            try {
               new LoginForm(theme).show();
                Session.utilisateur_mp_oublie=null;
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
                profilePicLabel,
                welcome,
                spaceLabel,
                BorderLayout.center(code_secret_mp_oublie).
                        add(BorderLayout.WEST, loginIcon),
                BoutonVerifierCodeSecret,BoutonAnnuler
                
        );
        add(BorderLayout.CENTER, by);
        
     
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
        
               // ***********************************  Gestion de l'événement suite au clique sur le bouton "Confirmer" pour vérifier le code secret *************************
        BoutonVerifierCodeSecret.addActionListener(e -> {
            if( code_secret_mp_oublie.getText().compareTo(String.valueOf(code_envoye))==0 )
            {
                 try {
               new ModifierMPUtilisateur(theme).show();
            } catch (IOException ex) {
               
            }
            }
            else
            {
                        Dialog.show("Erreur !","Code incorrect !","OK",null);
            }
                
          
        });
        
         // ***********************************  FIN Gestion de l'événement suite au clique sur le bouton "Confirmer" pour vérifier le code secret *************************
        
     // ***** Le bouton "Annuler" *******
     BoutonAnnuler.addActionListener(e -> {
         code_secret_mp_oublie.setText("");
     });
         
    }

    
 
    
    
    
    

}
