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

type AiCardChat = {
    id: number;
    sender: string;
    message: string;
}