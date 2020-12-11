$hNPPlus=WinGetHandle("Sikuli&AutoITScript")
$aNPPPos=WinGetPos($hNPPlus)
;~ WinActivate($hNotePad)
$sProgramFiles=@ProgramFilesDir
If WinExists("Sikuli&AutoITScript") Then
   WinActivate("Sikuli&AutoITScript")
   Send("^a")
   _FileDragDrop($hNPPlus,@Scrip&'|'&$sProgramFiles&"Drag.jpg",$aNPPPos[2]/2,$aNPPPos[3]/2)
EndIf


