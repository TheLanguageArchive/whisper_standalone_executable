
# OpenAi's Whisper   as   Standalone Ubuntu 20.04 CLI (Docker)

#### Compatibility:
- Platform: Ubuntu 20.04
- Python 3.11.5
- pip 23.3.1
- setuptools 68.2.2
- ffmpeg 6.1.0

## Dev Guide | Way to work with it:

In "workspace"

```shell
git clone git@github.com:TheLanguageArchive/whisper_standalone_executable.git --depth 1
```

```shell
cd whisper_standalone_executable/whisper
```

```shell
cp -r ./../../videos .
```

Build Docker Image

```shell
docker build -t ubuntu20exec_builder_image:latest .
```

Run container (perhaps to debug or check the created executable)
```shell
docker run -it --name ubuntu20exec_builder_container ubuntu20exec_builder_image:latest
```

Close container's terminal/shell
```shell
exit
```

Now from outside container:
```shell
docker cp ubuntu20exec_builder_container:/root/whisper/dist/transcriber_cli_ubuntu_001202401161123 ./../transcriber_jar/src/main/resources/releases
```

In `whisper` directory
```shell
"./../transcriber_jar/src/main/resources/releases/transcriber_cli_ubuntu_001202401161123" ./../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=tiny --language=en --output_format=json --output_dir=./whisper_transcriptions/ --word_timestamps=True
```
