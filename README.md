# Whisper Standalone Executable (Command Line Interface - CLI)

This is a mono repo style project.

<span style="color:red">**NOTE:** Early development phase</span>. Some breaking/structural changes are expected.

## Projects 

### Whisper CLI ( [Readme](whisper/readme_whisper_exec.md) )
- It is "Modified" OpenAi's whisper python project to create executable files using PyInstaller. 
- "Standalone Executable" should be usable by just copying to the target system; without requiring python environment setup.
- The created executables are platform dependant.

#### Supported Platforms:

- Ubuntu in Docker Container [readme](whisper/docs/ubuntu/readme_whisper_exec_mac_docker_ubuntu.md)
- Ubuntu in Virtual Machine [readme](whisper/docs/ubuntu/readme_whisper_exec_ubuntu.md)
- Mac M2 Chip [readme](whisper/docs/mac/readme_whisper_exec_mac.md)
- Windows 11 [readme](whisper/docs/win/readme_whisper_exec_win.md)

### Transcriber JAR ( [Readme](transcriber_jar/README.md) )
- It is a `Java` (`maven`) project which uses the created executable and provides an easy way to test the functionality. This may also be seen as a first step to create a jar which can be used as dependency in other java projects.
