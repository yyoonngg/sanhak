"use client";
import React, { useEffect, useRef, useState } from 'react';
import Card from '../card/Card';
import ChatRoomList from './ChatRoomList';
import ChatInterface from './ChatInterface';

// TODO: chatroom id로 카드 데이터를 불러오기로 함. -> API연결 후 없어질 예정
const cardList: AiCard[] = [
  {
    id: 1,
    fromDate: '2024-10-01',
    toDate: '2024-12-01',
    title: '산학 프로젝트',
    category: ['frontend', 'application'],
    skills: [{ id: 1, name: "HTML" }, { id: 2, name: "CSS" }, { id: 3, name: "JavaScript" }, { id: 4, name: "TypeScript" }, { id: 5, name: "Tailwind" },],
    tools: [{ id: 1, name: 'GitHub' }, { id: 2, name: "Figma"}],
    reflection: '이번 프로젝트를 통해 웹/프론트엔드 개발에서 JavaScript와 React를 활용한 실무 경험을 쌓을 수 있었습니다. 협업 도구를 효과적으로 사용하고, 팀원들과의 소통을 통해 문제를 해결하며 더 나은 결과물을 만들어낼 수 있었습니다.',
    imageUrl: '/asset/png/card/card_img_1.png',
    pdfFile: '프로젝트 최종제안서 1.pdf',
    sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak1']
  },
  {
    id: 2,
    fromDate: '2024-10-01',
    toDate: '2024-12-01',
    title: '게임 개발 공모전 참여',
    category: ['frontend', 'application'],
    skills: [{ id: 1, name: "HTML" }, { id: 28, name: "AWS S3" }, { id: 29, name: "Netlify" }],
    tools: [{ id: 1, name: 'GitHub' }],
    reflection: '게임 공모전 참여해서 배틀그라운드를 뛰어넘는 총싸움 게임을 개발하고자 목표를 세웠지만, 뜻대로 되지 않아서 참가상만 받았다. 앞으론 불륨 조절을 잘해야겠따.',
    imageUrl: '/asset/png/card/card_img_2.png',
    pdfFile: '프로젝트 최종제안서 1.pdf',
    sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak1']
  },
  {
    id: 3,
    fromDate: '2024-10-01',
    toDate: '2024-12-01',
    title: '교내 창업경진대회 대상',
    category: ['backend', 'data'],
    skills: [{ id: 24, name: "Cypress" }, { id: 25, name: "Jest" }, { id: 26, name: "MobX" }],
    tools: [{ id: 1, name: 'GitHub' }, { id: 2, name: "Figma"}],
    reflection: '이번 프로젝트를 통해 웹/백엔드 개발에서 어떤 스킬 언어들이 사용되는지 차근차근 알아볼 수 있었어요.',
    imageUrl: '/asset/png/card/card_img_3.png',
    pdfFile: '프로젝트 최종제안서 1.pdf',
    sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak1']
  },
  {
    id: 4,
    fromDate: '2024-10-01',
    toDate: '2024-12-01',
    title: '산학 프로젝트',
    category: ['frontend', 'application'],
    skills: [{ id: 1, name: "HTML" }, { id: 2, name: "CSS" }, { id: 3, name: "JavaScript" }, { id: 4, name: "TypeScript" }, { id: 5, name: "Tailwind" },],
    tools: [{ id: 1, name: 'GitHub' }, { id: 2, name: "Figma"}],
    reflection: '이번 프로젝트를 통해 웹/프론트엔드 개발에서 JavaScript와 React를 활용한 실무 경험을 쌓을 수 있었습니다. 협업 도구를 효과적으로 사용하고, 팀원들과의 소통을 통해 문제를 해결하며 더 나은 결과물을 만들어낼 수 있었습니다.',
    imageUrl: '/asset/png/card/card_img_4.png',
    pdfFile: '프로젝트 최종제안서 1.pdf',
    sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak1']
  },
  {
    id: 5,
    fromDate: '2024-10-01',
    toDate: '2024-12-01',
    title: '게임 개발 공모전 참여',
    category: ['frontend', 'application'],
    skills: [{ id: 1, name: "HTML" }, { id: 28, name: "AWS S3" }, { id: 29, name: "Netlify" }],
    tools: [{ id: 1, name: 'GitHub' }],
    reflection: '게임 공모전 참여해서 배틀그라운드를 뛰어넘는 총싸움 게임을 개발하고자 목표를 세웠지만, 뜻대로 되지 않아서 참가상만 받았다. 앞으론 불륨 조절을 잘해야겠따.',
    imageUrl: '/asset/png/card/card_img_5.png',
    pdfFile: '프로젝트 최종제안서 1.pdf',
    sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak1']
  },
];

