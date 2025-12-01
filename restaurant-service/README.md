# restaurant-service

README này hướng dẫn cách chạy, kiểm thử và kiểm tra nhanh `restaurant-service` trong workspace.

**Nền tảng & công cụ**
- Java 21
- Spring Boot 3.x (Maven)
- Spring Data JPA (Postgres in Docker, H2 in tests)
- Docker & Docker Compose (repo root `docker-compose.yml` chứa các service liên quan)

**Mục tiêu**
- Chạy service cục bộ bằng Maven
- Build artifact bằng Maven
- Chạy stack (Postgres + service) bằng Docker Compose
- Chạy tests (unit + integration)
- Ví dụ gọi API

---

## Chạy nhanh bằng Maven (local JVM)
- Chạy ứng dụng (đọc `src/main/resources/application.yml`, port mặc định `8083`):

```bash
cd /path/to/repo/restaurant-service
./mvnw spring-boot:run
# hoặc (macOS/Linux)
mvn spring-boot:run
```

- Build jar (bỏ qua tests):

```bash
mvn -B -DskipTests package
# artifact: target/restaurant-service-0.0.1-SNAPSHOT.jar
```

## Chạy tests (unit + integration)
Integration tests sử dụng H2 (in-memory). Chạy:

```bash
cd /path/to/repo/restaurant-service
mvn test
```

Nếu bạn chỉ muốn chạy unit tests:

```bash
mvn -Dtest="**/*Unit*" test
```

## Chạy toàn bộ stack bằng Docker Compose (recommended)
Ở root repository (nơi chứa `docker-compose.yml`), build và chạy toàn bộ:

```bash
cd /path/to/repo
docker-compose up --build -d
```

Xem logs của `restaurant-service`:

```bash
docker-compose logs -f restaurant-service
```

Nếu muốn chỉ khởi các service cho `restaurant-service` (Postgres + service):

```bash
docker-compose up --build -d restaurant-postgres restaurant-service
```

## Biến môi trường quan trọng
Bạn có thể ghi đè cấu hình qua env vars trước khi chạy container hoặc chạy Maven:

- `SPRING_DATASOURCE_URL` (ví dụ: `jdbc:postgresql://restaurant-postgres:5432/restaurantdb` hoặc `jdbc:postgresql://localhost:15434/restaurantdb` khi chạy local)
- `SPRING_DATASOURCE_USERNAME` (mặc định `postgres`)
- `SPRING_DATASOURCE_PASSWORD` (mặc định `postgres`)

Ví dụ chạy docker-compose với override envs (macOS zsh):

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:15434/restaurantdb
docker-compose up --build -d restaurant-postgres restaurant-service
```

## API nhanh (ví dụ)
Service cung cấp endpoint REST tại `/api/restaurants` (điểm truy cập mặc định: `http://localhost:8083`)

- Tạo restaurant (POST):

```bash
curl -s -X POST http://localhost:8083/api/restaurants \
  -H "Content-Type: application/json" \
  -d '{"name":"Bún Chả Hà Nội","address":"123 Hàng Gai"}'
```

- Lấy danh sách restaurants (GET):

```bash
curl http://localhost:8083/api/restaurants
```

- Lấy restaurant theo id (GET):

```bash
curl http://localhost:8083/api/restaurants/1
```

(Trường `CreateRestaurantRequest` có các trường cơ bản `name` và `address` — điều chỉnh payload nếu project của bạn khác.)

## Troubleshooting / Gợi ý
- Nếu tests bị lỗi YAML duplicate-key: ensure `src/main/resources/application.yml` có các mappings hợp lệ (đã định sẵn trong repo).
- Nếu dịch vụ không khởi trong Docker Compose: xem logs `docker-compose logs -f restaurant-service` để biết nguyên nhân kết nối.
- Nếu DB bị lỗi, bạn có thể reset volumes bằng:

```bash
docker-compose down -v
docker-compose up --build -d
```

## Contributor notes
- Cấu trúc code follow pattern ports-and-adapters: domain repository interfaces + infrastructure adapters.
- Các thay đổi test sử dụng H2 (in-memory) để giữ test isolation.

---

Nếu bạn muốn, tôi có thể:
- Thêm file `README` tiếng Anh song song, hoặc
- Thêm ví dụ Postman / HTTPie collection, hoặc
- Tạo script Makefile / npm script để rút gọn lệnh chạy.

Hãy cho biết bạn muốn thêm gì nữa.