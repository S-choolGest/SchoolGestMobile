/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.db.Database;
import com.codename1.io.Log;
import com.codename1.ui.Component;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import static com.mycompany.myapp.MyApplication.theme;
import com.mycompany.myapp.gui.ListEncadrementProfForm;
import com.mycompany.myapp.gui.ListPfeForm;
import entities.Utilisateur;
import java.io.IOException;

/**
 *
 * @author actar
 */
public class BaseForm extends Form {
     public Form current;
  
    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

   
    
    
    
    //sidemenu
   protected void addSideMenu(Resources res) {
       Toolbar.setGlobalToolbar(true);
        Toolbar tb = getToolbar();
        setUIID("Home");
        /*Form hi = new Form("Toolbar", new BoxLayout(BoxLayout.Y_AXIS));
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_WARNING, s);
        hi.getToolbar().addCommandToLeftBar("Left", icon, (e) -> Log.p("Clicked"));
        hi.getToolbar().addCommandToRightBar("Right", icon, (e) -> Log.p("Clicked"));
        hi.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        hi.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));
        hi.show();*/
        //Image img = res.getImage("Novant.png");
       
        
       
        

      
      
      
      if(Utilisateur.current_user.getRoles().contains("ROLE_PROFESSEUR")){
      tb.addMaterialCommandToSideMenu("Absence", FontImage.MATERIAL_CALENDAR_TODAY, e-> new GuiAbsence(current,Utilisateur.current_user.getId()).show());
      
      tb.addMaterialCommandToSideMenu("Encadrement", FontImage.MATERIAL_WORK, e-> new ListEncadrementProfForm(Utilisateur.current_user,theme).show());
      // */
           }
 if(Utilisateur.current_user.getRoles().contains("ROLE_MODERATEUR")){
      tb.addMaterialCommandToSideMenu("Affectaion", FontImage.MATERIAL_ARROW_FORWARD, e-> new GuiAffectation(current,Utilisateur.current_user.getId()).show());
 }
 if(Utilisateur.current_user.getRoles().contains("ROLE_ETUDIANT")){
       tb.addMaterialCommandToSideMenu("Pfe", FontImage.MATERIAL_BOOK, e-> new ListPfeForm(Utilisateur.current_user,theme).show());
       tb.addMaterialCommandToSideMenu("Absence", FontImage.MATERIAL_BOOK, e-> new GuiAbsenceEtudiant(current,Utilisateur.current_user.getId()).show());
                            
                            }

        tb.addMaterialCommandToSideMenu("se déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            try {
                Database db = Database.openOrCreate("School.db");
                db.execute("delete from appstates");
                Utilisateur.current_user = null;
                new SignInForm(res).show();
            } catch (IOException ex) {
            }
        });
   }
    
}

