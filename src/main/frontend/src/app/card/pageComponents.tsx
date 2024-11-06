"use client";
import React, {useEffect, useState} from 'react';
import CardRetrieve from './CardRetrieve';
import CardEditor from './CardEditor';
import {AiCard, AiCardWithNew} from "@/models/card";

export default function CardPage() {
  const [isCreatePage, setIsCreatePage] = useState<Boolean>(false);
  const [selectedCard, setSelectedCard] = useState<AiCard | null>(null);

  const onSelectCard = (card: AiCard | null) => {
    console.log("onSelectCard: ", card);
    setIsCreatePage(true);
    setSelectedCard(card);
  }

  const onCancel = () => {
    console.log("카드 취소");
    setIsCreatePage(false);
  }

  const onCreate = async (card: AiCard) => {
    console.log("카드 생성: ", card);
      const formData = new FormData();
      formData.append("cardInfo", new Blob([JSON.stringify(card)], { type: "application/json" }))
      if (card?.imageFile) {
        formData.append("image", card?.imageFile);
      }
      if (card?.pdfFile) {
        formData.append("pdfFile", card?.pdfFile);
      }
      try {
        const response = await fetch('http://localhost:8080/api/card/create', {
          method: 'POST',
          body: formData as any,
          credentials: 'include'
        });
        if (response.ok) {
          const result = await response.json();          
          if (result.status === "success") {
            setIsCreatePage(false);
          }
        } else {
          console.error("카드 생성에 실패했습니다.");
        }
      } catch (error) {
        console.error("오류 발생:", error);
      } finally {
        setIsCreatePage(false);
      }
  }

  const onModify = async (card: AiCard) => {
    console.log("카드 수정: ", card);
      const formData = new FormData();
      formData.append("cardInfo", new Blob([JSON.stringify(card)], { type: "application/json" }))
      if (card?.imageFile) {
        formData.append("image", card?.imageFile);
      }
      if (card?.pdfFile) {
        formData.append("pdfFile", card?.pdfFile);
      }

      try {
        const response = await fetch(`http://localhost:8080/api/card/update/${card?.id}`, {
          method: 'POST',
          body: formData as any,
          credentials: 'include'
        });
        if (response.ok) {
          const result = await response.json();
          if (result.status === "success") {
            setIsCreatePage(false);
          }
        } else {
          console.error("카드 수정에 실패했습니다.");
        }
      } catch (error) {
        console.error("오류 발생:", error);
      } finally {
        setIsCreatePage(false);
      }
  }

  return (
    <div className='w-full h-full flex flex-col items-center mt-5'>
      <div className='w-[1400px] h-full'>
        {isCreatePage ? (
          <CardEditor selectedCard={selectedCard} onCancel={onCancel} onCreate={onCreate} onModify={onModify}/> 
        ) : (
          <CardRetrieve onChangePage={onSelectCard}/>
        )}
      </div>
    </div>
  );
}