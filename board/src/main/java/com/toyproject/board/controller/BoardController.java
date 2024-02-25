package com.toyproject.board.controller;

import com.toyproject.board.dto.BoardDTO;
import com.toyproject.board.dto.BoardFileDTO;
import com.toyproject.board.service.BoardService;
import com.toyproject.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/save") // 주소. (게시글 작성 화면을 띄우기 위한 메서드)
    public String save() {
        return "save"; // return 할 화면 이름(html)
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDto) throws IOException {
        log.info("boardDto = " + boardDto);
        boardService.save(boardDto);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String findAll(Model model, HttpSession session) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        Integer byIdCount = boardService.findByIdCount((String)session.getAttribute("loginId"));
        model.addAttribute("findByIdCount", byIdCount);
        log.info("session.loginId : "+session.getAttribute("loginId")+"");

        log.info("boardDTOList = " + boardDTOList);
        return "list";
    }

    @GetMapping("/{id}") //id로 게시글 찾겠다.
    public String findById(@PathVariable("id") Long id, Model model) {
        //조회수 처리
        boardService.updateHits(id);

        //상세내용 가져오기
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        log.info("boardDTO = " + boardDTO);

        //첨부파일이 있으면 내용을 가져오는 조건문
        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, Model model) {
        boardService.update(boardDTO);
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }
}
