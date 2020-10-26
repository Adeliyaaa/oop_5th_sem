package com.company;
import java.util.Arrays;

import static java.lang.System.out;

public class Matrix {
    private final int rows;             // number of rows
    private final int cols;             // number of columns
    private final double[][] data;   // array

    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("This matrix cannot exist");
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    public Matrix(double[][] data) {
        this.data = data.clone();
        rows = this.data.length;
        cols = this.data[0].length;
    }


    //the transpose of the matrix
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);

        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                result.data[col][row] = data[row][col];
            }
        }
        return result;
    }

    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (rows != B.rows || cols != B.cols)
            throw new IllegalArgumentException("Wrong dimensions");
        if (B.rows != A.rows || B.cols != A.cols) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(rows, cols);
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                C.data[row][col] = A.data[row][col] + B.data[row][col];
        return C;
    }

    //set value for a specific cell
    public void set_value(double value, int row, int col) {
        if (row < 0 || cols < 0 || row >= rows || col >= cols)
            throw new IllegalArgumentException("Wrong indices");
        data[row][col] = value;
    }

    //get value of a specific cell
    public double get_value(int row, int col) {
        if (row < 0 || cols < 0 || row >= rows || col >= cols)
            throw new IllegalArgumentException("Wrong indices");
        return data[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return cols;
    }

    // return C = A - B
    public Matrix minus(Matrix B) {
        if (rows != B.rows || cols != B.cols)
            throw new IllegalArgumentException("Wrong dimensions");

        Matrix A = this;
        if (B.rows != A.rows || B.cols != A.cols) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(rows, cols);
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                C.data[row][col] = A.data[row][col] - B.data[row][col];
        return C;
    }

    public boolean equal (Matrix B) {
        Matrix A = this;
        if (B.rows != A.rows || B.cols != A.cols) throw new RuntimeException("Illegal matrix dimensions.");
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                if (A.data[row][col] != B.data[row][col]) return false;
        return true;
    }

    // return C = A * B
    public Matrix times(Matrix B) {
        if (cols != B.rows)
            throw new IllegalArgumentException("Wrong dimensions");

        Matrix A = this;
        if (A.cols != B.rows) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.rows, B.cols);
        for (int i = 0; i < C.rows; i++)
            for (int j = 0; j < C.cols; j++)
                for (int k = 0; k < A.cols; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }

    public double determinant() {
        if (rows != cols) throw new IllegalArgumentException("Matrix must be square!");
        else {
            return _determinant(data);
        }
    }

    private double _determinant(double[][] arr) {
        double result = 0;
        if (arr.length == 1) {
            result = arr[0][0];
            return result;
        }
        if (arr.length == 2) {
            result = arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];
            return result;
        }
        for (int i = 0; i < arr[0].length; i++) {
            double[][] temp = new double[arr.length - 1][arr[0].length - 1];

            for (int j = 1; j < arr.length; j++) {
                for (int k = 0; k < arr[0].length; k++) {
                    if (k < i) {
                        temp[j - 1][k] = arr[j][k];
                    } else if (k > i) {
                        temp[j - 1][k - 1] = arr[j][k];
                    }
                }
            }
            result += arr[0][i] * Math.pow(-1, i) * _determinant(temp);
        }
        return result;
    }

    public Matrix multiply(double scalar) {
        Matrix A = this;
        Matrix C = new Matrix(A.rows, A.cols);
        double value;
        for(int i = 0; i < A.rows; i++) {
            for(int j = 0; j < A.cols; j++) {
                value = scalar * A.get_value(i, j);
                C.set_value(value, i, j);
            }
        }

        return C;
    }

    @Override
    public String toString(){
        return "Matrix {\n" +
                "data = " + Arrays.deepToString(data) +
                "\n}";
    }


}
