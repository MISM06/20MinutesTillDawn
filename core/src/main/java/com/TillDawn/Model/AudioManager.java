package com.TillDawn.Model;

import com.TillDawn.View.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class AudioManager {

    private static Music music;
    private static float musicVolume = 0.5f;
    private static float sfxVolume = 0.5f;

    public static void playMusic(String path) {
        if (music != null) music.stop();
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();
    }

    public static void setMusicVolume(float volume) {
        volume = Math.max(0.0f, Math.min(1.0f, volume));
        musicVolume = volume;
        if (music != null) {
            music.setVolume(musicVolume);
        }
    }

    public static void stopMusic() {
        if (music != null) music.stop();
    }

    public static void pauseMusic() {
        if (music != null) music.pause();
    }

    public static void resumeMusic() {
        if (music != null) music.play();
    }

    public static void playSFX(String path) {
        Sound sfx = Gdx.audio.newSound(Gdx.files.internal(path));
        if (App.getInstance().getCurrentUser() != null && App.getInstance().getCurrentUser().getSettingData().isSfxEnabled()) {
            long id = sfx.play();
            sfx.setVolume(id, sfxVolume);
        }
    }
    public static void playSFX(FileHandle file) {
        Sound sfx = Gdx.audio.newSound(file);
        if (App.getInstance().getCurrentUser() != null && App.getInstance().getCurrentUser().getSettingData().isSfxEnabled()) {
            long id = sfx.play();
            sfx.setVolume(id, sfxVolume);
        }
    }

    public static void setSFXVolume(float volume) {
        volume = Math.max(0.0f, Math.min(1.0f, volume));
        sfxVolume = volume;
    }
}
