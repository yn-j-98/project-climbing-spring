<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 인포윈도우 스타일 -->
<style>
.custom-info-window {
    white-space: nowrap; /*줄바꿈 방지*/
}
</style>

</head>
<body>


<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e21f299ee3c0c9b60909dcd9bfec2038&libraries=services"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e21f299ee3c0c9b60909dcd9bfec2038"></script>


<div id="map" style="width: 100%; height: 800px;"></div>

<script type="text/javascript">
// 카카오에서 지원해줌 == 그 툴 사용
// apikey값이 지도생성보다 먼저나와야한다 // 나중에 넣으면 지도생성x

    // 지도 생성 및 옵션 설정
    var container = document.getElementById('map'); // 지도를 담을 영역의 DOM 레퍼런스 기본생성
    var options = { // 지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(37.5665, 126.978), // 초기 지도 중심 좌표 (서울)
        level: 8 // 지도의 레벨(확대, 축소 정도). 숫자가 클수록 확대됨.
    };

    // kakao.maps.Map 객체를 생성하여 'map' 요소에 지도를 추가
    var map = new kakao.maps.Map(container, options); // 카카오 맵 내장객체에 담는다
    
    
    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();
    
   // json타입으로 작성된 datas 받아온다
   var datas=${datas};
    
   // 받아온 json 데이터 만큼 반복해서
   datas.forEach(function(data){
      
      // 주소값으로 좌표값 검색
       geocoder.addressSearch(data.address, function(result, status) {
          
           // 정상적으로 검색이 완료됐으면 
           if (status === kakao.maps.services.Status.OK) {
              
              // searchPosition에 x,y 좌표를 담는다
                 var searchPosition = new kakao.maps.LatLng(result[0].y, result[0].x);
   
               // 장소에 대한 정보 배열 생성
               var places = [ // json으로 front >> front로 보내고 싶을 때 사용하면 된다
                   {   
                       position: searchPosition, // x,y좌표 배열에 저장
                       title: data.title, // 장소 제목
                       id: data.title // 고유 식별자
                   }
                   // 추가 장소는 이 배열에 추가하면 됨
               ];
               
               // 마커와 정보 창을 추가하는 함수
               function addMarkersAndInfoWindows() {
                     places.forEach(function(place){
                        
                        // 마커를 생성
                         var marker = new kakao.maps.Marker({
                           position: place.position, // 마커의 위치
                           map: map // 마커를 추가할 지도 객체
                       });
   
                       // 마커 클릭 시 이벤트
                       kakao.maps.event.addListener(marker, 'click', function() {
                          gymClick(place.id); // 해당 ID를 가진 div를 클릭
                       });
                       
                       // 마커에 표시할 인포윈도우를 생성
                       var infowindow = new kakao.maps.InfoWindow({
                           content: '<div class="custom-info-window">' + place.title + '</div>' // 인포윈도우에 표시할 내용
                       });
                       
                       // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록
                       // 이벤트 리스너로는 클로저를 만들어 등록
                       // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록
                       kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
                       kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
                  });
               }
              
               // div 클릭 이벤트 트리거 함수
               function gymClick(id) {
                   var div = document.getElementById(id);
                   if (div) {
                       div.click(); 
                   }
               }
               
               // 인포윈도우를 표시하는 클로저를 만드는 함수 
               function makeOverListener(map, marker, infowindow) {
                   return function() {
                       infowindow.open(map, marker);
                   };
               }
   
               // 인포윈도우를 닫는 클로저를 만드는 함수 
               function makeOutListener(infowindow) {
                   return function() {
                       infowindow.close();
                   };
               }
               
               // 마커와 정보 창 추가
               addMarkersAndInfoWindows();
           } 
       });
    });
   
    function handleClick(element) {
        // 배경색을 회색으로 변경
        element.classList.toggle('active');
        
        // 이동 버튼의 visibility를 토글
        const button = element.querySelector('.col-md-3');
        if (button) {
            button.classList.toggle('d-none');
        }
    }
    
    // 위치 오류 처리 함수 (나중에 추가 예정)
    function handleLocationError(browserHasGeolocation, errorMessage) {
        var message = browserHasGeolocation ?
            'Error: ' + errorMessage : // 지오로케이션 지원하지만 오류 발생
            'Error: Your browser does not support geolocation.'; // 지오로케이션 미지원
        alert(message); // 오류 메시지를 알림으로 표시
    }

</script>

      
</body>
</html>