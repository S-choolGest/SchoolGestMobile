/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.db.Database;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Stroke;
import service.TwilioSMS;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import static com.mycompany.myapp.MyApplication.theme;
import entities.Absence;
import entities.Classe;
import entities.Etudiant;
import entities.Matiere;
import entities.Professeur;
import entities.Utilisateur;
import entities.affectation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import service.AbsenceService;
import service.AffectationService;
import service.TwilioSMS;

/**
 *
 * @author MehdiS
 */
public class GuiAffectation extends Form {
    Form current;
     ArrayList<Classe> ListClasse;
     ComboBox<String> cbClasse;
     ArrayList<Professeur> ListProfs;
     ComboBox<String> cbProfs;
     ArrayList<Matiere> ListMatiere;
     ComboBox<String> cbMatiere;
    GuiAffectation(Form previous, int id) {
      current=this;
           Toolbar tb = getToolbar();
      setLayout(BoxLayout.y());
                     tb.addMaterialCommandToSideMenu("Affectation", FontImage.MATERIAL_WORK, e-> new GuiAffectation(current,id).show());
      
  tb.addMaterialCommandToSideMenu("se déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            try {
                Database db = Database.openOrCreate("School.db");
                db.execute("delete from appstates");
                Utilisateur.current_user = null;
                new SignInForm(theme).show();
            } catch (IOException ex) {
            }
        });
          
        Style catRecStyle= getAllStyles();
        catRecStyle.setBgColor(0xAACDFC);
    
        cbClasse = new ComboBox("Liste Classes");
        ListClasse = AffectationService.getInstance().getAllClasse();
          for(int i=0;i<ListClasse.size();i++)
        { cbClasse.addItem(ListClasse.get(i).getNomClasse());
        
        }
        cbMatiere = new ComboBox("Liste Matiere");
        ListMatiere = AffectationService.getInstance().getAllMatiere();
          for(int i=0;i<ListMatiere.size();i++)
        { cbMatiere.addItem(ListMatiere.get(i).getNomMatiere());
        
        }
         cbProfs = new ComboBox("Liste Professeurs");
         ListProfs = AffectationService.getInstance().getAllProf();
          for(int i=0;i<ListProfs.size();i++)
        { cbProfs.addItem(ListProfs.get(i).getNom());
        
        } 
       
        Button ajouter=new Button("ajouter");
        //style button
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        Style butStyle5=ajouter.getAllStyles();
        butStyle5.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(120).
        stroke(borderStroke));
        butStyle5.setBgColor(0xAACDFC);
        butStyle5.setBgTransparency(255);
        butStyle5.setMarginUnit(Style.UNIT_TYPE_DIPS);
        butStyle5.setMargin(Component.BOTTOM, 0);       
        butStyle5.setMargin(Component.TOP,0);           
        butStyle5.setMargin(Component.LEFT,0);  
        butStyle5.setMargin(Component.RIGHT,0); 
        
                    this.getToolbar().addCommandToRightBar("affichage", null, ev->{
         new  GererAffectation(current,theme).show();
        });
                     ajouter.addActionListener(new ActionListener() {

            @Override
             public void actionPerformed(ActionEvent evt) {
               
                    try {
                        Professeur e = rechercherProf(cbProfs.getSelectedItem());
                        Classe c = rechercherClasse(cbClasse.getSelectedItem());
                       Matiere m = rechercherMatiere(cbMatiere.getSelectedItem());
                        affectation t = new affectation(c,e,m);
                        System.out.println(t.toString());
                        if( AffectationService.getInstance().addAffectation(t))
                        { Dialog.show("Success","Connection accepted",new Command("OK"));
                            Utilisateur.current_user.toString();
                            TwilioSMS s=new TwilioSMS("AC245493356aedb9fdeaa79cc877b79e89","661b33ee957b4f5ad46ee7b88a4071f3","+12565948626");
                              s.sendSmsAsync("+21627763174","Mme/mr "+e.getNom()+" Vous étes affecter a la classe:"+c.getNomClasse());
                            
                            
                        }
                           
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Nb places must be a number", new Command("OK"));
                    }
             }
                
            
            
        });
       
        
    current.addAll(cbClasse,cbProfs,cbMatiere,ajouter);
        current.show();
}
     public Classe rechercherClasse(String nom) {
       for(int i = 0; i<ListClasse.size();i++){
           if(ListClasse.get(i).getNomClasse().equals(nom)){
              return ListClasse.get(i);
           }
       }
           return null;
       
    }
     public Matiere rechercherMatiere(String nom) {
       for(int i = 0; i<ListMatiere.size();i++){
           if(ListMatiere.get(i).getNomMatiere().equals(nom)){
              return ListMatiere.get(i);
           }
       }
           return null;
       
    }
      public Professeur rechercherProf(String nom) {
       for(int i = 0; i<ListProfs.size();i++){
           if(ListProfs.get(i).getNom().equals(nom)){
              return ListProfs.get(i);
           }
       }
           return null;
       
    }
}
