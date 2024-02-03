package com.example.board.domain.member;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {

    private Long id;
    private String loginId;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    public MemberDTO(Long id, String loginId, String password, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static MemberDTO toMemberDTO(Member member) {
        return new MemberDTO().builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .createdDate(member.getCreatedDate())
                .lastModifiedDate(member.getLastModifiedDate())
                .build();
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
