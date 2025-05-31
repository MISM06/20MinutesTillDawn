package com.TillDawn.Service;

import com.TillDawn.Model.App;
import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.GameStuff.AbilityManager;
import com.TillDawn.Model.GameStuff.BulletManager;
import com.TillDawn.Model.GameStuff.ChunkManager;
import com.TillDawn.Model.GameStuff.EnemyStuff.Enemy;
import com.TillDawn.Model.GameStuff.EnemyStuff.EnemyManager;
import com.TillDawn.Model.GameStuff.Game;
import com.TillDawn.Model.User;
import com.TillDawn.TillDawn;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SaveLoadService {
    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
    private static final File file = new File("C:\\Users\\Acer\\IdeaProjects\\AP\\practice3\\core\\src\\main\\java\\com\\TillDawn\\Saves/app_save.json");
    private static final File gameFile = new File("C:\\Users\\Acer\\IdeaProjects\\AP\\practice3\\core\\src\\main\\java\\com\\TillDawn\\Saves\\game_save.json");

    public static void saveGame(Game game) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(gameFile, game);
            System.out.println("Saved game to " + gameFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean loadGame() {
        try {
            if (!gameFile.exists()) return false;
            Game loaded = mapper.readValue(gameFile, Game.class);
            int id = loaded.getPlayer().getUserId();
            if (id != App.getInstance().getCurrentUser().getId()) return false;
            Game.getInstance().setSeed(loaded.getSeed());
            Game.getInstance().setPlayer(loaded.getPlayer());
            Game.getInstance().getPlayer().setUser(App.getInstance().appService().getUserById(id));
//            Game.getInstance().getPlayer().prePareEntity();
            Game.getInstance().getPlayer().getGun().prePareEntity();

//
            Game.getInstance().setTimeLeft(loaded.getTimeLeft());
            Game.getInstance().setTimeDuration(loaded.getTimeDuration());


//            Game.getInstance().setChunkManager(loaded.getChunkManager());
            Game.getInstance().setChunkManager(new ChunkManager());
//            Game.getInstance().setBulletManager(loaded.getBulletManager());
            Game.getInstance().setEnemyManager(loaded.getEnemyManager());
            for (Enemy enemy : Game.getInstance().getEnemyManager().getNormalEnemy()) {
                if (enemy.getEntity() == null) enemy.prePareEntity();
                switch (enemy.getType()) {
                    case Tentacle:
                        enemy.setTentacle();
                        break;
                    case EyeBat:
                        enemy.setEyeBat();
                        break;
                }
            }
            Game.getInstance().setAbilityManager(loaded.getAbilityManager());

            Game.getInstance().setBulletManager(new BulletManager());
//            Game.getInstance().setEnemyManager(new EnemyManager());
//            Game.getInstance().setAbilityManager(new AbilityManager());

            System.out.println("Game loaded from " + gameFile.getAbsolutePath());
//            Game.getInstance().getGameViewController().setView(Game.getInstance().getGameView());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveApp(App app) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, app);
            System.out.println("App saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadApp() {
        try {
            if (!file.exists()) return;
            App loaded = mapper.readValue(file, App.class);
            App.getInstance().getRegisteredUsers().clear();
            App.getInstance().getRegisteredUsers().addAll(loaded.getRegisteredUsers());
            App.getInstance().setStayLoggedIn(loaded.isStayLoggedIn());
            if (loaded.isStayLoggedIn()) {
                App.getInstance().setCurrentUser(App.getInstance().appService().getUserById(loaded.getCurrentUser().getId()));
            } else {
                App.getInstance().setCurrentUser(null);
            }
            User.setMaxId(0);
            for (User registeredUser : App.getInstance().getRegisteredUsers()) {
                User.setMaxId(Math.max(User.getMaxId(), registeredUser.getId()));
            }
            System.out.println("App loaded from " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
