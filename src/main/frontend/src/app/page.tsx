'use client';

import { useEffect, useState } from 'react';
import api from '../../src/util/api';

export default function Home() {
  const [data, setData] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get('/data');
        setData(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  return (
      <div>
        <h1>Data from Spring Boot:</h1>
        <p>{data}</p>
      </div>
  );
}
