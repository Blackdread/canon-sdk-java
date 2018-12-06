[![Build Status](https://travis-ci.org/Blackdread/canon-sdk-java.svg?branch=master)](https://travis-ci.org/Blackdread/canon-sdk-java)
[![Coverage Status](https://coveralls.io/repos/github/Blackdread/canon-sdk-java/badge.svg?branch=master)](https://coveralls.io/github/Blackdread/canon-sdk-java?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.blackdread/canon-sdk-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.blackdread/canon-sdk-java)
[![StackShare](https://img.shields.io/badge/tech-stack-0690fa.svg?style=flat)](https://stackshare.io/Blackdread/canon-sdk-java)

# Canon EOS SDK (EDSDK)
Canon EOS SDK in java (EDSDK).

Latest headers used to create bindings are with EDSDK 3.9.0 (version released on October 1st 2018)

See [Bindings README](https://github.com/Blackdread/canon-sdk-java/tree/master/camera-binding) for more detailed information of that module

See [Framework README](https://github.com/Blackdread/canon-sdk-java/tree/master/camera-framework) for more detailed information of that module, important design is explained about Threads and events.

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

# Get EDSDK library from Canon

- [Canon Europe](https://www.didp.canon-europa.com/)
- [Canon Asia](https://asia.canon/en/consumer/web/developerresource-digital-imaging)
- [Canon USA](https://www.developersupport.canon.com/)

# Analysis with sonar
[![sonar-quality-gate][sonar-quality-gate]][sonar-url] [![sonar-coverage][sonar-coverage]][sonar-url] [![sonar-bugs][sonar-bugs]][sonar-url] [![sonar-vulnerabilities][sonar-vulnerabilities]][sonar-url] [![sonar-code-lines][sonar-code-lines]][sonar-url]


# Previous project
It starts from scratch due to [older project](https://github.com/kritzikratzi/edsdk4j) that is not maintained (and some design could be changed).

# Links

[Previous project that allowed to communicate with Canon EDSDK with java](https://github.com/kritzikratzi/edsdk4j)


[sonar-url]: https://sonarcloud.io/dashboard?id=org.blackdread%3Acanon-sdk-java
[sonar-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=alert_status
[sonar-coverage]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=coverage
[sonar-bugs]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=bugs
[sonar-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=vulnerabilities
[sonar-code-lines]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=ncloc
[sonar-reliability-rating]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=reliability_rating
[sonar-code-smalls]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=code_smells
[sonar-maintainability-rating]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=sqale_rating
