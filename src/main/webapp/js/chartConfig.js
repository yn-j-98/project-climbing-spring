
//member chart 생성 정보
export const member_config = {
        labels: member_Chart_Data_title,
        datasets: [{
            label: '사용자',
            data: member_Chart_Data_text,
            fill: false,
            borderColor: 'rgb(244,16,16)',
            tension: 0.1,
            maintainAspectRatio: true,
            responsive: false,
        }]
    };

    export const reservation_config = {
        labels: registration_Chart_Data_title,
        datasets: [{
            label: '예약자',
            data: registration_Chart_Data_text,
            backgroundColor: [
                'rgb(255, 99, 132)'
            ],
            maintainAspectRatio: true,
            responsive: false,
        }]
    };
    export const gym_config = {
        labels: gym_Chart_Data_title,
        datasets: [{
            label: '암벽장',
            data: gym_Chart_Data_text,
            backgroundColor: [
                'rgb(255, 99, 132)',
                'rgb(54, 162, 235)',
                'rgb(35,71,198)',
                'rgb(101,170,33)'
            ],
            cutout : '50%',
            maintainAspectRatio: true,
            responsive: false,

        }]
    };