package com.dearsanta.app.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserListDto {
    List<SantaUserDto> userList;
}
