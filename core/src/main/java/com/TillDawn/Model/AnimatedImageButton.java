package com.TillDawn.Model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class AnimatedImageButton extends ImageButton {
    private Animation<TextureRegion> selectedAnim;
    private Animation<TextureRegion> unselectedAnim;

    private float stateTime = 0f;

    private float targetScale = 1f;
    private float currentScale = 1f;

    private float targetY = 0f;
    private float originalY = 0f;

    public AnimatedImageButton(Animation<TextureRegion> unselectedAnim,
                               Animation<TextureRegion> selectedAnim,
                               ImageButtonStyle style) {
        super(style);
        this.unselectedAnim = unselectedAnim;
        this.selectedAnim = selectedAnim;

        setTransform(true);
        setOrigin(Align.center);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        originalY = getY();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Animation<TextureRegion> currentAnim = isChecked() ? selectedAnim : unselectedAnim;

        stateTime += delta;
        TextureRegion frame = currentAnim.getKeyFrame(stateTime, true);
        getStyle().imageUp = new TextureRegionDrawable(frame);

        if (isPressed()) {
            targetScale = 0.9f;
            targetY = originalY - 0.03f;
        } else if (isOver()) {
            targetScale = 1.2f;
            targetY = originalY + 0.05f;
        } else {
            targetScale = 1.0f;
            targetY = originalY;
        }

        currentScale = MathUtils.lerp(currentScale, targetScale, 10f * delta);
        float newY = MathUtils.lerp(getY(), targetY, 10f * delta);
        setScale(currentScale);
        setY(newY);
    }
}

