import React, {useEffect, useState} from 'react';
import Card from './Card';
import ButtonLabel from '@/components/ButtonLabel';
import CardEditorFormSection from './CardEditorFormSection';
import Input from '@/components/Input';
import {AllKindOfSkills, Skill} from "@/models/skill";
import {AiCard} from "@/models/card";

type CardEditorProps = {
  selectedCard: AiCard | null,
  onCancel: () => void,
  onCreate: (card: any) => void,
  onModify: (card: any) => void,
};

// TODO: API 연결 
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

// TODO: API 연결
const allTools: Tool[] = [
  { id: 1, name: "GitHub" },
  { id: 2, name: "Figma" },
  { id: 3, name: "Visual Studio Code" },
  { id: 4, name: "Jira" },
  { id: 5, name: "Slack" },
  { id: 6, name: "Trello" },
  { id: 7, name: "Postman" },
  { id: 8, name: "Docker" },
  { id: 9, name: "Kubernetes" },
  { id: 10, name: "Notion" },
  { id: 11, name: "Zoom" },
  { id: 12, name: "GitLab" },
  { id: 13, name: "Bitbucket" },
  { id: 14, name: "Jenkins" },
  { id: 15, name: "CircleCI" },
  { id: 16, name: "IntelliJ IDEA" },
  { id: 17, name: "Eclipse" },
  { id: 18, name: "PyCharm" },
  { id: 19, name: "WebStorm" },
  { id: 20, name: "Android Studio" },
  { id: 21, name: "Xcode" },
  { id: 22, name: "Swagger" },
  { id: 23, name: "Firebase" },
  { id: 24, name: "Heroku" },
  { id: 25, name: "AWS CLI" },
  { id: 26, name: "Azure DevOps" },
  { id: 27, name: "Google Cloud Console" },
  { id: 28, name: "MySQL Workbench" },
  { id: 29, name: "PgAdmin" },
  { id: 30, name: "Robo 3T" },
];


