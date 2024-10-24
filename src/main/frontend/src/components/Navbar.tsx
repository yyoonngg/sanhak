import Link from 'next/link';
const NavigationBar = () =>{
    return(
        <nav className={"p-4 justify-center"}>
            <ul className={"sticky top-0 left-0 right-0 bottom-0 justify-center items-center flex space-x-10"}>
                <li><Link href="/main"><img src="/asset/png/servicelogo.png" alt="logoimg" className={"w-20 pb-3"}></img></Link></li>
                <li><Link href="/category" className={"hover:underline text-sm font-gmarketsansMedium"}>직무별로드맵</Link></li>
                <li><Link href="/company" className={"hover:underline text-sm font-gmarketsansMedium"}>기업별로드맵</Link></li>
                <li><Link href="/card" className={"hover:underline text-sm font-gmarketsansMedium"}>AI경험카드</Link></li>
                <li><Link href="/aiChatbot" className={"hover:underline text-sm font-gmarketsansMedium"}>AI경험Chat</Link></li>
                <li><Link href="/mypage" className={"hover:underline text-sm font-gmarketsansMedium"}>마이페이지</Link></li>
            </ul>
        </nav>
    );
};
export default NavigationBar;