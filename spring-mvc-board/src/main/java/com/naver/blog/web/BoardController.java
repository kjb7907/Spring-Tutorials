package com.naver.blog.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naver.blog.service.Board;
import com.naver.blog.service.BoardService;



@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	//상세화면
	@RequestMapping(value = "/board/boardView")
	public String boardView(Model model,
			@RequestParam(value = "boardNo") int boardNo){
		
		System.out.println("Controller boardView 시작 ");
			System.out.println("boardNo : "+boardNo);
			model.addAttribute(boardService.boardView(boardNo));
			System.out.println("Controller boardView 끝");
		return "/board/boardView";
	}
	
	//수정화면
	@RequestMapping(value = "/board/boardModify")
	public String boardModify(){
		
		return "redirect:/board/boardView?boardNo=1";
	}
	
	//삭제화면
	@RequestMapping(value = "/board/boardRemove", method = RequestMethod.GET)
	public String boardRemove(Model model,
			@RequestParam(value = "boardNo") int boardNo){
		model.addAttribute("boardNo",boardNo);
		return "/board/boardRemove";	
	}
	//삭제처리
	@RequestMapping(value = "/board/boardRemove", method = RequestMethod.POST)
	public String boardRemove(Board board){
		System.out.println(board.getBoardPw());
		int result = boardService.removeBoard(board);

			return "redirect:/board/boardList";	

	}
	//리스트
	@RequestMapping(value="/board/boardList")
	public String boardList(Model model,	//model = map을 상속받은 spring model
			@RequestParam(value="currentPage", defaultValue="1") int currentPage){
		Map<String, Object> returnMap = boardService.getBoardListPerCurrentPage(currentPage);
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("totalRowCount",returnMap.get("totalRowCount"));
		model.addAttribute("lastPage",returnMap.get("lastPage"));
		model.addAttribute("list",returnMap.get("list"));
	
		return "/board/boardList";
	}
	//입력처리
	@RequestMapping(value="/board/boardAdd", method=RequestMethod.POST)
	public String boardAdd(Board board){
		boardService.addBoard(board);
		return "redirect:/board/boardList";	
	}
	//입력화면
	@RequestMapping(value="/board/boardAdd", method=RequestMethod.GET)
	public String boardAdd(){
		return "/board/boardAdd";	
	}
}




