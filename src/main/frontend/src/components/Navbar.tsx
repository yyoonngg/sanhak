'use client';

import {useEffect, useState} from 'react';
import Link from 'next/link';

const NavigationBar = () =>{
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const fetchAuthStatus = async () => {
            const response = await fetch('http://localhost:8080/api/oauth/status', { credentials: 'include' });
            const data = await response.json();
            setIsAuthenticated(data.authenticated);
        };
        fetchAuthStatus();
    }, []);

    const handleLogout = async () => {
        try {
            const response = await fetch('http://localhost:8080/logout', {
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
    return(
        <nav className={"flex justify-center items-center py-5 border-b border-gray-d9"}>
            <div className={'w-[1400px] flex justify-between items-center px-24'}>
                <ul className={"sticky top-0 left-0 right-0 bottom-0 justify-center items-center flex space-x-20"}>
                    <li><Link href="/main"><img src="/asset/png/service_full_logo.png" alt="logoimg"
                                                className={"w-40"}></img></Link></li>
                    <li><Link href="/category"
                              className={"hover:underline text-sm font-gmarketsansMedium"}>직무별로드맵</Link></li>
                    <li><Link href="/company" className={"hover:underline text-sm font-gmarketsansMedium"}>기업별로드맵</Link>
                    </li>
                    {isAuthenticated ? (
                        <>
                            <li>
                                <Link href="/card" className={"hover:underline text-sm font-gmarketsansMedium"}>
                                    AI경험카드
                                </Link>
                            </li>
                            <li>
                                <Link href="/aiChatbot" className={"hover:underline text-sm font-gmarketsansMedium"}>
                                    AI경험Chat
                                </Link>
                            </li>
                            <li>
                                <Link href="/mypage" className={"hover:underline text-sm font-gmarketsansMedium"}>
                                    마이페이지
                                </Link>
                            </li>
                        </>
                    ) : (
                        <>
                            <li>
                                <Link href="/" className={"hover:underline text-sm font-gmarketsansMedium"}>
                                    AI경험카드
                                </Link>
                            </li>
                            <li>
                                <Link href="/" className={"hover:underline text-sm font-gmarketsansMedium"}>
                                    AI경험Chat
                                </Link>
                            </li>
                            <li>
                                <Link href="/" className={"hover:underline text-sm font-gmarketsansMedium"}>
                                    마이페이지
                                </Link>
                            </li>
                        </>
                    )}
                </ul>
                <div
                    className='flex items-center border-2 border-primary text-sm font-gmarketsansMedium px-3 pb-1 pt-2 rounded-xl cursor-pointer hover:bg-gray-ec'>
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