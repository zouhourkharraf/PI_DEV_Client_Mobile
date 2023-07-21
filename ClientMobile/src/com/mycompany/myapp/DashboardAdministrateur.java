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

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateur;
import com.mycompany.services.ServiceUtilisateur;
import com.mycompany.utils.Session;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author Shai Almog
 */
public class DashboardAdministrateur extends SideMenuBaseFormBackOffice {
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public DashboardAdministrateur(Resources res) throws IOException {
        super(new BorderLayout());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("user-picture.jpg");        
        Image tintedImage = Image.createImage(profilePic.getWidth(), profilePic.getHeight());
        Graphics g = tintedImage.getGraphics();
        g.drawImage(profilePic, 0, 0);
        g.drawImage(res.getImage("gradient-overlay.png"), 0, 0, profilePic.getWidth(), profilePic.getHeight());
        
        tb.getUnselectedStyle().setBgImage(tintedImage);
        
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Button settingsButton = new Button("");
        settingsButton.setUIID("Title");
        FontImage.setMaterialIcon(settingsButton, FontImage.MATERIAL_SETTINGS);
        
        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent = 
                BorderLayout.north(
                    BorderLayout.west(menuButton).add(BorderLayout.EAST, settingsButton)
                ).
                add(BorderLayout.CENTER, space).
                add(BorderLayout.SOUTH, 
                        FlowLayout.encloseIn(
                                new Label("Administrateur connecté :", "WelcomeBlue"),
                                new Label(Session.utilisateur_connecte.getPseudo_util(), "WelcomeWhite")
                        ));
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);
        
        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);
        add(BorderLayout.NORTH, separator);
             
      Label Tire1=new Label(" Liste des utilisateurs","WelcomeBlue");
    
        
        Container by = BoxLayout.encloseY(Tire1);
          add(BorderLayout.CENTER, 
                by);
         // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
          
        setupSideMenu(res);
        
        // ******************* Affichage de la liste des utilisateurs *******************************************
       ArrayList<Utilisateur> ListeUtilisateurs= ServiceUtilisateur.getInstance().AfficherListeUtilisateurs();
    
         System.out.println(ListeUtilisateurs.toString());
       //Parcourir la liste des utilisateurs de l'ArrayList "ListeUtilisateurs" et créer à chaque fois un élément graphique avec les éléments courants de la liste un par un et ajouter cet élémént ensuite à la page f
       for(Utilisateur util : ListeUtilisateurs)
         {        
       //      System.out.println(util.toString());
         Container C_resultat = addItemToList(util);  //cette méthode publique permet de créer  un élément graphique avec les données de l'utilisateur "util" passé en paramètres et l'ajouter à la Form(page) principale
        by.add(C_resultat);
     // by.add(new Label("bonjour Salut"));
        refreshTheme();
         
        }
 
        
        
        // ******************* FIN Affichage de la liste des utilisateurs *******************************************   
        
        
    }
    
       
       
    //La fonction addItem  //cette méthode va ous retourner un container qui représente une ligne utilisateur 
        public Container addItemToList(Utilisateur utilisateur) throws IOException
        {
           //  ImageViewer image_viewer_update=null;  //Créer un ImageViewer
             // ImageViewer image_viewer_delete=null;  //Créer un ImageViewer
            //  Image icone_update =Image.createImage( FontImage.MATERIAL_UPDATE); 
              Container Container_enregistrement_utilisateur = new Container(new BoxLayout(BoxLayout.X_AXIS),"TextField"); //le container qui va contenir la ligne des données de l'utilisateur
              ImageViewer image_viewer_update = new ImageViewer(Image.createImage("/icone_modifier.png"));
              ImageViewer image_viewer_delete = new ImageViewer(Image.createImage("/icone_supprimer.png"));
            
          Container Container_donnees = new Container(new BoxLayout(BoxLayout.Y_AXIS));//est le container qui va contenir des données de l'utilisateur seulement( id + pseudo ) dans les icones detete et update  
          Container Container_id = new Container(new BoxLayout(BoxLayout.X_AXIS)); //est le Container de l'id (label +id utilisateur)
          Container Container_pseudo = new Container(new BoxLayout(BoxLayout.X_AXIS)); //est le Container du pseudo (label +pseudo utilisateur)
         
          Label label_id = new Label("Id  : ");
          Label label_id_utilisateur = new Label( String.valueOf(utilisateur.getId()) ); 
          Container_id.add(label_id);
          Container_id.add(label_id_utilisateur);
          
          Label label_pseudo = new Label("Pseudo : ");
          Label label_pseudo_utilisateur = new Label(utilisateur.getPseudo_util() );
          Container_pseudo.add(label_pseudo);
          Container_pseudo.add(label_pseudo_utilisateur);
        
          Container_donnees.add(Container_id);
          Container_donnees.add(Container_pseudo);
          
          Container_enregistrement_utilisateur.add(Container_donnees);
          Container_enregistrement_utilisateur.add(image_viewer_update);
          Container_enregistrement_utilisateur.add(image_viewer_delete);
          Container_enregistrement_utilisateur.setLeadComponent(label_id);
          
          return Container_enregistrement_utilisateur;
         
         
         }
        
    private Image colorCircle(int color) {
        int size = Display.getInstance().convertToPixels(3);
        Image i = Image.createImage(size, size, 0);
        Graphics g = i.getGraphics();
        g.setColor(color);
        g.fillArc(0, 0, size, size, 0, 360);
        return i;
    }
    
    
   //à voir  
    @Override
    protected void showOtherForm(Resources res) {
        try {
            new DashboardAdministrateur(res).show();
        } catch (IOException ex) {
          
        }
    }

    private XYMultipleSeriesRenderer createChartMultiRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        for(int color : COLORS) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
            r.setFillPoints(false);
            XYSeriesRenderer.FillOutsideLine outline = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BELOW);
            outline.setColor(color);
            r.addFillOutsideLine(outline);
            r.setLineWidth(5);
        }
        renderer.setPointSize(5f);
        renderer.setLabelsColor(0);
        renderer.setBackgroundColor(0xffffffff);
        renderer.setApplyBackgroundColor(true);
        renderer.setAxesColor(COLORS[0]);

        renderer.setXTitle("");
        renderer.setYTitle("");
        renderer.setAxesColor(0xcccccc);
        renderer.setLabelsColor(0);
        renderer.setXLabels(5);
        renderer.setYLabels(5);
        renderer.setShowGrid(true);
        
        renderer.setMargins(new int[] {0, 0, 0, 0});
        renderer.setMarginsColor(0xffffff);

        renderer.setShowLegend(false);
        
        renderer.setXAxisMin(3);
        renderer.setXAxisMax(8);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(10);
        return renderer;
    }
    
    
    
    
    
    
}
