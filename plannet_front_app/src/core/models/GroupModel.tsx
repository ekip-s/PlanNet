export interface GroupModel {
    id: string,
    createdAt: Date,
    name: string,
    groupUsers: GroupUserModel[],
    services: GroupServiceModel[],
}

export interface GroupUserModel {
    userId: string,
    userLogin: string,
    createdAt: Date,
    role: string,
}

export interface GroupServiceModel {
    id: string,
    createdAt: Date,
    name: string,
}