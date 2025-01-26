import React, { useState, useEffect } from "react";
import { ScaleLoader } from "react-spinners";
import "./Recorder.css";
import CardItem from "./CardItem";

function Recorder() {
  const [destination, setDestination] = useState("");
  const [budget, setBudget] = useState("Medium");
  const [userPreferences, setUserPreferences] = useState("");
  const [response, setResponse] = useState({ places: [], weather: {} });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const savedResponse = localStorage.getItem("travelRecommendations");
    if (savedResponse) {
      setResponse(JSON.parse(savedResponse));
    }
  }, []);

  const handleGetRecommendations = () => {
    if (!destination) {
      alert("Please provide a destination.");
      return;
    }
  
    const jwtToken = localStorage.getItem("jwtToken");
    console.log("JWT Token from localStorage:", jwtToken); 
    
    if (!jwtToken) {
      alert("Please login first!");
      return;
    }
  
    setLoading(true);
  
    console.log("Sending request to:", "http://localhost:8081/api/where-to-go");
    console.log("Request headers:", {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${jwtToken}`
    });
  
    fetch("http://localhost:8081/api/where-to-go", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${jwtToken}` 
      },
      body: JSON.stringify({
        destination,
        budget,
        preferences: userPreferences,
      }),
    })
    .then(response => {
      console.log("Response status:", response.status); 
      console.log("Response headers:", [...response.headers]); 
      
      if (response.status === 401) {  
        localStorage.removeItem("jwtToken");
        alert("Session expired. Please login again.");
        return Promise.reject("Unauthorized");
      }
      return response.json();
    })
    .then((data) => {
      console.log("Response data:", data); 
      setResponse(data);
      setLoading(false);
      localStorage.setItem("travelRecommendations", JSON.stringify(data));
    })
    .catch((error) => {
      console.error("Error details:", error); 
      setLoading(false);
    });
  };

  return (
    <div className="page-container">
      <div className="form-container">
        <h1>Plan Your Trip</h1>

        <label htmlFor="destination">Destination:</label>
        <input
          type="text"
          id="destination"
          value={destination}
          onChange={(e) => setDestination(e.target.value)}
          placeholder="Enter your destination (e.g., Paris)"
        />

        <label htmlFor="budget">Budget:</label>
        <select
          id="budget"
          value={budget}
          onChange={(e) => setBudget(e.target.value)}
        >
          <option value="Low">Low</option>
          <option value="Medium">Medium</option>
          <option value="High">High</option>
        </select>

        <label htmlFor="preferences">Preferences (Optional):</label>
        <textarea
          id="preferences"
          value={userPreferences}
          onChange={(e) => setUserPreferences(e.target.value)}
          placeholder="E.g., cultural places, hidden gems, local food"
        ></textarea>

        <button onClick={handleGetRecommendations} className="submit-btn">
          Get Recommendations
        </button>
      </div>

      {loading && (
        <div className="overlay">
          <ScaleLoader loading={loading} height={70} width={6} aria-label="Loading Spinner" />
        </div>
      )}

      <div className="response-container">
        {(response.places?.length > 0 || response.weather) && !loading ? (
          <div className="travel-plan-container">
            {/* Weather Section */}
            <div className="weather-section">
              <h2>🌤️ Weather in {response.weather?.city}</h2>
              <div className="cards-container">
                {/* Current Weather Card */}
                <div className="card weather-card">
                  <h3>Current Weather</h3>
                  <div className="weather-details">
                    <p>🌡️ Temperature: {response.weather?.temperature}</p>
                    <p>🌈 Condition: {response.weather?.condition}</p>
                    <p>💧 Humidity: {response.weather?.humidity}</p>
                  </div>
                </div>

                {/* Forecast Cards */}
                {response.weather?.forecast?.map((day, index) => (
                  <div key={index} className="card forecast-card">
                    <h3>{new Date(day.date).toLocaleDateString()}</h3>
                    <div className="weather-details">
                      <p>⬆️ High: {day.maxTemp}</p>
                      <p>⬇️ Low: {day.minTemp}</p>
                      <p>🌤️ {day.condition}</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Places Section */}
            <h2>🌟 Recommended Places</h2>
            <div className="cards-container">
              {response.places.map((place, index) => (
                <CardItem
                  key={index}
                  src={place.image_link}
                  text={place.description}
                  label={place.place_name}
                  path={place.website_link}
                />
              ))}
            </div>
          </div>
        ) : (
          <p className="empty-state">Fill out the form and click "Get Recommendations" to see your personalized travel guide.</p>
        )}
      </div>
    </div>
  );
}

export default Recorder;