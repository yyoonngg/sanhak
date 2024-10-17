import React from 'react';
import CategoryButton from '../company/CategoryButton';
import Card from './Card';

type CardRetrieveProps = {
  onChangePage: () => void
};

const categories = ['frontend', 'backend', 'data', 'security', 'application'];

// TODO: API연결 -> '수정하기'라면 필요
const cardInfo: AiCard = {
  fromDate: '2024-10-01',
  toDate: '2024-12-01',
  title: '산학 프로젝트',
  category: ['frontend', 'application'],
  skills: [
    { id: 1, name: "HTML"},
    { id: 2, name: "CSS"},
    { id: 3, name: "JavaScript"},
    { id: 4, name: "TypeScript"},
  ],
  tools: [
    { id: 1, name: 'GitHub' }, 
    { id: 2, name: 'Figma' }
  ],
  reflection: '이번 프로젝트를 통해 웹/프론트엔드 개발에서 JavaScript와 React를 활용한 실무 경험을 쌓을 수 있었습니다. 협업 도구를 효과적으로 사용하고, 팀원들과의 소통을 통해 문제를 해결하며 더 나은 결과물을 만들어낼 수 있었습니다. 앞으로도 이러한 경험을 바탕으로 성장하고 싶습니다. 성장하는 개발자가 되겠습니다.',
  // TODO -> 사진 어떻게 처리할지 논의 -> 임시로 넣어둠
  imageUrl: '/asset/png/card_example_image.png', 
  pdfFile: '프로젝트 최종제안서.pdf',
  sourceUrl: ['https://github.com/KAU-2024-Sanhak/sanhak']
}

export default function CardRetrieve({
  onChangePage
}: CardRetrieveProps) {
  return (
    <div className='w-full h-full flex flex-row justify-between px-24'>
      {/* <div onClick={onChangePage}>AI경험카드 생성/수정 페이지로</div> */}
      <Card card={cardInfo}/>
    </div>
  );
}