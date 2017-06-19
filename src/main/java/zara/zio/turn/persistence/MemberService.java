package zara.zio.turn.persistence;

import java.util.List;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.Pagination;

public interface MemberService {
	
	public void regist(MemberVO vo) throws Exception;
	public MemberVO read(String id) throws Exception;
	public void modify(MemberVO vo, String user) throws Exception;
	public void remove(String id) throws Exception;
	public List<MemberVO> listAll(Pagination pagination) throws Exception;
	
	public int confirm(String id) throws Exception;
	public String userin(String id) throws Exception;
	public int getTotalAll() throws Exception;
}
