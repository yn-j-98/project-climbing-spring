$(document).ready(function () {
    renderpagination();
})
    // 페이지네이션 생성 함수
    function renderpagination() {
        var url = window.location.pathname;
        console.log("pagenation.js url :"+url)
        var _totalCount = parseInt($("#totalCount").val());  // 서버에서 전달된 전체 게시물 개수
        //NaN / null 유효성 검사
        if (isNaN(_totalCount) || _totalCount === 0 || _totalCount === null) {
            _totalCount = 0;
        }

        var currentPage = parseInt($("#currentPage").val()); // 서버에서 전달된 현재 페이지 번호
        //NaN / null 유효성 검사
        if (isNaN(currentPage) || currentPage === 0 || currentPage === null) {
            currentPage = 1;
        }

        console.log("pagenation.js _totalCount =["+_totalCount+"]");
        console.log("pagenation.js currentPage =["+currentPage+"]");

        $("#pagination").empty();  // 기존 페이지네이션을 지우기

        // 현재 게시물의 전체 개수가 10개 이하이면 pagination을 숨깁니다.
        if (_totalCount <= 10) return;

        // 총 페이지 수 계산 (전체 게시물 수를 한 페이지에 보여줄 게시물 수로 나눈 값의 올림)
        const totalPage = Math.ceil(_totalCount / 10);
        console.log("pagenation.js totalPage =["+totalPage+"]");

        // 현재 페이지 그룹 계산 (현재 페이지를 10으로 나눈 값의 올림)
        const pageGroup = Math.ceil(currentPage / 10);
        console.log("pagenation.js pageGroup =["+pageGroup+"]");

        // 현재 페이지 그룹에서의 마지막 페이지 계산
        let last = pageGroup * 10;
        console.log("pagenation.js last =["+last+"]");

        // 마지막 페이지가 총 페이지 수를 초과하지 않도록 조정
        if (last > totalPage) last = totalPage;

        // 현재 페이지 그룹에서의 첫 번째 페이지 계산
        const first = last - (10 - 1) <= 0 ? 1 : last - (10 - 1);
        console.log("pagenation.js first =["+first+"]");

        // 다음 그룹의 첫 페이지 계산
        const next = last + 1;
        console.log("pagenation.js next =["+next+"]");

        // 이전 그룹의 마지막 페이지 계산
        const prev = first - 1;
        console.log("pagenation.js prev =["+prev+"]");

        // 페이지네이션 버튼을 담을 비어있는 DocumentFragment 객체 생성
        const fragmentPage = document.createDocumentFragment();

        // 이전 그룹으로 이동하는 버튼 생성 (prev가 0보다 크다면 생성)
        if (prev > 0) {
            const preli = document.createElement('li');
            preli.className = 'page-item';

            // 쿼리 파라미터 없이 링크 생성
            preli.insertAdjacentHTML("beforeend",
                "<a class='page-link' href='"+url+"?page="+prev+"' aria-label='Previous' data-page='" + prev + "'>" +
                "<span aria-hidden='true'>&laquo;</span>" +
                "</a>"
            );

            fragmentPage.appendChild(preli); // fragment에 추가
        }

        // 현재 페이지 그룹의 페이지 번호 버튼 생성
        for (let i = first; i <= last; i++) {
            const li = document.createElement("li");
            li.className = 'page-item';

            let linkHTML = "<a class='page-link m-2' href='"+url+"?page="+i+"' data-page='" + i + "'>" + i + "</a>";
            if (i === currentPage) {
                linkHTML = "<a class='page-link m-2 active' href='"+url+"?page="+i+"' data-page='" + i + "'>" + i + "</a>";
            }

            li.insertAdjacentHTML("beforeend", linkHTML);
            fragmentPage.appendChild(li); // fragment에 추가
        }

        // 다음 그룹으로 이동하는 버튼 생성 (last가 totalPage보다 작다면 생성)
        if (last < totalPage) {
            const endli = document.createElement('li');
            endli.className = 'page-item';

            // 쿼리 파라미터 없이 링크 생성
            endli.insertAdjacentHTML("beforeend",
                "<a class='page-link' href='"+url+"?page="+next+"' aria-label='Next' data-page='" + next + "'>" +
                "<span aria-hidden='true'>&raquo;</span>" +
                "</a>"
            );

            fragmentPage.appendChild(endli); // fragment에 추가
        }

        // 생성된 페이지네이션 버튼들을 화면에 추가
        document.getElementById('pagination').appendChild(fragmentPage);
    }

// DOM이 완전히 로드된 후 페이지네이션을 생성
//     renderpagination(currentPage, _totalCount);

// 페이지 버튼 클릭 이벤트 처리
    $("#pagination").on("click", "a", function (e) {
        // 기본 동작(페이지 이동) 방지
        e.preventDefault();
        // 클릭된 페이지 링크 요소를 jQuery 객체로 저장
        const $item = $(this);
        // 클릭된 링크의 'data-page' 값(페이지 번호)을 가져옴
        const selectedPage = parseInt($item.attr("data-page"));

        // 페이지네이션 재생성 및 해당 페이지 데이터 로드
        if (selectedPage && selectedPage !== currentPage) {
            currentPage = selectedPage;
            renderpagination(currentPage, _totalCount);
            // 페이지 변경 시 필요한 추가 데이터 로드 로직이 있다면 여기에 추가
        }
    });