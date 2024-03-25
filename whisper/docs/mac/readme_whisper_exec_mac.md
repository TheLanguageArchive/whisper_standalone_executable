
# OpenAi's Whisper   as   Standalone Mac CLI

## Compatibility:
- Platform:  MacOS-14.1-arm64-arm-64bit (M2)
- Python 3.11.5
- pip 23.3.1
- setuptools 68.2.2
- ffmpeg 6.1.0

## Dev Guide | Way to work with it:

- Clone it. `git clone git@github.com:TheLanguageArchive/whisper_standalone_executable.git --depth 1`
- `cd whisper_standalone_executable/whisper`
- `mkdir -p docs/mac/logs/ ; conda list > docs/mac/logs/transcriber_cli_env_mac_base.txt`
- Create Anaconda (Conda) Environment : `transcriber_cli_env_mac_202312211128`
- `conda create --name transcriber_cli_env_mac_202312211128 python=3.11.5 -y`
- Select the created Python Environment in the `PyCharm` / `Terminal`
  - `conda activate transcriber_cli_env_mac_202312211128`
  - `mkdir -p docs/mac/logs/ ; conda list > docs/mac/logs/transcriber_cli_env_mac_202312211128_init.txt`
- Install Pre-req Dependencies:
  - `pip install -r requirements.txt`
  - `conda install -c conda-forge ffmpeg=6.1.0 -y`

- `mkdir -p docs/mac/logs/ ; conda list > docs/mac/logs/transcriber_cli_env_mac_202312211128_final.txt`

## Clean

```
rm -r build &&
rm -r dist &&
rm -r whisper_transcriptions &&
rm transcriber_cli_mac_001202312211131.spec
```

## Running as python script

From `whisper` project root folder.

```
python cli.py ./../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=large-v3 --language=en --output_format=json --output_dir=./whisper_transcriptions/ --word_timestamps=True
```

## Packaging for Release(deliverable):

Create the executable(s) file in `dist` directory that can be run from the command line: 

#### Sample command to run from OSx

Notes: 
- osPathsSeparator `:`
- versionWithoutDots

```
pyinstaller cli.py \
  --noconsole \
  --onefile \
  --clean \
  --log-level=DEBUG \
  --argv-emulation \
  --name transcriber_cli_mac_001202312211131 \
  --add-data "whisper/assets/*":whisper/assets \
  --add-binary ~/anaconda3/envs/transcriber_cli_env_mac_202312211128/bin/ffmpeg:bin \
  --add-binary ~/anaconda3/envs/transcriber_cli_env_mac_202312211128/bin/ffprobe:bin
```

OR pre-pend following line to the `*.spec` file `import sys ; sys.setrecursionlimit(sys.getrecursionlimit() * 5)`

And then
```
pyinstaller --clean --log-level=DEBUG transcriber_cli_mac_001202312211131.spec
```

For testing from the project root directory `transcriber_cli` execute the following command:

```
"dist/transcriber_cli_mac_001202312211131" ./../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=large-v3 --language=en --output_format=json --output_dir=./whisper_transcriptions/ --word_timestamps=True
```

## Distribution

```
mkdir -p ./../transcriber_jar/src/main/resources/releases/ ; \
cp "./dist/transcriber_cli_mac_001202312211131" "./../transcriber_jar/src/main/resources/releases/transcriber_cli_mac_001202312211131" 
```

```
conda deactivate
```

In `whisper` directory
```
"./../transcriber_jar/src/main/resources/releases/transcriber_cli_mac_001202312211131" ./../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=large-v3 --language=en --output_format=json --output_dir=./whisper_transcriptions/ --word_timestamps=True
```

Transcription files will be created.

<span style="color:red">**NOTE:**</span> Ideally, we should be able to assume that after installation if this is the first file copied and ran. It should work. While the executable tries to package everything it needs there are still some libraries which are expected to be present in the host system.

## Potentially useful info

Cache location: `~/.cache/whisper/`
