// 닉네임 중복확인 여부
let isMypageNicknameChecked = false;

// 이메일 중복확인 여부
let isMypageEmailChecked = false;

// 닉네임 입력 시 중복확인 초기화
function resetMypageNicknameCheck() {

    isMypageNicknameChecked = false;

    const result = document.getElementById("mypageNicknameResult");

    if (result) {
        result.innerText = "";
        result.className = "small mt-2";
    }
}


// 닉네임 형식 검사
function validateMypageNickname() {

    const nickname = document.getElementById("mypageNickname").value.trim();
    const result = document.getElementById("mypageNicknameResult");

    if (nickname.length === 0) {

        result.innerText = "닉네임을 입력해주세요.";
        result.className = "small mt-2 text-danger";

        return false;
    }

    if (nickname.length < 2 || nickname.length > 20) {

        result.innerText = "닉네임은 2자 이상 20자 이하로 입력해주세요.";
        result.className = "small mt-2 text-danger";

        return false;
    }

    result.innerText = "";
    result.className = "small mt-2";

    return true;
}


// 닉네임 중복확인
function checkMypageNickname() {

    const nickname = document.getElementById("mypageNickname").value.trim();
    const result = document.getElementById("mypageNicknameResult");

    if (!validateMypageNickname()) {

        isMypageNicknameChecked = false;
        return;
    }

    fetch(`/check-nickname?nickname=${encodeURIComponent(nickname)}`)

        .then(response => response.text())

        .then(data => {

            data = data.trim();

            if (data === "available") {

                result.innerText = "사용 가능한 닉네임입니다.";
                result.className = "small mt-2 text-success";

                isMypageNicknameChecked = true;

            } else if (data === "duplicate") {

                result.innerText = "이미 사용 중인 닉네임입니다.";
                result.className = "small mt-2 text-danger";

                isMypageNicknameChecked = false;

            } else {

                result.innerText = "중복확인 요청에 문제가 있습니다.";
                result.className = "small mt-2 text-danger";

                isMypageNicknameChecked = false;
            }
        })

        .catch(() => {

            result.innerText = "서버 요청 중 오류가 발생했습니다.";
            result.className = "small mt-2 text-danger";

            isMypageNicknameChecked = false;
        });
}


// 닉네임 형식 검사
function validateMypageNickname() {

    const nickname = document.getElementById("mypageNickname").value.trim();

    const result = document.getElementById("mypageNicknameResult");

    // 한글, 영어, 숫자, _ 허용
    const regex = /^[가-힣a-zA-Z0-9_]+$/;

    // 입력 여부 검사
    if (nickname.length === 0) {

        result.innerText = "";
        result.className = "small mt-2";

        return false;
    }

    // 허용 문자 검사
    if (!regex.test(nickname)) {

        result.innerText =
            "한글, 영어, 숫자, _(언더바)만 사용 가능합니다.";

        result.className = "small mt-2 text-danger";

        isMypageNicknameChecked = false;

        return false;
    }

    // 길이 검사
    if (nickname.length < 2 || nickname.length > 20) {

        result.innerText =
            "2~20글자 이내만 사용 가능합니다.";

        result.className = "small mt-2 text-danger";

        isMypageNicknameChecked = false;

        return false;
    }

    // 성공 시 메시지 초기화
    result.innerText = "";
    result.className = "small mt-2";

    return true;
}


// 이메일 입력 시 중복확인 초기화
function resetMypageEmailCheck() {

    isMypageEmailChecked = false;

    const result = document.getElementById("mypageEmailResult");

    if (result) {

        result.innerText = "";
        result.className = "small mt-2";
    }
}


// 이메일 형식 검사
function validateMypageEmail() {

    const email = document.getElementById("mypageEmail").value.trim();
    const result = document.getElementById("mypageEmailResult");

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (email.length === 0) {

        result.innerText = "이메일을 입력해주세요.";
        result.className = "small mt-2 text-danger";

        return false;
    }

    if (!emailRegex.test(email)) {

        result.innerText = "올바른 이메일 형식으로 입력해주세요.";
        result.className = "small mt-2 text-danger";

        return false;
    }

    result.innerText = "";
    result.className = "small mt-2";

    return true;
}


// 이메일 중복확인
function checkMypageEmail() {

    const email = document.getElementById("mypageEmail").value.trim();
    const result = document.getElementById("mypageEmailResult");

    if (!validateMypageEmail()) {

        isMypageEmailChecked = false;
        return;
    }

    fetch(`/check-email?email=${encodeURIComponent(email)}`)

        .then(response => response.text())

        .then(data => {

            data = data.trim();

            if (data === "available") {

                result.innerText = "사용 가능한 이메일입니다.";
                result.className = "small mt-2 text-success";

                isMypageEmailChecked = true;

            } else if (data === "duplicate") {

                result.innerText = "이미 사용 중인 이메일입니다.";
                result.className = "small mt-2 text-danger";

                isMypageEmailChecked = false;

            } else {

                result.innerText = "중복확인 요청에 문제가 있습니다.";
                result.className = "small mt-2 text-danger";

                isMypageEmailChecked = false;
            }
        })

        .catch(() => {

            result.innerText = "서버 요청 중 오류가 발생했습니다.";
            result.className = "small mt-2 text-danger";

            isMypageEmailChecked = false;
        });
}


