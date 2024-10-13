import ButtonLabel from '@/components/ButtonLabel';
import React from 'react';

const CardProp: AiCard = {};

export default function Card({
  style,
  card
}: {
  card: AiCard,
  style?: string
}) {
  return (
    // 일단 반응형을 고려하지 않고 width, height 고정값 부여 -> '%'로 하기엔 카드는 크기가 일정하게 유지되었으면 좋겠음
    <div className='overflow-hidden rounded-xl'>
      <div className={`${style ? style : ''} overflow-auto scrollbar w-[400px] h-[80dvh] max-h-[600px] flex flex-col items-start bg-gradient-to-b from-primary via-primary to-gray-cc p-4 text-white shadow-[4px_4px_8px_rgba(0,0,0,0.3)]`}>
        <div className='w-full h-1/3 bg-white border rounded-xl mb-4'>
          {card.imageUrl && (
            <img className='w-full h-full object-cover' src={card.imageUrl} alt=''/>
          )}
        </div>
        <div className='flex flex-col justify-between items-start'>
          {(card.fromDate || card.toDate)&& (
            <div className='font-normal text-gray-cc'>{card.fromDate} ~ {card.toDate}</div>
          )}
          {card.title && 
            (<div className='font-semibold text-2xl mb-4'>{card.title}</div>)
          }
          {card.category && card.category.length > 0 && (
            <div className='flex flex-wrap items-center text-sm font-semibold mb-2'>
              {card.category.map(c => (
                <ButtonLabel key={c} type='category' label={c} />
              ))}
            </div>
          )}
          {card.skills && card.skills.length > 0 && (
            <div className='flex flex-wrap items-center text-sm font-semibold mb-2'>
              {card.skills.map(s => (
                <ButtonLabel key={s.id} type='skill' label={s.name} />
              ))}
            </div>
          )}
          {card.tools && card.tools.length > 0 && (
            <div className='flex flex-wrap items-center text-sm font-semibold mb-2'>
              {card.tools.map(t => (
                <ButtonLabel key={t.id} type='tool' label={t.name} />
              ))}
            </div>
          )}
          {card.reflection && (
            <div className='line-clamp-5 text-sm mt-2 font-semibold'>{card.reflection}</div>
            )}
        </div>
      </div>
    </div>
  );
}