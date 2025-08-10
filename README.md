[![](https://jitpack.io/v/CrimsonDawn45/Fabric-Shield-Lib.svg)](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

# Shield Lib
Library that makes it easy to add shields with banner support, enchantment support, & custom shapes without conflictions!

## Importing
library is distributed via [jitpack.io](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

#### put this in gradle.properties
```properties
shieldlib_version=2.0-1.21.8
```

#### build.gradle under repositories just above dependencies
```gradle
maven { url = 'https://jitpack.io' }
```

if this mod is your only dependency it should look something like this.
```gradle
dependencies {
	minecraft "com.mojang:minecraft:${project.minecraft_version}"
	mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2"
	modImplementation "net.fabricmc:fabric-loader:${project.loader_version}"
	modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
        
        //Shield Lib
	modImplementation "com.github.StellarWind22:ShieldLib:v${project.shieldlib_version}"
}
```

#### **build.gradle** under dependencies
```gradle
modImplementation "com.github.StellarWind22:ShieldLib:v${project.shieldlib_version}"
```

- - - -

## Documentation?
Tutorial available on [Fabric Wiki](https://fabricmc.net/wiki/tutorial:shield).

The [example mod repo](https://github.com/CrimsonDawn45/Fabric-Shield-Lib-Example-Mod) is a template repo you can use to quickly get started if your making a new mod. Although it isn't updated as frequently.
