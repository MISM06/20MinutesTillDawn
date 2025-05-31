package com.TillDawn.Model.GameStuff;

import com.TillDawn.Controller.GameViewController;
import com.TillDawn.Model.GameStuff.EnemyStuff.EnemyManager;
import com.TillDawn.View.GameView;

import java.util.Random;

public class Game {

    private static Game instance;
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game(new Random().nextInt());
        }
        return instance;
    }

    private long seed;
    public Game(long seed) {
        this.seed = seed;
    }

    public Game() {
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    private Player player;
    private ChunkManager chunkManager;
    private float timeDuration;
    private float timeLeft;
    private GameViewController gameViewController;
    private GameView gameView;

    private BulletManager bulletManager;
    private EnemyManager enemyManager;
    private AbilityManager abilityManager;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setChunkManager(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public Player getPlayer() {
        return player;
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public void initMap() {
        setChunkManager(new ChunkManager());
        setBulletManager(new BulletManager());
        setEnemyManager(new EnemyManager());
        setAbilityManager(new AbilityManager());
    }

    public void setTimeDuration(float timeDurationMinute) {
        this.timeDuration = timeDurationMinute * 60f;
        setTimeLeft(timeDuration);
    }
    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }

    public BulletManager getBulletManager() {
        return bulletManager;
    }

    public void setBulletManager(BulletManager bulletManager) {
        this.bulletManager = bulletManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public float getTimeDuration() {
        return timeDuration;
    }

    public float getTimePassed() {
        return timeDuration - timeLeft;
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public void setAbilityManager(AbilityManager abilityManager) {
        this.abilityManager = abilityManager;
    }

    public GameViewController getGameViewController() {
        return gameViewController;
    }

    public void setGameViewController(GameViewController gameViewController) {
        this.gameViewController = gameViewController;
    }

    public void finishGame() {
        gameViewController.finishGame();
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
