import os
import sys
import time

from whisper.transcribe import cli

def resource_path(relative_path):
    """ Get absolute path to resource, works for dev and for PyInstaller """
    try:
        # PyInstaller creates a temp folder and stores path in _MEIPASS
        base_path = sys._MEIPASS
    except Exception:
        base_path = os.path.abspath(".")

    return os.path.join(base_path, relative_path)

if __name__ == '__main__':
    startTime = time.time_ns()

    print(f"Whisper cli started at : {startTime} with arguments {sys.argv}")
    # determine if application is a script file or frozen exe
    if getattr(sys, 'frozen', False):
        os.environ["PATH"] += os.pathsep + resource_path("bin")

    if sys.argv[1] != "-B":
        cli()

    print("Whisper cli returned which was started at : ", startTime)
    sys.exit()