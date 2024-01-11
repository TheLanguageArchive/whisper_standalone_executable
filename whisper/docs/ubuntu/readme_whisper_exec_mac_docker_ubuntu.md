
# OpenAi's Whisper   as   Standalone Ubuntu 20.04 CLI (Docker)

#### Compatibility:
- Platform: Ubuntu 20.04
- Python 3.11.5
- pip 23.3.1
- setuptools 68.2.2
- ffmpeg 6.1.0

## Dev Guide | Way to work with it:

In "workspace"

```
git clone git@github.com:TheLanguageArchive/whisper_standalone_executable.git --depth 1
```

Build Docker Image

```commandline
docker build -t ubuntu20exec_builder_image:latest .
```

Run container (perhaps to debug or check the created executable)
```commandline
docker run -it --name ubuntu20exec_builder_container ubuntu20exec_builder_image:latest
```

Close container's terminal/shell
```commandline
exit
```

Now from outside container:
```
docker cp ubuntu20exec_builder_container:/root/whisper/dist/transcriber_cli_ubuntu_001202401161123 ./../transcriber_jar/src/main/resources/releases
```
