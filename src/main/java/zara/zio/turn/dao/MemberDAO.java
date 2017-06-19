package zara.zio.turn.dao;

import java.util.List;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.Pagination;

public interface MemberDAO {
	// CRUD
	public void user_create(MemberVO vo) throws Exception;
	public MemberVO user_read(String user_id) throws Exception;
	public void user_update(MemberVO vo, String user) throws Exception;
	public void user_delete(String user_id) throws Exception;
	public List<MemberVO> user_listAll(Pagination pagination) throws Exception;
	
	public int id_confirm(String user_id) throws Exception;
	public String user_confirm(String user_id) throws Exception;
	public int getTotalAll() throws Exception;
	
}
