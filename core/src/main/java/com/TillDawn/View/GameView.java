package com.TillDawn.View;

import com.TillDawn.Controller.GameViewController;
import com.TillDawn.Model.Enums.AbilityTypes;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Model.GameStuff.Game;
import com.TillDawn.Model.Utils;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class GameView implements Screen {

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private SpriteBatch batch;
    private Skin skin;

    @JsonIgnore
    private GameViewController controller;

    private Stage stage;
    private InputMultiplexer inputMultiplexer;

    private ProgressBar levelBar;
    private Label levelLabel;
    private Label timeLabel;
    private Label killLabel;
    private Label ammoLabel;

    private Table hpTable;
    private Table table;

    private ArrayList<Image> hpBar;
    private Animation<TextureRegion> fullHeartAnimation;
    private Animation<TextureRegion> brokenHeartAnimation;

    private Table abilityMenu;
    private ImageButton[] abilityButtons;
    private Table[] abilityTables;
    private Label[] abilityDescriptions;
    private Label[] abilityNames;
    private AbilityTypes[] abilityTypes;
    private int abilityCnt = 3;

    private Sprite lightMaskSprite;

    private Table endingMenu;
    private Label endingStateTitle;
    private Label userNameLabel;
    private Label surviveTimeLabel;
    private Label killsCountLabel;
    private Label scoreLabel;
    private TextButton quit;

    private Table pauseMenu;
    private TextButton resumeButton;
    private Label[] cheatCodesLabel;
    private Table abilityAchieved;
    private Table abilityAchievedInside;
    private TextButton giveUp;
    private TextButton saveAndExit;
    private CheckBox blackWhiteCheckbox;

    public GameView(GameViewController controller) {
        this.controller = controller;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ExtendViewport(Utils.screenWidth, Utils.screenHeight, camera);
        batch = TillDawn.getBatch();
        skin = GameAssetManager.getInstance().getSkin();


        Game.getInstance().setGameView(this);
        controller.setView(this);
    }

    public GameView() {
    }

    public void setController(GameViewController controller) {
        this.controller = controller;
    }

    public TextureRegion createBackground(int width, int height, float r, float g, float b, float alpha)  {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, alpha);
        pixmap.fill();

        pixmap.setColor(r, g, b, alpha);
        pixmap.fillRectangle(2, 2, width - 4,  height - 4);
        Texture textureBackground = new Texture(pixmap);
        return new TextureRegion(textureBackground);
    }

    public Texture createLightMask(int width, int height, int radius) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        int centerX = width / 2;
        int centerY = height / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float dx = x - centerX;
                float dy = y - centerY;
                float dist = (float)Math.sqrt(dx * dx + dy * dy);

                float alpha;
                if (dist < radius) {
                    alpha = 0f;
                } else if (dist < radius + 120) {
                    alpha = (dist - radius) / 200f;
                } else {
                    alpha = 0.6f;
                }

                pixmap.setColor(0f, 0f, 0f, alpha);
                pixmap.drawPixel(x, y);
            }
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    public void createAbilityMenu() {
        abilityMenu = new Table(skin);
        abilityMenu.setFillParent(true);
        abilityButtons = new ImageButton[abilityCnt];
        abilityTables = new Table[abilityCnt];
        abilityDescriptions = new Label[abilityCnt];
        abilityNames = new Label[abilityCnt];

        Pixmap pixmap = new Pixmap(300, 600, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK); // border
        pixmap.fill();

        pixmap.setColor(Color.DARK_GRAY); // fill
        pixmap.fillRectangle(2, 1, 296, 598);
        Texture textureBackground = new Texture(pixmap);
        TextureRegion background = new TextureRegion(textureBackground);

        for (int i = 0; i < abilityCnt; i++) {
            abilityButtons[i] = new ImageButton(new TextureRegionDrawable());
            abilityButtons[i].setTransform(true);
            abilityTables[i] = new Table(skin);
            abilityDescriptions[i] = new Label("description\ndescription", skin);
            abilityNames[i] = new Label("name", skin);
            abilityTables[i].setBackground(new TextureRegionDrawable(background));
            abilityTables[i].add(abilityButtons[i]).top().padTop(10).padBottom(50).row();
            final int index = i;
            abilityButtons[i].addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    abilityButtons[index].clearActions();
                    abilityButtons[index].addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    abilityButtons[index].clearActions();
                    abilityButtons[index].addAction(Actions.scaleTo(1f, 1f, 0.1f));
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttonCode) {
                    abilityButtons[index].clearActions();
                    abilityButtons[index].addAction(Actions.scaleTo(0.9f, 0.9f, 0.05f));
                    return super.touchDown(event, x, y, pointer, buttonCode);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int buttonCode) {
                    super.touchUp(event, x, y, pointer, buttonCode);
                    abilityButtons[index].clearActions();
                    abilityButtons[index].addAction(Actions.scaleTo(1.2f, 1.2f, 0.05f));
                }
                public void clicked(InputEvent event, float x, float y) {
                    controller.addAbility(index);
                    abilityMenu.setVisible(false);
                    controller.setPaused(false);
                }
            });

            abilityTables[i].add(abilityNames[i]).center().padTop(20).row();
            abilityTables[i].add(abilityDescriptions[i]).bottom().padTop(40);


            abilityMenu.add(abilityTables[i]).padTop(30).padBottom(30).padLeft(i == 0 ? 50 : 5).padRight(i == abilityCnt - 1 ? 50 : 5);
        }


        stage.addActor(abilityMenu);
        abilityMenu.setVisible(false);

    }

    public void showAbilityMenu() {
        for (int i = 0; i < abilityCnt; i++) {
            abilityButtons[i].getStyle().imageUp = new TextureRegionDrawable(controller.getAbilityTypes()[i].getIconRegion());
            abilityNames[i].setText(controller.getAbilityTypes()[i].getName());
            abilityDescriptions[i].setText(controller.getAbilityTypes()[i].getDescription());
        }
        abilityMenu.setVisible(true);
        controller.setPaused(true);
    }

    public void createEndingMenu() {

        TextureRegion background = createBackground(1200, 800, 0.2f, 0, 0.2f, 0.7f);

        endingMenu = new Table(skin);
        endingMenu.setBackground(new TextureRegionDrawable(background));

        endingStateTitle = new Label("YOU DIED", skin);
        endingStateTitle.setFontScale(2.5f);
        endingStateTitle.setColor(Color.RED);

        userNameLabel = new Label("username: " + "name", skin);
        surviveTimeLabel = new Label("survive time: " + "(00:00)", skin);
        killsCountLabel = new Label("kills: " + "(000)", skin);
        scoreLabel = new Label("score: " + "(000)", skin);

        userNameLabel.setColor(1f, 1f, 0.6f, 1);
        surviveTimeLabel.setColor(1f, 1f, 0.6f, 1);
        killsCountLabel.setColor(1f, 1f, 0.6f, 1);
        scoreLabel.setColor(1f, 1f, 0.6f, 1);

        quit = new TextButton("Quit to menu", skin);
        quit.getLabel().setFontScale(0.8f);
        quit.pack();

        quit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.quitGame();
            }
        });

