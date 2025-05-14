DELIMITER //

CREATE TRIGGER after_doctor_details_insert
AFTER INSERT ON doctor_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('New doctor added: ID = ', NEW.id), NOW(), 'Doctor', NEW.id);
END//

DELIMITER ;

DROP TRIGGER IF EXISTS after_doctor_details_insert;


DELIMITER //

CREATE TRIGGER after_doctor_details_update
AFTER UPDATE ON doctor_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('Doctor updated: ID = ', NEW.id), NOW(), 'Doctor', NEW.id);
END//

DELIMITER ;


DELIMITER //

CREATE TRIGGER after_patient_details_insert
AFTER INSERT ON patient_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('New patient added: ID = ', NEW.id), NOW(), 'Patient', NEW.patient_register_id);
END//

DELIMITER ;



DELIMITER //

CREATE TRIGGER after_patient_details_update
AFTER UPDATE ON patient_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('Patient updated: ID = ', NEW.id), NOW(), 'Patient', NEW.patient_register_id);
END//

DELIMITER ;


DELIMITER //

CREATE TRIGGER after_doctor_details_delete
AFTER DELETE ON doctor_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('Doctor deleted: ID = ', OLD.id), NOW(), 'Doctor', OLD.id);
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_patient_details_delete
AFTER DELETE ON patient_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('Patient deleted: ID = ', OLD.id), NOW(), 'Patient', OLD.patient_register_id);
END//

DELIMITER ;


DELIMITER $$

CREATE TRIGGER after_booking_appointment_insert
AFTER INSERT ON booking_appointment
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (user_type, user_id, message, timestamp)
    VALUES ('Appointment', NEW.booking_id, CONCAT('New booking created with Booking ID: ', NEW.booking_id), NOW());
END $$

DELIMITER ;



DELIMITER $$

CREATE TRIGGER after_booking_appointment_update
AFTER UPDATE ON booking_appointment
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (user_type, user_id, message, timestamp)
    VALUES ('Appointment', NEW.booking_id, CONCAT('Booking ID ', NEW.booking_id, ' was updated'), NOW());
END $$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER after_booking_appointment_delete
AFTER DELETE ON booking_appointment
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (user_type, user_id, message, timestamp)
    VALUES ('Appointment', OLD.booking_id, CONCAT('Booking ID ', OLD.booking_id, ' was deleted'), NOW());
END $$

DELIMITER ;

-- 1. Insert a supplier record for homeopathic medicine supplies
INSERT INTO suppliers (
  created_at,
  updated_at,
  address,
  contact_details,
  email,
  name,
  created_by_id
)
VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  '123 Homeo Street, New Delhi, India',
  'Tel: +91-11-12345678',
  'contact@boironhomeo.com',
  'Boiron Homeopathic Supplies',
  1
);

-- 2. Insert 15 Homeopathic Medicine Records

-- 1. Arsenicum Album
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Used for treating food poisoning, gastrointestinal disturbances, and anxiety related to digestive upset.',
  '2027-12-31',
  'Boiron',
  'Arsenicum Album',
  50,
  'bottle',
  1,
  1,
  'Arsenicum Album',
  'None known',
  10.00,
  '30C',
  'Food poisoning, digestive disorders',
  '30C',
  'Approved',
  15.00,
  'Rarely any side effects',
  'Boiron',
  'Store in a cool, dry place',
  'Take 3 drops under the tongue 3 times a day',
  'https://m.media-amazon.com/images/I/51zXcMi0kSL.jpg'
);

-- 2. Belladonna
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Effective for reducing high fever, severe headache, and inflammation during acute illnesses.',
  '2028-06-30',
  'Boiron',
  'Belladonna',
  30,
  'box',
  1,
  1,
  'Belladonna',
  'Not for individuals with heart conditions',
  8.00,
  '200C',
  'Fever, headache, inflammation',
  '200C',
  'Approved',
  12.00,
  'May cause drowsiness',
  'Boiron',
  'Keep away from direct sunlight',
  'Take one tablet every 4 hours as needed',
  'https://m.media-amazon.com/images/I/419BgkzaPaL.AC_UF1000,1000_QL80.jpg'
);

-- 3. Nux Vomica
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Used for digestive disorders, hangover symptoms, and stress-induced stomach upset.',
  '2027-09-15',
  'Boiron',
  'Nux Vomica',
  40,
  'bottle',
  1,
  1,
  'Nux Vomica',
  'Not recommended for pregnant women',
  9.50,
  '30C',
  'Digestive upset, hangovers, stress',
  '30C',
  'Approved',
  14.00,
  'Minimal side effects',
  'Boiron',
  'Store in a cool, dark place',
  'Take 2 drops three times a day before meals',
  'https://m.media-amazon.com/images/I/31H-LrvtiXL.AC_UF1000,1000_QL80.jpg'
);

