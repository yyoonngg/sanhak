"use client";
import React, {useState} from 'react';
import UserProfile from './UserProfile';
import RoadmapCustomize from './RoadmapCustomize';
import {AllKindOfRoadmapSkills, RoadmapSkill, SkillDetail} from "@/models/skill";

// 스킬 상태에 대한 enum
enum SkillStatus {
  IN_PROGRESS = "in-progress",
  COMPLETED = "completed",
  NOT_STARTED = "not-started"
}

// TODO: API 연결 -> 유저의 커스텀 로드맵 리스트
const customRoadmapList: CustomRoadmap[] = [
  {id: 1, name: "나의 엄청난엄청난엄청난엄청난 로드맵"},
  {id: 2, name: "카카오 로드맵"},
  {id: 3, name: "삼성 로드맵"},
];

// TODO: API 연결 -> 카테고리별 모든 스킬 리스트
const allCategorySkills: AllKindOfRoadmapSkills[] = [
  { category:"frontend",
    skills: [
    { id: 1, name: 'HTML', child: [27, 28, 29], position: [0, 0] ,tag:'basic'},
    { id: 2, name: 'CSS', child: [5, 6], position: [1, 0] ,tag:'basic'},
    { id: 3, name: 'JavaScript', child: [4, 7, 8, 9], position: [3, 0],tag:'basic' },
    { id: 4, name: 'TypeScript', parent:[3], child: [7, 8, 9], position: [4, 0] ,tag:'basic'},
  ]},
  { category:"backend",
    skills: [
    { id: 5, name: 'Tailwind', parent: [2], child:[10], position: [1, 1] ,tag:'framework'},
    { id: 6, name: 'Bootstrap', parent: [2], position: [2, 1] ,tag:'framework'},
    { id: 7, name: 'React', parent: [3], child:[11, 12, 13], position: [3, 1],tag:'framework' },
    { id: 8, name: 'Angular', parent: [3], child:[14], position: [6, 1] ,tag:'framework'},
    { id: 9, name: 'Vue.js', parent: [3], child:[15, 16, 17], position: [7, 1] ,tag:'framework'},
  ]},
  { category:"data",
    skills: [
    { id: 10, name: 'SASS', parent: [5], child:[18], position: [1, 2] ,tag:'framework'},
    { id: 11, name: 'React Hooks', parent: [7],  child:[19, 20, 21], position: [3, 2],tag:'none' },
    { id: 12, name: 'Redux', parent: [7], child:[19, 20, 21], position: [4, 2] ,tag:'none'},
    { id: 13, name: 'Recoil', parent: [7], child:[19, 20, 21], position: [5, 2] ,tag:'none'},
    { id: 14, name: 'RxJS', parent: [8], child:[19, 20, 21], position: [6, 2] ,tag:'none'},
    { id: 15, name: 'VueX', parent: [9], child:[19, 20, 21], position: [7, 2] ,tag:'none'},
    { id: 16, name: 'Pinia', parent: [9], child:[19, 20, 21], position: [8, 2] ,tag:'none'},
    { id: 17, name: 'Vite', parent: [9], child:[19, 20, 21], position: [9, 2] ,tag:'none'},
  ]},
  { category:"security",
    skills: [
    { id: 22, name: 'Webpack', parent: [19, 20, 21], child:[24, 25, 26], position: [3, 4],tag:'none' },
    { id: 23, name: 'GraphQL', parent: [19, 20, 21], child:[24, 25, 26], position: [4, 4] ,tag:'connection'},
  ]},
  { category:"application",
    skills: [
    { id: 24, name: 'Cypress', parent: [22, 23], child:[27, 28, 29], position: [3, 5],tag:'test' },
    { id: 25, name: 'Jest', parent: [22, 23], child:[27, 28, 29], position: [4, 5] ,tag:'test'},
    { id: 26, name: 'MobX', parent: [22, 23], child:[27, 28, 29], position: [5, 5] ,tag:'none'},
  ]},
];

