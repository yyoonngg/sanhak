// 로드맵 구성을 위한 각 스킬 노드
type RoadmapSkill = {
    id: number;
    name: string;
    parent?: number[];
    child?: number[];
    position: [number, number];
    tag:number;
};