package invaders;

import invaders.adapter.ConfigAdapter;
import invaders.adapter.EasyConfigAdapter;
import invaders.adapter.HardConfigAdapter;
import invaders.adapter.MediumConfigAdapter;
import javafx.application.Application;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private String showDifficultySelectionDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Selecting the game difficulty");
        alert.setHeaderText("Please select the difficulty you wish to play: ");

        ButtonType buttonEasy = new ButtonType("Easy");
        ButtonType buttonMedium = new ButtonType("Medium");
        ButtonType buttonHard = new ButtonType("Hard");
        ButtonType buttonCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonEasy, buttonMedium, buttonHard, buttonCancel);

        ConfigAdapter adapter = null;

        java.util.Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonEasy) {
            adapter = new EasyConfigAdapter();
        } else if (result.get() == buttonMedium) {
            adapter = new MediumConfigAdapter();
        } else if (result.get() == buttonHard) {
            adapter = new HardConfigAdapter();
        } else {
            return null;
        }

        return adapter.getConfigPath();
    }

    @Override
    public void start(Stage primaryStage) {
        String configPath = showDifficultySelectionDialog();
        if (configPath == null) {
            System.out.println("The user cancels the selection or closes the pop-up.");
            return;
        }
        GameEngine model = new GameEngine(configPath);
        GameWindow window = new GameWindow(model);
        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }


}
