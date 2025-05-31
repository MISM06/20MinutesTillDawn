package com.TillDawn.View;

import com.TillDawn.Controller.PreGameMenuController;
import com.TillDawn.Controller.RegisterMenuController;
import com.TillDawn.Model.AnimatedImageButton;
import com.TillDawn.Model.Enums.Assets.Tiles;
import com.TillDawn.Model.Enums.GunType;
import com.TillDawn.Model.Enums.Hero;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreGameMenu implements Screen {
    private Stage stage;
    private PreGameMenuController controller;
    private Skin skin;
    private Table table;
    private Table choose;
    private Table show;
    private Table heroesTable;
    private Table gunsTable;

    private TextButton back;
    private Label title;
    private AnimatedImageButton[] heroes;
    private AnimatedImageButton[] guns;
    private Image portrait;

    private Label heroName;
    private Label[] heroDet;

    private Label gunName;
    private Label[] gunDet;

    private Label gameDuration;
    private SelectBox<String> gameDurationSelector;

    private TextButton startGame;

    public PreGameMenu(PreGameMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
//        stage = new Stage();

        table = new Table(skin);
        choose = new Table(skin);
        show = new Table(skin);

        back = new TextButton("Back", skin);
        back.getLabel().setFontScale(0.5f);
        back.pack();

        title = new Label("Pre Game", skin);
        title.setFontScale(3f);
        title.setColor(0.6f, 0, 0, 1);

        heroesTable = new Table(skin);
        gunsTable = new Table(skin);

        heroes = new AnimatedImageButton[Hero.values().length];
        guns = new AnimatedImageButton[GunType.values().length];

        TextureRegion buttonBackground = new TextureRegion(GameAssetManager.getInstance().cropToCircle(
            Tiles.Default.getTileTexture()));


        for (int i = 0; i < Hero.values().length; i++) {
            Animation<TextureRegion> idleAnim = new Animation<>(0.2f, Hero.values()[i].getIdleRegion());
            Animation<TextureRegion> runAnim = new Animation<>(0.1f, Hero.values()[i].getRunRegion());
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

            TextureRegionDrawable drawable =  new TextureRegionDrawable(buttonBackground);
            style.up = drawable;

            AnimatedImageButton button = new AnimatedImageButton(idleAnim, runAnim, style);
            heroes[i] = button;
            final int index = i;
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    controller.setHero(index);
                    for (AnimatedImageButton hero : heroes) {
                        hero.setChecked(false);
                    }
                    button.setChecked(true);
                }
            });

//            Table wrapper = new Table();
//            wrapper.setBackground(new TextureRegionDrawable(buttonBackground));
//            wrapper.add(button).size(300);
//            heroesTable.add(wrapper).size(110).pad(20);

            heroesTable.add(button).size(100).padRight(30);
        }

        heroes[0].setChecked(true);

        for (int i = 0; i < GunType.values().length; i++) {
            GunType value = GunType.values()[i];
            Animation<TextureRegion> stillAnim = new Animation<>(0.2f, value.getStillRegion());
            Array<TextureRegion> reloadRegion = new Array<>();
            for (TextureRegion textureRegion : value.getReloadRegion()) {
                reloadRegion.add(textureRegion);
            }
            for (int j = 0; j < 3; j++) {
                for (TextureRegion textureRegion : value.getStillRegion()) {
                    reloadRegion.add(textureRegion);
                }
            }
            Animation<TextureRegion> relAnim = new Animation<>(0.13f, reloadRegion);
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

            style.up = new TextureRegionDrawable(buttonBackground);

            AnimatedImageButton button = new AnimatedImageButton(stillAnim, relAnim, style);
            guns[i] = button;
            final int index = i;
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    controller.setGun(index);
                    for (AnimatedImageButton gun : guns) {
                        gun.setChecked(false);
                    }
                    button.setChecked(true);
                }
            });
            gunsTable.add(guns[i]).size(100).padRight(30);
        }

        guns[0].setChecked(true);

        heroName = new Label("Hero", skin);
        heroName.setFontScale(2f);
        heroName.setColor(0.6f, 0, 0, 1);
        heroDet = new Label[]{new Label("hp", skin), new Label("speed", skin)};
        for (Label det : heroDet) {
            det.setFontScale(0.9f);
            det.setColor(0.3f, 0, 0.6f, 1);
        }
//        gunName = new Label("Gun", skin);
//        gunDet = new Label[]{new Label("damage", skin), new Label("projectile", skin), new Label("max Ammo", skin), new Label("reload")};

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), TillDawn.getBatch());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.top();
        stage.addActor(table);


        back.setPosition(0, Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.back();
            }
        });

        table.add(title).colspan(9).top().center().padTop(20).padBottom(60).row();
        choose.add(heroesTable).colspan(Hero.values().length).left().padBottom(60).row();
        choose.add(gunsTable).colspan(GunType.values().length).left().padBottom(60).row();
        Table durationTable = new Table(skin);
        gameDuration = new Label("Survival time: ", skin);
        gameDuration.setFontScale(1.2f);
        gameDuration.setColor(0.5f, 0.5f, 0.5f, 1.0f);
        gameDurationSelector = new SelectBox<>(skin);
        gameDurationSelector.setItems("20 minutes", "10 minutes", "5 minutes", "2 minutes");
        gameDurationSelector.setSelectedIndex(0);
        durationTable.add(gameDuration).left();
        durationTable.add(gameDurationSelector).width(350).height(70).top().padLeft(20).row();
        choose.add(durationTable).left().padBottom(60).row();
        startGame = new TextButton("Start Game", skin);
        startGame.setScale(1f);
        startGame.pack();

        startGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.enterNewGame();
            }
        });

        choose.add(startGame).left().padBottom(60).row();
        table.add(choose).colspan(5).left().pad(20);

        portrait = new Image(controller.getSelectedHero().getPortraitTexture());
        show.add(portrait).width(portrait.getWidth() * 2f).height(portrait.getHeight() * 2f).top().padBottom(40);

        Table heroDetTable = new Table(skin);
        heroDetTable.add(heroName).padBottom(30).left().top().row();
        for (int i = 0; i < heroDet.length; i++) {
            heroDetTable.add(heroDet[i]).padBottom(20).left().top().padRight(10);
            if (i % 2 == 1) heroDetTable.row();
        }
        show.row();
        show.add(heroDetTable).colspan(4).left().top();

        table.add(show).colspan(4).right().top().padTop(20).padLeft(400);

        controller.setHero(0);

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

    public Table getChoose() {
        return choose;
    }

    public Table getShow() {
        return show;
    }

    public Table getHeroesTable() {
        return heroesTable;
    }

    public Table getGunsTable() {
        return gunsTable;
    }

    public TextButton getBack() {
        return back;
    }

    public Label getTitle() {
        return title;
    }

    public AnimatedImageButton[] getHeroes() {
        return heroes;
    }

    public AnimatedImageButton[] getGuns() {
        return guns;
    }

    public Image getPortrait() {
        return portrait;
    }

    public Label getGameDuration() {
        return gameDuration;
    }

    public SelectBox<String> getGameDurationSelector() {
        return gameDurationSelector;
    }

    public TextButton getStartGame() {
        return startGame;
    }

    public Label getHeroName() {
        return heroName;
    }

    public Label[] getHeroDet() {
        return heroDet;
    }
}
