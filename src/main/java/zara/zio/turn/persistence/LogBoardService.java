package zara.zio.turn.persistence;

import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;

public interface LogBoardService {
	
	public void logBoardCreate (LogBoardVO vo, int cnt, int type) throws Exception;
	public Map<String, Object> maxCode() throws Exception;
	
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum, String my) throws Exception;
	public int view(int no, int state) throws Exception;
	public int LikeUpDown(LikesVO vo, int states) throws Exception;
	public List<LogBoardVO> replyList(int no, String id) throws Exception;
	public List<LogBoardVO> replyCommand(int type, int no, int replyno, String text, String id) throws Exception;
	
	public List<ComunityVO> comunityInfoList(PaginationE pagenation) throws Exception;
	public int comuTotalCount() throws Exception;
	public ComunityVO comunityInfoRead(int page) throws Exception;
	public List<Map<String, Object>> comunityFileRead(int page) throws Exception;
	
	public void comunityFileDel (int target) throws Exception;
	public void comunityFileAdd (String file_name, int type, int page) throws Exception;
	public void comunityUpdate (LogBoardVO vo, int page) throws Exception;
	
	public void boardAllDel (int page) throws Exception;
	
}
