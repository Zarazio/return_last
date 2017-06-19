package zara.zio.turn.persistence;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zara.zio.turn.dao.GroupTravelDAO;
import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;

@Service
public class GroupTravelServiceImpl implements GroupTravelService {
	
	@Inject 
	private GroupTravelDAO dao ;
	
	@Override
	public void create(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		dao.create(group);
	}

	@Override
	public int selectGroupCode(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGroupCode(group);
	}
	
	// --------------- // 
	
	@Override
	public void create(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		dao.create(travel);
	}

	@Override
	public List<TravelListVO> planDayList(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return dao.planDayList(travel);
	}

	@Override
	public int travel_place(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_place(travel);
	}

	@Override
	public void planPriority(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		dao.planPriority(travel);
	}

	@Override
	public void planDelete(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		dao.planDelete(travel);
	}

	
	@Override
	public List<TravelListVO> planRealTimePriority(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return dao.planRealTimePriority(travel);
	}

	@Override
	public List<MaterialVO> material_list(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return dao.material_list(material);
	}

	@Override
	public void material_insert(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		dao.material_insert(material);
	}

	@Override
	public int material_code(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return dao.material_code(material);
	}

	@Override
	public void material_check(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		dao.material_check(material);
	}

	@Override
	public List<MaterialVO> material_checked(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return dao.material_checked(material);
	}

	@Override
	public void deleteCheckMaterial(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteCheckMaterial(material);
	}

	@Override
	public void materialDelete(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		dao.materialDelete(material);
	}

	@Override
	public void groupApplicationCreate(GroupApplicationVO groupA) throws Exception {
		// TODO Auto-generated method stub
		dao.groupApplicationCreate(groupA);
	}

	@Override
	public List<MemberVO> friendList(String mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.friendList(mem);
	}

}
