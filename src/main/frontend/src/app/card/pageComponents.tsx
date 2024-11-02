"use client";
import React, {useState} from 'react';
import CardRetrieve from './CardRetrieve';
import CardEditor from './CardEditor';
import {AiCard, AiCardWithNew} from "@/models/card";

export default function CardPage() {
  const [isCreatePage, setIsCreatePage] = useState<Boolean>(false);
  const [selectedCard, setSelectedCard] = useState<AiCardWithNew | null>(null);
  const onSelectCard = (card: AiCard) => {
    console.log("Card selected:", card);
    setIsCreatePage(true);
    setSelectedCard(card);
  }

  const onSaveCard = (card: AiCard) => {
    setIsCreatePage(false);
  }

  return (
    <div className='w-full h-full flex flex-col items-center mt-5'>
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