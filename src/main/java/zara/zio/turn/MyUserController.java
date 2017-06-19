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
	
	// ��� ���� path
	@Resource(name="profilePath")
	private String profilePath;
	
	@RequestMapping(value="/myinfo", method = RequestMethod.GET)
	public String myInfo() {
		
		return "userPage/myInfo";
	}
	
	@RequestMapping(value="/myModify", method = RequestMethod.GET)
	public String myModify(HttpSession session, Model model) throws Exception {
		
		String myName = (String)session.getAttribute("mem");
		MemberVO userInfo = service.read(myName); // ������ ���� �������
		model.addAttribute("my",userInfo);
		
		return "userPage/myModify";
	}
	
	@RequestMapping(value="/myModify", method = RequestMethod.POST)
	public String myModify(MemberVO vo, String nowid, HttpSession session) throws Exception {
		
		System.out.println(nowid); // �������̵����� ������
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
	
	
	// �������̹��� ���ε�
	@RequestMapping(value="/profile", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> profileAjax(MultipartFile file) throws Exception {
		
		logger.info("ProfileName : " + file.getOriginalFilename()); // ���ϸ�
		
		return new ResponseEntity<String>(UploadFileUtils.uploadFile(profilePath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
	// ������ �̹��� ǥ�� ����
	@ResponseBody
	@RequestMapping("/displayProfile") 
	public ResponseEntity<byte[]> displayProfile(String fileName) throws Exception {
		// ������ ������ �ٿ�ε��ϱ� ���� ��Ʈ��
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("DisplayProfile FILE NAME : " + fileName);
		
		try {
			// Ȯ���ڸ� �����Ͽ� formatName�� ����
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// ������ Ȯ���ڸ� MediaUtilsŬ��������  �̹������Ͽ��θ� �˻��ϰ� ���Ϲ޾� mType�� ����
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// ��� ���� ��ü(�ܺο��� �����͸� �ְ���� ������ header�� body�� �����ؾ��ϱ� ������)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream ����
			in = new FileInputStream(profilePath+fileName);
			
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
	
	
	@ResponseBody
	@RequestMapping(value="/deleteProfile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteProfile(String fileName) {
		
		logger.info("deleteProfile : " + fileName);
		
		// ������ Ȯ���� ����
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// �̹��� ���� ���� �˻�
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		// �̹����� ���(����� + �������� ����), �̹����� �ƴϸ� �������ϸ� ����
        // �̹��� �����̸�
		if(mType != null) {
			String che = "/" + fileName.substring(3);
			// ����� �̹��� ����
			new File(profilePath + (che).replace('/', File.separatorChar)).delete();
		} 
		// ���� ���� ����

		new File(profilePath + fileName.replace('/', File.separatorChar)).delete();
		
		// �����Ϳ� http ���� �ڵ� ����
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		
	}
	
}
