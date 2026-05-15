USE mnwiki;

-- =========================================================
-- 1. [회원 샘플 데이터]
-- =========================================================
-- 테스트용 비밀번호는 모두 1234
-- password 컬럼에는 BCrypt 암호화 값을 저장한다.

INSERT INTO member
(username, password, nickname, email, role, pet_type, pet_started, created_at)
VALUES
('user1',
 '$2a$10$XHAx2yQZ6wF.LplTxc0PSePjxxnagvng7QAn3YMQS5kyJJ5VhDIhq',
 '강아지맘',
 'user1@test.com',
 'ROLE_USER',
 '강아지',
 2018,
 NOW()),

('user2',
 '$2a$10$XHAx2yQZ6wF.LplTxc0PSePjxxnagvng7QAn3YMQS5kyJJ5VhDIhq',
 '냥집사',
 'user2@test.com',
 'ROLE_USER',
 '고양이',
 2021,
 NOW()),

('user3',
 '$2a$10$XHAx2yQZ6wF.LplTxc0PSePjxxnagvng7QAn3YMQS5kyJJ5VhDIhq',
 '초보견주',
 'user3@test.com',
 'ROLE_USER',
 '강아지',
 2025,
 NOW()),

('admin',
 '$2a$10$XHAx2yQZ6wF.LplTxc0PSePjxxnagvng7QAn3YMQS5kyJJ5VhDIhq',
 '관리자',
 'admin@test.com',
 'ROLE_ADMIN',
 NULL,
 NULL,
 NOW());


-- =========================================================
-- 2. [게시글 샘플 데이터]
-- =========================================================
-- category 값
-- QUESTION : 질문 게시판
-- ADOPTION : 분양 게시판
-- TIP      : 꿀팁 게시판
-- SHARE    : 애완용품 나눔 게시판
-- FREE     : 자유게시판
--
-- pet_type 값
-- ALL : 전체
-- DOG : 강아지
-- CAT : 고양이

INSERT INTO question
(title, content, member_id, category, pet_type, image_url, view_count, created_at, updated_at)
VALUES
('강아지가 밥을 잘 안 먹어요',
 '최근에 사료를 잘 안 먹는데 어떻게 해야 할까요? 너무 걱정됩니다.',
 3, 'QUESTION', 'DOG', NULL, 12, NOW(), NULL),

('고양이 모래 냄새 줄이는 방법 공유합니다',
 '화장실 위치를 통풍이 잘 되는 곳으로 옮기고 모래를 주기적으로 교체하니 냄새가 많이 줄었습니다.',
 2, 'TIP', 'CAT', NULL, 25, NOW(), NULL),

('강아지 장난감 나눔합니다',
 '사용감은 조금 있지만 충분히 깨끗하게 사용 가능합니다. 필요하신 분 댓글 주세요.',
 1, 'SHARE', 'DOG', '/images/share-dog-toy.jpg', 18, NOW(), NULL),

('고양이 분양합니다',
 '러시안블루 고양이 11개월, 중성화 X. 책임감 있게 키워주실 분을 찾습니다.',
 2, 'ADOPTION', 'CAT', '/images/adoption-cat.jpg', 30, NOW(), NULL),

('반려동물 키우는 분들 자유롭게 소통해요',
 '강아지나 고양이 키우면서 생긴 일상 이야기 자유롭게 나눠요.',
 1, 'FREE', 'ALL', NULL, 7, NOW(), NULL),

('산책할 때 배변봉투 꼭 챙기세요',
 '펫티켓을 지키기 위해 산책 시 배변봉투와 물티슈를 챙기는 습관이 중요합니다.',
 1, 'TIP', 'DOG', NULL, 21, NOW(), NULL),

('강아지가 산책 중에 계속 짖어요',
 '다른 강아지를 보면 계속 짖는데 훈련 방법이 있을까요?',
 3, 'QUESTION', 'DOG', NULL, 9, NOW(), NULL);


-- =========================================================
-- 3. [답변 샘플 데이터]
-- =========================================================
-- question_id는 위에서 삽입된 게시글 순서 기준

INSERT INTO answer
(content, question_id, member_id, created_at, updated_at)
VALUES
('사료를 갑자기 바꾸면 거부할 수 있어요. 기존 사료와 새 사료를 조금씩 섞어보세요.',
 1, 1, NOW(), NULL),

('식욕 저하가 오래 지속되면 건강 문제일 수 있으니 병원 방문도 고려해보세요.',
 1, 2, NOW(), NULL),

('다른 강아지를 볼 때 간식으로 시선을 돌리는 훈련부터 해보는 게 좋아요.',
 7, 1, NOW(), NULL),

('짖는 상황을 기록해두면 원인 파악에 도움이 됩니다.',
 7, 2, NOW(), NULL);


-- =========================================================
-- 4. [신고 샘플 데이터]
-- =========================================================
-- reporter_id : 신고한 회원
-- question_id : 신고된 게시글
-- answer_id   : 신고된 답변
--
-- status 값
-- PENDING  : 처리 대기
-- DELETED  : 삭제 처리
-- REJECTED : 신고 기각

INSERT INTO report
(reporter_id, question_id, answer_id, reason, status, created_at)
VALUES
(1, 4, NULL, '사기 분양 게시글로 의심됩니다.', 'PENDING', NOW()),
(2, 3, NULL, '부적절한 표현이 포함되어 있습니다.', 'PENDING', NOW()),
(3, NULL, 2, '개인정보가 포함된 답변입니다.', 'PENDING', NOW());