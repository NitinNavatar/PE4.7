WinWait($CMDLINE[1],"","10")
If WinExists($CMDLINE[1]) Then
   WinActivate($CMDLINE[1])
   WinSetState($CMDLINE[1],"",@SW_MAXIMIZE)
EndIf
