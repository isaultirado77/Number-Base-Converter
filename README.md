# Number Base Converter

## Project Description 
This project allows converting numbers between various bases, including both integer and fractional parts.
It supports bases from 2 to 36, enabling conversion from binary, octal, hexadecimal, and more, to any other supported base. The `NumberBaseConverter` class handles both 
whole numbers and fractional numbers, performing precise rounding for fractional parts up to a defined precision. The code ensures accuracy by converting numbers 
to an intermediate decimal (base 10) representation before converting them to the target base. Additionally, exception handling is built in to handle invalid inputs 
and base limits.

## Getting Started 

### Prerequisites

- Java Development Kit (JDK) installed on your machine. You can download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html).

### Running the Application

1. Clone the repository or download the code files.
2. Navigate to the project directory.
   ```sh
   cd .\Number-Base-Converter-main\
   ```
4. Compile the Java code using the following command:
   ```sh
   javac .\converter\Main.java
   ```
5. Run the application using the command:
   ```sh
   java converter.Main
   ```
6. **Follow the On-Screen Prompts.**

## Example
```
Enter two numbers in format: {source base} {target base} (To quit type /exit) > 10 7
Enter number in base 10 to convert to base 7 (To go back type /back) > 0.234
Conversion result: 0.14315

Enter number in base 10 to convert to base 7 (To go back type /back) > 10.234
Conversion result: 13.14315

Enter number in base 10 to convert to base 7 (To go back type /back) > /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) > 35 17
Enter number in base 35 to convert to base 17 (To go back type /back) > af.xy
Conversion result: 148.g88a8

Enter number in base 35 to convert to base 17 (To go back type /back) > aaaa.0
Conversion result: 54e36.00000

Enter number in base 35 to convert to base 17 (To go back type /back) > /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) > 21 10
Enter number in base 21 to convert to base 10 (To go back type /back) > 4242
Conversion result: 38012

Enter number in base 21 to convert to base 10 (To go back type /back) > 4242.13a
Conversion result: 38012.05550

Enter number in base 21 to convert to base 10 (To go back type /back) > /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) > /exit
```
## Author

- [Isaúl Tirado](https://github.com/isaultirado77)
