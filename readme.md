This is a home for the SDK after all the AARs have been extracted. It is the base for the OpenRC refresh.

Notes:

 - Currently holds v5.1
 - FIXME: Script_c_format_convert
 - annotations JAR replaced with gradle line
 - gson JAR replaced with gradle line (extras not published to maven/jcenter, so extras JAR still needed)
 - ~~JavaWebSocket JAR replaced with gradle line~~ (1.4.1 not on maven/jcenter yet)
 - ~~SL4J JAR omitted because aforementioned gradle line seems to pull it in~~
 - tensorflow-lite AAR replaced with gradle line
 - tfod FTC AAR replaced with gradle line (repacked AAR for Bintray)
 - temporarily added OpenFTC maven repo to root gradle file so above line resolved before repo linked to jcenter