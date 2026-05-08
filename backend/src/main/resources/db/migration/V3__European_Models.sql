-- ==========================================
-- GRUPO VOLKSWAGEN (Modelos históricos y actuales)
-- ==========================================

-- 1. SEAT
SET @seat_id = (SELECT id FROM car_brands WHERE name = 'SEAT');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Ibiza', @seat_id), ('León', @seat_id), ('Arona', @seat_id), ('Ateca', @seat_id),
('Tarraco', @seat_id), ('Alhambra', @seat_id), ('Toledo', @seat_id), ('Córdoba', @seat_id),
('Altea', @seat_id), ('Altea XL', @seat_id), ('Mii', @seat_id), ('Exeo', @seat_id),
('Arosa', @seat_id), ('Inca', @seat_id), ('Marbella', @seat_id), ('Panda', @seat_id), ('Ritmo', @seat_id);

-- 2. CUPRA
SET @cupra_id = (SELECT id FROM car_brands WHERE name = 'Cupra');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Formentor', @cupra_id), ('Born', @cupra_id), ('León', @cupra_id),
('Ateca', @cupra_id), ('Tavascan', @cupra_id), ('Terramar', @cupra_id);

-- 3. VOLKSWAGEN
SET @vw_id = (SELECT id FROM car_brands WHERE name = 'Volkswagen');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Golf', @vw_id), ('Polo', @vw_id), ('Passat', @vw_id), ('Tiguan', @vw_id),
('T-Roc', @vw_id), ('T-Cross', @vw_id), ('Taigo', @vw_id), ('Touareg', @vw_id),
('Touran', @vw_id), ('Sharan', @vw_id), ('Up!', @vw_id), ('Arteon', @vw_id),
('Scirocco', @vw_id), ('Beetle', @vw_id), ('New Beetle', @vw_id), ('Caddy', @vw_id),
('Transporter', @vw_id), ('Multivan', @vw_id), ('Caravelle', @vw_id), ('California', @vw_id),
('Amarok', @vw_id), ('Crafter', @vw_id), ('ID.3', @vw_id), ('ID.4', @vw_id),
('ID.5', @vw_id), ('ID.7', @vw_id), ('ID.Buzz', @vw_id), ('Jetta', @vw_id),
('Bora', @vw_id), ('Lupo', @vw_id), ('Fox', @vw_id), ('Phaeton', @vw_id),
('Eos', @vw_id), ('Corrado', @vw_id), ('Vento', @vw_id);

-- 4. AUDI
SET @audi_id = (SELECT id FROM car_brands WHERE name = 'Audi');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('A1', @audi_id), ('A2', @audi_id), ('A3', @audi_id), ('A4', @audi_id),
('A5', @audi_id), ('A6', @audi_id), ('A7', @audi_id), ('A8', @audi_id),
('Q2', @audi_id), ('Q3', @audi_id), ('Q4 e-tron', @audi_id), ('Q5', @audi_id),
('Q7', @audi_id), ('Q8', @audi_id), ('Q8 e-tron', @audi_id), ('TT', @audi_id),
('R8', @audi_id), ('e-tron', @audi_id), ('e-tron GT', @audi_id), ('80', @audi_id),
('90', @audi_id), ('100', @audi_id), ('Allroad', @audi_id);

-- 5. SKODA
SET @skoda_id = (SELECT id FROM car_brands WHERE name = 'Skoda');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Fabia', @skoda_id), ('Octavia', @skoda_id), ('Superb', @skoda_id), ('Kamiq', @skoda_id),
('Karoq', @skoda_id), ('Kodiaq', @skoda_id), ('Scala', @skoda_id), ('Enyaq', @skoda_id),
('Yeti', @skoda_id), ('Citigo', @skoda_id), ('Roomster', @skoda_id), ('Rapid', @skoda_id),
('Felicia', @skoda_id), ('Favorit', @skoda_id);

-- 6. PORSCHE
SET @porsche_id = (SELECT id FROM car_brands WHERE name = 'Porsche');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('911', @porsche_id), ('Cayenne', @porsche_id), ('Macan', @porsche_id),
('Panamera', @porsche_id), ('Taycan', @porsche_id), ('Boxster', @porsche_id),
('Cayman', @porsche_id), ('718', @porsche_id), ('Carrera GT', @porsche_id),
('918 Spyder', @porsche_id), ('944', @porsche_id), ('928', @porsche_id);

-- ==========================================
-- GRUPO STELLANTIS (PSA + FCA)
-- ==========================================

-- 1. PEUGEOT
SET @peugeot_id = (SELECT id FROM car_brands WHERE name = 'Peugeot');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('208', @peugeot_id), ('308', @peugeot_id), ('2008', @peugeot_id), ('3008', @peugeot_id),
('5008', @peugeot_id), ('508', @peugeot_id), ('Rifter', @peugeot_id), ('Partner', @peugeot_id),
('Expert', @peugeot_id), ('Boxer', @peugeot_id), ('108', @peugeot_id), ('107', @peugeot_id),
('106', @peugeot_id), ('205', @peugeot_id), ('206', @peugeot_id), ('207', @peugeot_id),
('306', @peugeot_id), ('307', @peugeot_id), ('406', @peugeot_id), ('407', @peugeot_id),
('807', @peugeot_id), ('RCZ', @peugeot_id), ('Traveller', @peugeot_id), ('Bipper', @peugeot_id);
-- (Mejor usamos la sintaxis completa para asegurar que no falle en MySQL si hay concurrencia o NULLs previos)

