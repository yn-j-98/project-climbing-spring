<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html class="wf-publicsans-n3-active wf-publicsans-n4-active wf-publicsans-n5-active wf-publicsans-n6-active wf-publicsans-n7-active wf-fontawesome5solid-n4-active wf-fontawesome5regular-n4-active wf-simplelineicons-n4-active wf-fontawesome5brands-n4-active wf-active sidebar-color">
<head>
  <title>관리자 : 회원관리</title>

  <!-- Fonts and icons -->
  <script src="../../assets/js/plugin/webfont/webfont.min.js"></script>
  <script src="https://kit.fontawesome.com/7f7b0ec58f.js" crossorigin="anonymous"></script>

  <!-- CSS Files -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <link rel="stylesheet" href="../../assets/css/plugins.min.css" />
  <link rel="stylesheet" href="../../assets/css/kaiadmin.css" />

  <!--   Core JS Files   -->
  <script src="../../assets/js/core/jquery-3.7.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

  <!-- template Js File -->
  <script src="../../assets/js/kaiadmin.min.js"></script>

  <!-- pagination Js -->
  <script src="../../js/pagination.js"></script>

  <!-- sweetAlert JS FILE -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
  <script src="../../js/sweetAlert_modal.js"></script>

  <!-- DatePicker Js CDN -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.10.0/locales/bootstrap-datepicker.ko.min.js" integrity="sha512-L4qpL1ZotXZLLe8Oo0ZyHrj/SweV7CieswUODAAPN/tnqN3PA1P+4qPu5vIryNor6HQ5o22NujIcAZIfyVXwbQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

  <script>
    $(document).ready(function() {

      const table_datas = $('.member-table-tr');

      table_datas.each(function () {
        const button = $(this).find('.member_delete');
        const delete_id = $(this).find('.member-id').text();
        button.on('click', function () {
          console.log(delete_id);
          $('#hidden_member_id').val(delete_id);

          sweetAlert_confirm_error('Test error','정말 삭제하시겠습니까?','삭제','취소').then(function(flag){
            console.log(flag);
            if (flag) {
              console.log('사용자 삭제 진행');
              $('#delete-member-form').submit();
            }
          })

        })
      });

      const form = $('#search-form');
      $('#search_button').on('click', function () {
        form.submit();
      })

      const search_keyword = $('#search_keyword');
      search_keyword.on('change', function () {
        $('#search_content').datepicker({
          format: 'yyyy-mm-dd', //데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
          startDate: '-10d', //달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
          endDate: '+10d', //달력에서 선택 할 수 있는 가장 느린 날짜. 이후로 선택 불가 ( d : 일 m : 달 y : 년 w : 주)
          autoclose: true, //사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
          calendarWeeks: false, //캘린더 옆에 몇 주차인지 보여주는 옵션 기본값 false 보여주려면 true
          clearBtn: false, //날짜 선택한 값 초기화 해주는 버튼 보여주는 옵션 기본값 false 보여주려면 true
          datesDisabled: ['2019-06-24', '2019-06-26'], //선택 불가능한 일 설정 하는 배열 위에 있는 format 과 형식이 같아야함.
          daysOfWeekDisabled: [0, 6], //선택 불가능한 요일 설정 0 : 일요일 ~ 6 : 토요일
          daysOfWeekHighlighted: [3], //강조 되어야 하는 요일 설정
          disableTouchKeyboard: false, //모바일에서 플러그인 작동 여부 기본값 false 가 작동 true가 작동 안함.
          immediateUpdates: false, //사용자가 보는 화면으로 바로바로 날짜를 변경할지 여부 기본값 :false
          multidate: false, //여러 날짜 선택할 수 있게 하는 옵션 기본값 :false
          multidateSeparator: ',', //여러 날짜를 선택했을 때 사이에 나타나는 글짜 2019-05-01,2019-06-01
          templates: {
            leftArrow: '&laquo;',
            rightArrow: '&raquo;',
          }, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징
          showWeekDays: true, // 위에 요일 보여주는 옵션 기본값 : true
          title: '테스트', //캘린더 상단에 보여주는 타이틀
          todayHighlight: true, //오늘 날짜에 하이라이팅 기능 기본값 :false
          toggleActive: true, //이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
          weekStart: 0, //달력 시작 요일 선택하는 것 기본값은 0인 일요일
          language: 'ko', //달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
        }).on('changeDate', function (e) {
          /* 이벤트의 종류 */
          //show : datePicker가 보이는 순간 호출
          //hide : datePicker가 숨겨지는 순간 호출
          //clearDate: clear 버튼 누르면 호출
          //changeDate : 사용자가 클릭해서 날짜가 변경되면 호출 (개인적으로 가장 많이 사용함)
          //changeMonth : 월이 변경되면 호출
          //changeYear : 년이 변경되는 호출
          //changeCentury : 한 세기가 변경되면 호출 ex) 20세기에서 21세기가 되는 순간

          console.log(e);
          // e.date를 찍어보면 Thu Jun 27 2019 00:00:00 GMT+0900 (한국 표준시) 위와 같은 형태로 보인다.
        });
      });

    })

  </script>

