var infoWindows = [] ;
var infoWindowsPlan = [] ;
var markers = [] ; // 장소리스트에서 markers에 장소들을 넣음
var markersPlan = [] ; // 여행일정에 추가된 장소를 넣음

var materials = [];

$(document).ready(function(){
   $("#header").remove();
   $("#topBar").remove();
   $("#myModal").remove();
   $("#footer").hide();   
   
   
   var date = $(".dateName").html(); // 날짜 객체를 가져온다. 2017-05-30 - 2017-06-22
   var left = date.substr(0,10).split('-'); // 2017 05 30
   var right = date.substr(13, 18).split('-'); //  - 2017 06 22
   var day1 = new Date(left[0], left[1] -1 , left[2]); // 2017 (6-1) 30
   var day2 = new Date(right[0], right[1] -1 , right[2]); // 2017 (7-1) 22
   var diff = day2 - day1; 
   var currDay = 24 * 60 * 60 * 1000; // 일수 (차) 구하는식 
   var cnt = parseInt(diff/currDay); // date (차) 결과값

   var planDay = nal(0) ;// 계획 날짜 확인 용도
   var planDayCheck ; // 첫째날인지 확인하는 용도
   var groupCode = $("#groupCode").text(); //schdulePageA groupCode
   var placeCode; //place_code 받는 변수
   var priority = 0; // priority 변수 
   var priority01 = 0; // 바뀐 data-pri와 기존의 pri를 비교해서

   var data01 = new Array()  ; //placeList의 모든 배열
   var planList = new Array() ; // 사용자가 여행계획에 등록한 장소 배열 넣기


   elements = $(".addMenu"); // ul 엘리먼트값불러온다.
   
   // 일정의 날들을 순차적으로 실행
   for(var i=0; i<=cnt; i++) {
      $("<li><a class='turn-center dayon' href='#' data-cnt="+(i+1)+" data-nal=" + nal(i) + 
            " date-day=" + day(i) + ">Day " + (i+1) + "</a></li>").appendTo(elements);
      var liAdd = $(".liAdd");
      $("<div></div>")
         .addClass("turn-scroll-auto")
         .addClass("selectPlace")
         .attr("data-nal", nal(i))
         .css("display","none")
         .css("border-top","1px solid rgba(255, 255, 255, 0.1)")
      .appendTo(liAdd);
      
      
      
   }
   initDay(); // 처음함수를 실행
   material_list(groupCode);
   
   
   //day 클릭 할때, 
   $(".addMenu").on("click",".dayon", function(){
      // var old = $(this).attr("data-day");
      priority = 0 ;
      // day 클릭 할때마다, planDay에 현재 여행일정짜는 날짜 넣어줌
      planDay = $(this).attr("data-nal");
      
      $(".dayChoose").text($(this).text() + " - " + $(this).attr("data-nal") + " " + $(this).attr("date-day"));
      
      console.log($(this).attr("data-nal") ) ;
      
      // class selectPlace에 data-nal 값 모두 비교해서 
      $(".selectPlace").each(function(){
         $(this).css("display","none") ;
         
         var select = $(this).attr("data-nal");
         
         if(select == planDay){ // 
            $(this).css("display", "block") ;
            console.log($(this).children(".selectPlace").empty()) ;
            
            $(".selectPlace ").empty();
            planDayPrint();
            placeMarker();
         }

      });
      
      
   });
   
   
   function initDay() { // 함수실행 기본값 날짜
      
      $(".dayChoose").text(
         $(".addMenu .dayon").eq(0).text() + " - " + 
         $(".addMenu .dayon").eq(0).attr("data-nal") + " " + 
         $(".addMenu .dayon").eq(0).attr("date-day")
      );
      
      var local = {
            localData : $(".turn-local > p").text()
      };
      
      // 장소리스트 출력
      $.ajax({
         type : 'GET',
         url : 'placeList',
         data : local,
         dataType: "json",
         success : function(data) { // @ResponseBody
            for(var i=0 ; i<data.length ; i++){
               console.log("RES : " + data[i]) ;
               console.log(data[0][1]);
               data01[i] = new Array(5) ;
               
               data01[i][0] = data[i].place_code ;
               data01[i][1] = data[i].place_lat ;
               data01[i][2] = data[i].place_lng ; 
               data01[i][3] = data[i].place_img ;
               data01[i][4] = data[i].place_name;
               
            }
            
            if(data){
               placeMarker() ; // 장소에 대한 마커 바로 불러주기위해!
               
               var count = data.length; 
               var elem = "";
               for(var i=0; i<count; i++) {
                  elem += "<div class='place placeCode' data-code='" + data[i].place_code +"' data-lat="+ data[i].place_lat 
                     +" data-lng="+ data[i].place_lng +" data-name="+ data[i].place_name + ">"
                      + "<img src='displayFile?fileName="+ thumb(data[i].place_img) + "'></div>";
   
               }
            
               
               $(".placeList").append(elem);
            }
         }
      })
      
      console.log($(".selectPlace"));
      
      /// - 맨 처음 schedulePageA 페이지 로드할 때, 기존의 리스트에 있던 day1 출력 - ///
      $(".selectPlace").each(function(){
            
            var selectThis = $(this) ;
            var select = $(this).attr("data-nal");
            
            if(select == planDay){
               $(this).css("display", "block") ;
               
               $.ajax({
                  type : "POST" ,
                  url :  'planDayList',
                  data : {
                     group : groupCode,
                       plan : planDay
                  },
                  dataType : "json",
                  success : function(data){
                     for(var i=0 ; i<data.length ; i++){
                        console.log("place_img2  : " + data[i].place_img) ;
                        $("<div data-code="+data[i].place_code+" data-pri="+ data[i].travel_Priority +" data-lat="+ data[i].place_lat +" data-lng="+ data[i].place_lng 
                          +" data-name="+ data[i].place_name+ " style='width:100%; padding:5px; height:160px; background : #333333; '></div>")
                           .addClass("planList")
                           .append("<img src='displayFile?fileName="+ thumb(data[i].place_img) + "' style='width:150px ; height:150px'>")
                           .append("<div class='planPlaceDelete' data-code='"+data[i].place_code+"'><a href='#'>삭제</a></div>") 
                           .css("border-bottom","1px solid #3a3c3f")
                        .appendTo(selectThis) ;
                        
                     }
                  }
               
               })
            }

       });
      /// ------------------------------------------------------ ///       
      
   }
   
   
   
   // 날짜와 요일을 순차적으로 구하는 함수 //
   function nal(i) {
      
      var dat = new Date(day1);
      var day = dat.getDate() + i;
      dat.setDate(day);
      dat.setMonth(dat.getMonth() + 1);
      
      var nal = "";
      
      if(dat.getMonth() < 10 && dat.getDate() < 10) {
         
         nal = dat.getFullYear() + "-0" + dat.getMonth() + "-0" + dat.getDate();
         
      } else if(dat.getMonth() < 10) {
         
         nal = dat.getFullYear() + "-0" + dat.getMonth() + "-" + dat.getDate();
         
      } else if (dat.getDate() < 10) {
         
         nal = dat.getFullYear() + "-" + dat.getMonth() + "-0" + dat.getDate();
         
      }
      
      return nal;
      
   }
   
   // 요일을 구하는 함수
   function day(i) {
      
      var dat = new Date(day1);
      var day = dat.getDate() + i;
      dat.setDate(day);
      
      var weekday = new Array(7);
       weekday[0]="(일)";
       weekday[1]="(월)";
       weekday[2]="(화)";
       weekday[3]="(수)";
       weekday[4]="(목)";
       weekday[5]="(금)";
       weekday[6]="(토)";

       return weekday[dat.getDay()]; 
   }
   
   
   // 장소 리스트 선택할때, draggable 선택
    $(".placeList").on("mouseover", ".place", function(){
        
        placeCode = $(this).attr("data-code") ;
        
        $(".place").draggable({
              accept : ".place",
             helper :  "clone",
             cursor: "crosshair",
             connectToSortable : ".selectPlace"
            
        });
        
     });
    
    $(".selectPlace").sortable({
    
        over : function(event, ui){
  
         placeCode = ui.item.attr("data-code") ;
         
         if(ui.item.hasClass("place")){
  
            planPlaceCheck() ;
            

            priority01 = ui.item;
            
          ui.item.attr("data-pri", priority);
            ui.item.append("<div class='planPlaceDelete'><a href='#'>삭제</a></div>");
            ui.item.removeClass() ;
            ui.item.addClass("planList");
            
         }

      },
      receive : function(event ,ui){
         // selectPlace의 draggable를 했을 때, 이벤트 발생
         console.log("receive : ") ;
         
         placeMarker() ;
         planPriority() ;
         planRealTimePriority() ;
         priority01 = priority01.attr("data-pri");
         console.log("priority : " + priority  + " priority01 : " + priority01);
         if(priority != priority01)
         {
            priority = priority01 ;
         }
         planListStore($(this));
         
        
         
         
         
      },
      update : function(){
         // 위치가 바꼈을 때, 이벤트 발생
         console.log("update : ") ;
         placeMarker() ;
         planPriority();
         planRealTimePriority() ;
         
      },
      cursor: "crosshair"
     
   });
    
    
    // 변화된  data-pri 값을 나타내줌
    function planRealTimePriority(){
       
       var count = $(".selectPlace > div").parent().children().index($(this)) + 1 ;
       var countCheck = 1 ;
       
       $(".selectPlace > div").each(function(){

            $(this).attr("data-pri",countCheck) ;
            
            countCheck++;
            
           
        })
       
       
    }
    
    //day1~.. 에 계획된 장소가 몇개인지
    function planPlaceCheck(){
       
       
       $.ajax({
          type : 'POST',
          url : 'planPlaceCodCheck',
          data : {
             
             plan : planDay,
             group : groupCode 
          },
          async : false,
          success : function(data){
             
             priority = data+1 ;
             
          }, 
          error : function() {
             alert("에러입니다.11111");
          }
      })
       
    }
    
    // draggable 한거 db에 저장
    function planListStore(){
          
       alert("typeof : " + typeof(priority));
        
       $.ajax({
          type : 'POST',
          url : 'planList',
          data : {
             place : placeCode,
             plan : planDay,
             group : groupCode,
             priority : priority 
          },
          success : function(data){
             
          }, 
          error : function() {
             alert("에러입니다.11111");
          }
      })
       
       console.log("ddd");
    
    }
    
    // 변경된 data-pri 를 데이터베이스에 넣어줌
    function planPriority(){
        var count=0;
        var place;
        var updatePriority ;
        var array = new Array();
        var array01 = new Array();
        $(".selectPlace > div").each(function(){
 
           count = $(this).parent().children().index($(this)) + 1 ;
      
           place = $(this).attr("data-code") ;
           updatePriority = $(this).attr("data-pri") ;
           
           var item = place;
           var items = updatePriority;
           
           array.push(item);
           array01.push(items) ;
     
       })
      
        
       $.ajax({
                 url : "planPlacePriority",
                 type : "POST",
                 traditional: true,
                 data : {
                    
                    array :  array ,
                    array01 : array01 ,
                    group : groupCode ,
                    plan : planDay ,
                    count : count
                     
                 },
                 success: function(data){
                   
                    for(var i=0 ; i<data.length ; i++){
                       planList[i] = new Array(3);
                       planList[i][0] = data[i].travel_Priority ;
                       planList[i][1] = data[i].place_lat ;
                       planList[i][2] = data[i].place_lng ; 
                    }
                    
                  
                   
                 }
            })
    }
    
   
   
    //plan Day에 List 출력~
    function planDayPrint(){
        
        $(".selectPlace").each(function(){
                 
           var selectThis = $(this) ;
           var select = $(this).attr("data-nal");
           
           if(select == planDay){
              $(this).css("display", "block");
              
              $.ajax({
                 type : "POST" ,
                 url :  'planDayList',
                 data : {
                    group : groupCode,
                      plan : planDay
                 },
                 dataType : "json",
                 success :function(data){    
                    
                    for(var i=0 ; i<data.length; i++){
                       $("<div data-code="+data[i].place_code+" data-pri="+ data[i].travel_Priority +" data-lat="+ data[i].place_lat 
                       +" data-lng="+ data[i].place_lng +" data-name="+ data[i].place_name + " style='width:100%; padding:5px; height:160px; background : #333333; border-bottom : 1px solid #3a3c3f'></div>")
                       .addClass("planList")
                       .addClass("priority")
                       .append("<img src='displayFile?fileName="+ thumb(data[i].place_img) + "' style='width:150px ; height:150px'><span>"+i+"</span>")
                       .append("<div class='planPlaceDelete' data-code='"+data[i].place_code+"'><a href='#'>삭제</a></div>") 
                       .css("border-bottom","1px solid #3a3c3f").appendTo(selectThis) ;
                    }
                    
                    placeMarker();
                 }
              
              })
           }
        
        });
              
    }
    
    //삭제 버튼 눌렀을 때, planList에서 삭제
    $(".selectPlace").on("click", ".planPlaceDelete" ,function(){
       //event.preventDefault(); // 이벤트를 막아준다. 404error 막아줌
       var planList = $(this) ;
       var place = $(this).parent().attr("data-code") ;

       console.log("place : " + $(this).parent());
       
       $.ajax({
          url : "planPlaceDelete" ,
          type : "POST" ,
          data : {
             place : place ,
             group : groupCode ,
             plan : planDay
          },
          success : function(){
             planList.parent().remove();
             planPriority();
             planRealTimePriority();
             placeMarker() ;
             
          },
          error : function(){
             alert("planPlaceDelete : error") ;
          }
          
       })
       
    });
/*-----------------------------------scheduleSuccess modal 창 -------------------------------------*/
    
    $("#scheduleSuccess").click(function(){
        materials = [] ; // 사용자가 등록한 준비물을 추가시켜줌
        materialCheck = [] ;
        materialDeleteCheck=[] ;
        managers = []; //data-manager이 본인이 등록했는지 관리자가 등록했는지
        var success = confirm("schedule을 저장하시겠습니까?");
        var smartCost = $("#smart-select input[type='radio']:checked").val() ;
        var cost ;
    
          // 체크박스에 check를 풀때, 디비 저장하기 위해서
         $("#material-list input").each(function(){
            var manager = $(this).parent().attr("data-manager") ;
           
            // checked가 아닌데 , data-check( check가 되어있다고 할때)
            if($(this).is(":checked") == false && $(this).parent().attr("data-check") == 1 ){
               console.log("delete : " + $(this).parent().attr("data-code") );
               materialDeleteCheck.push($(this).parent().attr("data-code")) ; // check 푼 것을 db에 저장
               $(this).parent().attr("data-code","0"); // check안된 상태로 돌려줌
            }
            
            if($(this).parent().attr("data-name") != '1'){ // 조건 : data-name != 1이 아니면 기존에 없던 준비물이 추가 된 것.
               alert("ddd0");
               managers.push(manager) ; 
               materials.push($(this).parent().children().eq(1).text()) ;
            }
            
            $(this).parent().attr("data-name", "1"); // 배열에 넣고나서 data-name에 1로 바꿔준다. 다시 완료버튼 눌렀을 때, 새로 등록되지 않기 위해
         })
        
         $("#material-list input[name='materialCheck']:checked").each(function(){
            if($(this).parent().attr("data-check") != "1"){ // 조건 : data-check != 1이 아니면 check안했던 것.
               alert("dddd");
               //console.log("ceheeir : " + $(this).parent().attr("data-code") + " ");
               materialCheck.push($(this).parent().children().eq(1).text()) ;
            }
            $(this).parent().attr("data-check", "1"); // 배열에 넣고나서 data-check에 1로 바꿔준다. 다시 완료버튼 눌렀을 때, 새로 등록되지 않기 위해
         }) ;
         
        
          
         if(smartCost == '차감'){
            cost = $("#smart-cost input[type='text']").val() ;
            
            if(cost == ""){
               alert("smart-cost 차감형 값을 적어주세요") ;
               return ;
            }
            
         }
         
        if(success == true){
           alert("확인을 누르셨ㄴ요");
           
          if(cost == null){
        	  cost = 0;
          }
           
           console.log(materialCheck);
           console.log(materialDeleteCheck);
           console.log(materials);
           console.log(managers);
           console.log(groupCode);
           console.log(smartCost);
           console.log(cost);
           
           
           $.ajax({
              url : "userPage/userScheduleList",
              type: "POST" ,
              traditional: true,
              data : {
                 materialCheck : materialCheck,
                 materialDeleteCheck : materialDeleteCheck,
                 materials : materials,
                 managers : managers,
                 groupCode : groupCode,
                 smartCost : smartCost,
                 cost : cost 
              }, error:function(){
            	  alert("전송에러");
              },
              contentType:"application/x-www-form-urlencoded;charset=utf-8"
           })
        }else{
           alert("취소");
        }
    })
    
    
/*-----------------------------------smart-cost modal 창 -------------------------------------*/
    $("input[type='radio'][id='smart_ deduction']").click(function(){
       $("#smart-cost").css("display","block");
    })
    
    $("input[type='radio'][id='smart_accumulate']").click(function(){
       $("#smart-cost").css("display","none");
       $("#smart-cost input[type='text']").val("");
    })
    
    

    
    
/*-----------------------------------준비물 modal 창 -------------------------------------*/
    $("#material-add > button").click(function(){
       var input = $("#material-add > input").val() ;
       var list = $("#material-list") ;
       
       if(input == ""){
          alert("준비물을 입력하세요");
          $("#material-add > input").focus();
       }else{
       
          $("<div class='material-list-atom' data-manager='0' data-check='0'><input type='checkbox' name='materialCheck' ><p>"+input+"</p><p class='materialDelete'>X</p></div>").appendTo(list) ;
       }
       $("#material-add > input").val("") ;
    
    })
    
    $("#material-list").on('click',"input",function(){
       
       if($(this).is(":checked") == false){
          $(this).removeAttr("checked");
          
       }
       else{
          $(this).attr("checked","true");
       }
    
    })
    
    $("#material-list").on('click', '.materialDelete', function(){
       
       var material_code = $(this).parent().attr("data-code") ;
       
       if(material_code) {
          
          $.ajax({
              url : "materialDelete" ,
              type : "POST" ,
              data : {
                 materialCode : material_code ,
                 groupCode : groupCode
              },
              success : function(){
                 
              }
           })

       }
       
       $(this).parent().remove() ;
       
    })
    
/*-----------------------------------장소체인지------------------------------------------*/
    var thisLocal ;
    
    $(".local-change").on("click", function(){
       
       if($(this).attr("data-check") == 0 ){
          $(this).attr("data-check",1);
          $(".local-change").text("완료 ▲")   ;
          $('.local-changeBox').slideDown();
       }else{
          $(this).attr("data-check",0);
          $(".local-change").text("장소변경 ▼")
           $('.local-changeBox').slideUp();
          localChange(thisLocal);
       }
    })
    
    
    $(".local-check").on("click", function(){
       if($(this).attr("data-check") == 0 ){
          $(".local-check").attr("data-check",0) ;
          $(".local-check").css("background","white") ;
          $(".local-check").css("color","black");
          
          $(this).attr("data-check",1);
          thisLocal = $(this).text() ;
          $(this).css("background","#585858");
          $(this).css("color","white");
          
       }else{
          $(this).attr("data-check",0);
          $(this).css("background","white") ;
          $(this).css("color","black");
          
       }
    })
   
  //장소변경할 때 함수.
    function localChange(thisLocal){
       var currentLocal = $(".turn-local > p").text() ;
       
       if(currentLocal != thisLocal){
          $.ajax({
             url : "placeList",
             type : "GET" ,
             data : { localData : thisLocal },
             dataType : "json",
             success : function(data){
                data01 = new Array() ;
                 $(".placeList").empty();
                 var elem ="" ;
                 if(data){
                    for(var i=0 ; i<data.length ; i++){
                       elem += "<div class='place placeCode' data-code='" + data[i].place_code +"' data-lat="+ data[i].place_lat 
                           +" data-lng="+ data[i].place_lng +" data-name="+ data[i].place_name + ">"
                           + "<img src='displayFile?fileName="+ thumb(data[i].place_img) + "'/></div>";
                       
                       data01[i] = new Array(5) ;
                       
                       data01[i][0] = data[i].place_code ;
                       data01[i][1] = data[i].place_lat ;
                       data01[i][2] = data[i].place_lng ; 
                       data01[i][3] = data[i].place_img ;
                       data01[i][4] = data[i].place_name;
                    }
                    
                    $(".placeList").append(elem) ;
                    $(".turn-local > p").text(thisLocal);
                    placeMarker() ; // 장소에 대한 마커 바로 불러주기위해!
                 }
             }
          })   
       }
    }
    
    

/*-----------------------------------place 장소 필터-------------------------------------*/ 
   $("#searchButton").on("click",function(){
      var input = $(".search-input").val() ;
      var radio = $(":input:radio[name=localCheck]:checked").val()
      console.log(typeof(input));
      console.log(radio);
      
      if(input == ""){
         alert("장소명을 입력하세요") ;
         return ;
      }
      
      if(input != null){
         
         $.ajax({
            url : "placeSearchList" ,
            type : "POST",
            data : {
               place_name : input ,
               local : radio 
            },
            success : function(data){
               data01 = new Array() ;
                $(".placeList").empty();
                var elem ="" ;
             
                if(data){
                   for(var i=0 ; i<data.length ; i++){
                      elem += "<div class='place placeCode' data-code='" + data[i].place_code +"' data-lat="+ data[i].place_lat 
                          +" data-lng="+ data[i].place_lng +" data-name="+ data[i].place_name + ">"
                          + "<img src='displayFile?fileName="+ thumb(data[i].place_img) + "'/></div>";
                      
                      data01[i] = new Array(5) ;
                      
                      data01[i][0] = data[i].place_code ;
                      data01[i][1] = data[i].place_lat ;
                      data01[i][2] = data[i].place_lng ; 
                      data01[i][3] = data[i].place_img ;
                      data01[i][4] = data[i].place_name;
                   }
                
                   $(".placeList").append(elem) ;
                
                   placeMarker() ; // 장소에 대한 마커 바로 불러주기위해!
                }
            },
            error : function(){
               
            }
   
         })
         $(".search-input").val("") ;
      }
   })
   
    $("#placeFilter").on("click",".placeIcon", function(){
       console.log($(this).attr("data-placeType"));
       console.log($(".turn-local > p").text());
       
       var local = $(".turn-local > p").text() ;
       var placeType = $(this).attr("data-placeType") ;
       
       $.ajax({
          url : "placeFilterList",
          type : "POST",
          data : {
             local : local,
             placeType : placeType 
          },
          success : function(data){
             data01 = new Array() ;
             $(".placeList").empty();
             var elem ="" ;
             
             
             if(data){
                for(var i=0 ; i<data.length ; i++){
                   elem += "<div class='place placeCode' data-code='" + data[i].place_code +"' data-lat="+ data[i].place_lat 
                       +" data-lng="+ data[i].place_lng +" data-name="+ data[i].place_name + ">"
                       + "<img src='displayFile?fileName="+ thumb(data[i].place_img) + "'/></div>";
                   
                   data01[i] = new Array(5) ;
                   
                   data01[i][0] = data[i].place_code ;
                   data01[i][1] = data[i].place_lat ;
                   data01[i][2] = data[i].place_lng ; 
                   data01[i][3] = data[i].place_img ;
                   data01[i][4] = data[i].place_name;
                }
             
                $(".placeList").append(elem) ;
             
                placeMarker() ; // 장소에 대한 마커 바로 불러주기위해!
             }
          },
          error : function(){
             
          }
          
       })
       
       
    })
    
  //이미지 썸네일
    function thumb(data){
       console.log(data)
       var idx = data.indexOf("/") + 1 ;
       var idxA = "/s_" + data.substr(idx) ;
       
       return idxA ;
    }
    
    
/*-----------------------------------지도-------------------------------------*/
    var mapDiv = document.getElementById('mapArea')  ;
    var map = new naver.maps.Map(mapDiv) ;
    var planB ;
    var count = 0 ;
 
    
    
    
   
    // 날짜가 바꼈을 경우, 이전 날짜에 선택된 장소를 숨김
    function markerHide(){
       
       var hide ;
       var hides ;
       
       naver.maps.Event.addListener(map ,'idle' , function(){
           
           // idle 지도의 움직이 멈췄을 때, 발생
          if(markersPlan != null){
             for(var i=0 ; i < markersPlan.length ; i++ ){
                  hide = markersPlan[i] ;
                  //position = marker.getPosition(); // 마커의 위치 반환
                  //alert("g확인 : " + markersPlan.length);
                  hide.setMap(null);
                  
               }
           }
          
          if(markers != null){
             for(var i=0 ; i < markers.length ; i++ ){
                  hides = markers[i] ;
                  //position = marker.getPosition(); // 마커의 위치 반환
                  console.log("hide  : " + markers[i]);
                  hides.setMap(null);
             }
          }
             
        })   
        
    }
    
    
    //planB에 다가 선택한 장소를 넣음
    function planBArray(){
       planB = new Array() ;// 초기화시켜주고 다시 넣음
       count = 0 ; //배열의 크기
   
       $(".selectPlace > div").each(function(){
          //console.log("this " + $(this).children().eq(0));
            planB[count] = new Array(4) ;
            
            planB[count][0] = $(this).attr("data-pri") ;
            planB[count][1] = $(this).attr("data-lat") ;
            planB[count][2] = $(this).attr("data-lng") ;
            planB[count][3] = $(this).children().eq(0).attr("src") ;
            planB[count][4] = $(this).attr("data-name") ;
           
            
            count++ ;
            //console.log("planB : " + $(this).children().eq(0).attr("src") );
            
        })
    }
    

    // 마커 호출
    function placeMarker(){
    	
       if (data01.length == 0) {
    	   return false;
       }
    	
       if(markersPlan != null || markers != null){
          
          markerHide() ;
       }
       
       planBArray();
           
        // 만약 count 값이 0이면 계획에 등록된 장소가 없으므로 
       // 제일 먼저 등록되어 있는 장소를 초첨 잡는다
       if(count == 0){
       
          //console.log("위도 : "+ planB[0][1] + "경도 : " + planB[0][2] );

          map = new naver.maps.Map(mapDiv , {
             center : new naver.maps.LatLng(data01[0][1], data01[0][2]) ,
             zoom : 7 
          })
       } else{
           map = new naver.maps.Map(mapDiv , {
               center : new naver.maps.LatLng(planB[0][1],planB[0][2]) ,
               zoom : 7
            })
       }
        markers = [];
        infoWindows = [] ;
       // data01.length만큼의 마커를 만들어줌
       for(var i=0 ; i<data01.length ; i++){
                  
           var marker = new naver.maps.Marker({
               position: new naver.maps.LatLng(data01[i][1], data01[i][2]),
               map: map,
               icon : {
                  content: [
                     '<div class="cs_mapbridge marker">',
                  '<img src="./resources/img/map/place_marker.png">',
                  '</div>'
               ].join(''),
               size: new naver.maps.Size(22, 35),
                 anchor: new naver.maps.Point(25,48)
             },
               zIndex : 100 
           });

          
           
           var infoWindow = new naver.maps.InfoWindow({
                content : "<div style='width:350px; height:180px ; padding:10px ; position: relative'>"
                      +"<div>"
                      +    "<div style='display:inline-block'>"
                      +       "<img src='displayFile?fileName="+ thumb(data01[i][3]) + "'  style='width:100px; height:100px'>"
                      +       "</div>"
                      +    "<div style='display:inline-block ; padding-left:10px'>"
                      +       "<h4 style='color:black'>"+data01[i][4]+"</h4>"
                      +         "<p style='color:black'>장소평점이랑 ㅇㅇㅇㅇ</p>"
                      +    "</div>"
                      +      "<div class='d' style='position: absolute; width:20px ; height:20px; border:1px solid black; text-align:center; line-height : 20px; right:10px; top:10px'><a href=\javascript:infoClose(" +i+ ");\>X</a>"      
                      +      "</div>"
                      + "</div>"
                      + "<div style='padding-top:8px; border-top: 1px solid black; margin-top:10px' >"
                      +    "<div style='width:160px; height: 40px; float:left; border:1px solid black; margin-right:10px; text-align:center; line-height : 40px'><a href='alert2('dddd');'>자세히보기</a></div>"
                      +    "<div style='width:160px; height: 40px; float:left; border:1px solid black; text-align:center; line-height : 40px'><a href='#'>일정에 추가</a></div>"
                      + "</div>"
                      + "</div>"
               });

            infoWindows.push(infoWindow) ;
            
           
            
            
            
           // markers 배열에 넣어줌 
           markers.push(marker) ;  
           marker.addListener("mouseover", onMouseOver) ;
           marker.addListener("mouseout", onMouseOut) ;
           
           marker = null ;
           
           
        }
       
       
       
        
        markersPlan = [];
        infoWindowsPlan = [] ;
       // planB가 존재하면 실행 
       if(count > 0){
          for(var i=0 ; i<planB.length ; i++){

             var marker01 = new naver.maps.Marker({
                position : new naver.maps.LatLng(planB[i][1],planB[i][2]),
                map : map ,
                icon : {
                   content: [
                         '<div class="cs_mapbridge marker">',
                            '<img src="./resources/img/map/select_marker.png">',
                            '<div class="priority2">'+(i+1)+'</div>',
                          '</div>'
                   ].join(''),
                   size: new naver.maps.Size(22, 35),
                     anchor: new naver.maps.Point(25,48)
                   
                },
                zIndex : 100 
             })
             
              
               console.log("info before : " + planB[i][3]);
              var infoWindow = new naver.maps.InfoWindow({
                content : "<div style='width:350px; height:180px ; padding:10px ; position: relative'>"
                      +"<div>"
                      +    "<div style='display:inline-block'>"
                      +       "<img src='"+planB[i][3] +"' style='width:100px ; height:100px'>"
                      +       "</div>"
                      +    "<div style='display:inline-block ; padding-left:10px'>"
                      +       "<h4 style='color:black'>"+planB[i][4]+"</h4>"
                      +         "<p style='color:black'>"+ i +"</p>"
                      +    "</div>"
                      +      "<div class='d' style='position: absolute; width:20px ; height:20px; border:1px solid black; text-align:center; line-height : 20px; right:10px; top:10px'><a href=\javascript:infoClosePlan(" +i+ ");\>X</a>"      
                      +      "</div>"
                      + "</div>"
                      + "<div style='padding-top:8px; border-top: 1px solid black; margin-top:10px' >"
                      +    "<div style='width:160px; height: 40px; float:left; border:1px solid black; margin-right:10px; text-align:center; line-height : 40px'><a href=\"javascript:alert2('dddd')\">자세히보기</a></div>"
                      +    "<div style='width:160px; height: 40px; float:left; border:1px solid black; text-align:center; line-height : 40px'><a href='#'>일정에 추가</a></div>"
                      + "</div>"
                      + "</div>"
               });
              
              
               
               infoWindowsPlan.push(infoWindow) ;
               console.log("marker01 : " +infoWindow.content) ;
               marker01.set('seq', i+1 ) ;
             markersPlan.push(marker01) ;
             marker01.addListener("mouseover", onMouseOverPlan) ;
               marker01.addListener("mouseout", onMouseOutPlan) ;
              
               marker01 = null ;
               
              
          }
          
          
       }
    
        naver.maps.Event.addListener(map ,'idle' , function(){
           
           // idle 지도의 움직이 멈췄을 때, 발생
           updateMarkers(map, markers) ;
           if(count > 0)
              updateMarkers(map, markersPlan);
        })
        
        function updateMarkers(map , markers) {
           var mapBounds = map.getBounds() ; // 현재 지도의 좌표경계
           var marker , position; 
           
           for(var i=0 ; i < markers.length ; i++ ){
              marker = markers[i] ;
              position = marker.getPosition(); // 마커의 위치 반환
              
              if(mapBounds.hasLatLng(position)){
                 showMarker(map, marker) ;             
              }else {
                 hideMarker(map, marker) ;
              }
           }
        }
        
        
        function showMarker(map, marker) {

            if (marker.setMap()) return;
            marker.setMap(map);
        }

        function hideMarker(map, marker) {

            if (!marker.setMap()) return;
            marker.setMap(null);
        }
        
        var polyline = new naver.maps.Polyline({
            map: map,
            path: [],
            endIcon: naver.maps.PointingIcon.OPEN_ARROW,
            //아이콘의 크기를 지정합니다. 단위는 픽셀입니다.
            endIconSize: 20,
            strokeColor: '#5347AA',
            strokeWeight: 2
        });
        
        for(var i=0 ; i<planB.length ; i++){
           var path = polyline.getPath() ;
           if(i==0){
              polyline.setVisible(false);
           }else{
              polyline.setVisible(true);
           }
           path.push(new naver.maps.LatLng(planB[i][1], planB[i][2]));
           console.log(path);
        }
        
        for(var i=0 ; i<data01.length ; i++){
           naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i)) ;
           
           console.log("mrea : " + markers[i] )
        }
        
        for(var i=0 ; i<planB.length ; i++){
           naver.maps.Event.addListener(markersPlan[i], 'click', getClickHandlerPlan(i)) ;
           
           console.log("markersPlan[i] : " + markersPlan[i] )
        }
        
    }
    
    
 // 마커마다 정보창 띄우기 (모든 장소)
    function getClickHandler(seq){
       return function(e){
          var marker = markers[seq] ;
          var infoWindow = infoWindows[seq] ;
          
          if(infoWindow.getMap()){
             infoWindow.close() ;
          }
          else{
             infoWindow.open(map, marker) ;
          }
       }
    }
    // 마커마다 정보창 띄우기 (사용자가 계획한 장소)
    function getClickHandlerPlan(seq){
       return function(e){
          var marker = markersPlan[seq] ;
          var infoWindow = infoWindowsPlan[seq] ;
          
          if(infoWindow.getMap()){
             infoWindow.close() ;
          }
          else{
             infoWindow.open(map, marker) ;
          }
       }
    }
  

});

