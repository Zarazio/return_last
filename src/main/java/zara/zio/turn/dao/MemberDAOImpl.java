package zara.zio.turn.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.Pagination;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	private static final String namespace = "zara.zio.turndMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void user_create(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("dao옴");
		sqlSession.insert(namespace + ".user_create", vo);
	}

	@Override
	public MemberVO user_read(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".user_read", user_id);
	}

	@Override
	public void user_update(MemberVO vo, String user) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("nowuser", user);
		infoMap.put("m", vo);
		sqlSession.update(namespace + ".user_update", infoMap);
	}

	@Override
	public void user_delete(String user_id) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".user_delete", user_id);
	}

	@Override
	public List<MemberVO> user_listAll(Pagination pagination) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".user_listAll", pagination);
	}

	@Override
	public int id_confirm(String user_id) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DAO드감" + user_id);
		return sqlSession.selectOne(namespace + ".id_confirm", user_id); // ȸ�����̵� üũ 
	}

	@Override
	public String user_confirm(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".user_confirm", user_id); // ȸ�� ���̵� �޾Ƽ� ���� �н����带 ã��
	}

	@Override
	public int getTotalAll() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".totalAll");
	}

}
