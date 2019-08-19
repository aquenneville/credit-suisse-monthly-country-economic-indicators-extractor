# credit-suisse-monthly-country-economic-indicators-extractor

<img src="https://travis-ci.org/aquenneville/credit-suisse-monthly-country-economic-indicators-extractor.svg?branch=master"/>

A friend at [Argo Capital Management](http://www.argocm.com/about-argo), asked me to extract the data from the country economic indicators tables found in his Credit Suisse pdf document (CS2016.pdf - example page 21). I created a JSmooth wrapper standalone application and later a Spring boot application hosted on Heroku. Both of the projects can be built from the parent pom in the main folder. <a href="https://creditsuisse-mcei-extractor.herokuapp.com/">Try it here!</a>

Requirements
------------
Java v8 or higher, No Java required on Windows run the executable.

Shell/git skills to run this app

Credit Suisse pdf document with key tables

The Excel file is generated in the project folder. In Spring boot the Excel file can be downloaded. 

Features 
------------
- Pdf extraction with iText
- Regex
- Data sent to Excel using Poi
- Exe package using JSmooth (to be used in Windows)
- User can extract data until he presses 'Q' to quit
- Spring boot application version (I tweaked the code for the upload from spring.io: https://spring.io/guides/gs/uploading-files/)

Spring boot first App Run  
-------------------------
mvn clean install 

mvn --projects spring-boot-credit-suisse-monthly-country-economic-indicators-extractor spring-boot:run

Then open your browser at: http://localhost:8080

Running the Standalone app
--------------------------
On Linux/Mac: Open a Terminal and run: java -jar [ ] -pdf_file CS2016.pdf -page 9

On Windows: Open a cmd window and run: cs-markets-cei-extractor.exe -pdf_file CS2016.pdf

-pdf_file: (mandatory) pdf file

-page: (optional) page number to extract


TODO List
------------
- [ ] Finish readme
- [x] Add Screenshots
- [x] Add jsmooth, exe package
- [x] Add Usage
- [x] Add Travis CI build badge
- [ ] Refactor code
- [x] Add field validation
- [ ] Add page or pages feature 
- [ ] Add user instructions on page
- [x] Add Heroku deployment
- [x] Add Bootstrap and Jquery
- [ ] More testing... file exist, page in range of doc, etc


Screenshots
------------
<img width="200" alt="1st page" src="images/cs2016-page1.png">
<img width="400" alt="Table data to extract" src="images/cs2016-page21.png">

Spring boot application
-----------------------
<img width="400" alt="Springboot execution" src="images/screenshot-springboot-execution.png">

Standalone application
----------------------
<img width="400" alt="Loop execution" src="images/screenshot-execution.png">


Changelog
------------
- Bootstrap added to web page. 
