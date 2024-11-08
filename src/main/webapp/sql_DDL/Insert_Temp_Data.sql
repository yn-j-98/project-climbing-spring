#외래키 제약조건 잠금
SET FOREIGN_KEY_CHECKS = 0;
/*DELETE MEMBER;
--멤버 테이블 샘플 데이터*/
INSERT INTO MEMBER (
    MEMBER_ID,
    MEMBER_NAME,
    MEMBER_PASSWORD,
    MEMBER_PHONE,
    MEMBER_REGISTRATION_DATE,
    MEMBER_PROFILE,
    MEMBER_CURRENT_POINT,
    MEMBER_TOTAL_POINT,
    MEMBER_CREW_NUM,
    MEMBER_CREW_JOIN_DATE,
    MEMBER_LOCATION,
    MEMBER_ROLE
) VALUES
      ('coma@naver.com', '코마', '1234', '010-6456-5678', '2024-01-01 10:00:00', '/default.jpg', 100, 500, 7, '2024-01-01 10:00:00', '서울', 'F'),
      ('admin@naver.com', '관리자', '1234', '010-0101-0001', '2024-01-01 09:00:00', '/default.jpg', 0, 0, 0, NULL, '서울', 'T'),
      ('alice@example.com', '이은정', '1234', '010-1234-5678', '2024-07-01 10:00:00', '/default.jpg', 100, 500, 1, '2024-07-01 10:00:00', '서울', 'F'),
      ('bob@example.com', '김민수', '1234', '010-2345-6789', '2024-07-10 11:00:00', '/default.jpg', 150, 600, 2, '2024-07-10 11:00:00', '서울', 'F'),
      ('charlie@example.com', '박지훈', '1234', '010-3456-7890', '2024-08-15 12:00:00', '/default.jpg', 200, 700, 1, '2024-08-15 12:00:00', '서울', 'F'),
      ('diana@example.com', '정유진', '1234', '010-4567-8901', '2024-09-02 13:00:00', '/default.jpg', 250, 800, 1, '2024-09-02 13:00:00', '서울', 'F'),
      ('edward@example.com', '김태현', '1234', '010-5678-9012', '2024-09-15 14:00:00', '/default.jpg', 300, 900, 3, '2024-09-15 14:00:00', '서울', 'F'),
      ('fiona@example.com', '최수영', '1234', '010-6789-0123', '2024-10-05 15:00:00', '/default.jpg', 350, 1000, 1, '2024-10-05 15:00:00', '서울', 'F'),
      ('george@example.com', '강지민', '1234', '010-7890-1234', '2024-10-20 16:00:00', '/default.jpg', 400, 1100, 2, '2024-10-20 16:00:00', '서울', 'F'),
      ('hannah@example.com', '김혜진', '1234', '010-8901-2345', '2024-11-01 17:00:00', '/default.jpg', 450, 1200, 1, '2024-11-01 17:00:00', '서울', 'F'),
      ('ian@example.com', '이상훈', '1234', '010-9012-3456', '2024-10-30 18:00:00', '/default.jpg', 500, 1300, 1, '2024-10-30 18:00:00', '서울', 'F'),
      ('jack@example.com', '박재형', '1234', '010-0123-4567', '2024-10-15 19:00:00', '/default.jpg', 550, 1400, 3, '2024-10-15 19:00:00', '서울', 'F'),
      ('kim@example.com', '이소영', '1234', '010-1234-5678', '2024-09-25 20:00:00', '/default.jpg', 600, 1500, 1, '2024-09-25 20:00:00', '서울', 'F'),
      ('liam@example.com', '유경민', '1234', '010-2345-6789', '2024-08-30 21:00:00', '/default.jpg', 650, 1600, 2, '2024-08-30 21:00:00', '서울', 'F'),
      ('monica@example.com', '이은비', '1234', '010-3456-7890', '2024-10-12 22:00:00', '/default.jpg', 700, 1700, 1, '2024-10-12 22:00:00', '서울', 'F'),
      ('nina@example.com', '김진아', '1234', '010-4567-8901', '2024-11-03 23:00:00', '/default.jpg', 750, 1800, 1, '2024-11-03 23:00:00', '서울', 'F'),
      ('oscar@example.com', '박상훈', '1234', '010-5678-9012', '2024-07-20 09:00:00', '/default.jpg', 800, 1900, 3, '2024-07-20 09:00:00', '서울', 'F'),
      ('peter@example.com', '최지원', '1234', '010-6789-0123', '2024-10-25 10:00:00', '/default.jpg', 850, 2000, 1, '2024-10-25 10:00:00', '서울', 'F'),
      ('quinn@example.com', '홍유진', '1234', '010-7890-1234', '2024-09-05 11:00:00', '/default.jpg', 900, 2100, 2, '2024-09-05 11:00:00', '서울', 'F'),
      ('ross@example.com', '이상호', '1234', '010-8901-2345', '2024-08-18 12:00:00', '/default.jpg', 950, 2200, 1, '2024-08-18 12:00:00', '서울', 'F'),
      ('steve@example.com', '최강욱', '1234', '010-9012-3456', '2024-07-30 13:00:00', '/default.jpg', 1000, 2300, 1, '2024-07-30 13:00:00', '서울', 'F'),
      ('tony@example.com', '김영수', '1234', '010-0123-4567', '2024-08-22 14:00:00', '/default.jpg', 1050, 2400, 3, '2024-08-22 14:00:00', '서울', 'F'),
      ('uma@example.com', '정다은', '1234', '010-1234-5678', '2024-09-10 15:00:00', '/default.jpg', 1100, 2500, 1, '2024-09-10 15:00:00', '서울', 'F'),
      ('victor@example.com', '박철호', '1234', '010-2345-6789', '2024-10-02 16:00:00', '/default.jpg', 1150, 2600, 2, '2024-10-02 16:00:00', '서울', 'F'),
      ('winnie@example.com', '배유나', '1234', '010-3456-7890', '2024-11-03 17:00:00', '/default.jpg', 1200, 2700, 1, '2024-11-03 17:00:00', '서울', 'F'),
      ('xena@example.com', '유진서', '1234', '010-4567-8901', '2024-07-05 18:00:00', '/default.jpg', 1250, 2800, 1, '2024-07-05 18:00:00', '서울', 'F'),
      ('yoda@example.com', '이창호', '1234', '010-5678-9012', '2024-11-02 19:00:00', '/default.jpg', 1300, 2900, 3, '2024-11-02 19:00:00', '서울', 'F'),
      ('zorro@example.com', '김준호', '1234', '010-6789-0123', '2024-10-30 20:00:00', '/default.jpg', 1350, 3000, 1, '2024-10-30 20:00:00', '서울', 'F'),
      ('albus@example.com', '박상민', '1234', '010-7890-1234', '2024-09-28 21:00:00', '/default.jpg', 1400, 3100, 2, '2024-09-28 21:00:00', '서울', 'F'),
      ('bella@example.com', '김소정', '1234', '010-8901-2345', '2024-08-28 22:00:00', '/default.jpg', 1450, 3200, 1, '2024-08-28 22:00:00', '서울', 'F');


