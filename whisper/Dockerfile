FROM amd64/ubuntu:20.04

VOLUME /root

WORKDIR root

# Semantic Version.
ARG CLI_VER_MAJ=0
ARG CLI_VER_MIN=0
ARG CLI_VER_PAT=1

# This version is to know how many versions are expected before it. This is derived from Semantic Version.
ARG CLI_SEM_VER=$CLI_VER_MAJ$CLI_VER_MIN$CLI_VER_PAT

# This version is to know how old this build is.
ARG CLI_TIM_VER=202401161123

ARG CLI_VER=$CLI_SEM_VER$CLI_TIM_VER

ARG PY_ENV_NAME="py_env_$CLI_VER"

ARG INITIAL="_init"

ARG FINAL="_final"

ARG HUMAN_READABLE_VERSION="$CLI_VER_MAJ.$CLI_VER_MIN.$CLI_VER_PAT $CLI_TIM_VER"

# Copy directory from host to container
COPY . /root/whisper

RUN apt-get update

RUN apt-get install -y libgl1-mesa-glx libegl1-mesa libxrandr2 libxrandr2 libxss1 libxcursor1 libxcomposite1 libasound2 libxi6 libxtst6

RUN apt-get install -y curl pip

RUN curl -O https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh

RUN bash Miniconda3-latest-Linux-x86_64.sh -b

RUN rm Miniconda3-latest-Linux-x86_64.sh

ENV PATH /root/miniconda3/bin:$PATH

WORKDIR ./whisper

RUN mv ./videos ./../

RUN echo $HUMAN_READABLE_VERSION > v.txt

RUN mkdir -p docs/ubuntu/logs/

SHELL ["conda", "run", "/bin/bash", "-i", "-c"]

RUN conda list > docs/ubuntu/logs/py_env_base.txt

RUN conda create \
    --name $PY_ENV_NAME \
    python=3.11.5 \
    --yes

SHELL ["conda", "run", "-n", "py_env_001202401161123", "/bin/bash", "-c"]

RUN conda list > docs/ubuntu/logs/$PY_ENV_NAME$INITIAL.txt

RUN pip install -r requirements.txt

RUN conda install -c conda-forge  ffmpeg=6.1.0 --yes

RUN conda list > docs/ubuntu/logs/$PY_ENV_NAME$FINAL.txt

RUN pyinstaller cli.py \
  --onefile \
  --name transcriber_cli_ubuntu_${CLI_VER} \
  --add-data "whisper/assets/*":whisper/assets \
  --add-binary /root/miniconda3/envs/${PY_ENV_NAME}/bin/ffmpeg:bin \
  --add-binary /root/miniconda3/envs/${PY_ENV_NAME}/bin/ffprobe:bin \
  --add-binary /root/miniconda3/envs/${PY_ENV_NAME}/lib/libstdc++.so.6:.

# Optional command. This tests the created exectuable. It needs the video file.
RUN "/root/whisper/dist/transcriber_cli_ubuntu_${CLI_VER}" \
  /root/videos/testFile202312061352.mp4 \
  --fp16=False \
  --model=tiny \
  --language=en \
  --output_format=json \
  --output_dir=./whisper_transcriptions/

## Switch back to dialog for any ad-hoc use of apt-get
ENV DEBIAN_FRONTEND=dialog

# Set the entry point to the shell executable
ENTRYPOINT ["/bin/bash"]
