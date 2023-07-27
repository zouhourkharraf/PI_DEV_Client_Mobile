

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
public class ModifierMPUtilisateur extends Form {
    public ModifierMPUtilisateur(Resources theme) throws IOException {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_TOTAL_BELOW));
        setUIID("LoginStyle1");
        Container welcome = FlowLayout.encloseCenter(

                new SpanLabel("Réinitialiser mon mot de passe :", "LoginButton2")
              
        );
        
            getTitleArea().setUIID("Container");
        
        Image profilePic = Image.createImage("/grand_logo_finale.png");
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        
        TextField champ_nouveau_mp = new TextField("", "Nouveau mot de passe", 20, TextField.PASSWORD) ;
        TextField champ_confirmation_nouveau_mp = new TextField("", "Confirmation", 20, TextField.PASSWORD) ;
        
        champ_nouveau_mp.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        champ_confirmation_nouveau_mp.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon2 = new Label("", "TextField");
        loginIcon2.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon2, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button BoutonValider = new Button("Confirmer");
        BoutonValider.setUIID("LoginButton3");
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
                BorderLayout.center(champ_nouveau_mp).
                        add(BorderLayout.WEST, loginIcon),
                 BorderLayout.center(champ_confirmation_nouveau_mp).
                        add(BorderLayout.WEST, loginIcon2),
                BoutonValider,BoutonAnnuler
                
        );
        add(BorderLayout.CENTER, by);
        
     
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
        
               // ***********************************  Gestion de l'événement suite au clique sur le bouton "Confirmer" pour vérifier le code secret *************************
        BoutonValider.addActionListener(e -> {
          
            if(  (champ_nouveau_mp.getText().compareTo("")==0) || (champ_confirmation_nouveau_mp.getText().compareTo("")==0))
               {  Dialog.show("Erreur !", "Veuillez renseigner tous les champs!","OK",null); }
            else
            {
            if( champ_nouveau_mp.getText().compareTo(champ_confirmation_nouveau_mp.getText())!=0 )
              {  Dialog.show("Erreur !", "Les mots de passe ne sont pas identiques !","OK",null); }
            else
             {
              if(ServiceUtilisateur.getInstance().ModifierMotDePasseUtilisateur(Session.utilisateur_mp_oublie.getId(),champ_nouveau_mp.getText(), theme))
                   {  
                       Dialog.show("Notification !", "Votre mot de passe a été modifié avec succès !","OK",null);
                         try {
                                new LoginForm(theme).show();
                                   Session.utilisateur_mp_oublie=null; //il n y'a plus un utilisateur qui est en train de modifier son mot de passe
                             } catch (IOException ex) { }
                   }
              }
            
            }
                
          
        });
        
         // ***********************************  FIN Gestion de l'événement suite au clique sur le bouton "Confirmer" pour vérifier le code secret *************************
        
     // ***** Le bouton "Annuler" *******
     BoutonAnnuler.addActionListener(e -> {
         
         champ_nouveau_mp.setText("");
         champ_confirmation_nouveau_mp.setText("");
         
     });
         
    }

    
 
    
    
    
    

}
