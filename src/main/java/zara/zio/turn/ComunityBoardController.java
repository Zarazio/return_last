package zara.zio.turn;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ComunityBoardController {
	
	@RequestMapping(value="/comuList", method = RequestMethod.GET)
	public String comuList() {
		
		return "comunityBorad/comuList";
	}
	
	@RequestMapping(value="/qnaList", method = RequestMethod.GET)
	public String QnAList() {
		
		return "comunityBorad/qnaList";
	}
	
}
