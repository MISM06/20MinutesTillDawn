package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.App;
import com.TillDawn.Model.GameStuff.Game;
import com.TillDawn.Service.SaveLoadService;
import com.TillDawn.TillDawn;
import com.TillDawn.View.MainMenu;

public class MainMenuController {
    MainMenu view;

    public void setView(MainMenu view) {
        this.view = view;
    }

    public void handle() {
        if (view.getSettingsButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.SettingMenu.getScreen());
        }
        if (view.getProfileButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.ProfileMenu.getScreen());
        }
        if (view.getScoreBoardButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.ScoreboardMenu.getScreen());
        }
        if (view.getPreGameButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.PreGameMenu.getScreen());
        }
        if (view.getTalentButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.TalentMenu.getScreen());
        }
        if (view.getLogoutButton().isChecked()) {
            view.dispose();
            App.getInstance().setCurrentUser(null);
            App.getInstance().setStayLoggedIn(false);
            TillDawn.getTillDawn().setScreen(Screens.LoginMenu.getScreen());
        }
        if (view.getExitButton().isChecked()) {
            view.dispose();
            App.getInstance().appService().closeApp();
        }

    }
    public void loadGame() {
        if (SaveLoadService.loadGame()) {

                view.dispose();
                TillDawn.getTillDawn().setScreen(Screens.GameView.getScreen());
        }
//        System.out.println(SaveLoadService.loadGame());
    }
}
