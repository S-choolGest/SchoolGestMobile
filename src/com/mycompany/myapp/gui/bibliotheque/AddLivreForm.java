/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

import Entite.Bibliotheque.Livre;
import Entite.Utilisateur.Utilisateur;
import com.codename1.capture.Capture;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.homepage.BibliothecaireForm;
import com.mycompany.myapp.services.ServiceLivre;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author william
 */
public class AddLivreForm extends Form {

	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	String url_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/uploads/";
	ServiceLivre ser = new ServiceLivre();

	public AddLivreForm(Form previous, Utilisateur user) {
		current = this;
		setTitle("Ajouter un livre");
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
//		img = URLImage.createToStorage(imc, l.getImg(), url_img + l.getImg(), URLImage.RESIZE_SCALE);
//		System.out.println(url_img + l.getImg());
//		imv = new ImageViewer(img);
//		add(imv);
		Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		Label ltitre = new Label("Titre : ");
		ltitre.getAllStyles().setFgColor(ColorUtil.BLUE);
		TextField ftitre = new TextField("");
		Container titre = new Container(new BoxLayout(BoxLayout.X_AXIS));
		titre.addAll(ltitre, ftitre);

		Label lauteur = new Label("Auteur : ");
		TextField fauteur = new TextField("");
		Container auteur = new Container(new BoxLayout(BoxLayout.X_AXIS));
		auteur.addAll(lauteur, fauteur);

		Label lediteur = new Label("Editeur : ");
		TextField fediteur = new TextField("");
		Container editeur = new Container(new BoxLayout(BoxLayout.X_AXIS));
		editeur.addAll(lediteur, fediteur);

		Label lpages = new Label("Pages : ");
		TextField fpages = new TextField("", "1234", 4, TextArea.NUMERIC);
		Container pages = new Container(new BoxLayout(BoxLayout.X_AXIS));
		pages.addAll(lpages, fpages);

		Label lqte = new Label("Quantite : ");
		TextField fqte = new TextField("", "1234", 4, TextArea.NUMERIC);
		Container qte = new Container(new BoxLayout(BoxLayout.X_AXIS));
		qte.addAll(lqte, fqte);

		Label lcategorie = new Label("Catégorie : ");
		TextField fcategorie = new TextField("");
		Container categorie = new Container(new BoxLayout(BoxLayout.X_AXIS));
		categorie.addAll(lcategorie, fcategorie);

		Label ldatesortie = new Label("Date de sortie : ");
		TextField fdatesortie = new TextField("");
		Picker pdatesortie = new Picker();
		Container datesortie = new Container(new BoxLayout(BoxLayout.X_AXIS));
		datesortie.addAll(ldatesortie, pdatesortie);

		c.addAll(titre, auteur, editeur, categorie, pages, qte, datesortie);

		add(c);

		Button uploadImg = new Button("Télécharger une image");
		uploadImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				MultipartRequest cr = new MultipartRequest();
				String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
				String url = "http://localhost/appliweb/AppliWeb/SchoolGest/web/upload/upload.php";
				cr.setUrl(url);
				cr.setPost(true);
				String mime = "image/jpeg";
				try {
					cr.addData("file", filePath, mime);
				} catch (IOException ex) {
					System.out.println(ex);
				}
				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				System.out.println(df.format(dateobj));
				cr.setFilename("file", df.format(dateobj));//any unique name you want

				InfiniteProgress prog = new InfiniteProgress();
				Dialog dlg = prog.showInifiniteBlocking();
				cr.setDisposeOnCompletion(dlg);
				NetworkManager.getInstance().addToQueueAndWait(cr);
			}
		});
		add(uploadImg);

		Button add = new Button("Ajouter");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (ftitre.getText().length() == 0 || fauteur.getText().length() == 0 || fediteur.getText().length() == 0 || fcategorie.getText().length() == 0 || fpages.getText().length() == 0 || fqte.getText().length() == 0) {
					Dialog.show("echec", "Veillez remplir tous les champs", new Command("OK"));
				} else {
					Livre l = new Livre();
					l.setTitre(ftitre.getText());
					l.setAuteur(fauteur.getText());
					l.setCategorie(fcategorie.getText());
					l.setEditeur(fediteur.getText());
					l.setQuantite(Integer.valueOf(fqte.getText()));
					l.setTaille(Integer.valueOf(fpages.getText()));
					boolean resultOK = ser.addLivre(l);
					if (resultOK) {
						Dialog.show("Succès", "Ajout réussi", new Command("OK"));
						new Catalogue_backForm(current, user).show();
					} else {
						Dialog.show("Echec", "Echec d'ajout", new Command("OK"));
					}
				}

			}
		});
		add(add);
	}
}
