package com.TillDawn.Model.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regexes {
    UsernameFormat("[a-zA-Z][a-zA-Z0-9_]*"),
    EmailFormat("(?<mail>([a-zA-Z0-9]*\\.[a-zA-Z0-9]*|[a-zA-Z0-9]+))" +
        "@(?<domain>[a-zA-Z0-9]+)\\.com"),
    PasswordStrong("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@%$#&*()_]).*")
    ;

    private final String pattern;

    Regexes(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
