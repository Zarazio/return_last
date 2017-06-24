package zara.zio.turn.persistence;

import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.LogBoardVO;

public interface LogBoardService {
	
	public void logBoardCreate (LogBoardVO vo, int cnt) throws Exception;
	public int maxCode() throws Exception;
	
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum) throws Exception;
	public List<Map<String, Object>> logHashRead() throws Exception;
	public List<Map<String, Object>> logImageFileRead() throws Exception;
}
