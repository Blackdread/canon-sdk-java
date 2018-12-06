# Camera framework java
Canon (EDSDK) EOS SDK in java. Module to use library of EDSDK and send commands to camera.

# Customization
The framework can be customized by inheritance, this logic has been grouped in the CanonFactory class.

Many commands, class, methods, etc are public (and not final) or protected to leave choice of inheritance and customization.

# Events
Different ways may work to handle events, framework use a default implementation where it uses one unique thread that initialize the library then loop to fetch events. And uses multi-threads to send commands to different cameras.

# Library thread
From EDSDK API reference, it is noted that other threads than the one that initialized the library requires to call another method in order to communicate with the camera. If this is indeed necessary then this framework will encapsulate calls in a single thread created by the framework.
It has already been seen that calling EdsGetEvent from another thread would do nothing and no event would be received but initializing from of pool thread and then calling EdsGetEvent from that same thread allowed to receive events.

# Commands

## Copy


## Decorator


## Interval between commands
If commands that were issued fail (because of DeviceBusy or other reasons)  and retry is required, some cameras may become unstable if multiple commands are issued in succession without an intervening interval.
Thus, leave an interval of about 500 ms before commands are reissued.

# Command settings

### Timeout
Commands can be canceled by timeout (0 = no timeout).

### Ignore or not camera errors
~~If not ignored, exceptions are thrown else logic continue if possible else null is returned (or optional)~~

## Global settings
- timeout 
- ~~ignore or not camera errors~~

## Settings per command
If a setting is not set for a command, default global setting applies if any.
- timeout
- ~~ignore or not camera errors~~


