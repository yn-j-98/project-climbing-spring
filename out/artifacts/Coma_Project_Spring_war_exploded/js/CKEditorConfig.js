//CDN 인포트한 내용중
//내용 사용할 메서드 설정
import {
	AccessibilityHelp,     // 접근성 관련 도움 기능 (예: 키보드 단축키 안내)
	Autoformat,            // 텍스트 자동으로 포맷팅해주는 기능 (굵게, 기울임 등 자동 처리)
	AutoImage,             // URL 입력하면 자동으로 이미지 삽입해주는 기능
	Autosave,              // 작성 중인 내용 자동 저장해주는 기능
	BlockQuote,            // 인용구 블록을 추가하는 기능
	Bold,                  // 텍스트 굵게 만드는 기능
	CloudServices,         // 클라우드 서비스 관련 기능 (파일 업로드 등)
	Essentials,            // 편집기에 꼭 필요한 기본 기능들 제공 (undo/redo 같은 것들)
	Heading,               // 제목(heading) 스타일을 추가하고 관리하는 기능
	ImageBlock,            // 블록형 이미지 삽입 기능
	ImageInline,           // 인라인 이미지 삽입 기능
	ImageInsert,           // 이미지 삽입 기능
	ImageInsertViaUrl,     // URL로 이미지 삽입하는 기능
	ImageResize,           // 이미지 크기 조절 기능
	ImageStyle,            // 이미지 스타일 설정 (정렬, 크기 등)
	ImageTextAlternative,  // 이미지 대체 텍스트(alt text) 추가 기능
	ImageToolbar,          // 이미지 선택하면 관련 도구를 제공하는 툴바
	ImageUpload,           // 이미지 업로드 기능
	Indent,                // 들여쓰기, 내어쓰기 기능
	IndentBlock,           // 블록 단위로 들여쓰기 기능
	Italic,                // 텍스트를 기울임체로 만드는 기능
	Link,                  // 텍스트에 하이퍼링크 추가 기능
	Paragraph,             // 기본 단락 요소 관리 기능
	SelectAll,             // 모든 내용 선택 기능
	SimpleUploadAdapter,   // 간단한 이미지 업로드 어댑터 (서버로 이미지 업로드할 때 사용)
	TextTransformation,    // 텍스트 자동 변환 기능 (대소문자 변환 등)
	Underline,             // 텍스트 밑줄 긋는 기능
	Undo,                  // 실행 취소/다시 실행 기능
} from 'CKEditor';

//한국어 설정 인포트
import translations from 'CKEditor/translations/ko.js';

