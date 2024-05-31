# build_and_run_backend.ps1
Set-Location -Path "backend"
Get-ChildItem -Path . -Filter "*.class" -Recurse | Remove-Item -Force
java Main.java -cp ../libs/*