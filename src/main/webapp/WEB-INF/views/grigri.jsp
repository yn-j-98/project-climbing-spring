<%--
  Created by IntelliJ IDEA.
  User: seonah
  Date: 24. 10. 31.
  Time: 오후 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<html>
<head>
    <title>프로젝트 GRIGRI를 소개합니다.</title>
    <!-- Fonts and icons -->
    <script src="assets/js/plugin/webfont/webfont.min.js"></script>
    <script src="https://kit.fontawesome.com/7f7b0ec58f.js"
            crossorigin="anonymous"></script>

    <!-- CSS Files -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/plugins.min.css" />
    <link rel="stylesheet" href="assets/css/kaiadmin.css" />
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />

    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.6.0/gsap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.6.0/ScrollTrigger.min.js"></script>
</head>
<style>
    body{
        z-index: -1;
    }
    .main-header{
        top:0;
    }
    .main-content {
        width:100%;
        height: 100vh;
        position: relative;
        background-image: url("/img/grigri/main_background.jpg");
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center;
        background-attachment: fixed;
    }
    .icon1{
        width: 100px;
        height: 100px;
        position: absolute;
        bottom: 30%;
        left:20%;
    }
    .icon2{
        width: 100px;
        height: 100px;
        position: absolute;
        bottom: 40%;
        right:20%;
    }
   .main_title{
        position: absolute;
        top: 40%;
        left: 50%;
        transform: translatex(-50%);
        color: black;
        background-color: #ffe332;
        padding: 20px;
    }
    .main_title h1{
        text-align: center;
        padding: 0;
    }
    .main-content .main_crew{
        position: absolute;
        width: 500px;
        bottom: 0;
        left: 50%;
        transform: translatex(-50%);
    }
    .main-content .main_crew img{
        width: 100%;
        height: 100%;
        object-fit: contain;
    }

    .intro_content{
        width: 100%;
        height: 100vh;
        position: relative;
        background-image: url("/img/grigri/intro-background.jpg");
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center;
        background-attachment: fixed;
        z-index: -1;
    }
    .intro_content .intro_logo{
        position: absolute;
        top:10%;
        left: 50%;
        transform: translatex(-50%);
    }
    .intro_content .intro_grigri{
        position: absolute;
        top: 20%;
        left: 50%;
        transform: translatex(-50%);
        text-align: center;
    }
    .intro_content .intro_grigri img{
        width: 50%;
    }
    .intro_content .intro_crew {
        position: absolute;
        width: 500px;
        top: 25%;
        left: 50%;
        transform: translatex(-50%);
    }
    .intro_content .intro_crew h1{
        color: black;
        padding: 20px;
        font-size: 60px;
        text-align: center;
    }
    .intro_content .intro_crew img{
        width: 100px;
        object-fit: contain;
    }
    .intro_content .intro_text{
        width:100%;
        position: absolute;
        bottom: 20%;
        left: 50%;
        transform: translatex(-50%);
        color: black;
    }
    .intro_content .intro_text h3{
        text-align: center;
        padding: 20px;
        margin:0;
        font-size: 40px;
    }
    .intro_content .intro_text p{
        text-align: center;
        padding-bottom: 10px;
        margin:0;
        font-size: 30px;
    }
    .background-box{
        width: 300px;
        height: 190px;
        background-color: #ede1cb;
        position: relative;
    }
    .img-box{
        width: 150px;
        height: 150px;
        position: absolute;
        top: 50%;
        left: -25%;
        transform: translateY(-50%);
        overflow: hidden;
    }
    .img-box img{
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    .text-box{
        width:200px;
        height: 64px;
        position: absolute;
        top: 50%;
        left: 35%;
        transform: translateY(-50%);
    }
    .text-box a:hover{
        width:200px;
        height: 84px;
        background-color: #ffffff;
    }
    .text-box h4{
        color: black;
        font-size: 20px;
        font-weight: bold;
    }
    .text-box p{
        color: black;
        font-size: 15px;
    }
    .grigri2{
        position: absolute;
        top: 30%;
        left:10%;
        width: 100px;
    }
    .carabiner{
        position: absolute;
        bottom: 5%;
        right: 10%;
        width: 200px;
    }
</style>
<body>
    <!-- GNB 커스텀 태그 -->
    <mytag:gnb member_id="${MEMBER_ID}"></mytag:gnb>

    <!--  container start -->
    <div class="main-content">
        <img src="img/grigri/icon1.png" alt="icon1" class="icon1"/>
        <img src="img/grigri/icon2.png" alt="icon2" class="icon2"/>
        <div class="main_title">
            <h1 class="m-0">GRIGRI 프로젝트를 소개합니다</h1>
        </div>
        <div class="main_crew">
            <img src="/img/grigri/main.png" alt="main_crew">
        </div>
    </div>
    <div class="intro_content">
        <div class="intro_logo">
            <img src="/images/logo.png" alt="intro_log"/>
        </div>
        <div class="intro_crew">
            <h1>COMA</h1>
        </div>
        <div class="intro_text">
            <h3> 저희 팀 이름은 ‘코마’로</h3>
            <p> 코드라는 마운틴을 올라 🚩코드 마스터가 되겠다는 목표🎯를 담고 있습니다.</p>
            <p> 주제 또한 🧗🏼클라이밍 크루 커뮤니티와 연관되어 </p>
            <p>코드 마스터🏆이자 코드 마운틴🏔️을 줄여 </p>
            <p>‘코마’라는 이름을 선택했습니다.</p>
        </div>
    </div>
    <div class="intro_content">
        <img src="/img/grigri/grigri2.png" class="grigri2" alt="grigri image"/>
        <img src="/img/grigri/carabiner.png" class="carabiner" alt="grigri image"/>
        <div class="intro_grigri">
            <img src="/img/grigri/grigri.png" alt="grigri image"/>
        </div>
        <div class="intro_text">
            <h3>GRIGRI 프로젝트는</h3>
            <h1 class="text-center">클라이밍 크루 커뮤니티 페이지를 개발했습니다.</h1>
            <p>여기서 크루👀란 공통의 관심사를 가진 사람들의 모임을 의미합니다.</p>
            <p>코마에서 만든 크루 커뮤니티는</p>
            <p>공통된 관심사를 가진 사람들이 다양한 방식으로 💬소통할 수 있도록 만들었습니다.</p>
        </div>
    </div>
    <div class="intro_content">
        <div class="intro_grigri">
            <img src="/img/grigri/crimp_grip.jpg" alt="grigri image"/>
        </div>
        <div class="intro_text">
            <h3>클라이밍이란?</h3>
            <p>암벽등반으로 🧗🏼암벽을 오르거나 높은 곳에 올라가는 🏅스포츠입니다</p>
            <p>저희 조는 주로 실내 클라이밍에 초점을 맞추어서 서비스를 구현했습니다.</p>
        </div>
    </div>
    <div class="intro_content">
        <div class="intro_crew">
            <img src="/img/grigri/c.png" alt="intro_crew"/>
            <img src="/img/grigri/R.png" alt="intro_crew"/>
            <img src="/img/grigri/e.png" alt="intro_crew"/>
            <img src="/img/grigri/W.png" alt="intro_crew"/>
        </div>
        <div class="intro_text">
            <h3> 🏆이 크루 커뮤니티의 메인 이벤트는 “크루전”입니다.</h3>
            <p> 🏅 크루전은 두 개 이상의 크루가 경쟁하는 🎉이벤트로,</p>
            <p> 경쟁보다는 🧗🏻‍♂️크루 간 친목 도모에 중점을 두었습니다. </p>
            <p>또한 포인트를 개인에게 부여하여 크루원의 참여를 🎯활성화시켰습니다.</p>
        </div>
    </div>
    <div class="intro_content">
        <div class="container position-relative">
            <div class="row position-relative">
                <div class="col-4 d-flex justify-content-end">
                    <div class="background-box">
                        <div class="img-box">
                            <img src="/img/grigri/sunho.jpeg" alt="sunho image"/>
                        </div>
                        <div class="text-box">
                            <a href="https://github.com/sunooou" class="w-100 h-100 d-block ">
                                <h4>김선호</h4>
                                <p>성장하는 개발자</p>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-4 d-flex justify-content-end">
                    <div class="background-box">
                        <div class="img-box">
                            <img src="/img/grigri/jongmin.jpeg" alt="jongmin image"/>
                        </div>
                        <div class="text-box">
                            <a href="https://github.com/kimjongmin1234" class="w-100 h-100 d-block ">
                                <h4>김종민</h4>
                                <p>도전적이고 성실한 개발자</p>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-4 d-flex justify-content-end">
                    <div class="background-box">
                        <div class="img-box">
                            <img src="/img/grigri/sangdo.jpeg" alt="sangdo image"/>
                        </div>
                        <div class="text-box">
                            <a href="https://github.com/no1fc" class="w-100 h-100 d-block ">
                                <h4>남상도</h4>
                                <p>우직한 개발자</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col-4 d-flex justify-content-end">
                    <div class="background-box">
                        <div class="img-box">
                            <img src="/img/grigri/seonah.jpeg" alt="seonah image"/>
                        </div>
                        <div class="text-box">
                            <a href="https://github.com/developerSeonah" class="w-100 h-100 d-block ">
                                <h4>박선아</h4>
                                <p>긍정 마인드 개발자</p>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-4 d-flex justify-content-end">
                    <div class="background-box">
                        <div class="img-box">
                            <img src="/img/grigri/junyeol.jpeg" alt="junyeol image"/>
                        </div>
                        <div class="text-box">
                            <a href="/https://github.com/junyeol" class="w-100 h-100 d-block ">
                                <h4>이준열</h4>
                                <p>열정적인 개발자</p>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-4 d-flex justify-content-end">
                    <div class="background-box">
                        <div class="img-box">
                            <img src="/img/grigri/yena.jpeg" alt="yena image"/>
                        </div>
                        <div class="text-box">
                            <a href="https://github.com/yn-j-98" class="w-100 h-100 d-block ">
                                <h4>주예나</h4>
                                <p>협력과 도전을 즐기는 개발자</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script>

</script>
</html>
