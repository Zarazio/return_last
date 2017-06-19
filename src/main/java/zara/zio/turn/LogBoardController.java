package zara.zio.turn;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LogBoardController { // �α� & Ÿ�Ӷ��� ��Ʈ�ѷ� 
	
	@RequestMapping(value="/logInfo", method = RequestMethod.GET)
	public String logInfo() {
		
		return "LogBoard/logInfo";
	}
	
	@RequestMapping(value="writeLog", method = RequestMethod.GET)
	public String writeLog(HttpSession session) {
		String username = (String)session.getAttribute("mem");
		String usergrant = (String) session.getAttribute("info");
		
		System.out.println(username);
		System.out.println(usergrant);
		
		return "redirect:main";
	}
	
}
