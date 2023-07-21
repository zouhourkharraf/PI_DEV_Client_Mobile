


package com.mycompany.services;


import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.mycompany.utils.Statics;

import com.codename1.io.JSONParser;
import com.codename1.io.CharArrayReader;
import com.codename1.io.NetworkEvent;

import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Utilisateur;
import com.mycompany.myapp.DashboardAdministrateur;
import com.mycompany.myapp.ProfileForm;
import com.mycompany.utils.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

//import java.util.ArrayList;
//import java.util.Map;
import java.util.List;

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
     private String pseudo_util_ajout=""; //cette variable permet de sauvegarder le pseudo généré automatiquement d'un utilisateur déja ajouté
    void set_pseudo_util_ajout(String a)
    {
    pseudo_util_ajout=a;
    }
   
     String get_pseudo_util_ajout()
    {
    return pseudo_util_ajout;
    }
    
    // ******************** Ajouter Utilisateur(Enseignant et élève) :cette méthode permet d'ajouter un enseignant/élève et retourner son pseudo(qui est généré automatiquement aprés l'ajout de l'utilisateur)
    public String AjouterUtilisateur(TextField champ_nom,TextField champ_prenom,String champ_age,String choix_genre,TextField champ_email,TextField champ_mp,TextField champ_confirmation_mp,String role,Resources res)
    {
        String URL_Ajout_Enseignant="/AjoutEnseignantJSON?nom_util="+champ_nom.getText().toString()+"&prenom_util="+champ_prenom.getText().toString()+"&mot_de_passe_util="+champ_mp.getText().toString()+"&email_util="+champ_email.getText().toString()+"&age_util="+champ_age+"&genre_util="+choix_genre; 
        String URL_Ajout_Eleve="/AjoutEleveJSON?nom_util="+champ_nom.getText().toString()+"&prenom_util="+champ_prenom.getText().toString()+"&mot_de_passe_util="+champ_mp.getText().toString()+"&email_util="+champ_email.getText().toString()+"&age_util="+champ_age+"&genre_util="+choix_genre;         
                
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
                   
    //Action lors de l'exécution de la requête
      req.addResponseListener((e)->{
    byte[] data=(byte[])e.getMetaData();
    String ResponseData=new String(data);
        System.out.println("data ====> "+ResponseData);
        set_pseudo_util_ajout(ResponseData);
    });
   
    //Exécution de la requête : Après l'exécution de la requête on attend la réponse du serveur
    NetworkManager.getInstance().addToQueueAndWait(req);
    
    return (get_pseudo_util_ajout());
    
    }
