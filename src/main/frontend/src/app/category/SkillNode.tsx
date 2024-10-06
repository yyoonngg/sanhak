import React from 'react';

type SkillNodeProps = {
  skill: RoadmapSkill,
  scale: number,
}
const SkillNode = ({
  skill,
  scale
}: SkillNodeProps) => {
  const imageSkillName = skill.name.toLowerCase().replace(/\s+/g, '').replace(/\./g, ''); // 소문자, 공백제거, "."제거
  const imageSrc = `/asset/png/frontend/${imageSkillName}_img.png`;
  return (
    <g transform={`translate(${skill.position[0] * scale}, ${skill.position[1] * scale})`}>
      <rect 
        x={-50} // 테두리의 x 위치
        y={-50} // 테두리의 y 위치
        width={100} // 테두리의 너비
        height={100} // 테두리의 높이
        fill="#FFFFFF" // 배경색
        stroke="#343434" // 테두리 색상
        strokeWidth={1} // 테두리 두께
        rx={8} // 수평 반경 (모서리 둥글기)
        ry={8} // 수직 반경 (모서리 둥글기)
      />
      <image 
        href={imageSrc}
        x={-25} // 이미지의 x 위치
        y={-30} // 이미지의 y 위치
        width={50} // 이미지의 너비
        height={50} // 이미지의 높이
      />
      <text 
        x={0} 
        y={40}
        fill="black"
        fontSize='20px'
        textAnchor="middle"
      >
        {skill.name}
      </text>
    </g>
  );
}

export default SkillNode;