package com.TillDawn.Model.GameStuff;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class OneTimeAnimationManager {
    private static OneTimeAnimationManager instance;
    public static OneTimeAnimationManager getInstance() {
        if (instance == null) {
            instance = new OneTimeAnimationManager();
        }
        return instance;
    }

    public OneTimeAnimationManager() {
    }

    private ArrayList<OneTimeAnimation> animations = new ArrayList<OneTimeAnimation>();
    public void update(float delta) {
        for (OneTimeAnimation animation : animations) {
            animation.update(delta);
        }
    }
    public void addAnimation(OneTimeAnimation animation) {
        animations.add(animation);
    }
    public void removeAnimation(OneTimeAnimation animation) {
        animations.remove(animation);
    }
    public void draw(SpriteBatch batch) {
        for (OneTimeAnimation animation : animations) {
            animation.draw(batch);
        }
    }
}
