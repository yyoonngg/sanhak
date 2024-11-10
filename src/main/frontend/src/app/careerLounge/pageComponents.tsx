'use client';
import React from 'react';
import CategoryButton from './CategoryButton';

const categories = ['frontend', 'backend', 'data', 'security', 'application'];

export default function CareerLoungePage() {

  return (
    <div className='w-full h-full flex flex-col items-center'>
      <div className='w-dvw h-40 flex flex-row items-center justify-center border-b border-gray-d9'>
        <div className='w-[1000px] h-full flex flex-row items-center justify-between'>
          {categories.map(c => (
            <CategoryButton key={c} category={c} />
          ))}
        </div>
      </div>
    </div>
  );
}