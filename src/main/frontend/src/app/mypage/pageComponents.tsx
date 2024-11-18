"use client";
import React, {useEffect, useState} from 'react';
import UserProfile from './UserProfile';
import { RoadmapSkill } from "@/models/skill";
import Roadmap from '../category/Roadmap';
import Slider from 'react-slick';
import Card from '../card/Card';
import { AiCard } from '@/models/card';
import RecommendCompany from './RecommendCompany';

// TODO: API 연결
const userInfo: User = {
  id: 1,
  name: "용용",
  category: "frontend",
  profile: "user_profile_1",
  skill_list: [
    {id: 1, name: "HTML"},
    {id: 2, name: "CSS"},
    {id: 3, name: "JavaScript"},
    {id: 4, name: "Typescript"},
    {id: 5, name: "React"},
    {id: 6, name: "Tailwind"},
    {id: 7, name: "BootStrap"},
    {id: 8, name: "Axios"},
    {id: 9, name: "ESLint"},
    {id: 10, name: "Netlify"},
  ],
  badge_cnt: 5, 
  roadmap_cnt: 2, 
  card_cnt: 1
}

// TODO: API 연결 -> 유저의 커스텀 로드맵 리스트
const customRoadmapList: CustomRoadmap[] = [
  {id: 1, name: "나의 엄청난엄청난엄청난엄청난 로드맵"},
  {id: 2, name: "카카오 로드맵"},
  {id: 3, name: "삼성 로드맵"},
];

// TODO: API 연결 -> customRoadmapList에서 선택된 로드맵의 id로 api호출
const allRoadmapSkills: RoadmapSkill[][] = [
  [
    { id: 1, name: 'HTML', child: [27, 28, 29], position: [0, 0] ,tag:'basic'},
    { id: 2, name: 'CSS', child: [5, 6], position: [1, 0] ,tag:'basic'},
    { id: 3, name: 'JavaScript', child: [4, 7, 8, 9], position: [3, 0],tag:'basic' },
    { id: 4, name: 'TypeScript', parent:[3], child: [7, 8, 9], position: [4, 0] ,tag:'framework'},
    { id: 5, name: 'Tailwind', parent: [2], child:[10], position: [1, 1] ,tag:'framework'},
    { id: 6, name: 'Bootstrap', parent: [2], position: [2, 1] ,tag:'framework'},
    { id: 7, name: 'React', parent: [3], child:[11, 12, 13], position: [3, 1],tag:'framework' },
    { id: 8, name: 'Angular', parent: [3], child:[14], position: [6, 1] ,tag:'framework'},
    { id: 9, name: 'Vue.js', parent: [3], child:[15, 16, 17], position: [7, 1] ,tag:'framework'},
    { id: 10, name: 'SASS', parent: [5], child:[18], position: [1, 2] ,tag:'framework'},
    { id: 11, name: 'React Hooks', parent: [7],  child:[19, 20, 21], position: [3, 2],tag:'none' },
    { id: 12, name: 'Redux', parent: [7], child:[19, 20, 21], position: [4, 2] ,tag:'none'},
    { id: 13, name: 'Recoil', parent: [7], child:[19, 20, 21], position: [5, 2] ,tag:'none'},
    { id: 14, name: 'RxJS', parent: [8], child:[19, 20, 21], position: [6, 2] ,tag:'none'},
    { id: 15, name: 'VueX', parent: [9], child:[19, 20, 21], position: [7, 2] ,tag:'none'},
    { id: 16, name: 'Pinia', parent: [9], child:[19, 20, 21], position: [8, 2] ,tag:'none'},
    { id: 17, name: 'Vite', parent: [9], child:[19, 20, 21], position: [9, 2] ,tag:'none'}
  ],
  [
    { id: 1, name: 'HTML', child: [27, 28, 29], position: [0, 0] ,tag:'basic'},
    { id: 2, name: 'CSS', child: [5, 6], position: [1, 0] ,tag:'basic'},
    { id: 3, name: 'JavaScript', child: [4, 7, 8, 9], position: [3, 0],tag:'basic' },
    { id: 4, name: 'TypeScript', parent:[3], child: [7, 8, 9], position: [4, 0] ,tag:'framework'},
    { id: 5, name: 'Tailwind', parent: [2], child:[10], position: [1, 1] ,tag:'framework'},
    { id: 6, name: 'Bootstrap', parent: [2], position: [2, 1] ,tag:'framework'},
    { id: 7, name: 'React', parent: [3], child:[11, 12, 13], position: [3, 1],tag:'framework' },
    { id: 8, name: 'Angular', parent: [3], child:[14], position: [6, 1] ,tag:'framework'},
    { id: 9, name: 'Vue.js', parent: [3], child:[15, 16, 17], position: [7, 1] ,tag:'framework'},
    { id: 10, name: 'SASS', parent: [5], child:[18], position: [1, 2] ,tag:'framework'},
    { id: 11, name: 'React Hooks', parent: [7],  child:[19, 20, 21], position: [3, 2],tag:'none' },
    { id: 12, name: 'Redux', parent: [7], child:[19, 20, 21], position: [4, 2] ,tag:'none'},
    { id: 13, name: 'Recoil', parent: [7], child:[19, 20, 21], position: [5, 2] ,tag:'none'},
    { id: 14, name: 'RxJS', parent: [8], child:[19, 20, 21], position: [6, 2] ,tag:'none'},
    { id: 15, name: 'VueX', parent: [9], child:[19, 20, 21], position: [7, 2] ,tag:'none'},
    { id: 16, name: 'Pinia', parent: [9], child:[19, 20, 21], position: [8, 2] ,tag:'none'},
    { id: 17, name: 'Vite', parent: [9], child:[19, 20, 21], position: [9, 2] ,tag:'none'},
    { id: 18, name: 'PostCSS', parent: [10], child:[27, 28, 29], position: [1, 3],tag:'none' },
    { id: 19, name: 'Axios', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [3, 3] ,tag:'connection'},
    { id: 20, name: 'Web Socket', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [4, 3] ,tag:'none'},
    { id: 21, name: 'ESLint', parent: [11, 12, 13, 14, 15, 16, 17], child:[22, 23], position: [5, 3] ,tag:'none'},

    { id: 22, name: 'Webpack', parent: [19, 20, 21], child:[24, 25, 26], position: [3, 4] ,tag:'none'},
    { id: 23, name: 'GraphQL', parent: [19, 20, 21], child:[24, 25, 26], position: [4, 4] ,tag:'connection'},

    { id: 24, name: 'Cypress', parent: [22, 23], child:[27, 28, 29], position: [3, 5],tag:'test' },
    { id: 25, name: 'Jest', parent: [22, 23], child:[27, 28, 29], position: [4, 5] ,tag:'test'},
    { id: 26, name: 'MobX', parent: [22, 23], child:[27, 28, 29], position: [5, 5] ,tag:'none'},

    { id: 27, name: 'Vercel', parent: [1, 18, 24, 25, 26], position: [1, 6],tag:'none' },
    { id: 28, name: 'AWS S3', parent: [1, 18, 24, 25, 26], position: [2, 6] ,tag:'none'},
    { id: 29, name: 'Netlify', parent: [1, 18, 24, 25, 26], position: [3, 6] ,tag:'none'}
  ]
];