-- 2. CITROËN
SET @citroen_id = (SELECT id FROM car_brands WHERE name = 'Citroën');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('C3', @citroen_id), ('C4', @citroen_id), ('C4 Cactus', @citroen_id), ('C5 Aircross', @citroen_id),
('C5 X', @citroen_id), ('Berlingo', @citroen_id), ('Jumpy', @citroen_id), ('Jumper', @citroen_id),
('Ami', @citroen_id), ('C1', @citroen_id), ('C2', @citroen_id), ('C3 Aircross', @citroen_id),
('Saxo', @citroen_id), ('Xsara', @citroen_id), ('Xsara Picasso', @citroen_id), ('C4 Picasso', @citroen_id),
('Grand C4 SpaceTourer', @citroen_id), ('Xantia', @citroen_id), ('C6', @citroen_id), ('C8', @citroen_id);

-- 3. DS AUTOMOBILES
SET @ds_id = (SELECT id FROM car_brands WHERE name = 'DS Automobiles');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('DS 3', @ds_id), ('DS 3 Crossback', @ds_id), ('DS 4', @ds_id), ('DS 7 Crossback', @ds_id), ('DS 9', @ds_id);

-- 4. OPEL
SET @opel_id = (SELECT id FROM car_brands WHERE name = 'Opel');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Corsa', @opel_id), ('Astra', @opel_id), ('Insignia', @opel_id), ('Mokka', @opel_id),
('Crossland', @opel_id), ('Grandland', @opel_id), ('Zafira', @opel_id), ('Meriva', @opel_id),
('Combo', @opel_id), ('Vivaro', @opel_id), ('Movano', @opel_id), ('Vectra', @opel_id),
('Omega', @opel_id), ('Tigra', @opel_id), ('Calibra', @opel_id), ('Adam', @opel_id),
('Karl', @opel_id), ('Antara', @opel_id), ('Frontera', @opel_id);

-- 5. FIAT
SET @fiat_id = (SELECT id FROM car_brands WHERE name = 'Fiat');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('500', @fiat_id), ('500X', @fiat_id), ('500L', @fiat_id), ('Panda', @fiat_id),
('Tipo', @fiat_id), ('Punto', @fiat_id), ('Grande Punto', @fiat_id), ('Bravo', @fiat_id),
('Brava', @fiat_id), ('Stilo', @fiat_id), ('Ducato', @fiat_id), ('Doblo', @fiat_id),
('Fiorino', @fiat_id), ('Scudo', @fiat_id), ('Talento', @fiat_id), ('Multipla', @fiat_id),
('Uno', @fiat_id), ('Seicento', @fiat_id), ('Croma', @fiat_id), ('Ulysse', @fiat_id), ('124 Spider', @fiat_id);

-- 6. ABARTH
SET @abarth_id = (SELECT id FROM car_brands WHERE name = 'Abarth');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('595', @abarth_id), ('695', @abarth_id), ('124 Spider', @abarth_id), ('Punto', @abarth_id);

-- 7. ALFA ROMEO
SET @alfa_id = (SELECT id FROM car_brands WHERE name = 'Alfa Romeo');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Giulia', @alfa_id), ('Stelvio', @alfa_id), ('Tonale', @alfa_id), ('Giulietta', @alfa_id),
('MiTo', @alfa_id), ('159', @alfa_id), ('156', @alfa_id), ('147', @alfa_id),
('GT', @alfa_id), ('Brera', @alfa_id), ('Spider', @alfa_id), ('4C', @alfa_id), ('8C Competizione', @alfa_id);

-- 8. LANCIA
SET @lancia_id = (SELECT id FROM car_brands WHERE name = 'Lancia');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Ypsilon', @lancia_id), ('Delta', @lancia_id), ('Thema', @lancia_id), ('Musa', @lancia_id),
('Voyager', @lancia_id), ('Phedra', @lancia_id), ('Lybra', @lancia_id);

-- 9. JEEP
SET @jeep_id = (SELECT id FROM car_brands WHERE name = 'Jeep');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Wrangler', @jeep_id), ('Cherokee', @jeep_id), ('Grand Cherokee', @jeep_id),
('Compass', @jeep_id), ('Renegade', @jeep_id), ('Avenger', @jeep_id), ('Gladiator', @jeep_id), ('Commander', @jeep_id);

-- 10. CHRYSLER
SET @chrysler_id = (SELECT id FROM car_brands WHERE name = 'Chrysler');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('300C', @chrysler_id), ('Voyager', @chrysler_id), ('Grand Voyager', @chrysler_id),
('Pacifica', @chrysler_id), ('PT Cruiser', @chrysler_id), ('Crossfire', @chrysler_id), ('Sebring', @chrysler_id);

-- 11. DODGE
SET @dodge_id = (SELECT id FROM car_brands WHERE name = 'Dodge');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Charger', @dodge_id), ('Challenger', @dodge_id), ('Durango', @dodge_id),
('Journey', @dodge_id), ('Viper', @dodge_id), ('Caliber', @dodge_id),
('Nitro', @dodge_id), ('Dart', @dodge_id), ('Avenger', @dodge_id);

-- 12. RAM
SET @ram_id = (SELECT id FROM car_brands WHERE name = 'RAM');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('1500', @ram_id), ('2500', @ram_id), ('3500', @ram_id), ('ProMaster', @ram_id);

-- 13. MASERATI
SET @maserati_id = (SELECT id FROM car_brands WHERE name = 'Maserati');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Ghibli', @maserati_id), ('Quattroporte', @maserati_id), ('Levante', @maserati_id),
('Grecale', @maserati_id), ('GranTurismo', @maserati_id), ('GranCabrio', @maserati_id), ('MC20', @maserati_id);

-- ==========================================
-- GRUPO RENAULT-NISSAN-MITSUBISHI
-- ==========================================

