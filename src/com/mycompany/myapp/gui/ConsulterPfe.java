/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
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
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import static com.mycompany.myapp.MyApplication.theme;
import com.mycompany.myapp.entities.Pfe;
import entities.Utilisateur;
import com.mycompany.myapp.services.ServicePfe;
import gui.SignInForm;
import java.util.ArrayList;

/**
 *
 * @author TOSHIBA
 */
public class ConsulterPfe extends Form{
    public ConsulterPfe(int id,Utilisateur user,Resources theme) {
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
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListPfeForm(user,theme).showBack());
        setTitle("Consulter Pfe"); 
        getToolbar().addCommandToRightBar("Home", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                ListPfeForm f = new ListPfeForm(user,theme);
                f.showBack();

            }
        });
          setLayout(BoxLayout.y());
          setLayout(new FlowLayout(CENTER,CENTER));
        ArrayList<Pfe> pfes = new ArrayList<>();
        pfes = ServicePfe.getInstance().getOnePfe(id);
            for (Pfe p : pfes) {
                 addItem(p,user);                 
             }          
    } 
     public void addItem(Pfe p,Utilisateur user){
     Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
      Container c2 = new Container(BoxLayout.y());
           Label titre= new Label("Titre :"); 
           Label sujet= new Label("Sujet :");  

     Label ltitre= new Label(p.getTitre());  
     Label lsujet= new Label(p.getSujet());  
     Button btnModifier = new Button("Modifier");
     Button btnSupprimer = new Button("Supprimer");
     btnSupprimer.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent evt) {
             ServicePfe.getInstance().SupprimerPfe(p);
             new ListPfeForm(user,theme).showBack();
         }
     });
     btnModifier.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent evt) {
             new ModifierPfeForm(p.getId(),user,theme).show();
         }
     });
     c.add(titre);
     c.add(ltitre);
     c.add(sujet);
    c.add(lsujet);
    c.setLeadComponent(ltitre);
    c2.add(c);
    c2.add(btnModifier);
    c2.add(btnSupprimer);
    add(c2);
    
 }
}
