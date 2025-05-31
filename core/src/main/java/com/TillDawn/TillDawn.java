package com.TillDawn;

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

//    private DragAndDropHandler dragAndDropHandler;

//    public void setDragAndDropHandler(DragAndDropHandler handler) {
//        this.dragAndDropHandler = handler;
//    }
//
//    public DragAndDropHandler getDragAndDropHandler() {
//        return dragAndDropHandler;
//    }

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
//        textures/tiles/forest/T_ForestTiles_0.png

//        System.out.println("Tiles:");
//        for (Tiles value : Tiles.values()) {
//            Texture tileTexture = value.getTileTexture();
//            int tileWidth = tileTexture.getWidth();
//            int tileHeight = tileTexture.getHeight();
//            System.out.println(value.toString() + ": " + tileWidth  + " , " + tileHeight);
//        }
//        System.out.println("Hero:");
//        for (Hero value : Hero.values()) {
//            System.out.println(value + ": ");
//            for (String path : value.getIdlePath()) {
//                Texture texture = new Texture(Gdx.files.internal(path));
//                int tileWidth = texture.getWidth();
//                int tileHeight = texture.getHeight();
//                System.out.println(path + ": " + tileWidth  + " , " + tileHeight);
//            }
//            for (String path : value.getRunPath()) {
//                Texture texture = new Texture(Gdx.files.internal(path));
//                int tileWidth = texture.getWidth();
//                int tileHeight = texture.getHeight();
//                System.out.println(path + ": " + tileWidth  + " , " + tileHeight);
//            }
//            System.out.println("-------------------------------------------");
//        }
//        System.out.println("Gun:");
//        for (GunType value : GunType.values()) {
//            System.out.println(value + ": ");
//            for (String path : value.getStillPath()) {
//                Texture texture = new Texture(Gdx.files.internal(path));
//                int tileWidth = texture.getWidth();
//                int tileHeight = texture.getHeight();
//                System.out.println(path + ": " + tileWidth  + " , " + tileHeight);
//            }
//            for (String path : value.getReloadPath()) {
//                Texture texture = new Texture(Gdx.files.internal(path));
//                int tileWidth = texture.getWidth();
//                int tileHeight = texture.getHeight();
//                System.out.println(path + ": " + tileWidth  + " , " + tileHeight);
//            }
//            System.out.println("-------------------------------------------");
//        }

        Pixmap pixmap = new Pixmap(Gdx.files.internal("textures/Cursor/T_Cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2);
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();
        tillDawn.setScreen(Screens.StartMenu.getScreen());
//        tillDawn.setScreen(Screens.GameView.getScreen());
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
