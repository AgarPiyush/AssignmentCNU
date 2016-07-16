import csv
import random
from random import randint
dateset = set()
db = open('neworder.csv','w')
db2 = open('neworderline.csv','w')
with open('order.csv', 'rb') as csvfile:
	spamreader = csv.reader(csvfile)
	reader = csv.DictReader(csvfile)
	for each in reader:
		if ';orderDate;' in each and each[';orderDate;'] != ';NULL;':
			print 
			dateset.add(each[';orderDate;'])

randomStatus = []
randomStatus.append("CREATE")
randomStatus.append("IN PROGRESS")
randomStatus.append("SHIPPED")
randomStatus.append("DELIVERED")
randomStatus.append("Check out")
lists = []
for each in dateset:
	lists.append(each)
l = len(dateset)
print l
for i in xrange(l):
 	print lists[i]


for i in xrange(1000000):
	k1 = ";"+str(1000+i)+";,"+lists[randint(0,l-2)]+",;"+str(randint(1,100))+";,;"+randomStatus[randint(0,3)]+";,;0;\n"
	k2 = ";"+str(1000+i)+";,;"+str(randint(0,100))+";,;"+str(random.uniform(10,200))+";,;"+str(randint(1,100))+";,;"+str(i+300)+";\n"
	db.write(k1)
	db2.write(k2)
	 	
 # 	k1 = ";"+str(i+1)+";,;"+lists(randint(0,l))+";,;"+str(randint((1,100)))+";,;"+randomStatus(randint(0,3))+";,;0;"
	#k2 = ";"+str(i+1)+";,;"+str(randint(0,100))+";,;"+str(random.uniform(10,200))+";,;"+str(randint(0,100))+";,;"+str(i+187)