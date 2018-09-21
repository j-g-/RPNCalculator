Reverse Polish Notation (RPN) Calculator
========================================
    - This program contains a command-line utility to operate RPN expressions.
        For example:
            1 1 5 PLUS PLUS =  7
            9 2 7 PLUS TIMES = 81

    - Can parse serveral lines from a file, or use an interactive mode to 
    evaluate a line at the time.

    - Keeps history of calculations and can print history sorted by result, 
    result in reverse and date.

Usage
-----
Execute jar file passing options as arguments or a file name.
Reading a file:
    java -jar RPNCalculator.jar ./operations.rpn

Printing sorted history:
    java -jar RPNCalculator.jar -ho
    java -jar RPNCalculator.jar --history
    java -jar RPNCalculator.jar --history=ordered

Printing reverse sorted history:
    java -jar RPNCalculator.jar -hr
    java -jar RPNCalculator.jar --history=reverse

Printing sorted by date history:
    java -jar RPNCalculator.jar -hd
    java -jar RPNCalculator.jar --history=date


