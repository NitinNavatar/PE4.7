WinWait("FilesToUpload","","10")
If WinExists("FilesToUpload") Then
   WinActivate("FilesToUpload")
ElseIf WinExists("watermarkingFolder") Then
   WinActivate("watermarkingFolder")
EndIf

