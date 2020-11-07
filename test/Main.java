package com.company;
import static java.lang.System.out;


public class Main {

    public static void main(String[] args) {
        double[][] arr = { { 1, 2, 3 }, { 4, 5, 6 }, { 9, 1, 3} };
        Matrix A = new Matrix(arr);
        out.println(A);

        Matrix B = A.transpose();
        out.println(B);

        Matrix C = A.times(B);
        out.println(C);

        Matrix D = C.multiply(2);
        out.println(D);

        D = C.plus(A);
        out.println(D);

        D = D.minus(B);
        out.println(D);

        out.println(D.determinant());

        out.println(A.equal(B.transpose()));

    }
}
