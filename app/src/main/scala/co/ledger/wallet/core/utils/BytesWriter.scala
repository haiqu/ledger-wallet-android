/**
 *
 * BytesWriter
 * Ledger wallet
 *
 * Created by Pierre Pollastri on 25/01/16.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Ledger
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package co.ledger.wallet.core.utils

import java.io.ByteArrayOutputStream

class BytesWriter(length: Int) {

  def this() {
    this(-1)
  }

  def writeByteArray(array: Array[Byte]): BytesWriter = {
    for (byte <- array)
      writeByte(byte)
    this
  }

  def writeByte(byte: Int): BytesWriter = {
    writeByte(byte.toByte)
  }

  def writeByte(byte: Byte): BytesWriter = {
    _buffer.write(byte)
    this
  }

  def writeInt(int: Int): BytesWriter = {
    writeByte((int >> 24 & 0xFF).toByte)
    writeByte((int >> 16 & 0xFF).toByte)
    writeByte((int >> 8 & 0xFF).toByte)
    writeByte((int & 0xFF).toByte)
  }

  def writeInt(int: Long): BytesWriter = {
    writeByte((int >> 24 & 0xFF).toByte)
    writeByte((int >> 16 & 0xFF).toByte)
    writeByte((int >> 8 & 0xFF).toByte)
    writeByte((int & 0xFF).toByte)
  }

  def writeLeInt(int: Int): BytesWriter = {
    writeByte((int & 0xFF).toByte)
    writeByte((int >> 8 & 0xFF).toByte)
    writeByte((int >> 16 & 0xFF).toByte)
    writeByte((int >> 24 & 0xFF).toByte)
  }

  def writeLeInt(int: Long): BytesWriter = {
    writeByte((int & 0xFF).toByte)
    writeByte((int >> 8 & 0xFF).toByte)
    writeByte((int >> 16 & 0xFF).toByte)
    writeByte((int >> 24 & 0xFF).toByte)
  }

  def writeLong(long: Long): BytesWriter = {
    writeByte((long & 0xFF).toByte)
    writeByte((long >> 8 & 0xFF).toByte)
    writeByte((long >> 16 & 0xFF).toByte)
    writeByte((long >> 24 & 0xFF).toByte)
    writeByte((long >> 32 & 0xFF).toByte)
    writeByte((long >> 40 & 0xFF).toByte)
    writeByte((long >> 48 & 0xFF).toByte)
    writeByte((long >> 56 & 0xFF).toByte)
  }

  def writeLeLong(long: Long): BytesWriter = {
    writeByte((long >> 56 & 0xFF).toByte)
    writeByte((long >> 48 & 0xFF).toByte)
    writeByte((long >> 40 & 0xFF).toByte)
    writeByte((long >> 32 & 0xFF).toByte)
    writeByte((long >> 24 & 0xFF).toByte)
    writeByte((long >> 16 & 0xFF).toByte)
    writeByte((long >> 8 & 0xFF).toByte)
    writeByte((long & 0xFF).toByte)
  }

  def writeVarInt(int: Long): BytesWriter = {
    if (int < 0xfd) {
      writeByte(int.toByte)
    } else if (int <= 0xffff) {
      writeByte(0xfd)
      writeByte((int & 0xff).toByte)
      writeByte(((int >> 8) & 0xff).toByte)
    } else {
      writeByte(0xfe)
      writeByte((int & 0xff).toByte)
      writeByte(((int >> 8) & 0xff).toByte)
      writeByte(((int >> 16) & 0xff).toByte)
      writeByte(((int >> 24) & 0xff).toByte)
    }
  }

  def toByteArray = _buffer.toByteArray

  private[this] val _buffer: ByteArrayOutputStream = {
    if (length > 0)
      new ByteArrayOutputStream(length)
    else
      new ByteArrayOutputStream()
  }
}