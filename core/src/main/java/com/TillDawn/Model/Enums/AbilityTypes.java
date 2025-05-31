package com.TillDawn.Model.Enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fasterxml.jackson.annotation.JsonIgnore;

public enum AbilityTypes {
    Vitality("Vitality", true, 10000, "Max HP +1.", "textures/Abilities/Vitality.png"),
    PowerShot("Power shot", false, 10, "Bullet Damage +25%,\n for 10 seconds.", "textures/Abilities/Damager.png"),
    DoubleShot("Double shot", true, 10000, "Projectiles +1.\nSpread +5.", "textures/Abilities/Procrease.png"),
    ArmedAndReady("Armed and Ready", true, 10000, "Max Ammo +5", "textures/Abilities/Amocrease.png"),
    Haste("Haste", false, 10, "Move Speed +100%,\n for 10 seconds.", "textures/Abilities/Speedy.png"),
    ;
    private String name;
    private String iconPath;
    @JsonIgnore
    private TextureRegion iconRegion;
    private String description;
    private float timeLimit;

    AbilityTypes(String name,boolean permanent, float timeLimit, String description, String iconPath) {
        this.name = name;
        this.description = description;
        this.iconPath = iconPath;
        this.timeLimit = timeLimit;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public TextureRegion getIconRegion() {
        if (iconRegion == null) {
            iconRegion = new TextureRegion(new Texture(Gdx.files.internal(iconPath)));
        }
        return iconRegion;
    }

    public String getDescription() {
        return description;
    }

    public float getTimeLimit() {
        return timeLimit;
    }
}