/*크루 테이블 샘플 데이터*/
INSERT INTO CREW (
    CREW_NAME,
    CREW_DESCRIPTION,
    CREW_MAX_MEMBER_SIZE,
    CREW_LEADER,
    CREW_BATTLE_STATUS,
    CREW_PROFILE
) VALUES
      ('클라이밍 워리어', '자연의 벽을 정복하고 새로운 정상에 도전하는 클라이머들의 모임.', 20, 'alice@example.com', 'F', '/crew_img/default.jpg'),
      ('암벽 수호자', '안전과 협력을 최우선으로 하며 함께 암벽을 오르는 팀.', 20, 'bob@example.com', 'F', '/crew_img/default.jpg'),
      ('탐험가 클라이머', '미지의 루트를 개척하며 새로운 경로를 발견하는 암벽가들.', 20, 'charlie@example.com', 'F', '/crew_img/default.jpg'),
      ('마운틴 마스터', '클라이밍 기술의 달인으로, 도전을 두려워하지 않는 리더들.', 20, 'diana@example.com', 'F', '/crew_img/default.jpg'),
      ('정상 정복자', '각종 산악 활동을 즐기며 정상 정복을 목표로 하는 팀.', 20, 'edward@example.com', 'F', '/crew_img/default.jpg'),
      ('안전우선', '클라이밍 중의 부상 예방과 치유를 전문으로 하는 팀.', 20, 'fiona@example.com', 'F', '/crew_img/default.jpg'),
      ('코마', '코드 마스터의 길, 코드 마운틴을 정복', 20, 'coma@naver.com', 'F', '/crew_img/coma.jpg');


