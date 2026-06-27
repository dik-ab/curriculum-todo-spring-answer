# Todo API Spring Boot回答コード

フルスタックTodo教材のJava / Spring Boot + JPAバックエンド回答コードです。Reactフロントエンドは別リポジトリ `curriculum-react-projects-answer` の `apps/todo` を使います。

## 構成

```text
curriculum-todo-spring-answer/
├── compose.yaml
├── mvnw
├── pom.xml
├── src/main/java/com/example/todo/
└── skills/APP_OVERVIEW.md
```

## ポート

| 役割 | URL |
|---|---|
| API | `http://localhost:8000` |
| PostgreSQL | `localhost:5432` |
| React | `http://localhost:5173` |

## 初回セットアップ

Maven本体は不要です。リポジトリ内の `mvnw` を使います。

```bash
docker compose up -d
./mvnw test
```

## 起動

```bash
./mvnw spring-boot:run
```

Reactフロントエンド側:

```bash
cd ../curriculum-react-projects-answer/apps/todo
pnpm install
cp .env.example .env
# .env の VITE_API_URL を http://localhost:8000 にする
pnpm run dev
```

ブラウザで `http://localhost:5173/` を開きます。

## API

| メソッド | パス | 役割 |
|---|---|---|
| `GET` | `/todos` | Todo一覧 |
| `GET` | `/todos/{id}` | Todo詳細 |
| `POST` | `/todos` | Todo作成 |
| `PATCH` | `/todos/{id}` | Todo更新 |
| `DELETE` | `/todos/{id}` | Todo削除 |

## テスト

```bash
./mvnw test
```

テストではH2のインメモリDBを使います。ローカル開発ではDocker ComposeのPostgreSQLを使います。

## 開発メモ

- デプロイやインフラ構築はこのリポジトリでは扱いません。
- DBだけDocker Composeで起動し、APIはローカルのSpring Bootで実行します。
- CORSは `http://localhost:5173` を許可しています。

