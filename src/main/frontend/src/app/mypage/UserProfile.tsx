import React from 'react';
import SkillBadge from './SkillBadge';

// TODO: API 연결
const user_info: User = {
  id: 1,
  name: "용용",
  profile: "user_profile_1",
  skill_list: [
    {id: 1, name: "HTML"},
    {id: 2, name: "CSS"},
    {id: 3, name: "JavaScript"},
    {id: 4, name: "Typescript"},
    {id: 5, name: "React"},
    {id: 6, name: "Tailwind"},
    {id: 7, name: "BootStrap"},
    {id: 8, name: "Axios"},
    {id: 9, name: "ESLint"},
    {id: 10, name: "Netlify"},
  ]
}
export default function UserProfile() {
  const profile_img_src = `/asset/png/profile/${user_info.profile}.png`;

  return (
    <div className='w-full h-[400px] flex border rounded-xl'>
        <div className="w-1/4 rounded-l-xl flex flex-col items-center justify-between">
            <div className="w-full text-left h-1/6 pt-8 pl-8">
                <div className='flex items-end'>
                    <strong className="block text-xl font-bold">{user_info.name}</strong>
                    <div>님의</div>
                </div>
                <strong className="text-2xl font-bold">개발뱃지</strong>
            </div>
            <div className='w-full h-4/6'>
                <img className="w-full h-full object-contain" src={profile_img_src} alt="Profile" />
            </div>
        </div>
        <div className='w-3/4 bg-primary rounded-r-xl flex flex-wrap content-start p-4'>
            {user_info.skill_list.map((skill) => (
                <SkillBadge key={skill.id} skill={skill} />
            ))}
        </div>
    </div>
  );
}