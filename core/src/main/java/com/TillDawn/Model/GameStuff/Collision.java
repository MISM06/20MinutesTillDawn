package com.TillDawn.Model.GameStuff;

public class Collision {
    private float width;
    private float height;
    private float centerX;
    private float centerY;

    private float startX;
    private float startY;
    private float endX;
    private float endY;


    public Collision(float centerX, float centerY, float width, float height) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.startX = centerX - width / 2;
        this.startY = centerY - height / 2;
        this.endX = centerX + width / 2;
        this.endY = centerY + height / 2;
    }

    public Collision() {
    }

    public void move(float dx, float dy) {
        centerX += dx;
        centerY += dy;
        startX += dx;
        startY += dy;
        endX += dx;
        endY += dy;
    }

    public boolean checkInside(float x, float y) {
        return x > startX && x < endX && y > startY && y < endY;
    }

    public boolean checkCollision(Collision collision) {
        return this.startX < collision.endX && this.endX > collision.startX &&
            this.startY < collision.endY && this.endY > collision.startY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
    }
}
