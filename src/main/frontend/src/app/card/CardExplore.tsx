import React from 'react';

type CardExploreProps = {
  onChangePage: () => void
};
export default function CardExplore({
  onChangePage
}: CardExploreProps) {
  return (
    <div onClick={onChangePage}>AI 경험 카드 조회 페이지</div>
  );
}