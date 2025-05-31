package com.TillDawn.Service;

import com.TillDawn.Model.App;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Model.User;
import com.badlogic.gdx.Gdx;

import java.util.Random;

public class AppService {
    private final App app;
    private final Random rand = new Random();
    public AppService(App app) {
        this.app = app;
    }

    public User getUserById(int id) {
        for (User registeredUser : app.getRegisteredUsers()) {
            if (registeredUser.getId() == id) {
                return registeredUser;
            }
        }
        return null;
    }

    public User getUserByName(String name) {
        for (User registeredUser : app.getRegisteredUsers()) {
            if (registeredUser.getName().equals(name)) {
                return registeredUser;
            }
        }
        return null;
    }

    public User getUserByEmail(String email) {
        for (User registeredUser : app.getRegisteredUsers()) {
            if (registeredUser.getEmail().equals(email)) {
                return registeredUser;
            }
        }
        return null;
    }

    public void addUser(User user) {
        app.getRegisteredUsers().add(user);
    }

    public void closeApp() {
        SaveLoadService.saveApp(app);
        GameAssetManager.getInstance().dispose();
        Gdx.app.exit();
    }

    public int getRandomNumber(int l, int r) {
        if (l == r) return l;
        return rand.nextInt(r - l + 1) + l;
    }

    public int getRandomNumber(int l, int r, Random rnd) {
        if (l == r) return l;
        return rnd.nextInt(r - l + 1) + l;
    }

    public boolean isNear(float vx, float vy, float ux, float uy, float range) {
        float d = (float) Math.sqrt((vx - ux) * (vx - ux) + (vy - uy) * (vy - uy));
        return d <= range;
    }

    public float getAngleRad(float vx, float vy, float ux, float uy) {
        float dx = vx - ux;
        float dy = vy - uy;
        return (float) Math.atan2(dy, dx);
    }
}
