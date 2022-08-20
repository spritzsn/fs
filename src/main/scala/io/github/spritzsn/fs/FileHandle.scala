package io.github.spritzsn.fs

import io.github.spritzsn.libuv.{File, FileReq, defaultLoop, errorMessage}

import scala.concurrent.{Future, Promise}

implicit class FileHandle(val fd: Int) extends AnyVal:
  def write(data: collection.IndexedSeq[Byte]): Future[FileHandle] =
    var idx = 0
    val len = data.length
    val promise = Promise[FileHandle]()

    def writecb(req: FileReq): Unit =
      val res = req.getResult

      if res < 0 then promise.failure(new RuntimeException(errorMessage(res, "uv_fs_write callback")))
      else if idx + res < len then
        idx += res
        defaultLoop.write(data, idx, fd, writecb)
      else promise.success(this)

    defaultLoop.write(data, 0, fd, writecb)
    promise.future
