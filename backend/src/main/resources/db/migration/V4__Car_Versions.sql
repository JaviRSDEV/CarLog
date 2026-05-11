SET @brand_id = (SELECT id FROM car_brands WHERE name = 'SEAT');

-- ------------------------------------------
-- 1. IBIZA
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Ibiza' AND brand_id = @brand_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'FR', 'BLT', '1.9 TDI', 'Diesel', 130, 310, 2004, 2008),
(@model_id, 'Cupra', 'BPX', '1.9 TDI', 'Diesel', 160, 330, 2004, 2007),
(@model_id, 'Sport', 'ASZ', '1.9 TDI', 'Diesel', 130, 310, 2002, 2004),
(@model_id, 'Reference', 'AXR', '1.9 TDI', 'Diesel', 100, 240, 2005, 2008),
(@model_id, 'FR', 'CAVC', '1.4 TSI', 'Gasolina', 150, 220, 2009, 2015),
(@model_id, 'Style', 'CAYC', '1.6 TDI', 'Diesel', 105, 250, 2009, 2015),
(@model_id, 'FR', 'DADA', '1.5 TSI', 'Gasolina', 150, 250, 2017, 2020);

-- ------------------------------------------
-- 2. LEÓN
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'León' AND brand_id = @brand_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'FR', 'ARL', '1.9 TDI', 'Diesel', 150, 320, 2001, 2005),
(@model_id, 'Sport', 'ASV', '1.9 TDI', 'Diesel', 110, 235, 1999, 2005),
(@model_id, 'Cupra R', 'BAM', '1.8 20VT', 'Gasolina', 225, 280, 2003, 2005),
(@model_id, 'FR', 'BMN', '2.0 TDI', 'Diesel', 170, 350, 2006, 2009),
(@model_id, 'Style', 'BKD', '2.0 TDI', 'Diesel', 140, 320, 2005, 2010),
(@model_id, 'Reference', 'BXE', '1.9 TDI', 'Diesel', 105, 250, 2005, 2010),
(@model_id, 'Cupra 300', 'CJXC', '2.0 TSI', 'Gasolina', 300, 380, 2017, 2018),
(@model_id, 'FR', 'CRBC', '2.0 TDI', 'Diesel', 150, 320, 2012, 2019);

-- ------------------------------------------
-- 3. TOLEDO
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Toledo' AND brand_id = @brand_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Magnus', 'AFN', '1.9 TDI', 'Diesel', 110, 235, 1996, 1999),
(@model_id, 'Signum', 'ARL', '1.9 TDI', 'Diesel', 150, 320, 2000, 2004),
(@model_id, 'Stylance', 'BKD', '2.0 TDI', 'Diesel', 140, 320, 2004, 2009);

-- ------------------------------------------
-- 4. CÓRDOBA
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Córdoba' AND brand_id = @brand_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'SX', 'AFN', '1.9 TDI', 'Diesel', 110, 235, 1997, 2002),
(@model_id, 'Sport', 'ASZ', '1.9 TDI', 'Diesel', 130, 310, 2002, 2005);

-- ------------------------------------------
-- 5. EXEO
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Exeo' AND brand_id = @brand_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Sport', 'CAGA', '2.0 TDI (CR)', 'Diesel', 143, 320, 2009, 2013),
(@model_id, 'Style', 'CAGC', '2.0 TDI (CR)', 'Diesel', 120, 290, 2009, 2013);

-- ==========================================
-- VERSIONES CUPRA Y VOLKSWAGEN - TANDA 1
-- ==========================================

-- ------------------------------------------
-- 1. CUPRA
-- ------------------------------------------
SET @cupra_id = (SELECT id FROM car_brands WHERE name = 'Cupra');

-- Formentor
SET @model_id = (SELECT id FROM car_models WHERE name = 'Formentor' AND brand_id = @cupra_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'VZ5', 'DNWB', '2.5 TSI (5 cil)', 'Gasolina', 390, 480, 2021, 2024),
(@model_id, 'VZ', 'DNFB', '2.0 TSI', 'Gasolina', 310, 400, 2020, 2024),
(@model_id, 'VZ e-Hybrid', 'DGEA', '1.4 e-Hybrid', 'Híbrido Ench.', 245, 400, 2021, 2024),
(@model_id, 'Básico', 'DADA', '1.5 TSI', 'Gasolina', 150, 250, 2020, 2024);

-- León Cupra
SET @model_id = (SELECT id FROM car_models WHERE name = 'León' AND brand_id = @cupra_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'VZ', 'DNFC', '2.0 TSI', 'Gasolina', 300, 400, 2020, 2024),
(@model_id, 'e-Hybrid', 'DGEA', '1.4 e-Hybrid', 'Híbrido Ench.', 204, 350, 2020, 2024);

