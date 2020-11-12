package com.company;
import static java.lang.System.out;


public class Main {

    public static void main(String[] args) {
        Matrix A = new Matrix(3, 3);
        A.set_value(1, 0, 0);
        A.set_value(2, 0, 1);
        A.set_value(3, 1, 0);
        A.set_value(4, 1, 1);
        A.set_value(5, 0, 2);
        A.set_value(6, 1, 2);
        A.set_value(7, 2, 2);
        A.set_value(8, 2, 0);
        A.set_value(9, 2, 1);

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

        out.println(A.equals(B));

    }
}
