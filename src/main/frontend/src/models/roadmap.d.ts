import {RoadmapSkill} from "@/models/skill";

type CustomRoadmapName = {
    id: number;
    name: string;
}

// 커스텀로드맵 페이지 - 커스텀 로드맵
type CustomRoadmapDetail = {
    id?: number;
    name: string;
    skills: RoadmapSkill[];
};