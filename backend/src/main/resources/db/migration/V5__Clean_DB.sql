-- ================================================= =
-- SCRIPT DE LIMPIEZA Y UNIFICACIÓN DE CATÁLOGO
-- ================================================= =

-- 1. Desactivar checks de claves foráneas para poder operar
SET FOREIGN_KEY_CHECKS = 0;

-- --------------------------------------------------
-- A. LIMPIEZA DE CAR_BRANDS (Marcas)
-- --------------------------------------------------

-- Unificar modelos que apuntan a marcas duplicadas
-- (Si hay dos 'Ford', todos los modelos apuntarán a la que tenga el ID más bajo)
UPDATE car_models cm
    JOIN car_brands cb1 ON cm.brand_id = cb1.id
    JOIN car_brands cb2 ON cb1.name = cb2.name AND cb1.id > cb2.id
    SET cm.brand_id = cb2.id;

-- Borrar marcas duplicadas (mantenemos la de ID más bajo)
DELETE b1 FROM car_brands b1
INNER JOIN car_brands b2
WHERE b1.id > b2.id AND b1.name = b2.name;

-- --------------------------------------------------
-- B. LIMPIEZA DE CAR_MODELS (Modelos)
-- --------------------------------------------------

-- Unificar versiones que apuntan a modelos duplicados
UPDATE car_version cv
    JOIN car_models cm1 ON cv.model_id = cm1.id
    JOIN car_models cm2 ON cm1.name = cm2.name AND cm1.brand_id = cm2.brand_id AND cm1.id > cm2.id
    SET cv.model_id = cm2.id;

-- Borrar modelos duplicados (mantenemos el de ID más bajo)
DELETE m1 FROM car_models m1
INNER JOIN car_models m2
WHERE m1.id > m2.id AND m1.name = m2.name AND m1.brand_id = m2.brand_id;

-- --------------------------------------------------
-- C. LIMPIEZA DE CAR_VERSION (Versiones)
-- --------------------------------------------------

-- Borrar versiones idénticas
-- Se considera duplicada si coincide modelo, nombre, motor y años
DELETE v1 FROM car_version v1
INNER JOIN car_version v2
WHERE v1.id > v2.id
  AND v1.model_id = v2.model_id
  AND v1.version_name = v2.version_name
  AND (v1.engine_code = v2.engine_code OR (v1.engine_code IS NULL AND v2.engine_code IS NULL))
  AND v1.year_start = v2.year_start;

-- --------------------------------------------------
-- D. APLICAR CANDADOS (Constraints UNIQUE)
-- --------------------------------------------------
-- Esto evitará que vuelvan a entrar duplicados en el futuro

-- Para las marcas: nombre único
ALTER TABLE car_brands ADD CONSTRAINT uk_brand_name UNIQUE (name);

-- Para los modelos: nombre único dentro de una marca
ALTER TABLE car_models ADD CONSTRAINT uk_model_brand UNIQUE (name, brand_id);

-- Para las versiones: evitar la misma versión para el mismo modelo y motor
ALTER TABLE car_version ADD CONSTRAINT uk_version_model_engine UNIQUE (model_id, version_name, engine_code, year_start);

-- 2. Reactivar checks de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- --------------------------------------------------
-- E. VERIFICACIÓN FINAL
-- --------------------------------------------------
SELECT 'Marcas' as Tabla, COUNT(*) as Total FROM car_brands
UNION ALL
SELECT 'Modelos', COUNT(*) FROM car_models
UNION ALL
SELECT 'Versiones', COUNT(*) FROM car_version;