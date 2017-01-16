adb push mccp2-gadap.db /sdcard/mccp2-gadap.db
adb shell "run-as com.example.hassannaqvi.mccp2 chmod 666 /data/data/com.example.hassannaqvi.mccp2/databases/mccp2-gadap
adb shell rm /data/data/com.example.hassannaqvi.mccp2/databases/mccp2-gadap
adb -d shell "run-as com.example.hassannaqvi.mccp2 cat /sdcard/mccp2-gadap.db > /data/data/com.example.hassannaqvi.mccp2/databases/mccp2-gadap
adb shell "run-as com.example.hassannaqvi.mccp2 chmod 600 /data/data/com.example.hassannaqvi.mccp2/databases/mccp2-gadap
@echo Databased Pulled!