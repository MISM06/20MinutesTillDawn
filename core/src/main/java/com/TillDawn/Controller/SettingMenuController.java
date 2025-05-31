package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Assets.Musics;
import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.App;
import com.TillDawn.Model.AudioManager;
import com.TillDawn.TillDawn;
import com.TillDawn.View.SettingMenu;

public class SettingMenuController {
    SettingMenu view;

    public void setView(SettingMenu view) {
        this.view = view;
    }

    public void handle() {
        if (view.getBackButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.MainMenu.getScreen());
            return;
        }
    }

    public void changeMusicVolume(float volume) {
        App.getInstance().getCurrentUser().getSettingData().setMusicVolume(volume / 100f);
        AudioManager.setMusicVolume(App.getInstance().getCurrentUser().getSettingData().getMusicVolume());
    }

    public void musicEnable(boolean state) {
        App.getInstance().getCurrentUser().getSettingData().setMusicEnabled(state);
        if (state) AudioManager.resumeMusic();
        else AudioManager.pauseMusic();
    }

    public void sfxEnable(boolean state) {
        App.getInstance().getCurrentUser().getSettingData().setSfxEnabled(state);
    }

    public void autoReloadEnable(boolean state) {
        App.getInstance().getCurrentUser().getSettingData().setAutoReloadEnabled(state);
    }

    public void blackWhiteEnable(boolean state) {
        App.getInstance().getCurrentUser().getSettingData().setBlackWhiteEnabled(state);
        TillDawn.getTillDawn().setBlackWhiteMode(state);
    }

    public void changeMusic(Musics music) {
        App.getInstance().getCurrentUser().getSettingData().setPlayingMusicPath(music.getPath());
        AudioManager.playMusic(App.getInstance().getCurrentUser().getSettingData().getPlayingMusicPath());
    }
}
