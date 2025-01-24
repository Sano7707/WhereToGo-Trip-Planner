CREATE TABLE transportations (
                                 id SERIAL PRIMARY KEY, -- Unique ID for each transportation option
                                 name VARCHAR(100) NOT NULL, -- Name of the transportation service
                                 city VARCHAR(100) NOT NULL, -- City where the service is available
                                 type VARCHAR(50) NOT NULL, -- Type of transportation (e.g., Bus, Train, Taxi, Shuttle)
                                 cost NUMERIC(10, 2) NOT NULL, -- Cost of the transportation
                                 description TEXT, -- Description of the service
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp of when the entry was added
);

INSERT INTO transportations (name, city, type, cost, description)
VALUES
    ('Rome City Bus', 'Rome', 'Bus', 2.50, 'Affordable public bus service covering major attractions'),
    ('Milan Metro', 'Milan', 'Train', 3.00, 'Efficient metro system connecting the city'),
    ('Paris Taxi', 'Paris', 'Taxi', 15.00, 'Convenient taxi service available 24/7'),
    ('New York Subway', 'New York', 'Train', 3.50, 'Extensive subway network for easy travel'),
    ('Tokyo Shuttle', 'Tokyo', 'Shuttle', 10.00, 'Airport shuttle service to major hotels'),
    ('London Underground', 'London', 'Train', 4.00, 'Iconic tube system for quick city travel'),
    ('Dubai Metro', 'Dubai', 'Train', 5.00, 'Modern metro system with air-conditioned stations'),
    ('Sydney Ferry', 'Sydney', 'Ferry', 6.50, 'Scenic ferry rides across Sydney Harbour'),
    ('Rome Hop-On Hop-Off Bus', 'Rome', 'Bus', 25.00, 'Tourist bus with stops at major landmarks'),
    ('Milan Tram', 'Milan', 'Tram', 2.00, 'Historic tram system for city travel'),
    ('Paris Bike Rental', 'Paris', 'Bike', 5.00, 'Bike rental service for exploring the city'),
    ('New York Yellow Cab', 'New York', 'Taxi', 20.00, 'Classic yellow cab service'),
    ('Tokyo Monorail', 'Tokyo', 'Train', 8.00, 'Monorail connecting the city to the airport'),
    ('London Black Cab', 'London', 'Taxi', 25.00, 'Iconic black cab service'),
    ('Dubai Tram', 'Dubai', 'Tram', 4.50, 'Tram service along the Dubai Marina'),
    ('Sydney Light Rail', 'Sydney', 'Train', 3.50, 'Light rail service for city travel'),
    ('Rome Taxi', 'Rome', 'Taxi', 12.00, 'Reliable taxi service in Rome'),
    ('Milan Bike Rental', 'Milan', 'Bike', 6.00, 'Bike rental for exploring Milan'),
    ('Paris Metro', 'Paris', 'Train', 2.50, 'Efficient metro system for city travel'),
    ('New York Bus', 'New York', 'Bus', 3.00, 'Public bus service covering all boroughs'),
    ('Tokyo Taxi', 'Tokyo', 'Taxi', 18.00, 'Comfortable taxi service in Tokyo'),
    ('London Bus', 'London', 'Bus', 2.50, 'Famous red double-decker bus service'),
    ('Dubai Taxi', 'Dubai', 'Taxi', 10.00, 'Affordable taxi service in Dubai'),
    ('Sydney Taxi', 'Sydney', 'Taxi', 15.00, 'Taxi service for quick city travel'),
    ('Rome Shuttle', 'Rome', 'Shuttle', 8.00, 'Shuttle service to major hotels'),
    ('Milan Taxi', 'Milan', 'Taxi', 14.00, 'Taxi service in Milan'),
    ('Paris Shuttle', 'Paris', 'Shuttle', 12.00, 'Shuttle service to airports and hotels'),
    ('New York Ferry', 'New York', 'Ferry', 7.00, 'Ferry service to Staten Island and beyond'),
    ('Tokyo Bus', 'Tokyo', 'Bus', 2.50, 'Public bus service in Tokyo'),
    ('London Shuttle', 'London', 'Shuttle', 10.00, 'Shuttle service to airports'),
    ('Dubai Bus', 'Dubai', 'Bus', 3.00, 'Public bus service in Dubai'),
    ('Sydney Shuttle', 'Sydney', 'Shuttle', 9.00, 'Shuttle service to the airport'),
    ('Rome Bike Rental', 'Rome', 'Bike', 7.00, 'Bike rental for exploring Rome'),
    ('Milan Shuttle', 'Milan', 'Shuttle', 11.00, 'Shuttle service to airports'),
    ('Paris Tram', 'Paris', 'Tram', 3.00, 'Tram service in Paris'),
    ('New York Bike Rental', 'New York', 'Bike', 8.00, 'Bike rental for city exploration'),
    ('Tokyo Train', 'Tokyo', 'Train', 4.00, 'Efficient train service in Tokyo'),
    ('London Bike Rental', 'London', 'Bike', 6.00, 'Bike rental for exploring London'),
    ('Dubai Shuttle', 'Dubai', 'Shuttle', 15.00, 'Shuttle service to major attractions'),
    ('Sydney Bus', 'Sydney', 'Bus', 4.00, 'Public bus service in Sydney'),
    ('Rome Tram', 'Rome', 'Tram', 2.50, 'Tram service in Rome'),
    ('Milan Ferry', 'Milan', 'Ferry', 5.00, 'Ferry service on the canals'),
    ('Paris Bus', 'Paris', 'Bus', 2.00, 'Public bus service in Paris'),
    ('New York Train', 'New York', 'Train', 5.00, 'Commuter train service'),
    ('Tokyo Ferry', 'Tokyo', 'Ferry', 6.00, 'Ferry service in Tokyo Bay'),
    ('London Tram', 'London', 'Tram', 3.50, 'Tram service in South London'),
    ('Dubai Bike Rental', 'Dubai', 'Bike', 10.00, 'Bike rental for exploring Dubai'),
    ('Sydney Tram', 'Sydney', 'Tram', 4.50, 'Tram service in Sydney'),
    ('Rome Metro', 'Rome', 'Train', 2.00, 'Metro service in Rome'),
    ('Milan Bus', 'Milan', 'Bus', 1.50, 'Public bus service in Milan'),
    ('Paris Ferry', 'Paris', 'Ferry', 7.00, 'Ferry service on the Seine'),
    ('New York Tram', 'New York', 'Tram', 4.00, 'Tram service in Brooklyn'),
    ('Tokyo Tram', 'Tokyo', 'Tram', 3.00, 'Tram service in Tokyo'),
    ('London Ferry', 'London', 'Ferry', 8.00, 'Ferry service on the Thames'),
    ('Dubai Ferry', 'Dubai', 'Ferry', 12.00, 'Ferry service along the coast'),
    ('Sydney Metro', 'Sydney', 'Train', 5.00, 'Metro service in Sydney'),
    ('Rome Ferry', 'Rome', 'Ferry', 6.00, 'Ferry service on the Tiber River'),
    ('Milan Metro', 'Milan', 'Train', 2.50, 'Metro service in Milan'),
    ('Paris Train', 'Paris', 'Train', 4.00, 'Commuter train service in Paris'),
    ('New York Metro', 'New York', 'Train', 3.00, 'Metro service in New York'),
    ('Tokyo Subway', 'Tokyo', 'Train', 2.50, 'Subway service in Tokyo'),
    ('London Train', 'London', 'Train', 5.00, 'Commuter train service in London'),
    ('Dubai Train', 'Dubai', 'Train', 6.00, 'Train service in Dubai'),
    ('Sydney Train', 'Sydney', 'Train', 4.00, 'Commuter train service in Sydney'),
    ('Rome Train', 'Rome', 'Train', 3.00, 'Commuter train service in Rome'),
    ('Milan Train', 'Milan', 'Train', 2.50, 'Commuter train service in Milan'),
    ('Paris Train', 'Paris', 'Train', 4.00, 'Commuter train service in Paris'),
    ('New York Train', 'New York', 'Train', 5.00, 'Commuter train service in New York'),
    ('Tokyo Train', 'Tokyo', 'Train', 4.00, 'Commuter train service in Tokyo'),
    ('London Train', 'London', 'Train', 5.00, 'Commuter train service in London'),
    ('Dubai Train', 'Dubai', 'Train', 6.00, 'Commuter train service in Dubai'),
    ('Sydney Train', 'Sydney', 'Train', 4.00, 'Commuter train service in Sydney'),
    ('Rome Train', 'Rome', 'Train', 3.00, 'Commuter train service in Rome'),
    ('Milan Train', 'Milan', 'Train', 2.50, 'Commuter train service in Milan'),
    ('Paris Train', 'Paris', 'Train', 4.00, 'Commuter train service in Paris'),
    ('New York Train', 'New York', 'Train', 5.00, 'Commuter train service in New York'),
    ('Tokyo Train', 'Tokyo', 'Train', 4.00, 'Commuter train service in Tokyo'),
    ('London Train', 'London', 'Train', 5.00, 'Commuter train service in London'),
    ('Dubai Train', 'Dubai', 'Train', 6.00, 'Commuter train service in Dubai'),
    ('Sydney Train', 'Sydney', 'Train', 4.00, 'Commuter train service in Sydney');


