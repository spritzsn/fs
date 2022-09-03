//package io.github.spritzsn.fs
//
//import io.github.spritzsn.libuv.*
//import io.github.spritzsn.async.loop
//
//import scala.util.{Failure, Success}
//
//@main def run(): Unit =
//  open("asdf", "wx") onComplete {
//    case Success(h) =>
//      h.write("zero\n".getBytes) onComplete {
//        case Success(h)         => h.close
//        case Failure(exception) => println(exception.getMessage)
//      }
//    case Failure(exception) => println(exception.getMessage)
//  }
//  loop.run()
