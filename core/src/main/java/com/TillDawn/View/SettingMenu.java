package com.TillDawn.View;

import com.TillDawn.Controller.SettingMenuController;
import com.TillDawn.Model.Enums.Assets.Musics;
import com.TillDawn.Model.App;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Model.SettingData;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingMenu implements Screen, InputProcessor {
    private Stage stage;
    private SettingMenuController controller;
    private Skin skin;
    private Table table;

    private Label title;
    private InputMultiplexer inputMultiplexer;

    private Slider musicVolumeSlider;
    private Label musicVolumeLabel;
    private CheckBox musicEnableCheckbox;

    private CheckBox sfxEnableCheckbox;

    private CheckBox autoReloadCheckbox;

    private CheckBox blackWhiteCheckbox;

    private SelectBox<Musics> musicSelectBox;

    private TextButton backButton;

    private Table keysTable;
    private TextButton[] keysButton;
    private int[] defaultKeys;
    private boolean isWaitingForKeyPress;
    private int waiterIndex;

    public SettingMenu(SettingMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
//        stage = new Stage();

        table = new Table(skin);
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), TillDawn.getBatch());
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        title = new Label("Setting", skin);
        title.setFontScale(2f);
        title.setColor(0.6f, 0, 0, 1);

        SettingData settingData = App.getInstance().getCurrentUser().getSettingData();

        musicVolumeSlider = new Slider(0, 100, 1, false, skin);
        musicVolumeSlider.setValue((int) (settingData.getMusicVolume() * 100));
        musicVolumeLabel = new Label("Music Volume: " + (int) musicVolumeSlider.getValue(), skin);
        musicEnableCheckbox = new CheckBox("Music", skin);
        musicEnableCheckbox.setChecked(settingData.isMusicEnabled());

        sfxEnableCheckbox = new CheckBox("SFX", skin);
        sfxEnableCheckbox.setChecked(settingData.isSfxEnabled());

        autoReloadCheckbox = new CheckBox("Auto Reload", skin);
        autoReloadCheckbox.setChecked(settingData.isAutoReloadEnabled());

        blackWhiteCheckbox = new CheckBox("Black and White", skin);
        blackWhiteCheckbox.setChecked(settingData.isBlackWhiteEnabled());

        backButton = new TextButton("Back", skin);

        musicSelectBox = new SelectBox<>(skin);
        musicSelectBox.setItems(Musics.getPreGames());
        musicSelectBox.setSelected(Musics.getMusicByPath(App.getInstance().getCurrentUser().getSettingData().getPlayingMusicPath()));
        musicVolumeSlider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                musicVolumeLabel.setText("Music Volume: " + (int) musicVolumeSlider.getValue());
                controller.changeMusicVolume(musicVolumeSlider.getValue());
            }
        });

        musicEnableCheckbox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.musicEnable(musicEnableCheckbox.isChecked());
            }
        });
        sfxEnableCheckbox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.sfxEnable(sfxEnableCheckbox.isChecked());
            }
        });
        autoReloadCheckbox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.autoReloadEnable(autoReloadCheckbox.isChecked());
            }
        });
        blackWhiteCheckbox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.blackWhiteEnable(blackWhiteCheckbox.isChecked());
            }
        });
        musicSelectBox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.changeMusic(musicSelectBox.getSelected());
            }
        });

        keysTable = new Table(skin);
        int keysCnt = App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().size();
        if (keysCnt > 5) {
            keysCnt = keysCnt;
        }
        keysButton = new TextButton[keysCnt];
        defaultKeys = new int[keysCnt];
        for (int i = 0; i < keysCnt; i++) {
            int keycode = Input.Keys.R;
            switch (i) {
                case 0:
                    keycode = Input.Keys.W;
                    break;
                case 1:
                    keycode = Input.Keys.A;
                    break;
                case 2:
                    keycode = Input.Keys.S;
                    break;
                case 3:
                    keycode = Input.Keys.D;
                    break;
                case 4:
                    keycode = Input.Keys.R;
                    break;
            }
            defaultKeys[i] = keycode;
            int equa = App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().get(keycode);
            keysButton[i] = new TextButton(Input.Keys.toString(equa), skin);
            keysButton[i].pack();
            keysButton[i].setScale(0.8f);
            keysTable.add(keysButton[i]).pad(10);
            final int index = i;
            keysButton[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    waiterIndex = index;
//                    System.out.println("waiterIndex: " + waiterIndex);
                    isWaitingForKeyPress = true;
                }
            });
        }

        table.add(title).colspan(2).padBottom(50).row();

        musicSelectBox.setScale(0.8f);
        table.add(new Label("Music: ", skin)).right().padRight(10).padBottom(15);
        table.add(musicSelectBox).left().padBottom(15);

        table.row();

        table.add(musicEnableCheckbox).colspan(2).center().padBottom(15).row();

        table.add(musicVolumeLabel).right().padBottom(20).padRight(10);
        table.add(musicVolumeSlider).width(400).left().padBottom(20).row();

        table.add(sfxEnableCheckbox).colspan(2).center().padBottom(20).row();

        table.add(autoReloadCheckbox).colspan(2).center().padBottom(20).row();

        table.add(blackWhiteCheckbox).colspan(2).center().padBottom(20).row();

        table.add(keysTable).colspan(2).center().padBottom(20).row();

        backButton.getLabel().setFontScale(0.6f);
        backButton.pack();
        table.add(backButton).colspan(2).center().padBottom(20);

    }

    public void waitToPress() {
        isWaitingForKeyPress = true;
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

    public Label getTitle() {
        return title;
    }

    public Slider getMusicVolumeSlider() {
        return musicVolumeSlider;
    }

    public Label getMusicVolumeLabel() {
        return musicVolumeLabel;
    }

    public CheckBox getMusicEnableCheckbox() {
        return musicEnableCheckbox;
    }

    public CheckBox getSfxEnableCheckbox() {
        return sfxEnableCheckbox;
    }

    public CheckBox getAutoReloadCheckbox() {
        return autoReloadCheckbox;
    }

    public CheckBox getBlackWhiteCheckbox() {
        return blackWhiteCheckbox;
    }

    public SelectBox<Musics> getMusicSelectBox() {
        return musicSelectBox;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (isWaitingForKeyPress) {
            isWaitingForKeyPress = false;
            if (App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().containsValue(keyCode)) {
                App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().put(defaultKeys[waiterIndex],
                    defaultKeys[waiterIndex]);
                keysButton[waiterIndex].setText(Input.Keys.toString(defaultKeys[waiterIndex]));
            } else {
                App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().put(defaultKeys[waiterIndex],
                    keyCode);
                keysButton[waiterIndex].setText(Input.Keys.toString(keyCode));
            }
            if (App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().size() > 5) {
                keyCode = keyCode;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
