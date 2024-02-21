
# OpenAi's Whisper   as   Standalone Windows 11 CLI


## Compatibility:
- Platform: Windows 11 Enterprise
- Python 3.11.5
- pip 23.3.1
- setuptools 68.2.2
- ffmpeg 6.1.0

## Dev Guide | Way to work with it:

- Clone it. `git clone git@github.com:TheLanguageArchive/whisper_standalone_executable.git --depth 1`
- `cd whisper_standalone_executable/whisper`
- `conda update conda`
- `mkdir -p docs/win/logs/ ; conda list > docs/win/logs/transcriber_cli_env_win_base202401021322.txt`

- Create Anaconda (Conda) Environment : `transcriber_cli_env_win_202312211128`
- `conda create --name transcriber_cli_env_win_202312211128 python=3.11.5 -y`
- Select the created Python Environment in the `PyCharm` / `Visual Studio Code` / `Terminal`
  - `conda activate transcriber_cli_env_win_202312211128`
- `mkdir -p docs/win/logs/ ; conda list > docs/win/logs/transcriber_cli_env_win_base202401021322_init.txt`
- Install Pre-req Dependencies:
  - `pip install -r requirements.txt`
  - `conda install -c conda-forge ffmpeg=6.1.0 -y`
- `mkdir -p docs/win/logs/ ; conda list > docs/win/logs/transcriber_cli_env_win_base202401021322_final.txt`

## Clean

```
Remove-Item -Recurse -Force .\build `
; Remove-Item -Recurse -Force .\dist `
; Remove-Item .\transcriber_cli_win_001202401021637.spec `
; Remove-Item -Recurse -Force .\whisper_transcriptions
```

## Running as python script

From `whisper` project root folder.

```
python cli.py ../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=large-v3 --language=en --output_format=json --output_dir=./whisper_transcriptions/ --word_timestamps=True  --model_dir ~/AppData/Local/.cache/whisper
```

## Packaging for Release(deliverable):

Create the executable(s) file in `dist` directory that can be run from the command line:

#### Sample command to run from windows

Notes:
- osPathsSeparator `;`
- versionWithoutDots


```
pyinstaller cli.py `
  --console `
  --onefile `
  --clean `
  --log-level=DEBUG `
  --name transcriber_cli_win_001202401021637 `
  --add-data "whisper/assets/*;whisper/assets" `
  --add-binary "$env:USERPROFILE/AppData/Local/anaconda3/envs/transcriber_cli_env_win_202312211128/Library/bin/ffmpeg.exe;bin" `
  --add-binary "$env:USERPROFILE/AppData/Local/anaconda3/envs/transcriber_cli_env_win_202312211128/Library/bin/ffprobe.exe;bin"
```

OR pre-pend following line to the `*.spec` file `import sys ; sys.setrecursionlimit(sys.getrecursionlimit() * 5)`

And then
```
pyinstaller --clean --log-level=DEBUG transcriber_cli_win_001202401021637.spec
```

For testing from the project root directory `transcriber_cli` execute the following command:

```
&"dist/transcriber_cli_win_001202401021637.exe" "../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4" '--fp16=False' '--model=large-v3' '--language=en' '--output_format=json' '--output_dir=./whisper_transcriptions/' '--word_timestamps=True' python cli.py ../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=large-v3 --language=en --output_format=json --output_dir=./whisper_transcriptions/ --word_timestamps=True  --model_dir ~/AppData/Local/.cache/whisper
```

## Distribution

```
cp "./dist/transcriber_cli_win_001202401021637.exe" "./../transcriber_jar/src/main/resources/releases/transcriber_cli_win_001202401021637.exe" 
```

```
conda deactivate
```

In `whisper` directory
```
&"./../transcriber_jar/src/main/resources/releases/transcriber_cli_win_001202401021637" "./../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4" '--fp16=False' '--model=large-v3' '--language=en' '--output_format=json' '--output_dir=./whisper_transcriptions/' '--word_timestamps=True'python cli.py ../transcriber_jar/src/test/resources/videos/testFile202312061352.mp4 --fp16=False --model=large-v3 --language=en --output_format=json --output_dir=./whisper_transcriptions/ --word_timestamps=True  --model_dir ~/AppData/Local/.cache/whisper
```

Transcription files will be created.

<span style="color:red">**NOTE:**</span> Ideally, we should be able to assume that after installation if this is the first file copied and ran. It should work. While the executable tries to package everything it needs there are still some libraries which are expected to be present in the host system.

## Potentially useful info

Default Cache location: `~/.cache/whisper/`
Configured Cache Location: ~/AppData/Local/.cache/whisper
