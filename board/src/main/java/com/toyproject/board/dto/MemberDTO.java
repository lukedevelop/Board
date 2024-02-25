package com.toyproject.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String memberName;
    private String memberId;
    private String memberPass;
    private String memberEmail;
}
