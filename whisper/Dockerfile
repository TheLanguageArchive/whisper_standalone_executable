FROM amd64/ubuntu:20.04

VOLUME /root

WORKDIR root

# Semantic Version.
ARG CLI_VER_MAJ=0
ARG CLI_VER_MIN=0
ARG CLI_VER_PAT=1

# This version is to know how many versions are expected before it. This is derived from Semantic Version.
ARG CLI_SEM_VER=$CLI_VER_MAJ$CLI_VER_MIN$CLI_VER_PAT

# This version is to know how old a this build is.
ARG CLI_TIM_VER=202401051511

ARG CLI_VER=$CLI_SEM_VER$CLI_TIM_VER

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

RUN mkdir -p docs/ubuntu/logs/

SHELL ["conda", "run", "/bin/bash", "-i", "-c"]

RUN conda list > docs/ubuntu/logs/transcriber_cli_env_ubuntu_base.txt

RUN conda create \
    --name "py_env_202401101448" \
    python=3.11.5 \
    --yes

SHELL ["conda", "run", "-n", "py_env_202401101448", "/bin/bash", "-c"]

RUN pip install -r requirements.txt

RUN conda install -c conda-forge  ffmpeg=6.1.0 --yes

RUN conda list > docs/ubuntu/logs/transcriber_cli_env_ubuntu_202401101448.txt

RUN pyinstaller cli.py \
  --onefile \
  --name transcriber_cli_ubuntu_001202312211131 \
  --add-data "whisper/assets/*":whisper/assets \
  --add-binary /root/miniconda3/envs/py_env_202401101448/bin/ffmpeg:bin \
  --add-binary /root/miniconda3/envs/py_env_202401101448/bin/ffprobe:bin \
  --add-binary /root/miniconda3/envs/py_env_202401101448/lib/libstdc++.so.6:.

## Switch back to dialog for any ad-hoc use of apt-get
ENV DEBIAN_FRONTEND=dialog
