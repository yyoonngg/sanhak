'use client';
import React, {useEffect, useState} from 'react';
import CustomRoadmapList from './CustomRoadmapList';
import Roadmap from '../category/Roadmap';
import {AllKindOfSkills, RoadmapSkill} from '@/models/skill';
import CustomSkillList from './CustomSkillList';
import {ChangeRoadmapDTO, CustomRoadmapDetail, CustomRoadmapName} from "@/models/roadmap";

// TODO 1: API 연결 -> 유저의 커스텀 로드맵 리스트
const fetchCustomRoadmapList = async (): Promise<CustomRoadmapName[]> => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/mypage/roadmap/list`, {
    method: 'GET',
    credentials: 'include',
  });

  if (!response.ok) {
    throw new Error('로드맵 목록 데이터를 가져오는 데 실패했습니다.');
  }
  return response.json();
};

// TODO 2: API 연결 -> 위에서 선택된 로드맵에 해당하는 디테일한 데이터
const fetchRoadmapDetail = async (roadmapId: number): Promise<CustomRoadmapDetail> => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/mypage/roadmap/${roadmapId}`, {
    method: 'GET',
    credentials: 'include',
  });

  if (!response.ok) {
    throw new Error('로드맵 데이터를 가져오는 데 실패했습니다.');
  }
  return response.json();
};

