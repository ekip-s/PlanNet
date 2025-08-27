export interface UserInfoModel {
  aiToken: string;
  model: string;
  currentWeight: number;
  desiredWeight: number;
  automaticUpdate: boolean;
  trainPlanOptions: string;
  mealPlanOptions: string;
}
