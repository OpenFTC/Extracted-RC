package org.firstinspires.ftc.robotcore.internal.camera.libuvc.api;

import org.firstinspires.ftc.robotcore.external.function.Supplier;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.FocusControl;
import org.firstinspires.ftc.robotcore.internal.camera.libuvc.nativeobject.UvcDeviceHandle;
import org.firstinspires.ftc.robotcore.internal.system.Tracer;

@SuppressWarnings("WeakerAccess")
public class UvcApiFocusControl implements FocusControl
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "UvcApiFocusControl";
    public String getTag() { return TAG; }
    public static boolean TRACE = true;
    protected Tracer tracer = Tracer.create(getTag(), TRACE);

    protected UvcDeviceHandle uvcDeviceHandle; // no ref

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public UvcApiFocusControl(UvcDeviceHandle uvcDeviceHandle)
        {
        this.uvcDeviceHandle = uvcDeviceHandle;
        }

    //----------------------------------------------------------------------------------------------
    // FocusControl
    //----------------------------------------------------------------------------------------------

    @Override public Mode getMode()
        {
        return uvcDeviceHandle.getFocusMode();
        }

    @Override public boolean setMode(final Mode mode)
        {
        return tracer.traceResult(tracer.format("setMode(%s)", mode), new Supplier<Boolean>()
            {
            @Override public Boolean get()
                {
                return uvcDeviceHandle.setFocusMode(mode);
                }
            });
        }

    @Override public boolean isModeSupported(final Mode mode)
        {
        return tracer.traceResult(tracer.format("isModeSupported(%s)", mode), new Supplier<Boolean>()
            {
            @Override public Boolean get()
                {
                return uvcDeviceHandle.isFocusModeSupported(mode);
                }
            });
        }


    @Override public double getMinFocusLength()
        {
        return tracer.traceResult("getMinFocusLength()", new Supplier<Double>()
            {
            @Override public Double get()
                {
                return uvcDeviceHandle.getMinFocusLength();
                }
            });
        }

    @Override public double getMaxFocusLength()
        {
        return tracer.traceResult("getMaxFocusLength()", new Supplier<Double>()
            {
            @Override public Double get()
                {
                return uvcDeviceHandle.getMaxFocusLength();
                }
            });
        }

    @Override public double getFocusLength()
        {
        return tracer.traceResult("getFocusLength()", new Supplier<Double>()
            {
            @Override public Double get()
                {
                return uvcDeviceHandle.getFocusLength();
                }
            });
        }

    @Override public boolean setFocusLength(final double focusLength)
        {
        return tracer.traceResult(tracer.format("setFocusLength(%s)", focusLength), new Supplier<Boolean>()
            {
            @Override public Boolean get()
                {
                return uvcDeviceHandle.setFocusLength(focusLength);
                }
            });
        }

    @Override public boolean isFocusLengthSupported()
        {
        return tracer.traceResult("isFocusLengthSupported", new Supplier<Boolean>()
            {
            @Override public Boolean get()
                {
                return uvcDeviceHandle.isFocusLengthSupported();
                }
            });
        }
    }
