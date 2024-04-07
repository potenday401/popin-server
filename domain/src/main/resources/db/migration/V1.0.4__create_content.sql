CREATE TABLE "popin"."content" (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    address TEXT NOT NULL,
    point GEOMETRY(Point,4326) NOT NULL,
    created_date_time TIMESTAMPTZ NOT NULL
);

CREATE INDEX "content_point_idx" ON "popin"."content" USING gist(point);