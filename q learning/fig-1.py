
import matplotlib.pyplot as plt
import numpy as np



xpt = []
ypt = []

con = 0




f = open("response_time.txt","r")
for i in f.readlines():
    ypt.append(float(i.strip("\n")))
f.close()




print(len(ypt))
for i in range(len(ypt)):
    xpt.append(con)
    con = con + 1
print(len(xpt))




x = np.array(xpt)
y = np.array(ypt)

plt.plot(y) 
plt.xticks([0,25,50,75,100,125,150,175]) # x axis
plt.yticks([0,1,2,3,4,5,6,7,8,9,10,20,30]) #y axis
plt.savefig("response_time.png")
plt.show() 







