CREATE TABLE hotels (
                        id SERIAL PRIMARY KEY, -- Unique ID for each hotel
                        name VARCHAR(100) NOT NULL, -- Name of the hotel
                        city VARCHAR(100) NOT NULL, -- City where the hotel is located
                        address VARCHAR(255) NOT NULL, -- Full address of the hotel
                        cost_per_night NUMERIC(10, 2) NOT NULL, -- Cost per night
                        rating NUMERIC(3, 2) CHECK (rating >= 0 AND rating <= 5), -- Rating (0 to 5)
                        amenities TEXT[], -- Array of amenities (e.g., {"WiFi", "Pool", "Gym"})
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp of when the hotel was added
);


INSERT INTO hotels (name, city, address, cost_per_night, rating, amenities)
VALUES
    ('Grand Hotel Rome', 'Rome', '123 Via del Corso, Rome, Italy', 150.00, 4.5, '{"WiFi", "Pool", "Gym"}'),
    ('Luxury Stay Milan', 'Milan', '456 Via Montenapoleone, Milan, Italy', 200.00, 4.7, '{"WiFi", "Spa", "Restaurant"}'),
    ('Paris Elegance', 'Paris', '789 Champs-Élysées, Paris, France', 250.00, 4.8, '{"WiFi", "Pool", "Bar"}'),
    ('New York Central', 'New York', '101 Broadway, New York, USA', 300.00, 4.6, '{"WiFi", "Gym", "Conference Room"}'),
    ('Tokyo Tower View', 'Tokyo', '202 Shibuya Crossing, Tokyo, Japan', 180.00, 4.4, '{"WiFi", "Restaurant", "Spa"}'),
    ('London Royal', 'London', '303 Oxford Street, London, UK', 220.00, 4.5, '{"WiFi", "Bar", "Gym"}'),
    ('Dubai Oasis', 'Dubai', '404 Sheikh Zayed Road, Dubai, UAE', 350.00, 4.9, '{"WiFi", "Pool", "Spa"}'),
    ('Sydney Harbor View', 'Sydney', '505 Circular Quay, Sydney, Australia', 280.00, 4.7, '{"WiFi", "Pool", "Restaurant"}'),
    ('Rome Palace', 'Rome', '234 Piazza Navona, Rome, Italy', 160.00, 4.3, '{"WiFi", "Restaurant", "Bar"}'),
    ('Milan Luxury', 'Milan', '567 Via della Spiga, Milan, Italy', 210.00, 4.6, '{"WiFi", "Spa", "Gym"}'),
    ('Paris Charm', 'Paris', '890 Rue de Rivoli, Paris, France', 240.00, 4.7, '{"WiFi", "Pool", "Restaurant"}'),
    ('New York Skyline', 'New York', '202 5th Avenue, New York, USA', 310.00, 4.8, '{"WiFi", "Gym", "Bar"}'),
    ('Tokyo Central', 'Tokyo', '303 Ginza, Tokyo, Japan', 190.00, 4.5, '{"WiFi", "Spa", "Restaurant"}'),
    ('London Bridge Hotel', 'London', '404 London Bridge, London, UK', 230.00, 4.6, '{"WiFi", "Pool", "Gym"}'),
    ('Dubai Marina', 'Dubai', '505 Marina Walk, Dubai, UAE', 360.00, 4.9, '{"WiFi", "Spa", "Pool"}'),
    ('Sydney Beachfront', 'Sydney', '606 Bondi Beach, Sydney, Australia', 290.00, 4.7, '{"WiFi", "Pool", "Restaurant"}'),
    ('Rome Vista', 'Rome', '345 Via Veneto, Rome, Italy', 170.00, 4.4, '{"WiFi", "Bar", "Gym"}'),
    ('Milan Boutique', 'Milan', '678 Via Manzoni, Milan, Italy', 220.00, 4.7, '{"WiFi", "Spa", "Restaurant"}'),
    ('Paris Bliss', 'Paris', '901 Rue de la Paix, Paris, France', 260.00, 4.8, '{"WiFi", "Pool", "Bar"}'),
    ('New York Central Park', 'New York', '303 Central Park West, New York, USA', 320.00, 4.7, '{"WiFi", "Gym", "Restaurant"}'),
    ('Tokyo Gardens', 'Tokyo', '404 Shinjuku, Tokyo, Japan', 200.00, 4.6, '{"WiFi", "Spa", "Pool"}'),
    ('London Tower Hotel', 'London', '505 Tower Hill, London, UK', 240.00, 4.5, '{"WiFi", "Bar", "Gym"}'),
    ('Dubai Desert Resort', 'Dubai', '606 Al Qudra Road, Dubai, UAE', 370.00, 4.9, '{"WiFi", "Pool", "Spa"}'),
    ('Sydney Opera View', 'Sydney', '707 Circular Quay, Sydney, Australia', 300.00, 4.8, '{"WiFi", "Pool", "Restaurant"}'),
    ('Rome Retreat', 'Rome', '456 Via Appia, Rome, Italy', 180.00, 4.5, '{"WiFi", "Gym", "Bar"}'),
    ('Milan Modern', 'Milan', '789 Via Torino, Milan, Italy', 230.00, 4.6, '{"WiFi", "Spa", "Restaurant"}'),
    ('Paris Serenity', 'Paris', '123 Rue de la Pompe, Paris, France', 270.00, 4.7, '{"WiFi", "Pool", "Bar"}'),
    ('New York Loft', 'New York', '404 SoHo, New York, USA', 330.00, 4.8, '{"WiFi", "Gym", "Restaurant"}'),
    ('Tokyo Zen', 'Tokyo', '505 Asakusa, Tokyo, Japan', 210.00, 4.5, '{"WiFi", "Spa", "Pool"}'),
    ('London Classic', 'London', '606 Kensington, London, UK', 250.00, 4.6, '{"WiFi", "Bar", "Gym"}'),
    ('Dubai Skyline', 'Dubai', '707 Downtown Dubai, Dubai, UAE', 380.00, 4.9, '{"WiFi", "Pool", "Spa"}'),
    ('Sydney Coastal', 'Sydney', '808 Manly Beach, Sydney, Australia', 310.00, 4.7, '{"WiFi", "Pool", "Restaurant"}'),
    ('Rome Gardens', 'Rome', '567 Via dei Fori Imperiali, Rome, Italy', 190.00, 4.4, '{"WiFi", "Gym", "Bar"}'),
    ('Milan Chic', 'Milan', '890 Via Brera, Milan, Italy', 240.00, 4.7, '{"WiFi", "Spa", "Restaurant"}'),
    ('Paris Oasis', 'Paris', '234 Rue de Rivoli, Paris, France', 280.00, 4.8, '{"WiFi", "Pool", "Bar"}'),
    ('New York Heights', 'New York', '505 Upper East Side, New York, USA', 340.00, 4.7, '{"WiFi", "Gym", "Restaurant"}'),
    ('Tokyo Central Inn', 'Tokyo', '606 Roppongi, Tokyo, Japan', 220.00, 4.6, '{"WiFi", "Spa", "Pool"}'),
    ('London Riverside', 'London', '707 Thames Embankment, London, UK', 260.00, 4.5, '{"WiFi", "Bar", "Gym"}'),
    ('Dubai Palm Resort', 'Dubai', '808 Palm Jumeirah, Dubai, UAE', 390.00, 4.9, '{"WiFi", "Pool", "Spa"}'),
    ('Sydney Skyline', 'Sydney', '909 Darling Harbour, Sydney, Australia', 320.00, 4.8, '{"WiFi", "Pool", "Restaurant"}'),
    ('Rome Luxe', 'Rome', '678 Via Condotti, Rome, Italy', 200.00, 4.5, '{"WiFi", "Gym", "Bar"}'),
    ('Milan Grand', 'Milan', '901 Via Dante, Milan, Italy', 250.00, 4.6, '{"WiFi", "Spa", "Restaurant"}'),
    ('Paris Retreat', 'Paris', '345 Rue de la Boétie, Paris, France', 290.00, 4.7, '{"WiFi", "Pool", "Bar"}'),
    ('New York Urban', 'New York', '606 Tribeca, New York, USA', 350.00, 4.8, '{"WiFi", "Gym", "Restaurant"}'),
    ('Tokyo Tranquility', 'Tokyo', '707 Harajuku, Tokyo, Japan', 230.00, 4.6, '{"WiFi", "Spa", "Pool"}'),
    ('London Heritage', 'London', '808 Westminster, London, UK', 270.00, 4.5, '{"WiFi", "Bar", "Gym"}'),
    ('Dubai Sands', 'Dubai', '909 Jumeirah Beach, Dubai, UAE', 400.00, 4.9, '{"WiFi", "Pool", "Spa"}'),
    ('Sydney Serenity', 'Sydney', '1010 Bondi Junction, Sydney, Australia', 330.00, 4.7, '{"WiFi", "Pool", "Restaurant"}'),
    ('Rome Classic', 'Rome', '789 Via del Tritone, Rome, Italy', 210.00, 4.4, '{"WiFi", "Gym", "Bar"}'),
    ('Milan Elegance', 'Milan', '123 Via San Babila, Milan, Italy', 260.00, 4.7, '{"WiFi", "Spa", "Restaurant"}'),
    ('Paris Harmony', 'Paris', '456 Rue de la Fayette, Paris, France', 300.00, 4.8, '{"WiFi", "Pool", "Bar"}'),
    ('New York Central Loft', 'New York', '707 Chelsea, New York, USA', 360.00, 4.7, '{"WiFi", "Gym", "Restaurant"}'),
    ('Tokyo Skyline', 'Tokyo', '808 Odaiba, Tokyo, Japan', 240.00, 4.6, '{"WiFi", "Spa", "Pool"}'),
    ('London Classic Inn', 'London', '909 Camden, London, UK', 280.00, 4.5, '{"WiFi", "Bar", "Gym"}'),
    ('Dubai Horizon', 'Dubai', '1010 Business Bay, Dubai, UAE', 410.00, 4.9, '{"WiFi", "Pool", "Spa"}'),
    ('Sydney Coastal Retreat', 'Sydney', '1111 Coogee Beach, Sydney, Australia', 340.00, 4.8, '{"WiFi", "Pool", "Restaurant"}'),
    ('Rome Grand', 'Rome', '890 Via del Babuino, Rome, Italy', 220.00, 4.5, '{"WiFi", "Gym", "Bar"}'),
    ('Milan Boutique Inn', 'Milan', '234 Via della Moscova, Milan, Italy', 270.00, 4.6, '{"WiFi", "Spa", "Restaurant"}'),
    ('Paris Blissful', 'Paris', '567 Rue de la Victoire, Paris, France', 310.00, 4.7, '{"WiFi", "Pool", "Bar"}'),
    ('New York Loft Central', 'New York', '808 Lower East Side, New York, USA', 370.00, 4.8, '{"WiFi", "Gym", "Restaurant"}'),
    ('Tokyo Central Retreat', 'Tokyo', '909 Akihabara, Tokyo, Japan', 250.00, 4.6, '{"WiFi", "Spa", "Pool"}'),
    ('London Riverside Inn', 'London', '1010 South Bank, London, UK', 290.00, 4.5, '{"WiFi", "Bar", "Gym"}'),
    ('Dubai Oasis Retreat', 'Dubai', '1111 Al Barsha, Dubai, UAE', 420.00, 4.9, '{"WiFi", "Pool", "Spa"}'),
    ('Sydney Harbor Retreat', 'Sydney', '1212 Darling Harbour, Sydney, Australia', 350.00, 4.7, '{"WiFi", "Pool", "Restaurant"}');