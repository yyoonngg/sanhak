import React from 'react';

type SkillBadgeProps = {
  skill: UserSkill
};
const SkillBadge = ({
 skill
}: SkillBadgeProps) => {
  const image_skill_name = skill.name.toLowerCase().replace(/\s+/g, '').replace(/\./g, '').replace(/#/g, 'sharp'); // 소문자, 공백제거, "."제거
  const image_src = `/asset/png/skill/${image_skill_name}_img.png`;
  const bg_shadow = 'shadow-[4px_4px_10px_#8247FF,4px_4px_20px_#EF39FF,4px_4px_30px_#FFC839]';
  
  return (
    <div className={`w-[70px] h-[70px] flex flex-col items-center justify-between bg-white p-2 m-4 bg-gray-200 rounded-3xl ${bg_shadow}`}>
      <div className='w-10 h-10'>
        <img className='w-full h-full object-contain' src={image_src} alt={skill.name}/>
      </div>
      <div className='h-4 font-bold text-xs'>{skill.name}</div>
    </div>
  )
};


export default SkillBadge;