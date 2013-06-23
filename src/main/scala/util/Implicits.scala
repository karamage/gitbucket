package util

import twirl.api.Html

/**
 * Provides some usable implicit conversions.
 */
object Implicits {
  
  implicit def extendsSeq[A](seq: Seq[A]) = new {

    def splitWith(condition: (A, A) => Boolean): Seq[Seq[A]] = split(seq)(condition)

    @scala.annotation.tailrec
    private def split[A](list: Seq[A], result: Seq[Seq[A]] = Nil)(condition: (A, A) => Boolean): Seq[Seq[A]] = {
      list match {
        case x :: xs => {
          xs.span(condition(x, _)) match {
            case (matched, remained) => split(remained, result :+ (x :: matched))(condition)
          }
        }
        case Nil => result
      }
    }
  }

  implicit def extendsHtmlSeq(seq: Seq[Html]) = new {
    def mkHtml(separator: String) = Html(seq.mkString(separator))
    def mkHtml(separator: scala.xml.Elem) = Html(seq.mkString(separator.toString))
  }

}