// 이메일 수정 최종 검사
function validateEmailUpdateForm() {

    if (!validateMypageEmail()) {
        return false;
    }

    if (!isMypageEmailChecked) {

        alert("이메일 중복확인을 해주세요.");
        return false;
    }

    return true;
}


// 비밀번호 조건 박스 표시
function showPasswordRuleBox() {

    const ruleBox = document.getElementById("passwordRuleBox");

    if (ruleBox) {
        ruleBox.style.display = "block";
    }
}


// 새 비밀번호 조건 검사
function validateMypagePassword() {

    const password = document.getElementById("newPassword").value;

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

    const isCombinationValid = combinationCount >= 2;
    const isLengthValid = noSpacePassword.length >= 8 && noSpacePassword.length <= 16;
    const isRepeatValid = password.length > 0 && !/(.)\1\1/.test(password);

    // 조합 조건 검사
    if (isCombinationValid) {

        combinationRule.className = "text-success mb-1";
        combinationRule.innerText = "✓ 영문/숫자/특수문자 중 2가지 이상 포함";

    } else {

        combinationRule.className = "text-danger mb-1";
        combinationRule.innerText = "✗ 영문/숫자/특수문자 중 2가지 이상 포함";
    }

    // 길이 조건 검사
    if (isLengthValid) {

        lengthRule.className = "text-success mb-1";
        lengthRule.innerText = "✓ 8자 이상 16자 이하 입력";

    } else {

        lengthRule.className = "text-danger mb-1";
        lengthRule.innerText = "✗ 8자 이상 16자 이하 입력";
    }

    // 반복 문자 검사
    if (isRepeatValid) {

        repeatRule.className = "text-success";
        repeatRule.innerText = "✓ 연속 3자 이상 동일한 문자/숫자 제외";

    } else {

        repeatRule.className = "text-danger";
        repeatRule.innerText = "✗ 연속 3자 이상 동일한 문자/숫자 제외";
    }

    checkMypagePasswordMatch();

    return isCombinationValid && isLengthValid && isRepeatValid;
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

// 새 비밀번호 일치 검사
function checkMypagePasswordMatch() {

    const password = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmNewPassword").value;

    const result = document.getElementById("currentPasswordResult");
    const successResult = document.getElementById("passwordModalResult");

    if (confirmPassword.length === 0) {

        result.innerText = "";
        result.className = "small text-danger mt-1";

        return false;
    }

    if (password === confirmPassword) {

        result.innerText = "비밀번호가 일치합니다.";
        result.className = "small text-success mt-1";

        return true;
    }

    result.innerText = "비밀번호가 일치하지 않습니다.";
    result.className = "small text-danger mt-1";

    return false;
}


// 비밀번호 변경 최종 검사
function validatePasswordModal() {

    const currentPassword =
        document.querySelector("input[name='currentPassword']").value;

    const currentPasswordResult = document.getElementById("currentPasswordResult");
    const passwordModalResult = document.getElementById("passwordModalResult");

    // 현재 비밀번호 입력 여부
    if (currentPassword.trim().length === 0) {

        result.innerText = "현재 비밀번호를 입력해주세요.";
        result.className = "small text-danger mt-1";

        return false;
    }

    // 새 비밀번호 조건 검사
    if (!validateMypagePassword()) {

        result.innerText = "새 비밀번호 조건을 확인해주세요.";
        result.className = "small text-danger mt-1";

        return false;
    }

    // 새 비밀번호 일치 검사
    if (!checkMypagePasswordMatch()) {

        result.innerText = "새 비밀번호 확인이 일치하지 않습니다.";
        result.className = "small text-danger mt-1";

        return false;
    }

    // form 가져오기
    const form = document.getElementById("passwordForm");

    // formData 생성
    const formData = new FormData(form);

    // fetch 요청
    fetch("/mypage/password", {
        method: "POST",
        body: formData
    })

        .then(async response => {

            const message = await response.text();

            if (response.ok) {

                currentPasswordResult.innerText = "";
                passwordModalResult.innerText = "비밀번호가 변경되었습니다.";
                passwordModalResult.className = "small text-success mt-1";

            } else {

                currentPasswordResult.innerText = message;
                currentPasswordResult.className = "small text-danger mt-1";

                passwordModalResult.innerText = "";
            }
        });

    // 기본 submit 막기
    return false;
}