

all:
	javac Codeverse.java
	jar -cfe Codeverse.jar Codeverse *class lib/
