package com.TillDawn.View;

import com.TillDawn.Controller.RegisterMenuController;
import com.TillDawn.Controller.ScoreboardMenuController;
import com.TillDawn.Model.App;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.swing.table.TableColumn;
import java.util.ArrayList;

public class ScoreboardMenu implements Screen {
    private Stage stage;
    private ScoreboardMenuController controller;
    private Skin skin;
    private Table table;
    private TextButton backButton;
    private SelectBox<String> sortBySelectBox;

    public ScoreboardMenu(ScoreboardMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
//        stage = new Stage();

        table = new Table(skin);
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), TillDawn.getBatch());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Drawable hLine = skin.newDrawable("white", Color.LIGHT_GRAY);
        Drawable vLine = skin.newDrawable("white", Color.LIGHT_GRAY);

        Table page = new Table(skin);
        page.center();
        controller.makeBoard("");
        ArrayList<ArrayList<String>> board = controller.getBoard();
        int n = board.size();
        int m = board.get(0).size();
        page.add(new Image(hLine)).colspan(board.get(0).size()).fillX().height(1).padBottom(5);
        page.row();
        for (int i = 0; i < board.size(); i++) {
            Table row = new Table(skin);
            if (i == 0) {
                for (String s : board.get(i)) {
                    Label label = new Label(s, skin);
                    label.setColor(0.6f, 0, 0, 1);
                    label.setFontScale(1.1f);
                    page.add(label).padRight(20);
                }
//                row.setColor(0.6f, 0, 0, 1);
//                page.add(row).colspan(board.get(i).size()).padBottom(5).row();
                page.padBottom(5).row();
            } else {
                int j = 0;
                for (String s : board.get(i)) {
                    Label label = new Label(s, skin);
                    label.setFontScale(1f);
                    float tr = 1;
                    if (i == 1) {
                        label.setColor(1f, 0.843f, 0, tr);
                    } else if (i == 2) {
                        label.setColor(0.75f, 0.75f, 0.75f, tr);
                    } else if (i == 3) {
                        label.setColor(0.803f, 0.521f, 0.247f, tr);
                    } else {
                        label.setColor(0, 1, 1, tr);
                    }
                    if (board.get(i).get(1).equals(App.getInstance().getCurrentUser().getName())) {
                        if (j == 1) label.setColor(0, 0, 1, tr);
                    }
                    page.add(label).padRight(20);
                    ++j;
                }
                page.padBottom(5).row();
            }
            page.add(new Image(hLine)).colspan(board.get(i).size()).fillX().height(1).padBottom(5);
            page.row();
        }
//        for (int j = 0; j < m; j++) {
//            Table column = new Table(skin);
//            for (int i = 0; i < n; i++) {
//                column.add(board.get(i).get(j)).pad(5).center();
//                column.row();
//            }
//            page.add(column);
//            column = new Table(skin);
//            for (int i = 0; i < n; i++) {
//                column.add(new Image(hLine)).width(1).height(20).row();
//            }
//            page.add(column);
//        }

        sortBySelectBox = new SelectBox(skin);
        sortBySelectBox.setItems("score", "username", "kill", "living time");
        sortBySelectBox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                page.clear();
                page.center();
                controller.makeBoard(sortBySelectBox.getSelected());
                ArrayList<ArrayList<String>> board = controller.getBoard();
                int n = board.size();
                int m = board.get(0).size();
                page.add(new Image(hLine)).colspan(board.get(0).size()).fillX().height(1).padBottom(5);
                page.row();
                for (int i = 0; i < board.size(); i++) {
                    Table row = new Table(skin);
                    if (i == 0) {
                        for (String s : board.get(i)) {
                            Label label = new Label(s, skin);
                            label.setColor(0.6f, 0, 0, 1);
                            label.setFontScale(1.1f);
                            page.add(label).padRight(20);
                        }
//                row.setColor(0.6f, 0, 0, 1);
//                page.add(row).colspan(board.get(i).size()).padBottom(5).row();
                        page.padBottom(5).row();
                    } else {
                        int j = 0;
                        for (String s : board.get(i)) {
                            Label label = new Label(s, skin);
                            label.setFontScale(1f);
                            float tr = 1;
                            if (i == 1) {
                                label.setColor(1f, 0.843f, 0, tr);
                            } else if (i == 2) {
                                label.setColor(0.75f, 0.75f, 0.75f, tr);
                            } else if (i == 3) {
                                label.setColor(0.803f, 0.521f, 0.247f, tr);
                            } else {
                                label.setColor(0, 1, 1, tr);
                            }
                            if (board.get(i).get(1).equals(App.getInstance().getCurrentUser().getName())) {
                                if (j == 1) label.setColor(0, 0, 1, tr);
                            }
                            page.add(label).padRight(20);
                            ++j;
                        }
                        page.padBottom(5).row();
                    }
                    page.add(new Image(hLine)).colspan(board.get(i).size()).fillX().height(1).padBottom(5);
                    page.row();
                }
            }
        });


        table.add(page).padBottom(20).row();
        table.add(sortBySelectBox).padBottom(20).row();
        backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(0.6f);
        backButton.pack();
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.back();
            }
        });
        table.add(backButton).padBottom(20).center().row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        TillDawn.getBatch().begin();
        TillDawn.getBatch().end();
        stage.act(delta);
        stage.draw();
        controller.handle();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update( i, i1, true );
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

    public Table getTable() {
        return table;
    }
}
