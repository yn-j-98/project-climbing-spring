<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="wf-publicsans-n7-active wf-publicsans-n5-active wf-publicsans-n6-active wf-publicsans-n3-active wf-publicsans-n4-active sidebar-color wf-fontawesome5solid-n4-active wf-fontawesome5regular-n4-active wf-fontawesome5brands-n4-active wf-simplelineicons-n4-active wf-active nav_open">
<head>
    <meta charset="UTF-8">
    <title>암벽장 관리</title>
    <!--   Core JS Files   -->
    <script src="assets/js/core/jquery-3.7.1.min.js"></script>

    <!-- Bootstrap JavaScript with Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="assets/css/kaiadmin.css"/>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
    <!--페이지네이션 외부 스크립트-->
    <script src="js/pagination.js"></script>

    <!-- sweetAlert JS FILE -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
    <script src="js/sweetAlert_modal.js"></script>

    <!-- Fonts and icons -->
    <script src="assets/js/plugin/webfont/webfont.min.js"></script>
    <script src="https://kit.fontawesome.com/7f7b0ec58f.js" crossorigin="anonymous"></script>

    <!-- CSS Files -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/kaiadmin.css"/>


    <!-- template Js File -->
    <script src="assets/js/kaiadmin.min.js"></script>

    <!--다음 주소 API-->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

    <style>
        .preview-image {
            width: 100%;
            height: 200px;
            background-color: #e0e0e0;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #ccc;
            border-radius: 5px;

        }

        .preview-image img {
            width: 100%;
            height: 100%;
            object-fit: contain;
        }

        .modal .modal-header {
            background-color: #007bff;
            color: white;
        }

        .modal .modal-body {
            padding: 2rem;
        }

        .modal .btn-close {
            color: white;
        }

        .main-panel {
            margin-left: 290px;
            padding: 20px;
        }
    </style>
</head>
<body>
<mytag:admin_gnb member_id="${MEMBER_ID}"></mytag:admin_gnb>
<div class="main-panel flex-grow-1 p-4">
    <div class="nav-toggle position-absolute top-0 start-0">
        <button class="sidenav-toggler btn btn-icon btn-round btn-white h-100 w-25">
            <i class="fa fa-align-left"></i>
        </button>
    </div>
    <h1 class="text-center">암벽장 관리</h1>
    <div class="container">
        <main class="container-fluid">
            <div class="row mb-5">
                <div class="col">
                    <!-- 모달 버튼 -->
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                            data-bs-target="#climbingGymModal">
                        암벽장 추가
                    </button>
                </div>
            </div>
            <div class="row p-3">
                <div class="col-md-3 ">
                    <div class="form-floating">
                        <%--TODO 컨디션 안쓴다면 ID 변경--%>
                        <select class="form-select" id="search_keyword">
                            <option  ${search_keyword == "ALL" ? "selected":''} value="ALL">전체</option>
                            <option ${search_keyword == "adminCertified" ? "selected":''} value="adminCertified">등록</option>
                            <option ${search_keyword == "adminUnCertified" ? "selected":''} value="adminUnCertified">비등록</option>
                        </select>
                        <label for="search_keyword">검색할 목록</label>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="form-floating">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" aria-label="암벽장 이름 입력" placeholder="암벽장 이름 입력"
                                   aria-describedby="search" id="search_content">
                            <%--TODO 키워드 안쓴다면 ID 변경--%>
                            <button class="btn btn-outline-secondary" type="button" id="search">검색
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- forEach 시작 TODO 삭제-->
            <div class="row" id="gymList">
                <c:forEach var="gym" items="${datas}">
                    <div class="col-md-4 hoverDiv" data-gym-battle-status="${gym.gym_admin_battle_verified}"
                         data-gym-num="${gym.gym_num}"><!--TODO 상태 바꾸기-->
                        <div class="card h-90">
                            <div class="row g-0 d-flex flex-column">
                                <div class="col mt-2">
                                    <img src="${gym.gym_profile}" class="card-img figure-img img-fluid rounded"
                                         alt="${gym.gym_name} 사진">
                                </div>
                                <div class="col">
                                    <div class="card-body">
                                        <h5 class="card-title border-bottom border-dark-subtle">${gym.gym_name}</h5>
                                        <div class="mt-3">
                                            <p class="card-text">
                                                <strong>암벽장 위치:</strong> ${gym.gym_location}
                                            </p>
                                            <p class="card-text">
                                                <strong>암벽장 가격:</strong> ${gym.gym_price}
                                            </p>
                                            <p class="card-text">
                                                <strong>암벽장 소개:</strong> ${gym.gym_description}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- forEach 종료 -->
            <!-- 페이지네이션 -->
            <div id="pageNation" class="row justify-content-center">
                <div class="row pt-5">
                    <div class="col-md-12 d-flex justify-content-center ">
                        <nav aria-label="Page navigation">
                            <input type="hidden" id="totalCount" value="${total}">
                            <input type="hidden" id="currentPage" value="${page}">
                            <ul id="pagination" class="pagination justify-content-center align-items-center"></ul>
                        </nav>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<!-- 암벽장 추가 모달-->