/*암벽장 테이블 샘플 데이터*/
INSERT INTO GYM (GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION) VALUES
                                                                           ('볼더하이웨이클라이밍짐', '/gym_img/default.jpg', '초보자부터 전문가까지 즐길 수 있는 다양한 난이도의 암벽 코스를 제공합니다.', '서울 강남구 논현로86길 20'),
                                                                           ('더클라임 클라이밍 짐앤샵 강남점', '/gym_img/default.jpg', '실내에서 안전하게 암벽 등반을 체험할 수 있는 최고의 공간입니다.', '서울 강남구 테헤란로8길 21'),
                                                                           ('클라이밍파크 강남점', '/gym_img/default.jpg', '암벽 등반을 통한 체력 향상과 스트레스 해소가 가능한 곳입니다.', '서울 강남구 강남대로 364'),
                                                                           ('손상원 클라이밍짐 강남역점', '/gym_img/default.jpg', '다양한 코스와 세심한 지도 아래 체험할 수 있는 암벽장입니다.', '서울 서초구 강남대로 331'),
                                                                           ('무중력 클라이밍', '/gym_img/default.jpg', '실내 암벽에서의 도전 정신을 키울 수 있는 독특한 경험을 제공합니다.', '서울 강남구 논현로 563'),
                                                                           ('클라이밍파크 신논현점', '/gym_img/default.jpg', '초보자부터 숙련자까지 자신의 수준에 맞는 코스를 제공하는 암벽장입니다.', '서울 강남구 강남대로 468'),
                                                                           ('돌나무클라이밍', '/gym_img/default.jpg', '편안한 분위기에서 전문가의 지도 아래 체험할 수 있는 암벽장입니다.', '서울 강남구 강남대로118길 42'),
                                                                           ('더클라임 논현점', '/gym_img/default.jpg', '다양한 난이도의 암벽을 경험하고 도전할 수 있는 멀티 기능의 공간입니다.', '서울 서초구 강남대로 519'),
                                                                           ('역삼 클라이밍 랩', '/gym_img/default.jpg', '실내 암벽과 트릭을 통해 다양한 장애물을 극복할 수 있는 암벽장입니다.', '서울 강남구 테헤란로30길 49'),
                                                                           ('클라이밍파크 한티점', '/gym_img/default.jpg', '기초부터 고급까지 체계적으로 암벽 등반을 배울 수 있는 곳입니다.', '서울 강남구 선릉로 324'),
                                                                           ('더클라임 서울역점', '/gym_img/default.jpg', '실내 암벽 등반을 체험할 수 있는 중심지 암벽장입니다.', '서울 용산구 한강대로 405'),
                                                                           ('도봉산 클라이밍센터', '/gym_img/default.jpg', '서울 도봉산에 위치한 자연 친화적인 클라이밍 장소입니다.', '서울 도봉구 도봉로150길 33'),
                                                                           ('노원 클라이밍센터', '/gym_img/default.jpg', '암벽 등반을 통해 체력 단련과 정신 집중을 경험할 수 있는 곳입니다.', '서울 노원구 동일로 1345'),
                                                                           ('중랑클라이밍짐', '/gym_img/default.jpg', '다양한 코스와 체험을 제공하는 클라이밍 센터입니다.', '서울 중랑구 봉우재로 178'),
                                                                           ('성동 클라이밍파크', '/gym_img/default.jpg', '서울 성동구에서 즐길 수 있는 다양한 난이도의 암벽장입니다.', '서울 성동구 왕십리로 222'),
                                                                           ('동작 클라이밍 스페이스', '/gym_img/default.jpg', '실내에서 암벽 등반을 안전하게 체험할 수 있는 공간입니다.', '서울 동작구 장승배기로 84'),
                                                                           ('은평 클라이밍파크', '/gym_img/default.jpg', '초보자부터 전문가까지 다양한 난이도의 코스를 제공하는 암벽장입니다.', '서울 은평구 통일로 854'),
                                                                           ('마포 클라이밍 스튜디오', '/gym_img/default.jpg', '편안한 분위기에서 클라이밍을 즐길 수 있는 아늑한 공간입니다.', '서울 마포구 독막로 224'),
                                                                           ('강서 클라이밍 짐', '/gym_img/default.jpg', '초보자부터 고급자까지 즐길 수 있는 다양한 암벽 코스를 제공합니다.', '서울 강서구 화곡로66길 20'),
                                                                           ('관악 클라이밍 파크', '/gym_img/default.jpg', '실내에서 암벽 등반을 체험하고 도전할 수 있는 공간입니다.', '서울 관악구 남부순환로 1900'),
                                                                           ('송파 클라이밍 클럽', '/gym_img/default.jpg', '실내 암벽과 체계적인 코스로 클라이밍을 배울 수 있는 곳입니다.', '서울 송파구 가락로 153'),
                                                                           ('양천 클라이밍 센터', '/gym_img/default.jpg', '편안한 분위기와 다양한 난이도로 즐길 수 있는 암벽장입니다.', '서울 양천구 목동중앙로 105'),
                                                                           ('금천 클라이밍 짐', '/gym_img/default.jpg', '도전 정신을 높이는 다양한 난이도의 암벽을 제공합니다.', '서울 금천구 시흥대로 245'),
                                                                           ('중구 클라이밍 체험장', '/gym_img/default.jpg', '서울 중구에서 즐길 수 있는 실내 암벽 체험장입니다.', '서울 중구 세종대로 110'),
                                                                           ('강동 클라이밍 센터', '/gym_img/default.jpg', '초보자부터 숙련자까지 다양한 암벽 코스와 체험이 가능한 공간입니다.', '서울 강동구 양재대로 1465'),
-- 부산
                                                                           ('부산 클라이밍 센터', '/gym_img/default.jpg', '부산에서 고급 코스와 체험 코스를 즐길 수 있는 실내 암벽장입니다.', '부산 해운대구 센텀동로 35'),
                                                                           ('더클라임 부산 센텀점', '/gym_img/default.jpg', '실내에서 안전하게 클라이밍을 체험할 수 있는 최고의 장소입니다.', '부산 해운대구 센텀2로 25'),
                                                                           ('클라이밍파크 부산 해운대점', '/gym_img/default.jpg', '부산 해운대에서 즐길 수 있는 다양한 난이도의 암벽 코스를 제공합니다.', '부산 해운대구 해운대로 802'),
                                                                           ('볼더 부산 클라이밍', '/gym_img/default.jpg', '초보자부터 숙련자까지 다양한 레벨을 위한 클라이밍 코스를 제공합니다.', '부산 연제구 쌍미천로 16'),
                                                                           ('클라이밍월드 부산점', '/gym_img/default.jpg', '부산에서 체계적으로 클라이밍을 배울 수 있는 전문 클라이밍 짐입니다.', '부산 동래구 아시아드대로 230'),
                                                                           ('더 클라이밍 서면점', '/gym_img/default.jpg', '편안한 분위기에서 안전하게 즐길 수 있는 암벽장입니다.', '부산 부산진구 중앙대로 568'),
                                                                           ('부산대학교 클라이밍 센터', '/gym_img/default.jpg', '부산대학교 내 위치한 실내 암벽장입니다.', '부산 금정구 부산대학로 63'),
                                                                           ('남구 클라이밍 파크', '/gym_img/default.jpg', '초보자부터 숙련자까지 다양한 난이도를 즐길 수 있는 암벽장입니다.', '부산 남구 전포대로 134'),
                                                                           ('동래 클라이밍 클럽', '/gym_img/default.jpg', '초보자부터 고급자까지 즐길 수 있는 다양한 암벽 코스를 제공합니다.', '부산 동래구 복천로 23'),
                                                                           ('해운대 클라이밍 체험장', '/gym_img/default.jpg', '해운대구에서 안전하게 즐길 수 있는 실내 암벽장입니다.', '부산 해운대구 송정광로 12'),