// TODO: API 연결 -> customRoadmapList에서 선택된 로드맵의 id로 api호출
const selectedSkills: RoadmapSkill[] = [
  { id: 1, name: 'HTML', child: [27, 28, 29], position: [0, 0] ,tag:'basic'},
  { id: 2, name: 'CSS', child: [5, 6], position: [1, 0] ,tag:'basic'},
  { id: 3, name: 'JavaScript', child: [4, 7, 8, 9], position: [3, 0],tag:'basic' },
  { id: 4, name: 'TypeScript', parent:[3], child: [7, 8, 9], position: [4, 0] ,tag:'framework'},

  { id: 5, name: 'Tailwind', parent: [2], child:[10], position: [1, 1] ,tag:'framework'},
  { id: 6, name: 'Bootstrap', parent: [2], position: [2, 1] ,tag:'framework'},
  { id: 7, name: 'React', parent: [3], child:[11, 12, 13], position: [3, 1],tag:'framework' },
  { id: 8, name: 'Angular', parent: [3], child:[14], position: [6, 1] ,tag:'framework'},
  { id: 9, name: 'Vue.js', parent: [3], child:[15, 16, 17], position: [7, 1] ,tag:'framework'},

  { id: 10, name: 'SASS', parent: [5], child:[18], position: [1, 2] ,tag:'framework'},
  { id: 11, name: 'React Hooks', parent: [7],  child:[19, 20, 21], position: [3, 2],tag:'none' },
  { id: 12, name: 'Redux', parent: [7], child:[19, 20, 21], position: [4, 2] ,tag:'none'},
  { id: 13, name: 'Recoil', parent: [7], child:[19, 20, 21], position: [5, 2] ,tag:'none'},
  { id: 14, name: 'RxJS', parent: [8], child:[19, 20, 21], position: [6, 2] ,tag:'none'},
  { id: 15, name: 'VueX', parent: [9], child:[19, 20, 21], position: [7, 2] ,tag:'none'},
  { id: 16, name: 'Pinia', parent: [9], child:[19, 20, 21], position: [8, 2] ,tag:'none'},
  { id: 17, name: 'Vite', parent: [9], child:[19, 20, 21], position: [9, 2] ,tag:'none'},

  { id: 18, name: 'PostCSS', parent: [10], child:[27, 28, 29], position: [1, 3],tag:'none' },
  { id: 19, name: 'Axios', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [3, 3] ,tag:'connection'},
  { id: 20, name: 'Web Socket', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [4, 3] ,tag:'none'},
  { id: 21, name: 'ESLint', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [5, 3] ,tag:'none'},

  { id: 22, name: 'Webpack', parent: [19, 20, 21], child:[24, 25, 26], position: [3, 4] ,tag:'none'},
  { id: 23, name: 'GraphQL', parent: [19, 20, 21], child:[24, 25, 26], position: [4, 4] ,tag:'connection'},

  { id: 24, name: 'Cypress', parent: [22, 23], child:[27, 28, 29], position: [3, 5],tag:'test' },
  { id: 25, name: 'Jest', parent: [22, 23], child:[27, 28, 29], position: [4, 5] ,tag:'test'},
  { id: 26, name: 'MobX', parent: [22, 23], child:[27, 28, 29], position: [5, 5] ,tag:'none'},

  { id: 27, name: 'Vercel', parent: [1, 18, 24, 25, 26], position: [1, 6],tag:'none' },
  { id: 28, name: 'AWS S3', parent: [1, 18, 24, 25, 26], position: [2, 6] ,tag:'none'},
  { id: 29, name: 'Netlify', parent: [1, 18, 24, 25, 26], position: [3, 6] ,tag:'none'},
];

