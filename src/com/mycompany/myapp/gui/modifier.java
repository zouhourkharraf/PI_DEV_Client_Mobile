/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Evenement;
import com.mycompany.myapp.services.ServiceCatgory;
/**
 *
 * @author HP
 */
public class modifier extends Form {
    public modifier(Form previous) {
        setTitle("Modifier Categorie");
        setLayout(BoxLayout.y());
        
     TextField ID = new TextField("","ID");
        TextField tnom = new TextField("","Nom");
        TextField tlieu = new TextField("","Lieu");
        TextField tdesc = new TextField("","Desc");
        
        Button btnValider = new Button("Edit ");
          
         
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tnom.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                   try {
                       float id = Float.parseFloat(ID.getText().toString());
                        Evenement t;
                      
                        t = new Evenement((int) id,tnom.getText(),tlieu.getText(),tdesc.getText());
                        if( ServiceCatgory.getInstance().modifierCategory(t))
                        {
                           Dialog.show("Success","Congrats!!",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
           }
        });
        
        addAll(ID,tnom,tlieu,tdesc,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
             
    }
    
}
