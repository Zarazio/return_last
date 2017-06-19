package zara.zio.turn;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LogBoardController { // 로그 & 타임라인 컨트롤러 
	
	@RequestMapping(value="/logInfo", method = RequestMethod.GET)
	public String logInfo() {
		
		return "LogBoard/logInfo";
	}
	
	@RequestMapping(value="logWrite", method = RequestMethod.GET)
	public String writeLog(HttpSession session) {
		String username = (String)session.getAttribute("mem");
		String usergrant = (String) session.getAttribute("info");
		
		if(username == null && usergrant == null) {
			return "redirect:login";
		}
		
		return "LogBoard/logWrite";
	}
	
}
