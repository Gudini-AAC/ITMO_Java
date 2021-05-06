javac -d ./protocol/build -sourcepath ./protocol/src ./protocol/src/protocol/*.java

javac -d ./protocol/build -sourcepath ./protocol/src ./protocol/src/structures/*.java

cd ./protocol/build 
jar cvf ../Protocol.jar .
cd ../../

javac -d ./server/build -cp protocol/Protocol.jar -sourcepath ./server/src ./server/src/Main.java

javac -d ./client/build --module-path D:\Programs\Java\javafx-sdk-11.0.2\lib --add-modules javafx.controls -encoding utf8 -cp protocol/Protocol.jar -sourcepath  ./client/src ./client/src/Main.java
