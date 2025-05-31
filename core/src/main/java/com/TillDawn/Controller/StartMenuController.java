package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.App;
import com.TillDawn.TillDawn;
import com.TillDawn.View.StartMenu;

public class StartMenuController {
    private StartMenu view;

    public void setView(StartMenu view) {
        this.view = view;
    }

    public void handle() {
        handleKeyPress(view.getLastKeyPressed());
    }

    public void handleKeyPress(int keyCode) {
        if (view == null) return;
        if (keyCode != -1) {
            view.dispose();
            if (App.getInstance().isStayLoggedIn()) {
                TillDawn.getTillDawn().setScreen(Screens.MainMenu.getScreen());
            } else {
                TillDawn.getTillDawn().setScreen(Screens.RegisterMenu.getScreen());
            }
        }
    }
}
