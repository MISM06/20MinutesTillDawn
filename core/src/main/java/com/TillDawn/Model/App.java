package com.TillDawn.Model;

import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Service.AppService;
import com.TillDawn.Service.SaveLoadService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class App {
    @JsonIgnore
    private static App instance;
    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }
    public AppService appService() {
        return new AppService(this);
    }

    public App() {
    }

    private ArrayList<User> registeredUsers = new ArrayList<>();
    private User currentUser = null;
    private boolean stayLoggedIn = false;
    @JsonIgnore
    private Screens currentMenu;

    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public Screens getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Screens currentMenu) {
        this.currentMenu = currentMenu;
    }
}
