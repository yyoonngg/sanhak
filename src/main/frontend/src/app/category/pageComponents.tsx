'use client';

import React, {useEffect, useState} from 'react';
import Roadmap from './Roadmap';
import {RoadmapSkill} from "@/models/skill";
import TimelineRoadmap from "@/app/category/TimeLineRoadMap";

const mockRoadmapSkills: RoadmapSkill[] = [
    { id: 1, name: 'HTML', child: [27, 28, 29], position: [0, 0], tag:'basic'},
    { id: 2, name: 'CSS', child: [5, 6], position: [1, 0]  ,tag:'basic'},
    { id: 3, name: 'JavaScript', child: [4, 7, 8, 9], position: [3, 0] ,tag:'basic'},
    { id: 4, name: 'TypeScript', parent:[3], child: [7, 8, 9], position: [4, 0] ,tag:'basic'},

    { id: 5, name: 'Tailwind', parent: [2], child:[10], position: [1, 1] ,tag:'framework'},
    { id: 6, name: 'Bootstrap', parent: [2], position: [2, 1] ,tag:'none'},
    { id: 7, name: 'React', parent: [3], child:[11, 12, 13], position: [3, 1],tag:'framework' },
    { id: 8, name: 'Angular', parent: [3], child:[14], position: [6, 1] ,tag:'framework'},
    { id: 9, name: 'Vue.js', parent: [3], child:[15, 16, 17], position: [7, 1] ,tag:'framework'},

    { id: 10, name: 'SASS', parent: [5], child:[18], position: [1, 2],tag:'framework' },
    { id: 11, name: 'React Hooks', parent: [7],  child:[19, 20, 21], position: [3, 2],tag:'none' },
    { id: 12, name: 'Redux', parent: [7], child:[19, 20, 21], position: [4, 2] ,tag:'none'},
    { id: 13, name: 'Recoil', parent: [7], child:[19, 20, 21], position: [5, 2] ,tag:'none'},
    { id: 14, name: 'RxJS', parent: [8], child:[19, 20, 21], position: [6, 2] ,tag:'none'},
    { id: 15, name: 'VueX', parent: [9], child:[19, 20, 21], position: [7, 2] ,tag:'none'},
    { id: 16, name: 'Pinia', parent: [9], child:[19, 20, 21], position: [8, 2] ,tag:'none'},
    { id: 17, name: 'Vite', parent: [9], child:[19, 20, 21], position: [9, 2] ,tag:'none'},

    { id: 18, name: 'PostCSS', parent: [10], child:[27, 28, 29], position: [1, 3] ,tag:'none'},
    { id: 19, name: 'Axios', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [3, 3] ,tag:'connection'},
    { id: 20, name: 'Web Socket', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [4, 3] ,tag:'none'},
    { id: 21, name: 'ESLint', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [5, 3],tag:'none' },

    { id: 22, name: 'Webpack', parent: [19, 20, 21], child:[24, 25, 26], position: [3, 4] ,tag:'none'},
    { id: 23, name: 'GraphQL', parent: [19, 20, 21], child:[24, 25, 26], position: [4, 4] ,tag:'connection'},

    { id: 24, name: 'Cypress', parent: [22, 23], child:[27, 28, 29], position: [3, 5] ,tag:'test'},
    { id: 25, name: 'Jest', parent: [22, 23], child:[27, 28, 29], position: [4, 5] ,tag:'test'},
    { id: 26, name: 'MobX', parent: [22, 23], child:[27, 28, 29], position: [5, 5] ,tag:'none'},

    { id: 27, name: 'Vercel', parent: [1, 18, 24, 25, 26], position: [1, 6] ,tag:'none'},
    { id: 28, name: 'AWS S3', parent: [1, 18, 24, 25, 26], position: [2, 6] ,tag:'none'},
    { id: 29, name: 'Netlify', parent: [1, 18, 24, 25, 26], position: [3, 6] ,tag:'none'},
];

export default function CategoryPage() {
  const [roadmapSkills, setRoadmapSkills] = useState<RoadmapSkill[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<boolean>(false);

  useEffect(() => {
    fetch('http://localhost:8080/api/category')
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            if (Array.isArray(data)) {
                const formattedData = data.map((item: any) => ({
                    id: item.id,
                    name: item.name,
                    parent: item.parent,
                    child: item.child,
                    position: [item.position[0], item.position[1]] as [number, number],
                    tag:item.tag,
                }));
                setRoadmapSkills(formattedData);
            } else {
                console.error('API 응답이 배열이 아닙니다:', data);
                setRoadmapSkills(mockRoadmapSkills);
                setError(true);
            }
            setLoading(false);
        })
        .catch((error) => {
          console.error('데이터를 가져오는 중 오류 발생:', error);
          setRoadmapSkills(mockRoadmapSkills);
          setError(true);
          setLoading(false);
        });
  }, []);

  if (loading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className="w-full h-screen flex flex-col items-center">
      <div className="w-[1400px] h-full">
        <div className='w-full px-24'>
          <TimelineRoadmap skills={roadmapSkills}/>
        </div>
        <div className='w-full flex flex-col px-24'>
          <div className="flex flex-col">
            <div className="font-bold">RoadMap</div>
            <div className="font-semibold text-category-front mb-5">Web_FrontEnd</div>
          </div>
          <Roadmap isEditMode={false} roadmapSkills={roadmapSkills} />
        </div>
      </div>
    </div>
  );
}