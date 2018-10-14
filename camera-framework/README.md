# Camera framework java
Canon (EDSDK) EOS SDK in java. Module to use library of EDSDK and send commands to camera.


# Commands


# Command settings

### Timeout
Commands can be canceled by timeout (0 = no timeout).

### Ignore or not camera errors
If not ignored, exceptions are thrown else logic continue if possible else null is returned (or optional)

## Global settings
- timeout 
- ignore or not camera errors

## Settings per command
If a setting is not set for a command, default global setting applies if any.
- timeout
- ignore or not camera errors