// TODO 3: API 연결 
const allCategorySkills: AllKindOfSkills[] = [
  { category:"frontend",
    skills: [
      { id: 1, name: 'HTML' },
      { id: 2, name: 'CSS' },
      { id: 3, name: 'JavaScript' },
      { id: 4, name: 'TypeScript' },
      { id: 5, name: 'Tailwind' },
      { id: 6, name: 'Bootstrap' },
      { id: 7, name: 'React' },
      { id: 8, name: 'Angular' },
      { id: 9, name: 'Vue.js' },
      { id: 10, name: 'SASS' },
      { id: 11, name: 'React Hooks' },
      { id: 12, name: 'Redux' },
      { id: 13, name: 'Recoil' },
      { id: 14, name: 'RxJS' },
      { id: 15, name: 'VueX' },
      { id: 16, name: 'Pinia' },
      { id: 17, name: 'Vite' },
      { id: 18, name: 'PostCSS' },
      { id: 19, name: 'Axios' },
      { id: 20, name: 'Web Socket' },
      { id: 21, name: 'ESLint' },
      { id: 22, name: 'Webpack' },
      { id: 23, name: 'GraphQL' },
      { id: 24, name: 'Cypress' },
      { id: 25, name: 'Jest' },
      { id: 26, name: 'MobX' },
      { id: 27, name: 'Vercel' },
      { id: 28, name: 'AWS S3' },
      { id: 29, name: 'Netlify' },
    ]},
  { category:"backend",
    skills: [
      { id: 3, name: 'JavaScript' },
      { id: 4, name: 'TypeScript' },
      { id: 25, name: 'Jest' },
      { id: 30, name: 'Python' },
      { id: 31, name: 'Java' },
      { id: 32, name: 'Kotlin' },
      { id: 33, name: 'Ruby' },
      { id: 34, name: 'PHP' },
      { id: 35, name: 'C#' },
      { id: 36, name: 'Flask' },
      { id: 37, name: 'Spring' },
      { id: 38, name: 'Express.js' },
      { id: 39, name: 'Next.js' },
      { id: 40, name: 'Fresh' },
      { id: 41, name: 'Ruby on rail' },
      { id: 42, name: 'Laravel' },
      { id: 43, name: 'Symfony' },
      { id: 44, name: 'ASP.NET' },
      { id: 45, name: 'Django' },
      { id: 46, name: 'Fast API' },
      { id: 47, name: 'Spring Boot' },
      { id: 48, name: 'Nest.js' },
      { id: 49, name: 'Nuxt.js' },
      { id: 50, name: 'Electron' },
      { id: 51, name: 'Pytest' },
      { id: 52, name: 'JUnit5' },
      { id: 53, name: 'RSpec' },
      { id: 54, name: 'PHPUnit' },
      { id: 55, name: 'NUnit' },
      { id: 56, name: 'SQLAlchemy' },
      { id: 57, name: 'Pypika' },
      { id: 58, name: 'Hibernate' },
      { id: 59, name: 'My batis' },
      { id: 60, name: 'Sequelize.js' },
      { id: 61, name: 'Prisma' },
      { id: 62, name: 'Knex.js' },
      { id: 63, name: 'ROM.rb' },
      { id: 64, name: 'Doctrine' },
      { id: 65, name: 'EF Core' },
      { id: 66, name: 'Dapper' },
      { id: 67, name: 'H2 Database' },
      { id: 68, name: 'MySQL' },
      { id: 69, name: 'PostgreSQL' },
      { id: 70, name: 'SQLite' },
      { id: 71, name: 'MariaDB' },
      { id: 72, name: 'MongoDB' },
      { id: 73, name: 'Cassandra' },
      { id: 74, name: 'Redis' },
      { id: 75, name: 'Firebase' },
      { id: 76, name: 'Hadoop' },
      { id: 77, name: 'Couchbase' },
      { id: 78, name: 'AWS' },
      { id: 79, name: 'GCP' },
      { id: 80, name: 'Azure' },
      { id: 81, name: 'Apache' },
      { id: 82, name: 'Nginx' },
      { id: 83, name: 'Tomcat' },
      { id: 84, name: 'Websphere' },
      { id: 85, name: 'uWSGI' },
      { id: 86, name: 'Gunicon' },
      { id: 87, name: 'Jenkins' },
      { id: 88, name: 'Git Lab' },
      { id: 89, name: 'Git Action' },
      { id: 90, name: 'ArgoCD' },
      { id: 91, name: 'Docker' },
      { id: 92, name: 'Kubernetes' },
      { id: 93, name: 'AWS ELB' },
      { id: 94, name: 'HAProxy' },
      { id: 95, name: 'Elastic Stack' },
      { id: 96, name: 'Grafana' },
      { id: 97, name: 'Open SSL' },
      { id: 98, name: 'OAuth' },
      { id: 99, name: 'JWT' },
      { id: 100, name: 'Kafka' },
      { id: 101, name: 'Cloudflare' },
      { id: 102, name: 'Zipkin' },
    ]},
  { category:"data",
    skills: [
      { id: 30, name: 'Python' },
      { id: 36, name: 'Flask' },
      { id: 46, name: 'Fast API' },
      { id: 76, name: 'Hadoop' },
      { id: 78, name: 'AWS' },
      { id: 79, name: 'GCP' },
      { id: 80, name: 'Azure' },
      { id: 91, name: 'Docker' },
      { id: 92, name: 'Kubernetes' },
      { id: 100, name: 'Kafka' },
      { id: 103, name: 'Bash' },
      { id: 104, name: 'Pandas' },
      { id: 105, name: 'Matplotlib' },
      { id: 106, name: 'Seaborn' },
      { id: 107, name: 'Jupyter' },
      { id: 108, name: 'Tableau' },
      { id: 109, name: 'Power Bi' },
      { id: 110, name: 'R' },
      { id: 111, name: 'ggplot2' },
      { id: 112, name: 'Tidyverse' },
      { id: 113, name: 'Rayserve' },
      { id: 114, name: 'MLflow' },
      { id: 115, name: 'Scipy' },
      { id: 116, name: 'Tensorflow' },
      { id: 117, name: 'Pytorch' },
      { id: 118, name: 'Stats models' },
      { id: 119, name: 'scikit learn' },
      { id: 120, name: 'sharp' },
      { id: 121, name: 'keras' },
      { id: 122, name: 'Huggingface' },
      { id: 123, name: 'ONNX' },
      { id: 124, name: 'Spark' },
      { id: 125, name: 'Flink' },
      { id: 127, name: 'terraform' },
      { id: 172, name: 'Numpy' },
      { id: 173, name: 'Dask' },
    ]},
  { category:"security",
    skills: [
      { id: 30, name: 'Python' },
      { id: 78, name: 'AWS' },
      { id: 103, name: 'Bash' },
      { id: 128, name: 'C' },
      { id: 129, name: 'Web' },
      { id: 130, name: 'Binary' },
      { id: 131, name: 'Assembly' },
      { id: 132, name: 'TCP IP' },
      { id: 133, name: 'MicroSoft' },
      { id: 134, name: 'Cryptography' },
      { id: 135, name: 'NAT' },
      { id: 136, name: 'Cookie' },
      { id: 137, name: 'XSS' },
      { id: 138, name: 'INJECTION' },
      { id: 139, name: 'Blockchain' },
      { id: 140, name: 'FIREWALL' },
      { id: 141, name: 'Linux' },
      { id: 142, name: 'IPS' },
      { id: 143, name: 'IDS' },
      { id: 144, name: 'VPN' },
      { id: 145, name: 'Linuxlib' },
      { id: 146, name: 'HTTP' },
    ]},
  { category:"application",
    skills: [
      { id: 1, name: 'HTML' },
      { id: 2, name: 'CSS' },
      { id: 3, name: 'JavaScript' },
      { id: 4, name: 'TypeScript' },
      { id: 5, name: 'Tailwind' },
      { id: 6, name: 'Bootstrap' },
      { id: 7, name: 'React' },
      { id: 8, name: 'Angular' },
      { id: 9, name: 'Vue.js' },
      { id: 10, name: 'SASS' },
      { id: 12, name: 'Redux' },
      { id: 14, name: 'RxJS' },
      { id: 15, name: 'VueX' },
      { id: 19, name: 'Axios' },
      { id: 20, name: 'Web Socket' },
      { id: 23, name: 'GraphQL' },
      { id: 24, name: 'Cypress' },
      { id: 25, name: 'Jest' },
      { id: 31, name: 'Java' },
      { id: 32, name: 'Kotlin' },
      { id: 61, name: 'Prisma' },
      { id: 68, name: 'MySQL' },
      { id: 69, name: 'PostgreSQL' },
      { id: 70, name: 'SQLite' },
      { id: 72, name: 'MongoDB' },
      { id: 75, name: 'Firebase' },
      { id: 147, name: 'WebView' },
      { id: 148, name: 'Dart' },
      { id: 149, name: 'Swift' },
      { id: 150, name: 'Objective-C' },
      { id: 151, name: 'Cordova' },
      { id: 152, name: 'Ionic' },
      { id: 153, name: 'NativeScript' },
      { id: 154, name: 'React Native' },
      { id: 155, name: 'Flutter' },
      { id: 156, name: 'Streams' },
      { id: 157, name: 'Appium' },
      { id: 158, name: 'PWA' },
      { id: 159, name: 'Expo' },
      { id: 160, name: 'SwiftUI' },
      { id: 161, name: 'UIKit' },
      { id: 162, name: 'CoreData' },
      { id: 163, name: 'Realm' },
      { id: 164, name: 'Alamofire' },
      { id: 165, name: 'SwiftTesting' },
      { id: 166, name: 'JetpackCompose' },
      { id: 167, name: 'AndroidStudio' },
      { id: 168, name: 'Room' },
      { id: 169, name: 'OkHttp' },
      { id: 170, name: 'Retrofit' },
      { id: 171, name: 'Espresso' },
    ]},
];

