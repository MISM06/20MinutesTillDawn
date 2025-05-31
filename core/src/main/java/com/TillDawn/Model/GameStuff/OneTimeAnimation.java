package com.TillDawn.Model.GameStuff;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OneTimeAnimation {
    private Sprite sprite;
    private Animation<TextureRegion> animation;
    private float stateTime = 0.0f;
    private float duration = 0.0f;
    private String name;
    private boolean isFinished = true;

    public OneTimeAnimation(float x, float y, float width, float height) {
        this.sprite = new Sprite();
        sprite.setBounds(x, y, width, height);
    }

    public OneTimeAnimation() {
    }

    public void setAnimation(String name, Animation<TextureRegion> animation, float duration) {
        stateTime = 0.0f;
        this.duration = duration;
        this.name = name;
        this.animation = animation;
    }
    public void setAnimation(String name, Animation<TextureRegion> animation) {
        stateTime = 0.0f;
        this.duration = animation.getAnimationDuration();
        this.name = name;
        this.animation = animation;
        isFinished = false;
    }

    public void move(float dx, float dy) {
        sprite.translate(dx, dy);
    }

    public void update(float delta) {
        stateTime += delta;
        if (!isFinished) {
            sprite.setRegion(animation.getKeyFrame(stateTime, true));
            if (stateTime >= duration) {
                isFinished = true;
            }
        }
    }
    public void draw(SpriteBatch batch) {
        if (!isFinished) {
            sprite.draw(batch);
        }
    }

}
