import React from 'react';
import CategoryButton from './CategoryButton';

type CardExploreProps = {
  onChangePage: () => void
};

const categories = ['frontend', 'backend', 'data', 'security', 'application'];

export default function CardExplore({
  onChangePage
}: CardExploreProps) {

  const handleSelectCategory = (category: string) => {
    console.log(category);
  };
  return (
    <div className='w-full h-full flex flex-col items-center'>
      <div className='w-dvw h-40 flex flex-row items-center justify-center border-b border-gray-d9'>
        <div className='w-[1000px] h-full flex flex-row items-center justify-between'>
          {categories.map(c => (
            <CategoryButton key={c} category={c} handleSelectCategory={handleSelectCategory}/>
          ))}
        </div>
      </div>
      <div className='w-full h-full flex flex-row justify-between px-24'>
        <div onClick={onChangePage}>AI경험카드 생성/수정 페이지로</div>
      </div>
    </div>
  );
}