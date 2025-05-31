package com.TillDawn.Model.GameStuff;

import com.TillDawn.Model.App;
import com.TillDawn.Model.AudioManager;
import com.TillDawn.Model.Enums.BulletType;
import com.TillDawn.Model.Enums.GunType;
import com.TillDawn.Model.GameAssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Gun {
    private GunType type;
    private AnimatedEntity entity;
    private float damage;
    private float projectile;
    private float reloadTime;
    private float maxAmmo;
    private float ammo;
    private float spreedDeg;
    private BulletType bulletType;
    private float shootingDelay;
    private float lastShotDiff = 0;
    private boolean inShooting = false;

    private boolean autoReload;
    private boolean autoAim;
    private float radius = 1f;
    private float angleDeg;

    public Gun(GunType type) {
        this.type = type;
        entity = new AnimatedEntity(0, 0, type.getWidth(), type.getHeight());
        entity.addAnimation("still", new Animation<>(0.1f, type.getStillRegion()));
        entity.addAnimation("reload", new Animation<>(0.1f, type.getReloadRegion()));
        entity.setAnimation("still");
        entity.getSprite().setOriginCenter();
        damage = type.getDamage();
        projectile = type.getProjectile();
        reloadTime = type.getTimeReload();
        maxAmmo = type.getMaxAmmo();
        shootingDelay = type.getShootingDelay();
        ammo = maxAmmo;
        spreedDeg = 5 * (projectile - 1);
        autoReload = false;
        autoAim = false;
        bulletType = BulletType.normal;
    }

    public Gun() {
    }

    public GunType getType() {
        return type;
    }

    public AnimatedEntity getEntity() {
        return entity;
    }

    public float getDamage() {
        return damage;
    }

    public float getProjectile() {
        return projectile;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public float getMaxAmmo() {
        return maxAmmo;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public boolean isAutoAim() {
        return autoAim;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setProjectile(float projectile) {
        this.projectile = projectile;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    public void setMaxAmmo(float maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public void setAutoAim(boolean autoAim) {
        this.autoAim = autoAim;
    }

    public void update(float delta, Player player, float targetX, float targetY) {
        float Ox = player.getX() + player.getEntity().getWidth() / 2f;
        float Oy = player.getY() + player.getEntity().getHeight() / 2f;
        float dx = targetX - Ox;
        float dy = targetY - Oy;
        float angleDeg = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
        float angleRad = angleDeg * MathUtils.degreesToRadians;
        float offsetX = MathUtils.cos(angleRad) * radius;
        float offsetY = MathUtils.sin(angleRad) * radius;

//        float centerDis = (float) Math.sqrt((entity.getWidth() / 2f) * (entity.getWidth() / 2f) + (entity.getHeight() / 2f) * (entity.getHeight() / 2f));
//        offsetX -= MathUtils.cos(angleRad) * centerDis;
//        offsetY -= MathUtils.sin(angleRad) * centerDis;

        entity.setPosition(Ox + offsetX - entity.getWidth() / 2, Oy + offsetY - entity.getHeight() / 2);
        entity.getSprite().setRotation(angleDeg);
        this.angleDeg = angleDeg;
        if (dx > 0) entity.faceWayUp();
        else entity.faceWayDown();
        if (entity.getCurrentAnimation().equals("reload") && entity.getTime() >= reloadTime) {
            entity.setAnimation("still");
            ammo = maxAmmo;
        }
        entity.update(delta);
        lastShotDiff += delta;
        if (!inShooting) {
        } else if (entity.getCurrentAnimation().equals("reload")) {
        } else if (ammo == 0 && !autoReload) {
        } else if (ammo == 0 && autoReload) {
            reload();
        } else if (lastShotDiff < shootingDelay) {
        }else shoot(spreedDeg);
    }
    public void draw(SpriteBatch batch) {
        entity.draw(batch);
    }

    public float getAmmo() {
        return ammo;
    }

    public void setRadius(float radius) {
        this.radius = radius;
//        this.radius = 0;
    }

    public void shootSingleProjectile(float angleDeg, float bulletStartX, float bulletStartY) {
        Bullet bull = new Bullet(bulletType, 20, 7.5f, bulletStartX, bulletStartY, angleDeg, 1, getDamage(), type);
        Game.getInstance().getBulletManager().addBullet(bull);
    }

    public void reload() {
        AudioManager.setSFXVolume(0.3f);
        AudioManager.playSFX(GameAssetManager.getInstance().reload);
        entity.setAnimation("reload");
    }

    public void shoot(float spreedDegree) {

        while (spreedDegree > 360) {
            spreedDegree -= 360;
        }
        while (spreedDegree < 0) {
            spreedDegree += 360;
        }

//        float bullStartX = entity.getX() + entity.getWidth() / 2 + MathUtils.cosDeg(angleDeg) * (entity.getWidth() / 2f);
//        float bullStartY = entity.getY() + entity.getHeight() / 2 + MathUtils.sinDeg(angleDeg) * (entity.getWidth() / 2f);

        float gunCenterX = entity.getX() + entity.getWidth() / 2f;
        float gunCenterY = entity.getY() + entity.getHeight() / 2f;


        float barrelLength = entity.getWidth() * 0.2f;

        float bullStartX = gunCenterX + MathUtils.cosDeg(angleDeg) * barrelLength;
        float bullStartY = gunCenterY + MathUtils.sinDeg(angleDeg) * barrelLength;
        if (projectile == 1) {
            shootSingleProjectile(angleDeg, bullStartX, bullStartY);
        } else {
            float startDeg = angleDeg - (spreedDegree / 2);
            float disAngle = spreedDegree / (projectile - 1);
            for (int i = 0; i < projectile; i++) {
                shootSingleProjectile(startDeg, bullStartX, bullStartY);
                startDeg += disAngle;
            }
        }
        AudioManager.setSFXVolume(0.25f);
        AudioManager.playSFX(GameAssetManager.getInstance().singleShotSfx);
        ammo -= 1;
        lastShotDiff = 0;
        if (ammo == 0 && autoReload) {
            reload();
        }
    }

    public float getLastShotDiff() {
        return lastShotDiff;
    }

    public boolean isInShooting() {
        return inShooting;
    }

    public void setInShooting(boolean inShooting) {
        this.inShooting = inShooting;
    }

    public void setSpreedDeg(float spreedDegree) {
        this.spreedDeg = spreedDegree;
    }
    public float getSpreedDeg() {
        return spreedDeg;
    }
}
