package zara.zio.turn.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zara.zio.turn.dao.LogBoardDAO;
import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;

@Service
public class LogBoardServiceImpl implements LogBoardService {

	@Inject 
	private LogBoardDAO dao;
	
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
		
		List<Map<String,Object>> LogHash = dao.logHashRead(); // 해시태그
		List<Map<String,Object>> LogImage = dao.logImageFileRead(); // 이미지 
		List<LikesVO> likes = dao.likeCounts(); // 좋아요 갯수 리스트
		List<LikesVO> myLike = dao.myLikes(my); // 나의좋아요 my
		
		// 좋아요 갯수 추가
		// 해당게시글의 내좋아요 엑티브 추가
		
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
			String [] itemB = image.split("◆");
			
			list.get(i).setHash_tag_content(itemA);
			list.get(i).setFile_content(itemB);
			
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
	public int view(int no, int state) throws Exception {
		// TODO Auto-generated method stub
		
		int value = 0;
		
		if(state == 0) {
			
			dao.viewCount(no);
			
		} 
		
		value = dao.viewSearch(no);
		
		return value;
	}
	
	@Override
	public List<LogBoardVO> replyList(int no, String id) throws Exception {
		// TODO Auto-generated method stub
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
