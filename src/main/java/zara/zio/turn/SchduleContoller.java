 package zara.zio.turn;

import java.sql.Date;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.domain.TravelListVO;
import zara.zio.turn.persistence.GroupTravelService;
import zara.zio.turn.persistence.PlaceService;

@Controller
public class SchduleContoller {
   
   @Inject
   private PlaceService service;
   
   @Inject
   private GroupTravelService service1;
   
   @ResponseBody
   @RequestMapping (value="friend_list" ,  method=RequestMethod.POST)
   public List<MemberVO> friend_list(HttpSession session) throws Exception{
      
      String mem = (String) session.getAttribute("mem") ;
      
      List<MemberVO> member01 = service1.friendList(mem);
      System.out.println(member01);
      return member01 ;
   }
   
   @RequestMapping (value="scheduleSet", method=RequestMethod.POST) // 스케쥴 페이지 이동 
   public String schduleSet(GroupVO group , GroupApplicationVO groupA, String scheduleDate, String local, RedirectAttributes rttr,HttpSession session,HttpServletRequest request) throws Exception {

      //그룹에 초대한 친구들 id 가져옴
      String[] friend = (String[]) request.getParameterValues("friend") ;

      String[] date = scheduleDate.split(" - ");
         
       String date01 = date[0];
       String date02 = date[1];
         
       Date start_Date = Date.valueOf(date01) ;
       Date end_Date = Date.valueOf(date02) ;
      
       group.setStart_Date(start_Date);
       group.setEnd_Date(end_Date);
          
       service1.create(group);
         
       // groupCode 뽑아내는 문
       int groupCode = service1.selectGroupCode(group);
       
       // 그룹신청여부
       String mem = (String) session.getAttribute("mem") ;
        
       //개인이 그룹을 만들 때, 
       groupA.setUser_id(mem);
       groupA.setInvite_user(mem);
       groupA.setGroup_Code(groupCode);
       groupA.setGroup_apply(1);
       // 그룹신청
       service1.groupApplicationCreate(groupA) ;
       
       //그룹을 같이 만들 친구가 있을 경우
       if(friend != null){
         for(int i=0; i<friend.length; i++){
            groupA.setUser_id(friend[i]);
            groupA.setGroup_apply(0);
            service1.groupApplicationCreate(groupA) ;
            
         }
      }
       
       
      
      rttr.addAttribute("scheduleDate", scheduleDate);
      rttr.addAttribute("local", local);
      rttr.addAttribute("groupCode", groupCode);
      
      return "redirect:scheduleSet";
   }
   
   
   
   @RequestMapping (value="scheduleSet", method=RequestMethod.GET) // 스케쥴 페이지 이동 
   public String schduleSetG(String scheduleDate, String local, int groupCode, Model model) {

      
      
      model.addAttribute("scheduleDate", scheduleDate);
      model.addAttribute("local", local);
      model.addAttribute("groupCode", groupCode);
      
      return "schedulePage/schedulePageA";
      
   }
   
   
   @ResponseBody
   @RequestMapping (value="placeList", method=RequestMethod.GET) // 장소정보 가져오기
   public List<PlaceVO> placeList(HttpServletRequest request) throws Exception {
      
      String local = request.getParameter("localData");
      local = "%" + local + "%";
      List<PlaceVO> list = service.readLocal(local);
      
      System.out.println("local : " + local);
      
      for(int i=0; i<list.size(); i++) {
         System.out.print(list.get(i).getPlace_code() + " ");
         System.out.println(list.get(i).getPlace_address());
      }
      
      return list;
   }
   
   @ResponseBody
   @RequestMapping (value="planPlaceCodCheck", method=RequestMethod.POST)
   public int planPlaceCodCheck(TravelListVO travel, String plan, String group) throws Exception{
      
   
       int group_Code = Integer.parseInt(group);
       Date travel_Date = Date.valueOf(plan);
         
    
       travel.setGroup_Code(group_Code);
       travel.setTravel_Date(travel_Date);
      
      int count = service1.travel_place(travel) ;
      //System.out.println("placecheck : " + place);
      System.out.println("countDB  : " + count);
      return count ;
   }
   
   
   @ResponseBody
   @RequestMapping (value="planList", method=RequestMethod.POST)
   public void planList(String place, String plan, String group, TravelListVO travel, int priority) throws Exception{
      int place_code = Integer.parseInt(place);
      int group_Code = Integer.parseInt(group);
      Date travel_Date = Date.valueOf(plan);
       
      System.out.println("priority : " + travel.getTravel_Priority());
      
      travel.setTravel_Priority(priority);
      travel.setPlace_code(place_code);
      travel.setGroup_Code(group_Code);
      travel.setTravel_Date(travel_Date);
         
       service1.create(travel) ;
         
   }
      
