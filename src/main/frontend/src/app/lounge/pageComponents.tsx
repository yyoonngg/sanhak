'use client';
import React, { useState } from 'react';
import LoungeFilter from './LoungeFilter';
import MiniProfile from './MiniProfile';

// TODO: API 연결
const profileMockData: MiniProfileInfo[] = [
  { id: 1, name: "아이유", category: "backend", badge_cnt: 5, roadmap_cnt: 2, card_cnt: 1 , imageURL:"url"},
  { id: 2, name: "제니", category: "frontend", badge_cnt: 3, roadmap_cnt: 5, card_cnt: 2 ,imageURL: "url"},
  { id: 3, name: "지수", category: "data", badge_cnt: 4, roadmap_cnt: 1, card_cnt: 3 ,imageURL:"url"},
  { id: 4, name: "로제", category: "security", badge_cnt: 6, roadmap_cnt: 3, card_cnt: 4, imageURL: "url" },
  { id: 5, name: "유재석", category: "application", badge_cnt: 2, roadmap_cnt: 1, card_cnt: 1, imageURL:"url"}
];

export default function LoungePage() {
  const [selectedFilter, setSelectedFilter] = useState<string>('time');
  const handleClickFilter = (filter: string) => {
    setSelectedFilter(filter);  
  };

  return (
    <div className='w-full h-full flex flex-col items-center mt-5'>
      <div className='w-[1400px] h-full'>
        <div className='w-full flex flex-col px-24'>
          <div className='font-bold text-2xl mb-1'>커리어라운지</div>
          <div className='font-md text-md mb-5'>다양한 커리어 경험들을 살펴보세요!</div>
          <LoungeFilter selectedFilter={selectedFilter} handleClickFilter={handleClickFilter}/>
        </div>
        <div className='w-full h-full flex flex-col px-24 pt-10 mb-10'>
          <div className='w-full h-full flex justify-between'>
          <div className='w-full grid grid-cols-4 gap-4'>
            {profileMockData.map((profile) => (
              <MiniProfile
                key={profile.id}
                name={profile.name}
                category={profile.category}
                badge_cnt={profile.badge_cnt}
                roadmap_cnt={profile.roadmap_cnt}
                card_cnt={profile.card_cnt}
              />
            ))}
          </div>
          </div>
        </div>
      </div>
    </div>
  );
}