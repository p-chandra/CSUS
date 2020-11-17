;Pawan Chandra
;219493392
;CSC 135

#lang racket

;Takes the absolute value of two inputs
(define (make6 x y)(test(abs x)(abs y)))

;Checks if both numbers are greater than what is required
(define (test x y)
  (if (and(> x 99999)(> y 99))(convert x y) (write "Invalid Input")))

;Replaces the last 3 numbers from x with y
(define (convert x y) (write (+(*(quotient x 1000) 1000) y)))
