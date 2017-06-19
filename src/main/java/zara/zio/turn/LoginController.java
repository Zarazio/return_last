package zara.zio.turn;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.persistence.MemberService;

@Controller
public class LoginController {
	
	@Inject
	private MemberService service;
	
	// 로그인 정보 아이디 & 패스워드 체크 (내부처리)
	@RequestMapping(value="user_check")
	@ResponseBody // 회원 로그인정보확인 (내부처리)
	public String userConfirm(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String value = "";
		String checkpass = "";
		
		try {
			
			checkpass = service.userin(id);
			
			if(checkpass == null) {
				value = "0";
			} else {
				if(checkpass.equals(pass))
					value = "1";
				else 
					value = "0";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	// 로그인 폼이동 
	@RequestMapping(value="login", method = RequestMethod.GET)
	public String loginForm(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String info = (String)session.getAttribute("info");
		// 로그인후 다시 로그인창으로 이동할시
		if(info == "admin") { // 관리자  
			return "mHome";
		}
		else if (info == "user"){ // 일반회원
			return "uHome";
		}
		
		return "loginForm/login";
	}
	
	// 메인페이지 이동 컨트롤러
	@RequestMapping(value="main", method = RequestMethod.GET)
	public String layoutForm(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		String info = (String)session.getAttribute("info");
		// 로그인후 다시 로그인창으로 이동할시
		if(info == "admin") { // 관리자  
			return "mHome";
		}
		else if (info == "user"){ // 일반회원
			return "uHome";
		}
		
		return "layout";
	}
	
	//로그인 정보전송 세션활성화 
	@RequestMapping (value="main", method=RequestMethod.POST) // 로그인이동
	public String Login(HttpSession session, HttpServletRequest request, HttpServletResponse response, MemberVO mem) throws Exception {
		
		String id = mem.getUser_id();
		session.setAttribute("mem", id); // 유저 아이디만 세션로 전송 
		
		if(id.equals("manager")) { // 매니저 로그인 
			session.setAttribute("info", "admin"); // 관리자 jstl정보
			return "mHome";
		}
		
		// 일반회원 로그인
		session.setAttribute("info", "user"); // 유저 jstl정보
		return "uHome";
	}
	
	@RequestMapping (value="logout")
	public String Logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		session.invalidate(); // 세션 로그아웃
		
		return "redirect:login"; // 로그아웃했을시 로그아웃 벨류에서 다시 로그인 벨류로 리다이렉트 
	}
}
