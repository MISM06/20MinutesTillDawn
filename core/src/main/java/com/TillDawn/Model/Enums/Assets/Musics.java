package com.TillDawn.Model.Enums.Assets;

import com.badlogic.gdx.utils.Array;

public enum Musics {
    PreGame1("musics/pre_game/Little_Nightmares_II.mp3", "little nightmares", true),
    PreGame2("musics/pre_game/Lure_of_the_Maw.mp3", "lure of the Maw", true),
    PreGame3("musics/pre_game/Prison_Toys.mp3", "prison toys", true),
    PreGame4("musics/pre_game/Togetherness_1.mp3", "togetherness", true),
    PreGame5("musics/pre_game/XL-TT.mp3", "XL-TT", true),
    PreGame6("musics/pre_game/Sealed_Vessel.mp3", "Sealed Vessel", true),
    PreGame7("musics/pre_game/City_of_tears.mp3", "City of Tears", true),
    PreGame8("musics/pre_game/Murder_berserk.mp3", "Murder", true),
    PreGame9("musics/pre_game/Fear_berserk.mp3", "Fear", true),
    ;
    private String path;
    private String name;
    boolean preGame;

    Musics(String path, String name, boolean preGame) {
        this.path = path;
        this.name = name;
        this.preGame = preGame;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public boolean isPreGame() {
        return preGame;
    }


    @Override
    public String toString() {
        return this.name;
    }

    public static Array<Musics> getPreGames() {
        Array<Musics> items = new Array<>();
        for (Musics value : values()) {
            if (value.isPreGame()) {
                items.add(value);
            }
        }
        return items;
    }

    public static Musics getMusicByPath(String path) {
        for (Musics value : values()) {
            if (value.getPath().equals(path)) {
                return value;
            }
        }
        return null;
    }
}
