package io.github.spritzsn.fs

import io.github.spritzsn.libuv.*
import io.github.spritzsn.async._

import scala.collection.immutable.ArraySeq
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Future, Promise}
import scala.io.Codec

import cps.*
import cps.monads.FutureAsyncMonad

def writeFile(path: String, data: collection.IndexedSeq[Byte], flags: Int, mode: Int): Future[Unit] = async {
  val file = await(open(path, flags, mode))

  await(file.write(data))
  await(file.close)
}

def writeFile(path: String, data: String, flags: Int, mode: Int, codec: Codec = Codec.UTF8): Future[Unit] =
  writeFile(path, data.getBytes(codec.charSet), flags, mode)
