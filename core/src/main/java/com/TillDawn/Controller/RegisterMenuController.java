package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Regexes;
import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.*;
import com.TillDawn.TillDawn;
import com.TillDawn.View.RegisterMenu;

public class RegisterMenuController {
    RegisterMenu view;

    public void setView(RegisterMenu view) {
        this.view = view;
    }

    public void handle() {
        if (view == null) return;
        if (view.getExitButton().isChecked()) {
            view.dispose();
            App.getInstance().appService().closeApp();
        }
        else if (view.getRegisterButton().isChecked()) {
            Result<String> userCheck = usernameCheck(view.getUsernameField().getText());
            Result<String> emailCheck = emailCheck(view.getEmailField().getText());
            Result<String> passwordCheck = passwordCheck(view.getPasswordField().getText());
            Result<String> securityQuestionCheck = securityQuestionCheck(view.getSecurityQuestionField().getText());
            view.getUsernameError().setText(userCheck.data);
            view.getEmailError().setText(emailCheck.data);
            view.getPasswordError().setText(passwordCheck.data);
            view.getSecurityQuestionError().setText(securityQuestionCheck.data);

            if (userCheck.success && emailCheck.success && passwordCheck.success && securityQuestionCheck.success) {
                view.dispose();
                User user = new User(view.getUsernameField().getText(),
                    view.getPasswordField().getText(),
                    view.getEmailField().getText(),
                    new SecurityQuestion(view.getQuestionsSelectBox().getSelected(),
                        view.getSecurityQuestionField().getText()));
                user.setAvatarPath(GameAssetManager.getInstance().getRandomAvatar());
                App.getInstance().appService().addUser(user);
                TillDawn.getTillDawn().setScreen(Screens.LoginMenu.getScreen());
            }
            view.getRegisterButton().setChecked(false);
        }
        else if (view.getGuestButton().isChecked()) {
            view.dispose();
            User guest = App.getInstance().appService().getUserById(0);
            if (guest != null) {
                App.getInstance().getRegisteredUsers().remove(guest);
            }
            guest = new User("guest", "", "", null);
            guest.setAvatarPath(GameAssetManager.getInstance().getRandomAvatar());
            App.getInstance().appService().addUser(guest);
            TillDawn.getTillDawn().setScreen(Screens.LoginMenu.getScreen());
        }
        else if (view.getLoginButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.LoginMenu.getScreen());
        }
    }

    public Result<String> emailCheck(String email) {
        if (email.isEmpty()) return new Result<>(false, "this field is necessary");
        if (Regexes.EmailFormat.getMatcher(email) == null) {
            return new Result<>(false, "invalid email format");
        }
        if (App.getInstance().appService().getUserByEmail(email) != null) {
            return new Result<>(false, "this email is already registered");
        }
        return new Result<>(true, "");
    }

    public Result<String> passwordCheck(String password) {
        if (password.isEmpty()) return new Result<>(false, "this field is necessary");
        if (password.length() < 8)
            return new Result<>(false, "password must be at least 8 characters");
        if (Regexes.PasswordStrong.getMatcher(password) == null) {
            return new Result<>(false, "password is weak");
        }
        return new Result<>(true, "");
    }

    public Result<String> usernameCheck(String username) {
        if (username.isEmpty()) return new Result<>(false, "this field is necessary");
        if (username.length() < 4) {
            return new Result<>(false, "username must be at least 4 characters");
        }
        if (Regexes.UsernameFormat.getMatcher(username) == null) {
            return new Result<>(false, "username should contains letters and digits");
        }
        if (App.getInstance().appService().getUserByName(username) != null) {
            return new Result<>(false, "username is already registered");
        }
        return new Result<>(true, "");
    }

    public Result<String> securityQuestionCheck(String answer) {
        if (answer.isEmpty()) return new Result<>(false, "this field is necessary");
        return new Result<>(true, "");
    }
}
