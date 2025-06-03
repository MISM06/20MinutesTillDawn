package com.TillDawn.Controller;

import com.TillDawn.Model.App;
import com.TillDawn.Model.AudioManager;
import com.TillDawn.Model.Enums.GunType;
import com.TillDawn.Model.Enums.Hero;
import com.TillDawn.Model.Enums.Screens;
import com.TillDawn.Model.GameStuff.ChunkManager;
import com.TillDawn.Model.GameStuff.Game;
import com.TillDawn.Model.GameStuff.Gun;
import com.TillDawn.Model.GameStuff.Player;
import com.TillDawn.Model.User;
import com.TillDawn.TillDawn;
import com.TillDawn.View.PreGameMenu;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PreGameMenuController {
    PreGameMenu view;

    private int selectedHero = 0;
    private int selectedGun = 0;

    public void setView(PreGameMenu view) {
        this.view = view;
    }

    public void handle() {

    }

    public void enterNewGame() {
        Game game = Game.getInstance();
        User user = App.getInstance().getCurrentUser();

        Player player = new Player(user.getId());
        player.setHeroType(getSelectedHero());
        player.setX(0);
        player.setY(0);
        Gun gun = new Gun(getSelectedGun());
        gun.setAutoReload(user.getSettingData().isAutoReloadEnabled());
        gun.setAutoAim(user.getSettingData().isAutoAimEnabled());
        player.setGun(gun);
        game.setPlayer(player);
        game.initMap();
//        AudioManager.stopMusic();
        switch (view.getGameDurationSelector().getSelected()) {
            case "10 minutes":
                game.setTimeDuration(10 * 60);
                game.setTimeLeft(10 * 60);
                break;
            case "5 minutes":
                game.setTimeDuration(5 * 60);
                game.setTimeLeft(5 * 60);
                break;
            case "2 minutes":
                game.setTimeDuration(2 * 60);
                game.setTimeLeft(2 * 60);
                break;
            default:
                game.setTimeDuration(20 * 60);
                game.setTimeLeft(20 * 60);
        }
        view.dispose();
        TillDawn.getTillDawn().setScreen(Screens.GameView.getScreen());
    }

    public void back() {
        view.dispose();
        TillDawn.getTillDawn().setScreen(Screens.MainMenu.getScreen());
    }

    public void setHero(int ind) {
        selectedHero = ind;
        view.getPortrait().setDrawable(new TextureRegionDrawable(getSelectedHero().getPortraitRegion()));
        view.getHeroName().setText(getSelectedHero().toString());
        view.getHeroDet()[0].setText("Hp: " + getSelectedHero().getHp());
        view.getHeroDet()[1].setText("Speed: " + getSelectedHero().getSpeed());
    }
    public void setGun(int ind) {
        selectedGun = ind;
    }

    public Hero getSelectedHero() {
        return Hero.values()[selectedHero];
    }

    public GunType getSelectedGun() {
        return GunType.values()[selectedGun];
    }
}
