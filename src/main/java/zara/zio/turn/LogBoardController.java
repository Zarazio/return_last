package zara.zio.turn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.persistence.LogBoardService;
import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;


@Controller
public class LogBoardController { // 로그 & 타임라인 컨트롤러 
	
	private static final Logger logger =
			LoggerFactory.getLogger(LogBoardController.class);
	
	@Inject
	private LogBoardService service; 
	
	// 경로 지정 path
	@Resource(name="logsPath")
	private String logsPath;
	
	@RequestMapping(value="/logInfo", method = RequestMethod.GET)
	public String logInfo() {
		
		return "LogBoard/logInfo";
	}
	
	@ResponseBody
	@RequestMapping(value="/logList", method = RequestMethod.POST)
	public List<LogBoardVO> logList(int logType, int startRecord, int recordTimeline) throws Exception {
		
		List<LogBoardVO> list = service.logInfoRead(logType, startRecord, recordTimeline);
		List<Map<String,Object>> LogHash = service.logHashRead();
		List<Map<String,Object>> LogImage = service.logImageFileRead();
		
		for(int i=0; i<list.size(); i++) {
			
			String hash = "";
			String image = "";
			
			int listNum = list.get(i).getBoard_code();

			for(int a=0; a<LogHash.size(); a++) {
				int hashNum = (int)LogHash.get(a).get("board_code");
				if(listNum == hashNum) {
					hash += (String)LogHash.get(a).get("hash_tag_content") + "◆";
				}
			}
			
			for(int b=0; b<LogImage.size(); b++) {
				int imageNum = (int)LogImage.get(b).get("board_code");
				if(listNum == imageNum) {
					image += (String)LogImage.get(b).get("file_content") + "◆";
				}
			}
			
			String [] itemA = hash.split("◆");
			String [] itemB = image.split("◆");
			
			list.get(i).setHash_tag_content(itemA);
			list.get(i).setFile_content(itemB);
			
		}
		
		return list;
		
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
	
	@RequestMapping(value="logWrite", method = RequestMethod.POST)
	public String writeLog(HttpSession session, LogBoardVO vo) throws Exception {
		
		String userId = (String)session.getAttribute("mem"); // 작성자아이디 정보
		vo.setUser_id(userId); // 작성자아이디 	
		vo.setBoard_type_code(1); // 로그작성 log 1번
		
		// 문자열 앱 파싱 =====================================
		String basic = vo.getBoard_content();		
        int idx, idx1, tokencount = 0; // 토큰의 수
		StringTokenizer s = new StringTokenizer(basic,"<>");
        tokencount =  s.countTokens(); // 토큰사이즈 변수에 적용
        String[] token_arr = new String[tokencount]; // 토큰의 값을 담을 배열
        
        for(int i = 0; i < tokencount; i++){
            token_arr[i] = s.nextToken();

             if(token_arr[i].contains("img")){
            	 idx = token_arr[i].indexOf("\"");
                 token_arr[i] = token_arr[i].substring(idx+1);
                 idx1 = token_arr[i].indexOf("\"");
                 token_arr[i] = token_arr[i].substring(0,idx1);
            	 basic = basic.replace(token_arr[i], "");
             }
        }
        
        vo.setBoard_content(basic);
        System.out.println(basic);
        // 문자열 앱 파싱 =====================================
		
		int boardMax = service.maxCode() + 1; // 등록할 최댓값
		service.logBoardCreate(vo, boardMax); // 파일정보

		return "redirect:logInfo";
	}
	
	// log-image 업로드  
	@RequestMapping(value="/logs", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> logsAjax(MultipartFile file) throws Exception {
		
		logger.info("LogName : " + file.getOriginalFilename()); // 파일명
		
		return new ResponseEntity<String>(UploadFileUtils.uploadFile(logsPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
	// log-image 표시맵핑
	@ResponseBody
	@RequestMapping("/displayLogs") 
	public ResponseEntity<byte[]> displayLogs(String fileName) throws Exception {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("DisplayLogs FILE NAME : " + fileName);
		
		try {
			// 확장자를 추출하여 formatName에 저장
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// 추출한 확장자를 MediaUtils클래스에서  이미지파일여부를 검사하고 리턴받아 mType에 저장
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// 헤더 구성 객체(외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream 생성
			in = new FileInputStream(logsPath+fileName);
			
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
	
	// log-image 삭제맵핑
	@ResponseBody
	@RequestMapping(value="/deleteLogs", method=RequestMethod.POST)
	public ResponseEntity<String> deleteProfile(String fileName) {
		
		logger.info("deleteLogs : " + fileName);
		
		// 파일의 확장자 추출
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// 이미지 파일 여부 검사
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		// 이미지의 경우(썸네일 + 원본파일 삭제), 이미지가 아니면 원본파일만 삭제
        // 이미지 파일이면
		if(mType != null) {
			String che = "/" + fileName.substring(3);
			// 썸네일 이미지 삭제
			new File(logsPath + (che).replace('/', File.separatorChar)).delete();
		} 
		// 원본 파일 삭제

		new File(logsPath + fileName.replace('/', File.separatorChar)).delete();
		
		// 데이터와 http 상태 코드 전송
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
}
