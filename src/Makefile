JFLAGS = -g
JC = javac
SRC = ./src/

.SUFFIXES: .java .class

.java.class:
	$(SRC) $(JC) $(JFLAGS) $*.java

CLASSES = \
		  Asteroid.java \
		  GameWindow.java \
		  Projectile.java \
		  Ship.java \
		  Sprite.java \
		  StartGame.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	
run:
	javac *.java
	java StartGame