-- 4. Rhus Toxicodendron
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Helps relieve musculoskeletal pain, joint stiffness, and symptoms of arthritis.',
  '2028-03-31',
  'Boiron',
  'Rhus Toxicodendron',
  35,
  'box',
  1,
  1,
  'Rhus Toxicodendron',
  'Avoid if allergic to poison ivy',
  11.00,
  '30C',
  'Arthritis, joint pain, muscle stiffness',
  '30C',
  'Approved',
  16.00,
  'May cause mild skin irritation',
  'Boiron',
  'Keep in a dry place away from heat',
  'Apply 3 times daily as per dosage guidelines',
  'https://m.media-amazon.com/images/I/51a5WPuQg9L.jpg'
);

-- 5. Gelsemium
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Commonly used in the early stages of flu, weakness, and anticipatory anxiety.',
  '2027-11-30',
  'Boiron',
  'Gelsemium',
  25,
  'bottle',
  1,
  1,
  'Gelsemium',
  'Not for individuals with extreme sensitivity',
  10.50,
  '30C',
  'Flu, weakness, anxiety',
  '30C',
  'Approved',
  15.50,
  'May cause mild sedation',
  'Boiron',
  'Store in a cool, dry place',
  'Take 3 drops every 2 hours during early symptoms',
  'https://m.media-amazon.com/images/I/61ljAjvntBL.jpg'
);

-- 6. Ignatia Amara
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Beneficial for managing emotional distress, grief, and mood swings.',
  '2028-01-31',
  'Boiron',
  'Ignatia Amara',
  30,
  'box',
  1,
  1,
  'Ignatia Amara',
  'Avoid if severe depression is present',
  9.00,
  '30C',
  'Emotional distress, grief, mood swings',
  '30C',
  'Approved',
  13.00,
  'May cause slight drowsiness',
  'Boiron',
  'Keep in a cool, dry environment',
  'Take one dose twice daily as needed',
  'https://m.media-amazon.com/images/I/31EbaxFABaL.AC_UF1000,1000_QL80.jpg'
);

-- 7. Pulsatilla
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Often used to address colds, coughs, and mood swings in sensitive individuals.',
  '2027-10-15',
  'Boiron',
  'Pulsatilla',
  30,
  'bottle',
  1,
  1,
  'Pulsatilla',
  'Not recommended for individuals with frequent headaches',
  8.50,
  '30C',
  'Cough, cold, mood swings',
  '30C',
  'Approved',
  12.50,
  'May cause mild drowsiness',
  'Boiron',
  'Store in a cool, dry area',
  'Take 2 drops 3 times daily',
  'https://m.media-amazon.com/images/I/619vpxtPhPL.jpg'
);

-- 8. Natrum Muriaticum
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Helps with headaches, migraines, and emotional stress often accompanied by grief.',
  '2027-08-31',
  'Boiron',
  'Natrum Muriaticum',
  40,
  'box',
  1,
  1,
  'Natrum Muriaticum',
  'Avoid if there is a history of severe dehydration',
  9.75,
  '30C',
  'Headaches, migraines, emotional stress',
  '30C',
  'Approved',
  14.25,
  'May cause mild nausea',
  'Boiron',
  'Store in a cool, dry place',
  'Take 2 drops under the tongue every 4 hours',
  'https://m.media-amazon.com/images/I/61FVkVoATtL.jpg'
);

-- 9. Sulphur
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Used for various skin conditions and to alleviate chronic digestive issues.',
  '2028-02-28',
  'Boiron',
  'Sulphur',
  30,
  'bottle',
  1,
  1,
  'Sulphur',
  'Not for individuals with severe dermatitis',
  10.25,
  '30C',
  'Skin issues, digestive problems',
  '30C',
  'Approved',
  15.75,
  'May cause minor irritation',
  'Boiron',
  'Store in a cool, dry place',
  'Take 3 drops 3 times daily',
  'https://m.media-amazon.com/images/I/71d2i0Aei4L.jpg'
);

-- 10. Calcarea Carbonica
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'A remedy for chronic fatigue, digestive disorders, and developmental issues in children.',
  '2027-07-31',
  'Boiron',
  'Calcarea Carbonica',
  35,
  'box',
  1,
  1,
  'Calcarea Carbonica',
  'Use cautiously in individuals with thyroid disorders',
  11.50,
  '30C',
  'Fatigue, digestive issues, developmental concerns',
  '30C',
  'Approved',
  16.50,
  'May cause slight constipation',
  'Boiron',
  'Keep in a cool, dry place',
  'Take 2 tablets twice daily with meals',
  'https://m.media-amazon.com/images/I/61tXlSyNQ6L.jpg'
);

