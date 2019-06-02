FROM mongo:4
MAINTAINER armen

# Add mongo user
RUN groupadd -r mongo && \
    useradd -r -g mongo mongo

#Install cron
RUN apt update && apt install -y cron

#Create crontab job
#RUN sudo mkdir /var/backups/mongobackups
#COPY crontab.sh /etc/cron.d/mongobackup
#RUN chmod 0644 /etc/cron.d/mongobackup
#RUN service cron start 
    
# USER mongo
# CMD mongod --dbpath=/var/lib/mongodb

