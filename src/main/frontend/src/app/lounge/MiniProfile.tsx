'use client';
import React from 'react';

type MiniProfileProps = {
  name: string;
  category: string;
  badge_cnt: number;
  roadmap_cnt: number;
  card_cnt: number;
};

const categoryLabels: Record<string, string> = {
  frontend: '웹/프론트엔드',
  backend: '웹/백엔드',
  data: '데이터사이언스',
  security: '보안',
  application: '어플리케이션',
};

export default function MiniProfile({
  name,
  category,
  badge_cnt,
  roadmap_cnt,
  card_cnt
}: MiniProfileProps) {
  return (
    <div className=' w-64 h-96 transform transition-transform duration-300 hover:scale-105'>
      <img className='w-64 h-64 object-cover rounded-xl border border-gray-cc' src='asset/png/profile/user_profile_1.png' />
      <div className='text-xl font-semibold mt-1'>{categoryLabels[category]} 개발자</div>
      <div className='text-md mt-1'>{name}</div>
      <div className='flex mt-1'>
        <div className='flex gap-2 mr-4'>
          <img className='w-6 h-6' src='asset/png/icon_filter_badge.png' />
          <div>{badge_cnt}</div>
        </div>
        <div className='flex gap-2 mr-4'>
          <img className='w-6 h-6' src='asset/png/icon_filter_roadmap.png' />
          <div>{roadmap_cnt}</div>
        </div>
        <div className='flex gap-2 mr-4'>
          <img className='w-6 h-6' src='asset/png/icon_filter_card.png' />
          <div>{card_cnt}</div>
        </div>
      </div>
    </div>
  );
}