-- 11. Lycopodium
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Helps in treating digestive issues, bloating, and abdominal discomfort.',
  '2028-05-31',
  'Boiron',
  'Lycopodium',
  40,
  'bottle',
  1,
  1,
  'Lycopodium',
  'Not recommended for individuals with severe liver issues',
  10.00,
  '30C',
  'Digestive discomfort, bloating, gas',
  '30C',
  'Approved',
  15.00,
  'May cause minor gastrointestinal upset',
  'Boiron',
  'Store in a cool, dry place',
  'Take 3 drops before meals',
  'https://onemg.gumlet.io/l_watermark_346,w_690,h_700/a_ignore,w_690,h_700,c_pad,q_auto,f_auto/cropped/wsgckxbkzjypvmfgvbnn.jpg'
);

-- 12. Aconite
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Used for acute conditions, shock, and to relieve fever and anxiety from sudden illness.',
  '2027-06-30',
  'Boiron',
  'Aconite',
  30,
  'box',
  1,
  1,
  'Aconite',
  'Not recommended for those with chronic heart conditions',
  9.00,
  '30C',
  'Fever, shock, acute anxiety',
  '30C',
  'Approved',
  13.00,
  'May cause slight dizziness',
  'Boiron',
  'Store in a cool, dry place',
  'Take 2 drops immediately at the onset of symptoms',
  'https://m.media-amazon.com/images/I/71g4q3Z41OL.jpg'
);

-- 13. Ruta Graveolens
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Often used for treating rheumatism, tendonitis, and muscle strains.',
  '2028-04-30',
  'Boiron',
  'Ruta Graveolens',
  35,
  'bottle',
  1,
  1,
  'Ruta Graveolens',
  'Not for individuals with sensitive skin',
  10.75,
  '30C',
  'Rheumatism, tendonitis, muscle strains',
  '30C',
  'Approved',
  14.75,
  'May cause mild skin irritation',
  'Boiron',
  'Store in a cool, dry place',
  'Take 3 drops three times daily with water',
  'https://m.media-amazon.com/images/I/71pM1ECprdL.jpg'
);

-- 14. Bryonia
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Used to treat joint pain, headaches, and respiratory issues, especially when symptoms worsen with movement.',
  '2028-01-31',
  'Boiron',
  'Bryonia',
  30,
  'box',
  1,
  1,
  'Bryonia',
  'Not for individuals with heart conditions',
  9.50,
  '30C',
  'Joint pain, headaches, respiratory issues',
  '30C',
  'Approved',
  13.50,
  'May cause dryness of mouth',
  'Boiron',
  'Store in a cool, dry place',
  'Take 2 tablets every 4-6 hours as needed',
  'https://m.media-amazon.com/images/I/51UmP5PrzjL.jpg'
);

-- 15. Chamomilla
INSERT INTO inventory_items (
  created_at,
  updated_at,
  description,
  expiry_date,
  manufacturer,
  name,
  reorder_level,
  unit,
  category_id,
  created_by_id,
  common_name,
  contraindications,
  cost_price,
  formulation,
  indications,
  potency,
  regulatory_status,
  selling_price,
  side_effects,
  source,
  storage_conditions,
  usage_instructions,
  image_url
) VALUES (
  '2025-04-16 00:00:00',
  '2025-04-16 00:00:00',
  'Commonly used for teething problems in infants, colic, and irritability.',
  '2028-05-31',
  'Boiron',
  'Chamomilla',
  25,
  'bottle',
  1,
  1,
  'Chamomilla',
  'Not recommended for children under 2 years without supervision',
  8.25,
  '30C',
  'Teething, colic, irritability',
  '30C',
  'Approved',
  12.25,
  'May cause mild sedation',
  'Boiron',
  'Keep in a cool, dry place',
  'Take 2 drops 3 times daily as needed',
  'https://m.media-amazon.com/images/I/31SHPypEvsL.AC_UF1000,1000_QL80.jpg'
);

-- 3. Insert Inventory Records for Each Medicine in Warehouse (warehouse_id = 1)
INSERT INTO inventory_records (
  created_at,
  updated_at,
  quantity,
  inventory_item_id,
  warehouse_id
)
VALUES
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 100, 1, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 90, 2, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 120, 3, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 80, 4, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 110, 5, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 85, 6, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 95, 7, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 105, 8, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 115, 9, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 90, 10, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 100, 11, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 80, 12, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 95, 13, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 85, 14, 1),
  ('2025-04-16 00:00:00', '2025-04-16 00:00:00', 100, 15, 1);