   @ResponseBody
   @RequestMapping (value="planDayList" , method=RequestMethod.POST)
   public List<TravelListVO> planList01(String plan, String group , TravelListVO travel) throws Exception{
      
      int group_Code = Integer.parseInt(group) ;
      Date travel_Date = Date.valueOf(plan) ;
      
      
      
      travel.setGroup_Code(group_Code);
      travel.setTravel_Date(travel_Date);
      
      List<TravelListVO> place = service1.planDayList(travel);
   

      
      return place;
      
      
    }
   
   @ResponseBody
   @RequestMapping (value="planPlacePriority", method=RequestMethod.POST)
   //public String planPlacePriority(@RequestBody List<TravelListVO> array) throws Exception {
   public List<TravelListVO> planPlacePriority(HttpServletRequest request, String plan, String group, TravelListVO travel, int count) throws Exception {

      int count_check = 0 ;
      String[] array = (String[])request.getParameterValues("array") ;
      String[] array01 = (String[])request.getParameterValues("array01") ;
   
      
      int group_Code = Integer.parseInt(group);
      Date travel_Date = Date.valueOf(plan);
       
      travel.setTravel_Priority(count);
      travel.setGroup_Code(group_Code);
      travel.setTravel_Date(travel_Date);
      
      if(array != null){
      for(int i=0 ; i<array.length ; i++){
         
         count_check++ ;
         if(count >= count_check){
            //0000000000System.out.println(Integer.parseInt(array[i]));
            
            int place_code = Integer.parseInt(array[i]) ;
            int travel_Priority = Integer.parseInt(array01[i]) ;
            
            travel.setCount(count_check);
            travel.setPlace_code(place_code);
            travel.setTravel_Priority(travel_Priority);
            
   
            service1.planPriority(travel);
            
            
         }
         
         if(count == count_check)
            count_check = 1 ;
         
         
         
      }
   }
      
      
      List<TravelListVO> place = service1.planDayList(travel);
      
//       
//      service1.planPriority(travel);
      return place;
    }
   
   @ResponseBody
   @RequestMapping (value="planPlaceDelete", method=RequestMethod.POST)
    public void planPlaceDelete(String place, String plan, String group, TravelListVO travel) throws Exception{
       
      int place_code = Integer.parseInt(place);
       int group_Code = Integer.parseInt(group);
       Date travel_Date = Date.valueOf(plan);
          
       travel.setPlace_code(place_code);
       travel.setGroup_Code(group_Code);
       travel.setTravel_Date(travel_Date);
         
       service1.planDelete(travel);
   }

   @ResponseBody
   @RequestMapping (value="planRealTimePriority", method=RequestMethod.POST)
    public List<TravelListVO> planRealTimePriority(String plan, String group, TravelListVO travel) throws Exception{
       
      
       int group_Code = Integer.parseInt(group);
       Date travel_Date = Date.valueOf(plan);
          
       
       travel.setGroup_Code(group_Code);
       travel.setTravel_Date(travel_Date);
         
       List<TravelListVO> list = service1.planRealTimePriority(travel);
       
      return list;
   }
   
   
   @ResponseBody
   @RequestMapping (value="placeFilterList" , method=RequestMethod.POST)
   public List<PlaceVO> placeFilterList(String local , String placeType, PlaceVO place) throws Exception{
      
      System.out.println("local : " + local  + " ddd : " + placeType);
      
      place.setPlace_type(placeType);
      place.setPlace_address(local);
      
      
      
      List<PlaceVO> list = service.placeFilter(place);
      
      return list ;
   }
   
   
   @ResponseBody
   @RequestMapping (value="placeSearchList" , method=RequestMethod.POST)
   public List<PlaceVO> placeSearchList(String local , String place_name, PlaceVO place) throws Exception{
      List<PlaceVO> list = null;
      System.out.println("local : " + local  + " ddd : " + place_name);
      
      place.setPlace_name(place_name);
      place.setPlace_address(local);
      
      System.out.println(local.equals("local"));
      
      // 전체 지역 검색일 경우
      if(local.equals("local")){
        
         list = service.searchAllFilter(place) ;
         System.out.println(list);
      }else{
         
         list = service.searchLocalFilter(place);
      }
      
      return list ;
   }
   
