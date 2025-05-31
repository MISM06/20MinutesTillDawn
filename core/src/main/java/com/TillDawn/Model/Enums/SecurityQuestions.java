package com.TillDawn.Model.Enums;

public enum SecurityQuestions {
    Q1("When is your birthday?"),
    Q2("What is your first school name?"),
    Q3("What is your father name?")
    ;
    private String question;

    SecurityQuestions(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return question;
    }
}
