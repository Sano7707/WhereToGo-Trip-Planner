import React, { useState, useEffect } from "react";
import axios from "axios";
import "../components/TravelPlan.css";

function TravelPlan() {
  const cities = ["Rome", "Milan", "Paris", "New York", "Tokyo", "London", "Dubai", "Sydney"];
  const [departure, setDeparture] = useState("");
  const [destination, setDestination] = useState("");
  const [travelPlan, setTravelPlan] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const savedTravelPlan = localStorage.getItem("travelPlan");
    if (savedTravelPlan) {
      setTravelPlan(JSON.parse(savedTravelPlan));
    }
  }, []);

  const handleSearch = async () => {
    if (!departure || !destination) {
      setError("Please select both departure and destination cities.");
      return;
    }
  
    const jwtToken = localStorage.getItem("jwtToken");
    if (!jwtToken) {
      alert("Please login first!");
      return;
    }
  
    try {
      const response = await axios.get("http://localhost:8080/api/search", {
        params: { departure, destination },
        headers: {
          Authorization: `Bearer ${jwtToken}`
        }
      });
      setTravelPlan(response.data);
      setError("");
      localStorage.setItem("travelPlan", JSON.stringify(response.data));
    } catch (err) {
      if (err.response?.status === 401) {
        localStorage.removeItem("jwtToken");
        alert("Session expired. Please login again.");
      } else {
        setError("Failed to fetch travel plan. Please try again.");
        setTravelPlan(null);
      }
    }
  };

  const handleReserveHotel = async (hotelId) => {
    try {
      const response = await axios.post("http://localhost:8080/travel/reserve-hotel", null, {
        params: { hotelId }
      });
      alert(response.data);
    } catch (err) {
      alert("Failed to reserve hotel. Please try again.");
    }
  };

  return (
    <div className="travel-plan-container">
      <h1>Travel Planner</h1>
      <div className="search-form">
        <select
          value={departure}
          onChange={(e) => setDeparture(e.target.value)}
        >
          <option value="">Select Departure City</option>
          {cities.map((city) => (
            <option key={city} value={city}>{city}</option>
          ))}
        </select>

        <select
          value={destination}
          onChange={(e) => setDestination(e.target.value)}
        >
          <option value="">Select Destination City</option>
          {cities.map((city) => (
            <option key={city} value={city}>{city}</option>
          ))}
        </select>

        <button onClick={handleSearch}>Search</button>
      </div>

      {error && <p className="error">{error}</p>}

      {travelPlan && (
        <div className="travel-plan">
          {/* Cheapest Plan Section */}
          {travelPlan.cheapestFlight && travelPlan.cheapestHotel && travelPlan.cheapestTransportation && (
            <div className="cheapest-plan">
              <h2>Best Value Plan</h2>
              <div className="cards-container">
                <div className="card cheapest-card">
                  <h3>✈️ Best Flight</h3>
                  <p><strong>Route:</strong> {travelPlan.cheapestFlight.departure} → {travelPlan.cheapestFlight.destination}</p>
                  <p><strong>Date:</strong> {travelPlan.cheapestFlight.date}</p>
                  <p><strong>Price:</strong> ${travelPlan.cheapestFlight.cost}</p>
                </div>

                <div className="card cheapest-card">
                  <h3>🏨 Top Hotel Deal</h3>
                  <p><strong>Name:</strong> {travelPlan.cheapestHotel.name}</p>
                  <p><strong>Address:</strong> {travelPlan.cheapestHotel.address}</p>
                  <p><strong>Nightly Rate:</strong> ${travelPlan.cheapestHotel.pricePerNight}</p>
                </div>

                <div className="card cheapest-card">
                  <h3>🚗 Recommended Transport</h3>
                  <p><strong>Type:</strong> {travelPlan.cheapestTransportation.type}</p>
                  <p><strong>Cost:</strong> ${travelPlan.cheapestTransportation.cost}</p>
                  <p>{travelPlan.cheapestTransportation.description}</p>
                </div>

                <div className="card total-card">
                  <h3>💰 Total Estimated Cost</h3>
                  <p className="total-price">${travelPlan.totalCost}</p>
                  <button className="book-button">Book This Plan</button>
                </div>
              </div>
            </div>
          )}

          {/* All Flights */}
          <h2>All Flight Options</h2>
          <div className="cards-container">
            {travelPlan.flights.map((flight) => (
              <div key={flight.id} className="card">
                <h3>{flight.departure} → {flight.destination}</h3>
                <p><strong>Date:</strong> {flight.date}</p>
                <p><strong>Cost:</strong> ${flight.cost}</p>
              </div>
            ))}
          </div>

          {/* All Hotels */}
          <h2>All Hotel Options</h2>
          <div className="cards-container">
            {travelPlan.hotels.map((hotel) => (
              <div key={hotel.id} className="card">
                <h3>{hotel.name}</h3>
                <p><strong>Address:</strong> {hotel.address}</p>
                <p><strong>Price/Night:</strong> ${hotel.pricePerNight}</p>
                <p><strong>Rating:</strong> {hotel.rating}</p>
                <button onClick={() => handleReserveHotel(hotel.id)}>Reserve</button>
              </div>
            ))}
          </div>

          {/* All Transportations */}
          <h2>All Transportation Options</h2>
          <div className="cards-container">
            {travelPlan.transportations.map((transport) => (
              <div key={transport.id} className="card">
                <h3>{transport.name}</h3>
                <p><strong>Type:</strong> {transport.type}</p>
                <p><strong>Cost:</strong> ${transport.cost}</p>
                <p><strong>Description:</strong> {transport.description}</p>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default TravelPlan;