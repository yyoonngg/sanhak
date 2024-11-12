'use client';
import React from 'react';

type CategoryButtonProps = {
  category: string;
};

const categoryLabels: Record<string, string> = {
  frontend: '웹/프론트엔드',
  backend: '웹/백엔드',
  data: '데이터 사이언스',
  security: '보안',
  application: '어플리케이션',
};

export default function CategoryButton({
  category,
}: CategoryButtonProps) {
  const categoryName = categoryLabels[category];
  const imageName = category.toLowerCase().replace(/\s+/g, '').replace(/\./g, ''); // 소문자, 공백제거, "."제거
  const imageSrc = `/asset/png/category/${category}_img.png`;
  return (
    <div 
      className='p-6 flex flex-col items-center justify-between text-sm font-semibold hover:bg-gray-dc hover:rounded-xl'
    >
      <div className='w-fit h-fit flex items-center justify-center bg-primary rounded-xl p-4 mb-1'>
        <img className='w-12 h-12 object-contain' src={imageSrc} alt={categoryName}/>
      </div>
      <div>
        {categoryName}
      </div>
    </div>
  );
}