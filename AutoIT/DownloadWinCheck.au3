WinWait("Save As","",10)
If WinExists("Save As") Then
   Exit(1)
Else
   Exit(0)
EndIf
