DROP SCHEMA IF NOT EXISTS mnwiki;
CREATE SCHEMA IF NOT EXISTS mnwiki;
USE mnwiki;
CREATE TABLE member (
                        member_id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '회원 PK',
                        username    VARCHAR(50)  NOT NULL COMMENT '아이디(로그인용)',
                        password    VARCHAR(255) NOT NULL COMMENT '비밀번호(암호화)',
                        nickname    VARCHAR(50)  NOT NULL COMMENT '닉네임',
                        email       VARCHAR(100) NOT NULL COMMENT '이메일',
                        role        VARCHAR(20)  NOT NULL COMMENT '권한(ROLE_USER, ROLE_ADMIN)',
                        pet_type    VARCHAR(50)  NULL     COMMENT '반려동물 종류(강아지, 고양이 등)',
                        pet_started YEAR         NULL     COMMENT '반려동물 키우기 시작한 연도',
                        created_at  DATETIME     NOT NULL COMMENT '가입일시',
                        PRIMARY KEY (member_id),
                        UNIQUE KEY uq_member_username (username),
                        UNIQUE KEY uq_member_nickname (nickname),
                        UNIQUE KEY uq_member_email    (email)
);

CREATE TABLE question (
                          question_id BIGINT       NOT NULL AUTO_INCREMENT COMMENT '질문 PK',
                          title       VARCHAR(200) NOT NULL COMMENT '제목',
                          content     TEXT         NOT NULL COMMENT '내용',
                          member_id   BIGINT       NOT NULL COMMENT '작성자(FK)',
                          category    VARCHAR(20)  NOT NULL DEFAULT 'QUESTION' COMMENT '게시판 구분(QUESTION, TIP, FREE 등)',
                          pet_type     VARCHAR(20)  NOT NULL DEFAULT 'ALL' COMMENT '동물 타입',
                          image_url   VARCHAR(500) NULL     COMMENT '이미지 경로',
                          view_count  INT          NOT NULL DEFAULT 0 COMMENT '조회수',
                          created_at  DATETIME     NOT NULL COMMENT '작성일시',
                          updated_at  DATETIME     NULL     COMMENT '수정일시',
                          PRIMARY KEY (question_id),
                          CONSTRAINT fk_question_member FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE answer (
                        answer_id   BIGINT   NOT NULL AUTO_INCREMENT COMMENT '답변 PK',
                        content     TEXT     NOT NULL COMMENT '내용',
                        question_id BIGINT   NOT NULL COMMENT '질문(FK)',
                        member_id   BIGINT   NOT NULL COMMENT '작성자(FK)',
                        created_at  DATETIME NOT NULL COMMENT '작성일시',
                        updated_at  DATETIME NULL     COMMENT '수정일시',
                        PRIMARY KEY (answer_id),
                        CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES question (question_id),
                        CONSTRAINT fk_answer_member   FOREIGN KEY (member_id)   REFERENCES member   (member_id)
);

CREATE TABLE report (
                        report_id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '신고 PK',
                        reporter_id BIGINT       NOT NULL COMMENT '신고자(FK)',
                        question_id BIGINT       NULL     COMMENT '신고 대상 질문(FK, 질문 신고 시)',
                        answer_id   BIGINT       NULL     COMMENT '신고 대상 답변(FK, 답변 신고 시)',
                        reason      VARCHAR(200) NOT NULL COMMENT '신고 사유',
                        status      VARCHAR(20)  NOT NULL DEFAULT 'PENDING' COMMENT '처리 상태(PENDING, DELETED, REJECTED)',
                        created_at  DATETIME     NOT NULL COMMENT '신고일시',
                        PRIMARY KEY (report_id),
                        CONSTRAINT fk_report_reporter FOREIGN KEY (reporter_id) REFERENCES member   (member_id),
                        CONSTRAINT fk_report_question FOREIGN KEY (question_id) REFERENCES question (question_id),
                        CONSTRAINT fk_report_answer   FOREIGN KEY (answer_id)   REFERENCES answer   (answer_id)
);