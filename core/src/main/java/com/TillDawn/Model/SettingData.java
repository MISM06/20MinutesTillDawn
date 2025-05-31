package com.TillDawn.Model;

import com.TillDawn.TillDawn;
import com.badlogic.gdx.Input;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class SettingData {
    private float musicVolume = 0.5f;
    private boolean musicEnabled = true;
    private String playingMusicPath = GameAssetManager.getInstance().defaultPreGameMusicPath;
    private float sfxVolume = 0.5f;
    private boolean sfxEnabled = true;
    private boolean autoReloadEnabled = false;
    private boolean blackWhiteEnabled = false;
    private boolean autoAimEnabled = false;

    private Map <Integer, Integer> playingKeys;


    public SettingData() {
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public String getPlayingMusicPath() {
        return playingMusicPath;
    }

    public float getSfxVolume() {
        return sfxVolume;
    }

    public boolean isSfxEnabled() {
        return sfxEnabled;
    }

    public boolean isAutoReloadEnabled() {
        return autoReloadEnabled;
    }

    public boolean isBlackWhiteEnabled() {
        return blackWhiteEnabled;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
    }

    public void setPlayingMusicPath(String playingMusicPath) {
        this.playingMusicPath = playingMusicPath;
    }

    public void setSfxVolume(float sfxVolume) {
        this.sfxVolume = sfxVolume;
    }

    public void setSfxEnabled(boolean sfxEnabled) {
        this.sfxEnabled = sfxEnabled;
    }

    public void setAutoReloadEnabled(boolean autoReloadEnabled) {
        this.autoReloadEnabled = autoReloadEnabled;
    }

    public void setBlackWhiteEnabled(boolean blackWhiteEnabled) {
        this.blackWhiteEnabled = blackWhiteEnabled;
    }

    public void loadSettings() {
        TillDawn.getTillDawn().setBlackWhiteMode(blackWhiteEnabled);
        AudioManager.playMusic(playingMusicPath);
        AudioManager.setMusicVolume(musicVolume);
        if (!musicEnabled) {
            AudioManager.stopMusic();
        }
    }

    public boolean isAutoAimEnabled() {
        return autoAimEnabled;
    }

    public void setAutoAimEnabled(boolean autoAimEnabled) {
        this.autoAimEnabled = autoAimEnabled;
    }

    public Map<Integer, Integer> getPlayingKeys() {
        if (playingKeys == null) {
            playingKeys = new HashMap<>();
        }
        if (playingKeys.isEmpty()) {
            playingKeys.put(Input.Keys.W, Input.Keys.W);
            playingKeys.put(Input.Keys.A, Input.Keys.A);
            playingKeys.put(Input.Keys.D, Input.Keys.D);
            playingKeys.put(Input.Keys.S, Input.Keys.S);
            playingKeys.put(Input.Keys.R, Input.Keys.R);
        }
        return playingKeys;
    }
}
