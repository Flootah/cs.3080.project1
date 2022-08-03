# Matrix Calculator
 Program that completes a system of equations in the form of a matrix. User can manually input matrices or choose from a text file. Program includes two ways to solve a system of equations:
 
## Gaussian Elimination
Gaussian elimination, also known as row reduction, is an algorithm for solving systems of linear equations. It consists of a sequence of operations performed on the corresponding matrix of coefficients.
 ```
/**
 * Gaussian Elimination
 * Solves a system of linear equations (up to 10) using the Gaussian
 * elimination method with Scaled Partial Pivoting method.
 * User can either enter number of equations and then give coefficients
 * from command line or can specify a text file with a coefficient matrix.
 * 
 * example input:
 * 2 3 0 8
 * -1 2 -1 0
 * 3 0 2 9
 * 
 * output:
 * x=1
 * y=2
 * z=3
 ```
## Gauss-Siedel Iterative Method
The Gauss–Seidel method, also known as the Liebmann method or the method of successive displacement, is an iterative method used to solve a system of linear equations.

```
/**
 * Gauss-Siedel
 * Solves a system of linear equations (up to 10) using the Gauss Siedel Method.
 * 
 * example input:
 * 2 3 0 8
 * -1 2 -1 0
 * 3 0 2 9
 * 
 * output:
 * x=1
 * y=2
 * z=3
 ```
