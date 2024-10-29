$(document).ready(function () {
    renderpagination();

    // 드롭다운 선택 이벤트 처리
    $('.dropdown-item').click(function(event) {
        event.preventDefault();
        var selectedText = $(this).data('value');
        $('#dropdownButton').text(selectedText);
        // 드롭다운 값 변경 시 URL에 반영하고 페이지네이션 업데이트
        console.log("pagenation.js Dropdown = [" + selectedText + "]");
        updateURLParam('search_keyword', selectedText);
        renderpagination();
    });

    // 검색어 입력 필드 변경 이벤트 처리
    $('#search_keyword').change(function() {
        var search_keyword = $("#search_keyword").val();
        var search_content = $("#search_content").val();
        console.log("19 pagenation.js search_keyword [" + search_keyword + "]");
        console.log("20 pagenation.js search_content [" + search_content + "]");
        updateURLParam('search_keyword', search_keyword);
        updateURLParam('search_content', search_content);
        renderpagination();
    });
});

// URL에 파라미터 추가/수정 함수
function updateURLParam(key, value) {
    var url = new URL(window.location);
    url.searchParams.set(key, value);
    history.replaceState(null, '', url.href);
    console.log("pagenation.js 업데이트 URL = [" + url.href + "]");
}

// 페이지네이션 생성 함수
function renderpagination() {
    var url = window.location.pathname;
    console.log("pagenation.js url = [" + url + "]");

    var _totalCount = parseInt($("#totalCount").val());
    if (isNaN(_totalCount) || _totalCount === 0 || _totalCount === null) {
        _totalCount = 0;
    }
    console.log("pagenation.js _totalCount = [" + _totalCount + "]");

    var currentPage = parseInt($("#currentPage").val());
    if (isNaN(currentPage) || currentPage === 0 || currentPage === null) {
        currentPage = 1;
    }
    console.log("pagenation.js currentPage = [" + currentPage + "]");

    var search_keyword = new URLSearchParams(window.location.search).get('search_keyword') || '';
    var search_content = new URLSearchParams(window.location.search).get('search_content') || '';
    console.log("53pagenation.js search_Keyword = [" + search_keyword + "]");
    console.log("54pagenation.js search_Contents = [" + search_content + "]");

    $("#pagination").empty();
    if (_totalCount <= 10) return;

    const totalPage = Math.ceil(_totalCount / 10);
    console.log("pagenation.js totalPage = [" + totalPage + "]");

    const pageGroup = Math.ceil(currentPage / 10);
    console.log("pagenation.js pageGroup = [" + pageGroup + "]");

    let last = pageGroup * 10;
    if (last > totalPage) last = totalPage;
    console.log("pagenation.js last = [" + last + "]");

    const first = last - (10 - 1) <= 0 ? 1 : last - (10 - 1);
    console.log("pagenation.js first = [" + first + "]");

    const next = last + 1;
    console.log("pagenation.js next = [" + next + "]");

    const prev = first - 1;
    console.log("pagenation.js prev = [" + prev + "]");

    const fragmentPage = document.createDocumentFragment();

    if (prev > 0) {
        const preli = document.createElement('li');
        preli.className = 'page-item';
        preli.insertAdjacentHTML("beforeend",
            `<a class='page-link' href='${appendParam('page', prev)}' aria-label='Previous' data-page='${prev}'>` +
            "<span aria-hidden='true'>&laquo;</span>" +
            "</a>"
        );
        fragmentPage.appendChild(preli);
    }

    for (let i = first; i <= last; i++) {
        const li = document.createElement("li");
        li.className = 'page-item';
        let linkHTML = `<a class='page-link m-2' href='${appendParam('page', i)}' data-page='${i}'>${i}</a>`;
        if (i === currentPage) {
            linkHTML = `<a class='page-link m-2 active' href='${appendParam('page', i)}' data-page='${i}'>${i}</a>`;
        }
        li.insertAdjacentHTML("beforeend", linkHTML);
        fragmentPage.appendChild(li);
    }

    if (last < totalPage) {
        const endli = document.createElement('li');
        endli.className = 'page-item';
        endli.insertAdjacentHTML("beforeend",
            `<a class='page-link' href='${appendParam('page', next)}' aria-label='Next' data-page='${next}'>` +
            "<span aria-hidden='true'>&raquo;</span>" +
            "</a>"
        );
        fragmentPage.appendChild(endli);
        console.log("Next button added: [" + next + "]");
    }

    console.log("페이지네이션 생성 완료");
    document.getElementById('pagination').appendChild(fragmentPage);
}

// 다른 파라미터가 삭제되지 않도록 URL에 페이지 번호 추가
function appendParam(key, value) {
    var url = new URL(window.location);
    url.searchParams.set(key, value);
    return url.href;
}

// 페이지 버튼 클릭 이벤트 처리
$("#pagination").on("click", "a", function (e) {
    e.preventDefault();
    const $item = $(this);
    const selectedPage = parseInt($item.attr("data-page"));

    if (selectedPage && selectedPage !== currentPage) {
        console.log("pagenation.js Page selected = [" + selectedPage + "]");
        currentPage = selectedPage;
        renderpagination();
    } else {
        console.log("pagenation.js Page 없음 [" + selectedPage + "]");
    }
});