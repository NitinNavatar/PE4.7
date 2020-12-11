Sleep(5000)
If WinExists("Upload Document - Mozilla Firefox") Then
   WinWait("Upload Document - Mozilla Firefox","",10)
   WinActivate("Upload Document - Mozilla Firefox")
   WinWait("File Upload","",10)
   ControlSetText("File Upload","","Edit1",@WorkingDir&$CmdLine[1])
   ControlClick("File Upload","","Button1")
EndIf
If WinExists("Upload Document - Google Chrome") Then
   WinWait("Upload Document - Google Chrome","",10)
   WinActivate("Upload Document - Google Chrome")
   WinWait("Open","",10)
   ControlSetText("Open","","Edit1",@ScriptDir & $CmdLine[1])
   ControlClick("Open","","Button1")
EndIf
If WinExists("Upload Document - Microsoft Edge") Then
   WinWait("Upload Document - Microsoft Edge","",10)
   WinActivate("Upload Document - Microsoft Edge")
   WinWait("File Upload","",10)
   ControlSetText("File Upload","","Edit1",@WorkingDir&$CmdLine[1])
   ControlClick("File Upload","","Button1")
EndIf

