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
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.services.ServiceUtilisateur;
import java.io.IOException;

/**
 *
 * @author william
 */
public class Bibliotheque_frontForm extends Form{
	Form current;
	ImageViewer imv;
	Image img;
	EncodedImage imc;
	String url_login_img = "http://localhost/appliweb/AppliWeb/SchoolGest/web/front/images/bibliotheque_cover.jpg";

	public Bibliotheque_frontForm(Form previous) {
		current = this;
		setTitle("Bibliotheque - Menu");
		setLayout(new FlowLayout(CENTER, CENTER));
		Container c = new Container(BoxLayout.y());
		try {
			imc = EncodedImage.create("/load.png");
		} catch (IOException ex) {
			System.out.println(ex);
		}
		img = URLImage.createToStorage(imc, "biblio", url_login_img, URLImage.RESIZE_SCALE);
		imv = new ImageViewer(img);
		Button btnLivre = new Button("Livre");
		Button btnEmprunt = new Button("Emprunt");
		Button btnReservation = new Button("Reservation");
		c.addAll(imv, btnLivre, btnEmprunt, btnReservation);
		
		btnLivre.addActionListener(e -> new ListLivresForm(current).show());

		add(c);

		getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
	}
}
