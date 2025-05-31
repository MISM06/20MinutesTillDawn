package com.TillDawn.Model.GameStuff;

import com.TillDawn.Model.Enums.Assets.Tiles;
import com.TillDawn.Model.App;
import com.TillDawn.Model.Enums.EnemyType;
import com.TillDawn.Model.GameStuff.EnemyStuff.Enemy;
import com.TillDawn.Model.Utils;

import java.util.Random;

public class Chunk {
    public static final int CHUNK_SIZE = 32;
    private Tiles[][] tiles;
    private int chunkX;
    private int chunkY;

    public Chunk(int ChunkX, int ChunkY) {
        tiles = new Tiles[CHUNK_SIZE][CHUNK_SIZE];
        this.chunkX = ChunkX;
        this.chunkY = ChunkY;
        generate();
    }

    public Chunk() {
    }

    private void generate() {
        long seed = Utils.getSeedForChunk(chunkX, chunkY);
        Random rand = new Random(seed);
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                float worldX = chunkX * CHUNK_SIZE + x;
                float worldY = chunkY * CHUNK_SIZE + y;
                tiles[x][y] = Tiles.getRandomTile(Tiles.normalL, Tiles.normalR, rand);
            }
        }
        int c = App.getInstance().appService().getRandomNumber(2, 4, rand);
        float dis = 12f / c;
        float[] xs = new float[c];
        float[] ys = new float[c];
        for (int i = 0; i < c; i++) {
            float tx;
            float ty;
            boolean ok;
            do {
                ok = true;
                tx = App.getInstance().appService().getRandomNumber(2, CHUNK_SIZE - 3, rand);
                ty = App.getInstance().appService().getRandomNumber(2, CHUNK_SIZE - 3, rand);
                for (int j = i - 1;j >= 0; j--) {
                    if (App.getInstance().appService().isNear(tx, ty, xs[j], ys[j], dis)) {
                        ok = false;
                        break;
                    }
                }
            } while (!ok);
            xs[i] = tx;
            ys[i] = ty;
        }
        for (int i = 0; i < c; i++) {
            xs[i] += chunkX * CHUNK_SIZE;
            ys[i] += chunkY * CHUNK_SIZE;
            Enemy tree = new Enemy(EnemyType.Tree, xs[i], ys[i]);
            tree.setTree();
            Game.getInstance().getEnemyManager().addTree(tree);
        }
    }

    public Tiles getTile(int x, int y) {
        if (x < 0 || x >= CHUNK_SIZE || y < 0 || y >= CHUNK_SIZE) return null;
        return tiles[x][y];
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }
}
