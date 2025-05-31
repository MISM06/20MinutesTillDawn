package com.TillDawn.Model.GameStuff;

import com.TillDawn.Model.GameAssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class BulletManager {
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();

    public BulletManager() {
    }
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }
    public void update(float delta) {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);
        }
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            if (Game.getInstance().getEnemyManager().handleBullet(bullet)) {
                bullets.remove(i);
            }
        }
        for (int i = enemyBullets.size() - 1; i >= 0; --i) {
            Bullet bullet = enemyBullets.get(i);
            bullet.update(delta);
        }
        for (int i = enemyBullets.size() - 1; i >= 0; --i) {
            Bullet bullet = enemyBullets.get(i);
            if (Game.getInstance().getPlayer().isInvincible()) continue;
            if (bullet.getCollision().checkCollision(Game.getInstance().getPlayer().getCollision())) {
                float bx = bullet.getSprite().getX() + bullet.getSprite().getWidth() / 2;
                float by = bullet.getSprite().getY() + bullet.getSprite().getHeight() / 2;

                float angleRad = bullet.getAngleRad();

                Game.getInstance().getPlayer().handleDamage((int) Math.floor(bullet.getDamage()), angleRad, 7, true);
                enemyBullets.remove(i);

                float AnWidth = 0.6f;
                float AnHeight = 0.6f;
                OneTimeAnimation damageFx = new OneTimeAnimation(bullet.getSprite().getX() + bullet.getSprite().getWidth() / 2 - AnWidth / 2, bullet.getSprite().getY() + bullet.getSprite().getHeight() / 2 - AnHeight / 2, AnWidth, AnHeight);
                damageFx.setAnimation("damage", new Animation<>(0.11f, GameAssetManager.getInstance().getEnemyDeathTextureRegion()));
                OneTimeAnimationManager.getInstance().addAnimation(damageFx);
//                Game.getInstance().getPlayer().applyKnockBack(angleRad, 7, 3);
            }
        }
    }
    public void draw(SpriteBatch batch) {
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
        for (Bullet bullet : enemyBullets) {
            bullet.draw(batch);
        }
    }
    public void addEnemyBullet (Bullet bullet) {
        enemyBullets.add(bullet);
    }
    public void removeEnemyBullet(Bullet bullet) {
        enemyBullets.remove(bullet);
    }
}