   @ResponseBody
   @RequestMapping (value="material_list" , method=RequestMethod.POST)
   public List<MaterialVO> material_list(String groupCode , HttpSession session, MaterialVO material) throws Exception{
      
      int group_Code = Integer.parseInt(groupCode);
      String mem = (String) session.getAttribute("mem") ;
      System.out.println("material : "  + "dd : 0" + mem);
      
      material.setGroup_Code(group_Code) ;
      material.setUser_id(mem) ;
      
      
      List<MaterialVO> checked = service1.material_checked(material);
      List<MaterialVO> list = service1.material_list(material);
      
      
      
      for(int i=0 ; i<list.size() ; i++) {
         
         for(int j=0 ; j<checked.size(); j++){
            
            if(list.get(i).getMaterial_code() == checked.get(j).getMaterial_code() ){
               
               list.get(i).setMaterial_check(checked.get(j).getMaterial_code());
               System.out.println("Ddd : "  +list.get(i).getMaterial_check());
            }
            
         }
         
         System.out.println("dsdffd : " + list.get(i).getMaterial_check());
         
      } 
      
      return list ;
   }
   
   
   @RequestMapping (value="userPage/userScheduleList", method=RequestMethod.POST)
   public String userScheduleList(HttpServletRequest request, MaterialVO material, String groupCode, HttpSession session) throws Exception{
      
      String mem = (String) session.getAttribute("mem") ;
      String[] materials = (String[])request.getParameterValues("materials");
      String[] materialCheck = (String[])request.getParameterValues("materialCheck");
      String[] managers = (String[])request.getParameterValues("managers");
      String[] materialDeleteCheck = (String[])request.getParameterValues("materialDeleteCheck") ;
      String smartCost = request.getParameter("smartCost"); 
      String cost = request.getParameter("cost");
      int group_Code = Integer.parseInt(groupCode); 
      int manager_check ;
      material.setUser_id(mem) ;
      material.setGroup_Code(group_Code);
      
      //System.out.println()
      
      
      if(materialDeleteCheck != null){
         for(int i=0 ; i < materialDeleteCheck.length ; i++){
            System.out.println("materialdelete : " + materialDeleteCheck[i]);
            int material_code = Integer.parseInt(materialDeleteCheck[i]) ;
            material.setMaterial_code(material_code);
            service1.deleteCheckMaterial(material);
         }
      }
      
   

      
      if(materials != null){
         for(int i=0 ; i<materials.length ; i++){
            
            System.out.println(materials[i]);
            
            if(managers[i].equals("0")){
            
               System.out.println("mdmd : " + materials[i]);
               manager_check = Integer.parseInt(managers[i]) ;
               material.setManager_check(manager_check);
               material.setMaterial_name(materials[i]);
               
               
               
               service1.material_insert(material) ;
               
            }
         }
      }
      
      if(materialCheck != null){
         for(int i=0 ; i<materialCheck.length ; i++){
            
            System.out.println(materialCheck[i]);
            
            material.setGroup_Code(group_Code);
            material.setMaterial_name(materialCheck[i]);
            System.out.println("ddddsfdsf : " + material.getGroup_Code() + "ddddsfdsf : " + material.getMaterial_name()  + "ddd sf : " +material.getUser_id() );   
            int material_code = service1.material_code(material) ;
            material.setMaterial_code(material_code);
            System.out.println(material_code);
            service1.material_check(material) ;
         }
      }
   
      return null ;
   }
   
   @ResponseBody
   @RequestMapping (value="materialDelete" , method=RequestMethod.POST)
   public void materialDelete(String groupCode , String materialCode ,HttpSession session, MaterialVO material) throws Exception{
      
      int group_Code = Integer.parseInt(groupCode);
      int material_code = Integer.parseInt(materialCode) ;
      String mem = (String) session.getAttribute("mem") ;
   
      material.setGroup_Code(group_Code) ;
      material.setUser_id(mem) ;
      material.setMaterial_code(material_code);
      
      service1.deleteCheckMaterial(material);
      service1.materialDelete(material) ;
      
      
   }

   
}