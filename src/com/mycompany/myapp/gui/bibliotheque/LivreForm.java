/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

import Entite.Bibliotheque.Livre;
import Entite.Utilisateur.Utilisateur;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
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
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.homepage.BibliothecaireForm;
import com.mycompany.myapp.gui.homepage.EtudiantForm;
import com.mycompany.myapp.services.ServiceEmprunt;
import com.mycompany.myapp.services.ServiceLivre;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author william
 */
public class LivreForm extends Form {

	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	String url_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/uploads/";
	ServiceLivre ser = new ServiceLivre();
	ServiceEmprunt ser_emp = new ServiceEmprunt();

	public LivreForm(Form previous, Livre l, Utilisateur user) {
		current = this;
		setTitle(l.getTitre());
		setLayout(new FlowLayout(CENTER, TOP));

		Container c0 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		getToolbar().addCommandToSideMenu("Home", null, (evt) -> {
			new EtudiantForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("Livre", null, (evt) -> {
			new ListBiblioForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("Emprunt", null, (evt) -> {
			new ListBiblio_EmpruntForm(this, user).show();
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
		img = URLImage.createToStorage(imc, l.getImg(), url_img + l.getImg(), URLImage.RESIZE_SCALE);
		System.out.println(url_img + l.getImg());
		imv = new ImageViewer(img);

		Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Label titre = new Label("Titre : " + l.getTitre());
		Label auteur = new Label("Auteur : " + l.getAuteur());
		Label editeur = new Label("Editeur : " + l.getEditeur());
		Label pages = new Label("Pages : " + l.getTaille());
		Label qte = new Label("Quantite : " + l.getQuantite());
		Label categorie = new Label("Catégorie : " + l.getCategorie());
		Label datesortie = new Label("Date de sortie : "+l.getDateSortie());
		Label dateajout = new Label("Date d'ajout : "+l.getDateajout());

		Label ldatedebut = new Label("Date de debut : ");
		Picker pdatedebut = new Picker();
		Container datedebut = new Container(new BoxLayout(BoxLayout.X_AXIS));
		datedebut.addAll(ldatedebut, pdatedebut);

		Label ldatefin = new Label("Date de fin : ");
		Picker pdatefin = new Picker();
		Container datefin = new Container(new BoxLayout(BoxLayout.X_AXIS));
		datefin.addAll(ldatefin, pdatefin);

		c.addAll(titre, auteur, editeur, pages, qte, categorie, datesortie, datedebut, datefin);

		Button emprunter = new Button("Emprunter");

		emprunter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.out.println(pdatedebut.getText());
				System.out.println(pdatefin.getDate());
				String date_debut = (new SimpleDateFormat("yyyy-MM-dd")).format(pdatedebut.getDate());
				String date_fin = (new SimpleDateFormat("yyyy-MM-dd")).format(pdatefin.getDate());
				System.out.println(date_debut + " + " + date_fin);
				if (date_debut.compareTo(date_fin) < 0) {
					if (ser_emp.emprunter(user.getId(), l.getId(), date_debut, date_fin)) {
						Dialog.show("Emprunt réussi", "attendez la confirmation du bibliothécaire pour passer récuperer le livre emprunté", new Command("OK"));
					}
				} else {
					Dialog.show("Date incorrecte", "date de debut doit être inférieure à la date de fin", new Command("OK"));
				}
			}
		});
		c0.addAll(imv, c, emprunter);
		addAll(c0);
	}

}
