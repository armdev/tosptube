FROM fabric8/java-alpine-openjdk8-jdk:1.6.2
COPY target/search.jar /opt/search.jar
ENTRYPOINT ["java",  "-Xms1024m", "-Xmx2048m", "-XX:MetaspaceSize=128m", \
            "-XX:+UseG1GC", "-XX:ParallelGCThreads=20", "-XX:ConcGCThreads=5", \
            "-XX:InitiatingHeapOccupancyPercent=70", "-XX:MaxGCPauseMillis=20", \
            "-XX:InitiatingHeapOccupancyPercent=35", "-XX:G1HeapRegionSize=16M", \
            "-XX:MinMetaspaceFreeRatio=50", "-XX:MaxMetaspaceFreeRatio=80",  \
            "-XX:+UnlockExperimentalVMOptions","-jar","/opt/search.jar"]
EXPOSE 4005
