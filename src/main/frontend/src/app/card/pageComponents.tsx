"use client";
import React, { useState } from 'react';
import CardExplore from './CardExplore';
import CardEditor from './CardEditor';

export default function CardPage() {
  const [isCreatePage, setIsCreatePage] = useState<Boolean>(false);

  return (
    <div className='w-full h-full flex flex-col items-center px-24'>
      {isCreatePage ? (
        <CardEditor onChangePage={()=>{setIsCreatePage(false)}}/> 
      ) : (
        <CardExplore onChangePage={()=>{setIsCreatePage(true)}}/>
      )}
    </div>
  );
}