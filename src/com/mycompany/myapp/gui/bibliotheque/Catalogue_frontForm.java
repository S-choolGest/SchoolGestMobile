/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

import Entite.Bibliotheque.Livre;
import Entite.Utilisateur.Utilisateur;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.homepage.BibliothecaireForm;
import com.mycompany.myapp.gui.homepage.EtudiantForm;
import com.mycompany.myapp.services.ServiceLivre;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author william
 */
public class Catalogue_frontForm extends Form {

	Form current;
	Utilisateur user;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	int idbiblio;
	Container c = new Container(new GridLayout(7, 3));
	String url_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/uploads/";
	ServiceLivre ser = new ServiceLivre();
	ArrayList<Livre> livres;

	public Catalogue_frontForm(Form previous, Utilisateur user, int id_bibliotheque) {
		current = this;
		this.user = user;
		idbiblio = id_bibliotheque;
		setTitle("Catalogue des livres");
		setLayout(new FlowLayout(CENTER, LEFT));
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

		getToolbar().addCommandToRightBar("Revenir", null, (evt) -> {
			previous.show();
		});

		getToolbar().addSearchCommand(e -> {
			String text = (String) e.getSource();
			if (text == null || text.length() == 0) {
				// clear search
				livres = ser.getAllLivresByBiblio(id_bibliotheque);
				afficher_livres(livres);
				getContentPane().animateLayout(150);
			} else {
				text = text.toLowerCase();
				livres = ser.rechercher_livreFront(text, idbiblio);
				afficher_livres(livres);
				getContentPane().animateLayout(150);
			}
		}, 4);
		
		try {
			imc = EncodedImage.create("/load.png");
		} catch (IOException ex) {
			System.out.println(ex);
		}
		livres = ser.getAllLivresByBiblio(id_bibliotheque);
		afficher_livres(livres);
		add(c);

	}

	public void afficher_livres(ArrayList<Livre> livres) {
		c.removeAll();
		for (Livre l : livres) {
			img = URLImage.createToStorage(imc, l.getImg(), url_img + l.getImg(), URLImage.RESIZE_SCALE_TO_FILL);
			imv = new ImageViewer(img);
			imv.addPointerPressedListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					new LivreEditForm(current, l, user).show();
				}
			});
			Button detail = new Button("detail");
			detail.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					new LivreForm(current, l, user).show();
				}
			});
			Container livr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			livr.addAll(imv, detail);
			c.add(livr);
		}
	}
}
