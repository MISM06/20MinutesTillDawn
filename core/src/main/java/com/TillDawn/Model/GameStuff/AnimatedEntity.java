package com.TillDawn.Model.GameStuff;

import com.TillDawn.Model.AudioManager;
import com.TillDawn.Model.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class AnimatedEntity {
    private Sprite sprite;
    private ObjectMap<String, Animation<TextureRegion>> animations = new ObjectMap<>();
    private String currentAnimation;
    private Queue<String> queueAnimation = new ArrayDeque<>();


    private float time = 0.0f;
    private float changeTime = 0.0f;

    private boolean rightFaceWay = true;
    private boolean upFaceWay = true;

    private Collision collision = null;

    public AnimatedEntity (float x, float y, float width, float height) {
        sprite = new Sprite();
        sprite.setBounds(x, y, width, height);
    }

    public void addAnimation (String name, Animation<TextureRegion> animation) {
        animations.put(name, animation);
        if (currentAnimation == null) {
            currentAnimation = name;
            sprite.setRegion(animation.getKeyFrame(0));
        }
    }
    public boolean setAnimation (String name) {
        if (!animations.containsKey(name)) return false;
        if (!name.equals(currentAnimation)) {
            currentAnimation = name;
            changeTime = animations.get(name).getAnimationDuration();
            time = 0;
        }
        return true;
    }
    public void setAnimationQueue (ArrayList<String> names) {
        queueAnimation.clear();
        queueAnimation.addAll(names);
    }
    public void addAnimationQueue (ArrayList<String> names) {
        queueAnimation.addAll(names);
    }
    public void update (float delta) {

        time += delta;
        if (currentAnimation != null) {
            TextureRegion frame =animations.get(currentAnimation).getKeyFrame(time, true);
            if (rightFaceWay && frame.isFlipX()) {
                frame.flip(true, false);
            } else if (!rightFaceWay && !frame.isFlipX()) {
                frame.flip(true, false);
            }
            if (upFaceWay && frame.isFlipY()) {
                frame.flip(false, true);
            } else if (!upFaceWay && !frame.isFlipY()) {
                frame.flip(false, true);
            }
            sprite.setRegion(frame);
        }
        if (queueAnimation.size() > 0) {
            if (time >= changeTime) {
                setAnimation(queueAnimation.remove());
            }
        }
    }
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
    public void setPosition (float x, float y) {
        if (collision != null) collision.move(x - sprite.getX(), y - sprite.getY());
        sprite.setPosition(x, y);
    }
    public void move (float dx, float dy) {
        if (collision != null) collision.move(dx, dy);
        sprite.translate(dx, dy);
    }
    public float getX () {
        return sprite.getX();
    }
    public float getY () {
        return sprite.getY();
    }
    public float getWidth () {
        return sprite.getWidth();
    }
    public float getHeight () {
        return sprite.getHeight();
    }
    public void setSize (float width, float height) {
        sprite.setSize(width, height);
    }
    public void faceWayRight() {
        rightFaceWay = true;
    }
    public void faceWayLeft() {
        rightFaceWay = false;
    }
    public void faceWayUp() {
        upFaceWay = true;
    }
    public void faceWayDown() {
        upFaceWay = false;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public ObjectMap<String, Animation<TextureRegion>> getAnimations() {
        return animations;
    }

    public String getCurrentAnimation() {
        return currentAnimation;
    }

    public float getTime() {
        return time;
    }

    public boolean isRightFaceWay() {
        return rightFaceWay;
    }

    public boolean isUpFaceWay() {
        return upFaceWay;
    }

    public boolean isAnimationFinished() {
        Animation<TextureRegion> anim = animations.get(currentAnimation);
        return anim.isAnimationFinished(time);
    }

    public Collision getCollision() {
        return collision;
    }

    public void setCollision(Collision collision) {
        this.collision = collision;
    }
}
