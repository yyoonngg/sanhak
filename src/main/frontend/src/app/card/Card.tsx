import TagButton from '@/components/ButtonLabel';
import React from 'react';

const CardProp: AiCard = {};

export default function Card({
  card
}: {card: AiCard}) {
  return (
    // 일단 반응형을 고려하지 않고 width, height 고정값 부여 -> '%'로 하기엔 카드는 크기가 일정하게 유지되었으면 좋겠음
    <div className='w-[400px] h-[600px] flex flex-col items-start bg-primary border rounded-xl p-4 text-white'>
      <div className='w-full h-2/5 bg-white border rounded-xl mb-4'></div>
      <div className='flex flex-col justify-between items-start'>
        {card.fromDate && card.toDate && (
          <div className='font-normal text-gray-cc'>{card.fromDate} ~ {card.toDate}</div>
        )}
        {card.title && 
          (<div className='font-semibold text-2xl mb-4'>{card.title}</div>)
        }
        {card.category && card.category.length > 0 && (
          <div className='flex flex-wrap items-center text-sm font-semibold mb-2'>
            {card.category.map(c => (
              <TagButton key={c} type='category' label={c} style='dark' />
            ))}
          </div>
        )}
        {card.skills && card.skills.length > 0 && (
          <div className='flex flex-wrap items-center text-sm font-semibold mb-2'>
            {card.skills.map(s => (
              <TagButton key={s.id} type='skill' label={s.name} style='dark' />
            ))}
          </div>
        )}
        {card.tools && card.tools.length > 0 && (
          <div className='flex flex-wrap items-center text-sm font-semibold mb-2'>
            {card.tools.map(t => (
              <TagButton key={t} type='tool' label={t} style='dark' />
            ))}
          </div>
        )}
        {card.reflection && (
          <div className='line-clamp-6 text-sm mt-2'>{card.reflection}</div>
          )}
      </div>
    </div>
  );
}