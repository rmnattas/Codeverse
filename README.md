# Codeverse

Statically analyzes the Java program and generates a summary of instructive selective recommendations on how you can improve your code.

## Build Command

```
make  // generates Codeverse.jar
```

## Run Command
```
java -jar Codeverse.jar <source code java file> [-s good source code java file]
```

## Testing
Few java source code files included in the `test/` directory, to run the main test use the command:
```
java -jar Codeverse.jar test/bad/Test.java -s test/good/Test.java
```

## Known bugs
- Passing 2+ source files

## Future work
- Option for all non-formatted output
- Include references to standards/possible-bugs documentations
- CheckStyle standard configration
- Analyzing CKJM output without a compare code


## Resources
The following tools used to analyize the code:
- [CKJM](https://www.spinellis.gr/sw/ckjm/doc/indexw.html)
- [CheckStyle](https://checkstyle.org/ )
- [SpotBugs](https://spotbugs.github.io)
