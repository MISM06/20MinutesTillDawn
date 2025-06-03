package com.TillDawn.Model.GameStuff;

import com.TillDawn.Model.*;
import com.TillDawn.Model.Enums.Hero;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {
    private int userId;
    @JsonIgnore
    private User user;

    @JsonIgnore
    private AnimatedEntity entity;
    private float x;
    private float y;
    private Collision collision;
    private Hero hero;
    private int hp;
    private int maxHp;
    private float speed;
    private Gun gun;
    private int xp;
    private int level;
    private int currentLevelMinXp;
    private int killCount = 0;

    private boolean autoAim = false;

    private float invincibilityRangeTime = 2;
    private float invincibilityStartTime = 0;
    private boolean isInvincible = true;
    private float lastBlinkDiff = 0f;
    private float blinkFrequency = 0.3f;

    private Vector2 knockbackVector = new Vector2(0, 0);
    private float knockBackDamping = 10f;

    public Player() {
    }

    public Player(int userId) {
        this.userId = userId;
        user = App.getInstance().appService().getUserById(userId);
        x = 0; y = 0;
        entity = new AnimatedEntity(x, y, 1, 1);
        xp = 0;
        currentLevelMinXp = 0;
        level = 1;
        autoAim = user.getSettingData().isAutoAimEnabled();
    }

    public void setX(float x) {
        if (entity != null) entity.getSprite().translateX(x - this.x);
        if (collision != null) collision.move(x - this.x, 0);
        this.x = x;
    }

    public void setY(float y) {
        if (entity != null) entity.getSprite().translateY(y - this.y);
        if (collision != null) collision.move(0, y - this.y);
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void prePareEntity() {
        entity = new AnimatedEntity(x, y, 1, 1);
        entity.addAnimation("idle", new Animation<>(0.15f, hero.getIdleRegion()));
        entity.addAnimation("run", new Animation<>(0.1f / (float) Math.sqrt(speed), hero.getRunRegion()));
        entity.setAnimation("idle");
        entity.setSize(hero.getWidth(), hero.getHeight());
    }

    public void setHeroType(Hero hero) {
        this.hero = hero;
        hp = hero.getHp();
        maxHp = hp;
        speed = (float) Math.sqrt(hero.getSpeed() * Math.sqrt(hero.getSpeed()));
        entity.addAnimation("idle", new Animation<>(0.15f, hero.getIdleRegion()));
        entity.addAnimation("run", new Animation<>(0.1f / (float) Math.sqrt(speed), hero.getRunRegion()));
        entity.setAnimation("idle");
        entity.setSize(hero.getWidth(), hero.getHeight());
        collision = new Collision(x + entity.getWidth() / 2, y + entity.getHeight() / 2.1f,
            entity.getWidth() * 0.7f, entity.getHeight() * 0.7f);
    }
    public void setHero(Hero hero) {
        this.hero = hero;
        if (entity == null) prePareEntity();
    }

    public Hero getHero() {
        return hero;
    }

    public void update(float delta) {
        float dx = knockbackVector.x * delta;
        float dy = knockbackVector.y * delta;
        move(dx, dy);
        knockbackVector.lerp(Vector2.Zero, knockBackDamping * delta);

        if (isInvincible) {
            if (isInvincible()) {
                lastBlinkDiff += delta;
                if (lastBlinkDiff >= blinkFrequency) {
                    lastBlinkDiff = 0;
                    float alpha = entity.getSprite().getColor().a == 1f ? 0.2f : 1f;
                    entity.getSprite().setAlpha(alpha);
                }
            } else {
                isInvincible = false;
                entity.getSprite().setAlpha(1f);
            }
        }

        entity.update(delta);
    }

    public User getUser() {
        return user;
    }

    public AnimatedEntity getEntity() {
        return entity;
    }

    public int getHp() {
        return hp;
    }

    public float getSpeed() {
        return speed;
    }

    public Gun getGun() {
        return gun;
    }

    public int getXp() {
        return xp;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        if (hero != null) {
            entity.getAnimations().put("run", new Animation<>(0.1f / (float) Math.sqrt(speed), hero.getRunRegion()));
        }
    }

    public void setGun(Gun gun) {
        this.gun = gun;
        gun.setRadius(Math.min(entity.getWidth(), entity.getHeight()) * 0.7f);
    }

    public void addXp() {
        xp += 1;
        if (xp - currentLevelMinXp >= Utils.xpCoeff * level) {
            addLevel();
        }
    }

    public void draw(SpriteBatch batch) {
        entity.draw(batch);
    }

    public void move(float dx, float dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public Collision getCollision() {
        return collision;
    }

    public void handleDamage(int damage, float knockBackAngelRad, float knockBackFore, boolean invincible) {
        if (damage > 0) {
            AudioManager.playSFX(GameAssetManager.getInstance().bloodSplash);
            AudioManager.setSFXVolume(0.35f);
        }
        hp -= damage;
        if (hp <= 0) {
            Game.getInstance().finishGame();
        }
        if (invincible) {
            makeInvincible();
        }
        applyKnockBack(knockBackAngelRad, knockBackFore, 3);
    }

    public void applyKnockBack(float knockBackAngleRad, float force, float knockBackDamping) {
        knockbackVector.set(MathUtils.cos(knockBackAngleRad) * force, MathUtils.sin(knockBackAngleRad) * force);
        this.knockBackDamping = knockBackDamping;
    }

    public void makeInvincible() {
        invincibilityStartTime = Game.getInstance().getTimePassed();
        isInvincible = true;
        lastBlinkDiff = blinkFrequency;
    }

    public boolean isInvincible() {
        return Game.getInstance().getTimePassed() - invincibilityStartTime < invincibilityRangeTime;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel() {
        ++level;
        currentLevelMinXp = xp;
        AudioManager.setSFXVolume(0.3f);
        AudioManager.playSFX(GameAssetManager.getInstance().levelUp);
        abilityShow();
    }

    public int getCurrentLevelMinXp() {
        return currentLevelMinXp;
    }

    public void abilityShow() {
        Game.getInstance().getGameViewController().chooseAbility();
    }

    public boolean isAutoAim() {
        return autoAim;
    }

    public void setAutoAim(boolean autoAim) {
        this.autoAim = autoAim;
    }

    public int getUserId() {
        return userId;
    }

    public void die() {

    }

    public void setEntity(AnimatedEntity entity) {
        this.entity = entity;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCurrentLevelMinXp(int currentLevelMinXp) {
        this.currentLevelMinXp = currentLevelMinXp;
    }

    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    public void setInvincibilityRangeTime(float invincibilityRangeTime) {
        this.invincibilityRangeTime = invincibilityRangeTime;
    }

    public void setInvincibilityStartTime(float invincibilityStartTime) {
        this.invincibilityStartTime = invincibilityStartTime;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    public void setLastBlinkDiff(float lastBlinkDiff) {
        this.lastBlinkDiff = lastBlinkDiff;
    }

    public void setBlinkFrequency(float blinkFrequency) {
        this.blinkFrequency = blinkFrequency;
    }

    public void setKnockbackVector(Vector2 knockbackVector) {
        this.knockbackVector = knockbackVector;
    }

    public void setKnockBackDamping(float knockBackDamping) {
        this.knockBackDamping = knockBackDamping;
    }

    public float getInvincibilityRangeTime() {
        return invincibilityRangeTime;
    }

    public float getInvincibilityStartTime() {
        return invincibilityStartTime;
    }

    public float getLastBlinkDiff() {
        return lastBlinkDiff;
    }

    public float getBlinkFrequency() {
        return blinkFrequency;
    }

    public Vector2 getKnockbackVector() {
        return knockbackVector;
    }

    public float getKnockBackDamping() {
        return knockBackDamping;
    }


}
