/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

import com.codename1.ui.Form;
import java.io.IOException;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.html.DocumentInfo;
import com.mycompany.myapp.entities.Evenement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * HP
 */
public class ServiceCatgory {
    public static ServiceCatgory instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Evenement> listCategory=new ArrayList<>();

    public ServiceCatgory() {
        req = new ConnectionRequest();
    }

    public static ServiceCatgory getInstance() {
        if (instance == null) {
            instance = new ServiceCatgory();
        }
        return instance;
    }
//ajout
    public boolean addCategory(Evenement t) {

        String nom_ev = t.getNom_ev();
      String lieu_ev=t.getLieu_ev();
      String desc_ev=t.getDesc_ev();
      //String Traitement=t.getTraitement();
        
       
    //  String url =  "http://127.0.0.1:8000/"+ "addCategMobile/" + type + "/" + description;
    String url = "http://localhost/PI_DEV_Symfony/public/index.php/addEventJSON/new?nom_ev=" + nom_ev + "&lieu_ev=" + lieu_ev + "&desc_ev=" + desc_ev;
        req.setUrl(url);
        req.setPost(false);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }


    /********** Affichage********************/
      
     public ArrayList<Evenement> affichageCategory()
    {

        ArrayList<Evenement> result = new ArrayList<>();
        String url ="http://localhost/PI_DEV_Symfony/public/index.php/AllEvents";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try 
                {
                    Map<String,Object>mapMaison = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> ListOfMaps = (List<Map<String,Object>>) mapMaison.get("root");
                    System.out.println(mapMaison);
                    for(Map<String, Object> obj : ListOfMaps)
                    {
                        System.out.println(obj);
                       Evenement c = new Evenement();
                       
                        String nom_ev = obj.get("nom_ev").toString();
                         float id = Float.parseFloat(obj.get("id").toString());
                         String lieu_ev = obj.get("lieu_ev").toString();
                         String desc_ev = obj.get("desc_ev").toString();

                         
                       

                       c.setId((int)id);
                        c.setNom_ev(nom_ev);                        
                        c.setLieu_ev(lieu_ev); 
                        c.setDesc_ev(desc_ev);
                        
                        result.add(c);
                        System.out.println(c.getNom_ev()+c.getLieu_ev()+c.getDesc_ev());
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;
    
    }    

    
     /*********************************************update***************************************************/    
    public boolean modifierCategory(Evenement t) {
        String nom_ev = t.getNom_ev();
        String url= "http://localhost/PI_DEV_Symfony/public/index.php/updateEventJSON/"+t.getId()+"?&nom_ev="+t.getNom_ev()+"&lieu_ev="+t.getLieu_ev()+"&desc_ev="+t.getDesc_ev();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOK;
        
    }
    //////////////////////////////////delete///////////////////////////////////////
    public boolean deleteCategory(int id ) {
        String url = "http://localhost/PI_DEV_Symfony/public/index.php/deleteEventJSON/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOK;
    }
   
}

    
   

