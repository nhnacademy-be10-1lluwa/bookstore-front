const sendBtn = document.getElementById('sendCodeBtn');
const timer = document.getElementById('timer');
const countdown = document.getElementById('countdown');
const codeGroup = document.getElementById('codeGroup');
const errorMessage = document.getElementById('errorMessage');
const serverEmailInput = document.getElementById('serverEmail');

const baseUrl = "";
const SEND_API   = "/proxy/members/inactive/verification";
const VERIFY_API = "/proxy/members/inactive/verification/verify";

let timeLeft = 180;
let timerInterval;

function startTimer() {
    clearInterval(timerInterval);
    timeLeft = 180;
    timer.classList.remove('hidden');
    updateTimer();

    timerInterval = setInterval(() => {
        timeLeft--;
        updateTimer();
        if (timeLeft <= 0) {
            clearInterval(timerInterval);
            countdown.textContent = "시간 초과";
            // 버튼 재활성화 등 안내 추가
            sendBtn.disabled = false;
        }
    }, 1000);
}

function updateTimer() {
    const minutes = String(Math.floor(timeLeft / 60)).padStart(2, '0');
    const seconds = String(timeLeft % 60).padStart(2, '0');
    countdown.textContent = `${minutes}:${seconds}`;
}

sendBtn.addEventListener('click', async () => {
    const emailInput = document.getElementById('email');
    const email = emailInput.value.trim();
    const serverEmail = serverEmailInput.value;

    if (!email) {
        alert("이메일을 입력해주세요!");
        return;
    }

    // 서버에서 전달받은 이메일과 입력한 이메일 비교
    if (serverEmail && email !== serverEmail) {
        errorMessage.textContent = "회원님의 이메일과 일치하지 않습니다.";
        errorMessage.classList.remove('hidden');
        return;
    }

    try {
        const response = await fetch(baseUrl + SEND_API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email })
        });
        const result = await response.json();

        if (response.ok && result.success) {
            startTimer();
            codeGroup.classList.remove('hidden');
            errorMessage.classList.add('hidden');
        } else {
            errorMessage.textContent = result.message || "인증번호 발송에 실패했습니다.";
            errorMessage.classList.remove('hidden');
        }
    } catch (error) {
        console.error("fetch error:", error);
        errorMessage.textContent = "서버와의 통신에 실패했어요 😢";
        errorMessage.classList.remove('hidden');
    }
});

document.getElementById('verifyBtn').addEventListener('click', async () => {
    const email = document.getElementById('email').value.trim();
    const code = document.getElementById('code').value.trim();

    if (!code) {
        alert("인증번호를 입력해주세요!");
        return;
    }

    try {
        const response = await fetch(baseUrl + VERIFY_API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, code })
        });

        const result = await response.json();

        if (response.ok && result.success) {
            errorMessage.classList.add('hidden');
            alert("인증 성공!🎉 로그인 페이지로 이동합니다");
            location.href = '/login';
        } else {
            errorMessage.textContent = result.message || "인증번호가 올바르지 않습니다.";
            errorMessage.classList.remove('hidden');
        }
    } catch (error) {
        errorMessage.textContent = "서버와의 통신에 실패했어요 😢";
        errorMessage.classList.remove('hidden');
    }
});