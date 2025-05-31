package com.TillDawn.Model.Enums.Assets;

public enum SFXs {
    Click("SFX/AudioClip/UI Click 36.wav")
    ;
    private String path;

    SFXs(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
