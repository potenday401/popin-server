CREATE TABLE "popin"."email_auth_code" (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    user_email VARCHAR(36) NOT NULL,
    code VARCHAR(6) NOT NULL,
    expiration_time TIMESTAMPTZ NOT NULL,
    create_at DATE NOT NULL
);

CREATE INDEX "auth_code_email_code_idx" on "popin"."email_auth_code" (user_email, code);
CREATE INDEX "auth_code_email_reg_date_idx" on "popin"."email_auth_code" (user_email, create_at);