# credit-suisse-monthly-country-economic-indicators-extractor

A friend at [Argo Capital Management](http://www.argocm.com/about-argo), asked me to extract the data from the country economic indicators tables found in his Credit Suisse pdf document (CS2016.pdf). 

Requirements
------------
Java v7 or higher, No Java required on Windows run the executable.

Shell/git skills to run this app

Credit Suisse pdf document with key tables

Usage 
------------
On Linux/Mac: Open a Terminal and run: java -jar [ ] -pdf_file CS2016.pdf -page 9

On Windows: Open a cmd window and run: cs-markets-cei-extractor.exe -pdf_file CS2016.pdf

-pdf_file: (mandatory) pdf file

-page: (optional) page number to extract


Features 
------------
- Pdf extraction with iText
- Regex
- Data sent to Excel using Poi
- Exe package using JSmooth (to be used in Windows)
- User can extract data until he presses 'Q' to quit

Screenshots
------------
<img width="200" alt="1st page" src="images/cs2016-page1.png">
<img width="400" alt="Table data to extract" src="images/cs2016-page21.png">
<img width="400" alt="Loop execution" src="images/screenshot-execution.png">

TODO List
------------
- [ ] Finish readme
- [ ] Add Screenshots
- [ ] Add jsmooth, exe package
- [ ] Add Usage
- [ ] More testing... file exist, page in range of doc, etc

Changelog
------------
