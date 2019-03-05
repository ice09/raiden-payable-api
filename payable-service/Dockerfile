FROM gradle:jdk8 as builder
COPY --chown=gradle:gradle . /home/gradle/payable-service
WORKDIR /home/gradle/payable-service
RUN ./gradlew build

FROM oracle/graalvm-ce:1.0.0-rc11 as graalvm
COPY --from=builder /home/gradle/payable-service/ /home/gradle/payable-service/
WORKDIR /home/gradle/payable-service
RUN java -cp build/libs/*-all.jar \
            io.micronaut.graal.reflect.GraalClassLoadingAnalyzer \
            reflect.json
RUN native-image --no-server \
                 --class-path /home/gradle/payable-service/build/libs/*-all.jar \
                 -H:ReflectionConfigurationFiles=/home/gradle/payable-service/reflect.json \
                 -H:EnableURLProtocols=http \
                 -H:IncludeResources='logback.xml|application.yml|META-INF/services/*.*' \
                 -H:+ReportUnsupportedElementsAtRuntime \
                 -H:+AllowVMInspection \
                 --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder',javax.net.ssl.SSLContext \
                 --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder,io.netty.handler.ssl.util.ThreadLocalInsecureRandom \
                 -H:-UseServiceLoaderFeature \
                 --allow-incomplete-classpath \
                 -H:Name=payable-service \
                 -H:Class=payable.service.Application


FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/gradle/payable-service/payable-service .
ENTRYPOINT ["./payable-service"]
