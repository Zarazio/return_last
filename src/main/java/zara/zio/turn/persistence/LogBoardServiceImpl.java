package zara.zio.turn.persistence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import zara.zio.turn.dao.LogBoardDAO;
import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.FileAndHashVO;
import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;
import zara.zio.turn.domain.StepLogVO;
import zara.zio.turn.util.GsonParserUtils;
import zara.zio.turn.util.KmlParsingUtils;

@Service
public class LogBoardServiceImpl implements LogBoardService {

	@Inject 
	private LogBoardDAO dao;
	
	@Resource(name="stepPath")
	private String stepPath;
	
	@Transactional
	@Override
	public void logBoardCreate(LogBoardVO vo, int cnt, int type) throws Exception {
		// TODO Auto-generated method stub
		
		dao.logInfoCreate(vo);
		
		boolean test1 = true;
		boolean test2 = true;

		String [] imagefile = vo.getFile_content();
		String[] hash = vo.getHash_tag_content();
		
		if(imagefile == null) {
			test1 = false;
		}
		if(hash == null) {
			test2 = false;
		}
		
		if(test1) {
			for(int i=0; i<imagefile.length; i++) {
				dao.logImageFileCreate(imagefile[i], cnt, type);
			}
		} 
		if(test2) {
			for(int i=0; i<hash.length; i++) {
				dao.logHashCreate(hash[i], cnt);
			}
		}
		
	}

	@Override
	public Map<String, Object> maxCode() throws Exception {
		// TODO Auto-generated method stub
		return dao.maxCode();
	}

