package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {

    Member findMemberByEmail(@Param("email")String email);
    Member findMemberByMemberId(@Param("memberId")String memberId);
    Member findMemberByRefreshToken(@Param("refreshToken")String refreshToken);
    void saveMember(Member member);
}
