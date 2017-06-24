package zara.zio.turn.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import zara.zio.turn.domain.ChattingVO;
import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;

@Repository
public class GroupTravelDAOImpl implements GroupTravelDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "zara.zio.groupTravelMapper";
	
	@Override
	public void create(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".group_insert", group) ;
	}
	
	@Override
	public int selectGroupCode(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE+ ".groupCode", group);
	}
	
	// --------------- // 
	
	@Override
	public void create(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".planList_insert", travel) ; 
	}

	@Override
	public List<TravelListVO> planDayList(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".planDayList" , travel) ;
	}

	@Override
	public int travel_place(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".travel_place", travel);
	}

	@Override
	public void planPriority(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.selectOne(NAMESPACE + ".planPriority", travel);
	}

	@Override
	public void planDelete(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".planDelete", travel);
	}

	@Override
	   public List<TravelListVO> planRealTimePriority(TravelListVO travel) throws Exception {
	      // TODO Auto-generated method stub
	      return sqlSession.selectList(NAMESPACE + ".planRealTimePriority", travel);
	      
	}

	@Override
	public List<MaterialVO> material_list(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".material_list", material);
	}

	@Override
	public void material_insert(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".material_insert", material); 
	}

	@Override
	public int material_code(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".material_code", material);
	}

	@Override
	public void material_check(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".material_check", material);
	}

	@Override
	public List<MaterialVO> material_checked(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".material_checked", material);
	}

	@Override
	public void deleteCheckMaterial(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".deleteCheckMaterial", material);
	}

	@Override
	public void materialDelete(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".materialDelete", material);
	}
	
	@Override
	public void groupApplicationCreate(GroupApplicationVO groupA) throws Exception {	
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".groupApplicationCreate", groupA) ;
	}
	
	@Override
	public List<MemberVO> friendList(String member) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".friendList" , member);
	}

	@Override
	public void limit_cost_update(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".limit_cost_update", group) ;
	}

	@Override
	public List<ChattingVO> chattingList(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".chattingList",  group_Code);
	}

	@Override
	public void chattingStore(ChattingVO chat) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".chattingStore", chat) ;
	}

}