-- 1. RENAULT
SET @renault_id = (SELECT id FROM car_brands WHERE name = 'Renault');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Clio', @renault_id), ('Megane', @renault_id), ('Captur', @renault_id), ('Austral', @renault_id),
('Arkana', @renault_id), ('Zoe', @renault_id), ('Twingo', @renault_id), ('Kangoo', @renault_id),
('Trafic', @renault_id), ('Master', @renault_id), ('Laguna', @renault_id), ('Espace', @renault_id),
('Scenic', @renault_id), ('Grand Scenic', @renault_id), ('Kadjar', @renault_id), ('Koleos', @renault_id),
('Talisman', @renault_id), ('R5', @renault_id), ('R4', @renault_id), ('19', @renault_id),
('21', @renault_id), ('Express', @renault_id), ('Symbioz', @renault_id), ('Rafale', @renault_id);

-- 2. DACIA
SET @dacia_id = (SELECT id FROM car_brands WHERE name = 'Dacia');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Sandero', @dacia_id), ('Sandero Stepway', @dacia_id), ('Duster', @dacia_id), ('Jogger', @dacia_id),
('Logan', @dacia_id), ('Spring', @dacia_id), ('Lodgy', @dacia_id), ('Dokker', @dacia_id);

-- 3. ALPINE
SET @alpine_id = (SELECT id FROM car_brands WHERE name = 'Alpine');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('A110', @alpine_id), ('A290', @alpine_id), ('A310', @alpine_id);

-- 4. NISSAN
SET @nissan_id = (SELECT id FROM car_brands WHERE name = 'Nissan');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Qashqai', @nissan_id), ('Juke', @nissan_id), ('X-Trail', @nissan_id), ('Micra', @nissan_id),
('Leaf', @nissan_id), ('Ariya', @nissan_id), ('Navara', @nissan_id), ('GT-R', @nissan_id),
('370Z', @nissan_id), ('350Z', @nissan_id), ('Patrol', @nissan_id), ('Pathfinder', @nissan_id),
('Terrano', @nissan_id), ('Almera', @nissan_id), ('Primera', @nissan_id), ('Skyline', @nissan_id),
('Townstar', @nissan_id), ('Primastar', @nissan_id), ('Interstar', @nissan_id), ('Pulsar', @nissan_id);

-- 5. INFINITI
SET @infiniti_id = (SELECT id FROM car_brands WHERE name = 'Infiniti');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Q30', @infiniti_id), ('Q50', @infiniti_id), ('Q60', @infiniti_id), ('Q70', @infiniti_id),
('QX30', @infiniti_id), ('QX50', @infiniti_id), ('QX60', @infiniti_id), ('QX70', @infiniti_id), ('FX', @infiniti_id);

-- 6. DATSUN
SET @datsun_id = (SELECT id FROM car_brands WHERE name = 'Datsun');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('240Z', @datsun_id), ('260Z', @datsun_id), ('280Z', @datsun_id), ('Cherry', @datsun_id),
('Bluebird', @datsun_id), ('Go', @datsun_id), ('Go+', @datsun_id);

-- 7. MITSUBISHI
SET @mitsubishi_id = (SELECT id FROM car_brands WHERE name = 'Mitsubishi');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('ASX', @mitsubishi_id), ('Outlander', @mitsubishi_id), ('Eclipse Cross', @mitsubishi_id),
('Space Star', @mitsubishi_id), ('L200', @mitsubishi_id), ('Montero', @mitsubishi_id),
('Pajero', @mitsubishi_id), ('Lancer', @mitsubishi_id), ('Lancer Evolution', @mitsubishi_id),
('Colt', @mitsubishi_id), ('Galant', @mitsubishi_id), ('3000GT', @mitsubishi_id), ('Grandis', @mitsubishi_id);

-- 8. LADA
SET @lada_id = (SELECT id FROM car_brands WHERE name = 'Lada');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Niva', @lada_id), ('Kalina', @lada_id), ('Granta', @lada_id), ('Vesta', @lada_id),
('Priora', @lada_id), ('Samara', @lada_id), ('Riva', @lada_id);

-- ==========================================
-- GRUPO TOYOTA
-- ==========================================

-- 1. TOYOTA
SET @toyota_id = (SELECT id FROM car_brands WHERE name = 'Toyota');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Corolla', @toyota_id), ('Yaris', @toyota_id), ('Yaris Cross', @toyota_id), ('Auris', @toyota_id),
('Aygo', @toyota_id), ('Aygo X', @toyota_id), ('C-HR', @toyota_id), ('RAV4', @toyota_id),
('Prius', @toyota_id), ('Prius+', @toyota_id), ('Camry', @toyota_id), ('Avensis', @toyota_id),
('Verso', @toyota_id), ('Land Cruiser', @toyota_id), ('Hilux', @toyota_id), ('Proace', @toyota_id),
('Proace City', @toyota_id), ('Supra', @toyota_id), ('GR Supra', @toyota_id), ('Celica', @toyota_id),
('MR2', @toyota_id), ('GT86', @toyota_id), ('GR86', @toyota_id), ('bZ4X', @toyota_id),
('Highlander', @toyota_id), ('Starlet', @toyota_id), ('Carina', @toyota_id), ('Urban Cruiser', @toyota_id),
('IQ', @toyota_id), ('Mirai', @toyota_id);

-- 2. LEXUS
SET @lexus_id = (SELECT id FROM car_brands WHERE name = 'Lexus');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('CT', @lexus_id), ('IS', @lexus_id), ('ES', @lexus_id), ('GS', @lexus_id),
('LS', @lexus_id), ('UX', @lexus_id), ('NX', @lexus_id), ('RX', @lexus_id),
('RZ', @lexus_id), ('RC', @lexus_id), ('LC', @lexus_id), ('LFA', @lexus_id);

