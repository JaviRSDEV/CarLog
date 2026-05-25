-- 1. Alterar la tabla para que 'workshop_id' acepte nulos
ALTER TABLE work_order MODIFY COLUMN workshop_id BIGINT NULL;

-- 2. Añadir la columna histórica que nos faltaba físicamente en la BBDD
ALTER TABLE work_order ADD COLUMN historical_workshop_name VARCHAR(255) NULL;

--3. Para que las órdenes viejas no se queden con el histórico vacío, hacemos un JOIN
-- y rellenamos automáticamente el campo histórico con el nombre actual de su taller.
UPDATE work_order wo
    JOIN workshop w ON wo.workshop_id = w.workshop_id
    SET wo.historical_workshop_name = w.name
WHERE wo.historical_workshop_name IS NULL;