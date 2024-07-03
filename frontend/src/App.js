import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import FileUploadComponent from './components/FileUploadComponent';
import ResultComponent from './components/ResultComponent';

const App = () => {

    return (
        <Router>
            {}
            <Routes>
                {}
                <Route path="/" element={<FileUploadComponent />} />

                {}
                <Route path="/result" element={<ResultComponent />} />
            </Routes>
        </Router>
    );
};

export default App;
