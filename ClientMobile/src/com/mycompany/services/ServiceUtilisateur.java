


package com.mycompany.services;


import com.codename1.components.OnOffSwitch;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.mycompany.utils.Statics;


/**
 *
 * @author MMD
 */
public class ServiceUtilisateur {
  
  //****** Application de patron de conception Singleton   
 
    public static ServiceUtilisateur instance=null;
     public static boolean ResultOK=true;
     
         //l'attribut req de type "Connection Request" ----> C'et la demande de conncexion au serveur ,utilisé pour qu'on puissse après conssommer les "Web Services"
          private ConnectionRequest req;
    
    public static ServiceUtilisateur getInstance()
    {
    if(instance==null)
    {
     instance =new ServiceUtilisateur();
    }
     return instance;
    }
    
    public ServiceUtilisateur()
    {
    req=new ConnectionRequest();
    }
          
  //****** FIN Application de patron de conception Singleton    
    
    // ******************** Ajouter Utilisateur(Enseignant et élève)
    public void AjouterUtilisateur(TextField champ_nom,TextField champ_prenom,String champ_age,OnOffSwitch choix_genre,TextField champ_email,TextField champ_mp,TextField champ_confirmation_mp,String role,Resources res)
    {
        //définir le genre choisi par l'utilisateur
        String genre_choisi="";
        if(choix_genre.isValue())
        {
            genre_choisi=choix_genre.getOn();
        }
        else
        {
        genre_choisi=choix_genre.getOff();
        }
            
        
    String url=Statics.BASE_URL+"/3A/PI_DEV_WEB/Client_Symfony_Gestion_des_utilisateurs/ClientSymfony/public/index.php/AjoutEnseignantJSON?nom_util="+champ_nom.getText().toString()+"&prenom_util="+champ_prenom.getText().toString()+"&mot_de_passe_util="+champ_mp.getText().toString()+"&email_util="+champ_email.getText().toString()+"&age_util="+champ_age+"&genre_util="+genre_choisi;
   
    req.setUrl(url);
   
    // ****** Controle de saisie ***********
    if( (champ_nom.getText().isEmpty()) || ( champ_prenom.getText().isEmpty() ) || ( champ_age.getText().isEmpty() ) || ( champ_email.getText().isEmpty() )||( champ_mp.getText().isEmpty() )|| ( champ_confirmation_mp.getText().isEmpty() ) )
    {
        Dialog.show("Erreur !","Veuillez renseigner tous les champs !!!","OK",null);
    }//si les champs obligatoires ne sont pas tous remplies on affiche donc un message d'erreur
    else 
    {
      if( champ_mp.getText().compareTo(champ_confirmation_mp.getText())!=0 )
              {
                 
                            Dialog.show("Erreur !", "Les mots de passe ne sont pas identiques !!!","OK",null);
              }
    }
    
     // ****** FIN Controle de saisie ***********
    
  //Action lors de l'exécution de l'URL
    req.addResponseListener((e)->{
    byte[] data=(byte[])e.getMetaData();
    String ResponseData=new String(data);
        System.out.println("data ====> "+ResponseData);
        
    }
    );
    
    //Après l'exécution de la requête on attend la réponse du serveur
    NetworkManager.getInstance().addToQueueAndWait(req);
    
    
    
    }
    
    
    // ******************** FIN Ajouter Utilisateur(Enseignant et élève)    
    
    
    
    
}
