[![Build Status](https://travis-ci.org/Blackdread/canon-sdk-java.svg?branch=master)](https://travis-ci.org/Blackdread/canon-sdk-java)
[![Coverage Status](https://coveralls.io/repos/github/Blackdread/canon-sdk-java/badge.svg?branch=master)](https://coveralls.io/github/Blackdread/canon-sdk-java?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.blackdread/canon-sdk-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.blackdread/canon-sdk-java)
[![Github Workflow](https://github.com/Blackdread/canon-sdk-java/workflows/Java%20CI/badge.svg)](https://github.com/Blackdread/canon-sdk-java/actions?workflow=Java+CI)
[![StackShare](https://img.shields.io/badge/tech-stack-0690fa.svg?style=flat)](https://stackshare.io/Blackdread/canon-sdk-java)
[![](https://img.shields.io/gitter/room/canon-sdk-java/Framework.svg)](https://gitter.im/canon-sdk-java/Framework)

# Canon EOS SDK (EDSDK)
Canon EOS SDK in java (EDSDK).

Latest headers used to create bindings are with EDSDK 13.10.0 (version released on February 23rd 2019)

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

Support for MAC OSX is not intended for now, but only few changes in implementation should be required to make it work. Only changes in library loader class should be needed.

Both 64 bit and 32 bit versions are supported.

# Java versions
Minimum Java 8 is required.
Works with JDK 8/11/14+

# Maven dependency
To control camera, use the framework:

    <dependency>
        <groupId>org.blackdread</groupId>
        <artifactId>camera-framework</artifactId>
        <version>put version here</version>
    </dependency>
    
If you only want bindings then just change artifact to "camera-binding" but framework should be what you should use as it gives commands, etc.
    
# Usage
See package "org.blackdread.cameraframework.demo" in test folder ([here](https://github.com/Blackdread/canon-sdk-java/tree/master/camera-framework/src/test/java/org/blackdread/cameraframework/demo)).

# Support project
To help me to maintain and add more features, you can be a sponsor.

# Company support and sponsor

For companies that desire support, help, or even sponsor, etc feel to contact me ([Follow this link](https://www.emailmeform.com/builder/form/46HAMcc300chL56f4a6uJo8e)). 

Commercial technical support and services are possible.

# Design note
Any collection/map return type should be considered as immutable, even if implementation does not use ImmutableList/Set/etc. If collection/map is mutable, it will be clearly specified in javadoc, otherwise implementation is free to change to immutable collections anytime. 

# How to contribute pull requests
Do a pull request. Explain your changes, reasons, write tests.

# Get EDSDK library from Canon

- [Canon Europe](https://developers.canon-europe.com/developers/s/login/SelfRegister) ([old](https://www.didp.canon-europa.com/))
- [Canon Asia](https://asia.canon/en/consumer/web/developerresource-digital-imaging)
- [Canon USA](https://www.developersupport.canon.com/)

# Analysis with sonar
[![sonar-quality-gate][sonar-quality-gate]][sonar-url] [![sonar-coverage][sonar-coverage]][sonar-url] [![sonar-bugs][sonar-bugs]][sonar-url] [![sonar-vulnerabilities][sonar-vulnerabilities]][sonar-url] [![sonar-code-lines][sonar-code-lines]][sonar-url]


# Previous project
It starts from scratch due to [older project](https://github.com/kritzikratzi/edsdk4j) that is not maintained (and some design could be changed).

# Links

[Previous project that allowed to communicate with Canon EDSDK with java](https://github.com/kritzikratzi/edsdk4j)

# Software example using this Framework/SDK
[![Example 1](https://i.imgur.com/UBHExoH.png?1)](https://i.imgur.com/UBHExoH.png?1)

[sonar-url]: https://sonarcloud.io/dashboard?id=org.blackdread%3Acanon-sdk-java
[sonar-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=alert_status
[sonar-coverage]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=coverage
[sonar-bugs]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=bugs
[sonar-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=vulnerabilities
[sonar-code-lines]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=ncloc
[sonar-reliability-rating]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=reliability_rating
[sonar-code-smalls]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=code_smells
[sonar-maintainability-rating]: https://sonarcloud.io/api/project_badges/measure?project=org.blackdread%3Acanon-sdk-java&metric=sqale_rating
