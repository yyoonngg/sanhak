import Input from '@/components/Input';
import React from 'react';

type CardRetrieveChatProps = {
};

export default function CardRetrieveChat({
}: CardRetrieveChatProps) {
  const handlerChatInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    console.log(event.target.value);
  }
  return (
    <div className='w-full h-full flex flex-col justify-between items-center'>
      <div className='w-full h-12 flex items-center px-8 bg-white rounded-t-lg border border-gray-d9 '>
        <div className='font-semibold text-lg'>AI Chatbot</div>
      </div>
      <div className='w-full h-80 flex bg-primary'>
      </div>
      <div className='w-full flex items-center bg-white rounded-b-lg border border-gray-d9'>
        <Input 
          type='text'
          className='w-full h-12 border-0 rounded-b-lg text-sm focus:outline-0'
          placeholder="AI에게 '산학 프로젝트'에 대한 질문을 해보세요."
          onChange={handlerChatInput}
        />
        <div className='w-8 h-8 flex justify-center items-center bg-primary rounded-full text-white font-semibold text-xl mr-2'>↑</div>
      </div>
    </div>
  );
}