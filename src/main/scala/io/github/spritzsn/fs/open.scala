package io.github.spritzsn.fs

import io.github.spritzsn.libuv.*

import scala.concurrent.{Future, Promise}

val DEFAULT_MODE = S_IRGRP|S_IWGRP|S_IRUSR|S_IWUSR|S_IROTH|S_IWOTH

def open(path: String, flags: Int, mode: Int = DEFAULT_MODE): Future[FileHandle] =
  val promise = Promise[FileHandle]()

  def opencb(req: FileReq): Unit =
    val openres = req.getResult

    if openres < 0 then promise.failure(new RuntimeException(errorMessage(openres, "uv_fs_open callback")))
    else promise.success(openres)

  defaultLoop.open(path, flags, mode, opencb)
  promise.future

def open(path: String, flags: String, mode: Int): Future[FileHandle] =
  val flagbits =
    flags match
      case "a" => O_CREAT|O_APPEND|O_WRONLY
      case "as" => O_CREAT|O_APPEND|O_WRONLY|O_SYNC
      case "ads" => O_CREAT|O_APPEND|O_WRONLY|O_DSYNC
      case "r" => O_RDONLY
      case "r+" => O_RDWR
      case "rs+" => O_RDWR|O_SYNC
      case "rds+" => O_RDWR|O_DSYNC
      case "w" => O_CREAT|O_WRONLY
      case "w+" => O_CREAT|O_RDWR

  open(path, flagbits, mode)