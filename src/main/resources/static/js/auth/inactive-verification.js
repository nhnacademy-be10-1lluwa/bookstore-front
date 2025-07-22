const contactInput = document.getElementById('contact');
const sendBtn = document.getElementById('sendCodeBtn');
const timer = document.getElementById('timer');
const countdown = document.getElementById('countdown');
const codeGroup = document.getElementById('codeGroup');
const errorMessage = document.getElementById('errorMessage');

const baseUrl = "";
const SEND_API   = "/verifications/inactive/code";
const VERIFY_API = "/verifications/inactive/verify";

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
            errorMessage.textContent = "인증 시간이 만료되었어요. 다시 시도해주세요.";
            errorMessage.classList.remove('hidden');
            sendBtn.disabled = false;
            contactInput.readOnly = false;
        }
    }, 1000);
}

function updateTimer() {
    const minutes = String(Math.floor(timeLeft / 60)).padStart(2, '0');
    const seconds = String(timeLeft % 60).padStart(2, '0');
    countdown.textContent = `${minutes}:${seconds}`;
}

sendBtn.addEventListener('click', async () => {
    const contactInput = document.getElementById('contact');
    const contact = contactInput.value.trim();

    if (!contact) {
        alert("전화번호를 입력해주세요!");
        return;
    }

    if (!/^01[0|1|6|7|8|9]-?\d{3,4}-?\d{4}$/.test(contact)) {
        alert("'-'을 포함한 올바른 전화번호 형식을 입력해주세요!");
        return;
    }

    sendBtn.disabled = true;

    try {
        const response = await fetch(baseUrl + SEND_API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ contact })
        });
        const isSuccess = await response.json();

        if (response.ok && isSuccess === true) {
            contactInput.readOnly = true;
            startTimer();
            codeGroup.classList.remove('hidden');
            errorMessage.classList.add('hidden');
        } else {
            errorMessage.textContent = "인증번호 발송에 실패했습니다.";
            errorMessage.classList.remove('hidden');

        }
    } catch (error) {
        console.error("fetch error:", error);
        errorMessage.textContent = " 전화번호가 일치하지 않습니다 😢";
        errorMessage.classList.remove('hidden');
        sendBtn.disabled = false;
    }
});

document.getElementById('verifyBtn').addEventListener('click', async () => {
    const contact = document.getElementById('contact').value.trim();
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
            body: JSON.stringify({ contact, code })
        });

        const isVerified = await response.json();

        if (response.ok && isVerified === true) {
            errorMessage.classList.add('hidden');
            alert("인증 성공!🎉 로그인 페이지로 이동합니다");
            location.href = '/auth/login';
        } else {
            errorMessage.textContent = "인증번호를 다시 확인해주세요.";
            errorMessage.classList.remove('hidden');
        }
    } catch (error) {
        errorMessage.textContent = "서버와의 통신에 실패했어요 😢";
        errorMessage.classList.remove('hidden');
    }
});