import http from "k6/http";

export const options = {
  vus: 1000,
  iterations: 1000,
};

// k6 테스트 진입점(시작점)
// 설정한 반복수(iterations) 만큼 실행
export default function () {
  http.post(
    "http://localhost:8080/order",
    JSON.stringify({ productId: 1, userId: __VU }),
    { headers: { "Content-Type": "application/json" } }
  );
}
