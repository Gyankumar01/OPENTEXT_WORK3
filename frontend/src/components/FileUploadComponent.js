import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './FileUploadComponent.css'; // Import the CSS file

const FileUploadComponent = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [fileName, setFileName] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        setSelectedFile(file);
        setFileName(file ? file.name : '');
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!selectedFile) {
            setErrorMessage('Please select a file to upload.');
            return;
        }

        const formData = new FormData();
        formData.append('file', selectedFile);

        try {
            const response = await fetch('http://localhost:9001/upload', {
                method: 'POST',
                body: formData,
            });

            if (!response.ok) {
                throw new Error('File upload failed');
            }

            const data = await response.json();

            if (data.length > 0) {
                navigate('/result', { state: { data } });
            } else {
                setErrorMessage('No data found in the uploaded file.');
            }
        } catch (error) {
            console.error('Error uploading file:', error);
            setErrorMessage('Error uploading file');
        }
    };

    return (
        <div className="upload-background">
            <div className="upload-container">
                <h1 className="header-title">Third Party Library Upgrade</h1>
                <form className="upload-form" onSubmit={handleSubmit}>
                    <label htmlFor="fileInput" className="file-label">
                        Choose File
                        <input
                            type="file"
                            id="fileInput"
                            className="file-input"
                            onChange={handleFileChange}
                        />
                    </label>
                    {fileName && <p className="file-name">Selected File: {fileName}</p>} {/* Display file name */}
                    <button type="submit" className="upload-button">Upload</button>
                </form>
                {errorMessage && <p className="error-message">{errorMessage}</p>}
            </div>
        </div>
    );
};

export default FileUploadComponent;
