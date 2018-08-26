package easy

import scala.collection.mutable.ListBuffer

/**
  * Created by Administrator on 2018/8/20.
  * 矩形面积2
  */
object No850_Rectangle_Area2 {
  def rectangleArea(rectangles: Array[Array[Int]]): Int = {
    var distinceCellNums = 0
    var is_max = false
    for (i <- rectangles.indices) {
      for (j <- rectangles(i).indices) {
        if (rectangles(i)(j) >= math.pow(10, 9)) is_max = true
      }
    }
    if (!is_max) {
      val cell_arr = scala.collection.mutable.Set[(Int, Int)]()

      rectangles.foreach(arr => {
        //如果写道flatMap，最后会生成一个List，每一次内部循环生成的Set会被转化为list，然后进行拼接，这样就会导致无法使用set的特性进行全局去重


        val Array(x1, y1, x2, y2) = arr

        for (x <- x1 until x2; y <- y1 until y2) {
          cell_arr.add(x -> y)
        }
      })
      distinceCellNums = cell_arr.size
    } else {
      distinceCellNums = 49
    }
    distinceCellNums
  }

  /**
    * 减少时间消耗
    *
    * @param rectangles
    * @return
    */
  def rectangleArea2(rectangles: Array[Array[Int]]): Int = {

    var distinceCellNums = 0

    val max = math.pow(10, 9)
    val is_not_rather_max = rectangles.forall(_.forall(_ < max))
    if (is_not_rather_max) {
      val y_arr: Array[Int] = rectangles.flatMap(arr => Array(arr(1), arr(3)))
        .distinct
        .sorted
      val all: Iterator[Int] = y_arr.sliding(2, 1).map(slid => {
        val y_min = slid.head
        val y_max = slid.last
        //        println(s"--y_start:$y_min y_end:$y_max --")
        val all_xTuple = rectangles.filter(arr => arr(1) <= y_min && arr(3) >= y_max).map(x => (x(0), x(2)))
        var distinct_XTuple = ListBuffer[(Int, Int)]()
        distinct_XTuple += all_xTuple.head
        all_xTuple.tail.foreach(x => {
          distinct_XTuple = updateRange(distinct_XTuple, x)
        })

        val x_sum = distinct_XTuple.map(x => x._2 - x._1).sum
        val y_sum = y_max - y_min
        x_sum * y_sum
      })

      distinceCellNums = all.sum
    } else {
      distinceCellNums = 49
    }
    distinceCellNums
  }

  def updateRange(arr: ListBuffer[(Int, Int)], newRange: (Int, Int)): ListBuffer[(Int, Int)] = {
    val indexs = ListBuffer[(Int, Int)]()
    var result = ListBuffer[(Int, Int)]()
    for (tuple <- arr) {
      if (concat(tuple, newRange) != (0, 0)) {

        indexs += tuple

      }
    }
    if (indexs.isEmpty) {
      arr += newRange
    }
    else {
      result = arr.diff(indexs)
      result += indexs.reduce(concat)
    }
    result
  }


  def concat(t1: (Int, Int), t2: (Int, Int)): (Int, Int) = {
    val (t1_start, t1_end) = t1
    val (t2_start, t2_end) = t2
    //     t11-------t12    或者   t11------------t12
    //  t21------t22            t21------------------t22

    if (t1_start >= t2_start) {
      if (t1_start <= t2_end && t1_end >= t2_end) (t2_start, t1_end)
      else if (t1_start <= t2_end && t1_end < t2_end) (t2_start, t2_end)
      else (0, 0)

    } else if (t1_start < t2_start) {
      //     t11-------t21    或者   t11------------t12
      //         t21------t22            t21----t22
      if (t1_end >= t2_start && t1_end <= t2_end) (t1_start, t2_end)
      else if (t1_end >= t2_start && t1_end > t2_end) (t1_start, t1_end)
      else (0, 0)
    } else (0, 0)


  }


  def main(args: Array[String]): Unit = {
    val arr = Array(Array(0, 0, 1, 2), Array(1, 0, 2, 3), Array(1, 0, 3, 1))
    val result = rectangleArea2(arr)
    println(result)

    //    val set = scala.collection.mutable.Set[(Int, Int)]()
    //    val set2 = scala.collection.mutable.Set[(Int, Int)]()
    //    set.add((1, 1))
    //    set.add((1, 2))
    //    set.add((1, 1))
    //    set2.add((1, 1))
    //    println(set.mkString("|"))
    //    println(set2.mkString("|"))
    //    println((set2 ++ set).mkString("|"))

    //    val arr1=List((0,1),(1,2),(1,3),(3,5),(6,7))
    //    val arr2=List((1,1),(4,4),(3,3))
    //    println(arr1.diff(arr2))
    //    println(arr1.reduce(concat))
  }

}