// TODO: API 연결
const recommendCompanyList: UserRecommendCompany[] = [
  { id: 1, name: '카카오페이', title: "카카오페이 신입개발자 채용", category: 'frontend', congruence: 90, imgUrl: 'https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2Fimages%2Fcompany%2F20947%2Fizyvwevuqdjqkvwu__1080_790.png&w=700&q=100', openingUrl: "https://www.wanted.co.kr/wd/224130"},
  { id: 2, name: '토스뱅크', title: "ML Engineer", category: 'data', congruence: 78, imgUrl: 'https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2Fimages%2Fcompany%2F24000%2Ffslrv5khtj5xfswl__1080_790.png&w=700&q=100', openingUrl: "https://www.wanted.co.kr/wd/203912" },
  { id: 3, name: '중고나라', title: "프론트엔드 개발자 채용", category: 'frontend', congruence: 65, imgUrl: 'https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2Fimages%2Fcompany%2F4857%2Fqrczj1slonz0klmz__1080_790.png&w=700&q=100', openingUrl: "https://www.wanted.co.kr/wd/242157" },
  { id: 4, name: '넥슨코리아', title: "[글로벌보안본부] 탐지솔루션실 백엔드 개발자", category: 'backend', congruence: 30, imgUrl: 'https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2Fimages%2Fcompany%2F886%2Fbx7zxeirqqeboq0q__1080_790.jpg&w=700&q=100', openingUrl: "https://www.wanted.co.kr/wd/251529" }
];

