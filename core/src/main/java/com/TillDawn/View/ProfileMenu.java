package com.TillDawn.View;

import com.TillDawn.Controller.ProfileMenuController;
import com.TillDawn.Controller.RegisterMenuController;
import com.TillDawn.Model.App;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonString;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;
import java.io.File;

public class ProfileMenu implements Screen {
    private Stage stage;
    private ProfileMenuController controller;
    private Skin skin;
    private Table table;

    private Label title;

    private ImageButton[] avatarButtons;
    private Table avatarTable;

    private TextButton backButton;

    private Label userChangeLabel;
    private TextField userChangeTextField;
    private TextButton userChangeButton;
    private Label userChangeError;

    private Label passChangeLabel;
    private TextField passChangeTextField;
    private TextButton passChangeButton;
    private Label passChangeError;


    public ProfileMenu(ProfileMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
//        stage = new Stage();

        table = new Table(skin);
        avatarTable = new Table(skin);
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), TillDawn.getBatch());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        avatarTable.center();
        stage.addActor(table);

        avatarButtons = new ImageButton[GameAssetManager.getInstance().getDefaultAvatars().size()];
        for (int i = 0; i < GameAssetManager.getInstance().getDefaultAvatars().size(); i++) {
            final int index = i;
            Texture avatarTexture = GameAssetManager.getInstance().cropToCircle(new Texture(Gdx.files.internal(GameAssetManager.getInstance().getDefaultAvatars().get(index))));

            TextureRegionDrawable baseDrawable = new TextureRegionDrawable(new TextureRegion(avatarTexture));
            TextureRegionDrawable overDrawable = new TextureRegionDrawable(new TextureRegion(avatarTexture));
            TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(avatarTexture));

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = baseDrawable;
            style.imageOver = overDrawable.tint(new Color(1f, 1f, 0.7f, 1f));  // Yellowish hover;
            style.imageDown = downDrawable.tint(new Color(0.7f, 0.7f, 1f, 1f)); // Bluish click;
            style.imageChecked = new TextureRegionDrawable(new TextureRegion(avatarTexture)).tint(new Color(1f, 0f, 0f, 1f));

            ImageButton button = new ImageButton(style);
            avatarButtons[index] = button;

            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    controller.setAvatar(GameAssetManager.getInstance().getDefaultAvatars().get(index), false);
                    for (ImageButton avatarButton : avatarButtons) {
                        avatarButton.setChecked(false);
                    }
                    button.setChecked(true);
                    App.getInstance().getCurrentUser().setOutsideAvatarPath("");
                }
            });

            avatarTable.add(button).size(150).pad(10);
            if ((i + 1) % 4 == 0) avatarTable.row();
            if (App.getInstance().getCurrentUser().getAvatarPath().equals(GameAssetManager.getInstance().getDefaultAvatars().get(index))) {
                button.setChecked(true);
            }
        }

        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.back();
            }
        });

        title = new Label("Profile", skin);
        title.setFontScale(2f);
        title.setColor(0.6f, 0, 0, 1);

        table.add(title).colspan(4).center().padBottom(50).row();

        table.add(avatarTable).colspan(4).center().padBottom(10).row();

        TextButton uploadButton = new TextButton("Upload image", skin);
        uploadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FileDialog dialog = new FileDialog((Frame) null);
                dialog.setMode(FileDialog.LOAD);
                dialog.setVisible(true);
                if (dialog.getFile() != null) {
                    File file = new File(dialog.getDirectory(), dialog.getFile());
                    App.getInstance().getCurrentUser().setOutsideAvatarPath(file.getAbsolutePath());
                }
            }
        });
        table.add(uploadButton).colspan(4).pad(10).row();

        userChangeLabel = new Label("new username: ", skin);
        userChangeTextField = new TextField("", skin);
        userChangeButton = new TextButton("apply", skin);
        userChangeButton.getLabel().setFontScale(0.4f);
        userChangeButton.pack();
        userChangeError = new Label("", skin);
        userChangeError.setFontScale(0.9f);
        userChangeError.setColor(0.5f, 0.5f, 0.5f, 0.9f);
        userChangeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.changeUsername();
            }
        });

        passChangeLabel = new Label("new password: ", skin);
        passChangeTextField = new TextField("", skin);
        passChangeButton = new TextButton("apply", skin);
        passChangeButton.getLabel().setFontScale(0.4f);
        passChangeButton.pack();
        passChangeError = new Label("", skin);
        passChangeError.setFontScale(0.9f);
        passChangeError.setColor(0.5f, 0.5f, 0.5f, 0.9f);
        passChangeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.changePassword();
            }
        });

        Table userTable = new Table(skin);
        userTable.center();
        userTable.add(userChangeLabel).right().pad(10);
        userTable.add(userChangeTextField).width(350).left().pad(10);
        userTable.add(userChangeButton).height(80).center().pad(10);
        userTable.add(userChangeError).right().pad(10);
        userTable.row();

        userTable.add(passChangeLabel).right().pad(10);
        userTable.add(passChangeTextField).width(350).left().pad(10);
        userTable.add(passChangeButton).height(80).center().pad(10);
        userTable.add(passChangeError).right().pad(10);

        table.add(userTable).colspan(4).center().row();

        backButton.getLabel().setFontScale(0.6f);
        backButton.pack();
        table.add(backButton).colspan(4).center().padBottom(10).row();

//        if (TillDawn.getTillDawn().getDragAndDropHandler() != null) {
//            TillDawn.getTillDawn().getDragAndDropHandler().registerFileDropListener(path -> {
//                Gdx.app.postRunnable(() -> {
//                    App.getInstance().getCurrentUser().setOutsideAvatarPath(path);
//                    System.out.println("yeeee");
//                    Gdx.app.log("DragDrop", "Dropped: " + path);
//                });
//            });
//        }

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

    public Label getUserChangeLabel() {
        return userChangeLabel;
    }

    public TextField getUserChangeTextField() {
        return userChangeTextField;
    }

    public TextButton getUserChangeButton() {
        return userChangeButton;
    }

    public Label getUserChangeError() {
        return userChangeError;
    }

    public Label getPassChangeLabel() {
        return passChangeLabel;
    }

    public TextField getPassChangeTextField() {
        return passChangeTextField;
    }

    public TextButton getPassChangeButton() {
        return passChangeButton;
    }

    public Label getPassChangeError() {
        return passChangeError;
    }
}
