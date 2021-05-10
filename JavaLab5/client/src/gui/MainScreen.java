package gui;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.collections.FXCollections;
import java.util.function.Predicate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

import java.lang.Integer;
import java.lang.Long;
import java.lang.Float;
import java.lang.Double;
import java.time.LocalDate;

import gui.ScreenManager;
import gui.ScreenType;
import gui.LocaleType;

import protocol.RequestRemove;
import protocol.SessionData;
import database.Database;
import structures.*;


public class MainScreen {
    static final int BUTTON_WIDTH = 100;
    
    private static class AddPersonGui implements EventHandler<ActionEvent> {
        
        public AddPersonGui(ScreenManager manager, Database database) { this.manager = manager; this.database = database; }
        
        @Override
        public void handle(ActionEvent event) {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(manager.getOwner());
            
            GridPane grid = new GridPane();
            ColumnConstraints column0 = new ColumnConstraints(); column0.setPercentWidth(25);
            ColumnConstraints column1 = new ColumnConstraints(); column1.setPercentWidth(75);
            grid.getColumnConstraints().addAll(column0, column1);
            grid.setAlignment(Pos.CENTER_LEFT);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            ComboBox<Color> eyeColorCombo = new ComboBox<>();
            ComboBox<Color> hairColorCombo = new ComboBox<>();
            ComboBox<Country> nationalityCombo = new ComboBox<>();
            eyeColorCombo.setItems(FXCollections.observableArrayList(Color.values()));
            hairColorCombo.setItems(FXCollections.observableArrayList(Color.values()));
            nationalityCombo.setItems(FXCollections.observableArrayList(Country.values()));

            TextField nameTextField = new TextField();
            TextField heightTextField = new TextField();
            
            HBox coordinatesGrid = new HBox(); coordinatesGrid.setSpacing(10);
            TextField coordinatesX = new TextField(); HBox.setHgrow(coordinatesX, Priority.ALWAYS);
            TextField coordinatesY = new TextField(); HBox.setHgrow(coordinatesY, Priority.ALWAYS);
            coordinatesGrid.getChildren().addAll(coordinatesX, coordinatesY);
        
            HBox locationGrid = new HBox(); locationGrid.setSpacing(10);
            TextField locationX = new TextField(); HBox.setHgrow(locationX, Priority.ALWAYS);
            TextField locationY = new TextField(); HBox.setHgrow(locationY, Priority.ALWAYS);
            TextField locationZ = new TextField(); HBox.setHgrow(locationZ, Priority.ALWAYS);
            locationGrid.getChildren().addAll(locationX, locationY, locationZ);
            TextField locationName = new TextField();
            
            Text actiontarget = new Text(); actiontarget.setFill(javafx.scene.paint.Color.FIREBRICK);
            
            HBox buttonGrid = new HBox(); buttonGrid.setSpacing(10);
            Button closeButton  = new Button("Close");  HBox.setHgrow(closeButton, Priority.ALWAYS);
            Button submitButton = new Button("Submit"); HBox.setHgrow(submitButton, Priority.ALWAYS);
            buttonGrid.getChildren().addAll(submitButton, closeButton);
            closeButton.setOnAction((closeEvent)-> { dialog.close(); });
            submitButton.setOnAction((submitEvent)-> {
                Person person = new Person();
                person.coordinates = new Coordinates();
                person.location = new Location();
                
                person.name = nameTextField.getCharacters().toString();
                if (person.name == null) { actiontarget.setText(manager.getString("illigal_name")); return; }
                
                try {
                    String str = coordinatesX.getCharacters().toString();
                    person.coordinates.x = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_coord_x"));
                    return;
                }
                
                try {
                    String str = coordinatesY.getCharacters().toString();
                    float y = Float.parseFloat(str);
                    if (y <= 971) {
                        person.coordinates.y = y;
                    } else {
                        actiontarget.setText(manager.getString("coord_y_out_of_range"));
                        return;
                    }
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_coord_y"));
                    return;
                }
                
                try {
                    String str = heightTextField.getCharacters().toString();
                    long height = Long.parseLong(str);
                    if (height > 0) {
                        person.height = height;
                    } else {
                        actiontarget.setText(manager.getString("height_out_of_range"));
                        return;
                    }
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_height"));
                    return;
                }
                
                try {
                    String str = locationX.getCharacters().toString();
                    person.location.x = new Long(Long.parseLong(str));
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_loc_x"));
                    return;
                }
                
                try {
                    String str = locationY.getCharacters().toString();
                    person.location.y = Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_loc_y"));
                    return;
                }
                
                try {
                    String str = locationZ.getCharacters().toString();
                    person.location.z = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_loc_z"));
                    return;
                }
                
                person.location.name = locationName.getCharacters().toString();
                
                person.eyeColor = eyeColorCombo.getSelectionModel().getSelectedItem();
                person.hairColor = hairColorCombo.getSelectionModel().getSelectedItem();
                person.nationality = nationalityCombo.getSelectionModel().getSelectedItem();
                
                if (person.eyeColor == null)    { actiontarget.setText(manager.getString("eye_color_must_be_set")); return; }
                if (person.nationality == null) { actiontarget.setText(manager.getString("hair_color_must_be_set")); return; }
                
                if (!database.add(person)) {
                    actiontarget.setText(manager.getString("connection_lost"));
                } else {
                    dialog.close();
                }
            });
        
            grid.add(new Label(manager.getString("in_lab_name")),          0, 0); grid.add(nameTextField,    1, 0);
            grid.add(new Label(manager.getString("in_lab_height")),        0, 1); grid.add(heightTextField,  1, 1);
            grid.add(new Label(manager.getString("in_lab_coordinates")),   0, 2); grid.add(coordinatesGrid,  1, 2);
            grid.add(new Label(manager.getString("in_lab_location")),      0, 3); grid.add(locationGrid,     1, 3);
            grid.add(new Label(manager.getString("in_lab_location_name")), 0, 4); grid.add(locationName,     1, 4);
            grid.add(new Label(manager.getString("in_lab_eye_color")),     0, 5); grid.add(eyeColorCombo,    1, 5);
            grid.add(new Label(manager.getString("in_lab_hair_color")),    0, 6); grid.add(hairColorCombo,   1, 6);
            grid.add(new Label(manager.getString("in_lab_nationality")),   0, 7); grid.add(nationalityCombo, 1, 7);
            grid.add(buttonGrid,                                           0, 8); grid.add(actiontarget,     1, 8);
            
            Scene dialogScene = new Scene(grid);
            dialog.setTitle(manager.getString("add_person"));
            dialog.setScene(dialogScene);
            dialog.show();
        }
        
