'use client';

import React, { useEffect, useState } from 'react';
import Card from './Card';
import { AiCard, AiCardWithNew } from "@/models/card";

type CardRetrieveProps = {
  onChangePage?: (card: AiCardWithNew | null) => void;
};

// 카드를 관리하는 컴포넌트
export default function CardRetrieve({ onChangePage }: CardRetrieveProps) {
  const [cardInfos, setCardInfos] = useState<AiCard[]>([]);

  useEffect(() => {
    // 백엔드에서 데이터 가져오기
    const fetchCards = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/card/", {
          credentials: 'include',
        });
        if (!response.ok) {
          throw new Error('Failed to fetch cards');
        }
        const data = await response.json();
        console.log("data: ", data);
        setCardInfos(data); // 카드 데이터를 상태에 저장
      } catch (error) {
        console.error('Error fetching cards:', error);
      }
    };

    fetchCards();
  }, []);

  const handleCardClick = (card: AiCardWithNew | null) => {
    if (onChangePage) {
      onChangePage(card);
    }
  };

  return (
      <div className='w-full h-full flex flex-col items-center justify-center px-24'>
        <div className='w-full text-2xl font-gmarketsansMedium mb-4'>AI경험카드 관리</div>
        <div className='w-full h-full grid grid-cols-3 gap-4'>
          {cardInfos.map((card, index) => (
              <div className='cursor-pointer' key={index} onClick={() => handleCardClick(card)}>
                <Card card={card} />
              </div>
          ))}
          <div
              className='w-[400px] h-[580px] cursor-pointer flex justify-center items-center mb-4 rounded-xl border-2 border-dashed border-gray-d9'
              onClick={() => handleCardClick(null)}
          >
            <div className='text-3xl font-bold text-gray-d9'>+</div>
          </div>
      </div>
    </div>
  );
}
