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
<br /><br /><br /><br />
##Problem 2

####Definitions

* **tf*idf** – term frequency times inverse document frequency – read more here: http://en.wikipedia.org/wiki/Tf%E2%80%93idf
* **query** – a search query which can have multiple words / terms
* **term** – a single word in a query

####Requirements
Given a set of Kobo’s Author and Title data, implement a tf*idf index in memory which does the following:
* Build an index from the given data file
* For a given query, output the top 10 results ranked by their tf*idf scores
* Case (capitalization) should be ignored
* You should take the union of the title and author fields when indexing the book records
* If the same term appears multiple times in a query or a book record, only count it once
* If multiple books have the same score, return sort them by id (lowest id first)

####Data
**title_author.tab.txt.gz** – This file contains most of the titles and authors of Kobo’s free catalog. You can decompress it using gunzip (available on mac/linux) or 7zip (windows.)

####Data Example
This is a line taken from the tab­delimited title_author.tab.txt.gz:
```
800 The plays Oscar Wilde
The columns are: id, title, author, so in this case:
id ­> 800
title ­> The plays
author ­> Oscar Wilde
```

####Code
You may use Python, C, C++, C#, or Java. You may not use any non­standard libraries (ex. core python libraries are fine, but PyPi packages are not; STL is fine, but boost is not.) Please put your code in a single file for convenience and provide the command to compile it, or the interpreter version used in a comment.

####Input/Output format

Your program will take a single argument: the name of the index file. We will invoke your script as follows:

```sh
$./your_solution_executable tile_author.tab.txt
```

Your program will accept queries on standard input and print the results to standard output. Any diagnostic output should be printed to standard error. Empty queries should be ignored. The end­of­file character (^d) should terminate the program.
Only print up to 10 results for each query. If there are no results, you may print a message to stderr, but don’t print anything to stdout.

Each result must be formatted as follows, where the upper case tokens correspond to the data in the book record:

**PRODUCTID SCORE title: TITLE author: AUTHOR**

####Sample Output
```
$pythonsolution.pytitle_author.tab.txt
AlysEyreMacklinGreuze #this is the input query
3932532.6460707315title:Greuzeauthor:AlysEyreMacklin
57069832.6460707315title:Greuzeauthor:AlysEyreMacklin
127769532.6460707315title:Greuzeauthor:AlysEyreMacklin
64262819.9661713056title:Twenty-ninetalesfromtheFrenchauthor:AlysEyreMacklin
35071917.9820399437title:APlainStatementofFacts,RelativetoSirEyreCoote:Containingthe...author:WilliamBagwell,SirEyreCoote
41768112.679899426title:Greuzeandhismodelsauthor:JohnRivers
72344212.2744343179title:CaptainMacklin:hismemoirsauthor:RichardHardingDavis,WalterAppletonClark
100630312.2744343179title:CharlesMacklinauthor:Parry,EdwardAbbott,Sir
122936512.2744343179title:CharelesMacklinauthor:EdwardAbbottParry
53957212.2744343179title:CharlesMacklinauthor:Parry,EdwardAbbott,Sir
sherlock      #thisisasecondquery
              #10results...
```

