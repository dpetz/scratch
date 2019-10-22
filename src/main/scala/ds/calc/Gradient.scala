package ds.calc

import ds.calc.Gradient.Direction
import ds.expr.Expr
import ds.num.Real
import ds.vec.Implicits._
import ds.expr.Functions._
import ds.expr.Implicits._
import ds.num.Implicits._
import ds.vec.Vec




/** Gradient of real-valued function
  * Estimated via difference quotient unless provided analytically
  * @param pd ith partial difference quotient of f at v
  * */
case class Gradient[R:Real](f:Seq[R]=>R, pd:Direction[R] => Expr[R]) extends (Vec[R] => Vec[R])  {

  /** Computes full (= across all indices) gradient at v  */
  def apply(v:Vec[R]):Vec[R] = transform(v) { s =>
    (s indices) map { i:Int => pd(Direction(s,i))}
  }

  /** Negate function and partial derivatives */
  //def unary_- : Gradient[R] = Gradient(-f,-pd)
  // @todo  arithmetic operations for functions

}

object Gradient {

  /** A position vector + a direction index to request a partial derivative */
  case class Direction[R](v:Seq[R], i:Int)
  // @todo How to make this an expression and still type check in Gradient constructor

  /** Estimates gradient. Computational expensive (2n function evaluations)
    * and estimation error can be substantial, see Gradient.test
    * The tiny constant to approximate limit for difference quotient is taken from Precision.
    * */
  def estimate[R](f:Seq[R]=>R)(implicit real:Real[R]):Gradient[R] =
    Gradient(f, { case Direction(v,i) =>
      val w = v update (i, (x:Expr[R]) => x + real.precision )
      ( f(w) - f(v) ) / real.precision
    })

}