package com.toyproject.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private String createdAt;

    // 파일첨부 필드
    private int fileAttached; //게시글에 파일첨부가 됐는지 안됐는지 판단하는 필드
    private List<MultipartFile> boardFile; //게시글을 작성할때 파일 자체를 담기위한 필드

}
