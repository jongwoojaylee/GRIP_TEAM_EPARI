async function fetchCongestionData(gymId) {
    try {
        const response = await fetch(`/presentCount/${gymId}`);
        if (!response.ok) {
            throw new Error('응답실패');
        }
        return await response.json();
    } catch (error) {
        console.error('에러:', error);
    }
}

async function makeChart() {
    const gymId = 2; // 클라이밍짐 선택할때마다 다르게 들어오도록 해야함
    const data = await fetchCongestionData(gymId);

    const timeZone = [];
    const presentCount = [];

    data.congestion.forEach(entry => {
        const time = Object.keys(entry)[0];
        timeZone.push(time);
        presentCount.push(entry[time]);
    });

    const ctx = document.getElementById('congestionChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: timeZone,
            datasets: [{
                label: 'Present Count',
                data: presentCount,
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 2,
                fill: false
            }]
        },
        options: {
            scales: {
                x: { beginAtZero: true },
                y: { beginAtZero: true }
            }
        }
    });
}

makeChart();
