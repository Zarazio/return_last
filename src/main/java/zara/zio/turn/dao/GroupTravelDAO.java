package zara.zio.turn.dao;

import java.util.List;


import zara.zio.turn.domain.ChattingVO;
import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;

public interface GroupTravelDAO {
	
	public void create(GroupVO group) throws Exception;
	public int selectGroupCode(GroupVO group) throws Exception;
	public void groupApplicationCreate(GroupApplicationVO groupA) throws Exception ;
	public List<MemberVO> friendList(String mem) throws Exception; 
	
	// --------------- // 
	
	public void create(TravelListVO travel) throws Exception;
	public List<TravelListVO> planDayList(TravelListVO travel) throws Exception;
	public int travel_place(TravelListVO travel) throws Exception; // travel_list에 같은 placeCode가 있는지
	public void planPriority(TravelListVO travel) throws Exception;
	public void planDelete(TravelListVO travel) throws Exception;
	
	public List<TravelListVO> planRealTimePriority(TravelListVO travel) throws Exception;
	
	public List<MaterialVO> material_list(MaterialVO material) throws Exception;
	public void material_insert(MaterialVO material) throws Exception;
	public int material_code(MaterialVO material) throws Exception;
	public void material_check(MaterialVO material) throws Exception;
	public List<MaterialVO> material_checked(MaterialVO material) throws Exception;
	public void deleteCheckMaterial(MaterialVO material) throws Exception;
	public void materialDelete(MaterialVO material) throws Exception;
	
	public void limit_cost_update(GroupVO group) throws Exception;
	
	public List<ChattingVO> chattingList(int group_Code) throws Exception ;
	public void chattingStore(ChattingVO chat) throws Exception ;
	
	// 회원이 들어간 그룹 뽑아오기
	public List<GroupVO> groupGoingList(String mem) throws Exception ;
	public List<GroupVO> groupFinishList(String mem) throws Exception ;

	// 지금 그룹에 몇명있는지 체크
	public int user_group_delete_check(int group_Code) throws Exception ;
	// plan 삭제
	public void user_group_delete (int group_Code) throws Exception ;
	public void user_groupApplication_delete(GroupApplicationVO groupA) throws Exception;

	//여행 일정
	public List<TravelListVO> user_plan_list(int group_Code) throws Exception ;
}
