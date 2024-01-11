
# OpenAi's Whisper   as   Standalone Ubuntu 20.04 CLI (Docker)

In "workspace"

```
git clone git@github.com:TheLanguageArchive/whisper_standalone_executable.git --depth 1
```
#### (Optional) Add the test file to container

This step is optional and should be done when one requires debugging the container. Whether the exe created inside the container can work within same environment or not.

`cd ./whisper_standalone_executable/whisper/`

`ln -v ../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 ./videos/testFile202312061352.mp4`

#### Build Docker Image

```
docker build -t ubuntu20exec_builder_image/transcriber:latest .
```

(Optional) Rename image if required :
```
docker image tag 7ab1164eaa98 ubuntu20exec_builder_image/transcriber:latest
```

#### Run executable

Running container and use container's bash

```commandline
docker run -it --name ubuntu20exec_builder_container ubuntu20exec_builder_image/transcriber:latest
```

Run the created executable:

```
  "/root/whisper/dist/transcriber_cli_ubuntu_001202312211131" \
    ./videos/testFile202312061352.mp4 \
    --fp16=False \
    --model=tiny \
    --language=en \
    --output_format=json \
    --output_dir=./whisper_transcriptions/
```

#### Copy out

Getting the executable out of the container to the host system

From outside container:
```
docker cp ubuntu20exec_builder_container:/root/whisper/dist/transcriber_cli_ubuntu_001202312211131 ./../transcriber_jar/src/main/resources/releases
```
