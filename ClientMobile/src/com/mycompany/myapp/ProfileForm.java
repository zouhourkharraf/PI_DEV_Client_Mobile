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

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUtilisateur;
import com.mycompany.utils.Session;
import java.io.IOException;



/**
 * Represents a user profile in the app, the first form we open after the walkthru
 *
 * @author Shai Almog
 */
public class ProfileForm extends SideMenuBaseFormFrontOffice {
    public ProfileForm(Resources res) throws IOException{
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        tb.getAllStyles().setBgImage(FontImage.createImage("/EspaceUtilisateurMobile.png") );
        Image profilePic=null;
        if( Session.utilisateur_connecte.getGenre_util().compareTo("H")==0 )
          {  profilePic = Image.createImage("/avatar_homme.png");  }
        else
           {  profilePic = Image.createImage("/avatar_femme.jpg");  }   
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        String ProverbeUtilisateur="";
        if( Session.utilisateur_connecte.getRole_util().compareTo("enseignant")==0 )
        {  
        ProverbeUtilisateur="« Enseigner, c’est apprendre deux fois. »\n – Joseph Joubert";
        }
        else
        {
        ProverbeUtilisateur="« La persévérance, c’est ce qui rend l’impossible possible, \nle possible probable et le probable réalisé. »\n– Léon Trotsky";
        }
    
        Container completedTasks = BoxLayout.encloseY(
                        new Label("Bonjour", "CenterSubTitle"),
                        new SpanLabel(ProverbeUtilisateur, "CenterSubTitle")
                        
        );
        completedTasks.setUIID("CompletedTasks");

        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label(Session.utilisateur_connecte.getPseudo_util(), "Title"),
                                    new Label(Session.utilisateur_connecte.getRole_util(), "CenterSubTitle2")
                                )
                            ).add(BorderLayout.WEST, profilePicLabel),
                        GridLayout.encloseIn(1, completedTasks)
                );
       
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
                   
        add(new Label("Informations personelles :", "Title3"));
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        add(new Label("Nom :", "label1"));
        add(new Label(" "+Session.utilisateur_connecte.getNom_util(), "label2"));
        add(new Label("Prénom :", "label1"));
        add(new Label(" "+Session.utilisateur_connecte.getPrenom_util(), "label2"));
        add(new Label("Age :", "label1"));
        add(new Label(" "+Session.utilisateur_connecte.getAge_util(), "label2"));
        add(new Label("Genre :", "label1"));
        add(new Label(" "+Session.utilisateur_connecte.getGenre_util(), "label2"));
        add(new Label("Pseudo :", "label1"));
        add(new Label(" "+Session.utilisateur_connecte.getPseudo_util(), "label2"));
        add(new Label("Email :", "label1"));
        add(new Label(" "+Session.utilisateur_connecte.getEmail_util(), "label2"));
        
        //Bouton "Modifier utilisateur"
         Button ModifierUtilisateur = new Button("Modifier mes informations");
        ModifierUtilisateur.setUIID("CreateNewAccountButton2");
          // ***********************************  Gestion de l'événement suite au clique sur le bouton de connexion *************************
       ModifierUtilisateur.addActionListener(e -> {
       try {
           
           if( Session.utilisateur_connecte.getRole_util().compareTo("enseignant")==0 )
               {  new PageModifEnseignant(res, Session.utilisateur_connecte).show(); }
           else
              {  new PageModifEleve(res, Session.utilisateur_connecte).show(); }     
       }catch (IOException ex) { }  
        });
        
         // ***********************************  FIN Gestion de l'événement suite au clique sur le bouton de connexion *************************
        
        
        add(ModifierUtilisateur);
      
        setupSideMenu(res);
    }
    
    
    
    
  /*  
    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(),  first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }
    
    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if(first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

*/


    @Override
    protected void showOtherForm(Resources res) {
        try {
            new ProfileForm(res).show();
        } catch (IOException ex) {
           
        }
    }
}
