package zara.zio.turn.persistence;

import java.util.List;


import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zara.zio.turn.dao.GroupTravelDAO;
import zara.zio.turn.domain.ChattingVO;
import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.Income_disbursementVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;

@Service
public class GroupTravelServiceImpl implements GroupTravelService {
	
	@Inject 
	private GroupTravelDAO dao;
	
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

	@Override
	public void limit_cost_update(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		dao.limit_cost_update(group); 
	}

	@Override
	public List<ChattingVO> chattingList(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.chattingList(group_Code);
	}

	@Override
	public void chattingStore(ChattingVO chat) throws Exception {
		// TODO Auto-generated method stub
		dao.chattingStore(chat) ;
	}

	@Override
	public List<GroupVO> groupGoingList(String mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.groupGoingList(mem);
	}

	@Override
	public List<GroupVO> groupFinishList(String mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.groupFinishList(mem);
	}

	@Override
	public int user_group_delete_check(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.user_group_delete_check(group_Code);
	}

	@Override
	public void user_group_delete(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		dao.user_group_delete(group_Code);
	}

	@Override
	public void user_groupApplication_delete(GroupApplicationVO groupA) throws Exception {
		// TODO Auto-generated method stub
		dao.user_groupApplication_delete(groupA);
	}

	@Override
	public List<TravelListVO> user_plan_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.user_plan_list(group_Code);
	}

	@Override
	public List<Income_disbursementVO> travel_cost_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_cost_list(group_Code);
	}

	@Override
	public List<MaterialVO> travel_supplies_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_supplies_list(group_Code);
	}

	@Override
	public GroupVO travel_modify(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_modify(group_Code);
	}

	@Override
	public String travel_cost(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_cost(group_Code);
	}

}
