class Matrix {
    var matrix: Array<Array<Double>>
    var n: Int
    var m: Int

    constructor(n: Int, m: Int) {
        this.m = m
        this.n = n
        this.matrix = Array(n) { Array(m) { 0.0 } }
    }

    constructor(initial: Array<Array<Double>>) {
        this.n = initial.size
        this.m = if (initial.isEmpty()) 0 else initial.first().size
        this.matrix = initial
    }

    constructor(source: Matrix) {
        this.m = source.m
        this.n = source.n
        this.matrix = source.matrix.copyOf()
    }

    fun multByConst(const: Double): Matrix {
        for (i in matrix.indices) {
            if (matrix.first().isEmpty()) {
                return this
            }
            for (j in matrix.first().indices) {
                matrix[i][j] *= const
            }
        }
        return this
    }

    fun add(other: Matrix): Matrix {
        require(this.m == other.m && this.n == other.n) { "Incompatible matrices dimensions" }
        for (i in matrix.indices) {
            if (matrix.first().isEmpty()) {
                return this
            }
            for (j in matrix.first().indices) {
                matrix[i][j] += other.matrix[i][j]
            }
        }
        return this
    }

    fun sub(other: Matrix): Matrix {
        require(this.m == other.m && this.n == other.n) { "Incompatible matrices dimensions" }
        other.multByConst(-1.0)
        return add(other)
    }

    fun mult(other: Matrix): Matrix {
        require(this.n == other.m || this.m == other.n) { "Incompatible matrices dimensions" }
        var temp: Array<Array<Double>>
        if (this.n == other.m) {
            temp = Array(this.n) { Array(other.m) { 0.0 } }
        } else {
            temp = Array(this.m) { Array(other.n) { 0.0 } }
        }
        for (i in matrix.indices) {
            for (j in matrix.first().indices) {
                temp[i][j] = _multiplyRowAndColumn(getRow(i), other.getColumn(j))
            }
        }
        matrix = temp
        return this
    }

    private fun _multiplyRowAndColumn(row: Array<Double>, column: Array<Double>): Double {
        var sum = 0.0
        for (i in row.indices) {
            sum += row[i] * column[i]
        }
        return sum
    }

    fun getRow(row_index: Int): Array<Double> {
        val row: Array<Double> = Array(n) { 0.0 }
        for (j in 0 until m) {
            row[j] = matrix[row_index][j]
        }
        return row
    }

    fun getColumn(col_index: Int): Array<Double> {
        val col: Array<Double> = Array(m) { 0.0 }
        for (i in 0 until n) {
            col[i] = matrix[i][col_index]
        }
        return col
    }

    override fun toString(): String {
        val str = StringBuilder("Rows: $n\nColumns: $m\nMatrix:\n")
        for (row in matrix) {
            for (elem in row) {
                str.append("$elem ")
            }
            str.append("\n")
        }
        return str.toString()
    }
}