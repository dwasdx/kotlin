class ComplexMatrix {
    private var mat: Array<Array<ComplexNumber>>
    private var n = 0
    private  var m = 0

    constructor() {
        n = 1
        this.m = 1
//        mat = Array<Array<ComplexNumber?>>(n) { arrayOfNulls<ComplexNumber>(this.m) }
        mat = Array(n) { Array(m) { ComplexNumber() } }
        mat[0][0] = ComplexNumber()
    }

    constructor(n: Int, m: Int) {
        require(!(n <= 0 || m <= 0)) { "One of the dimensions size is a negative integer" }
        mat = Array(n) { Array(m) { ComplexNumber() } }
        _sizeAllocate(n, m)
        for (i in 0 until n) {
            for (j in 0 until m) {
                val num = ComplexNumber(0, 0)
                mat[i][j] = num
            }
        }
    }

    constructor(src: Array<Array<ComplexNumber>>) {
        n = src.size
        m = src[0].size
        mat = src.clone()
    }

    constructor(src: ComplexMatrix) {
        n = src.n
        this.m = src.m
        mat = Array(src.n) { Array(src.m) { ComplexNumber() } }
        for (i in 0 until n) {
            for (j in 0 until m) {
                mat[i][j] = ComplexNumber(src.mat[i][j])
            }
        }
    }

    fun multByConst(c: Double): ComplexMatrix {
        for (i in 0 until n) {
            for (j in 0 until m) {
                mat[i][j].multByConst(c)
            }
        }
        return this
    }

    fun add(mat: ComplexMatrix): ComplexMatrix? {
        require(n == mat.n && m == mat.m) { "Matrices Dimensions do not coincide" }
        for (i in 0 until n) {
            for (j in 0 until this.m) {
                this.mat[i][j].add(mat.mat[i][j])
            }
        }
        return this
    }

    fun sub(mat: ComplexMatrix): ComplexMatrix? {
        return add(mat.multByConst(-1.0))
    }

    fun mult(other: ComplexMatrix): ComplexMatrix? {
    require(this.n == other.m || this.m == other.n) { "Incompatible matrices dimensions" }
        val temp: Array<Array<ComplexNumber>> = if (n == other.m) {
                Array(n) { Array(other.m) { ComplexNumber() } }
            } else {
                Array(m) { Array(other.n) { ComplexNumber() } }
            }
        for (i in temp.indices) {
            for (j in temp[i].indices) {
                temp[i][j] = _multiplyRowAndColumn(getRow(i), other.getColumn(j))
            }
        }
        _sizeAllocate(temp.size, temp[0].size)
        this.mat = temp
        return this
    }

    fun T(): ComplexMatrix? {
        val temp: Array<Array<ComplexNumber>> = Array(n) { Array(m) { ComplexNumber() } }
        for (i in temp.indices) {
            for (j in temp[i].indices) {
                temp[i][j] = mat[j][i]
            }
        }
        mat = temp
        return this
    }

    private fun _sizeAllocate(n: Int, m: Int) {
        this.n = n
        this.m = m
        mat = Array(n) { Array(m) { ComplexNumber() } }
    }

    private fun _multiplyRowAndColumn(row: Array<ComplexNumber>, column: Array<ComplexNumber>): ComplexNumber {
        val sum = ComplexNumber()
        for (i in row.indices) {
            sum.add(row[i].mult(column[i]))
        }
        return sum
    }

    fun getRow(row_index: Int): Array<ComplexNumber> {
        val row: Array<ComplexNumber> = Array(m) { ComplexNumber() }
        for (j in 0 until m) {
            row[j] = ComplexNumber(mat[row_index][j])
        }
        return row
    }

    fun getColumn(col_index: Int): Array<ComplexNumber> {
        val col: Array<ComplexNumber> = Array(m) { ComplexNumber() }
        for (i in 0 until n) {
            col[i] = ComplexNumber(mat[i][col_index])
        }
        return col
    }

    fun setElement(row_ind: Int, col_ind: Int, num: ComplexNumber) {
        mat[row_ind][col_ind] = num
    }

    fun getRowsCount(): Int {
        return n
    }

    fun getColumnCount(): Int {
        return m
    }

    override fun toString(): String {
        val str = StringBuilder("Rows: $n\nColumns: $m\nMatrix:\n")
        for (row in mat) {
            for (elem in row) {
                str.append("(").append(elem.real).append(" ").append(elem.img).append(") ")
            }
            str.append("\n")
        }
        return str.toString()
    }
}