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
					hash += (String)LogHash.get(a).get("hash_tag_content") + "��";
				}
			}
			
			for(int b=0; b<LogImage.size(); b++) {
				int imageNum = (int)LogImage.get(b).get("board_code");
				if(listNum == imageNum) {
					image += (String)LogImage.get(b).get("file_content") + "��";
				}
			}
			
			String [] itemA = hash.split("��");
			String [] itemB = image.split("��");
			
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
        }
        
        
		int boardMax = service.maxCode() + 1; // ����� �ִ�
		service.logBoardCreate(vo, boardMax, type); // ��������

		return "redirect:logInfo";
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
