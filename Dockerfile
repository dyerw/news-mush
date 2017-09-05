FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/the-lantern.jar /the-lantern/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/the-lantern/app.jar"]
