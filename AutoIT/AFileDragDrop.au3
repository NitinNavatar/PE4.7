#include <Constants.au3>
#include <MsgBoxConstants.au3>

;Reading comments which are passed by java code or terminal
$OTHERARGS=""
for $CPT=2 to $CMDLINE[0]
$OTHERARGS&=$CMDLINE[$CPT]
next

;Open file
Run("explorer.exe " & @ScriptDir & "\" &$CMDLINE[1])
Sleep(2000)


;Setting file at the top and maximizing the file explorer
WinWaitActive($CMDLINE[1])
WinSetOnTop("title",$CMDLINE[1],True)
WinSetState("[ACTIVE]", "[TITLE:"& $CMDLINE[1]& "]", @SW_MAXIMIZE)


;Handeling file explorer
Local $hWnd = WinWait("[TITLE:"& $CMDLINE[1]& "]", "", 10)
Local $aPos = WinGetPos($hWnd)
MouseClick($MOUSE_CLICK_LEFT, $aPos[0]+170, $aPos[1]+20, 1);Clicking Single click on folder
Sleep(1000)

WinMove($hWnd, "", 0, 0, 400, 700);Resizing  window at the left corner
Sleep(1000)
Local $Aoffset = ControlGetPos($hWnd, "", "DirectUIHWND3") ;Getting position of file explorer

;Setting position  for window 7 File explorer
if @OSVersion=="WIN_7" Then
$Aoffset[0]=$Aoffset[0]+50;
$Aoffset[1]=$Aoffset[1]+70;
EndIf

;Setting position for window 10 File explorer
if @OSVersion=="WIN_10" Then
$Aoffset[0]=$Aoffset[0]+50;
$Aoffset[1]=$Aoffset[1]+40;
EndIf



;Handeling chrome Upload Documents window
If WinExists ("[TITLE:Upload Documents - Google Chrome]", "") Then

	;Resize window at right side
	$hWnd1 = WinWait("[TITLE:Upload Documents - Google Chrome]", "", 10)
	Local $aPos = WinGetPos($hWnd1)
	WinMove($hWnd1, "", 1000,100)


	;Draging File from file explorer to Browser Upload Documents window
	Sleep(1000)
	$Boffset = WinGetPos("Upload Documents - Google Chrome")
    MouseClickDrag("left", $Aoffset[0], $Aoffset[1], $Boffset[0] + 250, $Boffset[1]+400)

EndIf


If  WinExists ("[TITLE:Upload Documents - Mozilla Firefox]", "") Then

	;Resize window at right side
	$hWnd1 = WinWait("[TITLE:Upload Documents - Mozilla Firefox]", "", 10)
	Local $aPos = WinGetPos($hWnd1)
	WinMove($hWnd1, "", 1000,100)


	;Draging File from file explorer to Browser Upload Documents window
	Sleep(1000)
	$Boffset = WinGetPos("Upload Documents - Mozilla Firefox")
	MouseClickDrag("left", $Aoffset[0], $Aoffset[1], $Boffset[0] + 250, $Boffset[1]+400)

EndIf

If  WinExists ("[TITLE:Upload Documents - Microsoft Edge]", "") Then

	;Resize window at right side
	$hWnd1 = WinWait("[TITLE:Upload Documents - Microsoft Edge]", "", 10)
	Local $aPos = WinGetPos($hWnd1)
	WinMove($hWnd1, "", 1000,100)

	;Draging File from file explorer to Browser Upload Documents window
	Sleep(1000)
	$Boffset = WinGetPos("Upload Documents - Microsoft Edge")
	MouseClickDrag("left", $Aoffset[0], $Aoffset[1], $Boffset[0] + 250, $Boffset[1]+400)

EndIf

sleep(2000)
WinClose("[TITLE:" & $CMDLINE[1] & "]");Closing opened file
