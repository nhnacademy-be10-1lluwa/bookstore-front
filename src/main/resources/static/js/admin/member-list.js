    function confirmPointBonus() {
    const grade = /*[[${selectedGrade}]]*/ '선택등급';
    const point = document.getElementById("point").value;

    if (!point || point <= 0) {
    alert("포인트를 올바르게 입력해주세요!");
    return false;
}

    return confirm(`'${grade}' 등급 회원들에게 ${point} 포인트를 적립하시겠습니까?`);
}

    // 드롭다운 선택 시 hidden input에 반영
    document.addEventListener("DOMContentLoaded", function () {
    const gradeSelect = document.querySelector('select[name="grade"]');
    const hiddenGradeInput = document.querySelector('#pointForm input[name="grade"]');

    if (gradeSelect && hiddenGradeInput) {
    hiddenGradeInput.value = gradeSelect.value;

    gradeSelect.addEventListener("change", function () {
    hiddenGradeInput.value = this.value;
});
}
});