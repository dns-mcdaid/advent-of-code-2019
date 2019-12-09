package com.dennismcdaid.advent2019

fun <T> Sequence<T>.indexedCycleIterator(): Iterator<Pair<Int, T>> = object : AbstractIterator<Pair<Int, T>>() {
  var source = this@indexedCycleIterator
  var i = 0
  override fun computeNext() {
    if (source.none()) return done()
    setNext(Pair(i, source.elementAt(i)))
    if (++i == source.count()) i = 0
  }
}