


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
import com.mycompany.myapp.LoginForm;
import com.mycompany.myapp.ProfileForm;
import com.mycompany.myapp.VerifierCodeSecret;
import com.mycompany.utils.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Random;



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
    
    // Variables et méthodes pour l'ajout d'un utilisateur
    private String pseudo_util_ajout=""; //cette variable permet de sauvegarder le pseudo généré automatiquement d'un utilisateur déja ajouté
    void set_pseudo_util_ajout(String a)
    {
    pseudo_util_ajout=a;
    }
   
     String get_pseudo_util_ajout()
    {
    return pseudo_util_ajout;
    }
      // FIN Variables et méthodes pour l'ajout d'un utilisateur 
     
    
       // Variables et méthodes pour la modification d'un utilisateur
    private String pseudo_util_modification=""; //cette variable permet de sauvegarder le pseudo généré automatiquement d'un utilisateur déja modifié
  
    void set_pseudo_util_modification(String a)
    {
    pseudo_util_modification=a;
    }
   
     String get_pseudo_util_modification()
    {
    return pseudo_util_modification;
    }
      // FIN Variables et méthodes pour la modification d'un utilisateur 
     
     
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
    
 
  
     
  // ******************** Modifier Utilisateur(Enseignant et élève) :cette méthode permet de modifier les informations d'un enseignant/élève et de retourner son pseudo(qui est généré automatiquement aprés la modification de l'utilisateur)
    
     public String ModifierUtilisateur(int id_utilisateur,TextField champ_nom,TextField champ_prenom,String champ_age,String choix_genre,TextField champ_email,String role,Resources res)
    {
        String URL_Modification_Enseignant="/ModifierEnseignantJSON/"+id_utilisateur+"?nom_util="+champ_nom.getText()+"&prenom_util="+champ_prenom.getText()+"&email_util="+champ_email.getText()+"&age_util="+champ_age+"&genre_util="+choix_genre;
        String URL_Modification_Eleve="/ModifierEleveJSON/"+id_utilisateur+"?nom_util="+champ_nom.getText()+"&prenom_util="+champ_prenom.getText()+"&email_util="+champ_email.getText()+"&age_util="+champ_age+"&genre_util="+choix_genre;       
                
          //définir l'url ---> selon le rôle de l'utilisateur(enseignant ou élève)
        if(role.compareTo("élève")==0 )
        {
        String url=Statics.BASE_URL+URL_Modification_Eleve;
          //Configurer l'url
           req.setUrl(url);
        }
        else
        {
        String url=Statics.BASE_URL+URL_Modification_Enseignant;
          //Configurer l'url
            req.setUrl(url);
        }
                   
    //Action lors de l'exécution de la requête
      req.addResponseListener((e)->{
    byte[] data=(byte[])e.getMetaData();
    String ResponseData=new String(data);
        System.out.println("data ====> "+ResponseData);
        set_pseudo_util_modification(ResponseData);
    });
   
    //Exécution de la requête : Après l'exécution de la requête on attend la réponse du serveur
    NetworkManager.getInstance().addToQueueAndWait(req);
    
    return (get_pseudo_util_modification());
    
    }
