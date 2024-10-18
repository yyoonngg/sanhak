// 기본 스킬
type Skill = {
    id: number;
    name: string;
};

// 기본 스킬 모든 리스트
type AllKindOfSkills = {
    category: string;
    skills: Skill[];
}

// 로드맵 구성을 위한 각 스킬 노드
type RoadmapSkill = {
    id: number;
    name: string;
    parent?: number[];
    child?: number[];
    position: [number, number];
};

// 카테고리별 모든 스킬 리스트
type AllKindOfRoadmapSkills = {
    category: string;
    skills: RoadmapSkill[];
}

// 스킬 목차 구성
type SkillTopic = {
    title: string;
    subtitle: string[];
    status: SkillStatus;
}

// 스킬 전체 상세설명
type SkillDetail = {
    id: number;
    name: string;
    description: string;
    list: SkillTopic[];
}