-- 인천
                                                                           ('인천 클라이밍 센터', '/gym_img/default.jpg', '인천에서 초보자부터 전문가까지 다양한 난이도의 암벽을 제공합니다.', '인천 남동구 문화로 112'),
                                                                           ('볼더 클라이밍 인천점', '/gym_img/default.jpg', '실내에서 안전하게 클라이밍을 체험할 수 있는 공간입니다.', '인천 미추홀구 숙골로 123'),
                                                                           ('클라이밍월드 인천 부평점', '/gym_img/default.jpg', '부평구에 위치한 다양한 난이도의 클라이밍 시설입니다.', '인천 부평구 부평대로 32'),
                                                                           ('더클라임 인천 계양점', '/gym_img/default.jpg', '계양구에서 실내 암벽을 체험할 수 있는 클라이밍 짐입니다.', '인천 계양구 장제로 134'),
                                                                           ('서구 클라이밍 스튜디오', '/gym_img/default.jpg', '편안한 분위기와 전문가 지도 아래 클라이밍을 즐길 수 있는 곳입니다.', '인천 서구 심곡로 45'),
                                                                           ('인천 클라이밍 파크', '/gym_img/default.jpg', '다양한 난이도로 암벽 등반을 경험할 수 있는 실내 클라이밍장입니다.', '인천 연수구 송도국제대로 94'),
                                                                           ('청라 클라이밍 체험장', '/gym_img/default.jpg', '인천 청라에 위치한 실내 암벽 체험 공간입니다.', '인천 서구 청라대로 233'),
                                                                           ('남동 클라이밍 아카데미', '/gym_img/default.jpg', '남동구에서 초보자부터 숙련자까지 체계적으로 배울 수 있는 암벽장입니다.', '인천 남동구 백범로 98'),
                                                                           ('연수 클라이밍 센터', '/gym_img/default.jpg', '연수구에서 다양한 암벽 코스를 체험할 수 있는 실내 클라이밍장입니다.', '인천 연수구 하모니로 202'),
                                                                           ('송도 클라이밍 스튜디오', '/gym_img/default.jpg', '송도에서 초보자부터 고급자까지 즐길 수 있는 다양한 암벽 코스를 제공합니다.', '인천 연수구 송도미래로 123'),
-- 대구
                                                                           ('대구 클라이밍 센터', '/gym_img/default.jpg', '대구에서 다양한 난이도의 클라이밍 코스를 제공합니다.', '대구 중구 동성로2길 77'),
                                                                           ('볼더하이웨이 클라이밍 대구점', '/gym_img/default.jpg', '초보자부터 숙련자까지 즐길 수 있는 실내 암벽장입니다.', '대구 남구 봉덕로 103'),
                                                                           ('클라이밍월드 대구 수성점', '/gym_img/default.jpg', '수성구에서 체계적으로 클라이밍을 배울 수 있는 곳입니다.', '대구 수성구 신천동로 321'),
                                                                           ('더클라임 대구 북구점', '/gym_img/default.jpg', '대구 북구에서 안전하게 클라이밍을 즐길 수 있는 장소입니다.', '대구 북구 구암로 57'),
                                                                           ('동구 클라이밍 파크', '/gym_img/default.jpg', '초보자부터 고급자까지 다양한 코스를 제공하는 실내 암벽장입니다.', '대구 동구 신암남로 105'),
                                                                           ('서구 클라이밍 아카데미', '/gym_img/default.jpg', '서구에서 전문가의 지도 아래 체험할 수 있는 암벽장입니다.', '대구 서구 서대구로 141'),
                                                                           ('달서 클라이밍 스튜디오', '/gym_img/default.jpg', '달서구에서 실내 암벽을 즐길 수 있는 쾌적한 공간입니다.', '대구 달서구 장기로 89'),
                                                                           ('남구 클라이밍 체험장', '/gym_img/default.jpg', '다양한 난이도의 암벽 체험을 할 수 있는 남구 암벽장입니다.', '대구 남구 대명로 94'),
                                                                           ('수성 클라이밍 스페이스', '/gym_img/default.jpg', '수성구에서 초보자부터 고급자까지 즐길 수 있는 암벽장입니다.', '대구 수성구 지산로 121'),
                                                                           ('대명 클라이밍 파크', '/gym_img/default.jpg', '대구에서 도전 정신을 높일 수 있는 실내 암벽장입니다.', '대구 남구 중앙대로 1001'),
-- 광주
                                                                           ('광주 클라이밍 센터', '/gym_img/default.jpg', '광주에서 초보자부터 숙련자까지 즐길 수 있는 실내 클라이밍장입니다.', '광주 서구 치평로 89'),
                                                                           ('더클라임 광주 상무점', '/gym_img/default.jpg', '실내 암벽 등반을 체험할 수 있는 다양한 코스가 마련되어 있습니다.', '광주 서구 상무중앙로 70'),
                                                                           ('볼더클라이밍 광주', '/gym_img/default.jpg', '광주 북구에 위치한 초보자 및 고급자 모두에게 적합한 암벽장입니다.', '광주 북구 서하로 54'),
                                                                           ('남구 클라이밍 체험장', '/gym_img/default.jpg', '남구에서 다양한 난이도의 암벽 체험이 가능합니다.', '광주 남구 효덕로 74'),
                                                                           ('광산 클라이밍 파크', '/gym_img/default.jpg', '광산구에서 즐길 수 있는 다양한 암벽 코스가 있는 실내 암벽장입니다.', '광주 광산구 송정로 231'),
                                                                           ('서구 클라이밍 아카데미', '/gym_img/default.jpg', '서구에서 전문가 지도 아래 클라이밍을 배울 수 있는 곳입니다.', '광주 서구 상무대로 100'),
                                                                           ('동구 클라이밍 월드', '/gym_img/default.jpg', '초보자부터 고급자까지 다양한 난이도로 즐길 수 있는 암벽장입니다.', '광주 동구 중앙로 76'),
                                                                           ('광주 클라이밍 스튜디오', '/gym_img/default.jpg', '편안한 분위기에서 실내 클라이밍을 체험할 수 있는 공간입니다.', '광주 서구 상무대로 215'),
                                                                           ('광주 체육클라이밍센터', '/gym_img/default.jpg', '체육 시설 내 클라이밍 체험 공간입니다.', '광주 북구 양산로 91'),
                                                                           ('남광주 클라이밍 체험장', '/gym_img/default.jpg', '남광주에서 초보자부터 숙련자까지 도전할 수 있는 다양한 코스 제공.', '광주 남구 백운로 52'),
