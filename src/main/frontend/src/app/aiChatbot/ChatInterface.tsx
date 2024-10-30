import React, { useEffect, useRef, useState } from 'react';
import RoleDropdown from './ChatRoleDropdown';

type ChatInterfaceProps = {
  chatData: AiCardChat[];
  chatInput: string;
  selectedCardTitle: string | undefined;
  onSendChat: () => void;
  onInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onKeyDown: (event: React.KeyboardEvent) => void;
  onResetChat: () => void;
}

const aiRoles: ChatRoleOption[] = [
  { label: "AI 면접관", description: "경험카드를 바탕으로 예상 면접질문 및 답변 생성", guideNotice: "어떤 예상질문을 뽑아드릴까요?" },
  { label: "AI 자소서 도우미", description: "경험카드를 기반으로 자기소개서 문항 생성", guideNotice: "어떤 자기소개서 문항을 작성해드릴까요?" },
  { label: "AI 포지션 질문", description: "포지션을 바탕으로 해당 직무와 언어에 대한 설명 생성", guideNotice: "해당 직무와 언어에 대해 어떤 점이 궁금한가요?" },
];

export default function ChatInterface({
  chatData,
  chatInput,
  selectedCardTitle,
  onSendChat,
  onInputChange,
  onKeyDown,
  onResetChat
}: ChatInterfaceProps) {
  const chatEndRef = useRef<HTMLDivElement | null>(null);
  const [roles, setRoles] = useState<ChatRoleOption[]>(aiRoles);
  const [selectedRole, setSelectedRole] = useState<ChatRoleOption>(roles[0]);
  
  const handleSelectRole = (role: ChatRoleOption) => {
    setSelectedRole(role);
  };

  useEffect(() => {
    if (chatEndRef.current) {
      chatEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [chatData]);

  return (
    <div className={`w-full h-full flex flex-col`}>
      <div className='w-full'>
        <RoleDropdown roles={roles} selectedRole={selectedRole} onResetChat={onResetChat} handleSelectRole={handleSelectRole}/>
      </div>
      <div className={`w-full h-full flex flex-col items-center ${chatData.length > 0 ? 'justify-between' : 'justify-center'}`}>
        {chatData.length > 0 ? (
          <div className="w-5/6 h-full overflow-y-auto scrollbar-none pt-4">
            {chatData.map((chat) => (
              <div
                key={chat.id}
                className={`w-full mb-2 flex ${
                  chat.isUser === 1 ? 'justify-end' : 'justify-start'
                }`}
              >
                <div
                  className={`${
                    chat.isUser === 1 ? 'bg-primary text-white' : 'bg-gray-ec text-black'
                  } p-2 rounded-lg max-w-xs`}
                >
                  <div>{chat.content}</div>
                </div>
              </div>
            ))}
            <div ref={chatEndRef} />
          </div>
        ) : (
          <div className='font-bold text-xl mb-4'>{selectedRole.guideNotice}</div>
        )}
        
        <div className="w-5/6 flex items-center bg-white rounded-xl border border-gray-d9 mb-2">
          <input
            type="text"
            value={chatInput}
            className="w-full h-10 border-0 rounded-xl text-sm focus:outline-0"
            placeholder={`AI에게 '${selectedCardTitle}'에 대한 질문을 해보세요.`}
            onChange={onInputChange}
            onKeyDown={onKeyDown}
          />
          <div
            className="w-8 h-7 flex justify-center items-center bg-primary rounded-full text-white font-semibold cursor-pointer text-xl mr-2 py-1"
            onClick={onSendChat}
          >
            ↑
          </div>
        </div>
      </div>
      
    </div>
  );
};