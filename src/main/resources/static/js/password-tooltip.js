document.addEventListener('DOMContentLoaded', function() {
    const tooltipIcons = document.querySelectorAll('.tooltip-icon');

    tooltipIcons.forEach(icon => {
        const targetId = icon.dataset.tooltipTarget;
        const tooltipContent = document.getElementById(targetId);

        if (tooltipContent) {
            icon.addEventListener('mouseenter', () => {
                tooltipContent.classList.add('active'); // 'active' 클래스 추가하여 보이게 함
            });

            icon.addEventListener('mouseleave', () => {
                tooltipContent.classList.remove('active');
            });
        }
    });
});