package org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses;

public class ImuAngularVelocityResponse {
    public final double xr;
    public final double yr;
    public final double zr;

    public ImuAngularVelocityResponse(double xr, double yr, double zr) {
        this.xr = xr;
        this.yr = yr;
        this.zr = zr;
    }
}