// ******************** FIN Ajouter Utilisateur(Enseignant et élève) ***********************************
    
    
    
   // **************************** S'authentifier ***********************************************
    public void login(TextField champ_pseudo,TextField champ_mot_de_passe,Resources res)
    {
        //Définir l'url
         String url=Statics.BASE_URL+"/LoginJSON?pseudo_a_verifier="+champ_pseudo.getText()+"&mp_a_verifier="+champ_mot_de_passe.getText();
         req=new ConnectionRequest(url, false);
         //Configurer l'url
           req.setUrl(url);
    
           
            //Action lors de l'exécution de la requête
      req.addResponseListener((e)->{
    JSONParser j=new JSONParser();
    
    String json=new String(req.getResponseData()) + "";
     
   
    if(json.compareTo("Ce pseudo n'existe pas")==0)
    {
    new Dialog().show("Attention !", "Ce pseudo n'existe pas !","OK",null);
    
    }
    else
    {
     if(json.compareTo("Mot de passe invalide")==0)
    {
      new Dialog().show("Erreur !", "Mot de passe invalide !", "OK", null);    
   
    }
    else
     { 
          try
    {
        
      Map<String,Object> user=j.parseJSON(new CharArrayReader(json.toCharArray())); 
        System.out.println("data ==="+user);
    
        if( ( user.get("role_util").toString().compareTo("enseignant")==0) ||( user.get("role_util").toString().compareTo("élève")==0) )
        {
       new ProfileForm(res).show();
        
        }
      else
        {
            
    Session.utilisateur_connecte=new Utilisateur(0 ,"Indéfini", "Indéfini", user.get("pseudo_util").toString(), user.get("mot_de_passe_util").toString(), user.get("email_util").toString(), "I", user.get("role_util").toString());
      System.out.println("uuuuuuuuuuuu"+Session.utilisateur_connecte.toString());
        new DashboardAdministrateur(res).show();
        } 
      }
    catch (Exception ex)
            {
            }
      
      
     }
    }
   
    
    });
   
    //Exécution de la requête : Après l'exécution de la requête on attend la réponse du serveur
    NetworkManager.getInstance().addToQueueAndWait(req);
           
    }
       // **************************** FIN S'authentifier ***********************************************
  
     // **************************** Afficher Utilisateurs ***********************************************  
    //********** Afficher la liste des utilisateurs ************
    public ArrayList<Utilisateur> AfficherListeUtilisateurs()
    {
    ArrayList<Utilisateur> resultat=new ArrayList<>();
    
        //Définir l'url
         String url=Statics.BASE_URL+"/listUtilisateursJSON";
         
         //Configurer l'url
           req.setUrl(url);
    req.setPost(false);
       //Action lors de l'exécution de la requête
      req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser j=new JSONParser();
            
            String json=new String(req.getResponseData());
            
            try
            {
                Map<String,Object> MapUtilisateurs=j.parseJSON(new CharArrayReader(json.toCharArray()));
                
                List<Map<String,Object>> ListOfMapUtilisateurs=(List<Map<String,Object>>)MapUtilisateurs.get("root");
                
                for(Map<String,Object> util :ListOfMapUtilisateurs)
                {
                    Utilisateur utilisateur=new Utilisateur();
                    
                    //Réccupérer les données à partir du Map Utilisateur
                    //L'id qui est un entier on va le prendre comme Float sur CodenameOne sinon il ne va pas fonctionner
                    float id=Float.parseFloat( util.get("id").toString() ); 
                       String nom="Indéfini";
                    if(util.get("nom_util") !=null)
                    {  nom=util.get("nom_util").toString();  }
                       String prenom="Indéfini";
                    if(util.get("prenom_util") !=null)
                    {  prenom=util.get("prenom_util").toString(); }
                    String pseudo=util.get("pseudo_util").toString();
                    String mot_de_passe=util.get("mot_de_passe_util").toString();
                    String email=util.get("email_util").toString();
                    float age=Float.parseFloat( util.get("age_util").toString() ); //Comme pour l'id
                       String genre="I";
                     if(util.get("genre_util") !=null)
                     {  genre=util.get("genre_util").toString(); }
                     
                    String role=util.get("role_util").toString();
                    
                    //Créer l'objet utilisateur avec les données réccupérées
                    utilisateur.setId((int)id);
                    
                    utilisateur.setNom_util(nom);
                    utilisateur.setPrenom_util(prenom);
                    utilisateur.setPseudo_util(pseudo);
                    utilisateur.setMot_de_passe_util(mot_de_passe);
                    utilisateur.setEmail_util(email);
                    utilisateur.setAge_util((int)age);
                    utilisateur.setGenre_util(genre);
                    utilisateur.setRole_util(role);
                    //Ajouter à chaque fois  l'ojet "utilisateur" à l'ArrayList resultat
                    
                    resultat.add(utilisateur);
                    
                }
            } catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            ResultOK=req.getResponseCode()==200;
            req.removeResponseListener(this);
        }
    });
      
        //Exécution de la requête : Après l'exécution de la requête on attend la réponse du serveur
       NetworkManager.getInstance().addToQueueAndWait(req);
     
        return resultat;
    }
    
    //********** Afficher un utilisateur selon son pseudo ************    
    public Utilisateur AfficherUtilisateurParPseudo(String pseudo,Utilisateur utilisateur)
    {
     //Définir l'url
         String url=Statics.BASE_URL+"/AfficherUtilisateurPseudoJSON/"+pseudo;
         //Configurer l'url
           req.setUrl(url);
         String str=new String(req.getResponseData());
           
           req.addResponseCodeListener((evt) -> {
           JSONParser jsonp=new JSONParser();
        try
        {
          Map<String,Object> MapUtilisateur=jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray() ));
          
          utilisateur.setNom_util(MapUtilisateur.get("nom_util").toString());
          utilisateur.setPrenom_util(MapUtilisateur.get("prenom_util").toString()); 
           utilisateur.setPseudo_util(MapUtilisateur.get("pseudo_util").toString());
         utilisateur.setMot_de_passe_util(MapUtilisateur.get("mot_de_passe_util").toString());
         utilisateur.setEmail_util(MapUtilisateur.get("email_util").toString());
          utilisateur.setAge_util(Integer.parseInt(MapUtilisateur.get("age_util").toString()));
           utilisateur.setGenre_util(MapUtilisateur.get("genre_util").toString());
            utilisateur.setRole_util(MapUtilisateur.get("role_util").toString());
        }
        catch(IOException ex)
        {
            System.out.println("Error related to SQL :( "+ex.getMessage());
        }
               
            System.out.println("data ====>"+str);
            
           });
        //Exécution de la requête : Après l'exécution de la requête on attend la réponse du serveur
       NetworkManager.getInstance().addToQueueAndWait(req);       
           
    return utilisateur;
    }
    
     // **************************** FINAfficher Utilisateurs ***********************************************      
    
    
    
    
}
