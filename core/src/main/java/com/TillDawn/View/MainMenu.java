package com.TillDawn.View;

import com.TillDawn.Controller.MainMenuController;
import com.TillDawn.Model.App;
import com.TillDawn.Model.AudioManager;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Model.User;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

public class MainMenu implements Screen {

    private Stage stage;
    private MainMenuController controller;
    private Skin skin;
    private Table table;
    private Table buttonsTable;

    private Label title;

    private TextButton profileButton;
    private TextButton settingsButton;
    private TextButton exitButton;
    private TextButton preGameButton;
    private TextButton scoreBoardButton;
    private TextButton talentButton;

    private TextButton continueGameButton;
    private TextButton logoutButton;

    private Texture avatarTexture;
    private Label usernameLabel;
    private Label scoreLabel;

    private Image avatarImage;

    public MainMenu(MainMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
//        stage = new Stage();

        if (App.getInstance().getCurrentUser().getSettingData().isMusicEnabled()) {
            AudioManager.resumeMusic();
        }

        table = new Table(skin);
        buttonsTable = new Table(skin);
        controller.setView(this);

        title = new Label("Main menu", skin);
        title.setFontScale(2f);
        title.setColor(0.6f, 0, 0, 1);

        profileButton = new TextButton("Profile", skin);
        settingsButton = new TextButton("Setting", skin);
        exitButton = new TextButton("Exit", skin);
        preGameButton = new TextButton("New Game", skin);
        scoreBoardButton = new TextButton("Scoreboard", skin);
        talentButton = new TextButton("Talent", skin);
        continueGameButton = new TextButton("Continue Game", skin);
        logoutButton = new TextButton("Logout", skin);

        avatarTexture = App.getInstance().getCurrentUser().getAvatarTexture();
//        avatarTexture = GameAssetManager.getInstance().getCircularAvatar(avatarTexture);
        avatarImage = new Image(avatarTexture);
        User user = App.getInstance().getCurrentUser();
        usernameLabel = new Label(user.getName(), skin);
        usernameLabel.setFontScale(1.2f);
        usernameLabel.setColor(1f, 0f, 1f, 1);
        scoreLabel = new Label("Score: " + user.getScore(), skin);
        scoreLabel.setFontScale(1f);
        scoreLabel.setColor(1f, 1f, 0f, 1);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), TillDawn.getBatch());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        table.add(title).colspan(2).padBottom(50).row();
        setAvatar(GameAssetManager.getInstance().cropToCircle(avatarTexture));
        table.add(avatarImage).size(300).row();
        table.add(usernameLabel).padTop(10).row();
        table.add(scoreLabel).padBottom(20).row();

        preGameButton.getLabel().setFontScale(0.6f);
        preGameButton.pack();
        buttonsTable.add(preGameButton).colspan(3).padTop(10).right();

        continueGameButton.getLabel().setFontScale(0.6f);
        continueGameButton.pack();
        buttonsTable.add(continueGameButton).padTop(10).center();
        continueGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.loadGame();
            }
        });

        profileButton.getLabel().setFontScale(0.6f);
        profileButton.pack();
        buttonsTable.add(profileButton).padTop(10).left().row();

        talentButton.getLabel().setFontScale(0.6f);
        talentButton.pack();
        buttonsTable.add(talentButton).colspan(3).padTop(10).right();

        scoreBoardButton.getLabel().setFontScale(0.6f);
        scoreBoardButton.pack();
        buttonsTable.add(scoreBoardButton).padTop(10).center();

        settingsButton.getLabel().setFontScale(0.6f);
        settingsButton.pack();
        buttonsTable.add(settingsButton).padTop(10).left().row();

        exitButton.getLabel().setFontScale(0.6f);
        exitButton.pack();
        logoutButton.getLabel().setFontScale(0.6f);
        logoutButton.pack();
        buttonsTable.add(logoutButton).colspan(3).padTop(10).right();
        buttonsTable.add().center();
        buttonsTable.add(exitButton).padTop(10).left().row();

        table.add(buttonsTable).center();

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

    public TextButton getProfileButton() {
        return profileButton;
    }

    public TextButton getSettingsButton() {
        return settingsButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public TextButton getPreGameButton() {
        return preGameButton;
    }

    public TextButton getScoreBoardButton() {
        return scoreBoardButton;
    }

    public TextButton getTalentButton() {
        return talentButton;
    }

    public TextButton getContinueGameButton() {
        return continueGameButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }

    public void setAvatar(Texture avatar) {
        avatarImage.setDrawable(new TextureRegionDrawable(new TextureRegion(avatar)));
    }
}
