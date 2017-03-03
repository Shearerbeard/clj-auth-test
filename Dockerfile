FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/auth-test.jar /auth-test/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/auth-test/app.jar"]
