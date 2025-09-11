package org.example.savingAndKeeping;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public final class AtomicFiles {
    private AtomicFiles() {}

    public static void writeJsonAtomically(ObjectMapper om, Path target, Object payload) {
        try {
            Files.createDirectories(target.getParent());
            Path tmp = target.resolveSibling(target.getFileName() + ".tmp");
            om.writerWithDefaultPrettyPrinter().writeValue(tmp.toFile(), payload);
            Files.move(tmp, target, REPLACE_EXISTING, ATOMIC_MOVE);
        } catch (IOException e) {
            throw new RuntimeException("Persist failed: " + target, e);
        }
    }
}
