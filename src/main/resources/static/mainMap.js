
$(document).ready(function() {
    loadAllGyms();
});
function loadAllGyms() {
    $.ajax({
        url: '/api/climbinggyms',
        dataType: 'json',
        success: function(data) {
            if (currentLocationInfoWindow) {
                currentLocationInfoWindow.close();
            }
            // 기존 마커 제거
            if (map.markers) {
                for (let marker of map.markers) {
                    marker.setMap(null);
                }
            }

            // 새로운 마커 추가
            map.markers = data.map(function(gym) {
                let position = new naver.maps.LatLng(gym.mapY, gym.mapX);

                let marker = new naver.maps.Marker({
                    position: position,
                    map: map,
                    title: gym.name
                });

                let infoWindow = new naver.maps.InfoWindow({
                    content:
                        `<div style="width:200px;text-align:center;padding:10px;">
            <strong>${gym.name}</strong><br>
            ${gym.address}<br>
            <a href="/climbinggym?climbingid=${gym.id}" target="_blank">내부 링크</a>
        </div>`
                });

                naver.maps.Event.addListener(marker, "click", function() {
                    if (infoWindow.getMap()) {
                        infoWindow.close();
                    } else {
                        infoWindow.open(map, marker);
                    }
                });

                return marker;
            });
        }
    });
}

let currentLocationInfoWindow = null;

let map = new naver.maps.Map('map', {
    center: new naver.maps.LatLng(37.5200704, 127.0215399),
    zoom: 13
});

function moveToCurrentLocation() {

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            let lat = position.coords.latitude;
            let lng = position.coords.longitude;
            let location = new naver.maps.LatLng(lat, lng);
            map.setCenter(location);
            // map.setZoom(15); // 원하는 줌 레벨 설정
            if (currentLocationInfoWindow) {
                currentLocationInfoWindow.close();
            }
            currentLocationInfoWindow = new naver.maps.InfoWindow({
                content: '<div style="padding:10px;">현재 위치</div>'
            });

            currentLocationInfoWindow.open(map, location);
        }, function(error) {
            console.error("Error occurred. Error code: " + error.code);
            // error.code can be:
            //   0: unknown error
            //   1: permission denied
            //   2: position unavailable (error response from locaton provider)
            //   3: timed out
        });
    } else {
        alert('Geolocation is not supported by this browser.');
    }
}

// function searchGyms() {
//     let keyword = $('#searchKeyword').val();
//     if (keyword === '클라이밍') {
//         keyword = '';
//     }
//
//     $.ajax({
//         url: '/search',
//         dataType: 'json',
//         data: {
//             query: keyword + "클라이밍",
//         },
//         success: function(data) {
//             if (currentLocationInfoWindow) {
//                 currentLocationInfoWindow.close();
//             }
//             // 기존 마커 제거
//             if (map.markers) {
//                 for (let marker of map.markers) {
//                     marker.setMap(null);
//                 }
//             }
//
//             console.log(data);
//             // 새로운 마커 추가
//             map.markers = data.items.map(function(gym) {
//                 let lat = parseFloat(gym.mapy) / 1e7; // mapy 값을 10^7로 나누어 위도 계산
//                 let lng = parseFloat(gym.mapx) / 1e7; // mapx 값을 10^7로 나누어 경도 계산
//                 console.log(lng, lat);
//                 let position = new naver.maps.LatLng(lat, lng);
//
//                 let marker = new naver.maps.Marker({
//                     position: position,
//                     map: map,
//                     title: gym.title
//                 });
//
//                 let infoWindow = new naver.maps.InfoWindow({
//                     content:
//                         `<div style="width:200px;text-align:center;padding:10px;">
//                                 <strong>${gym.title}</strong><br>
//                                 ${gym.roadAddress}<br>
//                                 <a href="/climbinggym/${gym.id}" target="_blank">내부 링크</a><br>
//                                 <a href="${gym.link}" target="_blank">더보기</a>
//                               </div>`
//                 });
//
//                 naver.maps.Event.addListener(marker, "click", function() {
//                     if (infoWindow.getMap()) {
//                         infoWindow.close();
//                     } else {
//                         infoWindow.open(map, marker);
//                     }
//                 });
//
//                 return marker;
//             });
//         }
//     });
// }
function searchGyms() {
    const keyword = document.getElementById("searchKeyword").value;
    $.ajax({
        url: `/api/search/climbinggyms`, // URL을 템플릿 리터럴로 수정
        data: { keyword: keyword },
        dataType: 'json',
        success: function(data) {
            if (currentLocationInfoWindow) {
                currentLocationInfoWindow.close();
            }
            // 기존 마커 제거
            if (map.markers) {
                for (let marker of map.markers) {
                    marker.setMap(null);
                }
            }

            // 새로운 마커 추가
            map.markers = data.map(function(gym) {
                let position = new naver.maps.LatLng(gym.mapY, gym.mapX);

                let marker = new naver.maps.Marker({
                    position: position,
                    map: map,
                    title: gym.name
                });

                let infoWindow = new naver.maps.InfoWindow({
                    content:
                        `<div style="width:200px;text-align:center;padding:10px;">
            <strong>${gym.name}</strong><br>
            ${gym.address}<br>
            <a href="/climbinggym?climbingid=${gym.id}" target="_blank">내부 링크</a>
        </div>`
                });

                naver.maps.Event.addListener(marker, "click", function() {
                    if (infoWindow.getMap()) {
                        infoWindow.close();
                    } else {
                        infoWindow.open(map, marker);
                    }
                });

                return marker;
            });
        }
    });
}