CREATE TABLE "popin"."auth_token" (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    token VARCHAR(255) NOT NULL,
    token_type VARCHAR(7) NOT NULL,
    expiration_time TIMESTAMPTZ NOT NULL
);

CREATE INDEX "user_token_type_idx" on "popin"."auth_token" (user_id, token, token_type);