// HTML 상세설명 정보 mock 데이터 -> To be deleted
const htmlMockData: SkillDetail = { 
  id: 1, 
  name: "HTML", 
  description: "html은 하이퍼 텍스트 마크업 언어HTML은 웹 페이지의 구조를 정의하는 마크업 언어입니다. 모든 웹 페이지의 기본 골격을 형성하며, 텍스트, 이미지, 비디오 등 다양한 콘텐츠를 브라우저에 표시할 수 있도록 구성합니다. 프론트엔드 개발에서 필수적으로 알아야 할 언어 중 하나로, CSS 및 JavaScript와 결합하여 완성도 있는 웹 페이지를 만들 수 있습니다.", 
  list: [
    {
      title: "HTML 기본 개념",
      subtitle: ["HTML이란 무엇인가", "HTML과 다른 웹 기술 (CSS, Javascript) 간의 관계", "브라우저에서의 HTML 해석 방식"],
      status: SkillStatus.COMPLETED,
    },
    {
      title: "HTML 태그와 속성",
      subtitle: ["HTML 기본 태그 (<html>, <head>, <body>, <div>, <p>, <h1> ~ <h6>, <a>, <img> 등)", "HTML 속성 사용법 (id, class, src, href, alt 등)", "HTML 의미론적 태그 사용법 (<header>, <nav>, <footer>, <article> 등)"],
      status: SkillStatus.COMPLETED,
    },
    {
      title: "HTML5의 새로운 기능",
      subtitle: ["HTML5에서 추가된 새로운 태그와 속성", "멀티미디어 관련 태그 (<video>, <audio>, <canvas>)", '폼 요소 개선 (<input type="email">, <input type="number">, <datalist>)'],
      status: SkillStatus.IN_PROGRESS,
    },
    {
      title: "SEO 및 웹 접근성 개선",
      subtitle: ["시멘틱 태그와 SEO의 관계", "aria-* 속성을 활용한 접근성 향상"],
      status: SkillStatus.NOT_STARTED,
    },
    {
      title: "실습",
      subtitle: ["HTML 웹 페이지 만들기", "포트폴리오 사이트 구조 만들기"],
      status: SkillStatus.NOT_STARTED,
    },
  ] 
}

export default function MypagePage() {
  const [updatedRoadmap, setUpdatedRoadmap] = useState<RoadmapSkill[]>(selectedSkills);
  const [skillDetailData, setSkillDetailData] = useState<SkillDetail>(htmlMockData);

  const [selectedRoadmap, setSelectedRoadmap] = useState<string>(customRoadmapList[0].name);
  const [roadmapSkills, setRoadmapSkills] = useState<RoadmapSkill[]>(selectedSkills);
  // 1. 사이드 선택창에서 스킬을 선택하여 편집창에 노드를 추가할 때
  // 2. 노드 2개를 선택해서 선후관계가 정해질 때
  const handleUpdateRoadmap = (newSkill: RoadmapSkill) => {
    setUpdatedRoadmap(prevSkills => {
      const skillExists = prevSkills.some(skill => skill.id === newSkill.id);
      if(skillExists) { // 2번 케이스
        return prevSkills.map(skill => 
          skill.id === newSkill.id ? newSkill : skill
        )
      } 
      else {            // 1번 케이스
        return [...prevSkills, newSkill]; 
      }
    });
  };

  // TODO: API 연결 -> 해당 커스텀 로드맵을 구성하는 스킬 리스트을 저장
  const onSaveRoadmap = async () => {
    try {
      const currentRoadmap = customRoadmapList.find(roadmap => roadmap.name === selectedRoadmap);

      if (!currentRoadmap) {
        throw new Error('선택된 로드맵을 찾을 수 없습니다.');
      }

      const response = await fetch('http://localhost:8080/api/roadmap/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include', // 쿠키 포함
        body: JSON.stringify({
          customRoadmapName: currentRoadmap.name,
        }),
      });

      if (!response.ok) {
        throw new Error('로드맵 저장에 실패했습니다.');
      }

      const data = await response.json();
      console.log('로드맵이 성공적으로 저장되었습니다:', data);
      // 상태 업데이트 또는 사용자에게 성공 메시지 표시
    } catch (error) {
      console.error('로드맵 저장 중 오류 발생:', error);
      // 사용자에게 오류 메시지 표시
    }
  };








  // TODO: API 연결 -> id에 맞는 해당 스킬의 상세설명 가져오기
  const getSelectDetail = () => {
    console.log("스킬 상세정보 가져오기");
  } 

  return (
    <div className="w-full h-full flex flex-col items-center mt-5">
      <div className='w-[1400px] h-full'>
        <div className='w-full flex flex-col px-24 pb-10 border-b border-gray-cc'>
          <div className='font-bold text-2xl mb-5'>마이페이지</div>
          <UserProfile/>
        </div>
        <div className='w-full h-full flex flex-col px-24 pt-10 mb-10'>
          <RoadmapCustomize 
            customRoadmapList={customRoadmapList} 
            allCategorySkills={allCategorySkills} 
            skills={updatedRoadmap} 
            handleUpdateRoadmap={handleUpdateRoadmap}
            onSaveRoadmap={onSaveRoadmap}
            getSelectDetail={getSelectDetail}
            skillDetailData={skillDetailData}
            defaultTag='none'
          />
        </div>
      </div>
    </div>
  );
}