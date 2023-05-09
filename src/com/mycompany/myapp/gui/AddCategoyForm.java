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
 * @author Sondes
 */
public class AddCategoyForm extends Form {
    
    public AddCategoyForm(Form previous) {
        setTitle("Ajoute un nouveau evenement");
        setLayout(BoxLayout.y());
        
        // Create input fields and button
        
        TextField tfNom = new TextField("", "Nom");
        TextField tfLieu = new TextField("", "Lieu");
        TextField tfDesc = new TextField("", "Desc");
        Button btnAdd = new Button("Add");
        
        // Add action listener to button
        btnAdd.addActionListener(evt -> {
            if (tfNom.getText().length() == 0 || tfLieu.getText().length() == 0 ||
                    tfDesc.getText().length() == 0) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {
                try {
                    // Create new category object and add it to the server
                    Evenement category = new Evenement(tfNom.getText(), tfLieu.getText(),
                            tfDesc.getText());
                    if (ServiceCatgory.getInstance().addCategory(category)) {
                        Dialog.show("Success", "Congrats", new Command("OK"));
                    } else {
                        Dialog.show("Error", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("Error", "Invalid input", new Command("OK"));
                }
            }
        });
        
        // Add input fields and button to the form
        addAll(tfNom, tfLieu, tfDesc,btnAdd);
        
        // Add back button to the toolbar
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> previous.showBack());
    }
}
   

    

