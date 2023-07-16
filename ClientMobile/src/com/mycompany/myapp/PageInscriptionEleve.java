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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.NumericSpinner;
import com.codename1.ui.util.Resources;
import com.codename1.util.regex.RE;
import com.mycompany.services.ServiceUtilisateur;
import java.io.IOException;



public class PageInscriptionEleve extends Form {
    public PageInscriptionEleve(Resources theme) throws IOException {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_TOTAL_BELOW));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("                            "),
                new Label("  Créer un compte  ", "WelcomeWhite"),
              new Label("  élève :", "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
        
        Image profilePic = Image.createImage("/logo_finale.png");
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
   
        
        TextField champ_nom = new TextField("Votre nom", "nom", 20, TextField.USERNAME) ;
        TextField champ_prenom = new TextField("Votre prénom", "prenom", 20, TextField.USERNAME) ;
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
        TextField champ_email = new TextField("Votre email", "Votre email", 20, TextField.EMAILADDR) ;
        TextField champ_mp = new TextField("Mot de passe", "Mot de passe", 20, TextField.PASSWORD) ;
        TextField champ_confirmation_mp = new TextField("Confirmation de mot de passe", "Confirmation de mot de passe", 20, TextField.PASSWORD) ;
        
        //Mise en forme des champs du formulaire
        champ_nom.getAllStyles().setMargin(LEFT, 0);
        champ_prenom.getAllStyles().setMargin(LEFT, 0);
        champ_age.getAllStyles().setMargin(LEFT, 0);
        champ_email.getAllStyles().setMargin(LEFT, 0);
        champ_mp.getAllStyles().setMargin(LEFT, 0);
        champ_confirmation_mp.getAllStyles().setMargin(LEFT, 0);
        //Crétion des label sous formes d'icones (et sans texte)
        Label loginIcon = new Label("", "TextField");
        Label prenomIcon = new Label("", "TextField");
        Label ageIcon = new Label("Votre age", "SpinnerWrapper");
        Label emailIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        Label password2Icon = new Label("", "TextField");
        //Mise en forme des labels
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        prenomIcon.getAllStyles().setMargin(RIGHT, 0);
        ageIcon.getAllStyles().setMargin(RIGHT, 0);
       
             
        
        emailIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        password2Icon.getAllStyles().setMargin(RIGHT, 0);
        //Définir les icones des labels
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_PIN, 3);
        FontImage.setMaterialIcon(prenomIcon, FontImage.MATERIAL_PERSON_PIN, 3);
        FontImage.setMaterialIcon(ageIcon, FontImage.MATERIAL_PERSON_PIN, 3);
        FontImage.setMaterialIcon(emailIcon, FontImage.MATERIAL_EMAIL, 3); 
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        FontImage.setMaterialIcon(password2Icon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
     
           //Définir le label pour l'age +  les contraintes (controle de saisie):
           champ_age.setLabelForComponent(ageIcon);
           champ_age.setMin(5);
           champ_age.setMax(200);
           //FIN Définir le label pour l'age +  les contraintes (controle de saisie):
        //Créer un Container qui contient le label de l'age et le NumericSpinner
         Container BoxAge=new Container(BoxLayout.x()); 
         BoxAge.add(ageIcon);
         BoxAge.add(champ_age);
        
        //Création du bouton d'ajout
        Button BoutonInscription = new Button("Valider");
        BoutonInscription.setUIID("LoginButton");
        
       // ***********************************  Gestion de l'événement suite au clique sur le bouton d'inscription *************************
        BoutonInscription.addActionListener(e -> {
          
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
        
         
         // ****** Controle de saisie ***********
      Boolean formulaire_valide=false; //On va utiliser cette variable pour tester la validité du formulaire
      
    if( (champ_nom.getText().isEmpty()) || ( champ_prenom.getText().isEmpty() ) || ( champ_email.getText().isEmpty() )||( champ_mp.getText().isEmpty() )|| ( champ_confirmation_mp.getText().isEmpty() ) )
    {
        Dialog.show("Erreur !","Veuillez renseigner tous les champs !!!","OK",null);
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
                      if( champ_mp.getText().compareTo(champ_confirmation_mp.getText())!=0 )
                           {  Dialog.show("Erreur !", "Les mots de passe ne sont pas identiques !!!","OK",null); }
                    /*
                     //le mot de passe
                      RE regexp4;
                      regexp4 = new RE("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}"); 
                      if(!regexp4.match(champ_mp.getText()) ) 
                      {  Dialog.show("Erreur !", "le mot de passe doit comporter au moins 8 caractères avec au moins une lettre majuscule lettre miniscule, et un chiffre","OK",null);  }
                      else
                      {}
                     */
                      else //càd si tous les données sont valides
                      {
                       formulaire_valide=true;
                      }
                    
                    
                 }
               }
            }

          }        
   
    }
   
     // ****** FIN Controle de saisie ***********
          
        if(formulaire_valide)
        {
           String pseudo1=ServiceUtilisateur.getInstance().AjouterUtilisateur(champ_nom, champ_prenom, age1.toString(), genre_choisi, champ_email, champ_mp, champ_confirmation_mp,"élève", theme);
        Dialog.show("Félicitation !","Votre inscription a été effectué avec succès !  Votre pseudo : "+pseudo1,"OK",null);
                try {
                    new LoginForm(theme).show();
                } catch (IOException ex) {
                   // Logger.getLogger(PageInscriptionEleve.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                BorderLayout.center(champ_mp).
                        add(BorderLayout.WEST, passwordIcon),
                BorderLayout.center(champ_confirmation_mp).
                        add(BorderLayout.WEST, password2Icon),
                BoutonInscription
               
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
