del mccp2-gadap.db
adb -d shell "run-as naunehal_listing cat /data/data/naunehal_listing/databases/pssp-hhl.db > /sdcard/pssp-hhl.db"
adb pull /sdcard/pssp-hhl.db
"C:\Program Files\Google\Chrome\Application\chrome.exe"  --profile-directory=Default --app-id=bponbdjkefbmgkfiiphhabghkkfocook
@echo Databased Pulled!
