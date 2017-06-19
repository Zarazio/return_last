package zara.zio.turn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.persistence.MemberService;
import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;

@Controller
public class MyUserController {
	
	private static final Logger logger =
			LoggerFactory.getLogger(MyUserController.class);
	
	@Inject
	private MemberService service;
	
	// 경로 지정 path
	@Resource(name="profilePath")
	private String profilePath;
	
	@RequestMapping(value="/myinfo", method = RequestMethod.GET)
	public String myInfo() {
		
		return "userPage/myInfo";
	}
	
	@RequestMapping(value="/myModify", method = RequestMethod.GET)
	public String myModify(HttpSession session, Model model) throws Exception {
		
		String myName = (String)session.getAttribute("mem");
		MemberVO userInfo = service.read(myName); // 유저에 대한 모든정보
		model.addAttribute("my",userInfo);
		
		return "userPage/myModify";
	}
	
	@RequestMapping(value="/myModify", method = RequestMethod.POST)
	public String myModify(MemberVO vo, String nowid, HttpSession session) throws Exception {
		
		System.out.println(nowid); // 원본아이디쿼리 셀렉팅
		System.out.println(vo);
		
		String yyyy = vo.getYyyy();
		String mm = vo.getMm();
		String dd = vo.getDd();
		String birth = yyyy + "-" + mm + "-" + dd;
		vo.setUser_birth(birth);
		
		service.modify(vo, nowid);
		String newid = vo.getUser_id();
		session.setAttribute("mem", newid);
		
		return "redirect:myinfo";
	}
	
	@RequestMapping(value="/myDelete", method = RequestMethod.GET)
	public String myDelete(HttpSession session) throws Exception {
		
		String userid = (String)session.getAttribute("mem");
		service.remove(userid);
		session.invalidate();
		
		return "redirect:main";
	}
	
	
	// 프로필이미지 업로드
	@RequestMapping(value="/profile", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> profileAjax(MultipartFile file) throws Exception {
		
		logger.info("ProfileName : " + file.getOriginalFilename()); // 파일명
		
		return new ResponseEntity<String>(UploadFileUtils.uploadFile(profilePath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
	// 프로필 이미지 표시 맵핑
	@ResponseBody
	@RequestMapping("/displayProfile") 
	public ResponseEntity<byte[]> displayProfile(String fileName) throws Exception {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("DisplayProfile FILE NAME : " + fileName);
		
		try {
			// 확장자를 추출하여 formatName에 저장
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// 추출한 확장자를 MediaUtils클래스에서  이미지파일여부를 검사하고 리턴받아 mType에 저장
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// 헤더 구성 객체(외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream 생성
			in = new FileInputStream(profilePath+fileName);
			
			if(mType != null) { // 이미지 파일일때 
				headers.setContentType(mType);
			} else { // 이미지파일이 아닐때
				fileName = fileName.substring(fileName.indexOf("_")+1);
				
				// 다운로드용 컨텐트 타입지정 application/octet-stream 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				
				// 바이트배열을 스트링으로 : 
				// new String(fileName.getBytes("utf-8"),"iso-8859-1") * iso-8859-1 서유럽언어, 큰 따옴표 내부에  " \" 내용 \" "
                // 파일의 한글 깨짐 방지
				headers.add("Content-Disposition", "attachment; filename=\"" + 
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
				//headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
			}
			
			// 바이트 배열, 헤더, HTTP 상태코드 
			// 대상파일에서 데이터를 읽어내는 IOUtils의 toByteArray()메소드 
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); 
				
		} catch(Exception e) {
			e.printStackTrace();
			
			// HTTP상태 코드()
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteProfile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteProfile(String fileName) {
		
		logger.info("deleteProfile : " + fileName);
		
		// 파일의 확장자 추출
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// 이미지 파일 여부 검사
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		// 이미지의 경우(썸네일 + 원본파일 삭제), 이미지가 아니면 원본파일만 삭제
        // 이미지 파일이면
		if(mType != null) {
			String che = "/" + fileName.substring(3);
			// 썸네일 이미지 삭제
			new File(profilePath + (che).replace('/', File.separatorChar)).delete();
		} 
		// 원본 파일 삭제

		new File(profilePath + fileName.replace('/', File.separatorChar)).delete();
		
		// 데이터와 http 상태 코드 전송
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		
	}
	
}
