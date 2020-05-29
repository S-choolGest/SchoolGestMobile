/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import entities.Absence;
import entities.Classe;
import entities.Etudiant;
import entities.Matiere;
import entities.Professeur;
import entities.Utilisateur;
import entities.affectation;
import java.util.ArrayList;
import service.AbsenceService;
import service.AffectationService;

/**
 *
 * @author MehdiS
 */
public class GererAffectation extends Form {
    Form current;

     ArrayList<Professeur>listProf;
     ArrayList<Classe>listClasse;
  Professeur idProf ;
  Classe idClasse ;
    

    
    public GererAffectation(Form previous, Resources theme){
        
        current = this;
       Display.getInstance().setCommandBehavior(Display.COMMAND_BEHAVIOR_SIDE_NAVIGATION);
 UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
UIManager.getInstance().getLookAndFeel().setMenuBarClass(SideMenuBar.class);
              setTitle("affichage  affectation");
        setLayout(BoxLayout.y());
        
        for (affectation  r : new AffectationService().getAllAffectation())
        {
            this.add(addItem(r,theme));
        }
         
       
        this.show();
                this.getToolbar().addCommandToLeftBar("back", MyApplication.theme.getImage("back-command.png"), ev->{
 
      new GuiAffectation(this,Utilisateur.current_user.getId()).show();
        });
    }
    
  
      public Container addItem(affectation c,Resources theme){
   
       Container cn1=new Container(new BorderLayout());
        Container cn2=new Container(BoxLayout.y());
        Button btn=new Button("professeur: "+c.getProf().getNom());
        
         Label labnbr=new Label("Classe: "+c.getClasse().getNomClasse());
 
  
    
        cn2.add(btn).addAll(labnbr);
        cn1.add(BorderLayout.WEST, cn2);
     //supprimer   
        btn.addActionListener((ActionEvent e)->{
            Form f2=new Form("Details",BoxLayout.y());
        
          
            ComboBox<String> tfProf=new ComboBox("Liste Prof");
                 listProf=AffectationService.getInstance().getAllProf();
                 ComboBox<String> tfClasse=new ComboBox("Liste Classe");
                 listClasse=AffectationService.getInstance().getAllClasse();
                

for(int i=0;i<listProf.size();i++)
{ 
    tfProf.addItem(listProf.get(i).getNom());
}
for(int i=0;i<listClasse.size();i++)
{ 
    tfClasse.addItem(listClasse.get(i).getNomClasse());
}
 tfProf.setSelectedItem(c.getProf().getNom());
 
 tfClasse.setSelectedItem(c.getClasse().getNomClasse());
 idProf =  rechercherProf(tfProf.getSelectedItem());
  idClasse =  rechercherClasse(tfClasse.getSelectedItem());
          Button btn_modif=new Button("modifier");
           Button btn_sup=new Button("supprimer");
          
            f2.add("Affectation").add("Professeur : ").add(tfProf).add("Classe: ").add(tfClasse).add(btn_sup).add(btn_modif);
     btn_sup.addActionListener(ww ->
     
     {
         new AffectationService().suppAbsence(c.getId());
         new GererAffectation(this,theme).showBack();
     }
     
     );
          btn_modif.addActionListener(aa ->
     
     {
          idProf =  rechercherProf(tfProf.getSelectedItem());
          idClasse =  rechercherClasse(tfClasse.getSelectedItem());
        
     
   c.setClasse(idClasse);
   c.setProf(idProf);
  
        
         new AffectationService().modifierAffectation(c);
         new GererAffectation(this,theme).showBack();
     }
     
     );
            f2.getToolbar().addCommandToLeftBar("back", null, ev->{
            this.show();
        });
                   f2.show();
        });
        
        cn1.setLeadComponent(btn);
        return cn1;
                
    }
       public Classe rechercherClasse(String nom) {
       for(int i = 0; i<listClasse.size();i++){
           if(listClasse.get(i).getNomClasse().equals(nom)){
              return listClasse.get(i);
           }
       }
           return null;
       
    }
      public Professeur rechercherProf(String nom) {
       for(int i = 0; i<listProf.size();i++){
           if(listProf.get(i).getNom().equals(nom)){
              return listProf.get(i);
           }
       }
           return null;
       
    }
  
      
    
    
}
