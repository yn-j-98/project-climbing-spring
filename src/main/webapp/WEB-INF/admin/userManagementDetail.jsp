<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html class="wf-publicsans-n3-active wf-publicsans-n4-active wf-publicsans-n5-active wf-publicsans-n6-active wf-publicsans-n7-active wf-fontawesome5solid-n4-active wf-fontawesome5regular-n4-active wf-simplelineicons-n4-active wf-fontawesome5brands-n4-active wf-active sidebar-color">

<head>
  <title>관리자 : 회원정보 수정</title>
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

  <!-- sweetAlert JS FILE -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
  <script src="../../js/sweetAlert_modal.js"></script>
  <script>
    $(document).ready(function() {
      let form = $('#form');
      form.on('submit', function(e) {
        e.preventDefault();
      });

      // from.submit();
      $('#submitButton').on('click', function () {
        sweetAlert_confirm_warning('수정하시겠습니까?', '', '수정', '취소').then(function (result) {
          if (result) {
            form.off('submit').submit();
          }
        });
      })
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

  <div class="row d-flex justify-content-between align-items-center">
    <div class="ms-auto me-auto mt-5 col-md-11">
      <div class="card">
        <div class="card-header bg-white">
          <h1 class="text-center"> ${data.member_name}님 회원 변경 </h1>
        </div>
        <div class="card-body p-3">
          <form action="userManagementDetail.do" method="POST" id="form">
            <div class="row g-3 mb-3">
              <div class="col-md-6">
                <div class="form-floating">
                  <input type="email" class="form-control" id="member_id" name="member_id" placeholder="ID" value="${data.member_id}">
                  <label for="member_id" class="form-label">ID</label>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-floating">
                  <input type="password" class="form-control" id="member_password" name="member_password" placeholder="Password" value="${data.member_password}">
                  <label for="member_password" class="form-label">Password</label>
                </div>
              </div>
            </div>
            <div class="row g-3 mb-3">
              <div class="col-md-6">
                <div class="form-floating">
                  <input type="text" class="form-control" id="member_name" name="member_name" placeholder="Name" value="${data.member_name}">
                  <label for="member_name" class="form-label">Name</label>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-floating">
                  <input type="number" min="0" class="form-control" id="current_point" name="member_current_point" placeholder="Current Point" value="${data.member_current_point}">
                  <label for="current_point" class="form-label">Current Point</label>
                </div>
              </div>
            </div>
            <div class="row g-3 mb-3">
              <div class="col-md-12">
                <div class="form-floating">
                  <input type="text" class="form-control" id="member_location" name="member_location" placeholder="Address" value="${data.member_location}">
                  <label for="member_location" class="form-label">Address</label>
                </div>
              </div>
            </div>
            <div class="row g-3 mb-3">
              <div class="col-md-12">
                <div class="form-floating">
                  <input type="number" class="form-control" id="member_crew" name="member_crew_num" min="0" placeholder="Crew" value="${data.member_crew_num}">
                  <label for="member_crew" class="form-label">Crew</label>
                </div>
              </div>
            </div>
            <div class="row g-3 mb-3">
              <div class="col-md-12">
                <div class="form-floating">
                  <input type="tel" class="form-control" id="member_phone" name="member_phone" placeholder="Phone" value="${data.member_phone}">
                  <label for="member_phone" class="form-label">Phone</label>
                </div>
              </div>
            </div>
            <div class="row g-3 mb-3">
              <div class="col-md-12 ">
                <div class="row">
                  <div class="col-md-2 ms-auto">
                    <label for="member_role" class="form-label">Role</label>
                    <select class="form-select" id="member_role" name="member_role">
                      <option value="F">사용자</option>
                      <option value="T" ${data.member_role eq 'T' ? 'selected':''}>관리자</option>
                    </select>
                  </div>
                  <div class="col-md-2 mt-auto">
                    <div class="row w-100 justify-content-end">
                      <button type="button" onclick='window.location.href = "userManagement.do"' class="btn btn-primary col-md-5 m-1">취소</button>
                      <button id="submitButton" class="btn btn-primary col-md-5 m-1">등록</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>
