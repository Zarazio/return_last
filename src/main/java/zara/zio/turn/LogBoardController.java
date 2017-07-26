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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.persistence.LogBoardService;
import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;


@Controller
public class LogBoardController { // �α� & Ÿ�Ӷ��� ��Ʈ�ѷ� 
	
	private static final Logger logger =
			LoggerFactory.getLogger(LogBoardController.class);
	
	@Inject
	private LogBoardService service; 
	
	// ��� ���� path
	@Resource(name="logsPath")
	private String logsPath;
	
	@RequestMapping(value="/logInfo", method = RequestMethod.GET)
	public String logInfo() {
		
		return "LogBoard/logInfo";
	}
	
	@ResponseBody
	@RequestMapping(value="/logList", method = RequestMethod.POST)
	public List<LogBoardVO> logList(int logType, int startRecord, int recordTimeline, HttpSession session) throws Exception {
		
		String my = (String)session.getAttribute("mem");
		
		List<LogBoardVO> list = service.logInfoRead(logType, startRecord, recordTimeline, my);
		
		return list;
		
	}
	
	@RequestMapping(value="logWrite", method = RequestMethod.GET)
	public String writeLog(HttpSession session, RedirectAttributes rttr) {
		String username = (String)session.getAttribute("mem");
		String usergrant = (String) session.getAttribute("info");
		
		if(username == null && usergrant == null) {
			rttr.addAttribute("board","1");
			return "redirect:login";
		}
		
		return "LogBoard/logWrite";
	}
	
	@RequestMapping(value="logWrite", method = RequestMethod.POST)
	public String writeLog(HttpSession session, LogBoardVO vo, String [] cache_content) throws Exception {
		
		String userId = (String)session.getAttribute("mem"); // �ۼ��ھ��̵� ����
		vo.setUser_id(userId); // �ۼ��ھ��̵� 	
		vo.setBoard_type_code(1); // �α��ۼ� log 1��

		
		// ���ڿ� �� �Ľ� =====================================
		String basic = vo.getBoard_content();		
	    int idx, idx1, tokencount = 0; // ��ū�� ��
	    StringTokenizer s = new StringTokenizer(basic,"<>");
	    tokencount =  s.countTokens(); // ��ū������ ������ ����
	    String[] token_arr = new String[tokencount]; // ��ū�� ���� ���� �迭
	    for(int i = 0; i < tokencount; i++) {
            token_arr[i] = s.nextToken();

            if(token_arr[i].contains("src")){
            	idx = token_arr[i].indexOf("src=\"") + 4;
                token_arr[i] = token_arr[i].substring(idx+1);
                idx1 = token_arr[i].indexOf("\"");
                token_arr[i] = token_arr[i].substring(0,idx1);
            	basic = basic.replace(token_arr[i], "");
            }
	    }     
			
        vo.setBoard_content(basic);
        System.out.println(basic);
        // ���ڿ� �� �Ľ� =====================================
		
        int type = 1;
        
        if(vo.getFile_content() == null) {
        	System.out.println("��ĳġ");
        } else {
	        if(vo.getFile_content()[0].contains(".youtube")) {
	        	type = 2;
	        } else if(vo.getFile_content()[0].contains(".kml")) {
	        	type = 3;
	        } 
	        
	        // ĳ�̵����� ����ó��
 			String [] one = vo.getFile_content();
 			
 			for(int i=0; i<cache_content.length; i++) {
 				int count = 0;
 				for(int j=0; j<one.length; j++) {
 					if(!(cache_content[i].equals(one[j]))) {
 						count++;
 					}
 					if(one.length == count) {
 						deleteLogs(cache_content[i]);
 					}
 				}
 			}
	        
        }
        
        Map<String,Object> map = service.maxCode(); // ����� �ִ�
        int max = 0;
        int count = Integer.parseInt(map.get("count").toString());
        
		if(count == 0) {
			 max = 1;
		} else {
			 max = Integer.parseInt(map.get("max").toString()) + 1;
		}
       
		vo.setBoard_code(max);
		service.logBoardCreate(vo, max, type); // ��������

		return "redirect:logInfo";
	}
	
	
	@ResponseBody
	@RequestMapping(value="viewCount", method = RequestMethod.GET)
	public int viewCount(int no, int state) throws Exception {
		
		int view = service.view(no, state);
		
		return view;
		
	}
	
