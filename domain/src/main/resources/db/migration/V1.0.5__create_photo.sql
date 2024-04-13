CREATE TABLE "popin"."photo"
(
    id           BIGSERIAL PRIMARY KEY,
    content_id   BIGSERIAL NOT NULL,
    url          TEXT      NOT NULL,
    memorized_at TIMESTAMP NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP NOT NULL
);

CREATE INDEX "photo_content_id" ON "popin"."photo" (content_id);