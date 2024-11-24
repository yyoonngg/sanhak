"use client";

import React, { useEffect, useRef, useState } from "react";
import Card from "../card/Card";
import ChatRoomList from "./ChatRoomList";
import ChatInterface from "./ChatInterface";
import { AiCard, AiCardChatRoom, ChatMessage, ChatRoleOption } from "@/models/card";

const AiChatbotPage: React.FC = () => {
  const [cardList, setCardList] = useState<AiCard[]>([]);
  const [chatRoomData, setChatRoomData] = useState<AiCardChatRoom[]>([]);
  const [selectedChatRoom, setSelectedChatRoom] = useState<AiCardChatRoom | null>(null);
  const [chatData, setChatData] = useState<ChatMessage[]>([]);
  const [selectedCard, setSelectedCard] = useState<AiCard | null>(null);
  const [chatInput, setChatInput] = useState("");
  const [roles, setRoles] = useState<ChatRoleOption[]>([
    { label: "AI 면접관", description: "경험카드를 바탕으로 예상 면접질문 및 답변 생성", guideNotice: "어떤 예상질문을 뽑아드릴까요?" },
    { label: "AI 자소서 도우미", description: "경험카드를 기반으로 자기소개서 문항 생성", guideNotice: "어떤 자기소개서 문항을 작성해드릴까요?" },
    { label: "AI 포지션 질문", description: "포지션을 바탕으로 해당 직무와 언어에 대한 설명 생성", guideNotice: "해당 직무와 언어에 대해 어떤 점이 궁금한가요?" },
  ]);
  const [selectedRole, setSelectedRole] = useState<ChatRoleOption>(roles[0]);
  const chatEndRef = useRef<HTMLDivElement | null>(null);

  // 채팅방 목록 API 호출
  const fetchChatList = async () => {
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/chat/list`, {
        method: "GET",
        credentials: "include",
      });
      if (response.ok) {
        const data = await response.json();
        setChatRoomData(data);
      } else {
        throw new Error("채팅방 목록을 불러오는 중 오류가 발생했습니다.");
      }
    } catch (error) {
      console.error(error);
    }
  };

  // 카드 목록 API 호출
  const fetchCardList = async () => {
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/card/`, {
        method: "GET",
        credentials: "include",
      });
      if (response.ok) {
        const data = await response.json();
        setCardList(data);
      } else {
        throw new Error("카드 목록을 불러오는 중 오류가 발생했습니다.");
      }
    } catch (error) {
      console.error(error);
    }
  };

  // 채팅 메시지 API 호출
  const fetchChatMessages = async (chatId: number) => {
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/chat/message/${chatId}`, {
        method: "GET",
        credentials: "include",
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
        throw new Error("채팅 메시지 불러오기 실패");
      }
    } catch (error) {
      console.error(error);
    }
  };

  // 채팅 초기화 API
  const initializeChat = async (chatId: number, role: ChatRoleOption) => {
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/chat/initialize/${chatId}/${roles.indexOf(role)}`, {
        method: "GET",
        credentials: "include",
      });
      if (response.ok) {
        setChatData([]);
      } else {
        throw new Error("채팅 초기화에 실패했습니다.");
      }
    } catch (error) {
      console.error(error);
    }
  };

  // 카드 선택 핸들러
  const handleSelectCard = (cardId: number) => {
    const selected = cardList.find((card) => card.id === cardId);
    if (selected) {
      setSelectedCard(selected);
      const chatRoom = chatRoomData.find((room) => room.cardId === cardId);
      if (chatRoom) {
        setSelectedChatRoom(chatRoom);
        initializeChat(chatRoom.id, selectedRole);
        fetchChatMessages(chatRoom.id);
      }
    }
  };

  // 채팅 전송 핸들러
  const handleSendChat = async () => {
    if (!chatInput.trim() || !selectedChatRoom) return;
    const userMessage: ChatMessage = { id: chatData.length + 1, isUser: 1, content: chatInput };
    setChatData((prev) => [...prev, userMessage]);
    setChatInput("");

    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/chat/${selectedChatRoom.id}/send/${roles.indexOf(selectedRole)}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ question: chatInput }),
      });
      if (response.ok) {
        const responseData = await response.json();
        const aiMessage: ChatMessage = { id: chatData.length + 2, isUser: 0, content: responseData.response };
        setChatData((prev) => [...prev, aiMessage]);
      } else {
        throw new Error("채팅 전송 실패");
      }
    } catch (error) {
      console.error(error);
    }
  };

  // 입력 필드 핸들러
  const handleChatInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setChatInput(e.target.value);
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
      handleSendChat();
    }
  };

  useEffect(() => {
    fetchChatList();
    fetchCardList();
  }, []);

  return (
      <div className="w-full h-full flex flex-col items-center">
        <div className="w-[1400px] h-[90dvh]">
          <div className="w-full h-full flex px-24">
            <ChatRoomList
                chatRoomMockData={chatRoomData}
                selectedCardId={selectedCard?.id || 0}
                onSelectCard={handleSelectCard}
            />
            <ChatInterface
                roles={roles}
                chatData={chatData}
                chatInput={chatInput}
                selectedCardTitle={selectedCard?.title || ""}
                onSendChat={handleSendChat}
                onInputChange={handleChatInputChange}
                onKeyDown={handleKeyDown}
                onResetChat={() => setChatData([])}
                selectedChatId={selectedChatRoom?.id || 0}
                selectedChatType={selectedRole.label}
                selectedRole={selectedRole}
                handleSelectRole={setSelectedRole}
            />
            <div className="w-1/3 h-full flex flex-col pl-4 border-l border-gray-d9">
              <div className="font-semibold py-4">현재 선택한 경험카드</div>
              {selectedCard && <Card card={selectedCard} />}
            </div>
          </div>
        </div>
      </div>
  );
};

export default AiChatbotPage;