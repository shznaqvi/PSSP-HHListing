@echo LIST OF DATABASE
@echo off
adb -d shell "run-as com.example.hassannaqvi.mccp2 ls /data/data/com.example.hassannaqvi.mccp2/databases/"
@echo End of List
