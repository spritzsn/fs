package io.github.spritzsn.fs

import io.github.spritzsn.libuv.*
import io.github.spritzsn.async.loop

@main def run(): Unit =
  open("asdf", O_CREAT, S_IRWXU).onComplete(t => println(t))
