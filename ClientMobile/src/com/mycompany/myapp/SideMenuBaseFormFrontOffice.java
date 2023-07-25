 
package com.mycompany.myapp;

import com.codename1.components.ToastBar;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUtilisateur;
import com.mycompany.utils.Session;
import java.io.IOException;


/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseFormFrontOffice extends Form {

    public SideMenuBaseFormFrontOffice(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseFormFrontOffice(String title) {
        super(title);
    }

    public SideMenuBaseFormFrontOffice() {
    }

    public SideMenuBaseFormFrontOffice(Layout contentPaneLayout){
        super(contentPaneLayout);
    }
    
    public void setupSideMenu(Resources res) throws IOException {
        Image profilePic =  Image.createImage("/grand_logo_finale.png");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(NomEspace(), profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop2");
        
        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu(" "+Session.utilisateur_connecte.getPseudo_util(), FontImage.MATERIAL_ACCOUNT_CIRCLE,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Les réclamations", FontImage.MATERIAL_REPLAY,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Les Cours", FontImage.MATERIAL_BOOK,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Les formations", FontImage.MATERIAL_BOOK,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Les activités", FontImage.MATERIAL_TRENDING_UP,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, (evt) -> {ServiceUtilisateur.getInstance().SeDeconnecter(res);
        }
       
        );
    }
    
    protected abstract void showOtherForm(Resources res);
    
    private String NomEspace()
    {
    if( Session.utilisateur_connecte.getRole_util().compareTo("enseignant")==0 )
      { return (" Espace Enseignant"); }
    else
      { return (" Espace Élève"); }
    
    }
}
