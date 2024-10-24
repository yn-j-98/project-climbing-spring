-- MEMBER
CREATE TABLE MEMBER
(
    MEMBER_ID                VARCHAR(100) PRIMARY KEY,            -- 사용자 PK
    MEMBER_NAME              VARCHAR(50)  NOT NULL,               -- 사용자 이름
    MEMBER_PASSWORD          VARCHAR(100) NOT NULL,               -- 사용자 비밀번호
    MEMBER_PHONE             VARCHAR(50)  NOT NULL,               -- 사용자 휴대폰 번호
    MEMBER_REGISTRATION_DATE DATE          DEFAULT CURRENT_DATE,  -- 사용자 회원가입 날짜
    MEMBER_PROFILE           VARCHAR(2048) DEFAULT 'default.jpg', -- 사용자 프로필 사진
    MEMBER_CURRENT_POINT     INT           DEFAULT 0,             -- 사용자 사용가능한 포인트
    MEMBER_TOTAL_POINT       INT           DEFAULT 0,             -- 사용자 누적포인트
    MEMBER_CREW_NUM          INT           DEFAULT NULL,          -- 사용자 가입한 크루 FK
    MEMBER_CREW_JOIN_DATE    DATE          DEFAULT NULL,          -- 사용자 크루 가입한 날짜
    MEMBER_LOCATION          VARCHAR(100) NOT NULL,               -- 사용자 지역
    MEMBER_ROLE              CHAR(1)       DEFAULT 'F'            -- 사용자 권한
);

-- GYM
CREATE TABLE GYM
(
    GYM_NUM             INT PRIMARY KEY AUTO_INCREMENT, -- 암벽장 PK
    GYM_NAME            VARCHAR(100)   NOT NULL,        -- 암벽장 이름
    GYM_PROFILE         VARCHAR(2048),                  -- 암벽장 사진 URL
    GYM_DESCRIPTION     TEXT,                           -- 암벽장 설명
    GYM_LOCATION        VARCHAR(100)   NOT NULL,        -- 암벽장 위치
    GYM_RESERVATION_CNT INT DEFAULT 10 NOT NULL,        -- 암벽 예약 가능 개수
    GYM_PRICE           INT DEFAULT 20000               -- 암벽장 이용가격
);

-- CREW
CREATE TABLE CREW
(
    CREW_NUM             INT PRIMARY KEY AUTO_INCREMENT, -- 크루 PK
    CREW_NAME            VARCHAR(250) NOT NULL,          -- 크루 이름
    CREW_DESCRIPTION     TEXT,                           -- 크루 설명
    CREW_MAX_MEMBER_SIZE INT          NOT NULL,          -- 크루 최대 인원수
    CREW_LEADER          VARCHAR(100) NOT NULL,          -- 크루 리더 PK
    CREW_BATTLE_STATUS   CHAR(1)       DEFAULT 'F',      -- 크루 배틀 상태
    CREW_PROFILE         VARCHAR(2048) DEFAULT NULL      -- 크루 프로필
);

-- BOARD
CREATE TABLE BOARD
(
    BOARD_NUM       INT PRIMARY KEY AUTO_INCREMENT, -- 게시글의 고유 식별자 (기본키)
    BOARD_TITLE     VARCHAR(100) NOT NULL,          -- 게시글 제목
    BOARD_CONTENT   VARCHAR(1000),                  -- 게시글 내용 (NULL 허용)
    BOARD_CNT       INT DEFAULT 0,                  -- 조회수 (기본값 0)
    BOARD_LOCATION  VARCHAR(100),                   -- 게시글 지역
    BOARD_WRITER_ID VARCHAR(100),                   -- 글 작성자의 FK (외래키)
    CONSTRAINT FK_BOARD_WRITER_ID
        FOREIGN KEY (BOARD_WRITER_ID)
            REFERENCES MEMBER (MEMBER_ID)
            ON DELETE CASCADE
);

-- REPLY
CREATE TABLE REPLY
(
    REPLY_NUM       INT PRIMARY KEY AUTO_INCREMENT, -- 댓글의 고유 식별자 (기본키)
    REPLY_CONTENT   VARCHAR(100) NOT NULL,          -- 댓글의 내용
    REPLY_BOARD_NUM INT,                            -- 댓글이 달린 게시글의 ID (외래키)
    REPLY_WRITER_ID VARCHAR(100),                   -- 댓글 작성자의 사용자 ID (외래키)
    CONSTRAINT FK_REPLY_BOARD_NUM
        FOREIGN KEY (REPLY_BOARD_NUM)
            REFERENCES BOARD (BOARD_NUM)
            ON DELETE CASCADE,

    CONSTRAINT FK_REPLY_WRITER_ID
        FOREIGN KEY (REPLY_WRITER_ID)
            REFERENCES MEMBER (MEMBER_ID)
            ON DELETE CASCADE
);

