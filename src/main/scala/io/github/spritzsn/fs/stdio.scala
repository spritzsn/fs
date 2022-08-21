package io.github.spritzsn.fs

import scalanative.posix.unistd.{STDIN_FILENO, STDOUT_FILENO, STDERR_FILENO}

val stdin = new FileHandle(STDIN_FILENO)
val stdout = new FileHandle(STDOUT_FILENO)
val stderr = new FileHandle(STDERR_FILENO)
