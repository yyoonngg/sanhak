import React from 'react';

export default function Mainpage() {
    return (
        <section className="sloganSection" >
            <div id="background_color" className="bg-[#E1DEE6] h-screen w-full px-10 justify-center items-center">
                <div id="text_container" className="justify-items-center text-center px-10">
                    <h1 className="font-extrabold text-5xl py-20">포트폴리오에 개성을 더하다</h1>
                </div>
                <div id="img_container" className="flex px-10 items-center justify-center">
                    <img src="/asset/png/mainpage/3d_laptop-removebg-preview.png" className="w-48"></img>
                    <img src="/asset/png/mainpage/3d_cloudserver-removebg-preview.png" className="w-48"></img>
                    <img src="/asset/png/mainpage/3d_watch-removebg-preview.png" className="w-48"></img>
                </div>
                <div id="image_container" className="flex px-10 justify-center items-center">
                    <img src="/asset/png/mainpage/3d_figma-removebg-preview.png" className="w-48"></img>
                    <img src="/asset/png/mainpage/3d_window-removebg-preview.png" className="w-48"></img>
                    <img src="/asset/png/mainpage/cd_compass-removebg-preview.png" className="w-48"></img>

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


        </section>
    );
}