-- GRADE
CREATE TABLE GRADE
(
    GRADE_NUM       INT PRIMARY KEY AUTO_INCREMENT, -- 등급별 PK
    GRADE_PROFILE   VARCHAR(2048) NOT NULL,         -- 등급 이미지 URL
    GRADE_NAME      VARCHAR(100)  NOT NULL,         -- 등급에 대한 이름
    GRADE_MIN_POINT INT           NOT NULL,         -- 해당 등급에 최소 포인트 범위
    GRADE_MAX_POINT INT           NOT NULL          -- 해당 등급에 최대 포인트 범위
);

-- FAVORITE
CREATE TABLE FAVORITE
(
    FAVORITE_NUM       INT PRIMARY KEY AUTO_INCREMENT, -- 좋아요 PK
    FAVORITE_MEMBER_ID VARCHAR(100) NOT NULL,          -- 좋아요 누른 사용자 FK
    FAVORITE_GYM_NUM   INT          NOT NULL,          -- 좋아요 누른 암벽장 FK

    CONSTRAINT FK_FAVORITE_MEMBER_ID
        FOREIGN KEY (FAVORITE_MEMBER_ID)
            REFERENCES MEMBER (MEMBER_ID)
            ON DELETE CASCADE,

    CONSTRAINT FK_FAVORITE_GYM_NUM
        FOREIGN KEY (FAVORITE_GYM_NUM)
            REFERENCES GYM (GYM_NUM)
            ON DELETE CASCADE
);

-- CREW_BOARD
CREATE TABLE CREW_BOARD
(
    CREW_BOARD_NUM       INT PRIMARY KEY AUTO_INCREMENT, -- 크루게시판 PK
    CREW_BOARD_WRITER_ID VARCHAR(100) NOT NULL,          -- 크루게시판 작성자 FK
    CREW_BOARD_CONTENT   VARCHAR(200) NOT NULL,          -- 크루게시판 글 내용

    CONSTRAINT FK_CREW_BOARD_WRITER_ID
        FOREIGN KEY (CREW_BOARD_WRITER_ID)
            REFERENCES MEMBER (MEMBER_ID)
            ON DELETE CASCADE
);

-- BATTLE
CREATE TABLE BATTLE
(
    BATTLE_NUM               INT PRIMARY KEY AUTO_INCREMENT, -- 크루전 PK
    BATTLE_GYM_NUM           INT,                            -- 크루전 참여 암벽장 FK
    BATTLE_REGISTRATION_DATE DATE DEFAULT CURRENT_DATE,      -- 크루전 등록 날짜
    BATTLE_GAME_DATE         DATE DEFAULT NULL               -- 크루전 실제 게임 날짜
);

-- BATTLE_RECORD
CREATE TABLE BATTLE_RECORD
(
    BATTLE_RECORD_NUM        INT PRIMARY KEY AUTO_INCREMENT, -- 크루전 참여기록 PK
    BATTLE_RECORD_BATTLE_NUM INT,                            -- 크루전 FK
    BATTLE_RECORD_CREW_NUM   INT,                            -- 크루전 참여한 크루 FK
    BATTLE_RECORD_IS_WINNER  CHAR(1)      DEFAULT 'F',       -- 해당 크루 승리 여부
    BATTLE_RECORD_MVP_ID     VARCHAR(100) DEFAULT NULL,      -- 해당 크루전 MVP 사용자의 PK

    CONSTRAINT FK_BATTLE_RECORD_BATTLE_NUM
        FOREIGN KEY (BATTLE_RECORD_BATTLE_NUM)
            REFERENCES BATTLE (BATTLE_NUM)
            ON DELETE SET NULL,

    CONSTRAINT FK_BATTLE_RECORD_CREW_NUM
        FOREIGN KEY (BATTLE_RECORD_CREW_NUM)
            REFERENCES CREW (CREW_NUM)
);

-- RESERVATION
CREATE TABLE RESERVATION
(
    RESERVATION_NUM       INT PRIMARY KEY AUTO_INCREMENT, -- 예약 번호
    RESERVATION_DATE      DATE         NOT NULL,          -- 예약 날짜
    RESERVATION_GYM_NUM   INT          NOT NULL,          -- 예약한 암벽장 FK
    RESERVATION_MEMBER_ID VARCHAR(100) NOT NULL,          -- 예약한 사용자 FK
    RESERVATION_PRICE     INT          NOT NULL,          -- 예약한 사용자가 실제 결제한 금액

    CONSTRAINT FK_RESERVATION_GYM_NUM
        FOREIGN KEY (RESERVATION_GYM_NUM)
            REFERENCES GYM (GYM_NUM)
            ON DELETE CASCADE,

    CONSTRAINT FK_RESERVATION_MEMBER_ID
        FOREIGN KEY (RESERVATION_MEMBER_ID)
            REFERENCES MEMBER (MEMBER_ID)
);