//        endingMenu.setFillParent(true);
//        endingMenu.center();
        endingMenu.add(endingStateTitle).top().padBottom(150).row();
        endingMenu.add(userNameLabel).padBottom(35).row();
        endingMenu.add(surviveTimeLabel).padBottom(35).row();
        endingMenu.add(killsCountLabel).padBottom(35).row();
        endingMenu.add(scoreLabel).padBottom(80).row();
        endingMenu.add(quit).padBottom(50).row();

        Table wrapper = new Table(skin);
        wrapper.setFillParent(true);
        wrapper.center();
        wrapper.add(endingMenu).center();

        stage.addActor(wrapper);
        endingMenu.setVisible(false);

    }

    public void showEndingMenu() {
        endingMenu.setVisible(true);
        controller.setPaused(true);
    }

    public void addMaxHp () {
        Image image = new Image(brokenHeartAnimation.getKeyFrame(Game.getInstance().getTimePassed(), true));
        hpBar.add(image);
        image.setScale(2f);
        hpTable.add(image).padRight(30).padTop(20);
    }

    public void showStage() {
        table = new Table(skin);
        table.setFillParent(true);
        table.top();

        Pixmap bgPixmap = new Pixmap(1, 50, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(Color.DARK_GRAY);
        bgPixmap.fill();
        Drawable background = new TextureRegionDrawable(new Texture(bgPixmap));

        Pixmap fgPixmap = new Pixmap(1, 50, Pixmap.Format.RGBA8888);
        fgPixmap.setColor(Color.LIME);
        fgPixmap.fill();
        Drawable knobBefore = new TextureRegionDrawable(new Texture(fgPixmap));

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = background;
        style.knobBefore = knobBefore;
        style.knob = null;

        levelBar = new ProgressBar(0, 100, 0.1f, false, style);

        levelBar.setAnimateDuration(0.2f);
        levelBar.setValue(0);
        table.add(levelBar).expandX().fillX().height(5).padBottom(30).colspan(2).row();

        levelLabel = new Label("Level 1", skin);
        levelLabel.setFontScale(0.9f);
        Table levelTable = new Table(skin);
        levelTable.setFillParent(true);
        levelTable.top();
        levelTable.add(levelLabel);


        //Hp bar
        hpTable = new Table(skin);
        fullHeartAnimation = new Animation<TextureRegion>(0.2f, GameAssetManager.getInstance().getHeartFullRegion());
        brokenHeartAnimation = new Animation<TextureRegion>(0.2f, GameAssetManager.getInstance().getHeartBrokenRegion());
        hpBar = new ArrayList<>();
        addMaxHp();
        table.add(hpTable).left().padBottom(10).padLeft(10);


        timeLabel = new Label("00:00", skin);
        timeLabel.setFontScale(2f);
        table.add(timeLabel).right().padRight(10).padBottom(10).row();


        Table ammoTable = new Table(skin);
        Image ammoImage = new Image(new Texture(Gdx.files.internal("textures/Icons/T_AmmoIcon.png")));
        ammoImage.setScale(1.3f);
        ammoTable.add(ammoImage).left().padRight(20);

        ammoLabel = new Label("00/00", skin);
        ammoLabel.setFontScale(1f);
        ammoTable.add(ammoLabel).left();
        table.add(ammoTable).left().padLeft(20).padBottom(10);


        killLabel = new Label("kills: 000", skin);
        killLabel.setFontScale(1f);
        table.add(killLabel).right().padRight(20).padBottom(10).row();


        stage.addActor(table);
        stage.addActor(levelTable);
        createAbilityMenu();
        createPauseMenu();
        createEndingMenu();
    }

    public void createPauseMenu() {

        pauseMenu = new Table(skin);
        pauseMenu.setBackground(new TextureRegionDrawable(createBackground(1200, 800, 0.2f, 0, 0.2f, 0.7f)));

        Table wrapper = new Table(skin);
        wrapper.setFillParent(true);
        wrapper.center();
        wrapper.add(pauseMenu);

        resumeButton = new TextButton("Resume", skin);
        resumeButton.getLabel().setFontScale(0.8f);
        resumeButton.pack();
        resumeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.setVisible(false);
                controller.setPaused(false);
            }
        });
        Table cheatCodeWrapper = new Table(skin);
        cheatCodeWrapper.setBackground(new TextureRegionDrawable(createBackground(350, 350, 0, 0, 0, 0.9f)));
        cheatCodesLabel = new Label[Utils.cheatCodes.length];
        for (int i = 0; i < Utils.cheatCodes.length; i++) {
            cheatCodesLabel[i] = new Label(Utils.cheatCodes[i], skin);
            cheatCodesLabel[i].setFontScale(1f);
            cheatCodesLabel[i].setColor(Color.WHITE);
            cheatCodesLabel[i].setWidth(350);
            if (i == Utils.cheatCodes.length - 1) {
                cheatCodesLabel[i].setFontScale(0.87f);
            }
            cheatCodeWrapper.add(cheatCodesLabel[i]).center().padBottom(5).row();
        }

        abilityAchieved = new Table(skin);
        abilityAchievedInside = new Table(skin);
        abilityAchievedInside.setFillParent(true);
        abilityAchieved.setBackground(new TextureRegionDrawable(createBackground(350, 350, 0, 0, 0, 0.9f)));
        abilityAchieved.add(abilityAchievedInside).colspan(3).top();

        giveUp = new TextButton("Give up", skin);
        giveUp.getLabel().setFontScale(0.8f);
        giveUp.pack();
        giveUp.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.setVisible(false);
                controller.finishGame();
            }
        });

        saveAndExit = new TextButton("Save and Exit", skin);
        saveAndExit.getLabel().setFontScale(0.6f);
        saveAndExit.pack();
        saveAndExit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.setVisible(false);
                controller.saveAndExit();
            }
        });


        blackWhiteCheckbox = new CheckBox("Black and White", skin);
        blackWhiteCheckbox.getLabel().setFontScale(0.5f);
        blackWhiteCheckbox.setChecked(Game.getInstance().getPlayer().getUser().getSettingData().isBlackWhiteEnabled());

        blackWhiteCheckbox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.blackWhiteEnable(blackWhiteCheckbox.isChecked());
            }
        });

        pauseMenu.add(resumeButton).colspan(2).center().pad(20).row();
        pauseMenu.add(blackWhiteCheckbox).colspan(2).center().pad(20).row();
        pauseMenu.add(cheatCodeWrapper).center().pad(20);
        pauseMenu.add(abilityAchieved).center().pad(20).row();
        pauseMenu.add(giveUp).colspan(2).pad(20).row();
        pauseMenu.add(saveAndExit).colspan(2).pad(20).row();

        stage.addActor(wrapper);
        pauseMenu.setVisible(false);

    }

    public void showPauseMenu() {
        controller.setPaused(true);
        pauseMenu.setVisible(true);
        abilityAchievedInside.clear();
        int c = 0;
        for (AbilityTypes ability : controller.getHasSeenAbilityTypes()) {
            Image image = new Image(ability.getIconRegion());
            abilityAchievedInside.add(image).center();
            image.setScale(0.5f);
            ++c;
            if (c % 3 == 0) abilityAchievedInside.row();
        }
    }

    @Override
    public void show() {

        camera.position.set(0, 0, 0);
        camera.update();
        stage = new Stage(new ScreenViewport());

        showStage();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(controller);
        Gdx.input.setInputProcessor(inputMultiplexer);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int lightRadius = 240;

        Texture lightMaskTexture = createLightMask(screenWidth, screenHeight, lightRadius);
        lightMaskSprite = new Sprite(lightMaskTexture);
        lightMaskSprite.setSize(Utils.screenWidth, Utils.screenHeight);

    }

    Vector2 playerPos = new Vector2();
    Vector2 cameraPos = new Vector2();

    @Override
    public void render(float delta) {

        playerPos.set(controller.getPlayer().getX() + controller.getPlayer().getEntity().getWidth() / 2,
            controller.getPlayer().getY() + controller.getPlayer().getEntity().getHeight() / 2);
        cameraPos.set(camera.position.x, camera.position.y);
        float distance = cameraPos.dst(playerPos);
        float lerpFactor = Math.min(distance * 0.1f, 0.2f);
        cameraPos.lerp(playerPos, lerpFactor);
        camera.position.set(cameraPos.x, cameraPos.y, 0);
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        controller.update(delta);
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        viewport.update(i, i1, true);
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
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public ExtendViewport getViewport() {
        return viewport;
    }

    public Label getLevelLabel() {
        return levelLabel;
    }

    public ProgressBar getLevelBar() {
        return levelBar;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public Label getKillLabel() {
        return killLabel;
    }

    public ArrayList<Image> getHpBar() {
        return hpBar;
    }

    public Animation<TextureRegion> getFullHeartAnimation() {
        return fullHeartAnimation;
    }

    public Animation<TextureRegion> getBrokenHeartAnimation() {
        return brokenHeartAnimation;
    }

    public Label getAmmoLabel() {
        return ammoLabel;
    }

    public int getAbilityCnt() {
        return abilityCnt;
    }

    public Sprite getLightMaskSprite() {
        return lightMaskSprite;
    }

    public Label getEndingStateTitle() {
        return endingStateTitle;
    }

    public Label getKillsCountLabel() {
        return killsCountLabel;
    }

    public Label getSurviveTimeLabel() {
        return surviveTimeLabel;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Label getUserNameLabel() {
        return userNameLabel;
    }

    public Stage getStage() {
        return stage;
    }
}
