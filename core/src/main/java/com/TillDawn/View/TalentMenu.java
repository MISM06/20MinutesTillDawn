package com.TillDawn.View;

import com.TillDawn.Controller.RegisterMenuController;
import com.TillDawn.Controller.TalentMenuController;
import com.TillDawn.Model.App;
import com.TillDawn.Model.Enums.AbilityTypes;
import com.TillDawn.Model.Enums.GunType;
import com.TillDawn.Model.Enums.Hero;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Model.Utils;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class TalentMenu implements Screen {
    private Stage stage;
    private TalentMenuController controller;
    private Skin skin;
    private Table table;

    private TextButton back;
    private Label title;

    private Table infos;

    public TalentMenu(TalentMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getInstance().getSkin();
//        stage = new Stage();

        table = new Table(skin);
        controller.setView(this);
    }

    public TextureRegion createBackground(int width, int height, float r, float g, float b, float alpha)  {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, alpha);
        pixmap.fill();

        pixmap.setColor(r, g, b, alpha);
        pixmap.fillRectangle(6, 6, width - 12,  height - 12);
        Texture textureBackground = new Texture(pixmap);
        return new TextureRegion(textureBackground);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), TillDawn.getBatch());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        infos = new Table(skin);
        infos.top();

        title = new Label("Talent", skin);
        title.setFontScale(2.5f);
        title.setColor(Color.RED);

        table.add(title).top().padBottom(50).row();

        back = new TextButton("Back", skin);
        back.getLabel().setFontScale(0.5f);
        back.pack();
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.back();
            }
        });

        Table herosTable = new Table(skin);
        herosTable.setBackground(new TextureRegionDrawable(createBackground(300, 800, 0.2f, 0, 0.2f, 1)));
        Label heroLabel = new Label("Heroes", skin);
        heroLabel.setFontScale(2f);
        heroLabel.setColor(Color.BLACK);
        herosTable.add(heroLabel).top().padBottom(30).row();
        for (Hero hero : Hero.values()) {
            Label name = new Label(hero.toString(), skin);
            name.setFontScale(1.5f);
            name.setColor(Color.RED);
            herosTable.add(name).left().row();

            Label info = new Label(hero.getDescription(), skin);
            herosTable.add(info).left().row();
            herosTable.add().row();
        }

        infos.add(herosTable).top().left().fillY().expandY();;

        Table gunTable = new Table(skin);
        gunTable.setBackground(new TextureRegionDrawable(createBackground(300, 800, 0.2f, 0, 0.2f, 1)));
        Label gunLabel = new Label("Guns", skin);
        gunLabel.setFontScale(2f);
        gunLabel.setColor(Color.BLACK);
        gunTable.add(gunLabel).center().padBottom(30).row();
        for (GunType gun : GunType.values()) {
            Label name = new Label(gun.toString(), skin);
            name.setFontScale(1.5f);
            name.setColor(Color.RED);
            gunTable.add(name).left().row();

            Label info = new Label(gun.getDescription() + "\n", skin);
            gunTable.add(info).left().row();
            gunTable.add().row();
        }

        infos.add(gunTable).top().left().fillY().expandY();;

        Table keysTable = new Table(skin);
        keysTable.setBackground(new TextureRegionDrawable(createBackground(300, 800, 0.2f, 0, 0.2f, 1)));
        Label keyLabel = new Label("Keys", skin);
        keyLabel.setFontScale(2f);
        keyLabel.setColor(Color.BLACK);
        keysTable.add(keyLabel).top().padBottom(30).row();
        ArrayList<Label> keyLabels = new ArrayList<>();
        keyLabels.add(new Label("move Left: " +
            Input.Keys.toString(App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().get(Input.Keys.A)) +"\n\n\n",
            skin));
        keyLabels.add(new Label("move Right: " +
            Input.Keys.toString(App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().get(Input.Keys.D)) +"\n\n\n",
            skin));
        keyLabels.add(new Label("move Up: " +
            Input.Keys.toString(App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().get(Input.Keys.W)) +"\n\n\n",
            skin));
        keyLabels.add(new Label("move Down: " +
            Input.Keys.toString(App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().get(Input.Keys.S)) +"\n\n\n",
            skin));
        keyLabels.add(new Label("move Reload: " +
            Input.Keys.toString(App.getInstance().getCurrentUser().getSettingData().getPlayingKeys().get(Input.Keys.R)) + "\n\n\n",
            skin));
        for (Label label : keyLabels) {
            keysTable.add(label).left().row();
        }

        infos.add(keysTable).top().left().fillY().expandY();;

        Table cheatCodeTable = new Table(skin);
        cheatCodeTable.setBackground(new TextureRegionDrawable(createBackground(300, 800, 0.2f, 0, 0.2f, 1)));

        Label cheatLabel = new Label("Cheats", skin);
        cheatLabel.setFontScale(2f);
        cheatLabel.setColor(Color.BLACK);
        cheatCodeTable.add(cheatLabel).top().padBottom(30).row();

        for (String cheatCode : Utils.cheatCodes) {
            Label cheatCodeLabel = new Label(cheatCode + "\n\n", skin);
            cheatCodeLabel.setWrap(true);
            cheatCodeTable.add(cheatCodeLabel).width(280).left().row();
        }
        infos.add(cheatCodeTable).top().left().fillY().expandY();;

        Table abilityTable = new Table(skin);
        abilityTable.setBackground(new TextureRegionDrawable(createBackground(300, 800, 0.2f, 0, 0.2f, 1)));
        Label abilityLabel = new Label("Abilities", skin);
        abilityLabel.setFontScale(2f);
        abilityLabel.setColor(Color.BLACK);
        abilityTable.add(abilityLabel).top().padBottom(30).row();

        for (AbilityTypes value : AbilityTypes.values()) {
            Label name = new Label(value.getName() + "\n", skin);
            name.setFontScale(1.2f);
            name.setColor(Color.RED);
            abilityTable.add(name).left().row();

            Label info = new Label(value.getDescription(), skin);
            info.setWrap(true);
            abilityTable.add(info).width(280).left().row();
            abilityTable.add().row();
        }
        infos.add(abilityTable).top().left().fillY().expandY();;


        table.add(infos).center();

        back.setPosition(0, Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);


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
}
