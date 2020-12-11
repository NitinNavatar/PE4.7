WinWait("Sikuli&AutoITScript","",10)
If WinExists("Sikuli&AutoITScript") Then
   WinActivate("Sikuli&AutoITScript")
   Send("^a")
EndIf

