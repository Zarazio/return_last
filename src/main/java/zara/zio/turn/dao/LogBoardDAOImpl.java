package zara.zio.turn.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.LogBoardVO;

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
	public int maxCode() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".maxcode");
	}

	
	
	@Override
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> typeMap = new HashMap<String, Object>();
		typeMap.put("type", type);
		typeMap.put("startRecord", start);
		typeMap.put("timeLineNum", timeNum);
		return sqlSession.selectList(NAMESPACE + ".infoRead",typeMap);
	}
	
	@Override
	public List<Map<String, Object>> logHashRead() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".hashRead");
	}

	@Override
	public List<Map<String, Object>> logImageFileRead() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".imgfileRead");
	}

	
	
	@Override
	public List<ComunityVO> comunityInfoList() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".comunityList");
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


}