const chatRoomMockData: AiCardChatRoom[] = [
  { id: 1, cardId: 1, title: "산학프로젝트", role: "AI 면접관" },
  { id: 2, cardId: 2, title: "게임 개발 공모전 참여참여참여", role: "AI 자소서 도우미" },
  { id: 3, cardId: 3, title: "교내 창업경진대회 대상", role: "AI 포지션 질문" },
  { id: 4, cardId: 4, title: "산학 프로젝트", role: "AI 자소서 도우미" },
  { id: 5, cardId: 5, title: "게임 개발 공모전 참여", role: "AI 면접관" },
];

const chatMockData: AiCardChat[] = [
  // { id: 1, isUser: 1, content: '이 프로젝트에서 어떤 기술을 사용했나요?'},
  // { id: 2, isUser: 0, content: '이 프로젝트에서는 React, TypeScript, TailwindCSS를 사용했습니다.'},
  // { id: 3, isUser: 1, content: 'AI와 협업하는 방법이 있나요?'},
  // { id: 4, isUser: 0, content: 'AI는 개발 및 디자인 자동화에 활용될 수 있습니다.'},
];

export default function AiChatbotPage() {
  const [selectedCard, setSelectedCard] = useState<AiCard>(cardList[0]);
  const [chatData, setChatData] = useState(chatMockData);
  const [chatInput, setChatInput] = useState('');
  const chatEndRef = useRef<HTMLDivElement | null>(null);

  // 카드를 골랐을 경우
  const handleSelect = (cardId: number) => {
    const selected = cardList.find(card => card.id === cardId);
    if(selected) {
      setSelectedCard(selected);
    }
  }

  // 채팅창 Input 태그 안에 값을 세팅
  const handleChatInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChatInput(event.target.value);
  }

  // 채팅을 추가하는 함수
  const handleSendChat = () => {
    if (chatInput.trim()) {
      const newMessage = { id: chatData.length + 1, isUser: 1, content: chatInput };
      setChatData([...chatData, newMessage]);

      const aiResponse = { id: chatData.length + 2, isUser: 0, content: 'AI경험카드를 기반으로 답변드립니다.' };
      setTimeout(() => setChatData((prev) => [...prev, aiResponse]), 1000);

      setChatInput('');
    }
  };

  // 채팅 엔터키를 누르면 채팅 추가
  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter') {
      event.preventDefault();
      handleSendChat();
    }
  };

  // 채팅창 스크롤이 생겼을 경우, 채팅을 추가한 후 자동으로 스크롤 다운
  useEffect(() => {
    if (chatEndRef.current) {
      chatEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [chatData]);

  return (
    <div className="w-full h-full flex flex-col items-center">
      <div className='w-[1400px] h-[90dvh]'>
        <div className='w-full h-full flex px-24'>
          {/* <Chatbot chatRoomMockData={chatRoomMockData} /> */}
          <div className='w-full h-full flex justify-between items-center'>
            <ChatRoomList 
              chatRoomMockData={chatRoomMockData} 
              selectedCardId={selectedCard.id} 
              onSelectCard={handleSelect}
            />
            <ChatInterface 
              chatData={chatData}
              chatInput={chatInput}
              selectedCardTitle={selectedCard.title}
              onSendChat={handleSendChat}
              onInputChange={handleChatInputChange}
              onKeyDown={handleKeyDown}
              onResetChat={()=>setChatData([])}
             />
          </div>
          <div className='w-1/3 h-full flex flex-col pl-4 border-l border-gray-d9'>
            <div className='font-semibold py-4'>현재 선택한 경험카드</div>
            <Card card={selectedCard} />
          </div>
        </div>
      </div>
    </div>
  );
}