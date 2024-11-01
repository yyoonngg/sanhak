'use client';

import React, {memo, useMemo} from 'react';
import SkillNode from './SkillNode';
import { RoadmapSkill } from '@/models/skill';

function TimelineRoadmap({ skills }: { skills: RoadmapSkill[] }) {
    //tag값이 none이 아닌 스킬들만 선택
    const selectedSkills = useMemo(() =>{
        return skills.filter(skill => skill.tag !== "none");
        },[skills]);

    const sortedSkills = useMemo(() => {
        return selectedSkills.map((skill, index) => {
            const x = (index % 5) * 150; // X 좌표를 5개의 열로 나누어 설정
            const y = Math.floor(index / 5) * 100; // 행마다 100px 간격으로 배치
            return {
                ...skill,
                position: [x, y] as [number,number]
            };
        });
    }, [selectedSkills]);

    const svgWidth = 5*150
    const svgHeight = Math.ceil(sortedSkills.length/5) *100;

    return (
        <div className="w-full flex flex-col items-center">
            <div className="font-bold">Timeline Roadmap</div>
            <div className="font-semibold text-category-front">Web_FrontEnd</div>
            <div className="w-full h-64 flex justify-center mt-4">

                <svg width="100%" height="svgHeight" viewBox={`0 0 ${svgWidth} ${svgHeight}`} className="flex justify-center items-center">
                    {sortedSkills.map((skill,index) => (
                        <SkillNode
                            key={skill.id}
                            skill={skill}
                            scale={1}
                            isSelected={false}
                            onSelect={undefined}
                            onDrag={undefined}
                        />

                    ))}
                </svg>
            </div>
        </div>
    );
}

export default TimelineRoadmap;

