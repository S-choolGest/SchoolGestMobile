/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.db.Database;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import static com.mycompany.myapp.MyApplication.theme;
import com.mycompany.myapp.gui.ListPfeForm;
import entities.Absence;
import entities.Classe;
import entities.Etudiant;
import entities.Matiere;
import entities.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import service.AbsenceService;

/**
 *
 * @author MehdiS
 */
public class GuiAbsenceEtudiant extends Form {
     Form current;

    
    
    public GuiAbsenceEtudiant(Form previous,int id ){
      
        current = this;
         Toolbar tb = getToolbar();
         tb.addMaterialCommandToSideMenu("Pfe", FontImage.MATERIAL_BOOK, e-> new ListPfeForm(Utilisateur.current_user,theme).show());
       tb.addMaterialCommandToSideMenu("Absence", FontImage.MATERIAL_BOOK, e-> new GuiAbsenceEtudiant(current,Utilisateur.current_user.getId()).show());
                            
                            

        tb.addMaterialCommandToSideMenu("se déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            try {
                Database db = Database.openOrCreate("School.db");
                db.execute("delete from appstates");
                Utilisateur.current_user = null;
                new SignInForm(theme).show();
            } catch (IOException ex) {
            }
        });
   
       
              setTitle("affichage   absence");
        setLayout(BoxLayout.y());
        
        for (Absence  r : new AbsenceService().getAllAbsenceEtudiant (id))
        {
            
            this.add(addItem(r));
        }
        this.show();
              
    }
    public Container addItem(Absence c){
          
        Container cn1=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cn2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
       
               Label labEtat=new Label(String.valueOf(c.getetat()));
               Label labDate=new Label(c.getDateString(c.getDate()));
               Label labHeure=new Label(c.getHeureString(c.getHeure()));
               Label labEtudiant=new Label(String.valueOf(c.getIdEtudiant().getNom()));
               Label labClasse=new Label(String.valueOf(c.getClasse().getNomClasse()));
               Label labMatiere=new Label(c.getMatiere().getNomMatiere());
               
              
     Button btn=new Button("Editer");
       cn2.add(btn);
      
        cn1.add(labEtat);
        cn1.add(labHeure);
        cn1.add(labEtudiant);
        cn1.add(labClasse);
        cn1.add(labMatiere);
        cn1.add(labDate);
        cn1.add("-----------------------");
       
        cn1.setLeadComponent(btn);
        return cn1;
                
    }
    }
