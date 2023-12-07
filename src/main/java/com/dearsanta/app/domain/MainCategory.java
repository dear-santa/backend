package com.dearsanta.app.domain;

import com.dearsanta.app.dto.MainCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum MainCategory {

    HOME("홈 화면", "Dear Santa에 오신 걸 환영합니다", "\uD83C\uDF1F"),
    MEMORY("크리스마스 추억", "소중한 추억을 공유해요", "\uD83C\uDF84"),
    PRESENT("크리스마스 선물", "친구,연인,가족에게 줄 선물을 공유해요", "\uD83C\uDF81"),
    RESTAURANT("크리스마스 식당", "특별한 날을 보낼 식당을 공유해요", "\uD83C\uDF77"),
    MY_PAGE("나의 활동", "나의 활동을 볼 수 있어요", "\uD83C\uDF85\uD83C\uDFFB");

    private String korean;
    private String subtitle;
    private String emoji;

    public static List<MainCategoryDto> getCategoryVos() {
        return Arrays.stream(MainCategory.values())
                .map(c -> MainCategoryDto.builder()
                        .mainCategory(c)
                        .korean(c.korean)
                        .emoji(c.emoji)
                        .subtitle(c.subtitle).build())
                .collect(Collectors.toUnmodifiableList());
    }
}