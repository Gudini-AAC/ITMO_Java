package gui;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;

import gui.ScreenManager;
import gui.ScreenType;

import database.Database;
import protocol.SessionData;

public class LoginScreen {

	public static Parent makeRoot(ScreenManager manager, Database database) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text(manager.getString("program_name"));
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label loginLabel = new Label(manager.getString("user_name"));
		grid.add(loginLabel, 0, 1);

		TextField loginTextField = new TextField();
		grid.add(loginTextField, 1, 1);

		Label passwordLabel = new Label(manager.getString("password"));
		grid.add(passwordLabel, 0, 2);

		PasswordField passwordBox = new PasswordField();
		grid.add(passwordBox, 1, 2);
		
		Button singInButton = new Button(manager.getString("sign_in"));
		Button singUpButton = new Button(manager.getString("sign_up"));
		
		HBox singBox = new HBox(10);
		singBox.getChildren().addAll(singUpButton, singInButton);
		grid.add(singBox, 1, 4);
		
		Text actiontarget = new Text();
		actiontarget.setFill(Color.FIREBRICK);
		
		grid.add(actiontarget, 1, 6);

		singUpButton.setOnAction((ActionEvent e)-> {
			SessionData.login = loginTextField.getCharacters().toString();
			SessionData.password = passwordBox.getCharacters().toString();
			SessionData.accessAllowed = database.register();
			
			if (SessionData.accessAllowed) {
				manager.changeScreenTo(ScreenType.MAIN_SCREEN);
				manager.resizeScreen(1600, 900);
				actiontarget.setText("");
			} else {
				actiontarget.setText(manager.getString("request_denied"));
			}
		});
		
		singInButton.setOnAction((ActionEvent e)-> {
			SessionData.login = loginTextField.getCharacters().toString();
			SessionData.password = passwordBox.getCharacters().toString();
			SessionData.accessAllowed = database.isAccessible();
			
			if (SessionData.accessAllowed) {
				manager.changeScreenTo(ScreenType.MAIN_SCREEN);
				manager.resizeScreen(1600, 900);
				actiontarget.setText("");
			} else {
				actiontarget.setText(manager.getString("wrong_login_password"));
			}
		});
		
		return grid;
	}
	
}
