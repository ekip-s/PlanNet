export interface TargetModel {
    id: string;
    type: string;
    status: string;
    title: string;
    description: string | null;
    createdAt: string;
}

export interface TargetDetailModel {
    id: string;
    type: string;
    status: string;
    title: string;
    description: string | null;
    createdAt: Date;
    subtarget: TargetModel[];
}

export interface PageTargetModel {
    _embedded: {
        targetResponseList: TargetModel[];
    },
    page: {
        size: number
        totalElements: number;
        totalPages: number;
        number: number;
    }
}
