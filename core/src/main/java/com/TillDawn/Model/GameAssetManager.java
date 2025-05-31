package com.TillDawn.Model;

import com.TillDawn.Model.Enums.Assets.Musics;
import com.TillDawn.Model.Enums.Hero;
import com.TillDawn.Model.GameStuff.AnimatedEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;

public class GameAssetManager {
    private static GameAssetManager instance;
    public static GameAssetManager getInstance() {
        if (instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    public Skin getSkin() {
        return skin;
    }

    private ArrayList<String> defaultAvatars = new ArrayList<>(Arrays.asList(
        "avatars/blue_sprite.jpg",
        "avatars/guts.jpg",
        "avatars/berserk.jpg"));

    public ShaderProgram shader;

    public final String defaultPreGameMusicPath = Musics.PreGame7.getPath();

    private ArrayList<String> enemyDeathTexturePath = new ArrayList<>(Arrays.asList(
        "textures/Effects/EnemyDeath/DeathFX_0.png",
        "textures/Effects/EnemyDeath/DeathFX_1.png",
        "textures/Effects/EnemyDeath/DeathFX_2.png",
        "textures/Effects/EnemyDeath/DeathFX_3.png"));

    private ArrayList<Texture> enemyDeathTexture = null;
    private Array<TextureRegion> enemyDeathTextureRegion = null;

    private ArrayList<String> borderTexturePath = new ArrayList<>(Arrays.asList("textures/Enemies/shub_niggura/border/T_TentacleAttackIndicator_0.png",
        "textures/Enemies/shub_niggura/border/T_TentacleAttackIndicator_1.png",
        "textures/Enemies/shub_niggura/border/T_TentacleAttackIndicator_2.png",
        "textures/Enemies/shub_niggura/border/T_TentacleAttackIndicator_3.png",
        "textures/Enemies/shub_niggura/border/T_TentacleAttackIndicator_4.png",
        "textures/Enemies/shub_niggura/border/T_TentacleAttackIndicator_5.png",
        "textures/Enemies/shub_niggura/border/T_TentacleAttackIndicator_6.png"));
    private ArrayList<Texture> borderTexture = null;
    private Array<TextureRegion> borderTextureRegion = null;

    private ArrayList<String> elecWallHPath =  new ArrayList<>(Arrays.asList("textures/Enemies/shub_niggura/border/electricalWall/H/T_ElectricWall1.png",
        "textures/Enemies/shub_niggura/border/electricalWall/H/T_ElectricWall2.png",
        "textures/Enemies/shub_niggura/border/electricalWall/H/T_ElectricWall3.png",
        "textures/Enemies/shub_niggura/border/electricalWall/H/T_ElectricWall4.png",
        "textures/Enemies/shub_niggura/border/electricalWall/H/T_ElectricWall5.png",
        "textures/Enemies/shub_niggura/border/electricalWall/H/T_ElectricWall6.png"));
    private ArrayList<Texture> elecWallHTexture = null;
    private Array<TextureRegion> elecWallHTextureRegion = null;

    private ArrayList<Texture> elecWallVTexture = null;
    private ArrayList<String> elecWallVPath =  new ArrayList<>(Arrays.asList("textures/Enemies/shub_niggura/border/electricalWall/V/T_ElectricWall1.png",
        "textures/Enemies/shub_niggura/border/electricalWall/V/T_ElectricWall2.png",
        "textures/Enemies/shub_niggura/border/electricalWall/V/T_ElectricWall3.png",
        "textures/Enemies/shub_niggura/border/electricalWall/V/T_ElectricWall4.png",
        "textures/Enemies/shub_niggura/border/electricalWall/V/T_ElectricWall5.png",
        "textures/Enemies/shub_niggura/border/electricalWall/V/T_ElectricWall6.png"));
    private Array<TextureRegion> elecWallVTextureRegion = null;

    private ArrayList<String> heartFullTexturePath = new ArrayList<>(Arrays.asList("textures/Heart/HeartAnimation_2.png",
        "textures/Heart/HeartAnimation_2.png",
        "textures/Heart/HeartAnimation_2.png",
        "textures/Heart/HeartAnimation_0.png",
        "textures/Heart/HeartAnimation_1.png"));
    private Array<TextureRegion> heartFullRegion = null;

    public Array<TextureRegion> getHeartFullRegion () {
        if (heartFullRegion == null) {
            heartFullRegion = new Array<>();
            for (String path : heartFullTexturePath) {
                heartFullRegion.add(new TextureRegion(new Texture(Gdx.files.internal(path))));
            }
        }
        return heartFullRegion;
    }

    private ArrayList<String> heartBrokenTexturePath = new ArrayList<>(Arrays.asList("textures/Heart/Broken/HeartAnimation_3.png"));
    private Array<TextureRegion> heartBrokenRegion = null;

    private ArrayList<String> xpTexturePath = new ArrayList<>(Arrays.asList("textures/XP/T_SmallCircle.png"));
    private Array<TextureRegion> xpRegion = null;

    public final FileHandle singleShotSfx = Gdx.files.internal("SFX/AudioClip/single_shot.wav");
    public final FileHandle bloodSplash = Gdx.files.internal("SFX/AudioClip/Blood_Splash_Quick_01.wav");
    public final FileHandle xpPick = Gdx.files.internal("SFX/AudioClip/Crystal Reward Tick.wav");
    public final FileHandle levelUp = Gdx.files.internal("SFX/AudioClip/Special & Powerup (11).wav");
    public final FileHandle reload = Gdx.files.internal("SFX/AudioClip/Weapon_Shotgun_Reload.wav");
    public final FileHandle winSfx = Gdx.files.internal("SFX/AudioClip/You Win (2).wav");
    public final FileHandle looseSfx = Gdx.files.internal("SFX/AudioClip/You Lose (4).wav");

    public Array<TextureRegion> getXpRegion () {
        if (xpRegion == null) {
            xpRegion = new Array<>();
            for (String path : xpTexturePath) {
                xpRegion.add(new TextureRegion(new Texture(Gdx.files.internal(path))));
            }
        }
        return xpRegion;
    }

    public Array<TextureRegion> getHeartBrokenRegion () {
        if (heartBrokenRegion == null) {
            heartBrokenRegion = new Array<>();
            for (String path : heartBrokenTexturePath) {
                heartBrokenRegion.add(new TextureRegion(new Texture(Gdx.files.internal(path))));
            }
        }
        return heartBrokenRegion;
    }

    public ArrayList<Texture> getElecWallVTexture() {
        if (elecWallVTexture == null) {
            elecWallVTexture = new ArrayList<>();
            for (String path : elecWallVPath) {
                elecWallVTexture.add(new Texture(Gdx.files.internal(path)));
            }
        }
        return elecWallVTexture;
    }
    public Array<TextureRegion> getElecWallVTextureRegion() {
        if (elecWallVTextureRegion == null) {
            elecWallVTextureRegion = new Array<>();
            for (Texture texture : getElecWallVTexture()) {
                elecWallVTextureRegion.add(new TextureRegion(texture));
            }
        }
        return elecWallVTextureRegion;
    }
    public ArrayList<Texture> getElecWallHTexture() {
        if (elecWallHTexture == null) {
            elecWallHTexture = new ArrayList<>();
            for (String path : elecWallHPath) {
                elecWallHTexture.add(new Texture(Gdx.files.internal(path)));
            }
        }
        return elecWallHTexture;
    }
    public Array<TextureRegion> getElecWallHTextureRegion() {
        if (elecWallHTextureRegion == null) {
            elecWallHTextureRegion = new Array<>();
            for (Texture texture : getElecWallHTexture()) {
                elecWallHTextureRegion.add(new TextureRegion(texture));
            }
        }
        return elecWallHTextureRegion;
    }


    public ArrayList<Texture> getBorderTexture() {
        if (borderTexture == null) {
            borderTexture = new ArrayList<>();
            for (String path : borderTexturePath) {
                borderTexture.add(new Texture(Gdx.files.internal(path)));
            }
        }
        return borderTexture;
    }

    public Array<TextureRegion> getBorderTextureRegion() {
        if (borderTextureRegion == null) {
            borderTextureRegion = new Array<>();
            for (Texture texture : getBorderTexture()) {
                borderTextureRegion.add(new TextureRegion(texture));
            }
        }
        return borderTextureRegion;
    }

    public ArrayList<Texture> getEnemyDeathTexture() {
        if (enemyDeathTexture == null) {
            enemyDeathTexture = new ArrayList<>();
            for (String path : enemyDeathTexturePath) {
                enemyDeathTexture.add(new Texture(Gdx.files.internal(path)));
            }
        }
        return enemyDeathTexture;
    }

    public Array<TextureRegion> getEnemyDeathTextureRegion() {
        if (enemyDeathTextureRegion == null) {
            enemyDeathTextureRegion = new Array<>();
            for (Texture texture : getEnemyDeathTexture()) {
                enemyDeathTextureRegion.add(new TextureRegion(texture));
            }
        }
        return enemyDeathTextureRegion;
    }

    public ArrayList<String> getDefaultAvatars() {
        return defaultAvatars;
    }

    public String getRandomAvatar() {
        int ind = App.getInstance().appService().getRandomNumber(0, defaultAvatars.size() - 1);
        return defaultAvatars.get(ind);
    }

    public Texture cropToCircle(Texture texture) {
        texture.getTextureData().prepare();
        Pixmap original = texture.getTextureData().consumePixmap();

        int size = Math.min(original.getWidth(), original.getHeight());
        Pixmap circlePixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);

        int centerX = size / 2;
        int centerY = size / 2;
        int radius = size / 2;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int dx = x - centerX;
                int dy = y - centerY;
                if (dx * dx + dy * dy <= radius * radius) {
                    int pixel = original.getPixel(x, y);
                    circlePixmap.drawPixel(x, y, pixel);
                } else {
                    circlePixmap.drawPixel(x, y, 0); // Transparent
                }
            }
        }

        Texture circleTexture = new Texture(circlePixmap);
        original.dispose();
        circlePixmap.dispose();

        return circleTexture;
    }

    public void dispose() {

    }
}
