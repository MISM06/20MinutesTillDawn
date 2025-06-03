package com.TillDawn;

import com.TillDawn.Controller.ProfileMenuController;
import com.TillDawn.Model.Enums.Assets.Tiles;
import com.TillDawn.Model.Enums.GunType;
import com.TillDawn.Model.Enums.Hero;
import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.App;
import com.TillDawn.Model.AudioManager;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Service.SaveLoadService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.ArrayList;
import java.util.Arrays;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TillDawn extends com.badlogic.gdx.Game {
    private static TillDawn tillDawn;
    private static SpriteBatch batch;

    public static ProfileMenuController unrealController;


    @Override
    public void create() {
        tillDawn = this;
        batch = new SpriteBatch();

        ShaderProgram.pedantic = false;
        GameAssetManager.getInstance().shader = new ShaderProgram(
            Gdx.files.internal("shaders/grayscale.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );

        if (!GameAssetManager.getInstance().shader.isCompiled()) {
            System.err.println("Shader error: " + GameAssetManager.getInstance().shader.getLog());
        }


        SaveLoadService.loadApp();
        if (App.getInstance().getCurrentUser() != null) {
            setBlackWhiteMode(App.getInstance().getCurrentUser().getSettingData().isBlackWhiteEnabled());
        }
        AudioManager.playMusic(GameAssetManager.getInstance().defaultPreGameMusicPath);
        if (App.getInstance().getCurrentUser() != null) {
            App.getInstance().getCurrentUser().getSettingData().loadSettings();
        }

        Pixmap pixmap = new Pixmap(Gdx.files.internal("textures/Cursor/T_Cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2);
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();
    tillDawn.setScreen(Screens.StartMenu.getScreen());
    }

    public void setBlackWhiteMode(boolean enabled) {
        batch.setShader(enabled ? GameAssetManager.getInstance().shader : null);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();

    }

    public static TillDawn getTillDawn() {
        return tillDawn;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
    public static void setBatch(SpriteBatch batch) {
        TillDawn.batch = batch;
    }
}
