

package com.mycompany.myapp;

import com.codename1.components.OnOffSwitch;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.NumericSpinner;
import com.codename1.ui.util.Resources;
import com.codename1.util.regex.RE;
import com.mycompany.entities.Utilisateur;
import com.mycompany.services.ServiceUtilisateur;
import com.mycompany.utils.Session;
import java.io.IOException;



public class PageModifEleve extends Form {
    public PageModifEleve(Resources theme,Utilisateur utilisateur_a_modifier) throws IOException {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_TOTAL_BELOW));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("                            "),
                new Label("Modifier mes information", "WelcomeWhite"),
              new Label("Élève :", "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
        
        Image profilePic = Image.createImage("/logo_finale.png");
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
       
        //********* Bouton "Retour"  *********************
      getToolbar().addCommandToLeftBar("", Image.createImage("/icone_retour.png") , (ActionListener) (ActionEvent evt) -> {
            try {
                if(Session.EspaceConnecte) //si on est connectée sur l'espace administrateur 
                {
                new DashboardAdministrateur(theme).showBack();
                }
                else //si on est connectée sur l'espace utilisateur
                {
                new ProfileForm(theme).showBack();
                }
                
            } catch (IOException ex) {
               
            }
        });
    //********* FIN Bouton "Retour"  *********************
    
    
        TextField champ_nom = new TextField(utilisateur_a_modifier.getNom_util(), "nom", 20, TextField.USERNAME) ;
        TextField champ_prenom = new TextField(utilisateur_a_modifier.getPrenom_util(), "prenom", 20, TextField.USERNAME) ;
        NumericSpinner champ_age=new NumericSpinner();
        //Le genre
        Container BoxGenre=new Container(BoxLayout.y());
        Container c=new Container(BoxLayout.x());
        Label label_genre=new Label("Votre genre : ");
       
        Label label_homme=new Label("Homme : ");
        Label label_femme=new Label("Femme : ");
        OnOffSwitch OnOffGenre=new OnOffSwitch();
        OnOffGenre.setOn("F");
        OnOffGenre.setOff("H");
        c.add(label_homme);
        c.add(OnOffGenre);
        c.add(label_femme);
        BoxGenre.add(label_genre);
        BoxGenre.add(c);
        //FIN Le genre
        TextField champ_email = new TextField(utilisateur_a_modifier.getEmail_util(), "Votre email", 20, TextField.EMAILADDR) ;
     
        
        //Mise en forme des champs du formulaire
        champ_nom.getAllStyles().setMargin(LEFT, 0);
        champ_prenom.getAllStyles().setMargin(LEFT, 0);
        champ_age.getAllStyles().setMargin(LEFT, 0);
        champ_email.getAllStyles().setMargin(LEFT, 0);
      
        //Crétion des label sous formes d'icones (et sans texte)
        Label loginIcon = new Label("", "TextField");
        Label prenomIcon = new Label("", "TextField");
        Label ageIcon = new Label("Votre age", "SpinnerWrapper");
        Label emailIcon = new Label("", "TextField");
        
        //Mise en forme des labels
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        prenomIcon.getAllStyles().setMargin(RIGHT, 0);
        ageIcon.getAllStyles().setMargin(RIGHT, 0);
        emailIcon.getAllStyles().setMargin(RIGHT, 0);
   
        //Définir les icones des labels
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_PIN, 3);
        FontImage.setMaterialIcon(prenomIcon, FontImage.MATERIAL_PERSON_PIN, 3);
        FontImage.setMaterialIcon(ageIcon, FontImage.MATERIAL_PERSON_PIN, 3);
        FontImage.setMaterialIcon(emailIcon, FontImage.MATERIAL_EMAIL, 3);         
     
           //Définir le label pour l'age +  les contraintes (controle de saisie):
           champ_age.setLabelForComponent(ageIcon);
           champ_age.setMin(5);
           champ_age.setMax(200);
           //FIN Définir le label pour l'age +  les contraintes (controle de saisie):
       
        //Créer un Container qui contient le label de l'age et le NumericSpinner
         Container BoxAge=new Container(BoxLayout.x()); 
         BoxAge.add(ageIcon);
         BoxAge.add(champ_age);
       // Réccupérer l'age de l'utilisateur à modifier
       champ_age.setValue( (double)utilisateur_a_modifier.getAge_util() );
         
       
        //Création du bouton de modification
        Button BoutonModifierEleve = new Button("Modifier");
        BoutonModifierEleve.setUIID("LoginButton");
        
       // ***********************************  Gestion de l'événement suite au clique sur le bouton de modification *************************
        BoutonModifierEleve.addActionListener(e -> {
          
            Double age1=champ_age.getValue(); //Récupérer la valeur de l'age choisi
               //définir le genre choisi par l'utilisateur
        String genre_choisi="";
        if(OnOffGenre.isValue())
        {
            genre_choisi=OnOffGenre.getOn();
        }
        else
        {
        genre_choisi=OnOffGenre.getOff();
        }
          Boolean formulaire_valide=false; //On va utiliser cette variable pour tester la validité du formulaire
         // ****** Controle de saisie ***********
    if( (champ_nom.getText().isEmpty()) || ( champ_prenom.getText().isEmpty() ) || ( champ_email.getText().isEmpty() ) )
    {
        Dialog.show("Erreur !","Veuillez renseigner tous les champs !","OK",null);
    }
    else 
    {
               //le nom
        RE regexp1=new RE(  ".*(\\d)+.*"); //Définir l'expression régulière à tester avec la classe RE
       if( regexp1.match(champ_nom.getText()) ) //tester l'expression regexp1 avec la méthode match  
          {  Dialog.show("Erreur !", "le nom ne doit pas contenir des chiffres !","OK",null);  }
       else
          {  
             //le prénom
             RE regexp2=new RE(".*(\\d)+.*"); //Définir l'expression régulière à tester avec la classe RE  
           if( regexp2.match(champ_prenom.getText()) ) //tester l'expression regexp2 avec la méthode match  
          {  Dialog.show("Erreur !", "le prénom ne doit pas contenir des chiffres !","OK",null);  }
            //Controle nom+prénom
            else
            {
               if(champ_prenom.getText().compareTo(champ_nom.getText())==0)
               {  Dialog.show("Erreur !", "le nom et le prénom sont identiques !","OK",null);  }
               else
               {
                   //L'email
                   RE regexp3=new RE("^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z][a-z]+$");
                  if( !regexp3.match(champ_email.getText()) ) // format souhaité : aaazjksjd@fdfdfdd.fdfdfd
                  {  Dialog.show("Erreur !", "Adresse invalide !","OK",null);  }
                 else
                 {
                    formulaire_valide=true;  
                 }
               }
            }

          }        
   
    }
   
     // ****** FIN Controle de saisie ***********
          
        if(formulaire_valide)
        {
           String pseudo1=ServiceUtilisateur.getInstance().ModifierUtilisateur(utilisateur_a_modifier.getId(),champ_nom, champ_prenom, age1.toString(), genre_choisi, champ_email,"élève", theme);
        Dialog.show("Notification !","La modification a été effectué avec succès ! \n Votre nouveau pseudo : "+pseudo1,"OK",null);
                try {
                    if(Session.EspaceConnecte)
                    { new DashboardAdministrateur(theme).show() ; }
                    else
                    {  new LoginForm(theme).show();  }
                } catch (IOException ex) { }
                  
        }
         
     
        });
      // ***********************************  FIN Gestion de l'événement suite au clique sur le bouton d'inscription **********
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        //ajouter tous les élément crées au Container "by"
        Container by = BoxLayout.encloseY(
                welcome,
                profilePicLabel,
                spaceLabel,
                BorderLayout.center(champ_nom).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(champ_prenom).
                        add(BorderLayout.WEST, prenomIcon),
                BoxAge,
                        BoxGenre,
               BorderLayout.center(champ_email).
                        add(BorderLayout.WEST, emailIcon),
                BoutonModifierEleve
               
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    
  
    
    
    
    }
}