export default function MypagePage() {
  const [cardInfos, setCardInfos] = useState<AiCard[]>([]);
  const [currentRoadmap, setCurrentRoadmap] = useState(0); 
  const [currentCard, setCurrentCard] = useState(0); 

  const roadmapSlideSettings = {
    dots: true, // 슬라이더 하단에 점 표시
    infinite: true, // 무한 반복
    speed: 500, // 슬라이딩 속도
    slidesToShow: 1, // 한 번에 보여줄 슬라이드 개수
    slidesToScroll: 1, // 스크롤할 슬라이드 개수
    arrows: true, // 좌우 화살표 표시
    beforeChange: (oldIndex: number, newIndex: number) => {
      setCurrentRoadmap(newIndex); // 슬라이드 변경 시 현재 인덱스 업데이트
    },
  };

  const cardSlideSettings = {
    dots: true, // 슬라이더 하단에 점 표시
    infinite: true, // 무한 반복
    speed: 500, // 슬라이딩 속도
    slidesToShow: 1, // 한 번에 보여줄 슬라이드 개수
    slidesToScroll: 1, // 스크롤할 슬라이드 개수
    arrows: true, // 좌우 화살표 표시
    beforeChange: (oldIndex: number, newIndex: number) => {
      setCurrentCard(newIndex); // 슬라이드 변경 시 현재 인덱스 업데이트
    },
  };

  useEffect(() => {
    // 백엔드에서 데이터 가져오기
    const fetchCards = async () => {
      try {
        const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/card/`, {
          credentials: 'include',
        });
        if (!response.ok) {
          throw new Error('Failed to fetch cards');
        }
        const data = await response.json();
        console.log("data: ", data);
        setCardInfos(data); // 카드 데이터를 상태에 저장
      } catch (error) {
        console.error('Error fetching cards:', error);
      }
    };

    fetchCards();
  }, []);

  return (
    <div className="w-full h-full flex flex-col items-center mt-5">
      <div className='w-[1400px] h-full px-24'>
        <div className='w-full flex flex-col pb-5'>
          <UserProfile userInfo={userInfo}/>
        </div>
        <div className='w-full flex justify-between mb-10 border-b border-gray-cc pb-10'>
          <div className='w-3/5 flex flex-col justify-start'>
            <div className='flex flex-col'>
              <div className='flex items-center text-center text-2xl font-gmarketsansMedium'><img className='w-6 h-6 mb-1 mr-1' src='asset/png/icon_filter_roadmap.png' alt='커스텀로드맵' />커스텀 로드맵</div>
              <div className='text-xl font-gmarketsansMedium'>{customRoadmapList[currentRoadmap]?.name || ''}</div>
            </div>
            <Slider {...roadmapSlideSettings} className="w-full mx-auto"> 
              {allRoadmapSkills.map((roadmap, index) => (
                <div key={index} className="w-full h-full flex justify-center items-center p-5">
                  <Roadmap isEditMode={false} roadmapSkills={roadmap} style={'h-[75dvh] max-h-[600px] mb-4'} />
                </div>
              ))}
            </Slider>
          </div>
          <div className='w-[435px] flex flex-col'>
            <div className='flex flex-col'>
              <div className='flex items-center text-center text-2xl font-gmarketsansMedium'><img className='w-6 h-6 mb-1 mr-1' src='asset/png/icon_filter_card.png' alt='AI 경험 카드' />AI경험카드</div>
              <div className='text-xl font-gmarketsansMedium'>{cardInfos[currentCard]?.title || ''}</div>
            </div>
            <Slider {...cardSlideSettings} className="w-full mx-auto"> 
              {cardInfos.map((card, index) => (
                <div key={index} className="w-full h-full flex justify-center items-center p-5"> 
                  <Card card={card} />
                </div>
              ))}
            </Slider>
          </div>
        </div>
        <div>
          <div className='text-2xl font-gmarketsansMedium'>AI추천기업</div>
          <div className='flex text-lg'>
            <div className='font-gmarketsansBold'>{userInfo.name}</div>
            <div className='font-gmarketsansMedium'>님의 커리어를 바탕으로 AI가 추천하는 맞춤형기업 TOP4</div>
          </div>
          <div className='flex justify-between items-center my-5'>
              {recommendCompanyList.map((company, index) => (
                <div key={index}>
                  <RecommendCompany company={company} />
                </div>
              ))}
          </div>
        </div>
      </div>
    </div>
  );
}