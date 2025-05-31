package com.TillDawn.Model.GameStuff;

import com.TillDawn.Model.Enums.AbilityTypes;

import java.util.ArrayList;
import java.util.Arrays;

public class AbilityManager {
    private ArrayList<AbilityTypes> temporaries = new ArrayList<>();
    private ArrayList<Float> timeAdded = new ArrayList<>();
    private boolean[] hasSeen = new boolean[AbilityTypes.values().length];

    public AbilityManager() {
        Arrays.fill(hasSeen, false);
    }

    public void add(AbilityTypes ability) {
        Player player = Game.getInstance().getPlayer();
        Gun gun = player.getGun();
        hasSeen[ability.ordinal()] = true;
        switch (ability) {
            case Haste:
                temporaries.add(AbilityTypes.Haste);
                timeAdded.add(Game.getInstance().getTimePassed());
                player.setSpeed(
                    player.getSpeed() * 2);
                break;
            case DoubleShot:
                gun.setProjectile(
                    gun.getProjectile() + 1);
                gun.setSpreedDeg(
                    gun.getSpreedDeg() + 5);
                break;
            case Vitality:
                player.setMaxHp(player.getMaxHp() + 1);
                player.setHp(player.getHp() + 1);
                break;
            case ArmedAndReady:
                gun.setMaxAmmo(
                    gun.getMaxAmmo() + 5);
                break;
            case PowerShot:
                temporaries.add(AbilityTypes.PowerShot);
                timeAdded.add(Game.getInstance().getTimePassed());
                gun.setDamage(
                    gun.getDamage() * 1.25f);
                break;
        }
    }
    public void update() {
        Player player = Game.getInstance().getPlayer();
        Gun gun = player.getGun();
        for (int i = temporaries.size() - 1; i >= 0; i--) {
            AbilityTypes ability = temporaries.get(i);
            Float time = timeAdded.get(i);
            if (Game.getInstance().getTimePassed() - time >= ability.getTimeLimit()) {
                temporaries.remove(i);
                timeAdded.remove(i);
                switch (ability) {
                    case Haste:
                        player.setSpeed(
                            player.getSpeed() / 2);
                        break;
                    case PowerShot:
                        gun.setDamage(
                            gun.getDamage() * 0.8f);
                        break;
                }
            }
        }
    }

    public boolean[] getHasSeen() {
        return hasSeen;
    }
}