-- 3. DAIHATSU
SET @daihatsu_id = (SELECT id FROM car_brands WHERE name = 'Daihatsu');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Terios', @daihatsu_id), ('Sirion', @daihatsu_id), ('Cuore', @daihatsu_id),
('Charade', @daihatsu_id), ('Rocky', @daihatsu_id), ('Copen', @daihatsu_id),
('Materia', @daihatsu_id), ('Feroza', @daihatsu_id);

-- ==========================================
-- GRUPO HYUNDAI-KIA
-- ==========================================

-- 1. HYUNDAI
SET @hyundai_id = (SELECT id FROM car_brands WHERE name = 'Hyundai');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('i10', @hyundai_id), ('i20', @hyundai_id), ('i30', @hyundai_id), ('i40', @hyundai_id),
('Tucson', @hyundai_id), ('Santa Fe', @hyundai_id), ('Kona', @hyundai_id), ('Ioniq', @hyundai_id),
('Ioniq 5', @hyundai_id), ('Ioniq 6', @hyundai_id), ('Elantra', @hyundai_id), ('Sonata', @hyundai_id),
('Accent', @hyundai_id), ('Getz', @hyundai_id), ('Coupe', @hyundai_id), ('Veloster', @hyundai_id),
('Bayon', @hyundai_id), ('Nexo', @hyundai_id), ('Staria', @hyundai_id), ('H-1', @hyundai_id),
('Matrix', @hyundai_id), ('Terracan', @hyundai_id), ('Atos', @hyundai_id), ('Trajet', @hyundai_id),
('ix20', @hyundai_id), ('ix35', @hyundai_id), ('Galloper', @hyundai_id);

-- 2. KIA
SET @kia_id = (SELECT id FROM car_brands WHERE name = 'Kia');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Picanto', @kia_id), ('Rio', @kia_id), ('Ceed', @kia_id), ('ProCeed', @kia_id),
('XCeed', @kia_id), ('Sportage', @kia_id), ('Sorento', @kia_id), ('Niro', @kia_id),
('EV6', @kia_id), ('EV9', @kia_id), ('Stonic', @kia_id), ('Stinger', @kia_id),
('Optima', @kia_id), ('Carens', @kia_id), ('Carnival', @kia_id), ('Venga', @kia_id),
('Soul', @kia_id), ('Cerato', @kia_id), ('Sephia', @kia_id), ('Shuma', @kia_id),
('Opirus', @kia_id), ('Pride', @kia_id), ('Magentis', @kia_id);

-- 3. GENESIS (La marca premium de Hyundai)
SET @genesis_id = (SELECT id FROM car_brands WHERE name = 'Genesis');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('G70', @genesis_id), ('G80', @genesis_id), ('G90', @genesis_id),
('GV60', @genesis_id), ('GV70', @genesis_id), ('GV80', @genesis_id);

-- ==========================================
-- GRUPO FORD
-- ==========================================

-- 1. FORD
SET @ford_id = (SELECT id FROM car_brands WHERE name = 'Ford');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Fiesta', @ford_id), ('Focus', @ford_id), ('Mondeo', @ford_id), ('Mustang', @ford_id),
('Puma', @ford_id), ('Kuga', @ford_id), ('EcoSport', @ford_id), ('Edge', @ford_id),
('Explorer', @ford_id), ('Bronco', @ford_id), ('Mustang Mach-E', @ford_id), ('Ka', @ford_id),
('Ka+', @ford_id), ('B-Max', @ford_id), ('C-Max', @ford_id), ('S-Max', @ford_id),
('Galaxy', @ford_id), ('Tourneo Connect', @ford_id), ('Tourneo Custom', @ford_id), ('Tourneo Courier', @ford_id),
('Transit', @ford_id), ('Transit Connect', @ford_id), ('Transit Custom', @ford_id), ('Transit Courier', @ford_id),
('Ranger', @ford_id), ('F-150', @ford_id), ('Escort', @ford_id), ('Sierra', @ford_id),
('Scorpio', @ford_id), ('Orion', @ford_id), ('Capri', @ford_id), ('Cougar', @ford_id),
('Probe', @ford_id), ('Taunus', @ford_id), ('Maverick', @ford_id), ('Fusion', @ford_id);

-- 2. LINCOLN (La marca de lujo de Ford en América)
SET @lincoln_id = (SELECT id FROM car_brands WHERE name = 'Lincoln');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Navigator', @lincoln_id), ('Aviator', @lincoln_id), ('Corsair', @lincoln_id), ('Nautilus', @lincoln_id),
('Continental', @lincoln_id), ('Town Car', @lincoln_id), ('MKZ', @lincoln_id), ('MKX', @lincoln_id),
('MKS', @lincoln_id), ('MKT', @lincoln_id);

-- ==========================================
-- GENERAL MOTORS
-- ==========================================

-- 1. CHEVROLET
SET @chevrolet_id = (SELECT id FROM car_brands WHERE name = 'Chevrolet');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Aveo', @chevrolet_id), ('Cruze', @chevrolet_id), ('Captiva', @chevrolet_id), ('Spark', @chevrolet_id),
('Matiz', @chevrolet_id), ('Kalos', @chevrolet_id), ('Epica', @chevrolet_id), ('Lacetti', @chevrolet_id),
('Nubira', @chevrolet_id), ('Camaro', @chevrolet_id), ('Corvette', @chevrolet_id), ('Malibu', @chevrolet_id),
('Tahoe', @chevrolet_id), ('Suburban', @chevrolet_id), ('Silverado', @chevrolet_id), ('Equinox', @chevrolet_id),
('Trax', @chevrolet_id), ('Blazer', @chevrolet_id), ('Orlando', @chevrolet_id), ('Tacuma', @chevrolet_id),
('Tahoe', @chevrolet_id);

