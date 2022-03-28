FROM openjdk:17
ADD target/dockerCommentService.jar dockerCommentService.jar
EXPOSE 3015
ENTRYPOINT ["java","-jar","dockerCommentService.jar"]