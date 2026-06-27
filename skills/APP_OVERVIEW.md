# Todo Spring Bootバックエンドの概要

このリポジトリは、フルスタックTodo教材のSpring Boot + JPA回答コードです。

## 機能

- `GET /todos`: Todo一覧
- `GET /todos/{id}`: Todo詳細
- `POST /todos`: Todo作成
- `PATCH /todos/{id}`: Todo更新
- `DELETE /todos/{id}`: Todo削除

## 技術構成

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- PostgreSQL 16（Docker Compose）
- H2（テスト用）

## 設計

- `TodoController`: HTTP API
- `TodoRepository`: JPA repository
- `Todo`: Entity
- DTOはController内のrecordとして定義

## 起動

```bash
docker compose up -d
./mvnw spring-boot:run
```

APIは `http://localhost:8000` で起動します。

