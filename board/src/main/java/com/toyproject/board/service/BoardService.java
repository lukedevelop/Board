package com.toyproject.board.service;

import com.toyproject.board.dto.BoardDTO;
import com.toyproject.board.dto.BoardFileDTO;
import com.toyproject.board.dto.MemberDTO;
import com.toyproject.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //파일 기능 없을때 기본 게시판 기능 로직
   /* public void save(BoardDTO boardDto) {
        boardRepository.save(boardDto);
    }*/

    public void save(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            //파일 없다.
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            //파일 있다.
            boardDTO.setFileAttached(1);
            //게시글 저장 후 id값 활용을 위해 리턴 받음.
            BoardDTO saveBoard = boardRepository.save(boardDTO);
            //파일만 따로 가져오기
//            MultipartFile boardFile = boardDTO.getBoardFile();  : 단일 파일

            //다중파일 가져오기
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {

            //파일 이름 가져오기
            String originalFilename = boardFile.getOriginalFilename();
            System.out.println("originalFilename = " + originalFilename);
            //저장용 이름 만들기
            System.out.println(System.currentTimeMillis());
            String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
            System.out.println("storedFileName = " + storedFileName);
            //BoardFileDTO 세팅
            BoardFileDTO boardFileDTO = new BoardFileDTO();
            boardFileDTO.setOriginalFileName(originalFilename);
            boardFileDTO.setStoredFileName(storedFileName);
            boardFileDTO.setBoardId(saveBoard.getId());
            //파일 저장용 폴더에 파일 저장 처리
            String savePath = "C:/Users/whgml/spring_upload_files/" + storedFileName;
            boardFile.transferTo(new File(savePath)); // savePath 경로에 파일이름으로 넘김.
            //board_file_table 저장 처리
            boardRepository.saveFile(boardFileDTO);
            }

        }
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public int findByIdCount(String id) {
        return boardRepository.findByIdCount(id);
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
