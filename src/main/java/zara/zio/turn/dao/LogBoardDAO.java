package zara.zio.turn.dao;

import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;

public interface LogBoardDAO {
	
	public void logInfoCreate(LogBoardVO vo) throws Exception;
	public void logHashCreate(String hash, int cnt) throws Exception;
	public void logImageFileCreate(String image, int cnt, int type) throws Exception;
	public Map<String, Object> maxCode() throws Exception;
	
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum) throws Exception;
	public List<Map<String, Object>> logHashRead() throws Exception;
	public List<Map<String, Object>> logImageFileRead() throws Exception;
	
	public List<ComunityVO> comunityInfoList(PaginationE pagenation) throws Exception;
	public int comuTotalCount() throws Exception;
	public ComunityVO comunityInfoRead(int page) throws Exception;
	public List<Map<String, Object>> comunityFileRead(int page) throws Exception;
	
	public void comunityFileDel (int target) throws Exception;
	public void comunityFileAdd (String file_name, int type, int page) throws Exception;
	public void comunityUpdate (LogBoardVO vo, int page) throws Exception;
	
	public void boardAllDel (int page) throws Exception;
	public void boardfileAllDel (int page) throws Exception;
	public void boardhashAllDel (int page) throws Exception;
}