        private ScreenManager manager;
        private Database database;
    }
    
    private static class ReplacePersonGui implements EventHandler<ActionEvent> {
        
        public ReplacePersonGui(ScreenManager manager, Database database) { this.manager = manager; this.database = database; }
        
        @Override
        public void handle(ActionEvent event) {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(manager.getOwner());
            
            GridPane grid = new GridPane();
            ColumnConstraints column0 = new ColumnConstraints(); column0.setPercentWidth(25);
            ColumnConstraints column1 = new ColumnConstraints(); column1.setPercentWidth(75);
            grid.getColumnConstraints().addAll(column0, column1);
            grid.setAlignment(Pos.CENTER_LEFT);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            ComboBox<Color> eyeColorCombo = new ComboBox<>();
            ComboBox<Color> hairColorCombo = new ComboBox<>();
            ComboBox<Country> nationalityCombo = new ComboBox<>();
            eyeColorCombo.setItems(FXCollections.observableArrayList(Color.values()));
            hairColorCombo.setItems(FXCollections.observableArrayList(Color.values()));
            nationalityCombo.setItems(FXCollections.observableArrayList(Country.values()));

            TextField nameTextField = new TextField();
            TextField heightTextField = new TextField();
            TextField idTextField = new TextField();
            
            HBox coordinatesGrid = new HBox(); coordinatesGrid.setSpacing(10);
            TextField coordinatesX = new TextField(); HBox.setHgrow(coordinatesX, Priority.ALWAYS);
            TextField coordinatesY = new TextField(); HBox.setHgrow(coordinatesY, Priority.ALWAYS);
            coordinatesGrid.getChildren().addAll(coordinatesX, coordinatesY);
        
            HBox locationGrid = new HBox(); locationGrid.setSpacing(10);
            TextField locationX = new TextField(); HBox.setHgrow(locationX, Priority.ALWAYS);
            TextField locationY = new TextField(); HBox.setHgrow(locationY, Priority.ALWAYS);
            TextField locationZ = new TextField(); HBox.setHgrow(locationZ, Priority.ALWAYS);
            locationGrid.getChildren().addAll(locationX, locationY, locationZ);
            TextField locationName = new TextField();
            
            Text actiontarget = new Text(); actiontarget.setFill(javafx.scene.paint.Color.FIREBRICK);
            
            HBox buttonGrid = new HBox(); buttonGrid.setSpacing(10);
            Button closeButton  = new Button("Close");  HBox.setHgrow(closeButton,  Priority.ALWAYS);
            Button submitButton = new Button("Submit"); HBox.setHgrow(submitButton, Priority.ALWAYS);
            buttonGrid.getChildren().addAll(submitButton, closeButton);
            closeButton.setOnAction((closeEvent)-> { dialog.close(); });
            submitButton.setOnAction((submitEvent)-> {
                Person person = new Person();
                person.coordinates = new Coordinates();
                person.location = new Location();
                
                long id;
                try {
                    String str = idTextField.getCharacters().toString();
                    id = Long.parseLong(str);
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_id"));
                    return;
                }
                
                person.name = nameTextField.getCharacters().toString();
                if (person.name == null) { actiontarget.setText(manager.getString("illigal_name")); return; }
                
                try {
                    String str = coordinatesX.getCharacters().toString();
                    person.coordinates.x = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_coord_x"));
                    return;
                }
                
                try {
                    String str = coordinatesY.getCharacters().toString();
                    float y = Float.parseFloat(str);
                    if (y <= 971) {
                        person.coordinates.y = y;
                    } else {
                        actiontarget.setText(manager.getString("coord_y_out_of_range"));
                        return;
                    }
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_coord_y"));
                    return;
                }
                
                try {
                    String str = heightTextField.getCharacters().toString();
                    long height = Long.parseLong(str);
                    if (height > 0) {
                        person.height = height;
                    } else {
                        actiontarget.setText(manager.getString("height_out_of_range"));
                        return;
                    }
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_height"));
                    return;
                }
                
                try {
                    String str = locationX.getCharacters().toString();
                    person.location.x = new Long(Long.parseLong(str));
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_loc_x"));
                    return;
                }
                
                try {
                    String str = locationY.getCharacters().toString();
                    person.location.y = Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_loc_y"));
                    return;
                }
                
                try {
                    String str = locationZ.getCharacters().toString();
                    person.location.z = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_loc_z"));
                    return;
                }
                
                person.location.name = locationName.getCharacters().toString();
                
                person.eyeColor = eyeColorCombo.getSelectionModel().getSelectedItem();
                person.hairColor = hairColorCombo.getSelectionModel().getSelectedItem();
                person.nationality = nationalityCombo.getSelectionModel().getSelectedItem();
                
                if (person.eyeColor == null)    { actiontarget.setText(manager.getString("eye_color_must_be_set")); return; }
                if (person.nationality == null) { actiontarget.setText(manager.getString("hair_color_must_be_set")); return; }
                
                person.id = id;
                if (!database.replace(person)) {
                    actiontarget.setText(manager.getString("connection_lost"));
                } else {
                    dialog.close();
                }
            });

            grid.add(new Label(manager.getString("in_lab_id")),            0, 0); grid.add(idTextField,      1, 0);
            grid.add(new Label(manager.getString("in_lab_name")),          0, 1); grid.add(nameTextField,    1, 1);
            grid.add(new Label(manager.getString("in_lab_height")),        0, 2); grid.add(heightTextField,  1, 2);
            grid.add(new Label(manager.getString("in_lab_coordinates")),   0, 3); grid.add(coordinatesGrid,  1, 3);
            grid.add(new Label(manager.getString("in_lab_location")),      0, 4); grid.add(locationGrid,     1, 4);
            grid.add(new Label(manager.getString("in_lab_location_name")), 0, 5); grid.add(locationName,     1, 5);
            grid.add(new Label(manager.getString("in_lab_eye_color")),     0, 6); grid.add(eyeColorCombo,    1, 6);
            grid.add(new Label(manager.getString("in_lab_hair_color")),    0, 7); grid.add(hairColorCombo,   1, 7);
            grid.add(new Label(manager.getString("in_lab_nationality")),   0, 8); grid.add(nationalityCombo, 1, 8);
            grid.add(buttonGrid,                                           0, 9); grid.add(actiontarget,     1, 9);
            
            Scene dialogScene = new Scene(grid);
            dialog.setTitle(manager.getString("replace_person"));
            dialog.setScene(dialogScene);
            dialog.show();
        }
        
