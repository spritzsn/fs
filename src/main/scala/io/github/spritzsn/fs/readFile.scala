package io.github.spritzsn.fs

import io.github.spritzsn.libuv.*
import io.github.spritzsn.async._

import scala.collection.immutable.ArraySeq
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Future, Promise}
import scala.io.Codec

def readFile(path: String): Future[ArraySeq[Byte]] =
  val promise = Promise[ArraySeq[Byte]]()
  val buf = new ArrayBuffer[Byte]

  def opencb(req: FileReq): Unit =
    val openres = req.getResult

    if openres < 0 then promise.failure(new RuntimeException(errorMessage(openres, "uv_fs_open callback")))
    else
      def readcb(req: FileReq): Unit =
        val res = req.getResult

        if res < 0 then promise.failure(new RuntimeException(errorMessage(res, "uv_fs_read callback")))
        else if res > 0 then
          req.buffer.read(buf, res)
          defaultLoop.read(openres, readcb)
        else
          defaultLoop.close(openres)
          promise.success(buf to ArraySeq)

        req.buffer.dispose()

      defaultLoop.read(openres, readcb)

  defaultLoop.open(path, O_RDONLY, 0, opencb)
  promise.future

def readFile(path: String, codec: Codec): Future[String] =
  readFile(path) map (a => new String(a.toArray, codec.charSet))
