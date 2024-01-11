# -*- mode: python ; coding: utf-8 -*-


a = Analysis(
    ['cli.py'],
    pathex=[],
    binaries=[('/Users/hafreh/anaconda3/envs/transcriber_cli_env202312211128/bin/ffmpeg', 'bin'), ('/Users/hafreh/anaconda3/envs/transcriber_cli_env202312211128/bin/ffprobe', 'bin')],
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
    name='transcriber_cli_mac_001202312211131',
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=True,
    upx_exclude=[],
    runtime_tmpdir=None,
    console=False,
    disable_windowed_traceback=False,
    argv_emulation=True,
    target_arch=None,
    codesign_identity=None,
    entitlements_file=None,
)
app = BUNDLE(
    exe,
    name='transcriber_cli_mac_001202312211131.app',
    icon=None,
    bundle_identifier=None,
)
