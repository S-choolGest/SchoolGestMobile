/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import Entite.Utilisateur.Utilisateur;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.gui.bibliotheque.GuiComponent;
import com.mycompany.myapp.gui.homepage.BibliothecaireForm;
import com.mycompany.myapp.gui.homepage.EtudiantForm;
import com.mycompany.myapp.services.ServiceUtilisateur;
import java.io.IOException;

/**
 *
 * @author william
 */
public class LoginForm extends Form {

	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	String url_login_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/img/online-school.jpg";

	public LoginForm() {
		current = this;
		setTitle("se connecter");
		setLayout(new FlowLayout(CENTER, CENTER));
		Container c = new Container(BoxLayout.y());
		try {
			imc = EncodedImage.create("/load.png");
		} catch (IOException ex) {
			System.out.println(ex);
		}
		img = URLImage.createToStorage(imc, "login", url_login_img, URLImage.RESIZE_SCALE);
		imv = new ImageViewer(img);
		TextField login = new TextField("", "username");
		TextField pwd = new TextField("", "mot de passe", 0, TextArea.PASSWORD);
		Button connect = new Button("se connecter");
		c.addAll(imv, login, pwd, connect);
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if ((login.getText().length() == 0) || (pwd.getText().length() == 0)) {
					Dialog.show("Alert", "remplir tous les champs", new Command("OK"));
				} else {
					try {
						Utilisateur u = new Utilisateur(login.getText(), pwd.getText());
						Utilisateur user  = new ServiceUtilisateur().connecter(u);
						if (!user.getRole().equals("")) {
							Dialog.show("Succès", "Connection réussie", new Command("OK"));
							if(user.getRole().equals("ROLE_BIBLIOTHECAIRE"))
								//new GuiComponent().show();
								new BibliothecaireForm(current, user).show();
							if(user.getRole().equals("ROLE_ETUDIANT"))
								new EtudiantForm(current, user).show();
							if(user.getRole().equals("ROLE_SCOLARITE"))
								new Bibliotheque_frontForm(current).show();
							if(user.getRole().equals("ROLE_PROFESSEUR"))
								new Bibliotheque_frontForm(current).show();
							if(user.getRole().equals("ROLE_PARENT"))
								new Bibliotheque_frontForm(current).show();
							if(user.getRole().equals("ROLE_ADMIN"))
								new Bibliotheque_frontForm(current).show();
						} else {
							Dialog.show("ERROR", "username ou mot de passe incorrect, veillez ressayer ...", new Command("OK"));
						}
					} catch (Exception e) {
						Dialog.show("ERROR", e.toString(), new Command("OK"));
					}

				}
			}
		});
		//connect.addActionListener(e-> new Bibliotheque_frontForm(current).show());

		add(c);

	}
}
