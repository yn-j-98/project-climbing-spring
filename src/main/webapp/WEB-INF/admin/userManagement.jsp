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
  <link rel="stylesheet" href="../../assets/css/bootstrap.min.css" />
  <link rel="stylesheet" href="../../assets/css/plugins.min.css" />
  <link rel="stylesheet" href="../../assets/css/kaiadmin.css" />

  <!--   Core JS Files   -->
  <script src="../../assets/js/core/jquery-3.7.1.min.js"></script>
  <script src="../../assets/js/core/bootstrap.min.js"></script>

  <!-- template Js File -->
  <script src="../../assets/js/kaiadmin.min.js"></script>

  <!-- pagination Js -->
  <script src="../../js/pagination.js"></script>

  <!-- sweetAlert JS FILE -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
  <script src="../../js/sweetAlert_modal.js"></script>

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
  <div class="row mb-3">
    <div class="d-flex justify-content-between align-items-center">
      <div class="col-md-2 ms-auto">
        <select class="form-select form-control" id="search_keyword" name="search_keyword">
          <option value="MEMBERNAME" ${member_search_keyword == 'MEMBERNAME'?'selected':''}>회원 이름</option>
          <option value="MEMBERID" ${member_search_keyword == 'MEMBERID'?'selected':''}>회원 아이디</option>
          <option value="DATE" ${member_search_keyword == 'DATE'?'selected':''}>가입 날짜</option>
        </select>
      </div>
      <div class="col-md-7">
        <input type="text" class="form-control" id="search_content" name="search_content" placeholder="검색어를 입력해주세요">
      </div>
      <div class="me-auto">
        <button class="btn btn-primary btn-border" type="button">검색</button>
      </div>
    </div>
  </div><!-- 검색 코드 종료 -->

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
                <td><a class="submenu text-dark col-md-1 member-id" href="userManagementDetail.do">${data.member_id}</a></td>
                <td>${data.member_name}</td>
                <td>${data.member_registration_date}</td>
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

  <form id="delete-member-form" action="member_delete.do" method="POST">
    <input type="hidden" id="hidden_member_id" name="member_id">
  </form>

</div>

</body>
</html>
