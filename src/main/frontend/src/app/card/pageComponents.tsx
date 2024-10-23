"use client";
import React, { useState } from 'react';
import CardRetrieve from './CardRetrieve';
import CardEditor from './CardEditor';

export default function CardPage() {
  const [isCreatePage, setIsCreatePage] = useState<Boolean>(false);
  const [selectedCard, setSelectedCard] = useState<AiCard | null>(null);
  const onSelectCard = (card: AiCard) => {
    setIsCreatePage(true);
    setSelectedCard(card);
  }

  const onSaveCard = (card: AiCard) => {
    setIsCreatePage(false);
  }

  return (
    <div className='w-full h-full flex flex-col items-center'>
      <div className='w-[1400px] h-full'>
        {isCreatePage ? (
          <CardEditor selectedCard={selectedCard} onChangePage={onSaveCard}/> 
        ) : (
          <CardRetrieve onChangePage={onSelectCard}/>
        )}
      </div>
    </div>
  );
}