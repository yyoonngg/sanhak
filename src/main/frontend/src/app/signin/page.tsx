'use client';

export default function Page() {
  return (
    <div className="flex flex-col w-full h-[80dvh] items-center justify-center">
      <div className='flex flex-col w-96 items-center justify-center'>
        
        <div className="w-full flex justify-center text-2xl font-bold mb-8">
          <img className='w-1/2' src='asset/png/service_text_logo.png' />
        </div>
        
        <div className="w-full space-y-4">
          <button className="w-full py-2 px-4 bg-blue-500 rounded-lg shadow-md hover:bg-blue-600 flex items-center justify-center">
            <img src='asset/png/icon_google.png' className="w-4 mr-2"/> 
            Google로 시작하기
          </button>

          <button className="w-full py-2 px-4 bg-yellow-400 rounded-lg shadow-md hover:bg-yellow-500 flex items-center justify-center">
            <img src='asset/png/icon_github.png' className="w-4 mr-2"/> 
            GitHub로 시작하기
          </button>
          
          <button className="w-full py-2 px-4 bg-gray-800 rounded-lg shadow-md hover:bg-gray-900 flex items-center justify-center">
            <img src='asset/png/icon_kakao.png' className="w-4 mr-2"/> 
            KaKao로 시작하기
          </button>
          
          <button className="w-full py-2 px-4 bg-green-500 rounded-lg shadow-md hover:bg-green-600 flex items-center justify-center">
            <img src='asset/png/icon_naver.png' className="w-4 mr-2"/> 
            Naver로 시작하기
          </button>
        </div>
      </div>
    </div>
  );
}
