import kotlin.math.sqrt

class ComplexNumber {
    var real = 0.0
    var img = 0.0
    var trigF: TrigonometricForm? = null

    constructor() {
    }

    constructor(real: Double, img: Double) {
        this.real = real
        this.img = img
    }

    constructor(real: Int, img: Int) {
        this.real = real.toDouble()
        this.img = img.toDouble()
    }

    constructor(src: ComplexNumber) {
        real = src.real
        this.img = src.img
        if (src.trigF != null) {
            trigF = TrigonometricForm(src.trigF)
        } else {
            trigF = null
        }
    }

    fun multByConst(c: Double): ComplexNumber {
        real *= c
        this.img *= c
        return this
    }

    fun add(num: ComplexNumber): ComplexNumber {
        real += num.real
        this.img += num.img
        return this
    }

    fun sub(num: ComplexNumber): ComplexNumber {
        real -= num.real
        this.img -= num.img
        return this
    }

    fun mult(num: ComplexNumber): ComplexNumber {
        real = real * num.real - this.img * num.img
        this.img = real * num.img + this.img * num.real
        return this
    }

    fun divide(num: ComplexNumber): ComplexNumber {
        real = (real * num.real + this.img * num.img) / (num.real * num.real + num.img * num.img)
        this.img = (this.img * num.real - real * num.img) / (num.real * num.real + num.img * num.img)
        return this
    }

    fun abs(): Double {
        return sqrt(real * real + img * img)
    }

    fun toTrigonometricStr(): String {
        trigF = TrigonometricForm(real, img)
        return trigF!!.r.toString() + "(cos + (i*sin))\twhere\ncos = " + trigF!!.cosCoef + "\nsin = " + trigF!!.sinCoef
    }

    override fun toString(): String {
        return "(" + real + " + " + img + "i)"
    }

    class TrigonometricForm {
        var cosCoef: Double
        var sinCoef: Double
        var r: Double

        internal constructor(real: Double, img: Double) {
            r = Math.sqrt(real * real + img * img)
            cosCoef = real / r
            sinCoef = img / r
        }

        internal constructor(src: TrigonometricForm?) {
            cosCoef = src?.cosCoef ?: 0.0
            sinCoef = src?.sinCoef ?: 0.0
            r = src?.r ?: 0.0
        }
    }
}