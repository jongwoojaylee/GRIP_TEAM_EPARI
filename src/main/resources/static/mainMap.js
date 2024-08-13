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
                    content:
                        `<div style="width:200px;text-align:center;padding:10px;">
                            <p><strong>${gym.name}</strong></p>
                            <p>${gym.address}</p>
                            수용 인원 : ${gym.acceptableCount}
                            <p>혼잡도 : <span id="congestion-${gym.id}">불러오는 중...</span></p>
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
                            .then(response => response.json())
                            .then(congestionData => {
                                let congestionElement = document.getElementById(`congestion-${gym.id}`);
                                if (congestionData) {
                                    // congestionElement.innerText = `${congestionData.presentCount}`;
                                    if (congestionData.presentCount/gym.acceptableCount < 0.33) {
                                        congestionElement.innerText = `여유`;
                                        congestionElement.style.color = 'green';
                                    } else if (congestionData.presentCount/gym.acceptableCount < 0.66) {
                                        congestionElement.innerText = `보통`;
                                        congestionElement.style.color = 'orange';
                                    } else {
                                        congestionElement.innerText = `혼잡`;
                                        congestionElement.style.color = 'red';
                                    }

                                } else {
                                    congestionElement.innerHTML = "정보 없음";
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
                            <p><strong>${gym.name}</strong></p>
                            <p>${gym.address}</p>
                            수용 인원 : ${gym.acceptableCount}
                            <p>혼잡도 : <span id="congestion-${gym.id}">불러오는 중...</span></p>
                            <mark>
                                <a href="/climbinggym?climbingid=${gym.id}" target="_blank">내부 링크</a>
                            </mark>
                        </div>`
                });

                naver.maps.Event.addListener(marker, "click", function() {
                    if (infoWindow.getMap()) {
                        infoWindow.close();
                    } else {
                        infoWindow.open(map, marker);
                        let currentHour = new Date().getHours();

                        fetch(`/api/climbinggym/${gym.id}/congestion?hour=${currentHour}`)
                            .then(response => response.json())
                            .then(congestionData => {
                                let congestionElement = document.getElementById(`congestion-${gym.id}`);
                                if (congestionData) {
                                    // congestionElement.innerText = `혼잡도: ${congestionData.presentCount}`;
                                    if (congestionData.presentCount/gym.acceptableCount < 0.33) {
                                        congestionElement.innerText = `여유`;
                                        congestionElement.style.color = 'green';
                                    } else if (congestionData.presentCount/gym.acceptableCount < 0.66) {
                                        congestionElement.innerText = `보통`;
                                        congestionElement.style.color = 'orange';
                                    } else {
                                        congestionElement.innerText = `혼잡`;
                                        congestionElement.style.color = 'red';
                                    }

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
            console.log(error);
        })
}