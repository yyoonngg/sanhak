"use client";

import React, { useEffect, useRef, useState } from 'react';
import Card from '../card/Card';
import ChatRoomList from './ChatRoomList';
import ChatInterface from './ChatInterface';

type AiCard = {
  id: number;
  fromDate: string;
  toDate: string;
  title: string;
  category: string[];
  skills: { id: number; name: string }[];
  tools: { id: number; name: string }[];
  reflection: string;
  imageUrl: string;
  pdfFile: string;
  sourceUrl: string[];
};

type AiCardChatRoom = {
  id: number;
  cardId: number;
  title: string;
  role: string;
};

type ChatMessage = {
  id: number;
  isUser: number;
  content: string;
};

const AiChatbotPage: React.FC = () => {
  const [cardList, setCardList] = useState<AiCard[]>([]);
  const [selectedChatRoom, setSelectedChatRoom] = useState<AiCardChatRoom | null>(null); // 선택된 채팅방 상태
  const [chatRoomData, setChatRoomData] = useState<AiCardChatRoom[]>([]);
  const [chatData, setChatData] = useState<ChatMessage[]>([]);
  const [selectedCard, setSelectedCard] = useState<AiCard | null>(null);
  const [chatInput, setChatInput] = useState('');
  const chatEndRef = useRef<HTMLDivElement | null>(null);

  const fetchChatList = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/chat/list', {
        method: 'GET',
        credentials: 'include',
      });
      if (response.ok) {
        const data = await response.json();
        setChatRoomData(data); // 채팅방 데이터 설정
      } else {
        throw new Error('채팅방 목록을 불러오는 중 오류가 발생했습니다.');
      }
    } catch (error) {
      if (error instanceof Error) {
        console.error(error.message);
      } else {
        console.error("An unknown error occurred");
      }
    }
  };

  const initializeChat = async (chatId: number, chatType: number) => {
    try {
      const response = await fetch(`http://localhost:8080/api/chat/initialize/${chatId}/${chatType}`, {
        method: 'GET',
        credentials: 'include',
      });
      if (response.ok) {
        console.log("채팅이 성공적으로 초기화되었습니다.");
      } else {
        throw new Error('채팅 초기화에 실패했습니다.');
      }
    } catch (error) {
      if (error instanceof Error) {
        console.error(error.message);
      } else {
        console.error("An unknown error occurred");
      }
    }
  };

  const fetchChatMessages = async (chatId: number) => {
    try {
      const response = await fetch(`http://localhost:8080/api/chat/message/${chatId}`, {
        method: 'GET',
        credentials: 'include',
      });
      if (response.ok) {
        const data = await response.json();
        setChatData(data); // 채팅 메시지 데이터 설정
      } else {
        throw new Error('채팅 메시지를 불러오는 중 오류가 발생했습니다.');
      }
    } catch (error) {
      if (error instanceof Error) {
        console.error(error.message);
      } else {
        console.error("An unknown error occurred");
      }
    }
  };

  useEffect(() => {
    fetchChatList(); // 컴포넌트 마운트 시 채팅방 목록 가져오기
  }, []);

  const handleSelect = (cardId: number) => {
    const selected = cardList.find((card) => card.id === cardId);
    if (selected) {
      setSelectedCard(selected);
      const selectedChatRoom = chatRoomData.find(room => room.cardId === cardId);
      if (selectedChatRoom) {
        initializeChat(selectedChatRoom.id, 1); // 예시로 채팅 타입을 1로 설정
        fetchChatMessages(selectedChatRoom.id);
      }
    }
  };

  const handleChatInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChatInput(event.target.value);
  };

  const handleSendChat = () => {
    if (chatInput.trim()) {
      const newMessage = { id: chatData.length + 1, isUser: 1, content: chatInput };
      setChatData([...chatData, newMessage]);

      const aiResponse = { id: chatData.length + 2, isUser: 0, content: 'AI경험카드를 기반으로 답변드립니다.' };
      setTimeout(() => setChatData((prev) => [...prev, aiResponse]), 1000);

      setChatInput('');
    }
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter') {
      event.preventDefault();
      handleSendChat();
    }
  };

  useEffect(() => {
    if (chatEndRef.current) {
      if ("scrollIntoView" in chatEndRef.current) {
        chatEndRef.current.scrollIntoView({behavior: 'smooth'});
      }
    }
  }, [chatData]);

  return (
      <div className="w-full h-full flex flex-col items-center">
        <div className='w-[1400px] h-[90dvh]'>
          <div className='w-full h-full flex px-24'>
            <div className='w-full h-full flex justify-between items-center'>
              <ChatRoomList
                  chatRoomMockData={chatRoomData}
                  selectedCardId={selectedCard ? selectedCard.id : 0}
                  onSelectCard={handleSelect}
              />
              <ChatInterface
                  chatData={chatData}
                  chatInput={chatInput}
                  selectedCardTitle={selectedCard ? selectedCard.title : ""}
                  onSendChat={handleSendChat}
                  onInputChange={handleChatInputChange}
                  onKeyDown={handleKeyDown}
                  onResetChat={() => setChatData([])}
                  selectedChatId={selectedChatRoom?.id as number}
                  selectedChatType={selectedChatRoom?.role as String}
              />
            </div>
            <div className='w-1/3 h-full flex flex-col pl-4 border-l border-gray-d9'>
              <div className='font-semibold py-4'>현재 선택한 경험카드</div>
              {selectedCard && <Card card={selectedCard} />}
            </div>
          </div>
        </div>
      </div>
  );
};

export default AiChatbotPage;
