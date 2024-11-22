'use client';
import React, { useState } from 'react';
import CustomRoadmapList from './CustomRoadmapList';
import Roadmap from '../category/Roadmap';

// TODO: API 연결 -> 유저의 커스텀 로드맵 리스트
const customRoadmapList: CustomRoadmapName[] = [
  {id: 1, name: "나의 엄청난엄청난엄청난엄청난 로드맵"},
  {id: 2, name: "카카오 로드맵"},
  {id: 3, name: "삼성 로드맵"},
];


const customRoadmap: CustomRoadmapDetail = {
  id: 1,
  name: "나의 엄청난엄청난엄청난엄청난 로드맵",
  skills: [
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
    { id: 29, name: 'Netlify', parent: [1, 18, 24, 25, 26], position: [3, 6] ,tag:'none'}
  ]
};

export default function CustomRoadmapPage() {
  const [selectedRoadmap, setSelectedRoadmap] = useState<CustomRoadmapDetail>(customRoadmap);

  const onSelectRoadmap = () => {
    console.log("저장 2");
  };

  return (
    <div className='w-full h-full flex flex-col items-center'>
      <div className='w-[1400px] h-[90dvh]'>
        <div className='w-full h-full flex px-24'>
          <CustomRoadmapList 
            roadmapData={customRoadmapList}
            selectedRoadmapId={selectedRoadmap.id}
            onSelectRoadmap={onSelectRoadmap}
          />
          <Roadmap />
        </div>
      </div>
    </div>
  );
}