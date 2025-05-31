package com.TillDawn.Model.Enums;

import com.TillDawn.Model.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;

public enum GunType {
    Revolver(1f, 1f, 20,
        1,
        1,
        6,
        0.4f,
        7,
        "Damage: 20\nProjectile: 1\nMax ammo: 6\nReload time: 1\nDelay: 0.4",
        new ArrayList<>(Arrays.asList("textures/Guns/Revolver/RevolverStill.png")),
        new ArrayList<>(Arrays.asList("textures/Guns/Revolver/RevolverReload_0.png",
            "textures/Guns/Revolver/RevolverReload_1.png",
            "textures/Guns/Revolver/RevolverReload_2.png",
            "textures/Guns/Revolver/RevolverReload_3.png"))
    ),
    Shotgun(1f, 1f, 10,
        4,
        1,
        2,
        0.8f,
        10,
        "Damage: 10\nProjectile: 4\nMax ammo: 2\nReload time: 1\nDelay: 0.8",
        new ArrayList<>(Arrays.asList("textures/Guns/Shotgun/T_Shotgun_still.png")),
        new ArrayList<>(Arrays.asList("textures/Guns/Shotgun/T_Shotgun_SS_1.png",
            "textures/Guns/Shotgun/T_Shotgun_SS_2.png",
            "textures/Guns/Shotgun/T_Shotgun_SS_3.png"))
    ),
    SMG(1f, 1f, 8,
        1,
        2,
        24,
        0.2f,
        4,
        "Damage: 8\nProjectile: 1\nMax ammo: 24\nReload time: 2\nDelay: 0.2",
        new ArrayList<>(Arrays.asList("textures/Guns/SMG/SMGStill.png")),
        new ArrayList<>(Arrays.asList("textures/Guns/SMG/SMGReload_0.png",
            "textures/Guns/SMG/SMGReload_1.png",
            "textures/Guns/SMG/SMGReload_2.png",
            "textures/Guns/SMG/SMGReload_3.png"))
    )
    ;

    private float width;
    private float height;
    private float damage;
    private float projectile;
    private float timeReload;
    private float maxAmmo;
    private float shootingDelay;
    private float knockBackForce;

    private final ArrayList<String> stillPath;
    private ArrayList<Texture> stillTexture = null;
    private Array<TextureRegion> stillRegion = null;

    private final ArrayList<String> reloadPath;
    private ArrayList<Texture> reloadTexture = null;
    private Array<TextureRegion> reloadRegion = null;

    private String description;

    GunType(float widthScale,
            float heightScale,
            float damage,
            float projectile,
            float timeReload,
            float maxAmmo,
            float shootingDelay,
            float knockBackForce,
            String description,
            ArrayList<String> stillPath,
            ArrayList<String> reloadPath) {
        this.width = widthScale * Utils.gunWidth;
        this.height = heightScale * Utils.gunHeight;
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        this.maxAmmo = maxAmmo;
        this.shootingDelay = shootingDelay;
        this.description = description;
        this.stillPath = stillPath;
        this.reloadPath = reloadPath;
        this.knockBackForce = knockBackForce;
    }

    public float getDamage() {
        return damage;
    }

    public float getProjectile() {
        return projectile;
    }

    public float getTimeReload() {
        return timeReload;
    }

    public float getMaxAmmo() {
        return maxAmmo;
    }

    public ArrayList<String> getStillPath() {
        return stillPath;
    }

    public ArrayList<Texture> getStillTexture() {
        if (stillTexture == null) {
            stillTexture = new ArrayList<>();
            for (String path : stillPath) {
                stillTexture.add(new Texture(Gdx.files.internal(path)));
            }
        }
        return stillTexture;
    }

    public Array<TextureRegion> getStillRegion() {
        if (stillRegion == null) {
            stillRegion = new Array<>();
            getStillTexture().forEach(texture -> stillRegion.add(new TextureRegion(texture)));
        }
        return stillRegion;
    }

    public ArrayList<String> getReloadPath() {
        return reloadPath;
    }

    public ArrayList<Texture> getReloadTexture() {
        if (reloadTexture == null) {
            reloadTexture = new ArrayList<>();
            for (String path : reloadPath) {
                reloadTexture.add(new Texture(Gdx.files.internal(path)));
            }
        }
        return reloadTexture;
    }

    public Array<TextureRegion> getReloadRegion() {
        if (reloadRegion == null) {
            reloadRegion = new Array<>();
            for (Texture texture : getReloadTexture()) {
                reloadRegion.add(new TextureRegion(texture));
            }
        }
        return reloadRegion;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getShootingDelay() {
        return shootingDelay;
    }

    public float getKnockBackForce() {
        return knockBackForce;
    }

    public String getDescription() {
        return description;
    }
}
