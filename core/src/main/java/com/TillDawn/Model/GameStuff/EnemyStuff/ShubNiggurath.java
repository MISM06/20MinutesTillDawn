package com.TillDawn.Model.GameStuff.EnemyStuff;

import com.TillDawn.Model.App;
import com.TillDawn.Model.Enums.EnemyType;
import com.TillDawn.Model.GameAssetManager;
import com.TillDawn.Model.GameStuff.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ShubNiggurath extends Boss{
    public ShubNiggurath(EnemyType type, float centerX, float centerY) {
        super(type, centerX, centerY);
    }

    public ShubNiggurath() {
    }

    private float attackDelay;
    private float chargeDuration;

    private float lastAttackDiff;
    private float chargeStartDiff;

    private float dashDuration;
    private float dashStartDiff;

    private boolean inCharge;
    private boolean inAttack;
    private boolean inRun;

    private float attackRange = 8;
    private float stopRange;
    private float dashExtraLength = 5;
    private Vector2 dashVector = new Vector2();
    private Vector2 startDashPlace = new Vector2();


    private int borderTileCntHalfH;
    private int borderTileCntHalfV;
    private float borderCenterX;
    private float borderCenterY;
    private int borderTileNumberInOneBlock;
    private float borderTileWidthH;
    private float borderTileHeightH;
    private ArrayList<AnimatedEntity> borderHs = new ArrayList<>();
    private ArrayList<AnimatedEntity> borderVs = new ArrayList<>();

    private float lastBorderSmallingDiff;

    @Override
    public void startBossFight() {
        attackDelay = 2.5f;
        chargeDuration = 1.5f;
        lastAttackDiff = 10f;
        chargeStartDiff = 0f;
        dashDuration = 1f;
        inCharge = false;
        inAttack = false;
        inRun = false;


        getEntity().addAnimation("run",
            new Animation<>(0.2f,getType().getTextureRegions("run")));
        getEntity().addAnimation("still",
            new Animation<>(0.2f,getType().getTextureRegions("still")));
        getEntity().addAnimation("charge",
            new Animation<>(chargeDuration / getType().getTextureRegions("charge").size,
                getType().getTextureRegions("charge")));
        getEntity().addAnimation("dash",
            new Animation<>(dashDuration / getType().getTextureRegions("dash").size,
                getType().getTextureRegions("dash")));

        lastBorderSmallingDiff = 0f;
        makeBorder();
        run();
    }


    @Override
    public void update(float delta) {

        lastAttackDiff += delta;
        lastBorderSmallingDiff += delta;
        float playerCenterX = Game.getInstance().getPlayer().getCollision().getCenterX();
        float playerCenterY = Game.getInstance().getPlayer().getCollision().getCenterY();
        if (inRun) {
            if (App.getInstance().appService().isNear(playerCenterX, playerCenterY,
                getCenterX(), getCenterY(),
                attackRange)) {
                if (lastAttackDiff < attackDelay) {
                    getEntity().setAnimation("still");
                    if (playerCenterX - getCenterX() > 0) getEntity().faceWayRight();
                    else getEntity().faceWayLeft();
                } else {
                    inRun = false;
                    charge();
                }
            } else {
                getEntity().setAnimation("run");
                Game.getInstance().getEnemyManager().moveEnemy(delta, this, playerCenterX, playerCenterY);
            }
        } else if (inCharge) {
            chargeStartDiff += delta;
            if (chargeStartDiff >= chargeDuration ) {
                inCharge = false;
                attack();
            }
        } else if (inAttack) {
            dashStartDiff += delta;

            if (dashStartDiff >= dashDuration) {
                inAttack = false;
                run();
            } else {
                float dx;
                float dy;
                float alpha = Math.min(dashStartDiff / dashDuration, 1f);
                float easedAlpha = Interpolation.pow2Out.apply(alpha);

                dx = startDashPlace.x + (dashVector.x - startDashPlace.x) * easedAlpha - getCenterX();
                dy = startDashPlace.y + (dashVector.y - startDashPlace.y) * easedAlpha - getCenterY();

                move(dx, dy);

            }
        }
//        System.out.println(getCenterX() + " " + getCenterY());
        updateBorder(delta);
        getEntity().update(delta);
    }

    public void run() {
        inRun = true;
        inAttack = false;
        inCharge = false;
        getEntity().setAnimation("run");

//        float width = getType().getWidth();
//        Texture texture = getType().getTextures("run").get(0);
//        float height = width * ((float) texture.getHeight() / texture.getWidth());
//        getEntity().setSize(width, height);
    }
    public void charge() {
        inRun = false;
        inCharge = true;
        inAttack = false;
        getEntity().setAnimation("charge");
        chargeStartDiff = 0f;
        float px = Game.getInstance().getPlayer().getCollision().getCenterX();
        float py = Game.getInstance().getPlayer().getCollision().getCenterY();
//        System.out.println("p: " + px + " " + " " + py);
        float dx = px - getCenterX();
        float dy = py - getCenterY();
        float angle = MathUtils.atan2(dy, dx);
        px += (float) (dashExtraLength * Math.cos(angle));
        py += (float) (dashExtraLength * Math.sin(angle));
//        System.out.println("f: " + px + " " + py);
        dashVector.set(px, py);

    }
    public void attack() {
        inRun = false;
        inCharge = false;
        inAttack = true;

        lastAttackDiff = 0;
        dashStartDiff = 0f;
        getEntity().setAnimation("dash");
        startDashPlace.set(getCenterX(), getCenterY());
    }

    @Override
    public void takeDamage(Bullet bullet) {
        setHp(getHp() - bullet.getDamage());
        if (getHp() <= 0) {
            float AnWidth = 0.8f;
            float AnHeight = 0.8f;
            OneTimeAnimation deathFx = new OneTimeAnimation(getCenterX() - AnWidth / 2, getCenterY() - AnHeight / 2, AnWidth, AnHeight);
            deathFx.setAnimation("death", new Animation<>(0.13f, GameAssetManager.getInstance().getEnemyDeathTextureRegion()));
            OneTimeAnimationManager.getInstance().addAnimation(deathFx);
            endBossFight();
        } else {
            float AnWidth = 0.5f;
            float AnHeight = 0.5f;
            OneTimeAnimation deathFx = new OneTimeAnimation(bullet.getSprite().getX() + bullet.getSprite().getWidth() / 2 - AnWidth / 2, bullet.getSprite().getY() + bullet.getSprite().getHeight() / 2 - AnHeight / 2, AnWidth, AnHeight);
            deathFx.setAnimation("damage", new Animation<>(0.1f, GameAssetManager.getInstance().getEnemyDeathTextureRegion()));
            OneTimeAnimationManager.getInstance().addAnimation(deathFx);
        }
    }

    @Override
    public void endBossFight() {
        Game.getInstance().getEnemyManager().getBosses().remove(this);
        removeBorder();
    }

    public void makeBorder() {
        borderTileNumberInOneBlock = 1;
        borderTileCntHalfH = 20 * borderTileNumberInOneBlock;
        borderTileCntHalfV = 20 * borderTileNumberInOneBlock;
        borderTileWidthH = 1f / (float) borderTileNumberInOneBlock;
        borderTileHeightH = borderTileWidthH / 2f;

        borderCenterX = Game.getInstance().getPlayer().getCollision().getCenterX();
        borderCenterY = Game.getInstance().getPlayer().getCollision().getCenterY();

        //just init
        for (int i = 0; i < 4 * borderTileCntHalfH; i++) {
            AnimatedEntity tile = new AnimatedEntity(0, 0, borderTileWidthH, borderTileHeightH);
            tile.addAnimation("border", new Animation<>(0.1f, GameAssetManager.getInstance().getElecWallHTextureRegion()));
            borderHs.add(tile);
        }
        for (int i = 0; i < 4 * borderTileCntHalfV; i++) {
            AnimatedEntity tile = new AnimatedEntity(0, 0, borderTileHeightH, borderTileWidthH);
            tile.addAnimation("border", new Animation<>(0.1f, GameAssetManager.getInstance().getElecWallVTextureRegion()));
            borderVs.add(tile);
        }
        assembleBorder();

    }
    public void assembleBorder() {
        float stX = borderCenterX - (borderTileCntHalfH) * (borderTileWidthH);
        float enX = borderCenterX + (borderTileCntHalfH) * (borderTileWidthH);

        float stY = borderCenterY - (borderTileCntHalfV) * (borderTileWidthH);
        float enY = borderCenterY + (borderTileCntHalfV) * (borderTileWidthH);

        for (int i = borderHs.size() - 1; i >= 4 * borderTileCntHalfH; --i) {
            borderHs.remove(i);
        }
        for (int i = borderVs.size() - 1; i >= 4 * borderTileCntHalfV; --i) {
            borderVs.remove(i);
        }
        float x = stX;
        for (int i = 0; i < borderTileCntHalfH; i++) {
            AnimatedEntity uL = borderHs.get(i);
            AnimatedEntity uR = borderHs.get(i + borderTileCntHalfH);
            AnimatedEntity dL = borderHs.get(i + 2 * borderTileCntHalfH);
            AnimatedEntity dR = borderHs.get(i + 3 * borderTileCntHalfH);

            uL.setPosition(x, enY - borderTileWidthH / 2);
            uR.setPosition(x + (borderTileCntHalfH * borderTileWidthH), enY - borderTileWidthH / 2);
            dL.setPosition(x, stY - borderTileWidthH / 2);
            dR.setPosition(x + (borderTileCntHalfH * borderTileWidthH), stY - borderTileWidthH / 2);
            x += borderTileWidthH;
        }
        float y = stY;
        for (int i = 0; i < borderTileCntHalfV; i++) {
            AnimatedEntity lD = borderVs.get(i);
            AnimatedEntity lU= borderVs.get(i + borderTileCntHalfV);
            AnimatedEntity rD = borderVs.get(i + 2 * borderTileCntHalfV);
            AnimatedEntity rU = borderVs.get(i + 3 * borderTileCntHalfV);

            lD.setPosition(stX - borderTileHeightH / 2, y);
            lU.setPosition(stX- borderTileHeightH / 2, y + borderTileCntHalfV * borderTileWidthH);
            rD.setPosition(enX- borderTileHeightH / 2, y);
            rU.setPosition(enX- borderTileHeightH / 2, y + borderTileCntHalfV * borderTileWidthH);
            y += borderTileWidthH;
        }
    }
    public void updateBorder(float delta) {
        if (lastBorderSmallingDiff >= 5) {
            boolean needMake = false;
            if (borderTileCntHalfH * borderTileWidthH > 5) {
                borderTileCntHalfH -= 1;
                needMake = true;
            }
            if (borderTileCntHalfV * borderTileWidthH > 5) {
                borderTileCntHalfV -= 1;
                needMake = true;
            }
            if (needMake) {
                assembleBorder();
                lastBorderSmallingDiff = 0;
            }
        }
        for (AnimatedEntity borderH : borderHs) {
            borderH.update(delta);
        }
        for (AnimatedEntity borderV : borderVs) {
            borderV.update(delta);
        }
    }
    public void drawBorder(SpriteBatch batch) {
        for (AnimatedEntity borderH : borderHs) {
            borderH.draw(batch);
        }
        for (AnimatedEntity borderV : borderVs) {
            borderV.draw(batch);
        }
    }
    public void removeBorder() {
        borderHs.clear();
        borderVs.clear();
    }

    public int borderCollisionCheck(Collision collision) { //-1 none, 0 left, 1 up, 2 right, 3 down
        float stX = borderCenterX - (borderTileCntHalfH) * (borderTileWidthH);
        float enX = borderCenterX + (borderTileCntHalfH) * (borderTileWidthH);

        float stY = borderCenterY - (borderTileCntHalfV) * (borderTileWidthH);
        float enY = borderCenterY + (borderTileCntHalfV) * (borderTileWidthH);

        if (collision.getStartX() >= stX && collision.getEndX() <= enX && collision.getStartY() <= enY && collision.getEndY() >= enY) {
            return 1;
        }
        if (collision.getStartX() >= stX && collision.getEndX() <= enX && collision.getStartY() <= stY && collision.getEndY() >= stY) {
            return 3;
        }
        if (collision.getStartY() >= stY && collision.getEndY() <= enY && collision.getStartX() <= stX && collision.getEndX() >= stX) {
            return 0;
        }
        if (collision.getStartY() >= stY && collision.getEndY() <= enY && collision.getStartX() <= enX && collision.getEndX() >= enX) {
            return 2;
        }
        return -1;
    }

    public void keepPlayerInsideBorder(Player player) {
        float stX = borderCenterX - (borderTileCntHalfH) * (borderTileWidthH);
        float enX = borderCenterX + (borderTileCntHalfH) * (borderTileWidthH);

        float stY = borderCenterY - (borderTileCntHalfV) * (borderTileWidthH);
        float enY = borderCenterY + (borderTileCntHalfV) * (borderTileWidthH);
        if (player.getCollision().getCenterX() < stX) {
            player.move(stX - player.getCollision().getCenterX(), 0);
        }
        if (player.getCollision().getCenterX() > enX) {
            player.move(enX - player.getCollision().getCenterX(), 0);
        }
        if (player.getCollision().getCenterY() < stY) {
            player.move(0, stY - player.getCollision().getCenterY());
        }
        if (player.getCollision().getCenterY() > enY) {
            player.move(0, enY - player.getCollision().getCenterY());
        }
    }

}
