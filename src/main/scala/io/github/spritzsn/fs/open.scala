package io.github.spritzsn.fs

import io.github.spritzsn.libuv.*

import scala.concurrent.{Future, Promise}

def open(path: String, flags: Int, mode: Int): Future[FileHandle] =
  val promise = Promise[FileHandle]()

  def opencb(req: FileReq): Unit =
    val openres = req.getResult

    if openres < 0 then promise.failure(new RuntimeException(errorMessage(openres, "uv_fs_open callback")))
    else promise.success(openres)

  defaultLoop.open(path, flags, mode, opencb)
  promise.future
