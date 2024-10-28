type AiCard = {
    id?: number;
    fromDate?: string,
    toDate?: string,
    title?: string,
    category?: string[],
    skills?: Skill[],
    tools?: Tool[],
    reflection?: string,
    imageUrl?: string,
    pdfFile?: string,
    sourceUrl?: string[]
}

type AiCardChatRoom = {
    id: number;
    cardId: number;
    title: string;
    role: string;
};

type AiCardChat = {
    id: number;
    isUser: number; // 0: AI, 1: User
    content: string;
}

type ChatRoleOption = {
    label: string;
    description: string;
    guideNotice: string;
}