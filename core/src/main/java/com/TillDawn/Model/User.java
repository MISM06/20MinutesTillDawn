package com.TillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class User {
    private static int maxId = 0;
    private int id;
    private String name;
    private String password;
    private String email;
    private SecurityQuestion securityQuestion;

    private SettingData settingData = new SettingData();

    private int score = 0;
    private int kills = 0;
    private int liveTime = 0; // second base;


    private String avatarPath;
    private String outsideAvatarPath = "";
    public User(String name, String password, String email, SecurityQuestion securityQuestion) {
        id = ++maxId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.securityQuestion = securityQuestion;
    }

    public User() {
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Texture getAvatarTexture() {
        FileHandle file = Gdx.files.absolute(outsideAvatarPath);
        if (file.exists()) {
            return new Texture(file);
        }
        return new Texture(Gdx.files.internal(avatarPath));
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSettingData(SettingData settingData) {
        this.settingData = settingData;
    }

    public SettingData getSettingData() {
        return settingData;
    }

    public static void setMaxId(int maxId) {
        User.maxId = maxId;
    }

    public static int getMaxId() {
        return maxId;
    }

    public String getOutsideAvatarPath() {
        return outsideAvatarPath;
    }

    public void setOutsideAvatarPath(String outsideAvatarPath) {
        this.outsideAvatarPath = outsideAvatarPath;
    }

    public int getKills() {
        return kills;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }
}
