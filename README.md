# Order Service – Production-Oriented Spring Boot & CI/CD Project

---

## 🇹🇷 Türkçe

Bu repository, sadece bir REST API yazmak değil,
**yazılan kodun test edilmesi, paketlenmesi ve güvenli şekilde üretime hazır hale getirilmesini**
öğrenmek amacıyla geliştirilmiş bir **Order Service** projesidir.

Bu proje bir demo değildir.
Gerçek hayatta bir şirkette karşılaşılan **backend + CI/CD** süreçlerini birebir yansıtmayı hedefler.

---

### 🎯 Proje Amacı

> Sadece kod yazmak değil,  
> **yazılan kodun production’daki sorumluluğunu almak.**

Bu projede şu sorulara cevap aranmıştır:
- Kod production’a uygun mu?
- Hatalar erken yakalanıyor mu?
- Güvenli şekilde deliver edilebiliyor mu?
- Mimari sürdürülebilir mi?

---

### 🧱 Mimari Yaklaşım

Proje **katmanlı ve domain-first** bir mimari kullanır:

```text
presentation  → Controller (HTTP layer)
api           → Service (use-case orchestration)
domain        → Entity & business rules
infrastructure→ Persistence (JPA)


Temel prensipler:

Entity’lerde setter yoktur

İş kuralları domain içinde tanımlıdır

Service katmanı sadece akışı yönetir

Infrastructure detayları izole edilmiştir

🛠 Kullanılan Teknolojiler

Java 21

Spring Boot 3

Maven

JUnit 5 & MockMvc

Docker

GitHub Actions

GitHub Container Registry (GHCR)



🔁 Idempotent Order Creation

Order oluşturma endpoint’i idempotent çalışır.
POST /api/orders
Idempotency-Key: <unique-key>
Aynı key ile gelen istekler aynı order’ı döner

Duplicate request yeni kayıt oluşturmaz

Servis mantığı ve veritabanı kısıtları ile garanti altındadır

Testlerle doğrulanmıştır


🧪 Test Stratejisi

Controller seviyesinde davranış testleri

HTTP contract doğrulaması

Idempotency senaryoları

/api/orders/ping endpoint’i ile servis health kontrolü

Testler CI pipeline’ında otomatik çalışır

⚙️ CI/CD Akışı

Branch stratejisi:
feature → develop → main

CI (Continuous Integration)

Build

Test

CD (Continuous Delivery)

Docker image build

SHA + latest tag

GitHub Container Registry’ye push

Her main merge sonrası deploy edilebilir bir Docker image üretilir.

Docker

docker pull ghcr.io/<user>/order-service:latest
docker run -p 8080:8080 ghcr.io/<user>/order-service:latest

Health check:
curl http://localhost:8080/api/orders/ping

🔜 Planlanan Adımlar

PostgreSQL + Flyway

Testcontainers

Docker Compose

Kubernetes

Observability (Prometheus / Grafana)

