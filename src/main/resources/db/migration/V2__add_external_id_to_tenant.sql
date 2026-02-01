ALTER TABLE tenant
    ADD COLUMN external_id VARCHAR(128);

CREATE UNIQUE INDEX uk_tenant_external_id
    ON tenant (external_id);
