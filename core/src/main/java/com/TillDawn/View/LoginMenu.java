package com.TillDawn.View;

import com.TillDawn.Controller.LoginMenuController;
import com.TillDawn.Controller.RegisterMenuController;
import com.TillDawn.Model.App;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginMenu implements Screen {
    private Stage stage;
    private LoginMenuController controller;
    private Skin skin;
    private Table table;

    private Label title;

    private Label usernameLabel;
    private TextField usernameField;
    private Label usernameError;

    private Label passwordLabel;
    private TextField passwordField;
    private Label passwordError;

    private TextButton loginButton;
    private TextButton backButton;
    private TextButton forgetPasswordButton;

    private CheckBox stayLoggedIn;

    public LoginMenu(LoginMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
//        stage = new Stage();

        title = new Label("Login to your Account", skin);
        title.setFontScale(2f);
        title.setColor(0.6f, 0, 0, 1);

        usernameLabel = new Label("Username:", skin);
        usernameField = new TextField("", skin);
        usernameError = new Label("", skin);
        usernameError.setFontScale(0.9f);
        usernameError.setColor(0.5f, 0.5f, 0.5f, 0.9f);

        passwordLabel = new Label("Password:", skin);
        passwordField = new TextField("", skin);
        passwordError = new Label("", skin);
        passwordError.setFontScale(0.9f);
        passwordError.setColor(0.5f, 0.5f, 0.5f, 0.9f);

        loginButton = new TextButton("Login", skin);
        backButton = new TextButton("Back", skin);
        forgetPasswordButton = new TextButton("Forget Password", skin);

        stayLoggedIn = new CheckBox("Stay Logged in", skin);

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

        table.add(title).colspan(2).padBottom(50).row();

        table.add(usernameLabel).right().padBottom(15).padRight(10);
        table.add(usernameField).left().width(350).padBottom(15);
        table.add(usernameError).left().padBottom(15);

        table.row();

        table.add(passwordLabel).right().padRight(10).padBottom(15);
        table.add(passwordField).left().width(350).padBottom(15).padRight(10);
        table.add(passwordError).left().padBottom(15);

        table.row();

        table.add(stayLoggedIn).colspan(2).padBottom(20).row();

        loginButton.getLabel().setFontScale(0.6f);
        loginButton.pack();
        table.add(loginButton).colspan(2).padTop(20).row();

        forgetPasswordButton.getLabel().setFontScale(0.6f);
        forgetPasswordButton.pack();
        table.add(forgetPasswordButton).colspan(2).padTop(20).row();

        backButton.getLabel().setFontScale(0.6f);
        backButton.pack();
        table.add(backButton).colspan(2).padTop(20).row();

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

    public Label getTitle() {
        return title;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public Label getUsernameError() {
        return usernameError;
    }

    public Label getPasswordLabel() {
        return passwordLabel;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Label getPasswordError() {
        return passwordError;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getForgetPasswordButton() {
        return forgetPasswordButton;
    }

    public CheckBox getStayLoggedIn() {
        return stayLoggedIn;
    }
}
