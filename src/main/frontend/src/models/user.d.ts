// 마이페이지 개발 뱃지를 위한 각 스킬 노드
type UserSkill = {
    id: number;
    name: string;
}

// 마이페이지 유저 정보
type User = {
    id: number;
    name: string;
    profile: string;
    skill_list: UserSkill[];
}

// 마이페이지 로드맵 리스트
type CustomRoadmap = {
    id: number;
    name: string;
}