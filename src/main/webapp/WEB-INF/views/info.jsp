<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>안내 페이지</title>
    <!--   Core JS Files   -->
    <script src="../../assets/js/core/jquery-3.7.1.min.js"></script>
    <!-- sweetAlert JS FILE -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
    <script src="js/sweetAlert_modal.js"></script>
</head>
<body>
<input type="hidden" id="title" value="${title}"/>
<input type="hidden" id="msg" value="${msg}"/>
<input type="hidden" id="path" value="${path}"/>
<script>
    $(document).ready(function(){
        let title = $("#title").val();
        console.log(title);
        let msg = $("#msg").val();
        console.log(msg);
        let path = $("#path").val();
        console.log(path);
        if(title == ""){
            console.log(msg+'||'+path);
            <%--alert('${msg}');--%>
            sweetAlert_error(title,msg);
        }
        else{
            sweetAlert_success(title,msg);
        }
        location.href=path;
    })
</script>

</body>
</html>