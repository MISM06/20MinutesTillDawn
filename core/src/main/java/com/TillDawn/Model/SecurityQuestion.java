package com.TillDawn.Model;

import com.TillDawn.Model.Enums.SecurityQuestions;

public class SecurityQuestion {
    SecurityQuestions question;
    String answer;

    public SecurityQuestion(SecurityQuestions question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public SecurityQuestion() {
    }

    public SecurityQuestions getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
