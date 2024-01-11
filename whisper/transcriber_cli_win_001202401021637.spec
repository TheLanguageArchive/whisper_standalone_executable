# -*- mode: python ; coding: utf-8 -*-


a = Analysis(
    ['cli.py'],
    pathex=[],
    binaries=[('C:\\Users\\hafreh/AppData/Local/anaconda3/envs/transcriber_cli_env_win_202312211128/Library/bin/ffmpeg.exe', 'bin'), ('C:\\Users\\hafreh/AppData/Local/anaconda3/envs/transcriber_cli_env_win_202312211128/Library/bin/ffprobe.exe', 'bin')],
    datas=[('whisper/assets/*', 'whisper/assets')],
    hiddenimports=[],
    hookspath=[],
    hooksconfig={},
    runtime_hooks=[],
    excludes=[],
    noarchive=False,
)
pyz = PYZ(a.pure)

exe = EXE(
    pyz,
    a.scripts,
    a.binaries,
    a.datas,
    [],
    name='transcriber_cli_win_001202401021637',
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=True,
    upx_exclude=[],
    runtime_tmpdir=None,
    console=True,
    disable_windowed_traceback=False,
    argv_emulation=False,
    target_arch=None,
    codesign_identity=None,
    entitlements_file=None,
)
