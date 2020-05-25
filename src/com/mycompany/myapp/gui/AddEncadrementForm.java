/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Pfe;
import entities.Utilisateur;
import com.mycompany.myapp.services.ServiceEncadrement;
import com.mycompany.myapp.services.ServicePfe;
import gui.SignInForm;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author TOSHIBA
 */
public class AddEncadrementForm extends Form {
    public AddEncadrementForm(Utilisateur user,Resources theme) {
    setTitle("Envoyer une Demande");
        Toolbar tb = getToolbar();
        Label l=new Label("menu");
            Container topBar = BorderLayout.east(l);
       topBar.add(BorderLayout.SOUTH, new Label("Application Name"));
        tb.addComponentToSideMenu(topBar);
        tb.addMaterialCommandToSideMenu("Ajouter Pfe", FontImage.MATERIAL_WEB, e -> {
            new AddPfeForm(user,theme).show();
        });
        tb.addMaterialCommandToSideMenu("Liste des Pfes", FontImage.MATERIAL_WEB, e -> {
            new ListPfeForm(user,theme).show();
        });
        tb.addMaterialCommandToSideMenu("List des demande d'encadremet", FontImage.MATERIAL_WEB, e -> {
            new ListEncadrementEtudiantForm(user,theme).show();
        });
        tb.addMaterialCommandToSideMenu("Ajouter Encadrement", FontImage.MATERIAL_WEB, e -> {
            new AddEncadrementForm(user,theme).show();
        });
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            new SignInForm(theme).showBack();
        });
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListEncadrementEtudiantForm(user,theme).showBack());

        setLayout(BoxLayout.y());
        setLayout(new FlowLayout(CENTER,CENTER));
         Pfe po =new Pfe();
        Label lsujet = new Label("Sujet Pfe");
       Label lemail = new Label("Email");
        TextField tfEmail= new TextField("", "Email du Professeur");
       ComboBox cbsujet= new ComboBox();
        ArrayList<Pfe> pfes = new ArrayList<>();
        pfes = ServicePfe.getInstance().getMyPfes(user.getId());
            for (Pfe p : pfes) {
                cbsujet.addItem(p);
        } 
             for (Pfe p : pfes) {
                cbsujet.addItem(p);
        } 
            Button btnValider = new Button("Envoyer");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfEmail.getText().length()==0)
                    Dialog.show("Alert", "Veuillez remplir tous les champs", new Command("OK"));
                else
                {
                           Pfe pid =(Pfe)cbsujet.getSelectedItem();

                        if( ServiceEncadrement.getInstance().addEncadrement(pid.getId(),tfEmail.getText()))
                            new ListEncadrementEtudiantForm(user,theme).showBack();
                        
                       
                      }
                
        } 
            
            
            
        });
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
      c1.add(lsujet);
      c1.add(cbsujet);
      c1.add(lemail);
      c1.add(tfEmail);
      add(c1);
      add(btnValider);
}
}
