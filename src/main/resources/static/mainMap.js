$(document).ready(function() {
    loadAllGyms();
});
function loadAllGyms() {
    // '/api/climbinggyms'를 호출하여 응답이 성공적이라면 json형식으로 응답 데이터 파싱
    fetch('/api/climbinggyms')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        // data에는 클라이밍짐 목록이 있다.
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
                // data의 gym에서 위도,경도를 가져와 네이버지도 위치 객체를 생성
                let position = new naver.maps.LatLng(gym.mapY, gym.mapX);

                // 위치 객체를 기반으로 마커를 생성, title을 통행 짐 이름을 설정
                let marker = new naver.maps.Marker({
                    position: position,
                    map: map,
                    title: gym.name
                });

                // 정보창 생성
                let infoWindow = new naver.maps.InfoWindow({
                    content:
                        `<div style="width:200px;text-align:center;padding:10px;">
                            <p><strong>${gym.name}</strong></p>
                            <p>${gym.address}</p>
                            수용 인원 : ${gym.acceptableCount}
                            <p>혼잡도 : <span id="congestion-${gym.id}">불러오는 중...</span></p>
                            <mark>
                                <a href="/climbinggym?climbingid=${gym.id}" target="_parent">내부 링크</a>
                            </mark>
                        </div>`
                });

                // 마커 클릭시 정보창을 여닫을 수 있다. (열린정보창이 있다면 닫고 없다면 연다.)
                naver.maps.Event.addListener(marker, "click", function() {
                    if (infoWindow.getMap()) {
                        infoWindow.close();
                    } else {
                        // 열릴 때 현재 시간을 가져와 혼잡도를 요청한다.
                        infoWindow.open(map, marker);

                        let currentHour = new Date().getHours();

                        // '/api/climbinggym/'에 클라이밍짐 아이디와 현재 시간을 넣어 요청
                        fetch(`/api/climbinggym/${gym.id}/congestion?hour=${currentHour}`)
                            .then(response => response.json())

                            // 받은 데이터를 계산하여 혼잡도 간단하게 표시
                            .then(congestionData => {
                                let congestionElement = document.getElementById(`congestion-${gym.id}`);
                                if (congestionData) {
                                    congestionElement.innerText = `${congestionData.presentCount}`;
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
                // 최종적으로 map.markers에 저장
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
    // 사용 중인 브라우저가 Geolocation API를 지원하는지 확인
    if (navigator.geolocation) {
        // 사용자의 위치를 요청
        navigator.geolocation.getCurrentPosition(function(position) {
            // 위치에서 위도, 경도를 추출
            let lat = position.coords.latitude;
            let lng = position.coords.longitude;

            // 위치 객체를 생성하여지도 지도의 중심 이동
            let location = new naver.maps.LatLng(lat, lng);
            map.setCenter(location);
            // map.setZoom(15); // 원하는 줌 레벨 설정

            // 열려있는 정보창이 있다면 닫기
            if (currentLocationInfoWindow) {
                currentLocationInfoWindow.close();
            }

            // 현재위치 정보창 생성
            currentLocationInfoWindow = new naver.maps.InfoWindow({
                content: '<div style="padding:10px;">현재 위치</div>'
            });

            // 현재위치 정보창 표시
            currentLocationInfoWindow.open(map, location);
        }, function(error) {
            // 실퍠시..
            console.error("Error occurred. Error code: " + error.code);
            // error.code can be:
            //   0: unknown error
            //   1: permission denied	// 위치정보 접근 거부
            //   2: position unavailable (error response from locaton provider)	// 위치정보 찾을 수 없음
            //   3: timed out	// 시간 초과
        });
    } else {
        // Geolocation API를 미지원시
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
                                <a href="/climbinggym?climbingid=${gym.id}" target="_parent">내부 링크</a>
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