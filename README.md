# Development Library

![Rating](https://img.shields.io/badge/Rating-1%2F5-Red)
![Sauce](https://img.shields.io/badge/100%25-Spaghetti%20Code-orange)
![Build Status](https://img.shields.io/badge/Build-Passing-green)


Have you Ever Written Code So Good You use it again? Same, but if I did I'd put it here.

  - jumbodinosaurs.com Support
  - java reflection help
  - command system
  - email manager
  - options manager
  - GSON/JSON tools
  - web tools

#TODOS
 - add Version System
 - add Task System
 - add Shell Script Execution System

### Gradle

```
buildscript {
    repositories {
        jcenter()
    }


}


allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
...

dependencies {
    compile 'com.github.WalkingLibrary:Dev-Lib:$version'

}


```
License
----
![AUR license](https://img.shields.io/badge/License-MIT-blue)


