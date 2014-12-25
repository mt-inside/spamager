# EXPOSE just opens the container's firewall, port must still be mapped by the operator
# run as "docker run -d -p $port:80 mtinside/lanager

MAINTAINER Matt Turner <matturner@gmail.com>

# Build instructions
FROM ubuntu:14.04
# Uh, this is shit. Use puppet yeah.
# apt-get install lamp-server?
RUN apt-get update && apt-get install -y git apache2 php5 php5-curl php5-mcrypt php5-mysql mysql-server curl
RUN apt-get install -y vim wget
RUN a2enmod rewrite
RUN php5enmod curl
RUN php5enmod mcrypt
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin
RUN git clone https://github.com/mt-inside/lanager.git
RUN cd /lanager && composer.phar update && cd /
RUN sed "s@'apikey' => '',@'apikey' => 'E7354D0E022067DDE3EEA8038A288697',@" -i /lanager/app/config/lanager/steam.php
RUN echo "<Directory /lanager/public>\nRequire all granted\n</Directory>" >> /etc/apache2/apache2.conf
RUN rm /etc/apache2/sites-enabled/000-default.conf
RUN echo "<VirtualHost *:80>\nServerName empty.org.uk:12345\nDocumentRoot /lanager/public\n<Directory /lanager/public>\nOptions Indexes FollowSymLinks MultiViews\nAllowOverride All\nOrder allow,deny\nAllow From All\n</Directory>\n</VirtualHost>" > /etc/apache2/sites-available/lanager.conf
RUN a2ensite lanager.conf
RUN echo "CREATE DATABASE lanager;\nCREATE USER 'lanager'@'localhost' IDENTIFIED BY 'lanager';\nGRANT ALL PRIVILEGES ON lanager.* TO 'lanager'@'localhost';\nFLUSH PRIVILEGES;" > /lanager.sql
RUN service mysql start && mysql -u root < /lanager.sql
RUN service mysql start && cd /lanager && php artisan lanager:install && cd /
RUN chmod -R 0777 /lanager/app/storage
RUN chmod -R 0777 /lanager/vendor/ezyang/htmlpurifier/library/HTMLPurifier/DefinitionCache/Serializer
RUN chmod 0755 /lanager/SteamImportUserStates.sh
RUN echo '*/1 * * * * /lanager/SteamImportUserStates.sh >> /dev/null 2>&1' | crontab -
RUN echo "<?php\nphpinfo();\n?>" > /lanager/public/info.php

# Runtime hints
EXPOSE 80
CMD /usr/sbin/service mysql start && /usr/sbin/apache2ctl -D FOREGROUND -k start
