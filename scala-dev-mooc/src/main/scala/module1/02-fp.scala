package module1

import module1.list.List.{::, Cons, Nil}

import scala.annotation.tailrec

/**
 *  Реализуем тип Option
 */


 object opt {

  /**
   *
   * Реализовать тип Option, который будет указывать на присутствие либо отсутсвие результата
   */

   sealed trait Option[+A]{
      def isEmpty: Boolean = this match {
       case Option.Some(_) => false
       case Option.None => true
      }

      def get: A = this match {
       case Option.Some(v) => v
       case Option.None => throw new Exception("get on empty Option")
      }
   }

   object Option {
    case class Some[A](v: A) extends Option[A]
    case object None extends Option[Nothing]
   }




  /**
   *
   * Реализовать метод printIfAny, который будет печатать значение, если оно есть
   */
  def printIfAny[A](option: Option[A]): Unit = {
    if (!option.isEmpty) println(option.get)
  }


  /**
   *
   * реализовать метод orElse который будет возвращать другой Option, если данный пустой
   */
  def orElse[A](option1: Option[A], option2: Option[A]): Option[A] = {
    if (option1.isEmpty) option2
    else option1
  }


  /**
   *
   * Реализовать метод isEmpty, который будет возвращать true если Option не пуст и false в противном случае
   */
  def isEmpty[A](option: Option[A]): Boolean = {
    option match {
      case Option.Some(_) => false
      case Option.None => true
    }
  }


  /**
   *
   * Реализовать метод get, который будет возвращать значение
   */
  def get[A](option: Option[A]): A = {
    option match {
      case Option.Some(v) => v
      case Option.None => throw new Exception("get on empty Option")
    }
  }


  /**
   *
   * Реализовать метод zip, который будет создавать Option от пары значений из 2-х Option
   */
  def zip[A](option1: Option[A], option2: Option[A]): Option[(A, A)] =
    Option.Some((option1.get, option2.get))


  /**
   *
   * Реализовать метод filter, который будет возвращать не пустой Option
   * в случае если исходный не пуст и предикат от значения = true
   */
  def filter[A](option: Option[A], p: Option[A] => Boolean): Option[A] = {
    if (!option.isEmpty && p(option)) option
    else Option.None
  }

 }

 object recursion {

   /**
    * Реализовать метод вычисления n!
    * n! = 1 * 2 * ... n
    */

   def fact(n: Int): Long = {
    var _n = 1L
    var i = 2
    while (i <= n) {
     _n *= i
     i += 1
    }
    _n
   }

   def !!(n: Int): Long = {
     if(n <= 1) 1
     else n * !!(n - 1)
   }

  def !(n: Int): Long = {
   @tailrec
   def loop(n1: Int, acc: Long): Long = {
     if(n <= 1) acc
     else loop(n1 - 1, n1 * acc)
    }
   loop(n, 1)
  }

 }

 object list {
   /**
    *
    * Реализовать односвязанный имутабельный список List
    */

   sealed trait List[+A]{
     def ::[AA >: A](head: AA): List[AA] = Cons(head, this)

    def mkString: String = mkString(", ")

    def mkString(sep: String): String = {
       import List._

      @tailrec
      def loop(l: List[A], acc: StringBuilder): StringBuilder = {
        l match {
         case List.Nil => acc
         case h :: Nil => acc.append(s"$h")
         case h :: t => loop(t, acc.append(s"$h$sep"))
        }
       }
      loop(this, new StringBuilder()).toString()
     }
   }

   object List{
    case object Nil extends List[Nothing]
    case class ::[A](head: A, tail: List[A]) extends List[A]
    val Cons = ::

    def apply[T](arg: T*): List[T] = {
     var l: List[T] = List.Nil
     arg.foreach(el => l = el :: l)
     l
    }
   }

   val list: List[Int] = 1 :: List.Nil

   /**
    *
    * Реализовать метод конс :: который позволит добавлять элемент в голову списка
    */
   def ::[A](head: A, tail: List[A]): List[A] = Cons(head, tail)


   /**
    *
    * Реализовать конструктор, для создания списка n элементов
    */
   def List[A](arg: A*): List[A] = {
     var l: List[A] = List.Nil
     arg.foreach(el => l = el :: l)
     l
   }


   /**
    *
    * Реализовать метод mkString который позволит красиво представить список в виде строки
    */
   def mkString[A](l: List[A], sep: String): String = {
     @tailrec
     def loop(list: List[A], acc: StringBuilder): StringBuilder = {
       list match {
         case List.Nil => acc
         case h :: Nil => acc.append(s"$h")
         case h :: t => loop(t, acc.append(s"$h$sep"))
       }
     }
     loop(l, new StringBuilder()).toString()
   }


   /**
    *
    * Реализовать метод reverse который позволит заменить порядок элементов в списке на противоположный
    */
   def append[T](list: List[T], last: T): List[T] = {
     list match {
       case h :: List.Nil => Cons(h, List[T](last))
       case h :: t => Cons(h, append(t, last))
     }
   }

   def reverse[A](list: List[A]): List[A] = {
     list match {
       case h :: Nil => List[A](h)
       case h :: t => append(reverse(t), h)
     }
   }


   /**
    *
    * Написать функцию incList котрая будет принимать список Int и возвращать список,
    * где каждый элемент будет увеличен на 1
    */
   def incList(list: List[Int]): List[Int] = {
     list match {
       case h :: List.Nil => List[Int](h + 1)
       case h :: t => Cons(h + 1, incList(t))
     }
   }


   /**
    *
    * Написать функцию shoutString котрая будет принимать список String и возвращать список,
    * где к каждому элементу будет добавлен префикс в виде '!'
    */
   def shoutString(list: List[String]): List[String] = {
     list match {
       case h :: List.Nil => List[String]("!" + h)
       case h :: t => Cons("!" + h, shoutString(t))
     }
   }


   /**
    *
    * Реализовать метод для списка который будет применять некую ф-цию к элементам данного списка
    */
   def map[T](list: List[T], f: T => T): List[T] = {
     list match {
       case h :: List.Nil => List[T](f(h))
       case h :: t => Cons(f(h), map(t, f))
     }
   }

 }