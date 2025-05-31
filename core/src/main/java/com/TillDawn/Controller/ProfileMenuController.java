package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Regexes;
import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.App;
import com.TillDawn.Model.Result;
import com.TillDawn.TillDawn;
import com.TillDawn.View.GameView;
import com.TillDawn.View.ProfileMenu;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class ProfileMenuController {
    ProfileMenu view;

    public void setView(ProfileMenu view) {
        TillDawn.unrealController = this;
        this.view = view;
    }

    public void onFileDropped(FileHandle fileHandle) {
        if (fileHandle != null && fileHandle.exists()) {
            App.getInstance().getCurrentUser().setOutsideAvatarPath(fileHandle.path());
        }
    }


    public void handle() {

    }

    public void setAvatar(String path, boolean outSide) {
        App.getInstance().getCurrentUser().setAvatarPath(path);
    }

    public void back() {
        view.dispose();
        TillDawn.getTillDawn().setScreen(Screens.MainMenu.getScreen());
    }

    public void changeUsername() {
        Result<String> res;
        String username = view.getUserChangeTextField().getText();
        if (username.isEmpty()) {
            res = new Result<>(false, "Please enter a username");
        }
        else if (App.getInstance().appService().getUserByName(username) != null) {
            res = new Result<>(false, "Username already exists");
        }
        else if (username.length() < 4) {
            res = new Result<>(false, "username must be at least 4 characters");
        }
        else if (Regexes.UsernameFormat.getMatcher(username) == null) {
            res =  new Result<>(false, "username should contains letters and digits");
        }
        else res = new Result<>(true, "username changed to: " + username + " successfully");
        view.getUserChangeError().setText(res.data);
        if (res.success) {
            App.getInstance().getCurrentUser().setName(username);
        }
    }

    public void changePassword() {
        Result<String> res;
        String password = view.getPassChangeTextField().getText();
        if (password.isEmpty()) {
            res = new Result<>(false, "Please enter a password");
        }
        else if (password.length() < 8)
            res = new Result<>(false, "password must be at least 8 characters");
        else if (Regexes.PasswordStrong.getMatcher(password) == null) {
            res = new Result<>(false, "password is weak");
        }
        else res = new Result<>(true, "password changed successfully");
        view.getPassChangeError().setText(res.data);
        if (res.success) {
            App.getInstance().getCurrentUser().setPassword(password);
        }
    }

}
