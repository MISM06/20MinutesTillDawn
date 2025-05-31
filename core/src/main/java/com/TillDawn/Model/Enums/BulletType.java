package com.TillDawn.Model.Enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum BulletType {
    normal("textures/Bullets/bullet.png", 1f, 1f),
    EyeBatEye("textures/Enemies/Eyebat/bullet/T_EyeBat_EM.png", 0.45f, 0.45f),
    ;
    private String bulletTexturePath;
    private Texture bulletTexture;
    private TextureRegion bulletTextureRegion;
    private float width;
    private float height;

    private float radius;
    private float speed;
    private float slowingFactor;

    BulletType(String bulletTexturePath, float width, float height) {
        this.bulletTexturePath = bulletTexturePath;
        this.width = width;
        this.height = height;
    }

    public String getBulletTexturePath() {
        return bulletTexturePath;
    }

    public Texture getBulletTexture() {
        if (bulletTexture == null) {
            bulletTexture = new Texture(Gdx.files.internal(bulletTexturePath));
        }
        return bulletTexture;
    }

    public TextureRegion getBulletTextureRegion() {
        if (bulletTextureRegion == null) {
            bulletTextureRegion = new TextureRegion(getBulletTexture());
        }
        return bulletTextureRegion;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
