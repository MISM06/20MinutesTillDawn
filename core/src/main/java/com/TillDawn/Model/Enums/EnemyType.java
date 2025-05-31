package com.TillDawn.Model.Enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum EnemyType {
    Tree(45000,
        1,
        0,
        false,
        null,
        0,
        3,
        4,
        1.3f,
        3.2f,
        new HashMap<>()) {
        @Override
        public void initTextureMap() {
            getTexturePaths().put("still", new ArrayList<>(Arrays.asList("textures/Enemies/Tree/T_TreeMonster_0.png")));
            getTexturePaths().put(
                    "active", new ArrayList<>(Arrays.asList("textures/Enemies/Tree/T_TreeMonster_0.png",
                            "textures/Enemies/Tree/T_TreeMonster_1.png",
                            "textures/Enemies/Tree/T_TreeMonster_2.png")));
        }
    },
    Tentacle(25,
            1,
            1,
            false,
            null,
            0,
            0.7f,
            1,
            0.5f,
            0.9f,
            new HashMap<>()) {
        public void initTextureMap() {
            getTexturePaths().put("run", new ArrayList<>(Arrays.asList("textures/Enemies/Tentacle/run/T_TentacleEnemy_0.png",
                    "textures/Enemies/Tentacle/run/T_TentacleEnemy_1.png",
                    "textures/Enemies/Tentacle/run/T_TentacleEnemy_2.png",
                    "textures/Enemies/Tentacle/run/T_TentacleEnemy_3.png")));
            getTexturePaths().put("attack", new ArrayList<>(Arrays.asList("textures/Enemies/Tentacle/attack/T_TentacleAttack.png")));
        }
    },
    EyeBat(50,
            1,
            0.8f,
            true,
            BulletType.EyeBatEye,
            7f,
            1.4f,
            0.8f,
            0.9f,
            0.5f,
            new HashMap<>()) {
        public void initTextureMap() {
            getTexturePaths().put("run", new ArrayList<>(Arrays.asList("textures/Enemies/Eyebat/run/T_EyeBat_0.png",
                    "textures/Enemies/Eyebat/run/T_EyeBat_1.png",
                    "textures/Enemies/Eyebat/run/T_EyeBat_2.png",
                    "textures/Enemies/Eyebat/run/T_EyeBat_3.png")));
        }
    },
    ShubNiggurath(400,
            1,
            2.5f,
            false,
            null,
            5,
            4.8f,
            3,
            3.6f,
            2.3f,
            new HashMap<>()) {
        public void initTextureMap() {
            getTexturePaths().put("run", new ArrayList<>(Arrays.asList("textures/Enemies/shub_niggura/run/T_ShubNiggurath_0.png",
                "textures/Enemies/shub_niggura/run/T_ShubNiggurath_1.png",
                "textures/Enemies/shub_niggura/run/T_ShubNiggurath_2.png")));
            getTexturePaths().put("dash", new ArrayList<>(Arrays.asList("textures/Enemies/shub_niggura/dash/T_ShubNiggurath_8.png",
                "textures/Enemies/shub_niggura/dash/T_ShubNiggurath_9.png",
                "textures/Enemies/shub_niggura/dash/T_ShubNiggurath_10.png",
                "textures/Enemies/shub_niggura/dash/T_ShubNiggurath_3.png",
                "textures/Enemies/shub_niggura/dash/T_ShubNiggurath_3.png")));
            getTexturePaths().put("charge", new ArrayList<>(Arrays.asList("textures/Enemies/shub_niggura/charge/T_ShubNiggurath_3.png",
                "textures/Enemies/shub_niggura/charge/T_ShubNiggurath_4.png",
                "textures/Enemies/shub_niggura/charge/T_ShubNiggurath_5.png",
                "textures/Enemies/shub_niggura/charge/T_ShubNiggurath_6.png",
                "textures/Enemies/shub_niggura/charge/T_ShubNiggurath_7.png")));
            getTexturePaths().put("still", new ArrayList<>(Arrays.asList("textures/Enemies/shub_niggura/charge/T_ShubNiggurath_3.png")));
        }
    }
    ;
    private float hp;
    private int damage;
    private float speed;
    private boolean isShooter;
    private BulletType bulletType;
    private float shootingDelay;
    private float width;
    private float height;
    private float collisionWidth;
    private float collisionHeight;

    private Map<String, ArrayList<String>> texturePaths;
    private Map<String, Array<Texture>> textures;
    private Map<String, Array<TextureRegion>> textureRegions;

    EnemyType(float hp,
              int damage,
              float speed,
              boolean isShooter,
              BulletType bulletType,
              float shootingDelay,
              float width,
              float height,
              float collisionWidth,
              float collisionHeight,
              Map<String, ArrayList<String>> texturePaths) {
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.isShooter = isShooter;
        this.bulletType = bulletType;
        this.shootingDelay = shootingDelay;
        this.width = width;
        this.height = height;
        this.collisionWidth = collisionWidth;
        this.collisionHeight = collisionHeight;
        this.texturePaths = texturePaths;

        initTextureMap();

        this.textures = new HashMap<>();
        this.textureRegions = new HashMap<>();
        for (String key : texturePaths.keySet()) {
            Array<Texture> textures = new Array<>();
            Array<TextureRegion> textureRegions = new Array<>();
            for (String path : texturePaths.get(key)) {
                Texture texture = new Texture(Gdx.files.internal(path));
                TextureRegion textureRegion = new TextureRegion(texture);
                textures.add(texture);
                textureRegions.add(textureRegion);
            }
            this.textures.put(key, textures);
            this.textureRegions.put(key, textureRegions);
        }
    }

    public abstract void initTextureMap();

    public Array<TextureRegion> getTextureRegions(String key) {
        return textureRegions.get(key);
    }

    public Array<Texture> getTextures(String key) {
        return textures.get(key);
    }

    public float getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getCollisionWidth() {
        return collisionWidth;
    }

    public float getCollisionHeight() {
        return collisionHeight;
    }

    public Map<String, ArrayList<String>> getTexturePaths() {
        return texturePaths;
    }

}
