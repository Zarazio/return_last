package zara.zio.turn.dao;

import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.LogBoardVO;

public interface LogBoardDAO {
	
	public void logInfoCreate(LogBoardVO vo) throws Exception;
	public void logHashCreate(String hash, int cnt) throws Exception;
	public void logImageFileCreate(String image, int cnt) throws Exception;
	public int maxCode() throws Exception;
	
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum) throws Exception;
	public List<Map<String, Object>> logHashRead() throws Exception;
	public List<Map<String, Object>> logImageFileRead() throws Exception;
	
}
