@echo off
echo Start Bakup...
mysqldump -h 192.168.1.167 -uroot -proot dbparking > "C:\sql_backup\dbparking_backup.%date%.sql" 
echo End Bakup...