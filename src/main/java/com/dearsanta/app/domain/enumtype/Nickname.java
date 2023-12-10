package com.dearsanta.app.domain.enumtype;

import lombok.Getter;

import java.util.Random;

@Getter
public enum Nickname {
    ADJECTIVE("눈 쌓인 ", "얼어 붙은 ", "울고 있는 ", "선물 받은 ", "행복한 "
            , "불 붙은 ", "반짝이는 ", "소중한 ", "기대하는 ", "배부른 ", "빨간 ", "편안한 ", "도망가는 ", "붙잡힌 "),
    NOUN("루돌프", "산타", "도둑", "사슴", "곰", "진저브레드맨", "트리"
            , "해리", "벽난로", "엘프", "양말", "눈사람", "선물", "엔젤", "케이크"
            , "장갑", "초콜릿", "쿠키", "캔디");

    private final String[] words;
    private static final Random random = new Random();

    Nickname(String... words) {
        this.words = words;
    }

    public static String getRandomNickname() {
        StringBuilder randomNickname = new StringBuilder();
        return randomNickname.append(getRandomWord(ADJECTIVE.words))
                .append(getRandomWord(NOUN.words))
                .toString();
    }

    private static String getRandomWord(String[] words)  {
        int index = random.nextInt(words.length);
        return words[index];
    }

    public static String getDeletedUserNickname() {
        StringBuilder randomNickname = new StringBuilder();
        return randomNickname.append("돌아온 ")
                .append(getRandomWord(NOUN.words))
                .toString();
    }
}
