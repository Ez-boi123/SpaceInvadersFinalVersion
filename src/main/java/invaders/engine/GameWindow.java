package invaders.engine;

import java.util.List;
import java.util.ArrayList;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    public static Pane pane;
    private GameEngine model;
    public   static List<EntityView> entityViews =  new ArrayList<EntityView>();
    private Renderable background;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private Label scoreLabel;
    private Label timerLabel;
    // private static final double VIEWPORT_MARGIN = 280.0;
	public GameWindow(GameEngine model){
        this.model = model;
		this.width =  model.getGameWidth();
        this.height = model.getGameHeight();

        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font("Arial", 24));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(10);
        pane.getChildren().add(scoreLabel);

        timerLabel = new Label("Time: 0");
        timerLabel.setFont(new Font("Arial", 24));
        timerLabel.setTextFill(Color.WHITE);
        timerLabel.setLayoutX(width - 100);
        timerLabel.setLayoutY(10);
        pane.getChildren().add(timerLabel);
    }

	public void run() {
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }


    private void draw(){
        model.update();

        timerLabel.setText("Time: " + model.getGameTime());
        scoreLabel.setText("Score: " + model.getScore());

        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (Renderable entity : renderables){
            if (!entity.isAlive()){
                for (EntityView entityView : entityViews){
                    if (entityView.matchesEntity(entity)){
                        entityView.markForDelete();
                    }
                }
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }


        model.getGameObjects().removeAll(model.getPendingToRemoveGameObject());
        model.getGameObjects().addAll(model.getPendingToAddGameObject());
        model.getRenderables().removeAll(model.getPendingToRemoveRenderable());
        model.getRenderables().addAll(model.getPendingToAddRenderable());

        model.getPendingToAddGameObject().clear();
        model.getPendingToRemoveGameObject().clear();
        model.getPendingToAddRenderable().clear();
        model.getPendingToRemoveRenderable().clear();

        entityViews.removeIf(EntityView::isMarkedForDelete);

    }

	public Scene getScene() {
        return scene;
    }
}
