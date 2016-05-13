Option Explicit
Dim WshShell
Set WshShell = WScript.CreateObject("WScript.Shell")
WScript.Sleep 100
WshShell.AppActivate("Shuffle Move")
' WshShell.SendKeys "% r"