        private ScreenManager manager;
        private Database database;
    }
    
    public static Parent makeRoot(ScreenManager manager, Database database) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setStyle("-fx-border-style: solid inside;");
        vbox.setStyle("-fx-border-width: 1;");
        vbox.setStyle("-fx-border-insets: 1;");
        vbox.setStyle("-fx-border-radius: 1;");
        vbox.setStyle("-fx-border-color: black;");

        ComboBox<LocaleType> localeCombo = new ComboBox<>();
        localeCombo.setPrefWidth(BUTTON_WIDTH);
        localeCombo.setItems(FXCollections.observableArrayList(LocaleType.values()));        
        localeCombo.getSelectionModel().select(manager.getLocale());        
        localeCombo.setOnAction((event)-> {
            manager.loadLocale(localeCombo.getSelectionModel().getSelectedItem());
            manager.changeScreenTo(ScreenType.MAIN_SCREEN);
        });
                        
        Button shuffleButton = new Button(manager.getString("shuffle"));
        shuffleButton.setPrefWidth(BUTTON_WIDTH);
        shuffleButton.setOnAction((event)-> { database.shuffle(); });
 
        Button addPersonButton = new Button(manager.getString("add___"));
        addPersonButton.setPrefWidth(BUTTON_WIDTH);
        addPersonButton.setOnAction(new AddPersonGui(manager, database));
 
