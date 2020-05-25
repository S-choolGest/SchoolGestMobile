/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Encadrement;
import com.mycompany.myapp.entities.Pfe;
import entities.Utilisateur;
import com.mycompany.myapp.services.ServiceEncadrement;
import com.mycompany.myapp.services.ServicePfe;
import gui.SignInForm;
import java.util.ArrayList;

/**
 *
 * @author TOSHIBA
 */
public class ListEncadrementEtudiantForm extends Form {
    public ListEncadrementEtudiantForm(Utilisateur user,Resources theme) {
   Toolbar tb = getToolbar();
            Label l=new Label("menu");
        Container topBar = BorderLayout.east(l);
        topBar.add(BorderLayout.SOUTH, new Label("Application Name"));
        tb.addComponentToSideMenu(topBar);

        tb.addMaterialCommandToSideMenu("Ajouter Pfe", FontImage.MATERIAL_LIBRARY_ADD, e -> {
            new AddPfeForm(user,theme).show();
        });
        tb.addMaterialCommandToSideMenu("Liste des Pfes", FontImage.MATERIAL_LIBRARY_BOOKS, e -> {
            new ListPfeForm(user,theme).show();
        });
        tb.addMaterialCommandToSideMenu("List des demande d'encadremet", FontImage.MATERIAL_EMAIL, e -> {
            new ListEncadrementEtudiantForm(user,theme).show();
        });
        tb.addMaterialCommandToSideMenu("Ajouter Encadrement", FontImage.MATERIAL_LIBRARY_ADD, e -> {
            new AddEncadrementForm(user,theme).show();
        });
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            new SignInForm(theme).showBack();
        });
        setTitle("Mes demandes d'encadrement");
        setLayout(BoxLayout.y());
        ArrayList<Encadrement> encadrements = new ArrayList<>();
        encadrements = ServiceEncadrement.getInstance().getMyEncadrements(user.getId());
            for (Encadrement e : encadrements) {
                 addItem(e);      
             }       
    }    
 public void addItem(Encadrement e){
     Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
     /*Label titre= new Label("Titre :");  
     Label ltitre= new Label(p.getTitre());*/ 
    ArrayList<Pfe> pfes = new ArrayList<>();
        pfes = ServicePfe.getInstance().getOnePfe(e.getIdPfe());
            for (Pfe p : pfes) {
                 
     Label ltitre= new Label(p.getTitre()); 
     ltitre.getAllStyles().setFgColor(0x74d0f1);
     Label lsujet= new Label(p.getSujet());
    
     c.add(ltitre);

     c.add(lsujet);
            }
            
      Label letat = new Label(e.getEtat());
       c.add(letat);
       c.getAllStyles().setBgColor(0xcecece);
       c.getAllStyles().setBgTransparency(10);
       c.getAllStyles().setBorder(Border.createInsetBorder(1, 0xededed));
add(c);
   
    
    
 }
    
}
