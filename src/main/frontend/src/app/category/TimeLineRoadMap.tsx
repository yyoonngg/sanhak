//컴포넌트1 가로로 정렬된 로드맵 : 들어가야하는 직무별 사진은 href 형태로...?
//컴포넌트 2 전체 카드 폼 형식 : 직무 카테고리 별 아이콘은 서로 다르게 짤수있도록

//import RoadmapSkill from "@/app/category/Roadmap";


// export enum TimeLineTag{
//     0="None",
//     1="기본언어",
//     2="확장프레임워크",
//     3="통신및api",
//     4="테스트",
//
// }

//export function timeLineSkills(skillParams: RoadmapSkill[]) {
//    const selectedSkills = skillParams.filter((skill) => skill.tag !== 0 && skill.tag !== undefined);
//    const groupedSkills = selectedSkills.reduce((group, skill) => {
//        const tagIndex = skill.tag!;
//        if (!group[tagIndex]) {
//            group[tagIndex] = [];
//        }
//        group[tagIndex].push(skill);
//        return group;
//    }, {} as { [key: number]: RoadmapSkill[] });

//    for (const tagIndex in groupedSkills) {
//        groupedSkills[tagIndex].sort((a, b) => a.id - b.id);
//    }
//
//    return groupedSkills;
//}