package com.TillDawn.Model.GameStuff;

import com.TillDawn.Model.Enums.BulletType;
import com.TillDawn.Model.Enums.GunType;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Sprite sprite;
    private BulletType type;
    private float radius;
    private float initSpeed;
    private float startX;
    private float startY;
    private float angleDegree;
    private float angleRad;
    private float slowingFactor;
    private float stoppedTime;

    private float damage;

    private Collision collision;
    private GunType gunType;
    public Bullet(BulletType type,
                  float radius,
                  float initSpeed,
                  float startX,
                  float startY,
                  float angleDegree,
                  float slowingFactor,
                  float damage,
                  GunType gunType) {
        this.type = type;
        this.radius = radius;
        this.initSpeed = initSpeed;
        this.startX = startX;
        this.startY = startY;
        this.angleDegree = angleDegree;
        this.slowingFactor = slowingFactor;
        this.damage = damage;
        this.angleRad = angleDegree * MathUtils.degreesToRadians;
        this.gunType = gunType;
        sprite = new Sprite();
        sprite.setRegion(type.getBulletTextureRegion());
        sprite.setBounds(startX - type.getWidth() / 2, startY - type.getHeight() / 2, type.getWidth(), type.getHeight());
        collision = new Collision(startX, startY, type.getWidth() / 4, type.getHeight() / 4);
    }

    public Bullet() {
    }

    public float getDis2() {
        return (sprite.getX() - startX) * (sprite.getX() - startX) + (sprite.getY() - startY) * (sprite.getY() - startY);
    }

    public float getSpeed() {
        return getDis2() < (radius * radius) ? initSpeed * Math.max(0f, (1f - (slowingFactor * getDis2() / (radius * radius)))) : 0;
//        return initSpeed;
    }
    public void update(float delta) {
        float d = getSpeed() * delta;
        if (getSpeed() <= 1f) {
            stoppedTime += delta;
            if (stoppedTime >= 0.1f) {
                Game.getInstance().getBulletManager().removeBullet(this);
                Game.getInstance().getBulletManager().removeEnemyBullet(this);
            }
        } else {
            float dx = MathUtils.cos(angleRad) * d;
            float dy = MathUtils.sin(angleRad) * d;
            sprite.translate(dx, dy);
            collision.move(dx, dy);
        }
    }
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Collision getCollision() {
        return collision;
    }

    public float getDamage() {
        return damage;
    }

    public float getAngleRad() {
        return angleRad;
    }

    public GunType getGunType() {
        return gunType;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
