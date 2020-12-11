Sleep(5000)
If WinExists("Navatar Investor Manager - Mozilla Firefox") Then
   WinWait("Navatar Investor Manager - Mozilla Firefox","","10")
   WinActivate("Navatar Investor Manager - Mozilla Firefox")
EndIf
If WinExists("Navatar Investor Manager - Google Chrome") Then
   WinWait("Navatar Investor Manager - Google Chrome","","10")
   WinActivate("Navatar Investor Manager - Google Chrome")
EndIf
If WinExists("Navatar Investor Manager - Microsoft Edge") Then
   WinWait("Navatar Investor Manager - Microsoft Edge","","10")
   WinActivate("Navatar Investor Manager - Microsoft Edge")
EndIf