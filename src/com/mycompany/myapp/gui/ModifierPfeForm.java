/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
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
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Pfe;
import entities.Utilisateur;
import com.mycompany.myapp.services.ServicePfe;
import gui.SignInForm;
import java.util.ArrayList;

/**
 *
 * @author TOSHIBA
 */
public class ModifierPfeForm extends Form {
    public ModifierPfeForm(int id,Utilisateur user,Resources theme) {
        setTitle("Mofifier Pfe");
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
        setLayout(BoxLayout.y());
                setLayout(new FlowLayout(CENTER,CENTER));

        ArrayList<Pfe> pfes = new ArrayList<>();
        pfes = ServicePfe.getInstance().getOnePfe(id); 
        for (Pfe p : pfes) {
        
        TextField tfTitre = new TextField(p.getTitre(),"Titre");
        TextField tfSujet= new TextField(p.getSujet(), "Sujet");
        Button btnValider = new Button("Modifier pfe"); 
        btnValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfTitre.getText().length()==0)||(tfSujet.getText().length()==0))
                    Dialog.show("Alert", "Veuillez remplir tous les champs", new Command("OK"));
                else{
                ServicePfe.getInstance().ModifierPfe(p,tfTitre.getText(),tfSujet.getText());
             new ListPfeForm(user,theme).showBack();}
            }
        });
        Label ltitre = new Label("Titre");
        Label lsujet =new Label("sujet");
        add(ltitre);
        add(tfTitre);
        add(lsujet);
        add(tfSujet);
        add(btnValider);
        }
    } 
}
