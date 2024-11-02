import {Skill} from "@/models/skill";

type AiCard = {
    id?: number;
    fromDate?: string,
    toDate?: string,
    title?: string,
    category?: string[],
    skills?: Skill[],
    tools?: Tool[],
    reflection?: string,
    imageFile? : File | null;
    imageUrl?: string,
    pdfFile?: File | null;
    pdfName?: String;
    sourceUrl?: string[]
    summary?: String | null;
};

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
};

type ChatRoleOption = {
    label: string;
    description: string;
    guideNotice: string;
};

type AiCardWithNew = AiCard & { isNew?: boolean };