package org.firstinspires.inspection;

import com.google.gson.annotations.SerializedName;

public class InspectionStateValidation {
    InspectionProperty rev;
    InspectionProperty name;
    InspectionProperty os;
    @SerializedName("av")
    InspectionProperty appVersion;
    @SerializedName("bt")
    InspectionProperty bluetooth;
    InspectionProperty wifi;
    @SerializedName("fw")
    InspectionProperty firmware;
    @SerializedName("pw")
    InspectionProperty password;
    @SerializedName("am")
    InspectionProperty airplaneMode;
    @SerializedName("ln")
    InspectionProperty localNetworks;
    // TODO This is in the checklist but no rule requires it...
    @SerializedName("rw")
    InspectionProperty rememberedWifi;
    @SerializedName("oa")
    InspectionProperty otherApp;
    @SerializedName("vm")
    InspectionProperty versionsMatch;
}
