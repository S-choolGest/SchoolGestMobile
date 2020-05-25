/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
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
public class ConsulterEncadrementForm extends Form {
    public ConsulterEncadrementForm(int id,Utilisateur user,Resources theme){
        Toolbar tb = getToolbar();
            Label l=new Label("menu");
        Container topBar = BorderLayout.east(l);
        topBar.add(BorderLayout.SOUTH, new Label("Application Name"));
        tb.addComponentToSideMenu(topBar);

       
       
        tb.addMaterialCommandToSideMenu("List des demande d'encadremet", FontImage.MATERIAL_EMAIL, e -> {
            new ListEncadrementProfForm(user,theme).show();
        });
        
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            new SignInForm(theme).showBack();
        });
                getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListEncadrementProfForm(user,theme).showBack());

        setLayout(BoxLayout.y());
        setLayout(new FlowLayout(CENTER,CENTER));

    setTitle("Consulter Encadrement"); 
          setLayout(BoxLayout.y());
          /*setLayout(new FlowLayout(CENTER,CENTER));*/
        ArrayList<Encadrement> encadrements = new ArrayList<>();
        encadrements = ServiceEncadrement.getInstance().getOneEncadrement(id);
            for (Encadrement e : encadrements) {
                 addItem(e,user);                 
             } 
           
}
    public void addItem(Encadrement e,Utilisateur user){
     /*Label titre= new Label("Titre :");  
     Label ltitre= new Label(p.getTitre());*/ 
    ArrayList<Pfe> pfes = new ArrayList<>();
        pfes = ServicePfe.getInstance().getOnePfe(e.getIdPfe());
            for (Pfe p : pfes) {
                Label titre= new Label("Titre :"); 
           Label sujet= new Label("Sujet :");  
     Label ltitre= new Label(p.getTitre());  
     Label lsujet= new Label(p.getSujet());
     add(titre);
     add(ltitre);
     add(sujet);
     add(lsujet);
            }
      Label etat = new Label("Etat :");
      Label letat = new Label(e.getEtat());
          add(etat); 
       add(letat);    

        Button btnAccepter = new Button("Accepter");
        Button btnRefuser = new Button("Refuser");
        btnAccepter.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent evt) {
             ServiceEncadrement.getInstance().AccepterEncadrement(e.getId());
              new ListEncadrementProfForm(user,theme).showBack();
            refreshTheme();
             
         }
     });
        btnRefuser.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent evt) {
             ServiceEncadrement.getInstance().RefuserEncadrement(e.getId());
             new ListEncadrementProfForm(user,theme).showBack();
             refreshTheme();
             

         }
     });
        if(e.getEtat().equalsIgnoreCase("en_attente"))
        {
            add(btnAccepter);
            add(btnRefuser);
        }

}
}