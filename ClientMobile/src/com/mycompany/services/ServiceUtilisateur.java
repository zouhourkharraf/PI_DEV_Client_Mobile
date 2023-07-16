


package com.mycompany.services;


import com.codename1.components.OnOffSwitch;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.mycompany.utils.Statics;
import com.codename1.util.regex.RE;
import com.codename1.util.regex.RESyntaxException;

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
          
    private String pseudo_util_ajout=""; //cette variable permet de sauvegarder le pseudo généré automatiquement d'un utilisateur déja ajouté
     
    void set_pseudo_util_ajout(String a)
    {
    pseudo_util_ajout=a;
    }
   
     String get_pseudo_util_ajout()
    {
    return pseudo_util_ajout;
    }
    
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
    
    // ******************** Ajouter Utilisateur(Enseignant et élève) :cette méthode permet d'ajouter un enseignant/élève et retourner son pseudo(qui est généré automatiquement aprés l'ajout de l'utilisateur)
    public String AjouterUtilisateur(TextField champ_nom,TextField champ_prenom,String champ_age,String choix_genre,TextField champ_email,TextField champ_mp,TextField champ_confirmation_mp,String role,Resources res)
    {
        String URL_Ajout_Enseignant="http://localhost/3A/PI_DEV_WEB/Client_Symfony_Gestion_des_utilisateurs/ClientSymfony/public/index.php/AjoutEnseignantJSON?nom_util="+champ_nom.getText().toString()+"&prenom_util="+champ_prenom.getText().toString()+"&mot_de_passe_util="+champ_mp.getText().toString()+"&email_util="+champ_email.getText().toString()+"&age_util="+champ_age+"&genre_util="+choix_genre; 
        String URL_Ajout_Eleve="http://localhost/3A/PI_DEV_WEB/Client_Symfony_Gestion_des_utilisateurs/ClientSymfony/public/index.php/AjoutEleveJSON?nom_util="+champ_nom.getText().toString()+"&prenom_util="+champ_prenom.getText().toString()+"&mot_de_passe_util="+champ_mp.getText().toString()+"&email_util="+champ_email.getText().toString()+"&age_util="+champ_age+"&genre_util="+choix_genre;         
                
          //définir l'url ---> selon le rôle de l'utilisateur(enseignant ou élève)
        if(role.compareTo("élève")==0 )
        {
        String url=Statics.BASE_URL+URL_Ajout_Eleve;
          //Configurer l'url
           req.setUrl(url);
        }
        else
        {
        String url=Statics.BASE_URL+URL_Ajout_Enseignant;
          //Configurer l'url
            req.setUrl(url);
        }
                   
  //Action lors de l'exécution de l'URL
      req.addResponseListener((e)->{
    byte[] data=(byte[])e.getMetaData();
    String ResponseData=new String(data);
        System.out.println("data ====> "+ResponseData);
        set_pseudo_util_ajout(ResponseData);
    }
    );
   
    //Après l'exécution de la requête on attend la réponse du serveur
    NetworkManager.getInstance().addToQueueAndWait(req);
    
    return (get_pseudo_util_ajout());
    
    }

   
    
    
    
    
    
}
