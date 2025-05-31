package com.TillDawn.View;

import com.TillDawn.Controller.StartMenuController;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class StartMenu implements Screen {
    private final StartMenuController controller;
    private Stage stage;
    private final Label gameTile;
    private final Label message;
    private Table table;
    private final Skin skin;
    private int lastKeyPressed = -1;

    public StartMenu(StartMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
        gameTile = new Label("20 Minutes till Dawn", skin);
        gameTile.setColor(0.6f, 0, 0, 1);
        gameTile.setFontScale(3f);
        message = new Label("press any key to continue", skin);
        message.setColor(1f, 1f, 1f, 0.5f);
        message.addAction(forever(sequence(fadeOut(1f), fadeIn(1f))));
        table = new Table();

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), TillDawn.getBatch());
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            public boolean keyDown(int keycode) {
                lastKeyPressed = keycode;
                return true;
            }
        }));

        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        table.add(gameTile);
        table.row().pad(20, 0 , 0, 0);
        table.add(message);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        TillDawn.getBatch().begin();
        TillDawn.getBatch().end();
        stage.act(delta);
        stage.draw();
        controller.handleKeyPress(lastKeyPressed);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update( width, height, true );
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Label getGameTile() {
        return gameTile;
    }

    public Label getMessage() {
        return message;
    }

    public Table getTable() {
        return table;
    }

    public int getLastKeyPressed() {
        return lastKeyPressed;
    }
}