-- 2. CADILLAC
SET @cadillac_id = (SELECT id FROM car_brands WHERE name = 'Cadillac');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Escalade', @cadillac_id), ('CTS', @cadillac_id), ('ATS', @cadillac_id), ('SRX', @cadillac_id),
('XT4', @cadillac_id), ('XT5', @cadillac_id), ('XT6', @cadillac_id), ('CT4', @cadillac_id),
('CT5', @cadillac_id), ('Seville', @cadillac_id), ('Eldorado', @cadillac_id), ('DeVille', @cadillac_id);

-- 3. BUICK
SET @buick_id = (SELECT id FROM car_brands WHERE name = 'Buick');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Enclave', @buick_id), ('Encore', @buick_id), ('Envision', @buick_id), ('Regal', @buick_id),
('LaCrosse', @buick_id), ('Excelle', @buick_id), ('Riviera', @buick_id);

-- 4. GMC
SET @gmc_id = (SELECT id FROM car_brands WHERE name = 'GMC');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Sierra', @gmc_id), ('Canyon', @gmc_id), ('Yukon', @gmc_id), ('Acadia', @gmc_id),
('Terrain', @gmc_id), ('Savana', @gmc_id), ('Vandura', @gmc_id);

-- 5. HOLDEN (Mercado Australiano / Extinta, pero hay que tenerla)
SET @holden_id = (SELECT id FROM car_brands WHERE name = 'Holden');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Commodore', @holden_id), ('Monaro', @holden_id), ('Caprice', @holden_id), ('Colorado', @holden_id),
('Barina', @holden_id), ('Ute', @holden_id);

-- 6. PONTIAC (Clásicos y extinta)
SET @pontiac_id = (SELECT id FROM car_brands WHERE name = 'Pontiac');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Firebird', @pontiac_id), ('Trans Am', @pontiac_id), ('GTO', @pontiac_id), ('Grand Prix', @pontiac_id),
('Grand Am', @pontiac_id), ('Solstice', @pontiac_id), ('Aztek', @pontiac_id), ('Bonneville', @pontiac_id);

-- 7. HUMMER
SET @hummer_id = (SELECT id FROM car_brands WHERE name = 'Hummer');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('H1', @hummer_id), ('H2', @hummer_id), ('H3', @hummer_id), ('EV', @hummer_id);

-- ==========================================
-- GRUPO BMW
-- ==========================================

-- 1. BMW
SET @bmw_id = (SELECT id FROM car_brands WHERE name = 'BMW');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Serie 1', @bmw_id), ('Serie 2', @bmw_id), ('Serie 3', @bmw_id), ('Serie 4', @bmw_id),
('Serie 5', @bmw_id), ('Serie 6', @bmw_id), ('Serie 7', @bmw_id), ('Serie 8', @bmw_id),
('X1', @bmw_id), ('X2', @bmw_id), ('X3', @bmw_id), ('X4', @bmw_id), ('X5', @bmw_id),
('X6', @bmw_id), ('X7', @bmw_id), ('XM', @bmw_id),
('Z1', @bmw_id), ('Z3', @bmw_id), ('Z4', @bmw_id), ('Z8', @bmw_id),
('i3', @bmw_id), ('i4', @bmw_id), ('i5', @bmw_id), ('i7', @bmw_id), ('i8', @bmw_id),
('iX', @bmw_id), ('iX1', @bmw_id), ('iX2', @bmw_id), ('iX3', @bmw_id),
('M2', @bmw_id), ('M3', @bmw_id), ('M4', @bmw_id), ('M5', @bmw_id), ('M6', @bmw_id), ('M8', @bmw_id);

-- 2. MINI
SET @mini_id = (SELECT id FROM car_brands WHERE name = 'Mini');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Cooper', @mini_id), ('One', @mini_id), ('Clubman', @mini_id), ('Countryman', @mini_id),
('Paceman', @mini_id), ('Cabrio', @mini_id), ('Coupé', @mini_id), ('Roadster', @mini_id),
('Aceman', @mini_id), ('John Cooper Works', @mini_id);

-- 3. ROLLS-ROYCE
SET @rolls_id = (SELECT id FROM car_brands WHERE name = 'Rolls-Royce');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Phantom', @rolls_id), ('Ghost', @rolls_id), ('Wraith', @rolls_id), ('Dawn', @rolls_id),
('Cullinan', @rolls_id), ('Spectre', @rolls_id), ('Silver Shadow', @rolls_id),
('Silver Spirit', @rolls_id), ('Silver Spur', @rolls_id), ('Corniche', @rolls_id);

-- ==========================================
-- GRUPO HONDA
-- ==========================================

-- 1. HONDA
SET @honda_id = (SELECT id FROM car_brands WHERE name = 'Honda');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Civic', @honda_id), ('Accord', @honda_id), ('CR-V', @honda_id), ('HR-V', @honda_id),
('Jazz', @honda_id), ('Fit', @honda_id), ('ZR-V', @honda_id), ('e', @honda_id),
('NSX', @honda_id), ('S2000', @honda_id), ('Prelude', @honda_id), ('Integra', @honda_id),
('CR-Z', @honda_id), ('Insight', @honda_id), ('Pilot', @honda_id), ('Odyssey', @honda_id),
('Ridgeline', @honda_id), ('City', @honda_id), ('Legend', @honda_id), ('Passport', @honda_id),
('FR-V', @honda_id), ('Logo', @honda_id), ('Stream', @honda_id), ('S660', @honda_id);

