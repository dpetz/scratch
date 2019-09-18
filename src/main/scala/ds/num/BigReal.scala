package ds.num

import scala.math.Numeric.BigDecimalIsFractional
import scala.util.Random


/**
  * @see https://www.scala-lang.org/api/current/scala/math/BigDecimal.html
  */
package object BigReal {

  type R = BigDecimal
  
  implicit val Real = new ds.num.Real[R]() with BigDecimalIsFractional {

    def compare(x:R, y:R):Int = (x - y).toInt

    def random = BigDecimal(Random.nextDouble)

    def json(n:parser.Num):R = n.asBigDecimal

    def power(x:R, y: R):R = x.pow(y.toIntExact)

    def apply(d:Double):R = BigDecimal(d)

    def apply(i:Int):R = BigDecimal(i)


    val precision = BigDecimal("0.0000001")

    def approx(x: R, y: R):Boolean = this.abs(x - y) < precision

    def MAX:R = BigDecimal(Double.MaxValue)

    def MIN:R = BigDecimal(Double.MinValue)

  }

}

