package com.TillDawn.Model.Enums;

import com.TillDawn.Controller.*;
import com.TillDawn.Model.App;
import com.TillDawn.View.*;
import com.badlogic.gdx.Screen;

public enum Screens {
    LoginMenu {
        @Override
        public Screen getScreen() {
            super.getScreen();
            return new LoginMenu(new LoginMenuController());
        }
    },
    StartMenu {
        @Override
        public Screen getScreen() {
            super.getScreen();
            return new StartMenu(new StartMenuController());
        }
    },
    RegisterMenu {
        @Override
        public Screen getScreen() {
            super.getScreen();
            return new RegisterMenu(new RegisterMenuController());
        }
    },
    ForgetPasswordMenu {
        @Override
        public Screen getScreen() {
            super.getScreen();
            return new ForgetPasswordMenu(new ForgetPasswordController());
        }
    },
    MainMenu {
        @Override
        public Screen getScreen() {
            super.getScreen();
            return new MainMenu(new MainMenuController());
        }
    },
    SettingMenu {
        public Screen getScreen() {
            super.getScreen();
            return new SettingMenu(new SettingMenuController());
        }
    },
    ProfileMenu {
        public Screen getScreen() {
            super.getScreen();
            return new ProfileMenu(new ProfileMenuController());
        }
    },
    PreGameMenu {
        public Screen getScreen() {
            super.getScreen();
            return new PreGameMenu(new PreGameMenuController());
        }
    },
    TalentMenu {
        public Screen getScreen() {
            super.getScreen();
            return new TalentMenu(new TalentMenuController());
        }
    },
    ScoreboardMenu {
        public Screen getScreen() {
            super.getScreen();
            return new ScoreboardMenu(new ScoreboardMenuController());
        }
    },
    GameView {
        public Screen getScreen() {
            super.getScreen();
            return new GameView(new GameViewController());
        }
    }
    ;



    public Screen getScreen() {
        App.getInstance().setCurrentMenu(this);
        return null;
    }
}
