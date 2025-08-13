export interface TargetModel {
    id: string;
    type: string;
    status: string;
    title: string;
    description: string | null;
    createdAt: Date;
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