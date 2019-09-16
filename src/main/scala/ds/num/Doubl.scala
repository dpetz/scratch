package ds.num

import parser.Num

import scala.math.Numeric.DoubleIsFractional
import scala.util.Random

/** Implements [[Real]] with [[Double]] precision. */
package object Doubl {

  type Real = Double

  type Vec = Seq[Real]

  implicit val Real = new ds.num.Real[Double] with DoubleIsFractional {

    def compare(x:Double, y:Double): Int = (x - y).toInt

    def random:Double = Random.nextDouble

    def json(n:Num): Double = n.asDouble

    def power(x:Double, y: Double):Double = Math.pow(x,y)

  }


  implicit val Tolerance = new ds.num.Tolerance[Double] {
    val epsilon = 0.00001
    def approx(x: Double, y: Double):Boolean = (x - y).abs < epsilon
  }


}
