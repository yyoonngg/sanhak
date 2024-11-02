'use client';

import React, { memo, useMemo } from 'react';
import SkillNode from './SkillNode';
import { RoadmapSkill } from '@/models/skill';

function TimelineRoadmap({ skills }: { skills: RoadmapSkill[] }) {
    const filteredSkills = useMemo(() => {
        return skills.filter(skill => skill.tag !== "none");
    }, [skills]);

    // tag별로 그룹화하고, 같은 tag 내에서는 id 순서대로 정렬
    const groupedSkills = useMemo(() => {
        const groups: { [tag: string]: RoadmapSkill[] } = {};

        filteredSkills.forEach(skill => {
            if (!groups[skill.tag]) {
                groups[skill.tag] = [];
            }
            groups[skill.tag].push(skill);
        });


        return groups;
    }, [filteredSkills]);

    // SVG의 가로 위치 계산을 위한 기준값들
    const itemSpacing = 110; // 그룹 내 아이템 간격 (좁게 설정해 tag별로 가깝게 배치)
    const groupSpacing =150;
    let currentXOffset = 0; // 그룹의 x 위치를 추적하기 위한 변수


    return (
        <div className="box-border justify-between my-10 mx-20 flex bg-category-front rounded-lg">
            <div className="w-1/3 bg-dark-light rounded-s-lg flex justify-center p-10">
                <img src="/asset/png/category/frontend_img.png" alt ="frontend"></img>
            </div>
            <div className="flex flex-col items-start w-full p-4 ">
                <div className="font-bold">웹/프론트엔드 개발자</div>
                <div className="font-light">유저와 가장 가까운 개발자</div>
            <svg
                width="100%"
                height="80%"
                viewBox="-50 -100 2000 300" // xmin, ymin, width, height
                className="flex items-center"

            >
                {Object.keys(groupedSkills).map((tag, groupIndex) => {
                    const skills = groupedSkills[tag];
                    const xOffset = currentXOffset;

                    // 다음 그룹의 시작 위치를 계산
                    currentXOffset += skills.length * itemSpacing + groupSpacing;

                    return (
                        <g key={tag} transform={`translate(${xOffset},0)`}>
                            {/* 각 태그 이름을 표시 (태그 그룹의 왼쪽에 위치) */}
                            <text x={-40} y={-60} textAnchor="start" fontSize="16" fontWeight="bold" fill="#ffffff">
                                {tag}
                            </text>
                            {/* 각 스킬을 가로로 일렬로 배치 (itemIndex * itemSpacing 만큼 오른쪽으로 이동) */}
                            {skills.map((skill, itemIndex) => (
                                <g key={skill.id} transform={`translate(${itemIndex * itemSpacing}, 0)`}>
                                    <SkillNode
                                        skill={skill}
                                        scale={0.5}
                                        isSelected={false}
                                        onSelect={undefined}
                                        onDrag={undefined}
                                    />
                                </g>
                            ))}
                        </g>
                    );
                })}
            </svg>
            </div>
        </div>
    );
}
export default TimelineRoadmap;

