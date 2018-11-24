[![Build Status](https://travis-ci.org/Blackdread/canon-sdk-java.svg?branch=master)](https://travis-ci.org/Blackdread/canon-sdk-java)
[![Coverage Status](https://coveralls.io/repos/github/Blackdread/canon-sdk-java/badge.svg?branch=master)](https://coveralls.io/github/Blackdread/canon-sdk-java?branch=master)
[![StackShare](https://img.shields.io/badge/tech-stack-0690fa.svg?style=flat)](https://stackshare.io/Blackdread/canon-sdk-java)

# canon-sdk-java
Canon EOS SDK in java (EDSDK).

Latest headers used to create bindings are with EDSDK 3.9.0 (version released on October 1st 2018)

# Project motives
Project intend to give access to Canon EOS Digital Software Development Kit EDSDK in Java.

Features are:
- Take and download pictures
- Set parameters like Aperture, ISO Speed, etc
- Use live view
- Control multiple cameras
- Simple extendable recorder implementation

Support for MAC OSX is not intended for now, but only few changes in implementation should be required to make it work.

As stated by EDSDK, 64 bit version is given as is but might not work properly. In any case, prefer to use 32 bit version by running on a java of x86.

# Java versions
First release will support java 8.

Two branches will be maintained as to support java 8 and 11 (and maven respectively).

# Usage
To write

# Design note
Any collection/map return type should be considered as immutable, even if implementation does not use ImmutableList/Set/etc. If collection/map is mutable, it will be clearly specified in javadoc, otherwise implementation is free to change to immutable collections anytime. 

# How to contribute pull requests
Do a pull request. Explain your changes, reasons, write tests.

# Previous project
It starts from scratch due to [older project](https://github.com/kritzikratzi/edsdk4j) that is not maintained (and some design could be changed).

# Links

[Previous project that allowed to communicate with Canon EDSDK with java](https://github.com/kritzikratzi/edsdk4j)
