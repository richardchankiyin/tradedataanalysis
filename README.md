# tradedataanalysis
codebase to demonstrate the analysis of trade data set including trade and bid/ask quote like mean trade change time

Requirement
----------
java 1.8
maven 3
build command:
mvn clean compile install assembly:single
run command:
java -jar target/tradedataanalysis-1.1-SNAPSHOT-jar-with-dependencies.jar
build docker image:
./builddockerimage.sh
run from docker:
./run_from_docker.sh

Sample output:
richard@xxxxx:~/tradedataanalysis$ java -jar target/tradedataanalysis-1.0-SNAPSHOT-jar-with-dependencies.jar 
downloading file from: https://s3.eu-west-2.amazonaws.com/itarlepublic/scanditicks/scandi.csv.zip ... 
download complete
id:TEL NO Equity|meantimebtwtrades:24.0|medianbtwtrades:0.0|longesttimebtwtrades:60026.0|meantimebtwtickchanges:2.0|medianbtwtickchanges:0.0|longesttimebtwtickchanges:59226.0|meanbidaskspread:0.4973972273836389|medianbidaskspread:0.09999999999999432|percentzeroaslastdigitattradeprice:11.72503242542153|percentzeroaslastdigitattradevol:26.69260700389105
id:BBHBEAT Index|meantimebtwtrades:NaN|medianbtwtrades:NaN|longesttimebtwtrades:NaN|meantimebtwtickchanges:NaN|medianbtwtickchanges:NaN|longesttimebtwtickchanges:NaN|meanbidaskspread:NaN|medianbidaskspread:NaN|percentzeroaslastdigitattradeprice:NaN|percentzeroaslastdigitattradevol:NaN
id:NOKISEK SS Equity|meantimebtwtrades:36.0|medianbtwtrades:0.0|longesttimebtwtrades:56114.0|meantimebtwtickchanges:0.0|medianbtwtickchanges:0.0|longesttimebtwtickchanges:55348.0|meanbidaskspread:0.09556958573962432|medianbidaskspread:0.09999999999999432|percentzeroaslastdigitattradeprice:8.56636720232823|percentzeroaslastdigitattradevol:46.830317600911044



CSV file format
---------
1 = Bloomberg Code/Stock identifier
3 = Bid Price
4 = Ask Price
5 = Trade Price
6 = Bid Volume
7 = Ask Volume
8 = Trade Volume
9 = Update type => 1=Trade; 2= Change to Bid (Px or Vol); 3=Change to Ask (Px or Vol)
11 = Date
12 = Time in seconds past midnight
15 = Condition codes

file to be downloaded automatically

Remarks
-------
Please also exclude auctions from your analysis. There should be c. 2 auctions a day - morning and afternoon. During this period you will see crossed spreads (i.e. bid price is larger than ask price) along with specific condition codes. Please only include the XT condition code (along with no condition code).
