// components/TravelArchivePage.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './TravelArchivePage.css';

const TravelArchivePage = () => {
  const navigate = useNavigate();
  const userId = 'bd4ffbd0-c1c4-492d-8163-b1b6861bc907';
  const [travelLogs, setTravelLogs] = useState([]);
  const [newLog, setNewLog] = useState({ travelDest: '', travelDate: '' });
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!localStorage.getItem('jwtToken')) navigate('/travel-archive');
    
    const fetchTravelLogs = async () => {
      try {
        const response = await axios.get('http://localhost:8086/api/photos/travels', {
          params: { userId },
         // headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` }
        });
        setTravelLogs(response.data);
      } catch (err) {
        setError(err.response?.data || 'Failed to load travel logs');
      } finally {
        setLoading(false);
      }
    };

    fetchTravelLogs();
  }, [navigate, userId]);

  const createTravelLog = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8086/api/photos/create', null, {
        params: { ...newLog, userId },
        headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` }
      });
      setShowCreateForm(false);
      setNewLog({ travelDest: '', travelDate: '' });
      const { data } = await axios.get('http://localhost:8086/api/photos/travels', { params: { userId } });
      setTravelLogs(data);
    } catch (err) {
      setError(err.response?.data || 'Failed to create travel log');
    }
  };

  const deleteLog = async (travelId) => {
    if (window.confirm('Are you sure you want to delete this archive?')) {
      try {
        await axios.delete(`http://localhost:8086/api/photos/delete/${travelId}`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` }
        });
        setTravelLogs(travelLogs.filter(log => log.travelId !== travelId));
      } catch (err) {
        setError(err.response?.data || 'Failed to delete archive');
      }
    }
  };

  const handleFileUpload = async (travelId, files) => {
    const formData = new FormData();
    formData.append('travelId', travelId);
    Array.from(files).forEach(file => formData.append('files', file));

    try {
      await axios.post('http://localhost:8086/api/photos/upload/5e7aa989-9308-49ba-bd53-3bf6b22b4fe4', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          //Authorization: `Bearer ${localStorage.getItem('jwtToken')}`
        }
      });
      const { data } = await axios.get('http://localhost:8086/api/photos/travels', { params: { userId } });
      setTravelLogs(data);
    } catch (err) {
      setError(err.response?.data || 'Upload failed');
    }
  };

  if (loading) return <div className="loading">Loading travel logs...</div>;

  const handleTravelClick = (travelId) => {
    console.log('Travel ID clicked:', travelId);
    };

  return (
    <div className="travel-archive-container">
      <header>
        <h1>Travel Archives</h1>
        <button 
          className="primary-btn"
          onClick={() => setShowCreateForm(!showCreateForm)}
        >
          {showCreateForm ? 'Cancel' : 'New Travel Log'}
        </button>
      </header>

      {error && <div className="error-banner">{error}</div>}

      {showCreateForm && (
        <form className="create-form" onSubmit={createTravelLog}>
          <input
            type="text"
            placeholder="Destination"
            value={newLog.travelDest}
            onChange={(e) => setNewLog({...newLog, travelDest: e.target.value})}
            required
          />
          <input
            type="date"
            value={newLog.travelDate}
            onChange={(e) => setNewLog({...newLog, travelDate: e.target.value})}
            required
          />
          <button type="submit" className="primary-btn">
            Create Travel Log
          </button>
        </form>
      )}

        <div className="travel-logs-grid"> 
        {travelLogs.map((log) => ( 
            <div  
            key={log.travelId}  
            className="log-card"  
            onClick={() => handleTravelClick(log.travelId)} // Capture travelId when the card is clicked 
            > 
            <div className="log-header"> 
                <h3>{log.travelDest}</h3> 
                <span className="log-date"> 
                {new Date(log.travelDate).toLocaleDateString()} 
                </span> 
            </div> 
        
            <div className="log-actions"> 
                <div className="file-upload"> 
                <input 
                    type="file" 
                    multiple 
                    onChange={(e) => handleFileUpload(log.travelId, e.target.files)} 
                    id={`file-upload-${log.travelId}`} 
                /> 
                <label htmlFor={`file-upload-${log.travelId}`} className="upload-btn"> 
                    📁 Add Photos 
                </label> 
                </div> 
        
                <button 
                className="download-btn" 
                onClick={(e) => { 
                    e.stopPropagation(); // Prevent triggering the parent onClick 
                    window.location.href = `http://localhost:8086/api/photos/download/${log.travelId}`; 
                } }> 
                ⬇️ Download 
                </button> 
        
                <button 
                className="delete-btn" 
                onClick={(e) => { 
                    e.stopPropagation(); // Prevent triggering the parent onClick 
                    deleteLog(log.travelId); 
                }} 
                > 
                🗑️ Delete 
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TravelArchivePage;