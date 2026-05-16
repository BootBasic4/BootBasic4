// 중복확인 여부 저장
let isUsernameChecked = false;
let isNicknameChecked = false;
let isEmailChecked = false;


// 메시지 출력
function setMessage(element, message, className = "") {
    element.innerText = message;
    element.className = className;
}


// 메시지 초기화
function clearMessage(element) {
    element.innerText = "";
}


// 중복확인 공통 fetch 처리
function handleDuplicateCheck(url, resultElement, successCallback, failCallback) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            data = data.trim();

            if (data === "available") {
                resultElement.innerText = "사용 가능합니다.";
                resultElement.style.color = "green";
                successCallback();

            } else if (data === "duplicate") {
                resultElement.innerText = "이미 사용 중입니다.";
                resultElement.style.color = "red";
                failCallback();

            } else {
                resultElement.innerText = "중복확인 요청에 문제가 있습니다.";
                resultElement.style.color = "red";
                failCallback();
            }
        })
        .catch(() => {
            resultElement.innerText = "서버 요청 중 오류가 발생했습니다.";
            resultElement.style.color = "red";
            failCallback();
        });
}


// 비밀번호 보기 / 숨기기
function togglePassword(inputId, button) {
    const input = document.getElementById(inputId);
    const icon = button.querySelector("i");

    if (input.type === "password") {
        input.type = "text";
        icon.className = "bi bi-eye";

    } else {
        input.type = "password";
        icon.className = "bi bi-eye-slash";
    }
}


// 비밀번호 조건 박스 표시
function showPasswordRuleBox() {
    document.getElementById("passwordRuleBox").style.display = "block";
}


