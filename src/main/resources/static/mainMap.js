
$(document).ready(function() {
    loadAllGyms();
});
function loadAllGyms() {
    fetch('/api/climbinggyms')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
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
            map.markers = data.map(gym => {
                let position = new naver.maps.LatLng(gym.mapY, gym.mapX);

                let marker = new naver.maps.Marker({
                    position: position,
                    map: map,
                    title: gym.name
                });

                let infoWindow = new naver.maps.InfoWindow({
                    content: `<div style="width:200px;text-align:center;padding:10px;" xmlns="http://www.w3.org/1999/html">
                        <strong>${gym.name}</strong><br>
                        ${gym.address}<br>
                        수용 인원 : ${gym.acceptableCount}<br>
                        <div id="congestion-${gym.id}">혼잡도 정보를 불러오는 중...</div>
                        <a href="/climbinggym?climbingid=${gym.id}" target="_blank">내부 링크</a>
                    </div>`
                });

                naver.maps.Event.addListener(marker, "click", function() {
                    if (infoWindow.getMap()) {
                        infoWindow.close();
                    } else {
                        infoWindow.open(map, marker);

                        let currentHour = new Date().getHours();

                        fetch(`/api/climbinggym/${gym.id}/congestion?hour=${currentHour}`)
                            .then(reponse => reponse.json())
                            .then(congestion => {
                                let congestionElement = document.getElementById(`congestion-${gym.id}`);
                                if (congestion) {
                                    congestionElement.innerText = '혼잡도: ${congestionData.presentCount}';
                                } else {
                                    congestionElement.innerHTML = "혼잡도 정보 없음";
                                }
                            })
                            .catch(error => {
                                console.error("Error fetching congestion data:", error);
                            });
                    }
                });

                return marker;
            });
        })
        .catch(error => {
            console.error(error);
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

function searchGyms() {
    const keyword = document.getElementById("searchKeyword").value;
    const encodedKeyword = encodeURIComponent(keyword);

    fetch(`/api/search/climbinggyms?keyword=${encodedKeyword}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data =>{
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
        })
        .catch(error => {
            console.log(error);
        })
}