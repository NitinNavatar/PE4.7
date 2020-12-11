#cs ----------------------------------------------------------------------------

 Author:         Ankur Rana

 Script Function:
	Close Mail Window

#ce ----------------------------------------------------------------------------
Sleep(3000)

If WinExists("Untitled - Message (HTML) ") Then
   WinClose("Untitled - Message (HTML) ")
   Send("{TAB}")
   Send("{ENTER}")
   Exit(1);
Else
   Exit(0);
EndIf
