package com.TillDawn.View;

import com.TillDawn.Controller.ForgetPasswordController;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ForgetPasswordMenu implements Screen {
    private Stage stage;
    private ForgetPasswordController controller;
    private Skin skin;
    private Table table;

    private Label title;

    private Label usernameLabel;
    private TextField usernameField;
    private Label usernameError;

    private Label emailLabel;
    private TextField emailField;
    private Label emailError;

    private Label answerLabel;
    private TextField answerField;
    private Label answerError;


    private Label newPassLabel;
    private TextField newPassField;
    private Label newPassError;

    private Label confirmPassLabel;
    private TextField confirmPassField;
    private Label confirmPassError;

    private TextButton changePassButton;
    private TextButton backButton;
    private TextButton seeQuestionButton;

    public ForgetPasswordMenu(ForgetPasswordController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
//        stage = new Stage();

        title = new Label("Change your Password", skin);
        title.setFontScale(2f);
        title.setColor(0.6f, 0, 0, 1);

        usernameLabel = new Label("Username:", skin);
        usernameField = new TextField("", skin);
        usernameError = new Label("", skin);
        usernameError.setFontScale(0.9f);
        usernameError.setColor(0.5f, 0.5f, 0.5f, 0.9f);

        emailLabel = new Label("Email:", skin);
        emailField = new TextField("", skin);
        emailError = new Label("", skin);
        emailError.setFontScale(0.9f);
        emailError.setColor(0.5f, 0.5f, 0.5f, 0.9f);

        answerLabel = new Label("Security question answer:", skin);
        answerField = new TextField("", skin);
        answerError = new Label("", skin);
        answerError.setFontScale(0.9f);
        answerError.setColor(0.5f, 0.5f, 0.5f, 0.9f);

        newPassLabel = new Label("Password:", skin);
        newPassField = new TextField("", skin);
        newPassError = new Label("", skin);
        newPassError.setFontScale(0.9f);
        newPassError.setColor(0.5f, 0.5f, 0.5f, 0.9f);

        confirmPassLabel = new Label("confirm password:", skin);
        confirmPassField = new TextField("", skin);
        confirmPassError = new Label("", skin);
        confirmPassError.setFontScale(0.9f);
        confirmPassError.setColor(0.5f, 0.5f, 0.5f, 0.9f);

        changePassButton = new TextButton("Change Password", skin);
        backButton = new TextButton("Back", skin);
        seeQuestionButton = new TextButton("Show security question", skin);

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

        table.add(emailLabel).right().padBottom(15).padRight(10);
        table.add(emailField).left().width(350).padBottom(15);
        table.add(emailError).left().padBottom(15);

        table.row();

        table.add(answerLabel).right().padBottom(15).padRight(10);
        table.add(answerField).left().width(350).padBottom(15);
        table.add(answerError).left().padBottom(15);

        table.row();

        table.add(newPassLabel).right().padBottom(15).padRight(10);
        table.add(newPassField).left().width(350).padBottom(15);
        table.add(newPassError).left().padBottom(15);

        table.row();

        table.add(confirmPassLabel).right().padBottom(20).padRight(10);
        table.add(confirmPassField).left().width(350).padBottom(20);
        table.add(confirmPassError).left().padBottom(20);

        table.row();

        changePassButton.getLabel().setFontScale(0.6f);
        changePassButton.pack();
        table.add(changePassButton).right();

        seeQuestionButton.getLabel().setFontScale(0.6f);
        seeQuestionButton.pack();
        table.add(seeQuestionButton).center().padRight(15).padLeft(15);
        seeQuestionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onSeeQuestionClicked();  // delegate to controller
            }
        });

        backButton.getLabel().setFontScale(0.6f);
        backButton.pack();
        table.add(backButton).left().row();


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

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public Label getUsernameError() {
        return usernameError;
    }

    public Label getEmailLabel() {
        return emailLabel;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public Label getEmailError() {
        return emailError;
    }

    public Label getAnswerLabel() {
        return answerLabel;
    }

    public TextField getAnswerField() {
        return answerField;
    }

    public Label getAnswerError() {
        return answerError;
    }

    public Label getNewPassLabel() {
        return newPassLabel;
    }

    public TextField getNewPassField() {
        return newPassField;
    }

    public Label getNewPassError() {
        return newPassError;
    }

    public Label getConfirmPassLabel() {
        return confirmPassLabel;
    }

    public TextField getConfirmPassField() {
        return confirmPassField;
    }

    public Label getConfirmPassError() {
        return confirmPassError;
    }

    public TextButton getChangePassButton() {
        return changePassButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getSeeQuestionButton() {
        return seeQuestionButton;
    }
}
