# tosptube

TospTube video is just video search/upload start up experemental project under development stage.

It is strange, but it is already working.:) Any Java Developer can setup it in his localhost.

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

What we have at the moment?

1. Search video from youtube.
2. Advanced Search from own database
3. User registration/login/logout
4. Random videos :)
5. Last viewed videos
6. Top Videos
7. Top Searches
8. Video(50 mb) upload for logged user
9. Uploaded video list
10. Video view.

![alt text](https://github.com/armdev/tosptube/blob/master/screens/08.JPG)

![alt text](https://github.com/armdev/tosptube/blob/master/screens/01.JPG)

