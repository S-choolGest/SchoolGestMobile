/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.mycompany.myapp.MyApplication;
import entities.Absence;
import entities.Classe;
import entities.Etudiant;
import entities.Matiere;
import entities.Utilisateur;
import java.util.ArrayList;
import java.util.Arrays;
import service.AbsenceService;


/**
 *
 * @author MehdiS
 */
public class gererAbsence extends Form{
    Form current;

    
    
    public gererAbsence(Form previous,   ArrayList<Classe> ListClasse, ArrayList<Etudiant> ListEtudiant, ArrayList<Matiere> ListMatiere){
         current = this;
        setLayout(BoxLayout.y());
        Toolbar.setGlobalToolbar(true);
Style s = UIManager.getInstance().getComponentStyle("Title");


TextField searchField = new TextField("", "Veuillez saisir le Nom de l'Etudiant"); // <1>
searchField.getHintLabel().setUIID("Title");
searchField.setUIID("Title");
searchField.getAllStyles().setAlignment(Component.LEFT);
getToolbar().setTitleComponent(searchField);
FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
searchField.addDataChangeListener((i1, i2) -> { // <2>
    String t = searchField.getText();
    if(t.length() < 1) {
        for(Component cmp : getContentPane()) {
              
            cmp.setHidden(false);
            cmp.setVisible(true);
        }
    } else {
        t = t.toLowerCase();
        for(Component cmp : getContentPane()) {
            
            String val = null;
            if(cmp instanceof Container){
                Container c1 =(Container)cmp;
            
            if(c1.getComponentAt(0).getClientProperty("Etu") instanceof Label) {
                val = ((Label)c1.getComponentAt(0).getClientProperty("Etu")).getText();
            } 
            }
            boolean show = val != null && val.toLowerCase().indexOf(t) > -1;
            cmp.setHidden(!show); // <3>
            cmp.setVisible(show);
        }
    }
    getContentPane().animateLayout(250);
});
getToolbar().addCommandToRightBar("", searchIcon, (e) -> {
    searchField.startEditingAsync(); // <4>
});
         //setTitle("affichage   absence");
        
       
       
                for (Absence  r : new AbsenceService().getAllAbsence ())
                {
                    this.add(addItem(r, ListClasse, ListEtudiant, ListMatiere));
                }
            
        this.show();
        this.getToolbar().addCommandToLeftBar("back", MyApplication.theme.getImage("back-command.png"), ev->{
        new GuiAbsence(this,Utilisateur.current_user.getId()).show();
        });
    }

        
  
      public Container addItem(Absence c,   ArrayList<Classe> ListClasse, ArrayList<Etudiant> ListEtudiant, ArrayList<Matiere> ListMatiere){
          
        Container cn1=new Container(new BorderLayout());
        Container cn2=new Container(BoxLayout.y());
       
               Label labEtat=new Label("Etat : "+String.valueOf(c.getetat()));
               Label labDate=new Label("Date: "+c.getDateString(c.getDate()));
               Label labHeure=new Label("Heure: "+c.getHeureString(c.getHeure()));
               Label labEtudiant=new Label("Etudiant: "+c.getIdEtudiant().getNom()+" "+c.getIdEtudiant().getPrenom());
               Label labClasse=new Label("Classe: "+String.valueOf(c.getClasse().getNomClasse()));
               Label labMatiere=new Label("Matiere: "+c.getMatiere().getNomMatiere());
               
              
              
              
     Button btn=new Button("Editer");
     cn2.add(btn).addAll(labEtudiant,labClasse,labMatiere,labDate,labEtat,labHeure);
        cn1.add(BorderLayout.WEST, cn2);
      cn2.putClientProperty("Etu", labEtudiant);
        
        //supprimer   
        btn.addActionListener(e->{
            Container cnD=new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Form f2=new Form("Details",BoxLayout.y());
            TextField tfdate= new TextField(c.getDateString(c.getDate()));
            TextField tfHeure=new TextField(c.getHeureString(c.getHeure()));
            TextField tfEtudiant=new TextField(c.getIdEtudiant().getNom());
            tfEtudiant.setEditable(false);
            tfHeure.setEditable(false);
            tfdate.setEditable(false);
            ComboBox<String>  cbetat=new ComboBox("Etat");
          cbetat.addItem("justifié");
        cbetat.addItem("non justifié");
       /*ComboBox<String> cbMatieres = new ComboBox("Liste Matiere");
       for(int i=0;i<ListMatiere.size();i++)
        { cbMatieres.addItem(ListMatiere.get(i).getNomMatiere());
       
        }
        ComboBox<String> cbClasse = new ComboBox("Liste Classes");
         for(int i=0;i<ListClasse.size();i++)
        { cbClasse.addItem(ListClasse.get(i).getNomClasse());
        
        }
         ComboBox<String> cbEtudiant = new ComboBox("Liste Etudiants");
          for(int i=0;i<ListEtudiant.size();i++)
        { cbEtudiant.addItem(ListEtudiant.get(i).getNom());
        
        }      
         cbMatieres.setSelectedItem(c.getMatiere().getNomMatiere());
         cbClasse.setSelectedItem(c.getClasse().getNomClasse());
         cbEtudiant.setSelectedItem(c.getIdEtudiant().getNom());*/
         cbetat.setSelectCommandText(c.getetat());
          Button btn_modif=new Button("modifier");
           Button btn_sup=new Button("supprimer");
           //cnD.add(cbMatieres);
            cnD.add("Absence").add("etudiant :").add(tfEtudiant).add("etat : ").add(cbetat).add(btn_sup).add(btn_modif);
           f2.add(cnD);
     btn_sup.addActionListener(ww ->
     
     {
         new AbsenceService().suppAbsence(c.getId());
         new gererAbsence(this,  ListClasse, ListEtudiant,ListMatiere).showBack();
     }
     
     );
     btn_modif.addActionListener(aa ->
     {
         /*Etudiant e1 = rechercherEtudiant(ListEtudiant,cbEtudiant.getSelectedItem());
        Classe c1 = rechercherClasse(ListClasse,cbClasse.getSelectedItem());
        Matiere m = rechercherMatiere(ListMatiere,cbMatieres.getSelectedItem());*/
         c.setEtat(cbetat.getSelectedItem());
       
           
         new AbsenceService().modifierAbsence(c);
         new gererAbsence(this,ListClasse, ListEtudiant,ListMatiere).showBack();
     }
     
     );
            f2.getToolbar().addCommandToLeftBar("back", null, ev->{
            this.show();
        });
                   f2.show();
        });
        
        cn1.setLeadComponent(btn);
          
          System.out.println(cn1.getComponentAt(0).getClientProperty("Etu"));
          
        return cn1;
                
    }
    
      public Etudiant rechercherEtudiant(ArrayList<Etudiant> ListEtudiant,String nom) {
       for(int i = 0; i<ListEtudiant.size();i++){
           if(ListEtudiant.get(i).getNom().equals(nom)){
              return ListEtudiant.get(i);
           }
       }
           return null;
       
    }
    
     public Classe rechercherClasse(ArrayList<Classe> ListClasse,String nom) {
       for(int i = 0; i<ListClasse.size();i++){
           if(ListClasse.get(i).getNomClasse().equals(nom)){
              return ListClasse.get(i);
           }
       }
           return null;
       
    }
   public Matiere rechercherMatiere(ArrayList<Matiere> ListMatiere,String nom) {
       for(int i = 0; i<ListMatiere.size();i++){
           if(ListMatiere.get(i).getNomMatiere().equals(nom)){
              return ListMatiere.get(i);
           }
       }
           return null;
       
    }
    
}
