package com.TillDawn.Model.Enums;

import com.TillDawn.Model.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;

public enum Hero {
    Shana(1, 1.64f, 4,
        4,
        "Perfectly normal!\n(Hp: 4, Speed: 4)\n",
        new ArrayList<>(Arrays.asList("textures/Heros/Shana/idle/Idle_0 #8330.png",
            "textures/Heros/Shana/idle/Idle_1 #8360.png",
            "textures/Heros/Shana/idle/Idle_2 #8819.png",
            "textures/Heros/Shana/idle/Idle_3 #8457.png",
            "textures/Heros/Shana/idle/Idle_4 #8318.png",
            "textures/Heros/Shana/idle/Idle_5 #8307.png")),
        new ArrayList<>(Arrays.asList("textures/Heros/Shana/run/Run_0 #8762.png",
            "textures/Heros/Shana/run/Run_1 #8778.png",
            "textures/Heros/Shana/run/Run_2 #8286.png",
            "textures/Heros/Shana/run/Run_3 #8349.png")),
        "textures/Heros/Portrait/T_Shana_Portrait.png"),
    Diamond(1, 1.93f, 7,
        1,
        "Slow but die hard!\n(Hp: 7, Speed: 4)\n",
        new ArrayList<>(Arrays.asList("textures/Heros/Diamond/idle/Idle_0 #8328.png",
            "textures/Heros/Diamond/idle/Idle_1 #8358.png",
            "textures/Heros/Diamond/idle/Idle_2 #8817.png",
            "textures/Heros/Diamond/idle/Idle_3 #8455.png",
            "textures/Heros/Diamond/idle/Idle_4 #8316.png",
            "textures/Heros/Diamond/idle/Idle_5 #8305.png")),
        new ArrayList<>(Arrays.asList("textures/Heros/Diamond/run/Run_0 #8760.png",
            "textures/Heros/Diamond/run/Run_1 #8776.png",
            "textures/Heros/Diamond/run/Run_2 #8284.png",
            "textures/Heros/Diamond/run/Run_3 #8347.png")),
        "textures/Heros/Portrait/T_Diamond_Portrait.png"),
    Scarlet(1, 1.66f, 3,
        5,
        "She's fast.\n(Hp: 3, Speed: 5)\n",
        new ArrayList<>(Arrays.asList("textures/Heros/Scarlet/idle/Idle_0 #8327.png",
            "textures/Heros/Scarlet/idle/Idle_1 #8357.png",
            "textures/Heros/Scarlet/idle/Idle_2 #8816.png",
            "textures/Heros/Scarlet/idle/Idle_3 #8454.png",
            "textures/Heros/Scarlet/idle/Idle_4 #8315.png",
            "textures/Heros/Scarlet/idle/Idle_5 #8304.png")),
        new ArrayList<>(Arrays.asList("textures/Heros/Scarlet/run/Run_0 #8759.png",
            "textures/Heros/Scarlet/run/Run_1 #8775.png",
            "textures/Heros/Scarlet/run/Run_2 #8283.png",
            "textures/Heros/Scarlet/run/Run_3 #8346.png")),
        "textures/Heros/Portrait/T_Scarlett_Portrait.png"),
    Lilith(1, 1.86f, 5,
        3,
        "She's a bit heavy!\n(Hp: 5, Speed: 3)\n",
        new ArrayList<>(Arrays.asList("textures/Heros/Lilith/idle/Idle_0 #8333.png",
            "textures/Heros/Lilith/idle/Idle_1 #8363.png",
            "textures/Heros/Lilith/idle/Idle_2 #8822.png",
            "textures/Heros/Lilith/idle/Idle_3 #8460.png",
            "textures/Heros/Lilith/idle/Idle_4 #8321.png",
            "textures/Heros/Lilith/idle/Idle_5 #8310.png")),
        new ArrayList<>(Arrays.asList("textures/Heros/Lilith/run/Run_0 #8765.png",
            "textures/Heros/Lilith/run/Run_1 #8781.png",
            "textures/Heros/Lilith/run/Run_2 #8289.png",
            "textures/Heros/Lilith/run/Run_3 #8352.png")),
        "textures/Heros/Portrait/T_Lilith_Portrait.png"),
    Dasher(1, 1.25f, 2,
        10,
        "Catch her if you can!\n(Hp: 2, Speed: 10!)\n",
        new ArrayList<>(Arrays.asList("textures/Heros/Dasher/idle/Idle_0 #8325.png",
            "textures/Heros/Dasher/idle/Idle_1 #8355.png",
            "textures/Heros/Dasher/idle/Idle_2 #8814.png",
            "textures/Heros/Dasher/idle/Idle_3 #8452.png",
            "textures/Heros/Dasher/idle/Idle_4 #8313.png",
            "textures/Heros/Dasher/idle/Idle_5 #8302.png")),
        new ArrayList<>(Arrays.asList("textures/Heros/Dasher/run/Run_0 #8757.png",
            "textures/Heros/Dasher/run/Run_1 #8773.png",
            "textures/Heros/Dasher/run/Run_2 #8281.png",
            "textures/Heros/Dasher/run/Run_3 #8344.png")),
        "textures/Heros/Portrait/T_Dasher_Portrait.png")
    ;
    private final ArrayList<String> idlePath;
    private ArrayList<Texture> idleTexture = null;
    private Array<TextureRegion> idleRegion = null;

