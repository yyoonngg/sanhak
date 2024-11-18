"use client";
import React, { useState, useEffect } from "react";

export default function Mainpage() {
    const [currentPage, setCurrentPage] = useState(0); // 현재 페이지 상태
    const pages = 2; // 페이지 개수

    useEffect(() => {
        const handleScroll = (event: WheelEvent) => {
            if (event.deltaY > 0 && currentPage < pages - 1) {
                // 스크롤 다운
                setCurrentPage((prev) => prev + 1);
            } else if (event.deltaY < 0 && currentPage > 0) {
                // 스크롤 업
                setCurrentPage((prev) => prev - 1);
            }
        };

        window.addEventListener("wheel", handleScroll, { passive: false });

        return () => {
            window.removeEventListener("wheel", handleScroll);
        };
    }, [currentPage]);

    useEffect(() => {
        window.scrollTo({
            top: currentPage * window.innerHeight,
            behavior: "smooth",
        });
    }, [currentPage]);

    return (
        <>
            <div
                id="background_color"
                className="bg-[#E1DEE6] h-screen w-full px-10 justify-center items-center"
                style={{ position: "relative", scrollSnapAlign: "start" }}
            >
                <div id="text_container" className="justify-items-center text-center px-10">
                    <h1 className="font-extrabold text-5xl py-20">포트폴리오에 개성을 더하다</h1>
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
                        className="arrow-animation"
                        alt="Bouncing Arrow"
                    />
                </div>
                <style>
                    {`
                        @keyframes bounce {
                            0%, 100% {
                                transform: translateY(-25%);
                                animation-timing-function: cubic-bezier(0.8, 0, 1, 1);
                            }
                            50% {
                                transform: translateY(0);
                                animation-timing-function: cubic-bezier(0, 0, 0.2, 1);
                            }
                        }

                        .arrow-animation {
                            animation: bounce 1s infinite;
                            display: inline-block;
                        }
                    `}
                </style>
            </div>

            <div
                id="rough_intro"
                className="bg-[#f9fafb] h-screen w-full flex flex-col font-bold text-4xl items-center justify-center"
                style={{position: "relative", scrollSnapAlign: "start"}}
            >
                <p className="pb-4">
                    여러분의 커리어 여정을 함께하는{" "}
                    <span className="text-category-application">PathFinder</span>입니다.
                </p>
                <p className="pb-4">자신의 희망 직무에 맞춘 스킬 로드맵과 AI가 생성해주는 경험카드를 통해,</p>
                <p className="pb-4">맞춤형 포트폴리오를 제공합니다.</p>
            </div>
        </>
    );
}
