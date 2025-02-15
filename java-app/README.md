## Native Image
To create the executable, run the following goal:

```
./gradlew nativeCompile
```

Then, you can run the app as follows:
```
build/native/nativeCompile/graalvm-cds
```

To create a docker image:
```
docker build -t my-app .
```

Image size: 1.04 GB

## Buildpacks native image
In order to create native images it's required to have this plugin

```
id("org.graalvm.buildtools.native") version "0.10.4"
```

To create the image, run the following goal:

```
./gradlew bootBuildImage
```

Then, you can run the app like any other container:
```
docker run -e MY_ENV=alow -p 8080:8080 java-app:1.0.0
```

Image size: 351 MB


## Buildpacks without native image
To create the image, run the following goal:

```
./gradlew bootBuildImage
```

Then, you can run the app like any other container:
```
docker run --rm -p 8080:8080 graalvm-cds:0.0.1-SNAPSHOT
```

Image size: 649 MB