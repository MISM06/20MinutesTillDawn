package com.TillDawn.Model.Enums;

import com.TillDawn.Controller.*;
import com.TillDawn.View.*;
import com.badlogic.gdx.Screen;

public enum Screens {
    LoginMenu {
        @Override
        public Screen getScreen() {
            return new LoginMenu(new LoginMenuController());
        }
    },
    StartMenu {
        @Override
        public Screen getScreen() {
            return new StartMenu(new StartMenuController());
        }
    },
    RegisterMenu {
        @Override
        public Screen getScreen() {
            return new RegisterMenu(new RegisterMenuController());
        }
    },
    ForgetPasswordMenu {
        @Override
        public Screen getScreen() {
            return new ForgetPasswordMenu(new ForgetPasswordController());
        }
    },
    MainMenu {
        @Override
        public Screen getScreen() {
            return new MainMenu(new MainMenuController());
        }
    },
    SettingMenu {
        public Screen getScreen() {
            return new SettingMenu(new SettingMenuController());
        }
    },
    ProfileMenu {
        public Screen getScreen() {
            return new ProfileMenu(new ProfileMenuController());
        }
    },
    PreGameMenu {
        public Screen getScreen() {
            return new PreGameMenu(new PreGameMenuController());
        }
    },
    TalentMenu {
        public Screen getScreen() {
            return new TalentMenu(new TalentMenuController());
        }
    },
    ScoreboardMenu {
        public Screen getScreen() {
            return new ScoreboardMenu(new ScoreboardMenuController());
        }
    },
    GameView {
        public Screen getScreen() {
            return new GameView(new GameViewController());
        }
    }
    ;



    public abstract Screen getScreen();
}
