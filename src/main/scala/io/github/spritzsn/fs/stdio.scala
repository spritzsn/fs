package io.github.spritzsn.fs

import scalanative.posix.unistd.STDOUT_FILENO

val stdin = new FileHandle(STDIN_FILENO)
val stdout = new FileHandle(STDOUT_FILENO)
val stderr = new FileHandle(STDERR_FILENO)
