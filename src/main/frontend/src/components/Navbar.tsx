'use client';

import {useEffect, useState} from 'react';
import Link from 'next/link';

const NavigationBar = () =>{
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const fetchAuthStatus = async () => {
            const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/oauth/status`, { credentials: 'include' });
            const data = await response.json();
            setIsAuthenticated(data.authenticated);
        };
        fetchAuthStatus();
    }, []);

    const handleLogout = async () => {
        try {
            const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/logout`, {
                method: 'POST',
                credentials: 'include',
            });

            if (response.ok) {
                setIsAuthenticated(false);
                window.location.href = '/';
            } else {
                console.error("로그아웃에 실패했습니다.");
            }
        } catch (error) {
            console.error("오류 발생:", error);
        }
    };
    return (
        <nav className="flex justify-center items-center py-5 border-b border-gray-d9">
            <div className="w-[1400px] flex justify-between items-center px-24">
                <ul className="flex space-x-12 items-center">
                    <li>
                        <Link href="/main">
                            <img src="/asset/png/icon/service_full_logo.png" alt="logo" className="w-40" />
                        </Link>
                    </li>
                    <li>
                        <Link href="/category" className="hover:underline text-sm font-gmarketsansMedium">
                            직무별로드맵
                        </Link>
                    </li>
                    <li>
                        <Link href="/lounge" className="hover:underline text-sm font-gmarketsansMedium">
                            커리어라운지
                        </Link>
                    </li>
                    <li>
                        <Link href={isAuthenticated ? "/card" : "/signin"} className="hover:underline text-sm font-gmarketsansMedium">
                            AI경험카드
                        </Link>
                    </li>
                    <li>
                        <Link href={isAuthenticated ? "/aiChatbot" : "/signin"} className="hover:underline text-sm font-gmarketsansMedium">
                            AI경험Chat
                        </Link>
                    </li>
                    <li>
                        <Link href={isAuthenticated ? "/customRoadmap" : "/signin"} className="hover:underline text-sm font-gmarketsansMedium">
                            커스텀로드맵
                        </Link>
                    </li>
                    <li>
                        <Link href={isAuthenticated ? "/mypage" : "/signin"} className="hover:underline text-sm font-gmarketsansMedium">
                            마이페이지
                        </Link>
                    </li>
                </ul>
                <div className="flex items-center border-2 border-primary text-sm font-gmarketsansMedium px-3 pb-1 pt-2 rounded-xl cursor-pointer hover:bg-gray-ec">
                    {isAuthenticated ? (
                        <button onClick={handleLogout}>로그아웃</button>
                    ) : (
                        <Link href="/signin">회원가입/로그인</Link>
                    )}
                </div>
            </div>
        </nav>
    );
};
export default NavigationBar;