package com.TillDawn.Model;

import com.TillDawn.Model.GameStuff.Chunk;
import com.TillDawn.Model.GameStuff.ChunkManager;
import com.TillDawn.Model.GameStuff.Game;

public class Utils {
    public static final float unitWidth = 32;
    public static final float unitHeight = 32;

    public static final float screenWidth = 32;
    public static final float screenHeight = 18;

    public static long getSeedForChunk(int x, int y) {
        return ((long)x * 341873128712L) + ((long)y * 132897987541L + Game.getInstance().getSeed() * 2654435761L);
    }

    public static final float heroWidth = 1;
    public static final float heroHeight = 1;

    public static final float gunWidth = 1f;
    public static final float gunHeight = 1f;

    public static final float enemyWidth = 1;
    public static final float enemyHeight = 1;

    public static final float activeRange = Chunk.CHUNK_SIZE * ChunkManager.viewRadius;

    public static final float treeAnimActiveRange = (float) Chunk.CHUNK_SIZE / 4;

    public static final float enemySpawnRange = (screenWidth * 1.2f / 2);

    public static final float xpWidth = 0.3f;
    public static final float xpHeight = 0.3f;

    public static final int xpCoeff = 20;

    public static final String[] cheatCodes = {"Start boss fight: Ctrl+B",
        "Go next level: Ctrl+L",
        "Achieve a heart: Ctrl+H",
        "Skip one minute: Ctrl+T",
        "Increase speed: Ctrl+O",
        "Remove/Bring darkness: Ctrl+G"};
}
