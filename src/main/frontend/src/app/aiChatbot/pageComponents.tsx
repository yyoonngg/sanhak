"use client";

import React, {useCallback, useEffect, useRef, useState} from 'react';
import Card from '../card/Card';
import ChatRoomList from './ChatRoomList';
import ChatInterface from './ChatInterface';
import {ChatRoleOption} from "@/models/card";

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
  pdfFileName: string;
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

const aiRoles: ChatRoleOption[] = [
  { label: "AI 면접관", description: "경험카드를 바탕으로 예상 면접질문 및 답변 생성", guideNotice: "어떤 예상질문을 뽑아드릴까요?" },
  { label: "AI 자소서 도우미", description: "경험카드를 기반으로 자기소개서 문항 생성", guideNotice: "어떤 자기소개서 문항을 작성해드릴까요?" },
  { label: "AI 포지션 질문", description: "포지션을 바탕으로 해당 직무와 언어에 대한 설명 생성", guideNotice: "해당 직무와 언어에 대해 어떤 점이 궁금한가요?" },
];

const AiChatbotPage: React.FC = () => {
  const [cardList, setCardList] = useState<AiCard[]>([]);
  const [selectedChatRoom, setSelectedChatRoom] = useState<AiCardChatRoom | null>(null); // 선택된 채팅방 상태
  const [chatRoomData, setChatRoomData] = useState<AiCardChatRoom[]>([]);
  const [chatData, setChatData] = useState<ChatMessage[]>([]);
  const [selectedCard, setSelectedCard] = useState<AiCard | null>(null);
  const [chatInput, setChatInput] = useState('');
  const chatEndRef = useRef<HTMLDivElement | null>(null);
  const [roles, setRoles] = useState<ChatRoleOption[]>(aiRoles); // 역할 리스트 설정
  const [selectedRole, setSelectedRole] = useState<ChatRoleOption>(roles[0]); // 선택된 역할 상태 추가

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

  const initializeChat = async (chatId: number, chatRole: String) => {
    console.log("Initializing chat:", { chatId, chatRole });
    let chatType;
    if (chatRole === "AI 면접관") {
      chatType = 0;
    } else if (chatRole === "AI 자소서 도우미") {
      chatType = 1;
    } else {
      chatType = 2;
    }

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

  const fetchCardList = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/card/', {
        method: 'GET',
        credentials: 'include', // 세션 인증을 위해 필요할 경우 추가
      });

      if (response.ok) {
        const data = await response.json();
        setCardList(data); // cardList 상태를 API 응답 데이터로 설정
      } else {
        console.error('카드 목록을 불러오는 중 오류가 발생했습니다.');
      }
    } catch (error) {
      console.error('카드 목록 불러오기 오류:', error);
    }
  };

  const fetchChatMessages = async (chatId: number) => {
    console.log("Fetching chat messages for chat ID:", chatId);
    try {
      const response = await fetch(`http://localhost:8080/api/chat/message/${chatId}`, {
        method: 'GET',
        credentials: 'include',
      });
      if (response.ok) {
        const data = await response.json();

        const formattedData: ChatMessage[] = data.map((message: any) => ({
          id: message.cmid,
          isUser: message.cmisUser,
          content: message.cmcontent,
        }));

        setChatData(formattedData);
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
    fetchCardList();
  }, []);

  const handleSelect = (cardId: number) => {
    const selected = cardList.find((card) => card.id === cardId);
    if (selected) {
      setSelectedCard(selected); // 선택한 카드를 상태로 설정
      const selectedChatRoom = chatRoomData.find(room => room.cardId === cardId);
      if (selectedChatRoom) {
        setSelectedChatRoom(selectedChatRoom); // selectedChatRoom 상태를 설정
        const role = roles.find((role) => role.label === selectedChatRoom.role);
        setSelectedRole(role || roles[0]);
        // 선택된 채팅방이 설정되었을 때만 initializeChat과 fetchChatMessages 호출
        initializeChat(selectedChatRoom.id, selectedChatRoom.role);
        fetchChatMessages(selectedChatRoom.id);
      } else {
        console.error("선택한 카드에 대한 채팅방이 없습니다.");
      }
    } else {
      console.error("선택한 카드가 목록에 없습니다.");
    }
  };

  const handleSelectRole = useCallback((role: ChatRoleOption) => {
    console.log("AiChatbotPage: handleSelectRole called with role:", role);
    setSelectedRole(role);
  }, []);

  const handleChatInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChatInput(event.target.value);
  };

  useEffect(() => {
    if (selectedChatRoom && selectedRole) {
      console.log("useEffect - Initializing chat with selectedRole:", selectedRole);
      initializeChat(selectedChatRoom.id, selectedRole.label);
      fetchChatMessages(selectedChatRoom.id);
    }
  }, [selectedRole, selectedChatRoom]);

  const handleSendChat = async () => {
    if (chatInput.trim()) {
      const userMessage = { id: chatData.length + 1, isUser: 1, content: chatInput };
      setChatData([...chatData, userMessage]);
      setChatInput('');

      try {
        let selectedChatId=selectedChatRoom?.id;
        let selectedChatType=selectedChatRoom?.role;
        let chatType;``
        if (selectedChatType === "AI 면접관") {
          chatType = 0;
        } else if (selectedChatType === "AI 자소서 도우미") {
          chatType = 1;
        } else {
          chatType = 2;
        }
        const response = await fetch(`http://localhost:8080/api/chat/${selectedChatId}/send/${chatType}`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          credentials: 'include',
          body: JSON.stringify({ question: chatInput }),
        });

        if (!response.ok) {
          throw new Error('서버 응답 실패');
        }

        const responseData = await response.json();
        console.log("Response Data:", responseData);
        const aiMessage = { id: chatData.length + 2, isUser: 0, content: responseData.response };
        setChatData((prev) => [...prev, aiMessage]);
      } catch (error) {
        console.error('채팅 전송 중 오류:', error);
        const errorMessage = { id: chatData.length + 2, isUser: 0, content: '오류가 발생했습니다. 다시 시도해 주세요.' };
        setChatData((prev) => [...prev, errorMessage]);
      }
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
                  roles={roles}
                  chatData={chatData}
                  chatInput={chatInput}
                  selectedCardTitle={selectedCard ? selectedCard.title : ""}
                  onSendChat={handleSendChat}
                  onInputChange={handleChatInputChange}
                  onKeyDown={handleKeyDown}
                  onResetChat={() => setChatData([])}
                  selectedChatId={selectedChatRoom?.id as number}
                  selectedChatType={selectedRole?.label as string}
                  selectedRole={selectedRole}
                  handleSelectRole={handleSelectRole}
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