-- ------------------------------------------
-- 2. VOLKSWAGEN GOLF (El Rey del Taller)
-- ------------------------------------------
SET @vw_id = (SELECT id FROM car_brands WHERE name = 'Volkswagen');
SET @model_id = (SELECT id FROM car_models WHERE name = 'Golf' AND brand_id = @vw_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
-- Golf IV (1997-2003)
(@model_id, 'R32', 'BFH', '3.2 VR6', 'Gasolina', 241, 320, 2002, 2004),
(@model_id, 'GTI', 'AUQ', '1.8T 20V', 'Gasolina', 180, 235, 2001, 2003),
(@model_id, 'GTI', 'AUM', '1.8T 20V', 'Gasolina', 150, 210, 2000, 2003),
(@model_id, 'TDI 150', 'ARL', '1.9 TDI', 'Diesel', 150, 320, 2000, 2003),
(@model_id, 'TDI 130', 'ASZ', '1.9 TDI', 'Diesel', 130, 310, 2001, 2003),
(@model_id, 'TDI 110', 'ASV', '1.9 TDI', 'Diesel', 110, 235, 1999, 2002),
(@model_id, 'TDI 100', 'AXR', '1.9 TDI', 'Diesel', 100, 240, 2001, 2003),
(@model_id, 'TDI 90', 'ALH', '1.9 TDI', 'Diesel', 90, 210, 1997, 2003),
-- Golf V (2003-2008)
(@model_id, 'GTI', 'AXX', '2.0 TFSI', 'Gasolina', 200, 280, 2004, 2006),
(@model_id, 'GTI Edition 30', 'BYD', '2.0 TFSI', 'Gasolina', 230, 300, 2006, 2008),
(@model_id, 'R32', 'BUB', '3.2 VR6', 'Gasolina', 250, 320, 2005, 2008),
(@model_id, 'TDI 140', 'BKD', '2.0 TDI (Bomba-Iny)', 'Diesel', 140, 320, 2003, 2008),
(@model_id, 'TDI 170', 'BMN', '2.0 TDI (Bomba-Iny)', 'Diesel', 170, 350, 2006, 2008),
(@model_id, 'TDI 105', 'BXE', '1.9 TDI', 'Diesel', 105, 250, 2003, 2008),
-- Golf VI (2008-2012)
(@model_id, 'GTI', 'CCZB', '2.0 TSI', 'Gasolina', 211, 280, 2009, 2012),
(@model_id, 'R', 'CDLF', '2.0 TSI', 'Gasolina', 270, 350, 2009, 2012),
(@model_id, 'TDI 140', 'CFFB', '2.0 TDI (CR)', 'Diesel', 140, 320, 2008, 2012),
-- Golf VII (2012-2020)
(@model_id, 'GTI Performance', 'CHHA', '2.0 TSI', 'Gasolina', 230, 350, 2013, 2017),
(@model_id, 'R', 'CJXC', '2.0 TSI', 'Gasolina', 300, 380, 2013, 2017),
(@model_id, 'GTD', 'CUNA', '2.0 TDI', 'Diesel', 184, 380, 2013, 2018);

-- ------------------------------------------
-- 3. VOLKSWAGEN SCIROCCO
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Scirocco' AND brand_id = @vw_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'R', 'CDLA', '2.0 TSI', 'Gasolina', 265, 350, 2009, 2014),
(@model_id, '2.0 TSI', 'CCZB', '2.0 TSI', 'Gasolina', 211, 280, 2009, 2014),
(@model_id, '2.0 TDI', 'CBBB', '2.0 TDI', 'Diesel', 170, 350, 2008, 2014),
(@model_id, '1.4 TSI', 'CAVD', '1.4 TSI (Twincharger)', 'Gasolina', 160, 240, 2008, 2014);

-- ------------------------------------------
-- 4. VOLKSWAGEN PASSAT
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Passat' AND brand_id = @vw_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
-- Passat B5 (1.9 TDI eterno)
(@model_id, 'Highline', 'AVF', '1.9 TDI', 'Diesel', 130, 310, 2000, 2005),
(@model_id, 'Comfortline', 'AFN', '1.9 TDI', 'Diesel', 110, 235, 1996, 2000),
-- Passat B6
(@model_id, 'R36', 'BWS', '3.6 FSI VR6', 'Gasolina', 300, 350, 2008, 2010),
(@model_id, 'Advance', 'BKP', '2.0 TDI', 'Diesel', 140, 320, 2005, 2008);

-- ==========================================
-- VERSIONES AUDI Y SKODA - TANDA 2
-- ==========================================

-- ------------------------------------------
-- 1. AUDI
-- ------------------------------------------
SET @audi_id = (SELECT id FROM car_brands WHERE name = 'Audi');

-- Audi A3 (El compacto premium por excelencia)
SET @model_id = (SELECT id FROM car_models WHERE name = 'A3' AND brand_id = @audi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
-- A3 8L (1996-2003)
(@model_id, '1.9 TDI 110', 'ASV', '1.9 TDI', 'Diesel', 110, 235, 1999, 2003),
(@model_id, '1.9 TDI 130', 'ASZ', '1.9 TDI', 'Diesel', 130, 310, 2000, 2003),
(@model_id, 'S3 Quattro', 'BAM', '1.8T 20V', 'Gasolina', 225, 280, 2001, 2003),
-- A3 8P (2003-2012)
(@model_id, '2.0 TDI 140', 'BKD', '2.0 TDI (Bomba-Iny)', 'Diesel', 140, 320, 2003, 2008),
(@model_id, '2.0 TDI 140', 'CFFB', '2.0 TDI (CR)', 'Diesel', 140, 320, 2008, 2012),
(@model_id, '2.0 TDI 170', 'BMN', '2.0 TDI (Bomba-Iny)', 'Diesel', 170, 350, 2006, 2008),
(@model_id, 'RS3 Sportback', 'CEPA', '2.5 TFSI', 'Gasolina', 340, 450, 2011, 2012),
-- A3 8V (2012-2020)
(@model_id, '2.0 TDI 150', 'CRBC', '2.0 TDI (CR)', 'Diesel', 150, 320, 2012, 2017);

-- Audi A4 (La berlina de batalla)
SET @model_id = (SELECT id FROM car_models WHERE name = 'A4' AND brand_id = @audi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
-- A4 B5
(@model_id, '1.9 TDI 110', 'AFN', '1.9 TDI', 'Diesel', 110, 235, 1996, 2000),
-- A4 B6/B7 (Ojo al 2.0 TDI de culata frágil)
(@model_id, '1.9 TDI 130', 'AVF', '1.9 TDI', 'Diesel', 130, 310, 2000, 2004),
(@model_id, '2.0 TDI 140', 'BLB', '2.0 TDI', 'Diesel', 140, 320, 2004, 2005),
(@model_id, '2.0 TDI 140', 'BRE', '2.0 TDI', 'Diesel', 140, 320, 2005, 2008),
-- A4 B8
(@model_id, '2.0 TDI 143', 'CAGA', '2.0 TDI (CR)', 'Diesel', 143, 320, 2008, 2012),
(@model_id, '3.0 TDI Quattro', 'CCWA', '3.0 V6 TDI', 'Diesel', 240, 500, 2008, 2012);

-- Audi TT
SET @model_id = (SELECT id FROM car_models WHERE name = 'TT' AND brand_id = @audi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.8T Quattro', 'APX', '1.8T 20V', 'Gasolina', 225, 280, 1998, 2001),
(@model_id, '2.0 TFSI', 'BWA', '2.0 TFSI', 'Gasolina', 200, 280, 2006, 2010);

-- ------------------------------------------
-- 2. SKODA
-- ------------------------------------------
SET @skoda_id = (SELECT id FROM car_brands WHERE name = 'Skoda');

-- Octavia (El favorito de los taxistas)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Octavia' AND brand_id = @skoda_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
-- Octavia 1U
(@model_id, '1.9 TDI 110', 'ASV', '1.9 TDI', 'Diesel', 110, 235, 2000, 2006),
(@model_id, 'RS', 'AUQ', '1.8T 20V', 'Gasolina', 180, 235, 2001, 2006),
-- Octavia 1Z
(@model_id, '2.0 TDI 140', 'BKD', '2.0 TDI', 'Diesel', 140, 320, 2004, 2010),
(@model_id, '1.9 TDI 105', 'BXE', '1.9 TDI', 'Diesel', 105, 250, 2004, 2010),
(@model_id, 'RS TDI', 'BMN', '2.0 TDI', 'Diesel', 170, 350, 2006, 2008),
(@model_id, 'RS TDI (CR)', 'CEGA', '2.0 TDI', 'Diesel', 170, 350, 2008, 2013),
-- Octavia 5E
(@model_id, '2.0 TDI 150', 'CKFC', '2.0 TDI', 'Diesel', 150, 320, 2013, 2017);

-- Fabia
SET @model_id = (SELECT id FROM car_models WHERE name = 'Fabia' AND brand_id = @skoda_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'RS TDI', 'ASZ', '1.9 TDI', 'Diesel', 130, 310, 2003, 2007),
(@model_id, '1.4 TDI', 'AMF', '1.4 TDI (3 cil)', 'Diesel', 75, 195, 2003, 2005),
(@model_id, '1.9 TDI', 'ATD', '1.9 TDI', 'Diesel', 100, 240, 2000, 2007);

-- ==========================================
-- VERSIONES PORSCHE
-- ==========================================
SET @porsche_id = (SELECT id FROM car_brands WHERE name = 'Porsche');

-- ------------------------------------------
-- 1. PORSCHE 911 (996, 997, 991, 992)
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = '911' AND brand_id = @porsche_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
-- Carrera 996 (Ojo al rodamiento IMS)
(@model_id, 'Carrera (996.1)', 'M96.01', '3.4 Bóxer', 'Gasolina', 300, 350, 1997, 2001),
(@model_id, 'Carrera (996.2)', 'M96.03', '3.6 Bóxer', 'Gasolina', 320, 370, 2001, 2004),
(@model_id, 'Turbo (996)', 'M96.70', '3.6 Bóxer Biturbo', 'Gasolina', 420, 560, 2000, 2005),
-- Carrera 997
(@model_id, 'Carrera S (997.1)', 'M97.01', '3.8 Bóxer', 'Gasolina', 355, 400, 2004, 2008),
(@model_id, 'Carrera (997.2)', 'MA1.02', '3.6 Bóxer (DFI)', 'Gasolina', 345, 390, 2008, 2012),
(@model_id, 'GT3 (997.2)', 'MA1.75', '3.8 Bóxer', 'Gasolina', 435, 430, 2009, 2011),
-- Carrera 991
(@model_id, 'Carrera S (991.1)', 'MA1.03', '3.8 Bóxer', 'Gasolina', 400, 440, 2011, 2015),
(@model_id, 'GT3 RS (991.1)', 'MA1.76', '4.0 Bóxer', 'Gasolina', 500, 460, 2015, 2017);

-- ------------------------------------------
-- 2. PORSCHE CAYENNE (El Porsche que más pisa el taller)
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Cayenne' AND brand_id = @porsche_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
-- Cayenne 955/957
(@model_id, 'S', 'M48.00', '4.5 V8', 'Gasolina', 340, 420, 2002, 2007),
(@model_id, 'Turbo S', 'M48.50', '4.5 V8 Biturbo', 'Gasolina', 521, 720, 2004, 2007),
(@model_id, 'Diesel (957)', 'CASA', '3.0 V6 TDI (VAG)', 'Diesel', 240, 550, 2009, 2010),
-- Cayenne 958
(@model_id, 'Diesel', 'MCR.CA', '3.0 V6 TDI', 'Diesel', 245, 550, 2010, 2014),
(@model_id, 'S Diesel', 'MCU.DB', '4.2 V8 TDI', 'Diesel', 382, 850, 2012, 2017),
(@model_id, 'S E-Hybrid', 'MCG.EA', '3.0 V6 Híbrido', 'Híbrido Ench.', 416, 590, 2014, 2017);

-- ------------------------------------------
-- 3. PORSCHE MACAN
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Macan' AND brand_id = @porsche_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'S Diesel', 'MCT.BA', '3.0 V6 TDI', 'Diesel', 258, 580, 2014, 2018),
(@model_id, 'Turbo', 'MCTL.AA', '3.6 V6 Biturbo', 'Gasolina', 400, 550, 2014, 2018),
(@model_id, 'Básico', 'DNYA', '2.0 TFSI (VAG)', 'Gasolina', 265, 400, 2021, 2024);

-- ------------------------------------------
-- 4. PORSCHE BOXSTER / CAYMAN (986, 987, 981)
-- ------------------------------------------
SET @model_id_box = (SELECT id FROM car_models WHERE name = 'Boxster' AND brand_id = @porsche_id);
SET @model_id_cay = (SELECT id FROM car_models WHERE name = 'Cayman' AND brand_id = @porsche_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id_box, '2.5 (986)', 'M96.20', '2.5 Bóxer', 'Gasolina', 204, 245, 1996, 1999),
(@model_id_box, 'S (986)', 'M96.21', '3.2 Bóxer', 'Gasolina', 252, 305, 1999, 2002),
(@model_id_cay, 'S (987)', 'M97.21', '3.4 Bóxer', 'Gasolina', 295, 340, 2005, 2008),
(@model_id_cay, 'R (987)', 'MA1.21', '3.4 Bóxer', 'Gasolina', 330, 370, 2010, 2012);

-- ------------------------------------------
-- 5. PORSCHE TAYCAN (El eléctrico)
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Taycan' AND brand_id = @porsche_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '4S', 'Electric', 'Dual Motor', 'Eléctrico', 530, 640, 2019, 2024),
(@model_id, 'Turbo S', 'Electric', 'Dual Motor', 'Eléctrico', 761, 1050, 2019, 2024);

-- ------------------------------------------
-- 6. CLÁSICOS
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = '944' AND brand_id = @porsche_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Turbo', 'M44.51', '2.5 L4 Turbo', 'Gasolina', 220, 330, 1985, 1988);

-- ==========================================
-- EL MEGAPARCHE DE PSA (PEUGEOT / CITROËN)
-- Todos los modelos restantes
-- ==========================================
SET @peugeot_id = (SELECT id FROM car_brands WHERE name = 'Peugeot');
SET @citroen_id = (SELECT id FROM car_brands WHERE name = 'Citroën');

-- ------------------------------------------
-- 1. CLÁSICOS Y BERLINAS PEUGEOT (205, 306, 406, 407, 508)
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = '205' AND brand_id = @peugeot_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'GTI 1.9', 'XU9JA', '1.9 8V', 'Gasolina', 130, 161, 1986, 1992),
(@model_id, 'Rallye', 'TU24', '1.3 8V (Carburación)', 'Gasolina', 103, 120, 1988, 1992),
(@model_id, 'Mito / Generation', 'XUD7', '1.8 D', 'Diesel', 60, 110, 1983, 1998);

SET @model_id = (SELECT id FROM car_models WHERE name = '306' AND brand_id = @peugeot_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'GTI', 'XU10J4RS', '2.0 16V', 'Gasolina', 167, 193, 1996, 2001),
(@model_id, 'D Turbo / XS', 'XUD9TE', '1.9 TD', 'Diesel', 90, 196, 1993, 1999),
(@model_id, 'HDI 90', 'RHY', '2.0 HDI', 'Diesel', 90, 205, 1999, 2002);

SET @model_id = (SELECT id FROM car_models WHERE name = '406' AND brand_id = @peugeot_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'HDI 110', 'RHZ', '2.0 HDI', 'Diesel', 110, 250, 1998, 2004),
(@model_id, 'Coupé V6', 'XFZ', '3.0 V6 24V', 'Gasolina', 190, 267, 1997, 2000);

SET @model_id = (SELECT id FROM car_models WHERE name = '407' AND brand_id = @peugeot_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'HDI 136', 'RHR', '2.0 HDI', 'Diesel', 136, 320, 2004, 2010),
(@model_id, 'V6 HDI', 'UHZ', '2.7 V6 HDI', 'Diesel', 204, 440, 2005, 2008);

SET @model_id = (SELECT id FROM car_models WHERE name = '508' AND brand_id = @peugeot_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'BlueHDI 180', 'AHW', '2.0 BlueHDI', 'Diesel', 180, 400, 2014, 2018),
(@model_id, 'PSE (Peugeot Sport)', '5G06', '1.6 PHEV', 'Híbrido Ench.', 360, 520, 2020, 2024);

-- ------------------------------------------
-- 2. PEUGEOT MODERNOS Y SUV (208, 2008, 3008, 5008, RCZ)
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = '208' AND brand_id = @peugeot_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'GTI', '5FU', '1.6 THP', 'Gasolina', 200, 275, 2013, 2018),
(@model_id, 'PureTech 82', 'HMZ', '1.2 PureTech', 'Gasolina', 82, 118, 2012, 2019),
(@model_id, 'BlueHDI 100', 'BHY', '1.6 BlueHDI', 'Diesel', 100, 254, 2015, 2019);

SET @model_3008 = (SELECT id FROM car_models WHERE name = '3008' AND brand_id = @peugeot_id);
SET @model_5008 = (SELECT id FROM car_models WHERE name = '5008' AND brand_id = @peugeot_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_3008, 'PureTech 130', 'HNY', '1.2 PureTech', 'Gasolina', 130, 230, 2016, 2024),
(@model_3008, 'BlueHDI 130', 'YH01', '1.5 BlueHDI', 'Diesel', 130, 300, 2018, 2024),
(@model_5008, 'BlueHDI 150', 'AHX', '2.0 BlueHDI', 'Diesel', 150, 370, 2017, 2020);

SET @model_id = (SELECT id FROM car_models WHERE name = 'RCZ' AND brand_id = @peugeot_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'R', '5FG', '1.6 THP', 'Gasolina', 270, 330, 2013, 2015),
(@model_id, 'HDI 163', 'RHH', '2.0 HDI', 'Diesel', 163, 340, 2010, 2015);

-- ------------------------------------------
-- 3. CITROËN CLÁSICOS Y FAMILIARES (Xantia, Xsara Picasso, C4 Picasso, C6)
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'Xantia' AND brand_id = @citroen_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Activa V6', 'XFZ', '3.0 V6 24V', 'Gasolina', 190, 267, 1997, 2000),
(@model_id, 'HDI 110', 'RHZ', '2.0 HDI', 'Diesel', 110, 250, 1998, 2001);

SET @model_id = (SELECT id FROM car_models WHERE name = 'Xsara Picasso' AND brand_id = @citroen_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'HDI 90', 'RHY', '2.0 HDI', 'Diesel', 90, 205, 1999, 2005),
(@model_id, 'HDI 110', '9HZ', '1.6 HDI', 'Diesel', 110, 240, 2004, 2010),
(@model_id, '1.6i', 'NFV', '1.6 8V', 'Gasolina', 95, 135, 1999, 2005);

SET @model_id = (SELECT id FROM car_models WHERE name = 'C4 Picasso' AND brand_id = @citroen_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'e-HDI 115', '9HD', '1.6 e-HDI', 'Diesel', 115, 270, 2013, 2016),
(@model_id, 'BlueHDI 150', 'AHE', '2.0 BlueHDI', 'Diesel', 150, 370, 2013, 2018);

SET @model_id = (SELECT id FROM car_models WHERE name = 'C6' AND brand_id = @citroen_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Exclusive', 'UHZ', '2.7 V6 HDI', 'Diesel', 204, 440, 2005, 2009);

-- ------------------------------------------
-- 4. CITROËN MODERNOS (C3, C4 Cactus, C5 Aircross)
-- ------------------------------------------
SET @model_id = (SELECT id FROM car_models WHERE name = 'C3' AND brand_id = @citroen_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'HDI 70', '8HZ', '1.4 HDI', 'Diesel', 68, 160, 2002, 2016),
(@model_id, 'PureTech 82', 'HMZ', '1.2 PureTech', 'Gasolina', 82, 118, 2012, 2019);

SET @model_id = (SELECT id FROM car_models WHERE name = 'C4 Cactus' AND brand_id = @citroen_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'BlueHDI 100', 'BHY', '1.6 BlueHDI', 'Diesel', 100, 254, 2014, 2018),
(@model_id, 'PureTech 110', 'HNZ', '1.2 PureTech Turbo', 'Gasolina', 110, 205, 2014, 2018);

SET @model_id = (SELECT id FROM car_models WHERE name = 'C5 Aircross' AND brand_id = @citroen_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'BlueHDI 130', 'YH01', '1.5 BlueHDI', 'Diesel', 130, 300, 2018, 2024),
(@model_id, 'PHEV 225', '5G06', '1.6 e-EAT8', 'Híbrido Ench.', 225, 360, 2020, 2024);

-- ------------------------------------------
-- 5. FURGONETAS Y MICROS (Jumpy, Jumper, Ami, Expert, Boxer)
-- ------------------------------------------
SET @model_jumpy = (SELECT id FROM car_models WHERE name = 'Jumpy' AND brand_id = @citroen_id);
SET @model_jumper = (SELECT id FROM car_models WHERE name = 'Jumper' AND brand_id = @citroen_id);
SET @model_expert = (SELECT id FROM car_models WHERE name = 'Expert' AND brand_id = @peugeot_id);
SET @model_boxer = (SELECT id FROM car_models WHERE name = 'Boxer' AND brand_id = @peugeot_id);
SET @model_ami = (SELECT id FROM car_models WHERE name = 'Ami' AND brand_id = @citroen_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_jumpy, 'BlueHDI 120', 'AHK', '2.0 BlueHDI', 'Diesel', 122, 340, 2016, 2024),
(@model_expert, 'HDI 120', 'RHK', '2.0 HDI', 'Diesel', 120, 300, 2007, 2016),
(@model_jumper, 'HDI 130', '4HV', '2.2 HDI', 'Diesel', 130, 320, 2006, 2014),
(@model_boxer, 'BlueHDI 140', 'YMR', '2.2 BlueHDI', 'Diesel', 140, 340, 2019, 2024),
(@model_ami, '100% Eléctrico', 'Elec', 'Motor Eléctrico', 'Eléctrico', 8, 40, 2020, 2024);

-- ==========================================
-- VERSIONES DS AUTOMOBILES Y OPEL
-- Todos los modelos cubiertos sin excepción
-- ==========================================

-- ------------------------------------------
-- 1. DS AUTOMOBILES
-- ------------------------------------------
SET @ds_id = (SELECT id FROM car_brands WHERE name = 'DS Automobiles');

-- DS 3
SET @model_id = (SELECT id FROM car_models WHERE name = 'DS 3' AND brand_id = @ds_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Sport Chic', '5FX', '1.6 THP', 'Gasolina', 156, 240, 2010, 2015),
(@model_id, 'BlueHDi 100', 'BHY', '1.6 BlueHDi', 'Diesel', 100, 254, 2015, 2019);

-- DS 3 Crossback
SET @model_id = (SELECT id FROM car_models WHERE name = 'DS 3 Crossback' AND brand_id = @ds_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'E-Tense', 'Elec', 'Motor Eléctrico', 'Eléctrico', 136, 260, 2019, 2024),
(@model_id, 'PureTech 130', 'HN05', '1.2 PureTech', 'Gasolina', 130, 230, 2019, 2024);

-- DS 4
SET @model_id = (SELECT id FROM car_models WHERE name = 'DS 4' AND brand_id = @ds_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'E-Tense 225', '5G06', '1.6 PHEV', 'Híbrido Ench.', 225, 360, 2021, 2024),
(@model_id, 'BlueHDi 130', 'YH01', '1.5 BlueHDi', 'Diesel', 130, 300, 2021, 2024);

-- DS 7 Crossback
SET @model_id = (SELECT id FROM car_models WHERE name = 'DS 7 Crossback' AND brand_id = @ds_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'E-Tense 4x4 300', '5G06', '1.6 PHEV 4x4', 'Híbrido Ench.', 300, 520, 2019, 2024),
(@model_id, 'BlueHDi 180', 'AHW', '2.0 BlueHDi', 'Diesel', 180, 400, 2018, 2024);

-- DS 9
SET @model_id = (SELECT id FROM car_models WHERE name = 'DS 9' AND brand_id = @ds_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'E-Tense 250', '5G06', '1.6 PHEV', 'Híbrido Ench.', 250, 360, 2021, 2024);


-- ------------------------------------------
-- 2. OPEL (GM, Isuzu, Fiat y PSA Mix)
-- ------------------------------------------
SET @opel_id = (SELECT id FROM car_brands WHERE name = 'Opel');

-- Corsa
SET @model_id = (SELECT id FROM car_models WHERE name = 'Corsa' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 16V (Corsa C)', 'Z12XE', '1.2 16V', 'Gasolina', 75, 110, 2000, 2006),
(@model_id, '1.3 CDTI (Corsa D)', 'Z13DTJ', '1.3 CDTI (Fiat)', 'Diesel', 75, 170, 2006, 2014),
(@model_id, 'Corsa-e (Corsa F)', 'Elec', 'Motor Eléctrico', 'Eléctrico', 136, 260, 2019, 2024);

-- Astra
SET @model_id = (SELECT id FROM car_models WHERE name = 'Astra' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 DTI (Astra G)', 'Y20DTH', '2.0 DTI', 'Diesel', 100, 230, 1999, 2004),
(@model_id, '1.9 CDTI (Astra H)', 'Z19DTH', '1.9 CDTI 16V (Fiat)', 'Diesel', 150, 320, 2004, 2010),
(@model_id, '1.4 Turbo (Astra K)', 'B14XFT', '1.4 Turbo', 'Gasolina', 150, 245, 2015, 2021);

-- Insignia
SET @model_id = (SELECT id FROM car_models WHERE name = 'Insignia' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 CDTI', 'A20DTH', '2.0 CDTI', 'Diesel', 160, 350, 2008, 2013),
(@model_id, 'OPC', 'A28NER', '2.8 V6 Turbo', 'Gasolina', 325, 435, 2009, 2017);

-- Mokka
SET @model_id = (SELECT id FROM car_models WHERE name = 'Mokka' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 CDTI', 'B16DTH', '1.6 CDTI', 'Diesel', 136, 320, 2015, 2019),
(@model_id, 'Mokka-e', 'Elec', 'Motor Eléctrico', 'Eléctrico', 136, 260, 2020, 2024);

-- Crossland (Base PSA)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Crossland' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 Turbo', 'EB2ADTS', '1.2 PureTech', 'Gasolina', 130, 230, 2017, 2024);

-- Grandland (Base PSA)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Grandland' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 CDTI', 'DV5RC', '1.5 BlueHDi', 'Diesel', 130, 300, 2018, 2024);

-- Zafira
SET @model_id = (SELECT id FROM car_models WHERE name = 'Zafira' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 DTI (Zafira A)', 'Y20DTH', '2.0 DTI', 'Diesel', 100, 230, 2000, 2005),
(@model_id, '1.9 CDTI (Zafira B)', 'Z19DT', '1.9 CDTI 8V', 'Diesel', 120, 280, 2005, 2011);

-- Meriva
SET @model_id = (SELECT id FROM car_models WHERE name = 'Meriva' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.7 CDTI', 'Z17DTH', '1.7 CDTI (Isuzu)', 'Diesel', 100, 240, 2003, 2010),
(@model_id, '1.4 Twinport', 'Z14XEP', '1.4 16V', 'Gasolina', 90, 125, 2004, 2010);

-- Combo
SET @model_id = (SELECT id FROM car_models WHERE name = 'Combo' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.3 CDTI', 'Z13DTJ', '1.3 CDTI', 'Diesel', 75, 170, 2004, 2011),
(@model_id, '1.5 TD', 'DV5RD', '1.5 BlueHDi (PSA)', 'Diesel', 102, 250, 2018, 2024);

-- Vivaro
SET @model_id = (SELECT id FROM car_models WHERE name = 'Vivaro' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 DI / DTI', 'F9Q', '1.9 dCi (Renault)', 'Diesel', 100, 240, 2001, 2006),
(@model_id, '2.0 CDTI', 'M9R', '2.0 dCi (Renault)', 'Diesel', 114, 290, 2006, 2014);

-- Movano
SET @model_id = (SELECT id FROM car_models WHERE name = 'Movano' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.3 CDTI', 'M9T', '2.3 dCi (Renault)', 'Diesel', 125, 310, 2010, 2019);

-- Vectra
SET @model_id = (SELECT id FROM car_models WHERE name = 'Vectra' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 DTI 16V (Vectra B)', 'X20DTH', '2.0 DTI', 'Diesel', 100, 230, 1997, 2002),
(@model_id, '1.9 CDTI (Vectra C)', 'Z19DTH', '1.9 CDTI 16V', 'Diesel', 150, 320, 2004, 2008);

-- Omega
SET @model_id = (SELECT id FROM car_models WHERE name = 'Omega' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.5 TD', 'X25DT', '2.5 L6 (BMW M51)', 'Diesel', 130, 250, 1994, 2000),
(@model_id, '3.0 V6', 'X30XE', '3.0 V6 24V', 'Gasolina', 211, 270, 1994, 1999);

-- Tigra
SET @model_id = (SELECT id FROM car_models WHERE name = 'Tigra' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.4 16V', 'X14XE', '1.4 16V', 'Gasolina', 90, 125, 1994, 2000);

-- Calibra (Mito de los 90)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Calibra' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 16V', 'C20XE', '2.0 16V', 'Gasolina', 150, 196, 1990, 1994),
(@model_id, '2.0 16V Turbo', 'C20LET', '2.0 16V Turbo 4x4', 'Gasolina', 204, 280, 1992, 1997);

-- Adam
SET @model_id = (SELECT id FROM car_models WHERE name = 'Adam' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.4', 'A14XEL', '1.4 16V', 'Gasolina', 87, 130, 2012, 2019);

-- Karl
SET @model_id = (SELECT id FROM car_models WHERE name = 'Karl' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.0', 'B10XE', '1.0 12V', 'Gasolina', 75, 95, 2015, 2019);

-- Antara
SET @model_id = (SELECT id FROM car_models WHERE name = 'Antara' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 CDTI', 'Z20DMH', '2.0 CDTI', 'Diesel', 150, 320, 2006, 2010);

-- Frontera (Los auténticos 4x4)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Frontera' AND brand_id = @opel_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.8 TD', '4JB1T', '2.8 TD (Isuzu)', 'Diesel', 113, 242, 1995, 1998),
(@model_id, '2.2 DTI', 'X22DTH', '2.2 DTI 16V', 'Diesel', 115, 260, 1998, 2002);

-- ==========================================
-- VERSIONES FIAT, ABARTH Y ALFA ROMEO
-- Cobertura total del bloque italiano (38 modelos)
-- ==========================================

-- ------------------------------------------
-- 1. FIAT (21 Modelos)
-- ------------------------------------------
SET @fiat_id = (SELECT id FROM car_brands WHERE name = 'Fiat');

-- 500
SET @model_id = (SELECT id FROM car_models WHERE name = '500' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 8V Lounge', '169A4000', '1.2 FIRE', 'Gasolina', 69, 102, 2007, 2020),
(@model_id, '1.3 Multijet', '169A1000', '1.3 JTDm', 'Diesel', 75, 145, 2007, 2015),
(@model_id, '1.0 Hybrid', '46341162', '1.0 GSE FireFly', 'Híbrido', 70, 92, 2020, 2024);

-- 500X
SET @model_id = (SELECT id FROM car_models WHERE name = '500X' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 Multijet', '55260384', '1.6 JTDm', 'Diesel', 120, 320, 2014, 2021),
(@model_id, '1.4 MultiAir', '55263624', '1.4 Turbo', 'Gasolina', 140, 230, 2014, 2019);

-- 500L
SET @model_id = (SELECT id FROM car_models WHERE name = '500L' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.3 Multijet', '199B4000', '1.3 JTDm', 'Diesel', 85, 200, 2012, 2018),
(@model_id, '1.4 16V', '843A1000', '1.4 FIRE', 'Gasolina', 95, 127, 2012, 2020);

-- Panda (El rey de la montaña)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Panda' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 8V (Panda 169)', '188A4000', '1.2 FIRE', 'Gasolina', 60, 102, 2003, 2012),
(@model_id, '1.3 Multijet 4x4', '188A8000', '1.3 JTDm', 'Diesel', 70, 145, 2004, 2012),
(@model_id, '1.0 Hybrid (Panda 319)', '46341162', '1.0 FireFly', 'Híbrido', 70, 92, 2020, 2024);

-- Tipo
SET @model_id = (SELECT id FROM car_models WHERE name = 'Tipo' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 Multijet', '55260384', '1.6 JTDm', 'Diesel', 120, 320, 2015, 2022),
(@model_id, '1.4 16V', '843A1000', '1.4 FIRE', 'Gasolina', 95, 127, 2015, 2020);

-- Punto
SET @model_id = (SELECT id FROM car_models WHERE name = 'Punto' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 8V (Punto 188)', '188A4000', '1.2 FIRE', 'Gasolina', 60, 102, 1999, 2010),
(@model_id, '1.9 JTD', '188A2000', '1.9 JTD', 'Diesel', 80, 196, 1999, 2003);

-- Grande Punto
SET @model_id = (SELECT id FROM car_models WHERE name = 'Grande Punto' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.3 Multijet', '199A3000', '1.3 JTDm', 'Diesel', 90, 200, 2005, 2012),
(@model_id, '1.4 T-Jet', '198A4000', '1.4 Turbo', 'Gasolina', 120, 206, 2007, 2012);

-- Bravo
SET @model_id = (SELECT id FROM car_models WHERE name = 'Bravo' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 Multijet', '198A2000', '1.6 JTDm', 'Diesel', 120, 300, 2008, 2014),
(@model_id, '1.4 T-Jet', '198A1000', '1.4 Turbo', 'Gasolina', 150, 206, 2007, 2014);

-- Brava
SET @model_id = (SELECT id FROM car_models WHERE name = 'Brava' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 16V / SX', '182A4000', '1.6 16V', 'Gasolina', 103, 144, 1995, 2001),
(@model_id, '1.9 JTD', '182B4000', '1.9 JTD', 'Diesel', 105, 200, 1998, 2001);

-- Stilo
SET @model_id = (SELECT id FROM car_models WHERE name = 'Stilo' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 JTD 115', '192A1000', '1.9 JTD', 'Diesel', 115, 255, 2001, 2006),
(@model_id, '2.4 20V Abarth', '192A2000', '2.4 L5', 'Gasolina', 170, 221, 2001, 2006);

-- Ducato (La reina del transporte y autocaravanas)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Ducato' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.3 Multijet 130', 'F1AE0481N', '2.3 JTD', 'Diesel', 130, 320, 2006, 2014),
(@model_id, '2.8 JTD', '8140.43S', '2.8 JTD (Sofim)', 'Diesel', 128, 300, 2000, 2006);

-- Doblo
SET @model_id = (SELECT id FROM car_models WHERE name = 'Doblo' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.3 Multijet', '223A9000', '1.3 JTDm', 'Diesel', 85, 200, 2005, 2010),
(@model_id, '1.6 Multijet', '263A3000', '1.6 JTDm', 'Diesel', 105, 290, 2010, 2020);

-- Fiorino
SET @model_id = (SELECT id FROM car_models WHERE name = 'Fiorino' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.3 Multijet', '199A2000', '1.3 JTDm', 'Diesel', 75, 190, 2007, 2024);

-- Scudo (Hermanastra de PSA)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Scudo' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 Multijet 120', 'RHK', '2.0 HDi (PSA)', 'Diesel', 120, 300, 2007, 2016);

-- Talento (Hermanastra de Renault)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Talento' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 EcoJet', 'R9M', '1.6 dCi (Renault)', 'Diesel', 120, 320, 2016, 2020);

-- Multipla (Estética incomprendida, practicidad absoluta)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Multipla' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 JTD 115', '186A8000', '1.9 JTD', 'Diesel', 115, 203, 2001, 2010);

-- Uno (Leyenda ochentera)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Uno' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Turbo i.e.', '146A8.000', '1.4 Turbo', 'Gasolina', 118, 161, 1989, 1993),
(@model_id, '45 1.0', '156A2.000', '1.0 FIRE', 'Gasolina', 45, 74, 1985, 1995);

-- Seicento
SET @model_id = (SELECT id FROM car_models WHERE name = 'Seicento' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Sporting', '176B2.000', '1.1 FIRE', 'Gasolina', 54, 88, 1998, 2005);

-- Croma
SET @model_id = (SELECT id FROM car_models WHERE name = 'Croma' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 Multijet 150', '939A2000', '1.9 JTDm 16V', 'Diesel', 150, 320, 2005, 2010);

-- Ulysse
SET @model_id = (SELECT id FROM car_models WHERE name = 'Ulysse' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 JTD', 'RHW', '2.0 HDi (PSA)', 'Diesel', 109, 250, 2002, 2006);

-- 124 Spider (Base Mazda MX-5 ND con corazón italiano)
SET @model_id = (SELECT id FROM car_models WHERE name = '124 Spider' AND brand_id = @fiat_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.4 MultiAir', '55253268', '1.4 Turbo MultiAir', 'Gasolina', 140, 240, 2016, 2020);


-- ------------------------------------------
-- 2. ABARTH (4 Modelos)
-- ------------------------------------------
SET @abarth_id = (SELECT id FROM car_brands WHERE name = 'Abarth');

-- 595
SET @model_id = (SELECT id FROM car_models WHERE name = '595' AND brand_id = @abarth_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Turismo / Competizione', '312A3000', '1.4 T-Jet', 'Gasolina', 165, 230, 2012, 2024);

-- 695
SET @model_id = (SELECT id FROM car_models WHERE name = '695' AND brand_id = @abarth_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Biposto / Rivale', '312A3000', '1.4 T-Jet Alta Presión', 'Gasolina', 180, 250, 2014, 2024);

-- 124 Spider Abarth
SET @model_id = (SELECT id FROM car_models WHERE name = '124 Spider' AND brand_id = @abarth_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Scorpione', '55253268', '1.4 Turbo MultiAir', 'Gasolina', 170, 250, 2016, 2020);

-- Punto Abarth (Grande Punto / Evo)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Punto' AND brand_id = @abarth_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Supersport (Punto Evo)', '955A8000', '1.4 MultiAir Turbo', 'Gasolina', 180, 270, 2010, 2014),
(@model_id, '1.4 T-Jet (Grande Punto)', '199A8000', '1.4 T-Jet', 'Gasolina', 155, 230, 2007, 2010);


-- ------------------------------------------
-- 3. ALFA ROMEO (13 Modelos)
-- ------------------------------------------
SET @alfa_id = (SELECT id FROM car_brands WHERE name = 'Alfa Romeo');

-- Giulia
SET @model_id = (SELECT id FROM car_models WHERE name = 'Giulia' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.2 JTDm', '55266388', '2.2 Turbo Diesel', 'Diesel', 180, 450, 2016, 2024),
(@model_id, 'Quadrifoglio', '670050436', '2.9 V6 Biturbo (Ferrari)', 'Gasolina', 510, 600, 2016, 2024);

-- Stelvio
SET @model_id = (SELECT id FROM car_models WHERE name = 'Stelvio' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.2 JTDm Q4', '55266388', '2.2 Turbo Diesel', 'Diesel', 210, 470, 2017, 2024),
(@model_id, '2.0 Turbo Q4', '55273835', '2.0 GME', 'Gasolina', 280, 400, 2017, 2024);

-- Tonale
SET @model_id = (SELECT id FROM car_models WHERE name = 'Tonale' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 Hybrid MHEV', '46347813', '1.5 e-VGT', 'Híbrido', 160, 240, 2022, 2024),
(@model_id, '1.3 PHEV Q4', '46337540', '1.3 Plug-in Hybrid', 'Híbrido Ench.', 280, 270, 2022, 2024);

-- Giulietta
SET @model_id = (SELECT id FROM car_models WHERE name = 'Giulietta' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 JTDm', '940A5000', '2.0 JTDm', 'Diesel', 140, 350, 2010, 2020),
(@model_id, 'Quadrifoglio Verde', '940A1000', '1.750 TBi', 'Gasolina', 235, 340, 2010, 2016);

-- MiTo
SET @model_id = (SELECT id FROM car_models WHERE name = 'MiTo' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.4 MultiAir TB', '955A8000', '1.4 Turbo MultiAir', 'Gasolina', 170, 250, 2009, 2018),
(@model_id, '1.3 JTDm', '199B1000', '1.3 JTDm', 'Diesel', 95, 200, 2008, 2018);

-- 159 (La berlina con la mirada más agresiva)
SET @model_id = (SELECT id FROM car_models WHERE name = '159' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 JTDm 150', '939A2000', '1.9 JTDm 16V', 'Diesel', 150, 320, 2005, 2011),
(@model_id, '2.4 JTDm Q4', '939A3000', '2.4 L5 JTDm', 'Diesel', 210, 400, 2007, 2011),
(@model_id, '1.750 TBi', '939B1000', '1.750 Turbo', 'Gasolina', 200, 320, 2009, 2011);

-- 156
SET @model_id = (SELECT id FROM car_models WHERE name = '156' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 JTD', 'AR32302', '1.9 JTD 8V', 'Diesel', 105, 255, 1997, 2001),
(@model_id, 'GTA', '932A000', '3.2 V6 24V (Busso)', 'Gasolina', 250, 300, 2002, 2005);

-- 147
SET @model_id = (SELECT id FROM car_models WHERE name = '147' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 JTD 115', '937A2000', '1.9 JTD', 'Diesel', 115, 275, 2001, 2005),
(@model_id, 'GTA', '932A000', '3.2 V6 24V (Busso)', 'Gasolina', 250, 300, 2002, 2005),
(@model_id, '2.0 Twin Spark', 'AR32310', '2.0 TS 16V', 'Gasolina', 150, 181, 2000, 2010);

-- GT
SET @model_id = (SELECT id FROM car_models WHERE name = 'GT' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 JTDm', '937A5000', '1.9 JTDm 16V', 'Diesel', 150, 305, 2003, 2010),
(@model_id, '3.2 V6', '932A000', '3.2 V6 (Busso)', 'Gasolina', 240, 289, 2003, 2007);

-- Brera
SET @model_id = (SELECT id FROM car_models WHERE name = 'Brera' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.4 JTDm', '939A3000', '2.4 L5 JTDm', 'Diesel', 200, 400, 2006, 2010),
(@model_id, '3.2 JTS Q4', '939A000', '3.2 V6 (GM/Alfa)', 'Gasolina', 260, 322, 2006, 2010);

-- Spider
SET @model_id = (SELECT id FROM car_models WHERE name = 'Spider' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 TS (Spider 916)', 'AR32310', '2.0 Twin Spark', 'Gasolina', 150, 181, 1995, 2005),
(@model_id, '2.4 JTDm (Spider 939)', '939A3000', '2.4 L5 JTDm', 'Diesel', 200, 400, 2006, 2010);

-- 4C (Chasis de carbono puro)
SET @model_id = (SELECT id FROM car_models WHERE name = '4C' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.750 TBi', '960A1000', '1.750 Turbo', 'Gasolina', 240, 350, 2013, 2020);

-- 8C Competizione (El unicornio)
SET @model_id = (SELECT id FROM car_models WHERE name = '8C Competizione' AND brand_id = @alfa_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '4.7 V8', 'F136 YC', '4.7 V8 (Maserati/Ferrari)', 'Gasolina', 450, 470, 2007, 2010);

-- ==========================================
-- VERSIONES LANCIA Y JEEP (La era FCA)
-- ==========================================

-- ------------------------------------------
-- 1. LANCIA (7 Modelos)
-- ------------------------------------------
SET @lancia_id = (SELECT id FROM car_brands WHERE name = 'Lancia');

-- Ypsilon
SET @model_id = (SELECT id FROM car_models WHERE name = 'Ypsilon' AND brand_id = @lancia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 8V (Base Fiat 500)', '169A4000', '1.2 FIRE', 'Gasolina', 69, 102, 2011, 2024),
(@model_id, '1.3 Multijet', '199B1000', '1.3 JTDm', 'Diesel', 95, 200, 2011, 2018);

-- Delta (El mito y el moderno)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Delta' AND brand_id = @lancia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'HF Integrale 16V', '831D5.000', '2.0 Turbo 16V', 'Gasolina', 200, 298, 1989, 1993),
(@model_id, '1.6 Multijet (Delta III)', '198A2000', '1.6 JTDm', 'Diesel', 120, 300, 2008, 2014),
(@model_id, '1.4 T-Jet', '198A4000', '1.4 Turbo', 'Gasolina', 120, 206, 2008, 2014);

-- Thema (El clásico Ferrari y el Clon Chrysler)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Thema' AND brand_id = @lancia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '8.32 (Motor Ferrari)', 'F105L', '2.9 V8 32V', 'Gasolina', 215, 285, 1986, 1992),
(@model_id, '3.0 V6 CRD (Base 300C)', 'EXF', '3.0 V6 Diesel (VM Motori)', 'Diesel', 239, 550, 2011, 2014);

-- Musa (Clon del Fiat Idea)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Musa' AND brand_id = @lancia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.3 Multijet', '199A3000', '1.3 JTDm', 'Diesel', 90, 200, 2004, 2012);

-- Voyager (Clon Chrysler Town & Country)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Voyager' AND brand_id = @lancia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.8 CRD', 'ENS', '2.8 CRD (VM Motori)', 'Diesel', 163, 360, 2011, 2015);

-- Phedra (Eurovan PSA/Fiat)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Phedra' AND brand_id = @lancia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.2 JTD', '4HW', '2.2 HDi (PSA)', 'Diesel', 128, 314, 2002, 2006);

-- Lybra
SET @model_id = (SELECT id FROM car_models WHERE name = 'Lybra' AND brand_id = @lancia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 JTD', 'AR32302', '1.9 JTD', 'Diesel', 105, 255, 1999, 2005),
(@model_id, '2.4 JTD', '839A6000', '2.4 L5 JTD', 'Diesel', 150, 305, 2002, 2005);


-- ------------------------------------------
-- 2. JEEP (8 Modelos - El Frankenstein de los motores)
-- ------------------------------------------
SET @jeep_id = (SELECT id FROM car_brands WHERE name = 'Jeep');

-- Wrangler
SET @model_id = (SELECT id FROM car_models WHERE name = 'Wrangler' AND brand_id = @jeep_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '4.0 L6 (TJ)', 'ERH', '4.0 6 Cilindros en Línea', 'Gasolina', 177, 296, 1996, 2006),
(@model_id, '2.8 CRD (JK)', 'ENS', '2.8 CRD (VM Motori)', 'Diesel', 200, 460, 2007, 2018),
(@model_id, '4xe PHEV (JL)', 'EC1', '2.0 Turbo PHEV', 'Híbrido Ench.', 380, 637, 2021, 2024);

-- Cherokee
SET @model_id = (SELECT id FROM car_models WHERE name = 'Cherokee' AND brand_id = @jeep_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '4.0 L6 (XJ)', 'ERH', '4.0 6 Cilindros', 'Gasolina', 190, 296, 1987, 2001),
(@model_id, '2.5 TD (XJ)', 'ENC', '2.5 TD (VM Motori - Culatines)', 'Diesel', 115, 278, 1995, 2001),
(@model_id, '2.0 CRD (KL)', 'EBT', '2.0 Multijet (Fiat)', 'Diesel', 140, 350, 2014, 2018);

-- Grand Cherokee
SET @model_id = (SELECT id FROM car_models WHERE name = 'Grand Cherokee' AND brand_id = @jeep_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '3.1 TD (WJ)', 'EXA', '3.1 TD 5 Cil (VM Motori)', 'Diesel', 140, 384, 1999, 2001),
(@model_id, '2.7 CRD (WJ)', 'ENF', '2.7 CRD (Mercedes OM612)', 'Diesel', 163, 400, 2001, 2004),
(@model_id, '3.0 V6 CRD (WK2)', 'EXF', '3.0 V6 CRD (VM Motori)', 'Diesel', 250, 570, 2011, 2021);

-- Compass
SET @model_id = (SELECT id FROM car_models WHERE name = 'Compass' AND brand_id = @jeep_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 CRD (MK49)', 'ECD', '2.0 TDI (Volkswagen BKD)', 'Diesel', 140, 310, 2006, 2010),
(@model_id, '1.6 Multijet (MP)', '55260384', '1.6 JTDm (Fiat)', 'Diesel', 120, 320, 2017, 2024);

-- Renegade
SET @model_id = (SELECT id FROM car_models WHERE name = 'Renegade' AND brand_id = @jeep_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 Multijet', '55260384', '1.6 JTDm (Fiat)', 'Diesel', 120, 320, 2014, 2024),
(@model_id, '1.3 4xe', '46337540', '1.3 PHEV', 'Híbrido Ench.', 240, 270, 2020, 2024);

-- Avenger (La influencia Stellantis pura)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Avenger' AND brand_id = @jeep_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Eléctrico', 'Elec', 'Motor Eléctrico (PSA)', 'Eléctrico', 156, 260, 2023, 2024),
(@model_id, '1.2 Turbo', 'HN05', '1.2 PureTech (PSA)', 'Gasolina', 100, 205, 2023, 2024);

-- Gladiator
SET @model_id = (SELECT id FROM car_models WHERE name = 'Gladiator' AND brand_id = @jeep_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '3.0 EcoDiesel', 'EXF', '3.0 V6 CRD (VM Motori)', 'Diesel', 264, 600, 2021, 2024);

-- Commander
SET @model_id = (SELECT id FROM car_models WHERE name = 'Commander' AND brand_id = @jeep_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '3.0 CRD', 'EXL', '3.0 V6 CRD (Mercedes OM642)', 'Diesel', 218, 510, 2006, 2010);


-- ==========================================
-- VERSIONES CHRYSLER, DODGE, RAM Y MASERATI
-- Cobertura total de los 27 modelos (El club de los motores cruzados)
-- ==========================================

-- ------------------------------------------
-- 1. CHRYSLER (7 Modelos)
-- ------------------------------------------
SET @chrysler_id = (SELECT id FROM car_brands WHERE name = 'Chrysler');

-- 300C (El "Bentley" de barrio, con chasis y motor Mercedes)
SET @model_id = (SELECT id FROM car_models WHERE name = '300C' AND brand_id = @chrysler_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '3.0 CRD', 'EXL', '3.0 V6 CRD (Mercedes OM642)', 'Diesel', 218, 510, 2005, 2010),
(@model_id, '5.7 HEMI V8', 'EZB', '5.7 V8 HEMI', 'Gasolina', 340, 525, 2004, 2010);

-- Voyager y Grand Voyager
SET @model_voyager = (SELECT id FROM car_models WHERE name = 'Voyager' AND brand_id = @chrysler_id);
SET @model_gvoyager = (SELECT id FROM car_models WHERE name = 'Grand Voyager' AND brand_id = @chrysler_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_voyager, '2.5 CRD', 'ENC', '2.5 CRD (VM Motori)', 'Diesel', 143, 340, 2001, 2008),
(@model_gvoyager, '2.8 CRD', 'ENS', '2.8 CRD (VM Motori)', 'Diesel', 163, 360, 2004, 2011);

-- Pacifica
SET @model_id = (SELECT id FROM car_models WHERE name = 'Pacifica' AND brand_id = @chrysler_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '3.6 V6', 'ERC', '3.6 V6 Pentastar', 'Gasolina', 291, 355, 2017, 2024),
(@model_id, 'Hybrid', 'EH3', '3.6 V6 PHEV', 'Híbrido Ench.', 260, 312, 2017, 2024);

-- PT Cruiser (Diseño retro, corazón europeo)
SET @model_id = (SELECT id FROM car_models WHERE name = 'PT Cruiser' AND brand_id = @chrysler_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.2 CRD', 'EDJ', '2.2 CRD (Mercedes OM646)', 'Diesel', 121, 300, 2002, 2010),
(@model_id, '2.4 GT Turbo', 'EDV', '2.4 Turbo', 'Gasolina', 223, 332, 2003, 2010);

-- Crossfire (Básicamente un Mercedes SLK R170 con otro traje)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Crossfire' AND brand_id = @chrysler_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '3.2 V6', 'EGX', '3.2 V6 (Mercedes M112)', 'Gasolina', 218, 310, 2003, 2008);

-- Sebring
SET @model_id = (SELECT id FROM car_models WHERE name = 'Sebring' AND brand_id = @chrysler_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 CRD', 'ECD', '2.0 TDI (VW Iny-Bomba BKD)', 'Diesel', 140, 310, 2007, 2010);


-- ------------------------------------------
-- 2. DODGE (9 Modelos)
-- ------------------------------------------
SET @dodge_id = (SELECT id FROM car_brands WHERE name = 'Dodge');

-- Charger y Challenger (El músculo americano)
SET @model_charger = (SELECT id FROM car_models WHERE name = 'Charger' AND brand_id = @dodge_id);
SET @model_challenger = (SELECT id FROM car_models WHERE name = 'Challenger' AND brand_id = @dodge_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_charger, 'SRT Hellcat', 'ESD', '6.2 V8 Supercharged', 'Gasolina', 717, 881, 2015, 2023),
(@model_charger, '5.7 R/T', 'EZH', '5.7 V8 HEMI', 'Gasolina', 375, 536, 2011, 2023),
(@model_challenger, '3.6 SXT', 'ERB', '3.6 V6 Pentastar', 'Gasolina', 305, 363, 2011, 2023),
(@model_challenger, 'SRT 392', 'ESG', '6.4 V8 HEMI Apache', 'Gasolina', 485, 644, 2015, 2023);

-- Durango
SET @model_id = (SELECT id FROM car_models WHERE name = 'Durango' AND brand_id = @dodge_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '5.7 HEMI', 'EZH', '5.7 V8 HEMI', 'Gasolina', 360, 529, 2011, 2024);

-- Journey (El hermano gemelo del Fiat Freemont)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Journey' AND brand_id = @dodge_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 CRD', 'ECE', '2.0 TDI (VW BKD)', 'Diesel', 140, 310, 2008, 2011),
(@model_id, '2.4', 'ED3', '2.4 VVT', 'Gasolina', 170, 220, 2008, 2020);

-- Viper (La bestia indomable)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Viper' AND brand_id = @dodge_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '8.4 SRT', 'EWP', '8.4 V10', 'Gasolina', 645, 814, 2013, 2017);

-- Caliber y Avenger (Con sorpresa alemana)
SET @model_caliber = (SELECT id FROM car_models WHERE name = 'Caliber' AND brand_id = @dodge_id);
SET @model_avenger = (SELECT id FROM car_models WHERE name = 'Avenger' AND brand_id = @dodge_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_caliber, '2.0 CRD', 'ECD', '2.0 TDI (VW Iny-Bomba BKD)', 'Diesel', 140, 310, 2006, 2011),
(@model_avenger, '2.0 CRD', 'ECD', '2.0 TDI (VW Iny-Bomba BKD)', 'Diesel', 140, 310, 2007, 2011);

-- Nitro
SET @model_id = (SELECT id FROM car_models WHERE name = 'Nitro' AND brand_id = @dodge_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.8 CRD', 'ENS', '2.8 CRD (VM Motori)', 'Diesel', 177, 460, 2007, 2011);

-- Dart (El injerto de Alfa Romeo)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Dart' AND brand_id = @dodge_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.4 MultiAir', 'EAF', '1.4 Turbo (Fiat)', 'Gasolina', 160, 250, 2013, 2016);


-- ------------------------------------------
-- 3. RAM (4 Modelos)
-- ------------------------------------------
SET @ram_id = (SELECT id FROM car_brands WHERE name = 'RAM');

-- 1500
SET @model_id = (SELECT id FROM car_models WHERE name = '1500' AND brand_id = @ram_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '5.7 HEMI', 'EZH', '5.7 V8 HEMI', 'Gasolina', 395, 556, 2011, 2024),
(@model_id, '3.0 EcoDiesel', 'EXF', '3.0 V6 CRD (VM Motori)', 'Diesel', 240, 569, 2014, 2024);

-- 2500 y 3500 (Territorio Cummins)
SET @model_2500 = (SELECT id FROM car_models WHERE name = '2500' AND brand_id = @ram_id);
SET @model_3500 = (SELECT id FROM car_models WHERE name = '3500' AND brand_id = @ram_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_2500, '6.7 Cummins', 'ETK', '6.7 L6 Turbo Diesel', 'Diesel', 370, 1085, 2010, 2024),
(@model_3500, '6.7 Cummins HO', 'ETL', '6.7 L6 High Output', 'Diesel', 420, 1458, 2013, 2024);

-- ProMaster (Básicamente una Fiat Ducato remarcada)
SET @model_id = (SELECT id FROM car_models WHERE name = 'ProMaster' AND brand_id = @ram_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '3.6 V6', 'ERB', '3.6 V6 Pentastar', 'Gasolina', 280, 353, 2014, 2024);


-- ------------------------------------------
-- 4. MASERATI (7 Modelos - Corazones de Maranello)
-- ------------------------------------------
SET @maserati_id = (SELECT id FROM car_brands WHERE name = 'Maserati');

-- Ghibli y Levante (Diesel y Gasolina)
SET @model_ghibli = (SELECT id FROM car_models WHERE name = 'Ghibli' AND brand_id = @maserati_id);
SET @model_levante = (SELECT id FROM car_models WHERE name = 'Levante' AND brand_id = @maserati_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ghibli, '3.0 V6 Diesel', 'M156D', '3.0 V6 (VM Motori)', 'Diesel', 275, 600, 2013, 2020),
(@model_ghibli, 'S Q4', 'M156E', '3.0 V6 Biturbo (Ferrari)', 'Gasolina', 430, 580, 2013, 2023),
(@model_levante, '3.0 V6 Diesel', 'M156D', '3.0 V6 (VM Motori)', 'Diesel', 275, 600, 2016, 2020),
(@model_levante, 'Trofeo', 'M156V8', '3.8 V8 Biturbo (Ferrari)', 'Gasolina', 580, 730, 2018, 2024);

-- Quattroporte
SET @model_id = (SELECT id FROM car_models WHERE name = 'Quattroporte' AND brand_id = @maserati_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '4.2 V8 (M139)', 'F136', '4.2 V8 Atmosférico (Ferrari)', 'Gasolina', 400, 451, 2003, 2012),
(@model_id, 'GTS', 'M156', '3.8 V8 Biturbo', 'Gasolina', 530, 710, 2013, 2024);

-- GranTurismo y GranCabrio (El canto de los cisnes atmosféricos)
SET @model_gt = (SELECT id FROM car_models WHERE name = 'GranTurismo' AND brand_id = @maserati_id);
SET @model_gc = (SELECT id FROM car_models WHERE name = 'GranCabrio' AND brand_id = @maserati_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_gt, '4.7 V8 S', 'F136Y', '4.7 V8 (Ferrari)', 'Gasolina', 460, 520, 2007, 2019),
(@model_gc, '4.7 V8 Sport', 'F136Y', '4.7 V8 (Ferrari)', 'Gasolina', 460, 520, 2010, 2019);

-- Grecale (La nueva era mild-hybrid y Nettuno)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Grecale' AND brand_id = @maserati_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'GT', '670053115', '2.0 L4 MHEV', 'Híbrido', 300, 450, 2022, 2024),
(@model_id, 'Trofeo', 'Nettuno V6', '3.0 V6 Biturbo', 'Gasolina', 530, 620, 2022, 2024);

-- MC20 (El superdeportivo)
SET @model_id = (SELECT id FROM car_models WHERE name = 'MC20' AND brand_id = @maserati_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Nettuno', 'Nettuno', '3.0 V6 Biturbo', 'Gasolina', 630, 730, 2020, 2024);

-- ==========================================
-- VERSIONES RENAULT, DACIA Y ALPINE
-- El imperio del K9K y la Alianza
-- ==========================================

-- ------------------------------------------
-- 1. RENAULT (24 Modelos)
-- ------------------------------------------
SET @renault_id = (SELECT id FROM car_brands WHERE name = 'Renault');

-- Clio (El rey de la ciudad)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Clio' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 16V (Clio II)', 'D4F', '1.2 16V', 'Gasolina', 75, 105, 2001, 2012),
(@model_id, '1.5 dCi (Clio III)', 'K9K', '1.5 dCi', 'Diesel', 85, 200, 2005, 2012),
(@model_id, '2.0 RS (Clio III)', 'F4R', '2.0 16V Sport', 'Gasolina', 200, 215, 2006, 2012),
(@model_id, '1.2 TCe (Clio IV)', 'H5F', '1.2 Turbo', 'Gasolina', 120, 190, 2013, 2019);

-- Megane (El compacto superventas)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Megane' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 dCi (Megane II)', 'F9Q', '1.9 dCi', 'Diesel', 120, 300, 2002, 2008),
(@model_id, '1.5 dCi (Megane III)', 'K9K', '1.5 dCi', 'Diesel', 110, 240, 2008, 2016),
(@model_id, 'RS 2.0T (Megane III)', 'F4Rt', '2.0 Turbo RS', 'Gasolina', 265, 360, 2009, 2016),
(@model_id, '1.3 TCe (Megane IV)', 'H5H', '1.3 Turbo (con Mercedes)', 'Gasolina', 140, 240, 2018, 2024);

-- Captur
SET @model_id = (SELECT id FROM car_models WHERE name = 'Captur' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '0.9 TCe', 'H4Bt', '0.9 Turbo 3 cil', 'Gasolina', 90, 135, 2013, 2019),
(@model_id, '1.5 dCi', 'K9K', '1.5 dCi', 'Diesel', 110, 260, 2015, 2019),
(@model_id, 'E-Tech PHEV', 'H4M', '1.6 Híbrido Ench.', 'Híbrido Ench.', 160, 205, 2020, 2024);

-- Austral
SET @model_id = (SELECT id FROM car_models WHERE name = 'Austral' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'E-Tech Full Hybrid', 'HR12', '1.2 Turbo Híbrido', 'Híbrido', 200, 410, 2022, 2024),
(@model_id, 'Mild Hybrid 140', 'H5H', '1.3 TCe MHEV', 'Híbrido', 140, 260, 2022, 2024);

-- Arkana
SET @model_id = (SELECT id FROM car_models WHERE name = 'Arkana' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'E-Tech Hybrid', 'H4M', '1.6 Híbrido', 'Híbrido', 145, 250, 2021, 2024),
(@model_id, 'TCe 140 EDC', 'H5H', '1.3 TCe MHEV', 'Híbrido', 140, 260, 2021, 2024);

-- Zoe (Pionero eléctrico)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Zoe' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'R110', '5AQ 601', 'Motor Eléctrico', 'Eléctrico', 108, 225, 2018, 2024),
(@model_id, 'R135', '5AQ 605', 'Motor Eléctrico', 'Eléctrico', 135, 245, 2019, 2024);

-- Twingo
SET @model_id = (SELECT id FROM car_models WHERE name = 'Twingo' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 8V (Twingo I)', 'D7F', '1.2 8V', 'Gasolina', 60, 93, 1996, 2007),
(@model_id, '0.9 TCe (Twingo III)', 'H4B', '0.9 Turbo Trasero', 'Gasolina', 90, 135, 2014, 2021);

-- Kangoo
SET @model_id = (SELECT id FROM car_models WHERE name = 'Kangoo' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 D (Kangoo I)', 'F8Q', '1.9 Atmosférico', 'Diesel', 65, 120, 1997, 2003),
(@model_id, '1.5 dCi (Kangoo II)', 'K9K', '1.5 dCi', 'Diesel', 90, 200, 2008, 2021);

-- Trafic
SET @model_id = (SELECT id FROM car_models WHERE name = 'Trafic' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 dCi', 'F9Q', '1.9 dCi', 'Diesel', 100, 240, 2001, 2006),
(@model_id, '2.0 dCi', 'M9R', '2.0 dCi', 'Diesel', 115, 290, 2006, 2014),
(@model_id, '1.6 dCi TwinTurbo', 'R9M', '1.6 dCi Biturbo', 'Diesel', 140, 340, 2014, 2024);

-- Master
SET @model_id = (SELECT id FROM car_models WHERE name = 'Master' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.5 dCi', 'G9U', '2.5 dCi', 'Diesel', 120, 300, 2003, 2010),
(@model_id, '2.3 dCi', 'M9T', '2.3 dCi', 'Diesel', 130, 330, 2010, 2024);

-- Laguna (El que daba trabajo a los talleres)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Laguna' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 dCi 120 (Laguna II)', 'F9Q', '1.9 dCi (Cuidado Turbo)', 'Diesel', 120, 300, 2001, 2005),
(@model_id, '2.0 dCi 150 (Laguna III)', 'M9R', '2.0 dCi (Cadena)', 'Diesel', 150, 340, 2007, 2015),
(@model_id, '3.0 V6 24V', 'L7X', '3.0 V6 (PSA/Renault)', 'Gasolina', 207, 285, 2001, 2005);

-- Espace
SET @model_id = (SELECT id FROM car_models WHERE name = 'Espace' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.2 dCi (Espace IV)', 'G9T', '2.2 dCi', 'Diesel', 150, 320, 2002, 2006),
(@model_id, '1.6 dCi TwinTurbo (Espace V)', 'R9M', '1.6 dCi Biturbo', 'Diesel', 160, 380, 2015, 2019);

-- Scenic & Grand Scenic
SET @model_scenic = (SELECT id FROM car_models WHERE name = 'Scenic' AND brand_id = @renault_id);
SET @model_gscenic = (SELECT id FROM car_models WHERE name = 'Grand Scenic' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_scenic, '1.9 dCi (Scenic II)', 'F9Q', '1.9 dCi', 'Diesel', 120, 300, 2003, 2006),
(@model_scenic, '1.5 dCi (Scenic III)', 'K9K', '1.5 dCi', 'Diesel', 110, 240, 2009, 2016),
(@model_gscenic, '1.3 TCe (Scenic IV)', 'H5H', '1.3 Turbo', 'Gasolina', 140, 240, 2018, 2022);

-- Kadjar
SET @model_id = (SELECT id FROM car_models WHERE name = 'Kadjar' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 dCi', 'K9K', '1.5 dCi', 'Diesel', 110, 260, 2015, 2022),
(@model_id, '1.2 TCe', 'H5F', '1.2 Turbo', 'Gasolina', 130, 205, 2015, 2018);

-- Koleos
SET @model_id = (SELECT id FROM car_models WHERE name = 'Koleos' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 dCi 4x4', 'M9R', '2.0 dCi (Nissan)', 'Diesel', 150, 320, 2008, 2015);

-- Talisman
SET @model_id = (SELECT id FROM car_models WHERE name = 'Talisman' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 dCi', 'R9M', '1.6 dCi TwinTurbo', 'Diesel', 160, 380, 2015, 2018),
(@model_id, '1.3 TCe', 'H5H', '1.3 Turbo', 'Gasolina', 160, 270, 2019, 2022);

-- R5 (El Clásico y el Moderno Eléctrico)
SET @model_id = (SELECT id FROM car_models WHERE name = 'R5' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'GT Turbo', 'C1J', '1.4 Turbo (Carburación)', 'Gasolina', 120, 165, 1985, 1991),
(@model_id, 'E-Tech 100% Eléctrico', 'Elec', 'Motor Eléctrico', 'Eléctrico', 150, 245, 2024, 2025);

-- R4 (El Cuatro Latas)
SET @model_id = (SELECT id FROM car_models WHERE name = 'R4' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'GTL 1.1', 'C1E', '1.1 Cléon-Fonte', 'Gasolina', 34, 74, 1978, 1992);

-- 19 (El 19 Dieciséis Válvulas)
SET @model_id = (SELECT id FROM car_models WHERE name = '19' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '16V', 'F7P', '1.8 16V', 'Gasolina', 137, 161, 1990, 1995),
(@model_id, '1.9 D', 'F8Q', '1.9 Atmosférico', 'Diesel', 65, 118, 1988, 1995);

-- 21
SET @model_id = (SELECT id FROM car_models WHERE name = '21' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 Turbo', 'J7R', '2.0 Turbo', 'Gasolina', 175, 270, 1987, 1993),
(@model_id, '2.1 TD', 'J8S', '2.1 Turbo Diesel', 'Diesel', 88, 181, 1986, 1994);

-- Express (La furgoneta clásica y la moderna)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Express' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 D (Clásica)', 'F8M', '1.6 Diesel Atmosférico', 'Diesel', 55, 102, 1985, 1994),
(@model_id, '1.5 Blue dCi (Van 2021)', 'K9K', '1.5 Blue dCi', 'Diesel', 95, 240, 2021, 2024);

-- Symbioz
SET @model_id = (SELECT id FROM car_models WHERE name = 'Symbioz' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'E-Tech Full Hybrid', 'H4M', '1.6 Híbrido', 'Híbrido', 145, 250, 2024, 2025);

-- Rafale
SET @model_id = (SELECT id FROM car_models WHERE name = 'Rafale' AND brand_id = @renault_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'E-Tech Full Hybrid', 'HR12', '1.2 Turbo Híbrido', 'Híbrido', 200, 410, 2024, 2025);


-- ------------------------------------------
-- 2. DACIA (8 Modelos - Base robusta Renault)
-- ------------------------------------------
SET @dacia_id = (SELECT id FROM car_brands WHERE name = 'Dacia');

-- Sandero y Sandero Stepway
SET @model_sandero = (SELECT id FROM car_models WHERE name = 'Sandero' AND brand_id = @dacia_id);
SET @model_stepway = (SELECT id FROM car_models WHERE name = 'Sandero Stepway' AND brand_id = @dacia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_sandero, '1.5 dCi', 'K9K', '1.5 dCi', 'Diesel', 75, 200, 2008, 2020),
(@model_sandero, '1.0 TCe ECO-G', 'H4D', '1.0 Turbo GLP', 'GLP / Gasolina', 100, 170, 2020, 2024),
(@model_stepway, '0.9 TCe', 'H4B', '0.9 Turbo', 'Gasolina', 90, 140, 2012, 2020),
(@model_stepway, '1.0 TCe ECO-G', 'H4D', '1.0 Turbo GLP', 'GLP / Gasolina', 100, 170, 2020, 2024);

-- Duster
SET @model_id = (SELECT id FROM car_models WHERE name = 'Duster' AND brand_id = @dacia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 dCi 4x4', 'K9K', '1.5 dCi', 'Diesel', 110, 240, 2010, 2024),
(@model_id, '1.6 16V', 'K4M', '1.6 16V Atmosférico', 'Gasolina', 105, 148, 2010, 2018),
(@model_id, '1.3 TCe', 'H5H', '1.3 Turbo', 'Gasolina', 150, 250, 2019, 2024);

-- Jogger
SET @model_id = (SELECT id FROM car_models WHERE name = 'Jogger' AND brand_id = @dacia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.0 TCe ECO-G', 'H4D', '1.0 Turbo GLP', 'GLP / Gasolina', 100, 170, 2022, 2024),
(@model_id, 'Hybrid 140', 'H4M', '1.6 Híbrido', 'Híbrido', 140, 205, 2023, 2024);

-- Logan
SET @model_id = (SELECT id FROM car_models WHERE name = 'Logan' AND brand_id = @dacia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.4 MPI', 'K7J', '1.4 8V Atmosférico', 'Gasolina', 75, 112, 2004, 2012),
(@model_id, '1.5 dCi', 'K9K', '1.5 dCi', 'Diesel', 70, 160, 2005, 2012);

-- Spring
SET @model_id = (SELECT id FROM car_models WHERE name = 'Spring' AND brand_id = @dacia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Electric 45', 'Elec', 'Motor Eléctrico', 'Eléctrico', 45, 125, 2021, 2024),
(@model_id, 'Electric 65', 'Elec', 'Motor Eléctrico', 'Eléctrico', 65, 113, 2023, 2024);

-- Lodgy
SET @model_id = (SELECT id FROM car_models WHERE name = 'Lodgy' AND brand_id = @dacia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 dCi', 'K9K', '1.5 dCi', 'Diesel', 110, 240, 2012, 2022),
(@model_id, '1.2 TCe', 'H5F', '1.2 Turbo', 'Gasolina', 115, 190, 2012, 2018);

-- Dokker
SET @model_id = (SELECT id FROM car_models WHERE name = 'Dokker' AND brand_id = @dacia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 dCi', 'K9K', '1.5 dCi', 'Diesel', 90, 200, 2012, 2021),
(@model_id, '1.6 MPI', 'K7M', '1.6 8V Atmosférico', 'Gasolina', 85, 134, 2012, 2015);


-- ------------------------------------------
-- 3. ALPINE (3 Modelos - Puro nervio francés)
-- ------------------------------------------
SET @alpine_id = (SELECT id FROM car_brands WHERE name = 'Alpine');

-- A110 (El Moderno)
SET @model_id = (SELECT id FROM car_models WHERE name = 'A110' AND brand_id = @alpine_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.8 TCe (Pure / Legende)', 'M5P', '1.8 Turbo', 'Gasolina', 252, 320, 2017, 2024),
(@model_id, '1.8 TCe (S / R)', 'M5P', '1.8 Turbo Alta Presión', 'Gasolina', 300, 340, 2019, 2024);

-- A290 (El R5 anabolizado)
SET @model_id = (SELECT id FROM car_models WHERE name = 'A290' AND brand_id = @alpine_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'GTS', 'Elec', 'Motor Eléctrico Ampere', 'Eléctrico', 220, 300, 2024, 2025);

-- A310 (El Clásico)
SET @model_id = (SELECT id FROM car_models WHERE name = 'A310' AND brand_id = @alpine_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.7 V6', 'PRV V6', '2.7 V6 PRV', 'Gasolina', 150, 204, 1976, 1984);

-- ==========================================
-- VERSIONES NISSAN, INFINITI Y DATSUN
-- El imperio del sol naciente y las sinergias
-- ==========================================

-- ------------------------------------------
-- 1. NISSAN (20 Modelos)
-- ------------------------------------------
SET @nissan_id = (SELECT id FROM car_brands WHERE name = 'Nissan');

-- Qashqai (El inventor del segmento SUV moderno)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Qashqai' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 dCi (J10/J11)', 'K9K', '1.5 dCi (Renault)', 'Diesel', 110, 240, 2007, 2018),
(@model_id, '1.6 dCi (J11)', 'R9M', '1.6 dCi (Renault)', 'Diesel', 130, 320, 2011, 2018),
(@model_id, '1.2 DIG-T (J11)', 'HRA2NDT', '1.2 Turbo', 'Gasolina', 115, 190, 2013, 2018),
(@model_id, '1.3 DIG-T MHEV (J12)', 'HR13DDT', '1.3 Turbo Mild Hybrid', 'Híbrido', 140, 240, 2021, 2024),
(@model_id, 'e-Power (J12)', 'KR15DDT', '1.5 e-Power (Eléctrico + Generador)', 'Híbrido', 190, 330, 2022, 2024);

-- Juke
SET @model_id = (SELECT id FROM car_models WHERE name = 'Juke' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 dCi (F15)', 'K9K', '1.5 dCi', 'Diesel', 110, 240, 2010, 2019),
(@model_id, '1.6 DIG-T Nismo', 'MR16DDT', '1.6 Turbo', 'Gasolina', 200, 250, 2013, 2019),
(@model_id, '1.0 DIG-T (F16)', 'HR10DDT', '1.0 Turbo 3 cil', 'Gasolina', 114, 200, 2019, 2024);

-- X-Trail
SET @model_id = (SELECT id FROM car_models WHERE name = 'X-Trail' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 dCi (T31)', 'M9R', '2.0 dCi', 'Diesel', 150, 320, 2007, 2014),
(@model_id, '1.6 dCi (T32)', 'R9M', '1.6 dCi', 'Diesel', 130, 320, 2014, 2018),
(@model_id, 'e-Power e-4ORCE (T33)', 'KR15DDT', '1.5 e-Power 4x4', 'Híbrido', 213, 525, 2022, 2024);

-- Micra
SET @model_id = (SELECT id FROM car_models WHERE name = 'Micra' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 16V (K12)', 'CR12DE', '1.2 16V', 'Gasolina', 80, 110, 2002, 2010),
(@model_id, '1.5 dCi (K12/K14)', 'K9K', '1.5 dCi', 'Diesel', 90, 200, 2005, 2019),
(@model_id, '0.9 IG-T (K14)', 'HR09DET', '0.9 Turbo 3 cil', 'Gasolina', 90, 140, 2017, 2020);

-- Leaf y Ariya (Eléctricos)
SET @model_leaf = (SELECT id FROM car_models WHERE name = 'Leaf' AND brand_id = @nissan_id);
SET @model_ariya = (SELECT id FROM car_models WHERE name = 'Ariya' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_leaf, '40 kWh (ZE1)', 'EM57', 'Motor Eléctrico', 'Eléctrico', 150, 320, 2017, 2024),
(@model_leaf, 'e+ 62 kWh', 'EM57', 'Motor Eléctrico', 'Eléctrico', 217, 340, 2019, 2024),
(@model_ariya, 'e-4ORCE 87 kWh', 'AM67', 'Motor Eléctrico Dual', 'Eléctrico', 306, 600, 2022, 2024);

-- Navara y Pathfinder
SET @model_navara = (SELECT id FROM car_models WHERE name = 'Navara' AND brand_id = @nissan_id);
SET @model_pathfinder = (SELECT id FROM car_models WHERE name = 'Pathfinder' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_navara, '2.5 dCi (D40)', 'YD25DDTi', '2.5 dCi', 'Diesel', 174, 403, 2005, 2015),
(@model_navara, '2.3 dCi Biturbo (D23)', 'YS23DDT', '2.3 dCi (Base Renault)', 'Diesel', 190, 450, 2015, 2024),
(@model_pathfinder, '2.5 dCi (R51)', 'YD25DDTi', '2.5 dCi', 'Diesel', 174, 403, 2005, 2012),
(@model_pathfinder, '3.0 V6 dCi', 'V9X', '3.0 V6 Turbo Diesel', 'Diesel', 231, 550, 2010, 2014);

-- Patrol y Terrano (Los todoterreno puros)
SET @model_patrol = (SELECT id FROM car_models WHERE name = 'Patrol' AND brand_id = @nissan_id);
SET @model_terrano = (SELECT id FROM car_models WHERE name = 'Terrano' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_patrol, '2.8 TD (GR Y60/Y61)', 'RD28T', '2.8 L6 Turbo Diesel', 'Diesel', 115, 235, 1988, 2000),
(@model_patrol, '3.0 Di (GR Y61)', 'ZD30DDTi', '3.0 L4 Turbo Diesel', 'Diesel', 158, 354, 2000, 2010),
(@model_terrano, '2.7 TD / TDi (Terrano II)', 'TD27TI', '2.7 L4 Turbo Diesel', 'Diesel', 125, 278, 1993, 2006);

-- Deportivos: GT-R, 370Z, 350Z, Skyline
SET @model_gtr = (SELECT id FROM car_models WHERE name = 'GT-R' AND brand_id = @nissan_id);
SET @model_370z = (SELECT id FROM car_models WHERE name = '370Z' AND brand_id = @nissan_id);
SET @model_350z = (SELECT id FROM car_models WHERE name = '350Z' AND brand_id = @nissan_id);
SET @model_skyline = (SELECT id FROM car_models WHERE name = 'Skyline' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_gtr, 'Godzilla (R35)', 'VR38DETT', '3.8 V6 Twin-Turbo', 'Gasolina', 550, 632, 2007, 2024),
(@model_370z, 'Nismo / Base (Z34)', 'VQ37VHR', '3.7 V6', 'Gasolina', 328, 363, 2009, 2020),
(@model_350z, 'Base (Z33)', 'VQ35DE', '3.5 V6', 'Gasolina', 280, 363, 2002, 2006),
(@model_350z, 'HR (Z33)', 'VQ35HR', '3.5 V6', 'Gasolina', 313, 358, 2007, 2008),
(@model_skyline, 'GT-R (R34)', 'RB26DETT', '2.6 L6 Twin-Turbo', 'Gasolina', 280, 392, 1999, 2002);

-- Almera y Primera
SET @model_almera = (SELECT id FROM car_models WHERE name = 'Almera' AND brand_id = @nissan_id);
SET @model_primera = (SELECT id FROM car_models WHERE name = 'Primera' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_almera, '1.5 16V (N16)', 'QG15DE', '1.5 16V', 'Gasolina', 90, 136, 2000, 2006),
(@model_almera, '2.2 Di', 'YD22DDT', '2.2 Direct Injection', 'Diesel', 110, 230, 2000, 2003),
(@model_primera, '1.9 dCi (P12)', 'F9Q', '1.9 dCi (Renault)', 'Diesel', 120, 300, 2002, 2006),
(@model_primera, '2.0 16V (P11)', 'SR20DE', '2.0 16V', 'Gasolina', 140, 181, 1996, 2001);

-- Furgonetas (Townstar, Primastar, Interstar) + Pulsar
SET @model_townstar = (SELECT id FROM car_models WHERE name = 'Townstar' AND brand_id = @nissan_id);
SET @model_primastar = (SELECT id FROM car_models WHERE name = 'Primastar' AND brand_id = @nissan_id);
SET @model_interstar = (SELECT id FROM car_models WHERE name = 'Interstar' AND brand_id = @nissan_id);
SET @model_pulsar = (SELECT id FROM car_models WHERE name = 'Pulsar' AND brand_id = @nissan_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_townstar, '1.3 DIG-T (Kangoo III)', 'H5Ht', '1.3 Turbo', 'Gasolina', 130, 240, 2021, 2024),
(@model_primastar, '2.0 dCi (Trafic II)', 'M9R', '2.0 dCi (Renault)', 'Diesel', 115, 290, 2006, 2014),
(@model_interstar, '2.5 dCi (Master II)', 'G9U', '2.5 dCi (Renault)', 'Diesel', 120, 300, 2002, 2010),
(@model_pulsar, '1.5 dCi (C13)', 'K9K', '1.5 dCi (Renault)', 'Diesel', 110, 260, 2014, 2018);


-- ------------------------------------------
-- 2. INFINITI (9 Modelos - Ojo a los motores Mercedes)
-- ------------------------------------------
SET @infiniti_id = (SELECT id FROM car_brands WHERE name = 'Infiniti');

-- Q30 y QX30 (Hermanos del Mercedes Clase A / GLA)
SET @model_q30 = (SELECT id FROM car_models WHERE name = 'Q30' AND brand_id = @infiniti_id);
SET @model_qx30 = (SELECT id FROM car_models WHERE name = 'QX30' AND brand_id = @infiniti_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_q30, '2.2d (OM651)', 'OM651', '2.2 Turbodiesel (Mercedes)', 'Diesel', 170, 350, 2015, 2019),
(@model_q30, '2.0t (M270)', 'M270', '2.0 Turbo (Mercedes)', 'Gasolina', 211, 350, 2015, 2019),
(@model_qx30, '2.2d AWD', 'OM651', '2.2 Turbodiesel (Mercedes)', 'Diesel', 170, 350, 2016, 2019);

-- Q50 y Q60
SET @model_q50 = (SELECT id FROM car_models WHERE name = 'Q50' AND brand_id = @infiniti_id);
SET @model_q60 = (SELECT id FROM car_models WHERE name = 'Q60' AND brand_id = @infiniti_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_q50, '2.2d (OM651)', 'OM651', '2.2 Turbodiesel (Mercedes)', 'Diesel', 170, 400, 2013, 2019),
(@model_q50, '3.5 Hybrid', 'VQ35HR', '3.5 V6 Híbrido', 'Híbrido', 364, 546, 2013, 2019),
(@model_q60, '3.0t 400 Sport', 'VR30DDTT', '3.0 V6 Twin-Turbo', 'Gasolina', 405, 475, 2016, 2022);

-- Q70, QX50, QX60, QX70, FX
SET @model_q70 = (SELECT id FROM car_models WHERE name = 'Q70' AND brand_id = @infiniti_id);
SET @model_qx50 = (SELECT id FROM car_models WHERE name = 'QX50' AND brand_id = @infiniti_id);
SET @model_qx60 = (SELECT id FROM car_models WHERE name = 'QX60' AND brand_id = @infiniti_id);
SET @model_qx70 = (SELECT id FROM car_models WHERE name = 'QX70' AND brand_id = @infiniti_id);
SET @model_fx = (SELECT id FROM car_models WHERE name = 'FX' AND brand_id = @infiniti_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_q70, '3.0d V6', 'V9X', '3.0 V6 Turbodiesel (Renault)', 'Diesel', 238, 550, 2013, 2018),
(@model_qx50, '2.0 VC-Turbo', 'KR20DDET', '2.0 Compresión Variable', 'Gasolina', 268, 380, 2019, 2024),
(@model_qx60, '3.5 V6', 'VQ35DD', '3.5 V6 Direct Injection', 'Gasolina', 295, 366, 2014, 2021),
(@model_qx70, '3.0d V6', 'V9X', '3.0 V6 Turbodiesel (Renault)', 'Diesel', 238, 550, 2013, 2017),
(@model_fx, 'FX35', 'VQ35DE', '3.5 V6', 'Gasolina', 280, 366, 2003, 2008),
(@model_fx, 'FX45', 'VK45DE', '4.5 V8', 'Gasolina', 320, 446, 2003, 2008);


-- ------------------------------------------
-- 3. DATSUN (7 Modelos - Los clásicos y los low-cost)
-- ------------------------------------------
SET @datsun_id = (SELECT id FROM car_brands WHERE name = 'Datsun');

-- Serie Z (Pura historia del JDM)
SET @model_240z = (SELECT id FROM car_models WHERE name = '240Z' AND brand_id = @datsun_id);
SET @model_260z = (SELECT id FROM car_models WHERE name = '260Z' AND brand_id = @datsun_id);
SET @model_280z = (SELECT id FROM car_models WHERE name = '280Z' AND brand_id = @datsun_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_240z, 'S30', 'L24', '2.4 L6 (Carburadores Hitachi)', 'Gasolina', 151, 198, 1969, 1973),
(@model_260z, 'S30', 'L26', '2.6 L6', 'Gasolina', 162, 213, 1974, 1975),
(@model_280z, 'S30', 'L28E', '2.8 L6 (Inyección Bosch)', 'Gasolina', 170, 221, 1975, 1978);

-- Cherry, Bluebird, Go, Go+
SET @model_cherry = (SELECT id FROM car_models WHERE name = 'Cherry' AND brand_id = @datsun_id);
SET @model_bluebird = (SELECT id FROM car_models WHERE name = 'Bluebird' AND brand_id = @datsun_id);
SET @model_go = (SELECT id FROM car_models WHERE name = 'Go' AND brand_id = @datsun_id);
SET @model_goplus = (SELECT id FROM car_models WHERE name = 'Go+' AND brand_id = @datsun_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_cherry, '100A (E10)', 'A10', '1.0 L4', 'Gasolina', 59, 81, 1970, 1977),
(@model_bluebird, '1.8 SSS (910)', 'L18', '1.8 L4', 'Gasolina', 105, 147, 1979, 1983),
(@model_go, '1.2 Base', 'HR12DE', '1.2 L3 (Nissan)', 'Gasolina', 68, 104, 2014, 2022),
(@model_goplus, '1.2 MPV', 'HR12DE', '1.2 L3 (Nissan)', 'Gasolina', 68, 104, 2014, 2022);

-- ==========================================
-- VERSIONES MITSUBISHI Y LADA
-- Sinergias japonesas y tanques rusos
-- ==========================================

-- ------------------------------------------
-- 1. MITSUBISHI (13 Modelos)
-- ------------------------------------------
SET @mitsubishi_id = (SELECT id FROM car_brands WHERE name = 'Mitsubishi');

-- ASX
SET @model_id = (SELECT id FROM car_models WHERE name = 'ASX' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.8 DI-D', '4N13', '1.8 Turbo Diesel', 'Diesel', 150, 300, 2010, 2015),
(@model_id, '1.6 MPI', '4A92', '1.6 Atmosférico', 'Gasolina', 117, 154, 2010, 2019),
(@model_id, '1.6 PHEV (Base Renault Captur)', 'H4M', '1.6 Híbrido Enchufable', 'Híbrido Ench.', 160, 144, 2023, 2024);

-- Outlander (Cuidado con los códigos de motor aquí)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Outlander' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 DI-D', 'BSY', '2.0 TDI (VW Iny-Bomba BKD)', 'Diesel', 140, 310, 2007, 2010),
(@model_id, '2.2 DI-D', '4HN', '2.2 HDi (PSA DW12)', 'Diesel', 156, 380, 2007, 2012),
(@model_id, '2.2 DI-D ClearTec', '4N14', '2.2 Turbo Diesel (Mitsubishi)', 'Diesel', 150, 380, 2012, 2021),
(@model_id, 'PHEV', '4B11', '2.0 Híbrido Enchufable', 'Híbrido Ench.', 203, 332, 2013, 2018);

-- Eclipse Cross
SET @model_id = (SELECT id FROM car_models WHERE name = 'Eclipse Cross' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '150T', '4B40', '1.5 Turbo', 'Gasolina', 163, 250, 2017, 2022),
(@model_id, 'PHEV', '4B12', '2.4 Híbrido Enchufable', 'Híbrido Ench.', 188, 332, 2021, 2024);

-- Space Star
SET @model_id = (SELECT id FROM car_models WHERE name = 'Space Star' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.2 MPI', '3A92', '1.2 3 cil. Atmosférico', 'Gasolina', 80, 106, 2012, 2024),
(@model_id, '1.9 DI-D (Monovolumen clásico)', 'F9Q', '1.9 dCi (Renault)', 'Diesel', 115, 265, 2001, 2005);

-- L200 (El caballo de batalla)
SET @model_id = (SELECT id FROM car_models WHERE name = 'L200' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.5 DI-D', '4D56', '2.5 Turbo Diesel', 'Diesel', 136, 314, 2006, 2015),
(@model_id, '2.4 DI-D', '4N15', '2.4 Turbo Diesel', 'Diesel', 154, 380, 2015, 2024);

-- Montero y Pajero (Mismo coche, distinto nombre según mercado)
SET @model_montero = (SELECT id FROM car_models WHERE name = 'Montero' AND brand_id = @mitsubishi_id);
SET @model_pajero = (SELECT id FROM car_models WHERE name = 'Pajero' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_montero, '3.2 DI-D', '4M41', '3.2 4 cil. Turbo Diesel', 'Diesel', 165, 373, 2000, 2006),
(@model_montero, '2.8 TD', '4M40', '2.8 Turbo Diesel', 'Diesel', 125, 292, 1994, 2000),
(@model_pajero, '3.5 V6 GDI', '6G74', '3.5 V6 Inyección Directa', 'Gasolina', 202, 318, 2000, 2006);

-- Lancer y Lancer Evolution
SET @model_lancer = (SELECT id FROM car_models WHERE name = 'Lancer' AND brand_id = @mitsubishi_id);
SET @model_evo = (SELECT id FROM car_models WHERE name = 'Lancer Evolution' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_lancer, '2.0 DI-D', 'BKD', '2.0 TDI (Volkswagen)', 'Diesel', 140, 310, 2008, 2011),
(@model_evo, 'EVO IX', '4G63T', '2.0 Turbo 16V', 'Gasolina', 280, 355, 2005, 2007),
(@model_evo, 'EVO X', '4B11T', '2.0 Turbo 16V MIVEC', 'Gasolina', 295, 366, 2007, 2016);

-- Colt
SET @model_id = (SELECT id FROM car_models WHERE name = 'Colt' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5 CZT Turbo', '4G15T', '1.5 Turbo', 'Gasolina', 150, 210, 2005, 2008),
(@model_id, '1.5 DI-D', 'OM639', '1.5 Turbodiesel 3 cil. (Mercedes)', 'Diesel', 95, 210, 2004, 2008),
(@model_id, '1.0 MPI (Base Renault Clio)', 'H4D', '1.0 Atmosférico', 'Gasolina', 67, 95, 2023, 2024);

-- Galant
SET @model_id = (SELECT id FROM car_models WHERE name = 'Galant' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.5 V6 24V', '6G73', '2.5 V6', 'Gasolina', 163, 223, 1997, 2003),
(@model_id, '2.0 GLS', '4G63', '2.0 16V', 'Gasolina', 133, 175, 1997, 2003);

-- 3000GT (El Godzilla de Mitsubishi)
SET @model_id = (SELECT id FROM car_models WHERE name = '3000GT' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'VR4 Twin Turbo', '6G72TT', '3.0 V6 Biturbo', 'Gasolina', 320, 427, 1990, 1999);

-- Grandis
SET @model_id = (SELECT id FROM car_models WHERE name = 'Grandis' AND brand_id = @mitsubishi_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 DI-D', 'BSY', '2.0 TDI (VW Iny-Bomba BKD)', 'Diesel', 136, 310, 2005, 2010);


-- ------------------------------------------
-- 2. LADA (7 Modelos)
-- ------------------------------------------
SET @lada_id = (SELECT id FROM car_brands WHERE name = 'Lada');

-- Niva (El todoterreno inmortal)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Niva' AND brand_id = @lada_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6', 'VAZ-2121', '1.6 Carburación', 'Gasolina', 76, 116, 1977, 1995),
(@model_id, '1.7i', 'VAZ-21214', '1.7 Inyección Multipunto', 'Gasolina', 83, 129, 1995, 2024);

-- Kalina
SET @model_id = (SELECT id FROM car_models WHERE name = 'Kalina' AND brand_id = @lada_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.4 16V', 'VAZ-11194', '1.4 16V', 'Gasolina', 90, 127, 2004, 2013);

-- Granta
SET @model_id = (SELECT id FROM car_models WHERE name = 'Granta' AND brand_id = @lada_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 8V', 'VAZ-11186', '1.6 8V', 'Gasolina', 87, 140, 2011, 2024);

-- Vesta
SET @model_id = (SELECT id FROM car_models WHERE name = 'Vesta' AND brand_id = @lada_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 16V', 'VAZ-21129', '1.6 16V', 'Gasolina', 106, 148, 2015, 2024);

-- Priora
SET @model_id = (SELECT id FROM car_models WHERE name = 'Priora' AND brand_id = @lada_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.6 16V', 'VAZ-21126', '1.6 16V', 'Gasolina', 98, 145, 2007, 2018);

-- Samara
SET @model_id = (SELECT id FROM car_models WHERE name = 'Samara' AND brand_id = @lada_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5', 'VAZ-21083', '1.5 Carburación', 'Gasolina', 72, 106, 1988, 2003);

-- Riva (También conocido como Nova o 2105/2107)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Riva' AND brand_id = @lada_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.5', 'VAZ-2103', '1.5 Carburación', 'Gasolina', 71, 104, 1980, 2012);

-- ==========================================
-- VERSIONES TOYOTA, LEXUS Y DAIHATSU
-- El imperio de la fiabilidad y los motores compartidos
-- ==========================================

-- ------------------------------------------
-- 1. TOYOTA (30 Modelos)
-- ------------------------------------------
SET @toyota_id = (SELECT id FROM car_brands WHERE name = 'Toyota');

-- Corolla (El coche más vendido de la historia)
SET @model_id = (SELECT id FROM car_models WHERE name = 'Corolla' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 D-4D (E120)', '1CD-FTV', '2.0 Turbo Diesel', 'Diesel', 116, 280, 2002, 2007),
(@model_id, '1.8 HSD (E210)', '2ZR-FXE', '1.8 Híbrido', 'Híbrido', 122, 142, 2018, 2024),
(@model_id, '2.0 HSD (E210)', 'M20A-FXS', '2.0 Híbrido Dynamic Force', 'Híbrido', 184, 190, 2018, 2024);

-- Yaris y Yaris Cross
SET @model_yaris = (SELECT id FROM car_models WHERE name = 'Yaris' AND brand_id = @toyota_id);
SET @model_ycross = (SELECT id FROM car_models WHERE name = 'Yaris Cross' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_yaris, '1.5 HSD (XP130)', '1NZ-FXE', '1.5 Híbrido', 'Híbrido', 100, 111, 2012, 2020),
(@model_yaris, 'GR Yaris (XP210)', 'G16E-GTS', '1.6 Turbo 3 cil. 4WD', 'Gasolina', 261, 360, 2020, 2024),
(@model_ycross, '1.5 HSD AWD-i', 'M15A-FXE', '1.5 Híbrido', 'Híbrido', 116, 120, 2021, 2024);

-- Auris
SET @model_id = (SELECT id FROM car_models WHERE name = 'Auris' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '2.0 D-4D', '1AD-FTV', '2.0 Turbo Diesel', 'Diesel', 126, 300, 2007, 2012),
(@model_id, '1.8 HSD', '2ZR-FXE', '1.8 Híbrido', 'Híbrido', 136, 142, 2010, 2018);

-- Aygo y Aygo X (Primos de PSA)
SET @model_aygo = (SELECT id FROM car_models WHERE name = 'Aygo' AND brand_id = @toyota_id);
SET @model_aygox = (SELECT id FROM car_models WHERE name = 'Aygo X' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_aygo, '1.0 VVT-i', '1KR-FE', '1.0 3 cil. (Compartido PSA)', 'Gasolina', 68, 93, 2005, 2021),
(@model_aygox, '1.0 VVT-i', '1KR-B52', '1.0 3 cil.', 'Gasolina', 72, 93, 2022, 2024);

-- C-HR, RAV4, Prius, Prius+, Camry, Highlander (La armada híbrida)
SET @model_chr = (SELECT id FROM car_models WHERE name = 'C-HR' AND brand_id = @toyota_id);
SET @model_rav4 = (SELECT id FROM car_models WHERE name = 'RAV4' AND brand_id = @toyota_id);
SET @model_prius = (SELECT id FROM car_models WHERE name = 'Prius' AND brand_id = @toyota_id);
SET @model_priusplus = (SELECT id FROM car_models WHERE name = 'Prius+' AND brand_id = @toyota_id);
SET @model_camry = (SELECT id FROM car_models WHERE name = 'Camry' AND brand_id = @toyota_id);
SET @model_highlander = (SELECT id FROM car_models WHERE name = 'Highlander' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_chr, '1.8 HSD', '2ZR-FXE', '1.8 Híbrido', 'Híbrido', 122, 142, 2016, 2024),
(@model_rav4, '2.2 D-CAT (XA30)', '2AD-FHV', '2.2 Diesel (Ojo Culata/Inyector)', 'Diesel', 177, 400, 2006, 2012),
(@model_rav4, '2.5 HSD AWD-i (XA50)', 'A25A-FXS', '2.5 Híbrido', 'Híbrido', 222, 221, 2019, 2024),
(@model_prius, '1.5 HSD (XW20)', '1NZ-FXE', '1.5 Híbrido', 'Híbrido', 110, 115, 2003, 2009),
(@model_priusplus, '1.8 HSD', '2ZR-FXE', '1.8 Híbrido', 'Híbrido', 136, 142, 2012, 2021),
(@model_camry, '2.5 HSD', 'A25A-FXS', '2.5 Híbrido', 'Híbrido', 218, 221, 2018, 2024),
(@model_highlander, '2.5 HSD AWD-i', 'A25A-FXS', '2.5 Híbrido', 'Híbrido', 248, 239, 2021, 2024);

-- Avensis y Verso (Con sorpresa bávara)
SET @model_avensis = (SELECT id FROM car_models WHERE name = 'Avensis' AND brand_id = @toyota_id);
SET @model_verso = (SELECT id FROM car_models WHERE name = 'Verso' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_avensis, '2.0 D-4D (T27)', '1AD-FTV', '2.0 Turbo Diesel', 'Diesel', 126, 310, 2009, 2015),
(@model_verso, '2.0 D-4D', '1AD-FTV', '2.0 Turbo Diesel', 'Diesel', 126, 310, 2009, 2013),
(@model_verso, '1.6 D-4D (Origen BMW)', '1WW', '1.6 Turbo Diesel (BMW N47)', 'Diesel', 112, 270, 2014, 2018);

-- Land Cruiser y Hilux (Los indestructibles)
SET @model_lc = (SELECT id FROM car_models WHERE name = 'Land Cruiser' AND brand_id = @toyota_id);
SET @model_hilux = (SELECT id FROM car_models WHERE name = 'Hilux' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_lc, '3.0 D-4D (J120)', '1KD-FTV', '3.0 Turbo Diesel', 'Diesel', 163, 343, 2002, 2009),
(@model_lc, '2.8 D-4D (J150)', '1GD-FTV', '2.8 Turbo Diesel', 'Diesel', 204, 500, 2015, 2024),
(@model_hilux, '2.4 D-4D', '2GD-FTV', '2.4 Turbo Diesel', 'Diesel', 150, 400, 2016, 2024);

-- Proace y Proace City (Corazón PSA)
SET @model_proace = (SELECT id FROM car_models WHERE name = 'Proace' AND brand_id = @toyota_id);
SET @model_proacecity = (SELECT id FROM car_models WHERE name = 'Proace City' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_proace, '2.0 D-4D', 'AHW', '2.0 HDi (PSA DW10)', 'Diesel', 122, 340, 2016, 2024),
(@model_proacecity, '1.5 D-4D', 'YH01', '1.5 BlueHDi (PSA DV5)', 'Diesel', 100, 250, 2019, 2024);

-- Deportivos: Supra, Celica, MR2, GT86, GR86
SET @model_supra = (SELECT id FROM car_models WHERE name = 'Supra' AND brand_id = @toyota_id);
SET @model_grsupra = (SELECT id FROM car_models WHERE name = 'GR Supra' AND brand_id = @toyota_id);
SET @model_celica = (SELECT id FROM car_models WHERE name = 'Celica' AND brand_id = @toyota_id);
SET @model_mr2 = (SELECT id FROM car_models WHERE name = 'MR2' AND brand_id = @toyota_id);
SET @model_gt86 = (SELECT id FROM car_models WHERE name = 'GT86' AND brand_id = @toyota_id);
SET @model_gr86 = (SELECT id FROM car_models WHERE name = 'GR86' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_supra, 'Twin Turbo (A80)', '2JZ-GTE', '3.0 L6 Twin Turbo', 'Gasolina', 330, 441, 1993, 2002),
(@model_grsupra, '3.0 Turbo (A90)', 'B58B30', '3.0 L6 Turbo (BMW)', 'Gasolina', 340, 500, 2019, 2024),
(@model_celica, '1.8 T-Sport (T230)', '2ZZ-GE', '1.8 16V VVTL-i (Yamaha)', 'Gasolina', 192, 180, 1999, 2006),
(@model_mr2, '1.8 VVT-i (W30)', '1ZZ-FE', '1.8 16V', 'Gasolina', 140, 170, 1999, 2007),
(@model_gt86, '2.0 Boxer', 'FA20', '2.0 Bóxer Atmosférico (Subaru)', 'Gasolina', 200, 205, 2012, 2021),
(@model_gr86, '2.4 Boxer', 'FA24', '2.4 Bóxer Atmosférico (Subaru)', 'Gasolina', 234, 250, 2022, 2024);

-- Clásicos y Especiales: Starlet, Carina, Urban Cruiser, IQ, Mirai, bZ4X
SET @model_starlet = (SELECT id FROM car_models WHERE name = 'Starlet' AND brand_id = @toyota_id);
SET @model_carina = (SELECT id FROM car_models WHERE name = 'Carina' AND brand_id = @toyota_id);
SET @model_urban = (SELECT id FROM car_models WHERE name = 'Urban Cruiser' AND brand_id = @toyota_id);
SET @model_iq = (SELECT id FROM car_models WHERE name = 'IQ' AND brand_id = @toyota_id);
SET @model_mirai = (SELECT id FROM car_models WHERE name = 'Mirai' AND brand_id = @toyota_id);
SET @model_bz4x = (SELECT id FROM car_models WHERE name = 'bZ4X' AND brand_id = @toyota_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_starlet, '1.3 Glanza V', '4E-FTE', '1.3 Turbo', 'Gasolina', 135, 157, 1996, 1999),
(@model_carina, '2.0 GLi (E)', '3S-FE', '2.0 16V', 'Gasolina', 133, 183, 1992, 1997),
(@model_urban, '1.4 D-4D', '1ND-TV', '1.4 Turbo Diesel', 'Diesel', 90, 205, 2009, 2014),
(@model_iq, '1.0 VVT-i', '1KR-FE', '1.0 3 cil.', 'Gasolina', 68, 90, 2008, 2015),
(@model_mirai, 'Fuel Cell', '4JM', 'Motor Eléctrico Pila Hidrógeno', 'Hidrógeno', 182, 300, 2020, 2024),
(@model_bz4x, 'AWD', '1XM', 'Dual Motor Eléctrico', 'Eléctrico', 218, 336, 2022, 2024);


-- ------------------------------------------
-- 2. LEXUS (12 Modelos - El lujo híbrido)
-- ------------------------------------------
SET @lexus_id = (SELECT id FROM car_brands WHERE name = 'Lexus');

-- CT, UX, NX, RX, RZ
SET @model_ct = (SELECT id FROM car_models WHERE name = 'CT' AND brand_id = @lexus_id);
SET @model_ux = (SELECT id FROM car_models WHERE name = 'UX' AND brand_id = @lexus_id);
SET @model_nx = (SELECT id FROM car_models WHERE name = 'NX' AND brand_id = @lexus_id);
SET @model_rx = (SELECT id FROM car_models WHERE name = 'RX' AND brand_id = @lexus_id);
SET @model_rz = (SELECT id FROM car_models WHERE name = 'RZ' AND brand_id = @lexus_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ct, '200h', '2ZR-FXE', '1.8 Híbrido (Base Prius)', 'Híbrido', 136, 142, 2011, 2020),
(@model_ux, '250h', 'M20A-FXS', '2.0 Híbrido', 'Híbrido', 184, 190, 2019, 2024),
(@model_nx, '300h', '2AR-FXE', '2.5 Híbrido', 'Híbrido', 197, 210, 2014, 2021),
(@model_rx, '400h', '3MZ-FE', '3.3 V6 Híbrido', 'Híbrido', 272, 288, 2005, 2009),
(@model_rx, '450h', '2GR-FXS', '3.5 V6 Híbrido', 'Híbrido', 313, 335, 2015, 2022),
(@model_rz, '450e Direct4', '1XM', 'Dual Motor Eléctrico', 'Eléctrico', 313, 435, 2023, 2024);

-- IS, ES, GS, LS
SET @model_is = (SELECT id FROM car_models WHERE name = 'IS' AND brand_id = @lexus_id);
SET @model_es = (SELECT id FROM car_models WHERE name = 'ES' AND brand_id = @lexus_id);
SET @model_gs = (SELECT id FROM car_models WHERE name = 'GS' AND brand_id = @lexus_id);
SET @model_ls = (SELECT id FROM car_models WHERE name = 'LS' AND brand_id = @lexus_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_is, '220d (XE20)', '2AD-FHV', '2.2 Diesel', 'Diesel', 177, 400, 2005, 2012),
(@model_is, '300h (XE30)', '2AR-FSE', '2.5 Híbrido', 'Híbrido', 223, 221, 2013, 2020),
(@model_es, '300h', 'A25A-FXS', '2.5 Híbrido', 'Híbrido', 218, 221, 2018, 2024),
(@model_gs, '450h', '2GR-FSE', '3.5 V6 Híbrido', 'Híbrido', 345, 368, 2006, 2011),
(@model_ls, '400 (XF10/20)', '1UZ-FE', '4.0 V8 (Leyenda fiabilidad)', 'Gasolina', 250, 353, 1989, 2000),
(@model_ls, '600h (XF40)', '2UR-FSE', '5.0 V8 Híbrido AWD', 'Híbrido', 445, 520, 2007, 2017);

-- RC, LC, LFA (Músculo y obras de arte)
SET @model_rc = (SELECT id FROM car_models WHERE name = 'RC' AND brand_id = @lexus_id);
SET @model_lc = (SELECT id FROM car_models WHERE name = 'LC' AND brand_id = @lexus_id);
SET @model_lfa = (SELECT id FROM car_models WHERE name = 'LFA' AND brand_id = @lexus_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_rc, 'F 5.0', '2UR-GSE', '5.0 V8 Atmosférico', 'Gasolina', 477, 530, 2014, 2024),
(@model_lc, '500 V8', '2UR-GSE', '5.0 V8 Atmosférico', 'Gasolina', 477, 540, 2017, 2024),
(@model_lfa, 'V10 (Sonido Yamaha)', '1LR-GUE', '4.8 V10 Atmosférico', 'Gasolina', 560, 480, 2010, 2012);


-- ------------------------------------------
-- 3. DAIHATSU (8 Modelos - Pequeños pero matones)
-- ------------------------------------------
SET @daihatsu_id = (SELECT id FROM car_brands WHERE name = 'Daihatsu');

SET @model_terios = (SELECT id FROM car_models WHERE name = 'Terios' AND brand_id = @daihatsu_id);
SET @model_sirion = (SELECT id FROM car_models WHERE name = 'Sirion' AND brand_id = @daihatsu_id);
SET @model_cuore = (SELECT id FROM car_models WHERE name = 'Cuore' AND brand_id = @daihatsu_id);
SET @model_charade = (SELECT id FROM car_models WHERE name = 'Charade' AND brand_id = @daihatsu_id);
SET @model_rocky = (SELECT id FROM car_models WHERE name = 'Rocky' AND brand_id = @daihatsu_id);
SET @model_copen = (SELECT id FROM car_models WHERE name = 'Copen' AND brand_id = @daihatsu_id);
SET @model_materia = (SELECT id FROM car_models WHERE name = 'Materia' AND brand_id = @daihatsu_id);
SET @model_feroza = (SELECT id FROM car_models WHERE name = 'Feroza' AND brand_id = @daihatsu_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_terios, '1.3 4WD', 'K3-VE', '1.3 16V VVT-i', 'Gasolina', 86, 120, 1997, 2005),
(@model_sirion, '1.0', '1KR-FE', '1.0 3 cil. (Toyota)', 'Gasolina', 69, 94, 2005, 2015),
(@model_cuore, '1.0', 'EJ-VE', '1.0 12V 3 cil.', 'Gasolina', 58, 91, 2003, 2007),
(@model_charade, '1.0 GTti', 'CB70', '1.0 Turbo 3 cil. 12V', 'Gasolina', 101, 130, 1987, 1993),
(@model_rocky, '2.8 TD', 'DL52', '2.8 Turbo Diesel', 'Diesel', 102, 245, 1989, 2002),
(@model_copen, '0.6 Turbo Kei', 'JB-DET', '0.6 Turbo 4 cil.', 'Gasolina', 64, 110, 2002, 2012),
(@model_materia, '1.5 16V', '3SZ-VE', '1.5 16V', 'Gasolina', 103, 132, 2006, 2011),
(@model_feroza, '1.6 16V', 'HD-E', '1.6 16V', 'Gasolina', 95, 128, 1989, 1998);

-- ==========================================
-- VERSIONES HYUNDAI, KIA Y GENESIS
-- El ascenso del imperio coreano (Motores CRDi, T-GDi y EVs)
-- ==========================================

-- ------------------------------------------
-- 1. HYUNDAI (27 Modelos)
-- ------------------------------------------
SET @hyundai_id = (SELECT id FROM car_brands WHERE name = 'Hyundai');

-- Urbanos y Utilitarios (i10, i20, Atos, Getz, Bayon)
SET @model_i10 = (SELECT id FROM car_models WHERE name = 'i10' AND brand_id = @hyundai_id);
SET @model_i20 = (SELECT id FROM car_models WHERE name = 'i20' AND brand_id = @hyundai_id);
SET @model_atos = (SELECT id FROM car_models WHERE name = 'Atos' AND brand_id = @hyundai_id);
SET @model_getz = (SELECT id FROM car_models WHERE name = 'Getz' AND brand_id = @hyundai_id);
SET @model_bayon = (SELECT id FROM car_models WHERE name = 'Bayon' AND brand_id = @hyundai_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_i10, '1.2 MPI', 'G4LA', '1.2 4 cil. Kappa', 'Gasolina', 84, 118, 2013, 2024),
(@model_i20, '1.2 MPI', 'G4LA', '1.2 4 cil. Kappa', 'Gasolina', 84, 122, 2014, 2020),
(@model_i20, '1.0 T-GDi N-Line', 'G3LC', '1.0 Turbo 3 cil.', 'Gasolina', 120, 172, 2020, 2024),
(@model_atos, '1.0 MPI', 'G4HC', '1.0 4 cil. Epsilon', 'Gasolina', 58, 84, 1997, 2008),
(@model_getz, '1.5 CRDi', 'D4FA', '1.5 CRDi 4 cil.', 'Diesel', 88, 215, 2005, 2009),
(@model_bayon, '1.0 T-GDi 48V', 'G3LE', '1.0 Turbo MHEV', 'Híbrido', 100, 172, 2021, 2024);

-- Compactos y Berlinas (i30, i40, Elantra, Sonata, Accent)
SET @model_i30 = (SELECT id FROM car_models WHERE name = 'i30' AND brand_id = @hyundai_id);
SET @model_i40 = (SELECT id FROM car_models WHERE name = 'i40' AND brand_id = @hyundai_id);
SET @model_elantra = (SELECT id FROM car_models WHERE name = 'Elantra' AND brand_id = @hyundai_id);
SET @model_sonata = (SELECT id FROM car_models WHERE name = 'Sonata' AND brand_id = @hyundai_id);
SET @model_accent = (SELECT id FROM car_models WHERE name = 'Accent' AND brand_id = @hyundai_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_i30, '1.6 CRDi', 'D4FB', '1.6 CRDi U2', 'Diesel', 110, 260, 2012, 2017),
(@model_i30, 'N Performance', 'G4KH', '2.0 Turbo T-GDi', 'Gasolina', 275, 353, 2017, 2024),
(@model_i40, '1.7 CRDi', 'D4FD', '1.7 CRDi U2', 'Diesel', 136, 325, 2011, 2019),
(@model_elantra, '1.6 MPI', 'G4FC', '1.6 Gamma', 'Gasolina', 122, 154, 2006, 2011),
(@model_sonata, '2.0 CRDi', 'D4EA', '2.0 CRDi', 'Diesel', 140, 305, 2005, 2010),
(@model_accent, '1.5 CRDi 3 cil.', 'D3EA', '1.5 CRDi 3 cil. (Vibra mucho)', 'Diesel', 82, 190, 2002, 2006);

-- SUV y Todoterrenos (Tucson, Santa Fe, Kona, ix35, Terracan, Galloper)
SET @model_tucson = (SELECT id FROM car_models WHERE name = 'Tucson' AND brand_id = @hyundai_id);
SET @model_santafe = (SELECT id FROM car_models WHERE name = 'Santa Fe' AND brand_id = @hyundai_id);
SET @model_kona = (SELECT id FROM car_models WHERE name = 'Kona' AND brand_id = @hyundai_id);
SET @model_ix35 = (SELECT id FROM car_models WHERE name = 'ix35' AND brand_id = @hyundai_id);
SET @model_terracan = (SELECT id FROM car_models WHERE name = 'Terracan' AND brand_id = @hyundai_id);
SET @model_galloper = (SELECT id FROM car_models WHERE name = 'Galloper' AND brand_id = @hyundai_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_tucson, '1.6 T-GDi HEV (NX4)', 'G4FT', '1.6 Turbo Híbrido', 'Híbrido', 230, 350, 2020, 2024),
(@model_tucson, '2.0 CRDi (JM)', 'D4EA', '2.0 CRDi', 'Diesel', 140, 305, 2004, 2010),
(@model_santafe, '2.2 CRDi', 'D4HB', '2.2 CRDi R-Line', 'Diesel', 197, 436, 2009, 2018),
(@model_kona, 'Electric 64 kWh', 'EM16', 'Motor Eléctrico', 'Eléctrico', 204, 395, 2018, 2023),
(@model_ix35, '1.7 CRDi', 'D4FD', '1.7 CRDi U2', 'Diesel', 115, 260, 2010, 2015),
(@model_terracan, '2.9 CRDi', 'J3', '2.9 CRDi (Ojo Inyectores Delphi)', 'Diesel', 163, 345, 2001, 2006),
(@model_galloper, '2.5 TD (Base Montero)', 'D4BH', '2.5 TD (Mitsubishi 4D56)', 'Diesel', 100, 225, 1998, 2003);

-- Monovolúmenes y Furgonetas (ix20, Matrix, Trajet, Staria, H-1)
SET @model_ix20 = (SELECT id FROM car_models WHERE name = 'ix20' AND brand_id = @hyundai_id);
SET @model_matrix = (SELECT id FROM car_models WHERE name = 'Matrix' AND brand_id = @hyundai_id);
SET @model_trajet = (SELECT id FROM car_models WHERE name = 'Trajet' AND brand_id = @hyundai_id);
SET @model_staria = (SELECT id FROM car_models WHERE name = 'Staria' AND brand_id = @hyundai_id);
SET @model_h1 = (SELECT id FROM car_models WHERE name = 'H-1' AND brand_id = @hyundai_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ix20, '1.4 CRDi', 'D4FC', '1.4 CRDi', 'Diesel', 90, 220, 2010, 2019),
(@model_matrix, '1.5 CRDi', 'D3EA', '1.5 CRDi 3 cil.', 'Diesel', 82, 190, 2001, 2008),
(@model_trajet, '2.0 CRDi', 'D4EA', '2.0 CRDi', 'Diesel', 112, 255, 2001, 2008),
(@model_staria, '2.2 CRDi', 'D4HB', '2.2 CRDi', 'Diesel', 177, 430, 2021, 2024),
(@model_h1, '2.5 CRDi', 'D4CB', '2.5 CRDi', 'Diesel', 170, 392, 2007, 2020);

-- Deportivos y Exóticos (Coupe, Veloster, Ioniq, Ioniq 5, Ioniq 6, Nexo)
SET @model_coupe = (SELECT id FROM car_models WHERE name = 'Coupe' AND brand_id = @hyundai_id);
SET @model_veloster = (SELECT id FROM car_models WHERE name = 'Veloster' AND brand_id = @hyundai_id);
SET @model_ioniq = (SELECT id FROM car_models WHERE name = 'Ioniq' AND brand_id = @hyundai_id);
SET @model_ioniq5 = (SELECT id FROM car_models WHERE name = 'Ioniq 5' AND brand_id = @hyundai_id);
SET @model_ioniq6 = (SELECT id FROM car_models WHERE name = 'Ioniq 6' AND brand_id = @hyundai_id);
SET @model_nexo = (SELECT id FROM car_models WHERE name = 'Nexo' AND brand_id = @hyundai_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_coupe, '2.0 16V (Tiburón)', 'G4GC', '2.0 16V Beta', 'Gasolina', 143, 186, 2002, 2009),
(@model_coupe, '2.7 V6', 'G6BA', '2.7 V6 Delta', 'Gasolina', 167, 245, 2002, 2009),
(@model_veloster, '1.6 T-GDi', 'G4FJ', '1.6 Turbo GDi', 'Gasolina', 186, 265, 2012, 2018),
(@model_ioniq, '1.6 GDi HEV', 'G4LE', '1.6 Híbrido', 'Híbrido', 141, 265, 2016, 2022),
(@model_ioniq5, '72.6 kWh RWD', 'EM17', 'Motor Eléctrico Trasero', 'Eléctrico', 218, 350, 2021, 2024),
(@model_ioniq6, '77.4 kWh AWD', 'EM07', 'Doble Motor Eléctrico', 'Eléctrico', 325, 605, 2022, 2024),
(@model_nexo, 'Fuel Cell', 'FM', 'Pila de Hidrógeno', 'Hidrógeno', 163, 395, 2018, 2024);


-- ------------------------------------------
-- 2. KIA (23 Modelos)
-- ------------------------------------------
SET @kia_id = (SELECT id FROM car_brands WHERE name = 'Kia');

-- Compactos y Urbanos (Picanto, Rio, Ceed, ProCeed, XCeed, Cerato, Sephia, Shuma, Pride)
SET @model_picanto = (SELECT id FROM car_models WHERE name = 'Picanto' AND brand_id = @kia_id);
SET @model_rio = (SELECT id FROM car_models WHERE name = 'Rio' AND brand_id = @kia_id);
SET @model_ceed = (SELECT id FROM car_models WHERE name = 'Ceed' AND brand_id = @kia_id);
SET @model_proceed = (SELECT id FROM car_models WHERE name = 'ProCeed' AND brand_id = @kia_id);
SET @model_xceed = (SELECT id FROM car_models WHERE name = 'XCeed' AND brand_id = @kia_id);
SET @model_cerato = (SELECT id FROM car_models WHERE name = 'Cerato' AND brand_id = @kia_id);
SET @model_sephia = (SELECT id FROM car_models WHERE name = 'Sephia' AND brand_id = @kia_id);
SET @model_shuma = (SELECT id FROM car_models WHERE name = 'Shuma' AND brand_id = @kia_id);
SET @model_pride = (SELECT id FROM car_models WHERE name = 'Pride' AND brand_id = @kia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_picanto, '1.0 MPI', 'G3LA', '1.0 3 cil.', 'Gasolina', 67, 95, 2011, 2024),
(@model_rio, '1.2 MPI', 'G4LA', '1.2 4 cil.', 'Gasolina', 84, 122, 2011, 2023),
(@model_ceed, '1.6 CRDi', 'D4FB', '1.6 CRDi U2', 'Diesel', 115, 260, 2007, 2018),
(@model_ceed, '1.4 T-GDi', 'G4LD', '1.4 Turbo', 'Gasolina', 140, 242, 2018, 2024),
(@model_proceed, '1.6 T-GDi GT', 'G4FJ', '1.6 Turbo GT', 'Gasolina', 204, 265, 2019, 2024),
(@model_xceed, '1.6 GDi PHEV', 'G4LE', '1.6 Híbrido Enchufable', 'Híbrido Ench.', 141, 265, 2020, 2024),
(@model_cerato, '1.5 CRDi', 'D4FA', '1.5 CRDi', 'Diesel', 102, 235, 2004, 2008),
(@model_sephia, '1.5 16V', 'B5', '1.5 16V', 'Gasolina', 80, 120, 1993, 1998),
(@model_shuma, '1.5 16V', 'B5', '1.5 16V', 'Gasolina', 88, 135, 1998, 2004),
(@model_pride, '1.3 (Base Mazda 121)', 'B3', '1.3 8V', 'Gasolina', 72, 104, 1987, 2000);

-- SUV y Crossovers (Sportage, Sorento, Niro, Stonic, Soul)
SET @model_sportage = (SELECT id FROM car_models WHERE name = 'Sportage' AND brand_id = @kia_id);
SET @model_sorento = (SELECT id FROM car_models WHERE name = 'Sorento' AND brand_id = @kia_id);
SET @model_niro = (SELECT id FROM car_models WHERE name = 'Niro' AND brand_id = @kia_id);
SET @model_stonic = (SELECT id FROM car_models WHERE name = 'Stonic' AND brand_id = @kia_id);
SET @model_soul = (SELECT id FROM car_models WHERE name = 'Soul' AND brand_id = @kia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_sportage, '1.7 CRDi', 'D4FD', '1.7 CRDi', 'Diesel', 115, 260, 2010, 2018),
(@model_sportage, '1.6 T-GDi MHEV', 'G4FT', '1.6 Turbo MHEV', 'Híbrido', 150, 250, 2021, 2024),
(@model_sorento, '2.5 CRDi (JC)', 'D4CB', '2.5 CRDi', 'Diesel', 140, 314, 2002, 2009),
(@model_sorento, '2.2 CRDi (UM)', 'D4HB', '2.2 CRDi', 'Diesel', 200, 441, 2015, 2020),
(@model_niro, '1.6 GDi HEV', 'G4LE', '1.6 Híbrido', 'Híbrido', 141, 265, 2016, 2024),
(@model_stonic, '1.0 T-GDi', 'G3LC', '1.0 Turbo 3 cil.', 'Gasolina', 120, 172, 2017, 2024),
(@model_soul, '1.6 CRDi', 'D4FB', '1.6 CRDi', 'Diesel', 128, 260, 2009, 2019),
(@model_soul, 'EV', 'EM15', 'Motor Eléctrico', 'Eléctrico', 110, 285, 2014, 2019);

-- Familiares, Monovolúmenes y Berlinas (Optima, Carens, Carnival, Venga, Magentis, Opirus)
SET @model_optima = (SELECT id FROM car_models WHERE name = 'Optima' AND brand_id = @kia_id);
SET @model_carens = (SELECT id FROM car_models WHERE name = 'Carens' AND brand_id = @kia_id);
SET @model_carnival = (SELECT id FROM car_models WHERE name = 'Carnival' AND brand_id = @kia_id);
SET @model_venga = (SELECT id FROM car_models WHERE name = 'Venga' AND brand_id = @kia_id);
SET @model_magentis = (SELECT id FROM car_models WHERE name = 'Magentis' AND brand_id = @kia_id);
SET @model_opirus = (SELECT id FROM car_models WHERE name = 'Opirus' AND brand_id = @kia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_optima, '1.7 CRDi', 'D4FD', '1.7 CRDi', 'Diesel', 141, 340, 2015, 2020),
(@model_carens, '1.7 CRDi', 'D4FD', '1.7 CRDi', 'Diesel', 115, 260, 2013, 2019),
(@model_carnival, '2.9 CRDi', 'J3', '2.9 CRDi (Delphi)', 'Diesel', 144, 310, 2001, 2006),
(@model_carnival, '2.2 CRDi', 'D4HB', '2.2 CRDi', 'Diesel', 195, 430, 2006, 2014),
(@model_venga, '1.4 CRDi', 'D4FC', '1.4 CRDi', 'Diesel', 90, 220, 2010, 2019),
(@model_magentis, '2.0 CRDi', 'D4EA', '2.0 CRDi', 'Diesel', 140, 305, 2005, 2010),
(@model_opirus, '3.5 V6', 'G6CU', '3.5 V6', 'Gasolina', 203, 298, 2003, 2006);

-- Puros Eléctricos y Deportivos (EV6, EV9, Stinger)
SET @model_ev6 = (SELECT id FROM car_models WHERE name = 'EV6' AND brand_id = @kia_id);
SET @model_ev9 = (SELECT id FROM car_models WHERE name = 'EV9' AND brand_id = @kia_id);
SET @model_stinger = (SELECT id FROM car_models WHERE name = 'Stinger' AND brand_id = @kia_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ev6, '77.4 kWh GT-Line', 'EM07', 'Motor Eléctrico Trasero', 'Eléctrico', 228, 350, 2021, 2024),
(@model_ev9, '99.8 kWh AWD', 'EM07', 'Doble Motor Eléctrico', 'Eléctrico', 384, 700, 2023, 2024),
(@model_stinger, '3.3 T-GDi V6 GT', 'G6DP', '3.3 V6 Twin-Turbo', 'Gasolina', 370, 510, 2017, 2023);


-- ------------------------------------------
-- 3. GENESIS (6 Modelos - Premium Coreano)
-- ------------------------------------------
SET @genesis_id = (SELECT id FROM car_brands WHERE name = 'Genesis');

SET @model_g70 = (SELECT id FROM car_models WHERE name = 'G70' AND brand_id = @genesis_id);
SET @model_g80 = (SELECT id FROM car_models WHERE name = 'G80' AND brand_id = @genesis_id);
SET @model_g90 = (SELECT id FROM car_models WHERE name = 'G90' AND brand_id = @genesis_id);
SET @model_gv60 = (SELECT id FROM car_models WHERE name = 'GV60' AND brand_id = @genesis_id);
SET @model_gv70 = (SELECT id FROM car_models WHERE name = 'GV70' AND brand_id = @genesis_id);
SET @model_gv80 = (SELECT id FROM car_models WHERE name = 'GV80' AND brand_id = @genesis_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_g70, '2.0T', 'G4KL', '2.0 Turbo', 'Gasolina', 252, 353, 2017, 2024),
(@model_g80, '2.5T', 'G4KR', '2.5 Turbo', 'Gasolina', 304, 422, 2020, 2024),
(@model_g90, '3.5T V6', 'G6DT', '3.5 V6 Twin-Turbo', 'Gasolina', 380, 530, 2021, 2024),
(@model_gv60, 'Dual Motor AWD', 'EM07', 'Doble Motor Eléctrico', 'Eléctrico', 318, 605, 2021, 2024),
(@model_gv70, '2.2 CRDi', 'D4HE', '2.2 Turbo Diesel', 'Diesel', 210, 441, 2020, 2024),
(@model_gv80, '3.0 CRDi L6', 'D6JA', '3.0 L6 Turbo Diesel', 'Diesel', 278, 588, 2020, 2024);

-- ==========================================
-- VERSIONES FORD Y LINCOLN
-- El imperio del óvalo: TDCi (PSA), EcoBoost y V8s
-- ==========================================

-- ------------------------------------------
-- 1. FORD (36 Modelos)
-- ------------------------------------------
SET @ford_id = (SELECT id FROM car_brands WHERE name = 'Ford');

-- Compactos y Urbanos (Fiesta, Focus, Ka, Ka+, Puma)
SET @model_fiesta = (SELECT id FROM car_models WHERE name = 'Fiesta' AND brand_id = @ford_id);
SET @model_focus = (SELECT id FROM car_models WHERE name = 'Focus' AND brand_id = @ford_id);
SET @model_ka = (SELECT id FROM car_models WHERE name = 'Ka' AND brand_id = @ford_id);
SET @model_kaplus = (SELECT id FROM car_models WHERE name = 'Ka+' AND brand_id = @ford_id);
SET @model_puma = (SELECT id FROM car_models WHERE name = 'Puma' AND brand_id = @ford_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_fiesta, '1.4 TDCi (Mk6)', 'F6JA', '1.4 TDCi (PSA)', 'Diesel', 68, 160, 2002, 2008),
(@model_fiesta, '1.0 EcoBoost (Mk8)', 'SFJC', '1.0 Turbo 3 cil. (Correa Húmeda)', 'Gasolina', 100, 170, 2017, 2024),
(@model_focus, '1.6 TDCi (Mk2)', 'G8DA', '1.6 TDCi (PSA 1.6 HDi)', 'Diesel', 109, 240, 2004, 2011),
(@model_focus, 'RS 2.5T (Mk2)', 'JZDA', '2.5 L5 Turbo (Volvo)', 'Gasolina', 305, 440, 2009, 2011),
(@model_focus, '1.5 EcoBlue (Mk4)', 'ZTDA', '1.5 Turbo Diesel', 'Diesel', 120, 300, 2018, 2024),
(@model_ka, '1.3 (Mk1)', 'J4C', '1.3 Endura-E (Árbol de levas lateral)', 'Gasolina', 60, 103, 1996, 2008),
(@model_ka, '1.2 Duratec (Mk2)', 'FP4', '1.2 8V (Base Fiat 500 FIRE)', 'Gasolina', 69, 102, 2008, 2016),
(@model_kaplus, '1.2 Ti-VCT', 'B2KA', '1.2 4 cil.', 'Gasolina', 85, 115, 2016, 2020),
(@model_puma, '1.7 Zetec (Clásico)', 'MHA', '1.7 16V VCT (Yamaha)', 'Gasolina', 125, 157, 1997, 2002),
(@model_puma, '1.0 EcoBoost MHEV (SUV)', 'B7JB', '1.0 Turbo Mild-Hybrid', 'Híbrido', 125, 210, 2019, 2024);

-- Berlinas y Clásicos (Mondeo, Escort, Sierra, Scorpio, Orion, Taunus, Capri)
SET @model_mondeo = (SELECT id FROM car_models WHERE name = 'Mondeo' AND brand_id = @ford_id);
SET @model_escort = (SELECT id FROM car_models WHERE name = 'Escort' AND brand_id = @ford_id);
SET @model_sierra = (SELECT id FROM car_models WHERE name = 'Sierra' AND brand_id = @ford_id);
SET @model_scorpio = (SELECT id FROM car_models WHERE name = 'Scorpio' AND brand_id = @ford_id);
SET @model_orion = (SELECT id FROM car_models WHERE name = 'Orion' AND brand_id = @ford_id);
SET @model_taunus = (SELECT id FROM car_models WHERE name = 'Taunus' AND brand_id = @ford_id);
SET @model_capri = (SELECT id FROM car_models WHERE name = 'Capri' AND brand_id = @ford_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_mondeo, '2.0 TDCi (Mk3)', 'FMBA', '2.0 TDCi (Inyección Delphi)', 'Diesel', 130, 330, 2001, 2007),
(@model_mondeo, '2.0 TDCi (Mk4)', 'QXBA', '2.0 TDCi (PSA DW10)', 'Diesel', 140, 320, 2007, 2014),
(@model_mondeo, '2.0 Hybrid (Mk5)', 'UACA', '2.0 Híbrido (Ciclo Atkinson)', 'Híbrido', 187, 173, 2014, 2022),
(@model_escort, 'RS Cosworth', 'N5F', '2.0 Turbo 16V YB', 'Gasolina', 227, 304, 1992, 1996),
(@model_escort, '1.8 TD', 'RFD', '1.8 Turbo Diesel Endura-DE', 'Diesel', 90, 180, 1993, 2000),
(@model_sierra, 'RS Cosworth', 'YBD', '2.0 Turbo 16V YB', 'Gasolina', 204, 276, 1986, 1992),
(@model_sierra, '2.0 DOHC', 'N9C', '2.0 8V Twin Cam', 'Gasolina', 120, 171, 1989, 1993),
(@model_scorpio, '2.9 V6 Cosworth', 'BOA', '2.9 V6 24V', 'Gasolina', 195, 275, 1991, 1994),
(@model_orion, '1.6 CVH', 'LUAE', '1.6 8V Carburación', 'Gasolina', 90, 133, 1983, 1990),
(@model_taunus, '2.0 V6', 'NY', '2.0 V6 Cologne', 'Gasolina', 90, 149, 1976, 1982),
(@model_capri, '2.8i V6', 'PRN', '2.8 V6 Inyección (Bosch K-Jetronic)', 'Gasolina', 160, 221, 1981, 1986);

-- Deportivos y Coupés (Mustang, Cougar, Probe)
SET @model_mustang = (SELECT id FROM car_models WHERE name = 'Mustang' AND brand_id = @ford_id);
SET @model_cougar = (SELECT id FROM car_models WHERE name = 'Cougar' AND brand_id = @ford_id);
SET @model_probe = (SELECT id FROM car_models WHERE name = 'Probe' AND brand_id = @ford_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_mustang, '5.0 V8 GT (S550)', 'Coyote', '5.0 V8 Atmosférico', 'Gasolina', 421, 530, 2015, 2024),
(@model_mustang, '2.3 EcoBoost', '2.3L', '2.3 Turbo 4 cil.', 'Gasolina', 317, 432, 2015, 2024),
(@model_cougar, '2.5 V6', 'LCBA', '2.5 V6 24V Duratec', 'Gasolina', 170, 220, 1998, 2001),
(@model_probe, '2.5 V6 24V', 'KL-DE', '2.5 V6 (Mazda)', 'Gasolina', 163, 211, 1993, 1997);

-- SUV y 4x4 (Kuga, EcoSport, Edge, Explorer, Bronco, Maverick)
SET @model_kuga = (SELECT id FROM car_models WHERE name = 'Kuga' AND brand_id = @ford_id);
SET @model_ecosport = (SELECT id FROM car_models WHERE name = 'EcoSport' AND brand_id = @ford_id);
SET @model_edge = (SELECT id FROM car_models WHERE name = 'Edge' AND brand_id = @ford_id);
SET @model_explorer = (SELECT id FROM car_models WHERE name = 'Explorer' AND brand_id = @ford_id);
SET @model_bronco = (SELECT id FROM car_models WHERE name = 'Bronco' AND brand_id = @ford_id);
SET @model_maverick = (SELECT id FROM car_models WHERE name = 'Maverick' AND brand_id = @ford_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_kuga, '2.0 TDCi (Mk1)', 'G6DG', '2.0 TDCi (PSA)', 'Diesel', 136, 320, 2008, 2012),
(@model_kuga, '2.5 PHEV (Mk3)', 'BGDA', '2.5 Híbrido Enchufable', 'Híbrido Ench.', 225, 200, 2020, 2024),
(@model_ecosport, '1.5 TDCi', 'UGJC', '1.5 TDCi', 'Diesel', 90, 205, 2013, 2017),
(@model_edge, '2.0 EcoBlue Biturbo', 'YLCA', '2.0 Turbo Diesel Biturbo', 'Diesel', 238, 500, 2018, 2021),
(@model_explorer, '3.0 V6 PHEV', 'BQWA', '3.0 V6 EcoBoost PHEV', 'Híbrido Ench.', 457, 825, 2020, 2024),
(@model_bronco, '2.7 V6 EcoBoost', 'Nano', '2.7 V6 Twin-Turbo', 'Gasolina', 335, 563, 2021, 2024),
(@model_maverick, '2.7 TD (Terrano II)', 'TD27', '2.7 Turbo Diesel (Nissan)', 'Diesel', 125, 278, 1993, 1998);

-- Monovolúmenes (B-Max, C-Max, S-Max, Galaxy, Fusion)
SET @model_bmax = (SELECT id FROM car_models WHERE name = 'B-Max' AND brand_id = @ford_id);
SET @model_cmax = (SELECT id FROM car_models WHERE name = 'C-Max' AND brand_id = @ford_id);
SET @model_smax = (SELECT id FROM car_models WHERE name = 'S-Max' AND brand_id = @ford_id);
SET @model_galaxy = (SELECT id FROM car_models WHERE name = 'Galaxy' AND brand_id = @ford_id);
SET @model_fusion = (SELECT id FROM car_models WHERE name = 'Fusion' AND brand_id = @ford_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_bmax, '1.0 EcoBoost', 'SFJA', '1.0 Turbo 3 cil.', 'Gasolina', 100, 170, 2012, 2017),
(@model_cmax, '1.6 TDCi', 'G8DA', '1.6 TDCi (PSA)', 'Diesel', 109, 240, 2003, 2010),
(@model_smax, '2.0 TDCi', 'QXWA', '2.0 TDCi (PSA)', 'Diesel', 140, 320, 2006, 2014),
(@model_galaxy, '1.9 TDI (Mk1/Mk2)', 'ASZ', '1.9 TDI (VW Iny-Bomba)', 'Diesel', 130, 310, 2000, 2006),
(@model_galaxy, '2.0 TDCi (Mk3)', 'UFWA', '2.0 TDCi (PSA)', 'Diesel', 140, 320, 2006, 2015),
(@model_fusion, '1.4 TDCi', 'F6JA', '1.4 TDCi (PSA)', 'Diesel', 68, 160, 2002, 2012);

-- Furgonetas y Pick-ups (Transit, Connect, Custom, Courier, Tourneo, Ranger, F-150)
SET @model_tconnect = (SELECT id FROM car_models WHERE name = 'Transit Connect' AND brand_id = @ford_id);
SET @model_tcustom = (SELECT id FROM car_models WHERE name = 'Transit Custom' AND brand_id = @ford_id);
SET @model_tcourier = (SELECT id FROM car_models WHERE name = 'Transit Courier' AND brand_id = @ford_id);
SET @model_transit = (SELECT id FROM car_models WHERE name = 'Transit' AND brand_id = @ford_id);
SET @model_tourneoconnect = (SELECT id FROM car_models WHERE name = 'Tourneo Connect' AND brand_id = @ford_id);
SET @model_tourneocustom = (SELECT id FROM car_models WHERE name = 'Tourneo Custom' AND brand_id = @ford_id);
SET @model_tourneocourier = (SELECT id FROM car_models WHERE name = 'Tourneo Courier' AND brand_id = @ford_id);
SET @model_ranger = (SELECT id FROM car_models WHERE name = 'Ranger' AND brand_id = @ford_id);
SET @model_f150 = (SELECT id FROM car_models WHERE name = 'F-150' AND brand_id = @ford_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_tconnect, '1.8 TDCi (Mk1)', 'RWPA', '1.8 TDCi Lynx', 'Diesel', 90, 220, 2002, 2013),
(@model_tcustom, '2.2 TDCi', 'CYF4', '2.2 TDCi Puma', 'Diesel', 125, 350, 2012, 2016),
(@model_tcustom, '2.0 EcoBlue', 'YMF6', '2.0 Turbo Diesel (Correa Bañada)', 'Diesel', 130, 385, 2016, 2024),
(@model_tcourier, '1.5 TDCi', 'XVJC', '1.5 TDCi', 'Diesel', 95, 215, 2014, 2024),
(@model_transit, '2.4 TDCi (Mk6 RWD)', 'H9FA', '2.4 TDCi Puma (Ojo Inyectores)', 'Diesel', 137, 375, 2000, 2006),
(@model_tourneoconnect, '1.5 EcoBlue', 'ZTGA', '1.5 Turbo Diesel', 'Diesel', 120, 270, 2018, 2024),
(@model_tourneocustom, '2.0 EcoBlue PHEV', 'M1FA', '1.0 EcoBoost Híbrido REX', 'Híbrido Ench.', 126, 355, 2019, 2024),
(@model_tourneocourier, '1.0 EcoBoost', 'SFCA', '1.0 Turbo', 'Gasolina', 100, 170, 2014, 2024),
(@model_ranger, '3.2 TDCi L5 (T6)', 'SAFA', '3.2 L5 Turbo Diesel', 'Diesel', 200, 470, 2011, 2022),
(@model_ranger, '2.0 EcoBlue Bi-Turbo Raptor', 'YN2W', '2.0 Turbo Diesel', 'Diesel', 213, 500, 2019, 2022),
(@model_f150, '3.5 V6 EcoBoost', '3.5L EB', '3.5 V6 Twin-Turbo', 'Gasolina', 365, 569, 2011, 2020);

-- Eléctricos (Mustang Mach-E)
SET @model_mache = (SELECT id FROM car_models WHERE name = 'Mustang Mach-E' AND brand_id = @ford_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_mache, 'Extended Range AWD', 'Elec', 'Doble Motor Eléctrico', 'Eléctrico', 351, 580, 2020, 2024);


-- ------------------------------------------
-- 2. LINCOLN (10 Modelos)
-- ------------------------------------------
SET @lincoln_id = (SELECT id FROM car_brands WHERE name = 'Lincoln');

SET @model_navigator = (SELECT id FROM car_models WHERE name = 'Navigator' AND brand_id = @lincoln_id);
SET @model_aviator = (SELECT id FROM car_models WHERE name = 'Aviator' AND brand_id = @lincoln_id);
SET @model_corsair = (SELECT id FROM car_models WHERE name = 'Corsair' AND brand_id = @lincoln_id);
SET @model_nautilus = (SELECT id FROM car_models WHERE name = 'Nautilus' AND brand_id = @lincoln_id);
SET @model_continental = (SELECT id FROM car_models WHERE name = 'Continental' AND brand_id = @lincoln_id);
SET @model_towncar = (SELECT id FROM car_models WHERE name = 'Town Car' AND brand_id = @lincoln_id);
SET @model_mkz = (SELECT id FROM car_models WHERE name = 'MKZ' AND brand_id = @lincoln_id);
SET @model_mkx = (SELECT id FROM car_models WHERE name = 'MKX' AND brand_id = @lincoln_id);
SET @model_mks = (SELECT id FROM car_models WHERE name = 'MKS' AND brand_id = @lincoln_id);
SET @model_mkt = (SELECT id FROM car_models WHERE name = 'MKT' AND brand_id = @lincoln_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_navigator, '5.4 V8 InTech', '5.4 V8', '5.4 V8 Modular DOHC', 'Gasolina', 300, 481, 1998, 2006),
(@model_navigator, '3.5 V6 EcoBoost', '3.5 EB', '3.5 V6 Twin-Turbo', 'Gasolina', 450, 691, 2018, 2024),
(@model_aviator, '3.0 V6 Grand Touring PHEV', '3.0 PHEV', '3.0 V6 Twin-Turbo PHEV', 'Híbrido Ench.', 494, 854, 2020, 2024),
(@model_corsair, '2.0T Standard', '2.0 EB', '2.0 Turbo EcoBoost', 'Gasolina', 250, 380, 2020, 2024),
(@model_nautilus, '2.7 V6 EcoBoost', 'Nano 2.7', '2.7 V6 Twin-Turbo', 'Gasolina', 335, 515, 2019, 2023),
(@model_continental, '3.0 V6 Twin Turbo', '3.0 TT', '3.0 V6 Twin-Turbo EcoBoost', 'Gasolina', 400, 542, 2017, 2020),
(@model_towncar, '4.6 V8 Modular', '4.6 V8', '4.6 V8 SOHC (Coche fúnebre/Limo)', 'Gasolina', 239, 389, 1998, 2011),
(@model_mkz, '2.0 Hybrid', '2.0 HEV', '2.0 4 cil. Híbrido Atkinson', 'Híbrido', 188, 175, 2013, 2020),
(@model_mkx, '3.7 V6 Duratec', '3.7 Cyclone', '3.7 V6 Ti-VCT', 'Gasolina', 305, 380, 2011, 2015),
(@model_mks, '3.5 V6 EcoBoost', '3.5 EB', '3.5 V6 Twin-Turbo', 'Gasolina', 365, 475, 2010, 2016),
(@model_mkt, '3.5 V6 EcoBoost', '3.5 EB', '3.5 V6 Twin-Turbo', 'Gasolina', 365, 475, 2010, 2019);

-- ==========================================
-- VERSIONES CHEVROLET Y CADILLAC
-- De los Daewoo remarcados a los V8 americanos
-- ==========================================

-- ------------------------------------------
-- 1. CHEVROLET (20 Modelos)
-- ------------------------------------------
SET @chevrolet_id = (SELECT id FROM car_brands WHERE name = 'Chevrolet');

-- Los ex-Daewoo y compactos (Aveo, Spark, Matiz, Kalos, Lacetti, Nubira, Tacuma, Epica)
SET @model_aveo = (SELECT id FROM car_models WHERE name = 'Aveo' AND brand_id = @chevrolet_id);
SET @model_spark = (SELECT id FROM car_models WHERE name = 'Spark' AND brand_id = @chevrolet_id);
SET @model_matiz = (SELECT id FROM car_models WHERE name = 'Matiz' AND brand_id = @chevrolet_id);
SET @model_kalos = (SELECT id FROM car_models WHERE name = 'Kalos' AND brand_id = @chevrolet_id);
SET @model_lacetti = (SELECT id FROM car_models WHERE name = 'Lacetti' AND brand_id = @chevrolet_id);
SET @model_nubira = (SELECT id FROM car_models WHERE name = 'Nubira' AND brand_id = @chevrolet_id);
SET @model_tacuma = (SELECT id FROM car_models WHERE name = 'Tacuma' AND brand_id = @chevrolet_id);
SET @model_epica = (SELECT id FROM car_models WHERE name = 'Epica' AND brand_id = @chevrolet_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_aveo, '1.2 16V', 'B12D1', '1.2 16V (Ecotec)', 'Gasolina', 84, 114, 2008, 2011),
(@model_aveo, '1.3 VCDi', 'LDV', '1.3 Turbodiesel (Fiat/Opel)', 'Diesel', 75, 190, 2011, 2015),
(@model_spark, '1.0 16V', 'B10D1', '1.0 16V', 'Gasolina', 68, 93, 2010, 2015),
(@model_matiz, '0.8', 'F8CV', '0.8 3 cil.', 'Gasolina', 52, 69, 2005, 2010),
(@model_kalos, '1.4 16V', 'F14D3', '1.4 16V', 'Gasolina', 94, 130, 2002, 2008),
(@model_lacetti, '2.0 TCDi', 'Z20S', '2.0 Turbodiesel (VM Motori)', 'Diesel', 121, 280, 2007, 2010),
(@model_nubira, '1.6 16V', 'F16D3', '1.6 16V', 'Gasolina', 109, 150, 2005, 2010),
(@model_tacuma, '1.6', 'A16DMS', '1.6 16V', 'Gasolina', 105, 142, 2005, 2008),
(@model_epica, '2.0 VCDi', 'Z20S', '2.0 Turbodiesel (VM Motori)', 'Diesel', 150, 320, 2007, 2011),
(@model_epica, '2.0 24V L6', 'X20D1', '2.0 6 Cil. en Línea', 'Gasolina', 143, 195, 2006, 2011);

-- Modernos y Familiares (Cruze, Orlando, Malibu)
SET @model_cruze = (SELECT id FROM car_models WHERE name = 'Cruze' AND brand_id = @chevrolet_id);
SET @model_orlando = (SELECT id FROM car_models WHERE name = 'Orlando' AND brand_id = @chevrolet_id);
SET @model_malibu = (SELECT id FROM car_models WHERE name = 'Malibu' AND brand_id = @chevrolet_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_cruze, '2.0 VCDi', 'Z20S1', '2.0 Turbodiesel (VM Motori)', 'Diesel', 163, 360, 2011, 2015),
(@model_cruze, '1.6 16V', 'F16D4', '1.6 16V (Ecotec Opel)', 'Gasolina', 124, 154, 2009, 2015),
(@model_orlando, '2.0 VCDi', 'Z20D1', '2.0 Turbodiesel', 'Diesel', 163, 360, 2011, 2018),
(@model_malibu, '2.0 VCDi', 'LBS', '2.0 Turbodiesel (Opel 2.0 CDTI)', 'Diesel', 160, 350, 2012, 2015);

-- SUV y Todoterrenos (Captiva, Trax, Equinox, Blazer)
SET @model_captiva = (SELECT id FROM car_models WHERE name = 'Captiva' AND brand_id = @chevrolet_id);
SET @model_trax = (SELECT id FROM car_models WHERE name = 'Trax' AND brand_id = @chevrolet_id);
SET @model_equinox = (SELECT id FROM car_models WHERE name = 'Equinox' AND brand_id = @chevrolet_id);
SET @model_blazer = (SELECT id FROM car_models WHERE name = 'Blazer' AND brand_id = @chevrolet_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_captiva, '2.0 VCDi', 'Z20S', '2.0 Turbodiesel (VM Motori)', 'Diesel', 150, 320, 2006, 2011),
(@model_captiva, '2.2 VCDi', 'A22DMH', '2.2 Turbodiesel', 'Diesel', 184, 400, 2011, 2018),
(@model_trax, '1.7 VCDi', 'LUD', '1.7 Turbodiesel (Isuzu)', 'Diesel', 130, 300, 2013, 2015),
(@model_trax, '1.4 Turbo', 'LUJ', '1.4 Turbo (Opel Mokka)', 'Gasolina', 140, 200, 2013, 2020),
(@model_equinox, '1.5 Turbo', 'LYX', '1.5 Turbo', 'Gasolina', 170, 275, 2017, 2024),
(@model_blazer, '3.6 V6', 'LGX', '3.6 V6', 'Gasolina', 308, 366, 2019, 2024);

-- Deportivos y Gigantes Americanos (Camaro, Corvette, Tahoe, Suburban, Silverado)
SET @model_camaro = (SELECT id FROM car_models WHERE name = 'Camaro' AND brand_id = @chevrolet_id);
SET @model_corvette = (SELECT id FROM car_models WHERE name = 'Corvette' AND brand_id = @chevrolet_id);
SET @model_tahoe = (SELECT id FROM car_models WHERE name = 'Tahoe' AND brand_id = @chevrolet_id);
SET @model_suburban = (SELECT id FROM car_models WHERE name = 'Suburban' AND brand_id = @chevrolet_id);
SET @model_silverado = (SELECT id FROM car_models WHERE name = 'Silverado' AND brand_id = @chevrolet_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_camaro, '6.2 V8 SS', 'LS3', '6.2 V8', 'Gasolina', 432, 569, 2009, 2015),
(@model_camaro, '2.0 Turbo', 'LTG', '2.0 Turbo 4 cil.', 'Gasolina', 275, 400, 2016, 2024),
(@model_corvette, '6.2 V8 (C7 Stingray)', 'LT1', '6.2 V8', 'Gasolina', 466, 630, 2014, 2019),
(@model_corvette, '6.2 V8 (C8 Mid-Engine)', 'LT2', '6.2 V8 Central', 'Gasolina', 482, 613, 2020, 2024),
(@model_tahoe, '5.3 V8', 'L83', '5.3 V8 EcoTec3', 'Gasolina', 355, 519, 2015, 2020),
(@model_suburban, '5.3 V8', 'L84', '5.3 V8 EcoTec3', 'Gasolina', 355, 519, 2021, 2024),
(@model_silverado, '6.6 V8 Duramax', 'L5P', '6.6 V8 Turbo Diesel (Isuzu/GM)', 'Diesel', 445, 1234, 2017, 2024);


-- ------------------------------------------
-- 2. CADILLAC (12 Modelos)
-- ------------------------------------------
SET @cadillac_id = (SELECT id FROM car_brands WHERE name = 'Cadillac');

SET @model_escalade = (SELECT id FROM car_models WHERE name = 'Escalade' AND brand_id = @cadillac_id);
SET @model_cts = (SELECT id FROM car_models WHERE name = 'CTS' AND brand_id = @cadillac_id);
SET @model_ats = (SELECT id FROM car_models WHERE name = 'ATS' AND brand_id = @cadillac_id);
SET @model_srx = (SELECT id FROM car_models WHERE name = 'SRX' AND brand_id = @cadillac_id);
SET @model_xt4 = (SELECT id FROM car_models WHERE name = 'XT4' AND brand_id = @cadillac_id);
SET @model_xt5 = (SELECT id FROM car_models WHERE name = 'XT5' AND brand_id = @cadillac_id);
SET @model_xt6 = (SELECT id FROM car_models WHERE name = 'XT6' AND brand_id = @cadillac_id);
SET @model_ct4 = (SELECT id FROM car_models WHERE name = 'CT4' AND brand_id = @cadillac_id);
SET @model_ct5 = (SELECT id FROM car_models WHERE name = 'CT5' AND brand_id = @cadillac_id);
SET @model_seville = (SELECT id FROM car_models WHERE name = 'Seville' AND brand_id = @cadillac_id);
SET @model_eldorado = (SELECT id FROM car_models WHERE name = 'Eldorado' AND brand_id = @cadillac_id);
SET @model_deville = (SELECT id FROM car_models WHERE name = 'DeVille' AND brand_id = @cadillac_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_escalade, '6.2 V8', 'L86', '6.2 V8 EcoTec3', 'Gasolina', 426, 624, 2015, 2020),
(@model_escalade, '3.0 L6 Duramax', 'LM2', '3.0 L6 Turbo Diesel', 'Diesel', 277, 623, 2021, 2024),
(@model_cts, 'CTS-V 6.2 V8', 'LSA', '6.2 V8 Supercharged', 'Gasolina', 564, 747, 2009, 2014),
(@model_ats, '2.0 Turbo', 'LTG', '2.0 Turbo', 'Gasolina', 276, 400, 2013, 2019),
(@model_srx, '3.6 V6', 'LFX', '3.6 V6', 'Gasolina', 308, 359, 2012, 2016),
(@model_xt4, '2.0 Turbodiesel', 'LSQ', '2.0 Turbodiesel', 'Diesel', 174, 381, 2020, 2024),
(@model_xt5, '3.6 V6', 'LGX', '3.6 V6', 'Gasolina', 314, 367, 2017, 2024),
(@model_xt6, '3.6 V6', 'LGX', '3.6 V6', 'Gasolina', 310, 367, 2020, 2024),
(@model_ct4, '2.7 Turbo V-Series', 'L3B', '2.7 Turbo 4 cil.', 'Gasolina', 325, 515, 2020, 2024),
(@model_ct5, '6.2 V8 Blackwing', 'LT4', '6.2 V8 Supercharged', 'Gasolina', 668, 893, 2022, 2024),
(@model_seville, '4.6 V8 STS', 'L37', '4.6 V8 Northstar', 'Gasolina', 305, 407, 1998, 2004),
(@model_eldorado, '4.6 V8 ETC', 'L37', '4.6 V8 Northstar', 'Gasolina', 300, 400, 1993, 2002),
(@model_deville, '4.6 V8 DTS', 'L37', '4.6 V8 Northstar', 'Gasolina', 300, 400, 2000, 2005);

-- ==========================================
-- VERSIONES BUICK, GMC Y HOLDEN
-- Clones europeos, mastodontes y V8s australianos
-- ==========================================

-- ------------------------------------------
-- 3. BUICK (7 Modelos - El "Opel" premium americano)
-- ------------------------------------------
SET @buick_id = (SELECT id FROM car_brands WHERE name = 'Buick');

-- Enclave, Encore, Envision, Regal, LaCrosse, Excelle, Riviera
SET @model_enclave = (SELECT id FROM car_models WHERE name = 'Enclave' AND brand_id = @buick_id);
SET @model_encore = (SELECT id FROM car_models WHERE name = 'Encore' AND brand_id = @buick_id);
SET @model_envision = (SELECT id FROM car_models WHERE name = 'Envision' AND brand_id = @buick_id);
SET @model_regal = (SELECT id FROM car_models WHERE name = 'Regal' AND brand_id = @buick_id);
SET @model_lacrosse = (SELECT id FROM car_models WHERE name = 'LaCrosse' AND brand_id = @buick_id);
SET @model_excelle = (SELECT id FROM car_models WHERE name = 'Excelle' AND brand_id = @buick_id);
SET @model_riviera = (SELECT id FROM car_models WHERE name = 'Riviera' AND brand_id = @buick_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_enclave, '3.6 V6 SIDI', 'LLT', '3.6 V6', 'Gasolina', 288, 366, 2008, 2017),
(@model_encore, '1.4 Turbo', 'LUJ', '1.4 Turbo (Es un Opel Mokka)', 'Gasolina', 140, 200, 2013, 2022),
(@model_envision, '2.0T', 'LTG', '2.0 Turbo 4 cil.', 'Gasolina', 252, 353, 2016, 2020),
(@model_regal, '2.0T GS', 'LTG', '2.0 Turbo (Es un Opel Insignia)', 'Gasolina', 259, 400, 2014, 2017),
(@model_lacrosse, '3.6 V6', 'LFX', '3.6 V6 Inyección Directa', 'Gasolina', 303, 358, 2010, 2016),
(@model_excelle, '1.6', 'F16D3', '1.6 16V (Es un Daewoo Lacetti)', 'Gasolina', 109, 150, 2003, 2016),
(@model_riviera, '3.8 V6 Supercharged', 'L67', '3.8 V6 Compresor (Serie II)', 'Gasolina', 240, 380, 1995, 1999);


-- ------------------------------------------
-- 4. GMC (7 Modelos - Los hermanos "Cachas" de Chevrolet)
-- ------------------------------------------
SET @gmc_id = (SELECT id FROM car_brands WHERE name = 'GMC');

SET @model_sierra = (SELECT id FROM car_models WHERE name = 'Sierra' AND brand_id = @gmc_id);
SET @model_canyon = (SELECT id FROM car_models WHERE name = 'Canyon' AND brand_id = @gmc_id);
SET @model_yukon = (SELECT id FROM car_models WHERE name = 'Yukon' AND brand_id = @gmc_id);
SET @model_acadia = (SELECT id FROM car_models WHERE name = 'Acadia' AND brand_id = @gmc_id);
SET @model_terrain = (SELECT id FROM car_models WHERE name = 'Terrain' AND brand_id = @gmc_id);
SET @model_savana = (SELECT id FROM car_models WHERE name = 'Savana' AND brand_id = @gmc_id);
SET @model_vandura = (SELECT id FROM car_models WHERE name = 'Vandura' AND brand_id = @gmc_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_sierra, '6.6 Duramax HD', 'L5P', '6.6 V8 Turbo Diesel (Isuzu)', 'Diesel', 445, 1234, 2017, 2024),
(@model_canyon, '2.8 Duramax', 'LWN', '2.8 4 cil. Turbo Diesel', 'Diesel', 181, 500, 2015, 2022),
(@model_yukon, '6.2 V8 Denali', 'L86', '6.2 V8 EcoTec3', 'Gasolina', 420, 624, 2015, 2020),
(@model_acadia, '3.6 V6', 'LGX', '3.6 V6', 'Gasolina', 310, 367, 2017, 2024),
(@model_terrain, '2.0 Turbo', 'LTG', '2.0 Turbo 4 cil.', 'Gasolina', 252, 353, 2018, 2021),
(@model_savana, '6.0 V8', 'L96', '6.0 V8 Vortec', 'Gasolina', 341, 506, 2010, 2024),
(@model_vandura, '5.7 V8 (El Equipo A)', 'L05', '5.7 V8', 'Gasolina', 195, 407, 1988, 1995);


-- ------------------------------------------
-- 5. HOLDEN (6 Modelos - Locura V8 Australiana)
-- ------------------------------------------
SET @holden_id = (SELECT id FROM car_brands WHERE name = 'Holden');

SET @model_commodore = (SELECT id FROM car_models WHERE name = 'Commodore' AND brand_id = @holden_id);
SET @model_monaro = (SELECT id FROM car_models WHERE name = 'Monaro' AND brand_id = @holden_id);
SET @model_caprice = (SELECT id FROM car_models WHERE name = 'Caprice' AND brand_id = @holden_id);
SET @model_colorado = (SELECT id FROM car_models WHERE name = 'Colorado' AND brand_id = @holden_id);
SET @model_barina = (SELECT id FROM car_models WHERE name = 'Barina' AND brand_id = @holden_id);
SET @model_ute = (SELECT id FROM car_models WHERE name = 'Ute' AND brand_id = @holden_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_commodore, '6.2 V8 SS (VF)', 'LS3', '6.2 V8 (Corvette)', 'Gasolina', 413, 570, 2015, 2017),
(@model_commodore, '3.6 V6 (VE)', 'LY7', '3.6 V6 Alloytec', 'Gasolina', 235, 325, 2006, 2013),
(@model_monaro, '5.7 V8 (CV8)', 'LS1', '5.7 V8', 'Gasolina', 333, 465, 2001, 2005),
(@model_caprice, '6.0 V8', 'L77', '6.0 V8 (Motor de Policía en USA)', 'Gasolina', 348, 517, 2011, 2017),
(@model_colorado, '2.8 TD', 'LWN', '2.8 Turbo Diesel', 'Diesel', 200, 500, 2012, 2020),
(@model_barina, '1.4 16V', 'LUJ', '1.4 Turbo (Es un Opel Corsa/Aveo)', 'Gasolina', 140, 200, 2011, 2018),
(@model_ute, '6.2 V8 SS', 'LS3', '6.2 V8 (Pick-up deportiva)', 'Gasolina', 413, 570, 2015, 2017);

-- ==========================================
-- VERSIONES PONTIAC Y HUMMER
-- Muscle cars, tanques de calle y estrellas de la tele
-- ==========================================

-- ------------------------------------------
-- 6. PONTIAC (8 Modelos - La extinta marca deportiva de GM)
-- ------------------------------------------
SET @pontiac_id = (SELECT id FROM car_brands WHERE name = 'Pontiac');

-- Los Muscle Cars y Deportivos (Firebird, Trans Am, GTO, Solstice)
SET @model_firebird = (SELECT id FROM car_models WHERE name = 'Firebird' AND brand_id = @pontiac_id);
SET @model_transam = (SELECT id FROM car_models WHERE name = 'Trans Am' AND brand_id = @pontiac_id);
SET @model_gto = (SELECT id FROM car_models WHERE name = 'GTO' AND brand_id = @pontiac_id);
SET @model_solstice = (SELECT id FROM car_models WHERE name = 'Solstice' AND brand_id = @pontiac_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_firebird, '3.8 V6 (4ª Gen)', 'L36', '3.8 V6 Series II', 'Gasolina', 200, 305, 1995, 2002),
(@model_transam, '5.7 V8 WS6', 'LS1', '5.7 V8', 'Gasolina', 325, 468, 1998, 2002),
(@model_gto, '6.0 V8 (Base Holden Monaro)', 'LS2', '6.0 V8', 'Gasolina', 400, 542, 2005, 2006),
(@model_solstice, '2.0 GXP (Gemelo Opel GT)', 'LNF', '2.0 Turbo Ecotec', 'Gasolina', 264, 353, 2007, 2009);

-- Berlinas, Coupés y el famoso Aztek (Grand Prix, Grand Am, Aztek, Bonneville)
SET @model_grandprix = (SELECT id FROM car_models WHERE name = 'Grand Prix' AND brand_id = @pontiac_id);
SET @model_grandam = (SELECT id FROM car_models WHERE name = 'Grand Am' AND brand_id = @pontiac_id);
SET @model_aztek = (SELECT id FROM car_models WHERE name = 'Aztek' AND brand_id = @pontiac_id);
SET @model_bonneville = (SELECT id FROM car_models WHERE name = 'Bonneville' AND brand_id = @pontiac_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_grandprix, '3.8 V6 Supercharged (GTP)', 'L67', '3.8 V6 Compresor', 'Gasolina', 260, 380, 2004, 2008),
(@model_grandam, '3.4 V6 Ram Air', 'LA1', '3.4 V6', 'Gasolina', 175, 278, 1999, 2005),
(@model_aztek, '3.4 V6 (El coche de Walter White)', 'LA1', '3.4 V6', 'Gasolina', 185, 285, 2001, 2005),
(@model_bonneville, '4.6 V8 GXP', 'LD8', '4.6 V8 Northstar', 'Gasolina', 275, 407, 2004, 2005);


-- ------------------------------------------
-- 7. HUMMER (4 Modelos - De la guerra a la electricidad)
-- ------------------------------------------
SET @hummer_id = (SELECT id FROM car_brands WHERE name = 'Hummer');

SET @model_h1 = (SELECT id FROM car_models WHERE name = 'H1' AND brand_id = @hummer_id);
SET @model_h2 = (SELECT id FROM car_models WHERE name = 'H2' AND brand_id = @hummer_id);
SET @model_h3 = (SELECT id FROM car_models WHERE name = 'H3' AND brand_id = @hummer_id);
SET @model_ev = (SELECT id FROM car_models WHERE name = 'EV' AND brand_id = @hummer_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_h1, '6.5 V8 Turbo Diesel (Alpha)', 'L65', '6.5 V8 Detroit Diesel', 'Diesel', 195, 583, 1992, 2006),
(@model_h2, '6.0 V8', 'LQ4', '6.0 V8 Vortec', 'Gasolina', 325, 492, 2002, 2007),
(@model_h2, '6.2 V8', 'L92', '6.2 V8 Vortec', 'Gasolina', 393, 563, 2008, 2009),
(@model_h3, '3.7 L5', 'LLR', '3.7 5 cil. en línea', 'Gasolina', 242, 328, 2007, 2010),
(@model_h3, '5.3 V8 Alpha', 'LH8', '5.3 V8', 'Gasolina', 300, 434, 2008, 2010),
(@model_ev, 'Edition 1 (Ultium)', 'Elec', 'Tri-Motor Eléctrico AWD', 'Eléctrico', 1000, 1500, 2021, 2024);

-- ==========================================
-- VERSIONES BMW Y MINI (CORREGIDO)
-- Columna: torque_nm
-- ==========================================

-- ------------------------------------------
-- 1. BMW
-- ------------------------------------------
SET @bmw_id = (SELECT id FROM car_brands WHERE name = 'BMW');

-- Serie 1 a 8
SET @model_s1 = (SELECT id FROM car_models WHERE name = 'Serie 1' AND brand_id = @bmw_id);
SET @model_s2 = (SELECT id FROM car_models WHERE name = 'Serie 2' AND brand_id = @bmw_id);
SET @model_s3 = (SELECT id FROM car_models WHERE name = 'Serie 3' AND brand_id = @bmw_id);
SET @model_s4 = (SELECT id FROM car_models WHERE name = 'Serie 4' AND brand_id = @bmw_id);
SET @model_s5 = (SELECT id FROM car_models WHERE name = 'Serie 5' AND brand_id = @bmw_id);
SET @model_s6 = (SELECT id FROM car_models WHERE name = 'Serie 6' AND brand_id = @bmw_id);
SET @model_s7 = (SELECT id FROM car_models WHERE name = 'Serie 7' AND brand_id = @bmw_id);
SET @model_s8 = (SELECT id FROM car_models WHERE name = 'Serie 8' AND brand_id = @bmw_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_s1, '120d (E87)', 'M47D20', '2.0 Turbodiesel (M47)', 'Diesel', 163, 340, 2004, 2007),
(@model_s1, '118d (E81/E87)', 'N47D20', '2.0 Turbodiesel (N47)', 'Diesel', 143, 300, 2007, 2011),
(@model_s1, '118i (F40)', 'B38A15', '1.5 3 cil. Turbo', 'Gasolina', 140, 220, 2019, 2024),
(@model_s2, '220d (F22)', 'B47D20', '2.0 Turbodiesel', 'Diesel', 190, 400, 2014, 2021),
(@model_s3, '320d (E46)', 'M47D20', '2.0 Turbodiesel', 'Diesel', 150, 330, 2001, 2005),
(@model_s3, '320d (E90)', 'N47D20', '2.0 Turbodiesel (N47)', 'Diesel', 177, 350, 2007, 2011),
(@model_s3, '330e (G20)', 'B48B20', '2.0 PHEV', 'Híbrido Ench.', 292, 420, 2019, 2024),
(@model_s4, '420d (F32)', 'N47D20', '2.0 Turbodiesel', 'Diesel', 184, 380, 2013, 2015),
(@model_s5, '530d (E39)', 'M57D30', '3.0 L6 Turbodiesel', 'Diesel', 193, 410, 1998, 2003),
(@model_s5, '520d (F10)', 'N47D20', '2.0 Turbodiesel', 'Diesel', 184, 380, 2010, 2014),
(@model_s7, '730d (E65)', 'M57TU', '3.0 L6 Turbodiesel', 'Diesel', 218, 500, 2002, 2008),
(@model_s8, '840d (G15)', 'B57D30', '3.0 L6 Turbodiesel', 'Diesel', 320, 680, 2018, 2024);

-- Familia X (SUV)
SET @model_x1 = (SELECT id FROM car_models WHERE name = 'X1' AND brand_id = @bmw_id);
SET @model_x3 = (SELECT id FROM car_models WHERE name = 'X3' AND brand_id = @bmw_id);
SET @model_x5 = (SELECT id FROM car_models WHERE name = 'X5' AND brand_id = @bmw_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_x1, 'sDrive18d (E84)', 'N47D20', '2.0 Turbodiesel', 'Diesel', 143, 320, 2009, 2015),
(@model_x3, '2.0d (E83)', 'M47/N47', '2.0 Turbodiesel', 'Diesel', 150, 330, 2004, 2010),
(@model_x5, '3.0d (E53)', 'M57D30', '3.0 L6 Turbodiesel', 'Diesel', 184, 410, 2001, 2006);

-- Familia M (Motorsport)
SET @model_m3 = (SELECT id FROM car_models WHERE name = 'M3' AND brand_id = @bmw_id);
SET @model_m5 = (SELECT id FROM car_models WHERE name = 'M5' AND brand_id = @bmw_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_m3, 'E46', 'S54B32', '3.2 L6 Atmosférico', 'Gasolina', 343, 365, 2000, 2006),
(@model_m3, 'Competition (G80)', 'S58B30', '3.0 L6 Twin-Turbo', 'Gasolina', 510, 650, 2021, 2024),
(@model_m5, 'E60', 'S85B50', '5.0 V10 Atmosférico', 'Gasolina', 507, 520, 2005, 2010);

-- ------------------------------------------
-- 2. MINI
-- ------------------------------------------
INSERT IGNORE INTO car_brands (name) VALUES ('MINI');
SET @mini_id = (SELECT id FROM car_brands WHERE name = 'MINI');

INSERT IGNORE INTO car_models (brand_id, name) VALUES
(@mini_id, 'Hatch'),
(@mini_id, 'Countryman');

SET @model_hatch = (SELECT id FROM car_models WHERE name = 'Hatch' AND brand_id = @mini_id);
SET @model_country = (SELECT id FROM car_models WHERE name = 'Countryman' AND brand_id = @mini_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_hatch, 'Cooper S (F56)', 'B48A20', '2.0 Turbo', 'Gasolina', 192, 280, 2014, 2024),
(@model_hatch, 'One 1.5', 'B38A12', '1.5 Turbo 3L', 'Gasolina', 102, 190, 2014, 2024),
(@model_country, 'Cooper D All4', 'B47C20', '2.0 Diesel', 'Diesel', 150, 330, 2017, 2024);

-- ==========================================
-- VERSIONES ROLLS-ROYCE
-- El lujo británico con corazón V12 de BMW y el eterno V8 L410
-- ==========================================

SET @rolls_id = (SELECT id FROM car_brands WHERE name = 'Rolls-Royce');

-- ------------------------------------------
-- 1. MODELOS MODERNOS (Era BMW)
-- ------------------------------------------

-- Phantom (El buque insignia)
SET @model_phantom = (SELECT id FROM car_models WHERE name = 'Phantom' AND brand_id = @rolls_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_phantom, '6.75 V12 (Phantom VII)', 'N73B68', '6.75 V12 Atmosférico (BMW)', 'Gasolina', 460, 720, 2003, 2017),
(@model_phantom, '6.75 V12 (Phantom VIII)', 'N74B68', '6.75 V12 Twin-Turbo (BMW)', 'Gasolina', 571, 900, 2017, 2024);

-- Ghost y Wraith (Las berlinas y coupés "deportivos")
SET @model_ghost = (SELECT id FROM car_models WHERE name = 'Ghost' AND brand_id = @rolls_id);
SET @model_wraith = (SELECT id FROM car_models WHERE name = 'Wraith' AND brand_id = @rolls_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ghost, '6.6 V12 (Series I/II)', 'N74B66', '6.6 V12 Twin-Turbo (BMW)', 'Gasolina', 570, 780, 2009, 2020),
(@model_ghost, '6.75 V12 Black Badge', 'N74B68', '6.75 V12 Twin-Turbo', 'Gasolina', 600, 900, 2021, 2024),
(@model_wraith, '6.6 V12 Black Badge', 'N74B66', '6.6 V12 Twin-Turbo', 'Gasolina', 632, 870, 2013, 2023);

-- Dawn (El descapotable) y Cullinan (El SUV)
SET @model_dawn = (SELECT id FROM car_models WHERE name = 'Dawn' AND brand_id = @rolls_id);
SET @model_cullinan = (SELECT id FROM car_models WHERE name = 'Cullinan' AND brand_id = @rolls_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_dawn, '6.6 V12', 'N74B66', '6.6 V12 Twin-Turbo', 'Gasolina', 571, 820, 2015, 2023),
(@model_cullinan, '6.75 V12', 'N74B68', '6.75 V12 Twin-Turbo', 'Gasolina', 571, 850, 2018, 2024);

-- Spectre (La nueva era eléctrica)
SET @model_spectre = (SELECT id FROM car_models WHERE name = 'Spectre' AND brand_id = @rolls_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_spectre, 'Dual Motor EV', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 585, 900, 2023, 2024);


-- ------------------------------------------
-- 2. CLÁSICOS (El reinado del motor L410)
-- ------------------------------------------

-- Silver Shadow y sus derivados (Silver Spirit, Silver Spur)
SET @model_shadow = (SELECT id FROM car_models WHERE name = 'Silver Shadow' AND brand_id = @rolls_id);
SET @model_spirit = (SELECT id FROM car_models WHERE name = 'Silver Spirit' AND brand_id = @rolls_id);
SET @model_spur = (SELECT id FROM car_models WHERE name = 'Silver Spur' AND brand_id = @rolls_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_shadow, '6.75 V8', 'L410', '6.75 V8 OHV (Doble Carburador SU)', 'Gasolina', 189, 400, 1965, 1980),
(@model_spirit, '6.75 V8', 'L410I', '6.75 V8 OHV Inyección', 'Gasolina', 240, 450, 1980, 1998),
(@model_spur, '6.75 V8 LWB', 'L410I', '6.75 V8 OHV Inyección', 'Gasolina', 240, 450, 1980, 1998);

-- Corniche (El descapotable clásico)
SET @model_corniche = (SELECT id FROM car_models WHERE name = 'Corniche' AND brand_id = @rolls_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_corniche, '6.75 V8 (I a IV)', 'L410', '6.75 V8 OHV', 'Gasolina', 240, 450, 1971, 1995),
(@model_corniche, '6.75 V8 Turbo (V)', 'L410T', '6.75 V8 Turbocharged', 'Gasolina', 325, 738, 2000, 2002);

-- ==========================================
-- VERSIONES HONDA Y ACURA
-- El reino del VTEC, los K20 y la ingeniería japonesa pura
-- ==========================================

-- ------------------------------------------
-- 1. HONDA (24 Modelos)
-- ------------------------------------------
SET @honda_id = (SELECT id FROM car_brands WHERE name = 'Honda');

-- Civic (La leyenda incombustible)
SET @model_civic = (SELECT id FROM car_models WHERE name = 'Civic' AND brand_id = @honda_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_civic, 'Type R (EP3)', 'K20A2', '2.0 i-VTEC', 'Gasolina', 200, 196, 2001, 2005),
(@model_civic, 'Type R (FK8)', 'K20C1', '2.0 VTEC Turbo', 'Gasolina', 320, 400, 2017, 2021),
(@model_civic, '1.6 i-DTEC (FK3)', 'N16A1', '1.6 Turbo Diesel', 'Diesel', 120, 300, 2013, 2017),
(@model_civic, '2.0 e:HEV (FL4)', 'LFC2', '2.0 Híbrido i-MMD', 'Híbrido', 184, 315, 2022, 2024);

-- Accord
SET @model_accord = (SELECT id FROM car_models WHERE name = 'Accord' AND brand_id = @honda_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_accord, '2.2 i-CTDi (VII Gen)', 'N22A1', '2.2 Turbo Diesel', 'Diesel', 140, 340, 2003, 2008),
(@model_accord, '2.4 i-VTEC (VII/VIII)', 'K24A3', '2.4 i-VTEC', 'Gasolina', 190, 223, 2003, 2015),
(@model_accord, '2.2 i-DTEC (VIII Gen)', 'N22B1', '2.2 Turbo Diesel', 'Diesel', 150, 350, 2008, 2015);

-- SUV (CR-V, HR-V, ZR-V, Pilot, Passport)
SET @model_crv = (SELECT id FROM car_models WHERE name = 'CR-V' AND brand_id = @honda_id);
SET @model_hrv = (SELECT id FROM car_models WHERE name = 'HR-V' AND brand_id = @honda_id);
SET @model_zrv = (SELECT id FROM car_models WHERE name = 'ZR-V' AND brand_id = @honda_id);
SET @model_pilot = (SELECT id FROM car_models WHERE name = 'Pilot' AND brand_id = @honda_id);
SET @model_passport = (SELECT id FROM car_models WHERE name = 'Passport' AND brand_id = @honda_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_crv, '2.2 i-DTEC', 'N22B4', '2.2 Turbo Diesel', 'Diesel', 150, 350, 2012, 2018),
(@model_crv, '2.0 i-MMD Hybrid', 'LFB1', '2.0 Híbrido', 'Híbrido', 184, 315, 2018, 2024),
(@model_hrv, '1.6 i-DTEC', 'N16A3', '1.6 Turbo Diesel', 'Diesel', 120, 300, 2015, 2020),
(@model_hrv, '1.5 e:HEV', 'LEC3', '1.5 Híbrido', 'Híbrido', 131, 253, 2021, 2024),
(@model_zrv, '2.0 e:HEV', 'LFC2', '2.0 Híbrido', 'Híbrido', 184, 315, 2023, 2024),
(@model_pilot, '3.5 V6 i-VTEC', 'J35Y6', '3.5 V6', 'Gasolina', 280, 355, 2016, 2024),
(@model_passport, '3.5 V6', 'J35Y6', '3.5 V6', 'Gasolina', 280, 355, 2019, 2024);

-- Utilitarios y Monovolúmenes (Jazz, Fit, FR-V, Stream, Odyssey, City, Logo)
SET @model_jazz = (SELECT id FROM car_models WHERE name = 'Jazz' AND brand_id = @honda_id);
SET @model_fit = (SELECT id FROM car_models WHERE name = 'Fit' AND brand_id = @honda_id);
SET @model_frv = (SELECT id FROM car_models WHERE name = 'FR-V' AND brand_id = @honda_id);
SET @model_stream = (SELECT id FROM car_models WHERE name = 'Stream' AND brand_id = @honda_id);
SET @model_odyssey = (SELECT id FROM car_models WHERE name = 'Odyssey' AND brand_id = @honda_id);
SET @model_city = (SELECT id FROM car_models WHERE name = 'City' AND brand_id = @honda_id);
SET @model_logo = (SELECT id FROM car_models WHERE name = 'Logo' AND brand_id = @honda_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_jazz, '1.4 i-VTEC', 'L13Z1', '1.3 4 cil.', 'Gasolina', 99, 127, 2008, 2015),
(@model_jazz, '1.5 e:HEV', 'LEB8', '1.5 Híbrido', 'Híbrido', 109, 253, 2020, 2024),
(@model_fit, '1.5 i-VTEC', 'L15A', '1.5 4 cil.', 'Gasolina', 110, 143, 2007, 2014),
(@model_frv, '2.2 i-CTDi', 'N22A1', '2.2 Turbo Diesel', 'Diesel', 140, 340, 2005, 2009),
(@model_stream, '2.0 i-VTEC', 'K20A1', '2.0 i-VTEC', 'Gasolina', 156, 192, 2001, 2005),
(@model_odyssey, '3.5 V6', 'J35Y6', '3.5 V6', 'Gasolina', 280, 355, 2018, 2024),
(@model_city, '1.5 i-VTEC', 'L15Z', '1.5 4 cil.', 'Gasolina', 120, 145, 2014, 2020),
(@model_logo, '1.3 8V', 'D13B7', '1.3 8V', 'Gasolina', 65, 108, 1996, 2001);

-- Deportivos y Exóticos (NSX, S2000, Prelude, Integra, CR-Z, S660, Insight, Ridgeline, Legend, e)
SET @model_nsx = (SELECT id FROM car_models WHERE name = 'NSX' AND brand_id = @honda_id);
SET @model_s2000 = (SELECT id FROM car_models WHERE name = 'S2000' AND brand_id = @honda_id);
SET @model_prelude = (SELECT id FROM car_models WHERE name = 'Prelude' AND brand_id = @honda_id);
SET @model_integra = (SELECT id FROM car_models WHERE name = 'Integra' AND brand_id = @honda_id);
SET @model_crz = (SELECT id FROM car_models WHERE name = 'CR-Z' AND brand_id = @honda_id);
SET @model_s660 = (SELECT id FROM car_models WHERE name = 'S660' AND brand_id = @honda_id);
SET @model_insight = (SELECT id FROM car_models WHERE name = 'Insight' AND brand_id = @honda_id);
SET @model_ridgeline = (SELECT id FROM car_models WHERE name = 'Ridgeline' AND brand_id = @honda_id);
SET @model_legend = (SELECT id FROM car_models WHERE name = 'Legend' AND brand_id = @honda_id);
SET @model_e = (SELECT id FROM car_models WHERE name = 'e' AND brand_id = @honda_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_nsx, '3.0 V6 (NA1)', 'C30A', '3.0 V6 VTEC', 'Gasolina', 274, 285, 1990, 1996),
(@model_s2000, '2.0 VTEC (AP1)', 'F20C', '2.0 VTEC (9000 RPM)', 'Gasolina', 240, 208, 1999, 2009),
(@model_prelude, '2.2 VTi (5ª Gen)', 'H22A5', '2.2 VTEC', 'Gasolina', 185, 206, 1997, 2001),
(@model_integra, 'Type R (DC2)', 'B18C6', '1.8 VTEC', 'Gasolina', 190, 178, 1998, 2001),
(@model_crz, '1.5 IMA', 'LEA', '1.5 Híbrido', 'Híbrido', 124, 174, 2010, 2016),
(@model_s660, '0.66 Turbo', 'S07A', '0.66 Turbo 3 cil. (Kei Car)', 'Gasolina', 64, 104, 2015, 2022),
(@model_insight, '1.3 IMA', 'LDA3', '1.3 Híbrido', 'Híbrido', 88, 121, 2009, 2014),
(@model_ridgeline, '3.5 V6', 'J35Y6', '3.5 V6 (Pick-up)', 'Gasolina', 280, 355, 2017, 2024),
(@model_legend, '3.5 V6 SH-AWD', 'J35A8', '3.5 V6', 'Gasolina', 295, 351, 2004, 2008),
(@model_e, 'Advance', 'MCF5', 'Motor Eléctrico Trasero', 'Eléctrico', 154, 315, 2020, 2024);


-- ------------------------------------------
-- 2. ACURA (14 Modelos)
-- ------------------------------------------
SET @acura_id = (SELECT id FROM car_brands WHERE name = 'Acura');

SET @model_a_integra = (SELECT id FROM car_models WHERE name = 'Integra' AND brand_id = @acura_id);
SET @model_a_legend = (SELECT id FROM car_models WHERE name = 'Legend' AND brand_id = @acura_id);
SET @model_a_mdx = (SELECT id FROM car_models WHERE name = 'MDX' AND brand_id = @acura_id);
SET @model_a_rdx = (SELECT id FROM car_models WHERE name = 'RDX' AND brand_id = @acura_id);
SET @model_a_rsx = (SELECT id FROM car_models WHERE name = 'RSX' AND brand_id = @acura_id);
SET @model_a_tl = (SELECT id FROM car_models WHERE name = 'TL' AND brand_id = @acura_id);
SET @model_a_tlx = (SELECT id FROM car_models WHERE name = 'TLX' AND brand_id = @acura_id);
SET @model_a_tsx = (SELECT id FROM car_models WHERE name = 'TSX' AND brand_id = @acura_id);
SET @model_a_rl = (SELECT id FROM car_models WHERE name = 'RL' AND brand_id = @acura_id);
SET @model_a_rlx = (SELECT id FROM car_models WHERE name = 'RLX' AND brand_id = @acura_id);
SET @model_a_nsx = (SELECT id FROM car_models WHERE name = 'NSX' AND brand_id = @acura_id);
SET @model_a_zdx = (SELECT id FROM car_models WHERE name = 'ZDX' AND brand_id = @acura_id);
SET @model_a_ilx = (SELECT id FROM car_models WHERE name = 'ILX' AND brand_id = @acura_id);
SET @model_a_cdx = (SELECT id FROM car_models WHERE name = 'CDX' AND brand_id = @acura_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_a_integra, '1.5T A-Spec (Gen 5)', 'L15CA', '1.5 VTEC Turbo', 'Gasolina', 200, 260, 2023, 2024),
(@model_a_legend, '3.2 V6 (Gen 2)', 'C32A', '3.2 V6', 'Gasolina', 200, 285, 1991, 1995),
(@model_a_mdx, '3.5 V6 SH-AWD', 'J35Y5', '3.5 V6', 'Gasolina', 290, 362, 2014, 2020),
(@model_a_rdx, '2.0T A-Spec', 'K20C4', '2.0 VTEC Turbo', 'Gasolina', 272, 380, 2019, 2024),
(@model_a_rsx, 'Type-S', 'K20A2', '2.0 i-VTEC (Integra DC5 remarcado)', 'Gasolina', 200, 193, 2002, 2006),
(@model_a_tl, '3.2 V6 (Gen 3)', 'J32A3', '3.2 V6', 'Gasolina', 258, 316, 2004, 2008),
(@model_a_tlx, '3.0T V6 Type S', 'J30AC', '3.0 V6 Turbo', 'Gasolina', 355, 480, 2021, 2024),
(@model_a_tsx, '2.4 i-VTEC', 'K24A2', '2.4 i-VTEC (Accord Europeo)', 'Gasolina', 201, 230, 2004, 2008),
(@model_a_rl, '3.5 V6 SH-AWD', 'J35A8', '3.5 V6 (Honda Legend)', 'Gasolina', 290, 351, 2005, 2012),
(@model_a_rlx, '3.5 V6 Sport Hybrid', 'J35Y4', '3.5 V6 Híbrido SH-AWD', 'Híbrido', 377, 462, 2014, 2020),
(@model_a_nsx, '3.5 V6 Twin-Turbo Hybrid', 'JNC1', '3.5 V6 Biturbo + 3 Motores Elec.', 'Híbrido', 581, 645, 2017, 2022),
(@model_a_zdx, '3.7 V6', 'J37A5', '3.7 V6 SH-AWD', 'Gasolina', 300, 366, 2010, 2013),
(@model_a_ilx, '2.4 i-VTEC', 'K24V7', '2.4 i-VTEC', 'Gasolina', 201, 244, 2016, 2022),
(@model_a_cdx, '1.5T', 'L15B9', '1.5 VTEC Turbo', 'Gasolina', 182, 240, 2016, 2022);

-- ==========================================
-- VERSIONES JAGUAR, LAND ROVER Y TATA
-- Herencia Ford/PSA, motores Ingenium y low-cost indio
-- ==========================================

-- ------------------------------------------
-- 1. JAGUAR (12 Modelos)
-- ------------------------------------------
SET @jaguar_id = (SELECT id FROM car_brands WHERE name = 'Jaguar');

-- Berlinas (XE, XF, XJ, S-Type, X-Type)
SET @model_xe = (SELECT id FROM car_models WHERE name = 'XE' AND brand_id = @jaguar_id);
SET @model_xf = (SELECT id FROM car_models WHERE name = 'XF' AND brand_id = @jaguar_id);
SET @model_xj = (SELECT id FROM car_models WHERE name = 'XJ' AND brand_id = @jaguar_id);
SET @model_stype = (SELECT id FROM car_models WHERE name = 'S-Type' AND brand_id = @jaguar_id);
SET @model_xtype = (SELECT id FROM car_models WHERE name = 'X-Type' AND brand_id = @jaguar_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_xe, '2.0d (Ingenium)', '204DTD', '2.0 Turbodiesel Ingenium', 'Diesel', 180, 430, 2015, 2024),
(@model_xf, '3.0 V6 Diesel', 'AJV6D', '3.0 V6 Turbodiesel (Lion/PSA)', 'Diesel', 240, 500, 2009, 2015),
(@model_xf, '2.2d', '224DT', '2.2 Turbodiesel (Ford/PSA DW12)', 'Diesel', 190, 450, 2011, 2015),
(@model_xj, '2.7 V6 Diesel (X350)', 'AJD-V6', '2.7 V6 Turbodiesel (Lion/PSA)', 'Diesel', 207, 435, 2005, 2009),
(@model_xj, '5.0 V8 (X351)', 'AJ133', '5.0 V8 Atmosférico', 'Gasolina', 385, 515, 2009, 2019),
(@model_stype, '3.0 V6', 'AJ30', '3.0 V6 (Base Ford Duratec)', 'Gasolina', 238, 293, 1999, 2007),
(@model_xtype, '2.0d', 'FMBA', '2.0 Turbodiesel (Ford Mondeo TDCi)', 'Diesel', 130, 330, 2003, 2009);

-- SUV (E-Pace, F-Pace, I-Pace)
SET @model_epace = (SELECT id FROM car_models WHERE name = 'E-Pace' AND brand_id = @jaguar_id);
SET @model_fpace = (SELECT id FROM car_models WHERE name = 'F-Pace' AND brand_id = @jaguar_id);
SET @model_ipace = (SELECT id FROM car_models WHERE name = 'I-Pace' AND brand_id = @jaguar_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_epace, 'D150 FWD', '204DTD', '2.0 Turbodiesel Ingenium', 'Diesel', 150, 380, 2017, 2024),
(@model_fpace, '3.0d V6 AWD', '306DT', '3.0 V6 Turbodiesel', 'Diesel', 300, 700, 2016, 2020),
(@model_ipace, 'EV400', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 400, 696, 2018, 2024);

-- Deportivos y Clásicos (F-Type, XK, XJS, E-Type)
SET @model_ftype = (SELECT id FROM car_models WHERE name = 'F-Type' AND brand_id = @jaguar_id);
SET @model_xk = (SELECT id FROM car_models WHERE name = 'XK' AND brand_id = @jaguar_id);
SET @model_xjs = (SELECT id FROM car_models WHERE name = 'XJS' AND brand_id = @jaguar_id);
SET @model_etype = (SELECT id FROM car_models WHERE name = 'E-Type' AND brand_id = @jaguar_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ftype, '3.0 V6 S', 'AJ126', '3.0 V6 Supercharged', 'Gasolina', 380, 460, 2013, 2019),
(@model_ftype, '5.0 V8 R', 'AJ133', '5.0 V8 Supercharged', 'Gasolina', 550, 680, 2014, 2024),
(@model_xk, '4.2 V8 (X150)', 'AJ34', '4.2 V8 Atmosférico', 'Gasolina', 298, 411, 2006, 2009),
(@model_xjs, '5.3 V12 HE', 'V12 HE', '5.3 V12 High Efficiency', 'Gasolina', 295, 430, 1981, 1992),
(@model_etype, '4.2 Series 1', 'XK 4.2', '4.2 L6', 'Gasolina', 265, 384, 1964, 1968);


-- ------------------------------------------
-- 2. LAND ROVER (8 Modelos)
-- ------------------------------------------
SET @landrover_id = (SELECT id FROM car_brands WHERE name = 'Land Rover');

SET @model_defender = (SELECT id FROM car_models WHERE name = 'Defender' AND brand_id = @landrover_id);
SET @model_discovery = (SELECT id FROM car_models WHERE name = 'Discovery' AND brand_id = @landrover_id);
SET @model_discosport = (SELECT id FROM car_models WHERE name = 'Discovery Sport' AND brand_id = @landrover_id);
SET @model_rr = (SELECT id FROM car_models WHERE name = 'Range Rover' AND brand_id = @landrover_id);
SET @model_rrsport = (SELECT id FROM car_models WHERE name = 'Range Rover Sport' AND brand_id = @landrover_id);
SET @model_rrvelar = (SELECT id FROM car_models WHERE name = 'Range Rover Velar' AND brand_id = @landrover_id);
SET @model_rrevoque = (SELECT id FROM car_models WHERE name = 'Range Rover Evoque' AND brand_id = @landrover_id);
SET @model_freelander = (SELECT id FROM car_models WHERE name = 'Freelander' AND brand_id = @landrover_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_defender, '2.5 Td5', '10P / 15P', '2.5 L5 Turbodiesel', 'Diesel', 122, 300, 1998, 2006),
(@model_defender, '2.4 TDCi (Puma)', 'ZSD-424', '2.4 Turbodiesel (Ford Transit)', 'Diesel', 122, 360, 2007, 2011),
(@model_defender, 'D300 MHEV (L663)', 'PT306', '3.0 L6 Turbodiesel Híbrido', 'Diesel', 300, 650, 2020, 2024),
(@model_discovery, '2.7 TDV6 (Disco 3)', '276DT', '2.7 V6 Turbodiesel (Lion/PSA)', 'Diesel', 190, 440, 2004, 2009),
(@model_discovery, '3.0 SDV6 (Disco 4)', '306DT', '3.0 V6 Turbodiesel Twin-Turbo', 'Diesel', 256, 600, 2009, 2016),
(@model_discosport, '2.0 TD4', '204DTD', '2.0 Turbodiesel Ingenium', 'Diesel', 150, 380, 2015, 2024),
(@model_rr, '3.0 TD6 (L322)', 'M57D30', '3.0 L6 Turbodiesel (Motor BMW M57)', 'Diesel', 177, 390, 2002, 2006),
(@model_rr, '4.4 SDV8 (L405)', '448DT', '4.4 V8 Turbodiesel', 'Diesel', 339, 740, 2012, 2021),
(@model_rrsport, '2.7 TDV6 (L320)', '276DT', '2.7 V6 Turbodiesel', 'Diesel', 190, 440, 2005, 2009),
(@model_rrsport, '3.0 SDV6 (L494)', '306DT', '3.0 V6 Turbodiesel', 'Diesel', 292, 600, 2013, 2022),
(@model_rrvelar, 'D240', '204DTA', '2.0 Turbodiesel Biturbo Ingenium', 'Diesel', 240, 500, 2017, 2021),
(@model_rrevoque, '2.2 eD4 (L538)', '224DT', '2.2 Turbodiesel (Ford/PSA DW12)', 'Diesel', 150, 380, 2011, 2015),
(@model_rrevoque, '2.0 TD4 (L538)', '204DTD', '2.0 Turbodiesel Ingenium', 'Diesel', 150, 380, 2015, 2018),
(@model_freelander, '2.0 Td4 (L314)', 'M47R', '2.0 Turbodiesel (Motor BMW M47)', 'Diesel', 112, 260, 2000, 2006),
(@model_freelander, '2.2 TD4 (L359)', '224DT', '2.2 Turbodiesel (Ford/PSA DW12)', 'Diesel', 152, 400, 2006, 2014);


-- ------------------------------------------
-- 3. TATA (12 Modelos)
-- ------------------------------------------
SET @tata_id = (SELECT id FROM car_brands WHERE name = 'Tata');

SET @model_safari = (SELECT id FROM car_models WHERE name = 'Safari' AND brand_id = @tata_id);
SET @model_nexon = (SELECT id FROM car_models WHERE name = 'Nexon' AND brand_id = @tata_id);
SET @model_harrier = (SELECT id FROM car_models WHERE name = 'Harrier' AND brand_id = @tata_id);
SET @model_tiago = (SELECT id FROM car_models WHERE name = 'Tiago' AND brand_id = @tata_id);
SET @model_tigor = (SELECT id FROM car_models WHERE name = 'Tigor' AND brand_id = @tata_id);
SET @model_altroz = (SELECT id FROM car_models WHERE name = 'Altroz' AND brand_id = @tata_id);
SET @model_punch = (SELECT id FROM car_models WHERE name = 'Punch' AND brand_id = @tata_id);
SET @model_nano = (SELECT id FROM car_models WHERE name = 'Nano' AND brand_id = @tata_id);
SET @model_indica = (SELECT id FROM car_models WHERE name = 'Indica' AND brand_id = @tata_id);
SET @model_indigo = (SELECT id FROM car_models WHERE name = 'Indigo' AND brand_id = @tata_id);
SET @model_aria = (SELECT id FROM car_models WHERE name = 'Aria' AND brand_id = @tata_id);
SET @model_sierra = (SELECT id FROM car_models WHERE name = 'Sierra' AND brand_id = @tata_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_safari, '2.2 DICOR', 'VTT DICOR', '2.2 Turbodiesel', 'Diesel', 140, 320, 2007, 2017),
(@model_safari, '2.0 Kryotec', 'Multijet II', '2.0 Turbodiesel (Motor Fiat)', 'Diesel', 170, 350, 2021, 2024),
(@model_nexon, '1.5 Revotorq', 'Revotorq', '1.5 Turbodiesel', 'Diesel', 110, 260, 2017, 2024),
(@model_harrier, '2.0 Kryotec', 'Multijet II', '2.0 Turbodiesel (Motor Fiat)', 'Diesel', 170, 350, 2019, 2024),
(@model_tiago, '1.2 Revotron', 'Revotron', '1.2 3 cil.', 'Gasolina', 86, 113, 2016, 2024),
(@model_tigor, '1.2 Revotron', 'Revotron', '1.2 3 cil.', 'Gasolina', 86, 113, 2017, 2024),
(@model_altroz, '1.5 Revotorq', 'Revotorq', '1.5 Turbodiesel', 'Diesel', 90, 200, 2020, 2024),
(@model_punch, '1.2 Revotron', 'Revotron', '1.2 3 cil.', 'Gasolina', 86, 113, 2021, 2024),
(@model_nano, '0.6 MPFI (El más barato del mundo)', 'Nano 0.6', '0.6 2 cil.', 'Gasolina', 38, 51, 2008, 2018),
(@model_indica, '1.4 D V2', '475 IDI', '1.4 Diesel Atmosférico', 'Diesel', 53, 85, 1998, 2013),
(@model_indigo, '1.4 TDI', '1.4 TDI', '1.4 Turbodiesel', 'Diesel', 71, 135, 2002, 2016),
(@model_aria, '2.2 DICOR', 'VTT DICOR', '2.2 Turbodiesel', 'Diesel', 150, 320, 2010, 2017),
(@model_sierra, '2.0 Diesel', 'XD88', '2.0 Diesel (Motor Peugeot)', 'Diesel', 63, 118, 1991, 2000);

-- ==========================================
-- VERSIONES VOLVO, POLESTAR Y GEELY
-- De los ladrillos indestructibles a la era eléctrica china
-- ==========================================

-- ------------------------------------------
-- 1. VOLVO (19 Modelos)
-- ------------------------------------------
SET @volvo_id = (SELECT id FROM car_brands WHERE name = 'Volvo');

-- Los SUV (XC90, XC60, XC40, EX90, EX30, C40)
SET @model_xc90 = (SELECT id FROM car_models WHERE name = 'XC90' AND brand_id = @volvo_id);
SET @model_xc60 = (SELECT id FROM car_models WHERE name = 'XC60' AND brand_id = @volvo_id);
SET @model_xc40 = (SELECT id FROM car_models WHERE name = 'XC40' AND brand_id = @volvo_id);
SET @model_ex90 = (SELECT id FROM car_models WHERE name = 'EX90' AND brand_id = @volvo_id);
SET @model_ex30 = (SELECT id FROM car_models WHERE name = 'EX30' AND brand_id = @volvo_id);
SET @model_c40 = (SELECT id FROM car_models WHERE name = 'C40' AND brand_id = @volvo_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_xc90, '2.4 D5 (Mk1)', 'D5244T', '2.4 L5 Turbodiesel', 'Diesel', 163, 340, 2002, 2014),
(@model_xc90, 'T8 Twin Engine PHEV (Mk2)', 'B4204T35', '2.0 Turbo+Compresor PHEV', 'Híbrido Ench.', 407, 640, 2015, 2024),
(@model_xc60, '2.4 D5 AWD', 'D5244T10', '2.4 L5 Turbodiesel Biturbo', 'Diesel', 205, 420, 2008, 2017),
(@model_xc60, '2.0 B4 MHEV', 'D420T8', '2.0 Turbodiesel Mild-Hybrid', 'Diesel', 197, 420, 2020, 2024),
(@model_xc40, '1.5 T3', 'B3154T', '1.5 Turbo 3 cil.', 'Gasolina', 156, 265, 2018, 2024),
(@model_xc40, 'Recharge Twin Motor', 'EAD3', 'Doble Motor Eléctrico AWD', 'Eléctrico', 408, 660, 2020, 2024),
(@model_ex90, 'Twin Motor Performance', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 517, 910, 2024, 2025),
(@model_ex30, 'Single Motor Extended Range', 'Elec', 'Motor Eléctrico Trasero', 'Eléctrico', 272, 343, 2023, 2025),
(@model_c40, 'Recharge Single Motor', 'EAD3', 'Motor Eléctrico FWD/RWD', 'Eléctrico', 231, 330, 2021, 2024);

-- Las Berlinas y Familiares Modernos (S90, V90, S60, V60)
SET @model_s90 = (SELECT id FROM car_models WHERE name = 'S90' AND brand_id = @volvo_id);
SET @model_v90 = (SELECT id FROM car_models WHERE name = 'V90' AND brand_id = @volvo_id);
SET @model_s60 = (SELECT id FROM car_models WHERE name = 'S60' AND brand_id = @volvo_id);
SET @model_v60 = (SELECT id FROM car_models WHERE name = 'V60' AND brand_id = @volvo_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_s90, '2.0 D5 AWD', 'D4204T23', '2.0 Turbodiesel Drive-E', 'Diesel', 235, 480, 2016, 2024),
(@model_v90, '2.0 T6 AWD', 'B4204T27', '2.0 Turbo+Compresor', 'Gasolina', 320, 400, 2016, 2021),
(@model_s60, '2.4 D5 (Mk1)', 'D5244T', '2.4 L5 Turbodiesel', 'Diesel', 163, 340, 2000, 2009),
(@model_s60, 'T8 Polestar Engineered', 'B4204T48', '2.0 Turbo PHEV', 'Híbrido Ench.', 405, 670, 2019, 2024),
(@model_v60, '2.0 D4', 'D4204T14', '2.0 Turbodiesel Drive-E', 'Diesel', 190, 400, 2013, 2018);

-- Los Compactos y la era Ford/PSA (V40, V50, C30, S40)
SET @model_v40 = (SELECT id FROM car_models WHERE name = 'V40' AND brand_id = @volvo_id);
SET @model_v50 = (SELECT id FROM car_models WHERE name = 'V50' AND brand_id = @volvo_id);
SET @model_c30 = (SELECT id FROM car_models WHERE name = 'C30' AND brand_id = @volvo_id);
SET @model_s40 = (SELECT id FROM car_models WHERE name = 'S40' AND brand_id = @volvo_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_v40, '1.6 D2', 'D4162T', '1.6 Turbodiesel (PSA 1.6 HDi 8v)', 'Diesel', 115, 270, 2012, 2015),
(@model_v40, '2.0 D4', 'D4204T14', '2.0 Turbodiesel (Drive-E)', 'Diesel', 190, 400, 2014, 2019),
(@model_v50, '2.0D', 'D4204T', '2.0 Turbodiesel (PSA DW10)', 'Diesel', 136, 320, 2004, 2010),
(@model_c30, '1.6D', 'D4164T', '1.6 Turbodiesel (PSA 1.6 HDi 16v)', 'Diesel', 109, 240, 2006, 2010),
(@model_c30, '2.5 T5', 'B5254T3', '2.5 L5 Turbo (Mismo del Focus ST)', 'Gasolina', 220, 320, 2006, 2012),
(@model_s40, '1.9D (Mk1)', 'D4192T2', '1.9 Turbodiesel (Renault F9Q)', 'Diesel', 95, 190, 1999, 2004),
(@model_s40, '2.4i (Mk2)', 'B5244S4', '2.4 L5 Atmosférico', 'Gasolina', 170, 230, 2004, 2012);

-- Los Clásicos y Ejecutivos (S80, 850, 740, 940, 240)
SET @model_s80 = (SELECT id FROM car_models WHERE name = 'S80' AND brand_id = @volvo_id);
SET @model_850 = (SELECT id FROM car_models WHERE name = '850' AND brand_id = @volvo_id);
SET @model_740 = (SELECT id FROM car_models WHERE name = '740' AND brand_id = @volvo_id);
SET @model_940 = (SELECT id FROM car_models WHERE name = '940' AND brand_id = @volvo_id);
SET @model_240 = (SELECT id FROM car_models WHERE name = '240' AND brand_id = @volvo_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_s80, '4.4 V8 AWD', 'B8444S', '4.4 V8 Atmosférico (Yamaha)', 'Gasolina', 315, 440, 2006, 2010),
(@model_850, '2.3 T5-R', 'B5234T5', '2.3 L5 Turbo (El mito volador)', 'Gasolina', 240, 330, 1995, 1996),
(@model_740, '2.3 GLE', 'B230F', '2.3 L4 (Redblock Indestructible)', 'Gasolina', 114, 185, 1984, 1992),
(@model_940, '2.3 Turbo', 'B230FT', '2.3 L4 Turbo (Redblock)', 'Gasolina', 165, 264, 1990, 1998),
(@model_240, '2.3 GL', 'B230F', '2.3 L4 (Redblock)', 'Gasolina', 114, 185, 1984, 1993);


-- ------------------------------------------
-- 2. POLESTAR (6 Modelos - La división deportiva / EV)
-- ------------------------------------------
SET @polestar_id = (SELECT id FROM car_brands WHERE name = 'Polestar');

SET @model_p1 = (SELECT id FROM car_models WHERE name = '1' AND brand_id = @polestar_id);
SET @model_p2 = (SELECT id FROM car_models WHERE name = '2' AND brand_id = @polestar_id);
SET @model_p3 = (SELECT id FROM car_models WHERE name = '3' AND brand_id = @polestar_id);
SET @model_p4 = (SELECT id FROM car_models WHERE name = '4' AND brand_id = @polestar_id);
SET @model_p5 = (SELECT id FROM car_models WHERE name = '5' AND brand_id = @polestar_id);
SET @model_p6 = (SELECT id FROM car_models WHERE name = '6' AND brand_id = @polestar_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_p1, '2.0 PHEV', 'B4204T48', '2.0 Turbo/Compresor + 2 Motores Elec.', 'Híbrido Ench.', 609, 1000, 2019, 2021),
(@model_p2, 'Long Range Dual Motor', 'EAD3', 'Doble Motor Eléctrico AWD', 'Eléctrico', 408, 660, 2020, 2024),
(@model_p3, 'Long Range Dual Motor', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 489, 840, 2023, 2025),
(@model_p4, 'Long Range Single Motor', 'Elec', 'Motor Eléctrico Trasero', 'Eléctrico', 272, 343, 2024, 2025),
(@model_p5, 'Dual Motor GT', 'Elec', 'Doble Motor Eléctrico AWD (Concept)', 'Eléctrico', 884, 900, 2024, 2025),
(@model_p6, 'LA Concept Edition', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 884, 900, 2026, 2027);


-- ------------------------------------------
-- 3. GEELY (12 Modelos - El Gigante Chino)
-- ------------------------------------------
SET @geely_id = (SELECT id FROM car_brands WHERE name = 'Geely');

SET @model_coolray = (SELECT id FROM car_models WHERE name = 'Coolray' AND brand_id = @geely_id);
SET @model_azkarra = (SELECT id FROM car_models WHERE name = 'Azkarra' AND brand_id = @geely_id);
SET @model_tugella = (SELECT id FROM car_models WHERE name = 'Tugella' AND brand_id = @geely_id);
SET @model_monjaro = (SELECT id FROM car_models WHERE name = 'Monjaro' AND brand_id = @geely_id);
SET @model_emgrand = (SELECT id FROM car_models WHERE name = 'Emgrand' AND brand_id = @geely_id);
SET @model_okavango = (SELECT id FROM car_models WHERE name = 'Okavango' AND brand_id = @geely_id);
SET @model_atlas = (SELECT id FROM car_models WHERE name = 'Atlas' AND brand_id = @geely_id);
SET @model_boyue = (SELECT id FROM car_models WHERE name = 'Boyue' AND brand_id = @geely_id);
SET @model_geometry = (SELECT id FROM car_models WHERE name = 'Geometry C' AND brand_id = @geely_id);
SET @model_ck = (SELECT id FROM car_models WHERE name = 'CK' AND brand_id = @geely_id);
SET @model_mk = (SELECT id FROM car_models WHERE name = 'MK' AND brand_id = @geely_id);
SET @model_panda = (SELECT id FROM car_models WHERE name = 'Panda' AND brand_id = @geely_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_coolray, '1.5TD', 'JLH-3G15TD', '1.5 Turbo 3 cil. (Base Volvo)', 'Gasolina', 177, 255, 2018, 2024),
(@model_azkarra, '1.5TD 48V EMS', 'JLH-3G15TD', '1.5 Turbo Mild-Hybrid', 'Híbrido', 190, 300, 2019, 2024),
(@model_tugella, '2.0TD', 'JLH-4G20TD', '2.0 Turbo (Volvo Drive-E T5)', 'Gasolina', 238, 350, 2019, 2024),
(@model_monjaro, '2.0TD', 'JLH-4G20TD', '2.0 Turbo (Volvo Drive-E T5)', 'Gasolina', 238, 350, 2021, 2024),
(@model_emgrand, '1.5 MPI', 'JLC-4G15', '1.5 4 cil. Atmosférico', 'Gasolina', 114, 147, 2021, 2024),
(@model_okavango, '1.5TD 48V', 'JLH-3G15TD', '1.5 Turbo Mild-Hybrid', 'Híbrido', 190, 300, 2020, 2024),
(@model_atlas, '1.8TD', 'JLE-4G18TDC', '1.8 Turbo', 'Gasolina', 184, 285, 2016, 2024),
(@model_boyue, '1.5TD', 'JLH-3G15TD', '1.5 Turbo (Base Volvo)', 'Gasolina', 177, 255, 2016, 2024),
(@model_geometry, 'Long Range 70 kWh', 'Elec', 'Motor Eléctrico', 'Eléctrico', 204, 310, 2020, 2024),
(@model_ck, '1.5 (Clon Toyota 5A-FE)', 'MR479QA', '1.5 16V', 'Gasolina', 94, 128, 2005, 2016),
(@model_mk, '1.5 (Clon Toyota)', 'MR479QA', '1.5 16V', 'Gasolina', 94, 128, 2006, 2015),
(@model_panda, '1.3', 'MR479Q', '1.3 4 cil.', 'Gasolina', 86, 110, 2008, 2016);

-- ==========================================
-- VERSIONES LOTUS, SMART, ZEEKR Y LYNK & CO
-- Motores Toyota, alianzas con Renault y tecnología Volvo
-- ==========================================

-- ------------------------------------------
-- 4. LOTUS (10 Modelos - Deportivos puros y la nueva era EV)
-- ------------------------------------------
SET @lotus_id = (SELECT id FROM car_brands WHERE name = 'Lotus');

SET @model_emira = (SELECT id FROM car_models WHERE name = 'Emira' AND brand_id = @lotus_id);
SET @model_eletre = (SELECT id FROM car_models WHERE name = 'Eletre' AND brand_id = @lotus_id);
SET @model_emeya = (SELECT id FROM car_models WHERE name = 'Emeya' AND brand_id = @lotus_id);
SET @model_evija = (SELECT id FROM car_models WHERE name = 'Evija' AND brand_id = @lotus_id);
SET @model_elise = (SELECT id FROM car_models WHERE name = 'Elise' AND brand_id = @lotus_id);
SET @model_exige = (SELECT id FROM car_models WHERE name = 'Exige' AND brand_id = @lotus_id);
SET @model_evora = (SELECT id FROM car_models WHERE name = 'Evora' AND brand_id = @lotus_id);
SET @model_esprit = (SELECT id FROM car_models WHERE name = 'Esprit' AND brand_id = @lotus_id);
SET @model_europa = (SELECT id FROM car_models WHERE name = 'Europa' AND brand_id = @lotus_id);
SET @model_elan = (SELECT id FROM car_models WHERE name = 'Elan' AND brand_id = @lotus_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_emira, '2.0 AMG', 'M139', '2.0 Turbo (Mercedes-AMG)', 'Gasolina', 360, 430, 2022, 2024),
(@model_emira, '3.5 V6 Supercharged', '2GR-FE', '3.5 V6 Compresor (Toyota)', 'Gasolina', 400, 420, 2022, 2024),
(@model_eletre, 'R Dual Motor', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 905, 985, 2023, 2024),
(@model_emeya, 'Hyper-GT AWD', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 905, 985, 2024, 2025),
(@model_evija, 'Quad Motor', 'Elec', 'Cuatro Motores Eléctricos', 'Eléctrico', 2000, 1700, 2020, 2024),
(@model_elise, '1.8 S1 (Ojo Junta Culata)', '18K4F', '1.8 16V (Rover K-Series)', 'Gasolina', 118, 165, 1996, 2001),
(@model_elise, '1.8 111R (S2)', '2ZZ-GE', '1.8 16V VVT-i (Toyota Celica)', 'Gasolina', 192, 181, 2004, 2011),
(@model_exige, 'S 3.5 V6', '2GR-FE', '3.5 V6 Compresor (Toyota)', 'Gasolina', 350, 400, 2012, 2021),
(@model_evora, '400 3.5 V6', '2GR-FE', '3.5 V6 Compresor (Toyota)', 'Gasolina', 400, 410, 2015, 2021),
(@model_esprit, 'V8 Twin Turbo', 'Type 918', '3.5 V8 Biturbo', 'Gasolina', 354, 400, 1996, 2004),
(@model_europa, 'S 2.0 Turbo', 'Z20LER', '2.0 Turbo (Opel Astra/Zafira)', 'Gasolina', 200, 272, 2006, 2010),
(@model_elan, '1.6 Turbo (M100)', '4XE1-T', '1.6 Turbo (Isuzu)', 'Gasolina', 165, 200, 1989, 1995);


-- ------------------------------------------
-- 5. SMART (7 Modelos - Mercedes, Mitsubishi, Renault y Geely)
-- ------------------------------------------
SET @smart_id = (SELECT id FROM car_brands WHERE name = 'Smart');

SET @model_fortwo = (SELECT id FROM car_models WHERE name = 'Fortwo' AND brand_id = @smart_id);
SET @model_forfour = (SELECT id FROM car_models WHERE name = 'Forfour' AND brand_id = @smart_id);
SET @model_roadster = (SELECT id FROM car_models WHERE name = 'Roadster' AND brand_id = @smart_id);
SET @model_crossblade = (SELECT id FROM car_models WHERE name = 'Crossblade' AND brand_id = @smart_id);
SET @model_1 = (SELECT id FROM car_models WHERE name = '#1' AND brand_id = @smart_id);
SET @model_3 = (SELECT id FROM car_models WHERE name = '#3' AND brand_id = @smart_id);
SET @model_5 = (SELECT id FROM car_models WHERE name = '#5' AND brand_id = @smart_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_fortwo, '0.8 CDI (W450/W451)', 'OM660', '0.8 Turbodiesel 3 cil. (Mercedes)', 'Diesel', 41, 100, 1999, 2014),
(@model_fortwo, '1.0 12V (W451)', '3B21', '1.0 3 cil. (Mitsubishi)', 'Gasolina', 71, 92, 2007, 2014),
(@model_fortwo, '0.9 Turbo (C453)', 'H4B', '0.9 Turbo 3 cil. (Renault Twingo)', 'Gasolina', 90, 135, 2014, 2019),
(@model_forfour, '1.5 (W454)', '4G15', '1.5 4 cil. (Mitsubishi Colt)', 'Gasolina', 109, 145, 2004, 2006),
(@model_forfour, 'EQ (Eléctrico)', '5AL', 'Motor Eléctrico', 'Eléctrico', 82, 160, 2017, 2021),
(@model_roadster, '0.7 Brabus', 'M160', '0.7 Turbo 3 cil.', 'Gasolina', 101, 130, 2003, 2006),
(@model_crossblade, '0.6 Brabus', 'M160', '0.6 Turbo 3 cil.', 'Gasolina', 71, 108, 2002, 2003),
(@model_1, 'Brabus AWD', 'Elec', 'Doble Motor Eléctrico (Plataforma Geely)', 'Eléctrico', 428, 543, 2022, 2024),
(@model_3, 'Pro+ Single Motor', 'Elec', 'Motor Eléctrico Trasero', 'Eléctrico', 272, 343, 2023, 2024),
(@model_5, 'AWD', 'Elec', 'Doble Motor Eléctrico', 'Eléctrico', 400, 550, 2024, 2025);


-- ------------------------------------------
-- 6. ZEEKR (5 Modelos - Plataforma SEA de Geely)
-- ------------------------------------------
SET @zeekr_id = (SELECT id FROM car_brands WHERE name = 'Zeekr');

SET @model_001 = (SELECT id FROM car_models WHERE name = '001' AND brand_id = @zeekr_id);
SET @model_009 = (SELECT id FROM car_models WHERE name = '009' AND brand_id = @zeekr_id);
SET @model_x = (SELECT id FROM car_models WHERE name = 'X' AND brand_id = @zeekr_id);
SET @model_007 = (SELECT id FROM car_models WHERE name = '007' AND brand_id = @zeekr_id);
SET @model_mix = (SELECT id FROM car_models WHERE name = 'MIX' AND brand_id = @zeekr_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_001, 'Long Range RWD', 'Elec', 'Motor Eléctrico Trasero', 'Eléctrico', 272, 343, 2021, 2024),
(@model_001, 'Performance AWD', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 544, 700, 2021, 2024),
(@model_009, 'AWD (MPV)', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 544, 686, 2022, 2024),
(@model_x, 'Privilege AWD', 'Elec', 'Doble Motor Eléctrico (Hermano Volvo EX30)', 'Eléctrico', 428, 543, 2023, 2024),
(@model_007, 'Standard RWD', 'Elec', 'Motor Eléctrico Trasero', 'Eléctrico', 415, 440, 2023, 2024),
(@model_mix, 'Minivan EV', 'Elec', 'Motor Eléctrico', 'Eléctrico', 415, 440, 2024, 2025);


-- ------------------------------------------
-- 7. LYNK & CO (9 Modelos - Sangre de Volvo bajo piel china)
-- ------------------------------------------
SET @lynk_id = (SELECT id FROM car_brands WHERE name = 'Lynk & Co');

SET @model_l01 = (SELECT id FROM car_models WHERE name = '01' AND brand_id = @lynk_id);
SET @model_l02 = (SELECT id FROM car_models WHERE name = '02' AND brand_id = @lynk_id);
SET @model_l03 = (SELECT id FROM car_models WHERE name = '03' AND brand_id = @lynk_id);
SET @model_l04 = (SELECT id FROM car_models WHERE name = '04' AND brand_id = @lynk_id);
SET @model_l05 = (SELECT id FROM car_models WHERE name = '05' AND brand_id = @lynk_id);
SET @model_l06 = (SELECT id FROM car_models WHERE name = '06' AND brand_id = @lynk_id);
SET @model_l07 = (SELECT id FROM car_models WHERE name = '07' AND brand_id = @lynk_id);
SET @model_l08 = (SELECT id FROM car_models WHERE name = '08' AND brand_id = @lynk_id);
SET @model_l09 = (SELECT id FROM car_models WHERE name = '09' AND brand_id = @lynk_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_l01, '1.5T PHEV', 'JLH-3G15TD', '1.5 Turbo 3 cil. PHEV (Volvo B3154T)', 'Híbrido Ench.', 261, 425, 2017, 2024),
(@model_l02, '2.0T Hatchback', 'JLH-4G20TD', '2.0 Turbo (Volvo Drive-E T4)', 'Gasolina', 190, 300, 2018, 2024),
(@model_l03, '2.0T Cyan Racing', 'JLH-4G20TDC', '2.0 Turbo Alta Potencia', 'Gasolina', 254, 350, 2018, 2024),
(@model_l04, 'e-Bike (Scooter Eléctrico)', 'Elec', 'Motor Eléctrico Hub', 'Eléctrico', 1, 3, 2021, 2024),
(@model_l05, '2.0T AWD', 'JLH-4G20TDC', '2.0 Turbo (Volvo Drive-E T5)', 'Gasolina', 254, 350, 2019, 2024),
(@model_l06, '1.5T MHEV', 'JLH-3G15TD', '1.5 Turbo 3 cil. Mild Hybrid', 'Híbrido', 190, 300, 2020, 2024),
(@model_l07, 'EM-P PHEV', 'BHE15-BFZ', '1.5 Turbo PHEV Dedicado', 'Híbrido Ench.', 380, 615, 2024, 2025),
(@model_l08, 'EM-P PHEV AWD', 'BHE15-BFZ', '1.5 Turbo PHEV Doble Motor', 'Híbrido Ench.', 593, 905, 2023, 2024),
(@model_l09, '2.0T PHEV (Base Volvo XC90)', 'JLH-4G20TDC', '2.0 Turbo + Eléctrico (Plataforma SPA)', 'Híbrido Ench.', 431, 659, 2021, 2024);

-- ==========================================
-- VERSIONES MAZDA Y SUBARU
-- El templo del motor Rotativo y el bloque Bóxer
-- ==========================================

-- ------------------------------------------
-- 1. MAZDA (15 Modelos)
-- ------------------------------------------
SET @mazda_id = (SELECT id FROM car_brands WHERE name = 'Mazda');

-- Compactos, Urbanos y Berlinas (Mazda2, Mazda3, Mazda6, Demio)
SET @model_m2 = (SELECT id FROM car_models WHERE name = 'Mazda2' AND brand_id = @mazda_id);
SET @model_m3 = (SELECT id FROM car_models WHERE name = 'Mazda3' AND brand_id = @mazda_id);
SET @model_m6 = (SELECT id FROM car_models WHERE name = 'Mazda6' AND brand_id = @mazda_id);
SET @model_demio = (SELECT id FROM car_models WHERE name = 'Demio' AND brand_id = @mazda_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_m2, '1.5 e-Skyactiv G', 'P5-VPS', '1.5 4 cil. Mild Hybrid', 'Híbrido', 90, 151, 2014, 2024),
(@model_m3, '1.6 CRTD (Mk1/Mk2)', 'Y601', '1.6 Turbodiesel (PSA 1.6 HDi)', 'Diesel', 109, 240, 2003, 2013),
(@model_m3, '2.0 e-Skyactiv X', 'HF-VPH', '2.0 4 cil. SPCCI Mild Hybrid', 'Híbrido', 186, 240, 2019, 2024),
(@model_m6, '2.0 CRTD (Mk1)', 'RF5C', '2.0 Turbodiesel MZR-CD', 'Diesel', 136, 310, 2002, 2007),
(@model_m6, '2.2 Skyactiv-D', 'SH-VPTS', '2.2 Turbodiesel Biturbo', 'Diesel', 175, 420, 2012, 2024),
(@model_demio, '1.3 16V', 'B3-ME', '1.3 4 cil.', 'Gasolina', 63, 108, 1996, 2002);

-- SUV y Crossovers (CX-3, CX-30, CX-5, CX-60, CX-80, Tribute)
SET @model_cx3 = (SELECT id FROM car_models WHERE name = 'CX-3' AND brand_id = @mazda_id);
SET @model_cx30 = (SELECT id FROM car_models WHERE name = 'CX-30' AND brand_id = @mazda_id);
SET @model_cx5 = (SELECT id FROM car_models WHERE name = 'CX-5' AND brand_id = @mazda_id);
SET @model_cx60 = (SELECT id FROM car_models WHERE name = 'CX-60' AND brand_id = @mazda_id);
SET @model_cx80 = (SELECT id FROM car_models WHERE name = 'CX-80' AND brand_id = @mazda_id);
SET @model_tribute = (SELECT id FROM car_models WHERE name = 'Tribute' AND brand_id = @mazda_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_cx3, '1.5 Skyactiv-D', 'S5-DPTS', '1.5 Turbodiesel', 'Diesel', 105, 270, 2015, 2021),
(@model_cx30, '2.0 e-Skyactiv G', 'PE-VPS', '2.0 4 cil. Mild Hybrid', 'Híbrido', 122, 213, 2019, 2024),
(@model_cx5, '2.2 Skyactiv-D', 'SH-VPTS', '2.2 Turbodiesel Biturbo', 'Diesel', 150, 380, 2012, 2024),
(@model_cx60, '3.3 e-Skyactiv D', 'T3-VPTS', '3.3 L6 Turbodiesel MHEV', 'Diesel', 254, 550, 2022, 2024),
(@model_cx80, '2.5 e-Skyactiv PHEV', 'PY-VPH', '2.5 4 cil. PHEV', 'Híbrido Ench.', 327, 500, 2024, 2025),
(@model_tribute, '2.0 16V', 'YF', '2.0 4 cil. (Base Ford Maverick)', 'Gasolina', 124, 175, 2000, 2006);

-- Deportivos y Exóticos (MX-5, MX-30, RX-7, RX-8, Premacy)
SET @model_mx5 = (SELECT id FROM car_models WHERE name = 'MX-5' AND brand_id = @mazda_id);
SET @model_mx30 = (SELECT id FROM car_models WHERE name = 'MX-30' AND brand_id = @mazda_id);
SET @model_rx7 = (SELECT id FROM car_models WHERE name = 'RX-7' AND brand_id = @mazda_id);
SET @model_rx8 = (SELECT id FROM car_models WHERE name = 'RX-8' AND brand_id = @mazda_id);
SET @model_premacy = (SELECT id FROM car_models WHERE name = 'Premacy' AND brand_id = @mazda_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_mx5, '1.6 16V (NA)', 'B6-ZE', '1.6 4 cil. Atmosférico', 'Gasolina', 115, 135, 1989, 1993),
(@model_mx5, '2.0 Skyactiv-G (ND)', 'PE-VPS', '2.0 4 cil. Atmosférico', 'Gasolina', 184, 205, 2015, 2024),
(@model_mx30, 'e-Skyactiv EV', 'MH', 'Motor Eléctrico FWD', 'Eléctrico', 145, 271, 2020, 2024),
(@model_rx7, '1.3 Twin-Turbo (FD3S)', '13B-REW', '1.3 Motor Rotativo (Wankel) Biturbo', 'Gasolina', 280, 314, 1992, 2002),
(@model_rx8, '1.3 Renesis High Power', '13B-MSP', '1.3 Motor Rotativo (Wankel) Atmosférico', 'Gasolina', 231, 211, 2003, 2012),
(@model_premacy, '2.0 DiTD', 'RF4F', '2.0 Turbodiesel', 'Diesel', 101, 230, 1999, 2005);


-- ------------------------------------------
-- 2. SUBARU (11 Modelos)
-- ------------------------------------------
SET @subaru_id = (SELECT id FROM car_brands WHERE name = 'Subaru');

-- La familia Impreza y Deportivos (Impreza, WRX STI, BRZ)
SET @model_impreza = (SELECT id FROM car_models WHERE name = 'Impreza' AND brand_id = @subaru_id);
SET @model_wrx = (SELECT id FROM car_models WHERE name = 'WRX STI' AND brand_id = @subaru_id);
SET @model_brz = (SELECT id FROM car_models WHERE name = 'BRZ' AND brand_id = @subaru_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_impreza, '2.0 GT Turbo (GC8)', 'EJ20T', '2.0 Bóxer Turbo', 'Gasolina', 211, 290, 1992, 2000),
(@model_impreza, '2.0D Bóxer Diesel', 'EE20', '2.0 Bóxer Turbodiesel', 'Diesel', 150, 350, 2008, 2012),
(@model_wrx, '2.5 Turbo (VA)', 'EJ257', '2.5 Bóxer Turbo', 'Gasolina', 300, 407, 2014, 2021),
(@model_brz, '2.0 Atmosférico (Base Toyota GT86)', 'FA20D', '2.0 Bóxer Atmosférico D-4S', 'Gasolina', 200, 205, 2012, 2021);

-- SUV, Familiares y Crossovers (Forester, Outback, Crosstrek, XV, Legacy, Levorg)
SET @model_forester = (SELECT id FROM car_models WHERE name = 'Forester' AND brand_id = @subaru_id);
SET @model_outback = (SELECT id FROM car_models WHERE name = 'Outback' AND brand_id = @subaru_id);
SET @model_crosstrek = (SELECT id FROM car_models WHERE name = 'Crosstrek' AND brand_id = @subaru_id);
SET @model_xv = (SELECT id FROM car_models WHERE name = 'XV' AND brand_id = @subaru_id);
SET @model_legacy = (SELECT id FROM car_models WHERE name = 'Legacy' AND brand_id = @subaru_id);
SET @model_levorg = (SELECT id FROM car_models WHERE name = 'Levorg' AND brand_id = @subaru_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_forester, '2.0 XT Turbo', 'EJ205', '2.0 Bóxer Turbo', 'Gasolina', 177, 245, 2002, 2008),
(@model_forester, '2.0 e-Boxer MHEV', 'FB20D', '2.0 Bóxer Mild-Hybrid', 'Híbrido', 150, 194, 2019, 2024),
(@model_outback, '2.0D Bóxer Diesel', 'EE20', '2.0 Bóxer Turbodiesel (Ojo Cigüeñal)', 'Diesel', 150, 350, 2008, 2014),
(@model_outback, '2.5i', 'FB25', '2.5 Bóxer Atmosférico', 'Gasolina', 175, 235, 2015, 2020),
(@model_xv, '2.0D Bóxer Diesel', 'EE20', '2.0 Bóxer Turbodiesel', 'Diesel', 147, 350, 2012, 2017),
(@model_crosstrek, '2.0 e-Boxer', 'FB20D', '2.0 Bóxer Mild-Hybrid', 'Híbrido', 136, 182, 2023, 2025),
(@model_legacy, '3.0 R H6', 'EZ30D', '3.0 6 cil. Bóxer (H6)', 'Gasolina', 245, 297, 2003, 2009),
(@model_levorg, '1.6 DIT', 'FB16', '1.6 Bóxer Turbo Inyección Directa', 'Gasolina', 170, 250, 2014, 2020);

-- Eléctricos y Pequeños (Solterra, Justy)
SET @model_solterra = (SELECT id FROM car_models WHERE name = 'Solterra' AND brand_id = @subaru_id);
SET @model_justy = (SELECT id FROM car_models WHERE name = 'Justy' AND brand_id = @subaru_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_solterra, 'AWD', '1XM', 'Doble Motor Eléctrico (Base Toyota bZ4X)', 'Eléctrico', 218, 336, 2022, 2024),
(@model_justy, '1.3 AWD (Mk3)', 'M13A', '1.3 4 cil. (Base Suzuki Ignis)', 'Gasolina', 94, 118, 2003, 2007);

-- ==========================================
-- VERSIONES SUZUKI, SSANGYONG Y KGM
-- Alianzas europeas (Fiat, Renault, PSA) y corazones Mercedes-Benz
-- ==========================================

-- ------------------------------------------
-- 3. SUZUKI (12 Modelos)
-- ------------------------------------------
SET @suzuki_id = (SELECT id FROM car_brands WHERE name = 'Suzuki');

-- Los utilitarios y urbanos (Swift, Ignis, Baleno, Alto, Celerio, Splash)
SET @model_swift = (SELECT id FROM car_models WHERE name = 'Swift' AND brand_id = @suzuki_id);
SET @model_ignis = (SELECT id FROM car_models WHERE name = 'Ignis' AND brand_id = @suzuki_id);
SET @model_baleno = (SELECT id FROM car_models WHERE name = 'Baleno' AND brand_id = @suzuki_id);
SET @model_alto = (SELECT id FROM car_models WHERE name = 'Alto' AND brand_id = @suzuki_id);
SET @model_celerio = (SELECT id FROM car_models WHERE name = 'Celerio' AND brand_id = @suzuki_id);
SET @model_splash = (SELECT id FROM car_models WHERE name = 'Splash' AND brand_id = @suzuki_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_swift, '1.3 DDiS', 'Z13DT', '1.3 Turbodiesel (Fiat Multijet)', 'Diesel', 75, 190, 2005, 2010),
(@model_swift, '1.2 Sport MHEV', 'K12D', '1.2 4 cil. Mild Hybrid', 'Híbrido', 83, 107, 2020, 2024),
(@model_swift, '1.6 Sport (Mk3)', 'M16A', '1.6 16V Atmosférico', 'Gasolina', 136, 160, 2012, 2017),
(@model_ignis, '1.2 Mild Hybrid', 'K12D', '1.2 Dualjet MHEV', 'Híbrido', 83, 107, 2016, 2024),
(@model_baleno, '1.0 Boosterjet', 'K10C', '1.0 Turbo 3 cil.', 'Gasolina', 111, 170, 2016, 2020),
(@model_alto, '1.0 (Mk7)', 'K10B', '1.0 3 cil.', 'Gasolina', 68, 90, 2009, 2014),
(@model_celerio, '1.0 Dualjet', 'K10C', '1.0 3 cil.', 'Gasolina', 68, 90, 2014, 2020),
(@model_splash, '1.3 DDiS', 'Z13DT', '1.3 Turbodiesel (Fiat)', 'Diesel', 75, 190, 2008, 2014);

-- Los 4x4 y Todoterrenos míticos (Vitara, Grand Vitara, Jimny, Samurai, S-Cross, Kizashi)
SET @model_vitara = (SELECT id FROM car_models WHERE name = 'Vitara' AND brand_id = @suzuki_id);
SET @model_gvitara = (SELECT id FROM car_models WHERE name = 'Grand Vitara' AND brand_id = @suzuki_id);
SET @model_jimny = (SELECT id FROM car_models WHERE name = 'Jimny' AND brand_id = @suzuki_id);
SET @model_samurai = (SELECT id FROM car_models WHERE name = 'Samurai' AND brand_id = @suzuki_id);
SET @model_scross = (SELECT id FROM car_models WHERE name = 'S-Cross' AND brand_id = @suzuki_id);
SET @model_kizashi = (SELECT id FROM car_models WHERE name = 'Kizashi' AND brand_id = @suzuki_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_vitara, '2.0 HDI (Clásico)', 'RHY', '2.0 HDI (Peugeot/PSA)', 'Diesel', 90, 205, 2001, 2005),
(@model_vitara, '1.4 Boosterjet MHEV', 'K14D', '1.4 Turbo Mild-Hybrid', 'Híbrido', 129, 235, 2020, 2024),
(@model_gvitara, '1.9 DDiS', 'F9Q', '1.9 Turbodiesel (Renault dCi)', 'Diesel', 129, 300, 2005, 2015),
(@model_gvitara, '2.0 16V', 'J20A', '2.0 16V Atmosférico', 'Gasolina', 140, 183, 2005, 2015),
(@model_jimny, '1.3 16V (Gen 3)', 'M13A', '1.3 16V VVT', 'Gasolina', 85, 110, 2001, 2018),
(@model_jimny, '1.5 Pro (Gen 4)', 'K15B', '1.5 16V Atmosférico', 'Gasolina', 102, 130, 2018, 2024),
(@model_samurai, '1.3i (Clásico)', 'G13BA', '1.3 8V Inyección Monopunto', 'Gasolina', 69, 103, 1990, 2003),
(@model_samurai, '1.9 TD', 'XUD9', '1.9 Diesel (Peugeot/PSA)', 'Diesel', 63, 135, 1998, 2003),
(@model_scross, '1.4 Boosterjet', 'K14C', '1.4 Turbo', 'Gasolina', 140, 220, 2016, 2021),
(@model_kizashi, '2.4 Sport', 'J24B', '2.4 16V', 'Gasolina', 178, 230, 2010, 2015);


-- ------------------------------------------
-- 4. SSANGYONG (7 Modelos - Los tanques con herencia Mercedes)
-- ------------------------------------------
SET @ssangyong_id = (SELECT id FROM car_brands WHERE name = 'SsangYong');

SET @model_rexton = (SELECT id FROM car_models WHERE name = 'Rexton' AND brand_id = @ssangyong_id);
SET @model_korando = (SELECT id FROM car_models WHERE name = 'Korando' AND brand_id = @ssangyong_id);
SET @model_tivoli = (SELECT id FROM car_models WHERE name = 'Tivoli' AND brand_id = @ssangyong_id);
SET @model_musso = (SELECT id FROM car_models WHERE name = 'Musso' AND brand_id = @ssangyong_id);
SET @model_rodius = (SELECT id FROM car_models WHERE name = 'Rodius' AND brand_id = @ssangyong_id);
SET @model_kyron = (SELECT id FROM car_models WHERE name = 'Kyron' AND brand_id = @ssangyong_id);
SET @model_actyon = (SELECT id FROM car_models WHERE name = 'Actyon' AND brand_id = @ssangyong_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_rexton, '2.7 Xdi (Rexton II)', 'D27DT', '2.7 L5 Turbodiesel (Base Mercedes)', 'Diesel', 165, 340, 2006, 2012),
(@model_rexton, 'D22 DTR', 'D22DTR', '2.2 Turbodiesel e-XDi', 'Diesel', 181, 400, 2015, 2020),
(@model_korando, '2.9 TD (KJ)', 'OM662', '2.9 L5 Turbodiesel (Mercedes OM602)', 'Diesel', 120, 256, 1996, 2005),
(@model_korando, 'D20T (C200)', 'D20DTF', '2.0 Turbodiesel e-XDi', 'Diesel', 149, 360, 2010, 2019),
(@model_tivoli, '1.6 D16T', 'D16DTF', '1.6 Turbodiesel e-XDi', 'Diesel', 115, 300, 2015, 2021),
(@model_musso, '2.9 TD', 'OM662', '2.9 L5 Turbodiesel (Mercedes OM602)', 'Diesel', 120, 256, 1998, 2005),
(@model_rodius, '2.7 Xdi', 'D27DT', '2.7 L5 Turbodiesel (Mismo bloque Mercedes)', 'Diesel', 165, 340, 2005, 2013),
(@model_rodius, '2.0 e-XDi AWD', 'D20DTR', '2.0 Turbodiesel e-XDi', 'Diesel', 155, 360, 2013, 2019),
(@model_kyron, '2.0 Xdi', 'D20DT', '2.0 Turbodiesel e-XDi', 'Diesel', 141, 310, 2005, 2012),
(@model_actyon, '2.0 Xdi 4x4', 'D20DT', '2.0 Turbodiesel e-XDi', 'Diesel', 141, 310, 2006, 2012);


-- ------------------------------------------
-- 5. KGM (5 Modelos - La nueva era de SsangYong)
-- ------------------------------------------
SET @kgm_id = (SELECT id FROM car_brands WHERE name = 'KGM');

SET @model_kgm_torres = (SELECT id FROM car_models WHERE name = 'Torres' AND brand_id = @kgm_id);
SET @model_kgm_tivoli = (SELECT id FROM car_models WHERE name = 'Tivoli' AND brand_id = @kgm_id);
SET @model_kgm_korando = (SELECT id FROM car_models WHERE name = 'Korando' AND brand_id = @kgm_id);
SET @model_kgm_rexton = (SELECT id FROM car_models WHERE name = 'Rexton' AND brand_id = @kgm_id);
SET @model_kgm_musso = (SELECT id FROM car_models WHERE name = 'Musso' AND brand_id = @kgm_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_kgm_torres, '1.5 T-GDI', 'G15DTF', '1.5 Turbo e-XGDi', 'Gasolina', 163, 280, 2023, 2024),
(@model_kgm_tivoli, '1.5 T-GDI', 'G15DTF', '1.5 Turbo e-XGDi', 'Gasolina', 163, 280, 2023, 2024),
(@model_kgm_korando, 'e-Motion (EV)', 'Elec', 'Motor Eléctrico FWD', 'Eléctrico', 190, 360, 2022, 2024),
(@model_kgm_rexton, '2.2 D22DTR', 'D22DTR', '2.2 Turbodiesel e-XDi', 'Diesel', 202, 441, 2023, 2024),
(@model_kgm_musso, '2.2 D22DTR (Pick-up)', 'D22DTR', '2.2 Turbodiesel e-XDi', 'Diesel', 202, 441, 2023, 2024);

-- ==========================================
-- VERSIONES ISUZU, MAHINDRA, MARUTI, BYD Y CHERY
-- Especialistas en Diesel, 4x4 robustos y Vanguardia Eléctrica
-- ==========================================

-- ------------------------------------------
-- 6. ISUZU (4 Modelos - Maestros del Diesel)
-- ------------------------------------------
SET @isuzu_id = (SELECT id FROM car_brands WHERE name = 'Isuzu');

-- D-Max (La pick-up indestructible)
SET @model_id = (SELECT id FROM car_models WHERE name = 'D-Max' AND brand_id = @isuzu_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '1.9 DDi Blue Power', 'RZ4E-TC', '1.9 Turbo Diesel', 'Diesel', 164, 360, 2017, 2024),
(@model_id, '2.5 TD Twin Turbo', '4JK1-TCX', '2.5 Turbo Diesel Biturbo', 'Diesel', 163, 400, 2012, 2017),
(@model_id, '3.0 DDi', '4JJ3-TCX', '3.0 Turbo Diesel', 'Diesel', 190, 450, 2020, 2024);

-- Trooper, Rodeo, MU-X
SET @model_trooper = (SELECT id FROM car_models WHERE name = 'Trooper' AND brand_id = @isuzu_id);
SET @model_rodeo = (SELECT id FROM car_models WHERE name = 'Rodeo' AND brand_id = @isuzu_id);
SET @model_mux = (SELECT id FROM car_models WHERE name = 'MU-X' AND brand_id = @isuzu_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_trooper, '3.0 DTI', '4JX1', '3.0 Turbo Diesel', 'Diesel', 159, 333, 1998, 2004),
(@model_rodeo, '2.5 TD', '4JK1-TC', '2.5 Turbo Diesel', 'Diesel', 136, 280, 2003, 2012),
(@model_mux, '3.0 DDi 4WD', '4JJ3-TCX', '3.0 Turbo Diesel', 'Diesel', 190, 450, 2013, 2024);


-- ------------------------------------------
-- 7. MAHINDRA (7 Modelos - Los tanques de la India)
-- ------------------------------------------
SET @mahindra_id = (SELECT id FROM car_brands WHERE name = 'Mahindra');

-- Thar, Scorpio, XUV700, XUV500, Bolero, KUV100, Goa
SET @model_thar = (SELECT id FROM car_models WHERE name = 'Thar' AND brand_id = @mahindra_id);
SET @model_scorpio = (SELECT id FROM car_models WHERE name = 'Scorpio' AND brand_id = @mahindra_id);
SET @model_xuv700 = (SELECT id FROM car_models WHERE name = 'XUV700' AND brand_id = @mahindra_id);
SET @model_bolero = (SELECT id FROM car_models WHERE name = 'Bolero' AND brand_id = @mahindra_id);
SET @model_goa = (SELECT id FROM car_models WHERE name = 'Goa' AND brand_id = @mahindra_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_thar, '2.2 mHawk', 'mHawk 130', '2.2 Turbo Diesel', 'Diesel', 130, 300, 2020, 2024),
(@model_scorpio, '2.2 mHawk N', 'mHawk 130', '2.2 Turbo Diesel', 'Diesel', 132, 300, 2022, 2024),
(@model_xuv700, '2.0 mStallion Turbo', 'mStallion', '2.0 Turbo Gasolina', 'Gasolina', 200, 380, 2021, 2024),
(@model_bolero, '1.5 mHawk75', 'D75', '1.5 Turbo Diesel 3 cil.', 'Diesel', 75, 210, 2016, 2024),
(@model_goa, '2.2 CRDe', 'mHawk', '2.2 Turbo Diesel', 'Diesel', 120, 280, 2008, 2018);


-- ------------------------------------------
-- 8. MARUTI (5 Modelos - El corazón de Suzuki en India)
-- ------------------------------------------
SET @maruti_id = (SELECT id FROM car_brands WHERE name = 'Maruti');

-- 800, Zen, Dzire, Ertiga, Brezza
SET @model_800 = (SELECT id FROM car_models WHERE name = '800' AND brand_id = @maruti_id);
SET @model_dzire = (SELECT id FROM car_models WHERE name = 'Dzire' AND brand_id = @maruti_id);
SET @model_ertiga = (SELECT id FROM car_models WHERE name = 'Ertiga' AND brand_id = @maruti_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_800, '0.8 Standard', 'F8B', '0.8 3 cil.', 'Gasolina', 37, 59, 1983, 2014),
(@model_dzire, '1.2 DualJet', 'K12N', '1.2 4 cil. VVT', 'Gasolina', 90, 113, 2017, 2024),
(@model_ertiga, '1.5 K15C Smart Hybrid', 'K15C', '1.5 4 cil. MHEV', 'Híbrido', 103, 137, 2018, 2024);


-- ------------------------------------------
-- 9. BYD (8 Modelos - El líder eléctrico mundial)
-- ------------------------------------------
SET @byd_id = (SELECT id FROM car_brands WHERE name = 'BYD');

-- Atto 3, Dolphin, Seal, Tang, Han, Seagull, Seal U
SET @model_atto3 = (SELECT id FROM car_models WHERE name = 'Atto 3' AND brand_id = @byd_id);
SET @model_dolphin = (SELECT id FROM car_models WHERE name = 'Dolphin' AND brand_id = @byd_id);
SET @model_seal = (SELECT id FROM car_models WHERE name = 'Seal' AND brand_id = @byd_id);
SET @model_tang = (SELECT id FROM car_models WHERE name = 'Tang' AND brand_id = @byd_id);
SET @model_sealu = (SELECT id FROM car_models WHERE name = 'Seal U' AND brand_id = @byd_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_atto3, 'Electric 60 kWh', 'TZ200XSQ', 'Motor Eléctrico FWD', 'Eléctrico', 204, 310, 2022, 2024),
(@model_dolphin, 'Design 60 kWh', 'TZ180XSF', 'Motor Eléctrico FWD', 'Eléctrico', 204, 310, 2023, 2024),
(@model_seal, 'Excellence AWD', 'TZ200XYC', 'Dual Motor Eléctrico AWD', 'Eléctrico', 530, 670, 2023, 2024),
(@model_tang, 'AWD Flagship', 'TZ200XSE', 'Dual Motor Eléctrico AWD', 'Eléctrico', 517, 680, 2022, 2024),
(@model_sealu, 'DM-i Boost', 'BYD472ZQB', '1.5 Híbrido Enchufable', 'Híbrido Ench.', 218, 300, 2024, 2025);

-- ------------------------------------------
-- 10. CHERY (5 Modelos - Los Tiggo dominan)
-- ------------------------------------------
SET @chery_id = (SELECT id FROM car_brands WHERE name = 'Chery');

-- Tiggo 3, Tiggo 7, Tiggo 8, QQ, Arrizo 5
SET @model_t7 = (SELECT id FROM car_models WHERE name = 'Tiggo 7' AND brand_id = @chery_id);
SET @model_t8 = (SELECT id FROM car_models WHERE name = 'Tiggo 8' AND brand_id = @chery_id);
SET @model_qq = (SELECT id FROM car_models WHERE name = 'QQ' AND brand_id = @chery_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_t7, '1.5 T Pro', 'SQRE4T15C', '1.5 Turbo', 'Gasolina', 147, 210, 2020, 2024),
(@model_t8, '1.6 TGDI Pro', 'SQRF4J16', '1.6 Turbo Inyección Directa', 'Gasolina', 186, 290, 2019, 2024),
(@model_qq, '1.0 Smile', 'SQR371F', '1.0 3 cil.', 'Gasolina', 69, 93, 2013, 2022);

-- ==========================================
-- VERSIONES MG, OMODA, DONGFENG, HAVAL Y BAOJUN
-- El desembarco chino y la herencia británica de Rover
-- ==========================================

-- ------------------------------------------
-- 11. MG (10 Modelos - De Rover a SAIC)
-- ------------------------------------------
SET @mg_id = (SELECT id FROM car_brands WHERE name = 'MG');

-- SUV Modernos (ZS, HS)
SET @model_zs = (SELECT id FROM car_models WHERE name = 'ZS' AND brand_id = @mg_id);
SET @model_hs = (SELECT id FROM car_models WHERE name = 'HS' AND brand_id = @mg_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_zs, '1.5 VTI-tech', '15S4C', '1.5 Atmosférico', 'Gasolina', 106, 141, 2017, 2024),
(@model_zs, 'EV 156cv', 'TZ180XS', 'Motor Eléctrico', 'Eléctrico', 156, 280, 2019, 2024),
(@model_hs, '1.5 T-GDI', '15E4E', '1.5 Turbo', 'Gasolina', 162, 250, 2018, 2024),
(@model_hs, 'EHS (PHEV)', '15E4E', '1.5 Turbo Híbrido Enchufable', 'Híbrido Ench.', 258, 370, 2020, 2024);

-- Eléctricos Puros (MG4, MG5, Marvel R, Cyberster)
SET @model_mg4 = (SELECT id FROM car_models WHERE name = 'MG4' AND brand_id = @mg_id);
SET @model_mg5 = (SELECT id FROM car_models WHERE name = 'MG5' AND brand_id = @mg_id);
SET @model_marvel = (SELECT id FROM car_models WHERE name = 'Marvel R' AND brand_id = @mg_id);
SET @model_cyber = (SELECT id FROM car_models WHERE name = 'Cyberster' AND brand_id = @mg_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_mg4, 'Electric 64kWh', 'UA01', 'Motor Eléctrico Trasero', 'Eléctrico', 204, 250, 2022, 2024),
(@model_mg5, 'Electric Long Range', 'TZ204XS', 'Motor Eléctrico FWD', 'Eléctrico', 156, 280, 2020, 2024),
(@model_marvel, 'Performance AWD', 'TZ180', 'Tri-Motor Eléctrico AWD', 'Eléctrico', 288, 665, 2021, 2024),
(@model_cyber, 'GT Dual Motor', 'Elec', 'Doble Motor Eléctrico AWD', 'Eléctrico', 544, 725, 2024, 2025);

-- MG Clásicos (TF, ZR, ZT, MGF - Herencia Rover)
SET @model_tf = (SELECT id FROM car_models WHERE name = 'TF' AND brand_id = @mg_id);
SET @model_zr = (SELECT id FROM car_models WHERE name = 'ZR' AND brand_id = @mg_id);
SET @model_zt = (SELECT id FROM car_models WHERE name = 'ZT' AND brand_id = @mg_id);
SET @model_mgf = (SELECT id FROM car_models WHERE name = 'MGF' AND brand_id = @mg_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_tf, '160 VVC', '18K4K', '1.8 16V VVC (Rover K-Series)', 'Gasolina', 160, 174, 2002, 2005),
(@model_zr, '160', '18K4K', '1.8 16V VVC', 'Gasolina', 160, 174, 2001, 2005),
(@model_zt, '2.0 CDTi', 'M47R', '2.0 Turbodiesel (BMW M47)', 'Diesel', 131, 300, 2002, 2005),
(@model_mgf, '1.8i', '18K4F', '1.8 16V Rover K-Series', 'Gasolina', 120, 165, 1995, 2002);


-- ------------------------------------------
-- 12. OMODA (3 Modelos - Chery Group)
-- ------------------------------------------
SET @omoda_id = (SELECT id FROM car_brands WHERE name = 'Omoda');

SET @model_o5 = (SELECT id FROM car_models WHERE name = '5' AND brand_id = @omoda_id);
SET @model_oe5 = (SELECT id FROM car_models WHERE name = 'E5' AND brand_id = @omoda_id);
SET @model_o7 = (SELECT id FROM car_models WHERE name = '7' AND brand_id = @omoda_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_o5, '1.6 Turbo GDI', 'SQRE4T16C', '1.6 Turbo (Chery)', 'Gasolina', 185, 290, 2023, 2024),
(@model_oe5, 'Electric 204', 'Elec', 'Motor Eléctrico FWD', 'Eléctrico', 204, 340, 2024, 2025),
(@model_o7, 'PHEV', 'SQRE4T15', '1.5 Turbo Híbrido', 'Híbrido Ench.', 245, 500, 2024, 2025);


-- ------------------------------------------
-- 13. DONGFENG (5 Modelos)
-- ------------------------------------------
SET @dongfeng_id = (SELECT id FROM car_brands WHERE name = 'Dongfeng');

SET @model_df580 = (SELECT id FROM car_models WHERE name = '580' AND brand_id = @dongfeng_id);
SET @model_df5 = (SELECT id FROM car_models WHERE name = 'Fengon 5' AND brand_id = @dongfeng_id);
SET @model_ix5 = (SELECT id FROM car_models WHERE name = 'ix5' AND brand_id = @dongfeng_id);
SET @model_huge = (SELECT id FROM car_models WHERE name = 'Huge' AND brand_id = @dongfeng_id);
SET @model_mage = (SELECT id FROM car_models WHERE name = 'Mage' AND brand_id = @dongfeng_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_df580, '1.5 Turbo Luxury', 'SFG15T', '1.5 Turbo', 'Gasolina', 146, 220, 2020, 2024),
(@model_df5, '1.5T', 'SFG15T', '1.5 Turbo', 'Gasolina', 146, 220, 2021, 2024),
(@model_ix5, '1.5T Turbo', 'SFG15T', '1.5 Turbo', 'Gasolina', 150, 220, 2020, 2024),
(@model_huge, '1.5T Hybrid', 'DFMC15TE', '1.5 Turbo Híbrido', 'Híbrido', 245, 540, 2023, 2024),
(@model_mage, '1.5T', 'DFMC15TE', '1.5 Turbo', 'Gasolina', 204, 305, 2023, 2024);


-- ------------------------------------------
-- 14. HAVAL (4 Modelos - Great Wall Motors)
-- ------------------------------------------
SET @haval_id = (SELECT id FROM car_brands WHERE name = 'Haval');

SET @model_h6 = (SELECT id FROM car_models WHERE name = 'H6' AND brand_id = @haval_id);
SET @model_jolion = (SELECT id FROM car_models WHERE name = 'Jolion' AND brand_id = @haval_id);
SET @model_bd = (SELECT id FROM car_models WHERE name = 'Big Dog' AND brand_id = @haval_id);
SET @model_h9 = (SELECT id FROM car_models WHERE name = 'H9' AND brand_id = @haval_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_h6, '2.0 GDIT AWD', 'GW4N20', '2.0 Turbo', 'Gasolina', 204, 320, 2021, 2024),
(@model_jolion, '1.5T HEV', 'GW4G15H', '1.5 Híbrido', 'Híbrido', 190, 375, 2021, 2024),
(@model_bd, '2.0T 4WD', 'GW4N20', '2.0 Turbo', 'Gasolina', 211, 325, 2021, 2024),
(@model_h9, '2.0 Turbo 4x4', 'GW4C20', '2.0 Turbo', 'Gasolina', 218, 350, 2015, 2024);


-- ------------------------------------------
-- 15. BAOJUN (4 Modelos - SAIC-GM-Wuling)
-- ------------------------------------------
SET @baojun_id = (SELECT id FROM car_brands WHERE name = 'Baojun');

SET @model_b510 = (SELECT id FROM car_models WHERE name = '510' AND brand_id = @baojun_id);
SET @model_b730 = (SELECT id FROM car_models WHERE name = '730' AND brand_id = @baojun_id);
SET @model_yep = (SELECT id FROM car_models WHERE name = 'Yep' AND brand_id = @baojun_id);
SET @model_kiwi = (SELECT id FROM car_models WHERE name = 'KiWi EV' AND brand_id = @baojun_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_b510, '1.5L', 'LAR', '1.5 Atmosférico', 'Gasolina', 105, 135, 2017, 2022),
(@model_b730, '1.5 Turbo', 'LJO', '1.5 Turbo', 'Gasolina', 150, 230, 2014, 2022),
(@model_yep, 'Electric 68', 'Elec', 'Motor Eléctrico Trasero', 'Eléctrico', 68, 140, 2023, 2024),
(@model_kiwi, 'Electric 54', 'Elec', 'Motor Eléctrico', 'Eléctrico', 54, 150, 2021, 2024);

-- ==========================================
-- VERSIONES TESLA, RIVIAN, LUCID Y FISKER
-- La vanguardia eléctrica y el alto rendimiento
-- ==========================================

-- ------------------------------------------
-- 1. TESLA (6 Modelos)
-- ------------------------------------------
SET @tesla_id = (SELECT id FROM car_brands WHERE name = 'Tesla');

-- Model S
SET @model_id = (SELECT id FROM car_models WHERE name = 'Model S' AND brand_id = @tesla_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '75D', 'L1S', 'Dual Motor AWD', 'Eléctrico', 332, 525, 2016, 2019),
(@model_id, '100D Long Range', 'L2S', 'Dual Motor AWD', 'Eléctrico', 423, 660, 2017, 2021),
(@model_id, 'Plaid', 'L3S', 'Tri-Motor AWD', 'Eléctrico', 1020, 1420, 2021, 2024);

-- Model 3
SET @model_id = (SELECT id FROM car_models WHERE name = 'Model 3' AND brand_id = @tesla_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Standard Range Plus', '3D1', 'Motor Trasero RWD', 'Eléctrico', 283, 450, 2019, 2023),
(@model_id, 'Long Range AWD', '3D3', 'Dual Motor AWD', 'Eléctrico', 498, 660, 2019, 2024),
(@model_id, 'Performance', '3D2', 'Dual Motor AWD High Performance', 'Eléctrico', 513, 660, 2019, 2024);

-- Model X
SET @model_id = (SELECT id FROM car_models WHERE name = 'Model X' AND brand_id = @tesla_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, '90D', 'L1S', 'Dual Motor AWD', 'Eléctrico', 422, 660, 2015, 2017),
(@model_id, 'Plaid', 'L3S', 'Tri-Motor AWD', 'Eléctrico', 1020, 1420, 2021, 2024);

-- Model Y
SET @model_id = (SELECT id FROM car_models WHERE name = 'Model Y' AND brand_id = @tesla_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_id, 'Long Range AWD', '3D3', 'Dual Motor AWD', 'Eléctrico', 514, 493, 2020, 2024),
(@model_id, 'Performance', '3D2', 'Dual Motor AWD', 'Eléctrico', 534, 660, 2020, 2024);

-- Cybertruck y Roadster
SET @model_cyber = (SELECT id FROM car_models WHERE name = 'Cybertruck' AND brand_id = @tesla_id);
SET @model_roadster = (SELECT id FROM car_models WHERE name = 'Roadster' AND brand_id = @tesla_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_cyber, 'Cyberbeast', 'Tri-Motor', 'Tri-Motor AWD', 'Eléctrico', 845, 930, 2023, 2025),
(@model_cyber, 'AWD', 'Dual-Motor', 'Dual Motor AWD', 'Eléctrico', 600, 671, 2023, 2025),
(@model_roadster, '1.5 (Original)', 'AC-Induction', 'Motor Eléctrico RWD', 'Eléctrico', 248, 370, 2008, 2012);


-- ------------------------------------------
-- 2. RIVIAN (2 Modelos)
-- ------------------------------------------
SET @rivian_id = (SELECT id FROM car_brands WHERE name = 'Rivian');

SET @model_r1t = (SELECT id FROM car_models WHERE name = 'R1T' AND brand_id = @rivian_id);
SET @model_r1s = (SELECT id FROM car_models WHERE name = 'R1S' AND brand_id = @rivian_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_r1t, 'Quad-Motor AWD', 'Large Pack', '4 Motores Eléctricos', 'Eléctrico', 835, 1231, 2021, 2024),
(@model_r1s, 'Dual-Motor AWD', 'Large Pack', '2 Motores Eléctricos', 'Eléctrico', 600, 827, 2022, 2024);


-- ------------------------------------------
-- 3. LUCID (2 Modelos)
-- ------------------------------------------
SET @lucid_id = (SELECT id FROM car_brands WHERE name = 'Lucid');

SET @model_air = (SELECT id FROM car_models WHERE name = 'Air' AND brand_id = @lucid_id);
SET @model_gravity = (SELECT id FROM car_models WHERE name = 'Gravity' AND brand_id = @lucid_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_air, 'Grand Touring', 'Lucid-EV', 'Dual Motor AWD', 'Eléctrico', 819, 1200, 2021, 2024),
(@model_air, 'Sapphire', 'Lucid-EV', 'Tri-Motor AWD', 'Eléctrico', 1234, 1940, 2023, 2024),
(@model_gravity, 'Dream Edition', 'Lucid-EV', 'Dual Motor AWD', 'Eléctrico', 800, 1000, 2024, 2025);


-- ------------------------------------------
-- 4. FISKER (3 Modelos)
-- ------------------------------------------
SET @fisker_id = (SELECT id FROM car_brands WHERE name = 'Fisker');

SET @model_ocean = (SELECT id FROM car_models WHERE name = 'Ocean' AND brand_id = @fisker_id);
SET @model_karma = (SELECT id FROM car_models WHERE name = 'Karma' AND brand_id = @fisker_id);
SET @model_pear = (SELECT id FROM car_models WHERE name = 'Pear' AND brand_id = @fisker_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ocean, 'Extreme', 'Dual-Motor', 'Dual Motor AWD', 'Eléctrico', 550, 700, 2023, 2024),
(@model_karma, 'EVer (Range Extender)', 'LNF', '2.0 Turbo (GM) + Motor Eléc.', 'Híbrido Ench.', 403, 1300, 2011, 2012),
(@model_pear, 'AWD', 'Dual-Motor', 'Motor Eléctrico AWD', 'Eléctrico', 300, 450, 2024, 2025);

-- ==========================================
-- VERSIONES FARADAY FUTURE, NIO, XPENG Y FERRARI
-- El futuro eléctrico y la leyenda de Maranello
-- ==========================================

-- ------------------------------------------
-- 5. FARADAY FUTURE (1 Modelo - El unicornio eléctrico)
-- ------------------------------------------
SET @faraday_id = (SELECT id FROM car_brands WHERE name = 'Faraday Future');

SET @model_ff91 = (SELECT id FROM car_models WHERE name = 'FF 91' AND brand_id = @faraday_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ff91, '2.0 Futurist Alliance', 'Elec', 'Tri-Motor AWD', 'Eléctrico', 1050, 1977, 2023, 2025);


-- ------------------------------------------
-- 6. NIO (7 Modelos - Los reyes del intercambio de baterías)
-- ------------------------------------------
SET @nio_id = (SELECT id FROM car_brands WHERE name = 'NIO');

SET @model_es8 = (SELECT id FROM car_models WHERE name = 'ES8' AND brand_id = @nio_id);
SET @model_es6 = (SELECT id FROM car_models WHERE name = 'ES6' AND brand_id = @nio_id);
SET @model_ec6 = (SELECT id FROM car_models WHERE name = 'EC6' AND brand_id = @nio_id);
SET @model_et7 = (SELECT id FROM car_models WHERE name = 'ET7' AND brand_id = @nio_id);
SET @model_et5 = (SELECT id FROM car_models WHERE name = 'ET5' AND brand_id = @nio_id);
SET @model_el7 = (SELECT id FROM car_models WHERE name = 'EL7' AND brand_id = @nio_id);
SET @model_ep9 = (SELECT id FROM car_models WHERE name = 'EP9' AND brand_id = @nio_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_es8, '100 kWh AWD', 'TZ220', 'Dual Motor AWD', 'Eléctrico', 653, 850, 2018, 2024),
(@model_es6, 'Performance', 'TZ180', 'Dual Motor AWD', 'Eléctrico', 544, 725, 2019, 2024),
(@model_ec6, 'Performance', 'TZ180', 'Dual Motor AWD', 'Eléctrico', 544, 725, 2020, 2024),
(@model_et7, '150 kWh Long Range', 'TZ220', 'Dual Motor AWD', 'Eléctrico', 653, 850, 2022, 2024),
(@model_et5, 'Standard Range', 'TZ180', 'Dual Motor AWD', 'Eléctrico', 490, 700, 2022, 2024),
(@model_el7, '100 kWh', 'TZ220', 'Dual Motor AWD', 'Eléctrico', 653, 850, 2022, 2024),
(@model_ep9, 'Supercar', 'Elec', 'Quad Motor AWD', 'Eléctrico', 1360, 1480, 2016, 2019);


-- ------------------------------------------
-- 7. XPENG (5 Modelos - El "Tesla" chino)
-- ------------------------------------------
SET @xpeng_id = (SELECT id FROM car_brands WHERE name = 'Xpeng');

SET @model_p7 = (SELECT id FROM car_models WHERE name = 'P7' AND brand_id = @xpeng_id);
SET @model_g3 = (SELECT id FROM car_models WHERE name = 'G3' AND brand_id = @xpeng_id);
SET @model_p5 = (SELECT id FROM car_models WHERE name = 'P5' AND brand_id = @xpeng_id);
SET @model_g9 = (SELECT id FROM car_models WHERE name = 'G9' AND brand_id = @xpeng_id);
SET @model_g6 = (SELECT id FROM car_models WHERE name = 'G6' AND brand_id = @xpeng_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_p7, 'Performance AWD', 'Elec', 'Dual Motor AWD', 'Eléctrico', 430, 655, 2020, 2024),
(@model_g3, '520i', 'Elec', 'Motor Eléctrico FWD', 'Eléctrico', 197, 300, 2018, 2023),
(@model_p5, '460E', 'Elec', 'Motor Eléctrico FWD', 'Eléctrico', 211, 310, 2021, 2024),
(@model_g9, 'Performance AWD', 'Elec', 'Dual Motor AWD (800V Architecture)', 'Eléctrico', 551, 717, 2022, 2024),
(@model_g6, 'Long Range AWD', 'Elec', 'Dual Motor AWD', 'Eléctrico', 476, 660, 2023, 2024);


-- ------------------------------------------
-- 8. FERRARI (15 Modelos - Sangre roja de Maranello)
-- ------------------------------------------
SET @ferrari_id = (SELECT id FROM car_brands WHERE name = 'Ferrari');

SET @model_458 = (SELECT id FROM car_models WHERE name = '458 Italia' AND brand_id = @ferrari_id);
SET @model_488 = (SELECT id FROM car_models WHERE name = '488 GTB' AND brand_id = @ferrari_id);
SET @model_f8 = (SELECT id FROM car_models WHERE name = 'F8 Tributo' AND brand_id = @ferrari_id);
SET @model_812 = (SELECT id FROM car_models WHERE name = '812 Superfast' AND brand_id = @ferrari_id);
SET @model_roma = (SELECT id FROM car_models WHERE name = 'Roma' AND brand_id = @ferrari_id);
SET @model_portofino = (SELECT id FROM car_models WHERE name = 'Portofino' AND brand_id = @ferrari_id);
SET @model_sf90 = (SELECT id FROM car_models WHERE name = 'SF90 Stradale' AND brand_id = @ferrari_id);
SET @model_purosangue = (SELECT id FROM car_models WHERE name = 'Purosangue' AND brand_id = @ferrari_id);
SET @model_laferrari = (SELECT id FROM car_models WHERE name = 'LaFerrari' AND brand_id = @ferrari_id);
SET @model_enzo = (SELECT id FROM car_models WHERE name = 'Enzo' AND brand_id = @ferrari_id);
SET @model_f40 = (SELECT id FROM car_models WHERE name = 'F40' AND brand_id = @ferrari_id);
SET @model_f50 = (SELECT id FROM car_models WHERE name = 'F50' AND brand_id = @ferrari_id);
SET @model_testarossa = (SELECT id FROM car_models WHERE name = 'Testarossa' AND brand_id = @ferrari_id);
SET @model_360 = (SELECT id FROM car_models WHERE name = '360 Modena' AND brand_id = @ferrari_id);
SET @model_california = (SELECT id FROM car_models WHERE name = 'California' AND brand_id = @ferrari_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_458, '4.5 V8 Italia', 'F136 FB', '4.5 V8 Atmosférico', 'Gasolina', 570, 540, 2009, 2015),
(@model_488, '3.9 V8 GTB', 'F154 CB', '3.9 V8 Twin-Turbo', 'Gasolina', 670, 760, 2015, 2019),
(@model_f8, '3.9 V8 Tributo', 'F154 CG', '3.9 V8 Twin-Turbo', 'Gasolina', 720, 770, 2019, 2023),
(@model_812, '6.5 V12 Superfast', 'F140 GA', '6.5 V12 Atmosférico', 'Gasolina', 800, 718, 2017, 2024),
(@model_roma, '3.9 V8 Turbo', 'F154 BH', '3.9 V8 Twin-Turbo', 'Gasolina', 620, 760, 2020, 2024),
(@model_portofino, '3.9 V8 M', 'F154 BH', '3.9 V8 Twin-Turbo', 'Gasolina', 620, 760, 2017, 2023),
(@model_sf90, '4.0 V8 Hybrid', 'F154 FA', '4.0 V8 PHEV (3 Motores Elec)', 'Híbrido Ench.', 1000, 800, 2019, 2024),
(@model_purosangue, '6.5 V12 AWD', 'F140 IA', '6.5 V12 Atmosférico', 'Gasolina', 725, 716, 2023, 2024),
(@model_laferrari, '6.3 V12 Hybrid', 'F140 FE', '6.3 V12 + KERS', 'Híbrido', 963, 900, 2013, 2018),
(@model_enzo, '6.0 V12', 'F140 B', '6.0 V12 Atmosférico', 'Gasolina', 660, 657, 2002, 2004),
(@model_f40, '2.9 V8 Twin Turbo', 'F120 A', '2.9 V8 Biturbo', 'Gasolina', 478, 577, 1987, 1992),
(@model_f50, '4.7 V12', 'F130 A', '4.7 V12 Atmosférico', 'Gasolina', 520, 471, 1995, 1997),
(@model_testarossa, '4.9 Flat-12', 'F113 A', '4.9 12 Cilindros Opuestos', 'Gasolina', 390, 490, 1984, 1991),
(@model_360, '3.6 V8 Modena', 'F131', '3.6 V8 Atmosférico', 'Gasolina', 400, 373, 1999, 2005),
(@model_california, '4.3 V8', 'F136 IB', '4.3 V8 Atmosférico', 'Gasolina', 460, 485, 2008, 2012);

-- ==========================================
-- VERSIONES ASTON MARTIN, MCLAREN, PAGANI Y KOENIGSEGG
-- El Olimpo de los Superdeportivos e Hiperdeportivos
-- ==========================================

-- ------------------------------------------
-- 9. ASTON MARTIN (10 Modelos - Elegancia y V12)
-- ------------------------------------------
SET @aston_id = (SELECT id FROM car_brands WHERE name = 'Aston Martin');

-- DB11 y DB12
SET @model_db11 = (SELECT id FROM car_models WHERE name = 'DB11' AND brand_id = @aston_id);
SET @model_db12 = (SELECT id FROM car_models WHERE name = 'DB12' AND brand_id = @aston_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_db11, '5.2 V12 Twin-Turbo', 'AE31', '5.2 V12 Biturbo', 'Gasolina', 608, 700, 2016, 2023),
(@model_db11, '4.0 V8 (Mercedes-AMG)', 'M177', '4.0 V8 Biturbo', 'Gasolina', 510, 675, 2017, 2023),
(@model_db12, '4.0 V8 Twin-Turbo', 'M177', '4.0 V8 Biturbo (AMG)', 'Gasolina', 680, 800, 2023, 2024);

-- Vantage, DBS, DBX
SET @model_vantage = (SELECT id FROM car_models WHERE name = 'Vantage' AND brand_id = @aston_id);
SET @model_dbs = (SELECT id FROM car_models WHERE name = 'DBS Superleggera' AND brand_id = @aston_id);
SET @model_dbx = (SELECT id FROM car_models WHERE name = 'DBX' AND brand_id = @aston_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_vantage, '4.0 V8 (Mercedes-AMG)', 'M177', '4.0 V8 Biturbo', 'Gasolina', 510, 685, 2018, 2024),
(@model_vantage, 'V12 Vantage', 'AE31', '5.2 V12 Biturbo', 'Gasolina', 700, 753, 2022, 2023),
(@model_dbs, '5.2 V12 Superleggera', 'AE31', '5.2 V12 Biturbo', 'Gasolina', 725, 900, 2018, 2024),
(@model_dbx, 'V8 SUV', 'M177', '4.0 V8 Biturbo', 'Gasolina', 550, 700, 2020, 2024),
(@model_dbx, '707', 'M177', '4.0 V8 Biturbo High Performance', 'Gasolina', 707, 900, 2022, 2024);

-- Valkyrie, Valhalla, DB9, Vanquish, Rapide
SET @model_valkyrie = (SELECT id FROM car_models WHERE name = 'Valkyrie' AND brand_id = @aston_id);
SET @model_db9 = (SELECT id FROM car_models WHERE name = 'DB9' AND brand_id = @aston_id);
SET @model_vanquish = (SELECT id FROM car_models WHERE name = 'Vanquish' AND brand_id = @aston_id);
SET @model_rapide = (SELECT id FROM car_models WHERE name = 'Rapide' AND brand_id = @aston_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_valkyrie, '6.5 V12 Hybrid', 'Cosworth V12', '6.5 V12 Atmosférico + KERS', 'Híbrido', 1155, 900, 2021, 2024),
(@model_db9, '6.0 V12', 'AM11', '5.9 V12 Atmosférico', 'Gasolina', 517, 620, 2004, 2016),
(@model_vanquish, '6.0 V12 (Gen 2)', 'AM29', '5.9 V12 Atmosférico', 'Gasolina', 576, 630, 2012, 2018),
(@model_rapide, 'S V12', 'AM29', '5.9 V12 Atmosférico', 'Gasolina', 560, 630, 2013, 2020);


-- ------------------------------------------
-- 10. MCLAREN (9 Modelos - Ingeniería de Woking)
-- ------------------------------------------
SET @mclaren_id = (SELECT id FROM car_brands WHERE name = 'McLaren');

SET @model_720s = (SELECT id FROM car_models WHERE name = '720S' AND brand_id = @mclaren_id);
SET @model_artura = (SELECT id FROM car_models WHERE name = 'Artura' AND brand_id = @mclaren_id);
SET @model_p1 = (SELECT id FROM car_models WHERE name = 'P1' AND brand_id = @mclaren_id);
SET @model_senna = (SELECT id FROM car_models WHERE name = 'Senna' AND brand_id = @mclaren_id);
SET @model_f1 = (SELECT id FROM car_models WHERE name = 'F1' AND brand_id = @mclaren_id);
SET @model_570s = (SELECT id FROM car_models WHERE name = '570S' AND brand_id = @mclaren_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_720s, '4.0 V8 Twin-Turbo', 'M840T', '4.0 V8 Biturbo', 'Gasolina', 720, 770, 2017, 2023),
(@model_artura, '3.0 V6 Hybrid', 'M630', '3.0 V6 Biturbo PHEV', 'Híbrido Ench.', 680, 720, 2021, 2024),
(@model_p1, '3.8 V8 Hybrid', 'M838TQ', '3.8 V8 Biturbo PHEV', 'Híbrido Ench.', 916, 900, 2013, 2015),
(@model_senna, '4.0 V8 GTR', 'M840TR', '4.0 V8 Biturbo', 'Gasolina', 800, 800, 2018, 2019),
(@model_f1, '6.1 V12 (BMW)', 'S70/2', '6.1 V12 Atmosférico (BMW)', 'Gasolina', 627, 650, 1992, 1998),
(@model_570s, '3.8 V8 Twin-Turbo', 'M838TE', '3.8 V8 Biturbo', 'Gasolina', 570, 600, 2015, 2021);


-- ------------------------------------------
-- 11. PAGANI (3 Modelos - El sueño de Horacio)
-- ------------------------------------------
SET @pagani_id = (SELECT id FROM car_brands WHERE name = 'Pagani');

SET @model_zonda = (SELECT id FROM car_models WHERE name = 'Zonda' AND brand_id = @pagani_id);
SET @model_huayra = (SELECT id FROM car_models WHERE name = 'Huayra' AND brand_id = @pagani_id);
SET @model_utopia = (SELECT id FROM car_models WHERE name = 'Utopia' AND brand_id = @pagani_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_zonda, 'F 7.3 V12 (AMG)', 'M297', '7.3 V12 Atmosférico (Mercedes-AMG)', 'Gasolina', 602, 760, 2005, 2011),
(@model_huayra, '6.0 V12 BC', 'M158', '6.0 V12 Biturbo (Mercedes-AMG)', 'Gasolina', 750, 1000, 2012, 2018),
(@model_utopia, '6.0 V12 Twin-Turbo', 'M158', '6.0 V12 Biturbo', 'Gasolina', 864, 1100, 2022, 2024);


-- ------------------------------------------
-- 12. KOENIGSEGG (5 Modelos - Innovación radical sueca)
-- ------------------------------------------
SET @koenigsegg_id = (SELECT id FROM car_brands WHERE name = 'Koenigsegg');

SET @model_agera = (SELECT id FROM car_models WHERE name = 'Agera' AND brand_id = @koenigsegg_id);
SET @model_jesko = (SELECT id FROM car_models WHERE name = 'Jesko' AND brand_id = @koenigsegg_id);
SET @model_gemera = (SELECT id FROM car_models WHERE name = 'Gemera' AND brand_id = @koenigsegg_id);
SET @model_ccx = (SELECT id FROM car_models WHERE name = 'CCX' AND brand_id = @koenigsegg_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_agera, 'RS 5.0 V8', 'K-V8', '5.0 V8 Biturbo', 'Gasolina/E85', 1160, 1280, 2015, 2018),
(@model_jesko, '5.0 V8 LST', 'K-V8', '5.0 V8 Biturbo', 'Gasolina/E85', 1600, 1500, 2021, 2024),
(@model_gemera, '2.0 TFG (Tiny Friendly Giant)', 'TFG', '2.0 3 cil. Biturbo + 3 Motores Elec.', 'Híbrido Ench.', 1700, 3500, 2021, 2024),
(@model_ccx, '4.7 V8 Supercharged', 'K-V8', '4.7 V8 Doble Compresor', 'Gasolina', 806, 920, 2006, 2010);

-- ==========================================
-- VERSIONES SAAB Y ROVER
-- Ingeniería aeronáutica sueca y el fin del imperio británico
-- ==========================================

-- ------------------------------------------
-- 1. SAAB (7 Modelos - Herencia GM y Turbos suecos)
-- ------------------------------------------
SET @saab_id = (SELECT id FROM car_brands WHERE name = 'Saab');

-- 900 y 9000
SET @model_900 = (SELECT id FROM car_models WHERE name = '900' AND brand_id = @saab_id);
SET @model_9000 = (SELECT id FROM car_models WHERE name = '9000' AND brand_id = @saab_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_900, '2.0 Turbo (Classic)', 'B202', '2.0 Turbo 16V', 'Gasolina', 175, 273, 1984, 1993),
(@model_900, '2.0 Turbo (GM)', 'B204L', '2.0 Turbo (Ecotec)', 'Gasolina', 185, 263, 1993, 1998),
(@model_9000, '2.3 Turbo Aero', 'B234R', '2.3 Turbo 16V', 'Gasolina', 225, 350, 1993, 1998);

-- 9-3 y 9-5 (La era moderna)
SET @model_93 = (SELECT id FROM car_models WHERE name = '9-3' AND brand_id = @saab_id);
SET @model_95 = (SELECT id FROM car_models WHERE name = '9-5' AND brand_id = @saab_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_93, '2.2 TiD (YS3D)', 'D223L', '2.2 Turbodiesel (Opel)', 'Diesel', 115, 260, 1998, 2002),
(@model_93, '1.9 TiD (YS3F)', 'Z19DTH', '1.9 Turbodiesel (Fiat JTD)', 'Diesel', 150, 320, 2004, 2011),
(@model_93, '2.0 Turbo Aero', 'B207R', '2.0 Turbo', 'Gasolina', 210, 300, 2003, 2011),
(@model_95, '3.0 V6 TiD', 'D308L', '3.0 V6 Turbodiesel (Isuzu)', 'Diesel', 176, 350, 2001, 2005),
(@model_95, '2.3 Turbo Aero', 'B235R', '2.3 Turbo High Output', 'Gasolina', 250, 350, 2000, 2009);

-- Clásicos (Sonett, 96, 99)
SET @model_sonett = (SELECT id FROM car_models WHERE name = 'Sonett' AND brand_id = @saab_id);
SET @model_96 = (SELECT id FROM car_models WHERE name = '96' AND brand_id = @saab_id);
SET @model_99 = (SELECT id FROM car_models WHERE name = '99' AND brand_id = @saab_id);
INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_sonett, '1.7 V4 (Sonett III)', 'Ford V4', '1.7 V4 (Motor Ford)', 'Gasolina', 65, 115, 1970, 1974),
(@model_96, '1.5 V4', 'Ford V4', '1.5 V4 (Motor Ford)', 'Gasolina', 65, 115, 1967, 1980),
(@model_99, '2.0 Turbo', 'B20', '2.0 Turbo (Primer Turbo masivo)', 'Gasolina', 145, 235, 1978, 1984);


-- ------------------------------------------
-- 2. ROVER (12 Modelos - El motor K-Series y herencia Honda/BMW)
-- ------------------------------------------
SET @rover_id = (SELECT id FROM car_brands WHERE name = 'Rover');

-- Los pequeños y compactos (25, 200, Streetwise, CityRover, Metro)
SET @model_25 = (SELECT id FROM car_models WHERE name = '25' AND brand_id = @rover_id);
SET @model_200 = (SELECT id FROM car_models WHERE name = '200' AND brand_id = @rover_id);
SET @model_swise = (SELECT id FROM car_models WHERE name = 'Streetwise' AND brand_id = @rover_id);
SET @model_city = (SELECT id FROM car_models WHERE name = 'CityRover' AND brand_id = @rover_id);
SET @model_metro = (SELECT id FROM car_models WHERE name = 'Metro' AND brand_id = @rover_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_25, '1.4 16V (K-Series)', '14K4F', '1.4 16V (Ojo Junta Culata)', 'Gasolina', 103, 123, 1999, 2005),
(@model_25, '2.0 iDT', '20T2N', '2.0 Turbodiesel L-Series', 'Diesel', 101, 240, 1999, 2005),
(@model_200, '214 Si (RF)', '14K4F', '1.4 16V K-Series', 'Gasolina', 103, 123, 1995, 2000),
(@model_swise, '2.0 TD', '20T2N', '2.0 Turbodiesel', 'Diesel', 101, 240, 2003, 2005),
(@model_city, '1.4 (Base Tata Indica)', 'Tata 1.4', '1.4 8V', 'Gasolina', 85, 120, 2003, 2005),
(@model_metro, '1.1 (K-Series)', '11K2', '1.1 8V', 'Gasolina', 60, 90, 1990, 1994);

-- Las berlinas medias (45, 400, 600)
SET @model_45 = (SELECT id FROM car_models WHERE name = '45' AND brand_id = @rover_id);
SET @model_400 = (SELECT id FROM car_models WHERE name = '400' AND brand_id = @rover_id);
SET @model_600 = (SELECT id FROM car_models WHERE name = '600' AND brand_id = @rover_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_45, '1.6 16V', '16K4F', '1.6 16V K-Series', 'Gasolina', 109, 145, 2000, 2005),
(@model_400, '420 Di (RT)', '20T2N', '2.0 Turbodiesel L-Series', 'Diesel', 105, 210, 1995, 2000),
(@model_600, '620 Ti', '20T4G', '2.0 Turbo T-Series', 'Gasolina', 200, 240, 1994, 1999),
(@model_600, '620 SDi (Base Honda Accord)', '20T2N', '2.0 Turbodiesel', 'Diesel', 105, 210, 1994, 1999);

-- Las grandes y clásicos (75, 800, Montego, Maestro)
SET @model_75 = (SELECT id FROM car_models WHERE name = '75' AND brand_id = @rover_id);
SET @model_800 = (SELECT id FROM car_models WHERE name = '800' AND brand_id = @rover_id);
SET @model_montego = (SELECT id FROM car_models WHERE name = 'Montego' AND brand_id = @rover_id);
SET @model_maestro = (SELECT id FROM car_models WHERE name = 'Maestro' AND brand_id = @rover_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_75, '2.0 CDT (Motor BMW)', 'M47R', '2.0 Turbodiesel (BMW M47)', 'Diesel', 116, 260, 1999, 2005),
(@model_75, '2.5 V6', '25K4F', '2.5 V6 KV6', 'Gasolina', 177, 240, 1999, 2005),
(@model_800, '827 Vitesse (Motor Honda)', 'C27A', '2.7 V6 (Honda)', 'Gasolina', 177, 225, 1988, 1991),
(@model_montego, '2.0 GTi', 'M-Series', '2.0 16V', 'Gasolina', 140, 182, 1988, 1993),
(@model_maestro, '2.0 Turbo', 'O-Series', '2.0 Turbo', 'Gasolina', 152, 229, 1988, 1991);

-- ==========================================
-- VERSIONES DAEWOO, MG ROVER, TALBOT, SANTANA Y GALLOPER
-- Clones coreanos, herencia británica y robustez española
-- ==========================================

-- ------------------------------------------
-- 3. DAEWOO (11 Modelos - Base GM/Opel)
-- ------------------------------------------
SET @daewoo_id = (SELECT id FROM car_brands WHERE name = 'Daewoo');

SET @model_lanos = (SELECT id FROM car_models WHERE name = 'Lanos' AND brand_id = @daewoo_id);
SET @model_matiz = (SELECT id FROM car_models WHERE name = 'Matiz' AND brand_id = @daewoo_id);
SET @model_nubira = (SELECT id FROM car_models WHERE name = 'Nubira' AND brand_id = @daewoo_id);
SET @model_leganza = (SELECT id FROM car_models WHERE name = 'Leganza' AND brand_id = @daewoo_id);
SET @model_kalos = (SELECT id FROM car_models WHERE name = 'Kalos' AND brand_id = @daewoo_id);
SET @model_tacuma = (SELECT id FROM car_models WHERE name = 'Tacuma' AND brand_id = @daewoo_id);
SET @model_nexia = (SELECT id FROM car_models WHERE name = 'Nexia' AND brand_id = @daewoo_id);
SET @model_espero = (SELECT id FROM car_models WHERE name = 'Espero' AND brand_id = @daewoo_id);
SET @model_tico = (SELECT id FROM car_models WHERE name = 'Tico' AND brand_id = @daewoo_id);
SET @model_evanda = (SELECT id FROM car_models WHERE name = 'Evanda' AND brand_id = @daewoo_id);
SET @model_arcadia = (SELECT id FROM car_models WHERE name = 'Arcadia' AND brand_id = @daewoo_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_lanos, '1.5 SOHC', 'A15SMS', '1.5 8V', 'Gasolina', 86, 130, 1997, 2002),
(@model_matiz, '0.8 SE', 'F8CV', '0.8 3 cil. (Suzuki)', 'Gasolina', 52, 69, 1998, 2004),
(@model_nubira, '2.0 CDX', 'X20SED', '2.0 16V (GM D-TEC)', 'Gasolina', 133, 184, 1997, 2003),
(@model_leganza, '2.0 CDX', 'X20SED', '2.0 16V', 'Gasolina', 133, 184, 1997, 2002),
(@model_kalos, '1.4 16V', 'F14D3', '1.4 16V', 'Gasolina', 94, 130, 2002, 2005),
(@model_tacuma, '1.6 SX', 'A16DMS', '1.6 16V', 'Gasolina', 105, 142, 2000, 2004),
(@model_nexia, '1.5 GLX (Base Opel Kadett)', 'G15MF', '1.5 8V', 'Gasolina', 75, 123, 1995, 1997),
(@model_espero, '1.5 DOHC', 'A15DMS', '1.5 16V', 'Gasolina', 90, 137, 1995, 1999),
(@model_tico, '0.8 DX (Base Suzuki Alto)', 'F8C', '0.8 3 cil.', 'Gasolina', 48, 64, 1991, 2001),
(@model_evanda, '2.0 CDX', 'X20SED', '2.0 16V', 'Gasolina', 131, 181, 2002, 2004),
(@model_arcadia, '3.2 V6 (Base Honda Legend)', 'C32A', '3.2 V6 (Honda)', 'Gasolina', 215, 285, 1994, 1999);


-- ------------------------------------------
-- 4. MG ROVER (6 Modelos - Bloque conjunto)
-- ------------------------------------------
SET @mgrover_id = (SELECT id FROM car_brands WHERE name = 'MG Rover');

SET @model_zr = (SELECT id FROM car_models WHERE name = 'ZR' AND brand_id = @mgrover_id);
SET @model_zs = (SELECT id FROM car_models WHERE name = 'ZS' AND brand_id = @mgrover_id);
SET @model_zt = (SELECT id FROM car_models WHERE name = 'ZT' AND brand_id = @mgrover_id);
SET @model_ztt = (SELECT id FROM car_models WHERE name = 'ZT-T' AND brand_id = @mgrover_id);
SET @model_tf = (SELECT id FROM car_models WHERE name = 'TF' AND brand_id = @mgrover_id);
SET @model_mgf = (SELECT id FROM car_models WHERE name = 'MGF' AND brand_id = @mgrover_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_zr, '160 VVC', '18K4K', '1.8 16V VVC (K-Series)', 'Gasolina', 160, 174, 2001, 2005),
(@model_zs, '180 2.5 V6', '25K4F', '2.5 V6 KV6', 'Gasolina', 177, 240, 2001, 2005),
(@model_zt, '2.0 CDTi', 'M47R', '2.0 Turbodiesel (BMW M47)', 'Diesel', 131, 300, 2002, 2005),
(@model_ztt, '2.0 CDTi Ranchera', 'M47R', '2.0 Turbodiesel (BMW)', 'Diesel', 131, 300, 2002, 2005),
(@model_tf, '135', '18K4F', '1.8 16V K-Series', 'Gasolina', 136, 165, 2002, 2005),
(@model_mgf, '1.8i VVC', '18K4K', '1.8 16V VVC', 'Gasolina', 145, 174, 1995, 2002);


-- ------------------------------------------
-- 5. TALBOT (7 Modelos - Herencia Simca/PSA)
-- ------------------------------------------
SET @talbot_id = (SELECT id FROM car_brands WHERE name = 'Talbot');

SET @model_horizon = (SELECT id FROM car_models WHERE name = 'Horizon' AND brand_id = @talbot_id);
SET @model_solara = (SELECT id FROM car_models WHERE name = 'Solara' AND brand_id = @talbot_id);
SET @model_samba = (SELECT id FROM car_models WHERE name = 'Samba' AND brand_id = @talbot_id);
SET @model_alpine = (SELECT id FROM car_models WHERE name = 'Alpine' AND brand_id = @talbot_id);
SET @model_tagora = (SELECT id FROM car_models WHERE name = 'Tagora' AND brand_id = @talbot_id);
SET @model_150 = (SELECT id FROM car_models WHERE name = '150' AND brand_id = @talbot_id);
SET @model_murena = (SELECT id FROM car_models WHERE name = 'Matra Murena' AND brand_id = @talbot_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_horizon, '1.5 GLD', 'XUD7', '1.7 Diesel (PSA)', 'Diesel', 60, 103, 1982, 1987),
(@model_solara, '1.6 SX', '6J2', '1.6 8V', 'Gasolina', 90, 132, 1980, 1986),
(@model_samba, '1.1 LS', 'XW7', '1.1 8V (Motor inclinado)', 'Gasolina', 50, 84, 1981, 1986),
(@model_alpine, '1.5 GL', '6G2', '1.4 8V', 'Gasolina', 85, 108, 1975, 1985),
(@model_tagora, '2.3 DT', 'XD2S', '2.3 Turbodiesel (Peugeot)', 'Diesel', 80, 188, 1980, 1983),
(@model_150, 'GT', '6J2', '1.6 8V', 'Gasolina', 90, 132, 1977, 1985),
(@model_murena, '2.2', 'N9LU', '2.2 8V (Motor Central)', 'Gasolina', 118, 181, 1980, 1983);


-- ------------------------------------------
-- 6. SANTANA (5 Modelos - El orgullo de Linares)
-- ------------------------------------------
SET @santana_id = (SELECT id FROM car_brands WHERE name = 'Santana');

SET @model_anibal = (SELECT id FROM car_models WHERE name = 'Aníbal' AND brand_id = @santana_id);
SET @model_300 = (SELECT id FROM car_models WHERE name = '300' AND brand_id = @santana_id);
SET @model_350 = (SELECT id FROM car_models WHERE name = '350' AND brand_id = @santana_id);
SET @model_ps10 = (SELECT id FROM car_models WHERE name = 'PS-10' AND brand_id = @santana_id);
SET @model_m25 = (SELECT id FROM car_models WHERE name = 'Motor 2.5' AND brand_id = @santana_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_anibal, '2.8 TD (Iveco)', '8140.43S', '2.8 Turbodiesel (Iveco)', 'Diesel', 125, 285, 2002, 2011),
(@model_300, '1.6 HDI (3 puertas)', 'DV6TED4', '1.6 Turbodiesel (PSA)', 'Diesel', 90, 215, 2005, 2011),
(@model_350, '2.0 HDI (5 puertas)', 'RHZ', '2.0 Turbodiesel (PSA)', 'Diesel', 90, 205, 2005, 2011),
(@model_ps10, '2.8 TD 4x4', '8140.43', '2.8 Turbodiesel (Iveco)', 'Diesel', 125, 285, 2002, 2011),
(@model_m25, '2.5 DL (Base Land Rover)', 'Santana 2.5', '2.5 Diesel Atmosférico', 'Diesel', 64, 157, 1984, 1994);


-- ------------------------------------------
-- 7. GALLOPER (4 Modelos - Herencia Mitsubishi)
-- ------------------------------------------
SET @galloper_id = (SELECT id FROM car_brands WHERE name = 'Galloper');

SET @model_exceed = (SELECT id FROM car_models WHERE name = 'Exceed' AND brand_id = @galloper_id);
SET @model_super = (SELECT id FROM car_models WHERE name = 'Super Exceed' AND brand_id = @galloper_id);
SET @model_innov = (SELECT id FROM car_models WHERE name = 'Innovation' AND brand_id = @galloper_id);
SET @model_santamo = (SELECT id FROM car_models WHERE name = 'Santamo' AND brand_id = @galloper_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_exceed, '2.5 TD (Base Montero L040)', '4D56', '2.5 Turbodiesel', 'Diesel', 99, 240, 1991, 2001),
(@model_super, '2.5 TCI (Base Montero V20)', '4D56', '2.5 Turbodiesel Intercooler', 'Diesel', 115, 253, 1998, 2003),
(@model_innov, '2.5 TD Corto', '4D56', '2.5 Turbodiesel', 'Diesel', 99, 240, 1998, 2002),
(@model_santamo, '2.0 (Base Mitsubishi Chariot)', 'G4CP', '2.0 8V / 16V', 'Gasolina', 105, 175, 1996, 2002);

-- ==========================================
-- VERSIONES MERCEDES-BENZ
-- El templo de los motores OM, M y las siglas AMG
-- ==========================================

SET @mercedes_id = (SELECT id FROM car_brands WHERE name = 'Mercedes-Benz');

-- ------------------------------------------
-- 1. COMPACTOS (Clase A, B, CLA, GLA) - Alianza Renault y Motores Transversales
-- ------------------------------------------
SET @model_a = (SELECT id FROM car_models WHERE name = 'Clase A' AND brand_id = @mercedes_id);
SET @model_b = (SELECT id FROM car_models WHERE name = 'Clase B' AND brand_id = @mercedes_id);
SET @model_cla = (SELECT id FROM car_models WHERE name = 'CLA' AND brand_id = @mercedes_id);
SET @model_gla = (SELECT id FROM car_models WHERE name = 'GLA' AND brand_id = @mercedes_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_a, 'A 180 d (W176)', 'OM607', '1.5 dCi (Renault K9K)', 'Diesel', 109, 260, 2012, 2018),
(@model_a, 'A 200 d (W176)', 'OM651', '2.1 Turbodiesel', 'Diesel', 136, 300, 2014, 2018),
(@model_a, 'A 45 AMG 4MATIC', 'M133', '2.0 Turbo AMG', 'Gasolina', 360, 450, 2013, 2015),
(@model_a, 'A 200 d (W177)', 'OM654', '2.0 Turbodiesel', 'Diesel', 150, 320, 2018, 2024),
(@model_b, 'B 180 d (W246)', 'OM607', '1.5 dCi (Renault)', 'Diesel', 109, 260, 2011, 2018),
(@model_cla, 'CLA 220 d (C117)', 'OM651', '2.1 Turbodiesel', 'Diesel', 170, 350, 2013, 2019),
(@model_gla, 'GLA 200 d (X156)', 'OM651', '2.1 Turbodiesel', 'Diesel', 136, 300, 2013, 2020);


-- ------------------------------------------
-- 2. BERLINAS MEDIAS (Clase C, CLK) - El corazón del taller
-- ------------------------------------------
SET @model_c = (SELECT id FROM car_models WHERE name = 'Clase C' AND brand_id = @mercedes_id);
SET @model_clk = (SELECT id FROM car_models WHERE name = 'CLK' AND brand_id = @mercedes_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_c, 'C 220 CDI (W203)', 'OM611 / OM646', '2.1 Turbodiesel', 'Diesel', 143, 340, 2000, 2007),
(@model_c, 'C 220 CDI (W204)', 'OM651', '2.1 Turbodiesel (Ojo Inyectores Delphi)', 'Diesel', 170, 400, 2007, 2014),
(@model_c, 'C 63 AMG (W204)', 'M156', '6.2 V8 Atmosférico (Leyenda)', 'Gasolina', 457, 600, 2007, 2014),
(@model_c, 'C 220 d (W205)', 'OM651', '2.1 Turbodiesel', 'Diesel', 170, 400, 2014, 2018),
(@model_c, 'C 220 d (W205 Facelift)', 'OM654', '2.0 Turbodiesel (Aluminio)', 'Diesel', 194, 400, 2018, 2021),
(@model_c, 'C 300 e (W206)', 'M254', '2.0 PHEV Híbrido', 'Híbrido Ench.', 313, 550, 2021, 2024),
(@model_clk, 'CLK 270 CDI (W209)', 'OM612', '2.7 L5 Turbodiesel', 'Diesel', 170, 400, 2002, 2005),
(@model_clk, 'CLK 320 CDI (W209)', 'OM642', '3.0 V6 Turbodiesel', 'Diesel', 224, 510, 2005, 2009);


-- ------------------------------------------
-- 3. GRANDES BERLINAS (Clase E, Clase S, CLS)
-- ------------------------------------------
SET @model_e = (SELECT id FROM car_models WHERE name = 'Clase E' AND brand_id = @mercedes_id);
SET @model_s = (SELECT id FROM car_models WHERE name = 'Clase S' AND brand_id = @mercedes_id);
SET @model_cls = (SELECT id FROM car_models WHERE name = 'CLS' AND brand_id = @mercedes_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_e, 'E 270 CDI (W211)', 'OM647', '2.7 L5 Turbodiesel', 'Diesel', 177, 400, 2002, 2005),
(@model_e, 'E 320 CDI (W211 L6)', 'OM648', '3.2 L6 Turbodiesel', 'Diesel', 204, 500, 2002, 2005),
(@model_e, 'E 320 CDI (W211 V6)', 'OM642', '3.0 V6 Turbodiesel', 'Diesel', 224, 540, 2005, 2009),
(@model_e, 'E 220 CDI (W212)', 'OM651', '2.1 Turbodiesel', 'Diesel', 170, 400, 2009, 2016),
(@model_e, 'E 220 d (W213)', 'OM654', '2.0 Turbodiesel', 'Diesel', 194, 400, 2016, 2023),
(@model_s, 'S 320 CDI (W220)', 'OM648', '3.2 L6 Turbodiesel', 'Diesel', 204, 500, 1999, 2005),
(@model_s, 'S 350 BlueTEC (W221)', 'OM642', '3.0 V6 Turbodiesel', 'Diesel', 258, 620, 2009, 2013),
(@model_s, 'S 400 d (W222)', 'OM656', '2.9 L6 Turbodiesel', 'Diesel', 340, 700, 2017, 2020),
(@model_cls, 'CLS 350 CDI (C218)', 'OM642', '3.0 V6 Turbodiesel', 'Diesel', 265, 620, 2011, 2014);


-- ------------------------------------------
-- 4. SUV Y 4X4 (ML, GLE, GLC, Clase G)
-- ------------------------------------------
SET @model_ml = (SELECT id FROM car_models WHERE name = 'ML' AND brand_id = @mercedes_id);
SET @model_gle = (SELECT id FROM car_models WHERE name = 'GLE' AND brand_id = @mercedes_id);
SET @model_glc = (SELECT id FROM car_models WHERE name = 'GLC' AND brand_id = @mercedes_id);
SET @model_g = (SELECT id FROM car_models WHERE name = 'Clase G' AND brand_id = @mercedes_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_ml, 'ML 270 CDI (W163)', 'OM612', '2.7 L5 Turbodiesel', 'Diesel', 163, 400, 1997, 2005),
(@model_ml, 'ML 320 CDI (W164)', 'OM642', '3.0 V6 Turbodiesel', 'Diesel', 224, 510, 2005, 2011),
(@model_gle, 'GLE 350 d (W166)', 'OM642', '3.0 V6 Turbodiesel', 'Diesel', 258, 620, 2015, 2018),
(@model_glc, 'GLC 220 d (X253)', 'OM651', '2.1 Turbodiesel', 'Diesel', 170, 400, 2015, 2019),
(@model_g, 'G 400 CDI (W463)', 'OM628', '4.0 V8 Turbodiesel (Ojo Electrónica)', 'Diesel', 250, 560, 2000, 2006),
(@model_g, 'G 350 d (W463)', 'OM642', '3.0 V6 Turbodiesel', 'Diesel', 211, 540, 2009, 2018),
(@model_g, 'G 63 AMG (W463)', 'M177', '4.0 V8 Biturbo AMG', 'Gasolina', 585, 850, 2018, 2024);


-- ------------------------------------------
-- 5. COMERCIALES (Vito, Sprinter, Citan) - El pan del taller
-- ------------------------------------------
SET @model_vito = (SELECT id FROM car_models WHERE name = 'Vito' AND brand_id = @mercedes_id);
SET @model_sprinter = (SELECT id FROM car_models WHERE name = 'Sprinter' AND brand_id = @mercedes_id);
SET @model_citan = (SELECT id FROM car_models WHERE name = 'Citan' AND brand_id = @mercedes_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_vito, '111 CDI (W639)', 'OM646', '2.1 Turbodiesel', 'Diesel', 109, 250, 2003, 2010),
(@model_vito, '114 CDI (W447)', 'OM651', '2.1 Turbodiesel', 'Diesel', 136, 330, 2014, 2020),
(@model_sprinter, '313 CDI (W906)', 'OM651', '2.1 Turbodiesel (El Rey de las Flotas)', 'Diesel', 129, 305, 2006, 2018),
(@model_sprinter, '316 CDI (W906)', 'OM651', '2.1 Turbodiesel Biturbo', 'Diesel', 163, 360, 2009, 2018),
(@model_sprinter, '319 CDI (W907)', 'OM642', '3.0 V6 Turbodiesel', 'Diesel', 190, 440, 2018, 2021),
(@model_citan, '109 CDI (W415)', 'OM607', '1.5 dCi (Renault)', 'Diesel', 90, 200, 2012, 2021);


-- ------------------------------------------
-- 6. CLÁSICOS MÍTICOS (190, 300, W124)
-- ------------------------------------------
SET @model_190 = (SELECT id FROM car_models WHERE name = '190' AND brand_id = @mercedes_id);
SET @model_w124 = (SELECT id FROM car_models WHERE name = 'W124' AND brand_id = @mercedes_id);

INSERT IGNORE INTO car_version (model_id, version_name, engine_code, engine_type, fuel_type, power_cv, torque_nm, year_start, year_end) VALUES
(@model_190, '190 D 2.0 (W201)', 'OM601', '2.0 Diesel Atmosférico', 'Diesel', 72, 123, 1983, 1993),
(@model_190, '190 E 2.5-16', 'M102', '2.5 16V Cosworth', 'Gasolina', 195, 235, 1988, 1993),
(@model_w124, '300 D', 'OM603', '3.0 L6 Diesel Atmosférico', 'Diesel', 113, 191, 1985, 1993),
(@model_w124, '500 E', 'M119', '5.0 V8', 'Gasolina', 326, 470, 1990, 1995);