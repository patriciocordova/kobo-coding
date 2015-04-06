##Problem 1

The assignment is to code an application that can convert a product ID number to a standard ISBN­10 number.

If we take the Da Vinci Code as an example, the product ID is 978140007917 and the ISBN is 1400079179. The first 3 digits of the product ID (978) are a prefix that can be removed. The remaining digits of the product ID (140007917) are the digits of the ISBN excluding the error control digit. Refer to http://www.ee.unb.ca/tervo/ee4243/isbn4243.htm for a description of how an ISBN number is constructed.

Your task is to develop an application that can accept a product ID number and generate the ISBN­10 number. You may use Python, C, C++, C#, or Java. You may not use any non­standard libraries (ex. core python libraries are fine, but PyPi packages are not; STL is fine, but boost is not.) 

Please put your code in a single file for convenience and provide the command to compile it, or the interpreter version used in a comment. 

The code will be reviewed for algorithm design and coding standards.

####Sample test cases:
Product ID   | ISBN
------------ | ----------
978155192370 | 155192370x
978140007917 | 1400079179
978037541457 | 0375414576
978037428158 | 0374281580

####Input/output format:
Your program will read product IDs from standard input and write the corresponding ISBNs to standard output. Each input product ID or output ISBN will be on its own line. No additional output on stdout is expected; if you wish to write any other messages, please use stderr instead. Your program should ignore empty lines, and terminate when it reads the end­of­file (^d) character.