export default function CustomRoadmapPage() {
  const [customRoadmapList, setCustomRoadmapList] = useState<CustomRoadmapName[]>([]);
  const [selectedRoadmap, setSelectedRoadmap] = useState<CustomRoadmapDetail>({
    id: null,
    name: '',
    skills: [],
  });
  const [triggerAction, setTriggerAction] = useState<null | 'increase' | 'decrease'>(null);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  // 로드맵 리스트 불러오기
  useEffect(() => {
    const loadRoadmapList = async () => {
      try {
        const roadmapList = await fetchCustomRoadmapList();
        setCustomRoadmapList(roadmapList);

        // 기본 선택 로드맵 설정
        if (roadmapList.length > 0) {
          onSelectRoadmap(roadmapList[0].id);
        }
      } catch (error) {
        console.error('로드맵 리스트 로드 실패:', error);
      }
    };
    loadRoadmapList();
  }, []);

  // 로드맵 선택 시 호출
  const onSelectRoadmap = async (roadmapId: number) => {
    try {
      const roadmapData = await fetchRoadmapDetail(roadmapId);
      console.log(roadmapData);
      setSelectedRoadmap(roadmapData);
    } catch (error) {
      console.error('로드맵 선택 실패:', error);
    }
  };

  // TODO 5: 로드맵 저장 API 호출
  const updateRoadmap = () => {
    setIsEditMode(false);
  };

  // 로드맵 크기 관련 트리거 
  // 1) 로드맵 확장 트리거
  const handleIncreaseSize = () => {
    setTriggerAction('increase'); 
  };
  // 2) 로드맵 축소 트리거
  const handleDecreaseSize = () => {
    setTriggerAction('decrease'); 
  };
  // 3) 초기화 트리거
  const resetAction = () => {
    setTriggerAction(null);
  };
  
  // (+) 버튼을 눌러서 로드맵을 새로 추가하려고 할때
  const handleCreateBtn = async () => {
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/mypage/roadmap/add`, {
        method: 'GET',
        credentials: 'include',
      });

      if (!response.ok) {
        throw new Error('로드맵 추가 실패');
      }

      const newRoadmap = await response.json();

      setCustomRoadmapList((prevList) => {
        return [...prevList, newRoadmap]; // 업데이트된 리스트 반환
      });
      onSelectRoadmap(newRoadmap.id);
    } catch (error) {
      console.error('로드맵 추가 중 오류 발생:', error);
    }
  };

  // 편집모드일때 로드맵 이름 onChange 이벤트
  const handlerRoadmapNameInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    console.log(event.target.value);
    setSelectedRoadmap({
      ...selectedRoadmap,
      name: event.target.value
    })
  }

  // 스킬을 새로 추가하거나, 선후관계를 설정할때
  // 1) 스킬을 새로 추가
  const onSelectSkill = (skillName: string) => {
    const maxX = Math.max(...selectedRoadmap.skills.map(skill => skill.position[0]), 0);
    const maxY = Math.max(...selectedRoadmap.skills.map(skill => skill.position[1]), 0);
    let newPosition;

    const generateUniquePosition = () => {
      // 자연스러운 position 추가를 위해 랜덤값 활용
      const getRandomOffset = () => Math.floor(Math.random() * 2) - 1;
      let x = Math.max(0, maxX + getRandomOffset());
      let y = Math.max(0, maxY + getRandomOffset());
      newPosition = [x, y];
      
      // 겹치는 스킬 있는지 확인
      while (selectedRoadmap.skills.some(skill => skill.position[0] === x && skill.position[1] === y)) {
        x += 1;
        y += 1;
        newPosition = [x, y];
      }
    };
    generateUniquePosition();
  
    if(newPosition) {
      const maxId = Math.max(...selectedRoadmap.skills.map(skill => skill.id), 0);
      const newId = maxId + 1;
      const newSkill: RoadmapSkill = {
        // "저장하기" 버튼을 눌러 API콜을 하기 전까지, frontend에서 임시로 ID를 생성하여 사용
        id: newId,
        name: skillName,
        child: [],
        position: newPosition,
        tag: "null",
      };
      console.log(newSkill);
      setSelectedRoadmap({
        ...selectedRoadmap,
        skills: [...selectedRoadmap.skills, newSkill]
      });
    }
  };
  // 2) 노드 2개를 선택해서 선후관계가 정해질 때
  const handleUpdateSkill = (newSkill: RoadmapSkill) => {
    setSelectedRoadmap((roadmap: CustomRoadmapDetail) => {
      const skillExists = roadmap.skills.some(skill => skill.id === newSkill.id);
      if(skillExists) { 
        return {
          ...roadmap,
          skills: roadmap.skills.map(skill => 
            skill.id === newSkill.id ? newSkill : skill
          ),
        }
      }
      return roadmap;
    });
  }
  
  return (
    <div className='w-full h-full flex flex-col items-center'>
      <div className='w-[1400px] h-[90dvh]'>
        <div className='w-full h-full flex justify-between px-24'>
          {isEditMode ? (
            <CustomSkillList 
            skillData={allCategorySkills}
            onSelectSkill={onSelectSkill}
            />
          ) : (
            <CustomRoadmapList 
              roadmapData={customRoadmapList}
              selectedRoadmapId={selectedRoadmap.id ? selectedRoadmap.id : null}
              onSelectRoadmap={onSelectRoadmap}
              handleCreateBtn={handleCreateBtn}
            />
          )}
          <div className='w-4/5 flex flex-col pt-4 px-8'>
            <div className='font-gmarketsansMedium text-2xl mb-2'>나의 커스텀 로드맵</div>
            <div className='w-full flex items-center justify-between mb-2'>
              {isEditMode ? (
                <>
                  <input 
                    className='w-1/2 rounded-xl border-gray-d9 focus:outline-0 font-gmarketsansBold text-lg' 
                    value={selectedRoadmap.name}
                    onChange={handlerRoadmapNameInput}
                  />
                  <div className='w-2/5 flex justify-between'>
                    <div
                      onClick={handleDecreaseSize}
                      className="bg-primary text-white px-4 py-2 rounded-xl cursor-pointer"
                    >
                      {'축소하기(-)'}
                    </div>
                    <div
                      onClick={handleIncreaseSize}
                      className="bg-primary text-white px-4 py-2 rounded-xl cursor-pointer"
                    >
                      {'확대하기(+)'}
                    </div>
                    <div   
                      className='px-3 py-1 rounded-xl border-2 border-primary cursor-pointer hover:bg-gray-ec'
                      onClick={()=>updateRoadmap()}
                    >저장하기</div>
                  </div>
                </>
              ) : (
                <>
                  <div className='font-gmarketsansBold text-lg'>{selectedRoadmap.name}</div>
                  <div 
                    className='px-3 py-1 rounded-xl border-2 border-primary cursor-pointer hover:bg-gray-ec'
                    onClick={()=>setIsEditMode(true)}
                  >수정하기</div>
                </>
              )}
            </div>
            <Roadmap 
              isEditMode={isEditMode} 
              roadmapSkills={selectedRoadmap.skills} 
              style={'h-[75dvh] max-h-[600px] mb-4'} 
              onTriggerAction={triggerAction}
              handleUpdateSkill={handleUpdateSkill}
              onResetAction={resetAction}
            />
          </div>
        </div>
      </div>
    </div>
  );
}