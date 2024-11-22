"use client";
import React from 'react';
import {AiCardChatRoom} from "@/models/card";

type CustomRoadmapListProps = {
  roadmapData: CustomRoadmapName[];
  selectedRoadmapId: number;
  onSelectRoadmap?: () => void;
};

export default function CustomRoadmapList({
  roadmapData,
  selectedRoadmapId,
  onSelectRoadmap
}: CustomRoadmapListProps) {

  const handleSelectedCard = () =>{
    if(onSelectRoadmap){
      onSelectRoadmap();
    }
  }
  return (
    <div className="w-1/3 h-full text-sm py-4 bg-gray-f8">
      <div className="w-full mb-2 px-4 font-semibold">커스텀로드맵 목록</div>
      {roadmapData.map((roadmap) => (
        <div
          key={roadmap.id}
          className={`${
            roadmap.id === selectedRoadmapId && "bg-white rounded-lg border-2 border-gray-99"
          } w-11/12 h-7 bg-white border border-gray-d9 rounded-lg line-clamp-1 mb-2 mx-2 py-1 px-2 cursor-pointer`}
          onClick={() => handleSelectedCard()}
        >
          {roadmap.name?.length && roadmap.name?.length > 15
            ? roadmap.name?.substring(0, 15) + "..."
            : roadmap.name}
        </div>
      ))}
    </div>
  );
};
