```yaml
#  __             _     _           
# / _\ __ _ _   _(_)___| |__  _   _
# \ \ / _` | | | | / __| '_ \| | | |
# _\ \ (_| | |_| | \__ \ | | | |_| |
# \__/\__, |\__,_|_|___/_| |_|\__, |
#        |_|                  |___/
#
# Easy to use Configuration Java Library
# By Smudge
```

<div align="center">
    <a href="https://smuddgge.gitbook.io/squishy-configuration/">
        <img src="https://raw.githubusercontent.com/smuddgge/Leaf/main/graphics/wiki.png" width="512">
    </a>
</div>

```java
Configuration configuration = new YamlConfiguration(new File("path.yml"));
configuration.load();
```

# Dependency

Replace `Tag` with this number
> [![](https://jitpack.io/v/smuddgge/SquishyConfiguration.svg)](https://jitpack.io/#smuddgge/SquishyConfiguration)

## Maven dependency
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.Smuddgge</groupId>
    <artifactId>SquishyYaml</artifactId>
    <version>Tag</version>
</dependency>
```

## Gradel dependency
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```gradle
dependencies {
    implementation 'com.github.Smuddgge:SquishyYaml:Tag'
}
```
