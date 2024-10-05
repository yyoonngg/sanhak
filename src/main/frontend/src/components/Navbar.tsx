import Link from 'next/link';
import Image from 'next/link';
const NavigationBar = () =>{
    return(
        <nav className={"p-4 justify-items-center"}>
            <ul className={"sticky top-0 left-0 right-0 flex space-x-4 font-pretendard"}>
                <li><Link href="/main"><img src="/asset/png/servicelogo.png" alt="logoimg" className={"w-3 h-1"}></img></Link></li>
                <li><Link href="/category" className={"hover:underline"}>직무별로드맵</Link></li>
                <li><Link href="/company" className={"hover:underline"}>기업별로드맵</Link></li>
                <li><Link href="/card" className={"hover:underline"}>AI경험카드</Link></li>
                <li><Link href="/mypage" className={"hover:underline"}>마이페이지</Link></li>
            </ul>
        </nav>
    );
};
export default NavigationBar;