<!--모달창 전송 멀티파트타입 인코딩 설정-->
<form id="insertForm" action="gymInsert.do" method="POST" enctype="multipart/form-data" onsubmit="combineAddress()">
    <div class="modal fade" id="climbingGymModal" tabindex="-1" aria-labelledby="gym-modal-title" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content rounded-4">
                <div class="modal-header d-flex justify-content-center align-items-center rounded-top-4">
                    <p class="h2 modal-title ms-auto">암벽장 추가</p>
                    <button type="button" class="btn btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body flex-fill bg-light">
                    <div class="row text-center d-flex align-items-center justify-content-center">

                        <!-- 암벽장 이름 -->
                        <div class="row mb-2 align-items-center">
                            <div class="col">
                                <div class="form-floating mb-3">
                                    <input type="text" name="gym_name" class="form-control" id="gymName"
                                           placeholder="암벽장 이름 입력" required>
                                    <label for="gymName">암벽장 이름</label>
                                </div>
                            </div>
                        </div>

                        <!-- 암벽장 주소 -->
                        <div class="row mb-2 align-items-center">
                            <div class="col-sm-3">
                                <label for="gym_location" class="form-label">암벽장 주소</label>
                            </div>
                            <div class="col-sm-9">
                                <input type="text" id="postcode" placeholder="우편번호">
                                <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
                                <input type="text" id="roadAddress" placeholder="도로명주소">
                                <input type="text" id="jibunAddress" placeholder="지번주소">
                                <span id="guide" style="color:#999;display:none"></span>
                                <input type="text" id="detailAddress" placeholder="상세주소">
                                <input type="text" id="extraAddress" placeholder="참고항목">
                                <input type="hidden" id="gym_location" name="gym_location">
                            </div>
                        </div>

                        <!-- 암벽장 가격 -->
                        <div class="row mb-2 align-items-center">
                            <label for="gymPrice" class="form-label col-sm-3 m-0">암벽장 가격</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="gymPrice" name="gym_price"
                                       placeholder="암벽장 가격 입력" required>
                            </div>
                        </div>

                        <!-- 암벽장 소개 -->
                        <div class="row mb-2 align-items-center">
                            <label for="gymDescription" class="form-label col-sm-3">암벽장 소개</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" id="gymDescription" name="gym_description" rows="3"
                                          placeholder="암벽장 소개 입력"></textarea>
                            </div>
                        </div>

                        <!-- 암벽장 사진 -->
                        <div class="row mb-2 align-items-center">
                            <label class="col-sm-3 col-form-label">암벽장 사진</label>
                            <div class="col-sm-9">
                                <div class="preview-image" id="previewImage">
                                    <span id="previewSpan">사진 미리보기</span>
                                    <img class="d-none" id="previewImg"/>
                                </div>
                                <input type="file" class="form-control mt-2" id="gymImg" name="gym_file" accept="image/*">
                                <span class="form-text">파일입력장소</span>
                            </div>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" id="modalCancel" class="btn btn-danger" data-bs-dismiss="modal">취소
                        </button>
                        <button type="submit" class="btn btn-primary" id="insertModal">등록</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

