package ru.darkt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {

    private String aiToken;
    private Model model;
    private Double currentWeight;
    private Double desiredWeight;
    private Boolean automaticUpdate;
    private String trainPlanOptions;
    private String mealPlanOptions;

    @Override
    public String toString() {
        return "UserInfoRequest{" +
                "aiToken='" + aiToken + '\'' +
                ", model=" + model +
                ", currentWeight=" + currentWeight +
                ", desiredWeight=" + desiredWeight +
                ", automaticUpdate=" + automaticUpdate +
                '}';
    }
}
