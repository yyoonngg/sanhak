'use client';
import React, { useEffect, useState } from 'react';
import MypagePage from '../mypage/pageComponents';
import { useUserContext } from '@/context/UserContext';

export default function UserCareerPage() {
  const { loggedInUserId, mypageUserId  } = useUserContext();
  const [userId, setUserId] = useState<number>();

  useEffect(()=>{
    if(mypageUserId) {
      setUserId(mypageUserId);
    }
  },[mypageUserId])
 
  return (
    <>
      <MypagePage user_id={userId}/>
    </>
  );
}