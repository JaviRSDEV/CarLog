-- V{version}__fix_pending_owner_dni_mapping.sql

ALTER TABLE vehicles
    ADD COLUMN IF NOT EXISTS pending_owner_dni VARCHAR(255),
    ADD COLUMN IF NOT EXISTS pending_workshop_id BIGINT;

ALTER TABLE vehicles
    ADD CONSTRAINT IF NOT EXISTS fk_vehicles_pending_owner
    FOREIGN KEY (pending_owner_dni) REFERENCES users(dni);

ALTER TABLE vehicles
    ADD CONSTRAINT IF NOT EXISTS fk_vehicles_pending_workshop
    FOREIGN KEY (pending_workshop_id) REFERENCES workshops(workshop_id);