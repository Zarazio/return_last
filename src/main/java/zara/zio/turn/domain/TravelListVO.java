package zara.zio.turn.domain;

import java.sql.Date;

public class TravelListVO {
   
      private int travel_Code; // 여행리스트코드
      private int travel_Priority; // 여행리스트우선순위
      private String travel_Memo; // 장소마다 메모
      private Date travel_Date; // 여행리스트 : 장소가 추가된날짜
      private int place_code; // 장소코드
      private int group_Code; // 그룹코드
      private int count;
      private double place_lat; // 장소 위도 
      private double place_lng; // 장소 경도 
      private String place_img ; // 장소 이미지
      private String place_name ; // 장소 이름
      
      public int getTravel_Code() {
         return travel_Code;
      }
      public void setTravel_Code(int travel_Code) {
         this.travel_Code = travel_Code;
      }
      public int getTravel_Priority() {
         return travel_Priority;
      }
      public void setTravel_Priority(int travel_Priority) {
         this.travel_Priority = travel_Priority;
      }
      public String getTravel_Memo() {
         return travel_Memo;
      }
      public void setTravel_Memo(String travel_Memo) {
         this.travel_Memo = travel_Memo;
      }
      public Date getTravel_Date() {
         return travel_Date;
      }
      public void setTravel_Date(Date travel_Date) {
         this.travel_Date = travel_Date;
      }
      public int getPlace_code() {
         return place_code;
      }
      public void setPlace_code(int place_code) {
         this.place_code = place_code;
      }
      public int getGroup_Code() {
         return group_Code;
      }
      public void setGroup_Code(int group_Code) {
         this.group_Code = group_Code;
      }
      public int getCount() {
        return count;
      }
      public void setCount(int count) {
         this.count = count;
      }
      public double getPlace_lat() {
         return place_lat;
      }
      public void setPlace_lat(double place_lat) {
         this.place_lat = place_lat;
      }
      public double getPlace_lng() {
         return place_lng;
      }
      public void setPlace_lng(double place_lng) {
           this.place_lng = place_lng;
      }
	  public String getPlace_img() {
	     return place_img;
	  }
	  public void setPlace_img(String place_img) {
	     this.place_img = place_img;
	  }
	  public String getPlace_name() {
	     return place_name;
	  }
	  public void setPlace_name(String place_name) {
	     this.place_name = place_name;
	  }
      
   }