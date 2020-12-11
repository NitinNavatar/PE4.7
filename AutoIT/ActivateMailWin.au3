#cs ----------------------------------------------------------------------------

 Author:         Ankur Rana

 Script Function:
	Activate Window

#ce ----------------------------------------------------------------------------

WinWait("Untitled - Message (HTML) ","",20)

If WinExists("Untitled - Message (HTML) ") Then
   WinActivate("Untitled - Message (HTML) ")
   Exit(1);
Else
   Exit(0);
EndIf

