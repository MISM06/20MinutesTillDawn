package com.TillDawn.Model;

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



}
