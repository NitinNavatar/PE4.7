
sleep(2000)
if WinExists($CMDLINE[1]) Then
   WinActivate($CMDLINE[1])
   Sleep(2000)
   WinClose("[TITLE:"&$CMDLINE[1]&"]");Closing opened file
   Sleep(2000)
EndIf

