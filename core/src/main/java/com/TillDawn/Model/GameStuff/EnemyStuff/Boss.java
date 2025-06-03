package com.TillDawn.Model.GameStuff.EnemyStuff;

import com.TillDawn.Model.Enums.EnemyType;
import com.TillDawn.Model.GameStuff.Bullet;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.nio.Buffer;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "bossType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ShubNiggurath.class, name = "shubniggurath")
})

public abstract class Boss extends Enemy{
    public Boss(EnemyType type, float centerX, float centerY) {
        super(type, centerX, centerY);
    }

    public Boss() {
    }

    public abstract void startBossFight();
    public abstract void update(float delta);
    public abstract void takeDamage(Bullet bullet);
    public abstract void endBossFight();
}