	@Transactional
	@Override
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum, String my) throws Exception {
		// TODO Auto-generated method stub
		
		List<LogBoardVO> list = new ArrayList<LogBoardVO>();
		list = dao.logInfoRead(type, start, timeNum);
		List<FileAndHashVO> LogHash = dao.logHashRead(start, timeNum); // 해시태그
		List<FileAndHashVO> LogImage = dao.logImageFileRead(start, timeNum); // 이미지 
		List<LikesVO> likes = dao.likeCounts(); // 좋아요 갯수 리스트
		List<LikesVO> myLike = dao.myLikes(my); // 나의좋아요 my
		
		Double lat, lng;
		String AddressData; 
		
		// 좋아요 갯수 추가
		// 해당게시글의 내좋아요 엑티브 추가
		
		for(int i=0; i<list.size(); i++) {
			String hash = "";
			String image = "";
			
			int listNum = list.get(i).getBoard_code();

			for(int a=0; a<LogHash.size(); a++) {
				int hashNum = LogHash.get(a).getBoard_code();
				if(listNum == hashNum) {
					hash += LogHash.get(a).getHash_tag_content() + "◆";
				}
			}
			
			for(int b=0; b<LogImage.size(); b++) {
				int imageNum = LogImage.get(b).getBoard_code();
				String resultfile = LogImage.get(b).getFile_content();
				if(listNum == imageNum) {
					if(resultfile.contains(".kml")) {
						
						image += KmlParsingUtils.kmlParse(stepPath, resultfile) + "◆";
						
					} else {
						image += resultfile + "◆";
					}
				}
			}
			
			for(int c=0; c<likes.size(); c++) {
				
				int likeNum = likes.get(c).getBoard_code();
				if(likeNum == listNum) {
					list.get(i).setLike_count(likes.get(c).getCnt());
				}
				
			}
			
			for(int d=0; d<myLike.size(); d++) {
				int myLikeNum = myLike.get(d).getBoard_code();
				if(listNum == myLikeNum) {
					list.get(i).setMy_like(1);
				}
			}
			
			String [] itemA = hash.split("◆");
			list.get(i).setHash_tag_content(itemA);

			String [] itemB = image.split("◆");
			list.get(i).setFile_content(itemB);
			
			if(list.get(i).getLog_latitude() != 0.000000) {
				lat = list.get(i).getLog_latitude();
				lng = list.get(i).getLog_longtitude();
				AddressData = GsonParserUtils.parser(lng, lat);
				list.get(i).setOnAddress(AddressData);
			} 

		}
		
		
		return list;
		
	}
	
	@Transactional
	@Override
	public int LikeUpDown(LikesVO vo, int states) throws Exception {
		// TODO Auto-generated method stub
		
		int value = 0;
		
		if(states == 1) {
			dao.likeUp(vo);
		} 
		if(states == 0) {
			dao.likeDown(vo);
		}
		
		LikesVO no = dao.likeState(vo.getBoard_code());
		if(no != null) {
			value = no.getCnt();
		}
		
		return value;
	}
	
	@Transactional
	@Override
	public Map<String, Object> commandTwo(int state, int no, int type, String id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>(); // 맵데이터
		List<LogBoardVO> list = new ArrayList<LogBoardVO>();
		list = dao.replyList(no);
		List<StepLogVO> step = new ArrayList<StepLogVO>();
		
		int value = 0;
		
		if(state == 0) {
			dao.viewCount(no);
		} 
		value = dao.viewSearch(no);
		
		
		for(int i=0; i<list.size(); i++) {
			String replyid = list.get(i).getUser_id();
			if(replyid.equals(id)) {
				list.get(i).setReply_state(1);
			}
		}
		
		// 스텝로그일때 실행  
		if(type == 3) {
			
			step = dao.stepLogs(no);
			List<FileAndHashVO> list2 = dao.stepLogs2(no);
			List<LikesVO> myLike = dao.myLikes(id);
			
			int lico = 0;
			int lico2 = 0;
			Double lat, lng;
			String AddressData; 
			
			
			for(int z=0; z<step.size(); z++) {
				
				String hash = "";
				lico = step.get(z).getBoard_code();
				
				for(int j=0; j<list2.size(); j++) {
					lico2 = list2.get(j).getBoard_code();
					if(lico == lico2) {
						hash += list2.get(z).getHash_tag_content() + "◆";
					}
				}
				
				for(int x=0; x<myLike.size(); x++) {
					int mynum = myLike.get(x).getBoard_code();
					if(lico == mynum) {
						step.get(z).setMylike(1);
					}
				}
				
				if(hash != "") {
					String [] itemA = hash.split("◆");
					step.get(z).setHash_tag_content(itemA);
				} 
				
				if(step.get(z).getLog_latitude() != 0.000000) {
					lat = step.get(z).getLog_latitude();
					lng = step.get(z).getLog_longtitude();
					AddressData = GsonParserUtils.parser(lng, lat);
					step.get(z).setOnAddress(AddressData);
				} 
				
				
			}
			
		}
		
		map.put("reply", list); // 댓글정보 
		map.put("view", value); // 조회수 정보 
		map.put("step", step); // 스텝로그 정보
		
		return map;
		
	}
	
	@Transactional
	@Override
	public List<LogBoardVO> replyCommand(int type, int no, int replyno, String text, String id) throws Exception {
		// TODO Auto-generated method stub

		if(type != 3) {
			
			LogBoardVO vo = new LogBoardVO();
			vo.setBoard_content(text);
			vo.setBoard_type_code(6);
			vo.setUser_id(id);
			vo.setReply_code(no);
			vo.setWrite_type(0);
			
			if(type == 1) {
				// wirte
				dao.replyWrite(vo);
			}
			
			if(type == 2) {
				
				// modify
				vo.setReply_code(replyno);
				dao.replyModify(vo);
				
			}
		} 
		
		if(type == 3) {
			
			// delete
			dao.replyDelete(replyno);
			
		}
			
		
		List<LogBoardVO> list = new ArrayList<LogBoardVO>();
		list = dao.replyList(no);
		
		for(int i=0; i<list.size(); i++) {
			String replyid = list.get(i).getUser_id();
			if(replyid.equals(id)) {
				list.get(i).setReply_state(1);
			}
		}
		
		
		return list;
	}
	
	
	@Override
	public List<ComunityVO> comunityInfoList(PaginationE pagenation) throws Exception {
		// TODO Auto-generated method stub
		return dao.comunityInfoList(pagenation);
	}
	
	@Override
	public int comuTotalCount() throws Exception {
		// TODO Auto-generated method stub
		return dao.comuTotalCount();
	}

	@Override
	public ComunityVO comunityInfoRead(int page) throws Exception {
		// TODO Auto-generated method stub
		return dao.comunityInfoRead(page);
	}

	@Override
	public List<Map<String, Object>> comunityFileRead(int page) throws Exception {
		// TODO Auto-generated method stub
		return dao.comunityFileRead(page);
	}
	
	@Override
	public void comunityFileDel(int target) throws Exception {
		// TODO Auto-generated method stub
		dao.comunityFileDel(target);
	}
	
	@Override
	public void comunityFileAdd(String file_name, int type, int page) throws Exception {
		// TODO Auto-generated method stub
		dao.comunityFileAdd(file_name, type, page);
	}

	@Override
	public void comunityUpdate(LogBoardVO vo, int page) throws Exception {
		// TODO Auto-generated method stub
		dao.comunityUpdate(vo, page);
	}

	@Transactional
	@Override
	public void boardAllDel(int page) throws Exception {
		// TODO Auto-generated method stub
		dao.boardAllDel(page);
		dao.boardfileAllDel(page);
		dao.boardhashAllDel(page);
	}

}
