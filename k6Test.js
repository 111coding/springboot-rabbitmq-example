import http from "k6/http";
import { check } from "k6";

export const options = {
  vus: 100,
  iterations: 1000,
};

// k6 테스트 진입점(시작점)
// 설정한 반복수(iterations) 만큼 실행
export default function () {
  const response = http.post(
    "http://localhost:8080/order",
    JSON.stringify({ productId: 1, userId: __VU }),
    { headers: { "Content-Type": "application/json" } }
  );
  check(response, {
    "is status CREATED": (r) => r.status === 201,
  });
}
