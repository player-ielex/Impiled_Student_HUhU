package com.example.impiled_students;

public class RewardsModel {

    private String rewards_name,rewards_description,rewards_photo;

    public RewardsModel(){}

    public RewardsModel(String rewards_name, String rewards_description, String rewards_photo){
        this.rewards_name = rewards_name;
        this.rewards_description = rewards_description;
        this.rewards_photo = rewards_photo;
    }

    public String getRewards_name() {
        return rewards_name;
    }

    public void setRewards_name(String rewards_name) {
        this.rewards_name = rewards_name;
    }

    public String getRewards_description() {
        return rewards_description;
    }

    public void setRewards_description(String rewards_description) {
        this.rewards_description = rewards_description;
    }

    public String getRewards_photo() {
        return rewards_photo;
    }

    public void setRewards_photo(String rewards_photo) {
        this.rewards_photo = rewards_photo;
    }
}
