package com.TillDawn.Model.GameStuff.EnemyStuff;

import com.TillDawn.Model.Enums.BulletType;
import com.TillDawn.Model.Enums.EnemyType;
import com.TillDawn.Model.GameStuff.AnimatedEntity;
import com.TillDawn.Model.GameStuff.Collision;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Enemy {
    @JsonIgnore
    private AnimatedEntity entity;
    private Collision collision;
    private EnemyType type;
    private float hp;
    private int damage;
    private boolean isShooter;
    private BulletType bulletType;
    private float shootingDelay;
    private float centerX;
    private float centerY;
    private float speed;
    private float lastShotDiff;

    private Vector2 knockbackVector = new Vector2(0, 0);
    private float knockBackDamping = 10f;

    public Enemy() {
    }

    public Enemy(EnemyType type, float centerX, float centerY) {
        this.type = type;
        this.centerX = centerX;
        this.centerY = centerY;
        hp = type.getHp();
        damage = type.getDamage();
        isShooter = type.isShooter();
        bulletType = type.getBulletType();
        shootingDelay = type.getShootingDelay();
        speed = type.getSpeed();
        entity = new AnimatedEntity(centerX - type.getWidth() / 2, centerY - type.getHeight() / 2, type.getWidth(), type.getHeight());
        collision = new Collision(centerX, centerY, type.getCollisionWidth(), type.getCollisionHeight());
    }

    public void prePareEntity() {
        entity = new AnimatedEntity(centerX - type.getWidth() / 2, centerY - type.getHeight() / 2, type.getWidth(), type.getHeight());
        collision = new Collision(centerX, centerY, type.getCollisionWidth(), type.getCollisionHeight());
    }

    public void setTree() {
        Array<TextureRegion> still = new Array<>(type.getTextureRegions("still"));
        Array<TextureRegion> active = new Array<>();
        active.addAll(still);
        active.addAll(still);
        active.addAll(type.getTextureRegions("active"));
        Array<TextureRegion> deActive = new Array<>(type.getTextureRegions("active"));
        deActive.reverse();
        active.add(type.getTextureRegions("active").get(2));
        active.add(type.getTextureRegions("active").get(2));
        active.add(type.getTextureRegions("active").get(2));
        active.add(type.getTextureRegions("active").get(2));
        active.addAll(deActive);
        active.addAll(still);
        active.addAll(still);
        entity.addAnimation("still", new Animation<>(0.2f, still));
        entity.addAnimation("active", new Animation<>(0.2f, active));
//        entity.setAnimation("active");
    }

    public void setTentacle() {
        Array<TextureRegion> run = new Array<>(type.getTextureRegions("run"));
        Array<TextureRegion> attack = new Array<>(type.getTextureRegions("attack"));

        entity.addAnimation("run", new Animation<>(0.1f, run));
        entity.addAnimation("attack", new Animation<>(0.1f, attack));
    }

    public void setEyeBat() {
        Array<TextureRegion> run = new Array<>(type.getTextureRegions("run"));

        entity.addAnimation("run", new Animation<>(0.1f, run));
    }

    public AnimatedEntity getEntity() {
        return entity;
    }

    public EnemyType getType() {
        return type;
    }

    public float getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isShooter() {
        return isShooter;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public float getShootingDelay() {
        return shootingDelay;
    }

    public void setEntity(AnimatedEntity entity) {
        this.entity = entity;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setShooter(boolean shooter) {
        isShooter = shooter;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    public void setShootingDelay(float shootingDelay) {
        this.shootingDelay = shootingDelay;
    }

    public Collision getCollision() {
        return collision;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setCenterX(float centerX) {
        float old = this.centerX;
        this.centerX = centerX;
        if (entity == null) prePareEntity();
        else {
            entity.move(centerX - old, 0);
            collision.move(centerX - old, 0);
        }
    }

    public void setCenterY(float centerY) {
        float old = this.centerY;
        this.centerY = centerY;
        if (entity == null) prePareEntity();
        else {
            entity.move(0, centerY - old);
            collision.move(0, centerY - old);
        }
    }

    public void move(float dx, float dy) {
        setCenterX(centerX + dx);
        setCenterY(centerY + dy);
    }

    public boolean handleDamage(float damage) {
        hp -= damage;
        if (hp <= 0) {
            return true;
        }
        return false;
    }

    public void applyKnockBack(float knockBackAngleRad, float force, float knockBackDamping) {
        knockbackVector.set(MathUtils.cos(knockBackAngleRad) * force, MathUtils.sin(knockBackAngleRad) * force);
        this.knockBackDamping = knockBackDamping;
    }

    public Vector2 getKnockbackVector() {
        return knockbackVector;
    }

    public float getKnockBackDamping() {
        return knockBackDamping;
    }

    public float getLastShotDiff() {
        return lastShotDiff;
    }
    public void setLastShotDiff(float lastShotDiff) {
        this.lastShotDiff = lastShotDiff;
    }
}
