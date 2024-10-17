import React from 'react';
import Card from './Card';
import Slider from 'react-slick';
import CardRetrieveChat from './CardRetrieveChat';

type CardRetrieveProps = {
  onChangePage: (card: AiCard) => void
};

// TODO: API연결 -> '수정하기'라면 필요
const cardInfos: AiCard[] = [
  {
    fromDate: '2024-10-01',
    toDate: '2024-12-01',
    title: '산학 프로젝트',
    category: ['frontend', 'application'],
    skills: [{ id: 1, name: "HTML" }, { id: 2, name: "CSS" }, { id: 3, name: "JavaScript" }, { id: 4, name: "TypeScript" }, { id: 5, name: "Tailwind" },],
    tools: [{ id: 1, name: 'GitHub' }, { id: 2, name: "Figma"}],
    reflection: '이번 프로젝트를 통해 웹/프론트엔드 개발에서 JavaScript와 React를 활용한 실무 경험을 쌓을 수 있었습니다. 협업 도구를 효과적으로 사용하고, 팀원들과의 소통을 통해 문제를 해결하며 더 나은 결과물을 만들어낼 수 있었습니다.',
    imageUrl: '/asset/png/card_example_image_1.png',
    pdfFile: '프로젝트 최종제안서 1.pdf',
    sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak1']
  },
  {
    fromDate: '2024-10-01',
    toDate: '2024-12-01',
    title: '게임 개발 공모전 참여',
    category: ['frontend', 'application'],
    skills: [{ id: 1, name: "HTML" }, { id: 28, name: "AWS S3" }, { id: 29, name: "Netlify" }],
    tools: [{ id: 1, name: 'GitHub' }],
    reflection: '게임 공모전 참여해서 배틀그라운드를 뛰어넘는 총싸움 게임을 개발하고자 목표를 세웠지만, 뜻대로 되지 않아서 참가상만 받았다. 앞으론 불륨 조절을 잘해야겠따.',
    imageUrl: '/asset/png/card_example_image_2.png',
    pdfFile: '프로젝트 최종제안서 1.pdf',
    sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak1']
  },
  {
    fromDate: '2024-10-01',
    toDate: '2024-12-01',
    title: '교내 창업경진대회 대상',
    category: ['backend', 'data'],
    skills: [{ id: 24, name: "Cypress" }, { id: 25, name: "Jest" }, { id: 26, name: "MobX" }],
    tools: [{ id: 1, name: 'GitHub' }, { id: 2, name: "Figma"}],
    reflection: '이번 프로젝트를 통해 웹/백엔드 개발에서 어떤 스킬 언어들이 사용되는지 차근차근 알아볼 수 있었어요.',
    imageUrl: '/asset/png/card_example_image_3.png',
    pdfFile: '프로젝트 최종제안서 1.pdf',
    sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak1']
  },
];

export default function CardRetrieve({
  onChangePage
}: CardRetrieveProps) {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    arrows: true,
  };

  return (
    <div className='w-full h-full flex flex-col items-center justify-center'>
      <Slider {...settings} className='w-[1200px] h-[60dvh]'>
        {cardInfos.map((card, index) => (
          <div className='cursor-pointer scale-75 origin-top' key={index} onClick={()=>onChangePage(card)}>
            <Card card={card} />
          </div>
        ))}
      </Slider>
      <div className='w-[1400px] h-full p-24'>
        <CardRetrieveChat />
      </div>
    </div>
  );
}