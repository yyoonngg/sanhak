import React, { useEffect, useRef, useState } from "react";
import RoleDropdown from "./ChatRoleDropdown";
import { ChatMessage, ChatRoleOption } from "@/models/card";

type ChatInterfaceProps = {
  roles: ChatRoleOption[];
  chatData: ChatMessage[];
  chatInput: string;
  selectedCardTitle: string | undefined;
  onSendChat: (userMessage: ChatMessage) => void;
  onInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onKeyDown: (event: React.KeyboardEvent<HTMLInputElement>) => void;
  onResetChat: () => void;
  selectedChatId: number;
  selectedChatType: string | undefined;
  selectedRole: ChatRoleOption;
  handleSelectRole: (role: ChatRoleOption) => void;
};

const ChatInterface: React.FC<ChatInterfaceProps> = ({
                                                       roles,
                                                       chatData,
                                                       chatInput,
                                                       selectedCardTitle,
                                                       onSendChat,
                                                       onInputChange,
                                                       onKeyDown,
                                                       onResetChat,
                                                       selectedChatId,
                                                       selectedChatType,
                                                       selectedRole,
                                                       handleSelectRole,
                                                     }) => {
  const chatEndRef = useRef<HTMLDivElement | null>(null); // 메시지 끝 참조
  const [loading, setLoading] = useState(false); // 로딩 상태 관리

  // 메시지 목록 끝으로 스크롤
  useEffect(() => {
    chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [chatData]);

  // 메시지 전송 핸들러
  const handleSendChat = async () => {
    if (!chatInput.trim()) return;

    if (selectedChatId == null || selectedChatType == null) {
      const errorMessage = { id: chatData.length + 1, isUser: 0, content: "채팅방을 선택해 주세요." };
      onSendChat(errorMessage);
      return;
    }

    const userMessage = { id: chatData.length + 1, isUser: 1, content: chatInput };
    onSendChat(userMessage);
    setLoading(true);

    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/chat/${selectedChatId}/send/${selectedChatType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ question: chatInput }),
      });

      if (!response.ok) throw new Error("서버 응답 실패");

      const responseData = await response.json();
      const aiMessage = { id: chatData.length + 2, isUser: 0, content: responseData };
      onSendChat(aiMessage);
    } catch (error) {
      console.error("채팅 전송 중 오류:", error);
      const errorMessage = { id: chatData.length + 2, isUser: 0, content: "오류가 발생했습니다. 다시 시도해 주세요." };
      onSendChat(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="w-full h-full flex flex-col">
        <div className="w-full">
          <RoleDropdown
              roles={roles}
              selectedRole={selectedRole}
              onResetChat={onResetChat}
              handleSelectRole={handleSelectRole}
          />
        </div>
        <div className={`w-full h-full flex flex-col items-center ${chatData.length > 0 ? "justify-between" : "justify-center"}`}>
          {chatData.length > 0 ? (
              <div className="w-5/6 h-full overflow-y-auto scrollbar-none pt-4">
                {chatData.map((chat) => (
                    <div key={chat.id} className={`w-full mb-2 flex ${chat.isUser === 1 ? "justify-end" : "justify-start"}`}>
                      <div className={`${chat.isUser === 1 ? "bg-primary text-white" : "bg-gray-ec text-black"} p-2 rounded-lg max-w-xs`}>
                        <div>{chat.content}</div>
                      </div>
                    </div>
                ))}
                {loading && (
                    <div className="w-full mb-2 flex justify-start">
                      <div className="bg-gray-ec text-black p-2 rounded-lg max-w-xs">
                        <div>대기중...</div>
                      </div>
                    </div>
                )}
                <div ref={chatEndRef} />
              </div>
          ) : (
              <div className="font-bold text-xl mb-4">{selectedRole.guideNotice}</div>
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
                className="w-8 h-7 flex justify-center items-center bg-primary rounded-full text-white font-semibold cursor-pointer text-xs mr-2 py-1"
                onClick={handleSendChat}
            >
              전송
            </div>
          </div>
        </div>
      </div>
  );
};

export default ChatInterface;