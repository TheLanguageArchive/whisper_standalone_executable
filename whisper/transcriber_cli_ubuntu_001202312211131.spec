# -*- mode: python ; coding: utf-8 -*-


a = Analysis(
    ['cli.py'],
    pathex=[],
    binaries=[('/home/h/anaconda3/envs/transcriber_cli_env202401081644/bin/ffmpeg', 'bin'), ('/home/h/anaconda3/envs/transcriber_cli_env202401081644/bin/ffprobe', 'bin'), ('/home/h/anaconda3/envs/transcriber_cli_env202401081644/lib/libstdc++.so.6', '.')],
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
    name='transcriber_cli_ubuntu_001202312211131',
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