-- 2. ACURA
SET @acura_id = (SELECT id FROM car_brands WHERE name = 'Acura');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Integra', @acura_id), ('Legend', @acura_id), ('MDX', @acura_id), ('RDX', @acura_id),
('RSX', @acura_id), ('TL', @acura_id), ('TLX', @acura_id), ('TSX', @acura_id),
('RL', @acura_id), ('RLX', @acura_id), ('NSX', @acura_id), ('ZDX', @acura_id),
('ILX', @acura_id), ('CDX', @acura_id);

-- ==========================================
-- GRUPO TATA MOTORS (JLR)
-- ==========================================

-- 1. JAGUAR
SET @jaguar_id = (SELECT id FROM car_brands WHERE name = 'Jaguar');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('XE', @jaguar_id), ('XF', @jaguar_id), ('XJ', @jaguar_id), ('E-Pace', @jaguar_id),
('F-Pace', @jaguar_id), ('I-Pace', @jaguar_id), ('F-Type', @jaguar_id), ('S-Type', @jaguar_id),
('X-Type', @jaguar_id), ('XK', @jaguar_id), ('XJS', @jaguar_id), ('E-Type', @jaguar_id);

-- 2. LAND ROVER
SET @landrover_id = (SELECT id FROM car_brands WHERE name = 'Land Rover');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Defender', @landrover_id), ('Discovery', @landrover_id), ('Discovery Sport', @landrover_id),
('Range Rover', @landrover_id), ('Range Rover Sport', @landrover_id), ('Range Rover Velar', @landrover_id),
('Range Rover Evoque', @landrover_id), ('Freelander', @landrover_id);

-- 3. TATA
SET @tata_id = (SELECT id FROM car_brands WHERE name = 'Tata');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Safari', @tata_id), ('Nexon', @tata_id), ('Harrier', @tata_id), ('Tiago', @tata_id),
('Tigor', @tata_id), ('Altroz', @tata_id), ('Punch', @tata_id), ('Nano', @tata_id),
('Indica', @tata_id), ('Indigo', @tata_id), ('Aria', @tata_id), ('Sierra', @tata_id);

-- ==========================================
-- GRUPO GEELY / VOLVO
-- ==========================================

-- 1. VOLVO
SET @volvo_id = (SELECT id FROM car_brands WHERE name = 'Volvo');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('XC90', @volvo_id), ('XC60', @volvo_id), ('XC40', @volvo_id), ('EX90', @volvo_id),
('EX30', @volvo_id), ('C40', @volvo_id), ('S90', @volvo_id), ('S60', @volvo_id),
('V90', @volvo_id), ('V60', @volvo_id), ('V40', @volvo_id), ('V50', @volvo_id),
('C30', @volvo_id), ('S80', @volvo_id), ('S40', @volvo_id), ('850', @volvo_id),
('740', @volvo_id), ('940', @volvo_id), ('240', @volvo_id);

-- 2. POLESTAR
SET @polestar_id = (SELECT id FROM car_brands WHERE name = 'Polestar');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('1', @polestar_id), ('2', @polestar_id), ('3', @polestar_id), ('4', @polestar_id),
('5', @polestar_id), ('6', @polestar_id);

-- 3. GEELY
SET @geely_id = (SELECT id FROM car_brands WHERE name = 'Geely');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Coolray', @geely_id), ('Azkarra', @geely_id), ('Tugella', @geely_id), ('Monjaro', @geely_id),
('Emgrand', @geely_id), ('Okavango', @geely_id), ('Atlas', @geely_id), ('Boyue', @geely_id),
('Geometry C', @geely_id), ('CK', @geely_id), ('MK', @geely_id), ('Panda', @geely_id);

-- 4. LOTUS
SET @lotus_id = (SELECT id FROM car_brands WHERE name = 'Lotus');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Emira', @lotus_id), ('Eletre', @lotus_id), ('Emeya', @lotus_id), ('Evija', @lotus_id),
('Elise', @lotus_id), ('Exige', @lotus_id), ('Evora', @lotus_id), ('Esprit', @lotus_id),
('Europa', @lotus_id), ('Elan', @lotus_id);

-- 5. SMART
SET @smart_id = (SELECT id FROM car_brands WHERE name = 'Smart');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Fortwo', @smart_id), ('Forfour', @smart_id), ('Roadster', @smart_id), ('Crossblade', @smart_id),
('#1', @smart_id), ('#3', @smart_id), ('#5', @smart_id);

-- 6. ZEEKR
SET @zeekr_id = (SELECT id FROM car_brands WHERE name = 'Zeekr');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('001', @zeekr_id), ('009', @zeekr_id), ('X', @zeekr_id), ('007', @zeekr_id), ('MIX', @zeekr_id);

-- 7. LYNK & CO
SET @lynk_id = (SELECT id FROM car_brands WHERE name = 'Lynk & Co');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('01', @lynk_id), ('02', @lynk_id), ('03', @lynk_id), ('04', @lynk_id),
('05', @lynk_id), ('06', @lynk_id), ('07', @lynk_id), ('08', @lynk_id), ('09', @lynk_id);

-- ==========================================
-- OTROS ASIÁTICOS INDEPENDIENTES Y GIGANTES CHINOS
-- ==========================================

-- 1. MAZDA
SET @mazda_id = (SELECT id FROM car_brands WHERE name = 'Mazda');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Mazda2', @mazda_id), ('Mazda3', @mazda_id), ('Mazda6', @mazda_id), ('CX-3', @mazda_id),
('CX-30', @mazda_id), ('CX-5', @mazda_id), ('CX-60', @mazda_id), ('CX-80', @mazda_id),
('MX-5', @mazda_id), ('MX-30', @mazda_id), ('RX-7', @mazda_id), ('RX-8', @mazda_id),
('Tribute', @mazda_id), ('Premacy', @mazda_id), ('Demio', @mazda_id);

