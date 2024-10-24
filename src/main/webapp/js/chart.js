import {gym_config, member_config, reservation_config} from 'chartConfig';

$(document).ready(function () {
//chart 형식 적용
const member_chart = {
    type: 'line',
    data: member_config,
};
const reservation_chart = {
    type: 'bar',
    data: reservation_config,
};
const gym_chart = {
    type: 'doughnut',
    data: gym_config,
};


//chart 생성
    new Chart($('#member_chart'), member_chart);
    new Chart($('#reservation_chart'), reservation_chart);
    new Chart($('#gym_chart'), gym_chart);
})
