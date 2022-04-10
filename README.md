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
- tweak Database Parent Structure

# Java Version

````bash
admin@MyLinuxPC:~/Java$ java -version
openjdk version "17.0.2" 2022-01-18
OpenJDK Runtime Environment (build 17.0.2+8-Debian-1deb11u1)
OpenJDK 64-Bit Server VM (build 17.0.2+8-Debian-1deb11u1, mixed mode, sharing)
````

# How to Build

````bash
admin@MyLinuxPC:~/Java$ sudo git clone https://github.com/WalkingLibrary/Java-Dev-Lib
Cloning into 'Java-Dev-Lib'...
remote: Enumerating objects: 1686, done.
remote: Counting objects: 100% (1073/1073), done.
remote: Compressing objects: 100% (547/547), done.
remote: Total 1686 (delta 401), reused 902 (delta 246), pack-reused 613
Receiving objects: 100% (1686/1686), 246.29 KiB | 1.09 MiB/s, done.
Resolving deltas: 100% (605/605), done.
admin@MyLinuxPC:~/Java$ cd Java-Dev-Lib/
admin@MyLinuxPC:~/Java/Java-Dev-Lib$ sudo chmod 700 ./gradlew
admin@MyLinuxPC:~/Java/Java-Dev-Lib$ sudo ./gradlew jar

> Task :compileJava
Note: Some input files use or override a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
Note: Some input files use unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.

BUILD SUCCESSFUL in 1s
3 actionable tasks: 3 executed
admin@MyLinuxPC:~/Java/Java-Dev-Lib$ cd ./build/libs/
admin@MyLinuxPC:~/Java/Java-Dev-Lib/build/libs$ ls
jumsdevlib-1.2.74.jar
admin@MyLinuxPC:~/Java/Java-Dev-Lib/build/libs$
````

### Gradle Build

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

dependencies {
    compile 'com.github.WalkingLibrary:Dev-Lib:$version'

}

```

# TODOS

-

License
----
![AUR license](https://img.shields.io/badge/License-MIT-blue)


