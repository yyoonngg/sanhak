'use client';
import React, { useEffect } from 'react';
import {UserRecommendCompany} from "@/models/user";

type RecommendCompanyProps = {
  company: UserRecommendCompany;
};

const categoryLabels: Record<string, string> = {
  frontend: '웹/프론트엔드',
  backend: '웹/백엔드',
  data: '데이터사이언스',
  security: '보안',
  application: '어플리케이션',
};

export default function RecommendCompany({
  company,
}: RecommendCompanyProps) {
  return (
    <div
      className='w-[250px] h-[180px] flex flex-col items-start text-white rounded-xl shadow-[4px_4px_8px_rgba(0,0,0,0.3)] p-4 font-gmarketsansMedium relative bg-cover bg-center'
      style={{
        backgroundImage: `url(${company.imgUrl})`,
      }}
    >
      <div className="absolute inset-0 bg-black bg-opacity-50 rounded-xl"></div>
      <div className="line-clamp-1 text-xl font-semibold relative z-10">{company.name}</div>
      <div className="line-clamp-1 mb-2 relative z-10">{company.title}</div>
      <div className="mb-1 relative z-10">{categoryLabels[company.category]}</div>
      <div className="mb-5 relative z-10">기업 적합도: {company.congruence}%</div>
      <div className="cursor-pointer text-xs relative z-10 ml-auto" onClick={() => window.open(`${company.openingUrl}`, '_blank')}>{'기업 홈페이지에서 확인하기 ->'}</div>
    </div>
  );
}