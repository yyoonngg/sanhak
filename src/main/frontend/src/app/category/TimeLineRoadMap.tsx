//컴포넌트1 가로로 정렬된 로드맵 : 들어가야하는 직무별 사진은 href 형태로...?
//컴포넌트 2 전체 카드 폼 형식 : 직무 카테고리 별 아이콘은 서로 다르게 짤수있도록


import skillNode from "@/app/category/SkillNode";
import SkillNode from "@/app/category/SkillNode";
import RoadmapSkill from "@/app/category/Roadmap";
import roadmapSkills from "@/app/category/Roadmap";

// export enum TimeLineTag{
//     0="None",
//     1="기본언어",
//     2="확장프레임워크",
//     3="통신및api",
//     4="테스트",
//
// }

export function timeLineSkills(skillparam:RoadmapSkill) {
    const selectedSkill = skillparam.filter((skill) =>skillparam.tag!==0 && skillparam.tag!==undefined); //tag값이 정의되어 있고, 0이 아닌 스킬노드를 선택

    //TimeLineRoadmap에 들어갈 스킬 추가
    const groupedSkills = selectedSkill.reduce((group,skill) =>{
        //undefined가 아닌 roadmapskills 그룹의 인덱스를 설정하여 group 이름의 배열에 저장
        const tagIndex = skillparam.tag!;
        if(!group[tagIndex]){
            group[tagIndex] =[];
        }
        group[tagIndex].push(skill);
        return group;

    }, {} as {[key:number]:RoadmapSkill[]});

    //tag값이 같으면 roadmapSkills 배열에서 부여한 id값을 기준으로 오름차순 정렬
    for (const tagIndex in groupedSkills){
        groupedSkills[tagIndex].sort((a,b) => a.id - b.id);
    }
}