const categories = ['frontend', 'backend', 'data', 'security', 'application'];
export default function CardEditor({
  onCreate,
  onModify,
  onCancel,
  selectedCard
}: CardEditorProps) {
  const [card, setCard] = useState<AiCard | null>(selectedCard);
  const isNewCard = selectedCard ? false : true;
  const [buttonStyles, setButtonStyles] = useState(Array(categories.length).fill('border-primary'));
  const [selectedSkills, setSelectedSkills] = useState<Skill[]>([]);
  const [isLoading, setIsLoading] = useState(false); // 로딩

  // 1. 제목과 기간
  const handlerTitleInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCard(prevCard => ({
      ...prevCard,
      title: event.target.value,
    }))
  }
  const handlerFromDateInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCard(prevCard => ({
      ...prevCard,
      fromDate: event.target.value
    }))
  }
  const handlerToDateInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCard(prevCard => ({
      ...prevCard,
      toDate: event.target.value
    }))
  }

  // 2. 카테고리
  useEffect(() => {
    if (card?.category) {
      const newStyles = categories.map(c =>
        card.category?.includes(c) ? '' : 'border-primary'
      );
      setButtonStyles(newStyles);
    }
  }, [card?.category, categories]);

  const handleCategoryClick = (index: number) => {
    const newStyles = [...buttonStyles];
    newStyles[index] = newStyles[index] === '' ? 'border-primary' : '';
    setButtonStyles(newStyles);

    const category = categories[index];

    setCard(prevCard => {
      const currentCategories = prevCard?.category || [];
      const updatedCategories = currentCategories.includes(category)
      ? currentCategories.filter(c => c !== category)
      : [...currentCategories, category];
      return {
        ...prevCard,
        category: updatedCategories
      }
    })
  };

  // 3. 언어와 스킬
  useEffect(() => {
    const filteredSkills = allCategorySkills
      .filter(skill => card?.category?.includes(skill.category)) 
      .flatMap(skill => skill.skills); 

    setSelectedSkills(filteredSkills);
  }, [card?.category]);
  
  const handleSkillClick = (skill: Skill) => {
    setCard(prevCard => {
      const currentSkills = prevCard?.skills || [];
      const updatedSkills = currentSkills.some(s=> skill.id === s.id)
        ? currentSkills.filter(s => s.id !== skill.id)
        : [...currentSkills, skill];
      
      return {
        ...prevCard,
        skills: updatedSkills
      };
    });
  };

  // 4 개발 도구
  const handleToolClick = (tool: Tool) => {
    setCard(prevCard => {
      const currentTools = prevCard?.tools || [];
      const updatedTools = currentTools.some(t => tool.id === t.id)
      ? currentTools.filter(t => t.id !== tool.id)
      : [...currentTools, tool];

      return {
        ...prevCard,
        tools: updatedTools
      }
    })
  }
 
  // 5. 경험 회고
  const handlerReflectionInput = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setCard(prevCard => ({
      ...prevCard,
      reflection: event.target.value
    }))
  }

  // 6. 경험 이미지 업로드
  // const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
  //   const file = event.target.files?.[0];
  //   if (file) {
  //     setCard(prevCard => ({
  //       ...prevCard,
  //       imageFile : file,
  //       imageUrl: `/asset/png/profile/${file.name}`
  //     }))
  //   }
  // };

  // 6. pdf 참고자료
  const handlePdfUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setCard(prevCard => ({
        ...prevCard,
        pdfFile:file,
        pdfName: file.name
      }));
    }
  };
  
  // 7. 소스 URL
  const handleSourceUrlInput = (index: number, value: string) => {
    setCard(prevCard => {
      const newSourceUrl = [...(prevCard?.sourceUrl || [])];
      newSourceUrl[index] = value;
      return {
        ...prevCard,
        sourceUrl: newSourceUrl
      };
    });
  };

  // 8. AI 이미지
  const getRandomImageUrl = (): string => {
    const randomIndex = Math.floor(Math.random() * 20) + 1; 
    return `/asset/png/card/card_img_${randomIndex}.png`; // 경로 수정
  };
  const fetchImageAsFile = async (imageUrl: string): Promise<File | null> => {
    try {
      const response = await fetch(imageUrl);
      if (!response.ok) throw new Error('Network response was not ok');

      const blob = await response.blob(); // Blob으로 변환
      const file = new File([blob], 'card_image.png', { type: blob.type }); // File 객체 생성
      return file;
    } catch (error) {
      console.error('Error fetching image:', error);
      return null;
    }
  };

  const handleSaveClick = async () => { // 비동기로 변경
    if (isNewCard) {
      const randomImageUrl = getRandomImageUrl(); // 랜덤 이미지 URL 생성
      const imageFile = await fetchImageAsFile(randomImageUrl);

      // 카드 상태 업데이트
      setCard(prevCard => ({
        ...prevCard,
        imageUrl: randomImageUrl,
        imageFile: imageFile,
      }));

      // 카드 생성 호출
      if (imageFile) { 
        onCreate({ ...card, imageFile });
      }
    } else {
      onModify(card);
    }
  };

  const handleCancelClick = () => {
    onCancel();
  }

  // 디버깅용
  useEffect(()=> {
    console.log(card);
    console.log(isNewCard);
  }, [card]);
  
  return (
    <div className='w-full h-full flex flex-row justify-between px-24'>
      <div className='w-[65%] h-full flex flex-col items-end'>
        <CardEditorFormSection title="경험의 제목과 기간을 입력해주세요">
          <div className='flex flex-row w-full justify-around mb-2'>
            <div className='w-1/6 mr-2 text-sm rounded-xl border-gray-d9 bg-primary font-semibold text-white flex justify-center items-center'>경험 제목</div>
            <div className='w-5/6'>
              <Input 
                type='text'
                className='w-full rounded-xl border-gray-d9 text-sm focus:outline-0'
                placeholder='제목을 입력해주세요'
                value={card?.title}
                onChange={handlerTitleInput}
              />
            </div>
          </div>
          <div className='flex flex-row w-full justify-around'>
            <div className='w-1/6 mr-2 text-sm rounded-xl border-gray-d9 bg-primary font-semibold text-white flex justify-center items-center'>기간</div>
            <div className='w-5/6 flex flex-row'>
              <div className='flex flex-row w-1/6'>
                <Input 
                  type='text'
                  className='w-full rounded-xl border-gray-d9 text-sm focus:outline-0'
                  placeholder='YYYY-MM-DD'
                  value={card?.fromDate}
                  onChange={handlerFromDateInput}
                />
              </div>
              <div className='flex text-2xl text-gray-d9 justify-center items-center mx-2'>&#126;</div>
              <div className='flex flex-row w-1/6'>
                <Input 
                  type='text'
                  className='w-full rounded-xl border-gray-d9 text-sm focus:outline-0'
                  placeholder='YYYY-MM-DD'
                  value={card?.toDate}
                  onChange={handlerToDateInput}
                />
              </div>
            </div>
          </div>
        </CardEditorFormSection>
        { card?.title && card?.fromDate && card?.toDate && card?.title.length > 0 && (
          <CardEditorFormSection title="내가 맡았던 역할은 무엇인가요?">
            <div className='flex flex-row w-full justify-between'>
              {categories.map((c, index) => (
                <ButtonLabel 
                  key={c}
                  type="category" 
                  label={c} 
                  style={buttonStyles[index]}
                  onClick={() => handleCategoryClick(index)}
                />
              ))}
            </div>
          </CardEditorFormSection>
        )}
        { card?.category && card?.category.length > 0 && (
          <CardEditorFormSection title="어떤 언어와 스킬을 경험해보았나요?">
            <div className='flex flex-row w-full flex-wrap'>
              {selectedSkills.map(s => (
                <ButtonLabel 
                  key={s.id} 
                  type="skill" 
                  label={s.name} 
                  style={card?.skills?.some(skill => skill.id === s.id) ? '' : 'border-primary'}
                  onClick={()=>handleSkillClick(s)}
                />
              ))}
            </div>
          </CardEditorFormSection>
        )}
        { card?.skills && card?.skills.length > 0 && (
          <CardEditorFormSection title="어떤 개발 도구를 경험해보았나요?">
            <div className='flex flex-row w-full flex-wrap'>
              {allTools.map(t => (
                <ButtonLabel 
                  key={t.id} 
                  type="tool" 
                  label={t.name} 
                  style={card?.tools?.some(tool => tool.id === t.id) ? '' : 'border-primary'}
                  onClick={()=>handleToolClick(t)}
                />
              ))}
            </div>
          </CardEditorFormSection>
        )}
        { card?.tools && card?.tools.length > 0 && (
          <CardEditorFormSection title="이번 경험에 대하여">
            <textarea
              className='w-full h-52 rounded-xl border-gray-d9 text-sm focus:outline-0 p-3'
              value={card?.reflection}
              placeholder='느낀점, 배운점, 더 알아보고 싶은 점 등 자유롭게 적어주세요'
              style={{ textAlign: 'left', verticalAlign: 'top' }}
              onChange={handlerReflectionInput}
            />
          </CardEditorFormSection>
        )}
        {/* { card?.reflection && card?.reflection.length > 0 && (
          <CardEditorFormSection title="사진으로 경험을 보여주세요">
            <div className='w-full flex'>
              <label className='cursor-pointer inline-flex items-center mr-4 py-2 px-4 rounded-lg border-0 text-sm font-semibold bg-primary text-white'>
                <Input 
                  type='file'
                  accept='image/*'
                  className='hidden'
                  onChange={handleImageUpload}
                />
                파일 선택
              </label>
              {card?.imageUrl && (
                <div className="mt-2 font-semibold">{card?.imageUrl}</div> 
              )}
            </div>
            {card?.imageUrl && (
              <img
                src={card?.imageUrl}
                alt="Preview"
                className="mt-4 w-1/2 h-1/2 object-cover border border-gray-d9 rounded-xl"
              />
            )}
          </CardEditorFormSection>
        )} */}
        { card?.reflection && card?.reflection.length > 0 && (
          <CardEditorFormSection title="경험을 가장 잘 보여주는 자료(.pdf)">
            <div className='w-full flex'>
              <label className="cursor-pointer inline-flex items-center mr-4 py-2 px-4 rounded-lg border-0 text-sm font-semibold bg-primary text-white">
                <Input 
                  type='file'
                  accept='.pdf'
                  className='hidden'
                  onChange={handlePdfUpload}
                />
                파일 선택
              </label>
              {card?.pdfFile && (
                <div className="mt-2 font-semibold">{card?.pdfName}</div>
              )}
            </div>
          </CardEditorFormSection>
        )}
        { card?.pdfName && card?.pdfName.length > 0 && (
          <CardEditorFormSection title="경험을 보여주는 소스 URL을 입력해주세요">
            {[0, 1].map(index => (
              <Input 
                key={index}
                value={card?.sourceUrl?.[index]}
                type='text'
                className='w-full mb-2 rounded-xl border-gray-d9 text-sm focus:outline-0'
                placeholder='ex) Github, Figma, ...'
                onChange={(e) => handleSourceUrlInput(index, e.target.value)}
              />
            ))}
          </CardEditorFormSection>
        )}
      </div>
      <div className='w-1/3 flex flex-col items-center'>
        <div className='fixed'>
          <Card card={card}/>
          {card?.sourceUrl && card?.sourceUrl.length > 0 ? (
            <div
              className='cursor-pointer w-full flex justify-center font-semibold bg-primary text-white border-2 border-primary hover:text-primary hover:bg-white px-4 py-2 mt-2 rounded-xl'
              onClick={handleSaveClick}
            >
              {isNewCard ? 'AI경험카드 생성하기' : 'AI경험카드 수정하기'}
            </div>
          ) :
          (
            <div
              className='cursor-pointer w-full flex justify-center font-semibold bg-primary text-white border-2 border-primary hover:text-primary hover:bg-white px-4 py-2 mt-2 rounded-xl'
              onClick={handleCancelClick}
            >
             {'AI경험카드 관리 돌아가기'}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}