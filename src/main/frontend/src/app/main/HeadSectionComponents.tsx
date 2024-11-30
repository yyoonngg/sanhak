"use client";
import '../../app/globals.css';
import React, { useEffect } from "react";

interface HeadSectionProps {
    currentPage: number;
}

export default function HeadSectionComponents({ currentPage }: HeadSectionProps) {
    useEffect(() => {
        // 애니메이션 스타일 추가
        const style = document.createElement("style");
        style.textContent = `
            @keyframes bounce {
                0%, 100% {
                    transform: translateY(0);
                }
                50% {
                    transform: translateY(-15px);
                }
            }
            .arrow-animation {
                animation: bounce 1s infinite;
            }
        `;
        document.head.appendChild(style);
    }, []);

    const handleScroll = () => {
        const nextSection = document.getElementById("rough_intro");
        if (nextSection) {
            nextSection.scrollIntoView({ behavior: "smooth" });
        }
    };

    return (
        <>
            <div
                id="background_color"
                className="bg-[#ffffff] h-screen w-full px-10 justify-center items-center transition-all duration-500"
                style={{
                    position: "relative",
                    scrollSnapAlign: "start",
                }}
            >
                <div id="text_container" className="justify-items-center text-center px-10">
                    <h1 className="font-extrabold font-gmarketsansBold text-2xl sm:text-5xl sm:py-20 text-[#000000]">포트폴리오에 개성을 더하다</h1>
                </div>
                <div id="img_container" className="flex px-10 items-center justify-center">
                    <img
                        src="/asset/png/mainpage/3d_laptop-removebg-preview.png"
                        className="w-48"
                        alt="3D Laptop"
                    />
                    <img
                        src="/asset/png/mainpage/3d_cloudserver-removebg-preview.png"
                        className="w-48"
                        alt="3D Cloud Server"
                    />
                    <img
                        src="/asset/png/mainpage/3d_watch-removebg-preview.png"
                        className="w-48"
                        alt="3D Watch"
                    />
                </div>
                <div id="image_container" className="flex px-10 justify-center items-center">
                    <img
                        src="/asset/png/mainpage/3d_figma-removebg-preview.png"
                        className="w-48"
                        alt="3D Figma"
                    />
                    <img
                        src="/asset/png/mainpage/3d_window-removebg-preview.png"
                        className="w-48"
                        alt="3D Window"
                    />
                    <img
                        src="/asset/png/mainpage/cd_compass-removebg-preview.png"
                        className="w-48"
                        alt="CD Compass"
                    />
                </div>
                <div className="flex justify-center mt-6">
                    <img
                        src="/asset/png/icon/icon_angle_bottom.png"
                        className="arrow-animation cursor-pointer"
                        alt="Bouncing Arrow"
                        onClick={handleScroll}
                    />
                </div>
            </div>
            <div
                id="rough_intro"
                className="bg-category-backend h-screen w-full flex flex-col font-bold items-center justify-center transition-all duration-500"
                style={{
                    position: "relative",
                    scrollSnapAlign: "start",
                }}
            >
                <p className="pb-4 font-gmarketsansMedium text-[#fed711] text-lg sm:text-4xl sm:py-4">
                    여러분의 커리어 여정을 함께하는{" "}
                    <span className="text-[#ffffff]">PathFinder</span>입니다.
                </p>
                <p className="pb-4 font-gmarketsansMedium text-[#fed711] text-lg px-8 sm:text-4xl sm:py-4">
                    자신의 희망 직무에 맞춘 스킬 로드맵과 AI가 생성해주는 경험카드를 통해,
                </p>
                <p className="pb-4 font-gmarketsansMedium text-[#fed711] text-lg sm:text-4xl sm:py-4">맞춤형 포트폴리오를 제공합니다.</p>
            </div>
        </>
    );
}
