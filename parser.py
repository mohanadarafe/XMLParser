from bs4 import BeautifulSoup as bs
import urllib.request

#JACOCO FILE
jacocoSauce = urllib.request.urlopen('file:///Users/mohanadarafe/Desktop/Concordia/SOEN345/A1/commons-io-master-lab/target/jacoco-ut/jacoco.xml').read()
soup = bs(jacocoSauce)

f = open("jacoco.txt", "w")

for line in soup.find_all('line'):
	if line.parent.name == 'sourcefile':
		src = line.parent["name"]
		f.write(src)
		f.write(" ")
		f.write(line["nr"])
		f.write('\n')

#CLOVER FILE
cloverSauce = urllib.request.urlopen('file:///Users/mohanadarafe/Desktop/Concordia/SOEN345/A1/commons-io-master-lab/target/site/clover/clover.xml').read()
soup2 = bs(cloverSauce)

f2 = open("clover.txt", "w")

for line in soup2.find_all('line'):
	if line.parent.name == 'file':
		src = line.parent["name"]
		f2.write(src)
		f2.write(" ")
		f2.write(line["num"])
		f2.write('\n')
