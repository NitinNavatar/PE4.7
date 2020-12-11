WinWait($CMDLINE[1],"","10")
If WinExists($CMDLINE[1]) Then
    WinActivate($CMDLINE[1])
	Send("^f")
	Send("^v")
 EndIf
