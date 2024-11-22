"use client";
import '../../app/globals.css';
import React, { useState, useEffect } from "react";

export default function BodySectionComponents() {
    const [currentPage, setCurrentPage] = useState(2); // 현재 페이지 상태
    const pages = 4; // 페이지 개수

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

        window.addEventListener("wheel", handleScroll, {passive: false});

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
                id="second_background_color"
                className="h-screen w-full px-10 justify-center items-center"
                style={{position: "relative", scrollSnapAlign: "start"}}>

                <div id="sub_txt_title" className="py-3">
                    <p className="font-gmarketsansBold text-xl text-category-backend py-4">직무별 로드맵</p>
                    <p className="font-gmarketsansMedium text-3xl">뭘 해야할지 모르겠다면,</p>
                    <p className="font-gmarketsansMedium text-3xl">로드맵 따라서 그냥 시작해보기</p>

                    </div>
                    <div className="flex">
                        <img src="/asset/png/mainpage/ipad_roadmap_mockup.png" className="w-1/2 justify-center"/>
                        <p className="font-gmarketsansMedium text-2xl pb-2">pathFinder에서 개발자의 역량을 확인해보세요.</p>
                        <p className="font-gmarketsansMedium text-2xl pb-2">프론트, 백, 보안, 앱, 데이터까지</p>
                        <p className="font-gmarketsansMedium text-2xl pb-2">각 분야에서 뭘 하면 되는지 한눈에 볼 수 있어요</p>

                    </div>
                <div className="flex">
                    <p className="font-gmarketsansMedium text-2xl pb-2">내가 했던 프로젝트를 입력해보세요.</p>
                    <p className="font-gmarketsansMedium text-2xl pb-2">막막한 포트폴리오 작성,</p>
                    <p className="font-gmarketsansMedium text-2xl pb-2">AI가 함께 합니다.</p>
                    <img src="/asset/png/mainpage/ipad_generatecard_mockup.png" className="w-2/3"/>

                </div>
            </div>

        </>
    );
}
