# Camera framework java
Canon (EDSDK) EOS SDK in java. Module to use library of EDSDK and send commands to camera.

# Support project
[![Beerpay](https://beerpay.io/Blackdread/canon-sdk-java/badge.svg)](https://beerpay.io/Blackdread/canon-sdk-java)

# Customization
The framework can be customized by inheritance, this logic has been grouped in the CanonFactory class.

Many commands, class, methods, etc are public (and not final) or protected to leave choice of inheritance and customization.

# Demo of code
See package "org.blackdread.cameraframework.demo" in test folder ([here](https://github.com/Blackdread/canon-sdk-java/tree/master/camera-framework/src/test/java/org/blackdread/cameraframework/demo)).

# Events
Different ways may work to handle events, framework use a default implementation where it uses one unique thread that execute commands.

Initialization of the library should be done that same thread and any further communication as well. 

Framework provide an event fetcher that will send on schedule fetch commands to make the dispatcher fetch events.

Another implementation uses multi-threads to send commands to different cameras and events but is not working properly due to thread model.

Therefore, all commands are implemented to support all use cases which mainly are (other cases exist but mostly derive from those):
- Only one single thread to communicate entirely with Edsdk, commands are implemented to "busy wait" and fetch events when a command should wait for an asynchronous event  
- One thread for fetching events, commands does not fetch event by itself

# Library thread
From EDSDK API reference, it is noted that other threads than the one that initialized the library requires to call another method in order to communicate with the camera. If this is indeed necessary then this framework will encapsulate calls in a single thread created by the framework.
It has already been seen that calling EdsGetEvent from another thread would do nothing and no event would be received but initializing from of pool thread and then calling EdsGetEvent from that same thread allowed to receive events.

# Framework initializer
A class "FrameworkInitialisation" is there to help to initialize the framework, it is optional but it is a good starting point when framework is not known yet.
More functionality will be added to it as needed.

# Commands

## Copy
All commands supports a copy constructor and copy() method, it provides an easy way to re-send a command without knowing how to construct it.

## Decorator
Decorating commands allow to apply extra logic without actually changing the base code or applying it to all commands.

Builders are provided to simplify decoration of commands.

Some decorators provided by default are:
- Default value on error
- Timeout decorator (but actual commands already provide the functionality)

## Interval between commands
If commands that were issued fail (because of DeviceBusy or other reasons)  and retry is required, some cameras may become unstable if multiple commands are issued in succession without an intervening interval.
Thus, leave an interval of about 500 ms before commands are reissued.

# Command settings

### Timeout
Commands can be canceled by timeout (0 = no timeout).

## Global settings
This functionality is not implemented yet.

- timeout 


## Settings per command
If a setting is not set for a command, default global setting applies if any.
- timeout



#### TODO
- Add annotation to specify commands/methods and library functions that have been removed from SDK, allow to specify date, etc.
