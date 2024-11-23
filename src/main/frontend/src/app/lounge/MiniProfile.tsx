'use client';
import React from 'react';

type MiniProfileProps = {
  profile: MiniProfileInfo
};

const categoryLabels: Record<string, string> = {
  frontend: '웹/프론트엔드',
  backend: '웹/백엔드',
  data: '데이터사이언스',
  security: '보안',
  application: '어플리케이션',
};

export default function MiniProfile({
  profile
}: MiniProfileProps) {
  return (
    <div className=' w-64 h-96 transform transition-transform duration-300 hover:scale-105'>
      <img className='w-64 h-64 object-cover rounded-xl border border-gray-cc' src={profile.imageURL} />
      <div className='text-xl font-semibold mt-1'>{categoryLabels[profile.category]} 개발자</div>
      <div className='text-md mt-1'>{profile.name}</div>
      <div className='flex mt-1'>
        <div className='flex gap-2 mr-4'>
          <img className='w-6 h-6' src='asset/png/icon_filter_badge.png' />
          <div>{profile.badge_cnt}</div>
        </div>
        <div className='flex gap-2 mr-4'>
          <img className='w-6 h-6' src='asset/png/icon_filter_roadmap.png' />
          <div>{profile.roadmap_cnt}</div>
        </div>
        <div className='flex gap-2 mr-4'>
          <img className='w-6 h-6' src='asset/png/icon_filter_card.png' />
          <div>{profile.card_cnt}</div>
        </div>
      </div>
    </div>
  );
}