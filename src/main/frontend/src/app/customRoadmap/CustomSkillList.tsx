"use client";
import { AllKindOfSkills } from '@/models/skill';
import React, { useState } from 'react';

type CustomSkillListProps = {
  skillData: AllKindOfSkills[];
  onSelectSkill?: (skillName: string) => void;
};

const categoryLabels: Record<string, string> = {
  frontend: '웹/프론트엔드',
  backend: '웹/백엔드',
  data: '데이터사이언스',
  security: '보안',
  application: '어플리케이션',
};


export default function CustomSkillList({
  skillData,
  onSelectSkill
}: CustomSkillListProps) {
  const [selectedCategory, setSelectedCategory] = useState<AllKindOfSkills | null>(null);
  
  const handleSelectedCate = (category: string) => {
    const selected = skillData.find((skill) => skill.category === category);
    if(selected && selected === selectedCategory){
      setSelectedCategory(null);
    } else if(selected) {
      setSelectedCategory(selected);
    }
  };

  const handleSelectedSkill = (skillName: string) => {
    console.log(skillName);
    if(onSelectSkill){
      onSelectSkill(skillName);
    }
  };

  return (
    <div className="w-[250px] h-full text-sm py-4 bg-gray-f8">
      <div className="w-full mb-2 px-4 font-gmarketsansMedium text-lg">직무별 스킬 목록</div>
      <div className='w-full h-full overflow-auto scrollbar flex flex-col'>
      {skillData.map((cate) => (
        <div className='w-full' key={cate.category}>
          <div
            className={`${
              cate.category === selectedCategory?.category &&
              "bg-white rounded-lg border-2 border-gray-99"
            } w-[220px] h-16 flex justify-start items-center bg-white border border-gray-d9 rounded-lg mb-2 mx-4 py-1 px-2 cursor-pointer`}
            onClick={() => handleSelectedCate(cate.category)}
          >
            <img
              src={
                cate.category === selectedCategory?.category
                  ? "asset/png/icon_arrow_up.png"
                  : "asset/png/icon_arrow_down.png"
              }
              className="w-4 mr-3"
            />
            {categoryLabels[cate.category]}
          </div>

          {cate.category === selectedCategory?.category &&
            selectedCategory.skills.map((skill) => (
              <div
                key={skill.id}
                className="w-10/12 ml-auto mr-auto py-2 px-3 mb-2 bg-gray-f2 rounded-md border border-gray-d9 hover:bg-white cursor-pointer"
                onClick={() => handleSelectedSkill(skill.name)}
              >
                {skill.name}
              </div>
            ))}
        </div>
      ))}
      </div>
    </div>
  );
};
