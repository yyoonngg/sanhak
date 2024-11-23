'use client';
import React, { ReactElement, useState } from 'react';
import CustomRoadmapList from './CustomRoadmapList';
import Roadmap from '../category/Roadmap';
import { AllKindOfSkills, RoadmapSkill } from '@/models/skill';
import CustomSkillList from './CustomSkillList';

// TODO 1: API 연결 -> 유저의 커스텀 로드맵 리스트
const customRoadmapList: CustomRoadmapName[] = [
  {id: 1, name: "나의 엄청난엄청난엄청난엄청난엄청난 로드맵"},
  {id: 2, name: "카카오 로드맵"},
  {id: 3, name: "삼성 로드맵"},
];

// TODO 2: API 연결 -> 위에서 선택된 로드맵에 해당하는 디테일한 데이터
const customRoadmap: CustomRoadmapDetail = {
  id: 1,
  name: "나의 엄청난엄청난엄청난엄청난엄청난 로드맵",
  skills: [
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
      { id: 18,name:'PostCSS'},
      { id: 19,name:'Axios'},
      { id: 20,name:'Web Socket'},
      { id: 21,name:'ESLint'},
      { id: 22,name:'Webpack'},
      { id: 23,name:'GraphQL'},
      { id: 24,name:'Cypress'},
      { id: 25,name:'Jest'},
      { id: 26,name:'MobX'},
      { id: 27,name:'Vercel'},
      { id: 28,name:'AWS S3'},
      { id: 29,name:'Netlify'},
  ]},
  { category:"backend",
    skills: [
      { id: 18,name:'PostCSS'},
      { id: 19,name:'Axios'},
      { id: 20,name:'Web Socket'},
      { id: 21,name:'ESLint'},
      { id: 22,name:'Webpack'},
      { id: 23,name:'GraphQL'},
      { id: 24,name:'Cypress'},
      { id: 25,name:'Jest'},
      { id: 26,name:'MobX'},
      { id: 27,name:'Vercel'},
      { id: 28,name:'AWS S3'},
      { id: 29,name:'Netlify}'},
      { id : 30, name :'Python'},
      { id : 31, name :'Java'},
      { id : 32, name :'Kotlin'},
      { id : 33, name :'JavaScript'},
      { id : 34, name :'TypeScript'},
      { id : 35, name :'Ruby'},
      { id : 36, name :'PHP'},
      { id : 37, name :'C#'},
      { id : 38, name :'Flask'},
      { id : 39, name :'Spring'},
      { id : 40, name :'Express.js'},
      { id : 41, name :'Next.js'},
      { id : 42, name :'Fresh'},
      { id : 43, name :'Ruby on rail'},
      { id : 44, name :'Laravel'},
      { id : 45, name :'Symfony'},
      { id : 46, name :'ASP.NET'},
      { id : 47, name :'Django'},
      { id : 48, name :'Fast API'},
      { id : 49, name :'Spring Boot'},
      { id : 50, name :'Nest.js'},
      { id : 51, name :'Nuxt.js'},
      { id : 52, name :'Electron'},
      { id : 53, name :'Pytest'},
      { id : 54, name :'JUnit5'},
      { id : 55, name :'Jest'},
      { id : 56, name :'RSpec'},
      { id : 57, name :'PHPUnit'},
      { id : 58, name :'NUnit'},
      { id : 59, name :'SQLAlchemy'},
      { id : 60, name :'Pypika'},
      { id : 61, name :'Hibernate'},
      { id : 62, name :'My batis'},
      { id : 63, name :'Sequelize.js'},
      { id : 64, name :'Prisma'},
      { id : 65, name :'Knex.js'},
      { id : 66, name :'ROM.rb'},
      { id : 67, name :'Doctrine'},
      { id : 68, name :'EF Core'},
      { id : 69, name :'Dapper'},
      { id : 70, name :'H2 Database'},
      { id : 71, name :'MySQL'},
      { id : 72, name :'PostgreSQL'},
      { id : 73, name :'SQLite'},
      { id : 74, name :'MariaDB'},
      { id : 75, name :'MongoDB'},
      { id : 76, name :'Cassandra'},
      { id : 77, name :'Redis'},
      { id : 78, name :'Firebase'},
      { id : 79, name :'Hadoop'},
      { id : 80, name :'Couchbase'},
      { id : 81, name :'AWS'},
      { id : 82, name :'GCP'},
      { id : 83, name :'Azure'},
      { id : 84, name :'Apache'},
      { id : 85, name :'Nginx'},
      { id : 86, name :'Tomcat'},
      { id : 87, name :'Websphere'},
      { id : 88, name :'uWSGI'},
      { id : 89, name :'Gunicon'},
      { id : 90, name :'Jenkins'},
      { id : 91, name :'Git Lab'},
      { id : 92, name :'Git Action'},
      { id : 93, name :'ArgoCD'},
      { id : 94, name :'Docker'},
      { id : 95, name :'Kubernetis'},
      { id : 96, name :'AWS ELB'},
      { id : 97, name :'HAProxy'},
      { id : 98, name :'Elastic Stack'},
      { id : 99, name :'Grafana'},
      { id : 100, name :'Open SSL'},
      { id : 101, name :'OAuth'},
      { id : 102, name :'JWT'},
      { id : 103, name :'Kafka'},
      { id : 104, name :'Cloudflare'},
      { id : 105, name :'Zipkin'},
  ]},
  { category:"data",
    skills: [
      {id:106, name:'Python'},
      {id:107, name:'Bash'},
      {id:108, name:'Pandas'},
      {id:109, name:'Matplotlib'},
      {id:110, name:'Seaborn'},
      {id:111, name:'Jupyter'},
      {id:112, name:'Tableau'},
      {id:113, name:'Power Bi'},
      {id:114, name:'R'},
      {id:115, name:'ggplot2'},
      {id:116, name:'Tidyverse'},
      {id:117, name:'Rayserve'},
      {id:118, name:'MLflow'},
      {id:119, name:'Flask'},
      {id:120, name:'Fast API'},
      {id:121, name:'Scipy'},
      {id:122, name:'Tensorflow'},
      {id:123, name:'Pytorch'},
      {id:124, name:'Stats models'},
      {id:125, name:'scikit learn'},
      {id:126, name:'sharp'},
      {id:127, name:'keras'},
      {id:128, name:'Huggingface'},
      {id:129, name:'ONNX'},
      {id:130, name:'Hadoop'},
      {id:131, name:'Spark'},
      {id:132, name:'Kafka'},
      {id:133, name:'Flink'},
      {id:134, name:'Docker'},
      {id:135, name:'Kubernetes'},
      {id:136, name:'AWS'},
      {id:137, name:'GCP'},
      {id:138, name:'Azure'},
      {id:139, name:'terraform'},
  ]},
  { category:"security",
    skills: [
      {id:156,name:'Python'},
      {id:157,name:'C'},
      {id:158,name:'Web'},
      {id:159,name:'Binary'},
      {id:160,name:'Assembly'},
      {id:161,name:'TCP IP'},
      {id:162,name:'BASH'},
      {id:163,name:'MicroSoft'},
      {id:164,name:'Cryptography'},
      {id:165,name:'NAT'},
      {id:166,name:'Cookie'},
      {id:167,name:'XSS'},
      {id:168,name:'INJECTION'},
      {id:169,name:'AWS'},
      {id:170,name:'Blockchain'},
      {id:171,name:'FIREWALL'},
      {id:172,name:'Linux'},
      {id:173,name:'IPS'},
      {id:174,name:'IDS'},
      {id:175,name:'VPN'},
      {id:176,name:'Linuxlib'},
      {id:177,name:'HTTP'},
  ]},
  { category:"application",
    skills: [
      {id:178,name:'HTML'},
      {id:179,name:'CSS'},
      {id:180,name:'JavaScript'},
      {id:181,name:'TypeScript'},
      {id:182,name:'WebView'},
      {id:183,name:'Dart'},
      {id:184,name:'Swift'},
      {id:185,name:'Objective-C'},
      {id:186,name:'SASS'},
      {id:187,name:'TailWind'},
      {id:188,name:'Bootstrap'},
      {id:189,name:'React'},
      {id:190,name:'Angular'},
      {id:191,name:'Vue.js'},
      {id:192,name:'Cordova'},
      {id:193,name:'Ionic'},
      {id:194,name:'NativeScript'},
      {id:195,name:'React Native'},
      {id:196,name:'Flutter'},
      {id:197,name:'Redux'},
      {id:198,name:'Vuex'},
      {id:199,name:'RxJS'},
      {id:200,name:'Streams'},
      {id:201,name:'Prisma'},
      {id:202,name:'MySQL'},
      {id:203,name:'PostgreSQL'},
      {id:204,name:'MongoDB'},
      {id:205,name:'Firebase'},
      {id:206,name:'Axios'},
      {id:207,name:'WebSocket'},
      {id:208,name:'GraphQL'},
      {id:209,name:'Appium'},
      {id:210,name:'Cypress'},
      {id:211,name:'Jest'},
      {id:212,name:'PWA'},
      {id:213,name:'Expo'},
      {id:214,name:'SwiftUI'},
      {id:215,name:'UIKit'},
      {id:216,name:'CoreData'},
      {id:217,name:'Realm'},
      {id:218,name:'Alamofire'},
      {id:219,name:'SwiftTesting'},
      {id:220,name:'Java'},
      {id:221,name:'Kotlin'},
      {id:222,name:'JetpackCompose'},
      {id:223,name:'AndroidStudio'},
      {id:224,name:'SQLite'},
      {id:225,name:'Room'},
      {id:226,name:'OkHttp'},
      {id:227,name:'Retrofit'},
      {id:228,name:'Espresso'},
    ]},
];

export default function CustomRoadmapPage() {
  const [selectedRoadmap, setSelectedRoadmap] = useState<CustomRoadmapDetail>(customRoadmap);
  const [triggerAction, setTriggerAction] = useState<null | 'increase' | 'decrease'>(null);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);

  // TODO 4: 로드맵 GET API 호출
  const onSelectRoadmap = (roadmapId: number) => {
    const selected = customRoadmapList.find((roadmap) => roadmap.id === roadmapId);
    if (selected) {
      // setSelectedRoadmap(selected);
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
  const handleCreateBtn = () => {
    setIsEditMode(true);
    setSelectedRoadmap({
      name: '',
      skills: []
    })
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
      let x = maxX + getRandomOffset(); 
      let y = maxY + getRandomOffset(); 
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
        tag: "none"
      };
      console.log(newSkill);
      setSelectedRoadmap({
        ...selectedRoadmap,
        skills: [...selectedRoadmap.skills, newSkill]
      });
    };
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