function onMouseOver(e){
   var marker = e.overlay 

   marker.setIcon({
      
        content: [
              '<div class="cs_mapbridge marker">',
            '<img src="./resources/img/map/place_marker_click.png">',
            '</div>'
      ].join(''),
      size: new naver.maps.Size(22, 35),
      anchor: new naver.maps.Point(25,48)
   });
}

function onMouseOverPlan(e){
   var marker = e.overlay 

   marker.setIcon({
      
        content: [
              '<div class="cs_mapbridge marker">',
            '<img src="./resources/img/map/select_marker_click.png">',
            '</div>'
      ].join(''),
      size: new naver.maps.Size(22, 35),
      anchor: new naver.maps.Point(25,48)
   });
}


function onMouseOut(e){
   var marker = e.overlay 
   
   marker.setIcon({
      content: [
          '<div class="cs_mapbridge marker">',
         '<img src="./resources/img/map/place_marker.png">',
         '</div>'
      ].join(''),
      size: new naver.maps.Size(22, 35),
      anchor: new naver.maps.Point(25,48)
   });


}

function onMouseOutPlan(e){
   var marker = e.overlay
   var seq = marker.get('seq') ;
   
   marker.setIcon({
      content: [
         '<div class="cs_mapbridge marker">',
            '<img src="./resources/img/map/select_marker.png">',
            '<div class="priority2">'+seq+'</div>',
          '</div>'
      ].join(''),
      size: new naver.maps.Size(22, 35),
      anchor: new naver.maps.Point(25,48)
   });


}



