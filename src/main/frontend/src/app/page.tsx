'use client';

import { useEffect, useState } from 'react';

export default function Home() {
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch(`/test`, {
            method: 'GET',
            credentials: 'include', // Include cookies for session handling
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then((data) => setData(data))
            .catch((error) => setError(error.message));
    }, []);

    return (
        <div>
            <h1>Data from Spring Boot:</h1>
            {error && <p>Error: {error}</p>}
            {data.length > 0 ? (
                <ul>
                    {data.map((item, index) => (
                        <li key={index}>{item}</li>
                    ))}
                </ul>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
}