export const editorConfig = {
  //인포트한값들중 메뉴에 보여줄 설정
  toolbar: {
	//메뉴바에 보여줄 메뉴를 설정해줍니다.
	//'|' <-- 해당 문자는 단지 메뉴를 나눠주기 위해 작성한 부분입니다.
    items: [
      'undo', //ctrl + z 뒤로가기
      'redo', //ctrl + Y 
      '|',
      'heading', //문단 설정
      '|',
      'bold', //글자 강조
      'italic', //글자 기울기
      'underline', //밑줄
      '|',
      'link', //주소 등록
      'insertImage', //이미지 등록
      'blockQuote', // 블록 형 글자
      '|',
      'outdent', //글자 정렬
      'indent', //들여쓰기
    ],
    shouldNotGroupWhenFull: false, // 도구 모음과 내용 작성 div OR TextArea 그룹화 설정
  },
  //사용 플러그인 등록
  plugins: [
	AccessibilityHelp,     // 접근성 관련 도움 기능 (예: 키보드 단축키 안내)
	Autoformat,            // 텍스트 자동으로 포맷팅해주는 기능 (굵게, 기울임 등 자동 처리)
	AutoImage,             // URL 입력하면 자동으로 이미지 삽입해주는 기능
	Autosave,              // 작성 중인 내용 자동 저장해주는 기능
	BlockQuote,            // 인용구 블록을 추가하는 기능
	Bold,                  // 텍스트 굵게 만드는 기능
	CloudServices,         // 클라우드 서비스 관련 기능 (파일 업로드 등)
	Essentials,            // 편집기에 꼭 필요한 기본 기능들 제공 (undo/redo 같은 것들)
	Heading,               // 제목(heading) 스타일을 추가하고 관리하는 기능
	ImageBlock,            // 블록형 이미지 삽입 기능
	ImageInline,           // 인라인 이미지 삽입 기능
	ImageInsert,           // 이미지 삽입 기능
	ImageInsertViaUrl,     // URL로 이미지 삽입하는 기능
	ImageResize,           // 이미지 크기 조절 기능
	ImageStyle,            // 이미지 스타일 설정 (정렬, 크기 등)
	ImageTextAlternative,  // 이미지 대체 텍스트(alt text) 추가 기능
	ImageToolbar,          // 이미지 선택하면 관련 도구를 제공하는 툴바
	ImageUpload,           // 이미지 업로드 기능
	Indent,                // 들여쓰기, 내어쓰기 기능
	IndentBlock,           // 블록 단위로 들여쓰기 기능
	Italic,                // 텍스트를 기울임체로 만드는 기능
	Link,                  // 텍스트에 하이퍼링크 추가 기능
	Paragraph,             // 기본 단락 요소 관리 기능
	SelectAll,             // 모든 내용 선택 기능
	SimpleUploadAdapter,   // 간단한 이미지 업로드 어댑터 (서버로 이미지 업로드할 때 사용)
	TextTransformation,    // 텍스트 자동 변환 기능 (대소문자 변환 등)
	Underline,             // 텍스트 밑줄 긋는 기능
	Undo,                  // 실행 취소/다시 실행 기능
  ],
  //문단을 선택할 수 있게 설정
  heading: {
	options: [
	  {
	    model: 'paragraph',  // 일반 단락 (Paragraph)
	    title: 'Paragraph',
	    class: 'ck-heading_paragraph',
	  },
	  {
	    model: 'heading1',   // 제목 1 (Heading 1)
	    view: 'h1',
	    title: 'Heading 1',
	    class: 'ck-heading_heading1',
	  },
	  {
	    model: 'heading2',   // 제목 2 (Heading 2)
	    view: 'h2',
	    title: 'Heading 2',
	    class: 'ck-heading_heading2',
	  },
	  {
	    model: 'heading3',   // 제목 3 (Heading 3)
	    view: 'h3',
	    title: 'Heading 3',
	    class: 'ck-heading_heading3',
	  },
	  {
	    model: 'heading4',   // 제목 4 (Heading 4)
	    view: 'h4',
	    title: 'Heading 4',
	    class: 'ck-heading_heading4',
	  },
	  {
	    model: 'heading5',   // 제목 5 (Heading 5)
	    view: 'h5',
	    title: 'Heading 5',
	    class: 'ck-heading_heading5',
	  },
	  {
	    model: 'heading6',   // 제목 6 (Heading 6)
	    view: 'h6',
	    title: 'Heading 6',
	    class: 'ck-heading_heading6',
	  },
	],
  },
  //이미지 수정 설정
  image: {
	resizeOptions: [
	  {
	    name: 'resizeImage:original',   // 원본 크기로 이미지 리사이즈
	    value: null,
	    icon: 'original'
	  },
	  {
	    name: 'resizeImage:custom',     // 사용자 정의 크기로 리사이즈
	    value: 'custom',
	    icon: 'custom'
	  },
	  {
	    name: 'resizeImage:50',         // 이미지 크기 50%로 리사이즈
	    value: '50',
	    icon: 'medium'
	  },
	  {
	    name: 'resizeImage:75',         // 이미지 크기 75%로 리사이즈
	    value: '75',
	    icon: 'large'
	  }
	],
	toolbar: [
	  'imageTextAlternative',            // 이미지에 대체 텍스트(alt text) 추가
	  '|',
	  'imageStyle:inline',               // 이미지 인라인 스타일
	  'imageStyle:wrapText',             // 텍스트와 이미지 감싸기 스타일
	  'imageStyle:breakText',            // 텍스트와 이미지 분리 스타일
	  '|',    
	  'resizeImage:50',                  // 툴바에 이미지 50% 리사이즈 버튼 추가
	  'resizeImage:75'                   // 툴바에 이미지 75% 리사이즈 버튼 추가
	],
  },
  // 파일 업로드 설정
  simpleUpload: {
	//파일을 저장할 서버의 주소를 입력
    uploadUrl: './ckupload',
  },
  //초기 값 설정
  initialData: '',
  //언어 설정
  language: 'ko',
  //링크 설정
  link: {
    addTargetToExternalLinks: true,
    defaultProtocol: 'https://',
    decorators: {
      toggleDownloadable: {
        mode: 'manual',
        label: 'Downloadable',
        attributes: {
          download: 'file',
        },
      },
    },
  },
  //처음 사용자 UI/UX 개선을 위한 내용
  placeholder: '내용은 255자를 넘을 수 없습니다. \n 이미지 제한은 2장입니다.',
  //사용자 언어 설정
  translations: [translations],
};


