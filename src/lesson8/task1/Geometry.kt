@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(other.x - x) + sqr(other.y - y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    fun perimeter() = a.distance(b) + b.distance(c) + c.distance(a)
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area() // is not work as well
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Вывод такой: надо исправлять алгоритм поиска точки в треугольнике,
 * заменой на векторный поиск.-.понимаю что немного зациклился не на том
 * и скорее всего для большинства очевидно что здесь будет что-то не так,
 * но все же для будущего поколения стоит эти изьяны убрать
 *
 * result:
 * ploshad' izza P = 0.7499999999999857 + 3.000000000000001 + 2.2499999999999982= 5.999999999999985
 * ploshad' izza M = 0.25000000000000505 + 4.999999999999997 + 0.7499999999999988= 6.000000000000002
 * ploshad' izza N = 0.49999999999999833 + 0.9999999999999981 + 4.499999999999998= 5.999999999999995
 * ploshad' izza T = 2.5000000000000027 + 1.999999999999999 + 1.4999999999999998= 6.000000000000002
 * ploshad' osnovi = 6.0
 */
fun main() {
    val new = Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0))
    val dotP = Point(1.5, 1.5)
    val dotM = Point(0.5, 2.5)
    val dotN = Point(3.0, 0.5)
    val dotT = Point(1.0, 1.0)
    val abp = Triangle(dotP, Point(0.0, 3.0), Point(4.0, 0.0))
    val bcp = Triangle(Point(0.0, 0.0), dotP, Point(4.0, 0.0))
    val cap = Triangle(Point(0.0, 0.0), Point(0.0, 3.0), dotP)

    val abm = Triangle(dotM, Point(0.0, 3.0), Point(4.0, 0.0))
    val bcm = Triangle(Point(0.0, 0.0), dotM, Point(4.0, 0.0))
    val cam = Triangle(Point(0.0, 0.0), Point(0.0, 3.0), dotM)

    val abn = Triangle(dotN, Point(0.0, 3.0), Point(4.0, 0.0))
    val bcn = Triangle(Point(0.0, 0.0), dotN, Point(4.0, 0.0))
    val can = Triangle(Point(0.0, 0.0), Point(0.0, 3.0), dotN)

    val abt = Triangle(dotT, Point(0.0, 3.0), Point(4.0, 0.0))
    val bct = Triangle(Point(0.0, 0.0), dotT, Point(4.0, 0.0))
    val cat = Triangle(Point(0.0, 0.0), Point(0.0, 3.0), dotT)
    println(
        "ploshad' izza P = ${abp.area()} + ${bcp.area()} + ${cap.area()}" +
                " = ${abp.area() + bcp.area() + cap.area()}"
    )
    println(
        "ploshad' izza M = ${abm.area()} + ${bcm.area()} + ${cam.area()}" +
                " = ${abm.area() + bcm.area() + cam.area()}"
    )
    println(
        "ploshad' izza N = ${abn.area()} + ${bcn.area()} + ${can.area()}" +
                " = ${abn.area() + bcn.area() + can.area()}"
    )
    println(
        "ploshad' izza T = ${abt.area()} + ${bct.area()} + ${cat.area()}" +
                " = ${abt.area() + bct.area() + cat.area()}"
    )
    println("ploshad' osnovi (ABC) = ${new.area()}")
}

fun newTriangle(): Double {
    val tri = Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0))
    println(tri.perimeter())
    return tri.perimeter()
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double =
        if (other.center.distance(center) <= other.radius + radius) (0.0)
        else (other.center.distance(center) - (other.radius + radius))

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = sqr(center.x - p.x) + sqr(center.y - p.y) <= sqr(radius)
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.isEmpty() || points.size < 2) throw IllegalArgumentException()
    else {
        var start = Point(0.0, 0.0)
        var end = Point(0.0, 0.0)
        for (i in 0 until points.size)
            for (j in i + 1 until points.size)
                if (points[i].distance(points[j]) > start.distance(end)) {
                    end = points[j]
                    start = points[i]
                }
        return Segment(start, end)
    }
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle = Circle(
    center = Point(
        x = (diameter.begin.x + diameter.end.x) / 2,
        y = (diameter.begin.y + diameter.end.y) / 2
    ), radius = diameter.begin.distance(diameter.end) / 2
)

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point = Point(
        x = (cos(angle) * other.b - cos(other.angle) * b) / sin(angle - other.angle),
        y = (sin(angle) * other.b - sin(other.angle) * b) / sin(angle - other.angle)
    )
//    fun crossPoint(other: Line): Point = Point(
//        (cos(angle) * other.b - cos(other.angle) * b) / sin(angle - other.angle),
//        (sin(angle) * other.b - sin(other.angle) * b) / sin(angle - other.angle)
//    )

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val angle = atan2(s.end.y - s.begin.y, s.end.x - s.begin.x)
    return Line(
        s.begin,
        when {
            angle >= 0 -> angle
            else -> (PI + angle)
        }
    )
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val angle = atan2(b.y - a.y, b.x - a.x)//scnd.y - frst.y, scnd.x - frst.x
    return Line(
        a, when {
            angle >= 0 -> angle
            else -> (PI + angle)
        }
    )
}
//fun lineByPoints(a: Point, b: Point): Line {
//    val angle = atan2(b.y - a.y, b.x - a.x) % PI //end.y - begin.y, end.x - begin.x
//    return Line(a, angle)
//}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line { // center, angle
    val angle = (lineByPoints(a, b).angle + PI / 2) % PI
    return Line(
        (circleByDiameter(Segment(a, b)).center),
        when {
            angle >= 0 -> angle % PI
            else -> (PI + angle) % PI
        }
    )
}
//fun bisectorByPoints(a: Point, b: Point): Line = Line(
//    circleByDiameter(Segment(a, b)).center,
//    (lineByPoints(a, b).angle + PI / 2) % PI
//)
/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> = TODO()

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle = TODO()

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()

