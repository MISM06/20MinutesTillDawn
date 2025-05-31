package com.TillDawn.Controller;

import com.TillDawn.Model.*;
import com.TillDawn.Model.Enums.AbilityTypes;
import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.GameStuff.EnemyStuff.EnemyManager;
import com.TillDawn.Model.GameStuff.Game;
import com.TillDawn.Model.GameStuff.OneTimeAnimationManager;
import com.TillDawn.Model.GameStuff.Player;
import com.TillDawn.Service.SaveLoadService;
import com.TillDawn.TillDawn;
import com.TillDawn.View.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class GameViewController implements InputProcessor {
    @JsonIgnore
    private GameView view;

    private Game game;
    private Player player;
    private boolean upRun = false;
    private boolean downRun = false;
    private boolean leftRun = false;
    private boolean rightRun = false;
    private AbilityTypes[] abilityTypes;
    private boolean isGaleOn = true;

    private boolean paused = false;
    private ArrayList<AbilityTypes> hasSeenAbilityTypes = new ArrayList<>();

    public GameViewController() {
        game = Game.getInstance();
        player = game.getPlayer();
        game.setGameViewController(this);
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void update(float delta) {

        if (!isPaused()) logic(delta);
        draw(!isPaused() ? delta : 0);
        updateStage(delta);
        if (Game.getInstance().getTimeLeft() <= 0) {
            finishGame();
        }
    }

    public void updateStage(float delta) {
        while (view.getHpBar().size() < player.getMaxHp()) {
            view.addMaxHp();
        }
        for (int i = 0; i < player.getHp(); i++) {
            Image image = view.getHpBar().get(i);
            image.setDrawable(new TextureRegionDrawable(view.getFullHeartAnimation().getKeyFrame(Game.getInstance().getTimePassed(), true)));
        }
        for (int i = Math.max(player.getHp(), 0); i < player.getMaxHp(); i++) {
            Image image = view.getHpBar().get(i);
            image.setDrawable(new TextureRegionDrawable(view.getBrokenHeartAnimation().getKeyFrame(Game.getInstance().getTimePassed(), true)));
        }

        view.getKillLabel().setText("kills: " + player.getKillCount());

        int minute = (int) Math.floor(Game.getInstance().getTimeLeft() / 60);
        minute = Math.max(0, minute);
        int second = ((int) Math.floor(Game.getInstance().getTimeLeft())) % 60;
        second = Math.max(0, second);

        view.getTimeLabel().setText(String.format("%02d:%02d", minute, second));

        view.getAmmoLabel().setText(String.format("%02d/%02d", (int) player.getGun().getAmmo(), (int) player.getGun().getMaxAmmo()));

        view.getLevelBar().setValue((float) ((player.getXp() - player.getCurrentLevelMinXp()) * 100) / (Utils.xpCoeff * player.getLevel()));
        view.getLevelLabel().setText("Level: " + player.getLevel());
    }

    public void playerInput(float delta) {
        String anim ="idle";
        if (leftRun) {
            player.move(-delta * player.getSpeed(), 0);
            player.getEntity().setAnimation("run");
            anim = "run";
            player.getEntity().faceWayLeft();
        }
        if (rightRun) {
            player.move(delta * player.getSpeed(), 0);
            player.getEntity().setAnimation("run");
            anim = "run";
            player.getEntity().faceWayRight();
        }
        if (upRun) {
            player.move(0, delta * player.getSpeed());
            player.getEntity().setAnimation("run");
            anim = "run";
        }
        if (downRun) {
            player.move(0, -delta * player.getSpeed());
            player.getEntity().setAnimation("run");
            anim = "run";
        }
        player.getEntity().setAnimation(anim);
    }

    public void logic(float delta) {
        playerInput(delta);
        game.setTimeLeft(game.getTimeLeft() - delta);
        player.update(delta);
        game.getAbilityManager().update();
        boolean[] hasSeen = game.getAbilityManager().getHasSeen();
        for (int i = 0; i < hasSeen.length; i++) {
            boolean b = hasSeen[i];
            AbilityTypes abilityType = AbilityTypes.values()[i];
            if (hasSeenAbilityTypes.contains(abilityType) == b) continue;
            if (b) hasSeenAbilityTypes.add(abilityType);
            else hasSeenAbilityTypes.remove(abilityType);
        }
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        view.getCamera().unproject(mouse);

        if (player.isAutoAim()) {
            Vector2 coord = game.getEnemyManager().getNearest(player.getCollision().getCenterX(), player.getCollision().getCenterY());
            if (coord == null) {
                player.getGun().update(delta, player, mouse.x, mouse.y);
            } else {
                if (Math.abs(player.getCollision().getCenterX() - coord.x) >= Utils.screenWidth / 2 + 1) {
                    player.getGun().update(delta, player, mouse.x, mouse.y);
                } else if (Math.abs(player.getCollision().getCenterY() - coord.y) >= Utils.screenHeight / 2 + 1) {
                    player.getGun().update(delta, player, mouse.x, mouse.y);
                } else {
                    player.getGun().update(delta, player, coord.x, coord.y);
                    Vector3 mouseForce = new Vector3(coord.x, coord.y, 0);
                    mouseForce = view.getCamera().project(mouseForce);
                    Gdx.input.setCursorPosition((int) mouseForce.x, Gdx.graphics.getHeight() - (int) mouseForce.y);
                }
            }
        } else {
            player.getGun().update(delta, player, mouse.x, mouse.y);
        }

        game.getChunkManager().update(player.getX(), player.getY());
        game.getEnemyManager().update(delta);
        game.getBulletManager().update(delta);
        OneTimeAnimationManager.getInstance().update(delta);
    }
    public void draw(float delta) {
        game.getChunkManager().draw(view.getBatch());
        game.getEnemyManager().drawBorders(view.getBatch());
        player.draw(view.getBatch());
        game.getEnemyManager().draw(view.getBatch());
        player.getGun().draw(view.getBatch());
        game.getBulletManager().draw(view.getBatch());
        OneTimeAnimationManager.getInstance().draw(view.getBatch());

        if (isGaleOn) {
            view.getLightMaskSprite().setPosition(view.getCamera().position.x - view.getLightMaskSprite().getWidth() / 2,
                view.getCamera().position.y - view.getLightMaskSprite().getHeight() / 2);
            view.getLightMaskSprite().draw(view.getBatch());
        }
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    private ArrayList<AbilityTypes> abilitySet = new ArrayList<>();
    public void chooseAbility() {
        if (abilityTypes == null) abilityTypes = new AbilityTypes[view.getAbilityCnt()];
        abilitySet.clear();
        abilitySet.addAll(Arrays.asList(AbilityTypes.values()));
        for (int i = 0; i < view.getAbilityCnt(); i++) {
            int j = App.getInstance().appService().getRandomNumber(0, abilitySet.size() - 1);
            abilityTypes[i] = abilitySet.get(j);
            abilitySet.remove(j);
        }
        view.showAbilityMenu();
    }

    public void addAbility(int index) {
        Game.getInstance().getAbilityManager().add(abilityTypes[index]);
    }

    public AbilityTypes[] getAbilityTypes() {
        return abilityTypes;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.W)) {
            upRun = true;
        }
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.A)) {
            leftRun = true;
        }
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.S)) {
            downRun = true;
        }
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.D)) {
            rightRun = true;
        }
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.R)) {
            player.getGun().reload();
            return true;
        }
        if (keycode == Input.Keys.ESCAPE) {
            view.showPauseMenu();
        }
        if (keycode == Input.Keys.B && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            game.getEnemyManager().spawnShubNiggurath();
            return true;
        }
        if (keycode == Input.Keys.L && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            cheatAddLevel();
            return true;
        }
        if (keycode == Input.Keys.H && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            cheatAddHp();
            return true;
        }
        if (keycode == Input.Keys.T && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            cheatSkipTime();
            return true;
        }
        if (keycode == Input.Keys.O && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            cheatAddSpeed();
            return true;
        }
        if (keycode == Input.Keys.G && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            cheatChangeGaleState();
            return true;
        }
        if (keycode == Input.Keys.SPACE) {
            player.setAutoAim(!player.isAutoAim());
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.W)) {
            upRun = false;
        }
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.A)) {
            leftRun = false;
        }
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.S)) {
            downRun = false;
        }
        if (keycode == player.getUser().getSettingData().getPlayingKeys().get(Input.Keys.D)) {
            rightRun = false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(Game.getInstance().getTimePassed() + "butt pressed" + button);
        if (button == Input.Buttons.LEFT) {
            player.getGun().setInShooting(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println(Game.getInstance().getTimePassed() + "butt released" + button);
        if (button == Input.Buttons.LEFT) {
            player.getGun().setInShooting(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    private boolean isGameEnded = false;
    public void finishGame() {

        if (Game.getInstance().getTimeLeft() <= 0) {
            view.getEndingStateTitle().setText("You Survived");
            view.getEndingStateTitle().setColor(255, 223, 0, 1);
            if (!isGameEnded) {
                AudioManager.pauseMusic();
                AudioManager.setSFXVolume(0.4f);
                AudioManager.playSFX(GameAssetManager.getInstance().winSfx);
            }
        } else {
            view.getEndingStateTitle().setText("You Died");
            view.getEndingStateTitle().setColor(Color.RED);
            if (!isGameEnded) {
                AudioManager.pauseMusic();
                AudioManager.setSFXVolume(0.4f);
                AudioManager.playSFX(GameAssetManager.getInstance().looseSfx);
            }
        }
        isGameEnded = true;

        int minute = (int) Math.floor(Game.getInstance().getTimePassed() / 60);
        int second = ((int) Math.floor(Game.getInstance().getTimePassed())) % 60;


        view.getKillsCountLabel().setText("kills:   " + player.getKillCount());
        view.getSurviveTimeLabel().setText("survive:   " + "(" + String.format("%02d:%02d", minute, second) + ")");
        int score = (int) Math.floor(Game.getInstance().getTimePassed()) * player.getKillCount();
        view.getScoreLabel().setText("score:   " + score);
        view.getUserNameLabel().setText("username:   " + player.getUser().getName());

        view.showEndingMenu();
    }

    public void quitGame() {
        System.out.println("quiting");
        view.dispose();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        view.getStage().dispose();
        System.out.println("deleted");
        TillDawn.setBatch(new SpriteBatch());
        TillDawn.getTillDawn().setBlackWhiteMode(player.getUser().getSettingData().isBlackWhiteEnabled());
        TillDawn.getTillDawn().setScreen(Screens.MainMenu.getScreen());
        User user = player.getUser();
        user.setKills(user.getKills() + player.getKillCount());
        user.setScore(user.getScore() + (int) Math.floor(Game.getInstance().getTimePassed()) * player.getKillCount());
        user.setLiveTime(user.getLiveTime() + (int) Math.floor(Game.getInstance().getTimePassed()));
    }

    public void blackWhiteEnable(boolean state) {
        App.getInstance().getCurrentUser().getSettingData().setBlackWhiteEnabled(state);
        TillDawn.getTillDawn().setBlackWhiteMode(state);
    }

    public void saveAndExit(){
        SaveLoadService.saveGame(Game.getInstance());
        quitGame();
    }

    public ArrayList<AbilityTypes> getHasSeenAbilityTypes() {
        return hasSeenAbilityTypes;
    }

    //Cheat Codes;

    public void cheatAddLevel() {
        int curLevel = player.getLevel();
        while (curLevel == player.getLevel()) {
            player.addXp();
        }
    }

    public void cheatAddHp() {
        if (player.getHp() < player.getMaxHp()) {
            player.setHp(player.getHp() + 1);
        }
    }

    public void cheatSkipTime() {
        Game.getInstance().setTimeLeft(Math.max(game.getTimeLeft() - 60, 1));
    }

    public void cheatAddSpeed() {
        player.setSpeed(player.getSpeed() * 1.1f);
    }

    public void cheatChangeGaleState() {
        isGaleOn = !isGaleOn;
    }

}
