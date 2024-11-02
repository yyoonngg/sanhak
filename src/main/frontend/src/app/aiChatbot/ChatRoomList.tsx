"use client";
import React from 'react';
import {AiCardChatRoom} from "@/models/card";

type ChatRoomListProps = {
  chatRoomMockData: AiCardChatRoom[];
  selectedCardId: number | undefined;
  onSelectCard: (cardId: number, chatType: String) => void;
};

export default function ChatRoomList({
  chatRoomMockData,
  selectedCardId,
  onSelectCard
}: ChatRoomListProps) {
  return (
    <div className="w-1/3 h-full text-sm py-4 bg-gray-f8">
      <div className="w-full mb-2 px-4 font-semibold">경험카드 목록</div>
      {chatRoomMockData.map((card) => (
        <div
          key={card.id}
          className={`${
            card.cardId === selectedCardId && "bg-white rounded-lg border-2 border-gray-99"
          } w-11/12 h-7 bg-white border border-gray-d9 rounded-lg line-clamp-1 mb-2 mx-2 py-1 px-2 cursor-pointer`}
          onClick={() => onSelectCard(card.cardId, "AI 면접관")}
        >
          {card.title?.length && card.title?.length > 15
            ? card.title?.substring(0, 15) + "..."
            : card.title}
        </div>
      ))}
    </div>
  );
};
