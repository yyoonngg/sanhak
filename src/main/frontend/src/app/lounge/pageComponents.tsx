'use client';
import React, { useEffect, useState } from 'react';
import LoungeFilter from './LoungeFilter';
import MiniProfile from './MiniProfile';
import { useRouter } from "next/navigation";

const filterRecord: Record<string, number> = {
  'time': 1,
  'badge': 2,
  'roadmap': 3,
  'card': 4,
  'click': 6
};

export default function LoungePage() {
  const router = useRouter();

  const handleProfileClick = () => {
    router.push("/mypage"); // `/mypage` 경로로 이동
  };

  const [profileData, setProfileData] = useState<MiniProfileInfo[]>([]);
  const [selectedFilter, setSelectedFilter] = useState<string>('time');
  const handleClickFilter = (filter: string) => {
    setSelectedFilter(filter);  
  };

  const fetchProfileData = async () => {
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/lounge/all/${filterRecord[selectedFilter]}/${1}`, { // page는 일단 1로 고정
        method: 'GET',
        credentials: 'include',
      });
      if (response.ok) {
        const data = await response.json();
  
        const formattedData: MiniProfileInfo[] = data.content.map((profile: any) => ({
          id: profile.id,
          name: profile.name,
          category: profile.category,
          likes: profile.likes,
          view_cnt: profile.view_cnt,
          badge_cnt: profile.badge_cnt,
          roadmap_cnt: profile.roadmap_cnt,
          card_cnt: profile.card_cnt,
          imageURL: profile.imageURL,
        }));
        console.log(formattedData);

        setProfileData(formattedData);
      } else {
        throw new Error('라운지 데이터를 불러오는 중 오류가 발생했습니다.');
      }
    } catch (error) {
      if (error instanceof Error) {
        console.error(error.message);
      } else {
        console.error("An unknown error occurred");
      }
    }
  };
  
  useEffect(() => {
    fetchProfileData();
  }, [selectedFilter]);
  
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
          <div className='w-full grid grid-cols-4 gap-4 cursor-pointer' onClick={handleProfileClick}>
            {profileData.map((profile) => (
              <MiniProfile
                key={profile.id}
                profile={profile}
              />
            ))}
          </div>
          </div>
        </div>
      </div>
    </div>
  );
}