</head>
<body class="user-select-none">
<mytag:admin_gnb member_id="Controller 데이터 입력할 예정"></mytag:admin_gnb>

<div class="main-panel p-2 overflow-x-hidden">
  <div class="nav-toggle position-absolute top-0 start-0">
    <button class="sidenav-toggler btn btn-icon btn-round btn-white h-100 w-25">
      <i class="fa fa-align-left"></i>
    </button>
  </div>

  <div class="col-md-12 align-content-center">
    <h1 class="text-center mt-5 mb-4">회원관리</h1>
  </div>

  <!-- FIXME 검색 코드 시작 -->
  <form id="search-form" action="userManagement.do" method="GET">
    <div class="row mb-3">
      <div class="d-flex justify-content-between align-items-center">
        <div class="col-md-2 ms-auto">
          <select class="form-select form-control" id="search_keyword" name="search_keyword">
            <option value="MEMBERNAME" ${search_keyword eq 'MEMBERNAME'?'selected':''}>회원 이름</option>
            <option value="MEMBERID" ${search_keyword eq 'MEMBERID'?'selected':''}>회원 아이디</option>
            <option value="DATE" ${search_keyword eq 'DATE'?'selected':''}>가입 날짜</option>
          </select>
        </div>
        <div class="col-md-7">
          <input type="text" class="form-control" id="search_content" name="search_content" value="${search_content}" placeholder="검색어를 입력해주세요">
        </div>
        <div class="me-auto">
          <button class="btn btn-primary btn-border" id="search_button" type="button">검색</button>
        </div>
      </div>
    </div><!-- 검색 코드 종료 -->
  </form>

  <!-- FIXME 사용자 리스트 코드 시작 -->
  <div class="row text-center d-flex justify-content-center">
    <div class="col-md-10">
      <div class="table-wrapper">
        <table class="table caption-top table-striped" id="member-table">
          <thead>
          <tr>
            <th scope="col" class="col-md-1">회원번호</th>
            <th scope="col" class="col-md-3">회원아이디</th>
            <th scope="col" class="col-md-3">회원이름</th>
            <th scope="col" class="col-md-3">가입날짜</th>
            <th scope="col" class="col-md-2">회원삭제</th>
          </tr>
          </thead>
          <tbody id="member-tbody">
          <c:if test="${empty data}" var="member">
            <tr>
              <td colspan="5">현재 회원이 없습니다.</td>
            </tr>
          </c:if>
          <c:if test="${not empty data}">
            <c:forEach items="${data}" var="member" varStatus="status">
              <tr class="member-table-tr">
                <th scope="row" class="member-num" id="member_num">${status.count}</th>
                <td><a class="submenu text-dark col-md-1 member-id" href="userManagementDetail.do?member_id=${member.member_id}">${member.member_id}</a></td>
                <td>${member.member_name}</td>
                <td>${member.member_registration_date}</td>
                <td><button class="btn btn-danger member_delete">삭제</button></td>
              </tr>
            </c:forEach>
          </c:if>
          </tbody>
        </table>
      </div>
    </div>
  </div><!-- 사용자 리스트 코드 종료 -->

  <!-- FIXME 페이지네이션 코드 시작 -->
  <div id="pageNation" class="row justify-content-center">
    <div class="row pt-5">
      <div class="col-md-12 d-flex justify-content-center ">
        <nav aria-label="Page navigation">
          <%-- <input type="hidden" id="totalCount" value="${member_total}">
           <input type="hidden" id="currentPage" value="${page}">--%>
          <input type="hidden" id="totalCount" value="${total}">
          <input type="hidden" id="currentPage" value="${page}">
          <ul id="pagination" class="pagination justify-content-center align-items-center">

          </ul>
        </nav>
      </div>
    </div>
  </div><!-- 페이지네이션 코드 종료 -->

  <form id="delete-member-form" action="adminDeleteMember.do" method="POST">
    <input type="hidden" id="hidden_member_id" name="member_id">
  </form>

</div>

</body>
</html>