	@ResponseBody
	@RequestMapping(value="likeConfirm", method = RequestMethod.GET)
	public int likeConfirm(int states, int no, HttpSession session) throws Exception {
		
		String users = (String) session.getAttribute("mem");
		LikesVO vo = new LikesVO();
		
		vo.setBoard_code(no);
		vo.setUser_id(users);
		
		int likes = service.LikeUpDown(vo, states);
		
		return likes;
		
	}
	
	@ResponseBody
	@RequestMapping(value="replyAll", method = RequestMethod.GET)
	public List<LogBoardVO> replyAll(int no, HttpSession session) throws Exception {
		
		String users = (String) session.getAttribute("mem");
		List<LogBoardVO> list = service.replyList(no, users);
		
		return list;
		
	}
	
	@ResponseBody
	@RequestMapping(value="replyGo", method = RequestMethod.POST)
	public List<LogBoardVO> replyGo(int replytype, int no, int replyno, String text, HttpSession session) throws Exception {
		
		String users = (String) session.getAttribute("mem");
		
		List<LogBoardVO> list = service.replyCommand(replytype, no, replyno, text, users);
		
		return list;
	}
	
	
	
	
	
	
	// log-image ���ε�  
	@RequestMapping(value="/logs", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> logsAjax(MultipartFile file) throws Exception {
		
		logger.info("LogName : " + file.getOriginalFilename()); // ���ϸ�
		
		return new ResponseEntity<String>(UploadFileUtils.uploadFile(logsPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
	// log-image ǥ�ø���
	@ResponseBody
	@RequestMapping("/displayLogs") 
	public ResponseEntity<byte[]> displayLogs(String fileName) throws Exception {
		// ������ ������ �ٿ�ε��ϱ� ���� ��Ʈ��
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("DisplayLogs FILE NAME : " + fileName);
		
		try {
			// Ȯ���ڸ� �����Ͽ� formatName�� ����
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// ������ Ȯ���ڸ� MediaUtilsŬ��������  �̹������Ͽ��θ� �˻��ϰ� ���Ϲ޾� mType�� ����
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// ��� ���� ��ü(�ܺο��� �����͸� �ְ���� ������ header�� body�� �����ؾ��ϱ� ������)
			HttpHeaders headers = new HttpHeaders();
			
			// InputStream ����
			in = new FileInputStream(logsPath+fileName);
			
			if(mType != null) { // �̹��� �����϶� 
				headers.setContentType(mType);
			} else { // �̹��������� �ƴҶ�
				fileName = fileName.substring(fileName.indexOf("_")+1);
				
				// �ٿ�ε�� ����Ʈ Ÿ������ application/octet-stream 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				
				// ����Ʈ�迭�� ��Ʈ������ : 
				// new String(fileName.getBytes("utf-8"),"iso-8859-1") * iso-8859-1 ���������, ū ����ǥ ���ο�  " \" ���� \" "
                // ������ �ѱ� ���� ����
				headers.add("Content-Disposition", "attachment; filename=\"" + 
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
				//headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
			}
			
			// ����Ʈ �迭, ���, HTTP �����ڵ� 
			// ������Ͽ��� �����͸� �о�� IOUtils�� toByteArray()�޼ҵ� 
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); 
				
		} catch(Exception e) {
			e.printStackTrace();
			
			// HTTP���� �ڵ�()
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	
	// log-image ��������
	@ResponseBody
	@RequestMapping(value="/deleteLogs", method=RequestMethod.POST)
	public ResponseEntity<String> deleteLogs(String fileName) {
		
		logger.info("deleteLogs : " + fileName);
		
		// ������ Ȯ���� ����
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// �̹��� ���� ���� �˻�
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		// �̹����� ���(����� + �������� ����), �̹����� �ƴϸ� �������ϸ� ����
        // �̹��� �����̸�
		if(mType != null) {
			String che = "/" + fileName.substring(3);
			// ����� �̹��� ����
			new File(logsPath + (che).replace('/', File.separatorChar)).delete();
		} 
		// ���� ���� ����

		new File(logsPath + fileName.replace('/', File.separatorChar)).delete();
		
		// �����Ϳ� http ���� �ڵ� ����
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
}
