# tosptube
video-sharing website

How to start?
1. Install and start mongodb on localhost
2. Copy mongodb/data/db to your mongodb database
3. Download Apache Tomcat, and start it.

Files which you can edit before start services:

a. tosptube\tosp-video\src\main\resources\config.yml

b. tosp-web\src\main\resources\youtube.properties (put your youtube.apikey )

4. cd to tosp-auth, mvn clean package -U , java -jar target/tosp-auth.jar server src/main/resources/config.yml
5. cd to tosp-search, mvn clean package -U , java -jar target/tosp-search.jar server src/main/resources/config.yml
6. cd to tosp-video, mvn clean package -U , java -jar target/tosp-video.jar server src/main/resources/config.yml
7. cd to tosp-web, mvn clean package -U , cd target, copy .war file to apache tomcat/webapps
8. file storage in apache-tomcat\webapps\file-storage
9. Access to front end http://localhost:8585(tomcat port)/tosp-web/index.jsf 
10. Documentation will be updated soon.