export function setContentsLength(str, imgMax, textMax) {
    var status = true;
    var results = str.match(/<img/g);
    var imgCnt = setImgLength(str);   // 이미지 개수 체크
    var textCnt = setTextLength(str); // 텍스트 길이 체크
    
    // 이미지가 있을 경우
    if(results != null) {
        $('.img-length').text(imgCnt + "장");
        if(imgCnt > imgMax) {
			// 이미지 개수가 최대 개수를 넘으면 상태를 false로
            status = false;
        }
    }
    
	//console.log("if(textCnt > textMax) { 1: " + textCnt);
    
    // 텍스트 길이가 최대 길이를 넘으면 상태를 false로
    if(textCnt > textMax) {
		status = false;
    }
    
    // 상태가 false일 경우, 알림 메시지 띄우기
    if(!status) {
        var msg = "등록오류\n글자수는 최대 " + textMax + "까지 등록이 가능합니다.\n현재 글자수 : " + textCnt + "자";
        
        // 특정 조건일 때 메시지 다르게 표시
        if(obj.name == "ideaDetail3") {
            msg = "등록오류\n글자수는 최대 " + textMax + "자, 이미지는 " + imgMax + "장까지만 사용이 가능합니다.\n총 글자수 : " + textCnt + "자, 총 이미지수 : " + imgCnt + "장";
        }
		alert(msg);
    }
    
    // 상태 리턴해서 폼 제출할 때 오류 여부 체크
    return status;
}

export function setTextLength(str) {
	var textCnt = 0;
    var editorText = f_SkipTags_html(str);   // HTML 태그 제거
    
    // 공백 제거
    editorText = editorText.replace(/\s/gi, "");
    editorText = editorText.replace(/&nbsp;/gi, "");
    
	//console.log("if(textCnt > textMax) { 1: " + editorText.length);
    
    // 텍스트 길이 측정
	textCnt = editorText.length;
	$('.text-length').text(textCnt + "자");
    
    return textCnt;   // 텍스트 길이 리턴
}

// 이미지 개수 구하는 함수
export function setImgLength(str) {
    var results = str.match(/<img/g);
    var imgCnt = 0;
    
    // 이미지 태그가 있으면 개수 카운트
    if(results != null) {
        imgCnt = results.length;
        $('.img-length').text(imgCnt + "장");
    }
    
    return imgCnt;   // 이미지 개수 리턴
}

// HTML 태그 제거용 함수
function f_SkipTags_html(input, allowed) {
    // 허용할 태그만 남기고 나머지 태그는 제거
    allowed = (((allowed || "") + "").toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join('');
    var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi,
        commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;
    
    // 주석이나 PHP 태그 제거
    return input.replace(commentsAndPhpTags, '').replace(tags, function ($0, $1) {
        // 허용된 태그만 남기고 제거
        return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
    });
}
