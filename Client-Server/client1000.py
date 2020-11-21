import os
#print(os.system("cd"))
os.chdir('C:\\Users\\glebu\\OneDrive\\Документы\\3 курс\\Разработка клиент-серверных приложений\\Client-Server\\src')
for i in range(1, 1000):
  print(i)
  print(os.popen("javaw rtu.mirea.echo.CalcClient localhost 8080").read())
#cd "C:\Users\glebu\OneDrive\Документы\3 курс\Разработка клиент-серверных приложений\Client-Server\src"
#java rtu.mirea.echo.Main 8080
#C:\Users\glebu\.jdks\openjdk-15\bin
#C:\Program Files (x86)\Common Files\Oracle\Java\javapath
#
