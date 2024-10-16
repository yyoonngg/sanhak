"use client";
import React, { useState } from 'react';
import CardRetrieve from './CardRetrieve';
import CardEditor from './CardEditor';

export default function CardPage() {
  const [isCreatePage, setIsCreatePage] = useState<Boolean>(false);

  return (
    <div className='w-full h-full flex flex-col items-center'>
      <div className='w-[1400px] h-full'>
        {isCreatePage ? (
          <CardEditor onChangePage={()=>{setIsCreatePage(false)}}/> 
        ) : (
          <CardRetrieve onChangePage={()=>{setIsCreatePage(true)}}/>
        )}
      </div>
    </div>
  );
}