# build_and_run_backend.ps1
Set-Location -Path "frontend"
Get-ChildItem -Path . -Filter "*.class" -Recurse | Remove-Item -Force
javac Main.java -d classes
java -cp classes Main