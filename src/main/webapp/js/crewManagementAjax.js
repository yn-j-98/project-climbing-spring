$(document).ready(function () {
    //FIXME model 이 숨겨진다면 select 초기화
    modal.on('hide.bs.modal', function () {
        console.log('modal hide Js Start');
        winner_crew_select_result(); // 승리 크루 select 초기화 함수 호출
        mvp_crew_select_result();// mvp 크루 select 초기화 함수 호출
        mvp_select_result();// 크루 select 초기화 함수 호출
        console.log('modal hide Js End');
    });

    // FIXME modal 켜지면 JS 실행
    modal.on('shown.bs.modal', function () {
        console.log(battle_num_text);
        console.log('modal Js Start');
        //FIXME 크루전 참여 크루 받아올 비동기 ajax 시작
        //TODO url 바꿔야함
        $.ajax({
            url: 'crewName.do',
            type: 'POST',
            dataType: 'json',
            data: {battle_num : battle_num_text},
            success: function (datas) {
                console.log('ajax success log Start');
                console.log(JSON.stringify(datas));
                datas.forEach(data => {
                    winner_crew_select.append(crew_name(data));
                    mvp_crew_select.append(crew_name(data));
                });

                // var datas = [{crew_name:'aa'},{crew_name:'bb'},{crew_name:'cc'}];//TODO 임시 데이터
                // datas.forEach(data => {
                //     winner_crew_select.append(mvp_name(data));
                // });


                function crew_name(data) { // select option 생성
                    console.log('function mvp_name log : ['+data.crew_name+']');
                    return '<option value="'+data.crew_name+'">'+data.crew_name+'</option>';
                }
                console.log('ajax success log End');
            },
            error: function (data) {

            }
        });//크루전 참여 크루 받아올 비동기 ajax 종료
        console.log('modal Js End');
    }); // modal JS 종료

//FIXME mvp_crew 확인 JS 실행
    winner_crew_select.on('change',function () {
        //TODO url 바꿔야함
        $.ajax({
            url : 'mvpMember.do',
            type : 'POST',
            dataType : 'json',
            data : JSON.stringify({crew_name:mvp_crew_select.val()}),
            success : function (datas) {
                console.log('ajax success log Start');
                console.log(JSON.stringify(datas));
                // var datas = [{member_name:'dd'},{member_name:'ff'},{member_name:'gg'}];//TODO 임시 데이터
                mvp_select_result();// 크루 select 초기화 함수 호출

                // datas.forEach(data => { // 받아온 데이터로 option 생성
                //     mvp_crew_select.append(mvp_name(data));
                // });
                datas.forEach(data => {
                    mvp_select.append(mvp_name(data));
                })

                function mvp_name(data) {
                    console.log('function mvp_name log : ['+data.member_name+']');
                    return '<option value="'+data.member_name+'">'+data.member_name+'</option>';
                }
                console.log('ajax success log End');
            },error : function (error) {

            }
        })
    });//mvp_crew 확인 JS 종료

// //FIXME mvp 확인 JS 실행
//     mvp_crew_select.on('change',function () {
//         $.ajax({
//             url : '',
//             type : 'POST',
//             dataType : 'json',
//             data : JSON.stringify({'crew_name':mvp_select.val()}),
//             success : function (datas) {
//                 console.log('ajax success log Start');
//                 console.log(JSON.stringify(datas));
//                 // var datas = [{member_name:'dd'},{member_name:'ff'}, {member_name:'gg'}];//TODO 임시 데이터
//
//                 mvp_select_result();// 크루 select 초기화 함수 호출
//                 datas.forEach(data => { // 받아온 데이터로 option 생성
//                     mvp_select.append(mvp_name(data));
//                 });
//
//                 function mvp_name(data) {
//                     console.log('function mvp_name log : ['+data.member_name+']');
//                     return '<option value="'+data.member_name+'">'+data.member_name+'</option>';
//                 }
//                 console.log('ajax success log End');
//             },error : function (error) {
//
//             }
//         })
//     });//MVP 확인 JS 종료



    //FIXME select option reset function start
    function winner_crew_select_result() { // 승리 크루 select 초기화 함수
        winner_crew_select.empty();
        winner_crew_select.append('<option>승리크루</option>');
    }
    function mvp_crew_select_result() { // mvp 크루 select 초기화 함수
        mvp_crew_select.empty();
        mvp_crew_select.append('<option>MVP CREW</option>');
    }
    function mvp_select_result() { // mvp select 초기화 함수
        mvp_select.empty();
        mvp_select.append('<option>MVP</option>');
    }
    //select option reset function end
})



