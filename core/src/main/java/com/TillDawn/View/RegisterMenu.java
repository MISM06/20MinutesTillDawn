package com.TillDawn.View;

import com.TillDawn.Controller.RegisterMenuController;
import com.TillDawn.Model.Enums.SecurityQuestions;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RegisterMenu implements Screen {
    private Stage stage;
    private RegisterMenuController controller;
    private Skin skin;
    private Table table;

    private Label title;

    private Label emailFieldLabel;
    private Label passwordFieldLabel;
    private Label usernameFieldLabel;
    private Label securityQuestionLabel;
    private Label emailError;
    private Label passwordError;
    private Label usernameError;
    private Label securityQuestionError;
    private Label questionsSelectLabel;
    private TextField emailField;
    private TextField usernameField;
    private TextField passwordField;
    private TextField securityQuestionField;

    private SelectBox<SecurityQuestions> questionsSelectBox;

    private TextButton registerButton;
    private TextButton guestButton;
    private TextButton loginButton;
    private TextButton exitButton;

    public RegisterMenu(RegisterMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();

        title = new Label("Register your Account", skin);
        title.setFontScale(2f);
        title.setColor(0.6f, 0, 0, 1);

        emailFieldLabel = new Label("Email:", skin);
        passwordFieldLabel = new Label("Password:", skin);
        usernameFieldLabel = new Label("Username:", skin);
        securityQuestionLabel = new Label("Your answer:", skin);
        questionsSelectLabel = new Label("Choose a security qestion:", skin);

        emailError = new Label("", skin);
        emailError.setFontScale(0.9f);
        emailError.setColor(0.5f, 0.5f, 0.5f, 0.9f);
        passwordError = new Label("", skin);
        passwordError.setFontScale(0.9f);
        passwordError.setColor(0.5f, 0.5f, 0.5f, 0.9f);
        usernameError = new Label("", skin);
        usernameError.setFontScale(0.9f);
        usernameError.setColor(0.5f, 0.5f, 0.5f, 0.9f);
        securityQuestionError = new Label("", skin);
        securityQuestionError.setFontScale(0.9f);
        securityQuestionError.setColor(0.5f, 0.5f, 0.5f, 0.9f);

        emailField = new TextField("", skin);
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        securityQuestionField = new TextField("", skin);

        questionsSelectBox = new SelectBox<>(skin);
        questionsSelectBox.setItems(SecurityQuestions.values());

        registerButton = new TextButton("Register", skin);
        guestButton = new TextButton("Continue as guest", skin);
        loginButton = new TextButton("Login menu", skin);
        exitButton = new TextButton("Exit", skin);

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

        table.add(title).colspan(2).padBottom(50).row();

        table.add(emailFieldLabel).right().padBottom(15).padRight(10);
        table.add(emailField).left().width(350).padBottom(15);
        table.add(emailError).left().padBottom(15);

        table.row();

        table.add(usernameFieldLabel).right().padRight(10).padBottom(15);
        table.add(usernameField).left().width(350).padBottom(15).padRight(10);
        table.add(usernameError).left().padBottom(15);

        table.row();

        table.add(passwordFieldLabel).right().padRight(10).padBottom(15);
        table.add(passwordField).left().width(350).padBottom(15).padRight(10);
        table.add(passwordError).left().padBottom(15);

        table.row();

        table.add(questionsSelectLabel).right().padRight(10).padBottom(15);
        table.add(questionsSelectBox).left().width(350).padBottom(15);

        table.row();

        table.add(securityQuestionLabel).right().padRight(10).padBottom(20);
        table.add(securityQuestionField).left().width(350).padBottom(20).padRight(10);
        table.add(securityQuestionError).left().padBottom(20);

        table.row();

        loginButton.getLabel().setFontScale(0.6f);
        loginButton.pack();
        table.add(loginButton).right().padBottom(20);

        guestButton.getLabel().setFontScale(0.6f);
        guestButton.pack();
        table.add(guestButton).left().padBottom(20);

        table.row();

        registerButton.getLabel().setFontScale(0.6f);
        registerButton.pack();
//        table.add(registerButton).colspan(2).center().padBottom(20);

        table.row();
        table.add(exitButton).right();
        exitButton.getLabel().setFontScale(0.7f);
        exitButton.pack();
        table.add(registerButton).left().bottom();

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

    public Label getEmailFieldLabel() {
        return emailFieldLabel;
    }

    public Label getPasswordFieldLabel() {
        return passwordFieldLabel;
    }

    public Label getUsernameFieldLabel() {
        return usernameFieldLabel;
    }

    public Label getEmailError() {
        return emailError;
    }

    public Label getPasswordError() {
        return passwordError;
    }

    public Label getUsernameError() {
        return usernameError;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextButton getGuestButton() {
        return guestButton;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public Label getSecurityQuestionLabel() {
        return securityQuestionLabel;
    }

    public Label getSecurityQuestionError() {
        return securityQuestionError;
    }

    public Label getQuestionsSelectLabel() {
        return questionsSelectLabel;
    }

    public TextField getSecurityQuestionField() {
        return securityQuestionField;
    }

    public SelectBox<SecurityQuestions> getQuestionsSelectBox() {
        return questionsSelectBox;
    }

}
