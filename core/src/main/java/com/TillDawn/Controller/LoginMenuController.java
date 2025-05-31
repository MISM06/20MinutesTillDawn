package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.App;
import com.TillDawn.Model.Result;
import com.TillDawn.Model.User;
import com.TillDawn.TillDawn;
import com.TillDawn.View.LoginMenu;

public class LoginMenuController {
    LoginMenu view;

    public void setView(LoginMenu view) {
        this.view = view;
    }

    public void handle() {
        if (view == null) return;
        if (view.getBackButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.RegisterMenu.getScreen());
        }
        else if (view.getLoginButton().isChecked()) {
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText();
            Result<String> userCheck = usernameCheck(username);
            Result<String> passCheck = passwordCheck(username, password);
            view.getPasswordError().setText(passCheck.data);
            view.getUsernameError().setText(userCheck.data);
            if (userCheck.success && passCheck.success) {
                User user = App.getInstance().appService().getUserByName(username);
                App.getInstance().setCurrentUser(user);
                view.dispose();
                if (view.getStayLoggedIn().isChecked()) {
                    App.getInstance().setStayLoggedIn(true);
                } else {
                    App.getInstance().setStayLoggedIn(false);
                }
                TillDawn.getTillDawn().setScreen(Screens.MainMenu.getScreen());
//                App.getInstance().appService().closeApp();
            }
            view.getLoginButton().setChecked(false);
        }
        else if (view.getForgetPasswordButton().isChecked()) {
            view.dispose();
            TillDawn.getTillDawn().setScreen(Screens.ForgetPasswordMenu.getScreen());
        }
    }

    public Result<String> usernameCheck(String username) {
        if (username.isEmpty()) return new Result<>(false, "This field is necessary");
        if (App.getInstance().appService().getUserByName(username) == null)
            return new Result<>(false, "Username does not exist");
        return new Result<>(true, "");
    }
    public Result<String> passwordCheck(String username, String password) {
        if (password.isEmpty()) return new Result<>(false, "This field is necessary");
        if (!usernameCheck(username).success) {
            return new Result<>(false, "");
        }
        User user = App.getInstance().appService().getUserByName(username);
        if (user.isPasswordCorrect(password)) {
            return new Result<>(true, "");
        }
        return new Result<>(false, "incorrect password");
    }
}
