/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

import Entite.Bibliotheque.Emprunt;
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
public class EmpruntForm extends Form{
	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	String url_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/uploads/";
	ServiceLivre ser = new ServiceLivre();
	ServiceEmprunt ser_emp = new ServiceEmprunt();

	public EmpruntForm(Form previous, LivreEmprunte l, Utilisateur user, int id_bibliotheque) {
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
			new EtudiantForm(this, user).show();
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
		Label dateconfirmation = new Label("Date confirmation : "+l.getDateConfirmation());
		Label daterendu = new Label("Date rendu : "+l.getDateRendu());
		Label datedebut = new Label("Date debut : "+l.getDateDebut());
		Label datefin = new Label("Date fin : "+l.getDateFin());

		c.addAll(titre, auteur, editeur, pages, qte, categorie, datesortie, dateajout, dateconfirmation, daterendu, datedebut, datefin);

		Button annuler = new Button("annuler");
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(ser_emp.annuler(l.getId_emprunt())){
					Dialog.show("Emprunt supprimé", "Votre emprunt a bien été annulé, retournez au catalogue pour passer un nouveau emprunt", new Command("OK"));
					new Emprunt_frontForm(current, user, id_bibliotheque).show();
				}
					else
					Dialog.show("Echec d'annulation", "Votre emprunt n'a pas pu être supprimé, Veillez contacter le bibliothécaire pour plus d'infos", new Command("OK"));
			}
		});

		if(l.getEtat() == 0)
			c0.addAll(imv, c, annuler);
		else
			c0.addAll(imv, c);
		
		addAll(c0);
	}
}