//infoWindow 창에 x누르면 꺼짐
function infoClose(seq){
   var infoWindow = infoWindows[seq]; 
   
   if(infoWindow.getMap()){
      infoWindow.close();
   }
}

//infoWindow 창에 x누르면 꺼짐
function infoClosePlan(seq){
   var infoWindow = infoWindowsPlan[seq]; 
   
   if(infoWindow.getMap()){
      infoWindow.close();
   }
}

// 여비 한도 
function onlyNumber(){
   if((event.keyCode<48) || (event.keyCode > 57 )){
      alert("금액을 입력하세요") ;
      event.returnValue = false ;
   }
}


//처음 준비물 리스트 불러오기
function material_list(groupCode){
   
   console.log("gorupd : " + groupCode );
   
   $.ajax({
      url : "material_list" ,
      type : "POST",
      data : { 
         groupCode : groupCode 
      },
      success : function(data){
         
         var list = $("#material-list") ;
         for(var i=0 ; i<data.length ; i++){
            if(data[i].material_check != 0 ){
               $("<div class='material-list-atom' data-manager='"+data[i].manager_check+"' data-code='"+data[i].material_code+"' data-name='1' data-check='1'>" 
               +"<input type='checkbox' name='materialCheck' checked>"
               +"<p>"+data[i].material_name+"</p>" 
               +"<p class='materialDelete'>X</p></div>").appendTo(list) ;
            }else {
               $("<div class='material-list-atom' data-manager='"+data[i].manager_check+"' data-code='"+data[i].material_code+"' data-name='1' data-check='0'>"
               +"<input type='checkbox' name='materialCheck' >" 
               +"<p>"+data[i].material_name+"</p>" 
               +"<p class='materialDelete'>X</p></div>").appendTo(list) ;   
            }
         }
         // 관리자가 제공하는 기본 준비물의 x표 뺴기
         $("#material-list div").each(function(){
            console.log($(this).attr("data-manager")) ;
            if($(this).attr("data-manager") == '1'){
               console.log($(this).children().eq(2)) ;
               $(this).children().eq(2).remove() ;
            }
         })
   
      }
   })
}