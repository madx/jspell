JC = javac
JFLAGS = -Xlint:unchecked

SRC = src/*.java

.PHONY: all clean

all:
	$(JC) $(JFLAGS) -d src/ ${SRC}
	@cd src && jar cfe jSpell.jar Main *.class
	@mv src/jSpell.jar .

clean:
	rm -rf src/*.class
	rm -f jSpell.jar

