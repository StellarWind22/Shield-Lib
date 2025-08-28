# Shield Lib
Library that makes it easy to add shields with banner support, enchantment support, & custom shapes without conflictions!

## Importing

#### put this in gradle.properties
```properties
shieldlib_version=2.0.0-beta.6-1.21.8
```

#### build.gradle in repositories just above dependencies
```gradle
maven {url = "https://api.modrinth.com/maven"}
```

#### build.gradle in dependencies
```gradle
dependencies {
	//Platform specific stuff here
        
    //Shield Lib(fabric OR neoforge replaces [LOADER])
	modImplementation "maven.modrinth:shieldlib:${project.shieldlib_version}-[LOADER]"
}
```

- - - -

## Documentation?
#### Fabric: [Fabric Wiki(Out of date)](https://fabricmc.net/wiki/tutorial:shield).

The [example mod repo](https://github.com/CrimsonDawn45/Fabric-Shield-Lib-Example-Mod) is a template repo you can use to quickly get started if your making a new mod. Although it isn't updated as frequently.
