/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import static com.mycompany.myapp.MyApplication.theme;
import com.mycompany.myapp.entities.Encadrement;
import com.mycompany.myapp.entities.Pfe;
import entities.Utilisateur;
import com.mycompany.myapp.services.ServicePfe;
import gui.GuiAbsenceEtudiant;
import gui.SignInForm;
import java.util.ArrayList;

/**
 *
 * @author TOSHIBA
 */
public class ListPfeForm extends Form {
  
Form current;
public ListPfeForm(Utilisateur user,Resources theme) {
               Toolbar tb = getToolbar();
            Label l=new Label("menu");
        Container topBar = BorderLayout.east(l);
        topBar.add(BorderLayout.SOUTH, new Label("Application Name"));
        tb.addComponentToSideMenu(topBar);
        
        tb.addMaterialCommandToSideMenu("Absence", FontImage.MATERIAL_BOOK, e-> new GuiAbsenceEtudiant(current,Utilisateur.current_user.getId()).show());
        
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

        setTitle("Mes Pfe");
        setLayout(BoxLayout.y());
        ArrayList<Pfe> pfes = new ArrayList<>();
        pfes = ServicePfe.getInstance().getMyPfes(user.getId());
            for (Pfe p : pfes) {
                 addItem(p,user);      
             }   
        
         
    }    
 public void addItem(Pfe p,Utilisateur user){
     Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
     Label ltitre= new Label(p.getTitre()); 
          ltitre.getAllStyles().setFgColor(0x74d0f1);

    /* Label sujet= new Label("Sujet :");  
     Label lsujet= new Label(p.getSujet());  */
   ltitre.addPointerPressedListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if(Dialog.show("Consulter Pfe", "Titre : " + p.getTitre() + " \n Sujet : " + p.getSujet(), "Ok", "cancel"))
                {
                new ConsulterPfe(p.getId(),user,theme).show();
                        }                 
            }
        });
     c.add(ltitre);
    c.setLeadComponent(ltitre);
     c.getAllStyles().setBgColor(0xcecece);
       c.getAllStyles().setBgTransparency(10);
       c.getAllStyles().setBorder(Border.createInsetBorder(1, 0xededed));
    add(c);
    
    
 }
}
