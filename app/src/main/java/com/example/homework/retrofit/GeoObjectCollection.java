package com.example.homework.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoObjectCollection {
    @SerializedName("featureMember")
    @Expose
    private List<FeatureMember> featureMemberList;

    public List<FeatureMember> getFeatureMemberList() {
        return featureMemberList;
    }

    public void setFeatureMemberList(List<FeatureMember> featureMemberList) {
        this.featureMemberList = featureMemberList;
    }
}
