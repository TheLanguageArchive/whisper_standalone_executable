package nl.mpi.transcriber;

public enum WhisperModels {
    TINY_EN("tiny.en"),
    TINY("tiny"),
    BASE_EN("base.en"),
    BASE("base"),
    SMALL_EN("small.en"),
    SMALL("small"),
    MEDIUM_EN("medium.en"),
    MEDIUM("medium"),
    LARGE_V1("large-v1"),
    LARGE_V2("large-v2"),
    LARGE_V3("large-v3"),
    LARGE("large");

    private final String modelNameKey;

    WhisperModels(String modelName) {
        modelNameKey = modelName;
    }

    @Override
    public String toString() {
        return modelNameKey;
    }
}
