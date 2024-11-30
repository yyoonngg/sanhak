'use client';
import React from 'react';

type LoungeFilterProps = {
  selectedFilter: string;
  handleClickFilter: (filter: string) => void
};

export default function LoungeFilter({
  selectedFilter,
  handleClickFilter
}: LoungeFilterProps) {
  return (
    <div className='flex flex-wrap gap-4 mt-2'>
      <button
        className={`flex items-center gap-2 px-2 py-1 border rounded-xl hover:bg-gray-d9 ${selectedFilter === "time" ? "bg-primary text-white" : "bg-white"}`}
        onClick={()=>handleClickFilter('time')}
      >
        <img className='w-4' src='asset/png/icon_filter_time.png' alt='최신순' />
        최신순
      </button>
      <button
        className={`flex items-center gap-2 px-2 py-1 border rounded-xl hover:bg-gray-d9 ${selectedFilter === "click" ? "bg-primary text-white" : "bg-white"}`}
        onClick={()=>handleClickFilter('click')}
      >
        <img className='w-4' src='asset/png/icon_filter_click.png' alt='조회순' />
        조회순
      </button>
      <button
        className={`flex items-center gap-2 px-2 py-1 border rounded-xl hover:bg-gray-d9 ${selectedFilter === "badge" ? "bg-primary text-white" : "bg-white"}`}
        onClick={()=>handleClickFilter('badge')}
      >
        <img className='w-4' src='asset/png/icon_filter_badge.png' alt='스킬뱃지 순' />
        스킬뱃지 순
      </button>
      <button
        className={`flex items-center gap-2 px-2 py-1 border rounded-xl hover:bg-gray-d9 ${selectedFilter === "roadmap" ? "bg-primary text-white" : "bg-white"}`}
        onClick={()=>handleClickFilter('roadmap')}
      >
        <img className='w-4' src='asset/png/icon_filter_roadmap.png' alt='커스텀로드맵 순' />
        커스텀로드맵 순
      </button>
      <button
        className={`flex items-center gap-2 px-2 py-1 border rounded-xl hover:bg-gray-d9 ${selectedFilter === "card" ? "bg-primary text-white" : "bg-white"}`}
        onClick={()=>handleClickFilter('card')}
      >
        <img className='w-4' src='asset/png/icon_filter_card.png' alt='경험카드 순' />
        경험카드 순
      </button>
    </div>
  );
}