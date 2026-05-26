INSERT INTO rooms (room_number, price_per_day, status, created_at)
VALUES
    ('101', 3500.00, 'AVAILABLE', NOW()),
    ('102', 3500.00, 'AVAILABLE', NOW()),
    ('201', 5000.00, 'AVAILABLE', NOW()),
    ('202', 5000.00, 'AVAILABLE', NOW()),
    ('301', 8000.00, 'AVAILABLE', NOW())
    ON CONFLICT (room_number) DO NOTHING;