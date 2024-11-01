'use client';

import React, { useEffect, useState } from 'react';
import Card from './Card';

type CardRetrieveProps = {
  onChangePage: (card: AiCard) => void
};

// 카드를 관리하는 컴포넌트
export default function CardRetrieve({ onChangePage }: CardRetrieveProps) {
  const [cardInfos, setCardInfos] = useState<AiCard[]>([]);

  useEffect(() => {
    // 백엔드에서 데이터 가져오기
    const fetchCards = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/card/", {
          credentials: 'include', // 세션 정보를 포함하여 요청
        });
        if (!response.ok) {
          throw new Error('Failed to fetch cards');
        }
        const data = await response.json();
        setCardInfos(data); // 카드 데이터를 상태에 저장
      } catch (error) {
        console.error('Error fetching cards:', error);
      }
    };

    fetchCards();
  }, []);

  return (
      <div className='w-full h-full flex flex-col items-center justify-center px-24'>
        <div className='w-full text-2xl font-gmarketsansMedium mb-4'>AI경험카드 관리</div>
        <div className='w-full h-full grid grid-cols-3 gap-4'>
          {cardInfos.map((card, index) => (
              <div className='cursor-pointer' key={index} onClick={() => onChangePage(card)}>
                <Card card={card} />
              </div>
          ))}
          <div
              className='w-[400px] h-[580px] cursor-pointer flex justify-center items-center mb-4 rounded-xl border-2 border-dashed border-gray-d9'
              onClick={() => onChangePage({})}
          >
            <div className='text-3xl font-bold text-gray-d9'>+</div>
          </div>
        </div>
      </div>
  );
}
