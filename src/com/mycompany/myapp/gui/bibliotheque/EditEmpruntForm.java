/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

import Entite.Bibliotheque.Emprunteur;
import Entite.Bibliotheque.LivreEmprunte;
import Entite.Utilisateur.Utilisateur;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.homepage.BibliothecaireForm;
import com.mycompany.myapp.gui.homepage.EtudiantForm;
import com.mycompany.myapp.services.ServiceEmprunt;
import com.mycompany.myapp.services.ServiceLivre;
import java.io.IOException;

/**
 *
 * @author william
 */
public class EditEmpruntForm extends Form {

	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	String url_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/uploads/";
	ServiceLivre ser = new ServiceLivre();
	ServiceEmprunt ser_emp = new ServiceEmprunt();

	public EditEmpruntForm(Form previous, Emprunteur e, Utilisateur user) {
		current = this;
		setTitle(e.getTitre());
		setLayout(new FlowLayout(CENTER, TOP));

		Container c0 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

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

		getToolbar().addCommandToRightBar("revenir", null, (evt) -> {
			previous.show();
		});
		try {
			imc = EncodedImage.create("/load.png");
		} catch (IOException ex) {
			System.out.println(ex);
		}
		img = URLImage.createToStorage(imc, e.getImg_livre(), url_img + e.getImg_livre(), URLImage.RESIZE_SCALE);
		imv = new ImageViewer(img);

		Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Label nom = new Label("Nom : " + e.getNom() + " " + e.getPrenom());
		Label email = new Label("Email : " + e.getEmail());
		Label tel = new Label("Téléphone : " + e.getTel());
		Label dateconfirmation = new Label("Date confirmation : " + e.getDateConfirmation());
		Label daterendu = new Label("Date rendu : " + e.getDateRendu());
		Label datedebut = new Label("Date debut : " + e.getDateDebut());
		Label datefin = new Label("Date fin : " + e.getDateFin());

		String etat_s = "";
		int choix = 0;
		Container valider = new Container(new BoxLayout(BoxLayout.X_AXIS));
		if (e.getEtat() == 0) {
			etat_s = "attente";
			Button accepter = new Button("accepter");
			Button refuser = new Button("refuser");
			accepter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					ser_emp.valider_emprunt(e.getId(), 1);
					new Emprunt_backForm(current, user).show();
				}
			});
			refuser.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					ser_emp.valider_emprunt(e.getId(), 2);
					new Emprunt_backForm(current, user).show();
				}
			});
			valider.addAll(accepter, refuser);
		}
		if (e.getEtat() == 1) {
			etat_s = "confirme";
			Button retour = new Button("confirmer retour");
			retour.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					ser_emp.valider_emprunt(e.getId(), 3);
					new Emprunt_backForm(current, user).show();
				}
			});
			valider.add(retour);
		}
		if (e.getEtat() == 2) {
			etat_s = "refuse";
		}
		if (e.getEtat() == 3) {
			etat_s = "rendu";
		}
		Label etat = new Label("Etat : " + etat_s);
		c.addAll(nom, email, tel, etat, dateconfirmation, daterendu, datedebut, datefin);

		if (e.getEtat() == 3 || e.getEtat() == 2) {
			c0.addAll(imv, c);
		} else {
			c0.addAll(imv, c, valider);
		}

		addAll(c0);
	}
}
