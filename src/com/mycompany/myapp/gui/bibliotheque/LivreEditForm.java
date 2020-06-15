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
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.homepage.BibliothecaireForm;
import com.mycompany.myapp.services.ServiceLivre;
import java.io.IOException;

/**
 *
 * @author william
 */
public class LivreEditForm extends Form {

	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	String url_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/uploads/";
	ServiceLivre ser = new ServiceLivre();

	public LivreEditForm(Form previous, Livre l, Utilisateur user) {
		current = this;
		setTitle(l.getTitre());
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));

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
		img = URLImage.createToStorage(imc, l.getImg(), url_img + l.getImg(), URLImage.RESIZE_SCALE);
		System.out.println(url_img + l.getImg());
		imv = new ImageViewer(img);
		add(imv);
		Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		Label ltitre = new Label("Titre : ");
		TextField ftitre = new TextField(l.getTitre());
		Container titre = new Container(new BoxLayout(BoxLayout.X_AXIS));
		titre.addAll(ltitre, ftitre);

		Label lauteur = new Label("Auteur : ");
		TextField fauteur = new TextField(l.getAuteur());
		Container auteur = new Container(new BoxLayout(BoxLayout.X_AXIS));
		auteur.addAll(lauteur, fauteur);

		Label lediteur = new Label("Editeur : ");
		TextField fediteur = new TextField(l.getEditeur());
		Container editeur = new Container(new BoxLayout(BoxLayout.X_AXIS));
		editeur.addAll(lediteur, fediteur);

		Label lpages = new Label("Pages : ");
		TextField fpages = new TextField(String.valueOf(l.getTaille()));
		Container pages = new Container(new BoxLayout(BoxLayout.X_AXIS));
		pages.addAll(lpages, fpages);

		Label lqte = new Label("Quantite : ");
		TextField fqte = new TextField(String.valueOf(l.getQuantite()));
		Container qte = new Container(new BoxLayout(BoxLayout.X_AXIS));
		qte.addAll(lqte, fqte);

		Label lcategorie = new Label("Catégorie : ");
		TextField fcategorie = new TextField(l.getCategorie());
		Container categorie = new Container(new BoxLayout(BoxLayout.X_AXIS));
		categorie.addAll(lcategorie, fcategorie);

		Label ldatesortie = new Label("Date de sortie : ");
		TextField fdatesortie = new TextField(l.getDateSortie());
		Picker pdatesortie = new Picker();
		Container datesortie = new Container(new BoxLayout(BoxLayout.X_AXIS));
		datesortie.addAll(ldatesortie, pdatesortie);

		c.addAll(titre, auteur, editeur, categorie, pages, qte, datesortie);

		add(c);

		Button edit = new Button("edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (ftitre.getText().length() == 0 || fauteur.getText().length() == 0 || fediteur.getText().length() == 0 || fcategorie.getText().length() == 0 || fpages.getText().length() == 0 || fqte.getText().length() == 0) {
					Dialog.show("echec", "Veillez remplir tous les champs", new Command("OK"));
				} else {
					l.setTitre(ftitre.getText());
					l.setAuteur(fauteur.getText());
					l.setCategorie(fcategorie.getText());
					l.setEditeur(fediteur.getText());
					l.setQuantite(Integer.valueOf(fqte.getText()));
					l.setTaille(Integer.valueOf(fpages.getText()));
					boolean resultOK = ser.editLivre(l);
					if (resultOK) {
						Dialog.show("Succès", "Modification réussie", new Command("OK"));
						new Catalogue_backForm(current, user).show();
					} else {
						Dialog.show("Echec", "Echec de modification", new Command("OK"));
					}
				}

			}
		});

		Button delete = new Button("supprimer");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				boolean resultOK = ser.deleteLivre(l.getId());
				if (resultOK) {
					Dialog.show("Succès", "Suppression réussie", new Command("OK"));
					new Catalogue_backForm(current, user).show();
				} else {
					Dialog.show("Echec", "Echec de suppression", new Command("OK"));
				}
			}
		});

		Container cb = new Container(new BoxLayout(BoxLayout.X_AXIS));
		cb.addAll(edit, delete);
		add(cb);
	}

}