<script>
    $(document).ready(function () {
        //hover 옵션
        // .hoverDiv 요소에 마우스를 올리면 확대 효과 적용
        $(".hoverDiv").hover(
            function () {
                $(this).css({"transform": "scale(1.1)", "transition": "transform 0.3s"});
            },
            function () {
                $(this).css({"transform": "scale(1)", "transition": "transform 0.3s"});
            }
        );

        // .hoverDiv 요소 클릭 시 이벤트 처리
        $(".hoverDiv").each(function () {
            $(this).click(function () {
                handleHoverDivClick($(this));
            });
        });

        // 모달창 암벽장 등록 로그
        // #insertModal 요소 클릭 시 콘솔에 모달 입력값 로그 기록
        $("#insertModal").click(function () {
            logModalInputs();
        });

        // #gymImg 요소에 change 이벤트 리스너 추가
        // 파일 선택 시 파일을 읽어와서 이미지 미리보기 설정
        $("#gymImg").on("change", handleFileChange);

        // 모달 닫힐 때 초기화
        // #climbingGymModal 요소가 닫힐 때 모달 내용 초기화
        $("#climbingGymModal").on('hide.bs.modal', resetModal);


        // 검색 버튼 클릭 시 동기 요청 처리
        // #search 요소 클릭 시 검색 함수 호출
        $('#search').click(searchGyms);
    });

    // .hoverDiv 요소 클릭 시 호출되는 함수
    function handleHoverDivClick(element) {
        var gymNum = element.data("gym-num"); // gym 번호 가져오기
        var gymBattleStatus = element.data("gym-battle-status"); // gym 크루전 상태 가져오기
        console.log("gymNum = [" + gymNum + "]");
        console.log("gymBattleStatus = [" + gymBattleStatus + "]");
        // 크루전 상태에 따라 알림 창 띄우기 및 승인 여부 확인
        if (gymBattleStatus !== undefined && gymBattleStatus !== "") {
            if (gymBattleStatus === "F") {
                sweetAlert_confirm_info('크루전 개최를 승인하지 않은 암벽장입니다', '크루전을 승인하시겠습니까?', '승인', '취소')
                    .then(function (battleInsert) {
                        if (battleInsert) {
                            var form = $('<form/>', {
                                //TODO
                                action: 'gymManagementReq.do',
                                method: 'POST',
                                style: 'display: none;'
                            });
                            form.append($('<input/>', {type: 'hidden', name: 'gym_num', value: gymNum}));
                            form.append($('<input/>', {type: 'hidden', name: 'gym_admin_battle_verified', value: gymBattleStatus}));
                            $('body').append(form);
                            form.submit();
                        }
                    });
            } else {
                sweetAlert_info('이미 크루전 개최를 승인한 암벽장입니다', ' ');
            }
        }
    }

    // 모달 입력값 콘솔 출력 함수
    function logModalInputs() {
        console.log("gymName = [" + $("#gymName").val() + "]");
        console.log("gymLocation = [" + $("#gymLocation").val() + "]");
        console.log("gymPrice = [" + $("#gymPrice").val() + "]");
        console.log("gymDescription = [" + $("#gymDescription").val() + "]");
        console.log("gymImg = [" + $("#gymImg").val() + "]");
    }

    // 파일 선택 시 미리보기 이미지 설정 함수
    function handleFileChange(event) {
        var file = event.target.files[0];
        var reader = new FileReader();

        reader.onload = function (e) {
            $("#previewImg").removeClass("d-none").attr("src", e.target.result);
            $("#previewSpan").addClass("d-none");
        }

        reader.readAsDataURL(file);
    }

    // 모달 닫힐 때 모달 내용 초기화 함수
    function resetModal() {
        $("#gymImg").val('');
        $("#previewSpan").removeClass("d-none");
        $("#previewImg").addClass("d-none").removeAttr("src");
    }

    // 검색 함수
    function searchGyms() {
        var search_content = $('#search_content').val(); // 검색어 필드 값 가져오기
        var search_keyword = $('#search_keyword').val(); // 검색어 필드 값 가져오기
        // window.location.href = `controller.do?search_keyword=`+search_keyword+`search_content=`+search_content;
        var form = $('<form/>', {
            //TODO .do 입력
            action: 'gymManagement.do',
            method: 'GET',
            style: 'display: none;'
        });
        form.append($('<input/>', {type: 'hidden', name: 'search_keyword', value: search_keyword}));
        form.append($('<input/>', {type: 'hidden', name: 'search_content', value: search_content}));
        // 문서에 form 추가
        $('body').append(form);
        form.submit();
    }

    // 주소 API
    function combineAddress() {
        var postcode = document.getElementById('postcode').value;
        var roadAddress = document.getElementById('roadAddress').value;
        var jibunAddress = document.getElementById('jibunAddress').value;
        var detailAddress = document.getElementById('detailAddress').value;
        var extraAddress = document.getElementById('extraAddress').value;

        var combinedAddress = postcode+roadAddress+jibunAddress+detailAddress+extraAddress.trim();

        document.getElementById('gym_location').value = combinedAddress;
    }
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postcode').value = data.zonecode;
                document.getElementById("roadAddress").value = roadAddr;
                document.getElementById("jibunAddress").value = data.jibunAddress;

                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>