-- 대전
                                                                           ('대전 클라이밍 센터', '/gym_img/default.jpg', '대전에서 초보자부터 고급자까지 다양한 코스를 제공하는 암벽장입니다.', '대전 서구 둔산대로 89'),
                                                                           ('더클라임 대전 유성점', '/gym_img/default.jpg', '유성구에서 안전하게 클라이밍을 즐길 수 있는 장소입니다.', '대전 유성구 계룡로 111'),
                                                                           ('볼더 클라이밍 대전점', '/gym_img/default.jpg', '초보자부터 숙련자까지 다양한 난이도로 즐길 수 있는 암벽장입니다.', '대전 동구 동서대로 45'),
                                                                           ('대덕 클라이밍 파크', '/gym_img/default.jpg', '대덕구에서 다양한 레벨의 클라이밍 코스를 제공하는 곳입니다.', '대전 대덕구 신탄진로 120'),
                                                                           ('중구 클라이밍 체험장', '/gym_img/default.jpg', '중구에서 실내 암벽 체험을 안전하게 할 수 있는 장소입니다.', '대전 중구 대종로 77'),
                                                                           ('서구 클라이밍 아카데미', '/gym_img/default.jpg', '초보자부터 전문가까지 다양한 코스를 제공하는 암벽장입니다.', '대전 서구 도솔로 35'),
                                                                           ('대전클라이밍월드', '/gym_img/default.jpg', '대전에서 체계적으로 클라이밍을 배울 수 있는 공간입니다.', '대전 유성구 온천북로 212'),
                                                                           ('유성 클라이밍 스튜디오', '/gym_img/default.jpg', '유성구에서 즐길 수 있는 실내 암벽 체험장입니다.', '대전 유성구 문화로 98'),
                                                                           ('대전 청소년클라이밍장', '/gym_img/default.jpg', '청소년을 위한 다양한 코스가 마련된 암벽장입니다.', '대전 동구 중앙로 145'),
                                                                           ('남대전 클라이밍 파크', '/gym_img/default.jpg', '대전에서 도전 정신을 키울 수 있는 실내 클라이밍 체험장입니다.', '대전 동구 한밭대로 300');
/*예약 테이블 샘플 데이터*/
INSERT INTO RESERVATION (RESERVATION_NUM, RESERVATION_DATE, RESERVATION_GYM_NUM, RESERVATION_MEMBER_ID, RESERVATION_PRICE) VALUES
                                                                                                                               ('R0001', '2024-08-03 10:15:00', 3, 'alice@example.com', 17000),
                                                                                                                               ('R0002', '2024-08-15 12:30:00', 5, 'george@example.com', 18000),
                                                                                                                               ('R0003', '2024-08-20 14:45:00', 8, 'ian@example.com', 16000),
                                                                                                                               ('R0004', '2024-08-25 17:00:00', 2, 'kim@example.com', 19000),
                                                                                                                               ('R0005', '2024-09-01 09:00:00', 10, 'hannah@example.com', 15000),
                                                                                                                               ('R0006', '2024-09-03 11:30:00', 4, 'fiona@example.com', 17000),
                                                                                                                               ('R0007', '2024-09-08 13:00:00', 6, 'edward@example.com', 16000),
                                                                                                                               ('R0008', '2024-09-12 10:30:00', 7, 'charlie@example.com', 18000),
                                                                                                                               ('R0009', '2024-09-18 15:15:00', 1, 'bob@example.com', 20000),
                                                                                                                               ('R0010', '2024-09-25 18:00:00', 9, 'jack@example.com', 19000),
                                                                                                                               ('R0011', '2024-09-30 20:30:00', 3, 'george@example.com', 17000),
                                                                                                                               ('R0012', '2024-10-02 14:00:00', 5, 'ian@example.com', 16000),
                                                                                                                               ('R0013', '2024-10-05 11:45:00', 2, 'alice@example.com', 19000),
                                                                                                                               ('R0014', '2024-10-08 12:30:00', 4, 'fiona@example.com', 18000),
                                                                                                                               ('R0015', '2024-10-12 09:00:00', 6, 'kim@example.com', 17000),
                                                                                                                               ('R0016', '2024-10-15 16:15:00', 7, 'bob@example.com', 16000),
                                                                                                                               ('R0017', '2024-10-18 14:30:00', 8, 'charlie@example.com', 15000),
                                                                                                                               ('R0018', '2024-10-22 10:00:00', 9, 'hannah@example.com', 19000),
                                                                                                                               ('R0019', '2024-10-25 13:00:00', 10, 'jack@example.com', 20000),
                                                                                                                               ('R0020', '2024-10-28 19:45:00', 3, 'bob@example.com', 18000),
                                                                                                                               ('R0021', '2024-11-01 11:00:00', 5, 'fiona@example.com', 17000),
                                                                                                                               ('R0022', '2024-11-04 17:30:00', 2, 'george@example.com', 16000),
                                                                                                                               ('R0023', '2024-11-04 15:00:00', 1, 'alice@example.com', 15000),
                                                                                                                               ('R0024', '2024-08-10 09:15:00', 4, 'kim@example.com', 19000),
                                                                                                                               ('R0025', '2024-08-17 13:45:00', 7, 'hannah@example.com', 17000),
                                                                                                                               ('R0026', '2024-08-22 18:00:00', 6, 'jack@example.com', 16000),
                                                                                                                               ('R0027', '2024-08-28 11:30:00', 9, 'bob@example.com', 18000),
                                                                                                                               ('R0028', '2024-09-05 14:00:00', 10, 'george@example.com', 17000),
                                                                                                                               ('R0029', '2024-09-09 16:30:00', 8, 'ian@example.com', 16000),
                                                                                                                               ('R0030', '2024-09-14 10:45:00', 3, 'alice@example.com', 15000),
                                                                                                                               ('R0031', '2024-09-20 12:30:00', 5, 'charlie@example.com', 19000),
                                                                                                                               ('R0032', '2024-09-23 17:15:00', 4, 'kim@example.com', 20000),
                                                                                                                               ('R0033', '2024-09-27 19:00:00', 2, 'fiona@example.com', 18000),
                                                                                                                               ('R0034', '2024-10-01 11:00:00', 6, 'jack@example.com', 16000),
                                                                                                                               ('R0035', '2024-10-04 10:00:00', 1, 'bob@example.com', 17000),
                                                                                                                               ('R0036', '2024-10-07 13:00:00', 9, 'hannah@example.com', 15000),
                                                                                                                               ('R0037', '2024-10-10 15:30:00', 10, 'charlie@example.com', 18000),
                                                                                                                               ('R0038', '2024-10-14 19:00:00', 7, 'george@example.com', 19000),
                                                                                                                               ('R0039', '2024-10-18 12:15:00', 5, 'alice@example.com', 17000),
                                                                                                                               ('R0040', '2024-10-21 11:30:00', 4, 'bob@example.com', 16000);


