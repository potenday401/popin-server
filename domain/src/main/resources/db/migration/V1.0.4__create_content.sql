CREATE TABLE "popin"."content" (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    address TEXT NOT NULL,
    point POINT NOT NULL,
    created_date_time TIMESTAMPTZ NOT NULL
);

CREATE INDEX "content_point_idx" ON "popin"."content" USING gist(point);