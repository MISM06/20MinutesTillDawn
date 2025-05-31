package com.TillDawn.Model.GameStuff.EnemyStuff;

import com.TillDawn.Model.App;
import com.TillDawn.Model.AudioManager;
import com.TillDawn.Model.Enums.EnemyType;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Model.GameStuff.*;
import com.TillDawn.Model.Utils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class EnemyManager {
    @JsonIgnore
    private ArrayList<Enemy> trees = new ArrayList<>();
//    @JsonIgnore
    private ArrayList<Enemy> normalEnemy = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Boss> bosses = new ArrayList<>();
    @JsonIgnore
    private ArrayList<AnimatedEntity> xpDrops = new ArrayList<>();

    private boolean isShubNiggurathSpawnedTillNow = false;

    public EnemyManager() {
        trees = new ArrayList<>();
        normalEnemy = new ArrayList<>();
        bosses = new ArrayList<>();
        xpDrops = new ArrayList<>();
    }

    public void update(float delta) {
        spawn();
        updateTree(delta);
        tentacleLastSpawn += delta;
        eyeBatLastSpawn += delta;
        moveEnemies(delta, Game.getInstance().getPlayer().getX() + Game.getInstance().getPlayer().getEntity().getWidth() / 2,
            Game.getInstance().getPlayer().getY() + Game.getInstance().getPlayer().getEntity().getHeight() / 2);
        for (Enemy enemy : normalEnemy) {
            enemy.getEntity().update(delta);
        }
        updateShooters(delta);
        for (int i = bosses.size() - 1; i >= 0; i--) {
            bosses.get(i).update(delta);
        }
        checkPlayerInteract();
        Player player = Game.getInstance().getPlayer();
        for (AnimatedEntity xpDrop : xpDrops) {
            xpDrop.update(delta);
        }
        for (int i = xpDrops.size() - 1; i >= 0; i--) {
            AnimatedEntity xpDrop = xpDrops.get(i);
            if (player.getCollision().checkCollision(xpDrop.getCollision())) {
                xpDrops.remove(i);
                for (int j = 0; j < 3; j++) {
                    player.addXp();
                    AudioManager.setSFXVolume(0.2f);
                    AudioManager.playSFX(GameAssetManager.getInstance().xpPick);
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {
        drawTree(batch);
        for (Enemy enemy : normalEnemy) {
            enemy.getEntity().draw(batch);
        }
        for (Boss boss : bosses) {
            boss.getEntity().draw(batch);
        }
        for (AnimatedEntity xpDrop : xpDrops) {
            xpDrop.draw(batch);
        }
    }

    public void drawBorders(SpriteBatch batch) {
        for (Boss boss : bosses) {
            if (boss instanceof ShubNiggurath) {
                ShubNiggurath shub = (ShubNiggurath) boss;
                shub.drawBorder(batch);
            }
        }
    }

    public boolean isPointActive(float x, float y, float range) {
        float dx = Game.getInstance().getPlayer().getX() - x;
        float dy = Game.getInstance().getPlayer().getY() - y;
        return range >= Math.abs(dx) && range >= Math.abs(dy);
    }

    public void updateShooters(float delta) {
        for (Enemy enemy : normalEnemy) {
            if (!enemy.isShooter()) continue;
            enemy.setLastShotDiff(enemy.getLastShotDiff() + delta);
            if (enemy.getLastShotDiff() >= enemy.getShootingDelay()) {
                Bullet bull = getBullet(enemy);
                Game.getInstance().getBulletManager().addEnemyBullet(bull);
                enemy.setLastShotDiff(0);
            }
        }
    }

    private static Bullet getBullet(Enemy enemy) {
        float tx =  Game.getInstance().getPlayer().getX() + Game.getInstance().getPlayer().getEntity().getWidth() / 2;
        float ty = Game.getInstance().getPlayer().getY() + Game.getInstance().getPlayer().getEntity().getHeight() / 2;
        float angleRad = MathUtils.atan2(ty - enemy.getCenterY(), tx - enemy.getCenterX());
        Bullet bull = new Bullet(enemy.getType().getBulletType(), 40, 1.5f, enemy.getCenterX(),
            enemy.getCenterY(), angleRad * MathUtils.radiansToDegrees, 0, enemy.getDamage(),
            null);
        return bull;
    }

    public void updateTree(float delta) {
        for (Enemy tree : trees) {
            if (!isPointActive(tree.getCenterX(), tree.getCenterY(), Utils.activeRange)) continue;
            if (isPointActive(tree.getCenterX(), tree.getCenterY(), Utils.treeAnimActiveRange)) {
                tree.getEntity().setAnimation("active");
            } else if (tree.getEntity().getCurrentAnimation().equals("active")) {
                tree.getEntity().setAnimation("still");
            }
            tree.getEntity().update(delta);
        }
    }

    public void drawTree(SpriteBatch batch) {
        for (Enemy tree : trees) {
            if (!isPointActive(tree.getCenterX(), tree.getCenterY(), Utils.activeRange)) continue;
            tree.getEntity().draw(batch);
        }
    }

    public void addTree(Enemy tree) {
        trees.add(tree);
    }

    private float tentacleLastSpawn = 0;
    private float eyeBatLastSpawn = 0;

    public void spawn() {
        spawnTentacle();
        spawnEyeBat();
        if (Game.getInstance().getTimePassed() >= Game.getInstance().getTimeDuration() / 2f && !isShubNiggurathSpawnedTillNow) {
            spawnShubNiggurath();
        }
    }

    public void spawnShubNiggurath() {
        isShubNiggurathSpawnedTillNow = true;
        ShubNiggurath shubNiggurath = (ShubNiggurath) spawnAEnemy(EnemyType.ShubNiggurath);
        bosses.add(shubNiggurath);
        shubNiggurath.startBossFight();
    }

    public Enemy spawnAEnemy(EnemyType type) {
        float angle, x, y;
        angle = App.getInstance().appService().getRandomNumber(0, 359);
        x = MathUtils.cosDeg(angle) * Utils.enemySpawnRange;
        y = MathUtils.sinDeg(angle) * Utils.enemySpawnRange;
        x += Game.getInstance().getPlayer().getX();
        y += Game.getInstance().getPlayer().getY();
        if (type == EnemyType.ShubNiggurath) {
            return new ShubNiggurath(type, x, y);
        }
        return new Enemy(type, x, y);
    }

    public void spawnEyeBat() {
        if (Game.getInstance().getTimePassed() < Game.getInstance().getTimeDuration() / 4 ) {
            return;
        }
        if (eyeBatLastSpawn < 30) return;
        int c = (int) Math.floor((4 * Game.getInstance().getTimePassed() - Game.getInstance().getTimeDuration() + 30) / 30);
//        c = 2;
        for (int i = 0; i < c; i++) {
            Enemy enemy = spawnAEnemy(EnemyType.EyeBat);
            enemy.setEyeBat();
            normalEnemy.add(enemy);
        }
        eyeBatLastSpawn = 0;
    }

    public void spawnTentacle() {
        if (tentacleLastSpawn < 3) return;
        tentacleLastSpawn = 0;
        float timePassed = Game.getInstance().getTimeDuration() - Game.getInstance().getTimeLeft();
        int c = (int) Math.ceil(timePassed / 30f);
        for (int i = 0; i < c; i++) {
            Enemy enemy = spawnAEnemy(EnemyType.Tentacle);
            enemy.setTentacle();
            normalEnemy.add(enemy);
        }
    }

    public void moveEnemy(float delta, Enemy enemy, float px, float py) {
        float d = enemy.getSpeed() * delta;
        float dy = py - enemy.getCenterY();
        float dx = px - enemy.getCenterX();
        if (dx > 0) enemy.getEntity().faceWayRight();
        else enemy.getEntity().faceWayLeft();
        float angle = MathUtils.atan2(dy, dx);
        dx = enemy.getKnockbackVector().x * delta;
        dy = enemy.getKnockbackVector().y * delta;
        enemy.getKnockbackVector().lerp(Vector2.Zero, enemy.getKnockBackDamping() * delta);
        enemy.move(MathUtils.cos(angle) * d + dx, MathUtils.sin(angle) * d + dy);
    }

    public void moveEnemies(float delta, float px, float py) {
        for (Enemy enemy : normalEnemy) {
            moveEnemy(delta, enemy, px, py);
        }
    }

    public void death(Enemy enemy) {
        float AnWidth = 0.8f;
        float AnHeight = 0.8f;
        OneTimeAnimation deathFx = new OneTimeAnimation(enemy.getCenterX() - AnWidth / 2, enemy.getCenterY() - AnHeight / 2, AnWidth, AnHeight);
        deathFx.setAnimation("death", new Animation<>(0.13f, GameAssetManager.getInstance().getEnemyDeathTextureRegion()));
        OneTimeAnimationManager.getInstance().addAnimation(deathFx);
        Game.getInstance().getPlayer().setKillCount(Game.getInstance().getPlayer().getKillCount() + 1);


        AnimatedEntity xpDrop = new AnimatedEntity(enemy.getCenterX() - Utils.xpWidth / 2, enemy.getCenterY() - Utils.xpHeight / 2, Utils.xpWidth, Utils.xpHeight);
        xpDrop.addAnimation("active", new Animation<>(0.2f, GameAssetManager.getInstance().getXpRegion()));
        Collision xpCollision = new Collision(enemy.getCenterX(), enemy.getCenterY(), Utils.xpWidth * 0.7f, Utils.xpHeight * 0.7f);
        xpDrop.setCollision(xpCollision);
        xpDrops.add(xpDrop);
    }

    public boolean handleBullet(Bullet bullet) {
        for (int i = normalEnemy.size() - 1; i >= 0; i--) {
            Enemy enemy = normalEnemy.get(i);
            if (!isPointActive(enemy.getCenterX(), enemy.getCenterY(), Utils.activeRange)) continue;
            if (!enemy.getCollision().checkCollision(bullet.getCollision())) continue;
            if (enemy.handleDamage(bullet.getDamage())) {
                normalEnemy.remove(enemy);
                death(enemy);
            } else {
                float AnWidth = 0.5f;
                float AnHeight = 0.5f;
                OneTimeAnimation deathFx = new OneTimeAnimation(bullet.getSprite().getX() + bullet.getSprite().getWidth() / 2 - AnWidth / 2, bullet.getSprite().getY() + bullet.getSprite().getHeight() / 2 - AnHeight / 2, AnWidth, AnHeight);
                deathFx.setAnimation("damage", new Animation<>(0.1f, GameAssetManager.getInstance().getEnemyDeathTextureRegion()));
                OneTimeAnimationManager.getInstance().addAnimation(deathFx);
                enemy.applyKnockBack(bullet.getAngleRad(), bullet.getGunType().getKnockBackForce(), 3);
            }
            return true;
        }
        for (int i = bosses.size() - 1; i >= 0; i--) {
            Boss boss = bosses.get(i);
            if (!boss.getCollision().checkCollision(bullet.getCollision())) continue;
            boss.takeDamage(bullet);
            return true;
        }
        for (int i = trees.size() - 1; i >= 0; i--) {
            Enemy tree = trees.get(i);
            if (!tree.getCollision().checkCollision(bullet.getCollision())) continue;
            float AnWidth = 0.5f;
            float AnHeight = 0.5f;
            OneTimeAnimation deathFx = new OneTimeAnimation(bullet.getSprite().getX() + bullet.getSprite().getWidth() / 2 - AnWidth / 2, bullet.getSprite().getY() + bullet.getSprite().getHeight() / 2 - AnHeight / 2, AnWidth, AnHeight);
            deathFx.setAnimation("damage", new Animation<>(0.1f, GameAssetManager.getInstance().getEnemyDeathTextureRegion()));
            OneTimeAnimationManager.getInstance().addAnimation(deathFx);
//            System.out.println("tree=" + tree);
//            System.out.println("(" + tree.getCenterX() + ", " + tree.getCenterY() + " | " + tree.getEntity().getWidth() + ", " + tree.getEntity().getHeight() + ")");
//            System.out.println("collision" + tree.getCollision().getCenterX() + "," + tree.getCollision().getCenterY() + " | " + tree.getCollision().getWidth() + " | " + tree.getCollision().getHeight());
            return true;
        }
        return false;
    }

    public ArrayList<Boss> getBosses() {
        return bosses;
    }

    public void checkPlayerInteract() {
        Player player = Game.getInstance().getPlayer();
        float px = player.getCollision().getCenterX();
        float py = player.getCollision().getCenterY();
        for (Enemy tree : trees) {
            if (tree.getCollision().checkCollision(player.getCollision())) {
                player.handleDamage(!player.isInvincible() ? tree.getDamage() : 0, App.getInstance().appService().getAngleRad(px, py, tree.getCenterX(), tree.getCenterY()),
                    10, !player.isInvincible());
            }
        }
        for (Enemy enemy : normalEnemy) {
            if (enemy.getCollision().checkCollision(player.getCollision()) && !player.isInvincible()) {
                player.handleDamage(enemy.getDamage(), App.getInstance().appService().getAngleRad(px, py, enemy.getCenterX(), enemy.getCenterY()),
                    10, true);
            }
        }
        for (Boss boss : bosses) {
            if (boss.getCollision().checkCollision(player.getCollision()) && !player.isInvincible()) {
                player.handleDamage(boss.getDamage(),
                    App.getInstance().appService().getAngleRad(boss.getCenterX(),
                        boss.getCenterY(), px, py) + 90,
                    10, true);
            }
            if (boss instanceof ShubNiggurath) {
                ShubNiggurath shubNiggurath = (ShubNiggurath) boss;
                int w = shubNiggurath.borderCollisionCheck(player.getCollision());
                if (w != -1) {
                    float angleRad = getAngleRadByWay(w);
                    int damage = 1;
                    if (player.isInvincible()) damage = 0;
                    player.handleDamage(damage, angleRad, 20, true);
                }
                shubNiggurath.keepPlayerInsideBorder(player);
            }
        }

    }

    private static float getAngleRadByWay(int w) {
        float angleDeg = 0;
        switch (w) {
            case 0:
                angleDeg = 0;
                break;
            case 1:
                angleDeg = 270;
                break;
            case 2:
                angleDeg = 180;
                break;
            case 3:
                angleDeg = 90;
        }
        float angleRad = angleDeg * MathUtils.degreesToRadians;
        return angleRad;
    }

    public Vector2 getNearest(float px, float py) {
        float x = 0;
        float y = 0;
        float minDistance = Float.MAX_VALUE;
        boolean found = false;
        for (Boss enemy : bosses) {
            float dis2 = (enemy.getCenterX() - px) * (enemy.getCenterX() - px) + (enemy.getCenterY() - py) * (enemy.getCenterY() - py);
            float dis = (float) Math.sqrt(dis2);
            if (dis < minDistance) {
                minDistance = dis;
                x = enemy.getCenterX();
                y = enemy.getCenterY();
                found = true;
            }
        }
        for (Enemy enemy: normalEnemy) {
            float dis2 = (enemy.getCenterX() - px) * (enemy.getCenterX() - px) + (enemy.getCenterY() - py) * (enemy.getCenterY() - py);
            float dis = (float) Math.sqrt(dis2);
            if (dis < minDistance) {
                minDistance = dis;
                x = enemy.getCenterX();
                y = enemy.getCenterY();
                found = true;
            }
        }
        if (!found) {
            for (Enemy enemy : trees) {
                float dis2 = (enemy.getCenterX() - px) * (enemy.getCenterX() - px) + (enemy.getCenterY() - py) * (enemy.getCenterY() - py);
                float dis = (float) Math.sqrt(dis2);
                if (dis < minDistance) {
                    minDistance = dis;
                    x = enemy.getCenterX();
                    y = enemy.getCenterY();
                    found = true;
                }
            }
        }
        if (found) return new Vector2(x, y);
        else return null;
    }

    public ArrayList<Enemy> getTrees() {
        return trees;
    }

    public ArrayList<Enemy> getNormalEnemy() {
        return normalEnemy;
    }

    public ArrayList<AnimatedEntity> getXpDrops() {
        return xpDrops;
    }

    public boolean isShubNiggurathSpawnedTillNow() {
        return isShubNiggurathSpawnedTillNow;
    }

    public float getTentacleLastSpawn() {
        return tentacleLastSpawn;
    }

    public float getEyeBatLastSpawn() {
        return eyeBatLastSpawn;
    }

    public void setTrees(ArrayList<Enemy> trees) {
        this.trees = trees;
    }

    public void setNormalEnemy(ArrayList<Enemy> normalEnemy) {
        this.normalEnemy = normalEnemy;
    }

    public void setBosses(ArrayList<Boss> bosses) {
        this.bosses = bosses;
    }

    public void setXpDrops(ArrayList<AnimatedEntity> xpDrops) {
        this.xpDrops = xpDrops;
    }

    public void setShubNiggurathSpawnedTillNow(boolean shubNiggurathSpawnedTillNow) {
        isShubNiggurathSpawnedTillNow = shubNiggurathSpawnedTillNow;
    }

    public void setTentacleLastSpawn(float tentacleLastSpawn) {
        this.tentacleLastSpawn = tentacleLastSpawn;
    }

    public void setEyeBatLastSpawn(float eyeBatLastSpawn) {
        this.eyeBatLastSpawn = eyeBatLastSpawn;
    }
}