-- 2. SUBARU
SET @subaru_id = (SELECT id FROM car_brands WHERE name = 'Subaru');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Impreza', @subaru_id), ('Forester', @subaru_id), ('Outback', @subaru_id), ('Crosstrek', @subaru_id),
('XV', @subaru_id), ('BRZ', @subaru_id), ('WRX STI', @subaru_id), ('Legacy', @subaru_id),
('Levorg', @subaru_id), ('Solterra', @subaru_id), ('Justy', @subaru_id);

-- 3. SUZUKI
SET @suzuki_id = (SELECT id FROM car_brands WHERE name = 'Suzuki');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Swift', @suzuki_id), ('Vitara', @suzuki_id), ('Grand Vitara', @suzuki_id), ('Jimny', @suzuki_id),
('Ignis', @suzuki_id), ('S-Cross', @suzuki_id), ('Baleno', @suzuki_id), ('Alto', @suzuki_id),
('Celerio', @suzuki_id), ('Splash', @suzuki_id), ('Kizashi', @suzuki_id), ('Samurai', @suzuki_id);

-- 4. SSANGYONG
SET @ssangyong_id = (SELECT id FROM car_brands WHERE name = 'SsangYong');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Rexton', @ssangyong_id), ('Korando', @ssangyong_id), ('Tivoli', @ssangyong_id), ('Musso', @ssangyong_id),
('Rodius', @ssangyong_id), ('Kyron', @ssangyong_id), ('Actyon', @ssangyong_id);

-- 5. KGM (Nueva denominación de SsangYong)
SET @kgm_id = (SELECT id FROM car_brands WHERE name = 'KGM');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Torres', @kgm_id), ('Tivoli', @kgm_id), ('Korando', @kgm_id), ('Rexton', @kgm_id), ('Musso', @kgm_id);

-- 6. ISUZU
SET @isuzu_id = (SELECT id FROM car_brands WHERE name = 'Isuzu');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('D-Max', @isuzu_id), ('Trooper', @isuzu_id), ('Rodeo', @isuzu_id), ('MU-X', @isuzu_id);

-- 7. MAHINDRA
SET @mahindra_id = (SELECT id FROM car_brands WHERE name = 'Mahindra');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Thar', @mahindra_id), ('Scorpio', @mahindra_id), ('XUV700', @mahindra_id), ('XUV500', @mahindra_id),
('Bolero', @mahindra_id), ('KUV100', @mahindra_id), ('Goa', @mahindra_id);

-- 8. MARUTI
SET @maruti_id = (SELECT id FROM car_brands WHERE name = 'Maruti');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('800', @maruti_id), ('Zen', @maruti_id), ('Dzire', @maruti_id), ('Ertiga', @maruti_id), ('Brezza', @maruti_id);

-- 9. BYD
SET @byd_id = (SELECT id FROM car_brands WHERE name = 'BYD');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Atto 3', @byd_id), ('Dolphin', @byd_id), ('Seal', @byd_id), ('Tang', @byd_id),
('Han', @byd_id), ('Seagull', @byd_id), ('Song Plus', @byd_id), ('Seal U', @byd_id);

-- 10. CHERY
SET @chery_id = (SELECT id FROM car_brands WHERE name = 'Chery');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Tiggo 3', @chery_id), ('Tiggo 7', @chery_id), ('Tiggo 8', @chery_id), ('QQ', @chery_id), ('Arrizo 5', @chery_id);

-- 11. MG
SET @mg_id = (SELECT id FROM car_brands WHERE name = 'MG');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('ZS', @mg_id), ('HS', @mg_id), ('MG4', @mg_id), ('MG5', @mg_id), ('Marvel R', @mg_id),
('Cyberster', @mg_id), ('TF', @mg_id), ('ZR', @mg_id), ('ZT', @mg_id), ('MGF', @mg_id);

-- 12. OMODA
SET @omoda_id = (SELECT id FROM car_brands WHERE name = 'Omoda');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('5', @omoda_id), ('E5', @omoda_id), ('7', @omoda_id);

-- 13. DONGFENG
SET @dongfeng_id = (SELECT id FROM car_brands WHERE name = 'Dongfeng');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('580', @dongfeng_id), ('Fengon 5', @dongfeng_id), ('ix5', @dongfeng_id), ('Huge', @dongfeng_id), ('Mage', @dongfeng_id);

-- 14. HAVAL
SET @haval_id = (SELECT id FROM car_brands WHERE name = 'Haval');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('H6', @haval_id), ('Jolion', @haval_id), ('Big Dog', @haval_id), ('H9', @haval_id);

-- 15. BAOJUN
SET @baojun_id = (SELECT id FROM car_brands WHERE name = 'Baojun');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('510', @baojun_id), ('730', @baojun_id), ('Yep', @baojun_id), ('KiWi EV', @baojun_id);

-- ==========================================
-- ELÉCTRICOS, NUEVOS Y SUPERDEPORTIVOS
-- ==========================================

-- 1. TESLA
SET @tesla_id = (SELECT id FROM car_brands WHERE name = 'Tesla');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Model S', @tesla_id), ('Model 3', @tesla_id), ('Model X', @tesla_id),
('Model Y', @tesla_id), ('Cybertruck', @tesla_id), ('Roadster', @tesla_id);

-- 2. RIVIAN
SET @rivian_id = (SELECT id FROM car_brands WHERE name = 'Rivian');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('R1T', @rivian_id), ('R1S', @rivian_id);

-- 3. LUCID
SET @lucid_id = (SELECT id FROM car_brands WHERE name = 'Lucid');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Air', @lucid_id), ('Gravity', @lucid_id);

-- 4. FISKER
SET @fisker_id = (SELECT id FROM car_brands WHERE name = 'Fisker');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Ocean', @fisker_id), ('Karma', @fisker_id), ('Pear', @fisker_id);

