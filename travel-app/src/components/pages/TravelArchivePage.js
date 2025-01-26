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
          params: { userId }
        });
    
        const transformedLogs = response.data.map((log) => ({
          travelId: log.travel_id,
          travelDest: log.dest_name,
          travelDate: log.visit_date,
          isUploadVisible: log.is_upload_visible,
          isDownloadVisible: log.is_download_visible,
          isDeleteVisible: log.is_delete_visible,
          isDeleteFilesVisible: log.is_delete_files_visible,
        }));
    
        setTravelLogs(transformedLogs);
      } catch (err) {
        const errorMessage = err.response?.data || 'Failed to load travel logs. Please try again later.';
        setError(errorMessage);
      } finally {
        setLoading(false);
      }
    };

    fetchTravelLogs();
  }, [navigate, userId]);

  const createTravelLog = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        'http://localhost:8086/api/photos/create',
        null,
        { params: { ...newLog, userId } }
      );
      setShowCreateForm(false);
      setNewLog({ travelDest: '', travelDate: '' });
      window.location.reload(); // This will reload the current page
    } catch (err) {
      setError(typeof err.response?.data === 'object' ? JSON.stringify(err.response?.data, null, 2) : err.response?.data || 'Failed to create travel log');
    }
  };

  const deleteLog = async (travelId) => {
    if (window.confirm('Are you sure you want to delete this travel log?')) {
      try {
        await axios.delete(`http://localhost:8086/api/photos/delete/${travelId}`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` }
        });
        setTravelLogs(travelLogs.filter(log => log.travelId !== travelId));
        window.location.reload(); // This will reload the current page
      } catch (err) {
        setError(typeof err.response?.data === 'object' ? JSON.stringify(err.response?.data, null, 2) : err.response?.data || 'Failed to delete travel log');
      }
    }
  };

  const deleteFiles = async (travelId) => {
    if (window.confirm('Are you sure you want to delete all files for this travel log?')) {
      try {
        await axios.delete(`http://localhost:8086/api/photos/delete-files/${travelId}`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` }
        });
        setTravelLogs(travelLogs.map(log => 
          log.travelId === travelId ? { ...log, files: [] } : log
        ));
        window.location.reload(); // This will reload the current page
      } catch (err) {
        setError(typeof err.response?.data === 'object' ? JSON.stringify(err.response?.data, null, 2) : err.response?.data || 'Failed to delete files');
      }
    }
  };

  const handleFileUpload = async (travelId, files) => {
    const formData = new FormData();
    Array.from(files).forEach(file => formData.append('files', file));

    try {
      await axios.post(`http://localhost:8086/api/photos/upload/${travelId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
        }
      });
      const { data } = await axios.get('http://localhost:8086/api/photos/travels', { params: { userId } });
      setTravelLogs(data);
      window.location.reload(); // This will reload the current page
    } catch (err) {
      setError(typeof err.response?.data === 'object' ? JSON.stringify(err.response?.data, null, 2) : err.response?.data || 'Upload failed');
    }
  };

  if (loading) return <div className="loading">Loading travel logs...</div>;

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

      {error && (
        <div className="error-banner">
          {typeof error === 'string' ? error : JSON.stringify(error, null, 2)}
        </div>
      )}

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
          <div key={log.travelId} className="log-card"> 
            <div className="log-header"> 
              <h3>{log.travelDest}</h3> 
              <span className="log-date">{new Date(log.travelDate).toLocaleDateString()}</span> 
            </div>

            <div className="log-actions"> 
              {log.isUploadVisible && (
                <div className="file-upload"> 
                  <input 
                    type="file" 
                    multiple 
                    onChange={(e) => handleFileUpload(log.travelId, e.target.files)} 
                    id={`file-upload-${log.travelId}`} 
                  /> 
                  <label htmlFor={`file-upload-${log.travelId}`} className="upload-btn"> 📁 Add Photos </label>
                </div>
              )}

              {log.isDownloadVisible && (
                <button
                  className="download-btn"
                  onClick={(e) => {
                    e.stopPropagation();
                    window.location.href = `http://localhost:8086/api/photos/download/${log.travelId}`;
                  }}
                >
                  ⬇️ Download
                </button>
              )}

              {log.isDeleteFilesVisible && (
                <button
                  className="delete-files-btn"
                  onClick={(e) => {
                    e.stopPropagation();
                    deleteFiles(log.travelId);
                  }}
                >
                  🗑️ Delete Files
                </button>
              )}

              {log.isDeleteVisible && (
                <button
                  className="delete-travel-btn"
                  onClick={(e) => {
                    e.stopPropagation();
                    deleteLog(log.travelId);
                  }}
                >
                  ❌ Delete Travel Log
                </button>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TravelArchivePage;
