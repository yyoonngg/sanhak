"use client";
import React, {useState} from 'react';
import CardRetrieve from './CardRetrieve';
import CardEditor from './CardEditor';
import {AiCard} from "@/models/card";
import Loading from '@/components/Loading';

export default function CardPage() {
  const [isCreatePage, setIsCreatePage] = useState<Boolean>(false);
  const [selectedCard, setSelectedCard] = useState<AiCard | null>(null);
  const [isLoading, setIsLoading] = useState<Boolean>(false); // 로딩


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
    formData.append("cardInfo", new Blob([JSON.stringify(card)], {type: "application/json"}))
    if (card?.imageFile) {
      formData.append("image", card?.imageFile);
    }
    if (card?.pdfFile) {
      formData.append("pdfFile", card?.pdfFile);
    }
    setIsLoading(true);
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/card/create`, {
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
      setIsLoading(false); //카드 생성에 실패할경우 로딩 페이지 불러오는것도 실패
    }
  }

  const onModify = async (card: AiCard) => {
    console.log("카드 수정: ", card);
    setIsLoading(true);
    const formData = new FormData();
    formData.append("cardInfo", new Blob([JSON.stringify(card)], {type: "application/json"}))
    if (card?.imageFile) {
      formData.append("image", card?.imageFile);
    }
    if (card?.pdfFile) {
      formData.append("pdfFile", card?.pdfFile);
    }

    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/card/update/${card?.id}`, {
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
      setIsLoading(false);
    }
  }
  if (isLoading) {
    return <Loading/>;
  }

  return (
      <div className="w-full h-full flex flex-col items-center mt-5 relative">
        {isLoading && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
              <div className="bg-white p-6 rounded shadow-lg">
                <p className="text-lg font-semibold">Loading...</p>
              </div>
            </div>
        )}
        <div className="max-w-[1400px] w-full h-full px-4 2xl:w-[1400px] xl:px-20 lg:px-10">
          {isCreatePage ? (
              <CardEditor selectedCard={selectedCard} onCancel={onCancel} onCreate={onCreate} onModify={onModify}/>
          ) : (
              <CardRetrieve onChangePage={onSelectCard}/>
          )}
        </div>
      </div>
  );
}