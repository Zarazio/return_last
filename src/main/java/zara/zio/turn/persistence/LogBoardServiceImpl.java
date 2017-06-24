package zara.zio.turn.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zara.zio.turn.dao.LogBoardDAO;
import zara.zio.turn.domain.LogBoardVO;

@Service
public class LogBoardServiceImpl implements LogBoardService {

	@Inject 
	private LogBoardDAO dao;
	
	@Transactional
	@Override
	public void logBoardCreate(LogBoardVO vo, int cnt) throws Exception {
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
				dao.logImageFileCreate(imagefile[i], cnt);
			}
		} 
		if(test2) {
			for(int i=0; i<hash.length; i++) {
				dao.logHashCreate(hash[i], cnt);
			}
		}
		
	}

	@Override
	public int maxCode() throws Exception {
		// TODO Auto-generated method stub
		return dao.maxCode();
	}

	
	@Override
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum) throws Exception {
		// TODO Auto-generated method stub	
		return dao.logInfoRead(type, start, timeNum);
	}

	@Override
	public List<Map<String, Object>> logHashRead() throws Exception {
		// TODO Auto-generated method stub
		return dao.logHashRead();
	}

	@Override
	public List<Map<String, Object>> logImageFileRead() throws Exception {
		// TODO Auto-generated method stub
		return dao.logImageFileRead();
	}
	

}