// ******************************************** FIN Modifier Utilisateur(Enseignant et élève) ***********************************
    
     
    
    
    
    
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
       // System.out.println("data ==="+user);
    
        if( ( user.get("role_util").toString().compareTo("enseignant")==0) ||( user.get("role_util").toString().compareTo("élève")==0) )
        {
       Session.utilisateur_connecte=new Utilisateur( (int)Float.parseFloat(user.get("id").toString()) , (int)Float.parseFloat(user.get("age_util").toString()) ,user.get("nom_util").toString(),user.get("prenom_util").toString() , user.get("pseudo_util").toString(), user.get("mot_de_passe_util").toString(), user.get("email_util").toString(), user.get("genre_util").toString(), user.get("role_util").toString());
       new ProfileForm(res).show();
        Session.EspaceConnecte=false;
        }
      else
        { 
    Session.utilisateur_connecte=new Utilisateur(0 ,"Indéfini", "Indéfini", user.get("pseudo_util").toString(), user.get("mot_de_passe_util").toString(), user.get("email_util").toString(), "I", user.get("role_util").toString());
   //   System.out.println("uuuuuuuuuuuu"+Session.utilisateur_connecte.toString());
        new DashboardAdministrateur(res).show();
        Session.EspaceConnecte=true;
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
  
    // *************************** Se déconnecter ***************************************
    public void SeDeconnecter(Resources res)
    {
        try {
            Session.utilisateur_connecte=null;
            Session.EspaceConnecte=null;
            new LoginForm(res).show();
            
        } catch (IOException ex) { }
         
        
    
    }
    
      // *************************** FIN Se déconnecter ***************************************
    
    
    
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
    
       // **************************** FINAfficher Utilisateurs *********************************************** 
    
    //***************************** Gestion de la fonctionnalié mot de passe oublié ************************************ 
    
    
    public void VerifierPseudoUtilPourMPoublie(String pseudo2,Resources res)
    {
        //Définir l'url
         String url=Statics.BASE_URL+"/AfficherUtilisateurPseudoJSON?pseudo_pour_mp_oublie="+pseudo2;
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
          try
         {
        
      Map<String,Object> user=j.parseJSON(new CharArrayReader(json.toCharArray())); 
       // System.out.println("data ==="+user);
      
        if( ( user.get("role_util").toString().compareTo("enseignant")==0) ||( user.get("role_util").toString().compareTo("élève")==0) )
        {
       Session.utilisateur_mp_oublie=new Utilisateur( (int)Float.parseFloat(user.get("id").toString()) , (int)Float.parseFloat(user.get("age_util").toString()) ,user.get("nom_util").toString(),user.get("prenom_util").toString() , user.get("pseudo_util").toString(), user.get("mot_de_passe_util").toString(), user.get("email_util").toString(), user.get("genre_util").toString(), user.get("role_util").toString());
       System.out.println("util ::::::::::::::::::::::::::::::"+Session.utilisateur_mp_oublie.toString());
        }
        else 
        {
            //si il est "administrateur" alors il possède quelques attributs qui sont "null" la chose qui permet de créer un problème même si on a fait le catch pour l'execption"NullPointerExeption" donc on va affecter d'autres valeurs pour les attributs qui sont suceptibles d'être "null"
    Session.utilisateur_mp_oublie=new Utilisateur((int)Float.parseFloat(user.get("id").toString()),0 ,"Indéfini", "Indéfini", user.get("pseudo_util").toString(), user.get("mot_de_passe_util").toString(), user.get("email_util").toString(), "I", user.get("role_util").toString());
        }
       //****************  Variables pour créer le texte du message
            int CodeSecret=GenererCodeSecret();
            String genre_utilisateur=""; //cette variable va contenir Monsieur ou Madame selon le genre de l'utilisateur réccupéré
            if( Session.utilisateur_mp_oublie.getRole_util().compareTo("administrateur") != 0) //si l'utilisateur n'est administrateur donc il possède un genre
            {
             if( Session.utilisateur_mp_oublie.getGenre_util().compareTo("F") ==0 ) 
             {
             genre_utilisateur="Madame";
             }
             else
             {
               genre_utilisateur="Monsieur";
             }
            }
             //sinon càd si l'utilisateur est administrateur (donc son attribut de genre contient null) la variable genre_utilisateur va rester vide  
            String nom_utilisateur="",prenom_utilisateur="";
             
             if( Session.utilisateur_mp_oublie.getRole_util().compareTo("administrateur") != 0) //si l'utilisateur n'est administrateur donc il possède un nom et un prénom
             {
             nom_utilisateur= Session.utilisateur_mp_oublie.getNom_util(); //---> le nom de l'utilisateur réccupéré 
             prenom_utilisateur= Session.utilisateur_mp_oublie.getPrenom_util(); //---> le prénom de l'utilisateur réccupéré 
             }
              //sinon càd si l'utilisateur est administrateur (donc il n'a pas de nom ni un prénom) les deux variables nom_utilisateur et prenom_utilisateur vont rester vides
             
              //****************  FIN Variables pour créer le texte du message    
                     // **************** Variables pour la configuration de l'email
             String email_utilisateur=Session.utilisateur_mp_oublie.getEmail_util();
             
             //construire le corps du message en html
             String message= "<h4>Bonjour "+genre_utilisateur+"</h4><h4>"+nom_utilisateur+" "+prenom_utilisateur+"</h4><h4>Vore code est : "+CodeSecret+"</h4><h4>L'équipe Magic Book</h4>";
             
             Mail.send("magicbook835@gmail.com", "yijfdvaakbioplfg",email_utilisateur, "Magic Book : Récupération du mot de passe", message); //send est une méthode statique 

              try {
                 new VerifierCodeSecret(res, CodeSecret).show();
                } catch (IOException ex) { }
           
      }
    catch (Exception ex)
            {
            }
      
    }
    
    });
   
    //Exécution de la requête : Après l'exécution de la requête on attend la réponse du serveur
    NetworkManager.getInstance().addToQueueAndWait(req);
               
}
    
    // *************** cette méthode permet de générer un nombre aléatoire compris entre 10000 et 99999
    private int GenererCodeSecret()
   {    
   Random rand = new Random(); // créer un  objet de type Random
   int Code = rand.nextInt(99999 - 10000 + 1) + 10000; //générer un nombre aléatoire entre 10000 et 99999
   return Code;
   }
    
    
    //Modifier mot de passe utilisateur
        
     public Boolean ModifierMotDePasseUtilisateur(int id_utilisateur,String nouveau_mp_utilisateur,Resources res)
    {
        String id_string=String.valueOf(id_utilisateur);
        //Définir l'url
        String url=Statics.BASE_URL+"/modifierMPJSON/"+id_string+"?nouveau_mot_de_passe_util="+nouveau_mp_utilisateur;
        //String url=Statics.BASE_URL+"/modifierMPJSON/1?nouveau_mot_de_passe_util=88888888Aa";
        req=new ConnectionRequest(url, false);   
        //Configurer l'url
            req.setUrl(url);
            
         //Action lors de l'exécution de la requête
      req.addResponseListener((e)->{
    byte[] data=(byte[])e.getMetaData();
    String ResponseData=new String(data);
        System.out.println("data ====> "+ResponseData);
       
    });   
             
        //Exécution de la requête : Après l'exécution de la requête on attend la réponse du serveur
       NetworkManager.getInstance().addToQueueAndWait(req); 
              
              return ResultOK;
    
    }
    
    //***************************** FIN Gestion de la fonctionnalié mot de passe oublié ************************************  
    
       
    
  
// ********************************* Supprimer un utilisateur *******************************************
    //cette méthode permet de supprimer un utilisateur par son id 
    public Boolean SupprimerUtilisateur(int id_utilisateur)
    {
    //Définir l'url
         String url=Statics.BASE_URL+"/SupprimerUtilJSON/"+String.valueOf(id_utilisateur);
         //Configurer l'url
           req.setUrl(url);
    
        //Action lors de l'exécution de la requête
      req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
      req.removeResponseCodeListener(this);
           
        }
    });     
             
        //Exécution de la requête : Après l'exécution de la requête on attend la réponse du serveur
       NetworkManager.getInstance().addToQueueAndWait(req); 
              
              return ResultOK;
    }
    
    
    
    // ********************************* FIN Supprimer un utilisateur ******************************************* 
    
}
