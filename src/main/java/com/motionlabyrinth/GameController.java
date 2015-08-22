package com.motionlabyrinth;

import com.motionlabyrinth.grid.GameGrid;
import com.motionlabyrinth.level.Level;
import com.motionlabyrinth.leap.UIPointer;
import com.motionlabyrinth.menu.GameMenuBar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * Controller that handles game initializations and bindings.
 *
 * @author lassi
 */
public class GameController {

    @FXML
    private AnchorPane gridLayout;

    @FXML
    private GameMenuBar menuBar;

    @FXML
    private Label inGameTime;

    @FXML
    private VBox scoreLayout;

    @FXML
    private Label mistakes;

    @FXML
    private Label scoreTime;

    @FXML
    private Button tryAgain;

    private GameGrid gameGrid;
    private GameTimer gameTimer;
    private Level selectedLevel;

    @FXML
    public void initialize() {
        Rectangle movable = createAndBindMovable();
        scoreLayout.setVisible(false);
        gameTimer = new GameTimer(inGameTime);
        menuBar.setOnLevelSelectedEvent(level -> initNewGame(movable, level));
        tryAgain.setOnAction(event -> initNewGame(movable, selectedLevel));
    }

    private void initNewGame(Rectangle movable, Level level) {
        removeExistingGameIfPresent();
        scoreLayout.setVisible(false);
        selectedLevel = level;
        gameGrid = createGameGrid(level);
        gameGrid.setLevelStartEvent(gameTimer::start);
        gameGrid.setLevelCompletedEvent(this::levelCompleted);
        gridLayout.getChildren().add(gameGrid);
        UIPointer.getInstance().setFrameChangedEvent(() -> gameGrid.checkTouches(movable));
    }

    private void removeExistingGameIfPresent() {
        if (gameGrid != null) {
            gridLayout.getChildren().remove(gameGrid);
        }
    }

    private GameGrid createGameGrid(Level level) {
        GameGrid gameGrid = new GameGrid(level.getBuilder());
        AnchorPane.setBottomAnchor(gameGrid, 0.0);
        AnchorPane.setTopAnchor(gameGrid, 0.0);
        AnchorPane.setRightAnchor(gameGrid, 0.0);
        AnchorPane.setLeftAnchor(gameGrid, 0.0);
        return gameGrid;
    }

    private void levelCompleted() {
        gameTimer.stopAndHide();
        UIPointer.getInstance().removeFrameChangedEvent();
        gameGrid.blur();
        gameGrid.toBack();
        scoreLayout.setVisible(true);
        mistakes.setText(String.valueOf(gameGrid.getCountOfHoveredWallTiles()));
        scoreTime.setText(inGameTime.getText());
    }

    private Rectangle createAndBindMovable() {
        Rectangle movable = new Rectangle();
        movable.setHeight(10);
        movable.setWidth(10);
        movable.setLayoutX(0);
        movable.setLayoutX(0);
        movable.xProperty().bind(UIPointer.getInstance().pointableXProperty());
        movable.yProperty().bind(UIPointer.getInstance().pointableYProperty());
        gridLayout.getChildren().add(movable);
        return movable;
    }

}
