/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

import Entite.Bibliotheque.Emprunt;
import Entite.Bibliotheque.Livre;
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
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.homepage.BibliothecaireForm;
import com.mycompany.myapp.gui.homepage.EtudiantForm;
import com.mycompany.myapp.services.ServiceEmprunt;
import com.mycompany.myapp.services.ServiceLivre;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author william
 */
public class Emprunt_frontForm extends Form {

	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	Utilisateur user;
	int idbiblio;
	Container c0 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
	String url_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/uploads/";
	ServiceLivre ser = new ServiceLivre();
	ServiceEmprunt ser_emp = new ServiceEmprunt();
	ArrayList<LivreEmprunte> livreempruntes;

	public Emprunt_frontForm(Form previous, Utilisateur user, int id_bibliotheque) {
		current = this;
		this.user = user;
		idbiblio = id_bibliotheque;
		setTitle("Emprunts effectués");
		setLayout(new FlowLayout(CENTER, TOP));

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

		getToolbar().addSearchCommand(e -> {
			String text = (String) e.getSource();
			if (text == null || text.length() == 0) {
				// clear search
				livreempruntes = ser_emp.getAllEmprunts(user.getId(), id_bibliotheque);
				afficher_emprunts(livreempruntes);
				getContentPane().animateLayout(150);
			} else {
				text = text.toLowerCase();
				livreempruntes = ser_emp.rechercher_livre_emprunte(text, user.getId(), id_bibliotheque);
				afficher_emprunts(livreempruntes);
				getContentPane().animateLayout(150);
			}
		}, 4);
		
		try {
			imc = EncodedImage.create("/load.png");
		} catch (IOException ex) {
			System.out.println(ex);
		}
		livreempruntes = ser_emp.getAllEmprunts(user.getId(), id_bibliotheque);
		afficher_emprunts(livreempruntes);
		add(c0);
	}

	public void afficher_emprunts(ArrayList<LivreEmprunte> livreEmpruntes) {
		c0.removeAll();
		for (LivreEmprunte l : livreEmpruntes) {
			img = URLImage.createToStorage(imc, l.getImg(), url_img + l.getImg(), URLImage.RESIZE_SCALE);
			imv = new ImageViewer(img);
			imv.setHeight(5);
			imv.setWidth(5);

			Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
			Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

			Label titre = new Label(l.getTitre());
			String etat_s = "";
			if (l.getEtat() == 0) {
				etat_s = "attente";
			}
			if (l.getEtat() == 1) {
				etat_s = "confirme";
			}
			if (l.getEtat() == 2) {
				etat_s = "refuse";
			}
			if (l.getEtat() == 3) {
				etat_s = "rendu";
			}
			Label etat = new Label("Etat : " + etat_s);
			Label dateemprunt = new Label(l.getDateEmprunt());
			Button detail = new Button("detail");
			detail.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					new EmpruntForm(current, l, user, idbiblio).show();
				}
			});
			Button annuler = new Button("annuler");
			annuler.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					if (ser_emp.annuler(l.getId_emprunt())) {
						Dialog.show("Emprunt supprimé", "Votre emprunt a bien été annulé, retournez au catalogue pour passer un nouveau emprunt", new Command("OK"));
						new Emprunt_frontForm(current, user, idbiblio).show();
					} else {
						Dialog.show("Echec d'annulation", "Votre emprunt n'a pas pu être supprimé, Veillez contacter le bibliothécaire pour plus d'infos", new Command("OK"));
					}
				}
			});

			if (l.getEtat() == 0) {
				c1.addAll(titre, etat, dateemprunt, detail, annuler);
			} else {
				c1.addAll(titre, etat, dateemprunt, detail);
			}

			c.addAll(imv, c1);
			c0.add(c);
		}
	}
}
