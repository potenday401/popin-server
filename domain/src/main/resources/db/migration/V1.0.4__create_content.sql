CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE "popin"."content"
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    VARCHAR(36) NOT NULL,
    title      TEXT        NOT NULL,
    address    TEXT        NOT NULL,
    point      GEOMETRY(Point,4326) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX "content_user_id_idx" ON "popin"."content" (user_id);
CREATE INDEX "content_point_idx" ON "popin"."content" USING gist(point);