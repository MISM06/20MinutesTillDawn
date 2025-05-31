package com.TillDawn.Controller;

import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.App;
import com.TillDawn.Model.User;
import com.TillDawn.TillDawn;
import com.TillDawn.View.ScoreboardMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ScoreboardMenuController {
    ScoreboardMenu view;

    ArrayList<ArrayList<String>> board;

    public void setView(ScoreboardMenu view) {
        this.view = view;
    }

    public void makeBoard(String sortBy) {
        board = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>(Arrays.asList("Rank", "Username", "kills", "score", "maxLivingTime"));
        board.add(row);
        ArrayList<User> users = new ArrayList<>(App.getInstance().getRegisteredUsers());
        switch (sortBy) {
            case "username":
                users.sort(Comparator.comparing(User::getName));
                break;
            case "kill":
                users.sort(Comparator.comparing(u -> -u.getKills()));
                break;
            case "living time":
                users.sort(Comparator.comparing(u -> -u.getLiveTime()));
                break;
            default:
                users.sort(Comparator.comparing(u -> -u.getScore()));
        }
        int r = 0;
        for (User user : users) {
            ++r;
            row = new ArrayList<>(Arrays.asList(String.valueOf(r), user.getName(),
                user.getKills() + "", user.getScore() + "", user.getLiveTime() + "s"));
            board.add(row);
            if (r >= 10) break;
        }
    }

    public ArrayList<ArrayList<String>> getBoard() {
        return board;
    }

    public void back() {
        view.dispose();
        TillDawn.getTillDawn().setScreen(Screens.MainMenu.getScreen());
    }

    public void handle() {

    }
}
