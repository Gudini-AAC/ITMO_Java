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

import java.util.EnumMap;
import java.util.Map;

import gui.LoginScreen;
import gui.MainScreen;
import gui.ScreenType;

import database.Database;

public class ScreenManager {
	
	public ScreenManager(Scene scene, Stage owner, Database database) {
		screenMap = new EnumMap<ScreenType, Parent>(ScreenType.class);
		screenMap.put(ScreenType.LOGIN_SCREEN, LoginScreen.makeRoot(this, database));
		screenMap.put(ScreenType.MAIN_SCREEN, MainScreen.makeRoot(this, database));
		this.scene = scene;
		this.owner = owner;
	}
	
	public void resizeScreen(int x, int y) { owner.setWidth(x); owner.setHeight(y); owner.centerOnScreen(); }
	
	public void changeScreenTo(ScreenType type) {
		scene.setRoot(screenMap.get(type));
	}
	
	public Stage getOwner() { return owner; }
	
	private Stage owner;
	private Scene scene;
	private Map<ScreenType, Parent> screenMap;
}