        Button replacePersonButton = new Button(manager.getString("update___"));
        replacePersonButton.setPrefWidth(BUTTON_WIDTH);
        replacePersonButton.setOnAction(new ReplacePersonGui(manager, database));
        
        Button removePersonButton = new Button(manager.getString("remove___"));
        removePersonButton.setPrefWidth(BUTTON_WIDTH);
        removePersonButton.setOnAction((event)-> {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(manager.getOwner());
            
            GridPane grid = new GridPane();
            ColumnConstraints column0 = new ColumnConstraints(); column0.setPercentWidth(50);
            ColumnConstraints column1 = new ColumnConstraints(); column1.setPercentWidth(50);
            grid.getColumnConstraints().addAll(column0, column1);
            grid.setAlignment(Pos.CENTER_LEFT);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            ComboBox<RequestRemove.Key> removeKeyCombo = new ComboBox<>();
            removeKeyCombo.setItems(FXCollections.observableArrayList(RequestRemove.Key.values()));
            TextField valueField = new TextField();
            Text actiontarget = new Text();
            actiontarget.setFill(javafx.scene.paint.Color.FIREBRICK);
            
            HBox buttonGrid = new HBox(); buttonGrid.setSpacing(10);
            Button closeButton  = new Button("Close");  HBox.setHgrow(closeButton, Priority.ALWAYS);
            Button submitButton = new Button("Submit"); HBox.setHgrow(submitButton, Priority.ALWAYS);
            buttonGrid.getChildren().addAll(submitButton, closeButton);
            closeButton.setOnAction((closeEvent)-> { dialog.close(); });
            submitButton.setOnAction((closeEvent)-> {            
                RequestRemove.Key key = removeKeyCombo.getSelectionModel().getSelectedItem();
                if (key == null) { actiontarget.setText(manager.getString("remove_key_must_be_set")); return; }
                
                final long value;
                try {
                    String str = valueField.getCharacters().toString();
                    value = Long.parseLong(str);
                } catch (NumberFormatException e) {
                    actiontarget.setText(manager.getString("unable_to_parse_value"));
                    return;
                }
                
                if (!database.removeIf(key, new Long(value))) {
                    actiontarget.setText(manager.getString("no_elements_removed"));
                } else {
                    dialog.close();
                }
            });
            
            grid.add(new Label(manager.getString("remove_key")),   0, 0); grid.add(removeKeyCombo, 1, 0);
            grid.add(new Label(manager.getString("remove_value")), 0, 1); grid.add(valueField,     1, 1);
            grid.add(buttonGrid,                                   0, 2); grid.add(actiontarget,   1, 2);
            
            Scene dialogScene = new Scene(grid);
            dialog.setTitle(manager.getString("remove"));
            dialog.setScene(dialogScene);
            dialog.show();
        });
        
