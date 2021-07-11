FROM adoptopenjdk:11-jre-openj9
RUN mkdir /opt/app
COPY target/jvds-sudoku-model*.jar /opt/app/jvds-sudoku-model.jar
CMD ["java", "-jar", "/opt/app/jvds-sudoku-model.jar", "408007000000001700607002590020004009300090007700100060083400902004200000000500108"]