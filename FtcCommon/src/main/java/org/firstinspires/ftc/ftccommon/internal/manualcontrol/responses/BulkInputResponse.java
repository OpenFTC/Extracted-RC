package org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses;

public class BulkInputResponse {
    public int diBf;
    public int m0ep;
    public int m1ep;
    public int m2ep;
    public int m3ep;
    public int msBf;
    public int m0v;
    public int m1v;
    public int m2v;
    public int m3v;
    public int a0;
    public int a1;
    public int a2;
    public int a3;

    public BulkInputResponse(int digitalInputBitField,
                             int motor0EncoderPosition, int motor1EncoderPosition,
                             int motor2EncoderPosition, int motor3EncoderPosition,
                             int motorStatusBitfield,
                             int motor0Velocity, int motor1Velocity, int motor2Velocity, int motor3Velocity,
                             int analog0mV, int analog1mV, int analog2mV, int analog3mV) {
        this.diBf = digitalInputBitField;
        this.m0ep = motor0EncoderPosition;
        this.m1ep = motor1EncoderPosition;
        this.m2ep = motor2EncoderPosition;
        this.m3ep = motor3EncoderPosition;
        this.msBf = motorStatusBitfield;
        this.m0v = motor0Velocity;
        this.m1v = motor1Velocity;
        this.m2v = motor2Velocity;
        this.m3v = motor3Velocity;
        this.a0 = analog0mV;
        this.a1 = analog1mV;
        this.a2 = analog2mV;
        this.a3 = analog3mV;
    }
}
