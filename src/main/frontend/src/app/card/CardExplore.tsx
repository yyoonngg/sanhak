import React from 'react';

type CardExploreProps = {
  onChangePage: () => void
};
export default function CardExplore({
  onChangePage
}: CardExploreProps) {
  return (
    <div onClick={onChangePage}>AI 경험 카드 제작하러 가기</div>
  );
}