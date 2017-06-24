package zara.zio.turn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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

import com.google.gson.Gson;

import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;
import zara.zio.turn.persistence.GroupTravelService;
import zara.zio.turn.persistence.MemberService;
import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;

@Controller
public class MyUserController {
	
	private static final Logger logger =
			LoggerFactory.getLogger(MyUserController.class);
	
	@Inject
	private MemberService service;
	
	@Inject 
	private GroupTravelService service1 ;
	
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
	
	// 사용자가 계획한 여행일정을 모두 뽑아옴
	@RequestMapping(value="userScheduleList", method=RequestMethod.GET)
	public String userScheduleList(HttpSession session, Model model , String state) throws Exception{
		
		String mem = (String) session.getAttribute("mem");
		List<GroupVO> list ; ;
		if(state == "finish"){
			list = service1.groupFinishList(mem) ;
			
		}else{
			list = service1.groupGoingList(mem) ;
		}
		System.out.println(state + " : state");
		
		model.addAttribute("group",list);
		
		return "userPage/userScheduleList";
	}
	
	// 사용자가 계획한 여행일정을 모두 뽑아옴
	@ResponseBody
	@RequestMapping(value="userScheduleListCheck", method=RequestMethod.POST)
	public List<GroupVO> userScheduleListCheck(HttpSession session, Model model , String state) throws Exception{
		
		String mem = (String) session.getAttribute("mem");
		List<GroupVO> list ; ;
		if(state.equals("finish")){
			list = service1.groupFinishList(mem) ;
			System.out.println(state + " : state");
		}else{
			list = service1.groupGoingList(mem) ;
			System.out.println(state + " : state");
		}

		System.out.println(list.toString());

		return list;
	}
	
	// 사용자가 계획한 여행일정을 삭제 시킴.
	@ResponseBody
	@RequestMapping(value="plan_list_delete", method=RequestMethod.POST)
	public void plan_list_delete(HttpSession session, Model model , String group) throws Exception{
		
		String mem = (String) session.getAttribute("mem");
		int group_code = Integer.parseInt(group) ;
		
		GroupApplicationVO groupA = new GroupApplicationVO() ;
		
		groupA.setGroup_Code(group_code);
		groupA.setUser_id(mem);
		
		// group_application에서 group_code 삭제
		service1.user_groupApplication_delete(groupA) ;
		
		// group_code를 가지는 user들 체크
		int check = service1.user_group_delete_check(group_code) ;
		
		if(check == 0){
			// user들이 없으면 travel_group , travel_list에 group_code를 다 삭제
			service1.user_group_delete(group_code);
		}


	}
	
	// 사용자가 계획한 여행일정을 모두 뽑아옴
	@RequestMapping(value="userPlanDetail", method=RequestMethod.GET)
	public String plandetail(HttpSession session, Model model , String group_Code, String travel_Title, String startDate, String endDate ) throws Exception{
		
		String mem = (String) session.getAttribute("mem");
		int group_code = Integer.parseInt(group_Code);
		System.out.println(group_Code);
		System.out.println(travel_Title);
		
		model.addAttribute("title",travel_Title);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("group_Code",group_code);
		
		List<TravelListVO> list = service1.user_plan_list(group_code);
		List<Integer> days = new ArrayList<Integer>();
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		// 서브스트링 데이 뽑아내기 위한 순서
		int index = list.get(0).getTravel_Date().toString().lastIndexOf("-")+1;
		
		// 데이 뽑아내기
		String dayStr = list.get(0).getTravel_Date().toString().substring(index);
		
		int day = Integer.parseInt(dayStr)-1;
				
		for(int i=0; i<list.size(); i++) {
			String toDayStr = list.get(i).getTravel_Date().toString().substring(index);
			System.out.println(list.get(i).getTravel_Date().toString());
			int toDay = Integer.parseInt(toDayStr);
			
			days.add((toDay-day));
			System.out.println("오늘날짜 = " + list.get(0).getTravel_Date().toString() + "  데이 = " + day + "   투데이 = " + toDay + "  계산값 = " + (toDay-day));
			
			
		}
		
		model.addAttribute("days",days) ;
		model.addAttribute("list",list);
		
		System.out.println(list.toString());

		
		return "userPage/userPlanDetail";
	}
	
	
	// 사용자가 계획한 여행일정을 모두 뽑아옴 비동기식으로 처리
	@ResponseBody
	@RequestMapping(value="plandetailAsnyc", method=RequestMethod.POST , produces = "application/text; charset=utf8")
	public String plandetailAsnyc(HttpSession session , String group_code, String travel_Title, String startDate, String endDate ) throws Exception{
		
		String mem = (String) session.getAttribute("mem");
		int group_Code = Integer.parseInt(group_code);

		List<TravelListVO> list = service1.user_plan_list(group_Code);
		List<Integer> days = new ArrayList<Integer>();
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		// 서브스트링 데이 뽑아내기 위한 순서
		int index = list.get(0).getTravel_Date().toString().lastIndexOf("-")+1;
		
		// 데이 뽑아내기
		String dayStr = list.get(0).getTravel_Date().toString().substring(index);
		
		int day = Integer.parseInt(dayStr)-1;
				
		for(int i=0; i<list.size(); i++) {
			String toDayStr = list.get(i).getTravel_Date().toString().substring(index);
			System.out.println(list.get(i).getTravel_Date().toString());
			int toDay = Integer.parseInt(toDayStr);
			
			days.add((toDay-day));
			System.out.println("오늘날짜 = " + list.get(0).getTravel_Date().toString() + "  데이 = " + day + "   투데이 = " + toDay + "  계산값 = " + (toDay-day));
			
			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("days", days);
		map.put("list", list);
		
		String str = new Gson().toJson(map);
		
		System.out.println(list.toString());

		
		return str;
	}
	
	
	
	
	
}