    private final ArrayList<String> runPath;
    private ArrayList<Texture> runTexture = null;
    private Array<TextureRegion> runRegion = null;

    private final float width;
    private final float height;
    private final int hp;
    private final float speed;
    private final String description;

    private String portraitPath;
    private Texture portraitTexture;
    private TextureRegion portraitRegion;
    Hero(float widthScale,
         float heightScale,
         int hp,
         float speed,
         String description,
         ArrayList<String> idlePath,
         ArrayList<String> runPath,
         String portraitPath) {
        this.width = widthScale * Utils.heroWidth;
        this.height = heightScale * Utils.heroHeight;
        this.idlePath = idlePath;
        this.runPath = runPath;
        this.hp = hp;
        this.speed = speed;
        this.description = description;
        this.portraitPath = portraitPath;
    }

    public ArrayList<String> getIdlePath() {
        return idlePath;
    }

    public ArrayList<Texture> getIdleTexture() {
        if (idleTexture == null) {
            idleTexture = new ArrayList<>();
            for (String path : idlePath) {
                idleTexture.add(new Texture(Gdx.files.internal(path)));
            }
        }
        return idleTexture;
    }

    public ArrayList<String> getRunPath() {
        return runPath;
    }

    public ArrayList<Texture> getRunTexture() {
        if (runTexture == null) {
            runTexture = new ArrayList<>();
            for (String path : runPath) {
                runTexture.add(new Texture(Gdx.files.internal(path)));
            }
        }
        return runTexture;
    }

    public int getHp() {
        return hp;
    }

    public float getSpeed() {
        return speed;
    }

    public String getDescription() {
        return description;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public Texture getPortraitTexture() {
        if (portraitTexture == null) {
            portraitTexture = new Texture(Gdx.files.internal(portraitPath));
        }
        return portraitTexture;
    }

    public TextureRegion getPortraitRegion() {
        if (portraitRegion == null) {
            portraitRegion = new TextureRegion(getPortraitTexture());
        }
        return portraitRegion;
    }

    public Array<TextureRegion> getRunRegion() {
        if (runRegion == null) {
            runRegion = new Array<>();
            for (Texture texture : getRunTexture()) {
                runRegion.add(new TextureRegion(texture));
            }
        }
        return runRegion;
    }

    public Array<TextureRegion> getIdleRegion() {
        if (idleRegion == null) {
            idleRegion = new Array<>();
            for (Texture texture : getIdleTexture()) {
                idleRegion.add(new TextureRegion(texture));
            }
        }
        return idleRegion;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


}
