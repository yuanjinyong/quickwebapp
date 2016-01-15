Set shell = Wscript.createobject("wscript.shell")

projectDir = Wscript.createobject("Scripting.FileSystemObject").GetFile(Wscript.ScriptFullName).ParentFolder.ParentFolder.Path
cmd = "%comspec% /c cd /d " & projectDir & " && " & projectDir & "\gradlew.bat --info -s --gui>" & Wscript.ScriptFullName & ".log 2>&1"
'MsgBox cmd

shell.run cmd, 0
