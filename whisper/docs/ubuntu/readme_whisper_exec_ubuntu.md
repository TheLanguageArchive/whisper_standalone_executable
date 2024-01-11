
# OpenAi's Whisper   as   Standalone Ubuntu 20.04 CLI

## Compatibility:
- Platform: 
- Python 3.11.5
- pip 23.3.1
- setuptools 68.2.2
- ffmpeg 6.1.0

## Environment 

```
VirtualBox Graphical User Interface Version 7.0.12 r159484 (Qt5.15.2) Copyright © 2023 Oracle and/or its affiliates
Download Ubuntu 23.10 and install as virtual machine OS
HardDisk : 128 GB
RAM: 8192 GB
Processor: 2 core
```
`Guest additions` installed (Optional)

OR 

Virtuyal box encountered an issue hence VMWare is being tested
```
VMware® Workstation 16 Player
16.2.5 build-20904516
RAM: 8192 GB
```

## Hardware Details

The OS that runs the whisper script needs to have at least 8 GB RAM available for the whisper's `tiny` model.


## Dev Guide | Way to work with it:

- `sudo apt-get update`
- `sudo apt-get install libgl1-mesa-glx libegl1-mesa libxrandr2 libxrandr2 libxss1 libxcursor1 libxcomposite1 libasound2 libxi6 libxtst6`
- `sudo apt-get install -y curl`
- `curl -O https://repo.anaconda.com/archive/Anaconda3-2023.09-0-Linux-x86_64.sh`
- `bash Anaconda3-2023.09-0-Linux-x86_64.sh -b`
- `source ~/.bashrc`
- `ssh-keygen -t ed25519 -C "<comment>"`
- `cat ~/.ssh/id_ed25519.pub`
- `sudo apt-get install git-all`
- `git clone git@github.com:TheLanguageArchive/whisper_standalone_executable.git --depth 1`
- From `App Center` install `PyCharm Community Edition` (Optional)
- `cd whisper_standalone_executable/whisper/`
- Create Anaconda (Conda) Environment : `transcriber_cli_env202401101448` (Intellij Idea Community > File > Settings > Project : Whisper > Add Interpreter > Conda > Create `transcriber_cli_env202401101448`)
- `conda list > docs/ubuntu/logs/transcriber_cli_env_ubuntu_base.txt`
- `conda create --name transcriber_cli_env202401101448 python=3.11.5`
- Select the created Python Environment in the `PyCharm` / `Visual Studio Code` / `Terminal`
- `conda activate transcriber_cli_env202401101448`
- `conda list > docs/ubuntu/logs/transcriber_cli_env_ubuntu_202401101448_init.txt`
- Install Pre-req Dependencies:
  - `pip install -r requirements.txt`
  - `conda install -c conda-forge ffmpeg=6.1.0`
- `cd <workspace>/whisper_standalone_executable/whisper`
- `conda list > docs/ubuntu/logs/transcriber_cli_env_ubuntu_202401101448_final.txt`


## Clean

```
rm -r build &&
rm -r dist &&
rm -r whisper_transcriptions &&
rm transcriber_cli_ubuntu_001202312211131.spec
```

## Running as python script

From `whisper` project root folder.

```
python cli.py ./../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=tiny --language=en --output_format=json --output_dir=./whisper_transcriptions/
```

## Packaging for Release(deliverable):

Create the executable(s) file in `dist` directory that can be run from the command line: 

#### Sample command to run from OSx

Notes:
- osPathsSeparator `:`
- versionWithoutDots

```
sudo apt-get install binutils
```

```
pyinstaller cli.py \
  --onefile \
  --name transcriber_cli_ubuntu_001202312211131 \
  --add-data "whisper/assets/*":whisper/assets \
  --add-binary ~/anaconda3/envs/transcriber_cli_env202401101448/bin/ffmpeg:bin \
  --add-binary ~/anaconda3/envs/transcriber_cli_env202401101448/bin/ffprobe:bin \
  --add-binary ~/anaconda3/envs/transcriber_cli_env202401101448/lib/libstdc++.so.6:.
```

OR pre-pend following line to the `*.spec` file `import sys ; sys.setrecursionlimit(sys.getrecursionlimit() * 5)`

And then
```
pyinstaller --clean --log-level=DEBUG transcriber_cli_ubuntu_001202312211131.spec
```

For testing from the project root directory `transcriber_cli` execute the following command:

```
"dist/transcriber_cli_ubuntu_001202312211131" ./../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=tiny --language=en --output_format=json --output_dir=./whisper_transcriptions/
```

## Distribution

```
cp "./dist/transcriber_cli_ubuntu_001202312211131" "./../transcriber_jar/src/main/resources/releases/transcriber_cli_ubuntu_001202312211131" 
```

```
conda deactivate
```

In `whisper_standalone_executable`
```
"./../transcriber_jar/src/main/resources/releases/transcriber_cli_ubuntu_001202312211131" ./../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=tiny --language=en --output_format=json --output_dir=./whisper_transcriptions/
```

Transcription files will be created.

<span style="color:red">**NOTE:**</span> Ideally, we should be able to assume that after installation if this is the first file copied and ran. It should work. While the executable tries to package everything it needs there are still some libraries which are expected to be present in the host system.

## Potentially useful info

Cache location: `~/.cache/whisper/`

``