'use client';

import Link from 'next/link';

export default function Page() {
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;

  return (
      <div className="flex flex-col w-full h-[80dvh] items-center justify-center">
        <div className='flex flex-col w-96 items-center justify-center'>

          <div className="w-full flex justify-center text-2xl font-bold mb-8">
            <img className='w-1/2' src='asset/png/service_text_logo.png' alt="Service Logo" />
          </div>

          <div className="w-full space-y-4">
            <Link
                href={`${apiUrl}/oauth2/authorization/google`}
                className="w-full py-2 px-4 bg-blue-500 rounded-lg shadow-md hover:bg-blue-600 flex items-center justify-center"
            >
              <img src='asset/png/icon_google.png' className="w-4 mr-2" alt="Google Icon"/>
              Google로 시작하기
            </Link>

            <Link
                href={`${apiUrl}/oauth2/authorization/github`}
                className="w-full py-2 px-4 bg-yellow-400 rounded-lg shadow-md hover:bg-yellow-500 flex items-center justify-center"
            >
              <img src='asset/png/icon_github.png' className="w-4 mr-2" alt="GitHub Icon"/>
              GitHub로 시작하기
            </Link>

            <Link
                href={`${apiUrl}/oauth2/authorization/kakao`}
                className="w-full py-2 px-4 bg-gray-800 rounded-lg shadow-md hover:bg-gray-900 flex items-center justify-center"
            >
              <img src='asset/png/icon_kakao.png' className="w-4 mr-2" alt="Kakao Icon"/>
              KaKao로 시작하기
            </Link>

            <Link
                href={`${apiUrl}/oauth2/authorization/naver`}
                className="w-full py-2 px-4 bg-green-500 rounded-lg shadow-md hover:bg-green-600 flex items-center justify-center"
            >
              <img src='asset/png/icon_naver.png' className="w-4 mr-2" alt="Naver Icon"/>
              Naver로 시작하기
            </Link>
          </div>
        </div>
      </div>
  );
}
