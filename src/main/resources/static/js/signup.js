// 중복확인 완료 여부 저장
let isUsernameChecked = false;
let isNicknameChecked = false;
let isEmailChecked = false;


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

    if (combinationCount >= 2) {
        combinationRule.className = "text-success mb-1";
        combinationRule.innerText = "✓ 영문/숫자/특수문자 중 2가지 이상 포함";
    } else {
        combinationRule.className = "text-danger mb-1";
        combinationRule.innerText = "✗ 영문/숫자/특수문자 중 2가지 이상 포함";
    }

    if (noSpacePassword.length >= 8 && noSpacePassword.length <= 16) {
        lengthRule.className = "text-success mb-1";
        lengthRule.innerText = "✓ 8자 이상 16자 이하 입력 (공백 제외)";
    } else {
        lengthRule.className = "text-danger mb-1";
        lengthRule.innerText = "✗ 8자 이상 16자 이하 입력 (공백 제외)";
    }

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


// 비밀번호 확인 검사
function checkPasswordMatch() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const result = document.getElementById("passwordCheckResult");

    if (confirmPassword.length === 0) {
        result.innerText = "";
        return;
    }

    if (password === confirmPassword) {
        result.innerText = "비밀번호가 일치합니다.";
        result.className = "small mt-1 text-success";
    } else {
        result.innerText = "비밀번호가 일치하지 않습니다.";
        result.className = "small mt-1 text-danger";
    }
}


// 아이디 값 변경 시 중복확인 다시 필요
function resetUsernameCheck() {
    isUsernameChecked = false;
    document.getElementById("usernameCheckResult").innerText = "";
}


// 닉네임 값 변경 시 중복확인 다시 필요
function resetNicknameCheck() {
    isNicknameChecked = false;
    document.getElementById("nicknameCheckResult").innerText = "";
}


// 이메일 값 변경 시 중복확인 다시 필요
function resetEmailCheck() {
    isEmailChecked = false;
    document.getElementById("emailCheckResult").innerText = "";
}


// 닉네임 형식 검사
function validateNickname() {
    const nickname = document.getElementById("nickname").value;
    const ruleText = document.getElementById("nicknameRuleText");
    const checkResult = document.getElementById("nicknameCheckResult");

    checkResult.innerText = "";

    if (nickname.length === 0) {
        ruleText.innerText = "";
        return false;
    }

    if (nickname.length >= 2 && nickname.length <= 20) {
        ruleText.innerText = "";
        return true;
    }

    ruleText.innerText = "닉네임은 2자 이상 20자 이하로 입력해주세요.";
    ruleText.className = "small mt-1 text-danger";
    return false;
}


// 이메일 아이디 형식 검사
function validateEmailId() {
    const emailId = document.getElementById("emailId").value;
    const domain = document.getElementById("emailDomain").value;
    const result = document.getElementById("emailIdResult");
    const checkResult = document.getElementById("emailCheckResult");

    checkResult.innerText = "";

    const regex = /^[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?$/;

    if (emailId.length === 0) {
        result.innerText = "";
        return false;
    }

    if (!regex.test(emailId)) {
        result.innerText = "이메일 아이디는 영문, 숫자, 점(.), 언더바(_), 하이픈(-)만 입력 가능합니다.";
        result.className = "small mt-1 text-danger";
        return false;
    }

    if (!domain) {
        result.innerText = "";
        return false;
    }

    result.innerText = "";
    return true;
}


// 이메일 아이디 + 도메인 조합
function makeEmail() {
    const emailId = document.getElementById("emailId").value;
    const emailDomain = document.getElementById("emailDomain").value;
    const email = document.getElementById("email");

    if (emailId && emailDomain) {
        email.value = emailId + "@" + emailDomain;
    } else {
        email.value = "";
    }
}


// 아이디 중복확인
function checkUsername() {
    const username = document.getElementById("username").value;
    const result = document.getElementById("usernameCheckResult");

    if (!username) {
        result.innerText = "아이디를 입력해주세요.";
        result.style.color = "red";
        isUsernameChecked = false;
        return;
    }

    fetch(`/check-username?username=${encodeURIComponent(username)}`)
        .then(response => response.text())
        .then(data => {
            data = data.trim();

            if (data === "available") {
                result.innerText = "사용 가능한 아이디입니다.";
                result.style.color = "green";
                isUsernameChecked = true;
            } else if (data === "duplicate") {
                result.innerText = "이미 사용 중인 아이디입니다.";
                result.style.color = "red";
                isUsernameChecked = false;
            } else {
                result.innerText = "중복확인 요청에 문제가 있습니다.";
                result.style.color = "red";
                isUsernameChecked = false;
            }
        })
        .catch(() => {
            result.innerText = "서버 요청 중 오류가 발생했습니다.";
            result.style.color = "red";
            isUsernameChecked = false;
        });
}


// 닉네임 중복확인
function checkNickname() {
    const nickname = document.getElementById("nickname").value;
    const result = document.getElementById("nicknameCheckResult");

    if (!validateNickname()) {
        result.innerText = "";
        isNicknameChecked = false;
        return;
    }

    fetch(`/check-nickname?nickname=${encodeURIComponent(nickname)}`)
        .then(response => response.text())
        .then(data => {
            data = data.trim();

            if (data === "available") {
                result.innerText = "사용 가능한 닉네임입니다.";
                result.style.color = "green";
                isNicknameChecked = true;
            } else if (data === "duplicate") {
                result.innerText = "이미 사용 중인 닉네임입니다.";
                result.style.color = "red";
                isNicknameChecked = false;
            } else {
                result.innerText = "중복확인 요청에 문제가 있습니다.";
                result.style.color = "red";
                isNicknameChecked = false;
            }
        })
        .catch(() => {
            result.innerText = "서버 요청 중 오류가 발생했습니다.";
            result.style.color = "red";
            isNicknameChecked = false;
        });
}


// 이메일 중복확인
function checkEmail() {
    makeEmail();

    const email = document.getElementById("email").value;
    const result = document.getElementById("emailCheckResult");

    if (!validateEmailId()) {
        result.innerText = "";
        isEmailChecked = false;
        return;
    }

    fetch(`/check-email?email=${encodeURIComponent(email)}`)
        .then(response => response.text())
        .then(data => {
            data = data.trim();

            if (data === "available") {
                result.innerText = "사용 가능한 이메일입니다.";
                result.style.color = "green";
                isEmailChecked = true;
            } else if (data === "duplicate") {
                result.innerText = "이미 사용 중인 이메일입니다.";
                result.style.color = "red";
                isEmailChecked = false;
            } else {
                result.innerText = "중복확인 요청에 문제가 있습니다.";
                result.style.color = "red";
                isEmailChecked = false;
            }
        })
        .catch(() => {
            result.innerText = "서버 요청 중 오류가 발생했습니다.";
            result.style.color = "red";
            isEmailChecked = false;
        });
}


// 회원가입 버튼 클릭 시 최종 검사
function validateSignupForm() {
    makeEmail();

    if (!isUsernameChecked) {
        alert("아이디 중복확인을 해주세요.");
        return false;
    }

    if (!isNicknameChecked) {
        alert("닉네임 중복확인을 해주세요.");
        return false;
    }

    if (!isEmailChecked) {
        alert("이메일 중복확인을 해주세요.");
        return false;
    }

    return true;
}