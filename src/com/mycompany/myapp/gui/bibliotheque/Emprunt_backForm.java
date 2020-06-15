/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

import Entite.Bibliotheque.Emprunteur;
import Entite.Utilisateur.Utilisateur;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.homepage.BibliothecaireForm;
import com.mycompany.myapp.services.ServiceEmprunt;
import com.mycompany.myapp.services.ServiceLivre;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author william
 */
public class Emprunt_backForm extends Form {

	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	String url_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/uploads/";
	ServiceLivre ser = new ServiceLivre();
	ServiceEmprunt ser_emp = new ServiceEmprunt();
	ArrayList<Emprunteur> emprunteurs;
	Utilisateur user;
	Container c0 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

	public Emprunt_backForm(Form previous, Utilisateur user) {
		current = this;
		this.user = user;
		setTitle("Emprunts effectués");
		setLayout(new FlowLayout(CENTER, TOP));

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

		getToolbar().addSearchCommand(e -> {
			String text = (String) e.getSource();
			if (text == null || text.length() == 0) {
				// clear search
				emprunteurs = ser_emp.getAllEmprunteurs(user.getId());
				afficher_emprunts(emprunteurs);
				getContentPane().animateLayout(150);
			} else {
				text = text.toLowerCase();
				emprunteurs = ser_emp.rechercher_emprunteur(text, user.getId());
				afficher_emprunts(emprunteurs);
				getContentPane().animateLayout(150);
			}
		}, 4);

		try {
			imc = EncodedImage.create("/load.png");
		} catch (IOException ex) {
			System.out.println(ex);
		}

		emprunteurs = ser_emp.getAllEmprunteurs(user.getId());
		afficher_emprunts(emprunteurs);
		add(c0);

	}

	public void afficher_emprunts(ArrayList<Emprunteur> emp) {
		c0.removeAll();
		for (Emprunteur e : emp) {
			img = URLImage.createToStorage(imc, e.getImg(), url_img + e.getImg(), URLImage.RESIZE_SCALE);
			imv = new ImageViewer(img);
			imv.setHeight(5);
			imv.setWidth(5);

			Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
			Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

			Label titre = new Label(e.getTitre());
			String etat_s = "";
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
			Label dateemprunt = new Label(e.getDateEmprunt());
			Button detail = new Button("detail");
			detail.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					new EditEmpruntForm(current, e, user).show();
				}
			});
			Label nom = new Label(e.getNom() + " " + e.getPrenom());

			c1.addAll(titre, etat, dateemprunt, nom, detail);
			if (e.getEtat() != 3 && e.getEtat() != 2) {
				c1.add(valider);
			}
			c.addAll(imv, c1);
			c0.add(c);
		}
	}
}
