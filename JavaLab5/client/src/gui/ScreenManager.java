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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.io.BufferedReader;

import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;
import gui.LocaleType;

public class ScreenManager {
    
    public ScreenManager(Scene scene, Stage owner, Database database) {
        this.scene = scene;
        this.owner = owner;
        this.database = database;
        this.locale = LocaleType.en_GB;
        
        callbacks = new ArrayList<TimerTask>();
        loadLocale(locale);
    }
    
    void addCallback(TimerTask task, long time) {
        Timer timer = new Timer(true);
        timer.schedule(task, 0, time);
        callbacks.add(task);
    }
    
    public boolean loadLocale(LocaleType type) {
        locale = type;
        
        for (TimerTask callback : callbacks) 
            callback.cancel();
        callbacks.clear();
        
        String path = "./assets/locale_" + type.toString() + ".properties";
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            Properties newProp = new Properties();
            newProp.load(reader);
            prop = newProp;
            
            screenMap = new EnumMap<ScreenType, Parent>(ScreenType.class);
            screenMap.put(ScreenType.LOGIN_SCREEN, LoginScreen.makeRoot(this, database));
            screenMap.put(ScreenType.MAIN_SCREEN,  MainScreen.makeRoot(this,  database));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String getString(String name) { return prop.getProperty(name); }
    
    public void resizeScreen(int x, int y) { owner.setWidth(x); owner.setHeight(y); owner.centerOnScreen(); }
    
    public void changeScreenTo(ScreenType type) { scene.setRoot(screenMap.get(type)); }
    
    public Stage getOwner() { return owner; }
    
    public LocaleType getLocale() { return locale; }
    
    private LocaleType locale;
    private Stage owner;
    private Scene scene;
    private Database database;
    private Properties prop;
    private List<TimerTask> callbacks;
    private Map<ScreenType, Parent> screenMap;
}

