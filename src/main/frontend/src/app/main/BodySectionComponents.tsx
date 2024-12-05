"use client";
import '../../app/globals.css';
import React, { useEffect, useRef } from "react";

interface BodySectionProps {
    currentPage: number;
}

const AutoImageSlider: React.FC = () => {
    const sliderRef = useRef<HTMLDivElement|null>(null);
    const images = [
        '/asset/png/mainpage/timelineroadmap_frontend.png',
        '/asset/png/mainpage/timelineroadmap_backend.png',
        '/asset/png/mainpage/timelineroadmap_datascience.png',
        '/asset/png/mainpage/timelineroadmap_security.png',
    ];

    useEffect(() => {
        const interval = setInterval(() => {
            if (sliderRef.current) {
                const { scrollWidth, clientWidth, scrollLeft } = sliderRef.current;

                if (Math.abs(scrollLeft + clientWidth - scrollWidth) < 1) {
                    if ("scrollTo" in sliderRef.current) {
                        sliderRef.current.scrollTo({left: 0, behavior: "smooth"});
                    }
                } else {
                    if ("scrollBy" in sliderRef.current) {
                        sliderRef.current.scrollBy({
                            left: clientWidth,
                            behavior: "smooth",
                        });
                    }
                }
            }

        }, 3000);

        return () => clearInterval(interval);
    }, []);

    return (
        <div className="flex overflow-x-auto w-full space-x-4 scrollbar-hide" ref={sliderRef}>
            {images.map((src, index) => (
                <img
                    key={index}
                    src={src}
                    alt={`slide-${index}`}
                    className="w-auto h-1/2 object-cover flex-shrink-0 sm:w-full"
                />
            ))}
        </div>
    );
};

export default function BodySectionComponents({ currentPage }: BodySectionProps) {
    return (
        <>
            <div
                id="second_background_color"
                className="h-auto w-full px-10 justify-center items-center transition-opacity duration-500 bg-[#ffffff]"
                style={{
                    position: "relative",
                    scrollSnapAlign: "end",
                }}
            >
                <div id="sub_txt_title" className="py-3 flex flex-col">
                    <p className="font-gmarketsansBold text-lg sm:text-xl text-[#3b2a70] py-4">
                        Category Roadmap Service
                    </p>
                    <p className="font-gmarketsansMedium text-base sm:text-3xl">
                        뭘 해야할지 모르겠다면,
                    </p>
                    <p className="font-gmarketsansMedium text-base sm:text-3xl">
                        로드맵 따라서 그냥 시작해보기
                    </p>
                </div>
                <div className="flex flex-col my-4">
                        <img src="/asset/png/mainpage/mockup_category.png" className="w-full sm:w-2/3 justify-center"/>
                    <div>
                        <p className="font-gmarketsansMedium text-base text-right sm:text-2xl sm:flex-col sm:flex sm:py-2">
                            각 분야에서 뭘 하면 되는지 한눈에 볼 수 있어요.
                        </p>
                    </div>
                </div>
                <p className="font-gmarketsansBold text-lg sm:text-xl text-[#3b2a70] py-4">
                    TimeLine Roadmap
                </p>
                <div className="py-4">
                    <AutoImageSlider/>
                </div>
            </div>
        </>
    );
}
