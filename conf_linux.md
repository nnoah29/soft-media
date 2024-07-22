    <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <executable>true</executable>
        </configuration>
      </plugin>
    </plugins>
    </build>
  
  
 ### fichier de configuration softmedia.service
     [Unit]
     Description=SOFT MEDIA SERVICE
     After=networking.service
     
     [Service]
     User=webapp
     ExecStart=/home/webapp/WebStarter/target/starter-0.0.1-SNAPSHOT.jar
     SuccessExitStatus=143
     WorkingDirectory=/home/webapp/WebStarter/
     
     [Install]
     WantedBy=multi-user.target
 
 sudo systemctl daemon-reload
 ## Start the service
 After we have created the service file, we can start the service.
 
 [user@centos]$ sudo systemctl start web-application
 
 ##Stop the service
 We can stop the service using sudo systemctl stop web-application.
 
 [user@centos]$ sudo systemctl stop web-application
 @enable_s`udo systemctl enable web-application`s
 
 #Active logs
 accoder permission utilisateurs
 
 donner droit au ficher de log `sudo chmod -R 0777  /var/soft_media/logs/`