// 비밀번호 조건 검사
function validatePassword() {

    const password = document.getElementById("password").value;

    const combinationRule = document.getElementById("rule-combination");
    const lengthRule = document.getElementById("rule-length");
    const repeatRule = document.getElementById("rule-repeat");

    const hasEnglish = /[A-Za-z]/.test(password);
    const hasNumber = /\d/.test(password);
    const hasSpecial = /[@$!%*#?&]/.test(password);

    let combinationCount = 0;

    if (hasEnglish) combinationCount++;
    if (hasNumber) combinationCount++;
    if (hasSpecial) combinationCount++;

    const noSpacePassword = password.replace(/\s/g, "");

    // 조합 검사
    if (combinationCount >= 2) {
        combinationRule.className = "text-success mb-1";
        combinationRule.innerText = "✓ 영문/숫자/특수문자 중 2가지 이상 포함";

    } else {
        combinationRule.className = "text-danger mb-1";
        combinationRule.innerText = "✗ 영문/숫자/특수문자 중 2가지 이상 포함";
    }

    // 길이 검사
    if (noSpacePassword.length >= 8 && noSpacePassword.length <= 16) {
        lengthRule.className = "text-success mb-1";
        lengthRule.innerText = "✓ 8자 이상 16자 이하 입력 (공백 제외)";

    } else {
        lengthRule.className = "text-danger mb-1";
        lengthRule.innerText = "✗ 8자 이상 16자 이하 입력 (공백 제외)";
    }

    // 반복 문자 검사
    if (password.length === 0) {
        repeatRule.className = "text-danger";
        repeatRule.innerText = "✗ 연속 3자 이상 동일한 문자/숫자 제외";

    } else if (!/(.)\1\1/.test(password)) {
        repeatRule.className = "text-success";
        repeatRule.innerText = "✓ 연속 3자 이상 동일한 문자/숫자 제외";

    } else {
        repeatRule.className = "text-danger";
        repeatRule.innerText = "✗ 연속 3자 이상 동일한 문자/숫자 제외";
    }

    checkPasswordMatch();
}


// 비밀번호 일치 검사
function checkPasswordMatch() {

    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const result = document.getElementById("passwordCheckResult");

    if (confirmPassword.length === 0) {
        clearMessage(result);
        return;
    }

    if (password === confirmPassword) {
        setMessage(result, "비밀번호가 일치합니다.", "small mt-1 text-success");

    } else {
        setMessage(result, "비밀번호가 일치하지 않습니다.", "small mt-1 text-danger");
    }
}


// 아이디 변경 시 중복확인 초기화
function resetUsernameCheck() {
    isUsernameChecked = false;
    clearMessage(document.getElementById("usernameCheckResult"));
}


// 닉네임 변경 시 중복확인 초기화
function resetNicknameCheck() {
    isNicknameChecked = false;
    clearMessage(document.getElementById("nicknameCheckResult"));
}


// 이메일 변경 시 중복확인 초기화
function resetEmailCheck() {
    isEmailChecked = false;
    clearMessage(document.getElementById("emailCheckResult"));
}


// 아이디 형식 검사
function validateUsername() {

    const username = document.getElementById("username").value;

    const ruleText = document.getElementById("usernameRuleText");
    const checkResult = document.getElementById("usernameCheckResult");

    clearMessage(checkResult);

    // 영어, 숫자, . _ - 허용
    const regex = /^[a-zA-Z0-9._-]+$/;

    if (username.length === 0) {
        clearMessage(ruleText);
        return false;
    }

    // 허용 문자 검사
    if (!regex.test(username)) {
        setMessage(
            ruleText,
            "영어, 숫자, 특수기호(., _, -)만 사용 가능합니다.",
            "small mt-1 text-danger"
        );

        isUsernameChecked = false;
        return false;
    }

    // 길이 검사
    if (username.length < 5 || username.length > 20) {

        setMessage(
            ruleText,
            "5~20글자 이내만 사용 가능합니다.",
            "small mt-1 text-danger"
        );

        isUsernameChecked = false;
        return false;
    }

    clearMessage(ruleText);
    return true;
}


// 닉네임 형식 검사
function validateNickname() {

    const nickname = document.getElementById("nickname").value;

    const ruleText = document.getElementById("nicknameRuleText");
    const checkResult = document.getElementById("nicknameCheckResult");

    clearMessage(checkResult);

    // 한글, 영어, 숫자, _ 허용
    const regex = /^[가-힣a-zA-Z0-9_]+$/;

    if (nickname.length === 0) {
        clearMessage(ruleText);
        return false;
    }

    // 허용 문자 검사
    if (!regex.test(nickname)) {

        setMessage(
            ruleText,
            "한글, 영어, 숫자, _(언더바)만 사용 가능합니다.",
            "small mt-1 text-danger"
        );

        isNicknameChecked = false;
        return false;
    }

    // 길이 검사
    if (nickname.length < 2 || nickname.length > 20) {

        setMessage(
            ruleText,
            "2~20글자 이내만 사용 가능합니다.",
            "small mt-1 text-danger"
        );

        isNicknameChecked = false;
        return false;
    }

    clearMessage(ruleText);
    return true;
}


// 이메일 아이디 형식 검사
function validateEmailId() {

    const emailId = document.getElementById("emailId").value;
    const domain = document.getElementById("emailDomain").value;

    const result = document.getElementById("emailIdResult");
    const checkResult = document.getElementById("emailCheckResult");

    clearMessage(checkResult);

    const regex = /^[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?$/;

    if (emailId.length === 0) {
        clearMessage(result);
        return false;
    }

    // 이메일 아이디 형식 검사
    if (!regex.test(emailId)) {

        setMessage(
            result,
            "영문, 숫자, 점(.), 언더바(_), 하이픈(-)만 입력 가능합니다.",
            "small mt-1 text-danger"
        );

        return false;
    }

    // 도메인 선택 여부 검사
    if (!domain) {
        clearMessage(result);
        return false;
    }

    clearMessage(result);
    return true;
}


// 이메일 조합
function makeEmail() {

    const emailId = document.getElementById("emailId").value;
    const emailDomain = document.getElementById("emailDomain").value;

    const email = document.getElementById("email");

    if (emailId && emailDomain) {
        email.value = `${emailId}@${emailDomain}`;

    } else {
        email.value = "";
    }
}


// 아이디 중복확인
function checkUsername() {

    const username = document.getElementById("username").value;
    const result = document.getElementById("usernameCheckResult");

    if (!validateUsername()) return;

    handleDuplicateCheck(
        `/check-username?username=${encodeURIComponent(username)}`,
        result,

        () => isUsernameChecked = true,

        () => isUsernameChecked = false
    );
}


// 닉네임 중복확인
function checkNickname() {

    const nickname = document.getElementById("nickname").value;
    const result = document.getElementById("nicknameCheckResult");

    if (!validateNickname()) return;

    handleDuplicateCheck(
        `/check-nickname?nickname=${encodeURIComponent(nickname)}`,
        result,

        () => isNicknameChecked = true,

        () => isNicknameChecked = false
    );
}


// 이메일 중복확인
function checkEmail() {

    makeEmail();

    const email = document.getElementById("email").value;
    const result = document.getElementById("emailCheckResult");

    if (!validateEmailId()) {

        clearMessage(result);
        isEmailChecked = false;
        return;
    }

    handleDuplicateCheck(
        `/check-email?email=${encodeURIComponent(email)}`,
        result,

        () => isEmailChecked = true,

        () => isEmailChecked = false
    );
}


// 회원가입 최종 검사
function validateSignupForm() {

    makeEmail();

    // 아이디 중복확인 여부 검사
    if (!isUsernameChecked) {
        alert("아이디 중복확인을 해주세요.");
        return false;
    }

    // 닉네임 중복확인 여부 검사
    if (!isNicknameChecked) {
        alert("닉네임 중복확인을 해주세요.");
        return false;
    }

    // 이메일 중복확인 여부 검사
    if (!isEmailChecked) {
        alert("이메일 중복확인을 해주세요.");
        return false;
    }

    return true;
}