package io.github.spritzsn.fs

import io.github.spritzsn.libuv.*
import io.github.spritzsn.async.loop

import scala.util.{Failure, Success}

@main def run(): Unit =
  open("asdf", O_CREAT, S_IRWXU) onComplete {
    case Success(h) =>
      h.write("one\ntwo\n".getBytes) onComplete {
        case Success(h)         => h.close()
        case Failure(exception) => println(exception.getMessage)
      }
    case Failure(exception) => println(exception.getMessage)
  }
  loop.run()
