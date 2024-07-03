import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import './ResultComponent.css'; // Import the CSS file

const ResultComponent = () => {
    const location = useLocation();
    const { data } = location.state || { data: [] };

    const [selectAll, setSelectAll] = useState(false);
    const [selectedItems, setSelectedItems] = useState([]);

    const handleCheckboxChange = (item) => {
        const selectedIndex = selectedItems.indexOf(item);
        let newSelectedItems = [...selectedItems];

        if (selectedIndex === -1) {
            newSelectedItems.push(item);
        } else {
            newSelectedItems.splice(selectedIndex, 1);
        }

        setSelectedItems(newSelectedItems);
    };

    const handleMasterCheckboxChange = () => {
        setSelectAll(!selectAll);
        setSelectedItems(selectAll ? [] : [...data]);
    };

    const handleUpdate = () => {
        // Implement your update logic here with selectedItems
        console.log('Selected items:', selectedItems);
        // Example: Send selectedItems to an API for update
    };

    if (!data || data.length === 0) {
        return <div className="results-table-container">No data found.</div>;
    }

    return (
        <div className="results-table-container">
            <h2>Third Party Library Upgrade</h2>
            <div className="update-button-container">
                <button className="update-button" onClick={handleUpdate}>Update</button>
            </div>
            <table className="results-table">
                <thead>
                    <tr>
                        <th>
                            <input
                                type="checkbox"
                                checked={selectAll}
                                onChange={handleMasterCheckboxChange}
                            />
                        </th>
                        <th>Property</th>
                        <th>Current Version</th>
                        <th>New Version</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((item, index) => (
                        <tr key={index}>
                            <td>
                                <input
                                    type="checkbox"
                                    checked={selectedItems.includes(item)}
                                    onChange={() => handleCheckboxChange(item)}
                                />
                            </td>
                            <td>{item.artifact}</td>
                            <td>{item.currentVersion}</td>
                            <td>{item.newVersion}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ResultComponent;
