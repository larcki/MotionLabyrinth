package com.motionlabyrinth.menu;

import com.motionlabyrinth.level.Level;
import com.motionlabyrinth.level.LevelOne;
import com.motionlabyrinth.level.LevelTwo;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Menu bar with levels items.
 *
 * @author lassi
 */
public class GameMenuBar extends MenuBar {

    private Menu levelsMenu;
    private LevelSelectedEvent levelSelectedEvent;

    public GameMenuBar() {
        levelsMenu = new Menu();
        levelsMenu.setText("Levels");
        getMenus().add(levelsMenu);
        addLevel(new LevelOne(), "Level 1");
        addLevel(new LevelTwo(), "Level 2");
    }

    private void addLevel(Level level, String text) {
        MenuItem menuItem = new MenuItem();
        menuItem.setText(text);
        menuItem.setOnAction(event -> levelSelectedEvent.select(level));
        levelsMenu.getItems().add(menuItem);
    }

    public void setOnLevelSelectedEvent(LevelSelectedEvent levelSelectedEvent) {
        this.levelSelectedEvent = levelSelectedEvent;
    }

}
