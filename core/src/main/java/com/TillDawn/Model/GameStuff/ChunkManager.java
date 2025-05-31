package com.TillDawn.Model.GameStuff;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChunkManager {
    @JsonIgnore
    public static final int viewRadius = 3;

    @JsonIgnore
    private HashMap<Coordinate, Chunk> chunks = new HashMap<>();
    @JsonIgnore
    private HashMap<Coordinate, Chunk> cachedChunks = new HashMap<>();

    private int nothing = 2;

    public int getNothing() {
        return nothing;
    }

    public void setNothing(int nothing) {
        this.nothing = nothing;
    }

    public ChunkManager() {
    }

    public HashMap<Coordinate, Chunk> getChunks() {
        return chunks;
    }

    public HashMap<Coordinate, Chunk> getCachedChunks() {
        return cachedChunks;
    }

    public void setChunks(HashMap<Coordinate, Chunk> chunks) {
        this.chunks = chunks;
    }

    public void setCachedChunks(HashMap<Coordinate, Chunk> cachedChunks) {
        this.cachedChunks = cachedChunks;
    }

    public void update(float playerX, float playerY) {
        int playerChunkX = Math.floorDiv((int) Math.floor(playerX), Chunk.CHUNK_SIZE);
        int playerChunkY = Math.floorDiv((int) Math.floor(playerY), Chunk.CHUNK_SIZE);

        HashSet<Coordinate> inRange = new HashSet<>();
        for (int dx = -viewRadius; dx <= viewRadius; dx++) {
            for (int dy = -viewRadius; dy <= viewRadius; dy++) {
                int y = playerChunkY + dy;
                int x = playerChunkX + dx;
                inRange.add(new Coordinate(x, y));
//                chunks.computeIfAbsent(new Coordinate(x, y), k -> new Chunk(x, y));
                Coordinate c = new Coordinate(x, y);
                if (!chunks.containsKey(c)) {
                    Chunk chunk;
                    chunk = cachedChunks.getOrDefault(c, null);
                    if (chunk == null) {
                        chunk = new Chunk(x, y);
                        cachedChunks.put(c, chunk);
                    }
                    chunks.put(c, chunk);
                }
            }
        }

        chunks.keySet().removeIf(c -> !inRange.contains(c));
    }

    public void draw(SpriteBatch batch) {
        for (Chunk chunk : chunks.values()) {
            int baseX = chunk.getChunkX() * Chunk.CHUNK_SIZE;
            int baseY = chunk.getChunkY() * Chunk.CHUNK_SIZE;
            for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
                for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                    batch.draw(chunk.getTile(x, y).getTileTexture(), baseX + x, baseY + y, 1, 1);
                }
            }
        }
    }

}
