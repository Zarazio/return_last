package zara.zio.turn.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.FileAndHashVO;
import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;
import zara.zio.turn.domain.TravelGroupCountVO;

@Repository
public class LogBoardDAOImpl implements LogBoardDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "zara.zio.LogBoardMapper";
	
	
	@Override
	public void logInfoCreate(LogBoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE +".Loginfo", vo);
	}

	@Override
	public void logHashCreate(String hash, int cnt) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> hashtagMap = new HashMap<String, Object>();
		hashtagMap.put("hash", hash);
		hashtagMap.put("cnt", cnt);
		sqlSession.insert(NAMESPACE +".Loghash", hashtagMap);
	}

	@Override
	public void logImageFileCreate(String image, int cnt, int type) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> imageMap = new HashMap<String, Object>();
		
		imageMap.put("type", type);
		imageMap.put("image", image);
		imageMap.put("cnt", cnt);
		
		sqlSession.insert(NAMESPACE +".Logimgfile", imageMap);
	}

	@Override
	public Map<String, Object> maxCode() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".maxcode");
	}

	
	// 리스트 읽기 전용
	@Override
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> typeMap = new HashMap<String, Object>();
		typeMap.put("type", type);
		typeMap.put("startRecord", start);
		typeMap.put("timeLineNum", timeNum);
		return sqlSession.selectList(NAMESPACE + ".infoRead", typeMap);
	}
	
	@Override
	public List<FileAndHashVO> logHashRead() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".hashRead");
	}

	@Override
	public List<FileAndHashVO> logImageFileRead() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".imgfileRead");
	}
	
	@Override
	public List<LikesVO> likeCounts() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".likeCounts");
	}
	
	@Override
	public List<LikesVO> myLikes(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".likeMy", id);
	}
	
	
	@Override
	public void likeUp(LikesVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".likeUp", vo);
	}

	@Override
	public void likeDown(LikesVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".likeDown", vo);
	}
	
	@Override
	public LikesVO likeState(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".likeState", no);
	}
	
	@Override
	public void viewCount(int no) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".viewCount", no);
	}
	
	@Override
	public int viewSearch(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".viewSearch", no);
	}
	
	@Override
	public List<LogBoardVO> replyList(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".replyList", no);
	}
	
	@Override
	public void replyWrite(LogBoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".replyWrite", vo);
	}
	
	@Override
	public void replyModify(LogBoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".replyModify", vo);
	}
	
	@Override
	public void replyDelete(int replyno) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".replyDelete", replyno);
	}
	
	
	@Override
	public List<ComunityVO> comunityInfoList(PaginationE pagenation) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".comunityList", pagenation);
	}
	
	@Override
	public int comuTotalCount() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".comuTotalCount");
	}

	@Override
	public ComunityVO comunityInfoRead(int page) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".comunityRead",page);
	}

	@Override
	public List<Map<String, Object>> comunityFileRead(int page) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".comunityfileRead",page);
	}

	
	
	@Override
	public void comunityFileDel(int target) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".comunityfileDel", target);
	}
	@Override
	public void comunityFileAdd(String file_name, int type, int page) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> fileMap = new HashMap<String, Object>();
		fileMap.put("file_name", file_name);
		fileMap.put("type", type);
		fileMap.put("page", page);
		sqlSession.insert(NAMESPACE + ".comunityfileAdd", fileMap);
	}

	@Override
	public void comunityUpdate(LogBoardVO vo, int page) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> Updateinfo = new HashMap<String, Object>();
		Updateinfo.put("vo", vo);
		Updateinfo.put("page", page);
		sqlSession.update(NAMESPACE + ".conmunityUpdate", Updateinfo); 
	}

	
	@Override
	public void boardAllDel(int page) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".boardAllDel", page);
	}

	@Override
	public void boardfileAllDel(int page) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".boardfileAllDel", page);
	}

	@Override
	public void boardhashAllDel(int page) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".boardhashAllDel", page);
	}

}
