

package com.mycompany.myapp;
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
import com.mycompany.entities.Utilisateur;
import com.mycompany.services.ServiceUtilisateur;
import com.mycompany.utils.Session;
import java.io.IOException;
import java.util.Random;


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
                new Label("Vérification ", "WelcomeBlue")
              
        );
        
            getTitleArea().setUIID("Container");
        
        Image profilePic = Image.createImage("/grand_logo_finale.png");
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        
        TextField pseudo_mp_oublie = new TextField("", "Pseudo", 20, TextField.USERNAME) ;
      
        pseudo_mp_oublie.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        
        Button BoutonVerifierPseudo = new Button("Valider");
        BoutonVerifierPseudo.setUIID("LoginButton");
        
        
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
                BorderLayout.center(pseudo_mp_oublie).
                        add(BorderLayout.WEST, loginIcon),
                BoutonVerifierPseudo
                
        );
        add(BorderLayout.CENTER, by);
        
     
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
        
               // ***********************************  Gestion de l'événement suite au clique sur le bouton "Valider" pour vérifier le pseudo *************************
        BoutonVerifierPseudo.addActionListener(e -> {
            if( pseudo_mp_oublie.getText().compareTo("")!=0 )
            {
            ServiceUtilisateur.getInstance().VerifierPseudoUtilPourMPoublie(pseudo_mp_oublie.getText(),theme);
               // System.out.println(Session.utilisateur_mp_oublie.toString());
            }
            else
            {
            Dialog.show("Erreur !","Veuillez renseigner votre pseudo !","OK",null);
            }
                
          
        });
        
         // ***********************************  FIN Gestion de l'événement suite au clique sur le bouton "Valider" pour vérifier le pseudo *************************
        
     
         
    }

    
 
    
    
    
    

}
