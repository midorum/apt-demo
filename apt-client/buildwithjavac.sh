${HOME}/.jdks/temurin-17.0.6/bin/javac \
-J--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED\
 -cp  ../apt-annotations/target/apt-annotations-1.0-SNAPSHOT.jar\
:${HOME}/.m2/repository/org/apache/velocity/velocity/1.6.2/velocity-1.6.2.jar\
:${HOME}/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar\
:${HOME}/.m2/repository/commons-lang/commons-lang/2.4/commons-lang-2.4.jar\
 src/main/java/org/example/exceptions/DuplicateBeanException.java \
 src/main/java/org/example/exceptions/InvalidBeanException.java \
 src/main/java/org/example/domain/Bean.java \
 src/main/java/org/example/persistence/DemoPersistence.java \
 src/main/java/org/example/service/DemoService.java