        vbox.getChildren().addAll(localeCombo, shuffleButton, addPersonButton, replacePersonButton, removePersonButton);
        
        TableView<Person> table = new TableView<Person>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Person, Long> idColumn = new TableColumn<>(manager.getString("in_lab_id"));
        TableColumn<Person, String> nameColumn = new TableColumn<>(manager.getString("in_lab_name"));
        TableColumn<Person, Long> heightColumn = new TableColumn<>(manager.getString("in_lab_height"));
        
        TableColumn<Person, Color> hairColorColumn = new TableColumn<>(manager.getString("in_lab_hair_color"));
        TableColumn<Person, Color> eyeColorColumn = new TableColumn<>(manager.getString("in_lab_eye_color"));
        TableColumn<Person, Country> nationalityColorColumn = new TableColumn<>(manager.getString("in_lab_nationality"));
        TableColumn<Person, LocalDate> dateColumn = new TableColumn<>(manager.getString("creation_date"));
        
        TableColumn<Person, Coordinates> coordinatesColumn = new TableColumn<>(manager.getString("in_lab_coordinates"));
        TableColumn<Person, Integer> coordinatesXColumn = new TableColumn<>("X");
        TableColumn<Person, Float> coordinatesYColumn = new TableColumn<>("Y");
        
        TableColumn<Person, Location> locationColumn = new TableColumn<>(manager.getString("in_lab_location"));
        TableColumn<Person, Long> locationXColumn = new TableColumn<>("X");
        TableColumn<Person, Double> locationYColumn = new TableColumn<>("Y");
        TableColumn<Person, Integer> locationZColumn = new TableColumn<>("Z");
        TableColumn<Person, String> locationNameColumn = new TableColumn<>(manager.getString("in_lab_location_name"));
        
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        
        hairColorColumn.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        eyeColorColumn.setCellValueFactory(new PropertyValueFactory<>("eyeColor"));
        nationalityColorColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        
        coordinatesColumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        coordinatesXColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatesX"));
        coordinatesYColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatesY"));
        coordinatesColumn.getColumns().addAll(coordinatesXColumn, coordinatesYColumn);
        
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationXColumn.setCellValueFactory(new PropertyValueFactory<>("locationX"));
        locationYColumn.setCellValueFactory(new PropertyValueFactory<>("locationY"));
        locationZColumn.setCellValueFactory(new PropertyValueFactory<>("locationZ"));
        locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        locationColumn.getColumns().addAll(locationXColumn, locationYColumn, locationZColumn, locationNameColumn);

        class DatabasePoll extends TimerTask {
            boolean firstTime = true;
            
            public void run() {
                if (firstTime | (database.isUpdateRequested() && SessionData.accessAllowed)) {
                    List<Person> persons = database.retrieve();
                    
                    if (persons != null) {
                        table.getItems().remove(0, table.getItems().size());
                        for (Person person : persons) table.getItems().add(person);
                        table.sort();
                    }
                    
                    firstTime = false;
                }
            }
        }
        
        //Timer timer = new Timer(true);
        //timer.schedule(new DatabasePoll(), 0, 100);
        manager.addCallback(new DatabasePoll(), 100);
        
        table.getColumns().add(idColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(heightColumn);
        table.getColumns().add(hairColorColumn);
        table.getColumns().add(eyeColorColumn);
        table.getColumns().add(nationalityColorColumn);
        table.getColumns().add(dateColumn);
        table.getColumns().add(coordinatesColumn);
        table.getColumns().add(locationColumn);
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(vbox);
        root.setCenter(table);
        
        return root;
    }
    
}