/*등급 테이블 샘플 데이터*/
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge1.png','5A',0,1000);
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge2.png','5B',1001,2000);
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge3.png','5C',2001,3000);
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge4.png','6A',3001,4000);
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge5.png','6B',4001,5000);
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge6.png','6C',5001,6000);
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge7.png','7A',6001,7000);
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge8.png','7B',7001,8000);
INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)
VALUES('/grade_img/bedge9.png','7C',8001,9000);

/*
DELETE BATTLE;
크루전 테이블 샘플 데이터*/
INSERT INTO BATTLE(BATTLE_GYM_NUM,BATTLE_REGISTRATION_DATE,BATTLE_GAME_DATE)
VALUES (1,'2024-09-07','2024-09-21');
INSERT INTO BATTLE(BATTLE_GYM_NUM,BATTLE_REGISTRATION_DATE,BATTLE_GAME_DATE)
VALUES (2,'2024-11-04','2024-11-16');
INSERT INTO BATTLE(BATTLE_GYM_NUM,BATTLE_REGISTRATION_DATE,BATTLE_GAME_DATE)
VALUES (3,'2024-09-07','2024-09-27');
INSERT INTO BATTLE(BATTLE_GYM_NUM,BATTLE_REGISTRATION_DATE,BATTLE_GAME_DATE)
VALUES (4,'2024-09-07','2024-09-28');
INSERT INTO BATTLE(BATTLE_GYM_NUM,BATTLE_REGISTRATION_DATE,BATTLE_GAME_DATE)
VALUES (5,'2024-09-07','2024-09-24');
INSERT INTO BATTLE(BATTLE_GYM_NUM,BATTLE_REGISTRATION_DATE,BATTLE_GAME_DATE)
VALUES (6,'2024-09-07','2024-10-05');
/*
DELETE BATTLE_RECORD;
--크루전 승패 여부 샘플 데이터*/
INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM)
VALUES (4,1);
INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM)
VALUES (4,2);
INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM)
VALUES (4,3);

INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM,BATTLE_RECORD_IS_WINNER,BATTLE_RECORD_MVP_ID)
VALUES (1,1,'F','alice@example.com');
INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM,BATTLE_RECORD_IS_WINNER,BATTLE_RECORD_MVP_ID)
VALUES (1,2,'F','alice@example.com');
INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM,BATTLE_RECORD_IS_WINNER,BATTLE_RECORD_MVP_ID)
VALUES (1,3,'T','alice@example.com');

INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM,BATTLE_RECORD_IS_WINNER,BATTLE_RECORD_MVP_ID)
VALUES (5,1,'T','alice@example.com');
INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM,BATTLE_RECORD_IS_WINNER,BATTLE_RECORD_MVP_ID)
VALUES (5,2,'F','alice@example.com');
INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM,BATTLE_RECORD_IS_WINNER,BATTLE_RECORD_MVP_ID)
VALUES (5,3,'F','alice@example.com');

INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM)
VALUES (3,4);

INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM)
VALUES (6,5);
/*
DELETE BOARD;
--BOARD 샘플 데이터*/
INSERT INTO BOARD (BOARD_TITLE, BOARD_CONTENT, BOARD_CNT, BOARD_LOCATION, BOARD_WRITER_ID) VALUES
                                                                                               ('암벽 등반 초보자 가이드', '초보자를 위한 등반 시작 가이드를 소개합니다.', 0, '서울특별시', 'alice@example.com'),
                                                                                               ('클라이밍 장비 추천', '등반 장비를 추천합니다.', 0, '서울특별시', 'bob@example.com'),
                                                                                               ('등반 파트너 찾기', '혼자서 등반하기 어려운 분들을 위한 파트너 찾기 방법.', 0, '서울특별시', 'charlie@example.com'),
                                                                                               ('암벽 등반의 기본', '암벽 등반을 시작하는 데 필요한 기본 지식을 공유합니다.', 0, '서울특별시', 'diana@example.com'),
                                                                                               ('장비 점검 및 유지 관리', '등반 장비의 점검 및 유지 보수 방법에 대해 다룹니다.', 0, '서울특별시', 'edward@example.com'),
                                                                                               ('등반 대회 준비 팁', '클라이밍 대회를 준비하는 팁을 소개합니다.', 0, '서울특별시', 'fiona@example.com'),
                                                                                               ('국내 암벽 등반 코스', '국내에서 추천하는 인기 있는 암벽 등반 코스를 소개합니다.', 0, '서울특별시', 'george@example.com'),
                                                                                               ('암벽 등반 운동법', '효과적인 등반을 위한 운동법과 훈련을 소개합니다.', 0, '서울특별시', 'hannah@example.com'),
                                                                                               ('클라이밍 대회 후기', '최근에 참가한 클라이밍 대회의 후기를 작성합니다.', 0, '서울특별시', 'ian@example.com'),
                                                                                               ('초보자를 위한 클라이밍 용어 정리', '클라이밍 초보자가 알아야 할 기본 용어들을 정리해봤습니다.', 0, '서울특별시', 'jack@example.com'),
                                                                                               ('겨울철 클라이밍 안전 수칙', '겨울철에 클라이밍을 할 때 유의해야 할 안전 수칙.', 0, '서울특별시', 'kim@example.com'),
                                                                                               ('상급자용 클라이밍 기술', '상급자 클라이밍 기술을 배우고 싶다면?', 0, '서울특별시', 'alice@example.com'),
                                                                                               ('등반 체력 훈련법', '등반 체력 향상을 위한 다양한 훈련법을 소개합니다.', 0, '서울특별시', 'bob@example.com'),
                                                                                               ('암벽 등반에서 중요한 점', '암벽 등반 시 가장 중요한 점에 대해 설명합니다.', 0, '서울특별시', 'charlie@example.com'),
                                                                                               ('클라이밍 사고 예방', '등반 중 사고를 예방하는 방법에 대해 알아봅니다.', 0, '서울특별시', 'diana@example.com'),
                                                                                               ('클라이밍 초보자가 피해야 할 실수', '등반 초보자가 자주 하는 실수를 피할 수 있는 팁.', 0, '서울특별시', 'edward@example.com'),
                                                                                               ('암벽 등반의 다양한 종류', '암벽 등반의 다양한 종류와 각각의 특징에 대해 다룹니다.', 0, '서울특별시', 'fiona@example.com'),
                                                                                               ('등반 장비 사용법', '클라이밍 장비의 올바른 사용법에 대해 알려드립니다.', 0, '서울특별시', 'george@example.com'),
                                                                                               ('클라이밍 대회 준비 체크리스트', '대회 준비를 위한 체크리스트를 제공합니다.', 0, '서울특별시', 'hannah@example.com'),
                                                                                               ('클라이밍 용어 사전', '등반을 하면서 필요한 용어들을 사전 형식으로 정리합니다.', 0, '서울특별시', 'ian@example.com'),
                                                                                               ('국내 암벽 등반 대회 일정', '국내에서 열리는 암벽 등반 대회의 일정을 안내합니다.', 0, '서울특별시', 'jack@example.com'),
                                                                                               ('등반 전 워밍업 방법', '효과적인 워밍업 방법으로 부상을 예방하세요.', 0, '서울특별시', 'kim@example.com'),
                                                                                               ('암벽 등반에서 필요한 기본 기술', '암벽 등반 시 반드시 필요한 기술들을 알려드립니다.', 0, '서울특별시', 'alice@example.com'),
                                                                                               ('암벽 등반 중 안전장비 착용법', '안전하게 등반을 즐기기 위한 장비 착용법.', 0, '서울특별시', 'bob@example.com'),
                                                                                               ('중급자 클라이밍 기술', '중급자를 위한 클라이밍 기술을 소개합니다.', 0, '서울특별시', 'charlie@example.com'),
                                                                                               ('자주 묻는 클라이밍 Q&A', '클라이밍 초보자들이 자주 묻는 질문과 그에 대한 답변.', 0, '서울특별시', 'diana@example.com'),
                                                                                               ('암벽 등반 루트 추천', '서울에 있는 추천 루트를 소개합니다.', 0, '서울특별시', 'edward@example.com'),
                                                                                               ('초보자 필수 클라이밍 장비', '초보자가 준비해야 할 필수 장비를 추천합니다.', 0, '서울특별시', 'fiona@example.com'),
                                                                                               ('암벽 등반의 심리적 준비', '등반 시 심리적으로 준비해야 할 점에 대해 설명합니다.', 0, '서울특별시', 'george@example.com'),
                                                                                               ('체력 관리와 등반 성과', '체력 관리가 클라이밍 성과에 미치는 영향을 다룹니다.', 0, '서울특별시', 'hannah@example.com'),
                                                                                               ('등반 후 회복법', '등반 후 필요한 회복법을 소개합니다.', 0, '서울특별시', 'ian@example.com'),
                                                                                               ('암벽 등반 코스별 난이도 비교', '다양한 암벽 등반 코스를 난이도별로 비교합니다.', 0, '서울특별시', 'jack@example.com'),
                                                                                               ('겨울철 암벽 등반 장비', '겨울철 등반 시 필요한 장비에 대해 알아봅니다.', 0, '서울특별시', 'kim@example.com'),
                                                                                               ('초보자도 할 수 있는 간단한 트릭', '초보자도 쉽게 할 수 있는 간단한 등반 트릭을 소개합니다.', 0, '서울특별시', 'alice@example.com'),
                                                                                               ('암벽 등반 후 스트레칭', '효과적인 스트레칭 방법으로 등반 후 근육을 이완시키세요.', 0, '서울특별시', 'bob@example.com'),
                                                                                               ('기본 암벽 등반 기법', '암벽 등반을 잘하기 위한 기본 기법을 배워봅시다.', 0, '서울특별시', 'charlie@example.com'),
                                                                                               ('여름철 암벽 등반 팁', '여름철에 등반할 때 유의해야 할 점들을 소개합니다.', 0, '서울특별시', 'diana@example.com'),
                                                                                               ('단기 등반 훈련 프로그램', '효과적인 단기 훈련 프로그램을 소개합니다.', 0, '서울특별시', 'edward@example.com'),
                                                                                               ('경쟁적 클라이밍의 재미', '경쟁적인 클라이밍 대회에서 얻을 수 있는 재미와 보람에 대해 이야기합니다.', 0, '서울특별시', 'fiona@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에 가면 입장료가 어떻게 되나요?', 1, 'coma@naver.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('초보자를 위한 벽 높이는 어느 정도가 적당한가요?', 2, 'bob@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에 처음 가는데 필요한 장비는 무엇인가요?', 3, 'alice@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('벽이 너무 어려워서 어떻게 해야 할지 모르겠어요. 조언 부탁드려요.', 4, 'diana@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에서 발판이 미끄러운데 어떻게 해야 할까요?', 5, 'charlie@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에 몇 시간 정도 연습해야 실력이 늘까요?', 6, 'hannah@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에 가면 대부분 혼자 가나요, 아니면 그룹으로 가나요?', 7, 'fiona@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('올바른 스트레칭 방법을 알려주세요. 부상 예방이 중요한 것 같아서요.', 8, 'edward@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장을 자주 가는 것이 중요한가요, 아니면 가끔 가도 괜찮을까요?', 9, 'george@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('등반 초보인데 한 번 가면 몇 번 정도 도전해야 실력을 느낄 수 있나요?', 10, 'jack@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에서 안전하게 등반하려면 어떤 점을 주의해야 할까요?', 11, 'kim@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에 꼭 필요한 장비는 무엇인가요?', 12, 'bob@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('등반 연습을 하는데 스태미너가 부족해서 힘들어요. 어떻게 하면 좋을까요?', 13, 'alice@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('고급자용 루트는 어떻게 다루어야 할까요?', 14, 'charlie@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에서 바닥에 떨어질 위험을 줄이려면 어떻게 해야 하나요?', 15, 'diana@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('초보자도 할 수 있는 기본적인 클라이밍 루트가 있나요?', 16, 'fiona@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에 있는 루트들이 다 어려운가요? 쉬운 루트도 있나요?', 17, 'hannah@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('등반 전후로 해야 하는 준비운동과 정리 운동은 어떤 것이 있을까요?', 18, 'george@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('실내 암벽장에서 사용할 로프는 어떤 것을 사용해야 할까요?', 19, 'edward@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장을 자주 가는 것이 실력 향상에 도움이 되나요?', 20, 'jack@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('초보자가 사용할 수 있는 안전 장비는 무엇이 있을까요?', 21, 'kim@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에 갈 때 적절한 옷차림은 어떻게 해야 하나요?', 22, 'coma@naver.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('등반 중에는 어떤 호흡법이 좋을까요?', 23, 'bob@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('처음 클라이밍을 시도하는데, 정신적 준비가 필요할까요?', 24, 'alice@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에서 미끄러지지 않도록 손과 발을 잘 쓰는 팁이 있을까요?', 25, 'diana@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에서 발생할 수 있는 부상에 대한 예방 방법을 알고 싶어요.', 26, 'charlie@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('클라이밍 실력을 빠르게 키우는 팁이 있을까요?', 27, 'fiona@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('실내 암벽장에서 외부 환경(날씨 등)의 영향을 받나요?', 28, 'hannah@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에서는 어떤 운동이 실력을 키우는 데 도움이 될까요?', 29, 'george@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에 가면 체력 소모가 많은데, 체력 관리 방법이 있을까요?', 30, 'edward@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('어떤 종류의 장비를 빌려서 사용해도 괜찮을까요?', 31, 'jack@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('초보자라도 즐길 수 있는 재미있는 코스가 있을까요?', 32, 'kim@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에서 주의해야 할 규칙들이 있을까요?', 33, 'coma@naver.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장 가기 전에 준비할 사항들이 있을까요?', 34, 'bob@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장 갈 때 준비물을 체크리스트로 알려주실 수 있나요?', 35, 'alice@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('체력적으로 힘들어지면 어떤 방법으로 휴식을 취해야 할까요?', 36, 'diana@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장 이용 시 꼭 알아둬야 할 안전 수칙은 무엇인가요?', 37, 'charlie@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('고난이도 루트를 도전하려면 어떤 준비가 필요할까요?', 38, 'hannah@example.com');

INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES
    ('암벽장에서는 신발을 어떻게 고르는 것이 좋을까요?', 39, 'fiona@example.com');

#외래키 제약조건 잠금 해제
SET FOREIGN_KEY_CHECKS = 1;