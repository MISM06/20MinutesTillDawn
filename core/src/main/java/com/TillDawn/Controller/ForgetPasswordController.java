package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Regexes;
import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.App;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Model.Result;
import com.TillDawn.Model.User;
import com.TillDawn.TillDawn;
import com.TillDawn.View.ForgetPasswordMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class ForgetPasswordController {
    ForgetPasswordMenu view;

    public void setView(ForgetPasswordMenu view) {
        this.view = view;
    }

    public void handle() {
        if (view.getBackButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.LoginMenu.getScreen());
            return;
        }
        if (view.getChangePassButton().isChecked()) {
            String username = view.getUsernameField().getText();
            String password = view.getNewPassField().getText();
            String confirmPass = view.getConfirmPassField().getText();
            String email = view.getEmailField().getText();
            String answer = view.getAnswerField().getText();

            Result<String> userCheck = !username.isEmpty() ? new Result<>(false, "") : new Result<>(false, "this field is necessary");
            Result<String> confirmPassCheck = !confirmPass.isEmpty() ? new Result<>(false, "") : new Result<>(false, "this field is necessary");
            Result<String> emailCheck = !email.isEmpty() ? new Result<>(false, "") : new Result<>(false, "this field is necessary");
            Result<String> passwordCheck = !password.isEmpty() ? new Result<>(false, "") : new Result<>(false, "this field is necessary");
            Result<String> answerCheck = !answer.isEmpty() ? new Result<>(false, "") : new Result<>(false, "this field is necessary");

            User user = App.getInstance().appService().getUserByName(username);
            if (user == null) {
                userCheck = new Result<>(false, "Username doesn't exist");
                return;
            }
            if (!user.getEmail().equals(email)) {
                emailCheck = new Result<>(false, "Email doesn't match username");
            }
            if (!user.getSecurityQuestion().getAnswer().equalsIgnoreCase(answer)) {
                answerCheck = new Result<>(false, "Answer is incorrect");
            }

            if (password.length() < 8) {
                passwordCheck = new Result<>(false, "password must be at least 8 characters");
                return;
            } else if (Regexes.PasswordStrong.getMatcher(password) == null) {
                passwordCheck = new Result<>(false, "password is weak");
                return;
            }

            if (!password.equals(confirmPass)) {
                confirmPassCheck = new Result<>(false, "confirm password doesn't match new password");
                return;
            }

            user.setPassword(password);
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.LoginMenu.getScreen());
            return;
        }
    }

    public void onSeeQuestionClicked() {
        Dialog dialog = new Dialog("", GameAssetManager.getInstance().getSkin());
        User user = App.getInstance().appService().getUserByName(view.getUsernameField().getText());
        String meesage = "username does not exist";
        if (user != null) {
            meesage = user.getSecurityQuestion().getQuestion().getQuestion();
        }
        dialog.text(meesage);
        dialog.button("OK");
        dialog.show(view.getStage());
        Gdx.input.setInputProcessor(view.getStage());
    }
}
