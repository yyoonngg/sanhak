import React, { useEffect, useState } from 'react';
import SkillBadge from './SkillBadge';

type UserProfileProps = {
  userInfo?: User
  badgeInfo?: UserSkill[]
  onSave: (updateProfileBody: UpdateUserProfile) => void;
};

const categoryLabels: Record<string, string> = {
  frontend: '웹/프론트엔드',
  backend: '웹/백엔드',
  data: '데이터사이언스',
  security: '보안',
  application: '어플리케이션',
  default: '예비' // null인 경우
};

export default function UserProfile({
  userInfo,
  badgeInfo,
  onSave
}:UserProfileProps) {
  const [isEditing, setIsEditing] = useState(false); 
  const [editedName, setEditedName] = useState(userInfo?.name || ''); 
  const [selectedCategory, setSelectedCategory] = useState(userInfo?.desirePosition || ''); 
  const categories = ['웹/프론트엔드', '웹/백엔드', '데이터사이언스', '보안', '어플리케이션', '예비'];
  const [profileImg, setProfileImg] = useState(userInfo?.profileImgURL || '/asset/png/profile_default_image.png');
  const [profileImgBlob, setProfileImgBlob] = useState('');

  const changeProfileMode = () => {
    setIsEditing((prev) => !prev); 
  };

  const handleNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEditedName(event.target.value); 
  };

  const handleCategoryChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedCategory(event.target.value);
  };

  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      const imageUrl = URL.createObjectURL(file);
      setProfileImg(imageUrl); // 이미지 URL을 상태에 저장

      const reader = new FileReader();
      reader.onloadend = () => {
        setProfileImgBlob(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  const saveProfile = () => {
    const categoryKey = Object.keys(categoryLabels).find(
      (key) => categoryLabels[key] === selectedCategory
    );

    if (!categoryKey) {
      console.error('Invalid category selected');
      return;
    }

    // profile 데이터 생성
    const profileData: User = {
      id: userInfo!.id, 
      bio: userInfo?.bio || '', 
      profileImgURL: profileImg, 
      name: editedName, 
      email: userInfo!.email, 
      desirePosition: categoryKey, 
      badge_cnt: userInfo!.badge_cnt, 
      roadmap_cnt: userInfo!.roadmap_cnt, 
      card_cnt: userInfo!.card_cnt, 
    };

    const updateProfileBody: UpdateUserProfile = {
      profile: profileData,
      image: profileImgBlob,
    };

    console.log(updateProfileBody);
    onSave(updateProfileBody);
    setIsEditing(false); // 편집 모드 종료
  };

  useEffect(()=>{
    if(userInfo?.name){
      setEditedName(userInfo?.name); 
    }

    if (userInfo?.desirePosition != null) {
      setSelectedCategory(categoryLabels[userInfo.desirePosition] || '');
    } else {
      setSelectedCategory('예비');
    }

    if (userInfo?.profileImgURL == 'default') {
      setProfileImg('/asset/png/profile_default_image.png');
    } else if (userInfo?.profileImgURL) {
      setProfileImg(userInfo.profileImgURL);
    }
  },[userInfo])

  return (
    <div className='w-full h-fit min-h-64 flex justify-between border-b border-gray-cc pb-5'>
      <div className='w-1/2 flex flex-row items-center justify-between'>
        <div className='w-full flex items-center'>
          <div className="w-64 h-64 object-cover rounded-xl border border-gray-cc">
            {isEditing ? (
              <>
                <input 
                  type="file"
                  accept="image/*"
                  onChange={handleImageChange}
                  className="hidden"
                  id="profileImageInput"
                />
                <label htmlFor="profileImageInput">
                  <img
                    className="w-64 h-64 object-cover rounded-xl cursor-pointer transition-all duration-300 ease-in-out hover:filter hover:blur-sm"
                    src={profileImg}
                    alt="Profile"
                  />
                </label>
              </>
            ) : (
              <img 
                className="w-64 h-64 object-cover rounded-xl"
                src={profileImg}
                alt="Profile"
              />
            )}
          </div>
          <div className='ml-4'>
            <div className="text-3xl font-gmarketsansBold">
              {isEditing ? (
                <input
                  type="text"
                  value={editedName}
                  onChange={handleNameChange}
                  className="w-60 h-10 border border-gray-cc rounded px-2 pb-1 text-3xl font-gmarketsansBold"
                />
              ) : (
                editedName
              )}
            </div>
            <div className="text-2xl font-gmarketsansMedium mt-1">
              {isEditing ? (
                <select
                  value={selectedCategory}
                  onChange={handleCategoryChange}
                  className="w-40 border border-gray-cc rounded px-2 py-1"
                >
                  {categories.map((category) => (
                    <option key={category} value={category}>
                      {category}
                    </option>
                  ))}
                </select>
              ) : (
                `${selectedCategory} 개발자`
              )}
            </div>
            <div className="text-medium font-gmarketsansBold mt-1">
              {userInfo?.email}
            </div>
            <div className='flex mt-2'>
              <div className='flex gap-2 mr-6'>
                <img className='w-6 h-6' src='asset/png/icon_filter_badge.png' />
                <div>{userInfo?.badge_cnt}</div>
              </div>
              <div className='flex gap-2 mr-6'>
                <img className='w-6 h-6' src='asset/png/icon_filter_roadmap.png' />
                <div>{userInfo?.roadmap_cnt}</div>
              </div>
              <div className='flex gap-2 mr-6'>
                <img className='w-6 h-6' src='asset/png/icon_filter_card.png' />
                <div>{userInfo?.card_cnt}</div>
              </div>
            </div>
          </div>
        </div>
        <div 
          className='border-2 border-primary rounded-xl p-2 cursor-pointer'
          onClick={isEditing ? saveProfile : changeProfileMode}
        >
          <img src='/asset/png/icon_modify_profile.png'/>
        </div>
      </div>
      
      <div className='w-[555px] max-h-[256px] bg-primary rounded-xl flex flex-wrap content-start p-4 overflow-y-auto scrollbar'>
        {badgeInfo?.map((skill) => (
            <SkillBadge key={skill.id} skill={skill} />
        ))}
      </div>
    </div>
  );
}