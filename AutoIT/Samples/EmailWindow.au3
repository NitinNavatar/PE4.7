#cs ----------------------------------------------------------------------------

 AutoIt Version: 3.3.14.2
 Author:         Ankur Rana

 Script Function:
	compare

#ce ----------------------------------------------------------------------------

$var="bcd"
$varc="bcd"
If $var=$varc Then
   MsgBox(4096, "matched", "both are same")
Else
   MsgBox(4096,"matched", "No")
EndIf
