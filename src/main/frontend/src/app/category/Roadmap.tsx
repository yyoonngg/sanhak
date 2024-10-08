import React from 'react';
import SkillNode from './SkillNode';
import {timeLineSkills} from "@/app/category/TimeLineRoadMap";
import {tag} from "postcss-selector-parser";
// import {TimeLineTag} from "@/app/category/TimeLineRoadMap";

const ROADMAP_SCALE = 200;

// TODO: API 연결
const roadmapSkills: RoadmapSkill[] = [
  { id: 1, name: 'HTML', child: [27, 28, 29], position: [0, 0], tag:1},
  { id: 2, name: 'CSS', child: [5, 6], position: [1, 0], tag:1 },
  { id: 3, name: 'JavaScript', child: [4, 7, 8, 9], position: [3, 0],tag:1},
  { id: 4, name: 'TypeScript', parent:[3], child: [7, 8, 9], position: [4, 0], tag:2},

  { id: 5, name: 'Tailwind', parent: [2], child:[10], position: [1, 1],tag:2 },
  { id: 6, name: 'Bootstrap', parent: [2], position: [2, 1],tag:2 },
  { id: 7, name: 'React', parent: [3], child:[11, 12, 13], position: [3, 1],tag:2 },
  { id: 8, name: 'Angular', parent: [3], child:[14], position: [6, 1],tag:2},
  { id: 9, name: 'Vue.js', parent: [3], child:[15, 16, 17], position: [7, 1],tag:2 },

  { id: 10, name: 'SASS', parent: [5], child:[18], position: [1, 2],tag:2 },
  { id: 11, name: 'React Hooks', parent: [7],  child:[19, 20, 21], position: [3, 2],tag:0 },
  { id: 12, name: 'Redux', parent: [7], child:[19, 20, 21], position: [4, 2],tag:0 },
  { id: 13, name: 'Recoil', parent: [7], child:[19, 20, 21], position: [5, 2],tag:0 },
  { id: 14, name: 'RxJS', parent: [8], child:[19, 20, 21], position: [6, 2],tag:0 },
  { id: 15, name: 'VueX', parent: [9], child:[19, 20, 21], position: [7, 2], tag:0},
  { id: 16, name: 'Pinia', parent: [9], child:[19, 20, 21], position: [8, 2] ,tag:0},
  { id: 17, name: 'Vite', parent: [9], child:[19, 20, 21], position: [9, 2],tag:0},

  { id: 18, name: 'PostCSS', parent: [10], child:[27, 28, 29], position: [1, 3],tag:0},
  { id: 19, name: 'Axios', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [3, 3],tag:3 },
  { id: 20, name: 'Web Socket', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [4, 3] ,tag:0},
  { id: 21, name: 'ESLint', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [5, 3] ,tag:0},

  { id: 22, name: 'Webpack', parent: [19, 20, 21], child:[24, 25, 26], position: [3, 4],tag:0 },
  { id: 23, name: 'GraphQL', parent: [19, 20, 21], child:[24, 25, 26], position: [4, 4],tag:3 },

  { id: 24, name: 'Cypress', parent: [22, 23], child:[27, 28, 29], position: [3, 5],tag:4 },
  { id: 25, name: 'Jest', parent: [22, 23], child:[27, 28, 29], position: [4, 5] ,tag:4},
  { id: 26, name: 'MobX', parent: [22, 23], child:[27, 28, 29], position: [5, 5] ,tag:0},

  { id: 27, name: 'Vercel', parent: [1, 18, 24, 25, 26], position: [1, 6] ,tag:0},
  { id: 28, name: 'AWS S3', parent: [1, 18, 24, 25, 26], position: [2, 6] ,tag:0},
  { id: 29, name: 'Netlify', parent: [1, 18, 24, 25, 26], position: [3, 6] ,tag:0},
];

const drawPath = (parent: RoadmapSkill, child: RoadmapSkill) => {
  const [x1, y1] = parent.position;
  const [x2, y2] = child.position;

  let pathData; 

  if (y1 === y2) {
    pathData = `
      M ${x1 * ROADMAP_SCALE} ${y1 * ROADMAP_SCALE}              
      L ${x2 * ROADMAP_SCALE} ${y2 * ROADMAP_SCALE}    
    `;
  } else {
    pathData = `
      M ${x1 * ROADMAP_SCALE} ${y1 * ROADMAP_SCALE}              
      L ${x1 * ROADMAP_SCALE} ${(y2 - 0.5) * ROADMAP_SCALE}   
      L ${x2 * ROADMAP_SCALE} ${(y2 - 0.5) * ROADMAP_SCALE}    
      L ${x2 * ROADMAP_SCALE} ${y2 * ROADMAP_SCALE}            
    `;
  }

  return <path d={pathData} stroke="#9E9EA7" strokeWidth={2} fill="none" />;
};

// TODO: 데이터 수에 따라 svg 크기가 잘 맞는지 확인
const getViewBox = () => {
  let minX = Infinity;
  let minY = Infinity;
  let maxX = -Infinity;
  let maxY = -Infinity;

  roadmapSkills.forEach(skill => {
    const [x, y] = skill.position;
    minX = Math.min(minX, x);
    minY = Math.min(minY, y);
    maxX = Math.max(maxX, x);
    maxY = Math.max(maxY, y);
  });
  const padding = 100;
  return `${minX * 100 - padding} ${minY * 100 - padding} ${ (maxX - minX) * 200 + 2 * padding} ${(maxY - minY) * 200 + 2 * padding}`;
};

const timeLine = () => {
  return timeLineSkills(roadmapSkills);
};

export default function Roadmap() {
  return (
      <div className='w-3/4 h-3/4 border border-2 border-dashed p-5'>
        <div>
          {Object.entries(timeLine()).map(([tag, skills]) => (
              <div key={tag}>
                <ul>
                  {skills.map(skill => (
                      <li key={skill.id}>{skill.id}</li>
                  ))}
                </ul>
              </div>
          ))}
        </div>
        <div className='flex flex-col'>
          <div className='font-bold text-3xl font-helveticaBold'>RoadMap</div>
          <div className='font-semibold text-category-front text-xl font-helvetica'>Web_FrontEnd</div>
        </div>
        <svg width="100%" height="95%" viewBox={getViewBox()} preserveAspectRatio="xMinYMin meet">
          {roadmapSkills.map((skill) => {
            if (skill.child) {
              return skill.child.map((childId) => {
                const childSkill = roadmapSkills.find((s) => s.id === childId);
                if (childSkill) {
                  return drawPath(skill, childSkill);
                }
                return null;
              });
            }
            return null;
          })}

          {roadmapSkills.map((skill) => (
              <SkillNode key={skill.id} skill={skill} scale={ROADMAP_SCALE}/>
          ))}
        </svg>
      </div>
  );
}

