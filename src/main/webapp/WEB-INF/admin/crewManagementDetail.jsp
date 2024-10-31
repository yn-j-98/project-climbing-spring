<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html class="wf-publicsans-n3-active wf-publicsans-n4-active wf-publicsans-n5-active wf-publicsans-n6-active wf-publicsans-n7-active wf-fontawesome5solid-n4-active wf-fontawesome5regular-n4-active wf-simplelineicons-n4-active wf-fontawesome5brands-n4-active wf-active sidebar-color">

<head>
  <title>관리자 : 크루전 확인</title>
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
</head>
<body class="user-select-none">
<mytag:admin_gnb member_id="${MEMBER_ID}"></mytag:admin_gnb>

<div class="main-panel p-2 overflow-x-hidden">
<div class="nav-toggle position-absolute top-0 start-0">
  <button class="sidenav-toggler btn btn-icon btn-round btn-white h-100 w-25">
    <i class="fa fa-align-left"></i>
  </button>
</div>

<!-- FIXME 크루전 정보 코드 시작 -->
<div class="row d-flex justify-content-between align-items-center">
<div class="ms-auto me-auto mt-5 col-md-11">
<div class="card">
<div class="card-header bg-white">
  <h1 class="text-center"> 크루전 정보 </h1>
</div>
<div class="card-body p-3">
<div class="row">
  <div class="col-md-12 d-flex justify-content-center">
    <label for="gym-name" class="form-label col-md-1 mt-auto text-center">암벽장</label>
    <div class="col-md-10">
      <input type="text" id="gym-name" readonly class="border-0 form-control" value="${data.battle_gym_name}">
    </div>
  </div>
</div>
<div class="row mt-4 p-0">
  <div class="col-md-12 d-flex justify-content-center">
    <label for="gym-date" class="form-label col-md-1 mt-auto text-center">경기일</label>
    <div class="col-md-10">
      <input type="text" id="gym-date" readonly class="border-0 form-control" value="${data.battle_game_date}">
    </div>
  </div>
</div>
<div class="row mt-4 p-0">
  <div class="col-md-12 d-flex justify-content-center">
    <label for="winner-crew" class="form-label col-md-1 mt-auto text-center">승리크루</label>
    <div class="col-md-10">
      <input type="text" id="winner-crew" readonly class="border-0 form-control" value="${data.battle_crew_name}">
    </div>
  </div>
</div>
<div class="row mt-4 p-0">
  <div class="col-md-12 d-flex justify-content-center">
    <label for="gym-mvp" class="form-label col-md-1 mt-auto text-center">MVP</label>
    <div class="col-md-10">
      <input type="text" id="gym-mvp" readonly class="border-0 form-control" value="${data.battle_member_name}">
    </div>
  </div>
</div>

<!-- FIXME 참여크루 코드 시작 -->
<div class="row d-flex justify-content-between align-items-center">
<div class="ms-auto me-auto mt-5 col-md-11">
<div class="row">
  <h2 class="col-md-6 ms-auto me-auto text-center">크루 정보</h2>
</div>

<div class="row d-flex d-flex justify-content-center">
<div class="row col-md-10 d-flex justify-content-center">
<!-- 참여 크루 card 생성 시작 -->
<c:if test="${empty datas}" var="crew">
  <div class="row col-md-6 mt-3 ms-auto me-auto">
    <div class="card">
      <div class="card-body">
        <div class="row d-flex justify-content-center">
          현재 크루가 없습니다.
        </div>
      </div>
    </div>
  </div>
</c:if>
  <!--CREW_LEADER AS BATTLE_MEMBER_NAME
  CREW_NAME AS BATTLE_CREW_NAME
  CREW_CURRENT_MEMBER_SIZE AS BATTLE_CREW_NUM
  -->
<c:if test="${not empty datas}">
  <c:forEach items="${datas}" var="data">
    <div class="row col-md-6 mt-3 ms-auto me-auto">
      <div class="card">
        <div class="card-body">
          <div class="row d-flex justify-content-center">
            <div class="col-md-12">
              <p class="card-title text-center h4">${data.battle_crew_name} <span class="badge bg-secondary">${data.battle_crew_num}</span></p>
              <div class="col-md-12">
                <p class="card-subtitle mb-2 mt-2 text-center h6">크루장: ${data.battle_member_name}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>
</c:if>
  <!-- 참여 크루 card 생성 종료 -->
  </div>
  </div>
  </div>
  </div><!-- 참여크루 코드 종료 -->

  </div>
  </div>
  </div>
  </div>  <!-- 크루전 정보 코드 종료 -->

  </div>

  </body>
  </html>
