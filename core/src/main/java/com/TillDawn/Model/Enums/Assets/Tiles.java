package com.TillDawn.Model.Enums.Assets;

import com.TillDawn.Model.App;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public enum Tiles {
    Grass("textures/tiles/forest/T_TileGrass.png", 1),
    Default("textures/tiles/forest/T_ForestTiles_0.png", 30),
    Forest1("textures/tiles/forest/T_ForestTiles_1.png", 1),
    Forest2("textures/tiles/forest/T_ForestTiles_2.png", 1),
    Forest3("textures/tiles/forest/T_ForestTiles_3.png", 1),
    Forest4("textures/tiles/forest/T_ForestTiles_4.png", 1),
    Forest5("textures/tiles/forest/T_ForestTiles_5.png", 1),
    Forest6("textures/tiles/forest/T_ForestTiles_6.png", 1),
    Forest7("textures/tiles/forest/T_ForestTiles_7.png", 1),
    Forest8("textures/tiles/forest/T_ForestTiles_8.png", 1),
    ;
    public static final int normalL = 0;
    public static final int normalR = 9;
    private int prob;
    private String tilePath;
    private Texture tileTexture = null;

    Tiles(String tilePath, int prob) {
        this.tilePath = tilePath;
        this.prob = prob;
    }

    public Texture getTileTexture() {
        if (tileTexture == null) {
            tileTexture = new Texture(Gdx.files.internal(tilePath));
        }
        return tileTexture;
    }

    public static Tiles getTile(int id) {
        if (id < 0 || id >= values().length) return null;
        return Tiles.values()[id];
    }

    public static Tiles getRandomTile(int idL, int idR, Random rand) {
        if (idL > idR) return Default;
        if (Tiles.getTile(idL) == null) return Default;
        if (Tiles.getTile(idR) == null) return Default;
        int pSum = 0;
        for (int i = idL; i <= idR; i++) {
            pSum += Tiles.getTile(i).getProb();
        }
        if (pSum < 1) return Default;
        int ind = App.getInstance().appService().getRandomNumber(1, pSum, rand);
        int sum = 0;
        for (int i = idL; i <= idR; i++) {
            sum += Tiles.getTile(i).getProb();
            if (ind <= sum) return Tiles.getTile(i);
        }
        return Default;
    }

    public int getProb() {
        return prob;
    }
}