-- 5. FARADAY FUTURE
SET @faraday_id = (SELECT id FROM car_brands WHERE name = 'Faraday Future');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('FF 91', @faraday_id);

-- 6. NIO
SET @nio_id = (SELECT id FROM car_brands WHERE name = 'NIO');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('ES8', @nio_id), ('ES6', @nio_id), ('EC6', @nio_id), ('ET7', @nio_id),
('ET5', @nio_id), ('EL7', @nio_id), ('EP9', @nio_id);

-- 7. XPENG
SET @xpeng_id = (SELECT id FROM car_brands WHERE name = 'Xpeng');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('P7', @xpeng_id), ('G3', @xpeng_id), ('P5', @xpeng_id), ('G9', @xpeng_id), ('G6', @xpeng_id);

-- 8. FERRARI
SET @ferrari_id = (SELECT id FROM car_brands WHERE name = 'Ferrari');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('458 Italia', @ferrari_id), ('488 GTB', @ferrari_id), ('F8 Tributo', @ferrari_id),
('812 Superfast', @ferrari_id), ('Roma', @ferrari_id), ('Portofino', @ferrari_id),
('SF90 Stradale', @ferrari_id), ('Purosangue', @ferrari_id), ('LaFerrari', @ferrari_id),
('Enzo', @ferrari_id), ('F40', @ferrari_id), ('F50', @ferrari_id),
('Testarossa', @ferrari_id), ('360 Modena', @ferrari_id), ('California', @ferrari_id);

-- 9. ASTON MARTIN
SET @aston_id = (SELECT id FROM car_brands WHERE name = 'Aston Martin');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('DB11', @aston_id), ('DB12', @aston_id), ('Vantage', @aston_id),
('DBS Superleggera', @aston_id), ('DBX', @aston_id), ('Valkyrie', @aston_id),
('Valhalla', @aston_id), ('DB9', @aston_id), ('Vanquish', @aston_id), ('Rapide', @aston_id);

-- 10. MCLAREN
SET @mclaren_id = (SELECT id FROM car_brands WHERE name = 'McLaren');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('720S', @mclaren_id), ('750S', @mclaren_id), ('Artura', @mclaren_id), ('GT', @mclaren_id),
('P1', @mclaren_id), ('Senna', @mclaren_id), ('570S', @mclaren_id), ('650S', @mclaren_id), ('F1', @mclaren_id);

-- 11. PAGANI
SET @pagani_id = (SELECT id FROM car_brands WHERE name = 'Pagani');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Zonda', @pagani_id), ('Huayra', @pagani_id), ('Utopia', @pagani_id);

-- 12. KOENIGSEGG
SET @koenigsegg_id = (SELECT id FROM car_brands WHERE name = 'Koenigsegg');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Agera', @koenigsegg_id), ('Regera', @koenigsegg_id), ('Jesko', @koenigsegg_id),
('Gemera', @koenigsegg_id), ('CCX', @koenigsegg_id);

-- ==========================================
-- MARCAS CLÁSICAS Y EXTINTAS
-- ==========================================

-- 1. SAAB
SET @saab_id = (SELECT id FROM car_brands WHERE name = 'Saab');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('900', @saab_id), ('9000', @saab_id), ('9-3', @saab_id), ('9-5', @saab_id),
('Sonett', @saab_id), ('96', @saab_id), ('99', @saab_id);

-- 2. ROVER
SET @rover_id = (SELECT id FROM car_brands WHERE name = 'Rover');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('25', @rover_id), ('45', @rover_id), ('75', @rover_id), ('200', @rover_id),
('400', @rover_id), ('600', @rover_id), ('800', @rover_id), ('Streetwise', @rover_id),
('CityRover', @rover_id), ('Metro', @rover_id), ('Montego', @rover_id), ('Maestro', @rover_id);

-- 3. DAEWOO
SET @daewoo_id = (SELECT id FROM car_brands WHERE name = 'Daewoo');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Lanos', @daewoo_id), ('Matiz', @daewoo_id), ('Nubira', @daewoo_id), ('Leganza', @daewoo_id),
('Kalos', @daewoo_id), ('Tacuma', @daewoo_id), ('Nexia', @daewoo_id), ('Espero', @daewoo_id),
('Tico', @daewoo_id), ('Evanda', @daewoo_id), ('Arcadia', @daewoo_id);

-- 4. MG ROVER
SET @mgrover_id = (SELECT id FROM car_brands WHERE name = 'MG Rover');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('ZR', @mgrover_id), ('ZS', @mgrover_id), ('ZT', @mgrover_id), ('ZT-T', @mgrover_id),
('TF', @mgrover_id), ('MGF', @mgrover_id);

-- 5. TALBOT
SET @talbot_id = (SELECT id FROM car_brands WHERE name = 'Talbot');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Horizon', @talbot_id), ('Solara', @talbot_id), ('Samba', @talbot_id),
('Alpine', @talbot_id), ('Tagora', @talbot_id), ('150', @talbot_id), ('Matra Murena', @talbot_id);

-- 6. SANTANA
SET @santana_id = (SELECT id FROM car_brands WHERE name = 'Santana');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Aníbal', @santana_id), ('300', @santana_id), ('350', @santana_id),
('PS-10', @santana_id), ('Motor 2.5', @santana_id);

-- 7. GALLOPER
SET @galloper_id = (SELECT id FROM car_brands WHERE name = 'Galloper');
INSERT IGNORE INTO car_models (name, brand_id) VALUES
('Exceed', @galloper_id), ('Super Exceed', @galloper_id), ('Innovation', @galloper_id), ('Santamo', @galloper_id);