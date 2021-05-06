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

public class LoginScreen {

	public static Parent makeRoot(ScreenManager manager, Database database) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text("Database manager");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label loginLabel = new Label("User Name:");
		grid.add(loginLabel, 0, 1);

		TextField loginTextField = new TextField();
		grid.add(loginTextField, 1, 1);

		Label passwordLabel = new Label("Password:");
		grid.add(passwordLabel, 0, 2);

		PasswordField passwordBox = new PasswordField();
		grid.add(passwordBox, 1, 2);
		
		Button singInButton = new Button("Sign in");
		HBox singInButtonHorizontalBox = new HBox(10);
		singInButtonHorizontalBox.setAlignment(Pos.BOTTOM_RIGHT);
		singInButtonHorizontalBox.getChildren().add(singInButton);
		grid.add(singInButtonHorizontalBox, 1, 4);
		
		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		singInButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String login = loginTextField.getCharacters().toString();
				if (true || "Путин".equals(login)) {
					manager.changeScreenTo(ScreenType.MAIN_SCREEN);
					manager.resizeScreen(1600, 900);
					actiontarget.setText("");
				} else {
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Неверный логин или пароль.");
				}
			}
		});
		
		return grid;
	}
	
}
