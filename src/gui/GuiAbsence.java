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
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.MyApplication;
import static com.mycompany.myapp.MyApplication.theme;
import com.mycompany.myapp.gui.ListEncadrementProfForm;
import entities.Absence;
import entities.Classe;
import entities.Etudiant;
import entities.Matiere;
import entities.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import service.AbsenceService;

/**
 *
 * @author MehdiS
 */
public class GuiAbsence extends Form{
     Form current;
    
  ArrayList<Matiere> ListMatiere;
  ArrayList<Classe> ListClasse;
  ArrayList<Etudiant> ListEtudiant;
   ComboBox<String> cbClasse;
   ComboBox<String> cbEtudiant ;
   ComboBox<String> cbMatieres;
   ComboBox<String> cbHeure;
    public GuiAbsence(Form previous, int id) {
        current=this;
           Toolbar tb = getToolbar();
      setLayout(BoxLayout.y());
                     tb.addMaterialCommandToSideMenu("Absence", FontImage.MATERIAL_CALENDAR_TODAY, e-> new GuiAbsence(current,id).show());
                     tb.addMaterialCommandToSideMenu("List des demandes d'encadrement", FontImage.MATERIAL_EMAIL, e -> {
            new ListEncadrementProfForm(Utilisateur.current_user,theme).show();
        });
      
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
        
              /*FORMULAIRE LIGNE DE TRANSPORT*/
      //  Form bus= new Form("LIGNE DE TRANSPORT",BoxLayout.y());
      
        
       
        cbHeure =new ComboBox("Crénaux Absences");
        cbHeure.addItem("09:00:00");
        cbHeure.addItem("09:45:00");
        cbHeure.addItem("-----------");
        cbHeure.addItem("13:30:00");
        cbHeure.addItem("15:00:00");
         cbClasse = new ComboBox("Liste Classes");
         ListClasse = AbsenceService.getInstance().getAllClasse(id);
         cbEtudiant = new ComboBox("Liste Etudiants");
         cbClasse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                
                ListEtudiant = AbsenceService.getInstance().getAllEtudiant(cbClasse.getSelectedItem());
               
                for(int i=0;i<ListEtudiant.size();i++)
                { 
                   cbEtudiant.getModel().removeItem(i);
                }
                 for(int i=0;i<ListEtudiant.size();i++)
                { 
                   cbEtudiant.addItem(ListEtudiant.get(i).getNom());
                }
            }
        });
        
       
      
        cbMatieres=new ComboBox("Liste Matiere");
        ListMatiere= AbsenceService.getInstance().getAllMatieres();

        for(int i=0;i<ListMatiere.size();i++)
        { cbMatieres.addItem(ListMatiere.get(i).getNomMatiere());
       
        }
        for(int i=0;i<ListClasse.size();i++)
        { cbClasse.addItem(ListClasse.get(i).getNomClasse());
        
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
         new  gererAbsence(current , ListClasse , ListEtudiant , ListMatiere).show();
        });
                    this.getToolbar().addCommandToRightBar("Statistique", null, ev->{
         new  StatAbsence(current).show();
        });
        
        ajouter.addActionListener(new ActionListener() {

            @Override
             public void actionPerformed(ActionEvent evt) {
               
                    try {
                        Etudiant e = rechercherEtudiant(cbEtudiant.getSelectedItem());
                        Classe c = rechercherClasse(cbClasse.getSelectedItem());
                        Matiere m = rechercherMatiere(cbMatieres.getSelectedItem());
                        Absence t = new Absence(m,e,cbHeure.getSelectedItem(),c);
                        System.out.println(t.toString());
                        if( AbsenceService.getInstance().addAbsence(t))
                        { Dialog.show("Success","Connection accepted",new Command("OK"));
             
                            
                        }
                           
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Nb places must be a number", new Command("OK"));
                    }
             }
                
            
            
        });
       
        
        //add to show
        current.addAll(cbMatieres,cbHeure,cbClasse,cbEtudiant,ajouter);
        current.show();

        
    }

    public Etudiant rechercherEtudiant(String nom) {
       for(int i = 0; i<ListEtudiant.size();i++){
           if(ListEtudiant.get(i).getNom().equals(nom)){
              return ListEtudiant.get(i);
           }
       }
           return null;
       
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
    
    
}
