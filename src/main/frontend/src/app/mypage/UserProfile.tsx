import React from 'react';
import SkillBadge from './SkillBadge';

type UserProfileProps = {
  userInfo: User
};

const categoryLabels: Record<string, string> = {
  frontend: '웹/프론트엔드',
  backend: '웹/백엔드',
  data: '데이터사이언스',
  security: '보안',
  application: '어플리케이션',
};

export default function UserProfile({
  userInfo
}:UserProfileProps) {
  const profile_img_src = `/asset/png/profile/${userInfo.profile}.png`;

  return (
    <div className='w-full h-fit min-h-64 flex justify-between border-b border-gray-cc pb-5'>
      <div className='w-1/2 flex flex-row items-center '>
        <img className='w-64 h-64 object-cover rounded-xl border border-gray-cc' src={profile_img_src} />
        <div className='ml-4'>
          <div className='text-3xl font-gmarketsansBold'>{userInfo.name}</div>
          <div className='text-2xl font-gmarketsansMedium mt-1'>{categoryLabels[userInfo.category]} 개발자</div>
          <div className='flex mt-2'>
            <div className='flex gap-2 mr-6'>
              <img className='w-6 h-6' src='asset/png/icon_filter_badge.png' />
              <div>{userInfo.badge_cnt}</div>
            </div>
            <div className='flex gap-2 mr-6'>
              <img className='w-6 h-6' src='asset/png/icon_filter_roadmap.png' />
              <div>{userInfo.roadmap_cnt}</div>
            </div>
            <div className='flex gap-2 mr-6'>
              <img className='w-6 h-6' src='asset/png/icon_filter_card.png' />
              <div>{userInfo.card_cnt}</div>
            </div>
          </div>
        </div>
      </div>
      
      <div className='w-[555px] max-h-[256px] bg-primary rounded-xl flex flex-wrap content-start p-4 overflow-y-auto scrollbar'>
        {userInfo.skill_list.map((skill) => (
            <SkillBadge key={skill.id} skill={skill} />
        ))}
      </div>
    </div>
  );
}