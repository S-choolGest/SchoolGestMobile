/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
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
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import static com.mycompany.myapp.MyApplication.theme;
import com.mycompany.myapp.entities.Encadrement;
import com.mycompany.myapp.entities.Pfe;

import com.mycompany.myapp.services.ServiceEncadrement;
import com.mycompany.myapp.services.ServicePfe;
import entities.Utilisateur;
import gui.GuiAbsence;
import gui.SignInForm;
import java.util.ArrayList;

/**
 *
 * @author TOSHIBA
 */
public class ListEncadrementProfForm extends Form {
    Form current;
    public ListEncadrementProfForm(Utilisateur user,Resources theme) {
        Toolbar tb = getToolbar();
        
            Label l=new Label("menu");
        Container topBar = BorderLayout.east(l);
        topBar.add(BorderLayout.SOUTH, new Label("Application Name"));
        tb.addComponentToSideMenu(topBar);
         tb.addMaterialCommandToSideMenu("Absence", FontImage.MATERIAL_CALENDAR_TODAY, e-> new GuiAbsence(current,user.getId()).show());
        tb.addMaterialCommandToSideMenu("List des demandes d'encadrement", FontImage.MATERIAL_EMAIL, e -> {
            new ListEncadrementProfForm(user,theme).show();
        });
      
        tb.addMaterialCommandToSideMenu("se déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            new SignInForm(theme).showBack();
        });
        setLayout(BoxLayout.y());
        setLayout(new FlowLayout(CENTER,CENTER));
        setTitle("Mes demandes d'encadrement");
        setLayout(BoxLayout.y());
        ArrayList<Encadrement> encadrements = new ArrayList<>();
        encadrements = ServiceEncadrement.getInstance().getMyEncadrementsProf(user.getId());
            for (Encadrement e : encadrements) {
                 addItem(e,user);      
             }       
    }    
    public void addItem(Encadrement e,Utilisateur user){
     Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
     /*Label titre= new Label("Titre :");  
     Label ltitre= new Label(p.getTitre());*/ 
    ArrayList<Pfe> pfes = new ArrayList<>();
        pfes = ServicePfe.getInstance().getOnePfe(e.getIdPfe());
            for (Pfe p : pfes) {
           
     Label ltitre= new Label(p.getTitre());
          ltitre.getAllStyles().setFgColor(0x74d0f1);

     Label lsujet= new Label(p.getSujet());
     ltitre.addPointerPressedListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if(Dialog.show("Consulter Demande", "Titre : " + p.getTitre() + " \n Sujet : " + p.getSujet() +" \n Etat:"+e.getEtat(), "Ok", "cancel"))
                {
                new ConsulterEncadrementForm(e.getId(),user,theme).show();
                        }                 
            }
        });
     c.add(ltitre);
    c.setLeadComponent(ltitre);
     c.add(lsujet);
            }

            Label letat = new Label(e.getEtat());
       c.add(letat);
        c.getAllStyles().setBgColor(0xcecece);
       c.getAllStyles().setBgTransparency(80);
       c.getAllStyles().setBorder(Border.createInsetBorder(1, 0xededed));
      
add(c);
   
    
    
 }
    
}
