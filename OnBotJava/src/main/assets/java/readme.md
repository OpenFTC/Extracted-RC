# Welcome to the OnBotJava Code Editor

If you are just starting out, click the '+' (Add File) icon in the top left corner.
Enter your new file name, and then choose one of the many samples.
If you just want to drive a basic robot, select the "BasicOpMode_Linear" sample.
Select the "TeleOp" radio button, and then click "OK".

The sample you chose will be renamed to match the name you entered, and it 
will appear on the "project files" list in the left pane.

To edit your code, just click on the desired file in the left hand pane, 
and it will be loaded into this Code Editor window. Make any changes. You can also
use Ctrl-F (or Cmd-F on Macs) to search the file you are working on.

Once you are done, click the "Build Everything" icon at the bottom of this pane.
This will build your OpModes and report any errors.
If there are no errors, the OpModes will be stored on the Robot for immediate use.

## Samples

There are a range of different samples to choose from.
Sample names use a convention which helps to indicate their general, and specific, purpose.

eg: The name's prefix describes the general purpose, which can be one of the following:

* Basic:    This is a minimally functional OpMode used to illustrate the skeleton\/structure
            of a particular style of OpMode.  These are bare bones examples.
* Sensor:   This is a Sample OpMode that shows how to use a specific sensor.
            It is not intended as a functioning robot, it is simply showing the minimal code
            required to read and display the sensor values.
* Robot:    This is a Sample OpMode that assumes a simple two-motor (differential) drive base.
            It may be used to provide a common baseline driving OpMode, or
            to demonstrate how a particular sensor or concept can be used to navigate.
* Concept:	This is a sample OpMode that illustrates performing a specific function or concept.
            These may be complex, but their operation will be explained clearly in the comments,
            or the header should reference an external doc., guide or tutorial.

For more help, visit the FTC Control System Wiki (https://github.com/FIRST-Tech-Challenge/FtcRobotController/wiki)

Please report any encountered issues on [GitHub](https://github.com/FIRST-Tech-Challenge/FtcRobotController).

## What's Java 8?

Java 8 provides more ways to help you write more concise, readable, and maintainable code
for your robot.

We're adding Java 8 editor support to this SDK release. 
To enable support, you can enable `Enable beta Java 8 editor features` in the Settings menu.

For an example, take the following code snippet from
the `ConceptTelemetry`[0] sample you might have already seen in prior years

```
/**
 * As an illustration, the first line on our telemetry display will display the
 * battery voltage. The idea here is that it's expensive to compute the voltage
 * (at least for purposes of illustration) so you don't want to do it unless the
 * data is <em>actually</em> going to make it to the driver station (recall that
 * telemetry transmission is throttled to reduce bandwidth use.
 * Note that getBatteryVoltage() below returns 'Infinity' if there's no voltage
 * sensor attached.
 *
 * @see Telemetry#getMsTransmissionInterval()
 */
telemetry.addData("voltage", "%.1f volts", new Func<Double>() {
    @Override public Double value() {
        return getBatteryVoltage();
    }
});
```

There's a few ways to rewrite the same snippet to be more concise by using 
what's called a lambda (lamb-da) expression[1] from the new Java 8 editor support.

The part of the code that simply defines what's called an "anonymous class"
can be actually rewritten as a lambda expression.

The anonymous class above is the following section:
```
new Func<Double>() {
    @Override public Double value() {
        return getBatteryVoltage();
    }
}
```

What you actually care about is the return value (`getBatteryVoltage()`)
of the function you defined, not the wrapping syntax (`new Func<Double>{...}`).

As a general pattern, for a function that takes no arguments, you can remove
the class declaration, method return type, and method name and the compiler
will get what you are implying.

To see this visually, this is the rewritten lambda expression:
```
() -> {
    // this area wrapped by the curly braces is the lambda body
    return getBatteryVoltage();
}
```

This leaves the lambda body intact and is a useful pattern if your return value
needs a multiline calculation.

In this case, we only have the method return taking a single line, so we can
"unwrap" the return expression:
```
() -> getBatteryVoltage()
```

This again leaves a bit of room to make more concise, applying the same pattern,
what can we remove?

```
this::getBatteryVoltage
```

This is called a method reference, which requires only two things, the object and
the method you want to call on the object,
Advanced note: the `this` comes from the `this` that was implied indirectly in the anonymous class
usage through a capture, but now you actually need to add this `this` reference.

Put all together, the same snippet can be rewritten simply as:
```
telemetry.addData("voltage", "%.1f volts", this::getBatteryVoltage);
```


We're still bound by some limitations of the Android platform, you can check
https://developer.android.com/studio/write/java8-support-table for known 
limitations for more advanced features.

### Known limitations
- Android 6.0 Marshmallow devices cannot use Java 8 features
- `IntStream` usage via direct usage can cause Robot Controller crashes. 
  - You can use `Arrays.of(new int[]{1})` as a workaround.

0: https://github.com/FIRST-Tech-Challenge/FtcRobotController/blob/00cbf344526d991ce33452437018af9f119d22d1/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/ConceptTelemetry.java#L110-L123
1: https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
2: https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html#accessing




