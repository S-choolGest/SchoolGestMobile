/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.homepage;

import Entite.Utilisateur.Utilisateur;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.bibliotheque.Catalogue_backForm;
import com.mycompany.myapp.gui.bibliotheque.Emprunt_backForm;

/**
 *
 * @author william
 */
public class BibliothecaireForm extends Form{
	Form current;
	public BibliothecaireForm(Form previous, Utilisateur user){
		current = this;
		setTitle("Bibliotheque");
		setLayout(new FlowLayout(CENTER, CENTER));
		
		getToolbar().addCommandToSideMenu("Home", null, (evt) -> {
			new BibliothecaireForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("Livre", null, (evt) -> {
			new Catalogue_backForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("Emprunt", null, (evt) -> {
			new Emprunt_backForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("Réservation", null, (evt) -> {
			new BibliothecaireForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("se déconnecter", null, (evt) -> {
			new LoginForm().show();
		});
		
		Label welcome = new Label("Bienvenu "+user.getPrenom());
		add(welcome);
	}
}
