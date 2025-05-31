package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.TillDawn;
import com.TillDawn.View.TalentMenu;

public class TalentMenuController {
    TalentMenu view;

    public void setView(TalentMenu view) {
        this.view = view;
    }

    public void handle() {

    }

    public void back() {
        view.dispose();
        TillDawn.getTillDawn().setScreen(Screens.MainMenu.getScreen());
    }
}
