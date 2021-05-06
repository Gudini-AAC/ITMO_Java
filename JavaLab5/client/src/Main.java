import java.io.*;
import database.Database;
import database.Server;
import java.lang.Exception;
import commands.CommandRunner;
import commands.CommandExecutionContext;
import structures.InteractionStreams;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;

import gui.*;

public class Main extends Application {
	
	public static void main(String[] args) { launch(args); }
	
	@Override
	public void start(Stage stage) throws Exception {
		Server server = null;
		try {
			server = new Server("localhost", 5454);
		} catch (IOException e) {
			System.out.println("Network access is denied. Exiting.");
            System.exit(65);
		}
		
		Database database = new Database(server);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		InteractionStreams userIO       = new InteractionStreams(reader, writer, writer);
		CommandExecutionContext context = new CommandExecutionContext(userIO);
		
		Scene scene = new Scene(new HBox());
		ScreenManager manager = new ScreenManager(scene, stage, database);
		manager.changeScreenTo(ScreenType.LOGIN_SCREEN);
		
		stage.setTitle("Database manager");
		stage.setScene(scene);
		stage.show();
		
		try {
			server.stop();
		} catch (IOException e) {
			System.out.println("Network access is denied. Exiting.");
            System.exit(65);
		}
	}
	
}