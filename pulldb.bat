del mccp2-gadap.db
adb -d shell "run-as edu.aku.hassannaqvi.mccp_linelisting cat /data/data/edu.aku.hassannaqvi.mccp_linelisting/databases/pssp-hhl.db > /sdcard/pssp-hhl.db"
adb pull /sdcard/pssp-hhl.db
"C:\Program Files\Google\Chrome\Application\chrome.exe"  --profile-directory=Default --app-id=bponbdjkefbmgkfiiphhabghkkfocook
@echo Databased Pulled!
