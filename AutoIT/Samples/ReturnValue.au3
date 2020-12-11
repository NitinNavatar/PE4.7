#cs ----------------------------------------------------------------------------

 AutoIt Version: 3.3.14.2
 Author:         Ankur Rana

 Script Function:
	Return value

#ce ----------------------------------------------------------------------------

$var = "abc"
WinActivate("Untitled - Message (HTML) ")
Send("^A")
$vars=ControlGetText("Untitled - Message (HTML) ","","4099")
MsgBox(4099,"Message", $vars)
ControlClick("Untitled - Message (HTML) ","","4099")
If $var = $vars Then
    Exit(1)
Else
    Exit(0)
 EndIf

